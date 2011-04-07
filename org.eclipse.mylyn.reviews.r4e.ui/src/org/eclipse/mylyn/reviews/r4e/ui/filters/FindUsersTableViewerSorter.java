/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.ui.filters;

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
 * 
 */
public class FindUsersTableViewerSorter extends ViewerSorter {
	private int columnIndex = 0;

	public FindUsersTableViewerSorter(int columnIndex) {
		super();
		this.columnIndex = columnIndex;
	}

	/*
	 * public static void reverseOrder() { order *= -1; }
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (viewer instanceof TableViewer) {

			TableViewer tv = (TableViewer) viewer;
			tv.getTable().setSortColumn(tv.getTable().getColumn(columnIndex));
			int idx1 = -1, idx2 = -1;
			for (int i = 0; i < tv.getTable().getItemCount(); i++) {
				Object obj = tv.getElementAt(i);
				if (obj.equals(e1)) {
					idx1 = i;
				} else if (obj.equals(e2)) {
					idx2 = i;
				}
				if (idx1 > 0 && idx2 > 0) {
					break;
				}
			}
			
			int order = 0;
			if (idx1 > -1 && idx2 > -1) {
				String str1 = tv.getTable().getItems()[idx1].getText(this.columnIndex);
				String str2 = tv.getTable().getItems()[idx2].getText(this.columnIndex);
				order = str1.compareTo(str2);
				if (tv.getTable().getSortDirection() != SWT.UP) {
					order *= -1;
				}
			}
			return order;
		} else if (viewer instanceof TreeViewer) {
			TreeViewer tv = (TreeViewer) viewer;
			tv.getTree().setSortColumn(tv.getTree().getColumn(columnIndex));
			int idx1 = -1, idx2 = -1;

			Object[] listObj = tv.getTree().getItems();
			for (int i = 0; i < listObj.length; i++) {
				Object obj = null;
				if (listObj[i] instanceof TreeItem) {
					obj = ((TreeItem) listObj[i]).getData();
					((TreeItem) listObj[i]).setExpanded(true);
				}
				if (obj != null) {
					if (obj.equals(e1)) {
						idx1 = i;
					} else if (obj.equals(e2)) {
						idx2 = i;
					}
					if (idx1 > 0 && idx2 > 0) {
						break;
					}
				}
			}
			int order = 0;
			if (idx1 > -1 && idx2 > -1) {
				String str1 = tv.getTree().getItems()[idx1].getText(this.columnIndex);
				String str2 = tv.getTree().getItems()[idx2].getText(this.columnIndex);
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
	 * @param tableViewer TableViewer
	 */
	public static void bind(final TableViewer tableViewer) {
		for (int i = 0; i < tableViewer.getTable().getColumnCount(); i++) {
			final int columnNum = i;
			TableColumn column = tableViewer.getTable().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					FindUsersTableViewerSorter sorter = new FindUsersTableViewerSorter(columnNum);
					Table table = tableViewer.getTable();
					if (table.getSortDirection() == SWT.UP) {
						table.setSortDirection(SWT.DOWN);
					} else if (table.getSortDirection() == SWT.DOWN) {
						table.setSortDirection(SWT.UP);
					} else {
						table.setSortDirection(SWT.UP);
					}
					tableViewer.setComparator(sorter);
				}
			});
		}
	}

	/**
	 * table tree bind to Sorter.
	 * @param treeViewer TreeViewer
	 */
	public static void bind(final TreeViewer treeViewer) {
		for (int i = 0; i < treeViewer.getTree().getColumnCount(); i++) {
			final int columnNum = i;
			TreeColumn column = treeViewer.getTree().getColumn(i);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					FindUsersTableViewerSorter sorter = new FindUsersTableViewerSorter(columnNum);
					Tree table = treeViewer.getTree();
					if (table.getSortDirection() == SWT.UP) {
						table.setSortDirection(SWT.DOWN);
					} else if (table.getSortDirection() == SWT.DOWN) {
						table.setSortDirection(SWT.UP);
					} else {
						table.setSortDirection(SWT.UP);
					}
					treeViewer.setComparator(sorter);
				}
			});
		}
	}

	/**
	 * table tree bind to Sorter with a specific column.
	 * @param treeViewer TreeViewer
	 * @param columnNum int
	 */
	public static void bind(final TreeViewer treeViewer, final int columnNum) {
		int maxColumn = treeViewer.getTree().getColumnCount();
		if (columnNum < maxColumn) {
			TreeColumn column = treeViewer.getTree().getColumn(columnNum);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					FindUsersTableViewerSorter sorter = new FindUsersTableViewerSorter(columnNum);
					Tree table = treeViewer.getTree();
					if (table.getSortDirection() == SWT.UP) {
						table.setSortDirection(SWT.DOWN);
					} else if (table.getSortDirection() == SWT.DOWN) {
						table.setSortDirection(SWT.UP);
					} else {
						table.setSortDirection(SWT.UP);
					}
					treeViewer.setComparator(sorter);
				}
			});
		}
	}

}
