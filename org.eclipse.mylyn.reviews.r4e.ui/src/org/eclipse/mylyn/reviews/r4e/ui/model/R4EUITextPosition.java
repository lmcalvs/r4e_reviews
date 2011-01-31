// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the position interface to manage text positions
 * within a file under review.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUITextPosition implements IR4EUIPosition {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fPosition.
	 */
	private R4ETextPosition fPosition;
	
	/**
	 * Field fOffset.
	 */
	private int fOffset = R4EUIConstants.NO_OFFSET;
	
	/**
	 * Field fLength.
	 */
	private int fLength = R4EUIConstants.INVALID_VALUE;
	
	/**
	 * Field fStartLine.
	 */
	private int fStartLine = R4EUIConstants.INVALID_VALUE;
	
	/**
	 * Field fEndLine.
	 */
	private int fEndLine = R4EUIConstants.INVALID_VALUE;
	
	/**
	 * Field fName.
	 */
	private String fName = null;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for TextPosition.
	 * @param aOffset int
	 * @param aLength int
	 * @param aSourceFile IFile
	 * @throws CoreException
	 */
	public R4EUITextPosition(int aOffset, int aLength, IFile aSourceFile) throws CoreException {
		setPositionValues(aOffset, aLength, aSourceFile);
	}
	
	/**
	 * Constructor for TextPosition.
	 * @param aOffset int
	 * @param aLength int
	 * @param aStartLine int
	 * @param aEndLine int
	 */
	public R4EUITextPosition(int aOffset, int aLength, int aStartLine, int aEndLine) {
		fOffset = aOffset;
		fLength = aLength;
		fStartLine = aStartLine + R4EUIConstants.LINE_OFFSET;
		fEndLine = aEndLine  + R4EUIConstants.LINE_OFFSET;
	}
	
	/**
	 * Constructor for R4EUITextPosition.
	 * @param aModelPosition R4EPosition
	 */
	public R4EUITextPosition(R4EPosition aModelPosition) {
		fPosition = (R4ETextPosition)aModelPosition;
		fOffset = ((R4ETextPosition)aModelPosition).getStartPosition();
		fLength = ((R4ETextPosition)aModelPosition).getLength();
		fStartLine = ((R4ETextPosition)aModelPosition).getStartLine();
		fEndLine = ((R4ETextPosition)aModelPosition).getEndLine();
		fName = ((R4EContent) fPosition.eContainer()).getInfo();
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	// Attributes
	
	/**
	 * Method setPositionInModel.
	 * @param aModelPosition R4EPosition
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition#setPositionInModel(R4EPosition)
	 */
	public void setPositionInModel(R4EPosition aModelPosition) throws ResourceHandlingException, OutOfSyncException {
		fPosition = (R4ETextPosition)aModelPosition;
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fPosition, 
				R4EUIModelController.getReviewer());
		fPosition.setStartLine(fStartLine);
		fPosition.setEndLine(fEndLine);
		fPosition.setStartPosition(fOffset);
		fPosition.setLength(fLength);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		
		final R4EContent content = (R4EContent) fPosition.eContainer();
		bookNum = R4EUIModelController.FResourceUpdater.checkOut(content, 
				R4EUIModelController.getReviewer());
		content.setInfo(fName);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}
	
	/**
	 * Method setPositionValues.
	 * @param aOffset int
	 * @param aLength int
	 * @param aSourceFile IFile
	 * @throws CoreException
	 */
	private void setPositionValues(int aOffset, int aLength, IFile aSourceFile) throws CoreException {
		try {

			final IDocumentProvider provider = new TextFileDocumentProvider();
			provider.connect(aSourceFile); 
			final IDocument document = provider.getDocument(aSourceFile);
			if (null != document) { 
				fOffset = aOffset;
				if (aLength != R4EUIConstants.INVALID_VALUE) {
					fLength = aLength;
				} else {
					fLength = document.getLength();
				}

				fStartLine = document.getLineOfOffset(fOffset)  + R4EUIConstants.LINE_OFFSET;

				int endOffset = fOffset + fLength;
				if (0 != fLength) endOffset--;
				fEndLine = document.getLineOfOffset(endOffset)  + R4EUIConstants.LINE_OFFSET;
				return;
			} 
		} catch (BadLocationException e) {
			Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ").  " +
					"Setting text position to default values");
			Activator.getDefault().logWarning("Exception: " + e.toString(), e);
			fEndLine = fStartLine;
			fLength = 0;
		}
	}
	
	/**
	 * Method setStartLine.
	 * @param aStartLine int
	 */
	public void setStartLine(int aStartLine) {
		fStartLine = aStartLine;
	}
	
	/**
	 * Method getStartLine.
	 * @return int
	 */
	public int getStartLine() {
		return fStartLine;
	}
	
	/**
	 * Method setEndLine.
	 * @param aEndLine int
	 */
	public void setEndLine(int aEndLine) {
		fEndLine = aEndLine;
	}
	
	/**
	 * Method getEndLine.
	 * @return int
	 */
	public int getEndLine() {
		return fEndLine;
	}
	
	/**
	 * Method getOffset.
	 * @return int
	 */
	public int getOffset() {
		return fOffset;
	}
	
	/**
	 * Method getLength.
	 * @return int
	 */
	public int getLength() {
		return fLength;
	}

	/**
	 * Method setName.
	 * @param aName String
	 */
	public void setName(String aName) {
		fName = aName;
	}
	
	/**
	 * Method getName.
	 * @return String
	 */
	public String getName() {
		return fName;
	}
	
	/**
	 * Method isSameAs.
	 * @param aPosition IR4EPosition
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition#isSameAs(IR4EUIPosition)
	 */
	public boolean isSameAs(IR4EUIPosition aPosition) {
		if (fOffset == ((R4EUITextPosition)aPosition).getOffset() && 
				fLength == ((R4EUITextPosition)aPosition).getLength()) return true;
		return false;
	}
	
	/**
	 * Method toString.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer(R4EUIConstants.DEFAULT_LINE_TAG_LENGTH);
		if (fStartLine == fEndLine) {
			buffer.append(R4EUIConstants.LINE_TAG + fStartLine);
		} else {
			buffer.append(R4EUIConstants.LINES_TAG + fStartLine + "-" + fEndLine);
		}
		if (null != fName) buffer.append(" (" + fName + ")");
		return buffer.toString();
	}
}
