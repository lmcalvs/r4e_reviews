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
 * This class implements a Factory that is used to create all R4E Input Dialogs
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import org.eclipse.core.resources.IProject;
import org.eclipse.mylyn.reviews.notifications.spi.NotificationsConnector;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.versions.ui.ScmUi;
import org.eclipse.mylyn.versions.ui.spi.ScmConnectorUi;
import org.eclipse.swt.widgets.Shell;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIDialogFactory {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field FInstance.
	 */
	private static R4EUIDialogFactory FInstance = null;

	/**
	 * Field fAnomalyInputDialog.
	 */
	IAnomalyInputDialog fAnomalyInputDialog = null;

	/**
	 * Field fCalendarDialog.
	 */
	ICalendarDialog fCalendarDialog = null;

	/**
	 * Field fChangeReviewStateDialog.
	 */
	IChangeStateDialog fChangeReviewStateDialog = null;

	/**
	 * Field fChangeAnomalyStateDialog.
	 */
	IChangeStateDialog fChangeAnomalyStateDialog = null;

	/**
	 * Field fCommentInputDialog.
	 */
	ICommentInputDialog fCommentInputDialog = null;

	/**
	 * Field fFindUserDialog.
	 */
	IFindUserDialog fFindUserDialog = null;

	/**
	 * Field fParticipantInputDialog.
	 */
	IParticipantInputDialog fParticipantInputDialog = null;

	/**
	 * Field fReviewGroupInputDialog.
	 */
	IReviewGroupInputDialog fReviewGroupInputDialog = null;

	/**
	 * Field fReviewInputDialog.
	 */
	IReviewInputDialog fReviewInputDialog = null;

	/**
	 * Field fRuleAreaInputDialog.
	 */
	IRuleAreaInputDialog fRuleAreaInputDialog = null;

	/**
	 * Field fRuleInputDialog.
	 */
	IRuleInputDialog fRuleInputDialog = null;

	/**
	 * Field fRuleSetInputDialog.
	 */
	IRuleSetInputDialog fRuleSetInputDialog = null;

	/**
	 * Field fRuleViolationInputDialog.
	 */
	IRuleViolationInputDialog fRuleViolationInputDialog = null;

	/**
	 * Field fSendNotificationInputDialog.
	 */
	ISendNotificationInputDialog fSendNotificationInputDialog = null;

	/**
	 * Field fScmConnectorUi.
	 */
	ScmConnectorUi fScmConnectorUi = null;

	/**
	 * Field fNotificationsConnector.
	 */
	static NotificationsConnector fNotificationsConnector = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIDialogFactory.
	 */
	private R4EUIDialogFactory() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getInstance.
	 * 
	 * @return R4EUIDialogFactory
	 */
	public static R4EUIDialogFactory getInstance() {
		if (null == FInstance) {
			FInstance = new R4EUIDialogFactory();
		}
		return FInstance;
	}

	/**
	 * Method createDialogs.
	 */
	public void createDialogs() {
		Shell shell = R4EUIModelController.getNavigatorView().getSite().getWorkbenchWindow().getShell();
		fAnomalyInputDialog = new AnomalyInputDialog(shell);
		fCalendarDialog = new CalendarDialog(shell, false);
		fChangeReviewStateDialog = new ChangeStateDialog(shell, R4EUIReviewBasic.class);
		fChangeAnomalyStateDialog = new ChangeStateDialog(shell, R4EUIAnomalyBasic.class);
		fCommentInputDialog = new CommentInputDialog(shell);
		fFindUserDialog = new FindUserDialog(shell);
		fParticipantInputDialog = new ParticipantInputDialog(shell);
		fReviewGroupInputDialog = new ReviewGroupInputDialog(shell);
		fReviewInputDialog = new ReviewInputDialog(shell);
		fRuleAreaInputDialog = new RuleAreaInputDialog(shell);
		fRuleInputDialog = new RuleInputDialog(shell);
		fRuleSetInputDialog = new RuleSetInputDialog(shell);
		fRuleViolationInputDialog = new RuleViolationInputDialog(shell);
	}

	/**
	 * Method createSendNotificationInputDialog.
	 * 
	 * @param aSource
	 *            Object
	 * @return ISendNotificationInputDialog
	 */
	public ISendNotificationInputDialog createSendNotificationInputDialog(Object aSource) {
		if (UIUtils.TEST_MODE == false) {
			fSendNotificationInputDialog = new SendNotificationInputDialog(R4EUIModelController.getNavigatorView()
					.getSite()
					.getWorkbenchWindow()
					.getShell(), aSource);
		}
		return fSendNotificationInputDialog;
	}

	/**
	 * Method getAnomalyInputDialog.
	 * 
	 * @return IAnomalyInputDialog
	 */
	public IAnomalyInputDialog getAnomalyInputDialog() {
		return fAnomalyInputDialog;
	}

	/**
	 * Method setAnomalyInputDialog.
	 * 
	 * @param aNewAnomalyInputDialog
	 *            IAnomalyInputDialog
	 */
	public void setAnomalyInputDialog(IAnomalyInputDialog aNewAnomalyInputDialog) {
		fAnomalyInputDialog = aNewAnomalyInputDialog;
	}

	/**
	 * Method getCalendarDialog.
	 * 
	 * @return ICalendarDialog
	 */
	public ICalendarDialog getCalendarDialog() {
		return fCalendarDialog;
	}

	/**
	 * Method setCalendarDialog.
	 * 
	 * @param aCalendarDialog
	 *            ICalendarDialog
	 */
	public void setCalendarDialog(ICalendarDialog aCalendarDialog) {
		fCalendarDialog = aCalendarDialog;
	}

	/**
	 * Method getChangeStateDialog.
	 * 
	 * @param aTargetClass
	 *            Class<?>
	 * @return IChangeStateDialog
	 */
	public IChangeStateDialog getChangeStateDialog(Class<?> aTargetClass) {
		if (aTargetClass.equals(R4EUIReviewBasic.class)) {
			return fChangeReviewStateDialog;
		}
		//Assume AnomalyChangeStateDialog
		return fChangeAnomalyStateDialog;
	}

	/**
	 * Method setChangeStateDialog.
	 * 
	 * @param aChangeStateDialog
	 *            IChangeStateDialog
	 * @param aTargetClass
	 *            Class<?>
	 */
	public void setChangeStateDialog(IChangeStateDialog aChangeStateDialog, Class<?> aTargetClass) {
		if (aTargetClass.equals(R4EUIReviewBasic.class)) {
			fChangeReviewStateDialog = aChangeStateDialog;
		}
		//Assume AnomalyChangeStateDialog
		fChangeAnomalyStateDialog = aChangeStateDialog;
	}

	/**
	 * Method getCommentInputDialog.
	 * 
	 * @return ICommentInputDialog
	 */
	public ICommentInputDialog getCommentInputDialog() {
		return fCommentInputDialog;
	}

	/**
	 * Method setCommentInputDialog.
	 * 
	 * @param aCommentInputDialog
	 *            ICommentInputDialog
	 */
	public void setCommentInputDialog(ICommentInputDialog aCommentInputDialog) {
		fCommentInputDialog = aCommentInputDialog;
	}

	/**
	 * Method getFindUserDialog.
	 * 
	 * @return IFindUserDialog
	 */
	public IFindUserDialog getFindUserDialog() {
		return fFindUserDialog;
	}

	/**
	 * Method setFindUserDialog.
	 * 
	 * @param aFindUserDialog
	 *            IFindUserDialog
	 */
	public void setFindUserDialog(IFindUserDialog aFindUserDialog) {
		fFindUserDialog = aFindUserDialog;
	}

	/**
	 * Method getParticipantInputDialog.
	 * 
	 * @return IParticipantInputDialog
	 */
	public IParticipantInputDialog getParticipantInputDialog() {
		return fParticipantInputDialog;
	}

	/**
	 * Method setParticipantInputDialog.
	 * 
	 * @param aParticipantInputDialog
	 *            IParticipantInputDialog
	 */
	public void setParticipantInputDialog(IParticipantInputDialog aParticipantInputDialog) {
		fParticipantInputDialog = aParticipantInputDialog;
	}

	/**
	 * Method getReviewGroupInputDialog.
	 * 
	 * @return IReviewGroupInputDialog
	 */
	public IReviewGroupInputDialog getReviewGroupInputDialog() {
		return fReviewGroupInputDialog;
	}

	/**
	 * Method setReviewGroupInputDialog.
	 * 
	 * @param aReviewGroupInputDialog
	 *            IReviewGroupInputDialog
	 */
	public void setReviewGroupInputDialog(IReviewGroupInputDialog aReviewGroupInputDialog) {
		fReviewGroupInputDialog = aReviewGroupInputDialog;
	}

	/**
	 * Method getReviewInputDialog.
	 * 
	 * @return IReviewInputDialog
	 */
	public IReviewInputDialog getReviewInputDialog() {
		return fReviewInputDialog;
	}

	/**
	 * Method setReviewInputDialog.
	 * 
	 * @param aReviewInputDialog
	 *            IReviewInputDialog
	 */
	public void setReviewInputDialog(IReviewInputDialog aReviewInputDialog) {
		fReviewInputDialog = aReviewInputDialog;
	}

	/**
	 * Method getRuleAreaInputDialog.
	 * 
	 * @return IRuleAreaInputDialog
	 */
	public IRuleAreaInputDialog getRuleAreaInputDialog() {
		return fRuleAreaInputDialog;
	}

	/**
	 * Method setRuleAreaInputDialog.
	 * 
	 * @param aRuleAreaInputDialog
	 *            IRuleAreaInputDialog
	 */
	public void setRuleAreaInputDialog(IRuleAreaInputDialog aRuleAreaInputDialog) {
		fRuleAreaInputDialog = aRuleAreaInputDialog;
	}

	/**
	 * Method getRuleInputDialog.
	 * 
	 * @return IRuleInputDialog
	 */
	public IRuleInputDialog getRuleInputDialog() {
		return fRuleInputDialog;
	}

	/**
	 * Method setRuleInputDialog.
	 * 
	 * @param aRuleInputDialog
	 *            IRuleInputDialog
	 */
	public void setRuleInputDialog(IRuleInputDialog aRuleInputDialog) {
		fRuleInputDialog = aRuleInputDialog;
	}

	/**
	 * Method getRuleSetInputDialog.
	 * 
	 * @return IRuleSetInputDialog
	 */
	public IRuleSetInputDialog getRuleSetInputDialog() {
		return fRuleSetInputDialog;
	}

	/**
	 * Method setRuleSetInputDialog.
	 * 
	 * @param aRuleSetInputDialog
	 *            IRuleSetInputDialog
	 */
	public void setRuleSetInputDialog(IRuleSetInputDialog aRuleSetInputDialog) {
		fRuleSetInputDialog = aRuleSetInputDialog;
	}

	/**
	 * Method getRuleViolationInputDialog.
	 * 
	 * @return IRuleViolationInputDialog
	 */
	public IRuleViolationInputDialog getRuleViolationInputDialog() {
		return fRuleViolationInputDialog;
	}

	/**
	 * Method setRuleViolationInputDialog.
	 * 
	 * @param aRuleViolationInputDialog
	 *            IRuleViolationInputDialog
	 */
	public void setRuleViolationInputDialog(IRuleViolationInputDialog aRuleViolationInputDialog) {
		fRuleViolationInputDialog = aRuleViolationInputDialog;
	}

	/**
	 * Method getSendNotificationInputDialog.
	 * 
	 * @return ISendNotificationInputDialog
	 */
	public ISendNotificationInputDialog getSendNotificationInputDialog() {
		return fSendNotificationInputDialog;
	}

	/**
	 * Method setSendNotificationInputDialog.
	 * 
	 * @param aSendNotificationInputDialog
	 *            ISendNotificationInputDialog
	 */
	public void setSendNotificationInputDialog(ISendNotificationInputDialog aSendNotificationInputDialog) {
		fSendNotificationInputDialog = aSendNotificationInputDialog;
	}

	/**
	 * Method setScmUIConnector.
	 * 
	 * @param aConnectorUi
	 *            ScmConnectorUi
	 */
	public void setScmUIConnector(ScmConnectorUi aConnectorUi) {
		fScmConnectorUi = aConnectorUi;
	}

	/**
	 * Method getScmUiConnector.
	 * 
	 * @return ScmConnectorUi
	 */
	public ScmConnectorUi getScmUiConnector(IProject aProject) {
		if (UIUtils.TEST_MODE == false) {
			return ScmUi.getUiConnector(aProject);
		} else {
			return fScmConnectorUi;
		}
	}

	/**
	 * Method setMailConnector.
	 * 
	 * @param aConnector
	 *            NotificationsConnector
	 */
	public void setMailConnector(NotificationsConnector aConnector) {
		fNotificationsConnector = aConnector;
	}

	/**
	 * Method getScmUiConnector.
	 * 
	 * @return ScmConnectorUi
	 */
	public static NotificationsConnector getMailConnector() {
		if (UIUtils.TEST_MODE == false) {
			return R4EUIModelController.getMailConnector();
		} else {
			return fNotificationsConnector;
		}
	}
}
