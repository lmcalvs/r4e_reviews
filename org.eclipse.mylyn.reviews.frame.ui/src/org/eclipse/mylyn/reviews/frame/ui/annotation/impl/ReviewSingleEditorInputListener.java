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
 * and the editor changes needed when the underlying document is opened.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.internal.text.StickyHoverManager;
import org.eclipse.jface.text.AbstractHoverInformationControlManager;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.internal.annotation.impl.ReviewAnnotationHover;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Thomas Ehrnhoefer
 * @author Steffen Pingel
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewSingleEditorInputListener extends ReviewEditorInputListener {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewSingleEditorInputListener.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 */
	public ReviewSingleEditorInputListener(ISourceViewer aSourceViewer, IReviewAnnotationModel aAnnotationModel) {
		super(aSourceViewer, aAnnotationModel);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getEditor.
	 * 
	 * @return ITextEditor
	 */
	@Override
	public ITextEditor getEditor() {
		return null; //unused for now
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
	@SuppressWarnings("restriction")
	@Override
	protected void updateVerticalRuler(Class<SourceViewer> aSourceViewerClazz) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchFieldException {

		//First remove annotation to dispose of data
		fSourceViewer.showAnnotations(false);

		//Set HoverControlCreator in SourceViewer
		final IInformationControlCreator annotationInformationControlCreator = ReviewAnnotationConfigFactory.createInformationControlCreator();
		((TextViewer) fSourceViewer).setHoverControlCreator(annotationInformationControlCreator);

		//Here we need to override the ensureAnnotationHoverManagerInstalled method in sourceViewer to put our own InformationControl data

		//Set annotationHover in SourceViewer
		final ReviewAnnotationHover annotationHover = new ReviewAnnotationHover();
		fSourceViewer.setAnnotationHover(annotationHover);

		//Get Vertical Ruler from sourceViewer
		final Method declaredMethod2 = aSourceViewerClazz.getDeclaredMethod("getVerticalRuler"); //$NON-NLS-1$
		declaredMethod2.setAccessible(true);
		final IVerticalRuler ruler = (CompositeRuler) declaredMethod2.invoke(fSourceViewer);

		//This overrides the call to ensureAnnotationHoverManagerInstalled method in viewer
		final AbstractHoverInformationControlManager reviewInformationControlManager = new ReviewAnnotationInformationControlManager(
				ruler, fSourceViewer, annotationHover, annotationInformationControlCreator);
		final Field hoverManager = SourceViewer.class.getDeclaredField("fVerticalRulerHoveringController"); //$NON-NLS-1$
		hoverManager.setAccessible(true);
		hoverManager.set(fSourceViewer, reviewInformationControlManager);
		reviewInformationControlManager.install(ruler.getControl());
		if (ReviewAnnotationConfigFactory.getUseInformationControlReplacer()) {
		reviewInformationControlManager.getInternalAccessor().setInformationControlReplacer(
				new StickyHoverManager((SourceViewer) fSourceViewer));
		}
		
		//Finally add back annotations showing
		fSourceViewer.showAnnotations(true);
	}

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
	@Override
	protected void updateOverviewRuler(Class<SourceViewer> aSourceViewerClazz) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchFieldException {
		// This is not used for the single editor and we rely on the default overview ruler
	}
}