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

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewSingleAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ESingleAnnotationSupport extends ReviewSingleAnnotationSupport {

	/**
	 * Constructor for R4ESingleAnnotationSupport.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aFileContext
	 *            Object
	 */
	public R4ESingleAnnotationSupport(ISourceViewer aSourceViewer, Object aFileContext) {
		super(aSourceViewer, aFileContext);
	}

	/**
	 * Method createEditorInputListener.
	 * 
	 * @param aViewer
	 *            ISourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return IEditorInputListener
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewCompareAnnotationSupport#createEditorInputListener(ISourceViewer,
	 *      IReviewAnnotationModel)
	 */
	@Override
	public IEditorInputListener createEditorInputListener(ISourceViewer aViewer, IReviewAnnotationModel aAnnotationModel) {
		return new R4ESingleEditorInputListener(aViewer, aAnnotationModel);
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
