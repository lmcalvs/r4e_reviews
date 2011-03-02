package org.eclipse.mylyn.reviews.r4e.ui.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class AnomalyExtraProperties extends AnomalyGeneralProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	private static String[] stateValues = { "CREATED" };  //TODO rework
	private static String[] rankValues = { "MAJOR" };  //TODO rework
	private static String[] participantValues = { "" };  //TODO rework
	
	/**
	 * Field ANOMALY_STATE_ID. (value is ""anomalyElement.state"")
	 */
	private static final String ANOMALY_STATE_ID = "anomalyElement.state";

	/**
	 * Field ANOMALY_STATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_STATE_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_STATE_ID, R4EUIConstants.STATE_LABEL, stateValues);
	
	/**
	 * Field ANOMALY_DUE_DATE_ID. (value is ""anomalyElement.dueDate"")
	 */
	private static final String ANOMALY_DUE_DATE_ID = "anomalyElement.dueDate";

	/**
	 * Field ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_DUE_DATE_ID, R4EUIConstants.DUE_DATE_LABEL);
	
	/**
	 * Field ANOMALY_RANK_ID. (value is ""anomalyElement.rank"")
	 */
	private static final String ANOMALY_RANK_ID = "anomalyElement.rank";

	/**
	 * Field ANOMALY_RANK_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_RANK_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_RANK_ID, R4EUIConstants.RANK_LABEL, rankValues);
	
	/**
	 * Field ANOMALY_NOT_ACCEPTED_REASON_ID. (value is ""anomalyElement.notAcceptedReason"")
	 */
	private static final String ANOMALY_NOT_ACCEPTED_REASON_ID = "anomalyElement.notAcceptedReason";

	/**
	 * Field ANOMALY_NOT_ACCEPTED_REASON_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor ANOMALY_NOT_ACCEPTED_REASON_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			ANOMALY_NOT_ACCEPTED_REASON_ID, R4EUIConstants.NOT_ACCEPTED_REASON_LABEL);
	
	/**
	 * Field ANOMALY_DECIDED_BY_ID. (value is ""anomalyElement.decidedBy"")
	 */
	private static final String ANOMALY_DECIDED_BY_ID = "anomalyElement.decidedBy";

	/**
	 * Field ANOMALY_DECIDED_BY_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_DECIDED_BY_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_DECIDED_BY_ID, R4EUIConstants.DECIDED_BY_LABEL, participantValues);
	
	/**
	 * Field ANOMALY_FIXED_BY_ID. (value is ""anomalyElement.fixedBy"")
	 */
	private static final String ANOMALY_FIXED_BY_ID = "anomalyElement.fixedBy";

	/**
	 * Field ANOMALY_FIXED_BY_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_FIXED_BY_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_FIXED_BY_ID, R4EUIConstants.FIXED_BY_LABEL, participantValues);
	
	/**
	 * Field ANOMALY_FOLLOWUP_BY_ID. (value is ""anomalyElement.followupBy"")
	 */
	private static final String ANOMALY_FOLLOWUP_BY_ID = "anomalyElement.followupBy";

	/**
	 * Field ANOMALY_FOLLOWUP_BY_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_FOLLOWUP_BY_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_FOLLOWUP_BY_ID, R4EUIConstants.FOLLOWUP_BY_LABEL, participantValues);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { ANOMALY_TITLE_PROPERTY_DESCRIPTOR,
		ANOMALY_POSITION_PROPERTY_DESCRIPTOR, ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR,  
		ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR, ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR,
		ANOMALY_STATE_PROPERTY_DESCRIPTOR, ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR,
		ANOMALY_RANK_PROPERTY_DESCRIPTOR, ANOMALY_NOT_ACCEPTED_REASON_PROPERTY_DESCRIPTOR,
		ANOMALY_DECIDED_BY_PROPERTY_DESCRIPTOR, ANOMALY_FIXED_BY_PROPERTY_DESCRIPTOR,
		ANOMALY_FOLLOWUP_BY_PROPERTY_DESCRIPTOR};
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for AnomalyProperties.
	 * @param aElement R4EUIModelElement
	 */
	public AnomalyExtraProperties(R4EUIModelElement aElement) {
		super(aElement);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getPropertyDescriptors.
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}
	
	/**
	 * Method getPropertyValue.
	 * 
	 * @param aId Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		return null;
	}
	
	/**
	 * Method setPropertyValue.
	 * @param aId Object
	 * @param aValue Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	@Override
	public void setPropertyValue(Object aId, Object aValue) { // $codepro.audit.disable emptyMethod
		if (!(R4EUIModelController.isDialogOpen())) {

		}
	}
}
