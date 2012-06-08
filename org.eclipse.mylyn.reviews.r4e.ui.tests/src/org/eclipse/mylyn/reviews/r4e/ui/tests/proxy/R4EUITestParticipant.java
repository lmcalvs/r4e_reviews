/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Proxy class used to access/control the UI Participant element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IParticipantInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ParticipantInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipantContainer;

@SuppressWarnings("restriction")
public class R4EUITestParticipant extends R4EUITestElement {

	public R4EUITestParticipant(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Method createParticipant.
	 * 
	 * @param aContainer
	 * @param aId
	 * @param aEmail
	 * @param aRoles
	 * @param aFocusArea
	 * @return R4EUIParticipant
	 */
	public R4EUIParticipant createParticipant(R4EUIParticipantContainer aContainer, List<R4EParticipant> aParticipants) {

		//Inject mockup dialog for New Participant
		IParticipantInputDialog mockParticipantDialog = mock(ParticipantInputDialog.class);
		R4EUIDialogFactory.getInstance().setParticipantInputDialog(mockParticipantDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		when(mockParticipantDialog.getParticipants()).thenReturn(aParticipants);
		when(mockParticipantDialog.open()).thenReturn(Window.OK);

		return (R4EUIParticipant) fParentProxy.getCommandProxy().runNewChildElement(aContainer);
	}

}
