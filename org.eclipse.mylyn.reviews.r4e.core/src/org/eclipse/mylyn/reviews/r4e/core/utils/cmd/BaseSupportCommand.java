/*******************************************************************************
 * Copyright (c) 2010 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * 	 Ericsson Research Canada - Initial API and Implementation 
 *   Alvaro Sanchez-Leon - Adapted for Review for Eclipse
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils.cmd;

import java.io.IOException;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.Activator;

/**
 * @author lmcbout
 * 
 */
public class BaseSupportCommand {

	/**
	 * @param cmdPath
	 * @param command
	 * @param waitFor
	 * @param errorNotified
	 * @return
	 * @throws IOException
	 */
	protected String executeWithStringReturned(String cmdPath, List<String> command, boolean waitFor,
			boolean errorNotified) throws IOException {

		List<String> results = execute(cmdPath, command, waitFor, errorNotified);

		if (results == null || results.size() == 0) {
			Activator.Tracer.traceInfo("execute received no results");
			return new String("");
		}

		int size = results.size();
		if (size == 0) {
			return new String("");
		}

		// reasonable limit to 10 lines, if more lines needed, use method "execute"
		StringBuilder result = new StringBuilder();
		int maxLines = results.size() > 10 ? 10 : results.size();
		for (int i = 0; i < maxLines; i++) {
			result.append(results.get(i));
		}

		return result.toString();
	}

	/**
	 * @param cmdPath
	 * @param command
	 * @param waitFor
	 * @param notifyIfError
	 * @return
	 * @throws IOException
	 */
	private List<String> execute(String cmdPath, List<String> command, boolean waitFor /* Blocking */,
			boolean notifyIfError) throws IOException {

		List<String> results = null;

		ShellCommandManager manager = new ShellCommandManager(cmdPath, command);

		StreamThread outputStream = new StreamThread();
		StreamThread errorStream = new StreamThread();

		Activator.Tracer.traceInfo("Command Path: " + cmdPath + "\n" + command.toString());

		int exitValue = manager.execute(outputStream, errorStream, waitFor /* true=wait */);

		StringBuilder str = new StringBuilder();
		if (!(outputStream.isAlive() || errorStream.isAlive())) {
			str.append("outputStream  + errorStream CLOSED properly, Java Process exit value: ");
			// Popup.info(null, str);
			Activator.Tracer.traceInfo(str.toString() + exitValue);
		} else {
			// Shellmanager waits for thread termination before providing the
			// result so this should not happen unless the waiting period have elapsed
			if (outputStream.isAlive()) {
				str.append("outputStream is alive: Java Process exit value: " + exitValue);
			}

			if (errorStream.isAlive()) {
				str.append("\nerrorStream is alive: Java Process exit value: "
						+ exitValue);
				Activator.Tracer.traceInfo(str.toString());
			}
		}

		results = outputStream.getLines();

		// Print errors, if any
		if (notifyIfError) {
			List<String> lines = errorStream.getLines();
			if (lines.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : lines) {
					sb = sb.append(s + "\n");
				}

				sb.append("Path: " + cmdPath + "\n");
				sb.append("Command: \n");
				sb.append(command.toString());
				Activator.Tracer.traceInfo(sb.toString());
				if (exitValue != 5) {
					// Filter the eror code 5
					// Error code 5 = Access is Denied
					throw new IOException("R4E - Unable to perform operation: " + "\n" + sb.toString());
				}
			}
		}

		return results;
	}

}
