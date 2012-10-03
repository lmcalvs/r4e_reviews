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
 * This class implements a Proxy class used to access/control the UI Review Group element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IReviewGroupInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ReviewGroupInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed.ReviewGroupTabPropertySection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;

@SuppressWarnings("restriction")
public class R4EUITestReviewGroup extends R4EUITestElement {

	public R4EUITestReviewGroup(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Create a new Review Group
	 * 
	 * @param aParentFolder
	 * @param aName
	 * @param aDescription
	 * @param aEntry
	 * @param aProjects
	 * @param aComponents
	 * @param aRuleSets
	 * @return R4EUIReviewGroup
	 */
	public R4EUIReviewGroup createReviewGroup(String aParentFolder, String aName, String aDescription, String aEntry,
			String[] aProjects, String[] aComponents, String[] aRuleSets) {

		//Inject mockup dialog for New Review Group
		IReviewGroupInputDialog mockReviewGroupDialog = mock(ReviewGroupInputDialog.class);
		R4EUIDialogFactory.getInstance().setReviewGroupInputDialog(mockReviewGroupDialog);

		//Here we need to stub the ReviewGroupInputDialog get methods to return what we want
		when(mockReviewGroupDialog.getGroupFolderValue()).thenReturn(aParentFolder);
		when(mockReviewGroupDialog.getGroupNameValue()).thenReturn(aName);
		when(mockReviewGroupDialog.getGroupDescriptionValue()).thenReturn(aDescription);
		when(mockReviewGroupDialog.getDefaultEntryCriteriaValue()).thenReturn(aEntry);
		when(mockReviewGroupDialog.getAvailableProjectsValues()).thenReturn(aProjects);
		when(mockReviewGroupDialog.getAvailableComponentsValues()).thenReturn(aComponents);
		when(mockReviewGroupDialog.getRuleSetValues()).thenReturn(aRuleSets);
		when(mockReviewGroupDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunCreateReviewGroup implements Runnable {
			private R4EUIReviewGroup group;

			public R4EUIReviewGroup getGroup() {
				return group;
			}

			public void run() {
				try {
					//Create Event for Command
					Event event = new Event();
					event.widget = null;
					ToolBarManager toolbar = (ToolBarManager) R4EUIModelController.getNavigatorView()
							.getViewSite()
							.getActionBars()
							.getToolBarManager();
					IContributionItem contribItem = toolbar.find(R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND);
					for (ToolItem item : toolbar.getControl().getItems()) {
						if (item.getData().equals(contribItem)) {
							event.widget = item;
							break;
						}
					}

					//Execute New Review Group Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND, event);
					TestUtils.waitForJobs();
					group = (R4EUIReviewGroup) getNavigatorSelectedElement();
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
		RunCreateReviewGroup groupJob = new RunCreateReviewGroup();
		Display.getDefault().syncExec(groupJob);
		TestUtils.waitForJobs();
		return groupJob.getGroup();
	}

	/**
	 * Method changeReviewGroupDescription.
	 * 
	 * @param aGroup
	 * @param aNewDescription
	 */
	public void changeReviewGroupDescription(R4EUIReviewGroup aGroup, String aNewDescription) {

		//Inner class that runs the command on the UI thread
		class RunChangeGroupDescription implements Runnable {
			private R4EUIReviewGroup group;

			private String description;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setDescription(String aNewDescription) {
				description = aNewDescription;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Description text field
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.setDescription(description);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunChangeGroupDescription changeDescriptionJob = new RunChangeGroupDescription();
		changeDescriptionJob.setGroup(aGroup);
		changeDescriptionJob.setDescription(aNewDescription);
		Display.getDefault().syncExec(changeDescriptionJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method changeReviewGroupDefaultEntryCriteria.
	 * 
	 * @param aGroup
	 * @param aNewCriteria
	 */
	public void changeReviewGroupDefaultEntryCriteria(R4EUIReviewGroup aGroup, String aNewCriteria) {

		//Inner class that runs the command on the UI thread
		class RunChangeGroupCriteria implements Runnable {
			private R4EUIReviewGroup group;

			private String criteria;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setCriteria(String aNewCriteria) {
				criteria = aNewCriteria;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Description text field
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.setDefaultEntryCriteria(criteria);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunChangeGroupCriteria changeCriteriaJob = new RunChangeGroupCriteria();
		changeCriteriaJob.setGroup(aGroup);
		changeCriteriaJob.setCriteria(aNewCriteria);
		Display.getDefault().syncExec(changeCriteriaJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method removeReviewGroupAvailableProject.
	 * 
	 * @param aGroup
	 * @param aProjectName
	 */
	public void removeReviewGroupAvailableProject(R4EUIReviewGroup aGroup, String aProjectName) {

		//Inner class that runs the command on the UI thread
		class RunRemoveGroupProject implements Runnable {
			private R4EUIReviewGroup group;

			private String project;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setProject(String aProjectName) {
				project = aProjectName;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Remove available project 
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.removeAvailableProject(project);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunRemoveGroupProject removeProjectJob = new RunRemoveGroupProject();
		removeProjectJob.setGroup(aGroup);
		removeProjectJob.setProject(aProjectName);
		Display.getDefault().syncExec(removeProjectJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method addReviewGroupAvailableProject.
	 * 
	 * @param aGroup
	 * @param aProjectName
	 */
	public void addReviewGroupAvailableProject(R4EUIReviewGroup aGroup, String aProjectName) {

		//Inner class that runs the command on the UI thread
		class RunAddGroupProject implements Runnable {
			private R4EUIReviewGroup group;

			private String project;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setProject(String aProjectName) {
				project = aProjectName;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Remove available project 
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.addAvailableProject(project);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunAddGroupProject addProjectJob = new RunAddGroupProject();
		addProjectJob.setGroup(aGroup);
		addProjectJob.setProject(aProjectName);
		Display.getDefault().syncExec(addProjectJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method removeReviewGroupAvailableComponent.
	 * 
	 * @param aGroup
	 * @param aComponentName
	 */
	public void removeReviewGroupAvailableComponent(R4EUIReviewGroup aGroup, String aComponentName) {

		//Inner class that runs the command on the UI thread
		class RunRemoveGroupComponent implements Runnable {
			private R4EUIReviewGroup group;

			private String component;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setProject(String aComponentName) {
				component = aComponentName;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Remove available project 
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.removeAvailableComponent(component);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunRemoveGroupComponent removeComponentJob = new RunRemoveGroupComponent();
		removeComponentJob.setGroup(aGroup);
		removeComponentJob.setProject(aComponentName);
		Display.getDefault().syncExec(removeComponentJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method addReviewGroupAvailableComponent.
	 * 
	 * @param aGroup
	 * @param aComponentName
	 */
	public void addReviewGroupAvailableComponent(R4EUIReviewGroup aGroup, String aComponentName) {

		//Inner class that runs the command on the UI thread
		class RunAddGroupComponent implements Runnable {
			private R4EUIReviewGroup group;

			private String component;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setProject(String aComponentName) {
				component = aComponentName;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Remove available project 
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.addAvailableComponent(component);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunAddGroupComponent addComponentJob = new RunAddGroupComponent();
		addComponentJob.setGroup(aGroup);
		addComponentJob.setProject(aComponentName);
		Display.getDefault().syncExec(addComponentJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method removeReviewGroupRuleSet.
	 * 
	 * @param aGroup
	 * @param aRuleSet
	 */
	public void removeReviewGroupRuleSet(R4EUIReviewGroup aGroup, String aRuleSet) {

		//Inner class that runs the command on the UI thread
		class RunRemoveGroupRuleSet implements Runnable {
			private R4EUIReviewGroup group;

			private String ruleSet;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setRuleSet(String aRuleSet) {
				ruleSet = aRuleSet;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Remove available project 
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.removeRuleSet(ruleSet);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunRemoveGroupRuleSet removeRuleSetJob = new RunRemoveGroupRuleSet();
		removeRuleSetJob.setGroup(aGroup);
		removeRuleSetJob.setRuleSet(aRuleSet);
		Display.getDefault().syncExec(removeRuleSetJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method addReviewGroupRuleSet.
	 * 
	 * @param aGroup
	 * @param aRuleSet
	 */
	public void addReviewGroupRuleSet(R4EUIReviewGroup aGroup, String aRuleSet) {

		//Inner class that runs the command on the UI thread
		class RunAddGroupRuleSet implements Runnable {
			private R4EUIReviewGroup group;

			private String ruleSet;

			public void setGroup(R4EUIReviewGroup aGroup) {
				group = aGroup;
			}

			public void setProject(String aRuleSet) {
				ruleSet = aRuleSet;
			}

			public void run() {
				//Set focus on Navigator view and select element
				setFocusOnNavigatorElement(group);
				setFocusOnPropertiesView();

				//Set Remove available project 
				ReviewGroupTabPropertySection section = (ReviewGroupTabPropertySection) R4EUIModelController.getCurrentPropertySection();
				section.addRuleSet(ruleSet);
				TestUtils.waitForJobs();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunAddGroupRuleSet addRuleSetJob = new RunAddGroupRuleSet();
		addRuleSetJob.setGroup(aGroup);
		addRuleSetJob.setProject(aRuleSet);
		Display.getDefault().syncExec(addRuleSetJob);
		TestUtils.waitForJobs();
	}

}
