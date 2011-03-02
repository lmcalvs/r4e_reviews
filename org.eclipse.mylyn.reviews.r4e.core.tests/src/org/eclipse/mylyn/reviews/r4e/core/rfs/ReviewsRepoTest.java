/*******************************************************************************
 * Copyright (c) 2011 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.rfs;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReviewsRepoTest {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	String		fRootDir	= null;
	ReviewsRFSProxy	fRepoProx		= null;

	// ------------------------------------------------------------------------
	// setUp and tearDown
	// ------------------------------------------------------------------------
	@Before
	public void setUp() throws Exception {
		fRootDir = System.getProperty("java.io.tmpdir");
		if (!fRootDir.endsWith(File.separator)) {
			fRootDir += File.separator;
		}

		// point to location
		fRepoProx = new ReviewsRFSProxy(new File(fRootDir), true);
	}

	@After
	public void tearDown() throws Exception {
		// Remove repository
		if (fRepoProx != null) {
			File repoDir = fRepoProx.getRepository().getDirectory();
			FileUtils.deleteDirectory(repoDir);
			fRepoProx.close();
		}
	}

	// ------------------------------------------------------------------------
	// Test methods
	// ------------------------------------------------------------------------
	@Test
	public void testRegisterReviewBlobByteArray() {
		String strContent = new String("The Content");
		byte[] content = strContent.getBytes();
		ObjectId id = null;
		InputStream is = null;
		try {
			id = fRepoProx.registerReviewBlob(content);
			fRepoProx.registerReviewBlob(new String("The Content 2").getBytes());
			is = fRepoProx.getBlobContent(null, id);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception");
		}

		TestCase.assertNotNull("blob stream is null", is);
		try {
			TestCase.assertEquals(strContent, IOUtils.toString(is));
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testGetRepoRegistry() {

	}

	@Test
	public void testSwitchRepoRegistries() {

	}

	@Test
	public void testGetRepoRegistryException() {

	}

	@Test
	public void testGetBlobFromPackedRepo() {
		fail("Not yet implemented");
	}

	@Test
	public void testWrittePermissionSetting() {
		fail("Not yet implemented");
	}

}
