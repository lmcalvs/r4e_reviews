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
 * This class implements the hover that is shown for Review annotations
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.internal.annotation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHoverExtension;
import org.eclipse.jface.text.source.IAnnotationHoverExtension2;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ILineRange;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.ISourceViewerExtension2;
import org.eclipse.jface.text.source.LineRange;
import org.eclipse.jface.text.source.projection.AnnotationBag;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewAnnotationConfigFactory;
import org.eclipse.mylyn.reviews.frame.ui.annotation.impl.ReviewAnnotationHoverInput;

/**
 * @author Shawn Minto
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewAnnotationHover implements IAnnotationHover, IAnnotationHoverExtension, IAnnotationHoverExtension2 {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field informationControlCreator.
	 */
	private final IInformationControlCreator fInformationControlCreator;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewAnnotationHover.
	 */
	public ReviewAnnotationHover() {
		fInformationControlCreator = ReviewAnnotationConfigFactory.createInformationControlCreator();
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getHoverInfo.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aLineNumber
	 *            int
	 * @return String
	 * @see org.eclipse.jface.text.source.IAnnotationHover#getHoverInfo(ISourceViewer, int)
	 */
	public String getHoverInfo(ISourceViewer aSourceViewer, int aLineNumber) {
		return null; //not used
	}

	/**
	 * Method getHoverControlCreator.
	 * 
	 * @return IInformationControlCreator
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#getHoverControlCreator()
	 */
	public IInformationControlCreator getHoverControlCreator() {
		return fInformationControlCreator;
	}

	/**
	 * Method getAnnotationModel.
	 * 
	 * @param aViewer
	 *            ISourceViewer
	 * @return IAnnotationModel
	 */
	private IAnnotationModel getAnnotationModel(ISourceViewer aViewer) {
		if (aViewer instanceof ISourceViewerExtension2) {
			final ISourceViewerExtension2 extension = (ISourceViewerExtension2) aViewer;
			return extension.getVisualAnnotationModel();
		}
		return aViewer.getAnnotationModel();
	}

	/**
	 * Method isContained.
	 * 
	 * @param aAnnotation
	 *            Annotation
	 * @param aPosition
	 *            Position
	 * @param aAnnotations
	 *            List<IReviewAnnotation>
	 * @return boolean
	 */
	private boolean isContained(Annotation aAnnotation, Position aPosition, List<IReviewAnnotation> aAnnotations) {
		if (!(aAnnotation instanceof IReviewAnnotation)) {
			return false;
		}
		return !aAnnotations.contains(aAnnotation);
	}

	/**
	 * Method getHoverInfo.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aLineRange
	 *            ILineRange
	 * @param aVisibleNumberOfLines
	 *            int
	 * @return Object
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#getHoverInfo(ISourceViewer, ILineRange, int)
	 */
	public Object getHoverInfo(ISourceViewer aSourceViewer, ILineRange aLineRange, int aVisibleNumberOfLines) {
		final List<IReviewAnnotation> annotationsForLine = getAnnotationsForLine(aSourceViewer,
				aLineRange.getStartLine());
		if (annotationsForLine.size() > 0) {
			final IAnnotationModel model = aSourceViewer.getAnnotationModel();
			if (model instanceof IReviewAnnotationModel) {
				return new ReviewAnnotationHoverInput(annotationsForLine);
			}
		}
		return getHoverInfo(aSourceViewer, aLineRange.getStartLine());
	}

	/**
	 * Method canHandleMouseCursor.
	 * 
	 * @return boolean
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#canHandleMouseCursor()
	 */
	public boolean canHandleMouseCursor() {
		return true;
	}

	/**
	 * Method canHandleMouseWheel.
	 * 
	 * @return boolean
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension2#canHandleMouseWheel()
	 */
	public boolean canHandleMouseWheel() {
		return true;
	}

	/**
	 * Method getHoverLineRange.
	 * 
	 * @param aViewer
	 *            ISourceViewer
	 * @param aLineNumber
	 *            int
	 * @return ILineRange
	 * @see org.eclipse.jface.text.source.IAnnotationHoverExtension#getHoverLineRange(ISourceViewer, int)
	 */
	public ILineRange getHoverLineRange(ISourceViewer aViewer, int aLineNumber) {
		final List<IReviewAnnotation> reviewAnnotations = getAnnotationsForLine(aViewer, aLineNumber);
		if (reviewAnnotations.size() > 0) {
			final IDocument document = aViewer.getDocument();
			int lowestStart = Integer.MAX_VALUE;
			int highestEnd = 0;
			for (IReviewAnnotation a : reviewAnnotations) {
				Position p = a.getPosition();
				try {

					int start = document.getLineOfOffset(p.offset);
					int end = document.getLineOfOffset(p.offset + p.length);

					if (start < lowestStart) {
						lowestStart = start;
					}

					if (end > highestEnd) {
						highestEnd = end;
					}
				} catch (BadLocationException e) {
					if (null != ReviewAnnotationConfigFactory.getPlugin()) {
						ReviewAnnotationConfigFactory.getPlugin().getLog().log(
							new Status(IStatus.ERROR, 
									ReviewAnnotationConfigFactory.getPlugin().getBundle().getSymbolicName(), 
									IStatus.OK, e.getMessage(), e));
					}
				}
			}
			if (lowestStart != Integer.MAX_VALUE) {
				return new LineRange(lowestStart, highestEnd - lowestStart);
			}
			return new LineRange(aLineNumber, 1);
		}
		return new LineRange(aLineNumber, 1);
	}

	/**
	 * Method isRulerLine.
	 * 
	 * @param aPosition
	 *            Position
	 * @param aDocument
	 *            IDocument
	 * @param aLine
	 *            int
	 * @return boolean
	 */
	private boolean isRulerLine(Position aPosition, IDocument aDocument, int aLine) {
		if ((aPosition.getOffset() > -1) && (aPosition.getLength() > -1)) {
			try {
				return aLine == aDocument.getLineOfOffset(aPosition.getOffset());
			} catch (BadLocationException ex) {
				if (null != ReviewAnnotationConfigFactory.getPlugin()) {
					ReviewAnnotationConfigFactory.getPlugin().getLog().log(
						new Status(IStatus.ERROR, 
								ReviewAnnotationConfigFactory.getPlugin().getBundle().getSymbolicName(), 
								IStatus.OK, ex.getMessage(), ex));
				}
			}
		}
		return false;
	}

	/**
	 * Method getAnnotationsForLine.
	 * 
	 * @param aViewer
	 *            ISourceViewer
	 * @param aLine
	 *            int
	 * @return List<IReviewAnnotation>
	 */
	@SuppressWarnings("unchecked")
	private List<IReviewAnnotation> getAnnotationsForLine(ISourceViewer aViewer, int aLine) {
		final IAnnotationModel model = getAnnotationModel(aViewer);
		if (model == null) {
			return Collections.emptyList();
		}

		final IDocument document = aViewer.getDocument();
		final List<IReviewAnnotation> reviewAnnotations = new ArrayList<IReviewAnnotation>();

		for (final Iterator<Annotation> it = model.getAnnotationIterator(); it.hasNext();) {
			Annotation annotation = it.next();
			Position position = model.getPosition(annotation);
			if (position == null) {
				continue;
			}

			if (!isRulerLine(position, document, aLine)) {
				continue;
			}

			if (annotation instanceof AnnotationBag) {
				AnnotationBag bag = (AnnotationBag) annotation;
				Iterator<Annotation> e = bag.iterator();
				while (e.hasNext()) {
					annotation = e.next();
					position = model.getPosition(annotation);
					if (((position != null) && isContained(annotation, position, reviewAnnotations))
							&& annotation instanceof IReviewAnnotation) {
						reviewAnnotations.add((IReviewAnnotation) annotation);
					}
				}
				continue;
			}

			if (isContained(annotation, position, reviewAnnotations) && annotation instanceof IReviewAnnotation) {
				reviewAnnotations.add((IReviewAnnotation) annotation);
			}
		}
		return reviewAnnotations;
	}
}
