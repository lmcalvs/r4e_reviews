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
 * This abstract class implements support for Review Annotations in Compare Editors
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewSingleAnnotationSupport extends ReviewAnnotationSupport {

	/**
	 * Constructor for ReviewSingleAnnotationSupport.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aSourceFile
	 *            Object
	 */
	public ReviewSingleAnnotationSupport(ISourceViewer aSourceViewer, Object aSourceFile) {
		super(aSourceFile);
		install(aSourceViewer);
		fTargetViewerListener.inputDocumentChanged(null, aSourceViewer.getDocument()); //Force annotation updates
	}

	/**
	 * Method createEditorInputListener.
	 * 
	 * @param aViewer
	 *            ISourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel   
	 * @return IEditorInputListener
	 */
	public abstract IEditorInputListener createEditorInputListener(final ISourceViewer aViewer,
			final IReviewAnnotationModel aAnnotationModel);
	
	/**
	 * Method install.
	 * 
	 * @param aSourceViewer
	 *            Object
	 */
	@Override
	protected void install(Object aSourceViewer) {
		if (aSourceViewer instanceof SourceViewer) {
			fTargetViewerListener = registerInputListener((ISourceViewer) aSourceViewer, fTargetAnnotationModel);
		}
	}

	/**
	 * Method registerInputListener.
	 * 
	 * @param aViewer
	 *            ISourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return IEditorInputListener
	 */
	protected IEditorInputListener registerInputListener(final ISourceViewer aViewer,
			final IReviewAnnotationModel aAnnotationModel) {
		final IEditorInputListener listener = createEditorInputListener(aViewer, aAnnotationModel);
		if (aViewer != null) {
			aViewer.addTextInputListener(listener);
		}
		return listener;
	}
}
