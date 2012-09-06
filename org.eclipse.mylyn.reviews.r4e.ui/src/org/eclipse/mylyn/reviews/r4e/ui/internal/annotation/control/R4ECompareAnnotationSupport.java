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

import java.lang.reflect.Field;

import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;

/**
 * Manages annotation models for compare viewers.
 * 
 * @author Thomas Ehrnhoefer
 * @author Steffen Pingel
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("restriction")
public class R4ECompareAnnotationSupport extends R4EAnnotationSupport {

	/**
	 * Field KEY_ANNOTATION_SUPPORT.
	 */
	private static final String KEY_ANNOTATION_SUPPORT = R4ECompareEditorInput.class.getName();

	/**
	 * Field fBaseAnnotationModel.
	 */
	private final IReviewAnnotationModel fBaseAnnotationModel;

	/**
	 * Field fBaseViewerListener.
	 */
	private IEditorInputListener fBaseViewerListener = null;

	/**
	 * Constructor for R4ECompareAnnotationSupport.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aFileContext
	 *            R4EUIFileContext
	 */
	public R4ECompareAnnotationSupport(Viewer aViewer, R4EUIFileContext aFileContext) {
		super(aViewer, aFileContext);
		fBaseAnnotationModel = new R4EAnnotationModel();
		install(aViewer);
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
	public static IReviewAnnotationSupport getAnnotationSupport(Viewer aViewer, R4EUIFileContext aFileContext) {
		R4ECompareAnnotationSupport support = (R4ECompareAnnotationSupport) aViewer.getData(KEY_ANNOTATION_SUPPORT);
		if (support == null) {
			support = new R4ECompareAnnotationSupport(aViewer, aFileContext);
			aViewer.setData(KEY_ANNOTATION_SUPPORT, support);
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
		if (aSourceViewer instanceof TextMergeViewer) {
			final TextMergeViewer textMergeViewer = (TextMergeViewer) aSourceViewer;
			try {
				final Class<TextMergeViewer> clazz = TextMergeViewer.class;
				Field declaredField = clazz.getDeclaredField("fLeft"); //$NON-NLS-1$
				declaredField.setAccessible(true);
				final MergeSourceViewer fLeft = (MergeSourceViewer) declaredField.get(textMergeViewer);

				declaredField = clazz.getDeclaredField("fRight"); //$NON-NLS-1$
				declaredField.setAccessible(true);
				final MergeSourceViewer fRight = (MergeSourceViewer) declaredField.get(textMergeViewer);

				fTargetViewerListener = registerInputListener(fLeft, fTargetAnnotationModel);
				fBaseViewerListener = registerInputListener(fRight, fBaseAnnotationModel);
			} catch (Throwable t) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + t.toString() + " (" + t.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				R4EUIPlugin.getDefault().logError("Exception: " + t.toString(), (Exception) t); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Method registerInputListener.
	 * 
	 * @param aViewer
	 *            MergeSourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return R4EEditorInputListener
	 */
	private IEditorInputListener registerInputListener(final MergeSourceViewer aViewer,
			final IReviewAnnotationModel aAnnotationModel) {
		final ISourceViewer sourceViewer = CommandUtils.getSourceViewer(aViewer);
		final IEditorInputListener listener = new R4ECompareEditorInputListener(aViewer, aAnnotationModel);
		if (sourceViewer != null) {
			sourceViewer.addTextInputListener(listener);
		}
		return listener;
	}
}
