package org.eclipse.mylyn.reviews.r4e.ui.navigator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;

public class LineViewerSorter extends ViewerSorter {

    @SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

    	if (e1 instanceof R4EUIAnomalyBasic && e2 instanceof R4EUIAnomalyBasic) {
    		if (null != ((R4EUIAnomalyBasic)e1).getPosition()) {
    			return ((R4EUITextPosition)((R4EUIAnomalyBasic)e1).getPosition()).getStartLine() -
    					((R4EUITextPosition)((R4EUIAnomalyBasic)e2).getPosition()).getStartLine();
    		}
    		//Global anomalies return alphabetically
    	    // use the comparator to compare the strings
    	    return getComparator().compare(((R4EUIAnomalyBasic)e1).getName(), ((R4EUIAnomalyBasic)e2).getName());
    	}
    	return 0;
    }
}
