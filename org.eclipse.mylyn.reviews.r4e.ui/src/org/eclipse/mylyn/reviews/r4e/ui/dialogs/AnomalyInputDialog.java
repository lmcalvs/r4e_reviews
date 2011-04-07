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
 * This class implements the dialog used to fill-in the Anomaly element details
 * This is a modeless-like dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleViolation;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorLabelProvider;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorTreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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
public class AnomalyInputDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field ADD_ANOMALY_DIALOG_TITLE.
	 * (value is ""Enter Anomaly details"")
	 */
	private static final String ADD_ANOMALY_DIALOG_TITLE = "Enter Anomaly Details";
	
	/**
	 * Field ADD_ANOMALY_DIALOG_VALUE.
	 * (value is ""Enter the Anomaly title:"")
	 */
	private static final String ADD_ANOMALY_DIALOG_VALUE = "Anomaly Title: ";
	
	/**
	 * Field ADD_COMMENT_DIALOG_VALUE.
	 * (value is ""Enter your comments for the new Anomaly:"")
	 */
	private static final String ADD_DESCRIPTION_DIALOG_VALUE = "Anomaly Description: ";
	
	/**
	 * Field BASIC_PARAMS_HEADER_MSG.
	 * (value is ""Enter the mandatory basic parameters for this anomaly"")
	 */
	private static final String BASIC_PARAMS_HEADER_MSG = "Enter the mandatory basic parameters for this anomaly";
	
	/**
	 * Field EXTRA_PARAMS_HEADER_MSG.
	 * (value is ""Enter the optional extra parameters for this Review Group"")
	 */
	private static final String EXTRA_PARAMS_HEADER_MSG = "Enter the optional extra parameters for this Review";
	
	/**
	 * Field ADD_RULE_DIALOG_VALUE.
	 * (value is ""Rule: "")
	 */
	private static final String ADD_RULE_DIALOG_VALUE = "Rule Tree " +
			"(Take note that the Anomaly will be created with the Design Rule default values)";
	
	/**
	 * Field DEFAULT_ELEMENT_COLUMN_WIDTH.
	 * (value is "150")
	 */
	private static final int DEFAULT_ELEMENT_COLUMN_WIDTH = 150;
	
	/**
	 * Field DEFAULT_TREE_COLUMN_WIDTH.
	 * (value is "100")
	 */
	private static final int DEFAULT_TREE_COLUMN_WIDTH = 100;
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fAnomalyTitleValue.
	 */
	private String fAnomalyTitleValue = "";
	
	/**
	 * Field fAnomalyTitleInputTextField.
	 */
	protected Text fAnomalyTitleInputTextField = null;
	
	/**
	 * Field fAnomalyDescriptionValue.
	 */
    private String fAnomalyDescriptionValue = "";
    
	/**
	 * Field fAnomalyDescriptionInputTextField.
	 */
    protected Text fAnomalyDescriptionInputTextField;
	
	/**
	 * Field fRuleTreeViewer.
	 */
	protected TreeViewer fRuleTreeViewer = null;
	
	/**
	 * Field fRuleReferenceValue.
	 */
	private R4EUIRule fRuleReferenceValue = null;
	
    /**
     * The input validator, or <code>null</code> if none.
     */
    private final IInputValidator fValidator;
    
    
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
    
	/**
	 * Constructor for R4EAnomalyInputDialog.
	 * @param aParentShell Shell
	 */
	public AnomalyInputDialog(Shell aParentShell) {
		super(aParentShell);
		setBlockOnOpen(false);
		fValidator = new R4EInputValidator();
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
	    	this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));

        	//Validate Anomaly Title
        	String validateResult = validateEmptyInput(fAnomalyTitleInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "No input given for Anomaly Title", 
    					new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
        		return;
        	}
        	fAnomalyTitleValue = fAnomalyTitleInputTextField.getText();
        	
        	//Validate Anomaly Comment
        	validateResult = validateEmptyInput(fAnomalyDescriptionInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "No input given for Anomaly Comment",
        				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
        		return;
        	}
        	fAnomalyDescriptionValue = fAnomalyDescriptionInputTextField.getText();
        	
        	//Validate R4EUIRule (if present)
        	fRuleReferenceValue = null;
        	if (fRuleTreeViewer.getSelection() instanceof IStructuredSelection) {
        		final IStructuredSelection selection = (IStructuredSelection)fRuleTreeViewer.getSelection();
        		if (null != selection) {
        			fRuleReferenceValue = (R4EUIRule) selection.getFirstElement();
        		}
        	}
        } else {
        	fAnomalyTitleValue = null;
        	fAnomalyDescriptionValue = null;
        	fRuleReferenceValue = null;
        }
		this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
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
        shell.setText(ADD_ANOMALY_DIALOG_TITLE);
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

		final FormToolkit toolkit = mform.getToolkit();
		final ScrolledForm sform = mform.getForm();
		sform.setExpandVertical(true);
		final Composite composite = sform.getBody();
		final GridLayout layout = new GridLayout(4, false);
		composite.setLayout(layout);
        GridData textGridData = null;

		//Basic parameters section
        final Section basicSection = toolkit.createSection(composite, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
        final GridData basicSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        basicSectionGridData.horizontalSpan = 4;
        basicSection.setLayoutData(basicSectionGridData);
        basicSection.setText(R4EUIConstants.BASIC_PARAMS_HEADER);
        basicSection.setDescription(BASIC_PARAMS_HEADER_MSG);
        basicSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
        
        final Composite basicSectionClient = toolkit.createComposite(basicSection);
        basicSectionClient.setLayout(layout);
        basicSection.setClient(basicSectionClient);
        
        //Anomaly Title
        Label label = toolkit.createLabel(basicSectionClient, ADD_ANOMALY_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fAnomalyTitleInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        fAnomalyTitleInputTextField.setLayoutData(textGridData);
        
        //Anomaly Description
        label = toolkit.createLabel(basicSectionClient, ADD_DESCRIPTION_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fAnomalyDescriptionInputTextField = toolkit.createText(basicSectionClient, "", SWT.MULTI | SWT.V_SCROLL);
        textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.horizontalSpan = 3;
        textGridData.heightHint = fAnomalyTitleInputTextField.getLineHeight() * 3;
        fAnomalyDescriptionInputTextField.setLayoutData(textGridData);
        
        //Extra parameters section
        final Section extraSection = toolkit.createSection(composite, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE);
        final GridData extraSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
        extraSectionGridData.horizontalSpan = 4;
        extraSection.setLayoutData(extraSectionGridData);
        extraSection.setText(R4EUIConstants.EXTRA_PARAMS_HEADER);
        extraSection.setDescription(EXTRA_PARAMS_HEADER_MSG);
        extraSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
        
        final Composite extraSectionClient = toolkit.createComposite(extraSection);
        extraSectionClient.setLayout(layout);
        extraSection.setClient(extraSectionClient);
        
        //Rule Tree
        label = toolkit.createLabel(extraSectionClient, ADD_RULE_DIALOG_VALUE);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 4;
        label.setLayoutData(textGridData);
        
        fRuleTreeViewer = new ReviewNavigatorTreeViewer(extraSectionClient, SWT.FULL_SELECTION | 
        		SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
        fRuleTreeViewer.setContentProvider(new ReviewNavigatorContentProvider());
        fRuleTreeViewer.getTree().setHeaderVisible(true);
        
		final TreeViewerColumn elementColumn = new TreeViewerColumn(fRuleTreeViewer, SWT.NONE);
		elementColumn.getColumn().setText("Rule Tree");
		elementColumn.getColumn().setWidth(DEFAULT_ELEMENT_COLUMN_WIDTH);
		elementColumn.setLabelProvider(new ReviewNavigatorLabelProvider());
        
		final TreeViewerColumn classColumn = new TreeViewerColumn(fRuleTreeViewer, SWT.NONE);
		classColumn.getColumn().setText("Rule Class");
		classColumn.getColumn().setWidth(DEFAULT_TREE_COLUMN_WIDTH);
		classColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof R4EUIRule) {
					return getClassStr(((R4EUIRule)element).getRule().getClass_());
				}
				return null;
			}
		});
        
		final TreeViewerColumn rankColumn = new TreeViewerColumn(fRuleTreeViewer, SWT.NONE);
		rankColumn.getColumn().setText("Rule Rank");
		rankColumn.getColumn().setWidth(DEFAULT_TREE_COLUMN_WIDTH);
		rankColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof R4EUIRule) {
					return getRankStr(((R4EUIRule)element).getRule().getRank());
				}
				return null;
			}
		});
		
		fRuleTreeViewer.setInput(R4EUIModelController.getRootElement());
		
		fRuleTreeViewer.addFilter(new ViewerFilter() {		
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
		        //Only display rule sets that are included in the parent review group
				if (element instanceof R4EUIRuleSet || element instanceof R4EUIRuleArea || element instanceof R4EUIRuleViolation ||
						element instanceof R4EUIRule) {
					//Get parent RuleSet
					IR4EUIModelElement parentRuleSetElement = (IR4EUIModelElement) element;
					while (!(parentRuleSetElement instanceof R4EUIRuleSet) && null != parentRuleSetElement.getParent()) {
						if (!parentRuleSetElement.isEnabled()) return false;
						parentRuleSetElement = parentRuleSetElement.getParent();
					}
					//If the current reveiw group contains a reference to this Rule Set, display it
					if ((((R4EUIReviewGroup) R4EUIModelController.getActiveReview().getParent()).getRuleSets().
							contains(parentRuleSetElement))) {
						return true;
					}		
				}
				return false;
			}
		});
		fRuleTreeViewer.collapseAll();
		fRuleTreeViewer.refresh();

		textGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		textGridData.horizontalSpan = 4;
		fRuleTreeViewer.getTree().setLayoutData(textGridData);
		
		fRuleTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				//Only Rules are selectable
				if(event.getSelection() instanceof IStructuredSelection ) {
					if (null == ((IStructuredSelection)event.getSelection()).getFirstElement()) return;
					if (((IStructuredSelection)event.getSelection()).getFirstElement() instanceof R4EUIRule) {
						final R4EUIRule rule = (R4EUIRule) ((IStructuredSelection)event.getSelection()).getFirstElement();
						fAnomalyTitleInputTextField.setText(rule.getRule().getTitle());
						fAnomalyDescriptionInputTextField.setText(rule.getRule().getDescription());
						return;
					}
				}
				fRuleTreeViewer.setSelection(null);
			}
		});
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
    
    /**
     * Returns the string typed into this input dialog.
     * @return the anomaly title input string
     */
    public String getAnomalyTitleValue() {
        return fAnomalyTitleValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the anomaly description input string
     */
    public String getAnomalyDescriptionValue() {
        return fAnomalyDescriptionValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the R4EUIRule reference (if any)
     */
    public R4EUIRule getRuleReferenceValue() {
        return fRuleReferenceValue;
    }
    
    /**
     * Method validateEmptyInput.
     * @param aText Text
     * @return String
     */
    private String validateEmptyInput(Text aText) {
        return fValidator.isValid(aText.getText());
    }
    
    /**
     * Method setShellStyle.
     * @param newShellStyle int
     */
    @Override
	protected void setShellStyle(int newShellStyle) {	
    	int newstyle = newShellStyle & ~SWT.APPLICATION_MODAL; /* turn off APPLICATION_MODAL */
    	newstyle |= SWT.MODELESS; /* turn on MODELESS */
    	super.setShellStyle(newstyle); 
    }
    
    /**
     * Method open.
     * @return int
     */
    @Override
	public int open() {
    	super.open();
    	pumpMessages(); /* this will let the caller wait till OK, Cancel is pressed, but will let the other GUI responsive */
    	return super.getReturnCode();
    }

    /**
     * Method pumpMessages.
     */
    protected void pumpMessages() {
    	final Shell sh = getShell();	
    	final Display disp = sh.getDisplay(); 
    	while( !sh.isDisposed() ) { // $codepro.audit.disable methodInvocationInLoopCondition
    		if( !disp.readAndDispatch() ) disp.sleep();
    	}
    	disp.update();
    }
    
    /**
     * Method getClassStr.
     * @param aClass R4EDesignRuleClass
     * @return String
     */
    protected String getClassStr(R4EDesignRuleClass aClass) {
		if (aClass.equals(R4EDesignRuleClass.R4E_CLASS_ERRONEOUS)) {
			return R4EUIConstants.ANOMALY_CLASS_ERRONEOUS;
		} else if (aClass.equals(R4EDesignRuleClass.R4E_CLASS_SUPERFLUOUS)) {
			return R4EUIConstants.ANOMALY_CLASS_SUPERFLUOUS;
		} else if (aClass.equals(R4EDesignRuleClass.R4E_CLASS_IMPROVEMENT)) {
			return R4EUIConstants.ANOMALY_CLASS_IMPROVEMENT;
		} else if (aClass.equals(R4EDesignRuleClass.R4E_CLASS_QUESTION)) {
			return R4EUIConstants.ANOMALY_CLASS_QUESTION;
		} else {
			return null;   //should never happen
		}
    }
    
    /**
     * Method getRankStr.
     * @param aRank R4EDesignRuleRank
     * @return String
     */
    protected String getRankStr(R4EDesignRuleRank aRank) {
		if (aRank.equals(R4EDesignRuleRank.R4E_RANK_NONE)) {
			return R4EUIConstants.ANOMALY_RANK_NONE ;
		} else if (aRank.equals(R4EDesignRuleRank.R4E_RANK_MINOR)) {
			return R4EUIConstants.ANOMALY_RANK_MINOR;
		} else if (aRank.equals(R4EDesignRuleRank.R4E_RANK_MAJOR)) {
			return R4EUIConstants.ANOMALY_RANK_MAJOR;
		} else return null;   //should never happen
    }
}
