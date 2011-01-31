// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the dialog used to fill-in the Find review items details
 * This is a modeless-like dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import java.io.FileNotFoundException;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIFFactory;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EFindReviewItemsDialog extends Dialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field TEXT_LEFT_ALIGNMENT.
	 * (value is 50)
	 */
	private static final int TEXT_LEFT_ALIGNMENT = 50;
	/**
	 * Field TEXT_RIGHT_ALIGNMENT.
	 * (value is 100)
	 */
	private static final int TEXT_RIGHT_ALIGNMENT = 100;
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
    /**
     * The title of the dialog.
     */
    private final String fTitle;

    /**
     * The item message to display
     */
    private final String fItemDetailsMessage;
    
    /**
     * The item details message to display
     */
    private final String fItemComponentsMessage;
    
    /**
     * Input text widget.
     */
    private final IProject fInputProject;

    /**
     * Field fReviewItemDescriptor.
     */
    private CommitDescriptor fReviewItemDescriptor;
    
    
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
    
	/**
	 * Constructor for R4EReviewGroupInputDialog.
	 * @param aParentShell Shell
	 * @param aDialogTitle String
	 * @param aItemDetailsMessage String
	 * @param aItemComponentsMessage String
	 * @param aInputProject IProject
	 */
	public R4EFindReviewItemsDialog(Shell aParentShell, String aDialogTitle, String aItemDetailsMessage, 
			String aItemComponentsMessage, IProject aInputProject) {
		super(aParentShell);
    	setBlockOnOpen(true);
		fTitle = aDialogTitle;
		fItemDetailsMessage = aItemDetailsMessage;
		fItemComponentsMessage = aItemComponentsMessage;
		fInputProject = aInputProject;
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
    /**
     * Method buttonPressed.
     * @param buttonId int
     * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
    @Override
	protected void buttonPressed(int buttonId) {

        if (buttonId == IDialogConstants.OK_ID) {
			try {
		    	this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
		    	this.getButton(IDialogConstants.OK_ID).setEnabled(false);
		    	this.getButton(IDialogConstants.CANCEL_ID).setEnabled(false);
		    	
				//Get the reviewer (i.e. ourselves :-) or create it if it does not exist
				final R4EUIReview uiReview = R4EUIModelController.getActiveReview();
				final String user = R4EUIModelController.getReviewer();
				final R4EParticipant participant = uiReview.getParticipant(user, true);
				
	        	//Get a temporary review item not attached to anything
				final ReviewsVersionsIF versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(fInputProject);
				final R4EItem tmpReviewItem = versionsIf.createCommitReviewItem(fInputProject, fReviewItemDescriptor.getId(), participant);
				if (null == tmpReviewItem) {
					//No files are found
					Activator.Ftracer.traceWarning("No files found for this review item");
					Activator.getDefault().logWarning("No files found for this review item", null);
					final ErrorDialog dialog = new ErrorDialog(null, "Warning", "No files found for this review item.",
							new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, null, null), IStatus.WARNING);
					dialog.open();	
				} else {
					final EList<R4EFileContext> tmpFiles = tmpReviewItem.getFileContextList();
					if (0 == tmpFiles.size())
					{
						//No files are found
						Activator.Ftracer.traceWarning("No files found for this review item");
						Activator.getDefault().logWarning("No files found for this review item", null);
						final ErrorDialog dialog = new ErrorDialog(null, "Warning", "No files found for this review item.",
								new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, null, null), IStatus.WARNING);
						dialog.open();
					} else {
						//Create a new review item in the serialization model
						final R4EItem reviewItem = R4EUIModelController.FModelExt.createR4EItem(participant);
						final R4EUIReviewItem uiReviewItem = new R4EUIReviewItem(uiReview, reviewItem, 
								R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT, fReviewItemDescriptor, null);
						Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(reviewItem, R4EUIModelController.getReviewer());
						reviewItem.getProjectURIs().add(tmpReviewItem.getProjectURIs().get(0));   //For now we only take the first project ID
						reviewItem.setDescription(tmpReviewItem.getDescription());
						reviewItem.setRepositoryRef(tmpReviewItem.getRepositoryRef());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//Copy the data from the temporary object to the ones in the serialization model/UI
						final int tmpFilesSize = tmpFiles.size();
						for (int i = 0; i < tmpFilesSize; i++) {
							final R4EFileContext fileContext = R4EUIModelController.FModelExt.createR4EFileContext(reviewItem);
							R4EFileVersion tmpBaseVersion = tmpFiles.get(i).getBase();
							if (null != tmpBaseVersion) {
								//TODO: for now comparisons using the compare editor from the UI are not supported.  The compare input comes
								//from the eGIT code in the R4E core plugin
								final R4EFileVersion baseVersion = R4EUIModelController.FModelExt.createR4EBaseFileVersion(fileContext);
								bookNum = R4EUIModelController.FResourceUpdater.checkOut(baseVersion, R4EUIModelController.getReviewer());
								baseVersion.setName(tmpBaseVersion.getName());
								baseVersion.setRepositoryPath(tmpBaseVersion.getRepositoryPath());
								String versionId = tmpBaseVersion.getVersionID();
								if (versionId.equals(R4EUIConstants.GIT_INVALID_VERSION_ID)) {
									baseVersion.setVersionID(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
								} else {
									baseVersion.setVersionID(versionId);
								}
								R4EUIModelController.FResourceUpdater.checkIn(bookNum);
							}
							final R4EFileVersion targetVersion = R4EUIModelController.FModelExt.createR4ETargetFileVersion(fileContext);

							R4EFileVersion tmpTargetVersion = tmpFiles.get(i).getTarget();
							bookNum = R4EUIModelController.FResourceUpdater.checkOut(targetVersion, R4EUIModelController.getReviewer());
							try {
								targetVersion.setResource(ResourceUtils.toIFile(tmpTargetVersion.getPlatformURI()));
							} catch (FileNotFoundException e) {
								Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
								Activator.getDefault().logWarning("Exception: " + e.toString(), e);

							}
							targetVersion.setPlatformURI(tmpTargetVersion.getPlatformURI());
							targetVersion.setName(tmpTargetVersion.getName());
							targetVersion.setRepositoryPath(tmpTargetVersion.getRepositoryPath());
							String versionId = tmpTargetVersion.getVersionID();
							if (versionId.equals(R4EUIConstants.GIT_INVALID_VERSION_ID)) {
								targetVersion.setVersionID(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
							} else {
								targetVersion.setVersionID(versionId);
							}
							R4EUIModelController.FResourceUpdater.checkIn(bookNum);

							@SuppressWarnings("unused")
							final R4EUIFileContext uiFile = new R4EUIFileContext(uiReviewItem, fileContext); // $codepro.audit.disable variableUsage
							uiReviewItem.addChildren(new R4EUIFileContext(uiReviewItem, fileContext));
						}
						R4EUIModelController.getActiveReview().addChildren(uiReviewItem);

					}
				}
			} catch (ReviewVersionsException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while adding review item ",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			} catch (ResourceHandlingException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Resource error detected while adding review item ",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			} catch (OutOfSyncException e) {
				Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while adding review item.  " +
						"Please refresh the review navigator view and try the command again",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
				// TODO later we will want to do this automatically
			} finally {
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				this.getButton(IDialogConstants.OK_ID).setEnabled(true);
		    	this.getButton(IDialogConstants.CANCEL_ID).setEnabled(true);
			}
        }
        super.buttonPressed(buttonId);
    }
    
    /**
     * Method configureShell.
     * @param shell Shell
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (null != fTitle) {
			shell.setText(fTitle);
		}
    }
    
    /**
     * Method createDialogArea.
     * @param parent Composite
     * @return Control
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
    @Override
	protected Control createDialogArea(Composite parent) {

    	Composite composite = null;

    	try {
    		// create composite
    		composite = (Composite) super.createDialogArea(parent);
    		composite.setLayout(new GridLayout());

    		final ReviewsVersionsIF versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(fInputProject);
    		final CommitDescriptor commit = versionsIf.getLastCommitInfo(fInputProject);

    		createReviewItemDetails(composite, commit);
    		createReviewItemComponents(composite, commit);
    		applyDialogFont(composite);

    	} catch (ReviewVersionsException e) {
    		Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
    		Activator.getDefault().logError("Exception: " + e.toString(), e);
    		final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while getting review item ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
    		dialog.open();
    	}
    	return composite;
    }

    
    /**
     * Method createReviewItemDetails.
     * @param aParent Composite
     * @param aCommit CommitDescriptor
     */
    private void createReviewItemDetails(Composite aParent, CommitDescriptor aCommit) {
    	
    	FormData data = null;
        
        //Review Item info composite
		final Group reviewItemDetailsGroup = new Group(aParent, SWT.BORDER_SOLID);
		reviewItemDetailsGroup.setText(fItemDetailsMessage);
        final GridData groupLayoutData = new GridData(GridData.FILL, GridData.FILL, true, false);
        groupLayoutData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
        reviewItemDetailsGroup.setLayoutData(groupLayoutData);
        reviewItemDetailsGroup.setLayout(new FormLayout());
        
	    //Title
        final Label titlelabel = new Label(reviewItemDetailsGroup, SWT.NONE);
        titlelabel.setText("Title: ");
	    data = new FormData();
	    data.left = new FormAttachment(reviewItemDetailsGroup, ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(reviewItemDetailsGroup, ITabbedPropertyConstants.VSPACE);
	    titlelabel.setLayoutData(data);
        
        final Text titleText = new Text(reviewItemDetailsGroup, getInputTextStyle());
        titleText.setText(aCommit.getTitle());
        titleText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, convertHorizontalDLUsToPixels(TEXT_LEFT_ALIGNMENT));
	    data.right = new FormAttachment(TEXT_RIGHT_ALIGNMENT, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(reviewItemDetailsGroup, ITabbedPropertyConstants.VSPACE);
	    titleText.setLayoutData(data);
	    
	    //Id
        final Label idlabel = new Label(reviewItemDetailsGroup, SWT.NONE);
        idlabel.setText("ID: ");
	    data = new FormData();
	    data.left = new FormAttachment(0, ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(titlelabel, ITabbedPropertyConstants.VSPACE);
	    idlabel.setLayoutData(data);
        
        final Text idText = new Text(reviewItemDetailsGroup, getInputTextStyle());
        fReviewItemDescriptor = aCommit;
        idText.setText(fReviewItemDescriptor.getId());
        idText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, convertHorizontalDLUsToPixels(TEXT_LEFT_ALIGNMENT));
	    data.right = new FormAttachment(TEXT_RIGHT_ALIGNMENT, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(titleText, ITabbedPropertyConstants.VSPACE);
	    idText.setLayoutData(data);
	    
	    //Author
        final Label authorlabel = new Label(reviewItemDetailsGroup, SWT.NONE);
        authorlabel.setText("Author: ");
	    data = new FormData();
	    data.left = new FormAttachment(0, ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(idlabel, ITabbedPropertyConstants.VSPACE);
	    authorlabel.setLayoutData(data);
        
        final Text authorText = new Text(reviewItemDetailsGroup, getInputTextStyle());
        authorText.setText(aCommit.getAuthor());
        authorText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, convertHorizontalDLUsToPixels(TEXT_LEFT_ALIGNMENT));
	    data.right = new FormAttachment(TEXT_RIGHT_ALIGNMENT, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(idText, ITabbedPropertyConstants.VSPACE);
	    authorText.setLayoutData(data);
	      
	    //Committer
        final Label commiterlabel = new Label(reviewItemDetailsGroup, SWT.NONE);
        commiterlabel.setText("Committer: ");
	    data = new FormData();
	    data.left = new FormAttachment(reviewItemDetailsGroup, ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(authorlabel, ITabbedPropertyConstants.VSPACE);
	    commiterlabel.setLayoutData(data);
        
        final Text commiterText = new Text(reviewItemDetailsGroup, getInputTextStyle());
        commiterText.setText(aCommit.getAuthor());
        commiterText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, convertHorizontalDLUsToPixels(TEXT_LEFT_ALIGNMENT));
	    data.right = new FormAttachment(TEXT_RIGHT_ALIGNMENT, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(authorText, ITabbedPropertyConstants.VSPACE);
	    commiterText.setLayoutData(data);
	    
	    //Date
        final Label datelabel = new Label(reviewItemDetailsGroup, SWT.NONE);
        datelabel.setText("Date: ");
	    data = new FormData();
	    data.left = new FormAttachment(reviewItemDetailsGroup, ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(commiterlabel, ITabbedPropertyConstants.VSPACE);
	    datelabel.setLayoutData(data);
        
        final Text dateText = new Text(reviewItemDetailsGroup, getInputTextStyle());
        dateText.setText(new Date(aCommit.getCommitDate().longValue()).toString());
        dateText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, convertHorizontalDLUsToPixels(TEXT_LEFT_ALIGNMENT));
	    data.right = new FormAttachment(TEXT_RIGHT_ALIGNMENT, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(commiterText, ITabbedPropertyConstants.VSPACE);
	    dateText.setLayoutData(data);
	    
	    //Message
        final Label messagelabel = new Label(reviewItemDetailsGroup, SWT.NONE);
        messagelabel.setText("Message: ");
	    data = new FormData();
	    data.left = new FormAttachment(reviewItemDetailsGroup, ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(datelabel, ITabbedPropertyConstants.VSPACE);
	    messagelabel.setLayoutData(data);
        
        final Text messageText = new Text(reviewItemDetailsGroup, getInputTextStyle());
        messageText.setText(aCommit.getMessage());
        messageText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, convertHorizontalDLUsToPixels(TEXT_LEFT_ALIGNMENT));
	    data.right = new FormAttachment(TEXT_RIGHT_ALIGNMENT, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(dateText, ITabbedPropertyConstants.VSPACE);
	    messageText.setLayoutData(data);
    }
    
    /**
     * Method createReviewItemComponents.
     * @param aParent Composite
     * @param aCommit CommitDescriptor
     */
    private void createReviewItemComponents(Composite aParent, CommitDescriptor aCommit) {
        
        //Review Item info composite
		final Group reviewItemComponentsGroup = new Group(aParent, SWT.BORDER_SOLID);
		reviewItemComponentsGroup.setText(fItemComponentsMessage);
        final GridData groupLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
        groupLayoutData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
        reviewItemComponentsGroup.setLayoutData(groupLayoutData);
        reviewItemComponentsGroup.setLayout(new GridLayout());
        //This is a test
        final List componentsList = new List(reviewItemComponentsGroup, getInputTextStyle());
        componentsList.setItems(aCommit.getChangeSet());
        final GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);
	    componentsList.setLayoutData(data);
	    
    }
    
	/**
	 * Returns the style bits that should be used for the input text field.
	 * Defaults to a single line entry. Subclasses may override.
	 * @since 3.4
	 * @return the integer style bits that should be used when creating the input text
	 */
	protected int getInputTextStyle() {
		return SWT.WRAP | SWT.READ_ONLY | SWT.H_SCROLL;
	}
	
	/**
	 * Method isResizable.
	 * @return boolean
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
}
