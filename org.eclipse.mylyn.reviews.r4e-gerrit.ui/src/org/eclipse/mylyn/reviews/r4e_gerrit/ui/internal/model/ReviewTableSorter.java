/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the R4E-Gerrit UI view column sorter.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the view sorter
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model.ReviewTableData.ReviewTableListItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeItem;



/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class ReviewTableSorter extends ViewerSorter {
	private int columnIndex = 0;
	private static UIReviewTable fReviewTable = null;

	public ReviewTableSorter(int columnIndex) {
		super();
		this.columnIndex = columnIndex;
	}

	/*
	 * public static void reverseOrder() { order *= -1; }
	 */

	public int compare(Viewer aViewer, Object aE1, Object aE2) {

		if (aViewer instanceof TableViewer) {

			TableViewer tv = (TableViewer) aViewer;
			tv.getTable().setSortColumn(tv.getTable().getColumn(columnIndex));
			int idx1 = -1, idx2 = -1;
			for (int i = 0; i < tv.getTable().getItemCount(); i++) {
				Object obj = tv.getElementAt(i);
				if (obj.equals(aE1)) {
					idx1 = i;
				} else if (obj.equals(aE2)) {
					idx2 = i;
				}
				if (idx1 > 0 && idx2 > 0) {
					break;
				}
			}
			int order = 0;
			if (idx1 > -1 && idx2 > -1) {
				String str1 = tv.getTable().getItems()[idx1]
						.getText(this.columnIndex);
				String str2 = tv.getTable().getItems()[idx2]
						.getText(this.columnIndex);
				order = str1.compareTo(str2);
				if (tv.getTable().getSortDirection() != SWT.UP) {
					order *= -1;
				}
			}
			return order;
		} else if (aViewer instanceof TreeViewer) {

			TreeViewer tv = (TreeViewer) aViewer;
			tv.getTree().setSortColumn(tv.getTree().getColumn(columnIndex));
			int idx1 = -1, idx2 = -1;

			Object[] listObj = tv.getTree().getItems();

			for (int i = 0; i < listObj.length; i++) {
				Object obj = null;
				if (listObj[i] instanceof TreeItem) {

					obj = ((TreeItem) listObj[i]).getData();
					((TreeItem) listObj[i]).setExpanded(true);

				} else {
				}

				if (obj != null) {
					if (obj.equals(aE1)) {
						idx1 = i;
					} else if (obj.equals(aE2)) {
						idx2 = i;
					}
					if (idx1 > 0 && idx2 > 0) {
						break;
					}
				}
			}
			
			int order = 0;
			if (idx1 > -1 && idx2 > -1) {
				String str1 = tv.getTree().getItems()[idx1]
						.getText(this.columnIndex);
				String str2 = tv.getTree().getItems()[idx2]
						.getText(this.columnIndex);
				order = str1.compareTo(str2);
				if (tv.getTree().getSortDirection() != SWT.UP) {
					order *= -1;
				}
			}
			return order;
		}
		return 0;
	}

	/**
	 * table is bind to Sorter.
	 * 
	 * @param aTableViewer
	 */
	public static void bind(final TableViewer aTableViewer) {
		for (int i = 0; i < aTableViewer.getTable().getColumnCount(); i++) {
			final int columnNum = i;
			TableColumn column = aTableViewer.getTable().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					ReviewTableSorter sorter = new ReviewTableSorter(
							columnNum);
					Table table = aTableViewer.getTable();
					if (table.getSortDirection() == SWT.UP) {
						table.setSortDirection(SWT.DOWN);
					} else if (table.getSortDirection() == SWT.DOWN) {
						table.setSortDirection(SWT.UP);
					} else {
						table.setSortDirection(SWT.UP);
					}
					aTableViewer.setComparator(sorter);
				}
			});
		}
	}
	
}
