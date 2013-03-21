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
 * This class implements a Proxy class used to issue R4E UI commands programmatically
 * for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation;
import org.eclipse.mylyn.reviews.notifications.core.NotificationFilter;
import org.eclipse.mylyn.reviews.notifications.spi.NotificationsConnector;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control.R4ESingleAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ISendNotificationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.SendNotificationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.mylyn.reviews.r4e.upgrade.ui.R4EUpgradeController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@SuppressWarnings("restriction")
public class R4EUITestCommands extends R4EUITestElement {

	private static EMailDetails FEmailDetails = null;

	public R4EUITestCommands(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Method executeCommand
	 * 
	 * @param aCommandStr
	 * @param aEvent
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 */
	protected void executeCommand(String aCommandStr, Event aEvent) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		IHandlerService service = (IHandlerService) R4EUIModelController.getNavigatorView()
				.getSite()
				.getWorkbenchWindow()
				.getService(IHandlerService.class);
		service.executeCommand(aCommandStr, aEvent);
	}

	/**
	 * Method runNewChildElement Create a new child element on the UI thread
	 * 
	 * @param aParent
	 * @return IR4EUIModelElement
	 */
	protected IR4EUIModelElement runNewChildElement(IR4EUIModelElement aParent) {
		//Inner class that runs the command on the UI thread
		class RunNewChild implements Runnable {
			private IR4EUIModelElement parent;

			private IR4EUIModelElement child;

			public IR4EUIModelElement getChild() {
				return child;
			}

			public void setParent(IR4EUIModelElement aParent) {
				parent = aParent;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(parent);

					//Create Event for Command
					Event event = new Event();
					event.widget = (R4EUIModelController.getNavigatorView().getTreeViewer().getTree().getSelection())[0];

					//Execute New Child Command
					executeCommand(R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND, event);
					TestUtils.waitForJobs();
					child = getNavigatorSelectedElement();
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
		RunNewChild newChildJob = new RunNewChild();
		newChildJob.setParent(aParent);
		Display.getDefault().syncExec(newChildJob);
		TestUtils.waitForJobs();
		return newChildJob.getChild();
	}

	/**
	 * Method closeElement Close a UI model element
	 * 
	 * @param aElement
	 */
	public void closeElement(IR4EUIModelElement aElement) {

		//Inner class that runs the command on the UI thread
		class RunCloseElement implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.CLOSE_ELEMENT_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunCloseElement closeJob = new RunCloseElement();
		closeJob.setElement(aElement);
		Display.getDefault().syncExec(closeJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method openElement Open a UI model element
	 * 
	 * @param aElement
	 */
	public void openElement(IR4EUIModelElement aElement) {

		//Inner class that runs the command on the UI thread
		class RunOpenElement implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.OPEN_ELEMENT_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunOpenElement openJob = new RunOpenElement();
		openJob.setElement(aElement);
		Display.getDefault().syncExec(openJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method openElementWithUpdate Open a UI model element with update dialog triggered
	 * 
	 * @param aElement
	 * @param aDialogButtonIndex
	 */
	public void openElementWithUpdate(IR4EUIModelElement aElement, int aDialogButtonIndex) {

		//Inject upgrade dialog result
		R4EUpgradeController.setUpgradeDialogResult(aDialogButtonIndex);

		//Inner class that runs the command on the UI thread
		class RunOpenElement implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.OPEN_ELEMENT_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunOpenElement openJob = new RunOpenElement();
		openJob.setElement(aElement);
		Display.getDefault().syncExec(openJob);
		TestUtils.waitForJobs();
		R4EUpgradeController.setUpgradeDialogResult(-1); //remove injected upgrade dialog result
	}

	/**
	 * Method dragDropElements Drag and Drop UI model elements
	 * 
	 * @param aElement
	 */
	public List<IR4EUIModelElement> dragDropElements(final List<IR4EUIModelElement> aSourceElements,
			IR4EUIModelElement aTargetElement) {

		//Inner class that runs the command on the UI thread
		class RunDragDropElements implements Runnable {
			private List<IR4EUIModelElement> sourceElements;

			private IR4EUIModelElement targetElement;

			private List<IR4EUIModelElement> droppedElements;

			public void setSourceElements(List<IR4EUIModelElement> aSourceElements) {
				sourceElements = aSourceElements;
			}

			public void setTargetElement(IR4EUIModelElement aTargetElement) {
				targetElement = aTargetElement;
			}

			public List<IR4EUIModelElement> getDroppedElements() {
				return droppedElements;
			}

			public void run() {
				final Display display = R4EUIModelController.getNavigatorView().getSite().getShell().getDisplay();

				display.timerExec(2000, new Runnable() {
					public void run() {
						new Thread() {
							Rectangle dragSourceItemBounds, dropTargetItemBounds;

							@Override
							public void run() {
								display.syncExec(new Runnable() {
									public void run() {
										final Tree reviewNavigatorTree = R4EUIModelController.getNavigatorView()
												.getTreeViewer()
												.getTree();

										setFocusOnNavigatorElement(sourceElements.get(0));
										TreeItem dragSourceItem = reviewNavigatorTree.getSelection()[0];
										dragSourceItemBounds = display.map(reviewNavigatorTree, null,
												dragSourceItem.getBounds());

										setFocusOnNavigatorElement(targetElement);
										TreeItem dropTargetItem = reviewNavigatorTree.getSelection()[0];
										dropTargetItemBounds = display.map(reviewNavigatorTree, null,
												dropTargetItem.getBounds());
									}
								});
								Event event = new Event();
								event.type = SWT.MouseMove;
								event.x = dragSourceItemBounds.x + 20;
								event.y = dragSourceItemBounds.y + (dragSourceItemBounds.height / 2);
								display.post(event);

								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								event = new Event();
								event.type = SWT.MouseDown;
								event.button = 1;
								event.x = dragSourceItemBounds.x + 20;
								event.y = dragSourceItemBounds.y + (dragSourceItemBounds.height / 2);
								display.post(event);

								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								Point midway = new Point(dragSourceItemBounds.x + 20, dragSourceItemBounds.y
										+ ((dropTargetItemBounds.y - dragSourceItemBounds.y) / 2));
								event = new Event();
								event.type = SWT.MouseMove;
								event.x = midway.x;
								event.y = midway.y;
								display.post(event);

								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								event = new Event();
								event.type = SWT.MouseMove;
								event.x = dropTargetItemBounds.x + 20;
								event.y = dropTargetItemBounds.y + (dropTargetItemBounds.height / 2);
								display.post(event);

								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								event = new Event();
								event.type = SWT.MouseUp;
								event.button = 1;
								event.x = dropTargetItemBounds.x + 20;
								event.y = dropTargetItemBounds.y + (dropTargetItemBounds.height / 2);
								display.post(event);
							}
						}.start();
					}
				});
				while (true) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				//droppedElements = getNavigatorSelectedElements();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunDragDropElements dragDropJob = new RunDragDropElements();
		dragDropJob.setSourceElements(aSourceElements);
		dragDropJob.setTargetElement(aTargetElement);
		Display.getDefault().syncExec(dragDropJob);
		TestUtils.waitForJobs();
		return dragDropJob.getDroppedElements();
	}

	/**
	 * Method copyElement Copy UI model elements to clipboard
	 * 
	 * @param aElement
	 */
	public void copyElements(final List<IR4EUIModelElement> aElements) {

		//Inner class that runs the command on the UI thread
		class RunCopyElements implements Runnable {
			private List<IR4EUIModelElement> elements;

			public void setElements(List<IR4EUIModelElement> aElements) {
				elements = aElements;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElements(elements);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.COPY_ELEMENT_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunCopyElements copyJob = new RunCopyElements();
		copyJob.setElements(aElements);
		Display.getDefault().syncExec(copyJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method pasteElement Paste UI model elements from clipboard
	 * 
	 * @param aElement
	 */
	public List<IR4EUIModelElement> pasteElements(IR4EUIModelElement aElement) {

		//Inner class that runs the command on the UI thread
		class RunPasteElement implements Runnable {
			private IR4EUIModelElement targetElement;

			private List<IR4EUIModelElement> pastedElements;

			public List<IR4EUIModelElement> getPastedElements() {
				return pastedElements;
			}

			public void setTargetElement(IR4EUIModelElement aElement) {
				targetElement = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(targetElement);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.PASTE_ELEMENT_COMMAND, null);
					TestUtils.waitForJobs();
					pastedElements = getNavigatorSelectedElements();
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
		RunPasteElement pasteJob = new RunPasteElement();
		pasteJob.setTargetElement(aElement);
		Display.getDefault().syncExec(pasteJob);
		TestUtils.waitForJobs();
		return pasteJob.getPastedElements();
	}

	/**
	 * Method changeReviewedState Mark/Unmark element as reviewed
	 * 
	 * @param aElement
	 */
	public void changeReviewedState(IR4EUIModelElement aElement) {

		//Here we need to inject a dependency and mock the Notifications dialog
		ISendNotificationInputDialog mockNotificationDialog = mock(SendNotificationInputDialog.class);
		R4EUIDialogFactory.getInstance().setSendNotificationInputDialog(mockNotificationDialog);
		when(mockNotificationDialog.open()).thenReturn(Window.OK);
		when(mockNotificationDialog.getMessageTypeValue()).thenReturn(R4EUIConstants.MESSAGE_TYPE_COMPLETION);

		//Inner class that runs the command on the UI thread
		class RunChangeReviewedState implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunChangeReviewedState changeJob = new RunChangeReviewedState();
		changeJob.setElement(aElement);
		Display.getDefault().syncExec(changeJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method progressElement Progress Element to next state
	 * 
	 * @param aElement
	 * @param aNextState
	 */
	public void progressElement(IR4EUIModelElement aElement, Object aNextState) {
		//Inner class that runs the command on the UI thread
		class RunProgressElement implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Create Event for Command
					Event event = new Event();
					event.widget = null;
					ToolBarManager toolbar = (ToolBarManager) R4EUIModelController.getNavigatorView()
							.getViewSite()
							.getActionBars()
							.getToolBarManager();
					IContributionItem contribItem = toolbar.find(R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND);
					for (ToolItem item : toolbar.getControl().getItems()) {
						if (item.getData().equals(contribItem)) {
							event.widget = item;
							break;
						}
					}

					//Execute Progress Element Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND, event);
					TestUtils.waitForJobs();
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
		RunProgressElement progressJob = new RunProgressElement();
		progressJob.setElement(aElement);
		Display.getDefault().syncExec(progressJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method regressElement Regress Element to previous state
	 * 
	 * @param aElement
	 */
	public void regressElement(IR4EUIModelElement aElement) {
		//Inner class that runs the command on the UI thread
		class RunRegressElement implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Create Event for Command
					Event event = new Event();
					event.widget = null;
					ToolBarManager toolbar = (ToolBarManager) R4EUIModelController.getNavigatorView()
							.getViewSite()
							.getActionBars()
							.getToolBarManager();
					IContributionItem contribItem = toolbar.find(R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND);
					for (ToolItem item : toolbar.getControl().getItems()) {
						if (item.getData().equals(contribItem)) {
							event.widget = item;
							break;
						}
					}

					//Execute Progress Element Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND, event);
					TestUtils.waitForJobs();
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
		RunRegressElement progressJob = new RunRegressElement();
		progressJob.setElement(aElement);
		Display.getDefault().syncExec(progressJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method toggleHideDeltasFilter Toggles the Hide Deltas Filter on/off
	 */
	public void toggleHideDeltasFilter() {
		//Inner class that runs the command on the UI thread
		class RunToggleHideDeltasFilter implements Runnable {
			public void run() {
				try {
					//Execute Progress Element Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.HIDE_DELTAS_FILTER_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunToggleHideDeltasFilter hideDeltasFilterJob = new RunToggleHideDeltasFilter();
		Display.getDefault().syncExec(hideDeltasFilterJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method toggleAlphaSorter Toggles the alphabetical sorter on the Review Navigator tree On/Off
	 */
	public void toggleAlphaSorter() {
		//Inner class that runs the command on the UI thread
		class RunToggleAlphaSorter implements Runnable {
			public void run() {
				try {
					//Execute Progress Element Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.ALPHA_SORTER_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunToggleAlphaSorter alphaSorterJob = new RunToggleAlphaSorter();
		Display.getDefault().syncExec(alphaSorterJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method toggleReviewTypeSorter Toggles the review type sorter on the Review Navigator tree On/Off
	 */
	public void toggleReviewTypeSorter() {
		//Inner class that runs the command on the UI thread
		class RunToggleReviewTypeSorter implements Runnable {
			public void run() {
				try {
					//Execute Progress Element Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunToggleReviewTypeSorter reviewTypeSorterJob = new RunToggleReviewTypeSorter();
		Display.getDefault().syncExec(reviewTypeSorterJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method getActiveSorter Gets active sorter on Review Navigator tree
	 * 
	 * @return ViewerComparator
	 */
	public ViewerComparator getActiveSorter() {
		//Inner class that runs the command on the UI thread
		class RunGetActiveSorter implements Runnable {

			private ViewerComparator sorter;

			public ViewerComparator getSorter() {
				return sorter;
			}

			public void run() {
				sorter = R4EUIModelController.getNavigatorView().getTreeViewer().getComparator();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunGetActiveSorter getActiveSorterJob = new RunGetActiveSorter();
		Display.getDefault().syncExec(getActiveSorterJob);
		TestUtils.waitForJobs();
		return getActiveSorterJob.getSorter();
	}

	/**
	 * Method getCommandState Gets the state of the specified command
	 * 
	 * @param aName
	 *            - String
	 * @return boolean
	 */
	public boolean getCommandState(String aName) {
		//Inner class that runs the command on the UI thread
		class RunGetCommandState implements Runnable {

			private String name;

			private boolean state;

			public void setName(String aName) {
				name = aName;
			}

			public boolean getState() {
				return state;
			}

			public void run() {
				ICommandService service = (ICommandService) R4EUIModelController.getNavigatorView()
						.getSite()
						.getWorkbenchWindow()
						.getService(ICommandService.class);
				state = ((Boolean) service.getCommand(name)
						.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
						.getValue()).booleanValue();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunGetCommandState getCommandStateJob = new RunGetCommandState();
		getCommandStateJob.setName(aName);
		Display.getDefault().syncExec(getCommandStateJob);
		TestUtils.waitForJobs();
		return getCommandStateJob.getState();
	}

	/**
	 * Method sendQuestionNotification
	 * 
	 * @param aElement
	 * @throws CoreException
	 */
	public void sendQuestionNotification(IR4EUIModelElement aElement) throws CoreException {

		//Here we need to inject a dependency and mock the Notifications dialog
		ISendNotificationInputDialog mockNotificationDialog = mock(SendNotificationInputDialog.class);
		R4EUIDialogFactory.getInstance().setSendNotificationInputDialog(mockNotificationDialog);
		when(mockNotificationDialog.open()).thenReturn(Window.OK);
		when(mockNotificationDialog.getMessageTypeValue()).thenReturn(R4EUIConstants.MESSAGE_TYPE_QUESTION);

		//Here we need to inject a dependency and mock the Notifications connector
		NotificationsConnector mockMailConnector = mock(NotificationsConnector.class);
		R4EUIDialogFactory.getInstance().setMailConnector(mockMailConnector);

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				final Object[] arguments = invocation.getArguments();
				FEmailDetails = new EMailDetails((String) arguments[0], (String[]) arguments[1], (String) arguments[2],
						(String) arguments[3]);
				return null;
			}
		}).when(mockMailConnector).sendEmailGraphical(anyString(), (String[]) anyObject(), anyString(), anyString(),
				anyString(), (NotificationFilter) anyObject());

		//Inner class that runs the command on the UI thread
		class RunSendQuestion implements Runnable {
			private IR4EUIModelElement element;

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(element);

					//Execute New Review Group Command
					executeCommand(R4EUIConstants.SEND_EMAIL_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunSendQuestion sendQuestionJob = new RunSendQuestion();
		sendQuestionJob.setElement(aElement);
		Display.getDefault().syncExec(sendQuestionJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method getEmailDetails
	 * 
	 * @return EMailDetails
	 */
	public EMailDetails getEmailDetails() {
		return FEmailDetails;
	}

	/**
	 * Inner Class EMailDetails Holds email information
	 */
	public static class EMailDetails {
		private String fSource = null;

		private String[] fDestinations = null;

		private String fSubject = null;

		private String fBody = null;

		EMailDetails(String aSource, String[] aDestinations, String aSubject, String aBody) {
			fSource = aSource;
			fDestinations = new String[aDestinations.length];
			for (int i = 0; i < aDestinations.length; i++) {
				fDestinations[i] = aDestinations[i];
			}
			fSubject = aSubject;
			fBody = aBody;
		}

		public String getSource() {
			return fSource;
		}

		public String[] getDestinations() {
			return fDestinations;
		}

		public String getSubject() {
			return fSubject;
		}

		public String getBody() {
			return fBody;
		}
	}

	/**
	 * Import Postponed Anomalies
	 * 
	 * @param aReview
	 *            - R4EUIReviewBasic
	 */
	public void importPostponedAnomalies(R4EUIReviewBasic aReview) {

		//Inner class that runs the command on the UI thread
		class RunImportPostponedAnomalies implements Runnable {
			private R4EUIReviewBasic review;

			public void setReview(R4EUIReviewBasic aReview) {
				review = aReview;
			}

			public void run() {
				try {
					//Set focus on Navigator view and select element
					setFocusOnNavigatorElement(review);

					//Execute Import postponed anomalies Command
					executeCommand(R4EUIConstants.IMPORT_POSTPONED_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunImportPostponedAnomalies importJob = new RunImportPostponedAnomalies();
		importJob.setReview(aReview);
		Display.getDefault().syncExec(importJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Change Display
	 * 
	 * @param aReview
	 *            - R4EUIReviewBasic
	 */
	public void changeDisplay() {

		//Inner class that runs the command on the UI thread
		class RunChangeDisplay implements Runnable {

			public void run() {
				try {
					//Execute Change display Command
					executeCommand(R4EUIConstants.CHANGE_DISPLAY_COMMAND, null);
					TestUtils.waitForJobs();
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
		RunChangeDisplay changeDisplayJob = new RunChangeDisplay();
		Display.getDefault().syncExec(changeDisplayJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method verifyAnnotations Check that annotations are correctly behaving in compare and single editor
	 * 
	 * @param aElement
	 * @param aIncludeCompareEditor
	 * @param aType
	 */
	public boolean verifyAnnotations(IR4EUIModelElement[] aElements, boolean aIncludeCompareEditor, String aType) {

		//Inner class that runs the command on the UI thread
		class RunVerifyAnnotations implements Runnable {
			private IR4EUIModelElement[] elements;

			private boolean includeCompareEditor;

			private String type;

			private boolean results;

			public boolean getResults() {
				return results;
			}

			public void setElements(IR4EUIModelElement[] aElements) {
				elements = aElements;
			}

			public void setIncludeCompareEditor(boolean aIncludeCompareEditor) {
				includeCompareEditor = aIncludeCompareEditor;
			}

			public void setType(String aType) {
				type = aType;
			}

			public void run() {
				results = false;
				IEditorPart editor = null;

				if (includeCompareEditor) {
					//Open Compare editor on first element and check annotations
					setFocusOnNavigatorElement(elements[0]);
					editor = openEditorOnCurrentElement(false);
					TestUtils.waitForJobs();
					if (null == editor) {
						return;
					}
					for (IR4EUIModelElement element : elements) {
						IReviewAnnotation annotation = ((R4ECompareEditorInput) editor.getEditorInput()).getAnnotationModel()
								.findAnnotation(type, element);
						if (null == annotation) {
							return;
						}
					}
					closeEditor(editor);
					TestUtils.waitForJobs();
				}
				//Open Single editor on first element and check annotations
				setFocusOnNavigatorElement(elements[0]);
				editor = openEditorOnCurrentElement(true);
				TestUtils.waitForJobs();
				if (null == editor) {
					return;
				}
				for (IR4EUIModelElement element : elements) {
					R4EUIFileContext sourceElement;
					if (element instanceof R4EUIPostponedAnomaly) {
						sourceElement = (R4EUIPostponedFile) element.getParent();
					} else {
						//Assume postponed element
						sourceElement = (R4EUIFileContext) element.getParent().getParent();
					}
					IReviewAnnotation annotation = ((R4EAnnotationModel) ((R4ESingleAnnotationSupport) UIUtils.getAnnotationSupport(sourceElement)).getAnnotationModel()).findAnnotation(
							type, element);
					if (null == annotation) {
						return;
					}
				}
				closeEditor(editor);
				TestUtils.waitForJobs();
				results = true;
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunVerifyAnnotations verifyAnnotationsJob = new RunVerifyAnnotations();
		verifyAnnotationsJob.setElements(aElements);
		verifyAnnotationsJob.setIncludeCompareEditor(aIncludeCompareEditor);
		verifyAnnotationsJob.setType(aType);
		Display.getDefault().syncExec(verifyAnnotationsJob);
		TestUtils.waitForJobs();
		return verifyAnnotationsJob.getResults();
	}

	/**
	 * Method verifyAnnotation Check that the annotation is correctly behaving in compare and single editor
	 * 
	 * @param aElement
	 * @param aIncludeCompareEditor
	 * @param aType
	 */
	public boolean verifyAnnotation(IR4EUIModelElement aElement, boolean aIncludeCompareEditor, String aType) {

		//Inner class that runs the command on the UI thread
		class RunVerifyAnnotation implements Runnable {
			private IR4EUIModelElement element;

			private boolean includeCompareEditor;

			private String type;

			private boolean result;

			public boolean getResult() {
				return result;
			}

			public void setElement(IR4EUIModelElement aElement) {
				element = aElement;
			}

			public void setIncludeCompareEditor(boolean aIncludeCompareEditor) {
				includeCompareEditor = aIncludeCompareEditor;
			}

			public void setType(String aType) {
				type = aType;
			}

			public void run() {
				result = false;
				IEditorPart editor = null;
				IReviewAnnotation annotation = null;

				if (includeCompareEditor) {

					//Open Compare editor on first element and check annotations
					setFocusOnNavigatorElement(element);
					editor = openEditorOnCurrentElement(false);
					TestUtils.waitForJobs();
					if (null == editor) {
						return;
					}

					annotation = ((R4ECompareEditorInput) editor.getEditorInput()).getAnnotationModel().findAnnotation(
							type, element);
					if (null == annotation) {
						return;
					}
					closeEditor(editor);
					TestUtils.waitForJobs();
				} else {

					//Open Single editor on first element and check annotations
					setFocusOnNavigatorElement(element);
					editor = openEditorOnCurrentElement(true);
					TestUtils.waitForJobs();
					if (null == editor) {
						return;
					}
					R4EUIFileContext sourceElement;
					if (element instanceof R4EUIPostponedAnomaly) {
						sourceElement = (R4EUIPostponedFile) element.getParent();
					} else {
						//Assume postponed element
						sourceElement = (R4EUIFileContext) element.getParent().getParent();
					}
					annotation = ((R4EAnnotationModel) ((R4ESingleAnnotationSupport) UIUtils.getAnnotationSupport(sourceElement)).getAnnotationModel()).findAnnotation(
							type, element);
					if (null == annotation) {
						return;
					}
					closeEditor(editor);
					TestUtils.waitForJobs();
				}
				result = true;
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunVerifyAnnotation verifyAnnotationJob = new RunVerifyAnnotation();
		verifyAnnotationJob.setElement(aElement);
		verifyAnnotationJob.setIncludeCompareEditor(aIncludeCompareEditor);
		verifyAnnotationJob.setType(aType);
		Display.getDefault().syncExec(verifyAnnotationJob);
		TestUtils.waitForJobs();
		return verifyAnnotationJob.getResult();
	}
}
