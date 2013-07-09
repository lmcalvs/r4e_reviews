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

package org.eclipse.mylyn.reviews.r4e_gerrit.core;

import org.eclipse.core.runtime.IStatus;

/**
 * An R4E Gerrit exception. Same as a QueryException but with an IStatus.
 * 
 * @author Francois Chouinard
 * @version 0.1
 */
public class R4EQueryException extends Exception {

    private static final long serialVersionUID = 1L;

    private final IStatus fStatus;
    
    public R4EQueryException(IStatus status, String message) {
        super(message);
        fStatus = status;
    }
    
    public IStatus getStatus() {
        return fStatus;
    }

}
