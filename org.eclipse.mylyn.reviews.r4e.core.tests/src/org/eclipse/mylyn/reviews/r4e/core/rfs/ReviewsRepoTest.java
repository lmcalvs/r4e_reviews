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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReviewsRepoTest extends TestCase {
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

		File repoDir = new File(fRootDir);
		boolean exist = ReviewsRFSProxy.isValidRepo(repoDir);
		// point to location
		fRepoProx = new ReviewsRFSProxy(repoDir, !exist);
	}

	@After
	public void tearDown() throws Exception {
		// Remove repository
		if (fRepoProx != null) {
			fRepoProx.close();
			File repoDir = fRepoProx.getRepository().getDirectory();
			FileUtils.deleteDirectory(repoDir);
		}
	}

	// ------------------------------------------------------------------------
	// Test methods
	// ------------------------------------------------------------------------
	/**
	 * Register a couple of Blobs and verify it exists in the repo
	 */
	@Test
	public void testRegisterReviewBlobByteArray() {
		String strContent = new String("The Content");
		byte[] content = strContent.getBytes();
		String id = null;
		InputStream is = null;
		try {
			id = fRepoProx.registerReviewBlob(content);
			fRepoProx.registerReviewBlob(new String("The Content 2").getBytes());
			is = fRepoProx.getBlobContent(null, id);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertNotNull("blob stream is null", is);
		try {
			assertEquals(strContent, IOUtils.toString(is));
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testResolveIdFor() {
		String strContent = new String("The Content");
		byte[] content = strContent.getBytes();
		String regId = null;
		String calcId = null;
		try {
			regId = fRepoProx.registerReviewBlob(content);
			calcId = fRepoProx.blobIdFor(content);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception");
		}

		Assert.assertEquals(calcId, regId);
	}

	// /**
	// * Manual test case: debug settings: unchecked Run on UI thread and continue running after test completion
	// */
	// @Test
	// public void triggerEclipseEditor() {
	//
	// Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	// String strContent = new String(
	// "package org.eclipse.mylyn.reviews.r4e.core.rfs;/nimport static org.junit.Assert.fail;\npublic class ReviewsRepoTest {}");
	// byte[] content = strContent.getBytes();
	// String id = null;
	// IStorage is = null;
	// try {
	// id = fRepoProx.registerReviewBlob(content);
	// fRepoProx.registerReviewBlob(new String("The Content 2").getBytes());
	// R4EFileVersion fileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
	// fileVersion.setLocalVersionID(id);
	// fileVersion.setRepositoryPath("/folder/repoDir/fileX.java");
	// final IFileRevision fileRev = fRepoProx.getIFileRevision(null, fileVersion);
	//
	// is = fRepoProx.getIStorage(null, fileVersion);
	//
	// Assert.assertNotNull("Istorage \"is\" is null", is);
	//
	// final FileRevisionEditorInput editorInp = new FileRevisionEditorInput(id, is);
	//
	// // Let a new thread display the action
	// Thread thread = new Thread(new Runnable() {
	// public void run() {
	// Display.getDefault().asyncExec(new Runnable() {
	// public void run() {
	// // String editorId = "org.eclipse.jdt.ui.ClassFileEditor";
	// IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	// try {
	// page.openEditor(editorInp, IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
	//
	// } catch (PartInitException e) {
	// e.printStackTrace();
	// }
	// System.out.println("executing");
	// }
	// });
	// }
	// });
	// thread.start();
	// thread.setPriority(Thread.MAX_PRIORITY);
	//
	// try {
	// // Wait for the other thread to execute
	// Thread.yield();
	// Thread.sleep(10000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// fail("Interrupted while waiting");
	// }
	// } catch (ReviewsFileStorageException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	// }
	//
	// /**
	// * Manual test case: debug settings: unchecked Run on UI thread and continue running after test completion
	// *
	// * Trigger Eclipse compare populated with one file only, from local git repo.
	// */
	// @Test
	// public void triggerEclipseCompare() {
	// Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	// String strContent = new String(
	// "package org.eclipse.mylyn.reviews.r4e.core.rfs;/nimport static org.junit.Assert.fail;\npublic class ReviewsRepoTest {}");
	// byte[] content = strContent.getBytes();
	// String id = null;
	// try {
	// id = fRepoProx.registerReviewBlob(content);
	// R4EFileVersion fileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
	// fileVersion.setLocalVersionID(id);
	// fileVersion.setRepositoryPath("/folder/repoDir/fileX.java");
	// final IFileRevision fileRev = fRepoProx.getIFileRevision(null, fileVersion);
	//
	// // Let a new thread display the action
	// Thread thread = new Thread(new Runnable() {
	// public void run() {
	// Display.getDefault().asyncExec(new Runnable() {
	// public void run() {
	// IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	// ScmUi.openCompareEditor(page, null, fileRev);
	// System.out.println("executing");
	// }
	// });
	// }
	// });
	//
	// thread.start();
	// thread.setPriority(Thread.MAX_PRIORITY);
	//
	// try {
	// // Wait for the other thread to execute
	// Thread.yield();
	// Thread.sleep(10000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// fail("Interrupted while waiting");
	// }
	// } catch (ReviewsFileStorageException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	// }

	// TODO: Add missing test cases
	// @Test
	// public void testGetRepoRegistryException() {
	//
	// }
	//
	// @Test
	// public void testGetBlobFromPackedRepo() {
	//
	// }
	//
	// @Test
	// public void testWrittePermissionSetting() {
	//
	// }

}
