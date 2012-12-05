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

package org.eclipse.mylyn.reviews.connector.support;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema;

public class BasicTestTaskSchema extends EmfTaskSchema {

	@Override
	protected EClass[] getSchemaEClasses() {
		return new EClass[] { EcorePackage.Literals.EANNOTATION, EcorePackage.Literals.ETYPED_ELEMENT };
	}

	@Override
	protected FieldFeature[] getSchemaPairs() {
		return new FieldFeature[] {};
	}
}