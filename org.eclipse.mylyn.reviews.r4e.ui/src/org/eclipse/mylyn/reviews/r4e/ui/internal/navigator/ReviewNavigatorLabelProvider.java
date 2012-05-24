// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the label provider for the Review Navigator View
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorLabelProvider extends ColumnLabelProvider {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getToolTipText.
	 * 
	 * @param element
	 *            Object
	 * @return String
	 */
	@Override
	public String getToolTipText(Object element) {
		return ((IR4EUIModelElement) element).getToolTip();
	}

	/**
	 * Method getToolTipShift.
	 * 
	 * @param object
	 *            Object
	 * @return Point
	 */
	@Override
	public Point getToolTipShift(Object object) {
		return new Point(R4EUIConstants.TOOLTIP_DISPLAY_OFFSET_X, R4EUIConstants.TOOLTIP_DISPLAY_OFFSET_Y);
	}

	/**
	 * Method getToolTipDisplayDelayTime.
	 * 
	 * @param object
	 *            Object
	 * @return int
	 */
	@Override
	public int getToolTipDisplayDelayTime(Object object) {
		return R4EUIConstants.TOOLTIP_DISPLAY_DELAY;
	}

	/**
	 * Method getToolTipTimeDisplayed.
	 * 
	 * @param object
	 *            Object
	 * @return int
	 */
	@Override
	public int getToolTipTimeDisplayed(Object object) {
		return R4EUIConstants.TOOLTIP_DISPLAY_TIME;
	}

	/**
	 * Method update.
	 * 
	 * @param cell
	 *            ViewerCell
	 */
	@Override
	public void update(ViewerCell cell) {
		cell.setText(((IR4EUIModelElement) cell.getElement()).getName());
		cell.setImage(((IR4EUIModelElement) cell.getElement()).getImage());
	}

	/**
	 * Method addListener.
	 * 
	 * @param listener
	 *            ILabelProviderListener
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() { // $codepro.audit.disable emptyMethod
		//We might need to do something here to avoid leaking resources
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
	@Override
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
	@Override
	public void removeListener(ILabelProviderListener listener) { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method getText.
	 * 
	 * @param element
	 *            Object
	 * @return String
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(Object)
	 */
	@Override
	public String getText(Object element) {
		return ((IR4EUIModelElement) element).getName();
	}

	/**
	 * Method getImage.
	 * 
	 * @param element
	 *            Object
	 * @return Image
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(Object)
	 */
	@Override
	public Image getImage(Object element) {
		return ((IR4EUIModelElement) element).getImage();
	}
}
