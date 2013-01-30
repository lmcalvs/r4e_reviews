/*******************************************************************************
 * Copyright (c) 2013 Ericsson
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
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.r4e.connector;

import static org.junit.Assert.assertEquals;

import org.eclipse.mylyn.reviews.connector.EmfTaskSchema;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskSchema.Field;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.junit.Test;

/**
 * @author Miles Parker
 */
public class R4ETaskSchemaTest {

	@Test
	public void testFeatureKeys() {
		EmfTaskSchema schema = new R4EConnector().createTaskSchema();
		schema.initialize();

		Field field = schema.getFieldByFeature(RModelPackage.Literals.R4E_REVIEW__NAME);
		assertEquals("emf.model.r4ereview.name", field.getKey());
		assertEquals("shortRichText", field.getType());

		field = schema.getFieldByFeature(RModelPackage.Literals.R4E_REVIEW__CREATED_BY);
		assertEquals("emf.model.r4ereview.createdby", field.getKey());
		assertEquals("person", field.getType());

		field = schema.getFieldByFeature(RModelPackage.Literals.R4E_REVIEW__DUE_DATE);
		assertEquals("emf.model.r4ereview.duedate", field.getKey());
		assertEquals("date", field.getType());

		field = schema.getFieldByFeature(RModelPackage.Literals.R4E_REVIEW__END_DATE);
		assertEquals("emf.model.r4ereview.enddate", field.getKey());
		assertEquals("date", field.getType());
	}

	@Test
	public void testMappedKeys() {
		EmfTaskSchema schema = new R4EConnector().createTaskSchema();
		schema.initialize();

		String key = schema.getKey(schema.getFeature(TaskAttribute.SUMMARY));
		assertEquals("emf.model.r4ereview.name", key);

		key = schema.getKey(schema.getFeature(TaskAttribute.DATE_COMPLETION));
		assertEquals("emf.model.r4ereview.enddate", key);
	}
}
