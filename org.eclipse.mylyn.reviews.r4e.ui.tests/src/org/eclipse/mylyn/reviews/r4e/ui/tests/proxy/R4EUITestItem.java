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
 * This class implements a Proxy class used to access/control the UI Review Item element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.CompareStructureViewerSwitchingPane;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control.R4ECompareAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EDiffNode;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.mylyn.versions.core.ChangeSet;
import org.eclipse.mylyn.versions.core.ScmCore;
import org.eclipse.mylyn.versions.core.ScmRepository;
import org.eclipse.mylyn.versions.core.spi.ScmConnector;
import org.eclipse.mylyn.versions.ui.spi.ScmConnectorUi;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.core.history.provider.FileRevision;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.ITextEditor;

@SuppressWarnings({ "restriction", "nls" })
public class R4EUITestItem extends R4EUITestElement {

	private static String COMPARE_REVIEW_ITEMS_COMMAND = "org.eclipse.mylyn.reviews.r4e.ui.commands.compareItems";

	public R4EUITestItem(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Method createCommitItem
	 * 
	 * @param aProject
	 * @param aCommitIndex
	 * @return R4EUIReviewItem
	 */
	public R4EUIReviewItem createCommitItem(IProject aProject, int aCommitIndex) throws CoreException {

		//Here we need to inject a ScmUI dependency and mock the Versions connector
		ScmConnectorUi mockScmUiConnector = mock(ScmConnectorUi.class);
		R4EUIDialogFactory.getInstance().setScmUIConnector(mockScmUiConnector);

		when(mockScmUiConnector.getChangeSet((ScmRepository) anyObject(), (IResource) anyObject())).thenReturn(
				getChangeSet(aProject, aCommitIndex));

		//Inner class that runs the command on the UI thread
		class RunCreateCommitItem implements Runnable {
			private IProject project;

			private R4EUIReviewItem item;

			public void setProject(IProject aProject) {
				project = aProject;
			}

			public R4EUIReviewItem getItem() {
				return item;
			}

			public void run() {
				try {
					//Set focus on Project Explorer and select project
					setFocusOnProjectExplorer(project);

					//Execute New Review Group Command
					fParentProxy.getCommandProxy().executeCommand(
							"org.eclipse.mylyn.reviews.r4e.ui.commands.FindReviewItems", null);
					TestUtils.waitForJobs();

					IR4EUIModelElement element = getNavigatorSelectedElement();
					R4EAssert r4eAssert = new R4EAssert("createCommitItem");
					r4eAssert.setTest("getNavigatorSelectedElement");
					r4eAssert.assertEquals(element.getClass(), R4EUIReviewItem.class);

					item = (R4EUIReviewItem) element;
				} catch (ExecutionException e) {
					// ignore, test will fail later
				} catch (NotDefinedException e) {
					// ignore, test will fail later
				} catch (NotEnabledException e) {
					// ignore, test will fail later
				} catch (NotHandledException e) {
					// ignore, test will fail later
				}
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunCreateCommitItem createCommitItemJob = new RunCreateCommitItem();
		createCommitItemJob.setProject(aProject);
		Display.getDefault().syncExec(createCommitItemJob);
		TestUtils.waitForJobs();
		return createCommitItemJob.getItem();
	}

	/**
	 * Method createManualTreeItem
	 * 
	 * @param aResource
	 * @return R4EUIReviewItem
	 */
	public R4EUIReviewItem createManualTreeItem(IResource aResource) {

		//Inner class that runs the command on the UI thread
		class RunCreateManualTreeItem implements Runnable {
			private IResource resource;

			private R4EUIReviewItem item;

			public void setResource(IResource aResource) {
				resource = aResource;
			}

			public R4EUIReviewItem getItem() {
				return item;
			}

			public void run() {
				try {
					//Set focus on Project Explorer and select resource
					setFocusOnProjectExplorer(resource);

					//Execute New Review Group Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.NEW_REVIEW_ITEM_COMMAND, null);
					TestUtils.waitForJobs();

					IR4EUIModelElement element = getNavigatorSelectedElement();
					R4EAssert r4eAssert = new R4EAssert("createManualTreeItem");
					r4eAssert.setTest("getNavigatorSelectedElement");
					r4eAssert.assertEquals(element.getClass(), R4EUIReviewItem.class);

					item = (R4EUIReviewItem) element;
				} catch (ExecutionException e) {
					// ignore, test will fail later
				} catch (NotDefinedException e) {
					// ignore, test will fail later
				} catch (NotEnabledException e) {
					// ignore, test will fail later
				} catch (NotHandledException e) {
					// ignore, test will fail later
				}
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunCreateManualTreeItem createManualTreeItemJob = new RunCreateManualTreeItem();
		createManualTreeItemJob.setResource(aResource);
		Display.getDefault().syncExec(createManualTreeItemJob);
		TestUtils.waitForJobs();
		return createManualTreeItemJob.getItem();
	}

	/**
	 * Method createManualTextItem
	 * 
	 * @param aResource
	 * @param aStartPosition
	 * @param aLength
	 * @return R4EUIReviewItem
	 */
	public R4EUIReviewItem createManualTextItem(IResource aResource, int aStartPosition, int aLength) {

		//Inner class that runs the command on the UI thread
		class RunCreateManualTextItem implements Runnable {
			private IResource resource;

			private int startPosition;

			private int length;

			private R4EUIReviewItem item;

			public void setResource(IResource aResource) {
				resource = aResource;
			}

			public void setStartPosition(int aStartPosition) {
				startPosition = aStartPosition;
			}

			public void setLength(int aLength) {
				length = aLength;
			}

			public R4EUIReviewItem getItem() {
				return item;
			}

			public void run() {
				try {
					//Set focus on Project Explorer and select project
					setFocusOnProjectExplorer(resource);
					TestUtils.waitForJobs();

					//Open text editor and Set selected range
					IEditorPart editor = openEditor(resource);
					final TextSelection selectedText = new TextSelection(startPosition, length);
					((ITextEditor) editor).getSelectionProvider().setSelection(selectedText);
					TestUtils.waitForJobs();

					//Execute New Review Item Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.NEW_REVIEW_ITEM_COMMAND, null);
					TestUtils.waitForJobs();

					//Close editor
					closeEditor(editor);
					TestUtils.waitForJobs();

					IR4EUIModelElement element = getNavigatorSelectedElement();
					R4EAssert r4eAssert = new R4EAssert("createManualTextItem");
					r4eAssert.setTest("getNavigatorSelectedElement");
					r4eAssert.assertEquals(element.getClass(), R4EUIReviewItem.class);

					item = (R4EUIReviewItem) element;
				} catch (ExecutionException e) {
					// ignore, test will fail later
				} catch (NotDefinedException e) {
					// ignore, test will fail later
				} catch (NotEnabledException e) {
					// ignore, test will fail later
				} catch (NotHandledException e) {
					// ignore, test will fail later
				} catch (PartInitException e) {
					// ignore, test will fail later
				}
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunCreateManualTextItem createManualTextItemJob = new RunCreateManualTextItem();
		createManualTextItemJob.setResource(aResource);
		createManualTextItemJob.setStartPosition(aStartPosition);
		createManualTextItemJob.setLength(aLength);
		Display.getDefault().syncExec(createManualTextItemJob);
		TestUtils.waitForJobs();
		return createManualTextItemJob.getItem();
	}

	/**
	 * Method openCompareReviewItems
	 * 
	 * @param aItem1
	 *            - R4EUIReviewItem
	 * @param aItem2
	 *            - R4EUIReviewItem
	 * @param aAnnotationElement
	 *            - IR4EUIModelElement
	 * @param aAnnotationType
	 *            - String
	 */
	public void openCompareReviewItems(final R4EUIReviewItem aItem1, final R4EUIReviewItem aItem2,
			IR4EUIModelElement aAnnotationElement, String aAnnotationType) {

		//Inner class that runs the command on the UI thread
		class RunCompareReviewItems implements Runnable {
			private List<IR4EUIModelElement> elements;

			private IR4EUIModelElement annotationElement;

			private String annotationType;

			public void setElements(List<IR4EUIModelElement> aElements) {
				elements = aElements;
			}

			public void setAnnotationElement(IR4EUIModelElement aAnnotationElement) {
				annotationElement = aAnnotationElement;
			}

			public void setAnnotationType(String aAnnotationType) {
				annotationType = aAnnotationType;
			}

			public void run() {
				try {
					R4EAssert r4eAssert = new R4EAssert("openCompareReviewItems");
					r4eAssert.setTest("Open Compare Review Item");

					//Set focus on Review Navigator Element
					setFocusOnNavigatorElements(elements);
					TestUtils.waitForJobs();

					//Compare Review Items
					fParentProxy.getCommandProxy().executeCommand(COMPARE_REVIEW_ITEMS_COMMAND, null);
					TestUtils.waitForJobs();

					//Select Range in Editor
					IEditorPart editor = getCurrentEditor();
					TestUtils.waitForJobs();

					R4EDiffNode selectedDiffNode = (R4EDiffNode) ((R4ECompareEditorInput) editor.getEditorInput()).getSelectedEdition();

					R4EFileVersion targetVersion = null;
					R4EFileVersion baseVersion = null;

					for (R4EFileContext file : aItem2.getItem().getFileContextList()) {
						if (null != file.getTarget()
								&& file.getTarget().getName().equals(selectedDiffNode.getTargetVersion().getName())) {
							targetVersion = file.getTarget();
						}
					}
					r4eAssert.assertEquals(targetVersion, selectedDiffNode.getTargetVersion());

					for (R4EFileContext file : aItem1.getItem().getFileContextList()) {
						if (null != file.getTarget()
								&& file.getTarget().getName().equals(selectedDiffNode.getBaseVersion().getName())) {
							baseVersion = file.getTarget();
						}
					}
					r4eAssert.assertEquals(baseVersion, selectedDiffNode.getBaseVersion());

					//Open the content CompareEditor on the selected diffNode
					final ICompareNavigator navigator = ((R4ECompareEditorInput) editor.getEditorInput()).getNavigator();
					final Object structuredPane = ((CompareEditorInputNavigator) navigator).getPanes()[0];
					Tree tree = (Tree) ((CompareStructureViewerSwitchingPane) structuredPane).getChildren()[2];
					tree.setSelection(tree.getItem(0));
					Viewer viewer = ((CompareStructureViewerSwitchingPane) structuredPane).getViewer();
					OpenEvent event = new OpenEvent(viewer, viewer.getSelection());
					((CompareStructureViewerSwitchingPane) structuredPane).open(event);
					TestUtils.waitForJobs();

					//Verify that the annotation for the passed in Anomaly is present on the right (base) side
					R4ECompareAnnotationSupport support = (R4ECompareAnnotationSupport) ((R4ECompareEditorInput) editor.getEditorInput()).getCurrentDiffNode()
							.getAnnotationSupport();
					IReviewAnnotation annotation = support.getBaseAnnotationModel().findAnnotation(annotationType,
							annotationElement);
					r4eAssert.assertNotNull(annotation);

				} catch (ExecutionException e) {
					// ignore, test will fail later
				} catch (NotDefinedException e) {
					// ignore, test will fail later
				} catch (NotEnabledException e) {
					// ignore, test will fail later
				} catch (NotHandledException e) {
					// ignore, test will fail later
				}
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunCompareReviewItems compareReviewItemsJob = new RunCompareReviewItems();
		List<IR4EUIModelElement> elements = new ArrayList<IR4EUIModelElement>(2);
		elements.add(aItem1);
		elements.add(aItem2);
		compareReviewItemsJob.setElements(elements);
		compareReviewItemsJob.setAnnotationElement(aAnnotationElement);
		compareReviewItemsJob.setAnnotationType(aAnnotationType);
		Display.getDefault().syncExec(compareReviewItemsJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method getChangeSet
	 * 
	 * @param aProject
	 * @param aCommitIndex
	 * @return ChangeSet
	 */
	private ChangeSet getChangeSet(IProject aProject, int aCommitIndex) throws CoreException {
		ScmConnector connector = ScmCore.getConnector(aProject);
		ScmRepository repository = connector.getRepository(aProject, new NullProgressMonitor());
		List<ChangeSet> changeSets = connector.getChangeSets(repository, new NullProgressMonitor());

		String changeSetId = changeSets.get(aCommitIndex).getId();
		ChangeSet updatedChangeSet = null;

		IFileRevision fileRevision = createFileRevision(changeSetId);
		updatedChangeSet = connector.getChangeSet(repository, fileRevision, new NullProgressMonitor());
		return updatedChangeSet;
	}

	/**
	 * Method createFileRevision
	 * 
	 * @param changeSetId
	 * @return IFileRevision
	 */
	private IFileRevision createFileRevision(final String changeSetId) {
		IFileRevision fileRevision = new FileRevision() {

			public IFileRevision withAllProperties(IProgressMonitor monitor) throws CoreException {
				return null;
			}

			public boolean isPropertyMissing() {
				return false;
			}

			public IStorage getStorage(IProgressMonitor monitor) throws CoreException {
				return null;
			}

			@Override
			public String getContentIdentifier() {
				return changeSetId;
			}

			public String getName() {
				return null;
			}
		};
		return fileRevision;
	}
}
