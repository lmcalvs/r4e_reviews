/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 *  * This interface is used to generate a report
 * 
 * Contributors:
 *   Jacques Bouthillier -Initial implementation of the R4E interface report generation
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.report.impl;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Jacques Bouthillier
 */
public interface IR4EReport {

	// Report type to be generated
	public final String INSPECTION_RECORD_TYPE = "Inspection Record";

	public final String GLOBAL_REPORT_TYPE = "Global Report";

	public final String SINGLE_REPORT_TYPE = "List Single Report";

	// Report extension file
	public final String HTML_EXTENSION = "html";

	public final String PDF_EXTENSION = "pdf";

	/**
	 * Set the type of report
	 * 
	 * @param aReportType
	 */
	public void setReportType(String aReportType);

	/**
	 * Set the output format to generate the report
	 * 
	 * @param String
	 *            aFormatOutput
	 */
	public void setOuputFormat(String aFormatOutput);

	/**
	 * Register the list of selected reviews
	 * 
	 * @param File
	 *            [] aListSelectedReview
	 */
	public void setReviewListSelection(File[] aListSelectedReview);

	/**
	 * Generate the selected report
	 * 
	 * @param String
	 *            agroupFile File of the Group
	 * @param IProgressMonitor
	 *            aMonitor File of the Group
	 */
	public void handleReportGeneration(final String agroupFile, IProgressMonitor aMonitor);

	/**
	 * Test if the report type selected is an inspection record
	 * 
	 * @return Boolean
	 */
	public Boolean isInspectionRecord();

	/**
	 * Count the number of selected review
	 * 
	 * @return int
	 */
	public int selectedReviewNumber();

}
