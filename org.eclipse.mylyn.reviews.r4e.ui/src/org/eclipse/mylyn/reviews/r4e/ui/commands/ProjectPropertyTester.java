package org.eclipse.mylyn.reviews.r4e.ui.commands;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

public class ProjectPropertyTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		Object selectedElement = getSelection();
		if (selectedElement instanceof IProject) { 
			return true;
		} else if (selectedElement instanceof IJavaProject) {
			if (null != ((IJavaProject)selectedElement).getProject()) return true;
		} else if (selectedElement instanceof ICProject) {
			if (null != ((ICProject)selectedElement).getProject()) return true;
		} else if (selectedElement instanceof IPackageFragment || selectedElement instanceof IPackageFragmentRoot) {
			if (null != ((IJavaElement)selectedElement).getJavaProject().getProject()) return true;
		} else if (selectedElement instanceof IFolder) {
			if (null != ((IFolder)selectedElement).getProject()) return true;
		} else if (selectedElement instanceof IAdaptable) {
			if (null != ((IAdaptable)selectedElement).getAdapter(IProject.class)) return true;
		}
		return false;
	}

	private Object getSelection() {
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			return sel.getFirstElement();
		}
		return null;
	}
}
