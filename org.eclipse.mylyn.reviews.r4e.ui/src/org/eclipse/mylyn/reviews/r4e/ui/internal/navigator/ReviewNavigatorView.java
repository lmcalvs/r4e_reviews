// $codepro.audit.able com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the Review Navigator View.  It is used to quickly browse the current
 * R4E elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.EditorProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.R4EPreferencePage;
import org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.DateComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.LinePositionComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorView extends ViewPart implements IMenuListener, IPreferenceChangeListener,
		ITabbedPropertySheetPageContributor, IPropertyListener {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fReviewTreeViewer.
	 */
	private ReviewNavigatorTreeViewer fReviewTreeViewer = null;

	/**
	 * Field fContextMenu.
	 */
	private Menu fContextMenu = null;

	/**
	 * Field fActionSet.
	 */
	private ActionGroup fActionSet = null;

	/**
	 * Field fEditorLinked - this is set if the editor is linked to the review Navigator view
	 */
	private boolean fEditorLinked;

	/**
	 * Field fPropertiesLinked - this is set if the R4E properties view is linked to the review Navigator view
	 */
	private boolean fPropertiesLinked;

	/**
	 * Field fPropertySheetPage - the associated Tabbed Properties view
	 */
	protected TabbedPropertySheetPage fPropertySheetPage;

	/**
	 * Field fPartListener
	 */
	private IPartListener fPartListener = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewNavigatorView.
	 */
	public ReviewNavigatorView() {
		R4EUIModelController.setNavigatorView(this);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method isVisible.
	 * 
	 * @return - true is the view is visible, false otherwise
	 */
	public boolean isVisible() {
		final IWorkbenchPage page = R4EUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (null != page) {
			return page.isPartVisible(this);
		}
		return false;
	}

	/**
	 * Method setEditorLinked.
	 * 
	 * @param aEditorLinked
	 *            boolean - true is the editor is linked, false otherwise
	 */
	public void setEditorLinked(boolean aEditorLinked) {
		fEditorLinked = aEditorLinked;
	}

	/**
	 * Method isEditorLinked.
	 * 
	 * @return - true is the editor is linked, false otherwise
	 */
	public boolean isEditorLinked() {
		return fEditorLinked;
	}

	/**
	 * Method setPropertiesLinked.
	 * 
	 * @param aPropertiesLinked
	 *            boolean - true is the R4E properties view is linked, false otherwise
	 */
	public void setPropertiesLinked(boolean aPropertiesLinked) {
		fPropertiesLinked = aPropertiesLinked;
	}

	/**
	 * Method isPropertiesLinked.
	 * 
	 * @return - true is the R4E properties view is linked, false otherwise
	 */
	public boolean isPropertiesLinked() {
		return fPropertiesLinked;
	}

	/**
	 * Method isDefaultDisplay.
	 * 
	 * @return boolean
	 */
	public boolean isDefaultDisplay() {
		if (fReviewTreeViewer != null) {
			return fReviewTreeViewer.isDefaultDisplay();
		}
		return true;

	}

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		if (null != fPartListener) {
			getSite().getPage().removePartListener(fPartListener);
		}

		if (null != fContextMenu && !fContextMenu.isDisposed()) {
			fContextMenu.dispose();
		}
		if (null != fActionSet) {
			fActionSet.dispose();
		}
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(R4EUIConstants.R4E_TEMP_PROJECT);

		if (null != fPropertySheetPage) {
			fPropertySheetPage.dispose();
		}

		try {
			if (project.exists()) {
				project.delete(true, null);
			}
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault().logWarning("Exception: " + e.toString(), e); //$NON-NLS-1$
		} finally {
			R4EUIModelController.setActiveReview(null);
			super.dispose();
		}
	}

	/**
	 * Method createPartControl.
	 * 
	 * @param parent
	 *            Composite
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		R4EUIPlugin.Ftracer.traceInfo("Build Review Navigator view"); //$NON-NLS-1$

		//Set tree viewer
		fReviewTreeViewer = new ReviewNavigatorTreeViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		fReviewTreeViewer.setUseHashlookup(true);
		fReviewTreeViewer.getTree().setHeaderVisible(true);
		ColumnViewerToolTipSupport.enableFor(fReviewTreeViewer);
		fReviewTreeViewer.setContentProvider(new ReviewNavigatorContentProvider());

		fReviewTreeViewer.setComparator(new LinePositionComparator());
		fReviewTreeViewer.setInput(getInitalInput());
		fReviewTreeViewer.setDefaultInput(fReviewTreeViewer.getInput());
		fReviewTreeViewer.setSorter(new LineViewerSorter());

		//Set Context menus
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		fContextMenu = menuMgr.createContextMenu(fReviewTreeViewer.getTree());
		fReviewTreeViewer.getTree().setMenu(fContextMenu);

		// Register viewer with site. This must be done before making the actions.
		final IWorkbenchPartSite site = getSite();
		site.registerContextMenu(menuMgr, fReviewTreeViewer);
		site.setSelectionProvider(fReviewTreeViewer);

		//Set Actions in Action group
		makeActions(); // call before registering for selection changes

		//Tie UI listeners
		hookListeners();
		final IEclipsePreferences node = new InstanceScope().getNode(R4EUIPlugin.PLUGIN_ID);
		node.addPreferenceChangeListener(this);

		//Set default Tree representation
		fReviewTreeViewer.setViewTree();

		//Apply default filters
		final IEvaluationService evService = (IEvaluationService) getSite().getWorkbenchWindow().getService(
				IEvaluationService.class);
		evService.requestEvaluation(R4EUIConstants.DEFAULT_DISPLAY_COMMAND);
		applyDefaultFilters();
		getTreeViewer().setComparator(null);

		//Make sure that the User Id in preferences is set to lower case
		final IPreferenceStore store = R4EUIPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.P_USER_ID, store.getString(PreferenceConstants.P_USER_ID).toLowerCase());

		//Make sure the Participants Lists map is populated
		R4EPreferencePage.populateParticipantListMap();

		//Make sure Navigator View is enabled at startup
		R4EUIModelController.setJobInProgress(false);
	}

	/**
	 * Method resetInput.
	 */
	public void resetInput() {
		IR4EUIModelElement[] groups = R4EUIModelController.getRootElement().getChildren();
		final List<String> openGroupNames = new ArrayList<String>();
		for (IR4EUIModelElement group : groups) {
			if (group.isOpen()) {
				group.close();
				openGroupNames.add(group.getName());
			}
		}

		//Only set input if the Tree widget is present
		if (!fReviewTreeViewer.getTree().isDisposed()) {
			fReviewTreeViewer.setInput(getInitalInput());
		} else {
			return;
		}

		//Restore previously open groups
		groups = R4EUIModelController.getRootElement().getChildren();
		for (String groupName : openGroupNames) {
			for (IR4EUIModelElement group : groups) {
				if (group.getName().equals(groupName)) {
					try {
						group.open();
					} catch (ResourceHandlingException e) {
						UIUtils.displayResourceErrorDialog(e);
					} catch (FileNotFoundException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
					} catch (CompatibilityException e) {
						UIUtils.displayCompatibilityErrorDialog(e);
					}
					break;
				}
			}
		}
	}

	/**
	 * Method setFocus.
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		fReviewTreeViewer.getControl().setFocus();
	}

	/**
	 * Method menuAboutToShow.
	 * 
	 * @param aMenuManager
	 *            IMenuManager
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(IMenuManager)
	 */
	public void menuAboutToShow(IMenuManager aMenuManager) {
		fActionSet.setContext(new ActionContext(fReviewTreeViewer.getSelection()));
		fActionSet.fillContextMenu(aMenuManager);
		fActionSet.setContext(null);
	}

	/**
	 * Get the view's jface tree viewer
	 * 
	 * @return the tree viewer
	 */
	public TreeViewer getTreeViewer() {
		return fReviewTreeViewer;
	}

	/**
	 * Get the initial input from the R4E model and populate the UI model with it
	 * 
	 * @return the root element of the UI model
	 */
	public IR4EUIModelElement getInitalInput() {
		R4EUIModelController.loadModel();
		final IR4EUIModelElement rootTreeNode = R4EUIModelController.getRootElement();
		rootTreeNode.getChildren();
		return rootTreeNode;
	}

	/**
	 * Add the listeners to the tree viewer
	 */
	protected void hookListeners() {
		fReviewTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (isPropertiesLinked()) {
						showProperties();
					}

					if (isEditorLinked()) {
						IR4EUIModelElement element = (IR4EUIModelElement) selection.getFirstElement();

						//Find the parent FileContextElement
						while (null != element && !(element instanceof R4EUIFileContext)) {
							element = element.getParent();
						}
						if (null == element) {
							return;
						}

						//Get file reference
						final R4EFileVersion fileVersion = ((R4EUIFileContext) element).getFileContext().getTarget();
						if (null == fileVersion) {
							return;
						}

						final IResource resource = fileVersion.getResource();
						if (resource instanceof IFile) {

							//Get open editors
							final IEditorReference[] editors = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow()
									.getActivePage()
									.getEditorReferences();

							for (IEditorReference editor : editors) {
								try {
									final IEditorInput input = editor.getEditorInput();
									if (input instanceof IFileEditorInput) {
										if (((IFileEditorInput) input).getFile().equals(resource)) {
											PlatformUI.getWorkbench()
													.getActiveWorkbenchWindow()
													.getActivePage()
													.bringToTop(editor.getPart(true));
											break;
										}
									}
								} catch (PartInitException e) {
									continue; //ignore
								}
							}
						}
					}
				}
			}
		});

		fReviewTreeViewer.addDoubleClickListener(new IDoubleClickListener() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			public void doubleClick(DoubleClickEvent event) {
				R4EUIPlugin.Ftracer.traceInfo("Double-click event received"); //$NON-NLS-1$

				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				final IR4EUIModelElement element = (IR4EUIModelElement) selection.getFirstElement();
				if (element instanceof R4EUIReviewBasic || element instanceof R4EUIReviewGroup
						|| element instanceof R4EUIRuleSet) {
					try {
						//open or close review if ReviewElement is double-clicked
						if (element.isEnabled()) {
							if (element.isOpen()) {
								((ReviewNavigatorActionGroup) getActionSet()).closeElementCommand();
							} else {
								((ReviewNavigatorActionGroup) getActionSet()).openElementCommand();
							}
						}
					} catch (ExecutionException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
					} catch (NotDefinedException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
					} catch (NotEnabledException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
					} catch (NotHandledException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
					}
				} else if (isEditorLinked()) {
					EditorProxy.openEditor(getSite().getPage(), selection, false);
				}
			}
		});

		getSite().getPage().addPartListener(fPartListener = new IPartListener() {

			public void partOpened(IWorkbenchPart part) { // $codepro.audit.disable emptyMethod
				// No implementation
			}

			public void partDeactivated(IWorkbenchPart part) { // $codepro.audit.disable emptyMethod
				// No implementation	
			}

			public void partClosed(IWorkbenchPart part) { // $codepro.audit.disable emptyMethod
				//Remove Properties View references when it is closed
				if (part instanceof PropertySheet) {
					if (((PropertySheet) part).getCurrentPage() == fPropertySheetPage) {
						fPropertySheetPage = null;
					}
				}
			}

			public void partBroughtToTop(IWorkbenchPart part) { // $codepro.audit.disable emptyMethod
				// No implementation	
			}

			@SuppressWarnings("synthetic-access")
			public void partActivated(IWorkbenchPart part) {
				if (isEditorLinked() && part instanceof IEditorPart) {
					//Check if the part activated is an editor, if so select corresponding
					//review navigator file context if applicable
					final IEditorInput input = ((IEditorPart) part).getEditorInput();
					if (input instanceof IFileEditorInput) {
						final IFile editorFile = ((IFileEditorInput) input).getFile();
						final IR4EUIModelElement rootElement = R4EUIModelController.getRootElement();

						for (IR4EUIModelElement group : rootElement.getChildren()) {
							for (IR4EUIModelElement review : group.getChildren()) {
								for (IR4EUIModelElement item : review.getChildren()) {
									for (final IR4EUIModelElement navigatorFile : item.getChildren()) {
										if (navigatorFile instanceof R4EUIFileContext) {
											R4EFileVersion version = ((R4EUIFileContext) navigatorFile).getFileContext()
													.getTarget();
											if (null != version && null != (IFile) version.getResource()
													&& ((IFile) version.getResource()).equals(editorFile)) {

												//We found the parent fileContext, now check if the selection is already within this branch
												IR4EUIModelElement selectedElement = (IR4EUIModelElement) ((IStructuredSelection) fReviewTreeViewer.getSelection()).getFirstElement();
												IR4EUIModelElement fileContextElement = selectedElement;
												while (null != fileContextElement
														&& !(fileContextElement instanceof R4EUIFileContext)) {
													fileContextElement = fileContextElement.getParent();
												}
												if (null != fileContextElement) {
													if (fileContextElement.equals(navigatorFile)) {
														return; //Correct selection already set
													}
												}
												//selection to the file context corresponding to the editor input
												Display.getDefault().asyncExec(new Runnable() {
													public void run() {
														while (Display.getDefault().readAndDispatch()) {
															//wait for events to finish before continue
														}

														fReviewTreeViewer.setSelection(new StructuredSelection(
																navigatorFile), true);
													}
												});
												return;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		});
	}

	/**
	 * Method showProperties.
	 * 
	 * @return IViewPart
	 */
	public IViewPart showProperties() {
		//Force show properties view
		IViewPart propertiesView = null;
		try {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			propertiesView = page.findView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
			if (!page.isPartVisible(propertiesView)) {
				//Make sure that the properties view references are updated properly before showing it
				if (null != fPropertySheetPage) {
					fPropertySheetPage = null;
				}
				getPropertySheetPage();
				propertiesView = page.showView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
			}
		} catch (PartInitException e) {
			R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault().logWarning("Exception: " + e.toString(), e); //$NON-NLS-1$
			// Do nothing
		}
		return propertiesView;
	}

	/**
	 * Create the Action Group associated with the view
	 */
	private void makeActions() {
		fActionSet = new ReviewNavigatorActionGroup(this);
		final IActionBars actionBars = getViewSite().getActionBars();
		fActionSet.fillActionBars(actionBars);
	}

	/**
	 * Gets the Action Group
	 * 
	 * @return the action group
	 */
	public ActionGroup getActionSet() {
		return fActionSet;
	}

	/**
	 * Method preferenceChange.
	 * 
	 * @param event
	 *            PreferenceChangeEvent
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences$IPreferenceChangeListener#preferenceChange(PreferenceChangeEvent)
	 */
	public void preferenceChange(org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent event) {

		if (event.getKey().equals(PreferenceConstants.P_USER_ID)) {
			//Reset reviewer to current ID
			final IPreferenceStore store = R4EUIPlugin.getDefault().getPreferenceStore();
			R4EUIModelController.setReviewer(store.getString(PreferenceConstants.P_USER_ID));
			if (isVisible()) {
				getTreeViewer().refresh();
			}
		} else if (event.getKey().equals(PreferenceConstants.P_GROUP_FILE_PATH)) {
			//Check what is currently loaded vs. what is in the preferences.  Adjust input accordingly
			final List<R4EUIReviewGroup> groupsLoaded = Arrays.asList(R4EUIModelController.getRootElement().getGroups());
			final List<String> groupsPreferencesPaths = UIUtils.parseStringList((String) event.getNewValue());

			//Convert the loaded groups array to array of File Paths
			final List<String> groupsLoadedPaths = new ArrayList<String>();
			IPath path;
			for (R4EUIReviewGroup group : groupsLoaded) {
				path = new Path(group.getGroupFile());
				groupsLoadedPaths.add(path.toOSString());
			}

			//Groups that are in preferences, but not loaded should be loaded
			final List<String> result = new ArrayList<String>();
			result.addAll(groupsPreferencesPaths);
			result.removeAll(groupsLoadedPaths);
			final List<String> groupsToLoad = new ArrayList<String>();
			for (String groupToLoad : result) {
				groupsToLoad.add(groupToLoad);
			}

			//Groups that are loaded, but not in preferences should be removed
			result.clear();
			result.addAll(groupsLoadedPaths);
			result.removeAll(groupsPreferencesPaths);
			final List<IR4EUIModelElement> groupsToRemove = new ArrayList<IR4EUIModelElement>();
			for (R4EUIReviewGroup group : groupsLoaded) {
				for (String groupPath : result) {
					path = new Path(group.getGroupFile());
					if (groupPath.equals(path.toOSString())) {
						groupsToRemove.add(group);
					}
				}
			}

			//Adjust loaded groups
			for (IR4EUIModelElement groupToRemove : groupsToRemove) {
				R4EUIModelController.getRootElement().removeChildrenFromUI(groupToRemove);
			}
			if (groupsToLoad.size() > 0) {
				List<String> loadErrors = new ArrayList<String>();
				loadErrors = R4EUIModelController.loadReviewGroups(groupsToLoad, loadErrors);

				//Report elements that failed to load
				if (loadErrors.size() > 0) {
					UIUtils.displayFailedLoadDialog(loadErrors);
				}
			}
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					//test if the tree is disposed first
					if (!fReviewTreeViewer.getControl().isDisposed()) {
						fReviewTreeViewer.refresh();
					} else {
						R4EUIPlugin.Ftracer.traceWarning("Exception: " + "preferenceChange() R4E Tree viewer is DISPOSED , Group path "); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
			});
		} else if (event.getKey().equals(PreferenceConstants.P_RULE_SET_FILE_PATH)) {
			//Check what is currently loaded vs. what is in the preferences.  Adjust input accordingly
			final List<R4EUIRuleSet> ruleSetsLoaded = R4EUIModelController.getRootElement().getRuleSets();
			final List<String> ruleSetsPreferencesPaths = UIUtils.parseStringList((String) event.getNewValue());

			//Convert the loaded rule set array to array of File Paths
			final List<String> ruleSetsLoadedPaths = new ArrayList<String>();
			IPath path;
			for (R4EUIRuleSet ruleSet : ruleSetsLoaded) {
				path = new Path(ruleSet.getRuleSetFile());
				ruleSetsLoadedPaths.add(path.toOSString());
			}

			//Rule Sets that are in preferences, but not loaded should be loaded
			final List<String> result = new ArrayList<String>();
			result.addAll(ruleSetsPreferencesPaths);
			result.removeAll(ruleSetsLoadedPaths);
			final List<String> ruleSetsToLoad = new ArrayList<String>();
			for (String ruleSetToLoad : result) {
				ruleSetsToLoad.add(ruleSetToLoad);
			}

			//Rule Sets that are loaded, but not in preferences should be removed
			result.clear();
			result.addAll(ruleSetsLoadedPaths);
			result.removeAll(ruleSetsPreferencesPaths);
			final List<IR4EUIModelElement> ruleSetsToRemove = new ArrayList<IR4EUIModelElement>();
			for (IR4EUIModelElement ruleSet : ruleSetsLoaded) {
				for (String ruleSetPath : result) {
					path = new Path(((R4EUIRuleSet) ruleSet).getRuleSetFile());
					if (ruleSetPath.equals(path.toOSString())) {
						ruleSetsToRemove.add(ruleSet);
					}
				}
			}

			//Adjust loaded groups
			for (IR4EUIModelElement ruleSetToRemove : ruleSetsToRemove) {
				R4EUIModelController.getRootElement().removeChildrenFromUI(ruleSetToRemove);
			}
			if (ruleSetsToLoad.size() > 0) {
				List<String> loadErrors = new ArrayList<String>();
				loadErrors = R4EUIModelController.loadRuleSets(ruleSetsToLoad, loadErrors);

				//Report elements that failed to load
				if (loadErrors.size() > 0) {
					UIUtils.displayFailedLoadDialog(loadErrors);
				}
			}
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					//test if the tree is disposed first
					if (!fReviewTreeViewer.getControl().isDisposed()) {
						fReviewTreeViewer.refresh();
					} else {
						R4EUIPlugin.Ftracer.traceWarning("Exception: " + "preferenceChange() R4E Tree viewer is DISPOSED , RULES SET PATH "); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
			});

		} else if (event.getKey().equals(PreferenceConstants.P_SHOW_DISABLED)) {
			resetInput(); //TODO This needs to be changed to not reload the model all the time
		}

	}

	/**
	 * Method applyDefaultFilters.
	 */
	public void applyDefaultFilters() {
		final IPreferenceStore store = R4EUIPlugin.getDefault().getPreferenceStore();
		try {
			((ReviewNavigatorActionGroup) fActionSet).resetAllFilterActions();
			((ReviewNavigatorActionGroup) fActionSet).runReviewsCompletedFilterCommand(store.getBoolean(PreferenceConstants.P_REVIEWS_COMPLETED_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewsOnlyFilterCommand(store.getBoolean(PreferenceConstants.P_REVIEWS_ONLY_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewsMyFilterCommand(store.getBoolean(PreferenceConstants.P_REVIEWS_MY_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewsParticipantFilterCommand(store.getString(PreferenceConstants.P_PARTICIPANT_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runAssignedMyFilterCommand(store.getBoolean(PreferenceConstants.P_ASSIGN_MY_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runAssignedParticipantFilterCommand(store.getString(PreferenceConstants.P_ASSIGN_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runUnassignedFilterCommand(store.getBoolean(PreferenceConstants.P_UNASSIGN_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runAnomaliesFilterCommand(store.getBoolean(PreferenceConstants.P_ANOMALIES_ALL_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runAnomaliesMyFilterCommand(store.getBoolean(PreferenceConstants.P_ANOMALIES_MY_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewElemsFilterCommand(store.getBoolean(PreferenceConstants.P_REVIEWED_ITEMS_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runHideRuleSetsFilterCommand(store.getBoolean(PreferenceConstants.P_HIDE_RULE_SETS_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runHideDeltasFilterCommand(store.getBoolean(PreferenceConstants.P_HIDE_DELTAS_FILTER));
			getTreeViewer().setComparator(new DateComparator());
		} catch (ExecutionException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
		} catch (NotEnabledException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
		} catch (NotHandledException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
		}
	}

	/**
	 * Method getAdapter.
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (adapter.equals(IPropertySheetPage.class)) {
			return getPropertySheetPage();
		}
		return super.getAdapter(adapter);
	}

	/**
	 * Method getPropertySheetPage.
	 * 
	 * @return TabbedPropertySheetPage
	 */
	public TabbedPropertySheetPage getPropertySheetPage() {
		if (null == fPropertySheetPage || null == fPropertySheetPage.getControl()
				|| null == fPropertySheetPage.getCurrentTab()) {
			fPropertySheetPage = new TabbedPropertySheetPage(this);
		}
		return fPropertySheetPage;
	}

	/**
	 * Method getContributorId.
	 * 
	 * @return String
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 */
	public String getContributorId() {
		return getSite().getId();
	}

	/**
	 * Method propertyChanged.
	 * 
	 * @param source
	 *            Object
	 * @param propId
	 *            int
	 * @see org.eclipse.ui.IPropertyListener#propertyChanged(Object, int)
	 */
	public void propertyChanged(Object source, int propId) {
		((ReviewNavigatorActionGroup) fActionSet).dialogOpenNotify();
	}

	/**
	 * Method updateView. Set focus to current view/element
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 * @param aExpandLevel
	 *            int
	 */
	public void updateView(final IR4EUIModelElement aElement, final int aExpandLevel) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				//Activate View
				getSite().getPage().activate(R4EUIModelController.getNavigatorView());

				//Set selection to current element
				fReviewTreeViewer.expandToLevel(aElement, aExpandLevel);
				fReviewTreeViewer.refresh(); //Make sure tree is refreshed

				//NOTE:  This is a trick to get around TabbedPropertySheetPage problems, we unselect the current selection and
				//		 then immediately select it back.  This makes sure the Properties view is 
				//		 refreshed properly without causing Exceptions
				fReviewTreeViewer.setSelection(null);
				final ISelection newSelection = new StructuredSelection(aElement);
				fReviewTreeViewer.setSelection(newSelection, true);
			}
		});
	}

	/**
	 * Method refreshItems. Refresh view elements states
	 */
	public void refreshItems() {
		//Toolbar items
		IContributionItem[] items = R4EUIModelController.getNavigatorView()
				.getViewSite()
				.getActionBars()
				.getToolBarManager()
				.getItems();
		for (IContributionItem item : items) {
			item.update();
		}

		//Menu items
		MenuManager menu = (MenuManager) R4EUIModelController.getNavigatorView()
				.getViewSite()
				.getActionBars()
				.getMenuManager();
		menu.updateAll(true);
	}
}
