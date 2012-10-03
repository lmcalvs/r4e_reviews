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

package org.eclipse.mylyn.reviews.r4e.core.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

public class CommonModelFileConverter extends SimpleFileConverter {
	static Replacement[] REPLACEMENTS = new Replacement[] {
			new Replacement("R4E_ROLE_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$
			new Replacement("R4E_ANOMALY_STATE_(\\s*)", "$1"), new Replacement("R4E_REVIEW_PHASE_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
			new Replacement("R4E_REVIEW_DECISION_(\\s*)", "$1"), new Replacement("R4E_REVIEW_TYPE_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			new Replacement("R4E_CLASS_(\\s*)", "$1"), new Replacement("R4E_RANK_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			new Replacement("R4E_(\\s*)", "$1"), new Replacement("\\suser=\"", " author=\""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			new Replacement("<location\\sxsi", "<locations xsi"), new Replacement(" fragmentVersion=\\S*\"", ""), new Replacement("(<r4ecore:R4EReview.*\" )(name=\".*>)", "$1fragmentVersion=\"2\\.0\\.0\" $2") }; //$NON-NLS-1$ //$NON-NLS-2$

	public CommonModelFileConverter(EObject object) {
		super(object, "xrer", new String[] { "git" }, REPLACEMENTS); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public CommonModelFileConverter(URI uri) {
		super(uri, "xrer", new String[] { "git" }, REPLACEMENTS); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static void main(String[] args) {
		URI uri = URI.createFileURI(args[0]);
		CommonModelFileConverter converter = new CommonModelFileConverter(uri);
		converter.setUser(true);
		converter.schedule();
		while (converter.getResult() == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.err.println(converter.getResult().getMessage());
	}
}
