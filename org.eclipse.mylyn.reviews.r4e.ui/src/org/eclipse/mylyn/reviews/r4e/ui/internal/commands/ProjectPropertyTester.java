/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a property tester that is used to see if a selected
 * ellement is contained in a workspace project
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ProjectPropertyTester extends PropertyTester {

	/**
	 * Method test.
	 * 
	 * @param receiver
	 *            Object
	 * @param property
	 *            String
	 * @param args
	 *            Object[]
	 * @param expectedValue
	 *            Object
	 * @return boolean
	 * @see org.eclipse.core.expressions.IPropertyTester#test(Object, String, Object[], Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final Object selectedElement = getSelection();
		if (selectedElement instanceof IProject) {
			return true;
		} else if (R4EUIPlugin.isJDTAvailable() && selectedElement instanceof org.eclipse.jdt.core.IJavaProject) {
			if (null != ((org.eclipse.jdt.core.IJavaProject) selectedElement).getProject()) {
				return true;
			}
		} else if (R4EUIPlugin.isCDTAvailable() && selectedElement instanceof org.eclipse.cdt.core.model.ICProject) {
			if (null != ((org.eclipse.cdt.core.model.ICProject) selectedElement).getProject()) {
				return true;
			}
		} else if (R4EUIPlugin.isJDTAvailable()
				&& (selectedElement instanceof org.eclipse.jdt.core.IPackageFragment || selectedElement instanceof org.eclipse.jdt.core.IPackageFragmentRoot)) {
			if (null != ((org.eclipse.jdt.core.IJavaElement) selectedElement).getJavaProject().getProject()) {
				return true;
			}
		} else if (selectedElement instanceof IFolder) {
			if (null != ((IFolder) selectedElement).getProject()) {
				return true;
			}
		} else if (selectedElement instanceof IAdaptable) {
			if (null != ((IAdaptable) selectedElement).getAdapter(IProject.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method getSelection.
	 * 
	 * @return Object
	 */
	private Object getSelection() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (null != workbench) {
			final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			if (null != window) {
				final IWorkbenchPage page = window.getActivePage();
				if (null != page) {
					final ISelection selection = page.getSelection();
					if (null != selection && selection instanceof IStructuredSelection && !selection.isEmpty()) {
						final IStructuredSelection sel = (IStructuredSelection) selection;
						return sel.getFirstElement();
					}
				}
			}
		}
		return null;
	}
}
