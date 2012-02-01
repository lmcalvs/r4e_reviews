/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.filters;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;

public class ReviewFilesFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		R4EUIReviewBasic activeReview = R4EUIModelController.getActiveReview();

		//If not review is open, then there is nothing to display
		if (null == activeReview) {
			return false;
		}
		EList<Item> reviewItems = activeReview.getReview().getReviewItems();

		//TODO: path are mismatched
		if (element instanceof IFile) {
			for (Item reviewItem : reviewItems) {
				R4EItem r4eReviewItem = (R4EItem) reviewItem;
				EList<R4EFileContext> files = r4eReviewItem.getFileContextList();
				for (R4EFileContext file : files) {
					if (file.getTarget().getResource().getFullPath().equals(((IFile) element).getFullPath())) {
						return true;
					}
				}
			}
			return false;
		} else if (element instanceof CompilationUnit) {
			for (Item reviewItem : reviewItems) {
				R4EItem r4eReviewItem = (R4EItem) reviewItem;
				EList<R4EFileContext> files = r4eReviewItem.getFileContextList();
				for (R4EFileContext file : files) {
					if (file.getTarget()
							.getResource()
							.getProjectRelativePath()
							.equals(((CompilationUnit) element).getResource().getFullPath())) {
						return true;
					}
				}
			}
			return false;
		} else {
			// show all other elements
			return true;
		}

	}
}
