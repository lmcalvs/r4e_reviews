package org.eclipse.mylyn.reviews.r4e.ui.utils;

import org.eclipse.swt.widgets.Item;

public interface IEditableListListener {
	
	/**
	 * Method itemAdded.
	 * @param aAdded Item
	 */
	void itemsUpdated(Item[] aItems, int aInstanceId);
}
