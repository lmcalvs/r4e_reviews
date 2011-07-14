// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class extends the default tree viewer to be able to browse the items
 * using user-defined commands
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorTreeViewer extends TreeViewer {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewNavigatorTreeViewer.
	 * 
	 * @param parent
	 *            Composite
	 * @param style
	 *            int
	 */
	public ReviewNavigatorTreeViewer(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Method getPrevious.
	 * 
	 * @param aItem
	 *            TreeItem
	 * @return TreeItem
	 */
	public TreeItem getPrevious(TreeItem aItem) {
		final TreeItem newItem = (TreeItem) getPreviousItem(aItem);
		if (null == newItem) {
			return aItem;
		}
		return newItem;
	}

	/**
	 * Method getNext.
	 * 
	 * @param aItem
	 *            TreeItem
	 * @return TreeItem
	 */
	public TreeItem getNext(TreeItem aItem) {
		final TreeItem newItem = (TreeItem) getNextItem(aItem, true);
		if (null == newItem) {
			return aItem;
		}
		return newItem;
	}
}
