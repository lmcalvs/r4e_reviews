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

package org.eclipse.mylyn.reviews.example.emftasks;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleConnector;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema.FieldFeature;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public class EmfConnectorEncodingTest extends EmfBaseClientTest {

	String enDashString = "3\u2013Alpha";

	String hyphenString = "3-Alpha";

	@Override
	@Before
	public void setUp() throws Exception {
		File sourceDir = new File(TEST_DIR);
		File targetTestDir = new File(FileUtils.getTempDirectory().getAbsolutePath() + File.separator
				+ TARGET_FOLDER_NAME + File.separator);
		FileUtils.copyDirectory(sourceDir, targetTestDir);

		targetTestDirUri = URI.createFileURI(targetTestDir.getAbsolutePath());

		connector = new EmfExampleConnector() {

			private final DefaultTaskSchema parentSchema = DefaultTaskSchema.getInstance();

			private final FieldFeature[] PAIRS_2 = new FieldFeature[] {
					new FieldFeature(parentSchema.TASK_KEY, EmfTasksPackage.Literals.SIMPLE_TASK__DESCRIPTION),
					new FieldFeature(parentSchema.SUMMARY, EmfTasksPackage.Literals.SIMPLE_TASK__SUMMARY),
					new FieldFeature(parentSchema.DATE_COMPLETION,
							EmfTasksPackage.Literals.SIMPLE_TASK__COMPLETION_DATE),
					new FieldFeature(parentSchema.DATE_DUE, EmfTasksPackage.Literals.SIMPLE_TASK__DUE_DATE),
					new FieldFeature(parentSchema.DATE_MODIFICATION,
							EmfTasksPackage.Literals.SIMPLE_TASK__MODIFICATION_DATE),
					new FieldFeature(parentSchema.DATE_CREATION, EmfTasksPackage.Literals.SIMPLE_TASK__CREATION_DATE),
					new FieldFeature(parentSchema.PRIORITY, EmfTasksPackage.Literals.SIMPLE_TASK__PRIORITY),
					new FieldFeature(parentSchema.STATUS, EmfTasksPackage.Literals.SIMPLE_TASK__STATUS),
					new FieldFeature(parentSchema.RANK, EmfTasksPackage.Literals.SIMPLE_TASK__RANKING) };

			@Override
			public FieldFeature[] getTaskFeatures() {
				return PAIRS_2;
			}
		};
		client = connector.getClientManager().getClient(
				new TaskRepository(EmfExampleConnector.CONNECTOR_KIND, getFullUri(TEST_ENCODING_FILE)));
		client.open();
		assertThat(enDashString, is(not(hyphenString)));
	}

	@Test
	public void testGetObject() throws CoreException {
		//We should get in both cases
		EObject object = connector.getTaskObject(client.getRepository(), hyphenString, new NullProgressMonitor());
		assertThat(object, instanceOf(SimpleTask.class));
		assertThat(((SimpleTask) object).getDescription(), is(hyphenString));
		EObject object2 = connector.getTaskObject(client.getRepository(), hyphenString, new NullProgressMonitor());
		assertNotNull(object2);
		assertEquals(object, object2);
	}

	@Test
	public void testPullTaskData() throws CoreException {
		//We should get in both cases
		TaskData taskData = connector.getTaskData(client.getRepository(), enDashString, new NullProgressMonitor());
		assertThat(taskData.getTaskId(), is(enDashString));
		taskData = connector.getTaskData(client.getRepository(), hyphenString, new NullProgressMonitor());
		assertThat(taskData.getTaskId(), is(enDashString));
		TaskAttribute root = taskData.getRoot();
		TaskAttribute mappedAttribute = root.getMappedAttribute(TaskAttribute.TASK_KEY);
		assertNotNull(mappedAttribute);
		assertThat(hyphenString, is(mappedAttribute.getValue()));
		TaskAttribute attribute = root.getAttribute("emf.emftasks.simpletask.description");
		assertNotNull(attribute);
		assertThat(hyphenString, is(attribute.getValue()));
	}

	@Test
	public void testPostTaskDataEncoded() throws CoreException {
		TaskData taskData = connector.getTaskData(client.getRepository(), enDashString, new NullProgressMonitor());
		TaskAttribute root = taskData.getRoot();
		TaskAttribute attribute = root.getAttribute("emf.emftasks.simpletask.summary");

		assertThat("Encoded Task", is(attribute.getValue()));
		attribute.setValue("Modified Encoded Task");
		HashSet<TaskAttribute> oldAttributes = new HashSet<TaskAttribute>();
		oldAttributes.add(attribute);
		connector.postTaskData(client.getRepository(), taskData, oldAttributes, new NullProgressMonitor());
		EObject object = connector.getTaskObject(client.getRepository(), hyphenString, new NullProgressMonitor());
		String summary = connector.getEmfMapper(client.getRepository()).getEmfString(object, TaskAttribute.SUMMARY);
		assertThat(summary, is("Modified Encoded Task"));
		Date modified = (Date) connector.getEmfMapper(client.getRepository()).getEmfValue(object,
				TaskAttribute.DATE_MODIFICATION);
		assertNotNull(modified);
		Calendar calendar = Calendar.getInstance();
		Date current = calendar.getTime();
		assertThat(modified.getTime() + 5000, greaterThan(current.getTime()));

		TaskAttribute dateAttribute = root.getMappedAttribute(TaskAttribute.DATE_MODIFICATION);
		DateFormat testFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertThat(dateAttribute.getValue(), not(startsWith(testFormat.format(current))));
	}
}
