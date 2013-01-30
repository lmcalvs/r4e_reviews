/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.reviews.r4e.connector.ui.editor;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.mylyn.commons.workbench.CommonImageManger;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.swt.graphics.Image;

/**
 * A simple provider for review items. To be merged with Reviews item label support.
 * 
 * @author Miles Parker
 */
public class ReviewItemLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private final CommonImageManger imageManager;

	public ReviewItemLabelProvider() {
		imageManager = new CommonImageManger();
	}

	@Override
	public void dispose() {
		super.dispose();
		imageManager.dispose();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof R4EFileContext) {
			R4EFileContext item = (R4EFileContext) element;
			if (item.getTarget() != null) {
				return getImage(item.getTarget());
			}
			return getImage(item.getBase());
		}
		if (element instanceof R4EFileVersion) {
			R4EFileVersion item = (R4EFileVersion) element;
			return imageManager.getFileImage(item.getName());
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof R4EFileContext) {
			R4EFileContext item = (R4EFileContext) element;
			if (item.getTarget() != null) {
				return getText(item.getTarget());
			}
			return getText(item.getBase());
		}
		if (element instanceof R4EFileVersion) {
			R4EFileVersion item = (R4EFileVersion) element;
			return item.getName();
		}
		return super.getText(element);
	}

	public StyledString getStyledText(Object element) {
		if (element instanceof R4EFileContext) {
			R4EFileContext item = (R4EFileContext) element;
			if (item.getTarget() != null) {
				return getStyledText(item.getTarget());
			}
			return getStyledText(item.getBase());
		}

		String text = getText(element);
		if (text != null) {
			StyledString styledString = new StyledString(text);
			return styledString;
		}
		return new StyledString();
	}

}
