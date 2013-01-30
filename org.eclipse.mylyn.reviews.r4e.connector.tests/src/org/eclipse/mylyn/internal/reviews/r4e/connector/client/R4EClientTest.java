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

package org.eclipse.mylyn.internal.reviews.r4e.connector.client;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EClient;
import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnector;
import org.eclipse.mylyn.internal.reviews.r4e.connector.support.R4EFixture;
import org.eclipse.mylyn.internal.reviews.r4e.connector.support.R4EHarness;
import org.eclipse.mylyn.internal.reviews.r4e.connector.tests.R4ETestConstants;
import org.eclipse.mylyn.reviews.connector.EmfConfiguration;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link EmfClient}.
 * 
 * @author Miles Parker
 * @author Alvaro Sanchez-Leon
 */
public class R4EClientTest {
	private R4EClient client;

	private R4EHarness harness;

	private URI testRootURI;

	private R4EConnector connector;

	@Before
	public void setUp() throws Exception {

		String base = System.getProperty("java.io.tmpdir");
		if (!base.endsWith(File.separator)) {
			base = base + File.separator;
		}

		File rootDir = new File(base + "r4eCoreTest");

		// Resolve golden group storage dir
		URI file = R4ETestConstants.TEST_GROUP_URI;
		String rootFile = file.lastSegment();
		File goldenDir = new File(URI.decode(file.trimSegments(1).devicePath()));

		// Take the directory name to be used as copy destination
		String testDir = rootDir.toString() + "OutL1" + File.separator + "OutL2" + File.separator + goldenDir.getName();
		File fTestDir = new File(testDir);

		// Determine the location of the group file in the destination folder
		testRootURI = URI.createFileURI(fTestDir.getAbsolutePath());
		testRootURI = testRootURI.appendSegment(rootFile);

		// Copy golden dir to test dir
		FileUtils.copyDirectory(goldenDir, fTestDir);
		harness = new R4EFixture(testRootURI.toString(), "1.0.0", "").harness();
		client = harness.client();
		connector = new R4EConnector();
		client.updateConfiguration(new NullProgressMonitor());
		assertThat(client.getRootContainer(), instanceOf(R4EReviewGroup.class));
	}

	@After
	public void tearDown() throws Exception {
		harness.dispose();
	}

	@Test
	public void testRefreshConfig() throws Exception {
		EmfConfiguration config = client.updateConfiguration(new NullProgressMonitor());
		assertNotNull(config);
	}

	@Test
	public void testChangeUrlConfig() throws Exception {
		EmfConfiguration config = client.updateConfiguration(new NullProgressMonitor());
//		client.getRepository().setRepositoryUrl(repositoryUrl);
		assertNotNull(config);
	}

	@Test
	public void testGetReview() throws CoreException {
		EObject object = connector.getTaskObject(client.getRepository(), "0", new NullProgressMonitor());
		assertThat(object, instanceOf(R4EReview.class));
		R4EReview r4eReview = (R4EReview) object;
		assertThat(r4eReview.getName(), startsWith("Review"));
		Calendar pst = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		pst.set(1867, 6, 2, 21, 0, 0);
		long testTime = pst.getTime().getTime();
		long reviewTime = r4eReview.getEndDate().getTime();
		assertThat(pst.getTime().getYear(), is(r4eReview.getEndDate().getYear()));
		assertThat(pst.getTime().getMonth(), is(r4eReview.getEndDate().getMonth()));
		assertThat(pst.getTime().getDay(), is(r4eReview.getEndDate().getDay()));
	}

	@Test
	public void testPullTaskData() throws CoreException {
		TaskData taskData = connector.getTaskData(client.getRepository(), "0", new NullProgressMonitor());
		String id = connector.getSchema().getKey(RModelPackage.Literals.R4E_REVIEW__NAME);
		assertThat(taskData.getRoot().getAttribute(id).getValue(), startsWith("Review"));
		id = connector.getSchema().getKey(RModelPackage.Literals.R4E_REVIEW__END_DATE);
		Calendar pst = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		pst.set(1867, 6, 2, 21, 0);
		assertThat(taskData.getRoot().getAttribute(id).getValue(), startsWith(pst.get(Calendar.YEAR) + "-"));
	}
}
