/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the R4E-Gerrit UI view label provider.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the label provider
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.R4EGerritUi;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model.ReviewTableData.ReviewTableListItem;
import org.eclipse.swt.graphics.Image;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class ReviewTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final String EMPTY_STRING = "";
	// Names of images used to represent review-checked
	public static final String CHECKED_IMAGE = "R4E_checked";

	// Names of images used to represent review-not OK
	public static final String NOT_OK_IMAGE = "R4E_not_ok";

	// Names of images used to represent STAR FILLED
	public static final String  STAR_FILLED= "starFilled";

	// Names of images used to represent STAR OPEN
	public static final String STAR_OPEN = "starOpen";


	// Value stored to define the state of the review item.
	public static final int NOT_OK_IMAGE_STATE = 1;
	public static final int CHECKED_IMAGE_STATE = 2;

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	// For the images
	private static ImageRegistry fImageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it,
	 * and automatically disposes of them the SWT Display is disposed.
	 */
	static {
		String iconPath = "icons/view16/";
		//icon used for the column ID
		fImageRegistry.put(
				CHECKED_IMAGE,
				R4EGerritUi.getImageDescriptor(iconPath + CHECKED_IMAGE
						+ ".gif"));
		fImageRegistry.put(NOT_OK_IMAGE, R4EGerritUi
				.getImageDescriptor(iconPath + NOT_OK_IMAGE + ".gif"));

		//icon used for the column ID
		fImageRegistry.put(STAR_FILLED, R4EGerritUi
				.getImageDescriptor(iconPath + STAR_FILLED + ".gif"));
		
		fImageRegistry.put(STAR_OPEN, R4EGerritUi
				.getImageDescriptor(iconPath + STAR_OPEN + ".gif"));
	}

	/**
	 * Return the text associated to the column
	 * 
	 * @param Object structure of the table
	 * @param int column index
	 * 
	 * @return String text associated to the column
	 */
	public String getColumnText(Object aObj, int aIndex) {
		R4EGerritPlugin.Ftracer.traceWarning("getColumnText object: " + aObj
				+ "\tcolumn: " + aIndex);
		if (aObj instanceof ReviewTableListItem) {
			ReviewTableListItem rtli = (ReviewTableListItem) aObj;
			switch (aIndex) {
			// case 0: return rtli.getId();
			case 1:
				return rtli.getSubject();
			case 2:
				return rtli.getOwner();
			case 3:
				return rtli.getProject();
			case 4:
				return rtli.getBranch();
			case 5:
				return rtli.getUpdated();
				// case 6: return rtli.getCr();
				// case 7: return rtli.getIc();
				// case 8: return rtli.getVerify();
			default:
				return EMPTY_STRING;
			}
		}

		return "";
	}

	/**
	 * Return the image associated to the column
	 * 
	 * @param Object structure of the table
	 * @param int column index
	 * 
	 * @return Image Image according to the selected column
	 */
	public Image getColumnImage(Object aObj, int aIndex) {
		R4EGerritPlugin.Ftracer
				.traceWarning("getColumnImage column: " + aIndex);
		Image image = null;
		if (aObj instanceof ReviewTableListItem) {
			ReviewTableListItem rtli = (ReviewTableListItem) aObj;
			switch (aIndex) {
			case 0:
				if (null != rtli.getId() && !rtli.getId().equals(EMPTY_STRING)) {
					R4EGerritPlugin.Ftracer
							.traceWarning("getColumnImage column: " + aIndex
									+ "\tId: " + rtli.getId());
					return getReviewId(Boolean.valueOf(rtli.getId().toLowerCase()));
				}
				break;
			case 1:
				return image;
			case 2:
				return image;
			case 3:
				return image;
			case 4:
				return image;
			case 5:
				return image;
			case 6:
				if (null != rtli.getCr() && !rtli.getCr().equals(EMPTY_STRING)) {
					return getReviewSate(Integer.parseInt(rtli.getCr()));
				}
				break;
			case 7:
				if (null != rtli.getIc() && !rtli.getIc().equals(EMPTY_STRING)) {
					return getReviewSate(Integer.parseInt(rtli.getIc()));
				}
				break;
			case 8:
				if (null != rtli.getVerify()
						&& !rtli.getVerify().equals(EMPTY_STRING)) {
					return getReviewSate(Integer.parseInt(rtli.getVerify()));
				}
				break;
			default:
				return image;
			}
		}

		return image;
	}

	/**
	 * Return an image representing the state of the object
	 * 
	 * @param aState
	 * @return Image
	 */
	private Image getReviewSate(int aState) {
		if (aState == NOT_OK_IMAGE_STATE) {
			return fImageRegistry.get(NOT_OK_IMAGE);
		} else if (aState == CHECKED_IMAGE_STATE) {
			return fImageRegistry.get(CHECKED_IMAGE);
		}
		return null;
	}

	/**
	 * Return an image representing the state of the ID object
	 * 
	 * @param Boolean aState
	 * @return Image
	 */
	private Image getReviewId(Boolean aState) {
		if (aState) {
			//True means the star is filled ??
			return fImageRegistry.get(STAR_FILLED);
		} else  {
			//
			return fImageRegistry.get(STAR_OPEN);
		}
	}


}
