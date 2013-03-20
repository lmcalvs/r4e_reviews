/*******************************************************************************
 * Copyright (c) 2013 Ericsson and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.rfs.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.pack.PackWriter;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@SuppressWarnings("nls")
public class R4ERemoteRepository {

	// --------------------------------------------------------------------------
	// Attributes
	// --------------------------------------------------------------------------

	// The GerritClient singleton
	private static R4ERemoteRepository fInstance = null;

	// --------------------------------------------------------------------------
	// Constructors
	// --------------------------------------------------------------------------

	public static R4ERemoteRepository getInstance() {
		if (fInstance == null) {
			fInstance = new R4ERemoteRepository();
		}
		return fInstance;
	}

	private R4ERemoteRepository() {
	}

	// --------------------------------------------------------------------------
	// Command: list-reviews
	// --------------------------------------------------------------------------

	private static final String R4E_LIST_CMD = "R4E list-reviews "; //$NON-NLS-1$

	/**
	 * Get the list of R4E reviews on the server for the given project
	 */
	public List<String> listReviews(String group) {
		String command = new StringBuilder(R4E_LIST_CMD).append(group).toString();
		List<String> gerritResult = execGerritCommand(command);

		// Relies heavily on the plugin's idiosyncrasies...
		// - remove first and last 2 lines
		// - keep only the ref names
		List<String> result = new ArrayList<String>();
		for (int i = 2; i < gerritResult.size() - 2; i++) {
			result.add(gerritResult.get(i).substring(45));
		}
		return result;
	}

	// --------------------------------------------------------------------------
	// Command: push-review
	// --------------------------------------------------------------------------

	private static final String R4E_PUSH_CMD = "R4E push-review "; //$NON-NLS-1$

	private static final String R4E_REVIEW_OPT = " --review "; //$NON-NLS-1$

	private static final String REF_PREFIX = "refs/for/"; //$NON-NLS-1$

	private static final String ROOT_COMMIT_ID = "0000000000000000000000000000000000000000";

	/**
	 * Push an R4E review on the server
	 * 
	 * @throws Exception
	 */
	public void pushReview(String reviewGroup, String reviewName) throws Exception {

		// Commit any change
		R4ELocalRepository.getInstance().openReview(reviewName);
		ObjectId reviewCommit = R4ELocalRepository.getInstance().commitReview("test");

		// Copy the commit information to the proper location
		String refPath = REF_PREFIX + "/" + reviewName; //$NON-NLS-1$
		Repository repo = R4ELocalRepository.getInstance().getRepository();
		RefDatabase db = repo.getRefDatabase();
		RefUpdate update = db.newUpdate(refPath, false);
		update.setNewObjectId(reviewCommit);
		update.update();

		// Push the reference to the shared review repository
		ObjectId prevCommit = ObjectId.fromString(ROOT_COMMIT_ID);
		PackWriter packWriter = preparePack(repo, prevCommit, reviewCommit);

		// Format the command
		String command = new StringBuilder(R4E_PUSH_CMD).append(reviewGroup)
				.append(R4E_REVIEW_OPT)
				.append(reviewName)
				.toString();

		sendPack(command, reviewName, prevCommit, reviewCommit, packWriter);
	}

	/**
	 * Prepare the upload pack
	 * 
	 * @param repo
	 * @param prevCommitId
	 * @param newCommitId
	 * @return
	 */
	private PackWriter preparePack(Repository repo, ObjectId prevCommitId, ObjectId newCommitId) {
		PackWriter packWriter = new PackWriter(repo);
		packWriter.setUseCachedPacks(true);
		packWriter.setReuseDeltaCommits(true);

		Set<ObjectId> want = new HashSet<ObjectId>();
		want.add(newCommitId);
		Set<ObjectId> have = new HashSet<ObjectId>();
		have.add(prevCommitId);
		try {
			packWriter.preparePack(NullProgressMonitor.INSTANCE, want, have);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packWriter;
	}

	/**
	 * Upload the review
	 * 
	 * @param connection
	 * @param reviewRef
	 * @param prevCommitId
	 * @param newCommitId
	 * @param pack
	 * @throws IOException
	 * @throws JSchException
	 */
	private void sendPack(String command, String branch, ObjectId prevCommitId, ObjectId newCommitId, PackWriter pack)
			throws IOException, JSchException {

		// Initiate the session
		Session session = openSshSession();
		session.connect();

		// Setup the command
		System.out.println("$ " + command);
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);

		// Setup the streams
		InputStream stdin = channel.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));

		OutputStream stdout = channel.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdout));

		// Issue the command
		channel.connect();

		// Receive the set of references from the server 
		while (true) {
			char[] buffer = new char[4];
			reader.read(buffer);
			String rawLength = new String(buffer);
			System.out.print("  " + rawLength + " ");
			try {
				int length = Integer.parseInt(rawLength, 16);
				if (length == 0) {
					System.out.println();
					break;
				}
			} catch (Exception e) {
			}
			String line = reader.readLine();
			System.out.println(line);
			if (line == null) {
				break;
			}
		}

		// Send our stuff
		StringBuilder commitRef = new StringBuilder(prevCommitId.getName()).append(' ')
				.append(newCommitId.getName())
				.append(' ')
				.append(REF_PREFIX)
				.append(branch);
		String refLength = String.format("%04x", commitRef.length() + 4);
		writer.write(refLength + commitRef);
		writer.flush();
		System.out.println("  " + refLength + " " + commitRef);
		writer.write("0000");
		System.out.println("  0000");
		System.out.println();
		writer.flush();

		// Upload the pack
		pack.writePack(NullProgressMonitor.INSTANCE, NullProgressMonitor.INSTANCE, stdout);

		// Wait for ACK - if any
		String line = reader.readLine();
		while (line != null && line.length() > 0) {
			System.out.println("  " + line);
			line = reader.readLine();
		}

		// Terminate the connection/session
		reader.close();
		writer.close();
		channel.disconnect();
		session.disconnect();

		// Show exit status, if available (otherwise "null")
		System.out.println("rc: " + channel.getExitStatus());
	}

	// --------------------------------------------------------------------------
	// Command: fetch-review
	// --------------------------------------------------------------------------

	private static final String R4E_FETCH_CMD = "R4E fetch-review "; //$NON-NLS-1$

	/**
	 * Get an R4E review from the server
	 */
	public void fetchReview(String reviewGroup, String reviewName) throws Exception {

		// Format the command
		String command = new StringBuilder(R4E_FETCH_CMD).append(reviewGroup)
				.append(R4E_REVIEW_OPT)
				.append(reviewName)
				.toString();

		// Initiate the session
		Session session = openSshSession();
		session.connect();

		// Setup the command
		System.out.println("$ " + command);
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);

		// Setup the streams
		InputStream stdin = channel.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));

		OutputStream stdout = channel.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdout));

		// Issue the command
		channel.connect();

		// Server answers with the list of its matching references
		List<String> wantedRefs = new ArrayList<String>();
		List<String> refPaths = new ArrayList<String>();
		while (true) {
			char[] buffer = new char[4];
			reader.read(buffer);
			String rawLength = new String(buffer);
			System.out.print("  " + rawLength + " ");
			int length = Integer.parseInt(rawLength, 16);
			if (length == 0) {
				System.out.println();
				break;
			}
			String line = reader.readLine();
			wantedRefs.add(line.substring(0, 40));
			int startIndex = 41;
			int endIndex = line.indexOf('\0');
			if (endIndex != -1) {
				refPaths.add(line.substring(startIndex, endIndex));
			} else {
				refPaths.add(line.substring(startIndex));
			}
			System.out.println(line);
		}
		String wantedRef = wantedRefs.get(wantedRefs.size() - 1);

		// Client requests a review
		String repoCapabilities = ""; // We don't advertise any capability - no need for now
		StringBuilder commitRef = new StringBuilder("want " + wantedRefs.get(0) + '\0' + repoCapabilities);
		String refLength = String.format("%04x", commitRef.length() + 4);
		System.out.println("  " + refLength + " " + commitRef);
		writer.write(refLength + commitRef);
		writer.flush();

		for (int i = 1; i < wantedRefs.size(); i++) {
			commitRef = new StringBuilder("want " + wantedRefs.get(i));
			refLength = String.format("%04x", commitRef.length() + 4);
			System.out.println("  " + refLength + " " + commitRef);
			writer.write(refLength + commitRef);
			writer.flush();
		}
		System.out.println("  " + "0000");
		writer.write("0000");
		writer.flush();

		System.out.println("  " + "0009 done");
		writer.write("0009done\n");
		writer.flush();

		// Get the NAK
		char[] buffer = new char[4];
		reader.read(buffer);
		String rawLength = new String(buffer);
		System.out.print("  " + rawLength + " ");
		int length = Integer.parseInt(rawLength, 16);
		if (length != 0) {
			System.out.println(reader.readLine());
		}

		// Get the packed data
		FileRepository repo = (FileRepository) R4ELocalRepository.getInstance().getRepository();
		String objDir = repo.getObjectDatabase().getDirectory().getAbsolutePath();
		String packFileName = objDir + "/pack/" + "pack-" + wantedRef + ".pack";
		OutputStream out = new FileOutputStream(packFileName);
		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(out));
		char[] cbuf = new char[1024];
		int len = reader.read(cbuf, 0, 1024);
		while (len > 0) {
			writer2.write(cbuf, 0, len);
			len = reader.read(cbuf, 0, 1024);
		}
		writer2.close();

		// Terminate the connection/session
		reader.close();
		writer.close();
		channel.disconnect();
		session.disconnect();

		// Add the references
		RefDatabase db = repo.getRefDatabase();
		for (int i = 0; i < wantedRefs.size(); i++) {
			RefUpdate update = db.newUpdate(refPaths.get(i), false);
			ObjectId objectId = ObjectId.fromString(wantedRefs.get(i));
			update.setNewObjectId(objectId);
			update.update();
		}

		// Checkout the latest
		int latest = -1;
		for (int i = 0; i < wantedRefs.size(); i++) {
			String refPath = refPaths.get(i);
			String seqNum = refPath.substring(refPath.lastIndexOf('/') + 1);
			int seq = Integer.parseInt(seqNum);
			if (seq > latest) {
				latest = seq;
			}
		}

		System.out.println("> Checkout  " + wantedRefs.get(latest) + " " + refPaths.get(latest) + " (latest)");
		R4ELocalRepository.getInstance().checkoutReview(reviewName, wantedRefs.get(latest));

		// Show exit status, if available (otherwise "null")
		System.out.println("rc: " + channel.getExitStatus());
	}

	// --------------------------------------------------------------------------
	// Helper functions
	// --------------------------------------------------------------------------

	/**
	 * Establish an SSH connection with the Gerrit server
	 * 
	 * @return
	 * @throws JSchException
	 */
	/*
	 * TODO: Add a preference page and pick the security info from it
	 */
	private Session openSshSession() throws JSchException {
		String HOSTNAME = "localhost";
		int PORT = 29418;
		String USERNAME = "francois";
		String SSH_DIR = "/home/francois/.ssh/";
		String KEYFILE = SSH_DIR + "id_rsa";
		String KNOWN_HOSTS = SSH_DIR + "known_hosts";

		JSch jsch = new JSch();
		jsch.setKnownHosts(KNOWN_HOSTS);
		jsch.addIdentity(KEYFILE);
		Session session = jsch.getSession(USERNAME, HOSTNAME, PORT);
		return session;
	}

	/**
	 * @param command
	 * @return
	 */
	private List<String> execGerritCommand(String command) {
		List<String> result = new ArrayList<String>();
		int rc = -1;
		try {
			// Initiate the session
			Session session = openSshSession();
			session.connect();

			// Setup the command
			System.out.println("$ " + command);
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// Setup the input stream
			InputStream input = channel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			// Issue the command
			channel.connect();

			// Get the server response
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				System.out.println("  " + line);
				result.add(line);
			}

			// Terminate the connection/session
			reader.close();
			channel.start();
			channel.disconnect();
			session.disconnect();

			rc = channel.getExitStatus();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		}

		// Show exit status
		System.out.println("rc: " + rc);
		return result;
	}

}
