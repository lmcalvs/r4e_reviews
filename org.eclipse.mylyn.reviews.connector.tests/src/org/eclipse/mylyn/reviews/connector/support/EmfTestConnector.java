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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.mylyn.internal.reviews.connector.EmfCorePlugin;
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema.FieldFeature;
import org.eclipse.mylyn.reviews.connector.GenericEmfConnector;
import org.osgi.framework.Bundle;

public class EmfTestConnector extends GenericEmfConnector {

	public static final String CONNECTOR_KIND = "emftasks"; //$NON-NLS-1$

	public class TestTaskSchema extends BasicTestTaskSchema {

	}

	BasicTestTaskSchema testSchema = new MappedTestTaskSchema();

	public EmfTestConnector() {
	}

	@Override
	public String getConnectorKind() {
		return CONNECTOR_KIND;
	}

	@Override
	public String getLabel() {
		return "EMF Test"; //$NON-NLS-1$
	}

	@Override
	public EReference getContainmentReference() {
		return EcorePackage.Literals.EPACKAGE__ECLASSIFIERS;
	}

	@Override
	public EClass[] getTaskClasses() {
		return new EClass[] { EcorePackage.Literals.ECLASSIFIER };
	}

	@Override
	public FieldFeature[] getTaskFeatures() {
		DefaultTaskSchema parent = DefaultTaskSchema.getInstance();
		return new FieldFeature[] { new FieldFeature(parent.TASK_KEY, EcorePackage.Literals.ENAMED_ELEMENT__NAME),
				new FieldFeature(parent.SUMMARY, EcorePackage.Literals.ECLASSIFIER__INSTANCE_TYPE_NAME) };
	}

	@Override
	public Bundle getConnectorBundle() {
		return EmfCorePlugin.getDefault().getBundle();
	}

	@Override
	public EAttribute getContentsNameAttribute() {
		return null;
	}

	@Override
	public EAttribute[] getSearchAttributes() {
		// ignore
		return null;
	}

	@Override
	public EAttribute getTaskKeySequenceAttribute() {
		return null;
	}
}