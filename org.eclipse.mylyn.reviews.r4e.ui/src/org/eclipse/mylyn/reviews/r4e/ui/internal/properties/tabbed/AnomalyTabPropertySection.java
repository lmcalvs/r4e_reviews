// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
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
 * This class implements the tabbed property section for the Anomaly model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.AnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.CalendarDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AnomalyTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field ANOMALY_DETAILS_SECTION_LABEL. (value is ""Anomaly Details"")
	 */
	private static final String ANOMALY_DETAILS_SECTION_LABEL = "Anomaly Details";

	/**
	 * Field PARTICIPANT_DETAILS_SECTION_LABEL. (value is ""Participants Details"")
	 */
	private static final String PARTICIPANT_DETAILS_SECTION_LABEL = "Participants Details";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fPosition.
	 */
	private IR4EUIPosition fPosition;

	/**
	 * Field FTitleText.
	 */
	protected Text fTitleText = null;

	/**
	 * Field FAuthorText.
	 */
	protected CLabel fAuthorText = null;

	/**
	 * Field FCreationDateText.
	 */
	protected CLabel fCreationDateText = null;

	/**
	 * Field FPositionText.
	 */
	protected CLabel fPositionText = null;

	/**
	 * Field FDescriptionText.
	 */
	protected Text fDescriptionText = null;

	/**
	 * Field fStateCombo.
	 */
	protected CCombo fStateCombo = null;

	/**
	 * Field fClassCombo.
	 */
	protected CCombo fClassCombo = null;

	/**
	 * Field fRankCombo.
	 */
	protected CCombo fRankCombo = null;

	/**
	 * Field fRuleId.
	 */
	protected Text fRuleId = null;

	/**
	 * Field fRuleButton.
	 */
	protected Button fRuleButton = null;

	/**
	 * Field fDateText.
	 */
	protected Text fDateText = null;

	/**
	 * Field fCalendarButton.
	 */
	protected Button fCalendarButton = null;

	/**
	 * Field fRankCombo.
	 */
	protected CCombo fDecidedByCombo = null;

	/**
	 * Field fRankCombo.
	 */
	protected CCombo fFixedByCombo = null;

	/**
	 * Field fRankCombo.
	 */
	protected CCombo fFollowUpByCombo = null;

	/**
	 * Field fNotAcceptedReasonText.
	 */
	protected Text fNotAcceptedReasonText = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method shouldUseExtraSpace.
	 * 
	 * @return boolean
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	/**
	 * Method createControls.
	 * 
	 * @param parent
	 *            Composite
	 * @param aTabbedPropertySheetPage
	 *            TabbedPropertySheetPage
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(Composite, TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		//Tell element to build its own detailed tab layout
		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
		final Composite composite = widgetFactory.createFlatFormComposite(parent);
		FormData data = null;

		//Anomaly title
		fTitleText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fTitleText.setToolTipText(R4EUIConstants.ANOMALY_TITLE_TOOLTIP);
		fTitleText.setLayoutData(data);
		fTitleText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						//Set new model data
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyBasic) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setTitle(fTitleText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//Set new UI display
						if (fProperties.getElement() instanceof R4EUIAnomalyExtended) {
							fProperties.getElement().setName(
									R4EUIAnomalyExtended.buildAnomalyExtName(modelAnomaly,
											((R4EUIAnomalyExtended) fProperties.getElement()).getPosition()));
						} else {
							fProperties.getElement().setName(
									R4EUIAnomalyBasic.buildAnomalyName(modelAnomaly,
											((R4EUIAnomalyBasic) fProperties.getElement()).getPosition()));
						}

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
				refresh();
			}

			public void focusGained(FocusEvent e) { // $codepro.audit.disable emptyMethod
				//Nothing to do
			}
		});
		UIUtils.addTabbedPropertiesTextResizeListener(fTitleText);

		final CLabel titleLabel = widgetFactory.createCLabel(composite, R4EUIConstants.TITLE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fTitleText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fTitleText, 0, SWT.CENTER);
		titleLabel.setToolTipText(R4EUIConstants.ANOMALY_TITLE_TOOLTIP);
		titleLabel.setLayoutData(data);

		//Anomaly Description
		fDescriptionText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fTitleText, ITabbedPropertyConstants.VSPACE);
		fDescriptionText.setLayoutData(data);
		fDescriptionText.setToolTipText(R4EUIConstants.ANOMALY_DESCRIPTION_TOOLTIP);
		fDescriptionText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						//Set new model data
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyBasic) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setDescription(fDescriptionText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//Set new UI display
						fProperties.getElement().setToolTip(R4EUIAnomalyBasic.buildAnomalyToolTip(modelAnomaly));

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void focusGained(FocusEvent e) { // $codepro.audit.disable emptyMethod
				//Nothing to do
			}
		});
		UIUtils.addTabbedPropertiesTextResizeListener(fDescriptionText);

		final CLabel descriptionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DESCRIPTION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fDescriptionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fDescriptionText, 0, SWT.TOP);
		descriptionLabel.setToolTipText(R4EUIConstants.ANOMALY_DESCRIPTION_TOOLTIP);
		descriptionLabel.setLayoutData(data);

		//State
		fStateCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fDescriptionText, ITabbedPropertyConstants.VSPACE);
		fStateCombo.setToolTipText(R4EUIConstants.ANOMALY_STATE_TOOLTIP);
		fStateCombo.setLayoutData(data);
		fStateCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!fRefreshInProgress) {
					try {
						((R4EUIAnomalyExtended) fProperties.getElement()).updateState(R4EUIAnomalyExtended.getStateFromString(fStateCombo.getText()));

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
		addScrollListener(fStateCombo);

		final CLabel stateLabel = widgetFactory.createCLabel(composite, R4EUIConstants.STATE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fStateCombo, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fStateCombo, 0, SWT.CENTER);
		stateLabel.setToolTipText(R4EUIConstants.ANOMALY_STATE_TOOLTIP);
		stateLabel.setLayoutData(data);

		createParticipantDetailsSection(widgetFactory, composite, createAnomalyDetailsSection(widgetFactory, composite));
	}

	/**
	 * Method createAnomalyDetailsSection.
	 * 
	 * @param aWidgetFactory
	 *            TabbedPropertySheetWidgetFactory
	 * @param aComposite
	 *            Composite
	 * @return Composite
	 */
	private Composite createAnomalyDetailsSection(TabbedPropertySheetWidgetFactory aWidgetFactory,
			final Composite aComposite) {
		//Anomaly Details section
		final ExpandableComposite anomalyDetailsSection = aWidgetFactory.createExpandableComposite(aComposite,
				ExpandableComposite.TWISTIE);
		final FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fStateCombo, ITabbedPropertyConstants.VSPACE);
		anomalyDetailsSection.setLayoutData(data);
		anomalyDetailsSection.setText(ANOMALY_DETAILS_SECTION_LABEL);
		anomalyDetailsSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				final ScrolledComposite scrolledParent = (ScrolledComposite) aComposite.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent();
				scrolledParent.setMinSize(aComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				scrolledParent.layout(true, true);
			}
		});
		anomalyDetailsSection.setLayout(new GridLayout(1, false));

		final Composite anomalyDetailsSectionClient = aWidgetFactory.createComposite(anomalyDetailsSection);
		anomalyDetailsSectionClient.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		anomalyDetailsSectionClient.setLayout(new GridLayout(4, false));
		anomalyDetailsSection.setClient(anomalyDetailsSectionClient);

		//Anomaly Creation Date (read-only)
		final CLabel creationDateLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient,
				R4EUIConstants.CREATION_DATE_LABEL);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		creationDateLabel.setToolTipText(R4EUIConstants.ANOMALY_CREATION_DATE_TOOLTIP);
		creationDateLabel.setLayoutData(gridData);

		fCreationDateText = aWidgetFactory.createCLabel(anomalyDetailsSectionClient, "");
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fCreationDateText.setToolTipText(R4EUIConstants.ANOMALY_CREATION_DATE_TOOLTIP);
		fCreationDateText.setLayoutData(gridData);

		//Anomaly position (read-only)
		final CLabel positionLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient,
				R4EUIConstants.POSITION_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		positionLabel.setToolTipText(R4EUIConstants.ANOMALY_POSITION_TOOLTIP);
		positionLabel.setLayoutData(gridData);

		fPositionText = aWidgetFactory.createCLabel(anomalyDetailsSectionClient, "");
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fPositionText.setToolTipText(R4EUIConstants.ANOMALY_POSITION_TOOLTIP);
		fPositionText.setLayoutData(gridData);

		//Class
		final CLabel classLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient, R4EUIConstants.CLASS_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		classLabel.setToolTipText(R4EUIConstants.ANOMALY_CLASS_TOOLTIP);
		classLabel.setLayoutData(gridData);

		fClassCombo = aWidgetFactory.createCCombo(anomalyDetailsSectionClient, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fClassCombo.setToolTipText(R4EUIConstants.ANOMALY_CLASS_TOOLTIP);
		fClassCombo.setLayoutData(gridData);
		fClassCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						final R4ECommentType type = RModelFactoryExt.eINSTANCE.createR4ECommentType();
						type.setType(UIUtils.getClassFromString(fClassCombo.getText()));
						modelAnomaly.setType(type);
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
		addScrollListener(fClassCombo);

		//Rank
		final CLabel rankLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient, R4EUIConstants.RANK_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		rankLabel.setToolTipText(R4EUIConstants.ANOMALY_RANK_TOOLTIP);
		rankLabel.setLayoutData(gridData);

		fRankCombo = aWidgetFactory.createCCombo(anomalyDetailsSectionClient, SWT.READ_ONLY);
		fRankCombo.setToolTipText(R4EUIConstants.ANOMALY_RANK_TOOLTIP);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fRankCombo.setLayoutData(gridData);
		fRankCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setRank(UIUtils.getRankFromString(fRankCombo.getText()));
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
		addScrollListener(fRankCombo);

		//RuleId
		final CLabel ruleIdLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient,
				R4EUIConstants.RULE_ID_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		ruleIdLabel.setToolTipText(R4EUIConstants.ANOMALY_RULE_ID_TOOLTIP);
		ruleIdLabel.setLayoutData(gridData);

		final Composite ruleComposite = aWidgetFactory.createComposite(anomalyDetailsSectionClient);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		ruleComposite.setToolTipText(R4EUIConstants.ANOMALY_RULE_ID_TOOLTIP);
		ruleComposite.setLayoutData(gridData);
		ruleComposite.setLayout(new GridLayout(2, false));

		fRuleId = aWidgetFactory.createText(ruleComposite, "", SWT.READ_ONLY);
		fRuleId.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		fRuleId.setEditable(false);
		fRuleButton = aWidgetFactory.createButton(ruleComposite, R4EUIConstants.UPDATE_LABEL, SWT.NONE);
		fRuleButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		fRuleButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				//Modify anomaly
				R4EUIModelController.setJobInProgress(true);
				final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
				final AnomalyInputDialog dialog = new AnomalyInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
						getSite()
						.getWorkbenchWindow()
						.getShell());
				dialog.create();
				dialog.setTitle(modelAnomaly.getTitle());
				dialog.setDescription(modelAnomaly.getDescription());
				final int result = dialog.open();
				if (result == Window.OK) {
					if (null != dialog.getRuleReferenceValue()) {
						if (!fRefreshInProgress) {
							try {
								//Set new model data
								final String currentUser = R4EUIModelController.getReviewer();
								final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly,
										currentUser);
								final R4EDesignRule rule = dialog.getRuleReferenceValue().getRule();
								modelAnomaly.setTitle(dialog.getAnomalyTitleValue());
								modelAnomaly.setDescription(dialog.getAnomalyDescriptionValue());
								final R4ECommentType commentType = RModelFactory.eINSTANCE.createR4ECommentType();
								commentType.setType(rule.getClass_());
								modelAnomaly.setType(commentType);
								modelAnomaly.setRank(rule.getRank());
								modelAnomaly.setRuleID(rule.getId());
								R4EUIModelController.FResourceUpdater.checkIn(bookNum);

								//Set new UI display
								if (fProperties.getElement() instanceof R4EUIAnomalyExtended) {
									fProperties.getElement().setName(
											R4EUIAnomalyExtended.buildAnomalyExtName(modelAnomaly,
													((R4EUIAnomalyBasic) fProperties.getElement()).getPosition()));
								} else {
									fProperties.getElement().setName(
											R4EUIAnomalyBasic.buildAnomalyName(modelAnomaly,
													((R4EUIAnomalyBasic) fProperties.getElement()).getPosition()));
								}
								fProperties.getElement()
										.setToolTip(R4EUIAnomalyBasic.buildAnomalyToolTip(modelAnomaly));

								//If this is a postponed anomaly, update original one as well
								if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
									((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
								}
							} catch (ResourceHandlingException e1) {
								UIUtils.displayResourceErrorDialog(e1);
							} catch (OutOfSyncException e1) {
								UIUtils.displaySyncErrorDialog(e1);
							}
						}
						refresh();
					}
				} else if (result != Window.CANCEL) {
					R4EUIModelController.setJobInProgress(false); //Enable commands in case of error
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// No implementation needed
			}
		});

		//Due Date
		final CLabel dateLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient, R4EUIConstants.DUE_DATE_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		dateLabel.setLayoutData(gridData);

		final Composite dateComposite = aWidgetFactory.createComposite(anomalyDetailsSectionClient);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		dateComposite.setToolTipText(R4EUIConstants.ANOMALY_DUE_DATE_TOOLTIP);
		dateComposite.setLayoutData(gridData);
		dateComposite.setLayout(new GridLayout(2, false));

		fDateText = aWidgetFactory.createText(dateComposite, "", SWT.READ_ONLY);
		fDateText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		fDateText.setEditable(false);
		fCalendarButton = aWidgetFactory.createButton(dateComposite, "...", SWT.NONE);
		fCalendarButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		fCalendarButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				final CalendarDialog dialog = new CalendarDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
						getSite()
						.getWorkbenchWindow()
						.getShell(), false);
				final int result = dialog.open();
				if (result == Window.OK) {
					final SimpleDateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.SIMPLE_DATE_FORMAT);
					fDateText.setText(dateFormat.format(dialog.getDate()));
					if (!fRefreshInProgress) {
						try {
							final String currentUser = R4EUIModelController.getReviewer();
							final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
							final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly,
									currentUser);
							modelAnomaly.setDueDate(dialog.getDate());
							R4EUIModelController.FResourceUpdater.checkIn(bookNum);

							//If this is a postponed anomaly, update original one as well
							if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
								((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
							}
						} catch (ResourceHandlingException e1) {
							UIUtils.displayResourceErrorDialog(e1);
						} catch (OutOfSyncException e1) {
							UIUtils.displaySyncErrorDialog(e1);
						}
					}
					refresh();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				// No implementation needed
			}
		});

		//Not accepted reason
		final CLabel notAcceptedReasonLabel = aWidgetFactory.createCLabel(anomalyDetailsSectionClient,
				R4EUIConstants.NOT_ACCEPTED_REASON_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		notAcceptedReasonLabel.setToolTipText(R4EUIConstants.ANOMALY_NOT_ACCEPTED_REASON_TOOLTIP);
		notAcceptedReasonLabel.setLayoutData(gridData);

		fNotAcceptedReasonText = aWidgetFactory.createText(anomalyDetailsSectionClient, "", SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fNotAcceptedReasonText.setToolTipText(R4EUIConstants.ANOMALY_NOT_ACCEPTED_REASON_TOOLTIP);
		fNotAcceptedReasonText.setLayoutData(gridData);
		fNotAcceptedReasonText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setNotAcceptedReason(fNotAcceptedReasonText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void focusGained(FocusEvent e) { // $codepro.audit.disable emptyMethod
				//Nothing to do
			}
		});
		UIUtils.addTabbedPropertiesTextResizeListener(fNotAcceptedReasonText);

		return anomalyDetailsSection;
	}

	/**
	 * Method createParticipantDetailsSection.
	 * 
	 * @param aWidgetFactory
	 *            TabbedPropertySheetWidgetFactory
	 * @param aComposite
	 *            Composite
	 * @param aTopComposite
	 *            Composite
	 */
	private void createParticipantDetailsSection(TabbedPropertySheetWidgetFactory aWidgetFactory,
			final Composite aComposite, final Composite aTopComposite) {
		//Participants Details section
		final ExpandableComposite participantDetailsSection = aWidgetFactory.createExpandableComposite(aComposite,
				ExpandableComposite.TWISTIE);
		final FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(aTopComposite, ITabbedPropertyConstants.VSPACE);
		participantDetailsSection.setLayoutData(data);
		participantDetailsSection.setText(PARTICIPANT_DETAILS_SECTION_LABEL);
		participantDetailsSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				final ScrolledComposite scrolledParent = (ScrolledComposite) aComposite.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent();
				scrolledParent.setMinSize(aComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				scrolledParent.layout(true, true);
			}
		});
		participantDetailsSection.setLayout(new GridLayout(1, false));

		final Composite participantDetailsSectionClient = aWidgetFactory.createComposite(participantDetailsSection);
		participantDetailsSectionClient.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		participantDetailsSectionClient.setLayout(new GridLayout(4, false));
		participantDetailsSection.setClient(participantDetailsSectionClient);

		//Anomaly Author (read-only)
		final CLabel authorLabel = aWidgetFactory.createCLabel(participantDetailsSectionClient,
				R4EUIConstants.AUTHOR_LABEL);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		authorLabel.setToolTipText(R4EUIConstants.ANOMALY_AUTHOR_TOOLTIP);
		authorLabel.setLayoutData(gridData);

		fAuthorText = aWidgetFactory.createCLabel(participantDetailsSectionClient, "");
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fAuthorText.setToolTipText(R4EUIConstants.ANOMALY_AUTHOR_TOOLTIP);
		fAuthorText.setLayoutData(gridData);

		//Decided by
		final CLabel decidedByLabel = aWidgetFactory.createCLabel(participantDetailsSectionClient,
				R4EUIConstants.DECIDED_BY_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		decidedByLabel.setToolTipText(R4EUIConstants.ANOMALY_DECIDED_BY_TOOLTIP);
		decidedByLabel.setLayoutData(gridData);

		fDecidedByCombo = aWidgetFactory.createCCombo(participantDetailsSectionClient, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fDecidedByCombo.setToolTipText(R4EUIConstants.ANOMALY_DECIDED_BY_TOOLTIP);
		fDecidedByCombo.setLayoutData(gridData);
		fDecidedByCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setDecidedByID(fDecidedByCombo.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
		addScrollListener(fDecidedByCombo);

		//Fixed by
		final CLabel fixedByLabel = aWidgetFactory.createCLabel(participantDetailsSectionClient,
				R4EUIConstants.FIXED_BY_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		fixedByLabel.setToolTipText(R4EUIConstants.ANOMALY_FIXED_BY_TOOLTIP);
		fixedByLabel.setLayoutData(gridData);

		fFixedByCombo = aWidgetFactory.createCCombo(participantDetailsSectionClient, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fFixedByCombo.setToolTipText(R4EUIConstants.ANOMALY_FIXED_BY_TOOLTIP);
		fFixedByCombo.setLayoutData(gridData);
		fFixedByCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setFixedByID(fFixedByCombo.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
		addScrollListener(fFixedByCombo);

		//Folluw-up by
		final CLabel followUpByLabel = aWidgetFactory.createCLabel(participantDetailsSectionClient,
				R4EUIConstants.FOLLOWUP_BY_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		followUpByLabel.setToolTipText(R4EUIConstants.ANOMALY_FOLLOWUP_BY_TOOLTIP);
		followUpByLabel.setLayoutData(gridData);

		fFollowUpByCombo = aWidgetFactory.createCCombo(participantDetailsSectionClient, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fFollowUpByCombo.setToolTipText(R4EUIConstants.ANOMALY_FOLLOWUP_BY_TOOLTIP);
		fFollowUpByCombo.setLayoutData(gridData);
		fFollowUpByCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setFollowUpByID(fFollowUpByCombo.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//If this is a postponed anomaly, update original one as well
						if (fProperties.getElement() instanceof R4EUIPostponedAnomaly) {
							((R4EUIPostponedAnomaly) fProperties.getElement()).updateOriginalAnomaly();
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}

			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
		addScrollListener(fFollowUpByCombo);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EUIAnomalyBasic uiModelAnomaly = (R4EUIAnomalyBasic) fProperties.getElement();
		final R4EAnomaly modelAnomaly = uiModelAnomaly.getAnomaly();
		fTitleText.setText(modelAnomaly.getTitle());
		fAuthorText.setText(modelAnomaly.getUser().getId());
		fCreationDateText.setText(modelAnomaly.getCreatedOn().toString());
		fPosition = ((R4EUIAnomalyBasic) fProperties.getElement()).getPosition();
		if (null == fPosition) {
			fPositionText.setText(R4EUIConstants.GLOBAL_ANOMALY_PROPERTY_VALUE);
		} else {
			fPositionText.setText(fPosition.toString());
		}
		fDescriptionText.setText(modelAnomaly.getDescription());

		if (fProperties.getElement() instanceof R4EUIAnomalyExtended) {
			fStateCombo.setItems(((R4EUIAnomalyExtended) uiModelAnomaly).getAvailableStates());
			fStateCombo.select(((R4EUIAnomalyExtended) uiModelAnomaly).mapStateToIndex(modelAnomaly.getState()));

			fClassCombo.setItems(UIUtils.getClasses());
			if (null != modelAnomaly.getType() && null != ((R4ECommentType) modelAnomaly.getType()).getType()) {
				fClassCombo.select(((R4ECommentType) modelAnomaly.getType()).getType().getValue());
			} else {
				fClassCombo.setText("");
			}
			fRankCombo.setItems(UIUtils.getRanks());
			fRankCombo.select(modelAnomaly.getRank().getValue());
			if (null != modelAnomaly.getRuleID()) {
				fRuleId.setText(modelAnomaly.getRuleID());
			}
			if (null != modelAnomaly.getDueDate()) {
				final SimpleDateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.SIMPLE_DATE_FORMAT);
				fDateText.setText(dateFormat.format(modelAnomaly.getDueDate()));
			} else {
				fDateText.setText("");
			}
			if (null != R4EUIModelController.getActiveReview()) {
				final List<String> participants = R4EUIModelController.getActiveReview().getParticipantIDs();
				fDecidedByCombo.setItems(participants.toArray(new String[participants.size()]));
				fDecidedByCombo.select(UIUtils.mapParticipantToIndex(modelAnomaly.getDecidedByID()));
				fFixedByCombo.setItems(participants.toArray(new String[participants.size()]));
				fFixedByCombo.select(UIUtils.mapParticipantToIndex(modelAnomaly.getFixedByID()));
				fFollowUpByCombo.setItems(participants.toArray(new String[participants.size()]));
				fFollowUpByCombo.select(UIUtils.mapParticipantToIndex(modelAnomaly.getFollowUpByID()));
			}
			if (null != modelAnomaly.getNotAcceptedReason()) {
				fNotAcceptedReasonText.setText(modelAnomaly.getNotAcceptedReason());
			} else {
				fNotAcceptedReasonText.setText("");
			}
		}
		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEditableFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isJobInProgress()
				|| ((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED)) {
			fTitleText.setEnabled(false);
			fAuthorText.setEnabled(false);
			fCreationDateText.setEnabled(false);
			fPositionText.setEnabled(false);
			fDescriptionText.setEnabled(false);
			fStateCombo.setEnabled(false);
			fClassCombo.setEnabled(false);
			fRankCombo.setEnabled(false);
			fDateText.setEnabled(false);
			fCalendarButton.setEnabled(false);
			fDecidedByCombo.setEnabled(false);
			fFixedByCombo.setEnabled(false);
			fFollowUpByCombo.setEnabled(false);
			fNotAcceptedReasonText.setEnabled(false);
			fRuleButton.setEnabled(false);
			fRuleId.setEnabled(false);
		} else {
			fTitleText.setEnabled(true);
			fAuthorText.setEnabled(true);
			fCreationDateText.setEnabled(true);
			fPositionText.setEnabled(true);
			fDescriptionText.setEnabled(true);

			if (fProperties.getElement() instanceof R4EUIAnomalyExtended) {
				final R4EUIAnomalyExtended uiAnomaly = (R4EUIAnomalyExtended) fProperties.getElement();
				fStateCombo.setEnabled(true);
				fRuleButton.setEnabled(true);
				fRuleId.setEnabled(true);

				if (uiAnomaly.isClassEnabled()) {
					fClassCombo.setEnabled(true);
				} else {
					fClassCombo.setEnabled(false);
				}

				if (uiAnomaly.isRankEnabled()) {
					fRankCombo.setEnabled(true);
				} else {
					fRankCombo.setEnabled(false);
				}

				if (uiAnomaly.isDueDateEnabled()) {
					fDateText.setEnabled(true);
					fCalendarButton.setEnabled(true);
				} else {
					fDateText.setEnabled(false);
					fCalendarButton.setEnabled(false);
				}

				if (uiAnomaly.isDecidedByEnabled()) {
					fDecidedByCombo.setEnabled(true);
				} else {
					fDecidedByCombo.setEnabled(false);
				}

				if (uiAnomaly.isFixedByEnabled()) {
					fFixedByCombo.setEnabled(true);
				} else {
					fFixedByCombo.setEnabled(false);
				}

				if (uiAnomaly.isFollowUpByEnabled()) {
					fFollowUpByCombo.setEnabled(true);
				} else {
					fFollowUpByCombo.setEnabled(false);
				}

				if (uiAnomaly.isNotAcceptedReasonEnabled()) {
					fNotAcceptedReasonText.setEnabled(true);
					fNotAcceptedReasonText.setEditable(true);
				} else {
					fNotAcceptedReasonText.setEnabled(false);
				}
			} else {
				fStateCombo.setEnabled(false);
				fClassCombo.setEnabled(false);
				fRankCombo.setEnabled(false);
				fDateText.setEnabled(false);
				fCalendarButton.setEnabled(false);
				fDecidedByCombo.setEnabled(false);
				fFixedByCombo.setEnabled(false);
				fFollowUpByCombo.setEnabled(false);
				fNotAcceptedReasonText.setEnabled(false);
				fRuleButton.setEnabled(false);
				fRuleId.setEnabled(false);
			}
		}
	}
}
