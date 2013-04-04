/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements various utility method used in R4E JUnit UI tests
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Francois Chouinard - Make the WS and shared drive test-independent
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.AddToIndexOperation;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.core.op.DisconnectProviderOperation;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRootElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4EUITestPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Assert;

@SuppressWarnings({ "restriction", "nls" })
public class TestUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// Git
	private static final String AUTHOR = "The Author <The.author@some.com>";

	private static final String COMMITTER = "The Commiter <The.committer@some.com>";

	// Java
	private static final String UI_TEST_JAVA_PROJECT_NAME = "javaProject";

	private static final String JAVA_FILE1_EXT_PATH = "testFiles" + File.separator + "extModCommitFile.java";

	public static final String JAVA_FILE1_PROJ_NAME = "modCommitFile.java";

	private static final String JAVA_FILE1_EXT_MOD_PATH = "testFiles" + File.separator + "extModCommitFile1.java";

	private static final String JAVA_FILE2_EXT_PATH = "testFiles" + File.separator + "extRemCommitFile.java";

	public static final String JAVA_FILE2_PROJ_NAME = "remCommitFile.java";

	private static final String JAVA_FILE3_EXT_PATH = "testFiles" + File.separator + "extNewCommitFile.java";

	public static final String JAVA_FILE3_PROJ_NAME = "newCommitFile.java";

	public static final String JAVA_FILE3_EXT_MOD_PATH = "testFiles" + File.separator + "extModCommitFile3.java";

	private static final String JAVA_FILE4_EXT_PATH = "testFiles" + File.separator + "extModNoCommitFile.java";

	public static final String JAVA_FILE4_PROJ_NAME = "modNoCommitFile.java";

	private static final String JAVA_FILE4_EXT_MOD_PATH = "testFiles" + File.separator + "extModNoCommitFile1.java";

	private static final String JAVA_FILE5_EXT_PATH = "testFiles" + File.separator + "extNoVCSFile.java";

	private static final String JAVA_FILE5_PROJ_NAME = "noVCSFile.java";

	// C
	private static final String UI_TEST_C_PROJECT_NAME = "cProject";

	private static final String C_FILE1_EXT_PATH = "testFiles" + File.separator + "extModCommitFile.cc";

	private static final String C_FILE1_PROJ_NAME = "extModCommitFile.cc";

	private static final String C_FILE1_EXT_MOD_PATH = "testFiles" + File.separator + "extModCommitFile1.cc";

	private static final String C_FILE2_EXT_PATH = "testFiles" + File.separator + "extRemCommitFile.cc";

	private static final String C_FILE2_PROJ_NAME = "extRemCommitFile.cc";

	private static final String C_FILE3_EXT_PATH = "testFiles" + File.separator + "extNewCommitFile.cc";

	private static final String C_FILE3_PROJ_NAME = "extNewCommitFile.cc";

	private static final String C_FILE4_EXT_PATH = "testFiles" + File.separator + "extModNoCommitFile.cc";

	private static final String C_FILE4_PROJ_NAME = "modNoCommitFile.cc";

	private static final String C_FILE4_EXT_MOD_PATH = "testFiles" + File.separator + "extModNoCommitFile1.cc";

	private static final String C_FILE5_EXT_PATH = "testFiles" + File.separator + "extNoVCSFile.cc";

	private static final String C_FILE5_PROJ_NAME = "noVCSFile.cc";

	// Text
	private static final String UI_TEST_TEXT_PROJECT_NAME = "textProject";

	private static final String TEXT_FILE1_EXT_PATH = "testFiles" + File.separator + "extModCommitFile.txt";

	private static final String TEXT_FILE1_PROJ_NAME = "extModCommitFile.txt";

	private static final String TEXT_FILE1_EXT_MOD_PATH = "testFiles" + File.separator + "extModCommitFile1.txt";

	private static final String TEXT_FILE2_EXT_PATH = "testFiles" + File.separator + "extRemCommitFile.txt";

	private static final String TEXT_FILE2_PROJ_NAME = "extRemCommitFile.txt";

	private static final String TEXT_FILE3_EXT_PATH = "testFiles" + File.separator + "extNewCommitFile.txt";

	private static final String TEXT_FILE3_PROJ_NAME = "extNewCommitFile.txt";

	private static final String TEXT_FILE4_EXT_PATH = "testFiles" + File.separator + "extModNoCommitFile.txt";

	private static final String TEXT_FILE4_PROJ_NAME = "modNoCommitFile.txt";

	private static final String TEXT_FILE4_EXT_MOD_PATH = "testFiles" + File.separator + "extModNoCommitFile1.txt";

	private static final String TEXT_FILE5_EXT_PATH = "testFiles" + File.separator + "extNoVCSFile.txt";

	private static final String TEXT_FILE5_PROJ_NAME = "noVCSFile.txt";

	// Test Views
	private static final String R4E_NAVIGATOR_VIEW_NAME = "org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView"; // $codepro.audit.disable constantNamingConvention

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	public String FSharedFolder = null;

	private IProject FExtProject = null;

	// Java
	public IProject FJavaIProject = null;

	private IJavaProject FJavaIJavaProject = null;

	public Repository FJavaRepository = null;

	private String FJavaWorkdirPrefix = null;

	public IFile FJavaFile1 = null;

	private IFile FJavaFile2 = null;

	public IFile FJavaFile3 = null;

	public IFile FJavaFile4 = null;

	private IFile FJavaFile5 = null;

	// C
	public IProject FCIProject = null;

	private IProject FCICProject = null;

	private Repository FCRepository = null;

	private String FCWorkdirPrefix = null;

	private IFile FCFile1 = null;

	private IFile FCFile2 = null;

	private IFile FCFile3 = null;

	private IFile FCFile4 = null;

	private IFile FCFile5 = null;

	// Text
	public IProject FTextIProject = null;

	private Repository FTextRepository = null;

	private String FTextWorkdirPrefix = null;

	private IFile FTextFile1 = null;

	private IFile FTextFile2 = null;

	private IFile FTextFile3 = null;

	private IFile FTextFile4 = null;

	private IFile FTextFile5 = null;

	// UI Model
	private R4EUIRootElement FRootElement;

	// Test View
	private ReviewNavigatorView FTestNavigatorView;

	// ------------------------------------------------------------------------
	// Factory stuff
	// ------------------------------------------------------------------------

	/**
	 * The TestUtils ID
	 */
	private String fTestUtilsID = null;

	/**
	 * TestUtils repository
	 */
	private static Map<String, TestUtils> fTestUtils = new HashMap<String, TestUtils>();

	/**
	 * TestUtils factory. If the requested TestUtils doesn't exist, it will be created.
	 * 
	 * @param id
	 *            the unique TestUtils ID
	 * @return the corresponding TestUtils
	 */
	public static TestUtils get(String id) {
		TestUtils testUtils = fTestUtils.get(id);
		if (testUtils == null) {
			testUtils = new TestUtils(id);
			fTestUtils.put(id, testUtils);
		}
		return testUtils;
	}

	private TestUtils(String id) {
		fTestUtilsID = id;
	}

	/**
	 * @return the TestUtils ID
	 */
	public String getId() {
		return fTestUtilsID;
	}

	// ------------------------------------------------------------------------
	// Test environment setup
	// ------------------------------------------------------------------------

	public void setupTestEnvironment() throws CoreException, IOException, URISyntaxException {
		FExtProject = ResourcesPlugin.getWorkspace().getRoot().getProject(R4EUITestPlugin.PLUGIN_ID);
		setupJavaProject();
		setupCProject();
		setupTextProject();
		setupSharedFolder(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());
	}

	// ------------------------------------------------------------------------
	// Project cleanup
	// ------------------------------------------------------------------------

	public void cleanupTestEnvironment() {
		try {
			cleanupSharedFolder(FSharedFolder);
			cleanupProject(FTextIProject, FTextRepository);
			cleanupProject(FCIProject, FCRepository);
			cleanupProject(FJavaIProject, FJavaRepository);
		} catch (Exception e) {
			// Don't care at this point
		}
	}

	// ------------------------------------------------------------------------
	// Projects
	// ------------------------------------------------------------------------

	private void setupJavaProject() throws CoreException, IOException, URISyntaxException {

		//Create project
		FJavaIProject = createProject(fTestUtilsID + "-" + UI_TEST_JAVA_PROJECT_NAME);

		//Create repository
		FJavaRepository = createRepository(FJavaIProject);
		FJavaWorkdirPrefix = getWorkDir(FJavaRepository);

		//Connect main project with repository
		connectProjectWithRepo(FJavaIProject, FJavaRepository);

		//Set Project As Java Project
		FJavaIJavaProject = setProjectAsJava(FJavaIProject);

		//Add initial files to project
		FJavaFile1 = addFileToProject(FJavaIProject, JAVA_FILE1_PROJ_NAME, JAVA_FILE1_EXT_PATH);
		FJavaFile2 = addFileToProject(FJavaIProject, JAVA_FILE2_PROJ_NAME, JAVA_FILE2_EXT_PATH);

		//Commit initial files to repository
		List<IResource> resources = new ArrayList<IResource>();
		resources.add(FJavaFile1);
		resources.add(FJavaFile2);
		addFilesToRepository(resources);
		commitFiles(FJavaIProject, FJavaRepository, "first Java Commit", false);

		//Update files (Modify File1, Remove File2, Add File 3, Add File4)
		FJavaFile1 = changeContentOfFile(FJavaFile1, JAVA_FILE1_EXT_MOD_PATH);
		FJavaFile2.delete(true, null);
		FJavaFile3 = addFileToProject(FJavaIProject, JAVA_FILE3_PROJ_NAME, JAVA_FILE3_EXT_PATH);
		FJavaFile4 = addFileToProject(FJavaIProject, JAVA_FILE4_PROJ_NAME, JAVA_FILE4_EXT_PATH);

		//Commit modifications
		resources.clear();
		resources.add(FJavaFile1);
		resources.add(FJavaFile2);
		resources.add(FJavaFile3);
		resources.add(FJavaFile4);
		addFilesToRepository(resources);
		commitFiles(FJavaIProject, FJavaRepository, "second Java Commit", false);

		//Update files (Modify File4, Add File5, do not commit)
		FJavaFile4 = changeContentOfFile(FJavaFile4, JAVA_FILE4_EXT_MOD_PATH);
		FJavaFile5 = addFileToProject(FJavaIProject, JAVA_FILE5_PROJ_NAME, JAVA_FILE5_EXT_PATH);
	}

	private void setupCProject() throws CoreException, IOException, URISyntaxException {
		//Create project
		FCIProject = createProject(fTestUtilsID + "-" + UI_TEST_C_PROJECT_NAME);

		//Create repository
		FCRepository = createRepository(FCIProject);
		FCWorkdirPrefix = getWorkDir(FCRepository);

		//Connect main project with repository
		connectProjectWithRepo(FCIProject, FCRepository);

		//Set Project As C/C++ Project
		FCICProject = setProjectAsC(FCIProject);

		//Add files to project
		FCFile1 = addFileToProject(FCIProject, C_FILE1_PROJ_NAME, C_FILE1_EXT_PATH);
		FCFile2 = addFileToProject(FCIProject, C_FILE2_PROJ_NAME, C_FILE2_EXT_PATH);

		//Commit files to repository
		List<IResource> resources = new ArrayList<IResource>();
		resources.add(FCFile1);
		resources.add(FCFile2);
		addFilesToRepository(resources);
		commitFiles(FCIProject, FCRepository, "first C Commit", false);

		//Update files (Modify File1, Remove File2, Add File 3, Add File4)
		FCFile1 = changeContentOfFile(FCFile1, C_FILE1_EXT_MOD_PATH);
		FCFile2.delete(true, null);
		FCFile3 = addFileToProject(FCIProject, C_FILE3_PROJ_NAME, C_FILE3_EXT_PATH);
		FCFile4 = addFileToProject(FCIProject, C_FILE4_PROJ_NAME, C_FILE4_EXT_PATH);

		//Commit modifications
		resources.clear();
		resources.add(FCFile1);
		resources.add(FCFile2);
		resources.add(FCFile3);
		resources.add(FCFile4);
		addFilesToRepository(resources);
		commitFiles(FCIProject, FCRepository, "second C Commit", false);

		//Update files (Modify File4, Add File5, do not commit)
		FCFile4 = changeContentOfFile(FCFile4, C_FILE4_EXT_MOD_PATH);
		FCFile5 = addFileToProject(FCIProject, C_FILE5_PROJ_NAME, C_FILE5_EXT_PATH);
	}

	private void setupTextProject() throws CoreException, IOException, URISyntaxException {
		//Create project
		FTextIProject = createProject(fTestUtilsID + "-" + UI_TEST_TEXT_PROJECT_NAME);

		//Create repository
		FTextRepository = createRepository(FTextIProject);
		FTextWorkdirPrefix = getWorkDir(FTextRepository);

		//Connect main project with repository
		connectProjectWithRepo(FTextIProject, FTextRepository);

		//Add files to project
		FTextFile1 = addFileToProject(FTextIProject, TEXT_FILE1_PROJ_NAME, TEXT_FILE1_EXT_PATH);
		FTextFile2 = addFileToProject(FTextIProject, TEXT_FILE2_PROJ_NAME, TEXT_FILE2_EXT_PATH);

		//Commit files to repository
		List<IResource> resources = new ArrayList<IResource>();
		resources.add(FTextFile1);
		resources.add(FTextFile2);
		addFilesToRepository(resources);
		commitFiles(FTextIProject, FTextRepository, "first Text Commit", false);

		//Update files (Modify File1, Remove File2, Add File 3, Add File4)
		FTextFile1 = changeContentOfFile(FTextFile1, TEXT_FILE1_EXT_MOD_PATH);
		FTextFile2.delete(true, null);
		FTextFile3 = addFileToProject(FTextIProject, TEXT_FILE3_PROJ_NAME, TEXT_FILE3_EXT_PATH);
		FTextFile4 = addFileToProject(FTextIProject, TEXT_FILE4_PROJ_NAME, TEXT_FILE4_EXT_PATH);

		//Commit modifications
		resources.clear();
		resources.add(FTextFile1);
		resources.add(FTextFile2);
		resources.add(FTextFile3);
		resources.add(FTextFile4);
		addFilesToRepository(resources);
		commitFiles(FTextIProject, FTextRepository, "second Text Commit", false);

		//Update files (Modify File4, Add File5, do not commit)
		FTextFile4 = changeContentOfFile(FTextFile4, TEXT_FILE4_EXT_MOD_PATH);
		FTextFile5 = addFileToProject(FTextIProject, TEXT_FILE5_PROJ_NAME, TEXT_FILE5_EXT_PATH);
	}

	private void cleanupProject(IProject aProject, Repository aRepository) throws CoreException, IOException {
		//Disconnect project from repository
		disconnectProject(aProject);

		//Delete Repository
		deleteRepository(aRepository);

		//Delete project
		deleteProject(aProject);
	}

	// ------------------------------------------------------------------------
	// Project helper functions
	// ------------------------------------------------------------------------

	private IProject createProject(String aProjectName) throws CoreException, IOException {
		TestUtils.waitForJobs();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(aProjectName);
		if (project.exists()) {
			project.delete(true, new NullProgressMonitor());
		}
		project.create(null);
		project.open(null);
		return project;
	}

	private void deleteProject(IProject aProject) throws CoreException, IOException {
		if (aProject.exists()) {
			aProject.refreshLocal(IResource.DEPTH_INFINITE, null);
			aProject.close(null);
			aProject.delete(true, true, null);
		} else {
			File f = new File(aProject.getLocation().toOSString());
			if (f.exists()) {
				FileUtils.delete(f, FileUtils.RECURSIVE | FileUtils.RETRY);
			}
		}
	}

	private IJavaProject setProjectAsJava(IProject aProject) throws CoreException, IOException {
		IJavaProject javaProject = JavaCore.create(aProject);

		IFolder binFolder = aProject.getFolder("bin");
		binFolder.create(false, true, null);

		IProjectDescription description = aProject.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		aProject.setDescription(description, null);

		javaProject.setRawClasspath(new IClasspathEntry[0], null);
		IPath outputLocation = binFolder.getFullPath();
		javaProject.setOutputLocation(outputLocation, null);

		IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
		System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
		newEntries[oldEntries.length] = JavaRuntime.getDefaultJREContainerEntry();
		javaProject.setRawClasspath(newEntries, null);
		return javaProject;
	}

	private IProject setProjectAsC(IProject aProject) throws CoreException, IOException {
		IProjectDescription description = aProject.getDescription();
		description.setNatureIds(new String[] { CProjectNature.C_NATURE_ID });
		aProject.setDescription(description, null);
		IProject cProject = CCorePlugin.getDefault().createCDTProject(description, aProject, null);
		IFolder binFolder = aProject.getFolder("bin");
		binFolder.create(false, true, null);
		return cProject;
	}

	private IFile addFileToProject(IProject aProject, String aProjFilepath, String aExtFilePath) throws IOException,
			CoreException, URISyntaxException {
		URL location = FileLocator.find(Platform.getBundle(R4EUITestPlugin.PLUGIN_ID), new Path(aExtFilePath), null);
		File extFile = new File(FileLocator.toFileURL(location).toURI());
		FileInputStream content = new FileInputStream(extFile);

		IPath filePath = new Path(aProjFilepath);
		IFolder folder = null;
		for (int i = 0; i < filePath.segmentCount() - 1; i++) {
			if (folder == null) {
				folder = aProject.getFolder(filePath.segment(i));
			} else {
				folder = folder.getFolder(filePath.segment(i));
			}
			if (!folder.exists()) {
				folder.create(false, true, null);
			}
		}
		IFile file = aProject.getFile(filePath);
		file.create(content, true, null);
		content.close();
		return file;
	}

	// ------------------------------------------------------------------------
	// Repository helper functions
	// ------------------------------------------------------------------------

	private Repository createRepository(IProject aProject) throws CoreException, IOException {
		TestUtils.waitForJobs();
		File gitDir = new File(aProject.getLocation().toOSString(), Constants.DOT_GIT);
		Repository repository = new FileRepository(gitDir);
		try {
			repository.create();
			Config storedConfig = repository.getConfig();
			storedConfig.setEnum("core", null, "autocrlf", AutoCRLF.FALSE);

		} catch (IllegalStateException e) {
			//Just go on
		}
		return repository;
	}

	private void deleteRepository(Repository aRepository) throws CoreException, IOException {
		Repository repository = aRepository;
		repository.close();
		repository = null;
	}

	private void connectProjectWithRepo(IProject aProject, Repository aRepository) throws CoreException, IOException {
		ConnectProviderOperation op = new ConnectProviderOperation(aProject, aRepository.getDirectory());
		op.execute(null);
	}

	private void disconnectProject(IProject aProject) throws CoreException, IOException {
		Collection<IProject> projects = Collections.singleton(aProject.getProject());
		DisconnectProviderOperation disconnect = new DisconnectProviderOperation(projects);
		disconnect.execute(null);
	}

	private void addFilesToRepository(List<IResource> aResources) throws CoreException {
		new AddToIndexOperation(aResources).execute(null);
	}

	// ------------------------------------------------------------------------
	// Workspace helper functions
	// ------------------------------------------------------------------------

	private String getWorkDir(Repository aRepository) throws CoreException, IOException {
		String workDir;
		try {
			workDir = aRepository.getWorkTree().getCanonicalPath();
		} catch (IOException err) {
			workDir = aRepository.getWorkTree().getAbsolutePath();
		}
		workDir = workDir.replace('\\', '/');
		if (!workDir.endsWith("/")) {
			workDir += "/"; //$NON-NLS-1$
		}
		return workDir;
	}

	public IFile changeContentOfFile(IFile file, String aNewContentsFilePath) throws CoreException, IOException,
			URISyntaxException {
		URL location = FileLocator.find(Platform.getBundle(R4EUITestPlugin.PLUGIN_ID), new Path(aNewContentsFilePath),
				null);
		File extFile = new File(FileLocator.toFileURL(location).toURI());
		FileInputStream newContent = new FileInputStream(extFile);
		file.setContents(newContent, 0, null);
		newContent.close();
		return file;
	}

	public void commitFiles(IProject aProject, Repository aRepository, String aCommitMsg, boolean aAmend)
			throws CoreException {
		CommitOperation commitOperation = new CommitOperation(null, null, null, TestUtils.AUTHOR, TestUtils.COMMITTER,
				aCommitMsg);
		commitOperation.setCommitAll(true);
		commitOperation.setAmending(aAmend);
		commitOperation.setRepository(aRepository);
		commitOperation.execute(null);
		aProject.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	public void commitAmendFiles(IProject aProject, Repository aRepository, String aCommitMsg,
			Collection<String> acommitFileList) throws CoreException {
		CommitOperation commitOperation = new CommitOperation(null, acommitFileList, null, TestUtils.AUTHOR,
				TestUtils.COMMITTER, aCommitMsg);
		commitOperation.setAmending(true);
		commitOperation.setRepository(aRepository);
		commitOperation.execute(null);
		aProject.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	// ------------------------------------------------------------------------
	// Shared folder
	// ------------------------------------------------------------------------

	private void setupSharedFolder(String dir) {
		if (!dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		dir = dir + fTestUtilsID + "-shared";
		FSharedFolder = dir;
	}

	private void cleanupSharedFolder(String aFolder) throws IOException {
		if (null != aFolder) {
			File f = new File(aFolder);
			if (f.exists()) {
				FileUtils.delete(f, FileUtils.RECURSIVE | FileUtils.RETRY);
			}
		}
	}

	// ------------------------------------------------------------------------
	// Review Navigator
	// ------------------------------------------------------------------------

	public void startNavigatorView() {
		waitForJobs();
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					//First make sure the welcome view is removed
					IViewPart welcomeView = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow()
							.getActivePage()
							.findView("org.eclipse.ui.internal.introview"); //$NON-NLS-1$
					if (welcomeView != null) {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(welcomeView);
					}

					FTestNavigatorView = (ReviewNavigatorView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow()
							.getActivePage()
							.showView(R4E_NAVIGATOR_VIEW_NAME);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void stopNavigatorView() {
		waitForJobs();
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(FTestNavigatorView);
			}
		});
	}

	public R4EUIRootElement getRootElement() {
		return FRootElement;
	}

	/**
	 * Get the initial input from the R4E model and populate the UI model with it
	 * 
	 * @return the root element of the UI model
	 */
	public IR4EUIModelElement getInitalInput() {
		R4EUIModelController.loadModel();
		final IR4EUIModelElement rootTreeNode = R4EUIModelController.getRootElement();
		rootTreeNode.getChildren();
		return rootTreeNode;
	}

	/**
	 * Method setDefaultUser
	 */
	public void setDefaultUser(R4EUITestMain proxy) {
		proxy.getPreferencesProxy().setUser(TestConstants.DEFAULT_USER_ID);
		proxy.getPreferencesProxy().setEmail(TestConstants.DEFAULT_USER_EMAIL);
		Assert.assertEquals(TestConstants.DEFAULT_USER_ID, proxy.getPreferencesProxy().getUser());
		Assert.assertEquals(TestConstants.DEFAULT_USER_EMAIL, proxy.getPreferencesProxy().getEmail());
	}

	// ------------------------------------------------------------------------
	// General utilities
	// ------------------------------------------------------------------------

	/**
	 * Wait for the ongoing jobs to complete
	 */
	public static void waitForJobs() {
		//NOTE: In order to the above to work, no background jobs (such as Usage Data Collector) should be running.
		//otherwise we will be blocked forever here.  An alternative solution is to join our thread to the 
		//executing job family, but that might make the tests unreliable because we cannot control
		//scheduling of the job which might cause livelocks
		while (!Job.getJobManager().isIdle()) {
			delay(1000);
		}
		/*
		try {
			Job.getJobManager().join(R4EUIConstants.R4E_UI_JOB_FAMILY, null);
		} catch (OperationCanceledException e) {
			R4EUITestPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (InterruptedException e) {
			R4EUITestPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
		*/
	}

	/**
	 * Delay (suspend) this job for a certain duration
	 * 
	 * @param delay
	 *            wait time (ms)
	 */
	private static void delay(long delay) {
		Display display = Display.getCurrent();

		//If this is the UI thread, process input
		if (null != display) {
			long endTimeMillis = System.currentTimeMillis() + delay;
			while (System.currentTimeMillis() < endTimeMillis) {
				try {
					if (!display.isDisposed() && !display.readAndDispatch()) {
						display.sleep();
					}
				} catch (Exception e) {
					// Ignore
				}
			}
			display.update();
		} else {
			//Just sleep
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				//ignore
			}
		}
	}

}
