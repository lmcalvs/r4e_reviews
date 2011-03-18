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
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIFFactory;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FindReviewItemsDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field ADD_ANOMALY_DIALOG_TITLE.
	 * (value is ""Enter Anomaly details"")
	 */
	private static final String FIND_REVIEW_ITEMS_DIALOG_TITLE = "Find Review Items";

	/**
	 * Field ADD_ANOMALY_DIALOG_VALUE.
	 * (value is ""Enter the Anomaly title:"")
	 */
	private static final String FIND_REVIEW_ITEMS_DIALOG_VALUE = "Review Item Info";

	/**
	 * Field ADD_COMMENT_DIALOG_VALUE.
	 * (value is ""Enter your comments for the new Anomaly:"")
	 */
	private static final String FIND_REVIEW_ITEMS_DESCRIPTION_DIALOG_VALUE = "Review Item Components";
	
	/**
	 * Field COMMIT_INFO_HEADER_MSG.
	 * (value is ""Commit Information"")
	 */
	private static final String COMMIT_INFO_HEADER_MSG = "Commit Information";
	
	/**
	 * Field COMMIT_COMPONENTS_HEADER_MSG.
	 * (value is ""Committed Components"")
	 */
	private static final String COMMIT_COMPONENTS_HEADER_MSG = "Committed Components";
	
	/**
	 * Field DIALOG_COMBO_MAX_CHARACTERS.
	 * (value is 80)
	 */
	private static final int DIALOG_COMBO_MAX_CHARACTERS = 80;

	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
    /**
     * Input text widget.
     */
	protected final IProject fInputProject;

    /**
     * Field fReviewItemDescriptor.
     */
    protected CommitDescriptor fReviewItemDescriptor;
    
    Label fTitleText = null;
    Label fIdText = null;
    Label fAuthorText = null;
    Label fCommitterText = null;
    Label fDateText = null;
    Label fMessageText = null;
    List fComponentsList = null;
	final SimpleDateFormat fDateFormat = new SimpleDateFormat(R4EUIConstants.SIMPLE_DATE_FORMAT);	

	
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
	public FindReviewItemsDialog(Shell aParentShell, IProject aInputProject) {
		super(aParentShell);
    	setBlockOnOpen(true);
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
				final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
				final String user = R4EUIModelController.getReviewer();
				final R4EParticipant participant = uiReview.getParticipant(user, true);
				
	        	//Get a temporary review item not attached to anything
				final ReviewsVersionsIF versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(fInputProject);
				final R4EItem tmpReviewItem = versionsIf.createCommitReviewItem(fInputProject, fReviewItemDescriptor.getId(), participant);
				if (null == tmpReviewItem) {
					//No files are found
					Activator.Ftracer.traceWarning("No Files found for this Review Item");
					Activator.getDefault().logWarning("No Files found for this Review Item", null);
					final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING, "No files found for this review item.",
							new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, null, null), IStatus.WARNING);
					dialog.open();	
				} else {
					final EList<R4EFileContext> tmpFiles = tmpReviewItem.getFileContextList();
					if (0 == tmpFiles.size())
					{
						//No files are found
						Activator.Ftracer.traceWarning("No Files found for this Review Item");
						Activator.getDefault().logWarning("No Files found for this Review Item", null);
						final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING, "No files found for this review item.",
								new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, null, null), IStatus.WARNING);
						dialog.open();
					} else {
						// Create a new review item in the serialization model
						final R4EItem reviewItem = R4EUIModelController.FModelExt.createR4EItem(participant);
						final R4EUIReviewItem uiReviewItem = new R4EUIReviewItem(uiReview, reviewItem,
								R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT, fReviewItemDescriptor, null);
						Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(reviewItem,
								R4EUIModelController.getReviewer());
						reviewItem.getProjectURIs().add(tmpReviewItem.getProjectURIs().get(0)); // For now we only take
																								// the first project ID
						reviewItem.setDescription(tmpReviewItem.getDescription());
						reviewItem.setRepositoryRef(tmpReviewItem.getRepositoryRef());
						// Get handle to local storage repository. No need to continue in case of failure.
						IRFSRegistry revRepo = null;
						try {
							revRepo = RFSRegistryFactory.getRegistry((R4EReview) reviewItem.getReview());
						} catch (ReviewsFileStorageException e1) {
							Activator.Ftracer.traceWarning("Exception while obtaining handle to local repo: "
									+ e1.toString() + " (" + e1.getMessage() + ")");
							Activator.getDefault().logWarning("Exception: " + e1.toString(), e1);
							final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
									"Exception while obtaining handle to local repo: "
											+ " Cannot get to interface to the local reviews repository", new Status(
											IStatus.WARNING, Activator.PLUGIN_ID, 0, e1.getMessage(), e1),
									IStatus.WARNING);

							// TODO: Disable created item (incomplete), i.e. shall not be visible to the users
							R4EUIModelController.FResourceUpdater.undoCheckOut(bookNum);
							dialog.open();
							return;
						}

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
									InputStream is = versionsIf.getBlobById(fInputProject, versionId);
									try {
										String localId = revRepo.registerReviewBlob(is);
										baseVersion.setLocalVersionID(localId);
									} catch (ReviewsFileStorageException e) {
										Activator.Ftracer.traceWarning("Exception while obtaining handle to local repo: " + 
												e.toString() + " (" + e.getMessage() + ")");
										Activator.getDefault().logWarning("Exception: " + e.toString(), e);
										final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
												"Exception while obtaining handle to local repo: " + 
												" Cannot get to interface to the local reviews repository",
												new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, e.getMessage(), e),IStatus.WARNING);

										// TODO: Disable created item (incomplete), i.e. shall not be visible to the
										// users
										R4EUIModelController.FResourceUpdater.undoCheckOut(bookNum);
										dialog.open();
										return;
									} finally {
										if (is != null) {
											try {
												is.close();
											} catch (IOException e) {
												Activator.Ftracer.traceError("IOException while trying to close stream: " + e.getMessage());
											}
										}
									}
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
								InputStream is = versionsIf.getBlobById(fInputProject, versionId);
								try {
									String localId = revRepo.registerReviewBlob(is);
									targetVersion.setLocalVersionID(localId);
								} catch (ReviewsFileStorageException e) {
									Activator.Ftracer.traceWarning("Exception while obtaining handle to local repo: "
											+ e.toString() + " (" + e.getMessage() + ")");
									Activator.getDefault().logWarning("Exception: " + e.toString(), e);
									final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
											"Exception while obtaining handle to local repo: " + 
											" Cannot get to interface to the local reviews repository",
											new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, e.getMessage(), e),
											IStatus.WARNING);

									// TODO: Disable created item (incomplete), i.e. shall not be visible to the users
									R4EUIModelController.FResourceUpdater.undoCheckOut(bookNum);
									dialog.open();
									return;
								} finally {
									if (is != null) {
										try {
											is.close();
										} catch (IOException e) {
											Activator.Ftracer.traceError("IOException while trying to close stream: " + e.getMessage());
										}										
									}
								}
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
				UIUtils.displayVersionErrorDialog(e);

			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);

			} catch (OutOfSyncException e) {
				UIUtils.displaySyncErrorDialog(e);

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
        shell.setText(FIND_REVIEW_ITEMS_DIALOG_TITLE);
    }
    
	/**
	 * Configures the dialog form and creates form content. Clients should
	 * override this method.
	 * 
	 * @param mform
	 *            the dialog form
	 */
	@Override
	protected void createFormContent(final IManagedForm mform) {

    	try {
    		final ReviewsVersionsIF versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(fInputProject);
    		//fReviewItemDescriptor = versionsIf.getLastCommitInfo(fInputProject);

    		final FormToolkit toolkit = mform.getToolkit();
    		final ScrolledForm sform = mform.getForm();
    		sform.setExpandVertical(true);

            //Main dialog composite
    		final Composite composite = sform.getBody();
    		composite.setLayout(new GridLayout(4, false));
    		
    		//Add Commit List in drop-down menu
            Label label = toolkit.createLabel(composite, "Available Commits: ");
            label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
    		final CCombo commitList = new CCombo(composite, SWT.WRAP | SWT.READ_ONLY);
    		commitList.setItems(getCommitTitleList(versionsIf));
    		commitList.setTextLimit(DIALOG_COMBO_MAX_CHARACTERS);
    		commitList.select(0);
    		fReviewItemDescriptor = versionsIf.getCommitInfo(fInputProject, versionsIf.getCommitIds(fInputProject).get(0));
    		GridData textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
            textGridData.horizontalSpan = 3;
    		commitList.setLayoutData(textGridData);
    		commitList.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
		    		try {
						fReviewItemDescriptor = versionsIf.getCommitInfo(fInputProject, 
								versionsIf.getCommitIds(fInputProject).get(commitList.getSelectionIndex()));
						refresh();
					} catch (ReviewVersionsException e1) {
						//TODO add error hangling code
						e1.printStackTrace();
					}
					commitList.getText();
				}
				public void widgetDefaultSelected(SelectionEvent e) {
					//Nothing to do
				}
			});
    		
    		createReviewItemDetails(toolkit, sform);
    		createReviewItemComponents(toolkit, sform);

    	} catch (ReviewVersionsException e) {
			UIUtils.displayVersionErrorDialog(e);
    	}
    	return;
	}
    
    /**
     * Method createReviewItemDetails.
     * @param aParent Composite
     * @param aCommit CommitDescriptor
     */
    private void createReviewItemDetails(FormToolkit aToolkit, final ScrolledForm aParent) {
        
        GridData textGridData = null;

		//Basic parameters section
        final Section basicSection = aToolkit.createSection(aParent.getBody(), Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
        final GridData basicSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        basicSectionGridData.horizontalSpan = 4;
        basicSection.setLayoutData(basicSectionGridData);
        basicSection.setText(COMMIT_INFO_HEADER_MSG);
        basicSection.setDescription(FIND_REVIEW_ITEMS_DIALOG_VALUE);
        basicSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				aParent.reflow(true);
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
        final Composite basicSectionClient = aToolkit.createComposite(basicSection);
        basicSectionClient.setLayout(new GridLayout(4, false));
        basicSection.setClient(basicSectionClient);
        
	    //Title
        final Label titlelabel = aToolkit.createLabel(basicSectionClient, "Title: ", SWT.WRAP);
	    titlelabel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
        fTitleText = aToolkit.createLabel(basicSectionClient, fReviewItemDescriptor.getTitle(), SWT.NONE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        fTitleText.setLayoutData(textGridData);
	    
	    //Id
        final Label idlabel = aToolkit.createLabel(basicSectionClient, "ID: ", SWT.WRAP);
	    idlabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fIdText = aToolkit.createLabel(basicSectionClient, fReviewItemDescriptor.getId(), SWT.NONE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        textGridData.widthHint = 80;
        fIdText.setLayoutData(textGridData);
	    
	    //Author
        final Label authorlabel = aToolkit.createLabel(basicSectionClient, "Author: ", SWT.WRAP);
	    authorlabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fAuthorText = aToolkit.createLabel(basicSectionClient, fReviewItemDescriptor.getAuthor(), SWT.NONE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        textGridData.widthHint = 80;
        fAuthorText.setLayoutData(textGridData);
	      
	    //Committer
        final Label committerlabel = aToolkit.createLabel(basicSectionClient, "Committer: ", SWT.WRAP);
        committerlabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fCommitterText = aToolkit.createLabel(basicSectionClient, fReviewItemDescriptor.getCommitter(), SWT.NONE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        textGridData.widthHint = 80;
        fCommitterText.setLayoutData(textGridData);
	    
	    //Date
        final Label datelabel = aToolkit.createLabel(basicSectionClient, "Date: ", SWT.WRAP);
	    datelabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		String dateStr = fDateFormat.format(new Date(fReviewItemDescriptor.getCommitDate().longValue()));
        fDateText = aToolkit.createLabel(basicSectionClient, dateStr, SWT.NONE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        textGridData.widthHint = 80;
        fDateText.setLayoutData(textGridData);
	    
	    //Message
        final Label messagelabel = aToolkit.createLabel(basicSectionClient, "Message: ", SWT.WRAP);
	    messagelabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fMessageText = aToolkit.createLabel(basicSectionClient, fReviewItemDescriptor.getMessage(), SWT.NONE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        textGridData.widthHint = 80;
        fMessageText.setLayoutData(textGridData);
	    
	    basicSectionClient.layout();
    }
    
    /**
     * Method createReviewItemComponents.
     * @param aParent Composite
     * @param aCommit CommitDescriptor
     */
    private void createReviewItemComponents(FormToolkit aToolkit, final ScrolledForm aParent) {
        
        //Extra parameters section
        final Section extraSection = aToolkit.createSection(aParent.getBody(), Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
        final GridData extraSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
        extraSectionGridData.horizontalSpan = 4;
        extraSection.setLayoutData(extraSectionGridData);
        extraSection.setText(COMMIT_COMPONENTS_HEADER_MSG);
        extraSection.setDescription(FIND_REVIEW_ITEMS_DESCRIPTION_DIALOG_VALUE);
        extraSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				aParent.reflow(true);
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
        
        final Composite extraSectionClient = aToolkit.createComposite(extraSection);
        extraSectionClient.setLayout(new GridLayout(4, false));
        extraSectionClient.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        extraSection.setClient(extraSectionClient);
        
        //Components List
        fComponentsList = new List(extraSectionClient, SWT.V_SCROLL | SWT.H_SCROLL);
        fComponentsList.setItems(fReviewItemDescriptor.getChangeSet());
        final GridData data = new GridData(GridData.FILL, GridData.FILL, true, true);
        fComponentsList.setLayoutData(data);
    }
	
	/**
	 * Method getCommitTitleList.
	 * @param aVersionsIf ReviewsVersionsIF
	 * @return String[]
	 */
    public String[] getCommitTitleList(ReviewsVersionsIF aVersionsIf) throws ReviewVersionsException {
    	java.util.List<String> commitIds = aVersionsIf.getCommitIds(fInputProject);
    	java.util.List<String> commitTitles = new ArrayList<String>();
    	String title = null;
    	for (String commitId : commitIds) {
    		title = aVersionsIf.getCommitInfo(fInputProject, commitId).getTitle();
    		//Truncate title is it is too long to avoid screwing up the display
    		if (title.length() > DIALOG_COMBO_MAX_CHARACTERS) title = (title.substring(0, DIALOG_COMBO_MAX_CHARACTERS - 3) + "...");
    		commitTitles.add(title);
    	}
    	return commitTitles.toArray(new String[commitTitles.size()]);
    }
    
    void refresh() {
    	fTitleText.setText(fReviewItemDescriptor.getTitle());
    	fIdText.setText(fReviewItemDescriptor.getId());
    	fAuthorText.setText(fReviewItemDescriptor.getAuthor());
    	fCommitterText.setText(fReviewItemDescriptor.getCommitter());
    	fDateText.setText(fDateFormat.format(new Date(fReviewItemDescriptor.getCommitDate().longValue())));
    	fMessageText.setText(fReviewItemDescriptor.getMessage());
    	fComponentsList.setItems(fReviewItemDescriptor.getChangeSet());
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
