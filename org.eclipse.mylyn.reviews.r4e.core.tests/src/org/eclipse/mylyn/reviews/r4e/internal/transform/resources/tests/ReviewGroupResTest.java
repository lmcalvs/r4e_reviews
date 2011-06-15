/**
 * Copyright (c) 2011 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources.tests;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.tests.GoldenStubHandler;
import org.eclipse.mylyn.reviews.r4e.internal.transform.api.ModelTransform;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes;
import org.junit.Test;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Review Group Res</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReviewGroupResTest extends TestCase {

	/**
	 * The fixture for this R4E Review Group test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ReviewGroupRes		fixture			= null;
	private final IModelReader		fReader			= SerializeFactory.getReader();
	private final IModelWriter		fWriter			= SerializeFactory.getWriter();

	private final RModelFactoryExt	fFactory		= SerializeFactory.getModelExtension();

	private static final String		fGroupPathStr	= "outGroupX";
	private static URI				fGroupPath		= null;

	private static final String		GROUP_NAME		= "Group One";
	private static final String		REVIEW_NAME1	= "Alpha 1";

	private static final String		fUser1			= "au 1";
	private File					fRootTestDir	= null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ReviewGroupResTest.class);
	}

	/**
	 * Constructs a new Review Group Res test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewGroupResTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Review Group Res test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ReviewGroupRes fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Review Group Res test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewGroupRes getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		TstGeneral.activateTracer();
		String baseDir = System.getProperty("java.io.tmpdir");
		if (!baseDir.endsWith(File.separator)) {
			baseDir = baseDir + File.separator;
		}
		fRootTestDir = new File(baseDir + fGroupPathStr + File.separator);
		fGroupPath = URI.createFileURI(fRootTestDir.getAbsolutePath());

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
		// remove test directory
		// if (fRootTestDir != null && fRootTestDir.exists()) {
		// FileUtils.deleteDirectory(fRootTestDir);
		// }
	}

	/**
	 * Transform model to shared file resources format
	 */
	@Test
	public void testReviewTransform() {
		// From Objects to Disk
		R4EReviewGroup loadedGroup = null;
		try {
			loadedGroup = GoldenStubHandler.serializeStub();
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		URI origURI = loadedGroup.eResource().getURI();
		// Open Original Serialised model
		URI destFolderURI = origURI.trimSegments(1).appendSegment("transformations");
		String destGroupName = "TransformedGrp";
		ReviewGroupRes destGroup = null;
		try {
			destGroup = ModelTransform.instance.createReviewGroupRes(destFolderURI, destGroupName);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertNotNull("Failed to create transformation group", destGroup);

		R4EReview oReview = null;
		R4EReview dReview = null;
		Set<String> reviewNames = loadedGroup.getReviewsMap().keySet();
		URI destURI = destGroup.eResource().getURI();
		for (Iterator iterator = reviewNames.iterator(); iterator.hasNext();) {
			String reviewName = (String) iterator.next();
			// Open the review
			try {
				oReview = fFactory.openR4EReview(loadedGroup, reviewName);
				dReview = ModelTransform.instance.reviewTransform(origURI, destURI, reviewName);
			} catch (ResourceHandlingException e) {
				e.printStackTrace();
				fail("Exception");
			}

			// Transform
			assertNotNull("Transformed Review is null", dReview);
		}
		System.out.println("test case finished");
	}

} //ReviewGroupResTest
