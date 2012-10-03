/**
 * Copyright (c) 2010, 2012 Ericsson
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * Miles Parker  - Initial implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.core.model.IReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.BasePersistenceTest;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.junit.Test;

public class CommonModelFileConverterTest extends BasePersistenceTest {

	private R4EReviewGroup oldGroup;

	public static final String OLD_GROUP_PATH_STR = "outGroupPrior";

	public static final String OLD_GROUP_DIR_STR = "stubs_model/" + OLD_GROUP_PATH_STR;

	public static final String OLD_GROUP_FILE_STR = OLD_GROUP_DIR_STR + "/Golden_Group_group_root.xrer";

	public static final URI OLD_GROUP_FILE = URI.createFileURI(OLD_GROUP_FILE_STR);

	@Test
	public void testConversion() throws ResourceHandlingException, CompatibilityException, IOException {
		keepFiles = true;

		// Take the directory name to be used as copy destination
		File goldenDir = new File(URI.decode(OLD_GROUP_FILE.trimSegments(1).devicePath()));
		String testDir = fRootTestDir.toString() + File.separator + "OutL1" + fSep + "OutL2" + fSep
				+ goldenDir.getName();
		File oldTestDir = new File(testDir);

		// Determine the location of the group file in the destination folder
		URI oldTestRootURI = URI.createFileURI(oldTestDir.getAbsolutePath());
		String rootFile = fGoldenGroup.lastSegment();
		URI oldTestGroupFileURI = oldTestRootURI.appendSegment(rootFile);

		// Copy golden dir to test dir
		FileUtils.copyDirectory(goldenDir, oldTestDir);

		oldGroup = (R4EReviewGroup) fResFactory.openR4EReviewGroup(oldTestGroupFileURI);
		// Load
		try {
			R4EReview oldReview = fResFactory.openR4EReview(oldGroup, "Review1");
			fail();
		} catch (Exception e) {
			assertTrue("Actual Message: " + e.getMessage(), e.getMessage().contains("IllegalValueException"));
		}
		CommonModelFileConverter converter = new CommonModelFileConverter(oldTestRootURI);
		converter.schedule();
		long elapsed = 0;
		while (converter.getResult() == null) {
			if (elapsed > 20000) {
				fail("Timeout for conversion.");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			elapsed++;
		}
		assertTrue(converter.getResult().getMessage(), converter.getResult().isOK());

		R4EReviewGroup newGroup = (R4EReviewGroup) fResFactory.openR4EReviewGroup(oldTestGroupFileURI);
		R4EReview newReview = fResFactory.openR4EReview(newGroup, "Review1");
		assertNotNull(newReview);
		for (IReview review : newGroup.getReviews()) {
			fResFactory.openR4EReview(newGroup, review.getId());
		}
		//keepFiles = false;
	}
}
