/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial contribution
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;

public class GroupSampl {
	// ------------------------------------------------------------------------
	// constants
	// ------------------------------------------------------------------------
	private static String								_GROUP_ROOT;
	private final static String							_DEFAULT_ENTRY_CRITERIA		= "-Entry Criteria";
	private final static String							_VERSION					= "0.8.0";
	private final static String[]						_AVAILABLE_PROJECTS			= new String[] { "P1", "P2", "P3" };
	private final static String[]						_AVAILABLE_COMPONENTS		= new String[] { "Component1",
			"Component2", "Component3"												};
	private final static String[]						_DESIGN_RULE_LOCATIONS		= new String[] { _GROUP_ROOT };
	private final static String[]						_AVAILABLE_ANOMALY_TYPES	= new String[] { "Erroneous",
			"Superflous", "Improvement", "Question"								};

	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	// private R4EReviewGroup fgroup;
	// private final R4EWriter fWriter = SerializeFactory.getWriter();
	private static final Persistence.RModelFactoryExt	factory						= SerializeFactory
																							.getModelExtension();
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public static R4EReviewGroup createGroup(String aRoot, String aGroupName) {
		_GROUP_ROOT = aRoot;
		URI uri = URI.createFileURI(aRoot);

		R4EReviewGroup fgroup = null;
		// Group
		try {
			fgroup = factory.createR4EReviewGroup(uri, aGroupName);
			// checkout at Resourceset level during creation
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		// LOCAL ATTRIBUTES
		fgroup.setDescription("This is sample description - Group level");
		fgroup.setDefaultEntryCriteria(_DEFAULT_ENTRY_CRITERIA);
		// set available projects
		for (String project : _AVAILABLE_PROJECTS) {
			fgroup.getAvailableProjects().add(project);
		}

		for (String component : _AVAILABLE_COMPONENTS) {
			fgroup.getAvailableComponents().add(component);
		}

		for (String dRuleLoc : _DESIGN_RULE_LOCATIONS) {
			fgroup.getDesignRuleLocations().add(dRuleLoc);
		}

		fgroup.setFragmentVersion(_VERSION);

		// Mylyn Task
		R4ETaskReference task = RModelFactory.eINSTANCE.createR4ETaskReference();
		task.setRepositoryURL("https://bugs.eclipse.org/bugs/show_bug.cgi?id=324327");
		task.setTaskId("324327");
		fgroup.setReviewGroupTask(task);

		// LOCAL REFERENCES
		R4EAnomalyType anomalyTypeTemplate;
		R4EAnomalyType anomalyTypeReference;
		EMap<String, R4EAnomalyType> anomalyTypeMap = fgroup.getAnomalyTypeKeyToReference();
		for (String type : _AVAILABLE_ANOMALY_TYPES) {
			anomalyTypeTemplate = RModelFactoryExt.eINSTANCE.createR4EAnomalyType();
			anomalyTypeReference = RModelFactoryExt.eINSTANCE.createR4EAnomalyType();

			anomalyTypeTemplate.setType(type);
			anomalyTypeReference.setType(type);

			// for Serialization
			fgroup.getAvailableAnomalyTypes().add(anomalyTypeTemplate);

			// RWCommon reference used when creating anomalies
			anomalyTypeMap.put(type, anomalyTypeReference);
		}

		try {
			R4EReview review1 = factory.createR4EReview(fgroup, "Review1", "Alvaro Sanchez-Leon");
			Calendar indCal = new GregorianCalendar(1867, Calendar.JULY, 1);
			Date startDate = indCal.getTime();
			// 2 days after
			Date endDate = new Date(startDate.getTime() + 2 * 24 * 60 * 60 * 1000);
			review1.setStartDate(startDate);
			review1.setEndDate(endDate);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}
		return fgroup;
	}

}
