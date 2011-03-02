package org.eclipse.mylyn.reviews.r4e.core.versions.git.internal;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.mylyn.reviews.r4e.core.rrepo.ReviewsRepoProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReviewsRepoTest {
	String		fRootDir	= null;
	ReviewsRepoProxy	fRepoProx		= null;

	@Before
	public void setUp() throws Exception {
		fRootDir = System.getProperty("java.io.tmpdir");
		if (!fRootDir.endsWith(File.separator)) {
			fRootDir += File.separator;
		}

		// point to location
		fRepoProx = new ReviewsRepoProxy(new File(fRootDir), true);
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
	public void testGetBlobFromPackedRepo() {
		fail("Not yet implemented");
	}

	@Test
	public void testWrittePermissionSetting() {
		fail("Not yet implemented");
	}

}
