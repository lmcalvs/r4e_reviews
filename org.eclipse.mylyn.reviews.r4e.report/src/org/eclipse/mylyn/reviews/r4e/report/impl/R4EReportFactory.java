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
 *  * This implementation to generate a report
 * 
 * Contributors:
 *   Jacques Bouthillier -Initial implementation of the R4E report generation
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.report.impl;

import org.eclipse.mylyn.reviews.r4e.report.internal.dialog.ReportGeneration;


/**
 * @author Jacques Bouthillier
 *
 */
public class R4EReportFactory {
	
	public static IR4EReport getInstance() {
		return new ReportGeneration();
	}

}
