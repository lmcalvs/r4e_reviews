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
 *   Alvaro Sanchez-Leon - Intial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.Activator;

/**
 * @author Alvaro Sanchez-Leon
 *
 */
public class ResourceUtils {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Receive a platform URI in string form and resolves to a workspace Ifile
	 * 
	 * @param platformUriStr
	 * @return
	 * @throws FileNotFoundException
	 */
	public static IFile toIFile(String platformUriStr) throws FileNotFoundException {
		if (platformUriStr == null) {
			return null;
		}

		URI platformUri = URI.createURI(platformUriStr);
		return toIFile(platformUri);
	}

	/**
	 * Receive a platform URI to a project in string form and resolves to a workspace IProject
	 * 
	 * @param platformUriStr
	 * @return
	 * @throws FileNotFoundException
	 */
	public static IProject toIProject(String platformUriStr) throws FileNotFoundException {
		if (platformUriStr == null) {
			return null;
		}

		URI platformUri = URI.createURI(platformUriStr);
		return toIProject(platformUri);
	}

	/**
	 * Resolves a platform URI to a workspace IFile
	 * 
	 * @param platformUri
	 * @return
	 * @throws FileNotFoundException
	 */
	public static IFile toIFile(URI platformUri) throws FileNotFoundException {
		if (platformUri == null) {
			return null;
		}
		
		if (!platformUri.isPlatform()) {
			throw new IllegalArgumentException("Not a platform uri");
		}
		
		IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformUri.toPlatformString(true)));
		
		if (!ifile.exists()) {
			StringBuilder sb = new StringBuilder("The file: " + platformUri.toString()
					+ " does not exists in the workspace");
			Activator.fTracer.traceError(sb.toString());
			throw new FileNotFoundException(sb.toString());
		}

		return ifile;
	}

	/**
	 * Resolves a platform URI to a workspace IProject
	 * 
	 * @param platformUri
	 * @return
	 * @throws FileNotFoundException
	 */
	public static IProject toIProject(URI platformUri) throws FileNotFoundException {
		if (platformUri == null) {
			return null;
		}

		if (!platformUri.isPlatform()) {
			throw new IllegalArgumentException("Not a platform uri");
		}

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(platformUri.toPlatformString(true));

		if (iProject == null) {
			StringBuilder sb = new StringBuilder("The Project: " + platformUri.toString()
					+ " does not exists in the workspace");
			Activator.fTracer.traceError(sb.toString());
			throw new FileNotFoundException(sb.toString());
		}

		return iProject;
	}

	/**
	 * creates a workspace platform URI from a given IFile
	 * 
	 * @param ifile
	 * @return
	 */
	public static URI toPlatformURI(IFile ifile) {
		if (ifile == null) {
			throw new IllegalArgumentException("input is null");
		}

		URI genURI = URI.createPlatformResourceURI(ifile.getFullPath().toString(), true);
		return genURI;
	}

	/**
	 * Directly translate to serializable URI platform string. This utility method helps avoid importing EMF URI
	 * dependencies to user applications
	 * 
	 * @param aResource
	 * @return
	 */
	public static String toPlatformURIStr(IResource aResource) {
		if (aResource == null) {
			return null;
		}
		URI resUri = URI.createPlatformResourceURI(aResource.getFullPath().toString(), true);
		return resUri.toString();
	}

	/**
	 * @param project
	 * @param relativeProjectPath
	 * @return
	 */
	public static IResource findResource(IProject project, String relativeProjectPath) {
		if (project == null || relativeProjectPath == null) {
			return null;
		}

		// resolve the resource from the relative path
		return project.findMember(relativeProjectPath);
	}

	/**
	 * Return the projects currently opened in the work space
	 * 
	 * @return
	 */
	public static IProject[] getProjects() {
		IWorkspace root = ResourcesPlugin.getWorkspace();
		IProject[] projects = root.getRoot().getProjects();
		return projects;
	}

	/**
	 * Return the workspace project associated with the given name
	 * 
	 * @param name
	 * @return - IProject if found, null if not found
	 */
	public static IProject getProject(String name) {
		IWorkspace root = ResourcesPlugin.getWorkspace();
		IProject[] projects = root.getRoot().getProjects();
		IProject project = null;
		if (projects != null) {
			for (int i = 0; i < projects.length; i++) {
				project = projects[i];
				if (project.getName().equals(name)) {
					return project;
				}
			}
		}
		return null;
	}

	/**
	 * Find the workspace file identified by the absolute URI which is associated to the given project
	 * 
	 * @param aFilePathURI
	 *            - URI in absolute format
	 * @param aProject
	 *            - workspace project where is expected
	 * @return - null if no file found
	 */
	public static IFile getWorkSpaceFile(java.net.URI aFilePathURI, IProject aProject) {
		IFile[] files = getWorkSpaceFiles(aFilePathURI);
		for (int i = 0; i < files.length; i++) {
			IFile iFile = files[i];
			if (iFile.getProject().equals(aProject)) {
				// found
				return iFile;
			}
		}
		// not found
		return null;

	}

	/**
	 * Return all handles to Resource files for the given URI (in absolute form)
	 * 
	 * @param filePathURI
	 *            - Absolute URI to the file
	 * @return - File handles to resource files within the workspace
	 */
	private static IFile[] getWorkSpaceFiles(java.net.URI filePathURI) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IFile[] files = null;
		if (filePathURI != null) {
			files = filterNonExistentFiles(root.findFilesForLocationURI(filePathURI));
		}

		return files;
	}

	/**
	 * @param files
	 * @return
	 */
	private static IFile[] filterNonExistentFiles(IFile[] files) {
		if (files == null) {
			return null;
		}
		int length = files.length;
		ArrayList<IFile> existentFiles = new ArrayList<IFile>(length);
		for (int i = 0; i < length; i++) {
			if (files[i].exists()) {
				existentFiles.add(files[i]);
			} else if (files[i].getType() == IResource.FILE) {
				existentFiles.add(files[i]);
			}
		}
		return (IFile[]) existentFiles.toArray(new IFile[existentFiles.size()]);
	}

	/**
	 * From a file path e.g. file://c:/dir/demo.xml return the folder path i.e. file://c:/dir
	 * 
	 * @param uri
	 * @return
	 */
	public static URI getFolderPath(URI uri) {
		URI retURI = null;
		if (uri != null) {
			retURI = uri.trimSegments(1);
		}
		return retURI;
	}

}
	