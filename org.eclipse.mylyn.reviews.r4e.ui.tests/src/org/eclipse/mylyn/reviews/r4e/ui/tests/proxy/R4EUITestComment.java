/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Proxy class used to access/control the UI Comment element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.CommentInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ICommentInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;

@SuppressWarnings("restriction")
public class R4EUITestComment extends R4EUITestElement {

	public R4EUITestComment(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	public R4EUIComment createComment(R4EUIAnomalyBasic aAnomaly, String aComment) {

		//Inject mockup dialog for New Comment
		ICommentInputDialog mockCommentDialog = mock(CommentInputDialog.class);
		R4EUIDialogFactory.getInstance().setCommentInputDialog(mockCommentDialog);

		//Here we need to stub the RuleAreaInputDialog get methods to return what we want
		when(mockCommentDialog.getCommentValue()).thenReturn(aComment);
		when(mockCommentDialog.open()).thenReturn(Window.OK);

		return (R4EUIComment) fParentProxy.getCommandProxy().runNewChildElement(aAnomaly);
	}
}
