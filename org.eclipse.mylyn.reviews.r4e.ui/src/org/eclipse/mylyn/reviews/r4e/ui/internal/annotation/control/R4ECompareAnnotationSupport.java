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
 * This class implements support for R4E Annotations in Compare Editors
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewCompareAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;

/**
 * Manages annotation models for compare viewers.
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ECompareAnnotationSupport extends ReviewCompareAnnotationSupport {

	/**
	 * Constructor for R4ECompareAnnotationSupport.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aFileContext
	 *            Object
	 */
	public R4ECompareAnnotationSupport(Viewer aViewer, Object aFileContext) {
		super(aViewer, aFileContext);
	}

	/**
	 * Method createEditorInputListener.
	 * 
	 * @param aViewer
	 *            MergeSourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return IEditorInputListener
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewCompareAnnotationSupport#createEditorInputListener(MergeSourceViewer,
	 *      IReviewAnnotationModel)
	 */
	@Override
	public IEditorInputListener createEditorInputListener(MergeSourceViewer aViewer,
			IReviewAnnotationModel aAnnotationModel) {
		return new R4ECompareEditorInputListener(aViewer, aAnnotationModel);
	}

	/**
	 * Method createAnnotationModel.
	 * 
	 * @param aSourceFile
	 *            Object
	 * @return IReviewAnnotationModel
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewCompareAnnotationSupport#createAnnotationModel()
	 */
	@Override
	protected IReviewAnnotationModel createAnnotationModel(Object aSourceFile) {
		if (null != aSourceFile) {
			final IReviewAnnotationModel model = ((R4EUIFileContext) aSourceFile).getAnnotationModel();
			if (null != model) {
				return model;
			}
		}
		return new R4EAnnotationModel();
	}

}
