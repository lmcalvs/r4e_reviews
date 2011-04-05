/**
 * Copyright (c) 2010 Ericsson
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * Alvaro Sanchez-Leon  - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.team.core.history.IFileRevision;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RModelPackageImpl extends EPackageImpl implements RModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EReviewGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EReviewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EAnomalyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EFormalReviewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4ETextPositionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EReviewDecisionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EUserEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EParticipantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4ETextContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EIDEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EAnomalyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4ETaskReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EReviewStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4ECommentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EReviewComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EFileContextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4ECommentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapToAnomalyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EPositionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EFileVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapNameToReviewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapToUsersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EUserReviewsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EIDComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapIDToComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapUserIDToUserReviewsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EAnomalyTextPositionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapDateToDurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapKeyToInfoAttributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EReviewPhaseInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EAnomalyStateEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EReviewPhaseEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EUserRoleEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EDecisionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EReviewTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EContextTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType mylynTaskEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iResourceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType uriEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iFileRevisionEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RModelPackageImpl() {
		super(eNS_URI, RModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link RModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RModelPackage init() {
		if (isInited) return (RModelPackage)EPackage.Registry.INSTANCE.getEPackage(RModelPackage.eNS_URI);

		// Obtain or create and register package
		RModelPackageImpl theRModelPackage = (RModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ModelPackage.eINSTANCE.eClass();
		DRModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRModelPackage.createPackageContents();

		// Initialize created meta-data
		theRModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RModelPackage.eNS_URI, theRModelPackage);
		return theRModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EReviewGroup() {
		return r4EReviewGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_Name() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_Folder() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_DefaultEntryCriteria() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_AvailableProjects() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_AvailableComponents() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_DesignRuleLocations() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewGroup_XmlVersion() {
		return (EAttribute)r4EReviewGroupEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReviewGroup_AvailableAnomalyTypes() {
		return (EReference)r4EReviewGroupEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReviewGroup_AnomalyTypeKeyToReference() {
		return (EReference)r4EReviewGroupEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReviewGroup_ReviewsMap() {
		return (EReference)r4EReviewGroupEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReviewGroup_UserReviews() {
		return (EReference)r4EReviewGroupEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EReview() {
		return r4EReviewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_Name() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_Project() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_Components() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_EntryCriteria() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_ExtraNotes() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_Objectives() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_ReferenceMaterial() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReview_Decision() {
		return (EReference)r4EReviewEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_StartDate() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_EndDate() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_XmlVersion() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReview_AnomalyTemplate() {
		return (EReference)r4EReviewEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReview_Type() {
		return (EAttribute)r4EReviewEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReview_UsersMap() {
		return (EReference)r4EReviewEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReview_IdsMap() {
		return (EReference)r4EReviewEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EReview_CreatedBy() {
		return (EReference)r4EReviewEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EAnomaly() {
		return r4EAnomalyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_State() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_UserAssigned() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_UserFollowUp() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_UserDecision() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_DueDate() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_Rank() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_Rule() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_DecidedBy() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_FixedBy() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_FollowupBy() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_NotAcceptedReason() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_IsImported() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomaly_FixedInVersion() {
		return (EReference)r4EAnomalyEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_RuleID() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_DecidedByID() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_FixedByID() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomaly_FollowUpByID() {
		return (EAttribute)r4EAnomalyEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EFormalReview() {
		return r4EFormalReviewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFormalReview_PhaseOwner() {
		return (EReference)r4EFormalReviewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFormalReview_Phases() {
		return (EReference)r4EFormalReviewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFormalReview_Current() {
		return (EReference)r4EFormalReviewEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4ETextPosition() {
		return r4ETextPositionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ETextPosition_StartPosition() {
		return (EAttribute)r4ETextPositionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ETextPosition_Length() {
		return (EAttribute)r4ETextPositionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ETextPosition_StartLine() {
		return (EAttribute)r4ETextPositionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ETextPosition_EndLine() {
		return (EAttribute)r4ETextPositionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EReviewDecision() {
		return r4EReviewDecisionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewDecision_SpentTime() {
		return (EAttribute)r4EReviewDecisionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewDecision_Value() {
		return (EAttribute)r4EReviewDecisionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EUser() {
		return r4EUserEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUser_GroupPaths() {
		return (EAttribute)r4EUserEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUser_SequenceIDCounter() {
		return (EAttribute)r4EUserEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EUser_AddedComments() {
		return (EReference)r4EUserEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EUser_AddedItems() {
		return (EReference)r4EUserEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUser_ReviewCreatedByMe() {
		return (EAttribute)r4EUserEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EUser_ReviewInstance() {
		return (EReference)r4EUserEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUser_XmlVersion() {
		return (EAttribute)r4EUserEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUser_ReviewCompleted() {
		return (EAttribute)r4EUserEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUser_ReviewCompletedCode() {
		return (EAttribute)r4EUserEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EParticipant() {
		return r4EParticipantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EParticipant_Roles() {
		return (EAttribute)r4EParticipantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EParticipant_FocusArea() {
		return (EAttribute)r4EParticipantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EParticipant_IsPartOfDecision() {
		return (EAttribute)r4EParticipantEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EParticipant_ReviewedContent() {
		return (EReference)r4EParticipantEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EParticipant_TimeLog() {
		return (EReference)r4EParticipantEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EItem() {
		return r4EItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_XmlVersion() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_Description() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_AddedById() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EItem_FileContextList() {
		return (EReference)r4EItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_RepositoryRef() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_ProjectURIs() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_AuthorRep() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EItem_Submitted() {
		return (EAttribute)r4EItemEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EItem_InfoAtt() {
		return (EReference)r4EItemEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4ETextContent() {
		return r4ETextContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ETextContent_Content() {
		return (EAttribute)r4ETextContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EID() {
		return r4EIDEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EID_SequenceID() {
		return (EAttribute)r4EIDEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EID_UserID() {
		return (EAttribute)r4EIDEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EAnomalyType() {
		return r4EAnomalyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EAnomalyType_Type() {
		return (EAttribute)r4EAnomalyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4ETaskReference() {
		return r4ETaskReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ETaskReference_Task() {
		return (EAttribute)r4ETaskReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EReviewState() {
		return r4EReviewStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewState_State() {
		return (EAttribute)r4EReviewStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EComment() {
		return r4ECommentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EComment_CreatedOn() {
		return (EAttribute)r4ECommentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EComment_Anomaly() {
		return (EReference)r4ECommentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EReviewComponent() {
		return r4EReviewComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EFileContext() {
		return r4EFileContextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFileContext_Deltas() {
		return (EReference)r4EFileContextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFileContext_Base() {
		return (EReference)r4EFileContextEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFileContext_Target() {
		return (EReference)r4EFileContextEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileContext_Type() {
		return (EAttribute)r4EFileContextEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFileContext_InfoAtt() {
		return (EReference)r4EFileContextEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EDelta() {
		return r4EDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EDelta_Base() {
		return (EReference)r4EDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EDelta_Target() {
		return (EReference)r4EDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4ECommentType() {
		return r4ECommentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4ECommentType_Type() {
		return (EAttribute)r4ECommentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapToAnomalyType() {
		return mapToAnomalyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapToAnomalyType_Key() {
		return (EAttribute)mapToAnomalyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapToAnomalyType_Value() {
		return (EReference)mapToAnomalyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EContent() {
		return r4EContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EContent_Location() {
		return (EReference)r4EContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EContent_Info() {
		return (EAttribute)r4EContentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EPosition() {
		return r4EPositionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EFileVersion() {
		return r4EFileVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_PlatformURI() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_VersionID() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_RepositoryPath() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_Name() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_Resource() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_LocalVersionID() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EFileVersion_FileRevision() {
		return (EAttribute)r4EFileVersionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EFileVersion_InfoAtt() {
		return (EReference)r4EFileVersionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapNameToReview() {
		return mapNameToReviewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapNameToReview_Key() {
		return (EAttribute)mapNameToReviewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapNameToReview_Value() {
		return (EReference)mapNameToReviewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapToUsers() {
		return mapToUsersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapToUsers_Key() {
		return (EAttribute)mapToUsersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapToUsers_Value() {
		return (EReference)mapToUsersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EUserReviews() {
		return r4EUserReviewsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUserReviews_Name() {
		return (EAttribute)r4EUserReviewsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EUserReviews_InvitedToMap() {
		return (EReference)r4EUserReviewsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EUserReviews_Group() {
		return (EReference)r4EUserReviewsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUserReviews_CreatedReviews() {
		return (EAttribute)r4EUserReviewsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EUserReviews_XmlVersion() {
		return (EAttribute)r4EUserReviewsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EIDComponent() {
		return r4EIDComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EIDComponent_Id() {
		return (EReference)r4EIDComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapIDToComponent() {
		return mapIDToComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapIDToComponent_Key() {
		return (EReference)mapIDToComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapIDToComponent_Value() {
		return (EReference)mapIDToComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapUserIDToUserReviews() {
		return mapUserIDToUserReviewsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapUserIDToUserReviews_Key() {
		return (EAttribute)mapUserIDToUserReviewsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapUserIDToUserReviews_Value() {
		return (EReference)mapUserIDToUserReviewsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EAnomalyTextPosition() {
		return r4EAnomalyTextPositionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EAnomalyTextPosition_File() {
		return (EReference)r4EAnomalyTextPositionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapDateToDuration() {
		return mapDateToDurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapDateToDuration_Key() {
		return (EAttribute)mapDateToDurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapDateToDuration_Value() {
		return (EAttribute)mapDateToDurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapKeyToInfoAttributes() {
		return mapKeyToInfoAttributesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapKeyToInfoAttributes_Key() {
		return (EAttribute)mapKeyToInfoAttributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMapKeyToInfoAttributes_Value() {
		return (EAttribute)mapKeyToInfoAttributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EReviewPhaseInfo() {
		return r4EReviewPhaseInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewPhaseInfo_EndDate() {
		return (EAttribute)r4EReviewPhaseInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewPhaseInfo_Type() {
		return (EAttribute)r4EReviewPhaseInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewPhaseInfo_PhaseOwnerID() {
		return (EAttribute)r4EReviewPhaseInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EReviewPhaseInfo_StartDate() {
		return (EAttribute)r4EReviewPhaseInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EAnomalyState() {
		return r4EAnomalyStateEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EReviewPhase() {
		return r4EReviewPhaseEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EUserRole() {
		return r4EUserRoleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EDecision() {
		return r4EDecisionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EReviewType() {
		return r4EReviewTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EContextType() {
		return r4EContextTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getMylynTask() {
		return mylynTaskEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIResource() {
		return iResourceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getURI() {
		return uriEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIFileRevision() {
		return iFileRevisionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RModelFactory getRModelFactory() {
		return (RModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		r4EReviewGroupEClass = createEClass(R4E_REVIEW_GROUP);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__NAME);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__FOLDER);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__AVAILABLE_PROJECTS);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS);
		createEAttribute(r4EReviewGroupEClass, R4E_REVIEW_GROUP__XML_VERSION);
		createEReference(r4EReviewGroupEClass, R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES);
		createEReference(r4EReviewGroupEClass, R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE);
		createEReference(r4EReviewGroupEClass, R4E_REVIEW_GROUP__REVIEWS_MAP);
		createEReference(r4EReviewGroupEClass, R4E_REVIEW_GROUP__USER_REVIEWS);

		r4EReviewEClass = createEClass(R4E_REVIEW);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__NAME);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__PROJECT);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__COMPONENTS);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__ENTRY_CRITERIA);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__EXTRA_NOTES);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__OBJECTIVES);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__REFERENCE_MATERIAL);
		createEReference(r4EReviewEClass, R4E_REVIEW__DECISION);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__START_DATE);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__END_DATE);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__XML_VERSION);
		createEReference(r4EReviewEClass, R4E_REVIEW__ANOMALY_TEMPLATE);
		createEAttribute(r4EReviewEClass, R4E_REVIEW__TYPE);
		createEReference(r4EReviewEClass, R4E_REVIEW__USERS_MAP);
		createEReference(r4EReviewEClass, R4E_REVIEW__CREATED_BY);
		createEReference(r4EReviewEClass, R4E_REVIEW__IDS_MAP);

		r4EAnomalyEClass = createEClass(R4E_ANOMALY);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__STATE);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__USER_ASSIGNED);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__USER_FOLLOW_UP);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__USER_DECISION);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__DUE_DATE);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__RANK);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__RULE);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__DECIDED_BY);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__FIXED_BY);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__FOLLOWUP_BY);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__NOT_ACCEPTED_REASON);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__IS_IMPORTED);
		createEReference(r4EAnomalyEClass, R4E_ANOMALY__FIXED_IN_VERSION);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__RULE_ID);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__DECIDED_BY_ID);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__FIXED_BY_ID);
		createEAttribute(r4EAnomalyEClass, R4E_ANOMALY__FOLLOW_UP_BY_ID);

		r4EFormalReviewEClass = createEClass(R4E_FORMAL_REVIEW);
		createEReference(r4EFormalReviewEClass, R4E_FORMAL_REVIEW__PHASE_OWNER);
		createEReference(r4EFormalReviewEClass, R4E_FORMAL_REVIEW__PHASES);
		createEReference(r4EFormalReviewEClass, R4E_FORMAL_REVIEW__CURRENT);

		r4ETextPositionEClass = createEClass(R4E_TEXT_POSITION);
		createEAttribute(r4ETextPositionEClass, R4E_TEXT_POSITION__START_POSITION);
		createEAttribute(r4ETextPositionEClass, R4E_TEXT_POSITION__LENGTH);
		createEAttribute(r4ETextPositionEClass, R4E_TEXT_POSITION__START_LINE);
		createEAttribute(r4ETextPositionEClass, R4E_TEXT_POSITION__END_LINE);

		r4EReviewDecisionEClass = createEClass(R4E_REVIEW_DECISION);
		createEAttribute(r4EReviewDecisionEClass, R4E_REVIEW_DECISION__SPENT_TIME);
		createEAttribute(r4EReviewDecisionEClass, R4E_REVIEW_DECISION__VALUE);

		r4EUserEClass = createEClass(R4E_USER);
		createEAttribute(r4EUserEClass, R4E_USER__GROUP_PATHS);
		createEAttribute(r4EUserEClass, R4E_USER__SEQUENCE_ID_COUNTER);
		createEReference(r4EUserEClass, R4E_USER__ADDED_COMMENTS);
		createEReference(r4EUserEClass, R4E_USER__ADDED_ITEMS);
		createEAttribute(r4EUserEClass, R4E_USER__REVIEW_CREATED_BY_ME);
		createEReference(r4EUserEClass, R4E_USER__REVIEW_INSTANCE);
		createEAttribute(r4EUserEClass, R4E_USER__XML_VERSION);
		createEAttribute(r4EUserEClass, R4E_USER__REVIEW_COMPLETED);
		createEAttribute(r4EUserEClass, R4E_USER__REVIEW_COMPLETED_CODE);

		r4EParticipantEClass = createEClass(R4E_PARTICIPANT);
		createEAttribute(r4EParticipantEClass, R4E_PARTICIPANT__ROLES);
		createEAttribute(r4EParticipantEClass, R4E_PARTICIPANT__FOCUS_AREA);
		createEAttribute(r4EParticipantEClass, R4E_PARTICIPANT__IS_PART_OF_DECISION);
		createEReference(r4EParticipantEClass, R4E_PARTICIPANT__REVIEWED_CONTENT);
		createEReference(r4EParticipantEClass, R4E_PARTICIPANT__TIME_LOG);

		r4EItemEClass = createEClass(R4E_ITEM);
		createEAttribute(r4EItemEClass, R4E_ITEM__XML_VERSION);
		createEAttribute(r4EItemEClass, R4E_ITEM__DESCRIPTION);
		createEAttribute(r4EItemEClass, R4E_ITEM__ADDED_BY_ID);
		createEReference(r4EItemEClass, R4E_ITEM__FILE_CONTEXT_LIST);
		createEAttribute(r4EItemEClass, R4E_ITEM__REPOSITORY_REF);
		createEAttribute(r4EItemEClass, R4E_ITEM__PROJECT_UR_IS);
		createEAttribute(r4EItemEClass, R4E_ITEM__AUTHOR_REP);
		createEAttribute(r4EItemEClass, R4E_ITEM__SUBMITTED);
		createEReference(r4EItemEClass, R4E_ITEM__INFO_ATT);

		r4ETextContentEClass = createEClass(R4E_TEXT_CONTENT);
		createEAttribute(r4ETextContentEClass, R4E_TEXT_CONTENT__CONTENT);

		r4EIDEClass = createEClass(R4EID);
		createEAttribute(r4EIDEClass, R4EID__SEQUENCE_ID);
		createEAttribute(r4EIDEClass, R4EID__USER_ID);

		r4EAnomalyTypeEClass = createEClass(R4E_ANOMALY_TYPE);
		createEAttribute(r4EAnomalyTypeEClass, R4E_ANOMALY_TYPE__TYPE);

		r4ETaskReferenceEClass = createEClass(R4E_TASK_REFERENCE);
		createEAttribute(r4ETaskReferenceEClass, R4E_TASK_REFERENCE__TASK);

		r4EReviewStateEClass = createEClass(R4E_REVIEW_STATE);
		createEAttribute(r4EReviewStateEClass, R4E_REVIEW_STATE__STATE);

		r4ECommentEClass = createEClass(R4E_COMMENT);
		createEAttribute(r4ECommentEClass, R4E_COMMENT__CREATED_ON);
		createEReference(r4ECommentEClass, R4E_COMMENT__ANOMALY);

		r4EReviewComponentEClass = createEClass(R4E_REVIEW_COMPONENT);

		r4EFileContextEClass = createEClass(R4E_FILE_CONTEXT);
		createEReference(r4EFileContextEClass, R4E_FILE_CONTEXT__DELTAS);
		createEReference(r4EFileContextEClass, R4E_FILE_CONTEXT__BASE);
		createEReference(r4EFileContextEClass, R4E_FILE_CONTEXT__TARGET);
		createEAttribute(r4EFileContextEClass, R4E_FILE_CONTEXT__TYPE);
		createEReference(r4EFileContextEClass, R4E_FILE_CONTEXT__INFO_ATT);

		r4EDeltaEClass = createEClass(R4E_DELTA);
		createEReference(r4EDeltaEClass, R4E_DELTA__BASE);
		createEReference(r4EDeltaEClass, R4E_DELTA__TARGET);

		r4ECommentTypeEClass = createEClass(R4E_COMMENT_TYPE);
		createEAttribute(r4ECommentTypeEClass, R4E_COMMENT_TYPE__TYPE);

		mapToAnomalyTypeEClass = createEClass(MAP_TO_ANOMALY_TYPE);
		createEAttribute(mapToAnomalyTypeEClass, MAP_TO_ANOMALY_TYPE__KEY);
		createEReference(mapToAnomalyTypeEClass, MAP_TO_ANOMALY_TYPE__VALUE);

		r4EContentEClass = createEClass(R4E_CONTENT);
		createEReference(r4EContentEClass, R4E_CONTENT__LOCATION);
		createEAttribute(r4EContentEClass, R4E_CONTENT__INFO);

		r4EPositionEClass = createEClass(R4E_POSITION);

		r4EFileVersionEClass = createEClass(R4E_FILE_VERSION);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__PLATFORM_URI);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__VERSION_ID);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__REPOSITORY_PATH);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__NAME);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__RESOURCE);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__LOCAL_VERSION_ID);
		createEAttribute(r4EFileVersionEClass, R4E_FILE_VERSION__FILE_REVISION);
		createEReference(r4EFileVersionEClass, R4E_FILE_VERSION__INFO_ATT);

		mapNameToReviewEClass = createEClass(MAP_NAME_TO_REVIEW);
		createEAttribute(mapNameToReviewEClass, MAP_NAME_TO_REVIEW__KEY);
		createEReference(mapNameToReviewEClass, MAP_NAME_TO_REVIEW__VALUE);

		mapToUsersEClass = createEClass(MAP_TO_USERS);
		createEAttribute(mapToUsersEClass, MAP_TO_USERS__KEY);
		createEReference(mapToUsersEClass, MAP_TO_USERS__VALUE);

		r4EUserReviewsEClass = createEClass(R4E_USER_REVIEWS);
		createEAttribute(r4EUserReviewsEClass, R4E_USER_REVIEWS__NAME);
		createEReference(r4EUserReviewsEClass, R4E_USER_REVIEWS__INVITED_TO_MAP);
		createEReference(r4EUserReviewsEClass, R4E_USER_REVIEWS__GROUP);
		createEAttribute(r4EUserReviewsEClass, R4E_USER_REVIEWS__CREATED_REVIEWS);
		createEAttribute(r4EUserReviewsEClass, R4E_USER_REVIEWS__XML_VERSION);

		r4EIDComponentEClass = createEClass(R4EID_COMPONENT);
		createEReference(r4EIDComponentEClass, R4EID_COMPONENT__ID);

		mapIDToComponentEClass = createEClass(MAP_ID_TO_COMPONENT);
		createEReference(mapIDToComponentEClass, MAP_ID_TO_COMPONENT__KEY);
		createEReference(mapIDToComponentEClass, MAP_ID_TO_COMPONENT__VALUE);

		mapUserIDToUserReviewsEClass = createEClass(MAP_USER_ID_TO_USER_REVIEWS);
		createEAttribute(mapUserIDToUserReviewsEClass, MAP_USER_ID_TO_USER_REVIEWS__KEY);
		createEReference(mapUserIDToUserReviewsEClass, MAP_USER_ID_TO_USER_REVIEWS__VALUE);

		r4EAnomalyTextPositionEClass = createEClass(R4E_ANOMALY_TEXT_POSITION);
		createEReference(r4EAnomalyTextPositionEClass, R4E_ANOMALY_TEXT_POSITION__FILE);

		mapDateToDurationEClass = createEClass(MAP_DATE_TO_DURATION);
		createEAttribute(mapDateToDurationEClass, MAP_DATE_TO_DURATION__KEY);
		createEAttribute(mapDateToDurationEClass, MAP_DATE_TO_DURATION__VALUE);

		mapKeyToInfoAttributesEClass = createEClass(MAP_KEY_TO_INFO_ATTRIBUTES);
		createEAttribute(mapKeyToInfoAttributesEClass, MAP_KEY_TO_INFO_ATTRIBUTES__KEY);
		createEAttribute(mapKeyToInfoAttributesEClass, MAP_KEY_TO_INFO_ATTRIBUTES__VALUE);

		r4EReviewPhaseInfoEClass = createEClass(R4E_REVIEW_PHASE_INFO);
		createEAttribute(r4EReviewPhaseInfoEClass, R4E_REVIEW_PHASE_INFO__END_DATE);
		createEAttribute(r4EReviewPhaseInfoEClass, R4E_REVIEW_PHASE_INFO__TYPE);
		createEAttribute(r4EReviewPhaseInfoEClass, R4E_REVIEW_PHASE_INFO__PHASE_OWNER_ID);
		createEAttribute(r4EReviewPhaseInfoEClass, R4E_REVIEW_PHASE_INFO__START_DATE);

		// Create enums
		r4EAnomalyStateEEnum = createEEnum(R4E_ANOMALY_STATE);
		r4EReviewPhaseEEnum = createEEnum(R4E_REVIEW_PHASE);
		r4EUserRoleEEnum = createEEnum(R4E_USER_ROLE);
		r4EDecisionEEnum = createEEnum(R4E_DECISION);
		r4EReviewTypeEEnum = createEEnum(R4E_REVIEW_TYPE);
		r4EContextTypeEEnum = createEEnum(R4E_CONTEXT_TYPE);

		// Create data types
		mylynTaskEDataType = createEDataType(MYLYN_TASK);
		iResourceEDataType = createEDataType(IRESOURCE);
		uriEDataType = createEDataType(URI);
		iFileRevisionEDataType = createEDataType(IFILE_REVISION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		DRModelPackage theDRModelPackage = (DRModelPackage)EPackage.Registry.INSTANCE.getEPackage(DRModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		r4EReviewGroupEClass.getESuperTypes().add(theModelPackage.getReviewGroup());
		r4EReviewGroupEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4EReviewEClass.getESuperTypes().add(theModelPackage.getReview());
		r4EReviewEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4EAnomalyEClass.getESuperTypes().add(theModelPackage.getTopic());
		r4EAnomalyEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4EAnomalyEClass.getESuperTypes().add(this.getR4EComment());
		r4EFormalReviewEClass.getESuperTypes().add(this.getR4EReview());
		r4ETextPositionEClass.getESuperTypes().add(this.getR4EPosition());
		r4EUserEClass.getESuperTypes().add(theModelPackage.getUser());
		r4EUserEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4EParticipantEClass.getESuperTypes().add(this.getR4EUser());
		r4EItemEClass.getESuperTypes().add(this.getR4EIDComponent());
		r4EItemEClass.getESuperTypes().add(theModelPackage.getItem());
		r4ETextContentEClass.getESuperTypes().add(this.getR4EContent());
		r4EAnomalyTypeEClass.getESuperTypes().add(theModelPackage.getCommentType());
		r4ETaskReferenceEClass.getESuperTypes().add(theModelPackage.getTaskReference());
		r4ETaskReferenceEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4EReviewStateEClass.getESuperTypes().add(theModelPackage.getReviewState());
		r4ECommentEClass.getESuperTypes().add(theModelPackage.getComment());
		r4ECommentEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4ECommentEClass.getESuperTypes().add(this.getR4EIDComponent());
		r4EReviewComponentEClass.getESuperTypes().add(theModelPackage.getReviewComponent());
		r4EFileContextEClass.getESuperTypes().add(this.getR4EIDComponent());
		r4EDeltaEClass.getESuperTypes().add(this.getR4EIDComponent());
		r4ECommentTypeEClass.getESuperTypes().add(theModelPackage.getCommentType());
		r4EContentEClass.getESuperTypes().add(theModelPackage.getLocation());
		r4EIDComponentEClass.getESuperTypes().add(this.getR4EReviewComponent());
		r4EAnomalyTextPositionEClass.getESuperTypes().add(this.getR4ETextPosition());

		// Initialize classes and features; add operations and parameters
		initEClass(r4EReviewGroupEClass, R4EReviewGroup.class, "R4EReviewGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EReviewGroup_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewGroup_Folder(), ecorePackage.getEString(), "folder", null, 0, 1, R4EReviewGroup.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewGroup_DefaultEntryCriteria(), ecorePackage.getEString(), "defaultEntryCriteria", null, 0, 1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewGroup_AvailableProjects(), ecorePackage.getEString(), "availableProjects", null, 0, -1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewGroup_AvailableComponents(), ecorePackage.getEString(), "availableComponents", null, 0, -1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewGroup_DesignRuleLocations(), ecorePackage.getEString(), "designRuleLocations", null, 0, -1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewGroup_XmlVersion(), ecorePackage.getEString(), "xmlVersion", "1.0.0", 0, 1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReviewGroup_AvailableAnomalyTypes(), this.getR4EAnomalyType(), null, "availableAnomalyTypes", null, 0, -1, R4EReviewGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReviewGroup_AnomalyTypeKeyToReference(), this.getMapToAnomalyType(), null, "anomalyTypeKeyToReference", null, 0, -1, R4EReviewGroup.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReviewGroup_ReviewsMap(), this.getMapNameToReview(), null, "reviewsMap", null, 0, -1, R4EReviewGroup.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReviewGroup_UserReviews(), this.getMapUserIDToUserReviews(), null, "userReviews", null, 0, -1, R4EReviewGroup.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(r4EReviewEClass, R4EReview.class, "R4EReview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EReview_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_Project(), ecorePackage.getEString(), "project", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_Components(), ecorePackage.getEString(), "components", null, 0, -1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_EntryCriteria(), ecorePackage.getEString(), "entryCriteria", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_ExtraNotes(), ecorePackage.getEString(), "extraNotes", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_Objectives(), ecorePackage.getEString(), "objectives", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_ReferenceMaterial(), ecorePackage.getEString(), "referenceMaterial", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReview_Decision(), this.getR4EReviewDecision(), null, "decision", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_StartDate(), ecorePackage.getEDate(), "startDate", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_EndDate(), ecorePackage.getEDate(), "endDate", null, 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_XmlVersion(), ecorePackage.getEString(), "xmlVersion", "1.0.0", 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReview_AnomalyTemplate(), this.getR4EAnomaly(), null, "anomalyTemplate", null, 1, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReview_Type(), this.getR4EReviewType(), "type", "", 0, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReview_UsersMap(), this.getMapToUsers(), null, "usersMap", null, 0, -1, R4EReview.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReview_CreatedBy(), this.getR4EUser(), null, "createdBy", null, 1, 1, R4EReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EReview_IdsMap(), this.getMapIDToComponent(), null, "idsMap", null, 0, -1, R4EReview.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(r4EAnomalyEClass, R4EAnomaly.class, "R4EAnomaly", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EAnomaly_State(), this.getR4EAnomalyState(), "state", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_UserAssigned(), theModelPackage.getUser(), null, "userAssigned", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_UserFollowUp(), theModelPackage.getUser(), null, "userFollowUp", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_UserDecision(), theModelPackage.getUser(), null, "userDecision", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_DueDate(), ecorePackage.getEDate(), "dueDate", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_Rank(), theDRModelPackage.getR4EDesignRuleRank(), "rank", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_Rule(), theDRModelPackage.getR4EDesignRule(), null, "rule", null, 0, 1, R4EAnomaly.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_DecidedBy(), this.getR4EParticipant(), null, "decidedBy", null, 0, 1, R4EAnomaly.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_FixedBy(), this.getR4EParticipant(), null, "fixedBy", null, 0, 1, R4EAnomaly.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_FollowupBy(), this.getR4EParticipant(), null, "followupBy", null, 0, 1, R4EAnomaly.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_NotAcceptedReason(), ecorePackage.getEString(), "notAcceptedReason", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_IsImported(), ecorePackage.getEBoolean(), "isImported", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EAnomaly_FixedInVersion(), this.getR4EFileVersion(), null, "fixedInVersion", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_RuleID(), ecorePackage.getEString(), "ruleID", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_DecidedByID(), ecorePackage.getEString(), "decidedByID", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_FixedByID(), ecorePackage.getEString(), "fixedByID", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EAnomaly_FollowUpByID(), ecorePackage.getEString(), "followUpByID", null, 0, 1, R4EAnomaly.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EFormalReviewEClass, R4EFormalReview.class, "R4EFormalReview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EFormalReview_PhaseOwner(), this.getR4EParticipant(), null, "phaseOwner", null, 1, 1, R4EFormalReview.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getR4EFormalReview_Phases(), this.getR4EReviewPhaseInfo(), null, "phases", null, 0, -1, R4EFormalReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EFormalReview_Current(), this.getR4EReviewPhaseInfo(), null, "current", null, 0, 1, R4EFormalReview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4ETextPositionEClass, R4ETextPosition.class, "R4ETextPosition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4ETextPosition_StartPosition(), ecorePackage.getEInt(), "startPosition", null, 0, 1, R4ETextPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4ETextPosition_Length(), ecorePackage.getEInt(), "length", null, 0, 1, R4ETextPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4ETextPosition_StartLine(), ecorePackage.getEInt(), "startLine", null, 0, 1, R4ETextPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4ETextPosition_EndLine(), ecorePackage.getEInt(), "endLine", null, 0, 1, R4ETextPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EReviewDecisionEClass, R4EReviewDecision.class, "R4EReviewDecision", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EReviewDecision_SpentTime(), ecorePackage.getEInt(), "spentTime", null, 0, 1, R4EReviewDecision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewDecision_Value(), this.getR4EDecision(), "value", null, 0, 1, R4EReviewDecision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EUserEClass, R4EUser.class, "R4EUser", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EUser_GroupPaths(), ecorePackage.getEString(), "groupPaths", null, 0, -1, R4EUser.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUser_SequenceIDCounter(), ecorePackage.getEInt(), "sequenceIDCounter", null, 0, 1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EUser_AddedComments(), this.getR4EComment(), null, "addedComments", null, 0, -1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EUser_AddedItems(), this.getR4EItem(), null, "addedItems", null, 0, -1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUser_ReviewCreatedByMe(), ecorePackage.getEBoolean(), "reviewCreatedByMe", null, 0, 1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EUser_ReviewInstance(), this.getR4EReview(), null, "reviewInstance", null, 1, 1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUser_XmlVersion(), ecorePackage.getEString(), "xmlVersion", "1.0.0", 0, 1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUser_ReviewCompleted(), ecorePackage.getEBoolean(), "reviewCompleted", null, 0, 1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUser_ReviewCompletedCode(), ecorePackage.getEInt(), "reviewCompletedCode", null, 0, 1, R4EUser.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EParticipantEClass, R4EParticipant.class, "R4EParticipant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EParticipant_Roles(), this.getR4EUserRole(), "roles", null, 0, -1, R4EParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EParticipant_FocusArea(), ecorePackage.getEString(), "focusArea", null, 0, 1, R4EParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EParticipant_IsPartOfDecision(), ecorePackage.getEBoolean(), "isPartOfDecision", null, 0, 1, R4EParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EParticipant_ReviewedContent(), this.getR4EID(), null, "reviewedContent", null, 0, -1, R4EParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EParticipant_TimeLog(), this.getMapDateToDuration(), null, "timeLog", null, 0, -1, R4EParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EItemEClass, R4EItem.class, "R4EItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EItem_XmlVersion(), ecorePackage.getEString(), "xmlVersion", "1.0.0", 0, 1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EItem_Description(), ecorePackage.getEString(), "description", null, 0, 1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EItem_AddedById(), ecorePackage.getEString(), "addedById", null, 0, 1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EItem_FileContextList(), this.getR4EFileContext(), null, "fileContextList", null, 0, -1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EItem_RepositoryRef(), ecorePackage.getEString(), "repositoryRef", null, 0, 1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EItem_ProjectURIs(), ecorePackage.getEString(), "ProjectURIs", null, 0, -1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EItem_AuthorRep(), ecorePackage.getEString(), "authorRep", null, 0, 1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EItem_Submitted(), ecorePackage.getEDate(), "submitted", null, 0, 1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EItem_InfoAtt(), this.getMapKeyToInfoAttributes(), null, "infoAtt", null, 0, -1, R4EItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4ETextContentEClass, R4ETextContent.class, "R4ETextContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4ETextContent_Content(), ecorePackage.getEString(), "content", null, 0, 1, R4ETextContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EIDEClass, org.eclipse.mylyn.reviews.r4e.core.model.R4EID.class, "R4EID", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EID_SequenceID(), ecorePackage.getEInt(), "sequenceID", null, 0, 1, org.eclipse.mylyn.reviews.r4e.core.model.R4EID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EID_UserID(), ecorePackage.getEString(), "userID", null, 0, 1, org.eclipse.mylyn.reviews.r4e.core.model.R4EID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EAnomalyTypeEClass, R4EAnomalyType.class, "R4EAnomalyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EAnomalyType_Type(), ecorePackage.getEString(), "type", null, 0, 1, R4EAnomalyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4ETaskReferenceEClass, R4ETaskReference.class, "R4ETaskReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4ETaskReference_Task(), this.getMylynTask(), "task", null, 0, 1, R4ETaskReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EReviewStateEClass, R4EReviewState.class, "R4EReviewState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EReviewState_State(), this.getR4EReviewPhase(), "state", null, 0, 1, R4EReviewState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4ECommentEClass, R4EComment.class, "R4EComment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EComment_CreatedOn(), ecorePackage.getEDate(), "createdOn", null, 0, 1, R4EComment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EComment_Anomaly(), this.getR4EAnomaly(), null, "anomaly", null, 1, 1, R4EComment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EReviewComponentEClass, R4EReviewComponent.class, "R4EReviewComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(r4EFileContextEClass, R4EFileContext.class, "R4EFileContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EFileContext_Deltas(), this.getR4EDelta(), null, "deltas", null, 0, -1, R4EFileContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EFileContext_Base(), this.getR4EFileVersion(), null, "base", null, 0, 1, R4EFileContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EFileContext_Target(), this.getR4EFileVersion(), null, "target", null, 1, 1, R4EFileContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileContext_Type(), this.getR4EContextType(), "type", null, 0, 1, R4EFileContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EFileContext_InfoAtt(), this.getMapKeyToInfoAttributes(), null, "infoAtt", null, 0, -1, R4EFileContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EDeltaEClass, R4EDelta.class, "R4EDelta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EDelta_Base(), this.getR4EContent(), null, "base", null, 0, 1, R4EDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EDelta_Target(), this.getR4EContent(), null, "target", null, 1, 1, R4EDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4ECommentTypeEClass, R4ECommentType.class, "R4ECommentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4ECommentType_Type(), theDRModelPackage.getR4EDesignRuleClass(), "type", null, 0, 1, R4ECommentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapToAnomalyTypeEClass, Map.Entry.class, "MapToAnomalyType", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapToAnomalyType_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapToAnomalyType_Value(), this.getR4EAnomalyType(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EContentEClass, R4EContent.class, "R4EContent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EContent_Location(), this.getR4EPosition(), null, "location", null, 1, 1, R4EContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EContent_Info(), ecorePackage.getEString(), "info", null, 0, 1, R4EContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EPositionEClass, R4EPosition.class, "R4EPosition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(r4EFileVersionEClass, R4EFileVersion.class, "R4EFileVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EFileVersion_PlatformURI(), ecorePackage.getEString(), "platformURI", null, 0, 1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileVersion_VersionID(), ecorePackage.getEString(), "versionID", null, 0, 1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileVersion_RepositoryPath(), ecorePackage.getEString(), "repositoryPath", null, 0, 1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileVersion_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileVersion_Resource(), this.getIResource(), "resource", null, 0, 1, R4EFileVersion.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileVersion_LocalVersionID(), ecorePackage.getEString(), "localVersionID", null, 0, 1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EFileVersion_FileRevision(), this.getIFileRevision(), "fileRevision", null, 0, 1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EFileVersion_InfoAtt(), this.getMapKeyToInfoAttributes(), null, "infoAtt", null, 0, -1, R4EFileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapNameToReviewEClass, Map.Entry.class, "MapNameToReview", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapNameToReview_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapNameToReview_Value(), this.getR4EReview(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapToUsersEClass, Map.Entry.class, "MapToUsers", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapToUsers_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapToUsers_Value(), this.getR4EUser(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EUserReviewsEClass, R4EUserReviews.class, "R4EUserReviews", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EUserReviews_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EUserReviews.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EUserReviews_InvitedToMap(), this.getMapNameToReview(), null, "invitedToMap", null, 0, -1, R4EUserReviews.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getR4EUserReviews_Group(), this.getR4EReviewGroup(), null, "group", null, 0, 1, R4EUserReviews.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUserReviews_CreatedReviews(), ecorePackage.getEString(), "createdReviews", null, 0, -1, R4EUserReviews.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EUserReviews_XmlVersion(), ecorePackage.getEString(), "xmlVersion", "1.0.0", 0, 1, R4EUserReviews.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EIDComponentEClass, R4EIDComponent.class, "R4EIDComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EIDComponent_Id(), this.getR4EID(), null, "id", null, 0, 1, R4EIDComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapIDToComponentEClass, Map.Entry.class, "MapIDToComponent", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMapIDToComponent_Key(), this.getR4EID(), null, "key", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapIDToComponent_Value(), this.getR4EIDComponent(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapUserIDToUserReviewsEClass, Map.Entry.class, "MapUserIDToUserReviews", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapUserIDToUserReviews_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapUserIDToUserReviews_Value(), this.getR4EUserReviews(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EAnomalyTextPositionEClass, R4EAnomalyTextPosition.class, "R4EAnomalyTextPosition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EAnomalyTextPosition_File(), this.getR4EFileVersion(), null, "file", null, 0, 1, R4EAnomalyTextPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapDateToDurationEClass, Map.Entry.class, "MapDateToDuration", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapDateToDuration_Key(), ecorePackage.getEDate(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapDateToDuration_Value(), ecorePackage.getEIntegerObject(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mapKeyToInfoAttributesEClass, Map.Entry.class, "MapKeyToInfoAttributes", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMapKeyToInfoAttributes_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMapKeyToInfoAttributes_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EReviewPhaseInfoEClass, R4EReviewPhaseInfo.class, "R4EReviewPhaseInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EReviewPhaseInfo_EndDate(), ecorePackage.getEDate(), "endDate", null, 0, 1, R4EReviewPhaseInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewPhaseInfo_Type(), this.getR4EReviewPhase(), "type", null, 0, 1, R4EReviewPhaseInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewPhaseInfo_PhaseOwnerID(), ecorePackage.getEString(), "phaseOwnerID", null, 0, 1, R4EReviewPhaseInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EReviewPhaseInfo_StartDate(), ecorePackage.getEDate(), "startDate", null, 0, 1, R4EReviewPhaseInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(r4EAnomalyStateEEnum, R4EAnomalyState.class, "R4EAnomalyState");
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_CREATED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_ACCEPTED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_FIXED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED);
		addEEnumLiteral(r4EAnomalyStateEEnum, R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED);

		initEEnum(r4EReviewPhaseEEnum, R4EReviewPhase.class, "R4EReviewPhase");
		addEEnumLiteral(r4EReviewPhaseEEnum, R4EReviewPhase.R4E_REVIEW_PHASE_STARTED);
		addEEnumLiteral(r4EReviewPhaseEEnum, R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION);
		addEEnumLiteral(r4EReviewPhaseEEnum, R4EReviewPhase.R4E_REVIEW_PHASE_DECISION);
		addEEnumLiteral(r4EReviewPhaseEEnum, R4EReviewPhase.R4E_REVIEW_PHASE_REWORK);
		addEEnumLiteral(r4EReviewPhaseEEnum, R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED);

		initEEnum(r4EUserRoleEEnum, R4EUserRole.class, "R4EUserRole");
		addEEnumLiteral(r4EUserRoleEEnum, R4EUserRole.R4E_ROLE_REVIEWER);
		addEEnumLiteral(r4EUserRoleEEnum, R4EUserRole.R4E_ROLE_LEAD);
		addEEnumLiteral(r4EUserRoleEEnum, R4EUserRole.R4E_ROLE_AUTHOR);
		addEEnumLiteral(r4EUserRoleEEnum, R4EUserRole.R4E_ROLE_ORGANIZER);

		initEEnum(r4EDecisionEEnum, R4EDecision.class, "R4EDecision");
		addEEnumLiteral(r4EDecisionEEnum, R4EDecision.R4E_REVIEW_DECISION_NONE);
		addEEnumLiteral(r4EDecisionEEnum, R4EDecision.R4E_REVIEW_DECISION_ACCEPTED);
		addEEnumLiteral(r4EDecisionEEnum, R4EDecision.R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP);
		addEEnumLiteral(r4EDecisionEEnum, R4EDecision.R4E_REVIEW_DECISION_REJECTED);

		initEEnum(r4EReviewTypeEEnum, R4EReviewType.class, "R4EReviewType");
		addEEnumLiteral(r4EReviewTypeEEnum, R4EReviewType.R4E_REVIEW_TYPE_BASIC);
		addEEnumLiteral(r4EReviewTypeEEnum, R4EReviewType.R4E_REVIEW_TYPE_INFORMAL);
		addEEnumLiteral(r4EReviewTypeEEnum, R4EReviewType.R4E_REVIEW_TYPE_FORMAL);

		initEEnum(r4EContextTypeEEnum, R4EContextType.class, "R4EContextType");
		addEEnumLiteral(r4EContextTypeEEnum, R4EContextType.R4E_UNDEFINED);
		addEEnumLiteral(r4EContextTypeEEnum, R4EContextType.R4E_ADDED);
		addEEnumLiteral(r4EContextTypeEEnum, R4EContextType.R4E_DELETED);
		addEEnumLiteral(r4EContextTypeEEnum, R4EContextType.R4E_MODIFIED);
		addEEnumLiteral(r4EContextTypeEEnum, R4EContextType.R4E_REPLACED);

		// Initialize data types
		initEDataType(mylynTaskEDataType, ITask.class, "MylynTask", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iResourceEDataType, IResource.class, "IResource", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(uriEDataType, org.eclipse.emf.common.util.URI.class, "URI", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iFileRevisionEDataType, IFileRevision.class, "IFileRevision", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (r4EReviewGroupEClass, 
		   source, 
		   new String[] {
			 "name", "R4EReviewGroup"
		   });
	}

} //RModelPackageImpl
