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
 * This class implements the listeners that creates the Review annotation model
 * and the editor changes needed when the underlying Review document is opened.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Thomas Ehrnhoefer
 * @author Steffen Pingel
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewEditorInputListener implements IEditorInputListener {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fAnnotationModel.
	 */
	protected final IReviewAnnotationModel fAnnotationModel;

	/**
	 * Field fSourceViewer.
	 */
	protected final ISourceViewer fSourceViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewEditorInputListener.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 */
	public ReviewEditorInputListener(ISourceViewer aSourceViewer, IReviewAnnotationModel aAnnotationModel) {
		this.fSourceViewer = aSourceViewer;
		this.fAnnotationModel = aAnnotationModel;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getEditor.
	 * 
	 * @return ITextEditor
	 */
	public abstract ITextEditor getEditor();

	/**
	 * Method inputDocumentAboutToBeChanged.
	 * 
	 * @param aOldInput
	 *            IDocument
	 * @param aNewInput
	 *            IDocument
	 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(IDocument, IDocument)
	 */
	public void inputDocumentAboutToBeChanged(IDocument aOldInput, IDocument aNewInput) {
		// ignore
	}

	/**
	 * Method addAnnotationModel.
	 * 
	 * @param aModel
	 *            IAnnotationModel
	 * @param aNewInput
	 *            IDocument
	 */
	protected abstract void addAnnotationModel(IAnnotationModel aModel, IDocument aNewInput);
	
	
	/**
	 * Method inputDocumentChanged.
	 * 
	 * @param aOldInput
	 *            IDocument
	 * @param aNewInput
	 *            IDocument
	 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(IDocument, IDocument)
	 */
	public void inputDocumentChanged(IDocument aOldInput, IDocument aNewInput) {
		if (aOldInput != null) {
			fAnnotationModel.disconnect(aOldInput);
		}
		if ((aNewInput != null) && (fSourceViewer != null)) {
			final IAnnotationModel originalAnnotationModel = fSourceViewer.getAnnotationModel();
			addAnnotationModel(originalAnnotationModel, aNewInput);
		}
	}

	/**
	 * Method configureViewerAnnotations.
	 * 
	 * @param aModel
	 *            IAnnotationModel
	 * @param aInput
	 *            IDocument
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void configureViewerAnnotations(IAnnotationModel aModel, IDocument aInput) throws SecurityException,
			NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final Class<SourceViewer> sourceViewerClazz = SourceViewer.class;
		final Field declaredField2 = sourceViewerClazz.getDeclaredField("fVisualAnnotationModel"); //$NON-NLS-1$
		declaredField2.setAccessible(true);
		final Method declaredMethod = sourceViewerClazz.getDeclaredMethod("createVisualAnnotationModel", //$NON-NLS-1$
				IAnnotationModel.class);
		declaredMethod.setAccessible(true);
		aModel = (IAnnotationModel) declaredMethod.invoke(fSourceViewer, fAnnotationModel);
		declaredField2.set(fSourceViewer, aModel);
		aModel.connect(aInput);

		//Override default vertical ruler.  We use the default overview ruler
		updateVerticalRuler(sourceViewerClazz);
		updateOverviewRuler(sourceViewerClazz);
	}

	/**
	 * Method replaceVerticalRuler.
	 * 
	 * @param aSourceViewerClazz
	 *            Class<SourceViewer>
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 */
	protected abstract void updateVerticalRuler(Class<SourceViewer> aSourceViewerClazz) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchFieldException;

	/**
	 * Method updateOverviewRuler.
	 * 
	 * @param aSourceViewerClazz
	 *            Class<SourceViewer>
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 */
	protected abstract void updateOverviewRuler(Class<SourceViewer> aSourceViewerClazz) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchFieldException;
}