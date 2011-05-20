// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as the input class that feeds the eclipse compare
 * editor
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.text.MessageFormat;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4ECompareEditorInput extends SaveableCompareEditorInput {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fConfig - the compare configuration
	 */
	private final CompareConfiguration fConfig;

	/**
	 * Field fAncestor - the optional element that will appear on the top of the compare editor
	 */
	private final ITypedElement fAncestor;

	/**
	 * Field fLeft - the element that will appear on the left side of the compare editor
	 */
	private final ITypedElement fLeft;

	/**
	 * Field fRight - the element that will appear on the right side of the compare editor
	 */
	private final ITypedElement fRight;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ECompareEditorInput.
	 * 
	 * @param aConfig
	 *            CompareConfiguration
	 * @param aAncestor
	 *            ITypedElement
	 * @param aLeft
	 *            ITypedElement
	 * @param aRight
	 *            ITypedElement
	 */
	public R4ECompareEditorInput(CompareConfiguration aConfig, ITypedElement aAncestor, ITypedElement aLeft,
			ITypedElement aRight) {
		super(aConfig, null);
		fConfig = aConfig;
		fAncestor = aAncestor;
		fLeft = aLeft;
		fRight = aRight;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getLeftElement.
	 * 
	 * @return FileRevisionTypedElement
	 */
	public ITypedElement getLeftElement() {
		return fLeft;
	}

	/**
	 * Method getRightElement.
	 * 
	 * @return FileRevisionTypedElement
	 */
	public ITypedElement getRightElement() {
		return fRight;
	}

	/**
	 * Method prepareInput.
	 * 
	 * @param aMonitor
	 *            IProgressMonitor
	 * @return Object
	 */
	@Override
	protected Object prepareInput(IProgressMonitor aMonitor) {

		if (null != aMonitor) {
			aMonitor.beginTask("R4E Compare", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		}

		// Set the label values for the compare editor
		if (null != fLeft) {
			final StringBuilder leftLabel = new StringBuilder("Target: " + fLeft.getName());
			if (fLeft instanceof R4EFileRevisionTypedElement) {
				leftLabel.append(" "
						+ ((R4EFileRevisionTypedElement) fLeft).getFileVersion().getVersionID().substring(0, 7));
			}
			fConfig.setLeftLabel(leftLabel.toString());
		}
		if (null != fRight) {
			final StringBuilder rightLabel = new StringBuilder("Base: " + fRight.getName());
			if (fRight instanceof R4EFileRevisionTypedElement) {
				rightLabel.append("_" + ((R4EFileRevisionTypedElement) fRight).getFileVersion().getVersionID());
			}
			fConfig.setRightLabel(rightLabel.toString());
		}

		// If the ancestor is not null, just put the file name as the workspace label
		if (null != fAncestor) {
			fConfig.setAncestorLabel(fAncestor.getName());
		}

		// Build the diff node to compare the files		
		Differencer differencer = new Differencer();

		//Store the differences here, we might need them later
		Object differences = differencer.findDifferences(false, aMonitor, null, fAncestor, fLeft, fRight);
		/* We might want to do something here in the future
		node.addCompareInputChangeListener(new ICompareInputChangeListener() {
			
			@Override
			public void compareInputChanged(ICompareInput source) {
			}
		});
		*/
		return differences;
	}

	/**
	 * Method getToolTipText.
	 * 
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		if (null != fLeft && null != fRight) {
			String format = null;

			// Set the label values for the compare editor
			StringBuilder leftLabel = null;
			if (null != fLeft) {
				leftLabel = new StringBuilder("Target: " + fLeft.getName());
				if (fLeft instanceof R4EFileRevisionTypedElement) {
					leftLabel.append("_" + ((R4EFileRevisionTypedElement) fLeft).getFileVersion().getVersionID());
				}
				fConfig.setLeftLabel(leftLabel.toString());
			}
			StringBuilder rightLabel = null;
			if (null != fRight) {
				rightLabel = new StringBuilder("Base: " + fRight.getName());
				if (fRight instanceof R4EFileRevisionTypedElement) {
					rightLabel.append("_" + ((R4EFileRevisionTypedElement) fRight).getFileVersion().getVersionID());
				}
				fConfig.setRightLabel(rightLabel.toString());
			}

			if (null != fAncestor) {
				format = CompareUI.getResourceBundle().getString("ResourceCompare.threeWay.tooltip"); //$NON-NLS-1$
				final String ancestorLabel = "";
				return MessageFormat.format(format, new Object[] { ancestorLabel, leftLabel, rightLabel });
			}
			format = CompareUI.getResourceBundle().getString("ResourceCompare.twoWay.tooltip"); //$NON-NLS-1$
			return MessageFormat.format(format, new Object[] { leftLabel, rightLabel });
		}
		// fall back
		return super.getToolTipText();
	}

	/**
	 * Method prepareCompareInput.
	 * 
	 * @param monitor
	 *            IProgressMonitor
	 * @return ICompareInput
	 */
	@Override
	protected ICompareInput prepareCompareInput(IProgressMonitor monitor) {
		// Nothing to do for now
		return null;
	}

	/**
	 * Method fireInputChange.
	 */
	@Override
	protected void fireInputChange() { // $codepro.audit.disable emptyMethod
		// Not implemented for now
	}

	/**
	 * Method canRunAsJob.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean canRunAsJob() {
		return false;
	}
}
