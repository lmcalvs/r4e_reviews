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

package org.eclipse.mylyn.reviews.example.emftasks;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleConnector;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link EmfClient}.
 * 
 * @author Miles Parker
 */
@SuppressWarnings("nls")
public abstract class EmfBaseClientTest extends TestCase {

	protected static final String TARGET_FOLDER_NAME = "mylyn.emf.test";

	protected static final String TEST_DIR = "testdata";

	protected static final String TEST_EXAMPLE_FILE = "Example.emftasks";

	protected static final String TEST_MODIFIED_EXAMPLE_FILE = "ExampleModified.emftasks";

	protected static final String TEST_COPY_EXAMPLE_FILE = "ExampleCopy.emftasks";

	protected static final String TEST_ENCODING_FILE = "ExampleEncoding.emftasks";

	protected AbstractEmfConnector connector;

	protected EmfClient client;

	protected URI targetTestDirUri;

	protected String getFullUri(String modelName) {
		return targetTestDirUri.appendSegment(modelName).toString();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		File sourceDir = new File(TEST_DIR);
		File targetTestDir = new File(FileUtils.getTempDirectory().getAbsolutePath() + File.separator
				+ TARGET_FOLDER_NAME + File.separator);
		FileUtils.copyDirectory(sourceDir, targetTestDir);

		targetTestDirUri = URI.createFileURI(targetTestDir.getAbsolutePath());

		connector = new EmfExampleConnector();
		client = connector.getClientManager().getClient(
				new TaskRepository(EmfExampleConnector.CONNECTOR_KIND, getFullUri(TEST_EXAMPLE_FILE)));
		client.open();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	public void testDummy() {
		assertNotNull(client);
	}
}
