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

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskSchema.Field;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.reviews.connector.support.BasicTestTaskSchema;
import org.eclipse.mylyn.reviews.connector.support.MappedTestTaskSchema;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public class EmfTaskSchemaTest extends TestCase {

	@Override
	@Before
	protected void setUp() throws Exception {
		// ignore
		super.setUp();
	}

	@Test
	public void testBasicSchema() {
		BasicTestTaskSchema schema = new BasicTestTaskSchema();
		schema.initialize();
		Field field = schema.getFieldByFeature(EcorePackage.Literals.EANNOTATION__SOURCE);
		assertNotNull(field);
		assertEquals("emf.ecore.eannotation.source", field.getKey());
		assertEquals(TaskAttribute.KIND_DEFAULT, field.getKind());
		assertEquals("Source", field.getLabel());
		assertEquals("emf.ecore.eannotation.source", field.getIndexKey());
	}

	@Test
	public void testTypes() {
		BasicTestTaskSchema schema = new BasicTestTaskSchema();
		schema.initialize();
		Field field = schema.getFieldByFeature(EcorePackage.Literals.EANNOTATION__SOURCE);
		assertEquals(TaskAttribute.TYPE_LONG_RICH_TEXT, field.getType());
//		field = schema.getFieldByFeature(EcorePackage.Literals.EANNOTATION__CONTENTS);
//		assertEquals(EmfTaskSchema.TYPE_EMF_REFERENCE, field.getType());
		field = schema.getFieldByFeature(EcorePackage.Literals.ETYPED_ELEMENT__LOWER_BOUND);
		assertEquals(TaskAttribute.TYPE_INTEGER, field.getType());
		field = schema.getFieldByFeature(EcorePackage.Literals.ETYPED_ELEMENT__REQUIRED);
		assertEquals(TaskAttribute.TYPE_BOOLEAN, field.getType());
		//TODO check more types
	}

	@Test
	public void testMappedTaskData() {
		MappedTestTaskSchema schema = new MappedTestTaskSchema();
		schema.initialize();
		String featureKey = schema.getKey(schema.getFeature(TaskAttribute.TASK_KEY));
		assertEquals("emf.ecore.epackage.nsuri", featureKey);
		featureKey = schema.getKey(schema.getFeature(TaskAttribute.SUMMARY));
		assertEquals("emf.ecore.epackage.nsprefix", featureKey);
	}
}
