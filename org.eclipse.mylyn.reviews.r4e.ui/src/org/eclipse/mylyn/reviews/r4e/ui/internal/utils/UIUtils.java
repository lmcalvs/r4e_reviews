// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class provides general utility methods used in the UI implementation
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.CompareContentViewerSwitchingPane;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IParticipantInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IParticipantUnassignDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIDelta;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIDeltaContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class UIUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	//NOTE:  THese values are used in the hack to change cursor position in compare editor.

	/**
	 * Field COMPARE_EDITOR_TEXT_CLASS_NAME. (value is ""org.eclipse.compare.contentmergeviewer.TextMergeViewer"")
	 */
	private static final String COMPARE_EDITOR_TEXT_CLASS_NAME = "org.eclipse.compare.contentmergeviewer.TextMergeViewer";

	/**
	 * Field COMPARE_EDITOR_TEXT_FIELD_LEFT. (value is ""fLeft"")
	 */
	private static final String COMPARE_EDITOR_TEXT_FIELD_LEFT = "fLeft";

	/**
	 * Field DEFAULT_OBJECT_CLASS_NAME. (value is ""Object"")
	 */
	private static final String DEFAULT_OBJECT_CLASS_NAME = "Object";

	/**
	 * Field TEST_MODE.
	 */
	//This could be replaced by org.eclipse.mylyn.commons.core.CoreUtil#TEST_MODE,
	//however it's worth repeating as it's a small piece of code
	public static final boolean TEST_MODE;
	static {
		String application = System.getProperty("eclipse.application", ""); //$NON-NLS-1$ //$NON-NLS-2$
		if (application.length() > 0) {
			TEST_MODE = application.endsWith("testapplication") || application.endsWith("uitest"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			String commands = System.getProperty("eclipse.commands", ""); //$NON-NLS-1$ //$NON-NLS-2$
			TEST_MODE = commands.contains("testapplication\n"); //$NON-NLS-1$
		}
		//Do not show Error Dialogs when doing automated testing
		if (true == TEST_MODE) {
			ErrorDialog.AUTOMATED_MODE = true;
		} else {
			ErrorDialog.AUTOMATED_MODE = false;
		}
	}

	/**
	 * Field COMPATIBILITY_ERROR_MESSAGE. (value is ""Cannot use older version of R4E to open newer element version"")
	 */
	private static final String COMPATIBILITY_ERROR_MESSAGE = "Cannot use older version of R4E to open newer element version";

	/**
	 * Field COMPATIBILITY_WARNING_DIALOG_TITLE. (value is ""Version Mismatch Detected"")
	 */
	private static final String COMPATIBILITY_WARNING_DIALOG_TITLE = "Version Mismatch Detected";

	/**
	 * Field COMPATIBILITY_WARNING_MESSAGE. (value is ""You are trying to open an older version of the element than the
	 * one ucrrently handled by this version of R4E.\n You can open the element normally, which will upgrade its version
	 * to the current one, or in Read-only mode, which will preserve its version.\n"")
	 */
	private static final String COMPATIBILITY_WARNING_MESSAGE = "You are trying to open an older version of the element than the one currently handled by this version of R4E.\n"
			+ "You can open the element normally, which will upgrade its version to the current one, or in Read-only mode, which will preserve its version.";

	/**
	 * Field COMPATIBILITY_WARNING_DIALOG_BUTTONS.
	 */
	private static final String[] COMPATIBILITY_WARNING_DIALOG_BUTTONS = { "Open Normally", "Open in Read-Only Mode",
			"Cancel" };

	/**
	 * Field DISABLED_FONT_COLOR.
	 */
	public static final Color DISABLED_FONT_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);

	/**
	 * Field ENABLED_FONT_COLOR.
	 */
	public static final Color ENABLED_FONT_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Load the current image and add it to the image registry
	 * 
	 * @param url
	 *            - the localtion of the image to load
	 * @return Image
	 */
	public static Image loadIcon(String url) {
		final R4EUIPlugin plugin = R4EUIPlugin.getDefault();
		Image icon = plugin.getImageRegistry().get(url);
		if (null == icon) {
			final URL imageURL = plugin.getBundle().getEntry(url);
			final ImageDescriptor descriptor = ImageDescriptor.createFromURL(imageURL);
			icon = descriptor.createImage();
			plugin.getImageRegistry().put(url, icon);
		}
		return icon;
	}

	/**
	 * Method displayResourceErrorDialog.
	 * 
	 * @param e
	 *            ResourceHandlingException
	 */
	public static void displayResourceErrorDialog(ResourceHandlingException e) {
		R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Resource Error Detected",
				new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayCompatibilityErrorDialog.
	 * 
	 * @param e
	 *            CompatibilityException
	 */
	public static void displayCompatibilityErrorDialog(CompatibilityException e) {
		R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
				"Compatibility problem Detected",
				new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayCompatibilityErrorDialog.
	 */
	public static void displayCompatibilityErrorDialog() {
		R4EUIPlugin.Ftracer.traceError(COMPATIBILITY_ERROR_MESSAGE);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
				"Compatibility problem Detected", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
						COMPATIBILITY_ERROR_MESSAGE, null), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayCompatibilityWarningDialog.
	 * 
	 * @return boolean
	 */
	public static int displayCompatibilityWarningDialog(String aDataVersion, String aApplVersionl) {
		R4EUIPlugin.Ftracer.traceWarning(COMPATIBILITY_WARNING_MESSAGE);
		final MessageDialog dialog = new MessageDialog(null, COMPATIBILITY_WARNING_DIALOG_TITLE, null,
				COMPATIBILITY_WARNING_MESSAGE + R4EUIConstants.LINE_FEED + "Element meta-data Version: " + aDataVersion
						+ R4EUIConstants.LINE_FEED + "Application meta-data Version: " + aApplVersionl,
				MessageDialog.QUESTION_WITH_CANCEL, COMPATIBILITY_WARNING_DIALOG_BUTTONS, 0);
		return dialog.open();
	}

	/**
	 * Method displaySyncErrorDialog.
	 * 
	 * @param e
	 *            OutOfSyncException
	 */
	public static void displaySyncErrorDialog(OutOfSyncException e) {
		R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		final ErrorDialog dialog = new ErrorDialog(
				null,
				R4EUIConstants.DIALOG_TITLE_ERROR,
				"Synchronization Error Detected" + "Please refresh the review navigator view and try the command again",
				new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
		dialog.open();
		// TODO later we will want to do this automatically
	}

	/**
	 * Method displayVersionErrorDialog.
	 * 
	 * @param e
	 *            ReviewVersionsException
	 */
	public static void displayVersionErrorDialog(ReviewVersionsException e) {
		R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Version Error Detected",
				new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayVersionErrorDialog.
	 * 
	 * @param e
	 *            ReviewVersionsException
	 */
	public static void displayReviewsFileStorageErrorDialog(ReviewsFileStorageException e) {
		R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
				"Local Review Storage Error Detected", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
						e.getMessage(), e), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayCoreErrorDialog.
	 * 
	 * @param e
	 *            CoreException
	 */
	public static void displayCoreErrorDialog(CoreException e) {
		R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
				"Eclipse Runtime Core Error Detected", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
						e.getMessage(), e), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayFailedLoadDialog.
	 * 
	 * @param aLoadErrors
	 *            List<String>
	 */
	public static void displayFailedLoadDialog(List<String> aLoadErrors) {
		final StringBuilder errorMsgs = new StringBuilder();
		errorMsgs.append("The following errors were reported:" + R4EUIConstants.LINE_FEED);
		for (String msg : aLoadErrors) {
			errorMsgs.append(msg);
		}
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
				"Some elements failed to load", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
						errorMsgs.toString(), null), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method isFilterPreferenceSet.
	 * 
	 * @param aFilterSet
	 *            Object
	 * @return boolean
	 */
	public static boolean isFilterPreferenceSet(Object aFilterSet) {
		if (null != aFilterSet && aFilterSet.toString().equals(R4EUIConstants.VALUE_TRUE_STR)) {
			return true;
		}
		return false;
	}

	/**
	 * Method parseStringList.
	 * 
	 * @param aStringList
	 *            String
	 * @return List<String>
	 */
	public static List<String> parseStringList(String aStringList) {
		final List<String> stringArray = new ArrayList<String>();
		if (null != aStringList) {
			final StringTokenizer st = new StringTokenizer(aStringList, File.pathSeparator + R4EUIConstants.LINE_FEED);
			while (st.hasMoreElements()) {
				stringArray.add((String) st.nextElement());
			}
		}
		return stringArray;
	}

	/**
	 * Method addTabbedPropertiesTextResizeListener. Resizes a Text widget in a ScrolledComposite to fit the text being
	 * typed. It also adds scrollbars to the composite as needed
	 * 
	 * @param aText
	 *            Text - The Text widget
	 */
	//TODO this only works for flatFormComposites and not vanilla ones.  For now this is not a big deal, but we will want to review it later
	//A new auto-resizable text widget class should be created for this eventually
	public static void addTabbedPropertiesTextResizeListener(final Text aText) {
		aText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				//compute new Text field size
				final Point newSize = aText.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				final Point oldSize = aText.getSize();
				final int heightDiff = newSize.y - oldSize.y;
				if (0 != heightDiff && 0 != oldSize.y) {
					aText.setSize(newSize);
					aText.getParent().layout();

					//Set scrollable height so that scrollbar appear if needed
					Composite scrolledParent = aText.getParent();
					while (!(scrolledParent instanceof ScrolledComposite)) {
						scrolledParent = scrolledParent.getParent();
						if (null == scrolledParent) {
							return;
						}
					}
					((ScrolledComposite) scrolledParent).setMinSize(aText.getParent().computeSize(SWT.DEFAULT,
							SWT.DEFAULT));

					//If the text falls outside of the display scroll down to reposition
					if ((aText.getLocation().y + aText.getCaretLocation().y + aText.getLineHeight()) > (scrolledParent.getClientArea().y + scrolledParent.getClientArea().height)) {

						final Point origin = ((ScrolledComposite) scrolledParent).getOrigin();
						origin.y += heightDiff;

						((ScrolledComposite) scrolledParent).setOrigin(origin);
					}
				}
			}
		});
	}

	public static void addTabbedPropertiesTextResizeListener(final StyledText aText) {
		aText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				//compute new Text field size
				final Point newSize = aText.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				final Point oldSize = aText.getSize();
				final int heightDiff = newSize.y - oldSize.y;
				if (0 != heightDiff && 0 != oldSize.y) {
					aText.setSize(newSize);
					aText.getParent().layout();

					//Set scrollable height so that scrollbar appear if needed
					Composite scrolledParent = aText.getParent();
					while (!(scrolledParent instanceof ScrolledComposite)) {
						scrolledParent = scrolledParent.getParent();
						if (null == scrolledParent) {
							return;
						}
					}
					((ScrolledComposite) scrolledParent).setMinSize(aText.getParent().computeSize(SWT.DEFAULT,
							SWT.DEFAULT));

					//If the text falls outside of the display scroll down to reposition
					if ((aText.getLocation().y + aText.getCaretOffset() + aText.getLineHeight()) > (scrolledParent.getClientArea().y + scrolledParent.getClientArea().height)) {

						final Point origin = ((ScrolledComposite) scrolledParent).getOrigin();
						origin.y += heightDiff;

						((ScrolledComposite) scrolledParent).setOrigin(origin);
					}
				}
			}
		});
	}

	/**
	 * Method mapParticipantToIndex.
	 * 
	 * @param aParticipant
	 *            String
	 * @return int
	 */
	public static int mapParticipantToIndex(String aParticipant) {
		if (null == R4EUIModelController.getActiveReview()) {
			return 0;
		}
		final List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		final int numParticipants = participants.size();
		for (int i = 0; i < numParticipants; i++) {
			if (participants.get(i).getId().equals(aParticipant)) {
				return i;
			}
		}
		return R4EUIConstants.INVALID_VALUE; //should never happen
	}

	/**
	 * Method getClassFromString.
	 * 
	 * @param aClass
	 *            String
	 * @return R4ECommentClass
	 */
	public static R4EDesignRuleClass getClassFromString(String aClass) {
		if (aClass.equals(R4EUIConstants.ANOMALY_CLASS_ERRONEOUS)) {
			return R4EDesignRuleClass.R4E_CLASS_ERRONEOUS;
		} else if (aClass.equals(R4EUIConstants.ANOMALY_CLASS_SUPERFLUOUS)) {
			return R4EDesignRuleClass.R4E_CLASS_SUPERFLUOUS;
		} else if (aClass.equals(R4EUIConstants.ANOMALY_CLASS_IMPROVEMENT)) {
			return R4EDesignRuleClass.R4E_CLASS_IMPROVEMENT;
		} else if (aClass.equals(R4EUIConstants.ANOMALY_CLASS_QUESTION)) {
			return R4EDesignRuleClass.R4E_CLASS_QUESTION;
		} else {
			return null; //should never happen
		}
	}

	/**
	 * Method getRankFromString.
	 * 
	 * @param aRank
	 *            String
	 * @return R4EAnomalyRank
	 */
	public static R4EDesignRuleRank getRankFromString(String aRank) {
		if (aRank.equals(R4EUIConstants.ANOMALY_RANK_NONE)) {
			return R4EDesignRuleRank.R4E_RANK_NONE;
		} else if (aRank.equals(R4EUIConstants.ANOMALY_RANK_MINOR)) {
			return R4EDesignRuleRank.R4E_RANK_MINOR;
		} else if (aRank.equals(R4EUIConstants.ANOMALY_RANK_MAJOR)) {
			return R4EDesignRuleRank.R4E_RANK_MAJOR;
		} else {
			return null; //should never happen
		}
	}

	/**
	 * Method getClasses.
	 * 
	 * @return String[]
	 */
	public static String[] getClasses() {
		return R4EUIConstants.CLASS_VALUES;
	}

	/**
	 * Method getRanks.
	 * 
	 * @return String[]
	 */
	public static String[] getRanks() {
		return R4EUIConstants.RANK_VALUES;
	}

	/**
	 * Method buildUserDetailsString.
	 * 
	 * @param aUserInfo
	 *            IUserInfo
	 * @return String
	 */
	public static String buildUserDetailsString(IUserInfo aUserInfo) {
		final StringBuffer tempStr = new StringBuffer(100);
		final int numAttributeTypes = aUserInfo.getAttributeTypes().length;
		for (int i = 0; i < numAttributeTypes; i++) {
			tempStr.append(aUserInfo.getAttributeTypes()[i] + " = " + aUserInfo.getAttributeValues()[i]
					+ R4EUIConstants.LINE_FEED);
		}
		return tempStr.toString();
	}

	public static void selectElementInEditor(IEditorPart editor) {

		final IR4EUIModelElement element = getR4EUIElement();
		if (element instanceof R4EUIContent) {
			final IR4EUIPosition position = ((R4EUIContent) element).getPosition();
			if (position instanceof R4EUIModelPosition) {
				selectElementInCompareModelEditor(editor, (R4EUIModelPosition) position);
			} else {
				final IEditorInput aInput = editor.getEditorInput();
				selectElementInEditor((R4ECompareEditorInput) aInput);
			}
		}
	}

	private static void selectElementInCompareModelEditor(IEditorPart editor, R4EUIModelPosition pos) {
		if (isEMFCompareActive()) {
			UIEMFCompareUtils.selectElementInCompareModelEditor(editor, pos);
		} else {
			R4EUIPlugin.Ftracer.traceDebug("EMF Compare is not available or does not meet minimum requirements"); //$NON-NLS-1$
		}
	}

	private static IR4EUIModelElement getR4EUIElement() {
		final ISelection selection = R4EUIModelController.getNavigatorView().getTreeViewer().getSelection();
		final IR4EUIModelElement element = (IR4EUIModelElement) ((IStructuredSelection) selection).getFirstElement();
		return element;
	}

	/**
	 * Method selectElementInEditor.
	 * 
	 * @param aInput
	 *            R4ECompareEditorInput
	 */
	public static void selectElementInEditor(R4ECompareEditorInput aInput) {
		final IR4EUIModelElement element = getR4EUIElement();
		IR4EUIPosition position = null;
		int selectionIndex = -1;

		if (element instanceof R4EUIAnomalyBasic) {
			position = ((R4EUIAnomalyBasic) element).getPosition();
		} else if (element instanceof R4EUIComment) {
			position = ((R4EUIAnomalyBasic) element.getParent()).getPosition();
		} else if (element instanceof R4EUISelection) {
			position = ((R4EUIContent) element).getPosition();
		} else if (element instanceof R4EUIDelta) {
			final R4EUIDeltaContainer container = (R4EUIDeltaContainer) ((R4EUIDelta) element).getParent();
			selectionIndex = container.getContentsList().indexOf(element);
		} else {
			return; //Do nothing if any other element is selected
		}
		final ICompareNavigator navigator = aInput.getNavigator();

		//Decide which way we select the current position
		if (selectionIndex != -1) {
			//Use the change index to select position in file
			while (!(navigator.selectChange(false))) {
				//Reset position to the first difference
			}

			for (int i = 0; i < selectionIndex; i++) {
				navigator.selectChange(true); //get the difference that corresponds to the right selection
			}
		} else {
			//Use free form to select position in file
			//NOTE:  This is a dirty hack that involves accessing class and field we shouldn't, but that's
			//       the only way to select the current position in the compare editor.  Hopefully this code can
			//		 be removed later when the Eclipse compare editor allows this.
			if (navigator instanceof CompareEditorInputNavigator) {
				final Object[] panes = ((CompareEditorInputNavigator) navigator).getPanes();
				for (Object pane : panes) {
					if (pane instanceof CompareContentViewerSwitchingPane) {
						Viewer viewer = ((CompareContentViewerSwitchingPane) pane).getViewer();
						if (viewer instanceof TextMergeViewer) {
							TextMergeViewer textViewer = (TextMergeViewer) viewer;
							Class textViewerClass = textViewer.getClass();
							if (!textViewerClass.getName().equals(COMPARE_EDITOR_TEXT_CLASS_NAME)) {
								do {
									textViewerClass = textViewerClass.getSuperclass();
									if (textViewerClass.getName().equals(DEFAULT_OBJECT_CLASS_NAME)) {
										break;
									}
								} while (!textViewerClass.getName().equals(COMPARE_EDITOR_TEXT_CLASS_NAME));
							}
							try {
								Field field = textViewerClass.getDeclaredField(COMPARE_EDITOR_TEXT_FIELD_LEFT);
								field.setAccessible(true);
								MergeSourceViewer sourceViewer = (MergeSourceViewer) field.get(textViewer);
								ITextEditor adapter = (ITextEditor) sourceViewer.getAdapter(ITextEditor.class);
								adapter.selectAndReveal(((R4EUITextPosition) position).getOffset(),
										((R4EUITextPosition) position).getLength());
							} catch (SecurityException e) {
								//just continue
							} catch (NoSuchFieldException e) {
								//just continue
							} catch (IllegalArgumentException e) {
								//just continue
							} catch (IllegalAccessException e) {
								//just continue
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Method openUrl Opens a default web browser
	 * 
	 * @param aLocation
	 *            - String
	 */
	public static void openUrl(String aLocation) {
		URL url = null;
		if (null != aLocation) {
			try {
				url = new URL(aLocation);
			} catch (MalformedURLException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Invalid location",
						new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			}
		}
		final String generatedId = "org.eclipse.mylyn.web.browser-" + Calendar.getInstance().getTimeInMillis(); //$NON-NLS-1$
		final IWebBrowser browser;
		try {
			browser = PlatformUI.getWorkbench().getBrowserSupport().createBrowser(generatedId);
			browser.openURL(url);
		} catch (PartInitException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
					"Error opening Browser", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e),
					IStatus.ERROR);
			dialog.open();
		}
	}

	/**
	 * Method changeReviewState Verifies and Changes Review Element phase to a new one
	 * 
	 * @param aReview
	 *            - R4EUIModelElement
	 * @param aNewPhase
	 *            - R4EReviewPhase
	 */
	public static void changeReviewPhase(IR4EUIModelElement aReview, R4EReviewPhase aNewPhase) {
		final AtomicReference<String> aResultMsg = new AtomicReference<String>(null);
		if (((R4EUIReviewBasic) aReview).validatePhaseChange(aNewPhase, aResultMsg)) {
			if (null != aResultMsg.get()) {
				final ErrorDialog dialog = new ErrorDialog(null, "Warning", aResultMsg.get(), new Status(
						IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, 0, null, null), IStatus.WARNING);
				dialog.open();
			}
			try {
				if (aReview instanceof R4EUIReviewExtended) {
					final R4EFormalReview review = ((R4EFormalReview) ((R4EUIReviewExtended) aReview).getReview());
					if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)
							&& null == review.getActiveMeeting()) {
						MailServicesProxy.sendMeetingRequest();
					}
					//Prevent changing state to PREPARATION if no Meeting Request was sent out
					if (null != review.getActiveMeeting()) {
						((R4EUIReviewExtended) aReview).updatePhase(aNewPhase);
					} else {
						final ErrorDialog dialog = new ErrorDialog(null, "Error", "No Meeting Data present",
								new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
										"Please allow sending out a Meeting Request", null), IStatus.ERROR);
						dialog.open();
					}
				} else {
					((R4EUIReviewBasic) aReview).updatePhase(aNewPhase);
				}
			} catch (ResourceHandlingException e1) {
				UIUtils.displayResourceErrorDialog(e1);
			} catch (OutOfSyncException e1) {
				UIUtils.displaySyncErrorDialog(e1);
			} finally {
				R4EUIModelController.setJobInProgress(false);
			}
		} else {
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.REVIEW_NOT_COMPLETED_ERROR,
					"Review phase cannot be changed", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
							aResultMsg.get(), null), IStatus.ERROR);
			dialog.open();
		}
	}

	/**
	 * Method changeAnomalyState Verifies and Changes Anomaly Element state to a new one
	 * 
	 * @param aAnomaly
	 *            - IR4EUIModelElement
	 * @param aNewState
	 *            - R4EAnomalyState
	 */
	public static void changeAnomalyState(IR4EUIModelElement aAnomaly, R4EAnomalyState aNewState) {
		try {
			((R4EUIAnomalyExtended) aAnomaly).updateState(aNewState);

			//If this is a postponed anomaly, update original one as well
			if (aAnomaly instanceof R4EUIPostponedAnomaly) {
				((R4EUIPostponedAnomaly) aAnomaly).updateOriginalAnomaly();
			}
		} catch (ResourceHandlingException e1) {
			UIUtils.displayResourceErrorDialog(e1);
		} catch (OutOfSyncException e1) {
			UIUtils.displaySyncErrorDialog(e1);
		} catch (CompatibilityException e) {
			UIUtils.displayCompatibilityErrorDialog(e);
		}
	}

	/**
	 * Method setNavigatorViewFocus. Set focus to current view/element
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 * @param aExpandLevel
	 *            int
	 */
	public static void setNavigatorViewFocus(IR4EUIModelElement aElement, int aExpandLevel) {
		if (null != aElement) {
			R4EUIModelController.getNavigatorView().updateView(aElement, aExpandLevel);
		}
	}

	/**
	 * Method formatAssignedParticipants. Concatenates assigned participants for UI display
	 * 
	 * @param aParticipants
	 *            EList<String>
	 * @return String
	 */
	public static String formatAssignedParticipants(EList<String> aParticipants) {
		if (aParticipants.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			for (String participants : aParticipants) {
				buffer.append(participants + R4EUIConstants.LIST_SEPARATOR + " ");
			}
			return buffer.toString().substring(0, buffer.length() - 2);
		} else {
			return "";
		}
	}

	/**
	 * Display the dialog used by the user to enter the participant to use as filter criteria
	 * 
	 * @return String
	 */
	public static String getParticipantFilterInputDialog() {
		final InputDialog dlg = R4EUIDialogFactory.getInstance().getParticipantFilterInputDialog();
		if (dlg.open() == Window.OK) {
			return dlg.getValue();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Display the dialog used by the user to enter the participant to assign
	 * 
	 * @return String
	 */
	public static List<R4EParticipant> getAssignParticipants() {
		final IParticipantInputDialog dialog = R4EUIDialogFactory.getInstance().getParticipantInputDialog(false);
		dialog.create();
		final int result = dialog.open();
		if (result == Window.OK) {
			return dialog.getParticipants();
		}
		return new ArrayList<R4EParticipant>(0); //Cancelled, no participants to use
	}

	/**
	 * Display the dialog used by the user to enter the participant unassign
	 * 
	 * @param aElement
	 *            - IR4EUIModelElement
	 * @return String
	 */
	public static List<R4EParticipant> getUnassignParticipants(IR4EUIModelElement aElement) {
		final IParticipantUnassignDialog dialog = R4EUIDialogFactory.getInstance().getParticipantUnassignDialog(
				aElement);
		dialog.create();
		final int result = dialog.open();
		if (result == Window.OK) {
			return dialog.getParticipants();
		}
		return new ArrayList<R4EParticipant>(0); //Cancelled, no participants to use
	}

	/**
	 * Gets the project path for the given file version
	 * 
	 * @param aFileVersion
	 *            - R4EFileVersion
	 * @return String
	 */
	public static String getProjectPath(R4EFileVersion aFileVersion) {
		String path = ""; //$NON-NLS-1$
		try {
			if (null != aFileVersion.getPlatformURI()) {
				URI uri = new URI(aFileVersion.getPlatformURI());
				String uriPath = uri.getPath();
				if (null != uriPath) {
					path = uriPath.substring(uriPath.indexOf(R4EUIConstants.SEPARATOR, 1) + 1);
				}
			}
		} catch (URISyntaxException e) {
			// Ignore
		}
		return path;
	}

	/**
	 * Method formatNumChanges. formats the NumChanges value for UI display
	 * 
	 * @param aNumChanges
	 *            int
	 * @param aNumReviewedChanges
	 *            int
	 * @return String
	 */
	public static String formatNumChanges(int aNumChanges, int aNumReviewedChanges) {
		return Integer.toString(aNumReviewedChanges) + R4EUIConstants.SEPARATOR + Integer.toString(aNumChanges);
	}

	/**
	 * Check if the requirements to use model compare are met
	 * 
	 * @return
	 */
	public static boolean isEMFCompareActive() {
		boolean active = false;
		try {
			Class emfCompareCheck = Class.forName("org.eclipse.mylyn.reviews.r4e.internal.emf.compare.EMFCompareCheck.java"); //$NON-NLS-1$
			Object obj = emfCompareCheck.newInstance();
			if (obj != null) {
				active = true;
			}
		} catch (ClassNotFoundException e) {
			R4EUIPlugin.Ftracer.traceInfo("EMF Compare is not active i.e.EMFCompareCheck.java class not found"); //$NON-NLS-1$
		} catch (InstantiationException e) {
			R4EUIPlugin.Ftracer.traceInfo("EMF Compare is not active i.e.EMFCompareCheck.java Initiation Exception"); //$NON-NLS-1$
		} catch (IllegalAccessException e) {
			R4EUIPlugin.Ftracer.traceInfo("EMF Compare is not active i.e.EMFCompareCheck.java Illegal Access Exception"); //$NON-NLS-1$
		}

		return active;
	}
}
