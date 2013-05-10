/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the R4E-Gerrit UI view.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4egerrit.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model.ReviewTableLabelProvider;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model.UIReviewTable;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;



/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */

/**
 * This class initiate a new workbench view. The view
 * shows data obtained from R4E-Gerrit model. The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. 
 */

public class R4EGerritTableView extends ViewPart {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.mylyn.reviews.r4egerrit.ui.views.R4EGerritTableView";

	// Labels for the Search 
	private final String SEARCH_LABEL = "Search for:";
	private final String SEARCH_BTN = "Search";

	private final int SEARCH_WIDTH = 150;
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	//
	private Label 	fSearchForLabel;
	private Label	fSearchResulLabel;

	private Text	fSearchRequestText;
	private Button	fSearchRequestBtn;
	private TableViewer fViewer;
	
	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" ,"Jacques", "Bouthillier"};
		}
	}

//	class ViewLabelProvider extends LabelProvider implements
//			ITableLabelProvider {
//		public String getColumnText(Object obj, int index) {
//			R4EGerritPlugin.Ftracer.traceWarning("getColumnText column: " + index );
//			return getText(obj);
//		}
//
//		public Image getColumnImage(Object obj, int index) {
//			R4EGerritPlugin.Ftracer.traceWarning("getColumnImage column: " + index );
//			return getImage(obj);
//		}
//
//		public Image getImage(Object obj) {
//			R4EGerritPlugin.Ftracer.traceWarning("getImage column: " + obj.toString() );
//			return PlatformUI.getWorkbench().getSharedImages()
//					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
//		}
//	}

	class NameSorter extends ViewerSorter {
	}

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public R4EGerritTableView() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method createPartControl. This is a callback that will allow us to create
	 * the viewer and initialize it.
	 * 
	 * @param parent
	 *            Composite
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(Composite)
	 */
	public void createPartControl(Composite aParent) {

		createSearchSection(aParent);
		UIReviewTable reviewTable = new UIReviewTable();
		fViewer = reviewTable.createTableViewerSection(aParent);
		
			
		// Setup the view layout
		createLayout(aParent);
		
		
		fViewer.setContentProvider(new ViewContentProvider());
//		fViewer.setLabelProvider(new ViewLabelProvider());
		fViewer.setLabelProvider(new ReviewTableLabelProvider());
		
		fViewer.setSorter(new NameSorter());
		fViewer.setInput(getViewSite());
//
//		// Create the help context id for the viewer's control
//		PlatformUI
//				.getWorkbench()
//				.getHelpSystem()
//				.setHelp(viewer.getControl(),
//						"org.eclipse.mylyn.reviews.r4e-gerrit.ui.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void createLayout(Composite aParent) {

		//Add a listener when the view is resized
		GridLayout layout = new GridLayout();
		layout.numColumns = 1 ;
		layout.makeColumnsEqualWidth = false;
		
		aParent.setLayout(layout);
		
	}

	private void createSearchSection(Composite aParent) {
		
		final Group formGroup =  new Group(aParent, SWT.SHADOW_ETCHED_IN | SWT.H_SCROLL);
		GridData gribDataGroup = new GridData(GridData.FILL_HORIZONTAL);
//		gribDataGroup.minimumWidth = 260;
		formGroup.setLayoutData(gribDataGroup);
		
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.verticalSpacing = 1;
		layout.makeColumnsEqualWidth = false;
		
		formGroup.setLayout(layout);


		//Left side of the Group
		//Create a form to maintain the search data
		Composite leftSearchForm = UIUtils.createsGeneralComposite(formGroup, SWT.NONE);

		GridData gribDataViewer = new GridData(GridData.FILL_HORIZONTAL);
		leftSearchForm.setLayoutData(gribDataViewer);

		GridLayout leftLyoutForm = new GridLayout();
		leftLyoutForm.numColumns = 3;
		leftLyoutForm.marginHeight = 0;
		leftLyoutForm.makeColumnsEqualWidth = false;
		
		leftSearchForm.setLayout(leftLyoutForm);
		
		// Label for SEARCH for
		fSearchForLabel = new Label(leftSearchForm, SWT.NONE);
		fSearchForLabel.setText(SEARCH_LABEL);
		
		// Label for the SEARH request
		fSearchResulLabel = new Label(leftSearchForm, SWT.NONE);
		fSearchResulLabel.setLayoutData(new GridData(SEARCH_WIDTH, SWT.DEFAULT));

		
		//Right side of the Group

		Composite rightSsearchForm = UIUtils.createsGeneralComposite(formGroup, SWT.NONE);
		GridData gribDataViewer2 = new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END);
		rightSsearchForm.setLayoutData(gribDataViewer2);
		GridLayout rightLayoutForm = new GridLayout();
		rightLayoutForm.numColumns = 2;
		rightLayoutForm.marginHeight = 0;
		rightLayoutForm.makeColumnsEqualWidth = false;
		
		rightSsearchForm.setLayout(rightLayoutForm);

		//Create a SEARCH text data entry
		fSearchRequestText = new Text (rightSsearchForm, SWT.BORDER);
		fSearchRequestText.setLayoutData(new GridData(SEARCH_WIDTH, SWT.DEFAULT));
	
		//Create a SEARCH button 
		fSearchRequestBtn = new Button (rightSsearchForm, SWT.NONE);
		fSearchRequestBtn.setText(SEARCH_BTN);

	}


	/**********************************************************/
	/*                                                        */
	/* DEFAULT METHODS, EITHER MOVE OR DELETED EVENTUALLY */
	/*                                                        */
	/**********************************************************/
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				R4EGerritTableView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(fViewer.getControl());
		fViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, fViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		// manager.add(action1);
		// manager.add(new Separator());
		// manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		// manager.add(action1);
		// manager.add(action2);
		// manager.add(action1);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = fViewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(fViewer.getControl().getShell(),
				"R4E-Gerrit table", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		fViewer.getControl().setFocus();
	}
	

}