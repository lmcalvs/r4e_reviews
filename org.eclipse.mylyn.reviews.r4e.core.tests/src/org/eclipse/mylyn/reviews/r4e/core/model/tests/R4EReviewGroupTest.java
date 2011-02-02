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
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.r4e.core.TestGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.impl.SampleR4EModel;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.R4EReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.R4EWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;

/**
 * <!-- begin-user-doc --> A test case for the model object '<em><b>R4E Review Group</b></em>'. <!-- end-user-doc -->
 * 
 * @generated
 */
public class R4EReviewGroupTest extends TestCase {

	/**
	 * The fixture for this R4E Review Group test case. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected R4EReviewGroup		fixture			= null;
	private final R4EReader			fReader			= SerializeFactory.getReader();
	private final R4EWriter			fWriter			= SerializeFactory.getWriter();

	private final RModelFactoryExt	fFactory		= SerializeFactory.getModelExtension();

	private static final String		fGroupPathStr	= "outGroupX";
	private static URI				fGroupPath		= null;

	private static final String		GROUP_NAME		= "Group One";
	private static final String		REVIEW_NAME1	= "Alpha 1";

	private static final String		fUser1			= "au 1";



	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(R4EReviewGroupTest.class);
	}

	/**
	 * Constructs a new R4E Review Group test case with the given name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewGroupTest(String name) {
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
		TestGeneral.activateTracer();
		File groupFile = new File(fGroupPathStr);
		fGroupPath = URI.createFileURI(groupFile.getAbsolutePath());
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
	 * Build elements one by one
	 */
	public void testGroupReviewCreate() {
		R4EReviewGroup group = null;
		// required folder path and group name to build the new group file
		try {
			group = fFactory.createR4EReviewGroup(fGroupPath, GROUP_NAME);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		assertNotNull(group);
		URI uri = group.eResource().getURI();
		String expectedURI = fGroupPath.appendSegment("Group_One_group_root.xrer").toString();
		assertEquals(expectedURI, uri.toString());

		// Deserialise and assert
		group = null;
		try {
			group = fFactory.openR4EReviewGroup(uri);
		} catch (ResourceHandlingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// validate
		assertNotNull(group);
		assertEquals(GROUP_NAME, group.getName());

		// crate a review
		R4EReview review = null;
		try {
			review = fFactory.createR4EReview(group, REVIEW_NAME1, fUser1);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(REVIEW_NAME1, review.getName());

		// re-open the group
		group = null;
		try {
			group = fFactory.openR4EReviewGroup(uri);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		uri = review.eResource().getURI();
		// read the review
		try {
			review = fFactory.openR4EReview(group, review.getName());
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(REVIEW_NAME1, review.getName());

		// read the participant
		R4EUser participant = review.getUsersMap().values().iterator().next();
		uri = participant.eResource().getURI();
		try {
			ResourceSet resSet = participant.eResource().getResourceSet();
			participant = fReader.deserializeTopElement(uri, resSet, R4EParticipant.class);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(fUser1, participant.getId());

		// Clean up
		File folder = new File(fGroupPath.devicePath());
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	/**
	 * Validate new serialization vs golden
	 */
	public void testSerializeStub() {
		String groupPath = fGroupPath.devicePath();
		String groupName = "Golden Group";

		// Serialize
		R4EReviewGroup persistedGroup = SampleR4EModel.createAndSerialize(groupPath, groupName);

		// Read actual uri
		URI groupURI = persistedGroup.eResource().getURI();

		// Load Group
		R4EReviewGroup loadedGroup = null;
		try {
			loadedGroup = fFactory.openR4EReviewGroup(groupURI);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// Verify
		assertEquals(groupName, loadedGroup.getName());
		assertEquals(3, loadedGroup.getReviews().size());
		assertEquals(3, loadedGroup.getReviewsMap().size());
		assertEquals(3, loadedGroup.getUserReviews().size());

		// This check is done comparing the size only. to eliminate the changes from Id values e.g. equal size per id.
		// good enough for the time being.
		boolean same = TestGeneral.compareDirectories(new File(fGroupPath.devicePath()), new File(
				TestGeneral.GOLDEN_GROUP_DIR.devicePath()));

		assertTrue("Contents differ from base directory", same);

		// Clean up
		File folder = new File(fWriter.getFolderPath(groupURI).devicePath());
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	/**
	 * Validate non transients From instances -> disk-ver1 -> instances -> disk-ver2 <br>
	 * then compare disk-ver1 equal disk-ver2
	 */
	public void testVerifyLoading() {
		// From Objects to Disk
		URI groupFileUri = null;
		try {
			groupFileUri = GoldenStubHandler.serializeStub().eResource().getURI();
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// deserializes/loads the stub
		// Reload from Disk
		R4EReviewGroup loadedGroup = null;
		ResourceSet resSet = null;
		try {
			loadedGroup = GoldenStubHandler.loadStub(groupFileUri);
			resSet = loadedGroup.eResource().getResourceSet();
			// load all reviews, to associate to the same resourceSet
			EList<Review> reviews = loadedGroup.getReviews();
			for (Iterator<Review> iterator = reviews.iterator(); iterator.hasNext();) {
				Review review = iterator.next();
				fFactory.openR4EReview(loadedGroup, ((R4EReview) review).getName());
			}

		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// Move the original to different location to save the loaded one in its place
		URI srcDirUri = groupFileUri.trimSegments(1);
		File srcDir = new File(srcDirUri.devicePath());
		File destDir = toDestinationFolder(srcDirUri);
		try {
			FileUtils.moveDirectory(srcDir, destDir);
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("Exception");
		}

		// Save it from loaded model
		R4EWriter updater = SerializeFactory.getWriter();
		try {
			updater.saveResources(resSet);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		boolean result = TestGeneral.compareDirectories(srcDir, destDir);
		assertTrue("Serialized model does not match Loaded one", result);

		// CleanUp
		try {
			FileUtils.deleteDirectory(srcDir);
			FileUtils.deleteDirectory(destDir);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	/**
	 * Load from disk and verify transient references
	 */
	public void testLoadGoldenGroup() {
		// Read actual uri
		String groupName = "Golden Group";
		File groupfile = new File(TestGeneral.GOLDEN_GROUP_FILE_STR);
		URI groupFileURI = null;
		try {
			groupFileURI = URI.createFileURI(groupfile.getCanonicalPath().toString());
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("Error creating group file uri");
		}

		// Load Group
		R4EReviewGroup loadedGroup = null;
		try {
			loadedGroup = fFactory.openR4EReviewGroup(groupFileURI);
			// load all reviews, to associate to the same resourceSet
			EMap<String, R4EReview> reviews = loadedGroup.getReviewsMap();
			for (Iterator<String> iterator = reviews.keySet().iterator(); iterator.hasNext();) {
				String reviewName = iterator.next();
				fFactory.openR4EReview(loadedGroup, reviewName);
			}
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// The number of created resources shall match the number of physical files
		String folderPath = groupFileURI.trimSegments(1).devicePath();
		File goldenFolder = new File(folderPath);
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(goldenFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		EList<Resource> resources = loadedGroup.eResource().getResourceSet().getResources();
		boolean goodNumResources = (files.size() == resources.size());
		if (!goodNumResources) {
			System.out.println("Unexpected number of resources found: ");
			for (Iterator<Resource> iterator = resources.iterator(); iterator.hasNext();) {
				Resource resource = iterator.next();
				System.out.println("\t" + resource.getURI());
			}
		}

		assertTrue(goodNumResources);

		// TODO: The following verifications are using hard coded values which requires more maintenance, it should be
		// possible to serialize a reference model where transient references are converted to non transient, then load
		// from TRANSIENTS model to serialize in non transient form to perform directory comparison of the two non
		// transient models on disk

		// Verify
		assertEquals(groupName, loadedGroup.getName());
		assertEquals(3, loadedGroup.getReviews().size());
		assertEquals(3, loadedGroup.getReviewsMap().size());
		assertEquals(3, loadedGroup.getUserReviews().size());
		assertNotNull(((R4EReview) loadedGroup.getReviews().get(0)).getName());

		// Verify Reviews Ids Map
		EList<Review> reviews = loadedGroup.getReviews();
		R4EReview review = null;
		for (Iterator<Review> iterator = reviews.iterator(); iterator.hasNext();) {
			R4EReview tmpReview = (R4EReview) iterator.next();
			if (tmpReview.getName().equals("ReviewTwo")) {
				review = tmpReview;
				break;
			}
		}

		assertNotNull(review);
		assertEquals(30, review.getIdsMap().size()); // 30 expected ids

		// Verify Anomaly, at least one expected
		R4EAnomaly anomaly = (R4EAnomaly) review.getTopics().get(0);
		assertEquals(2, anomaly.getComments().size()); // 2 comments to the anomaly

		R4EUser user = review.getCreatedBy();
		// Verify Review.createdBy
		assertEquals("Tom10", user.getId());

		// verify user review instance matches
		assertEquals(user.getReviewInstance(), review);

		// verify group folder
		File storedFolder = new File(loadedGroup.getFolder());
		File groupResourceFolder = new File(loadedGroup.eResource().getURI().trimSegments(1).devicePath());
		assertEquals(storedFolder, groupResourceFolder);
	}

	/**
	 * @param groupFileUri
	 * @return
	 */
	private File toDestinationFolder(URI groupFileUri) {
		String toReplace = TestGeneral.GROUP_PATH_STR;
		String forReplace = toReplace + "Y";

		String path = groupFileUri.devicePath();
		// modify to destination folder
		path = path.replace(toReplace, forReplace);
		File destFile = new File(path);
		return destFile;
	}
} // R4EReviewGroupTest
