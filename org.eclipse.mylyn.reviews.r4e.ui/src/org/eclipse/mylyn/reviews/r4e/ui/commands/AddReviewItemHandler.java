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
 * This class implements the context-sensitive command to add a review item 
 * to a review
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelectionContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddReviewItemHandler extends AbstractHandler {
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) {

		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		//Act differently depending on the type of selection we get
		if (selection instanceof ITextSelection) {
			addReviewItemFromText((ITextSelection) selection);

		} else if (selection instanceof ITreeSelection) {

			//First remove any editor selection (if open) if we execute the command from the review navigator view
			final IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow(). // $codepro.audit.disable methodChainLength
			getActivePage().getActiveEditor();
			if (null != editorPart && editorPart instanceof ITextEditor) {
				((ITextEditor)editorPart).getSelectionProvider().setSelection(null);
			}

			//Then iterate through all selections
			for (final Iterator<?> iterator = ((ITreeSelection)selection).iterator(); iterator.hasNext();) {
				addReviewItemFromTree(iterator.next());
			}
		}

		return null;
	}

	
	/**
	 * Method addReviewItemFromText.
	 * @param aSelection ITextSelection
	 * @throws ReviewVersionsException 
	 */
	private void addReviewItemFromText(ITextSelection aSelection) {
		//This is a text selection in a text editor, we need to get the file path and
		//the position of the selection within the file
		try {
			final IR4EUIPosition position = CommandUtils.getPosition(aSelection);
			final IFile targetFile = CommandUtils.getTargetFile();
			final IFile baseFile = null;
			
			//Add selection to model
			addReviewItem(baseFile, targetFile, position);
			
		} catch (CoreException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
	}
	
	/**
	 * Method addReviewItemFromTree.
	 * @param aSelection ITreeSelection
	 * @throws ReviewVersionsException 
	 */
	private void addReviewItemFromTree(Object aSelection) {
		
		//This is a selection from the tree view (e.g. Review Navigator, Package Explorer etc...)
		//We will need to get the parent file path and the position of the element in a text editor
		//If the selection is on the File itself, then the selection will include all the lines
		//in the file.  Otherwise it will include all the lines corresponding to the currently 
		//selected element	
		try {
			
			IR4EUIPosition position = null;
			IFile targetFile = null;
			
			//Next find out what kind of selection we are dealing with
			if (aSelection instanceof IFile) {
				position = CommandUtils.getPosition((IFile)aSelection);
				targetFile = (IFile)aSelection;
			} else if (aSelection instanceof org.eclipse.jdt.core.ISourceReference) {
				//NOTE:  This is always true because all elements that implement ISourceReference
				//       also implement IJavaElement.  The resource is always an IFile
				targetFile = (IFile)((IJavaElement)aSelection).getResource();
				position = CommandUtils.getPosition((org.eclipse.jdt.core.ISourceReference)aSelection, targetFile);
			} else if (aSelection instanceof org.eclipse.cdt.core.model.ISourceReference) {
				//NOTE:  This is always true because all elements that implement ISourceReference
				//       also implement ICElement.  The resource is always an IFile
				targetFile = (IFile)((ICElement) aSelection).getParent().getResource();
				position = CommandUtils.getPosition((org.eclipse.cdt.core.model.ISourceReference)aSelection, targetFile);
			} else {
				//This should never happen
				Activator.Ftracer.traceWarning("Invalid selection " + aSelection.getClass().toString() + ".  Ignoring");
				return;
			}
			
			//Add selection to model
			addReviewItem(null, targetFile, position);
			
		} catch (JavaModelException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (CModelException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (CoreException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
	}
	
	/** // $codepro.audit.disable blockDepth
	 * Method AddReviewItem.
	 * 		Adds a review item to the model based on user input
	 * @param aUIPosition IR4EUIPosition
	 * @param aTargetFile IFile
	 * @param aBaseFile IFile
	 * @throws ReviewVersionsException 
	 */
	private void addReviewItem(IFile aBaseFile, IFile aTargetFile, IR4EUIPosition aUIPosition) {

		try {
			
			//Check if the file element and/or selection already exist
			//If file exists, add selection element to it
			//if selection element already exist, ignore command
			//for all other cases, create the parent elements as needed as well.	
			final List<R4EUIReviewItem> reviewItems = R4EUIModelController.getActiveReview().getReviewItems();

			boolean newSelection = true;
			for (R4EUIReviewItem reviewItem : reviewItems) {
				R4EUIFileContext[] files = (R4EUIFileContext[]) reviewItem.getChildren();
				for (R4EUIFileContext file : files) {
					if (aTargetFile.equals(file.getTargetFile()) && reviewItem.getType() == R4EUIConstants.REVIEW_ITEM_TYPE_RESOURCE) {
						if ((null == aBaseFile && null == file.getBaseFile()) || 
								(null != aBaseFile && null != file.getBaseFile() && aBaseFile.equals(file.getBaseFile()))) {
							
							//File already exists, check if selection also exists
							R4EUISelectionContainer selectionContainer = (R4EUISelectionContainer) file.getSelectionContainerElement();
							if (null != selectionContainer) {
								R4EUISelection[] selectionElements = (R4EUISelection[])selectionContainer.getChildren();
								for (R4EUISelection selectionElement : selectionElements) {
									if (selectionElement.getPosition().isSameAs(aUIPosition)) {
										newSelection = false;
									}
								}
							} else {
								selectionContainer = new R4EUISelectionContainer(file, R4EUIConstants.SELECTIONS_LABEL_NAME);
								file.addChildren(selectionContainer);
							}
							if (newSelection) {
								addReviewItemToExistingFileContext(selectionContainer, aUIPosition);
								Activator.Ftracer.traceInfo("Added review item: Target = " + aTargetFile.toString() + 
										((null != aBaseFile) ? "Base = " + aBaseFile.toString(): "") + " Position = " 
										+ aUIPosition.toString());
							} else {						
								//The selection already exists so ignore command
								Activator.Ftracer.traceWarning("Review Item already exists.  Ignoring");
								final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING, "Cannot add Review Item",
					    				new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, "Review Item already exists", null), IStatus.WARNING);
								dialog.open();
							}
							return;  //We found the file so we are done here	
						}
					}
				}
			}

			//This is a new file create it (and its parent reviewItem) and all its children
			addReviewItemToNewFileContext(aBaseFile, aTargetFile, aUIPosition);
			Activator.Ftracer.traceInfo("Added review item: Target = " + aTargetFile.toString() + 
					((null != aBaseFile) ? "Base = " + aBaseFile.getFullPath(): "") + " Position = " 
					+ aUIPosition.toString());
			
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);
			
		} catch (OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);

		}
	}
	

	/**
	 * Method addReviewItemToExistingFileContext.
	 * @param aContainer R4EUISelectionContainer
	 * @param aUIPosition IR4EUIPosition
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addReviewItemToExistingFileContext(R4EUISelectionContainer aContainer, IR4EUIPosition aUIPosition) 
		throws ResourceHandlingException, OutOfSyncException {
		
		final R4EUISelection uiSelection = aContainer.createSelection((R4EUITextPosition) aUIPosition);
		
		//Set focus to newly created anomaly comment
		R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiSelection, AbstractTreeViewer.ALL_LEVELS);
		R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiSelection), true);
	}
	
	/**
	 * Method addReviewItemToNewFileContext.
	 * @param aBaseFile IFile
	 * @param aTargetFile IFile
	 * @param aUIPosition IR4EUIPosition
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addReviewItemToNewFileContext(IFile aBaseFile, IFile aTargetFile, IR4EUIPosition aUIPosition) 
		throws ResourceHandlingException, OutOfSyncException {
		
		final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
		final R4EUIReviewItem uiReviewItem = uiReview.createReviewItem(aTargetFile);
		if (null == uiReviewItem) return;
		
		final R4EUIFileContext uiFileContext = uiReviewItem.createFileContext(aBaseFile, aTargetFile);
		if (null == uiFileContext) {
			uiReview.removeChildren(uiReviewItem, false);
			return;
		}
		
		final R4EUISelectionContainer uiSelectionContainer = new R4EUISelectionContainer(
				uiFileContext, R4EUIConstants.ANOMALIES_LABEL_NAME);
		uiFileContext.addChildren(uiSelectionContainer);
		
		final R4EUISelection uiSelection = uiSelectionContainer.createSelection((R4EUITextPosition) aUIPosition);
			//Set focus to newly created anomaly comment
			R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiSelection, AbstractTreeViewer.ALL_LEVELS);
			R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiSelection), true);
	}
}
