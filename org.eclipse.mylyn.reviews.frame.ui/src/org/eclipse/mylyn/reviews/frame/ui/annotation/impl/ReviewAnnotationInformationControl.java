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
 * This class implements the controls that are shown in the Annotation Hover
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewAnnotationInformationControl extends AbstractInformationControl implements
		IInformationControlExtension2, ITextHoverExtension {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fInput.
	 */
	protected ReviewAnnotationHoverInput fInput = null;


	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewAnnotationInformationControl.
	 * 
	 * @param aParentShell
	 *            Shell
	 * @param aStatusFieldText
	 *            String
	 */
	public ReviewAnnotationInformationControl(Shell aParentShell, String aStatusFieldText) {
		super(aParentShell, aStatusFieldText);
		create();
	}

	/**
	 * Constructor for ReviewAnnotationInformationControl.
	 * 
	 * @param aParentShell
	 *            Shell
	 * @param aToolBarManager
	 *            ToolBarManager
	 */
	public ReviewAnnotationInformationControl(Shell aParentShell, ToolBarManager aToolBarManager) {
		super(aParentShell, aToolBarManager);
		create();
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method setInput.
	 * 
	 * @param aInput
	 *            Object
	 * @see org.eclipse.jface.text.IInformationControlExtension2#setInput(Object)
	 */
	public void setInput(Object aInput) {
		fInput = (ReviewAnnotationHoverInput) aInput;
		disposeDeferredCreatedContent();
		deferredCreateContent();
	}

	/**
	 * Method hasContents.
	 * 
	 * @return boolean
	 * @see org.eclipse.jface.text.IInformationControlExtension#hasContents()
	 */
	public boolean hasContents() {
		return fInput != null;
	}

	/**
	 * Method setVisible.
	 * 
	 * @param aVisible
	 *            boolean
	 * @see org.eclipse.jface.text.IInformationControl#setVisible(boolean)
	 */
	@Override
	public final void setVisible(boolean aVisible) {
		if (!aVisible) {
			disposeDeferredCreatedContent();
		}
		super.setVisible(aVisible);
	}

	/**
	 * Method disposeDeferredCreatedContent.
	 */
	protected abstract void disposeDeferredCreatedContent();

	/**
	 * Method createContent.
	 * 
	 * @param aParent
	 *            Composite
	 * @see org.eclipse.jface.text.AbstractInformationControl#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected abstract void createContent(Composite aParent);

	/**
	 * Method computeSizeHint.
	 * 
	 * @return Point
	 * @see org.eclipse.jface.text.IInformationControl#computeSizeHint()
	 */
	@Override
	public Point computeSizeHint() {
		final Point preferedSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

		final Point constrains = getSizeConstraints();
		if (constrains == null) {
			return preferedSize;
		}

		final int trimWidth = getShell().computeTrim(0, 0, 0, 0).width;
		final Point constrainedSize = getShell().computeSize(constrains.x - trimWidth, SWT.DEFAULT, true);

		//The width of the control is calculated differently if a toolbar is present
		int width;
		if (null != getToolBarManager()) {
			width = Math.max(preferedSize.x, constrainedSize.x);
		} else {
			width = Math.min(preferedSize.x, constrainedSize.x);
		}
		final int height = Math.max(preferedSize.y, constrainedSize.y);
		return new Point(width, height);
	}

	/**
	 * Create content of the hover. This is called after the input has been set.
	 */
	protected abstract void deferredCreateContent();

	/**
	 * Fills the toolbar actions, if a toolbar is available. This is called after the input has been set.
	 * 
	 * @param aSourceElement
	 *            Object
	 */
	protected abstract void updateToolbar(Object aSourceElement);

	/**
	 * Method addToolbarElementCommands.
	 * 
	 * @param aToolBarManager
	 *            ToolBarManager
	 * @param aSourceElement
	 *            Object
	 */
	protected abstract void addToolbarElementCommands(ToolBarManager aToolBarManager, Object aSourceElement);

	/**
	 * Method setColorAndFont.
	 * 
	 * @param aControl
	 *            Control
	 * @param aForeground
	 *            Color
	 * @param aBackground
	 *            Color
	 * @param aFont
	 *            Font
	 */
	protected void setColorAndFont(Control aControl, Color aForeground, Color aBackground, Font aFont) {
		aControl.setForeground(aForeground);
		aControl.setBackground(aBackground);
		aControl.setFont(aFont);

		if (aControl instanceof Composite) {
			final Control[] children = ((Composite) aControl).getChildren();
			for (Control element : children) {
				setColorAndFont(element, aForeground, aBackground, aFont);
			}
		}
	}

	/**
	 * Method addAnnotationsInformation.
	 */
	protected abstract void addAnnotationsInformation();

	/**
	 * Method getInformationPresenterControlCreator.
	 * 
	 * @return IInformationControlCreator
	 * @see org.eclipse.jface.text.IInformationControlExtension5#getInformationPresenterControlCreator()
	 */
	@Override
	public abstract IInformationControlCreator getInformationPresenterControlCreator();

	/**
	 * Method getInformationPresenter.
	 * 
	 * @return InformationPresenter
	 */
	public InformationPresenter getInformationPresenter() {
		return new InformationPresenter(ReviewAnnotationConfigFactory.createInformationControlCreator());
	}

	/**
	 * Method getHoverControlCreator.
	 * 
	 * @return IInformationControlCreator
	 * @see org.eclipse.jface.text.ITextHoverExtension#getHoverControlCreator()
	 */
	public abstract IInformationControlCreator getHoverControlCreator();
}
