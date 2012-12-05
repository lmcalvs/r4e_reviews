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

import java.util.Date;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskSchema.Field;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;

public class EmfAttributeMapper extends TaskAttributeMapper {

	private final EmfTaskSchema schema;

	public EmfAttributeMapper(EmfTaskSchema emfTaskSchema, TaskRepository taskRepository) {
		super(taskRepository);
		this.schema = emfTaskSchema;
	}

	@Override
	public String mapToRepositoryKey(TaskAttribute parent, String key) {
		EStructuralFeature feature = schema.getFeature(key);
		if (feature != null) {
			return schema.getKey(feature);
		}
		return key;
	}

	public boolean copyEmfToTask(EObject parent, TaskAttribute attribute) throws CoreException {
		EStructuralFeature feature = schema.getFeature(attribute.getId());
		//TODO support object references
		if (feature instanceof EAttribute) {
			EDataType type = ((EAttribute) feature).getEAttributeType();
			EFactory factory = type.getEPackage().getEFactoryInstance();
			Object value = parent.eGet(feature);
			if (value == null) {
				attribute.clearValues();
			} else {
				String stringValue = factory.convertToString(type, value);
				if (!StringUtils.equals(attribute.getValue(), stringValue)) {
					attribute.setValue(stringValue);
					return true;
				}
			}
		}
		return false;
	}

	public boolean copyTaskToEmf(TaskAttribute attribute, EObject object) throws CoreException {
		EStructuralFeature feature = schema.getFeature(attribute.getId());
		//TODO support object references
		if (feature instanceof EAttribute) {
			EDataType type = ((EAttribute) feature).getEAttributeType();
			EFactory factory = type.getEPackage().getEFactoryInstance();
			String newValue = attribute.getValue();
			Object newObject = factory.createFromString(type, newValue);
			Object emfOldValue = object.eGet(feature);
			if (!ObjectUtils.equals(emfOldValue, newObject)) {
				object.eSet(feature, newObject);
				return true;
			}
		}
		return false;
	}

	@Override
	public Date getDateValue(TaskAttribute attribute) {
		return getDateValue(attribute.getValue());
	}

	public Date getDateValue(String value) {
		if (!value.equals("") && !value.equals("null")) {
			return (Date) EcoreFactory.eINSTANCE.createFromString(EcorePackage.Literals.EDATE, value);
		}
		return null;
	}

	public String getDateString(Date date) {
		return EcoreFactory.eINSTANCE.convertToString(EcorePackage.Literals.EDATE, date);
	}

	@Override
	public void setDateValue(TaskAttribute attribute, Date date) {
		if (date != null) {
			super.setValue(attribute, getDateString(date));
		} else {
			attribute.clearValues();
		}
	}

	public boolean copyEmfToTask(EObject parent, EStructuralFeature feature, TaskData data) throws CoreException {
		Field field = schema.getFieldByFeature(feature);
		if (field != null) {
			TaskAttribute newAttribute = field.createAttribute(data.getRoot());
			return copyEmfToTask(parent, newAttribute);
		}
		return false;
	}

	public Object getEmf(EObject taskObject, String key) {
		EStructuralFeature feature = schema.getFeature(key);
		if (feature instanceof EAttribute) {
			return taskObject.eGet(feature);
		}
		return null;
	}

	public Object getEmfValue(EObject taskObject, String key) {
		EStructuralFeature feature = schema.getFeature(key);
		if (feature instanceof EAttribute) {
			Object value = taskObject.eGet(feature);
			return value;
		}
		return null;
	}

	public String getEmfString(EObject taskObject, String key) {
		Object value = getEmfValue(taskObject, key);
		if (value != null) {
			EStructuralFeature feature = schema.getFeature(key);
			EFactory factory = feature.getEType().getEPackage().getEFactoryInstance();
			//Handles basic string case in addition to simple coercions
			return factory.convertToString((EDataType) feature.getEType(), value);
		}
		return null;
	}
}