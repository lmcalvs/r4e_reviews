// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc, explicitThisUsage
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
 * This class implements the content provider for the Review Navigator View
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.navigator;


import java.util.Arrays;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelListener;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelEvent;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewGroup;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorContentProvider implements ITreeContentProvider, IR4EUIModelListener {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fViewer.
	 */
	private TreeViewer fViewer;
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method dispose.
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() { // $codepro.audit.disable emptyMethod
	}
	
	/**
	 * Method getChildren.
	 * @param aParentElement Object
	 * @return Object[]
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object aParentElement) {
		final IR4EUIModelElement element = (IR4EUIModelElement)aParentElement;
		final IR4EUIModelElement[] elements = element.getChildren();
		return Arrays.asList(elements).toArray();
	}
 
	/**
	 * Method getParent.
	 * @param aElement Object
	 * @return Object
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object aElement) {
		return ((IR4EUIModelElement)aElement).getParent();
	}
 
	/**
	 * Method hasChildren.
	 * @param aElement Object
	 * @return boolean
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object aElement) {
		if (null != aElement) {
			return ((IR4EUIModelElement)aElement).hasChildren();
		}
		return false;
	}
 
	/**
	 * Method getElements.
	 * @param aInputElement Object
	 * @return Object[]
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object aInputElement) {
		return getChildren(aInputElement);
	}

	/**
	 * Method inputChanged.
	 * @param aViewer Viewer
	 * @param aOldInput Object
	 * @param aNewInput Object
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
	    Activator.Ftracer.traceInfo("Input changed");
		fViewer = (TreeViewer)aViewer;
		if(null != aOldInput) {
			removeListenerFrom((IR4EUIModelElement)aOldInput);
		}
		if(null != aNewInput) {
			addListenerTo((IR4EUIModelElement)aNewInput);
		}
	}
	
	/**
	 * Method addEvent.
	 * @param aEvent ReviewModelEvent
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelListener#addEvent(R4EUIModelEvent)
	 */
	public void addEvent(R4EUIModelEvent aEvent) {
	    Activator.Ftracer.traceInfo("Add event received for element " + 
	    		((IR4EUIModelElement)aEvent.receiver()).getName());
		fViewer.refresh(((IR4EUIModelElement)aEvent.receiver()).getParent(), false);
	}

	/**
	 * Method removeEvent.
	 * @param aEvent ReviewModelEvent
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelListener#removeEvent(R4EUIModelEvent)
	 */
	public void removeEvent(R4EUIModelEvent aEvent) {
	    Activator.Ftracer.traceInfo("Remove event received for element " + 
	    		((IR4EUIModelElement)aEvent.receiver()).getName());
		fViewer.refresh(((IR4EUIModelElement)aEvent.receiver()).getParent(), false);
	}

	/**
	 * Method changedEvent.
	 * @param aEvent ReviewModelEvent
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelListener#changedEvent(R4EUIModelEvent)
	 */
	public void changedEvent(R4EUIModelEvent aEvent) {
		final IR4EUIModelElement affectedObject = (IR4EUIModelElement)aEvent.receiver();
	    Activator.Ftracer.traceInfo("Changed event received for element " + affectedObject.getName());
		if (affectedObject instanceof R4EUIReviewGroup || affectedObject instanceof R4EUIReviewBasic) {
			
			//Open or Close review or review group
			fViewer.collapseToLevel(affectedObject, AbstractTreeViewer.ALL_LEVELS );
			fViewer.refresh(affectedObject, true);
		} else {
			//Change review state
			fViewer.refresh(affectedObject.getParent(), true);
		}
	}
	
	/**
	 * Add a listener to the element
	 * @param aElement - the element to act upon
	 */
	private void addListenerTo(IR4EUIModelElement aElement) {
		aElement.addListener(this);
	}

	/**
	 * Remove the listener from the element
	 * @param aElement - the element to act upon
	 */
	private void removeListenerFrom(IR4EUIModelElement aElement) {
		aElement.removeListener();
	}
}
