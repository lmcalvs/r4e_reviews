/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * 	 Ericsson AB - Initial API and Implementation 
 *   Alvaro Sanchez-Leon - Adapted for Review for Eclipse
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// No stop(), suspend, ... allowed
// If needed, see http://java.sun.com/j2se/1.5.0/docs/api/ Thread
public class StreamThread extends Thread {

	private InputStream stream;

	private List<String> lines;
	
	private boolean fin = false;

	public StreamThread() {
	}
	
	// Filtered out unwanted separation characters
	// from Java.process
	String strTwoDblQuoteLine = "^(\"+)";
	String strSingleQuoteline = "^(\"$)";
	Pattern pattRegex = Pattern.compile(strTwoDblQuoteLine);
	Pattern pattVis = Pattern.compile(strSingleQuoteline);
	Matcher match;
	
	public StreamThread(InputStream stream) {
		this.stream = stream;
		//this.setPriority(Thread.MAX_PRIORITY);
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public List<String> getLines() {
		return lines;
	}
	
	public boolean completed() {
		return fin;
	}

	public void run() {
		try {
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(reader);
			String line = null;
			lines = new ArrayList<String>();

			while ((line = buffer.readLine()) != null) {
				line = line.trim();
				match = pattVis.matcher(line);
				if (match.lookingAt()) {
					// Don't add single quote lines returned by process builder
				} else {
					// remove two double quote characters appended to the
					// begining of
					// the line by process builder when present
					match = pattRegex.matcher(line);
					line = match.replaceAll("");
					lines.add(line);
				}
			}
			reader.close();
			buffer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		fin = true;
		// Debug.print.println("StreamThread.run() " + getName() + " lines="
		// + lines.size());
	}

}
