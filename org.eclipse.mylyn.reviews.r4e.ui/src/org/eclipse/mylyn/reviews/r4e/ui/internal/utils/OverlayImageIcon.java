// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * This class implements an image descriptor that is used to overlay multiple images into one. It is used to decorate
 * icons
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class OverlayImageIcon extends CompositeImageDescriptor {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field TOP_LEFT. (value is 0)
	 */
	public static final int TOP_LEFT = 0;

	/**
	 * Field TOP_RIGHT. (value is 1)
	 */
	public static final int TOP_RIGHT = 1;

	/**
	 * Field BOTTOM_LEFT. (value is 2)
	 */
	public static final int BOTTOM_LEFT = 2;

	/**
	 * Field BOTTOM_RIGHT. (value is 3)
	 */
	public static final int BOTTOM_RIGHT = 3;

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fBaseImage. Base image of the object
	 */
	private final Image fBaseImage;

	/**
	 * Field fOverlayImage. Image to overlay on base image
	 */
	private final Image fOverlayImage;

	/**
	 * Field fSizeOfBaseImage. Size of the base image
	 */
	private final Point fSizeOfBaseImage;

	/**
	 * Field fLocation. Location of the overlay image, relative to the base image
	 */
	private final int fLocation;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for overlayImageIcon.
	 * 
	 * @param aBaseImage
	 *            Image
	 * @param aOverlayImage
	 *            Image
	 * @param aLocation
	 *            int
	 */
	public OverlayImageIcon(Image aBaseImage, Image aOverlayImage, int aLocation) {
		fBaseImage = aBaseImage;
		fOverlayImage = aOverlayImage;
		fSizeOfBaseImage = new Point(fBaseImage.getBounds().width, fBaseImage.getBounds().height);
		fLocation = aLocation;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method drawCompositeImage.
	 * 
	 * @param aWidth
	 *            int
	 * @param aHeight
	 *            int
	 */
	@Override
	protected void drawCompositeImage(int aWidth, int aHeight) {

		// To draw a composite image, the base image should be  
		// drawn first (first layer) and then the overlay image  
		// (second layer) 
		// Draw the base image using the base image's image data 
		drawImage(fBaseImage.getImageData(), 0, 0);

		// Overlaying the icon in the top left corner i.e. x and y  
		// coordinates are both zero 
		switch (fLocation) {
		// Draw on the top left corner
		case TOP_LEFT:
			drawImage(fOverlayImage.getImageData(), 0, 0);
			break;

		// Draw on top right corner  
		case TOP_RIGHT:
			drawImage(fOverlayImage.getImageData(), fSizeOfBaseImage.x - fOverlayImage.getImageData().width, 0);
			break;

		// Draw on bottom left  
		case BOTTOM_LEFT:
			drawImage(fOverlayImage.getImageData(), 0, fSizeOfBaseImage.y - fOverlayImage.getImageData().height);
			break;

		// Draw on bottom right corner  
		case BOTTOM_RIGHT:
		default:
			drawImage(fOverlayImage.getImageData(), fSizeOfBaseImage.x - fOverlayImage.getImageData().width,
					fSizeOfBaseImage.y - fOverlayImage.getImageData().height);
			break;

		}
	}

	/**
	 * Method getSize.
	 * 
	 * @return Point
	 */
	@Override
	protected Point getSize() {
		return fSizeOfBaseImage;
	}

	/**
	 * Get the image formed by overlaying different images on the base image
	 * 
	 * @return composite image
	 */
	public Image getImage() {
		return createImage();
	}
}
