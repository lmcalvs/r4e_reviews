/**
/**
 * Copyright (c) 2010 Ericsson
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * Alvaro Sanchez-Leon  - Initial implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.tests;

import java.io.File;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.impl.SampleR4EModel;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>R4E Review Group</b></em>'. <!-- end-user-doc -->
 * 
 * @generated
 */
public class GoldenStubHandler extends TestCase {

	/**
	 * The fixture for this R4E Review Group test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected R4EReviewGroup				fixture		= null;
	private static final RModelFactoryExt	fFactory	= SerializeFactory.getModelExtension();
	private static File						fGroupDir	= new File(TstGeneral.GROUP_PATH_STR);
	private static URI						fGroupPath	= URI.createFileURI(fGroupDir.getAbsolutePath());

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GoldenStubHandler.class);
	}

	/**
	 * Constructs a new R4E Review Group test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public GoldenStubHandler(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this R4E Review Group test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(R4EReviewGroup fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this R4E Review Group test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected R4EReviewGroup getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		TstGeneral.activateTracer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Serialize the golden test stub and compares the folder size against the former one
	 */
	public void testSerializeStub() {
		try {
			serializeStub();
			loadGolden();
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		// Check against published golden directory
		boolean same = TstGeneral.compareDirectories(new File(fGroupPath.devicePath()),
				new File(TstGeneral.GOLDEN_GROUP_DIR.devicePath()));

		assertTrue("Contents differ from base directory", same);

		// NOTE: No Clean up, for creation of golden test stub
	}

	public static R4EReviewGroup serializeStub() throws ResourceHandlingException {
		String groupPath = fGroupPath.devicePath();
		String groupName = "Golden Group";

		// Serialize
		R4EReviewGroup persistedGroup = SampleR4EModel.createAndSerialize(groupPath, groupName);

		// Read actual uri
		URI groupURI = persistedGroup.eResource().getURI();

		// Load Group
		R4EReviewGroup loadedGroup = null;
		loadedGroup = fFactory.openR4EReviewGroup(groupURI);
		return loadedGroup;
	}

	/**
	 * @return
	 * @throws ResourceHandlingException
	 */
	public static R4EReviewGroup loadGolden() throws ResourceHandlingException {
		// Load Group
		R4EReviewGroup loadedGroup = null;
		loadedGroup = fFactory.openR4EReviewGroup(TstGeneral.GOLDEN_GROUP_FILE);

		return loadedGroup;
	}

	/**
	 * @param groupFileURI
	 * @return
	 * @throws ResourceHandlingException
	 */
	public static R4EReviewGroup loadStub(URI groupFileURI) throws ResourceHandlingException {
		// Load Group
		R4EReviewGroup loadedGroup = null;
		loadedGroup = fFactory.openR4EReviewGroup(groupFileURI);

		return loadedGroup;
	}

}
