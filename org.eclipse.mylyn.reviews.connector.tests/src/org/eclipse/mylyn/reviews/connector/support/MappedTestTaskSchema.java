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
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;

public class MappedTestTaskSchema extends BasicTestTaskSchema {

	@Override
	protected EClass[] getSchemaEClasses() {
		return new EClass[] { EcorePackage.Literals.EPACKAGE };
	}

	@Override
	protected FieldFeature[] getSchemaPairs() {
		DefaultTaskSchema parent = DefaultTaskSchema.getInstance();
		return new FieldFeature[] { new FieldFeature(parent.TASK_KEY, EcorePackage.Literals.EPACKAGE__NS_URI),
				new FieldFeature(parent.SUMMARY, EcorePackage.Literals.EPACKAGE__NS_PREFIX) };
	}
}