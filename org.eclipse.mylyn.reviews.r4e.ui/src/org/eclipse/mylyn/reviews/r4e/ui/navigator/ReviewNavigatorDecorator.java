// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements a label decorator for the Review Navigator View.  
 * It is used to modify the label text and icons based on various criteria
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.navigator;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IFontDecorator;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.utils.OverlayImageIcon;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorDecorator implements ILabelDecorator, IFontDecorator {

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
	 * @param element
	 *            Object
	 * @return Image
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(Image, Object)
	 */
	public Image decorateImage(Image aBaseImage, Object aElement) { // $codepro.audit.disable

		OverlayImageIcon overlayIcon = null;
		//Disabled element decorator
		if (!((IR4EUIModelElement) aElement).isEnabled()) {
			overlayIcon = new OverlayImageIcon(aBaseImage, ((IR4EUIModelElement) aElement).getDisabledImage(),
					OverlayImageIcon.BOTTOM_RIGHT);
			return overlayIcon.getImage(); //No need to check for other decorators
		}

		//Completed element decoraor
		if (((IR4EUIModelElement) aElement).isUserReviewed()) {
			overlayIcon = new OverlayImageIcon(aBaseImage, ((IR4EUIModelElement) aElement).getUserReviewedImage(),
					OverlayImageIcon.BOTTOM_RIGHT);
			//return overlayIcon.getImage();	
		}

		//Added, Removed or Modified file
		if (aElement instanceof R4EUIFileContext) {
			if (null == ((R4EUIFileContext) aElement).getBaseFileVersion()
					&& null != ((R4EUIFileContext) aElement).getTargetFileVersion()) {
				//Only target present, file was added
				if (null == overlayIcon) {
					overlayIcon = new OverlayImageIcon(aBaseImage, ((R4EUIFileContext) aElement).getAddedImage(),
							OverlayImageIcon.BOTTOM_LEFT);
				} else {
					overlayIcon = new OverlayImageIcon(overlayIcon.getImage(),
							((R4EUIFileContext) aElement).getAddedImage(), OverlayImageIcon.BOTTOM_LEFT);
				}
			} else if (null != ((R4EUIFileContext) aElement).getBaseFileVersion()
					&& null == ((R4EUIFileContext) aElement).getTargetFileVersion()) {
				//Only base present, file was removed
				if (null == overlayIcon) {
					overlayIcon = new OverlayImageIcon(aBaseImage, ((R4EUIFileContext) aElement).getRemovedImage(),
							OverlayImageIcon.BOTTOM_LEFT);
				} else {
					overlayIcon = new OverlayImageIcon(overlayIcon.getImage(),
							((R4EUIFileContext) aElement).getRemovedImage(), OverlayImageIcon.BOTTOM_LEFT);
				}
			} //else modified file
		}
		return (Image) ((null == overlayIcon) ? overlayIcon : overlayIcon.getImage());
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
}
