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
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.mylyn.reviews.r4e_gerrit.core.R4EGerritReviewSummary;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.R4EGerritUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 * 
 */
public class ReviewTableLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final String EMPTY_STRING = "";
	// Names of images used to represent review-checked
	public static final String CHECKED_IMAGE = "greenCheck";

	// Names of images used to represent review-not OK
	public static final String NOT_OK_IMAGE = "redNot";

	// Names of images used to represent STAR FILLED
	public static final String STAR_FILLED = "starFilled";

	// Names of images used to represent STAR OPEN
	public static final String STAR_OPEN = "starOpen";

	// Value stored to define the state of the review item.
	public static final int NOT_OK_IMAGE_STATE = -2;
	public static final int CHECKED_IMAGE_STATE = 2;

	// Constant for the column with colors: CR, IC and V
	private static Display fDisplay = Display.getCurrent();
	private static Color RED = fDisplay.getSystemColor(SWT.COLOR_RED);
	private static Color GREEN = fDisplay.getSystemColor(SWT.COLOR_DARK_GREEN);
	//Color used depending on the review state
	private static Color DEFAULT_COLOR = fDisplay.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
//	private static Color INCOMING_COLOR = fDisplay.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	private static Color INCOMING_COLOR = fDisplay.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
	private static Color CLOSED_COLOR = fDisplay.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);

	// For the images
	private static ImageRegistry fImageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it,
	 * and automatically disposes of them the SWT Display is disposed.
	 */
	static {
		String iconPath = "icons/view16/";
		// icon used for the column ID

		fImageRegistry.put(
				CHECKED_IMAGE,
				R4EGerritUi.getImageDescriptor(iconPath + CHECKED_IMAGE
						+ ".png"));
		fImageRegistry.put(NOT_OK_IMAGE, R4EGerritUi
				.getImageDescriptor(iconPath + NOT_OK_IMAGE + ".png"));

		// icon used for the column ID
		fImageRegistry
				.put(STAR_FILLED,
						R4EGerritUi.getImageDescriptor(iconPath + STAR_FILLED
								+ ".gif"));

		fImageRegistry.put(STAR_OPEN,
				R4EGerritUi.getImageDescriptor(iconPath + STAR_OPEN + ".gif"));
	}

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public ReviewTableLabelProvider() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Return an image representing the state of the object
	 * 
	 * @param int aState
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
	 * @param Boolean
	 *            aState
	 * @return Image
	 */
	private Image getReviewId(Boolean aState) {
		if (aState) {
			// True means the star is filled ??
			return fImageRegistry.get(STAR_FILLED);
		} else {
			//
			return fImageRegistry.get(STAR_OPEN);
		}
	}

	/**
	 * Return the text associated to the column
	 * 
	 * @param Object
	 *            structure of the table
	 * @param int column index
	 * 
	 * @return String text associated to the column
	 */
	public String getColumnText(Object aObj, int aIndex) {
		// R4EGerritPlugin.Ftracer.traceWarning("getColumnText object: " + aObj
		// + "\tcolumn: " + aIndex);
		if (aObj instanceof R4EGerritReviewSummary) {
			R4EGerritReviewSummary reviewSummary = (R4EGerritReviewSummary) aObj;
			String value = null;
			switch (aIndex) {
			case 0:
				return reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_STAR); // Needed for the sorter
			case 1:
				return reviewSummary.getAttribute(R4EGerritReviewSummary.SHORT_CHANGE_ID); 
			case 2:
				return reviewSummary.getAttribute(R4EGerritReviewSummary.SUBJECT); 
			case 3:
				return reviewSummary.getAttribute(R4EGerritReviewSummary.OWNER); 
			case 4:
				return reviewSummary.getAttribute(R4EGerritReviewSummary.PROJECT); 
			case 5:
				return reviewSummary.getAttribute(R4EGerritReviewSummary.BRANCH); 
			case 6:
				return reviewSummary.getAttributeAsDate(R4EGerritReviewSummary.DATE_MODIFICATION); 
			case 7:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CR);
				if (null != value && !value.equals(EMPTY_STRING)) {
					return formatValue (value);
				}
				break; 
			case 8:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CI);
				if (null != value && !value.equals(EMPTY_STRING)) {
					return formatValue (value);
				}
				break; 
			case 9:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_V);
				if (null != value && !value.equals(EMPTY_STRING)) {
					return formatValue (value);
				}
				break; 
			default:
				return EMPTY_STRING;
			}
		}
		return "";
	}

	
	/**
	 * Format the numbering value to display
	 * @param aSt
	 * @return String
	 */
	private String formatValue (String aSt) {
		int val = aSt.equals("")? 0 : Integer.parseInt(aSt, 10);
		if ( val > 0) {
			String st = "+" + aSt;
			return st;
		}
		return aSt; 

	}

	/**
	 * Return the image associated to the column
	 * 
	 * @param Object
	 *            structure of the table
	 * @param int column index
	 * 
	 * @return Image Image according to the selected column
	 */
	public Image getColumnImage(Object aObj, int aIndex) {
		// R4EGerritPlugin.Ftracer
		// .traceWarning("getColumnImage column: " + aIndex);
		Image image = null;
		String value = null;
		if (aObj instanceof R4EGerritReviewSummary) {
			R4EGerritReviewSummary reviewSummary = (R4EGerritReviewSummary) aObj;
			switch (aIndex) {
			case 0:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_STAR);
				if (null != value && !value.equals(EMPTY_STRING)) {
					return getReviewId(Boolean.valueOf(value.toLowerCase()));
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
				return image;
			case 7:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CR);

				if (null != value && !value.equals(EMPTY_STRING)) {
					int val = Integer.parseInt(value);
					return getReviewSate(val);
				}
				break;
			case 8:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CI);

				if (null != value && !value.equals(EMPTY_STRING)) {
					int val = Integer.parseInt(value);
					return getReviewSate(val);
				}
				break;
			case 9:
				value = reviewSummary.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_V);

				if (null != value && !value.equals(EMPTY_STRING)) {
					int val = Integer.parseInt(value);
					return getReviewSate(val);
				}
				break;
			default:
				return image;
			}
		}

		return image;
	}

	/**
	 * Adjust the column color
	 * 
	 * @param Object
	 *            ReviewTableListItem
	 * @param int columnIndex
	 */
	@Override
	public Color getForeground(Object aElement, int aColumnIndex) {
		if (aElement instanceof R4EGerritReviewSummary) {
			R4EGerritReviewSummary item = (R4EGerritReviewSummary) aElement;
			int value = 0;
			String st = null;
			// R4EGerritPlugin.Ftracer.traceWarning("getForeground() object CR : "
			// + item.getCr() + "\tcolumn : " + aColumnIndex );
			if (aColumnIndex == ReviewTableDefinition.CR.ordinal()
					|| aColumnIndex == ReviewTableDefinition.IC.ordinal()
					|| aColumnIndex == ReviewTableDefinition.VERIFY.ordinal()) {
				switch (aColumnIndex) {
				case 7: // ReviewTableDefinition.CR.ordinal():
					st = item.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CR);
					if (st != null) {
						value = st.equals(EMPTY_STRING) ? 0 : Integer
								.parseInt(st);						
					}
					break;
				case 8: // ReviewTableDefinition.IC.ordinal():
					st = item.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CI);
					if (st != null) {
						value = st.equals(EMPTY_STRING) ? 0 : Integer
								.parseInt(st);						
					}
					break;
				case 9: // ReviewTableDefinition.VERIFY.ordinal():
					st = item.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_V);
					if (st != null) {
						value = st.equals(EMPTY_STRING) ? 0 : Integer
								.parseInt(st);						
					}
					break;
				}
				if (value < 0) {
					return RED;
				} else if (value > 0) {
					return GREEN;
				}
			}
		}
		return null;
	}

	@Override
	public Color getBackground(Object aElement, int aColumnIndex) {
		// R4EGerritUi.Ftracer.traceInfo("getBackground column : " +
		// aColumnIndex +
		// " ]: "+ aElement );
		if (aElement instanceof R4EGerritReviewSummary) {
			R4EGerritReviewSummary item = (R4EGerritReviewSummary) aElement;
			//
			// To modify when we can verify the review state
			String state = item
					.getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_STAR);
			if (state != null) {
				if (state.equals("true")) {
					return INCOMING_COLOR;

				} else if (state.equals("false")) {
					return CLOSED_COLOR;
				}
			}
		}

		return DEFAULT_COLOR;
	}

}
