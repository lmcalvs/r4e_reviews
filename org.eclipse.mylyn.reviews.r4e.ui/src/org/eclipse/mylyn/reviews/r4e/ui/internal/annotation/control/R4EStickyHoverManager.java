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
 * This class extends the StickyHoverManager so that it is not hidden when the
 * parent control is still visible
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.jface.internal.text.StickyHoverManager;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorView;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("restriction")
public class R4EStickyHoverManager extends StickyHoverManager {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EStickyHoverManager.
	 * 
	 * @param sourceViewer
	 *            TextViewer
	 */
	public R4EStickyHoverManager(ISourceViewer sourceViewer) {
		super((SourceViewer) sourceViewer);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method hideInformationControl.
	 * 
	 * @see StickyHoverManager#hideInformationControl()
	 */
	@Override
	public void hideInformationControl() {
		//We do this to avoid losing the annotation control widget when the Navigator view is activated to update the properties view.  See 
		//R4EAnnotationInformationControl#addAnnotationsInformation for more info
		if (!R4EAnnotationInformationControl.isPropertyViewBeingActivated()) {
			if (R4EAnnotationInformationControl.getPreSelectionActivePart() instanceof CompareEditor
					|| R4EAnnotationInformationControl.getPreSelectionActivePart() instanceof ReviewNavigatorView) {
				R4EAnnotationInformationControl.setPreSelectionActivePart(null);
			} else {
				super.hideInformationControl();
			}
		}
	}
}
