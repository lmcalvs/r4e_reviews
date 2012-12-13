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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.tasks.core.RepositoryResponse;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.junit.Test;

@SuppressWarnings("nls")
public class EmfConnectorTest extends EmfBaseClientTest {

	@Test
	public void testCreateTaskData() {
		TaskRepository repository = new TaskRepository(connector.getConnectorKind(), "http://ignore"); //$NON-NLS-1$
		TaskData data = connector.createTaskData(repository, "123", null); //$NON-NLS-1$
		String key = connector.getSchema().getKey(EmfTasksPackage.Literals.SIMPLE_TASK__ID);
		assertNotNull(key);
		TaskAttribute attribute = data.getRoot().getAttribute(key);
		assertNotNull(attribute);
		assertThat(attribute.getValue(), is(""));
		TaskAttribute mappedAttribute = data.getRoot().getMappedAttribute(TaskAttribute.TASK_KEY);
		assertNotNull(mappedAttribute);
		assertThat(attribute, is(mappedAttribute));

		TaskData data2 = connector.createTaskData(repository, "456", null); //$NON-NLS-1$
		TaskAttribute mappedAttribute2 = data2.getRoot().getMappedAttribute(TaskAttribute.TASK_KEY);
		assertThat(mappedAttribute2.getValue(), is(""));
	}

	@Test
	public void testCreatePartialTaskData() {
		TaskRepository repository = new TaskRepository(connector.getConnectorKind(), "http://ignore"); //$NON-NLS-1$
		TaskData data = connector.createPartialTaskData(repository, "123", null); //$NON-NLS-1$
		TaskAttribute attribute = data.getRoot().getMappedAttribute(TaskAttribute.TASK_KEY);
		assertNotNull(attribute);
		assertThat(attribute.getValue(), is(""));
		assertNotNull(data.getRoot().getMappedAttribute(TaskAttribute.SUMMARY));
		assertNotNull(data.getRoot().getAttribute(
				connector.getSchema().getKey(EmfTasksPackage.Literals.SIMPLE_TASK__ID)));
		assertEquals(
				data.getRoot().getMappedAttribute(TaskAttribute.DATE_MODIFICATION),
				data.getRoot().getAttribute(
						connector.getSchema().getKey(EmfTasksPackage.Literals.SIMPLE_TASK__MODIFICATION_DATE)));
	}

	@Test
	public void testGetObject() throws CoreException {
		client.updateConfiguration(new NullProgressMonitor());
		EObject object = connector.getTaskObject(client.getRepository(), "1", new NullProgressMonitor());
		assertNotNull(object);
		assertThat(1, is(connector.getEmfMapper(client.getRepository()).getEmfValue(object, TaskAttribute.TASK_KEY)));
		assertThat("1", is(connector.getEmfMapper(client.getRepository()).getEmfString(object, TaskAttribute.TASK_KEY)));
		assertThat("1",
				is(connector.getEmfMapper(client.getRepository()).getEmfString(object, "emf.emftasks.simpletask.id")));
		SimpleTask eClassClassifier = (SimpleTask) object;
		assertThat("Test Task 1", is(eClassClassifier.getSummary()));
	}

	@Test
	public void testPullTaskData() throws CoreException {
		TaskData taskData = connector.getTaskData(client.getRepository(), "1", new NullProgressMonitor());
		TaskAttribute root = taskData.getRoot();
		TaskAttribute mappedAttribute = root.getMappedAttribute(TaskAttribute.TASK_KEY);
		assertNotNull(mappedAttribute);
		assertThat("1", is(mappedAttribute.getValue()));
		TaskAttribute attribute = root.getAttribute("emf.emftasks.simpletask.id");
		assertNotNull(attribute);
		assertThat("1", is(attribute.getValue()));
		attribute = root.getAttribute("emf.emftasks.simpletask.summary");
		assertNotNull(attribute);
		assertThat("Test Task 1", is(attribute.getValue()));
	}

	///Developer/workspaces/runtime-EmfExample/temp/My2.emftasks

	@Test
	public void testPostTaskData() throws CoreException {
		TaskData taskData = connector.getTaskData(client.getRepository(), "1", new NullProgressMonitor());
		TaskAttribute root = taskData.getRoot();
		TaskAttribute summaryAttribute = root.getAttribute("emf.emftasks.simpletask.summary");

		assertThat("Test Task 1", is(summaryAttribute.getValue()));
		summaryAttribute.setValue("Modified Test Task 1");
		HashSet<TaskAttribute> oldAttributes = new HashSet<TaskAttribute>();
		oldAttributes.add(summaryAttribute);
		connector.postTaskData(client.getRepository(), taskData, oldAttributes, new NullProgressMonitor());
		EObject object = connector.getTaskObject(client.getRepository(), "1", new NullProgressMonitor());
		String summary = connector.getEmfMapper(client.getRepository()).getEmfString(object, TaskAttribute.SUMMARY);
		assertThat(summary, is("Modified Test Task 1"));
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

	@Test
	public void testPostNewTaskData() throws CoreException {
		Object lastId = client.getRootContainer().eGet(EmfTasksPackage.Literals.TASK_COLLECTION__LAST_TASK_ID);
		assertThat(lastId, instanceOf(Integer.class));
		assertThat(((Integer) lastId), is(100));
		TaskRepository repository = client.getRepository();
		TaskData taskData = connector.createTaskData(repository, "", new NullProgressMonitor());
		TaskAttribute root = taskData.getRoot();
		TaskAttribute summaryAttribute = root.createAttribute("emf.emftasks.simpletask.summary");
		summaryAttribute.setValue("A New Task");

		HashSet<TaskAttribute> oldAttributes = new HashSet<TaskAttribute>();
		oldAttributes.add(summaryAttribute);
		RepositoryResponse postTaskData = connector.postTaskData(repository, taskData, oldAttributes,
				new NullProgressMonitor());
		lastId = client.getRootContainer().eGet(EmfTasksPackage.Literals.TASK_COLLECTION__LAST_TASK_ID);
		assertThat(((Integer) lastId), is(101));

		assertThat(postTaskData.getTaskId(), is("101"));
		EObject object = connector.getTaskObject(repository, "101", new NullProgressMonitor());

		assertThat(((SimpleTask) object).getId(), is(101));
		assertThat(((SimpleTask) object).getSummary(), is("A New Task"));

		String id = connector.getEmfMapper(repository).getEmfString(object, TaskAttribute.TASK_KEY);
		assertThat(id, is("101"));

		String summary = connector.getEmfMapper(repository).getEmfString(object, TaskAttribute.SUMMARY);
		assertThat(summary, is("A New Task"));
	}
}
