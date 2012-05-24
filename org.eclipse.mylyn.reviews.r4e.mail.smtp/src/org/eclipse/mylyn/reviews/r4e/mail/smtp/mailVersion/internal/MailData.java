/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson AB - Mail data information structure
 * 
 */
package org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal;

/**
 * @author Jacques Bouthillier
 *
 * @version $Revision: 1.0 $
 */
public class MailData {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fSubject.
	 */
	private final String fSubject;
	/**
	 * Field fBody.
	 */
	private final String fBody;
	/**
	 * Field fSendTo.
	 */
	private final String[] fSendTo;
	/**
	 * Field fAttachment.
	 */
	private final String fAttachment;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for MailData.
	 * @param aSubject String
	 * @param aBody String
	 * @param aSendTo String[]
	 * @param aAttachment String
	 */
	public MailData(String aSubject, String aBody, String[] aSendTo, String aAttachment) {
		fSubject = aSubject;
		fBody = aBody;
		fSendTo = aSendTo;
		fAttachment = aAttachment;
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getSubject.
	 * @return String
	 */
	public String getSubject () {
		return fSubject;
	}
	
	/**
	 * Method getBody.
	 * @return String
	 */
	public String getBody () {
		return fBody;
	}
	
	/**
	 * Method getSendTo.
	 * @return String[]
	 */
	public String[] getSendTo () {
		return fSendTo;
	}
	
	/**
	 * Method getAttachment.
	 * @return String
	 */
	public String getAttachment () {
		return fAttachment;
	}
}
