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
 * This class implements a Proxy class used to access/control the UI Anomaly element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ChangeStateDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.CloneAnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IAnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IChangeStateDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.NewAnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

@SuppressWarnings({ "restriction", "nls" })
public class R4EUITestAnomaly extends R4EUITestElement {

	private static final String NEW_ANOMALY_COMMAND = "org.eclipse.mylyn.reviews.r4e.ui.commands.NewAnomaly";

	private static final String CLONE_ANOMALY_COMMAND = "org.eclipse.mylyn.reviews.r4e.ui.commands.CloneAnomaly";

	public R4EUITestAnomaly(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Method createLinkedAnomaly
	 * 
	 * @param aContent
	 * @param aTitle
	 * @param aDescription
	 * @param aClass
	 * @param aRank
	 * @param aDueDate
	 * @param aRule
	 * @return R4EUIAnomalyBasic
	 */
	public R4EUIAnomalyBasic createLinkedAnomaly(R4EUIContent aContent, String aTitle, String aDescription,
			R4EDesignRuleClass aClass, R4EDesignRuleRank aRank, Date aDueDate, String aAssignedTo, R4EUIRule aRule) {

		//Inject mockup dialog for New Linked Anomaly
		IAnomalyInputDialog mockAnomalyDialog = mock(NewAnomalyInputDialog.class);
		R4EUIDialogFactory.getInstance().setNewAnomalyInputDialog(mockAnomalyDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		if (null == aRule) {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aTitle);
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aDescription);
			when(mockAnomalyDialog.getClass_()).thenReturn(aClass);
			when(mockAnomalyDialog.getRank()).thenReturn(aRank);
		} else {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aRule.getRule().getTitle());
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aRule.getRule().getDescription());
			when(mockAnomalyDialog.getClass_()).thenReturn(aRule.getRule().getClass_());
			when(mockAnomalyDialog.getRank()).thenReturn(aRule.getRule().getRank());
		}
		when(mockAnomalyDialog.getDueDate()).thenReturn(aDueDate);
		when(mockAnomalyDialog.getAssigned()).thenReturn(aAssignedTo);
		when(mockAnomalyDialog.getRuleReferenceValue()).thenReturn(null != aRule ? aRule.getRule() : null);
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunNewLinkedAnomaly implements Runnable {
			private R4EUIContent content;

			private R4EUIAnomalyBasic anomaly;

			public R4EUIAnomalyBasic getAnomaly() {
				return anomaly;
			}

			public void setContent(R4EUIContent aContent) {
				content = aContent;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(content);

					//Execute New Child Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.NEW_LINKED_ANOMALY_COMMAND, null);
					TestUtils.waitForJobs();
					anomaly = (R4EUIAnomalyBasic) getNavigatorSelectedElement();
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
		RunNewLinkedAnomaly newLinkedAnomalyJob = new RunNewLinkedAnomaly();
		newLinkedAnomalyJob.setContent(aContent);
		Display.getDefault().syncExec(newLinkedAnomalyJob);
		TestUtils.waitForJobs();
		return newLinkedAnomalyJob.getAnomaly();
	}

	/**
	 * Method createExternalAnomaly
	 * 
	 * @param aResource
	 * @param aTitle
	 * @param aDescription
	 * @param aClass
	 * @param aRank
	 * @param aDueDate
	 * @param aRule
	 * @return R4EUIAnomalyBasic
	 */
	public R4EUIAnomalyBasic createExternalAnomaly(IResource aResource, String aTitle, String aDescription,
			R4EDesignRuleClass aClass, R4EDesignRuleRank aRank, Date aDueDate, String aAssignedTo, R4EUIRule aRule) {

		//Inject mockup dialog for New External Anomaly
		IAnomalyInputDialog mockAnomalyDialog = mock(NewAnomalyInputDialog.class);
		R4EUIDialogFactory.getInstance().setNewAnomalyInputDialog(mockAnomalyDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		if (null == aRule) {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aTitle);
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aDescription);
			when(mockAnomalyDialog.getClass_()).thenReturn(aClass);
			when(mockAnomalyDialog.getRank()).thenReturn(aRank);
		} else {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aRule.getRule().getTitle());
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aRule.getRule().getDescription());
			when(mockAnomalyDialog.getClass_()).thenReturn(aRule.getRule().getClass_());
			when(mockAnomalyDialog.getRank()).thenReturn(aRule.getRule().getRank());
		}
		when(mockAnomalyDialog.getDueDate()).thenReturn(aDueDate);
		when(mockAnomalyDialog.getAssigned()).thenReturn(aAssignedTo);
		when(mockAnomalyDialog.getRuleReferenceValue()).thenReturn(null != aRule ? aRule.getRule() : null);
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunNewExternalAnomaly implements Runnable {
			private IResource resource;

			private R4EUIAnomalyBasic anomaly;

			public R4EUIAnomalyBasic getAnomaly() {
				return anomaly;
			}

			public void setResource(IResource aResource) {
				resource = aResource;
			}

			public void run() {
				try {
					//Set focus on Project Explorer and select resource
					setFocusOnProjectExplorer(resource);

					//Execute New Child Command
					fParentProxy.getCommandProxy().executeCommand(NEW_ANOMALY_COMMAND, null);
					TestUtils.waitForJobs();
					anomaly = (R4EUIAnomalyBasic) getNavigatorSelectedElement();
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
		RunNewExternalAnomaly newExternalAnomalyJob = new RunNewExternalAnomaly();
		newExternalAnomalyJob.setResource(aResource);
		Display.getDefault().syncExec(newExternalAnomalyJob);
		TestUtils.waitForJobs();
		return newExternalAnomalyJob.getAnomaly();
	}

	/**
	 * Method createCompareEditorAnomaly
	 * 
	 * @param aResource
	 * @param aStartPosition
	 * @param aLength
	 * @param aTitle
	 * @param aDescription
	 * @param aClass
	 * @param aRank
	 * @param aDueDate
	 * @param aRule
	 * @return R4EUIAnomalyBasic
	 */
	public R4EUIAnomalyBasic createCompareEditorAnomaly(IR4EUIModelElement aElement, int aStartPosition, int aLength,
			String aTitle, String aDescription, R4EDesignRuleClass aClass, R4EDesignRuleRank aRank, Date aDueDate,
			String aAssignedTo, R4EUIRule aRule) {

		//Inject mockup dialog for New Text Anomaly
		IAnomalyInputDialog mockAnomalyDialog = mock(NewAnomalyInputDialog.class);
		R4EUIDialogFactory.getInstance().setNewAnomalyInputDialog(mockAnomalyDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		if (null == aRule) {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aTitle);
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aDescription);
			when(mockAnomalyDialog.getClass_()).thenReturn(aClass);
			when(mockAnomalyDialog.getRank()).thenReturn(aRank);
		} else {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aRule.getRule().getTitle());
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aRule.getRule().getDescription());
			when(mockAnomalyDialog.getClass_()).thenReturn(aRule.getRule().getClass_());
			when(mockAnomalyDialog.getRank()).thenReturn(aRule.getRule().getRank());
		}
		when(mockAnomalyDialog.getDueDate()).thenReturn(aDueDate);
		when(mockAnomalyDialog.getAssigned()).thenReturn(aAssignedTo);
		when(mockAnomalyDialog.getRuleReferenceValue()).thenReturn(null != aRule ? aRule.getRule() : null);
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunNewTextAnomaly implements Runnable {
			private IR4EUIModelElement element;

			private int startPosition;

			private int length;

			private R4EUIAnomalyBasic anomaly;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void setStartPosition(int aStartPosition) {
				startPosition = aStartPosition;
			}

			public void setLength(int aLength) {
				length = aLength;
			}

			public R4EUIAnomalyBasic getAnomaly() {
				return anomaly;
			}

			public void run() {
				try {
					//Set focus on Review Navigator Element
					setFocusOnNavigatorElement(element);
					TestUtils.waitForJobs();

					//Open text editor
					openEditorOnCurrentElement(false);
					TestUtils.waitForJobs();

					//Select Range in Editor
					IEditorPart editor = getCurrentEditor();
					setCompareEditorSelection(editor, startPosition, length);
					TestUtils.waitForJobs();

					//Execute New Anomaly Command
					fParentProxy.getCommandProxy().executeCommand(NEW_ANOMALY_COMMAND, null);
					TestUtils.waitForJobs();

					//Close editor
					closeEditor(editor);
					TestUtils.waitForJobs();
					anomaly = (R4EUIAnomalyBasic) getNavigatorSelectedElement();
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
		RunNewTextAnomaly newTextAnomalyJob = new RunNewTextAnomaly();
		newTextAnomalyJob.setElement(aElement);
		newTextAnomalyJob.setStartPosition(aStartPosition);
		newTextAnomalyJob.setLength(aLength);
		Display.getDefault().syncExec(newTextAnomalyJob);
		TestUtils.waitForJobs();
		return newTextAnomalyJob.getAnomaly();
	}

	/**
	 * Method createGlobalAnomaly
	 * 
	 * @param aContainer
	 * @param aTitle
	 * @param aDescription
	 * @param aClass
	 * @param aRank
	 * @param aDueDate
	 * @param aRule
	 * @return R4EUIAnomalyBasic
	 */
	public R4EUIAnomalyBasic createGlobalAnomaly(R4EUIAnomalyContainer aContainer, String aTitle, String aDescription,
			R4EDesignRuleClass aClass, R4EDesignRuleRank aRank, Date aDueDate, R4EUIRule aRule, String aAssignedTo) {

		//Inject mockup dialog for New Linked Anomaly
		IAnomalyInputDialog mockAnomalyDialog = mock(NewAnomalyInputDialog.class);
		R4EUIDialogFactory.getInstance().setNewAnomalyInputDialog(mockAnomalyDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		if (null == aRule) {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aTitle);
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aDescription);
			when(mockAnomalyDialog.getClass_()).thenReturn(aClass);
			when(mockAnomalyDialog.getRank()).thenReturn(aRank);
		} else {
			when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aRule.getRule().getTitle());
			when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aRule.getRule().getDescription());
			when(mockAnomalyDialog.getClass_()).thenReturn(aRule.getRule().getClass_());
			when(mockAnomalyDialog.getRank()).thenReturn(aRule.getRule().getRank());
		}
		when(mockAnomalyDialog.getDueDate()).thenReturn(aDueDate);
		when(mockAnomalyDialog.getRuleReferenceValue()).thenReturn(null != aRule ? aRule.getRule() : null);
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);
		when(mockAnomalyDialog.getAssigned()).thenReturn(aAssignedTo);

		return (R4EUIAnomalyBasic) fParentProxy.getCommandProxy().runNewChildElement(aContainer);
	}

	/**
	 * Method cloneExternalAnomaly
	 * 
	 * @param aResource
	 * @param aSourceAnomaly
	 * @param aDueDate
	 * @param aAssignedTo
	 * @return R4EUIAnomalyBasic
	 */
	public R4EUIAnomalyBasic cloneExternalAnomaly(IResource aResource, R4EUIAnomalyBasic aSourceAnomaly, Date aDueDate,
			String aAssignedTo) {

		//Inject mockup dialog for New External Anomaly
		IAnomalyInputDialog mockAnomalyDialog = mock(CloneAnomalyInputDialog.class);
		R4EUIDialogFactory.getInstance().setCloneAnomalyInputDialog(mockAnomalyDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aSourceAnomaly.getAnomaly().getTitle());
		when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aSourceAnomaly.getAnomaly().getDescription());
		when(mockAnomalyDialog.getClass_()).thenReturn(
				((R4ECommentType) aSourceAnomaly.getAnomaly().getType()).getType());
		when(mockAnomalyDialog.getRank()).thenReturn(aSourceAnomaly.getAnomaly().getRank());
		when(mockAnomalyDialog.getRuleReferenceValue()).thenReturn(aSourceAnomaly.getAnomaly().getRule());
		when(mockAnomalyDialog.getDueDate()).thenReturn(aDueDate);
		when(mockAnomalyDialog.getAssigned()).thenReturn(aAssignedTo);
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunCloneExternalAnomaly implements Runnable {
			private IResource resource;

			private R4EUIAnomalyBasic anomaly;

			public R4EUIAnomalyBasic getAnomaly() {
				return anomaly;
			}

			public void setResource(IResource aResource) {
				resource = aResource;
			}

			public void run() {
				try {
					//Set focus on Project Explorer and select resource
					setFocusOnProjectExplorer(resource);

					//Execute New Child Command
					fParentProxy.getCommandProxy().executeCommand(CLONE_ANOMALY_COMMAND, null);
					TestUtils.waitForJobs();
					anomaly = (R4EUIAnomalyBasic) getNavigatorSelectedElement();
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
		RunCloneExternalAnomaly cloneExternalAnomalyJob = new RunCloneExternalAnomaly();
		cloneExternalAnomalyJob.setResource(aResource);
		Display.getDefault().syncExec(cloneExternalAnomalyJob);
		TestUtils.waitForJobs();
		return cloneExternalAnomalyJob.getAnomaly();
	}

	/**
	 * Method createCompareEditorAnomaly
	 * 
	 * @param aResource
	 * @param aStartPosition
	 * @param aLength
	 * @param aSourceAnomaly
	 * @param aDueDate
	 * @param aAssignedTo
	 * @return R4EUIAnomalyBasic
	 */
	public R4EUIAnomalyBasic cloneEditorAnomaly(IR4EUIModelElement aElement, int aStartPosition, int aLength,
			R4EUIAnomalyBasic aSourceAnomaly, Date aDueDate, String aAssignedTo) {

		//Inject mockup dialog for New Text Anomaly
		IAnomalyInputDialog mockAnomalyDialog = mock(CloneAnomalyInputDialog.class);
		R4EUIDialogFactory.getInstance().setCloneAnomalyInputDialog(mockAnomalyDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		when(mockAnomalyDialog.getAnomalyTitleValue()).thenReturn(aSourceAnomaly.getAnomaly().getTitle());
		when(mockAnomalyDialog.getAnomalyDescriptionValue()).thenReturn(aSourceAnomaly.getAnomaly().getDescription());
		when(mockAnomalyDialog.getClass_()).thenReturn(
				((R4ECommentType) aSourceAnomaly.getAnomaly().getType()).getType());
		when(mockAnomalyDialog.getRank()).thenReturn(aSourceAnomaly.getAnomaly().getRank());
		when(mockAnomalyDialog.getRuleReferenceValue()).thenReturn(aSourceAnomaly.getAnomaly().getRule());
		when(mockAnomalyDialog.getDueDate()).thenReturn(aDueDate);
		when(mockAnomalyDialog.getAssigned()).thenReturn(aAssignedTo);
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunCloneTextAnomaly implements Runnable {
			private IR4EUIModelElement element;

			private int startPosition;

			private int length;

			private R4EUIAnomalyBasic anomaly;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void setStartPosition(int aStartPosition) {
				startPosition = aStartPosition;
			}

			public void setLength(int aLength) {
				length = aLength;
			}

			public R4EUIAnomalyBasic getAnomaly() {
				return anomaly;
			}

			public void run() {
				try {
					//Set focus on Review Navigator Element
					setFocusOnNavigatorElement(element);
					TestUtils.waitForJobs();

					//Open text editor
					openEditorOnCurrentElement(false);
					TestUtils.waitForJobs();

					//Select Range in Editor
					IEditorPart editor = getCurrentEditor();
					setCompareEditorSelection(editor, startPosition, length);
					TestUtils.waitForJobs();

					//Execute New Anomaly Command
					fParentProxy.getCommandProxy().executeCommand(CLONE_ANOMALY_COMMAND, null);
					TestUtils.waitForJobs();

					//Close editor
					closeEditor(editor);
					TestUtils.waitForJobs();
					anomaly = (R4EUIAnomalyBasic) getNavigatorSelectedElement();
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
		RunCloneTextAnomaly cloneTextAnomalyJob = new RunCloneTextAnomaly();
		cloneTextAnomalyJob.setElement(aElement);
		cloneTextAnomalyJob.setStartPosition(aStartPosition);
		cloneTextAnomalyJob.setLength(aLength);
		Display.getDefault().syncExec(cloneTextAnomalyJob);
		TestUtils.waitForJobs();
		return cloneTextAnomalyJob.getAnomaly();
	}

	/**
	 * @param R4EUIAnomalyBasic
	 *            aCompareEditorAnomaly
	 * @param R4EAnomalyState
	 *            aState
	 */
	public void progressAnomaly(R4EUIAnomalyBasic aCompareEditorAnomaly, R4EAnomalyState aState) {
		//Inject mockup dialog for ChangeStateDialog 
		IChangeStateDialog mockAnomalyDialog = mock(ChangeStateDialog.class);
		R4EUIDialogFactory.getInstance().setChangeStateDialog(mockAnomalyDialog, R4EUIAnomalyExtended.class);

		//Here we need to stub the ChangeStateDialog get methods to return what we want
		when(mockAnomalyDialog.getState()).thenReturn(R4EUIAnomalyExtended.getStateString(aState));
		when(mockAnomalyDialog.open()).thenReturn(Window.OK);

		fParentProxy.getCommandProxy().progressElement(aCompareEditorAnomaly, null);
	}
}
