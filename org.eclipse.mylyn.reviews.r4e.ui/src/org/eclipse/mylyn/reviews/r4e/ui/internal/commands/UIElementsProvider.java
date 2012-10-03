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
 * This class implements a command variable used to pass in R4E UI Elements.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.ui.AbstractSourceProvider;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class UIElementsProvider extends AbstractSourceProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_CURRENT. (value is ""org.eclipse.mylyn.reviews.r4e.ui.commands.selectedElements"")
	 */
	public static final String SELECTED_ELEMENTS = "org.eclipse.mylyn.reviews.r4e.ui.commands.selectedElements";

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	private List<IR4EUIModelElement> fSelectedElements = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.ui.ISourceProvider#dispose()
	 */
	public void dispose() { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method getCurrentState.
	 * 
	 * @return Map<String,ReviewElement>
	 * @see org.eclipse.ui.ISourceProvider#getCurrentState()
	 */
	public Map<String, List<IR4EUIModelElement>> getCurrentState() {
		final Map<String, List<IR4EUIModelElement>> map = new HashMap<String, List<IR4EUIModelElement>>(1, 1);
		map.put(SELECTED_ELEMENTS, fSelectedElements);
		return map;
	}

	/**
	 * Method getProvidedSourceNames.
	 * 
	 * @return String[]
	 * @see org.eclipse.ui.ISourceProvider#getProvidedSourceNames()
	 */
	public String[] getProvidedSourceNames() {
		return new String[] { SELECTED_ELEMENTS };
	}

	/**
	 * Method setSelectedElements.
	 * 
	 * @param aElements
	 *            List<IR4EUIModelElement>
	 */
	public void setSelectedElements(final List<IR4EUIModelElement> aElements) {
		fSelectedElements = aElements;
	}

	/**
	 * Method getSelectedElements.
	 * 
	 * @return List<IR4EUIModelElement>
	 */
	public List<IR4EUIModelElement> getSelectedElements() {
		return fSelectedElements;
	}
}
