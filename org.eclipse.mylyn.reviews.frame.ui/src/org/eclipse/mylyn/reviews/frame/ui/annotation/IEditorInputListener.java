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
 * This interface defines a generic editor input listener for inline commmenting 
 * functionality
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation;

import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IEditorInputListener extends ITextInputListener {

	/**
	 * Gets the Text Editor for the input we are listening to.
	 * 
	 * @return ITextEditor - the Text Editor instance
	 */
	ITextEditor getEditor();

}
