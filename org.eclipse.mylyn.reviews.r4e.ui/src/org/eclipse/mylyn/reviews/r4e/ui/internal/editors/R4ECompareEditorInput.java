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

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
	 * Method getAncestorElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getAncestorElement() {
		return fAncestor;
	}

	/**
	 * Method getLeftElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getLeftElement() {
		return fLeft;
	}

	/**
	 * Method getRightElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getRightElement() {
		return fRight;
	}

	/**
	 * Method prepareCompareInputNoEditor.
	 */
	public void prepareCompareInputNoEditor() {
		prepareCompareInput(null);
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
	 * Method getAdapter.
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.compare.CompareEditorInput#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (IFile.class.equals(adapter)) {
			if (null != getWorkspaceElement()) {
				return getWorkspaceElement().getResource();
			}
			return null;
		}
		return super.getAdapter(adapter);
	}

	/**
	 * Method fireInputChange.
	 */
	@Override
	protected void fireInputChange() { // $codepro.audit.disable emptyMethod
		// Not implemented for now
	}

	/**
	 * Method getWorkspaceElement.
	 * 
	 * @return R4EFileTypedElement
	 */
	private R4EFileTypedElement getWorkspaceElement() {
		if (fLeft instanceof R4EFileTypedElement) {
			return (R4EFileTypedElement) fLeft;
		}
		return null;
	}

	/**
	 * Method prepareCompareInput.
	 * 
	 * @param aMonitor
	 *            IProgressMonitor
	 * @return ICompareInput
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @see org.eclipse.compare.CompareEditorInput#prepareCompareInput(IProgressMonitor)
	 */
	@Override
	protected ICompareInput prepareCompareInput(IProgressMonitor aMonitor) {

		if (null != aMonitor) {
			aMonitor.beginTask("R4E Compare", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		}

		// Set the label values for the compare editor
		initLabels();

		// Build the diff node to compare the files		
		final Differencer differencer = new Differencer();

		//Store the differences here, we might need them later
		final Object differences = differencer.findDifferences(false, aMonitor, null, fAncestor, fLeft, fRight);

		/* We might want to do something here in the future
		node.addCompareInputChangeListener(new ICompareInputChangeListener() {
			@Override
			public void compareInputChanged(ICompareInput source) {
			}
		});
		*/
		return (ICompareInput) differences;
	}

	/**
	 * Method initLabels.
	 */
	private void initLabels() {
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
	}

	/**
	 * Method createContents.
	 * 
	 * @param aParent
	 *            Composite
	 * @return Control
	 * @see org.eclipse.compare.CompareEditorInput#createContents(Composite)
	 */
	@Override
	public Control createContents(Composite aParent) {
		final Control control = super.createContents(aParent);

		//Go to the correct element in the compare editor
		UIUtils.selectElementInEditor(this);
		return control;
	}
}
