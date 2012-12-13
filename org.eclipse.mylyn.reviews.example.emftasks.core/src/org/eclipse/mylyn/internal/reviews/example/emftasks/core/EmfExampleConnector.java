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
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *   Others (includes code modified from GerritConnector in org.eclipse.mylyn.gerit, see author annotations.)
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.example.emftasks.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema.FieldFeature;
import org.eclipse.mylyn.reviews.connector.GenericEmfConnector;
import org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage;
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;
import org.osgi.framework.Bundle;

/**
 * Core connector support for the EMF example.
 * 
 * @author Miles Parker
 */
public class EmfExampleConnector extends GenericEmfConnector {

	/**
	 * Connector kind
	 */
	public static final String CONNECTOR_KIND = "org.eclipse.emftasks"; //$NON-NLS-1$

	/**
	 * Label for the connector.
	 */
	public static final String CONNECTOR_LABEL = "Emf Tasks Example"; //$NON-NLS-1$

	private final EClass[] CLASSES = new EClass[] { EmfTasksPackage.Literals.SIMPLE_TASK };

	private final EAttribute[] SEARCH_ATTRIBUTES = new EAttribute[] { EmfTasksPackage.Literals.SIMPLE_TASK__SUMMARY,
			EmfTasksPackage.Literals.SIMPLE_TASK__ID, EmfTasksPackage.Literals.SIMPLE_TASK__DESCRIPTION,
			EmfTasksPackage.Literals.SIMPLE_TASK__PRIORITY, EmfTasksPackage.Literals.SIMPLE_TASK__STATUS,
			EmfTasksPackage.Literals.SIMPLE_TASK__DUE_DATE, EmfTasksPackage.Literals.SIMPLE_TASK__CREATION_DATE,
			EmfTasksPackage.Literals.SIMPLE_TASK__MODIFICATION_DATE,
			EmfTasksPackage.Literals.SIMPLE_TASK__COMPLETION_DATE };

	private final DefaultTaskSchema parentSchema = DefaultTaskSchema.getInstance();

	private final FieldFeature[] PAIRS = new FieldFeature[] {
			new FieldFeature(parentSchema.TASK_KEY, EmfTasksPackage.Literals.SIMPLE_TASK__ID),
			new FieldFeature(parentSchema.DESCRIPTION, EmfTasksPackage.Literals.SIMPLE_TASK__DESCRIPTION),
			new FieldFeature(parentSchema.SUMMARY, EmfTasksPackage.Literals.SIMPLE_TASK__SUMMARY),
			new FieldFeature(parentSchema.DATE_COMPLETION, EmfTasksPackage.Literals.SIMPLE_TASK__COMPLETION_DATE),
			new FieldFeature(parentSchema.DATE_DUE, EmfTasksPackage.Literals.SIMPLE_TASK__DUE_DATE),
			new FieldFeature(parentSchema.DATE_MODIFICATION, EmfTasksPackage.Literals.SIMPLE_TASK__MODIFICATION_DATE),
			new FieldFeature(parentSchema.DATE_CREATION, EmfTasksPackage.Literals.SIMPLE_TASK__CREATION_DATE),
			new FieldFeature(parentSchema.PRIORITY, EmfTasksPackage.Literals.SIMPLE_TASK__PRIORITY),
			new FieldFeature(parentSchema.STATUS, EmfTasksPackage.Literals.SIMPLE_TASK__STATUS),
			new FieldFeature(parentSchema.RANK, EmfTasksPackage.Literals.SIMPLE_TASK__RANKING) };

	public EmfExampleConnector() {
		if (EmfExampleCorePlugin.getDefault() != null) {
			EmfExampleCorePlugin.getDefault().setConnector(this);
		}
	}

	@Override
	public String getConnectorKind() {
		return CONNECTOR_KIND;
	}

	@Override
	public String getLabel() {
		return CONNECTOR_LABEL;
	}

	@Override
	public EReference getContainmentReference() {
		return EmfTasksPackage.Literals.TASK_COLLECTION__TASKS;
	}

	@Override
	public Bundle getConnectorBundle() {
		return EmfExampleCorePlugin.getDefault().getBundle();
	}

	@Override
	public EClass[] getTaskClasses() {
		return CLASSES;
	}

	@Override
	public FieldFeature[] getTaskFeatures() {
		return PAIRS;
	}

	@Override
	public EAttribute getContentsNameAttribute() {
		return EmfTasksPackage.Literals.TASK_COLLECTION__LABEL;
	}

	@Override
	public EAttribute[] getSearchAttributes() {
		return SEARCH_ATTRIBUTES;
	}

	@Override
	public EAttribute getTaskKeySequenceAttribute() {
		return EmfTasksPackage.Literals.TASK_COLLECTION__LAST_TASK_ID;
	}
}
