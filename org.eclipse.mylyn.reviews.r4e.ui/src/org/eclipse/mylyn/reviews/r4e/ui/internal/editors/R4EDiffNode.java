// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as the difference node in R4E Compare Editor inputs 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * @author Sebastien Dubois
 * @author Steffen Pingel
 * @version $Revision: 1.0 $
 */
public class R4EDiffNode extends DiffNode {

	// ------------------------------------------------------------------------
	// Members
	// -----------------------------------------------------------------------

	private IPath path;

	private String name;

	/**
	 * Field fTargetElement
	 */
	private final ITypedElement fTargetElement;

	/**
	 * Field fBaseElement
	 */
	private final ITypedElement fBaseElement;

	/**
	 * Field fTargetFile
	 */
	private final R4EUIFileContext fTargetFile;

	/**
	 * Field fBaseFile
	 */
	private final R4EUIFileContext fBaseFile;

	/**
	 * Field fAnnotationSupport
	 */
	private IReviewAnnotationSupport fAnnotationSupport = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EDiffNode.
	 * 
	 * @param aTargetFile
	 *            R4EUIFileContext
	 * @param aBaseFile
	 *            R4EUIFileContext
	 * @param aUseBaseFileTargetVersion
	 *            - boolean
	 */
	public R4EDiffNode(R4EUIFileContext aTargetFile, R4EUIFileContext aBaseFile, boolean aUseBaseFileTargetVersion) {
		super(Differencer.NO_CHANGE);
		fTargetFile = aTargetFile;
		fBaseFile = aBaseFile;

		//The target version of the selected UI File Context element is always used as target for compare
		R4EFileVersion targetVersion = null;
		if (null != aTargetFile) {
			targetVersion = aTargetFile.getTargetFileVersion();
		}

		//Use the proper base file version based on the Compare editor input type
		R4EFileVersion baseVersion = null;
		if (null != aBaseFile) {
			if (aUseBaseFileTargetVersion) {
				baseVersion = aBaseFile.getTargetFileVersion();
			} else {
				baseVersion = aBaseFile.getBaseFileVersion();
			}
		}

		//Set content for the two sides of the compare editor
		fTargetElement = CommandUtils.createTypedElement(targetVersion);
		setLeft(fTargetElement);
		fBaseElement = CommandUtils.createTypedElement(baseVersion);

		setRight(fBaseElement);
		setKind(resolveKind(targetVersion, baseVersion));
		setPath(Path.fromPortableString(resolvePath(targetVersion, baseVersion)));
		name = path.lastSegment();
	}

	public R4EDiffNode(String name) {
		super(Differencer.NO_CHANGE);
		this.name = name;
		fTargetFile = null;
		fBaseFile = null;
		fTargetElement = null;
		fBaseElement = null;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method resolveKind.
	 * 
	 * @param aTargetVersion
	 *            - R4EFileVersion
	 * @return int
	 */
	private int resolveKind(R4EFileVersion aTargetVersion, R4EFileVersion aBaseVersion) {
		if (null == aTargetVersion || null == aTargetVersion.getRepositoryPath()) {
			return Differencer.ADDITION;
		}
		if (null == aBaseVersion || null == aBaseVersion.getRepositoryPath()) {
			return Differencer.DELETION;
		}
		return Differencer.CHANGE;
	}

	/**
	 * Method resolvePath.
	 * 
	 * @param aTargetVersion
	 *            - R4EFileVersion
	 * @return String
	 */
	private String resolvePath(R4EFileVersion aTargetVersion, R4EFileVersion aBaseVersion) {
		if (null != aTargetVersion && null != aTargetVersion.getRepositoryPath()) {
			return aTargetVersion.getRepositoryPath();
		}
		if (null != aBaseVersion && null != aBaseVersion.getRepositoryPath()) {
			return aBaseVersion.getRepositoryPath();
		}
		return ""; //Should never happen
	}

	/**
	 * Method getTargetLabel.
	 * 
	 * @return String
	 */
	public String getTargetLabel() {
		return getLabel("Target: ", fTargetElement); //$NON-NLS-1$
	}

	/**
	 * Method getBaseLabel.
	 * 
	 * @return String
	 */
	public String getBaseLabel() {
		return getLabel("Base: ", fBaseElement); //$NON-NLS-1$
	}

	/**
	 * Method getLabel.
	 * 
	 * @param aTitle
	 *            - String
	 * @param aElement
	 *            - ITypedElement
	 * @return String
	 */
	private String getLabel(String aTitle, ITypedElement aElement) {
		final StringBuilder label = new StringBuilder(aTitle + (null != aElement ? aElement.getName() : ""));
		R4EFileVersion version = getFileVersion(aElement);
		if (null != version && !CommandUtils.useWorkspaceResource(version)) {
			label.append(" " + version.getVersionID()); //$NON-NLS-1$
		}
		return label.toString();
	}

	/**
	 * Method getTargetFile.
	 * 
	 * @return R4EUIFileContext
	 */
	public R4EUIFileContext getTargetFile() {
		return fTargetFile;
	}

	/**
	 * Method getBaseFile.
	 * 
	 * @return R4EUIFileContext
	 */
	public R4EUIFileContext getBaseFile() {
		return fBaseFile;
	}

	/**
	 * Method getTargetVersion.
	 * 
	 * @return R4EFileVersion
	 */
	public R4EFileVersion getTargetVersion() {
		return getFileVersion(fTargetElement);
	}

	/**
	 * Method getBaseVersion.
	 * 
	 * @return R4EFileVersion
	 */
	public R4EFileVersion getBaseVersion() {
		return getFileVersion(fBaseElement);
	}

	/**
	 * Method getFileVersion.
	 * 
	 * @param aElement
	 *            - ITypedElement
	 * @return R4EFileVersion
	 */
	private R4EFileVersion getFileVersion(ITypedElement aElement) {
		if (aElement instanceof R4EFileTypedElement) {
			return ((R4EFileTypedElement) aElement).getFileVersion();
		} else if (aElement instanceof R4EFileRevisionTypedElement) {
			return ((R4EFileRevisionTypedElement) aElement).getFileVersion();
		}
		return null;
	}

	/**
	 * Method getTargetTypedElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getTargetTypedElement() {
		return fTargetElement;
	}

	/**
	 * Method getBaseTypedElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getBaseTypedElement() {
		return fBaseElement;
	}

	/**
	 * Method setAnnotationSupport.
	 * 
	 * @param aSupport
	 *            - IReviewAnnotationSupport
	 */
	public void setAnnotationSupport(IReviewAnnotationSupport aSupport) {
		fAnnotationSupport = aSupport;
	}

	/**
	 * Method getAnnotationSupport.
	 * 
	 * @return IReviewAnnotationSupport
	 */
	public IReviewAnnotationSupport getAnnotationSupport() {
		return fAnnotationSupport;
	}

	/**
	 * Method refreshAnnotations.
	 */
	public void refreshAnnotations() {
		if (fAnnotationSupport != null) {
			fAnnotationSupport.refreshAnnotations(null);
		}
	}

	@Override
	public Image getImage() {
		return (fTargetFile == null && fBaseFile == null) ? PlatformUI.getWorkbench()
				.getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_FOLDER) : super.getImage();
	}

	@Override
	public String getName() {
		return name;
	}

	public IPath getPath() {
		return path;
	}

	@Override
	public String getType() {
		return (fTargetFile != null || fBaseFile != null) ? super.getType() : FOLDER_TYPE;
	}

	public void setPath(IPath path) {
		this.path = path;
	}

	public void setName(String name) {
		this.name = name;
	}
}
