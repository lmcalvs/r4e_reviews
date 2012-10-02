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
 * This class implements the listeners that creates the R4E annotation model
 * and the compare editor changes needed when the underlying R4E document is opened.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewCompareEditorInputListener;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ECompareEditorInputListener extends ReviewCompareEditorInputListener {

	/**
	 * Constructor for R4ECompareEditorInputListener.
	 * 
	 * @param aMergeSourceViewer
	 *            MergeSourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 */
	public R4ECompareEditorInputListener(MergeSourceViewer aMergeSourceViewer, IReviewAnnotationModel aAnnotationModel) {
		super(aMergeSourceViewer, aAnnotationModel);
	}

	/**
	 * Method addAnnotationModel.
	 * 
	 * @param aModel
	 *            IAnnotationModel
	 * @param aNewInput
	 *            IDocument
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewEditorInputListener#addAnnotationModel(IAnnotationModel,
	 *      IDocument)
	 */
	@Override
	protected void addAnnotationModel(IAnnotationModel aModel, IDocument aNewInput) {
		try {
			if (aModel instanceof IAnnotationModelExtension) {
				final IAnnotationModelExtension annotationModelExtension = (IAnnotationModelExtension) aModel;
				annotationModelExtension.addAnnotationModel(R4EUIPlugin.PLUGIN_ID, fAnnotationModel);
			}
			configureViewerAnnotations(aModel, aNewInput);
		} catch (Throwable t) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + t.toString() + " (" + t.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault()
					.getLog()
					.log(new Status(IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, IStatus.OK, t.toString(), t));
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
					"Error attaching annotation model", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, //$NON-NLS-1$
							t.getMessage(), t), IStatus.ERROR);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					dialog.open();
				}
			});
		}
	}

}
