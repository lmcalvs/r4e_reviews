// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the ITypedElements that are used as comparison items
 * with the eclipse compare editor
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.swt.graphics.Image;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4ECompareItem implements ITypedElement, IStreamContentAccessor { 

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fContent.
	 */
	private final URI fContent;
	
	/**
	 * Field fType - file type (typically file extension e.g. txt, java, c, h etc.)
	 */
	private final String fType;
	
	/**
	 * Field fName - the file name
	 */
	private final String fName;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4ECompareItem.
	 * @param aURI URI
	 */
	public R4ECompareItem(URI aURI) {
		fType = getFileType(aURI);
		fName = new File(aURI).getName();
		fContent = aURI;
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	
	/**
	 * Method getURI.
	 * @return URI
	 */
	public URI getURI() {
		return fContent;
	}
	
	
	/**
	 * Method getContents.
	 * @return InputStream
	 * @see org.eclipse.compare.IStreamContentAccessor#getContents()
	 */
	@Override
	public InputStream getContents() {

		try {
			//First check if the file exists in the workspace (internal file)
			if (ResourcesPlugin.getWorkspace().getRoot().exists(new Path(fContent.getPath()))) {
				final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fContent.getPath()));
				return file.getContents();
			}

			//This is an external file
			return EFS.getLocalFileSystem().getStore(fContent).openInputStream(EFS.NONE, null);

		} catch (CoreException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			return null;
		}
	}

	/**
	 * Method getName.
	 * @return String
	 * @see org.eclipse.compare.ITypedElement#getName()
	 */
	@Override
	public String getName() {
		return fName;
	}

	/**
	 * Method getImage
	 * @return Image 
	 * @see org.eclipse.compare.ITypedElement#getImage()
	 */
	@Override
	public Image getImage() {
		return null;   //No image provided
	}

	/**
	 * Method getType.
	 * @return String
	 * @see org.eclipse.compare.ITypedElement#getType()
	 */
	@Override
	public String getType() {
		return fType; 
	}
	
	/**
	 * Read the File extension type of the file to be compared to define the
	 * type of compare
	 * @param aURI URI
	 * @return String fileExtension
	 */
	private static String getFileType(URI aURI) {
		
		final String[] typeAr = new File(aURI).getName().split("\\.");
		if (null != typeAr && typeAr.length >= 1) {
			return typeAr[typeAr.length - 1];
		}
		return ITypedElement.TEXT_TYPE;
	}
	
	/**
	 * Method getPath - Get the file absolute path
	 * @return String
	 */
	public String getPath() {
		return fContent.getPath();
	}
} 