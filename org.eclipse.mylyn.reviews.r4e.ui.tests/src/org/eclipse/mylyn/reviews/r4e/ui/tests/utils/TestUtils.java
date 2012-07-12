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
 *   
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
import java.util.List;

import junit.framework.Assert;

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
import org.eclipse.jgit.lib.Constants;
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

@SuppressWarnings("restriction")
public class TestUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	//Git
	public final static String AUTHOR = "The Author <The.author@some.com>";

	public final static String COMMITTER = "The Commiter <The.committer@some.com>";

	//Java
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

	//C
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

	//Text
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

	//Test Views
	public static final String R4E_NAVIGATOR_VIEW_NAME = "org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView"; // $codepro.audit.disable constantNamingConvention

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	public static String FSharedFolder = null;

	public static IProject FExtProject = null;

	//Java
	public static IProject FJavaIProject = null;

	public static IJavaProject FJavaIJavaProject = null;

	public static Repository FJavaRepository = null;

	public static String FJavaWorkdirPrefix = null;

	public static IFile FJavaFile1 = null;

	public static IFile FJavaFile2 = null;

	public static IFile FJavaFile3 = null;

	public static IFile FJavaFile4 = null;

	public static IFile FJavaFile5 = null;

	//C
	public static IProject FCIProject = null;

	public static IProject FCICProject = null;

	public static Repository FCRepository = null;

	public static String FCWorkdirPrefix = null;

	public static IFile FCFile1 = null;

	public static IFile FCFile2 = null;

	public static IFile FCFile3 = null;

	public static IFile FCFile4 = null;

	public static IFile FCFile5 = null;

	//Text
	public static IProject FTextIProject = null;

	public static Repository FTextRepository = null;

	public static String FTextWorkdirPrefix = null;

	public static IFile FTextFile1 = null;

	public static IFile FTextFile2 = null;

	public static IFile FTextFile3 = null;

	public static IFile FTextFile4 = null;

	public static IFile FTextFile5 = null;

	//UI Model
	public static R4EUIRootElement FRootElement;

	//Test View
	public static ReviewNavigatorView FTestNavigatorView;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public static void setupTestEnvironment() throws CoreException, IOException, URISyntaxException {
		FExtProject = ResourcesPlugin.getWorkspace().getRoot().getProject(R4EUITestPlugin.PLUGIN_ID);
		setupJavaProject();
		setupCProject();
		setupTextProject();
		setupSharedFolder();
	}

	private static void setupJavaProject() throws CoreException, IOException, URISyntaxException {

		//Create project
		FJavaIProject = createProject(UI_TEST_JAVA_PROJECT_NAME);

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
		commitFiles(FJavaIProject, FJavaRepository, "first Java Commit");

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
		commitFiles(FJavaIProject, FJavaRepository, "second Java Commit");

		//Update files (Modify File4, Add File5, do not commit)
		FJavaFile4 = changeContentOfFile(FJavaFile4, JAVA_FILE4_EXT_MOD_PATH);
		FJavaFile5 = addFileToProject(FJavaIProject, JAVA_FILE5_PROJ_NAME, JAVA_FILE5_EXT_PATH);
	}

	private static void setupCProject() throws CoreException, IOException, URISyntaxException {
		//Create project
		FCIProject = createProject(UI_TEST_C_PROJECT_NAME);

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
		commitFiles(FCIProject, FCRepository, "first C Commit");

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
		commitFiles(FCIProject, FCRepository, "second C Commit");

		//Update files (Modify File4, Add File5, do not commit)
		FCFile4 = changeContentOfFile(FCFile4, C_FILE4_EXT_MOD_PATH);
		FCFile5 = addFileToProject(FCIProject, C_FILE5_PROJ_NAME, C_FILE5_EXT_PATH);
	}

	private static void setupTextProject() throws CoreException, IOException, URISyntaxException {
		//Create project
		FTextIProject = createProject(UI_TEST_TEXT_PROJECT_NAME);

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
		commitFiles(FTextIProject, FTextRepository, "first Text Commit");

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
		commitFiles(FTextIProject, FTextRepository, "second Text Commit");

		//Update files (Modify File4, Add File5, do not commit)
		FTextFile4 = changeContentOfFile(FTextFile4, TEXT_FILE4_EXT_MOD_PATH);
		FTextFile5 = addFileToProject(FTextIProject, TEXT_FILE5_PROJ_NAME, TEXT_FILE5_EXT_PATH);
	}

	private static IProject createProject(String aProjectName) throws CoreException, IOException {
		TestUtils.waitForJobs();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(aProjectName);
		project.create(null);
		project.open(null);
		return project;
	}

	private static Repository createRepository(IProject aProject) throws CoreException, IOException {
		TestUtils.waitForJobs();
		File gitDir = new File(aProject.getLocation().toOSString(), Constants.DOT_GIT);
		Repository repository = new FileRepository(gitDir);
		try {
			repository.create();
		} catch (IllegalStateException e) {
			//Jusy go on
		}
		return repository;
	}

	private static String getWorkDir(Repository aRepository) throws CoreException, IOException {
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

	private static void connectProjectWithRepo(IProject aProject, Repository aRepository) throws CoreException,
			IOException {
		ConnectProviderOperation op = new ConnectProviderOperation(aProject, aRepository.getDirectory());
		op.execute(null);
	}

	private static IJavaProject setProjectAsJava(IProject aProject) throws CoreException, IOException {
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

	private static IProject setProjectAsC(IProject aProject) throws CoreException, IOException {
		IProjectDescription description = aProject.getDescription();
		description.setNatureIds(new String[] { CProjectNature.C_NATURE_ID });
		aProject.setDescription(description, null);
		IProject cProject = CCorePlugin.getDefault().createCDTProject(description, aProject, null);
		IFolder binFolder = aProject.getFolder("bin");
		binFolder.create(false, true, null);
		return cProject;
	}

	private static IFile addFileToProject(IProject aProject, String aProjFilepath, String aExtFilePath)
			throws IOException, CoreException, URISyntaxException {
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

	public static IFile changeContentOfFile(IFile file, String aNewContentsFilePath) throws CoreException, IOException,
			URISyntaxException {
		URL location = FileLocator.find(Platform.getBundle(R4EUITestPlugin.PLUGIN_ID), new Path(aNewContentsFilePath),
				null);
		File extFile = new File(FileLocator.toFileURL(location).toURI());
		FileInputStream newContent = new FileInputStream(extFile);
		file.setContents(newContent, 0, null);
		newContent.close();
		return file;
	}

	public static void addFilesToRepository(List<IResource> aResources) throws CoreException {
		new AddToIndexOperation(aResources).execute(null);
	}

	public static void commitFiles(IProject aProject, Repository aRepository, String aCommitMsg) throws CoreException {
		CommitOperation commitOperation = new CommitOperation(null, null, null, TestUtils.AUTHOR, TestUtils.COMMITTER,
				aCommitMsg);
		commitOperation.setCommitAll(true);
		commitOperation.setRepository(aRepository);
		commitOperation.execute(null);
		aProject.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	public static void commitAmendFiles(IProject aProject, Repository aRepository, String aCommitMsg,
			Collection<String> acommitFileList) throws CoreException {
		CommitOperation commitOperation = new CommitOperation(null, acommitFileList, null, TestUtils.AUTHOR,
				TestUtils.COMMITTER, aCommitMsg);
		commitOperation.setAmending(true);
		commitOperation.setRepository(aRepository);
		commitOperation.execute(null);
		aProject.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	private static void setupSharedFolder() {
		String dir = System.getProperty("java.io.tmpdir");
		if (!dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		dir = dir + "R4ETest";
		FSharedFolder = dir;
	}

	public static void startNavigatorView() {
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

	public static void cleanupTestEnvironment() throws CoreException, IOException {
		cleanupSharedFolder(FSharedFolder + File.separator);
		cleanupProject(FTextIProject, FTextRepository);
		cleanupProject(FCIProject, FCRepository);
		cleanupProject(FJavaIProject, FJavaRepository);
	}

	public static void stopNavigatorView() {
		waitForJobs();
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(FTestNavigatorView);
			}
		});
	}

	private static void cleanupProject(IProject aProject, Repository aRepository) throws CoreException, IOException {
		//Disconnect project from repository
		disconnectProject(aProject);

		//Delete Repository
		deleteRepository(aRepository);

		//Delete project
		deleteProject(aProject);
	}

	private static void disconnectProject(IProject aProject) throws CoreException, IOException {
		Collection<IProject> projects = Collections.singleton(aProject.getProject());
		DisconnectProviderOperation disconnect = new DisconnectProviderOperation(projects);
		disconnect.execute(null);
	}

	private static void deleteRepository(Repository aRepository) throws CoreException, IOException {
		Repository repository = aRepository;
		repository.close();
		repository = null;
	}

	private static void deleteProject(IProject aProject) throws CoreException, IOException {
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

	private static void cleanupSharedFolder(String aFolder) throws IOException {
		if (null != aFolder) {
			File f = new File(aFolder);
			if (f.exists()) {
				FileUtils.delete(f, FileUtils.RECURSIVE | FileUtils.RETRY);
			}
		}
	}

	public static void waitForJobs() {
		//NOTE: In order to the above to work, no backgroup jobs (such as Usage Data Collector) should be running.
		//otherwise we will be blocked forever here.  An alternative solution is to join our thread to the 
		//executing job family, but that might make the tests unreliable because we cannot control
		//scheduling of the jos which might cause livelocks
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

	public static void delay(long aWaitTimeMillis) {
		Display display = Display.getCurrent();

		//If this is the UI thread, process input
		if (null != display) {
			long endTimeMillis = System.currentTimeMillis() + aWaitTimeMillis;
			while (System.currentTimeMillis() < endTimeMillis) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			display.update();
		} else {
			//Just sleep
			try {
				Thread.sleep(aWaitTimeMillis);
			} catch (InterruptedException e) {
				//ignore
			}
		}
	}

	/**
	 * Get the initial input from the R4E model and populate the UI model with it
	 * 
	 * @return the root element of the UI model
	 */
	public static IR4EUIModelElement getInitalInput() {
		R4EUIModelController.loadModel();
		final IR4EUIModelElement rootTreeNode = R4EUIModelController.getRootElement();
		rootTreeNode.getChildren();
		return rootTreeNode;
	}

	public static R4EUIRootElement getRootElement() {
		return FRootElement;
	}

	/**
	 * Method setDefaultUser
	 */
	public static void setDefaultUser() {
		R4EUITestMain.getInstance().getPreferencesProxy().setUser(TestConstants.DEFAULT_USER_ID);
		R4EUITestMain.getInstance().getPreferencesProxy().setEmail(TestConstants.DEFAULT_USER_EMAIL);
		Assert.assertEquals(TestConstants.DEFAULT_USER_ID, R4EUITestMain.getInstance().getPreferencesProxy().getUser());
		Assert.assertEquals(TestConstants.DEFAULT_USER_EMAIL, R4EUITestMain.getInstance()
				.getPreferencesProxy()
				.getEmail());
	}
}
