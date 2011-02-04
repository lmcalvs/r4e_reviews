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
 * This class implements the context-sensitive command to add an anomaly on 
 * a review item
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
import org.eclipse.cdt.core.model.ISourceReference;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
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
public class AddAnomalyHandler extends AbstractHandler {
	
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
			addAnomalyFromText((ITextSelection)selection);
			
		} else if (selection instanceof ITreeSelection) {
			
			//First remove any editor selection (if open) if we execute the command from the review navigator view
			final IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor(); // $codepro.audit.disable methodChainLength
			if (null != editorPart && editorPart instanceof ITextEditor) {
				((ITextEditor)editorPart).getSelectionProvider().setSelection(null);
			}
			
			//Then iterate through all selections	
			for (final Iterator<?> iterator = ((ITreeSelection)selection).iterator(); iterator.hasNext();) {
				addAnomalyFromTree(iterator.next());		
			}
		}
		return null;
	}

	/**
	 * Method addAnomalyFromText.
	 * @param aSelection ITextSelection
	 */
	private void addAnomalyFromText(ITextSelection aSelection) {
		//This is a text selection in a text editor, we need to get the file path and
		//the position of the selection within the file
		try {
			final R4EUITextPosition position = CommandUtils.getPosition(aSelection);
			final IFile targetFile = CommandUtils.getTargetFile();
			//TODO For now we set the base file to null since the comparison is being done in the Egit compare editor
			//later we will do our own comparisons
			final IFile baseFile = null;
			
			//Add anomaly to model
			addAnomaly(baseFile, targetFile, position);
			
		} catch (CoreException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
	}
	
	
	/**
	 * Method addAnomalyFromTree.
	 * @param aSelection ITreeSelection
	 */
	private void addAnomalyFromTree(Object aSelection) {
		
		//This is a selection from the tree view (e.g. Review Navigator, Package Explorer etc...)
		//We will need to get the parent file path and the position of the element in a text editor
		//If the selection is on the File itself, then the selection will include all the lines
		//in the file.  Otherwise it will include all the lines corresponding to the currently 
		//selected element	
		try {

			R4EUITextPosition position = null;
			IFile targetFile = null;
			
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
				if (aSelection instanceof ITranslationUnit) {
					targetFile = (IFile)((ICElement) aSelection).getResource();
				} else if (aSelection instanceof ISourceReference) {
					targetFile = (IFile)((ICElement) aSelection).getParent().getResource();
				}
				position = CommandUtils.getPosition((org.eclipse.cdt.core.model.ISourceReference)aSelection, targetFile);
			} else {
				//This should never happen
				Activator.Ftracer.traceWarning("Invalid selection " + aSelection.getClass().toString() + ".  Ignoring");
				return;
			}
			
			//Add anomaly to model
			addAnomaly(null, targetFile, position);
			
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
	
	
	
	/** 
	 * Method AddAnomaly.
	 * 		Adds an anomaly to the model based on user input
	 * @param aBaseFile IFile
	 * @param aTargetFile IFile
	 * @param aUIPosition IR4EUIPosition
	 */
	private void addAnomaly(IFile aBaseFile, IFile aTargetFile, IR4EUIPosition aUIPosition) {
		
		try {
			
			//Check if the file element and/or anomaly already exist
			//If file exists, add anomaly element to it
			//if anomaly element already exist, add a new comment to it
			//for all other cases, create the parent elements as needed as well.
			final List<R4EUIReviewItem> reviewItems = R4EUIModelController.getActiveReview().getReviewItems();

			boolean isNewAnomaly = true;
			for (R4EUIReviewItem reviewItem : reviewItems) {
				R4EUIFileContext[] files = (R4EUIFileContext[]) reviewItem.getChildren();
				for (R4EUIFileContext file : files) {
					if (aTargetFile.equals(file.getTargetFile())) {
						
						//File already exists, check if anomaly also exists
						R4EUIAnomalyContainer anomalyContainer = (R4EUIAnomalyContainer) file.getAnomalyContainerElement();
						if (null != anomalyContainer) {
							R4EUIAnomaly[] anomalies = (R4EUIAnomaly[])anomalyContainer.getChildren();
							for (R4EUIAnomaly uiAnomaly : anomalies) {
								if (uiAnomaly.getPosition().isSameAs(aUIPosition)) {
									isNewAnomaly = false;		
									addCommentToExistingAnomaly(uiAnomaly);
									Activator.Ftracer.traceInfo("Added comment to existing anomaly: Target = " + aTargetFile.toString() + 
											((null != aBaseFile) ? "Base = " + aBaseFile.getFullPath(): "") + " Position = " 
											+ aUIPosition.toString());
								}
							}
						} else {
							anomalyContainer = new R4EUIAnomalyContainer(file, R4EUIConstants.ANOMALIES_LABEL_NAME);
							file.addChildren(anomalyContainer);
						}
						if (isNewAnomaly) {
							addAnomalyToExistingFileContext(anomalyContainer, aUIPosition);
							Activator.Ftracer.traceInfo("Added anomaly: Target = " + aTargetFile.toString() + 
									((null != aBaseFile) ? "Base = " + aBaseFile.getFullPath(): "") + " Position = " 
									+ aUIPosition.toString());
						}
						return;  //We found the file so we are done here	
					}
				}
			}

			//This is a new file create it (and its parent reviewItem) and all its children
			addAnomalyToNewFileContext(aBaseFile, aTargetFile, aUIPosition);
			Activator.Ftracer.traceInfo("Added anomaly: Target = " + aTargetFile.toString() + 
					((null != aBaseFile) ? "Base = " + aBaseFile.getFullPath(): "") + " Position = " 
					+ aUIPosition.toString());
			
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);
			
		} catch (OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);
		}
	}
	

	/**
	 * Method addCommentToExistingAnomaly.
	 * @param aUIAnomaly R4EUIAnomaly
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException 
	 */
	private void addCommentToExistingAnomaly(R4EUIAnomaly aUIAnomaly) throws ResourceHandlingException, OutOfSyncException {
		
		final R4EUIComment uiComment = aUIAnomaly.createComment();
		if (null != uiComment) {
			//Set focus to newly created anomaly comment
			R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiComment, AbstractTreeViewer.ALL_LEVELS);
			R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiComment), true);
		}
	}
	
	
	/**
	 * Method addAnomalyToExistingFileContext.
	 * @param aContainer R4EUIAnomalyContainer
	 * @param aUIPosition IR4EUIPosition
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException
	 */
	private void addAnomalyToExistingFileContext(R4EUIAnomalyContainer aContainer, IR4EUIPosition aUIPosition) 
		throws ResourceHandlingException, OutOfSyncException {

		final R4EUIAnomaly uiAnomaly = aContainer.createAnomaly((R4EUITextPosition) aUIPosition);
		if (null != uiAnomaly) {
			//Set focus to newly created anomaly comment
			R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiAnomaly, AbstractTreeViewer.ALL_LEVELS);
			R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiAnomaly), true);
		}	
	}
	
	
	/**
	 * Method addAnomalyToNewFileContext.
	 * @param aBaseFile IFile
	 * @param aTargetFile IFile
	 * @param aUIPosition IR4EUIPosition
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addAnomalyToNewFileContext(IFile aBaseFile, IFile aTargetFile, IR4EUIPosition aUIPosition) 
		throws ResourceHandlingException, OutOfSyncException {
			
		final R4EUIReview uiReview = R4EUIModelController.getActiveReview();
		final R4EUIReviewItem uiReviewItem = uiReview.createReviewItem(aTargetFile);
		if (null == uiReviewItem) return;
		
		final R4EUIFileContext uiFileContext = uiReviewItem.createFileContext(aBaseFile, aTargetFile);
		if (null == uiFileContext) {
			uiReview.removeChildren(uiReviewItem, false);
			return;
		}
		
		final R4EUIAnomalyContainer uiAnomalyContainer = new R4EUIAnomalyContainer(
				uiFileContext, R4EUIConstants.ANOMALIES_LABEL_NAME);
		uiFileContext.addChildren(uiAnomalyContainer);
		
		final R4EUIAnomaly uiAnomaly = uiAnomalyContainer.createAnomaly((R4EUITextPosition) aUIPosition);
		if (null != uiAnomaly) {
			//Set focus to newly created anomaly comment
			R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiAnomaly, AbstractTreeViewer.ALL_LEVELS);
			R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiAnomaly), true);
		}		
	}

}
