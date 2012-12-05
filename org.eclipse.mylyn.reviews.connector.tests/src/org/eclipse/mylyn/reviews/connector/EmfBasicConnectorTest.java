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

package org.eclipse.mylyn.reviews.connector;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.eclipse.mylyn.reviews.connector.support.EmfTestConnector;
import org.junit.Before;
import org.junit.Test;

/**
 * Excercises connector issues that don't require a client.
 * 
 * @author milesparker
 */
@SuppressWarnings("nls")
public class EmfBasicConnectorTest extends TestCase {

	static AbstractEmfConnector connector;

	@Override
	@Before
	public void setUp() {
		connector = new EmfTestConnector();
	}

	@Test
	public void testCanCreateNewTask() {
		assertTrue(connector.canCreateNewTask(null));
	}

	@Test
	public void testGetConnectorKind() {
		assertEquals("emftasks", connector.getConnectorKind());
	}

	@Test
	public void testGetRepositoryUrlFromTaskUrl() {
		assertNull(connector.getRepositoryUrlFromTaskUrl(null));
		assertNull(connector.getRepositoryUrlFromTaskUrl("")); //$NON-NLS-1$
		assertNull(connector.getRepositoryUrlFromTaskUrl("invalid repository url")); //$NON-NLS-1$
		assertNull(connector.getRepositoryUrlFromTaskUrl("http://invalid.repository.url")); //$NON-NLS-1$
		assertNull(connector.getRepositoryUrlFromTaskUrl("file://invalid.repository.url")); //$NON-NLS-1$
		assertNull(connector.getRepositoryUrlFromTaskUrl("platform:/resource/invalid.plugin/etc")); //$NON-NLS-1$
		assertNull(connector.getRepositoryUrlFromTaskUrl("platform:/resource/invalid.plugin/etc")); //$NON-NLS-1$
		assertEquals("file:///somewhere", connector.getRepositoryUrlFromTaskUrl("file:///somewhere#MyTest")); //$NON-NLS-1$
	}

	@Test
	public void testGetTaskIdFromTaskUrl() {
		assertNull(connector.getTaskIdFromTaskUrl(null));
		assertNull(connector.getTaskIdFromTaskUrl("")); //$NON-NLS-1$
		assertNull(connector.getTaskIdFromTaskUrl("invalid repository url")); //$NON-NLS-1$
		assertNull(connector.getTaskIdFromTaskUrl("http://invalid.repository.url")); //$NON-NLS-1$
		assertEquals("MyTest", connector.getTaskIdFromTaskUrl("file:///somewhere" + "#MyTest")); //$NON-NLS-1$
	}

	@Test
	public void testGetTaskUrl() {
		assertEquals("file:///somewhere" + "#MyTest", //$NON-NLS-1$
				connector.getTaskUrl("file:///somewhere", "MyTest")); //$NON-NLS-1$ 
	}

	@Test
	public void testGetTasKeyFromTaskId() {
		String enDashString = "This is a test \u2013 of hyphens.";
		String hyphenString = "This is a test - of hyphens.";
		assertThat(enDashString, is(not(hyphenString)));
		assertThat(enDashString, is(connector.encodeTaskKey(hyphenString)));
	}

	@Test
	/**
	 * Since emf ids can be arbitrary strings, we have to be careful to handle delimiters, spaces and other stray characters in the key itself
	 */
	public void testGetRepositoryUrlFromTaskUrl_UrlEncode() {
		assertEquals(
				"file:///somewhere", connector.getRepositoryUrlFromTaskUrl("file:///somewhere#MyTest 3 @ http://something.com")); //$NON-NLS-1$
		assertEquals(
				"file:///somewhere", connector.getRepositoryUrlFromTaskUrl("file:///somewhere#MyTest <breakme>\"broken\"</breakme>")); //$NON-NLS-1$
		assertEquals(
				"file:///somewhere", connector.getRepositoryUrlFromTaskUrl("file:///somewhere#MyTest &lt;breakme/&gt;&quot;broken&quot;&lt;breakme/&gt;")); //$NON-NLS-1$
	}

	@Test
	public void testGetTaskIdFromTaskUrl_UrlEncode() {
		assertEquals(
				"MyTest 3 @ http://something.com", connector.getTaskIdFromTaskUrl("file:///somewhere#MyTest 3 @ http://something.com")); //$NON-NLS-1$
		assertEquals(
				"MyTest <breakme>\"broken\"</breakme>", connector.getTaskIdFromTaskUrl("file:///somewhere#MyTest%20<breakme>\"broken\"</breakme>")); //$NON-NLS-1$
		assertEquals(
				"MyTest &lt;breakme/&gt;&quot;broken&quot;&lt;breakme/&gt;", connector.getTaskIdFromTaskUrl("file:///somewhere#MyTest &lt;breakme/&gt;&quot;broken&quot;&lt;breakme/&gt;")); //$NON-NLS-1$
	}

	@Test
	public void testGetTaskUrl_UrlEncode() {
		assertEquals("file:///somewhere#MyTest+3+%40+http%3A%2F%2Fsomething.com", //$NON-NLS-1$
				connector.getTaskUrl("file:///somewhere", "MyTest 3 @ http://something.com")); //$NON-NLS-1$ 
		assertEquals("file:///somewhere#MyTest+%3Cbreakme%3E%22broken%22%3C%2Fbreakme%3E", //$NON-NLS-1$
				connector.getTaskUrl("file:///somewhere", "MyTest <breakme>\"broken\"</breakme>")); //$NON-NLS-1$ 
		assertEquals(
				"file:///somewhere#MyTest+%26lt%3Bbreakme%2F%26gt%3B%26quot%3Bbroken%26quot%3B%26lt%3Bbreakme%2F%26gt%3B", //$NON-NLS-1$
				connector.getTaskUrl("file:///somewhere", "MyTest &lt;breakme/&gt;&quot;broken&quot;&lt;breakme/&gt;")); //$NON-NLS-1$ 
	}
}
