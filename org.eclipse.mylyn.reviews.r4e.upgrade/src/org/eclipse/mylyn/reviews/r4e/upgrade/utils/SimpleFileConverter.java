/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class defines and implements a simple file converter
 * 
 * Contributors:
 * Miles Parker  - Initial implementation
 * Sebastien Dubois - Integration into the R4E upgrade plugin.
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.reviews.r4e.core.Activator;

/**
 * @author Miles Parker (Tasktop)
 * @author Sebastien Dubois 
 */
public class SimpleFileConverter extends Job {

	
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field LINE_FEED. (value is "\n" or "\r\n") Depends on operating system
	 */
	public static final String LINE_FEED = System.getProperty("line.separator");
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fLogWriter.
	 */
	BufferedWriter fLogWriter;

	/**
	 * Field fFilesConverted.
	 */
	int fFilesConverted;

	/**
	 * Field fLinesConverted.
	 */
	int fLinesConverted;

	/**
	 * Field fExtension.
	 */
	private final String fExtension;

	/**
	 * Field fPatterns.
	 */
	private Pattern[] fPatterns;

	/**
	 * Field fReplacements.
	 */
	private final Replacement[] fReplacements;

	/**
	 * Field fRootUri.
	 */
	private URI fRootUri;

	/**
	 * Field fIgnoreExtension.
	 */
	private final String[] fIgnoreExtensions;

	/**
	 * Field fMonitor.
	 */
	private IProgressMonitor fMonitor;
	
	/**
	 * Field fRecurse.
	 */
	private boolean fRecurse;
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Inner Class that wraps replacement information
	 */
	public static class Replacement {
		String fMatch;
		String fReplace;

		public Replacement(String aMatch, String aReplace) {
			super();
			fMatch = aMatch;
			fReplace = aReplace;
		}
		
		public String getMatch() {
			return fMatch;
		}
		
		public String getReplace() {
			return fReplace;
		}
	}

	/**
	 * Constructor
	 * @param aExtension - String
	 * @param aIgnoreExtensions - String[]
	 * @param aReplacements - String[]
	 * @param aRecurse - boolean
	 */
	private SimpleFileConverter(String aExtension, String[] aIgnoreExtensions, Replacement[] aReplacements, boolean aRecurse) {
		super("Convert " + aExtension + " files.");
		fExtension = aExtension;
		fIgnoreExtensions = aIgnoreExtensions;
		fReplacements = aReplacements;
		fRecurse = aRecurse;
	}

	/**
	 * Constructor
	 * @param aParent - EObject
	 * @param aExtension - String
	 * @param aIgnoreExtensions - String[]
	 * @param aReplacements - String[]
	 * @param aRecurse - boolean
	 */
	public SimpleFileConverter(EObject aParent, String aExtension, String[] aIgnoreExtensions, Replacement[] aReplacements, boolean aRecurse) {
		this(aExtension, aIgnoreExtensions, aReplacements, aRecurse);
		fRootUri = aParent.eResource().getURI().trimSegments(1);
	}

	/**
	 * Constructor
	 * @param aUri - URI
	 * @param aExtension - String
	 * @param aIgnoreExtensions - String[]
	 * @param aReplacements - String[]
	 * @param aRecurse - boolean
	 */
	public SimpleFileConverter(URI aUri, String aExtension, String[] aIgnoreExtensions, Replacement[] aReplacements, boolean aRecurse) {
		this(aExtension, aIgnoreExtensions, aReplacements, aRecurse);
		fRootUri = aUri;
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method run.
	 * @param aMonitor - IProgressMonitor
	 * @return IStatus
	 * @see org.eclipse.core.runtime.jobs.Job#run(IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor aMonitor) {
		fMonitor = aMonitor;
		fPatterns = new Pattern[fReplacements.length];
		for (int i = 0; i < fPatterns.length; i++) {
			fPatterns[i] = Pattern.compile(fReplacements[i].getMatch());
		}
		String filePath = (fRootUri.toFileString());
		String folderPath;
		File file = new File(filePath);
		if (file.isDirectory()) {
			folderPath = filePath;
		} else {
			folderPath = file.getParent();
		}
		aMonitor.beginTask("Converting Files", count(file));
		File logFile = new File(folderPath + File.separator + "conversion.log");
		try {
			logFile.createNewFile();
			fLogWriter = new BufferedWriter(new FileWriter(logFile));
			log("R4E Common Model Conversion Log: " + logFile.getAbsolutePath() + LINE_FEED + LINE_FEED);
			log("Root Folder: " + folderPath);
			convert(file);
			fLogWriter.close();
		} catch (IOException e) {
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Couldn't convert model at URI: " + file, e);
		}
		return Status.OK_STATUS;
	}

	/**
	 * Method convert.
	 * @param aFile - File
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void convert(File aFile) throws FileNotFoundException, IOException {
		Path path = new Path(aFile.getAbsolutePath());
		if (!aFile.isDirectory() && path.getFileExtension() == null) {
			return;
		}
		for (String ext : fIgnoreExtensions) {
			if (StringUtils.equals(ext, path.getFileExtension())) {
				return;
			}
		}
		if (aFile.exists()) {
			if (aFile.isDirectory()) {
				for (File member : aFile.listFiles()) {
					if (fRecurse || !aFile.isDirectory()) {
						convert(member);
					}
				}
				//return;
			} else {
				if (path.getFileExtension() == null) {
					return;
				}
				if (path.getFileExtension().equals(fExtension)) {
					log(LINE_FEED + "    " + aFile.getAbsolutePath());
					fFilesConverted++;
					BufferedReader br = new BufferedReader(new FileReader(aFile));
					StringBuilder fileContents = new StringBuilder(8000);
					int lineNum = 0;
					while (br.ready()) {
						String line = br.readLine();
						String convert = convert(line);
						fileContents.append(convert + LINE_FEED);
						if (!line.equals(convert)) {
							String lineNumString = StringUtils.leftPad(lineNum + "", 5);
							log("      " + lineNumString + ":  " + line + LINE_FEED + "              " + convert);
						}
						lineNum++;
					}
					br.close();
					BufferedWriter writer = new BufferedWriter(new FileWriter(aFile));
					writer.write(fileContents.toString());
					writer.close();
				}
			}
		}
		fMonitor.worked(1);
	}

	/**
	 * Method convert.
	 * @param aLine - String
	 * @return String
	 */
	private String convert(String aLine) {
		String result = aLine;
		for (int i = 0; i < fPatterns.length; i++) {
			Matcher matcher = fPatterns[i].matcher(result);
			result = matcher.replaceAll(fReplacements[i].getReplace());
		}
		return result;
	}
	
	/**
	 * Method count.
	 * @param aFile - File
	 * @return int
	 */
	protected int count(File aFile) {
		int count = 0;
		Path path = new Path(aFile.getAbsolutePath());
		if (!aFile.isDirectory() && path.getFileExtension() == null) {
			return 0;
		}
		for (String ext : fIgnoreExtensions) {
			if (StringUtils.equals(ext, path.getFileExtension())) {
				return 0;
			}
		}
		if (aFile.exists()) {
			count++;
			if (aFile.isDirectory()) {
				for (File member : aFile.listFiles()) {
					count += count(member);
				}
			}
		}
		return count;
	}

	/**
	 * Method log.
	 * @param aItem - String
	 * @throws RuntimeException
	 */
	private void log(String aItem) {
		System.out.println(aItem);
		try {
			fLogWriter.write(aItem + LINE_FEED);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
