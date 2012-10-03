// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements a label decorator for the Review Navigator View.  
 * It is used to modify the label text and icons based on various criteria
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IColorDecorator;
import org.eclipse.jface.viewers.IFontDecorator;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorDecorator implements ILabelDecorator, IFontDecorator, IColorDecorator {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field DECORATOR_DISABLED_ID. (value is ""Disabled"")
	 */
	private static final String DECORATOR_DISABLED_ID = "Disabled"; //$NON-NLS-1$

	/**
	 * Field DECORATOR_REVIEWED_ID. (value is ""Reviewed"")
	 */
	private static final String DECORATOR_REVIEWED_ID = "Reviewed"; //$NON-NLS-1$

	/**
	 * Field DECORATOR_ADDED_ID. (value is ""Added"")
	 */
	private static final String DECORATOR_ADDED_ID = "Added"; //$NON-NLS-1$

	/**
	 * Field DECORATOR_REMOVED_ID. (value is ""Removed"")
	 */
	private static final String DECORATOR_REMOVED_ID = "Removed"; //$NON-NLS-1$

	/**
	 * Field DECORATOR_READONLY_ID. (value is ""Readonly"")
	 */
	private static final String DECORATOR_READONLY_ID = "Readonly"; //$NON-NLS-1$

	/**
	 * Field DECORATOR_OVERDUE_ID. (value is ""Overdue"")
	 */
	private static final String DECORATOR_OVERDUE_ID = "Overdue"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method addListener.
	 * 
	 * @param listener
	 *            ILabelProviderListener
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method isLabelProperty.
	 * 
	 * @param element
	 *            Object
	 * @param property
	 *            String
	 * @return boolean
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(Object, String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * Method removeListener.
	 * 
	 * @param listener
	 *            ILabelProviderListener
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method decorateImage.
	 * 
	 * @param aBaseImage
	 *            Image
	 * @param aElement
	 *            Object
	 * @return Image
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(Image, Object)
	 */
	public Image decorateImage(Image aBaseImage, Object aElement) { // $codepro.audit.disable

		//If the image is not already loaded, do it here
		Image currentOverlayImage;
		if (null != aBaseImage) {
			currentOverlayImage = aBaseImage;
		} else {
			currentOverlayImage = ((IR4EUIModelElement) aElement).getImage(((IR4EUIModelElement) aElement).getImageLocation());
		}

		ImageDescriptor topLeftOverlay = null;
		String topLeftOverlayId = ""; //$NON-NLS-1$

		ImageDescriptor topRightOverlay = null;
		String topRightOverlayId = ""; //$NON-NLS-1$

		ImageDescriptor bottomLeftOverlay = null;
		String bottomLeftOverlayId = ""; //$NON-NLS-1$

		ImageDescriptor bottomRightOverlay = null;
		String bottomRightOverlayId = ""; //$NON-NLS-1$

		ImageRegistry registry = R4EUIPlugin.getDefault().getImageRegistry();

		//Disabled element decorator
		if (!((IR4EUIModelElement) aElement).isEnabled()) {
			bottomRightOverlay = ImageDescriptor.createFromImage(((IR4EUIModelElement) aElement).getDisabledImage());
			bottomRightOverlayId = DECORATOR_DISABLED_ID;
		} else {
			if (((IR4EUIModelElement) aElement).isUserReviewed()
					|| (aElement instanceof R4EUIAnomalyExtended && ((R4EUIAnomalyExtended) aElement).isTerminalState())) {
				//Completed element decorator
				bottomRightOverlay = ImageDescriptor.createFromImage(((IR4EUIModelElement) aElement).getUserReviewedImage());
				bottomRightOverlayId = DECORATOR_REVIEWED_ID;
			}

			//Added, Removed or Modified file
			if (aElement instanceof R4EUIFileContext) {
				if (null == ((R4EUIFileContext) aElement).getBaseFileVersion()
						&& null != ((R4EUIFileContext) aElement).getTargetFileVersion()) {
					//Only target present, file was added
					bottomLeftOverlay = ImageDescriptor.createFromImage(((R4EUIFileContext) aElement).getAddedImage());
					bottomLeftOverlayId = DECORATOR_ADDED_ID;
				} else if (null != ((R4EUIFileContext) aElement).getBaseFileVersion()
						&& null == ((R4EUIFileContext) aElement).getTargetFileVersion()) {
					//Only base present, file was removed
					bottomLeftOverlay = ImageDescriptor.createFromImage(((R4EUIFileContext) aElement).getRemovedImage());
					bottomLeftOverlayId = DECORATOR_REMOVED_ID;
				} //else modified file
			}

			//Read-Only
			if (((IR4EUIModelElement) aElement).isReadOnly()) {
				topRightOverlay = ImageDescriptor.createFromImage(((IR4EUIModelElement) aElement).getReadOnlyImage());
				topRightOverlayId = DECORATOR_READONLY_ID;
			}

			//Due date passed
			if (((IR4EUIModelElement) aElement).isDueDatePassed()) {
				topLeftOverlay = ImageDescriptor.createFromImage(((IR4EUIModelElement) aElement).getDueDatePassedImage());
				topLeftOverlayId = DECORATOR_OVERDUE_ID;
			}
		}

		// Construct a new image identifier
		String baseImageId = ((IR4EUIModelElement) aElement).getImageLocation();
		String decoratedImageId = baseImageId + topLeftOverlayId + topRightOverlayId + bottomLeftOverlayId
				+ bottomRightOverlayId;

		// Return the stored image if we have one
		if (registry.get(decoratedImageId) == null) {
			DecorationOverlayIcon decoratedImage = new DecorationOverlayIcon(currentOverlayImage,
					new ImageDescriptor[] { topLeftOverlay, topRightOverlay, bottomLeftOverlay, bottomRightOverlay,
							null }) {
			};
			registry.put(decoratedImageId, decoratedImage);
		}
		return registry.get(decoratedImageId);
	}

	/**
	 * Method decorateText.
	 * 
	 * @param aText
	 *            String
	 * @param aElement
	 *            Object
	 * @return String
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(String, Object)
	 */
	public String decorateText(String aText, Object aElement) {
		if (aElement instanceof R4EUIFileContext) {
			if (!CommandUtils.useWorkspaceResource(((R4EUIFileContext) aElement).getTargetFileVersion())) {
				//File is not in sync with file present in workspace
				return "> " + aText;
			}
		}
		return null;
	}

	/**
	 * Method decorateFont.
	 * 
	 * @param aElement
	 *            Object
	 * @return Font
	 * @see org.eclipse.jface.viewers.IFontDecorator#decorateFont(Object)
	 */
	public Font decorateFont(Object aElement) { // $codepro.audit.disable
		if (null != R4EUIModelController.getActiveReview() && R4EUIModelController.getActiveReview().equals(aElement)) {
			return JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
		}
		if (isMyReview((IR4EUIModelElement) aElement)) {
			return JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);
		}
		return null;
	}

	/**
	 * Verifies whether this element in a Review element and, if so, if we are part of it
	 * 
	 * @param aElement
	 *            - the model element
	 * @return - true if this is a review and we are part of it, false otherwise
	 */
	private boolean isMyReview(IR4EUIModelElement aElement) {

		IR4EUIModelElement currentElement = aElement;
		while (null != currentElement) {
			if (currentElement instanceof R4EUIReviewBasic) {
				if (((R4EUIReviewBasic) currentElement).isParticipant(R4EUIModelController.getReviewer())) {
					return true;
				}
			}
			currentElement = currentElement.getParent();
		}
		return false;
	}

	/**
	 * Method decorateBackground
	 * 
	 * @param aElement
	 *            Object
	 * @return Color
	 */
	public Color decorateBackground(Object aElement) {
		return null;
	}

	/**
	 * Method decorateForeground
	 * 
	 * @param aElement
	 *            Object
	 * @return Color
	 */
	public Color decorateForeground(Object aElement) {
		if (aElement instanceof R4EUIReviewBasic) {
			if (((R4EReviewState) ((R4EUIReviewBasic) aElement).getReview().getState()).getState().equals(
					R4EReviewPhase.COMPLETED)) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
			}
		}

		if (aElement instanceof IR4EUIModelElement) {
			if (((IR4EUIModelElement) aElement).isDueDatePassed()) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
			} else if (((IR4EUIModelElement) aElement).isReadOnly()) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
			}
		}
		return null;
	}
}
