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
 * This class overrides AnnotationBarHoverManager to provide adapted management
 * of the R4E Annotation Controls, especially support for focus hotkeys.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import java.lang.reflect.Method;

import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.jface.text.AbstractHoverInformationControlManager;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IWidgetTokenKeeper;
import org.eclipse.jface.text.IWidgetTokenKeeperExtension;
import org.eclipse.jface.text.IWidgetTokenOwner;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.AnnotationBarHoverManager;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("restriction")
public class R4EAnnotationInformationControlManager extends AnnotationBarHoverManager implements IWidgetTokenKeeper,
		IWidgetTokenKeeperExtension {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Priority of the hovers managed by this manager. Default value: <code>0</code>;
	 */
	private final static int WIDGET_PRIORITY = 0;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EAnnotationInformationControlManager.
	 * 
	 * @param aRulerInfo
	 *            IVerticalRulerInfo
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aAnnotationHover
	 *            IAnnotationHover
	 * @param aCreator
	 *            IInformationControlCreator
	 */
	public R4EAnnotationInformationControlManager(IVerticalRulerInfo aRulerInfo, ISourceViewer aSourceViewer,
			IAnnotationHover aAnnotationHover, IInformationControlCreator aCreator) {
		super(aRulerInfo, aSourceViewer, aAnnotationHover, aCreator);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/*
	 */
	/**
	 * Method showInformationControl.
	 * 
	 * @param aSubjectArea
	 *            Rectangle
	 * @see org.eclipse.jface.text.AbstractInformationControlManager#showInformationControl(org.eclipse.swt.graphics.Rectangle)
	 */
	@Override
	protected void showInformationControl(Rectangle aSubjectArea) {
		if (((TextViewer) getSourceViewer()).requestWidgetToken(this, WIDGET_PRIORITY)) {
			super.showInformationControl(aSubjectArea);
		}
	}

	/**
	 * Method requestWidgetToken.
	 * 
	 * @param aOwner
	 *            IWidgetTokenOwner
	 * @return boolean
	 * @see org.eclipse.jface.text.IWidgetTokenKeeper#requestWidgetToken(IWidgetTokenOwner)
	 */
	public boolean requestWidgetToken(IWidgetTokenOwner aOwner) {
		super.hideInformationControl();
		return true;
	}

	/**
	 * Method requestWidgetToken.
	 * 
	 * @param aOwner
	 *            IWidgetTokenOwner
	 * @param aPriority
	 *            int
	 * @return boolean
	 * @see org.eclipse.jface.text.IWidgetTokenKeeperExtension#requestWidgetToken(IWidgetTokenOwner, int)
	 */
	public boolean requestWidgetToken(IWidgetTokenOwner aOwner, int aPriority) {
		super.hideInformationControl();
		return true;
	}

	/**
	 * Method setFocus.
	 * 
	 * @param aOwner
	 *            IWidgetTokenOwner
	 * @return boolean
	 * @see org.eclipse.jface.text.IWidgetTokenKeeperExtension#setFocus(IWidgetTokenOwner)
	 */
	public boolean setFocus(IWidgetTokenOwner aOwner) {
		final Class<AbstractHoverInformationControlManager> abstractHoverInformationControlManagerClass = AbstractHoverInformationControlManager.class;
		try {
			final Method declaredMethod = abstractHoverInformationControlManagerClass.getDeclaredMethod(
					"replaceInformationControl", new Class[] { Boolean.TYPE }); //$NON-NLS-1$
			declaredMethod.setAccessible(true);
			declaredMethod.invoke(this, new Object[] { new Boolean(true) });
			return true;
		} catch (Throwable t) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + t.toString() + " (" + t.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault().logError("Exception: " + t.toString(), (Exception) t); //$NON-NLS-1$
		}
		return true;
	}

	/**
	 * Method computeInformationControlLocation.
	 * 
	 * @param aSubjectArea
	 *            Rectangle
	 * @param aControlSize
	 *            Point
	 * @return Point
	 * @see org.eclipse.jface.text.AbstractInformationControlManager#computeInformationControlLocation(org.eclipse.swt.graphics.Rectangle,
	 *      org.eclipse.swt.graphics.Point)
	 */
	@Override
	protected Point computeInformationControlLocation(Rectangle aSubjectArea, Point aControlSize) {
		return computeLocation(aSubjectArea, aControlSize, AbstractInformationControlManager.ANCHOR_BOTTOM);
	}

	/**
	 * Method hideInformationControl.
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControlManager#hideInformationControl()
	 */
	@Override
	protected void hideInformationControl() {
		//We do this to avoid losing the annotation control widget when the Navigator view is activated to update the properties view.  See 
		//R4EAnnotationInformationControl#addAnnotationsInformation for more info
		if (!R4EAnnotationInformationControl.isPropertyViewBeingActivated()) {
			if (R4EAnnotationInformationControl.getPreSelectionActivePart() instanceof CompareEditor) {
				R4EAnnotationInformationControl.setPreSelectionActivePart(null);
			} else {
				super.hideInformationControl();
			}
		}
	}
}
