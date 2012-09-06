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
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ESingleAnnotationSupport extends R4EAnnotationSupport {

	/**
	 * Field KEY_ANNOTATION_SUPPORT.
	 */
	private static final String KEY_ANNOTATION_SUPPORT = R4EFileEditorInput.class.getName();

	/**
	 * Constructor for R4ESingleAnnotationSupport.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aFileContext
	 *            R4EUIFileContext
	 */
	public R4ESingleAnnotationSupport(SourceViewer aSourceViewer, R4EUIFileContext aFileContext) {
		super(aSourceViewer, aFileContext);
		install(aSourceViewer);
		fTargetViewerListener.inputDocumentChanged(null, aSourceViewer.getDocument()); //Force annotation updates
	}

	/**
	 * Method getAnnotationSupport.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aFileContext
	 *            R4EUIFileContext
	 * @return IReviewAnnotationSupport
	 */
	public static IReviewAnnotationSupport getAnnotationSupport(SourceViewer aSourceViewer,
			R4EUIFileContext aFileContext) {
		IReviewAnnotationSupport support = (IReviewAnnotationSupport) aSourceViewer.getData(KEY_ANNOTATION_SUPPORT);
		if (support == null) {
			support = new R4ESingleAnnotationSupport(aSourceViewer, aFileContext);
			aSourceViewer.setData(KEY_ANNOTATION_SUPPORT, support);
		}
		return support;
	}

	/**
	 * Method install.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
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
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return R4EEditorInputListener
	 */
	protected IEditorInputListener registerInputListener(final ISourceViewer aSourceViewer,
			final IReviewAnnotationModel aAnnotationModel) {
		final IEditorInputListener listener = new R4ESingleEditorInputListener(aSourceViewer, aAnnotationModel);
		if (aSourceViewer != null) {
			aSourceViewer.addTextInputListener(listener);
		}
		return listener;
	}
}
