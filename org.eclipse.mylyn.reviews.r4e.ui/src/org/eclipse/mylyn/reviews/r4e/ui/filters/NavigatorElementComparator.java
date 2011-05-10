// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class extends the default viewer comparator to compare
 * two string and removing the first "> " sequence of charaters 
 * before the comparison
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.filters;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIComment;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class NavigatorElementComparator extends ViewerComparator {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method compare.
	 * 
	 * @param viewer
	 *            Viewer
	 * @param e1
	 *            Object
	 * @param e2
	 *            Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		final int cat1 = category(e1);
		final int cat2 = category(e2);
		if (cat1 != cat2)
			return cat1 - cat2;

		//If the compared objects are CommentElements, leave them alone
		if (e1 instanceof R4EUIComment)
			return 0;

		//Otherwise sort them alphabetically
		final ILabelProvider prov = (ILabelProvider) ((ContentViewer) viewer).getLabelProvider();
		String name1 = prov.getText(e1);
		String name2 = prov.getText(e2);

		if (null == name1 || null == name2)
			return 0; //Ignore invalid strings

		//Remove the decorator characters form the text label
		if (name1.startsWith("> "))
			name1 = name1.substring(2); // $codepro.audit.disable numericLiterals
		if (name2.startsWith("> "))
			name2 = name2.substring(2); // $codepro.audit.disable numericLiterals

		// use the comparator to compare the strings
		return getComparator().compare(name1, name2);
	}
}
