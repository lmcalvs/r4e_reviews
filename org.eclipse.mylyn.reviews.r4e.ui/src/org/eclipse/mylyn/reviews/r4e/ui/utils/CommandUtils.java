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
 * This class implements various utility methods used in the context-
 * sensitive commands
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4ECompareItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class CommandUtils {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getTargetFileURI.
	 * @return URI 
	 * @throws URISyntaxException 
	 * @throws CoreException
	 */
	public static IFile getTargetFile() throws CoreException {
		final IEditorInput input = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput(); // $codepro.audit.disable methodChainLength
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		} else if (input instanceof R4ECompareEditorInput) {
			final ITypedElement targetElement = ((R4ECompareEditorInput)input).getLeftElement();
			if (targetElement instanceof ResourceNode) {
				return (IFile) ((ResourceNode)targetElement).getResource();
			//TODO: for now comparisons using the compare editor from the UI are not supported.  The compare input comes
			//from the eGIT code in the R4E core plugin
			} else if (targetElement instanceof R4ECompareItem) {
				//TODO:  For now we pick the first IFile with the specified URI.  Is this okay?
				final URI uri = ((R4ECompareItem)targetElement).getURI();
				return ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri)[0];
			} 
		//TODO: For now we let egit do the comparison.  We need to be able to get the target file from the egit compare
		//		input, thus this dirty hack is necessary.  This will be changed later when we will manage our own compare input 
		/*
		} else if (input instanceof GitCompareFileRevisionEditorInput) {
			IFile file = null;
			try {
				final Class<?> proxyClass = Class.forName("org.eclipse.egit.ui.internal.EgitProxy");
				final Method method = proxyClass.getMethod("getCompareLeftFile", GitCompareFileRevisionEditorInput.class);
				file = (IFile) method.invoke(CommandUtils.class, (GitCompareFileRevisionEditorInput)input); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.preferInterfacesToReflection
			} catch (ClassNotFoundException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			} catch (SecurityException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			} catch (NoSuchMethodException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			} catch (IllegalArgumentException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			} catch (InvocationTargetException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			} catch (IllegalAccessException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			}
			if (null != file) return file;
			*/
		}
		//Should never happen
		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, 
				"Invalid input " + input.getClass().toString() , null));
	}
	
	/**
	 * Method getBaseFileURI.
	 * @return URI 
	 * @throws URISyntaxException
	 * @throws CoreException
	 */
	public static IFile getBaseFile() throws CoreException {
		final IEditorInput input = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput(); // $codepro.audit.disable methodChainLength
		if (input instanceof IFileEditorInput) {
			return null;   //no base file included
		} else if (input instanceof R4ECompareEditorInput) {
			final ITypedElement baseElement = ((R4ECompareEditorInput)input).getRightElement();
			if (baseElement instanceof ResourceNode) {
				return (IFile) ((ResourceNode)baseElement).getResource();
				//TODO: for now comparisons using the compare editor from the UI are not supported.  The compare input comes
				//from the eGIT code in the R4E core plugin
			} else if (baseElement instanceof R4ECompareItem) {
				//TODO:  For now we pick the first IFile with the specified URI.  Is this okay?
				final URI uri = ((R4ECompareItem)baseElement).getURI();
				return ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri)[0];
			}
		}
		//Should never happen
		throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, 
				"Invalid input " + input.getClass().toString() , null));
	}
	
	/**
	 * Method getPosition.
	 * @param aSelection ITextSelection
	 * @return TextPosition */
	public static R4EUITextPosition getPosition(ITextSelection aSelection) {
		return new R4EUITextPosition(aSelection.getOffset(), (aSelection).getLength(), 
				(aSelection).getStartLine(), (aSelection).getEndLine());
	}
	

	/**
	 * Method getPosition.
	 * 		Get position for generic workspace files
	 * @param aSelectedElement IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(IFile aSelectedElement) throws CoreException { // $codepro.audit.disable overloadedMethods
		final R4EUITextPosition position = new R4EUITextPosition(R4EUIConstants.NO_OFFSET, R4EUIConstants.INVALID_VALUE, aSelectedElement);
		position.setName(aSelectedElement.getName());
		return position;
	}
	
	/**
	 * Method getPosition.
	 * 		Get position for workspace java source files
	 * @param aSelectedElement org.eclipse.jdt.core.ISourceReference
	 * @param aFile IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(org.eclipse.jdt.core.ISourceReference aSelectedElement, IFile aFile) 
		throws CoreException {
		final R4EUITextPosition position = new R4EUITextPosition(aSelectedElement.getSourceRange().getOffset(), 
				aSelectedElement.getSourceRange().getLength(), aFile);
		position.setName(((IJavaElement)aSelectedElement).getElementName());
		return position;	
	}

	/**
	 * Method getPosition.
	 * 		Get position for workspace C and C++ source files
	 * @param aSelectedElement org.eclipse.cdt.core.model.ISourceReference
	 * @param aFile IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(org.eclipse.cdt.core.model.ISourceReference aSelectedElement, IFile aFile)  // $codepro.audit.disable overloadedMethods
		throws CoreException {
		final R4EUITextPosition position = new R4EUITextPosition(aSelectedElement.getSourceRange().getStartPos(), 
				aSelectedElement.getSourceRange().getLength(), aFile);
		position.setStartLine(aSelectedElement.getSourceRange().getStartLine());
		position.setEndLine(aSelectedElement.getSourceRange().getEndLine());
		position.setName(((ICElement)aSelectedElement).getElementName());
		return position;
	}
}
