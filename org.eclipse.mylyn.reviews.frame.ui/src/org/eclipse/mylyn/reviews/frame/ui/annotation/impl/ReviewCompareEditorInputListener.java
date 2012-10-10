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
 * and the compare editor changes needed when the underlying Review document is opened.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.jface.internal.text.StickyHoverManager;
import org.eclipse.jface.text.AbstractHoverInformationControlManager;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.internal.annotation.impl.ReviewAnnotationHover;
import org.eclipse.ui.internal.texteditor.AnnotationColumn;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Thomas Ehrnhoefer
 * @author Steffen Pingel
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewCompareEditorInputListener extends ReviewEditorInputListener {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fMergeSourceViewer.
	 */
	private final MergeSourceViewer fMergeSourceViewer;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewCompareEditorInputListener.
	 * 
	 * @param aMergeSourceViewer
	 *            MergeSourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 */
	public ReviewCompareEditorInputListener(MergeSourceViewer aMergeSourceViewer,
			IReviewAnnotationModel aAnnotationModel) {
		super(ReviewAnnotationSupport.getSourceViewer(aMergeSourceViewer), aAnnotationModel);
		fMergeSourceViewer = aMergeSourceViewer;
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
		return (ITextEditor) fMergeSourceViewer.getAdapter(ITextEditor.class);
	}

	/**
	 * Method updateVerticalRuler.
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
	protected void updateVerticalRuler(Class<SourceViewer> aSourceViewerClazz) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchFieldException {

		//Set fHoverControlCreator in SourceViewer
		final IInformationControlCreator annotationInformationControlCreator = ReviewAnnotationConfigFactory.createInformationControlCreator();
		((TextViewer) fSourceViewer).setHoverControlCreator(annotationInformationControlCreator);

		//First remove annotation to dispose of data
		fSourceViewer.showAnnotations(false);

		//Set annotationHover in SourceViewer
		final IAnnotationHover annotationHover = new ReviewAnnotationHover();
		fSourceViewer.setAnnotationHover(annotationHover);

		//Get Vertical Ruler from sourceViewer
		final Method declaredMethod2 = aSourceViewerClazz.getDeclaredMethod("getVerticalRuler"); //$NON-NLS-1$
		declaredMethod2.setAccessible(true);
		final CompositeRuler ruler = (CompositeRuler) declaredMethod2.invoke(fSourceViewer);

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

		//This creates the extra vertical ruler that will be used to display the annotations
		boolean hasDecorator = false;
		final Iterator<?> iter = (ruler).getDecoratorIterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof AnnotationColumn) {
				hasDecorator = true;
			}
		}
		if (!hasDecorator) {
			final AnnotationColumn annotationColumn = new AnnotationColumn();
			annotationColumn.createControl(ruler, ruler.getControl().getParent());
			ruler.addDecorator(0, annotationColumn);
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
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Override
	protected void updateOverviewRuler(Class<SourceViewer> aSourceViewerClazz) throws SecurityException,
			NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		//TODO lmcdubo:  Right now the annotations do not show in the overview ruler, even if
		//                the OverviewRuler paint method is executed properly.  So we disable this code
		//
		// overview ruler problem: displayed in both viewers. the diff editor ruler is actually custom drawn (see
		// TextMergeViewer.fBirdsEyeCanvas) the ruler that gets created in this method is longer than the editor, meaning its
		// not an overview (not next to the scrollbar)
		/*
		final IInformationControlCreator annotationInformationControlCreator = ReviewAnnotationConfigFactory.createInformationControlCreator();

		//Set overviewHover in SourceViewer
		final IAnnotationHover annotationHover = new ReviewAnnotationHover();
		((SourceViewer) fSourceViewer).setOverviewRulerAnnotationHover(annotationHover);

		//Create Overview Ruler and set it in Viewer
		final IOverviewRuler ruler = new OverviewRuler(new DefaultMarkerAnnotationAccess(), 12,
				EditorsPlugin.getDefault().getSharedTextColors());
		final Field compositeField = aSourceViewerClazz.getDeclaredField("fComposite"); //$NON-NLS-1$
		compositeField.setAccessible(true);
		ruler.createControl((Composite) compositeField.get(fSourceViewer), (SourceViewer)fSourceViewer);
		ruler.setModel(fAnnotationModel);
		final Field overViewRulerField = aSourceViewerClazz.getDeclaredField("fOverviewRuler"); //$NON-NLS-1$
		overViewRulerField.setAccessible(true);
		if (overViewRulerField.get(fSourceViewer) == null) {
			overViewRulerField.set(fSourceViewer, ruler);
		}

		final SourceViewerDecorationSupport support = new SourceViewerDecorationSupport(fSourceViewer, ruler,
				new DefaultMarkerAnnotationAccess(), EditorsUI.getSharedTextColors());
		final Iterator<?> e2 = new MarkerAnnotationPreferences().getAnnotationPreferences().iterator();
		while (e2.hasNext()) {
			support.setAnnotationPreference((AnnotationPreference) e2.next());
		}
		support.install(EditorsUI.getPreferenceStore());
		((SourceViewer) fSourceViewer).getControl().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent aEvent) {
				support.dispose();
			}
		});

		//This overrides the call to ensureOverviewHoverManagerInstalled method in viewer
		final AbstractHoverInformationControlManager reviewInformationControlManager = new ReviewAnnotationInformationControlManager(
				ruler, fSourceViewer, annotationHover, annotationInformationControlCreator);
		final Field hoverManager = SourceViewer.class.getDeclaredField("fOverviewRulerHoveringController"); //$NON-NLS-1$
		hoverManager.setAccessible(true);
		hoverManager.set(fSourceViewer, reviewInformationControlManager);
		reviewInformationControlManager.install(ruler.getControl());
		if (ReviewAnnotationConfigFactory.getUseInformationControlReplacer()) {
		reviewInformationControlManager.getInternalAccessor().setInformationControlReplacer(
				new StickyHoverManager((SourceViewer)fSourceViewer));
		}
		((SourceViewer) fSourceViewer).showAnnotationsOverview(true);
		*/
	}
}