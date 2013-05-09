/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the review table view.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the table view
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model;

import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class UIReviewTable {
	

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	//Definition of the review table list {name, width of the column}
	private final String[] ID = 		{"ID", "30"};
	private final String[] SUBJECT = 	{"Subject", "200"};
	private final String[] OWNER =		{"Owner", "100"};
	private final String[] PROJECT = 	{"Project", "100"};
	private final String[] BRANCH = 	{"Branch", "100"};
	private final String[] UPDATED = 	{"Updated", "100"};
	private final String[] CR = 		{"CR", "30"};
	private final String[] IC = 		{"IC", "30"};
	private final String[] VERIFY = 	{"V","30"};

	private final String TITLES[][] = { ID, SUBJECT, OWNER, PROJECT , BRANCH, UPDATED, CR, IC,VERIFY};
	
	private TableViewer aViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor.
	 */
	public UIReviewTable() {
		
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public TableViewer createTableViewerSection(Composite aParent) {
		//Create a form to maintain the search data
		Composite viewerForm =  UIUtils.createsGeneralComposite(aParent, SWT.BORDER | SWT.SHADOW_ETCHED_IN);

		GridData gribDataViewer = new GridData(GridData.FILL_BOTH);
		viewerForm.setLayoutData(gribDataViewer);
	
		//Add a listener when the view is resized
		GridLayout layout = new GridLayout();
		layout.numColumns = TITLES.length;
		layout.makeColumnsEqualWidth = false;
		
		viewerForm.setLayout(layout);
		

		aViewer = new TableViewer(viewerForm, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		
		aViewer = buildAndLayoutTable(aViewer);
		
//		aViewer.setContentProvider(new ViewContentProvider());
//		aViewer.setLabelProvider(new ViewLabelProvider());
//		aViewer.setSorter(new NameSorter());
//		aViewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(aViewer.getControl(),
						"org.eclipse.mylyn.reviews.r4e-gerrit.ui.viewer");
//		getSite().setSelectionProvider(viewer);
		return aViewer;

	}
	
	
	/**
	 * Create each column for the List of Reviews
	 * 
	 * @param aParent
	 * @param aViewer
	 */
	private TableViewer buildAndLayoutTable(final TableViewer aViewer) {
		final Table table = aViewer.getTable();

		int size = TITLES.length;
		for (int index = 0; index < size; index++) {
			R4EGerritPlugin.Ftracer.traceInfo("index [ " + index + " ] :[0] : "
					+ TITLES[index][0] + " \tvalue 1: " + TITLES[index][1]);
			int width = Integer.valueOf(TITLES[index][1]);
			TableViewerColumn col = createTableViewerColumn(TITLES[index][0],
					width);
			GridData gribData = new GridData(GridData.FILL_BOTH);
			gribData.minimumWidth = width;
			col.getColumn().getParent().setLayoutData(gribData);

		}
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		table.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent e) {
				Point tableSize = table.getSize();
				Point parentSize = table.getParent().getSize();
//				R4EGerritPlugin.Ftracer.traceInfo("Table size: " + tableSize + "\tparent size: " +
//				parentSize);
				//Adjust the width  according to its parent
				table.setSize(parentSize.x - 10, tableSize.y);
				
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
				
			}
		});

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		return aViewer;
	}

	/**
	 * Create each column 
	 * @param aTitle
	 * @param aWidth
	 * @return TableViewerColumn
	 */
	private TableViewerColumn createTableViewerColumn(String aTitle, int aWidth) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(aViewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(aTitle);
		column.setWidth(aWidth);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}

}
