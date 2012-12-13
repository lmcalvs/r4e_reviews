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
 *******************************************************************************/

package org.eclipse.mylyn.reviews.example.emftasks.client;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleConnector;
import org.eclipse.mylyn.reviews.connector.EmfConfiguration;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.reviews.example.emftasks.EmfBaseClientTest;
import org.eclipse.mylyn.reviews.example.emftasks.TaskCollection;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.junit.Test;

/**
 * Unit tests for {@link EmfClient} exercising Ecore. (May be redundant with newer example model tests.)
 * 
 * @author Miles Parker
 */
@SuppressWarnings("nls")
public class EmfExampleClientTest extends EmfBaseClientTest {

	@Test
	public void testRefreshConfig() throws Exception {
		EmfConfiguration config = client.updateConfiguration(new NullProgressMonitor());
		assertNotNull(config);
	}

	@Test
	public void testClose() throws Exception {
		String fullUri = getFullUri(TEST_MODIFIED_EXAMPLE_FILE);
		client.getRepository().setRepositoryUrl(fullUri);
		client.updateConfiguration(new NullProgressMonitor());
		((TaskCollection) client.getRootContainer()).getTasks().get(0).setSummary("Modified Task 1");
		client.close();
		ResourceSet set = new ResourceSetImpl();
		Resource resource = set.getResource(targetTestDirUri.appendSegment(TEST_MODIFIED_EXAMPLE_FILE), true);
		assertThat(((TaskCollection) resource.getContents().get(0)).getTasks().get(0).getSummary(), not("Task 1"));
	}

	@Test
	public void testModifyAndCloseModel() throws Exception {
		String fullUri = getFullUri(TEST_EXAMPLE_FILE);
		client.getRepository().setRepositoryUrl(fullUri);
		client.updateConfiguration(new NullProgressMonitor());
		assertThat(((TaskCollection) client.getRootContainer()).getTasks().get(0).getSummary(), is("Test Task 1"));
		((TaskCollection) client.getRootContainer()).getTasks().get(0).setSummary("Modified Task 1");
		client.save();
		ResourceSet set = new ResourceSetImpl();
		Resource resource = set.getResource(targetTestDirUri.appendSegment(TEST_EXAMPLE_FILE), true);
		assertThat(((TaskCollection) resource.getContents().get(0)).getTasks().get(0).getSummary(),
				is("Modified Task 1"));
	}

	@Test
	public void testModifyAndSaveModel() throws Exception {
		String fullUri = getFullUri(TEST_COPY_EXAMPLE_FILE);
		client.getRepository().setRepositoryUrl(fullUri);
		client.updateConfiguration(new NullProgressMonitor());
		assertThat(((TaskCollection) client.getRootContainer()).getTasks().get(0).getSummary(), is("Test Task 1"));
		((TaskCollection) client.getRootContainer()).getTasks().get(0).setSummary("Modified Task 1");
		client.save();
		client.close();
		ResourceSet set = new ResourceSetImpl();
		Resource resource = set.getResource(targetTestDirUri.appendSegment(TEST_COPY_EXAMPLE_FILE), true);
		assertThat(((TaskCollection) resource.getContents().get(0)).getTasks().get(0).getSummary(),
				is("Modified Task 1"));
	}

	@Test
	public void testChangeUrlConfig() throws Exception {
		EObject container1 = client.getRootContainer();
		client.getRepository().setRepositoryUrl(getFullUri(TEST_COPY_EXAMPLE_FILE));
		assertThat(container1.eResource().getURI().toString(), endsWith(TEST_EXAMPLE_FILE));
		EmfConfiguration config = client.updateConfiguration(new NullProgressMonitor());
		assertNotNull(config);
		EObject container2 = client.getRootContainer();
		assertThat(container1, not(sameInstance(container2)));
		assertThat(container2.eResource().getURI().toString(), endsWith(TEST_COPY_EXAMPLE_FILE));
	}

	@Test
	public void testChangeUrlConfigSave() throws Exception {
		EObject container1 = client.getRootContainer();
		assertThat(((TaskCollection) container1).getLabel(), is("Test Tasks"));
		String modifiedUri = getFullUri(TEST_MODIFIED_EXAMPLE_FILE);
		TaskRepository repository = new TaskRepository(EmfExampleConnector.CONNECTOR_KIND, modifiedUri);
		EmfClient client2 = connector.getClient(repository);
		assertThat(client2.getRepository().getUrl(), is(modifiedUri + ""));
		client2.open();
		EObject container2 = client2.getRootContainer();
		assertThat(((TaskCollection) container2).getLabel(), is("Modified Test Tasks"));
		client.getRepository().setRepositoryUrl(getFullUri(TEST_EXAMPLE_FILE));
		client.updateConfiguration(new NullProgressMonitor());
		container2 = client.getRootContainer();
		assertThat(((TaskCollection) container2).getLabel(), is("Test Tasks"));
		client.getRepository().setRepositoryUrl(modifiedUri);
		client.updateConfiguration(new NullProgressMonitor());
		EObject container1_Reopen = client.getRootContainer();
		assertThat(((TaskCollection) container1_Reopen).getLabel(), is("Modified Test Tasks"));
	}

}
