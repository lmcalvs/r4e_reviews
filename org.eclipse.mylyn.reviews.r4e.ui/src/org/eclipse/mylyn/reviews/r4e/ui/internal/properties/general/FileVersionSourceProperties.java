// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Properties source for the R4EFileVersion model
 * class
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.core.resources.IResource;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class FileVersionSourceProperties implements IPropertySource {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field FILE_VERSION_NAME_ID. (value is ""fileVersionElement.name"")
	 */
	private static final String FILE_VERSION_NAME_ID = "fileVersionElement.name";

	/**
	 * Field FILE_VERSION_NAME_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_VERSION_NAME_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_VERSION_NAME_ID, R4EUIConstants.NAME_LABEL);

	/**
	 * Field FILE_VERSION_PATH_REPOSITORY_ID. (value is ""fileVersionElement.repositoryPath"")
	 */
	private static final String FILE_VERSION_PATH_REPOSITORY_ID = "fileVersionElement.repositoryPath";

	/**
	 * Field FILE_VERSION_PATH_REPOSITORY_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_VERSION_PATH_REPOSITORY_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_VERSION_PATH_REPOSITORY_ID, R4EUIConstants.PATH_REPOSITORY_LABEL);

	/**
	 * Field FILE_VERSION_PATH_ABSOLUTE_ID. (value is ""fileVersionElement.absolutePath"")
	 */
	private static final String FILE_VERSION_PATH_ABSOLUTE_ID = "fileVersionElement.absolutePath";

	/**
	 * Field FILE_VERSION_PATH_ABSOLUTE_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_VERSION_PATH_ABSOLUTE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_VERSION_PATH_ABSOLUTE_ID, R4EUIConstants.PATH_ABSOLUTE_LABEL);

	/**
	 * Field FILE_VERSION_PATH_PROJECT_ID. (value is ""fileVersionElement.projectPath"")
	 */
	private static final String FILE_VERSION_PATH_PROJECT_ID = "fileVersionElement.projectPath";

	/**
	 * Field FILE_VERSION_PATH_PROJECT_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_VERSION_PATH_PROJECT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_VERSION_PATH_PROJECT_ID, R4EUIConstants.PATH_PROJECT_LABEL);

	/**
	 * Field FILE_VERSION_ID. (value is ""fileVersionElement.version"")
	 */
	private static final String FILE_VERSION_ID = "fileVersionElement.version";

	/**
	 * Field FILE_VERSION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(FILE_VERSION_ID,
			R4EUIConstants.VERSION_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { FILE_VERSION_NAME_PROPERTY_DESCRIPTOR,
			FILE_VERSION_PROPERTY_DESCRIPTOR, FILE_VERSION_PATH_REPOSITORY_PROPERTY_DESCRIPTOR,
			FILE_VERSION_PATH_ABSOLUTE_PROPERTY_DESCRIPTOR, FILE_VERSION_PATH_PROJECT_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fFileVersion.
	 */
	private final R4EFileVersion fFileVersion;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIFileVersionSource.
	 * 
	 * @param aVersion
	 *            R4EFileVersion
	 */
	public FileVersionSourceProperties(R4EFileVersion aVersion) {
		fFileVersion = aVersion;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getEditableValue.
	 * 
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue() {
		return null;
	}

	/**
	 * Method getPropertyDescriptors.
	 * 
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}

	/**
	 * Method getPropertyValue.
	 * 
	 * @param aId
	 *            Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	public Object getPropertyValue(Object aId) {
		if (FILE_VERSION_NAME_ID.equals(aId)) {
			if (null != fFileVersion) {
				return fFileVersion.getName();
			}
		} else if (FILE_VERSION_PATH_REPOSITORY_ID.equals(aId)) {
			//The properties shows the absolute path
			if (null != fFileVersion) {
				return fFileVersion.getRepositoryPath();
			}
		} else if (FILE_VERSION_PATH_ABSOLUTE_ID.equals(aId)) {
			//The properties shows the absolute path
			if (null != fFileVersion) {
				final IResource resource = fFileVersion.getResource();
				if (null != resource) {
					return resource.getLocation().toPortableString();
				}
			}
		} else if (FILE_VERSION_PATH_PROJECT_ID.equals(aId)) {
			//The properties shows the absolute path
			if (null != fFileVersion) {
				final IResource resource = fFileVersion.getResource();
				if (null != resource) {
					return resource.getProjectRelativePath().toPortableString();
				}
			}
		} else if (FILE_VERSION_ID.equals(aId)) {
			if (null != fFileVersion) {
				return fFileVersion.getVersionID();
			}
		}
		return null;
	}

	/**
	 * Method isPropertySet.
	 * 
	 * @param id
	 *            Object
	 * @return boolean
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}

	/**
	 * Method resetPropertyValue.
	 * 
	 * @param id
	 *            Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(Object)
	 */
	public void resetPropertyValue(Object id) { // $codepro.audit.disable emptyMethod
		//no implementation needed
	}

	/**
	 * Method setPropertyValue.
	 * 
	 * @param id
	 *            Object
	 * @param value
	 *            Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	public void setPropertyValue(Object id, Object value) { // $codepro.audit.disable emptyMethod
		//no implementation needed
	}

}
