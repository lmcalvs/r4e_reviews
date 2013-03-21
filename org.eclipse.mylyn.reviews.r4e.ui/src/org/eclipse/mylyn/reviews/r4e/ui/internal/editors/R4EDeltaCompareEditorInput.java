// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as the input class that feeds the eclipse compare
 * editor to internally compare 2 files to extract the differences (deltas)
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EDeltaCompareEditorInput extends CompareEditorInput {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fTargetElement
	 */
	private final ITypedElement fTargetElement;

	/**
	 * Field fBaseElement
	 */
	private final ITypedElement fBaseElement;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ECompareEditorInput.
	 * 
	 * @param aTargetVersion
	 *            R4EFileVersion
	 * @param aBaseVersion
	 *            R4EFileVersion
	 */
	public R4EDeltaCompareEditorInput(R4EFileVersion aTargetVersion, R4EFileVersion aBaseVersion) {
		super(new CompareConfiguration());
		fTargetElement = CommandUtils.createTypedElement(aTargetVersion);
		fBaseElement = CommandUtils.createTypedElement(aBaseVersion);
		getCompareConfiguration().setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.valueOf(true));
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getAncestorElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getAncestorElement() {
		return null; //Not supported for now	
	}

	/**
	 * Method getTargetElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getTargetElement() {
		return fTargetElement;
	}

	/**
	 * Method getBaseElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getBaseElement() {
		return fBaseElement;
	}

	/**
	 * Method prepareCompareInput
	 * 
	 * @param aMonitor
	 *            - IProgressMonitor
	 * @return Object
	 */
	public Object prepareCompareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		return prepareInput(monitor);
	}

	/**
	 * Method prepareInput
	 * 
	 * @param aMonitor
	 *            - IProgressMonitor
	 * @return Object
	 * @see org.eclipse.compare.CompareEditorInput#prepareInput(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		//Bug 392349
		if (null == fTargetElement && null == fBaseElement) {
			R4EUIPlugin.Ftracer.traceWarning("Nothing to compare, both sides are NULL");
			return null;
		}
		//Get Types Elements and compare the sides		
		final Differencer differencer = new Differencer();
		return differencer.findDifferences(false, monitor, null, null, fTargetElement, fBaseElement);
	}
}
