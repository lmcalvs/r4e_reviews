/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial API and Implementation
 *******************************************************************************/

/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.junit.After;
import org.junit.Before;

/**
 * @author Alvaro Sanchez-Leon
 */
public class BasePersistenceTest extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	protected static final URI fGoldenGroup = TstGeneral.GOLDEN_GROUP_FILE;

	private static File fTestDir;

	// ------------------------------------------------------------------------
	// Instance Variables
	// ------------------------------------------------------------------------
	protected final RModelFactoryExt fResFactory = SerializeFactory.getModelExtension();

	protected final ResourceUpdater fUpdater = SerializeFactory.getResourceSetUpdater();

	protected R4EReviewGroup fGroup = null;

	protected static File fRootTestDir = null;

	protected final String fSep = File.separator;

	protected boolean keepFiles;

	// ------------------------------------------------------------------------
	// RWCommon
	// ------------------------------------------------------------------------

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		String base = System.getProperty("java.io.tmpdir");
		if (!base.endsWith(File.separator)) {
			base = base + File.separator;
		}

		fRootTestDir = new File(base + "r4eTst" + File.separator + System.currentTimeMillis());

		// Resolve golden group storage dir
		String rootFile = fGoldenGroup.lastSegment();
		File goldenDir = new File(URI.decode(fGoldenGroup.trimSegments(1).devicePath()));

		// Take the directory name to be used as copy destination
		String testDir = fRootTestDir.toString() + File.separator + "OutL1" + fSep + "OutL2" + fSep
				+ goldenDir.getName();
		fTestDir = new File(testDir);

		// Determine the location of the group file in the destination folder
		URI testRootURI = URI.createFileURI(fTestDir.getAbsolutePath());
		testRootURI = testRootURI.appendSegment(rootFile);

		// Copy golden dir to test dir
		FileUtils.copyDirectory(goldenDir, fTestDir);

		// Load
		try {
			fGroup = fResFactory.openR4EReviewGroup(testRootURI);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		// Tracing on
		TstGeneral.activateTracer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	@After
	public void tearDown() throws Exception {
		fResFactory.closeR4EReviewGroup(fGroup);
		// remove test directory
		if (!keepFiles && fRootTestDir != null && fRootTestDir.exists()) {
			FileUtils.deleteDirectory(fRootTestDir);
		}
	}
}