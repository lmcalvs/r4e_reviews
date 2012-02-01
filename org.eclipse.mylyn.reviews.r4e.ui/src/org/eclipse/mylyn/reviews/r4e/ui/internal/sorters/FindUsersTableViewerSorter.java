/*******************************************************************************
 * Copyright (c) 2008-2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class extends the default viewer sorter to sort
 * the items in TableViewers and TreeViewers
 * 
 * Contributors:
 *   Jacques Bouthilier - Created for internal Ericsson R4E
 *   Sebastien Dubois - Updated for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.sorters;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author lmcbout
 * @version $Revision: 1.0 $
 */
public class FindUsersTableViewerSorter extends ViewerSorter {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fColumnIndex.
	 */
	private int fColumnIndex = 0;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor
	 * 
	 * @param aColumnIndex
	 *            int
	 */
	public FindUsersTableViewerSorter(int aColumnIndex) {
		super();
		fColumnIndex = aColumnIndex;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method compare.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aObj1
	 *            Object
	 * @param aObj2
	 *            Object
	 * @return int
	 */
	@Override
	public int compare(Viewer aViewer, Object aObj1, Object aObj2) {
		if (aViewer instanceof TableViewer) {

			final TableViewer tv = (TableViewer) aViewer;
			tv.getTable().setSortColumn(tv.getTable().getColumn(fColumnIndex));
			int idx1 = -1, idx2 = -1;
			final int numItems = tv.getTable().getItemCount();
			Object obj = null;
			for (int i = 0; i < numItems; i++) {
				obj = tv.getElementAt(i);
				if (obj.equals(aObj1)) {
					idx1 = i;
				} else if (obj.equals(aObj2)) {
					idx2 = i;
				}
				if (idx1 > 0 && idx2 > 0) {
					break;
				}
			}

			int order = 0;
			if (idx1 > -1 && idx2 > -1) {
				final String str1 = tv.getTable().getItems()[idx1].getText(fColumnIndex);
				final String str2 = tv.getTable().getItems()[idx2].getText(fColumnIndex);
				order = str1.compareTo(str2);
				if (tv.getTable().getSortDirection() != SWT.UP) {
					order *= -1;
				}
			}
			return order;
		} else if (aViewer instanceof TreeViewer) {
			final TreeViewer tv = (TreeViewer) aViewer;
			tv.getTree().setSortColumn(tv.getTree().getColumn(fColumnIndex));
			int idx1 = -1, idx2 = -1;

			final Object[] listObj = tv.getTree().getItems();
			Object obj = null;
			for (int i = 0; i < listObj.length; i++) {
				if (listObj[i] instanceof TreeItem) {
					obj = ((TreeItem) listObj[i]).getData();
					((TreeItem) listObj[i]).setExpanded(true);
				}
				if (null != obj) {
					if (obj.equals(aObj1)) {
						idx1 = i;
					} else if (obj.equals(aObj2)) {
						idx2 = i;
					}
					if (idx1 > 0 && idx2 > 0) {
						break;
					}
				}
			}
			int order = 0;
			if (idx1 > -1 && idx2 > -1) {
				final String str1 = tv.getTree().getItems()[idx1].getText(fColumnIndex);
				final String str2 = tv.getTree().getItems()[idx2].getText(fColumnIndex);
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
	 * Bind Sorter to tableViewer.
	 * 
	 * @param aTableViewer
	 *            TableViewer
	 */
	public static void bind(final TableViewer aTableViewer) {
		final int numColumns = aTableViewer.getTable().getColumnCount();
		for (int i = 0; i < numColumns; i++) {
			final int columnNum = i;
			TableColumn column = aTableViewer.getTable().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					FindUsersTableViewerSorter sorter = new FindUsersTableViewerSorter(columnNum);
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

	/**
	 * Bind Sorter to TreeViewer
	 * 
	 * @param aTreeViewer
	 *            TreeViewer
	 */
	public static void bind(final TreeViewer aTreeViewer) {
		final int numColumns = aTreeViewer.getTree().getColumnCount();
		for (int i = 0; i < numColumns; i++) {
			final int columnNum = i;
			TreeColumn column = aTreeViewer.getTree().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					FindUsersTableViewerSorter sorter = new FindUsersTableViewerSorter(columnNum);
					Tree table = aTreeViewer.getTree();
					if (table.getSortDirection() == SWT.UP) {
						table.setSortDirection(SWT.DOWN);
					} else if (table.getSortDirection() == SWT.DOWN) {
						table.setSortDirection(SWT.UP);
					} else {
						table.setSortDirection(SWT.UP);
					}
					aTreeViewer.setComparator(sorter);
				}
			});
		}
	}

	/**
	 * table tree bind to Sorter with a specific column.
	 * 
	 * @param aTreeViewer
	 *            TreeViewer
	 * @param aColumnNum
	 *            int
	 */
	public static void bind(final TreeViewer aTreeViewer, final int aColumnNum) {
		final int maxColumn = aTreeViewer.getTree().getColumnCount();
		if (aColumnNum < maxColumn) {
			final TreeColumn column = aTreeViewer.getTree().getColumn(aColumnNum);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					final FindUsersTableViewerSorter sorter = new FindUsersTableViewerSorter(aColumnNum);
					final Tree table = aTreeViewer.getTree();
					if (table.getSortDirection() == SWT.UP) {
						table.setSortDirection(SWT.DOWN);
					} else if (table.getSortDirection() == SWT.DOWN) {
						table.setSortDirection(SWT.UP);
					} else {
						table.setSortDirection(SWT.UP);
					}
					aTreeViewer.setComparator(sorter);
				}
			});
		}
	}

}
