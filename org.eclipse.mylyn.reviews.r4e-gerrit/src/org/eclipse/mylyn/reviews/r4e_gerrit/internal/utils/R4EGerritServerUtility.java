/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements some utility for the Gerrit servers.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the server selection
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;


/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class R4EGerritServerUtility {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field GERRIT_PORT. (value is "":29418"")
	 */
	private static final String GERRIT_PORT = ":29418"; 

	/**
	 * Field AT. (value is ""@"")
	 */
	private static final String AT = "@"; 

	/**
	 * Field AT. (value is ""https://"")
	 */
	private static final String HTTPS = "https://"; 
	
	/**
	 * Field LAST_GERRIT_FILE. (value is ""lastGerrit.txt"")
	 */
	private static final String LAST_GERRIT_FILE = "lastGerrit.txt";

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private Map<Repository, String> fResult = new HashMap<Repository,String>();
	
	private R4EGerritServerUtility instance = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public R4EGerritServerUtility() {
		instance = this;
		mapConfiguredGerritServer();
	}

	// ------------------------------------------------------------------------
	// Methods Private
	// ------------------------------------------------------------------------
	private Map<Repository, String> mapConfiguredGerritServer () {
		RepositoryUtil repoUtil = org.eclipse.egit.core.Activator.getDefault().getRepositoryUtil();
		List<String> repoPaths = repoUtil.getConfiguredRepositories();
		RepositoryCache repositoryCache = org.eclipse.egit.core.Activator.getDefault().getRepositoryCache();
		Repository repo = null;
		
		//Reset the list of Gerrit server
		fResult.clear();

		for (String repoPath : repoPaths) {
			R4EGerritPlugin.Ftracer.traceInfo("List Gerrit repository: " + repoPath );
			File gitDir = new File(repoPath);
			if (!gitDir.exists()) {
				R4EGerritPlugin.Ftracer.traceInfo("Gerrit repository do not exist: " + gitDir.getPath());
				continue;
			
			}
			try {
				repo = repositoryCache.lookupRepository(gitDir);
				R4EGerritPlugin.Ftracer.traceInfo("\trepository config after lookup: " +
						repo.getConfig());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (repo != null) {
				Config config  = new Config(repo.getConfig());
				Set<String> remotes = config.getSubsections(ConfigConstants.CONFIG_REMOTE_SECTION);
				for (String remote: remotes) {
					String remoteURL = config.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
							remote,
							ConfigConstants.CONFIG_KEY_URL);
					R4EGerritPlugin.Ftracer.traceInfo("\t\t " + remote +" -> remoteURL: " + remoteURL );
					
					//Test if this is a Gerrit server
					String convertedRemoteURL = getReformatGerritServer(remoteURL) ;
					if (null != convertedRemoteURL  ) {
						fResult.put(repo, convertedRemoteURL);
					}
				}			
			}
		}

		return fResult;
	}
	
	//Note the Gerrit server for "git.eclipse.org" in config is 
	//      not the same as in the task Repository: "git.eclipse.org/r"
	/**
	 * Verify if the gerrit remote URL has the gerrit port (29418 )
	 * @param aRemoteURL
	 * @return String remote converted URL
	 */
	private String getReformatGerritServer(String aRemoteURL) {
		//Test if this is a Gerrit server or not
		String[] strParsePort = aRemoteURL.split(GERRIT_PORT);
		if (strParsePort.length == 2) {
			//Do not convert it for now
			return aRemoteURL;
//			//We found a Gerrit server, lets build the URL
//			//String[] strParseServer = strParsePort[0].split(AT);
//			int index = strParsePort[0].indexOf(AT);
//			String server = strParsePort[0].substring(++index);
//			StringBuilder sb = new StringBuilder();
//			sb.append(HTTPS);
//			sb.append(server);
//			return sb.toString();
		}
		return null;
	}

	private File getLastGerritFile () {
		IPath ipath = R4EGerritPlugin.getDefault().getStateLocation();
		String fileName = ipath.append(LAST_GERRIT_FILE).toPortableString();
		File file = new File (fileName);
		return file;
	}
	
	// ------------------------------------------------------------------------
	// Methods Public
	// ------------------------------------------------------------------------
	public R4EGerritServerUtility getDefault () {
		if (instance == null) {
			new R4EGerritServerUtility();
		}
		return instance;
	}
	
	/**
	 * Return the mapping of the available Gerrit server used in the user workspace
	 * @return Map<Repository, String>
	 */
	public Map<Repository, String> getGerritMapping () {
		return fResult;
	}
	
	/**
	 * Save the selected Gerrit server URL
	 * @param aURL
	 * @return Boolean
	 */
	public Boolean saveLastGerritServer (String aURL) {
		Boolean ok = true;
		File file = getLastGerritFile();
		try {
			FileWriter fw= new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(aURL);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			ok = false;
		}
		
		return ok;
	}
	
	/**
	 * Return the last selected Gerrit server used
	 * @return String
	 */
	public String getLastSavedGerritServer () {
		String lastGerritURL = null;
		File file = getLastGerritFile();
		if (file != null) {
			try {
				FileReader fr= new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				lastGerritURL = in.readLine();
				in.close();
			} catch (IOException e1) {
				//When there is no file, 
				//e1.printStackTrace();
			}			
		}
		return lastGerritURL;
	}
	
	/**
	 * Get the Gerrit URL based on the provided string
	 * 
	 * @param  Menu string aSt
	 * @return URL as a string
	 * 
	 */
	public String getMenuSelectionURL (String aSt) {
		String urlStr = null;
		if (!fResult.isEmpty()) {
			Set<Repository> mapSet = fResult.keySet();
			R4EGerritPlugin.Ftracer.traceInfo("-------------------");
			for (Repository key: mapSet) {
				if (key.getWorkTree().getName().equals(aSt)) {
					urlStr = fResult.get(key);
					
					R4EGerritPlugin.Ftracer.traceInfo("Map Key: " + key.getWorkTree().getName() + "\t URL: " + fResult.get(key));
					return urlStr;
				}
			}
		}
		
		return urlStr;
	}

	/**
	 * Read the Gerrit server to populate the list of reviews
	 */
	public void getReviewListFromServer () {
		//Get the Gerrit URL to query
		String urlToUsed = getLastSavedGerritServer ();
		
		if (urlToUsed != null) {
			//Initiate the request to populate the list of Reviews
			R4EGerritPlugin.Ftracer.traceInfo("use the following Gerrit URL to populate the list of reviews: " +  urlToUsed); 			
		} else {
			//Open the dialogue to populate a Gerrit server, Should not happen here
			R4EGerritPlugin.Ftracer.traceInfo("Need to open the dialogue to populate a gerrit server" ); 			
		}
		
	}

}
