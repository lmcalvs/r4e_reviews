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

package org.eclipse.mylyn.reviews.connector;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskSchema;
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;

public abstract class EmfTaskSchema extends AbstractTaskSchema {

	public static final String TYPE_EMF_REFERENCE = "emf.reference"; //$NON-NLS-1$

	public static final String TYPE_EMF_GENERIC = "emf.generic"; //$NON-NLS-1$

	public static final String TYPE_EMF_UNSUPPORTED = "emf.unsupported"; //$NON-NLS-1$

	private final DefaultTaskSchema parent = DefaultTaskSchema.getInstance();

	public static class FieldFeature {
		Field parentField;

		EStructuralFeature feature;

		Flag[] flags;

		boolean readOnly;

		public FieldFeature(Field taskAttribute, EStructuralFeature emfFeature) {
			super();
			this.parentField = taskAttribute;
			this.feature = emfFeature;
		}

		public FieldFeature(Field taskAttribute, EStructuralFeature emfFeature, boolean readOnly, Flag... flags) {
			this(taskAttribute, emfFeature);
			this.readOnly = readOnly;
			this.flags = flags;
		}

		@Override
		public int hashCode() {
			// ignore
			return parentField.hashCode() * 31 + feature.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			return other instanceof FieldFeature && ObjectUtils.equals(parentField, ((FieldFeature) other).parentField)
					&& ObjectUtils.equals(feature, ((FieldFeature) other).feature);
		}
	}

	private final Map<EStructuralFeature, Field> fieldByFeature = new HashMap<EStructuralFeature, Field>();

	private final Map<String, EStructuralFeature> featureByTaskKey = new HashMap<String, EStructuralFeature>();

	private boolean keyFieldDefined;

	public void initialize() {
		for (FieldFeature fieldFeature : getSchemaPairs()) {
			if (fieldFeature.parentField.getKey() == parent.TASK_KEY.getKey()) {
				keyFieldDefined = true;
				break;
			}
		}
		for (FieldFeature fieldFeature : getSchemaPairs()) {
			createField(fieldFeature);
		}
		for (EClass schemaClass : getSchemaEClasses()) {
			for (EStructuralFeature feature : schemaClass.getEAllStructuralFeatures()) {
				//Don't replace explicitly mapped features.
				if (isSupported(feature) && fieldByFeature.get(feature) == null) {
					createField(feature);
				}
			}
		}
	}

	@Override
	public void initialize(TaskData taskData) {
		if (!keyFieldDefined) {
			DefaultTaskSchema.getInstance().TASK_KEY.createAttribute(taskData.getRoot());
		}
		for (Field field : getFields()) {
			TaskAttribute attribute = field.createAttribute(taskData.getRoot());
			EStructuralFeature feature = getFeature(attribute.getId());
			if (feature instanceof EAttribute) {
				EAttribute emfAttribute = (EAttribute) feature;
				if (emfAttribute.getEAttributeType() instanceof EEnum) {
					EEnum enumerator = (EEnum) emfAttribute.getEAttributeType();
					for (EEnumLiteral literal : enumerator.getELiterals()) {
						attribute.putOption(literal.getLiteral(), literal.getName());
					}
				}
			}
		}
	}

	protected abstract EClass[] getSchemaEClasses();

	protected abstract FieldFeature[] getSchemaPairs();

	public String getTaskType(EStructuralFeature feature) {
		if (feature instanceof EReference) {
			return TYPE_EMF_REFERENCE;
		}
		int classifierID = feature.getEType().getClassifierID();
		switch (classifierID) {
		case EcorePackage.EBIG_DECIMAL:
			return TaskAttribute.TYPE_DOUBLE;
		case EcorePackage.EBIG_INTEGER:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.EBOOLEAN:
			return TaskAttribute.TYPE_BOOLEAN;
		case EcorePackage.EBOOLEAN_OBJECT:
			return TaskAttribute.TYPE_BOOLEAN;
		case EcorePackage.EBYTE:
			return TaskAttribute.TYPE_SHORT_TEXT;
		case EcorePackage.EBYTE_OBJECT:
			return TaskAttribute.TYPE_SHORT_TEXT;
		case EcorePackage.ECHAR:
			return TaskAttribute.TYPE_SHORT_TEXT;
		case EcorePackage.ECHARACTER_OBJECT:
			return TaskAttribute.TYPE_SHORT_TEXT;
		case EcorePackage.EDATE:
			return TaskAttribute.TYPE_DATE;
		case EcorePackage.EDOUBLE:
			return TaskAttribute.TYPE_DOUBLE;
		case EcorePackage.EDOUBLE_OBJECT:
			return TaskAttribute.TYPE_DOUBLE;
		case EcorePackage.EFLOAT:
			return TaskAttribute.TYPE_DOUBLE;
		case EcorePackage.EFLOAT_OBJECT:
			return TaskAttribute.TYPE_DOUBLE;
		case EcorePackage.EINT:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.EINTEGER_OBJECT:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.ELONG:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.ELONG_OBJECT:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.ESHORT:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.ESHORT_OBJECT:
			return TaskAttribute.TYPE_INTEGER;
		case EcorePackage.ESTRING:
			return TaskAttribute.TYPE_LONG_RICH_TEXT;
		case EcorePackage.EBYTE_ARRAY:
			return TYPE_EMF_GENERIC;
		case EcorePackage.EDIAGNOSTIC_CHAIN:
			return TYPE_EMF_UNSUPPORTED;
		default:
			return TYPE_EMF_UNSUPPORTED;
		}
	}

	public boolean isSupported(EStructuralFeature feature) {
		return !getTaskType(feature).startsWith("emf"); //$NON-NLS-1$
	}

	private void addEmfField(EStructuralFeature feature, Field field) {
		fieldByFeature.put(feature, field);
		featureByTaskKey.put(field.getKey(), feature);
	}

	protected Field createField(EStructuralFeature feature, Flag... flags) {
		String key = getKey(feature);
		String label = getLabel(feature);
		String type = getTaskType(feature);
		flags = (Flag[]) ArrayUtils.add(flags, Flag.ATTRIBUTE);
		Field field = createField(key, label, type, key, flags);
		addEmfField(feature, field);
		return field;
	}

	protected Field createField(FieldFeature fieldFeature) {
		Field parentField = fieldFeature.parentField;
		EStructuralFeature emfAttribute = fieldFeature.feature;
		String key = getKey(emfAttribute);
		FieldFactory factory = inheritFrom(parentField).key(key);
		if (fieldFeature.readOnly) {
			factory.addFlags(Flag.READ_ONLY);
		} else {
			factory.removeFlags(Flag.READ_ONLY);
		}
		Field field = factory.create();
		addEmfField(emfAttribute, field);
		featureByTaskKey.put(parentField.getKey(), emfAttribute);
		return field;
	}

	public String getKey(EStructuralFeature feature) {
		return "emf." + feature.getEContainingClass().getEPackage().getName() + "." //$NON-NLS-1$//$NON-NLS-2$
				+ feature.getEContainingClass().getName().toLowerCase() + "." + feature.getName().toLowerCase(); //$NON-NLS-1$
	}

	public String getLabel(EStructuralFeature feature) {
		return WordUtils.capitalizeFully(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(feature.getName()),
				' '));
	}

	/**
	 * Finds the feature corresponding to the supplied task key. Features are matched on both attributes and matched
	 * attributes.
	 * 
	 * @param feature
	 * @return
	 */
	public EStructuralFeature getFeature(String key) {
		return featureByTaskKey.get(key);
	}

	/**
	 * Finds the field corresponding to the given structural feature.
	 * 
	 * @param feature
	 * @return
	 */
	public Field getFieldByFeature(EStructuralFeature feature) {
		return fieldByFeature.get(feature);
	}

	public TaskAttributeMapper getAttributeMapper(TaskRepository repository) {
		return new EmfAttributeMapper(this, repository);
	}
}