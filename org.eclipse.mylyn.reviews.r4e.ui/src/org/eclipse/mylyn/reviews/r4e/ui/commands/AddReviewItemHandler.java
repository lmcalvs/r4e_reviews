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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.FileVersionInfo;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIFFactory;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition;

import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelectionContainer;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddReviewItemHandler extends AbstractHandler {
	
	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	@Override
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
			addReviewItem(targetFile, baseFile, position);
			
		} catch (CoreException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
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
				Activator.Tracer.traceWarning("Invalid selection " + aSelection.getClass().toString() + ".  Ignoring");
				return;
			}
			
			//Add selection to model
			addReviewItem(targetFile, null, position);
			
		} catch (JavaModelException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (CModelException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (CoreException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
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
	private void addReviewItem(IFile aTargetFile, IFile aBaseFile, IR4EUIPosition aUIPosition) {

		try {

			//Get core version interface
			final IProject project = aTargetFile.getProject();
			ReviewsVersionsIF versionsIf = null;
			try {
				versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(project);
			} catch (ReviewVersionsException e) {
				Activator.Tracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logInfo("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Info", 
						"Take note that the review item you are trying to add is not in source control.",
	    				new Status(IStatus.INFO, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.INFO);
				dialog.open();
			}
			
			//Check if the file element and/or selection already exist
			//If file exists, add selection element to it
			//if selection element already exist, ignore command
			//for all other cases, create the parent elements as needed as well.
			final R4EUIReview review = R4EUIModelController.getActiveReview();

			//Get the reviewer (i.e. ourselves :-) or create it if it does not exist
			final String user = R4EUIModelController.getReviewer();
			final R4EParticipant participant = review.getParticipant(user, true);
			
			//Get review items only
			final IR4EUIModelElement[] reviewChildren = review.getChildren();
			final List<R4EUIReviewItem> reviewItems = new ArrayList<R4EUIReviewItem>();
			for (IR4EUIModelElement child : reviewChildren) {
				if (child instanceof R4EUIReviewItem) {
					reviewItems.add((R4EUIReviewItem)child);
				}
			}

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
								addReviewItemToExistingFileContext(selectionContainer, file, aUIPosition);
								Activator.Tracer.traceInfo("Added review item: Target = " + aTargetFile.toString() + 
										((null != aBaseFile) ? "Base = " + aBaseFile.toString(): "") + " Position = " 
										+ aUIPosition.toString());
							} else {						
								//The selection already exists so ignore command
								Activator.Tracer.traceWarning("Review Item already exists.  Ignoring");
								final ErrorDialog dialog = new ErrorDialog(null, "Warning", "Cannot add Review Item",
					    				new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, "Review Item already exists", null), IStatus.WARNING);
								dialog.open();
							}
							return;  //We found the file so we are done here	
						}
					}
				}
			}

			//This is a new file create it (and its parent reviewItem) and all its children
			addReviewItemToNewFileContext(review, aTargetFile, aBaseFile, aUIPosition, participant, versionsIf);
			Activator.Tracer.traceInfo("Added review item: Target = " + aTargetFile.toString() + 
					((null != aBaseFile) ? "Base = " + aBaseFile.getFullPath(): "") + " Position = " 
					+ aUIPosition.toString());
			
		} catch (ResourceHandlingException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Resource error detected while adding review item ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			
		} catch (OutOfSyncException e) {
			Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while adding review item.  " +
					"Please refresh the review navigator view and try the command again",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			// TODO later we will want to do this automatically
		}
	}
	

	/**
	 * Method addReviewItemToExistingFileContext.
	 * @param aContainer R4EUISelectionContainer
	 * @param aFile R4EUIFileContext
	 * @param aUIPosition IR4EUIPosition
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addReviewItemToExistingFileContext(R4EUISelectionContainer aContainer, R4EUIFileContext aFile, 
			IR4EUIPosition aUIPosition) throws ResourceHandlingException, OutOfSyncException {
		
		//Add selectionElement to model in this container
		final R4EDelta selection = R4EUIModelController.FModelExt.createR4EDelta(aFile.getFileContext());
		final R4ETextPosition position = R4EUIModelController.FModelExt.createR4ETextPosition(
				R4EUIModelController.FModelExt.createR4ETargetTextContent(selection));
		aUIPosition.setPositionInModel(position);
		final R4EUISelection uiSelection = new R4EUISelection(aContainer, selection, aUIPosition);
		aContainer.addChildren(uiSelection);

		//Set focus on the new element in the Review Navigator
		R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiSelection, AbstractTreeViewer.ALL_LEVELS);
		R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiSelection), true);

	}
	
	/**
	 * Method addReviewItemToNewFileContext.
	 * @param aReview R4EUIReview
	 * @param aUIPosition IR4EUIPosition
	 * @param aParticipant R4EParticipant
	 * @param aTargetFile IFile
	 * @param aBaseFile IFile
	 * @param aVersionsIf ReviewsVersionsIF
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws ReviewVersionsException 
	 */
	private void addReviewItemToNewFileContext(R4EUIReview aReview, IFile aTargetFile, IFile aBaseFile, 
			IR4EUIPosition aUIPosition, R4EParticipant aParticipant, ReviewsVersionsIF aVersionsIf) 
	throws ResourceHandlingException, OutOfSyncException {
		
		final R4EItem reviewItem = R4EUIModelController.FModelExt.createR4EItem(aParticipant);
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(reviewItem, R4EUIModelController.getReviewer());
		reviewItem.getProjectURIs().add(ResourceUtils.toPlatformURIStr(aTargetFile.getProject()));
		reviewItem.setDescription("");
		reviewItem.setRepositoryRef(aTargetFile.getFullPath().toOSString());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		final R4EFileContext fileContext = R4EUIModelController.FModelExt.createR4EFileContext(reviewItem);
		if (null != aBaseFile) {
			//  TODO: for now comparisons using the compare editor from the UI are not supported.  The compare input comes
			// from the eGIT code in the R4E core plugin
			final R4EFileVersion baseVersion = R4EUIModelController.FModelExt.createR4EBaseFileVersion(fileContext);
			bookNum = R4EUIModelController.FResourceUpdater.checkOut(baseVersion, R4EUIModelController.getReviewer());
			try {
				final FileVersionInfo baseVersionInfo = aVersionsIf.getFileVersionInfo(aBaseFile);
				baseVersion.setName(baseVersionInfo.getName());
				baseVersion.setRepositoryPath(baseVersionInfo.getRepositoryPath());
				baseVersion.setVersionID(baseVersionInfo.getId());
			} catch (ReviewVersionsException e) {
				Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logWarning("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while adding anomaly. " +
						" Assuming no base version is present.",
	    				new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.WARNING);
				dialog.open();
			} finally {
				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
			}
		}
		final R4EFileVersion targetVersion = R4EUIModelController.FModelExt.createR4ETargetFileVersion(fileContext);
		bookNum = R4EUIModelController.FResourceUpdater.checkOut(targetVersion, R4EUIModelController.getReviewer());
		targetVersion.setResource(aTargetFile);
		targetVersion.setPlatformURI(ResourceUtils.toPlatformURI(aTargetFile).toString());
		
		if (null != aVersionsIf) {
			//File is in a Git repository
			try {
				final FileVersionInfo targetVersionInfo = aVersionsIf.getFileVersionInfo(aTargetFile);
				targetVersion.setName(targetVersionInfo.getName());
				targetVersion.setRepositoryPath(targetVersionInfo.getRepositoryPath());
				targetVersion.setVersionID(targetVersionInfo.getId());
			} catch (ReviewVersionsException e) {
				Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logWarning("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while adding anomaly. " +
						" Assuming no version control is present",
	    				new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.WARNING);
				dialog.open();
				
				//File is not version-controlled
				targetVersion.setName(aTargetFile.getName());
				targetVersion.setRepositoryPath(aTargetFile.getFullPath().toOSString());
				targetVersion.setVersionID(R4EUIConstants.FILE_NOT_IN_VERSION_CONTROL_MSG);
			}
		} else {
			//File is not version-controlled
			targetVersion.setName(aTargetFile.getName());
			targetVersion.setRepositoryPath(aTargetFile.getFullPath().toOSString());
			targetVersion.setVersionID(R4EUIConstants.FILE_NOT_IN_VERSION_CONTROL_MSG);
		}
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		final R4EUIReviewItem uiReviewItem = new R4EUIReviewItem(aReview, reviewItem, 
				R4EUIConstants.REVIEW_ITEM_TYPE_RESOURCE, reviewItem);
		final R4EUIFileContext uiFile = new R4EUIFileContext(uiReviewItem, fileContext);
		
		final R4EUISelectionContainer uiSelectionContainer = new R4EUISelectionContainer(uiFile, R4EUIConstants.SELECTIONS_LABEL_NAME);
		
		final R4EDelta selection = R4EUIModelController.FModelExt.createR4EDelta(fileContext);
		final R4ETextPosition position = R4EUIModelController.FModelExt.createR4ETextPosition(
				R4EUIModelController.FModelExt.createR4ETargetTextContent(selection));
		aUIPosition.setPositionInModel(position);
		final R4EUISelection uiSelection = new R4EUISelection(uiSelectionContainer, selection, aUIPosition);
		
		uiSelectionContainer.addChildren(uiSelection);
		uiFile.addChildren(uiSelectionContainer);
		uiReviewItem.addChildren(uiFile);
		aReview.addChildren(uiReviewItem);

		//Set focus on the new element in the Review Navigator
		R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiSelection, AbstractTreeViewer.ALL_LEVELS);
		R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiSelection), true);
	}
}
