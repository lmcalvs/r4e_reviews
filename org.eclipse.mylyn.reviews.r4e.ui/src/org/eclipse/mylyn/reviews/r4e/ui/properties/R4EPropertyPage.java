// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the R4E properties page
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EPropertyPage extends PropertyPage {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field PATH_TITLE.
	 * (value is ""Path:"")
	 */
	private static final String PATH_TITLE = "Path:";
	
	/**
	 * Field OWNER_TITLE.
	 * (value is ""&Owner:"")
	 */
	private static final String OWNER_TITLE = "&Owner:";
	
	/**
	 * Field OWNER_PROPERTY.
	 * (value is ""OWNER"")
	 */
	private static final String OWNER_PROPERTY = "OWNER";
	
	/**
	 * Field DEFAULT_OWNER.
	 * (value is ""John Doe"")
	 */
	private static final String DEFAULT_OWNER = "John Doe";

	/**
	 * Field TEXT_FIELD_WIDTH.
	 * (value is 50)
	 */
	private static final int TEXT_FIELD_WIDTH = 50;

	/**
	 * Field PROPERTIES_NUM_COLUMNS.
	 * (value is 2)
	 */
	private static final int PROPERTIES_NUM_COLUMNS = 2;
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field ownerText.
	 */
	private Text ownerText;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public R4EPropertyPage() {
		super();
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method addFirstSection.
	 * @param parent Composite
	 */
	private void addFirstSection(Composite parent) {
		final Composite composite = createDefaultComposite(parent);

		//Label for path field
		final Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(PATH_TITLE);

		// Path text field
		final Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
		pathValueText.setText(((IResource) getElement()).getFullPath().toString());
	}

	/**
	 * Method addSeparator.
	 * @param parent Composite
	 */
	private void addSeparator(Composite parent) {
		final Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		final GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	/**
	 * Method addSecondSection.
	 * @param parent Composite
	 */
	private void addSecondSection(Composite parent) {
		final Composite composite = createDefaultComposite(parent);

		// Label for owner field
		final Label ownerLabel = new Label(composite, SWT.NONE);
		ownerLabel.setText(OWNER_TITLE);

		// Owner text field
		ownerText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		final GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		ownerText.setLayoutData(gd);

		// Populate owner text field
		try {
			final String owner = ((IResource) getElement()).getPersistentProperty(
					new QualifiedName("", OWNER_PROPERTY));
			ownerText.setText((null != owner) ? owner : DEFAULT_OWNER);
		} catch (CoreException e) {
			ownerText.setText(DEFAULT_OWNER);
			Activator.getDefault().logWarning("Exception: " + e.toString(), e);
		}
	}

	/**
	 * @param parent Composite
	 * @return Control
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		final GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		addSeparator(composite);
		addSecondSection(composite);
		return composite;
	}

	/**
	 * Method createDefaultComposite.
	 * @param parent Composite
	 * @return Composite
	 */
	private Composite createDefaultComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.numColumns = PROPERTIES_NUM_COLUMNS;
		composite.setLayout(layout);

		final GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	/**
	 * Method performDefaults.
	 */
	@Override
	protected void performDefaults() {
		super.performDefaults();
		// Populate the owner text field with the default value
		ownerText.setText(DEFAULT_OWNER);
	}

	/**
	 * Method performOk.
	 * @return boolean
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		// store the value in the owner text field
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", OWNER_PROPERTY),
				ownerText.getText());
		} catch (CoreException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			return false;
		}
		return true;
	}
}