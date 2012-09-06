/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the contents provider for the TreeViewer included in the
 * R4E Annotation Hover
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationText;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationContentProvider implements ITreeContentProvider {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// ignore
	}

	/**
	 * Method getChildren.
	 * 
	 * @param aParentElement
	 *            Object
	 * @return Object[]
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object aParentElement) {
		if (aParentElement instanceof IReviewAnnotation) {
			return ((IReviewAnnotation) aParentElement).getChildren();
		} else if (aParentElement instanceof R4EAnnotationHoverInput) {
			final List<IReviewAnnotation> annotations = ((R4EAnnotationHoverInput) aParentElement).getAnnotations();
			return annotations.toArray(new IReviewAnnotation[annotations.size()]);
		}
		return null;
	}

	/**
	 * Method getParent.
	 * 
	 * @param aElement
	 *            Object
	 * @return Object
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(Object)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object aElement) {
		if (aElement instanceof IReviewAnnotation) {
			return ((IReviewAnnotation) aElement).getParent();
		} else if (aElement instanceof R4EAnnotationText) {
			return ((R4EAnnotationText) aElement).getParent();
		}
		return null;
	}

	/**
	 * Method hasChildren.
	 * 
	 * @param aElement
	 *            Object
	 * @return boolean
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object aElement) {
		if (aElement instanceof IReviewAnnotation) {
			return ((IReviewAnnotation) aElement).hasChildren();
		} else if (aElement instanceof R4EAnnotationHoverInput) {
			return ((R4EAnnotationHoverInput) aElement).getAnnotations().size() > 0;
		}
		return false;
	}

	/**
	 * Method getElements.
	 * 
	 * @param aInputElement
	 *            Object
	 * @return Object[]
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(Object)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object aInputElement) {
		return getChildren(aInputElement);
	}

	/**
	 * Method inputChanged.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aOldInput
	 *            Object
	 * @param aNewInput
	 *            Object
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
		// ignore
	}
}
