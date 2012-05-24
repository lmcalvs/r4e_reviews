// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc, explicitThisUsage
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
 * This class implements the content provider for the Review Navigator View
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import java.util.Arrays;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorContentProvider implements ITreeContentProvider {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method getChildren.
	 * 
	 * @param aParentElement
	 *            Object
	 * @return Object[]
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object aParentElement) {
		final IR4EUIModelElement element = (IR4EUIModelElement) aParentElement;
		final IR4EUIModelElement[] elements = element.getChildren();
		return Arrays.asList(elements).toArray();
	}

	/**
	 * Method getParent.
	 * 
	 * @param aElement
	 *            Object
	 * @return Object
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object aElement) {
		return ((IR4EUIModelElement) aElement).getParent();
	}

	/**
	 * Method hasChildren.
	 * 
	 * @param aElement
	 *            Object
	 * @return boolean
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object aElement) {
		if (null != aElement) {
			return ((IR4EUIModelElement) aElement).hasChildren();
		}
		return false;
	}

	/**
	 * Method getElements.
	 * 
	 * @param aInputElement
	 *            Object
	 * @return Object[]
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object aInputElement) {
		return getChildren(aInputElement);
	}

	/**
	 * Method inputChanged.
	 * 
	 * @param viewer
	 *            Viewer
	 * @param oldInput
	 *            Object
	 * @param newInput
	 *            Object
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// ignore
	}
}
