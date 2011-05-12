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
 * This class implements the context-sensitive command to find review items
 * in the parent project to add to the review
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ICompareNavigator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelectionContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.mylyn.versions.core.Change;
import org.eclipse.mylyn.versions.core.ChangeSet;
import org.eclipse.mylyn.versions.core.ScmArtifact;
import org.eclipse.mylyn.versions.ui.ScmUi;
import org.eclipse.mylyn.versions.ui.spi.ScmConnectorUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension3;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FindReviewItemsHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param event
	 *            ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) {

		// Get project to use (use adapters if needed)
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			final Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
			IProject project = null;

			// NOTE: The valadity testes are done if the ProjectPropertyTester class
			if (selectedElement instanceof IProject) {
				project = (IProject) selectedElement;
			} else if (selectedElement instanceof IJavaProject) {
				project = ((IJavaProject) selectedElement).getProject();
			} else if (selectedElement instanceof ICProject) {
				project = ((ICProject) selectedElement).getProject();
			} else if (selectedElement instanceof IPackageFragment || selectedElement instanceof IPackageFragmentRoot) {
				project = ((IJavaElement) selectedElement).getJavaProject().getProject();
			} else if (selectedElement instanceof IFolder) {
				project = ((IFolder) selectedElement).getProject();
			} else if (selectedElement instanceof IAdaptable) {
				final IAdaptable adaptableProject = (IAdaptable) selectedElement;
				project = (IProject) adaptableProject.getAdapter(IProject.class);
			} else {
				// Should never happen
				Activator.Ftracer.traceError("No project defined for selection of class " + selectedElement.getClass());
				Activator.getDefault().logError(
						"No project defined for selection of class " + selectedElement.getClass(), null);
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Find Review Item Error", new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
								"No project defined for selection", null), IStatus.ERROR);
				dialog.open();
				return null;
			}

			final ScmConnectorUi uiConnector = ScmUi.getUiConnector(project);
			ChangeSet changeSet = null;
			if (null != uiConnector) {
				try {
					Activator.Ftracer.traceDebug("Resolved Scm Ui connector: " + uiConnector);
					R4EUIModelController.setDialogOpen(true);
					changeSet = uiConnector.getChangeSet(null, project, null);
					R4EUIModelController.setDialogOpen(false);
				} catch (final CoreException e) {
					Activator.Ftracer.traceError("Exception: " + e.getMessage());
					Activator.getDefault().logError("Exception: " + e.toString(), e);
					R4EUIModelController.setDialogOpen(false);
					return null;
				}

				//TODO: This is a long-running operation.  For now set cursor.  Later we want to start a job here
				final Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
				shell.setCursor(shell.getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
				createReviewItem(changeSet);
				shell.setCursor(null);
			} else {
				// We could not find any version control system, thus no items
				final String strProject = ((null == project) ? "null" : project.getName());
				Activator.Ftracer.traceDebug("No Scm Ui connector found for project: " + strProject);
			}
		}
		return null;
	}

	/**
	 * Create and serialize the changeset in a Review Item
	 * 
	 * @param changeSet
	 */
	private void createReviewItem(final ChangeSet changeSet) {

		if (null == changeSet) {
			Activator.Ftracer.traceInfo("Received null ChangeSet");
			return;
		}

		final int size = changeSet.getChanges().size();
		Activator.Ftracer.traceInfo("Received ChangeSet with " + size + " elements");
		if (0 == size) {
			return; // nothing to add
		}

		try {
			// Add Review Item
			final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();

			for (R4EUIReviewItem uiItem : uiReview.getReviewItems()) {
				if (changeSet.getId().equals(uiItem.getItem().getRepositoryRef())) {
					//The commit item already exists so ignore command
					Activator.Ftracer.traceWarning("Review Item already exists.  Ignoring");
					final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING,
							"Cannot add Review Item", new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0,
									"Review Item already exists", null), IStatus.WARNING);
					dialog.open();
					return;
				}
			}

			final R4EUIReviewItem uiReviewItem = uiReview.createCommitReviewItem(changeSet, null);

			for (final Change change : changeSet.getChanges()) {
				final ScmArtifact baseArt = change.getBase();
				final ScmArtifact targetArt = change.getTarget();
				if (null == baseArt && null == targetArt) {
					Activator.Ftracer.traceDebug("Received a Change with no base and target in ChangeSet: "
							+ changeSet.getId() + ", Date: " + changeSet.getDate().toString());
				}

				// Get handle to local storage repository
				final IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview()
						.getReview());

				R4EFileVersion baseLocalVersion = null;
				R4EFileVersion targetLocalVersion = null;
				// Copy remote files to the local repository
				if (null != baseArt) {
					baseLocalVersion = CommandUtils.copyRemoteFileToLocalRepository(localRepository, baseArt);
				}
				if (null != targetArt) {
					targetLocalVersion = CommandUtils.copyRemoteFileToLocalRepository(localRepository, targetArt);
				}

				// Add File Context
				final R4EUIFileContext uiFileContext = uiReviewItem.createFileContext(baseLocalVersion,
						targetLocalVersion, CommandUtils.adaptType(change.getChangeType()));
				if (null == uiFileContext) {
					uiReview.removeChildren(uiReviewItem, false);
					return;
				}

				//For now, in order to populate differences, we temporarly open the compare editor 
				//and go through the differences
				R4ECompareEditorInput input = CommandUtils.createCompareEditorInput(uiFileContext.getBaseFileVersion(),
						uiFileContext.getTargetFileVersion(), false);
				CompareUI.openCompareEditor(input, true);

				//Do not create deltas for removed files
				if (null != targetLocalVersion) {
					//Create Delta Container

					final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					if (null != window) {
						final IWorkbenchPage page = window.getActivePage();
						if (null != page) {
							IEditorPart part = page.getActiveEditor();
							if (null != part) {
								//Get selections on the left pane of the compare editor
								final ICompareNavigator navigator = input.getNavigator();
								do {
									ITextEditorExtension3 ext = (ITextEditorExtension3) part.getAdapter(ITextEditorExtension3.class);

									if (ext instanceof AbstractTextEditor) {
										AbstractTextEditor editor = (AbstractTextEditor) ext;
										ISelection selection = editor.getSelectionProvider().getSelection();
										IR4EUIPosition position = CommandUtils.getPosition((ITextSelection) selection);

										//Lazily create the Delta container if not already done
										R4EUISelectionContainer deltaContainer = (R4EUISelectionContainer) uiFileContext.getSelectionContainerElement();
										if (null == deltaContainer) {
											deltaContainer = new R4EUISelectionContainer(uiFileContext,
													R4EUIConstants.DELTAS_LABEL);
											uiFileContext.addChildren(deltaContainer);
										}
										deltaContainer.createSelection((R4EUITextPosition) position);
									} else {
										//No delta can be created for this Change, so move on
										break;
									}
								} while (!navigator.selectChange(true));
							}
						}
					}

					//Close the editor
					IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					activePage.closeEditor(activePage.getActiveEditor(), false);
				}
			}
			//Notify users if need be
			final List<R4EReviewComponent> addedItems = new ArrayList<R4EReviewComponent>();
			addedItems.add(uiReviewItem.getItem());
			final R4EReview review = uiReview.getReview();
			if (review.getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
				if (((R4EFormalReview) review).getCurrent()
						.getType()
						.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
					MailServicesProxy.sendItemsAddedNotification(addedItems);
				}
			}

		} catch (final ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);
		} catch (final ReviewsFileStorageException e) {
			UIUtils.displayReviewsFileStorageErrorDialog(e);
		} catch (final OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);
		} catch (final CoreException e) {
			UIUtils.displayCoreErrorDialog(e);
		}
	}
}