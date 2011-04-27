// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class is used to access various eclipse editors from R4E model elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelectionContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class EditorProxy {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field R4E_COMPARE_EDITOR_TITLE.
	 * (value is ""R4E Compare"")
	 */
	private static final String R4E_COMPARE_EDITOR_TITLE = "R4E Compare";  //$NON-NLS-1$ // $codepro.audit.disable constantNamingConvention

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method openEditor.
	 * 		Open the editor
	 * @param aPage IWorkbenchPage - the current workbench page
	 * @param aSelection ISelection - the currently selected model element
	 * @param forceSingleEditor boolean - flag to force single editor
	 * @throws CoreException 
	 */
	public static void openEditor(IWorkbenchPage aPage, ISelection aSelection, boolean forceSingleEditor) {

		if (aSelection.isEmpty() || !(aSelection instanceof IStructuredSelection)) return;

		IR4EUIModelElement element = null;
		boolean targetFileEditable = false;
		IR4EUIPosition position = null;
		R4EUISelectionContainer container = null;
		int selectionIndex = -1;
		
		R4EUIFileContext context = null;
		R4EFileVersion baseFileVersion = null;
		R4EFileVersion targetFileVersion = null;
		
		for (final Iterator<?> iterator = ((IStructuredSelection)aSelection).iterator(); iterator.hasNext();) {
			element = (IR4EUIModelElement) iterator.next();

			//Depending on which element was selected in the tree, we make the target file editable
			//The file is editable if it was opened from the anomaly or comment level, otherwise it is not
			//Also check to get the position we should put the cursor on and the highlight range in the editor
			if (element instanceof R4EUIAnomalyContainer) {
				targetFileEditable = true;
			} else if (element instanceof R4EUIAnomalyBasic) {
				targetFileEditable = true;
				position = ((R4EUIAnomalyBasic)element).getPosition();
			} else if (element instanceof R4EUIComment) {
				targetFileEditable = true;
				position = ((R4EUIAnomalyBasic)element.getParent()).getPosition();
			} else if (element instanceof R4EUISelection) {
				position = ((R4EUISelection)element).getPosition();
				container = (R4EUISelectionContainer) ((R4EUISelection)element).getParent();
				selectionIndex = container.getSelectionList().indexOf(element);
			}

			//Find the parent FileContextElement
			while (!(element instanceof R4EUIFileContext)) {
				element = element.getParent();
				if (null == element) return;
			}
			context = ((R4EUIFileContext) element);
			
			//Get files from FileContext
			baseFileVersion = context.getBaseFileVersion();
			targetFileVersion = context.getTargetFileVersion();

			//Check if the base file is set, if so, we will use the compare editor.  Otherwise we use the normal editor of the appropriate type
			if (context.isFileVersionsComparable() && !forceSingleEditor) {
				openCompareEditor(aPage, baseFileVersion, targetFileVersion,
						targetFileEditable, selectionIndex);
			} else {
				if (null != targetFileVersion) {
					openSingleEditor(aPage, targetFileVersion, position);
				} else {
					//File was removed, open the base then
					openSingleEditor(aPage, baseFileVersion, position);
				}
			}
		}
	}
	
	/**
	 * Method openSingleEditor.
	 *  	Open the single-mode default editor for the file type
	 * @param aPage IWorkbenchPage - the current workbench page
	 * @param aFileVersion R4EFileVersion - the file version
	 * @param aPosition IR4EUIPosition - the position to go to in the file
	 */
	private static void openSingleEditor(IWorkbenchPage aPage, R4EFileVersion aFileVersion, IR4EUIPosition aPosition) {

		try {
			final FileRevisionEditorInput fileRevEditorInput = FileRevisionEditorInput.createEditorInputFor(aFileVersion, null);
			final String id = getEditorId(fileRevEditorInput);
			final IEditorPart editor = aPage.openEditor(fileRevEditorInput, id, OpenStrategy.activateOnOpen());

			//Set highlighted selection and reset cursor if possible
			if (editor instanceof ITextEditor && aPosition instanceof R4EUITextPosition) {
				((ITextEditor) editor).setHighlightRange(((R4EUITextPosition)aPosition).getOffset(), 
						((R4EUITextPosition)aPosition).getLength(), true);
				final TextSelection selectedText = new TextSelection(((R4EUITextPosition)aPosition).getOffset(), 
						((R4EUITextPosition)aPosition).getLength());
				((ITextEditor) editor).getSelectionProvider().setSelection(selectedText);
			}
		} catch (CoreException e) {
			UIUtils.displayCoreErrorDialog(e);
		}
	}
	
	/**
	 * Method openCompareEditor.
	 *  	Open the compare-mode default editor for the file types
	 * @param aPage IWorkbenchPage - the current workbench page
	 * @param aBaseFileVersion R4EFileVersion - the base (or reference) file version
	 * @param aTargetFileVersion R4EFileVersion
	 * @param aTargetFileEditable boolean - flag set whether the target file is editable or not
	 * @param aSelectionIndex int - the index of the selection to go to in the target file
	 */
	private static void openCompareEditor(IWorkbenchPage aPage, R4EFileVersion aBaseFileVersion,
			R4EFileVersion aTargetFileVersion, boolean aTargetFileEditable, int aSelectionIndex) {
		
		//Reuse editor if it is already open on the same input
		CompareEditorInput input = null;
		
		IEditorPart editor = findReusableCompareEditor(aPage, aBaseFileVersion, aTargetFileVersion);

		if (null != editor) {
			//Simply provide focus to editor
			aPage.activate(editor);
			input = (R4ECompareEditorInput) editor.getEditorInput();
		} else {
			final CompareConfiguration config = new CompareConfiguration();
			config.setLeftEditable(aTargetFileEditable);
			config.setRightEditable(false);
			config.setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.valueOf(true));

			final ITypedElement ancestor = null;   //Might be improved later
			ITypedElement target = null;
			if (null != aTargetFileVersion) target = new FileRevisionTypedElement(aTargetFileVersion);
			ITypedElement base = null;
			if (null != aBaseFileVersion) base =  new FileRevisionTypedElement(aBaseFileVersion);

		    input = new R4ECompareEditorInput(config, ancestor, target, aTargetFileVersion, base, aBaseFileVersion);
			input.setTitle(R4E_COMPARE_EDITOR_TITLE);   // Adjust the compare title

			Activator.Ftracer.traceInfo("Open compare editor on files " + (null != target ? target.getName(): "") + " (Target) and "
					+ (null != base ? base.getName(): "") + " (Base)");
			CompareUI.openCompareEditor(input, true);
		}
		
		if (aSelectionIndex != -1) {
			//Set the correct difference, based on the selection index, in the compare editor
			final ICompareNavigator navigator = input.getNavigator();
		
			while (!(navigator.selectChange(false))) { // $codepro.audit.disable emptyWhileStatement, methodInvocationInLoopCondition
				//Reset position to the first difference
			}
			
			for (int i = 0; i < aSelectionIndex; i++) {
				navigator.selectChange(true);   //get the difference that corresponds to the right selection
			}
		}
	}
	
	/** 
	 * Method findReusableCompareEditor.
	 * 		Find the appropriate compare editor based on the file types
	 * @param aPage IWorkbenchPage - the current workbench page
	 * @param aBaseFileName String - the base (or reference) file name
	 * @param aTargetFileName String - the target file name
	 * @return IEditorPart - the editor to use
	 */
	public static IEditorPart findReusableCompareEditor(IWorkbenchPage aPage, R4EFileVersion aBaseFile,
			R4EFileVersion aTargetFile) {
		
		final IEditorReference[] editorRefs = aPage.getEditorReferences();
		IEditorPart part = null;
		R4ECompareEditorInput input = null;
		ITypedElement left = null;
		ITypedElement right = null;
		// first loop looking for an editor with the same input
		for (int i = 0; i < editorRefs.length; i++) {
			part = editorRefs[i].getEditor(false);
			if (null != part && part instanceof IReusableEditor) {
					// check if the editor input type complies with the types given by the caller
					if (R4ECompareEditorInput.class.isInstance(part.getEditorInput())) {
						//Now check if the input files are the same as with the found editor
						input = (R4ECompareEditorInput)part.getEditorInput();
					left = input.getLeftElement();
					right = input.getRightElement();
					
					//Case:  No input in editor, that should never happen but guard here just in case
					if (left == null && right == null) {
						return null;
					}

					//Case:  No target file and base is the same
					if (left == null && aTargetFile == null && right != null && aBaseFile != null && right.getName().equals(aBaseFile.getName())) {
						return part;
					}

					//Case:  No base file and target is the same
					if (right == null && aBaseFile == null && left != null && aTargetFile != null && left.getName().equals(aTargetFile.getName())) {
						return part;
					}
					
					//Case: Base and target are the same
					if (left != null && right != null && aBaseFile != null && aTargetFile != null) {
						if (left.getName().equals(aTargetFile.getName()) && right.getName().equals(aBaseFile.getName())) {
							return part;
						}
					}
				}
			}
		}
		// no re-usable editor found
		return null;
	}
	
	
	/**
	 * Method getEditorId.
	 * @param aEditorInput FileRevisionEditorInput
	 * @return String
	 */
	private static String getEditorId(FileRevisionEditorInput aEditorInput) {
		final String id = getEditorId(aEditorInput.getFileVersion().getFileRevision().getName(),
				getContentType(aEditorInput));
		return id;
	}

	/**
	 * Method getEditorId.
	 * @param aFileName String
	 * @param aType IContentType
	 * @return String
	 */
	private static String getEditorId(String aFileName, IContentType aType) {
		final IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		final IEditorDescriptor descriptor = registry.getDefaultEditor(aFileName, aType);
		String id = null;
		if (null == descriptor || descriptor.isOpenExternal()) {
			id = "org.eclipse.ui.DefaultTextEditor";
		} else {
			id = descriptor.getId();
		}
		return id;
	}

	/**
	 * Method getContentType.
	 * @param aEditorInput FileRevisionEditorInput
	 * @return IContentType
	 */
	private static IContentType getContentType(FileRevisionEditorInput aEditorInput) {
		IContentType type = null;
		try {
			final InputStream contents = aEditorInput.getStorage().getContents();
			try {
				type = getContentType(aEditorInput.getFileVersion().getFileRevision().getName(), contents);
			} finally {
				try {
					contents.close();
				} catch (IOException e) {
					Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					Activator.getDefault().logWarning("Exception: " + e.toString(), e);
				}
			}
		} catch (CoreException e) {
			UIUtils.displayCoreErrorDialog(e);
		}
		return type;
	}

	/**
	 * Method getContentType.
	 * @param aFileName String
	 * @param aContents InputStream
	 * @return IContentType
	 */
	private static IContentType getContentType(String aFileName, InputStream aContents) {
		IContentType type = null;
		if (null != aContents) {
			try {
				type = Platform.getContentTypeManager().findContentTypeFor(aContents, aFileName);
			} catch (IOException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			}
		}
		if (null == type) {
			type = Platform.getContentTypeManager().findContentTypeFor(aFileName);
		}
		return type;
	}
}
