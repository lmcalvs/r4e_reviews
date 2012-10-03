/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ericsson AB - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * This class implements a dialog that shows the UI legend dialog NOTE: This is adapted from Mylyn UiLegendDialog EPL
 * class (c) Tasktop 2004, 2008
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUILegendDialog extends PopupDialog {

	/**
	 * Field CLOSE_ICON_FILE. (value is ""icons/view16/notclose_misc.gif"")
	 */
	private static final String CLOSE_ICON_FILE = "icons/view16/notclose_misc.gif";

	/**
	 * Field toolkit.
	 */
	private FormToolkit toolkit;

	/**
	 * Field form.
	 */
	private ScrolledForm form;

	/**
	 * Field content.
	 */
	private R4EUILegendControl content;

	/**
	 * Constructor for R4EUILegendDialog.
	 * 
	 * @param parent
	 *            Shell
	 */
	public R4EUILegendDialog(Shell parent) {
		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE | SWT.ON_TOP, false, false, false, false, false, null, null);
	}

	/**
	 * Method createContents.
	 * 
	 * @param parent
	 *            Composite
	 * @return Control
	 */
	@Override
	protected Control createContents(Composite parent) {
		getShell().setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		return createDialogArea(parent);
	}

	/**
	 * Method open.
	 * 
	 * @return int
	 */
	@Override
	public int open() {
		final int open = super.open();
//		getShell().setLocation(getShell().getLocations().x, getShell().getLocations().y+20);
		getShell().setFocus();
		return open;
	}

	/**
	 * Method close.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean close() {
		if (null != toolkit) {
			toolkit.dispose();
		}
		return super.close();
	}

	/**
	 * Method createDialogArea.
	 * 
	 * @param parent
	 *            Composite
	 * @return Control
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		toolkit = new FormToolkit(R4EUIPlugin.getDefault().getFormColors(parent.getDisplay()));

		form = toolkit.createScrolledForm(parent);
		form.setText("R4E UI Legend");
		form.getToolBarManager().add(new CloseDialogAction());
		form.getToolBarManager().update(true);
		form.getBody().setLayout(new TableWrapLayout());
		toolkit.decorateFormHeading(form.getForm());

		content = new R4EUILegendControl(form.getBody(), toolkit);
		content.setWindow(this);

		return parent;
	}

	/**
	 * @author Sebastien Dubois
	 */
	private class CloseDialogAction extends Action {

		/**
		 * Constructor for CloseDialogAction.
		 */
		private CloseDialogAction() {
			setImageDescriptor(ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
					.getBundle()
					.getEntry(CLOSE_ICON_FILE)));
			setText("Close");
		}

		/**
		 * Method run.
		 * 
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		@Override
		public void run() {
			close();
		}

	}
}
