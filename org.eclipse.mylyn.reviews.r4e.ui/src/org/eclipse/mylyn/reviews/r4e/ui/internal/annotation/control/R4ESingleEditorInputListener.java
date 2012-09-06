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
 * and the editor changes needed when the underlying R4E document is opened.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.text.AbstractHoverInformationControlManager;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Thomas Ehrnhoefer
 * @author Steffen Pingel
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ESingleEditorInputListener extends R4EEditorInputListener {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ESingleEditorInputListener.
	 * 
	 * @param aSourceViewer
	 *            SourceViewer
	 * @param aAnnotationModel
	 *            R4EAnnotationModel
	 */
	public R4ESingleEditorInputListener(ISourceViewer aSourceViewer, IReviewAnnotationModel aAnnotationModel) {
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
	 * @param aNewInput
	 *            IDocument
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
		final IInformationControlCreator annotationInformationControlCreator = new R4EAnnotationInformationControlCreator();
		((TextViewer) fSourceViewer).setHoverControlCreator(annotationInformationControlCreator);

		//Here we need to override the ensureAnnotationHoverManagerInstalled method in sourceViewer to put our own InformationControl data

		//Set annotationHover in SourceViewer
		final R4EAnnotationHover annotationHover = new R4EAnnotationHover();
		fSourceViewer.setAnnotationHover(annotationHover);

		//Get Vertical Ruler from sourceViewer
		final Method declaredMethod2 = aSourceViewerClazz.getDeclaredMethod("getVerticalRuler"); //$NON-NLS-1$
		declaredMethod2.setAccessible(true);
		final IVerticalRuler ruler = (CompositeRuler) declaredMethod2.invoke(fSourceViewer);

		//This overrides the call to ensureAnnotationHoverManagerInstalled method in viewer
		final AbstractHoverInformationControlManager r4eInformationControlManager = new R4EAnnotationInformationControlManager(
				ruler, fSourceViewer, annotationHover, annotationInformationControlCreator);
		final Field hoverManager = SourceViewer.class.getDeclaredField("fVerticalRulerHoveringController"); //$NON-NLS-1$
		hoverManager.setAccessible(true);
		hoverManager.set(fSourceViewer, r4eInformationControlManager);
		r4eInformationControlManager.install(ruler.getControl());
		r4eInformationControlManager.getInternalAccessor().setInformationControlReplacer(
				new R4EStickyHoverManager(fSourceViewer));

		//Finally add back annotations showing
		fSourceViewer.showAnnotations(true);
	}

	/**
	 * Method updateOverviewRuler.
	 * 
	 * @param aNewInput
	 *            IDocument
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