/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Proxy class used to interact with R4E Elements programmatically
 * for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.CompareContentViewerSwitchingPane;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.EditorProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

@SuppressWarnings("restriction")
public class R4EUITestElement {

	/**
	 * Field COMPARE_EDITOR_TEXT_CLASS_NAME. (value is ""org.eclipse.compare.contentmergeviewer.TextMergeViewer"")
	 */
	private static final String COMPARE_EDITOR_TEXT_CLASS_NAME = "org.eclipse.compare.contentmergeviewer.TextMergeViewer";

	/**
	 * Field COMPARE_EDITOR_TEXT_FIELD_LEFT. (value is ""fLeft"")
	 */
	private static final String COMPARE_EDITOR_TEXT_FIELD_LEFT = "fLeft";

	/**
	 * Field DEFAULT_OBJECT_CLASS_NAME. (value is ""Object"")
	 */
	private static final String DEFAULT_OBJECT_CLASS_NAME = "Object";

	protected R4EUITestMain fParentProxy = null;

	public R4EUITestElement(R4EUITestMain aR4EUITestProxy) {
		fParentProxy = aR4EUITestProxy;
	}

	/**
	 * Method setFocusOnProjectExplorer
	 * 
	 * @param aProject
	 */
	protected void setFocusOnProjectExplorer(IResource aResource) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			ProjectExplorer projectExplorer = (ProjectExplorer) page.showView("org.eclipse.ui.navigator.ProjectExplorer");
			ISelection selection = new StructuredSelection(aResource);
			projectExplorer.selectReveal(selection);
		} catch (PartInitException e) {
			// Do nothing, test will fail
		}
	}

	/**
	 * Method setFocusOnNavigatorElement
	 * 
	 * @param aElement
	 */
	protected void setFocusOnNavigatorElement(IR4EUIModelElement aElement) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		page.activate(R4EUIModelController.getNavigatorView());
		ISelection selection = new StructuredSelection(aElement);
		R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(selection, true);
	}

	/**
	 * Method setFocusOnNavigatorElements
	 * 
	 * @param aElement
	 */
	protected void setFocusOnNavigatorElements(List<IR4EUIModelElement> aElements) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		page.activate(R4EUIModelController.getNavigatorView());
		ISelection selection = new StructuredSelection(aElements);
		R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(selection);
	}

	/**
	 * Method setFocusOnPropertiesView
	 * 
	 * @return IViewPart
	 */
	protected IViewPart setFocusOnPropertiesView() {
		return R4EUIModelController.getNavigatorView().showProperties();
	}

	/**
	 * Method getNavigatorSelectedElement
	 * 
	 * @return IR4EUIModelElement
	 */
	protected IR4EUIModelElement getNavigatorSelectedElement() {
		ITreeSelection newSelection = (ITreeSelection) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getSelection();
		return (IR4EUIModelElement) newSelection.getFirstElement();
	}

	/**
	 * Method getNavigatorSelectedElements
	 * 
	 * @return List<IR4EUIModelElement>
	 */
	protected List<IR4EUIModelElement> getNavigatorSelectedElements() {
		ITreeSelection newSelection = (ITreeSelection) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getSelection();
		List<IR4EUIModelElement> selectedElements = new ArrayList<IR4EUIModelElement>();
		for (final Iterator<?> iterator = ((IStructuredSelection) newSelection).iterator(); iterator.hasNext();) {
			selectedElements.add((IR4EUIModelElement) iterator.next());
		}
		return selectedElements;
	}

	/**
	 * Method openEditor
	 * 
	 * @param aResource
	 * @return IEditorPart
	 * @throws PartInitException
	 */
	protected IEditorPart openEditor(IResource aResource) throws PartInitException {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(aResource.getName());
		return page.openEditor(new FileEditorInput((IFile) aResource), desc.getId());
	}

	/**
	 * Method openEditorOnCurrentElement
	 */
	protected void openEditorOnCurrentElement() {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		EditorProxy.openEditor(page, R4EUIModelController.getNavigatorView().getTreeViewer().getSelection(), false);
	}

	/**
	 * Method closeEditor
	 * 
	 * @param aEditor
	 */
	protected void closeEditor(IEditorPart aEditor) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		page.closeEditor(aEditor, false);
	}

	/**
	 * Method getCurrentEditor
	 * 
	 * @return IEditorPart
	 */
	protected IEditorPart getCurrentEditor() {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		return (page.getEditorReferences())[0].getEditor(false); //only 1 editor open on this page
	}

	/**
	 * Method setCompareEditorSelection
	 * 
	 * @param IEditorPart
	 * @param aStartPosition
	 * @param aLength
	 */
	protected void setCompareEditorSelection(IEditorPart aEditor, int aStartPosition, int aLength) {
		//Use free form to select position in file
		//NOTE:  This is a dirty hack that involves accessing class and field we shouldn't, but that's
		//       the only way to select the current position in the compare editor.  Hopefully this code can
		//		 be removed later when the Eclipse compare editor allows this.
		final ICompareNavigator navigator = ((R4ECompareEditorInput) aEditor.getEditorInput()).getNavigator();
		if (navigator instanceof CompareEditorInputNavigator) {
			final Object[] panes = ((CompareEditorInputNavigator) navigator).getPanes();
			for (Object pane : panes) {
				if (pane instanceof CompareContentViewerSwitchingPane) {
					Viewer viewer = ((CompareContentViewerSwitchingPane) pane).getViewer();
					if (viewer instanceof TextMergeViewer) {
						TextMergeViewer textViewer = (TextMergeViewer) viewer;
						Class textViewerClass = textViewer.getClass();
						if (!textViewerClass.getName().equals(COMPARE_EDITOR_TEXT_CLASS_NAME)) {
							do {
								textViewerClass = textViewerClass.getSuperclass();
								if (textViewerClass.getName().equals(DEFAULT_OBJECT_CLASS_NAME)) {
									break;
								}
							} while (!textViewerClass.getName().equals(COMPARE_EDITOR_TEXT_CLASS_NAME));
						}
						try {
							Field field = textViewerClass.getDeclaredField(COMPARE_EDITOR_TEXT_FIELD_LEFT);
							field.setAccessible(true);
							MergeSourceViewer sourceViewer = (MergeSourceViewer) field.get(textViewer);
							ITextEditor adapter = (ITextEditor) sourceViewer.getAdapter(ITextEditor.class);
							adapter.selectAndReveal(aStartPosition, aLength);
						} catch (SecurityException e) {
							//just continue
						} catch (NoSuchFieldException e) {
							//just continue
						} catch (IllegalArgumentException e) {
							//just continue
						} catch (IllegalAccessException e) {
							//just continue
						}
					}
				}
			}
		}
	}
}
