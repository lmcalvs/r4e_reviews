/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.example.emftasks.query;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleConnector;
import org.eclipse.mylyn.internal.tasks.core.RepositoryQuery;
import org.eclipse.mylyn.reviews.connector.query.EmfQueryEngine;
import org.eclipse.mylyn.reviews.connector.query.QueryClause;
import org.eclipse.mylyn.reviews.connector.query.QueryException;
import org.eclipse.mylyn.reviews.connector.query.QueryOperation;
import org.eclipse.mylyn.reviews.example.emftasks.EmfBaseClientTest;
import org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage;
import org.eclipse.mylyn.reviews.example.emftasks.SimpleTask;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public class EmfQueryEngineTest extends EmfBaseClientTest {

	private EmfQueryEngine engine;

	/**
	 * (Example.ecore)
	 * 
	 * <pre>
	 * <tasks id="1" summary="Test Task 1" description="This is test task 1." priority="STANDARD" duedate="2012-10-28T00:00:00.000-0700" creationDate="2012-10-21T00:00:00.000-0700"/>
	 * <tasks id="2" summary="Test Task 2" description="This is test task 2." priority="IMPORTANT" duedate="2012-10-29T00:00:00.000-0700" creationDate="2012-10-22T00:00:00.000-0700"/>
	 * <tasks id="3" summary="Test Task 3" description="This is test task 3." priority="URGENT" duedate="2012-10-30T00:00:00.000-0700" creationDate="2012-10-23T00:00:00.000-0700"/>
	 * </pre>
	 **/

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		engine = connector.getQueryEngine(client.getRepository());
	}

	@Test
	public void testGetClauses() throws QueryException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");

			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "substring");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "foo");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "regexp");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "baz");
			QueryClause[] clauses = engine.getClauses(query);
			assertThat(clauses.length, is(2));
			QueryClause cFoo = clauses[0];
			QueryClause cBaz = clauses[1];
			if (cFoo.getValue().equals("baz")) {
				assertThat(clauses[1].getValue(), is("foo"));
				cFoo = clauses[1];
				cBaz = clauses[0];
			}
			assertThat(cFoo.getFeature(), instanceOf(EAttribute.class));
			assertThat((EAttribute) cFoo.getFeature(), is(EmfTasksPackage.Literals.SIMPLE_TASK__SUMMARY));
			assertThat(cFoo.getOperation(), is(QueryOperation.SUBSTRING));
			assertThat(cFoo.getValue(), is("foo"));
			assertThat(cBaz.getFeature(), instanceOf(EAttribute.class));
			assertThat((EAttribute) cBaz.getFeature(), is(EmfTasksPackage.Literals.SIMPLE_TASK__DESCRIPTION));
			assertThat(cBaz.getOperation(), is(QueryOperation.REGEXP));
			assertThat(cBaz.getValue(), is("baz"));
		}

		{
			//Out of order
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");

			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "regexp");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "foo");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "substring");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "baz");
			QueryClause[] clauses = engine.getClauses(query);
			assertThat(clauses.length, is(2));
			QueryClause cFoo = clauses[0];
			QueryClause cBaz = clauses[1];
			if (cFoo.getValue().equals("baz")) {
				assertThat(clauses[1].getValue(), is("foo"));
				cFoo = clauses[1];
				cBaz = clauses[0];
			}
			assertThat(cFoo.getFeature(), instanceOf(EAttribute.class));
			assertThat((EAttribute) cFoo.getFeature(), is(EmfTasksPackage.Literals.SIMPLE_TASK__SUMMARY));
			assertThat(cFoo.getOperation(), is(QueryOperation.SUBSTRING));
			assertThat(cFoo.getValue(), is("foo"));
			assertThat(cBaz.getFeature(), instanceOf(EAttribute.class));
			assertThat((EAttribute) cBaz.getFeature(), is(EmfTasksPackage.Literals.SIMPLE_TASK__DESCRIPTION));
			assertThat(cBaz.getOperation(), is(QueryOperation.REGEXP));
			assertThat(cBaz.getValue(), is("baz"));
		}
	}

	@Test
	public void testPerformQuery() throws QueryException, CoreException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Test Task 2");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
			assertThat(results.get(0), instanceOf(SimpleTask.class));
			assertThat(((SimpleTask) results.get(0)).getId(), is(2));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "substring");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Task");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
			assertThat(results.get(0), instanceOf(SimpleTask.class));
		}
		{

			assertTrue("this has 3".matches(".*3"));

			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "regexp");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", ".*\\s\\d\\.");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
			assertThat(results.get(0), instanceOf(SimpleTask.class));
		}
	}

	@Test
	public void testPerformQueryMultipleClauses() throws QueryException, CoreException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Test Task 2");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Test Task 2");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "Nope");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "This is test task 2.");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "nope");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}

		//Queries must match all results
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Test Task 2");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "This is test task 2.");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "This is test task 2.");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Test Task 2");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}

	}

	@Test
	public void testPerformQueryNoClauses() throws QueryException, CoreException {
		//When no queries present we simply return all (this might change)
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
		}
	}

	@Test
	public void testPerformQueryEmptyValueClauses() throws QueryException, CoreException {
		//When no queries present we simply return all (this might change)
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
		}
	}

	@Test
	public void testPerformMultipleWithEmptyClause() throws QueryException, CoreException {
		//When no queries present we simply return all (this might change)
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.summary_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.summary_query.value", "Test Task 2");
			query.setAttribute("emf.emftasks.simpletask.description_query.operation", "exact");
			query.setAttribute("emf.emftasks.simpletask.description_query.value", "");

			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
	}

	@Test
	public void testPerformListAny() throws QueryException, CoreException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.priority_query.operation", "anyitems");
			query.setAttribute("emf.emftasks.simpletask.priority_query.value", "");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.priority_query.operation", "anyitems");
			query.setAttribute("emf.emftasks.simpletask.priority_query.value", "foo,bar");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.priority_query.operation", "anyitems");
			query.setAttribute("emf.emftasks.simpletask.priority_query.value", "Standard");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.priority_query.operation", "anyitems");
			query.setAttribute("emf.emftasks.simpletask.priority_query.value", "Standard,Important");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(2));
		}
	}

	@Test
	public void testPerformDateBefore() throws QueryException, CoreException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "before");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-09-28T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "before");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-10-28T00:23:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
			EObject result = results.get(0);
			assertThat(result, instanceOf(SimpleTask.class));
			SimpleTask taskResult = (SimpleTask) result;
			Calendar testDate = Calendar.getInstance();
			testDate.set(2012, 9, 28, 23, 0, 0);
			assertTrue(taskResult.getDueDate().before(testDate.getTime()));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "before");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-10-28T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "before");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-11-28T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
		}
	}

	@Test
	public void testPerformDateAfter() throws QueryException, CoreException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "after");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-10-30T23:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "after");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-10-29T00:23:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
			EObject result = results.get(0);
			assertThat(result, instanceOf(SimpleTask.class));
			SimpleTask taskResult = (SimpleTask) result;
			Calendar testDate = Calendar.getInstance();
			testDate.set(2012, 9, 29, 23, 0, 0);
			assertTrue(taskResult.getDueDate().after(testDate.getTime()));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "after");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-10-30T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "after");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "2012-09-28T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
		}
	}

	@Test
	public void testPerformDateInRange() throws QueryException, CoreException {
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "inrange");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value", "");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(3));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "inrange");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value",
					"2012-09-28T00:00:00.000-0700,2012-09-30T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "inrange");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value",
					"2013-09-28T00:00:00.000-0700,2013-09-30T00:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(0));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "inrange");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value",
					"2012-10-28T23:00:00.000-0700,2012-10-29T23:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
		{
			IRepositoryQuery query = new RepositoryQuery(EmfExampleConnector.CONNECTOR_KIND, "1");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.operation", "inrange");
			query.setAttribute("emf.emftasks.simpletask.duedate_query.value",
					"2012-10-30T00:00:00.000-0700,2062-10-29T23:00:00.000-0700");
			List<EObject> results = engine.performQuery(query, new NullProgressMonitor());
			assertThat(results.size(), is(1));
		}
	}
}
