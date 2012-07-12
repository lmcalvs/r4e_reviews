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

import java.util.List;

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
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.mylyn.versions.core.ChangeSet;
import org.eclipse.mylyn.versions.core.ScmCore;
import org.eclipse.mylyn.versions.core.ScmRepository;
import org.eclipse.mylyn.versions.core.spi.ScmConnector;
import org.eclipse.mylyn.versions.ui.spi.ScmConnectorUi;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.core.history.provider.FileRevision;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.ITextEditor;

@SuppressWarnings("restriction")
public class R4EUITestItem extends R4EUITestElement {

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
					item = (R4EUIReviewItem) getNavigatorSelectedElement();
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
					item = (R4EUIReviewItem) getNavigatorSelectedElement();
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
					item = (R4EUIReviewItem) getNavigatorSelectedElement();
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
