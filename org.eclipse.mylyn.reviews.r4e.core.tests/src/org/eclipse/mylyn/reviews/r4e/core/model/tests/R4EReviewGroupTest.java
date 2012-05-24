/**
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
 * Alvaro Sanchez-Leon  - Initial implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.junit.Test;

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
		TstGeneral.activateTracer();
		String baseDir = System.getProperty("java.io.tmpdir");
		if (!baseDir.endsWith(File.separator)) {
			baseDir = baseDir + File.separator;
		}
		fRootTestDir = new File(baseDir + fGroupPathStr + File.separator);
		fGroupPath = URI.createFileURI(fRootTestDir.getAbsolutePath());
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
		// remove test directory
		if (fRootTestDir != null && fRootTestDir.exists()) {
			FileUtils.deleteDirectory(fRootTestDir);
		}
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
			e.printStackTrace();
		} catch (CompatibilityException e) {
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
		} catch (CompatibilityException e) {
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
		} catch (CompatibilityException e) {
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
		File folder = new File(URI.decode(fGroupPath.devicePath()));
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	// /**
	// * Validate new serialization vs golden
	// */
	// public void testSerializeStub() {
	// String groupPath = fGroupPath.devicePath();
	// String groupName = "Golden Group";
	//
	// // Serialize
	// R4EReviewGroup persistedGroup = SampleR4EModel.createAndSerialize(groupPath, groupName);
	//
	// // Read actual uri
	// URI groupURI = persistedGroup.eResource().getURI();
	//
	// // Load Group
	// R4EReviewGroup loadedGroup = null;
	// try {
	// loadedGroup = fFactory.openR4EReviewGroup(groupURI);
	// } catch (ResourceHandlingException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	//
	// // Verify
	// assertEquals(groupName, loadedGroup.getName());
	// assertEquals(3, loadedGroup.getReviews().size());
	// assertEquals(3, loadedGroup.getReviewsMap().size());
	// assertEquals(3, loadedGroup.getUserReviews().size());
	//
	// //Select the golden file as per operating system
	// URI goldenDir = TstGeneral.GOLDEN_GROUP_DIR;
	// if (OSPLATFORM.TYPE.isWindowsOS()) {
	// goldenDir = TstGeneral.GOLDEN_GROUP_DIRW;
	// }
	//
	// // This check is done comparing the size only. to eliminate the changes from Id values e.g. equal size per id.
	// // good enough for the time being.
	// boolean same = TstGeneral.compareDirectories(new File(fGroupPath.devicePath()), new File(
	// goldenDir.devicePath()));
	//
	// assertTrue("Contents differ from base directory", same);
	//
	// // Clean up
	// File folder = new File(fWriter.getFolderPath(groupURI).devicePath());
	// try {
	// FileUtils.deleteDirectory(folder);
	// } catch (IOException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	// }
	//
	// /**
	// * Validate non transients From instances -> disk-ver1 -> instances -> disk-ver2 <br>
	// * then compare disk-ver1 equal disk-ver2
	// */
	// public void testVerifyLoading() {
	// // From Objects to Disk
	// URI groupFileUri = null;
	// try {
	// groupFileUri = GoldenStubHandler.serializeStub().eResource().getURI();
	// } catch (ResourceHandlingException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	//
	// // deserializes/loads the stub
	// // Reload from Disk
	// R4EReviewGroup loadedGroup = null;
	// ResourceSet resSet = null;
	// try {
	// loadedGroup = GoldenStubHandler.loadStub(groupFileUri);
	// resSet = loadedGroup.eResource().getResourceSet();
	// // load all reviews, to associate to the same resourceSet
	// EList<Review> reviews = loadedGroup.getReviews();
	// for (Iterator<Review> iterator = reviews.iterator(); iterator.hasNext();) {
	// Review review = iterator.next();
	// fFactory.openR4EReview(loadedGroup, ((R4EReview) review).getName());
	// }
	//
	// } catch (ResourceHandlingException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	//
	// // Move the original to different location to save the loaded one in its place
	// URI srcDirUri = groupFileUri.trimSegments(1);
	// File srcDir = new File(srcDirUri.devicePath());
	// File destDir = toDestinationFolder(srcDirUri);
	// try {
	// FileUtils.moveDirectory(srcDir, destDir);
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// fail("Exception");
	// }
	//
	// // Save it from loaded model
	// R4EWriter updater = SerializeFactory.getWriter();
	// try {
	// updater.saveResources(resSet);
	// } catch (ResourceHandlingException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	//
	// boolean result = TstGeneral.compareDirectories(srcDir, destDir);
	// assertTrue("Serialized model does not match Loaded one", result);
	//
	// // CleanUp
	// try {
	// FileUtils.deleteDirectory(srcDir);
	// FileUtils.deleteDirectory(destDir);
	// } catch (IOException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	// }
	//
	// /**
	// * Load from disk and verify transient references
	// */
	// public void testLoadGoldenGroup() {
	// // Read actual uri
	// String groupName = "Golden Group";
	// File groupfile = new File(TstGeneral.GOLDEN_GROUP_FILE_STR);
	// URI groupFileURI = null;
	// try {
	// groupFileURI = URI.createFileURI(groupfile.getCanonicalPath().toString());
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// fail("Error creating group file uri");
	// }
	//
	// // Load Group
	// R4EReviewGroup loadedGroup = null;
	// try {
	// loadedGroup = fFactory.openR4EReviewGroup(groupFileURI);
	// // load all reviews, to associate to the same resourceSet
	// EMap<String, R4EReview> reviews = loadedGroup.getReviewsMap();
	// for (Iterator<String> iterator = reviews.keySet().iterator(); iterator.hasNext();) {
	// String reviewName = iterator.next();
	// fFactory.openR4EReview(loadedGroup, reviewName);
	// }
	// } catch (ResourceHandlingException e) {
	// e.printStackTrace();
	// fail("Exception");
	// }
	//
	// // USER REVIEWS
	// // Verify that the group is loaded with user reviews information
	// R4EUserReviews tomReviews = loadedGroup.getUserReviews().get("Tom10");
	// R4EUserReviews jerryReviews = loadedGroup.getUserReviews().get("Jerry20");
	//
	// assertNotNull(tomReviews);
	// assertNotNull(jerryReviews);
	//
	// EList<String> createdByTom = tomReviews.getCreatedReviews();
	// EList<String> createdByJerry = jerryReviews.getCreatedReviews();
	//
	// Set<String> tomInvitedTo = tomReviews.getInvitedToMap().keySet();
	// Set<String> jerryInvitedTo = jerryReviews.getInvitedToMap().keySet();
	//
	// assertTrue(createdByTom.contains("ReviewSampl"));
	// assertTrue(createdByTom.contains("ReviewTwo"));
	// assertTrue(createdByTom.size() == 2);
	// assertTrue(createdByJerry.size() == 0);
	//
	// assertTrue(tomInvitedTo.contains("ReviewSampl"));
	// assertTrue(tomInvitedTo.contains("ReviewTwo"));
	// assertTrue(tomInvitedTo.size() == 2);
	//
	// assertTrue(jerryInvitedTo.contains("ReviewSampl"));
	// assertTrue(jerryInvitedTo.contains("ReviewTwo"));
	// assertTrue(jerryInvitedTo.size() == 2);
	//
	// // The number of created resources shall match the number of physical files
	// String folderPath = groupFileURI.trimSegments(1).devicePath();
	// File goldenFolder = new File(folderPath);
	// @SuppressWarnings("unchecked")
	// Collection<File> files = FileUtils.listFiles(goldenFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
	// EList<Resource> resources = loadedGroup.eResource().getResourceSet().getResources();
	// boolean goodNumResources = (files.size() == resources.size());
	// if (!goodNumResources) {
	// System.out.println("Unexpected number of resources found: ");
	// for (Iterator<Resource> iterator = resources.iterator(); iterator.hasNext();) {
	// Resource resource = iterator.next();
	// System.out.println("\t" + resource.getURI());
	// }
	// }
	//
	// assertTrue(goodNumResources);
	//
	// // TODO: The following verifications are using hard coded values which requires more maintenance, it should be
	// // possible to serialize a reference model where transient references are converted to non transient, then load
	// // from TRANSIENTS model to serialize in non transient form to perform directory comparison of the two non
	// // transient models on disk
	//
	// // Verify
	// assertEquals(groupName, loadedGroup.getName());
	// assertEquals(3, loadedGroup.getReviews().size());
	// assertEquals(3, loadedGroup.getReviewsMap().size());
	// assertEquals(3, loadedGroup.getUserReviews().size());
	// assertNotNull(((R4EReview) loadedGroup.getReviews().get(0)).getName());
	//
	// // Verify Reviews Ids Map
	// EList<Review> reviews = loadedGroup.getReviews();
	// R4EReview review = null;
	// for (Iterator<Review> iterator = reviews.iterator(); iterator.hasNext();) {
	// R4EReview tmpReview = (R4EReview) iterator.next();
	// if (tmpReview.getName().equals("ReviewTwo")) {
	// review = tmpReview;
	// break;
	// }
	// }
	//
	// assertNotNull(review);
	// assertEquals(30, review.getIdsMap().size()); // 30 expected ids
	//
	// // Verify Anomaly, at least one expected
	// R4EAnomaly anomaly = (R4EAnomaly) review.getTopics().get(0);
	// assertEquals(2, anomaly.getComments().size()); // 2 comments to the anomaly
	//
	// R4EUser user = review.getCreatedBy();
	// // Verify Review.createdBy
	// assertEquals("Tom10", user.getId());
	//
	// // verify user review instance matches
	// assertEquals(user.getReviewInstance(), review);
	//
	// // verify group folder
	// File storedFolder = new File(loadedGroup.getFolder());
	// File groupResourceFolder = new File(loadedGroup.eResource().getURI().trimSegments(1).devicePath());
	// assertEquals(storedFolder, groupResourceFolder);
	// }

	/**
	 * Make sure the invited to map is still usable after re-opening of a review.
	 */
	@Test
	public void testReviewReopening() {
		// From Objects to Disk
		R4EReviewGroup loadedGroup = null;
		try {
			loadedGroup = GoldenStubHandler.serializeStub();
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (CompatibilityException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// initialise local refs
		String dReviewName = "ReviewTwo";
		String dUser = "Jerry20";
		// Save a reference to the initial review
		R4EReview oReview = loadedGroup.getUserReviews().get(dUser).getInvitedToMap().get(dReviewName);
		R4EReview nReview = null;

		// Action: Close the review
		fFactory.closeR4EReview(oReview);
		
		try {
			nReview = fFactory.openR4EReview(loadedGroup, dReviewName);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (CompatibilityException e) {
			e.printStackTrace();
			fail("Exception");
		}
		
		assertNotNull("Opened Review is null", nReview);

		// A new instance of the review should have been created
		assertNotSame(oReview, nReview);
		// Read the review reference available in the invitedto map
		R4EReview wasInvitedTo = loadedGroup.getUserReviews().get(dUser).getInvitedToMap().get(dReviewName);

		// The reference resolved from the invited to, shall be the same as the new one re-opened and without manually
		// refreshing the reference to the new instance
		assertSame(nReview, wasInvitedTo);

		// The container group shall be re-initialised as part of the review opening sequence
		EObject rContainer = nReview.eContainer();
		assertNotNull(rContainer);
		// Make sure that the reference to the parent group is updated to the original parent group
		assertSame(loadedGroup, nReview.eContainer());
	}

	/**
	 * @param groupFileUri
	 * @return
	 */
	private File toDestinationFolder(URI groupFileUri) {
		String toReplace = TstGeneral.GROUP_PATH_STR;
		String forReplace = toReplace + "Y";

		String path = URI.decode(groupFileUri.devicePath());
		// modify to destination folder
		path = path.replace(toReplace, forReplace);
		File destFile = new File(path);
		return destFile;
	}
} // R4EReviewGroupTest
