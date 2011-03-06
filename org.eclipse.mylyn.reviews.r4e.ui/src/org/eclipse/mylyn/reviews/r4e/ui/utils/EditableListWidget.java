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
 * This class implements an editable List-like widget 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class EditableListWidget {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field MAX_COLUMN_WIDTH.
	 * (value is 600)
	 */
	private static final int MAX_COLUMN_WIDTH = 600;
	
	/**
	 * Field MAX_COLUMN_HALF_WIDTH.
	 * (value is 300)
	 */
	private static final int MAX_COLUMN_HALF_WIDTH = 300;
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fMainComposite.
	 */
	private Composite fMainComposite = null;
	
	/**
	 * Field fMainTable.
	 */
	private Table fMainTable = null;
	
	/**
	 * Field fListener.
	 */
	private IEditableListListener fListener = null;
	
	/**
	 * Field fInstanceId.
	 */
	private int fInstanceId = 0;
	
	
	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------
	
	//TODO:  This should be made more generic to accept various tables.  Right now it is very hard-coded to our stuff...
	/**
	 * Constructor for EditableListWidget.
     * @param aToolkit - FormToolkit
     * @param aParent - Composite
     * @param aLayoutData - Object
     * @param aListener - IEditableListListener
     * @param aInstanceId - int
     * @param aEditableWidgetClass - Class<?>
     * @param aEditableValues - String[]
	 */
	public EditableListWidget(FormToolkit aToolkit, Composite aParent, Object aLayoutData, 
			IEditableListListener aListener, int aInstanceId, Class<?> aEditableWidgetClass, String[] aEditableValues) {
		fMainComposite = aToolkit.createComposite(aParent);
		fMainComposite.setLayoutData(aLayoutData);
		fMainComposite.setLayout(new GridLayout(4, false));
		fMainTable = aToolkit.createTable(fMainComposite, SWT.FULL_SELECTION);
        final GridData tableData = new GridData(GridData.FILL, GridData.FILL, true, true);
        if (aEditableWidgetClass.equals(Date.class)) {
        	tableData.horizontalSpan = 1;
        } else {
        	tableData.horizontalSpan = 2;
        }
		fMainTable.setLayoutData(tableData);
		fMainTable.setLinesVisible(true);
		fListener = aListener;
		fInstanceId = aInstanceId;
		createEditableListFromTable(aToolkit, fMainComposite, fMainTable, fListener, fInstanceId, aEditableWidgetClass,
				aEditableValues);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method dispose.
	 */
	public void dispose() {
		fMainComposite.dispose();
	}
	
    /**
     * Method createEditableListFromTable.
     * 		Builds the editable list in the provided table
     * @param aToolkit - FormToolkit
     * @param aParent - Composite
     * @param aTable - Table
     * @param aListener - IEditableListListener
     * @param aInstanceId - int
     * @param aEditableWidgetClass - Class<?>
     * @param aEditableValues - String[]
     */
	public static void createEditableListFromTable(FormToolkit aToolkit, Composite aParent, final Table aTable,
			final IEditableListListener aListener, final int aInstanceId, final Class<?> aEditableWidgetClass, 
			final String[] aEditableValues) {

		final TableColumn tableColumn = new TableColumn(aTable, SWT.LEFT, 0);
		final TableColumn tableColumn2 = new TableColumn(aTable, SWT.RIGHT, 1);
		
		if (aEditableWidgetClass.equals(Date.class)) {
			aTable.setHeaderVisible(true);
			tableColumn.setText(R4EUIConstants.SPENT_TIME_COLUMN_HEADER);
			tableColumn2.setText(R4EUIConstants.ENTRY_TIME_COLUMN_HEADER);
		}

        aTable.addControlListener(new ControlAdapter() { 
			@Override
			public void controlResized(ControlEvent e) {
				//TODO:  This is a hack so that the table is displayed somewhat correctly.  However, we need to have a better implementation later
				if (aEditableWidgetClass.equals(Date.class)) {
					tableColumn.setWidth((aTable.getClientArea().width > MAX_COLUMN_WIDTH) ? MAX_COLUMN_HALF_WIDTH : aTable.getClientArea().width >> 1);
					tableColumn2.setWidth((aTable.getClientArea().width > MAX_COLUMN_WIDTH) ? MAX_COLUMN_HALF_WIDTH : aTable.getClientArea().width >> 1);
				} else {
					tableColumn.setWidth((aTable.getClientArea().width > MAX_COLUMN_WIDTH) ? MAX_COLUMN_WIDTH : aTable.getClientArea().width);
				}
			}
		});
        
        aTable.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				//Send items updated notification
				if (null != aListener) {
					aListener.itemsUpdated(aTable.getItems(), aInstanceId);
				}
			}
			public void focusGained(FocusEvent e) {
				//Send items updated notification
				if (null != aListener) {
					aListener.itemsUpdated(aTable.getItems(), aInstanceId);
				}
			}
		});
	      
		final Composite buttonsComposite = aToolkit.createComposite(aParent);
		buttonsComposite.setLayout(new GridLayout());
		buttonsComposite.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

        final Button addButton = aToolkit.createButton(buttonsComposite, R4EUIConstants.BUTTON_ADD_LABEL, SWT.NONE);
        final Button removeButton = aToolkit.createButton(buttonsComposite, R4EUIConstants.BUTTON_REMOVE_LABEL, SWT.NONE);

        addButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				final TableItem newItem = new TableItem(aTable, SWT.NONE);
				
				final Control editableControl;
				if (aEditableWidgetClass.equals(Text.class)) {
					editableControl = new Text(aTable, SWT.NONE);
					((Text)editableControl).addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							newItem.setText(((Text)editableControl).getText());
						}
					});
				} else if (aEditableWidgetClass.equals(CCombo.class)) {
					editableControl = new CCombo(aTable, SWT.BORDER | SWT.READ_ONLY);
					((CCombo)editableControl).setItems(aEditableValues);
					((CCombo)editableControl).addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							newItem.setText(((CCombo)editableControl).getText());
						}
					});
				} else if (aEditableWidgetClass.equals(Date.class)) {					
					editableControl = new Text(aTable, SWT.NONE);
					final DateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.DEFAULT_DATE_FORMAT);
					final String[] data = { ((Text)editableControl).getText(), 
							dateFormat.format(Calendar.getInstance().getTime()) };
					newItem.setText(data);
					((Text)editableControl).addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							//Only accept numbers
							String newText = ((Text)editableControl).getText();
							try {
								Integer.valueOf(newText);
							} catch (NumberFormatException nfe) {
								if (newText.length() > 0) {
									newText = newText.substring(0, newText.length() - 1);
									((Text)editableControl).setText(newText);
									((Text)editableControl).setSelection(newText.length());
								}
							}
							newItem.setText(0, newText);
						}
					});	
				} else {
					return;
				}
				editableControl.setFocus();
				final TableEditor editor = new TableEditor (aTable);
				editor.grabHorizontal = true;
				editor.grabVertical = true;
				editor.setEditor(editableControl, newItem, 0);
				removeButton.setEnabled(true);
				
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
			}
		});
        removeButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));	
        removeButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				final int numItems = aTable.getItemCount();
				if (numItems > 0) {
					//Find the table index for the first control
					final Control[] controls = aTable.getChildren();
					final int firstControlIndex = numItems - controls.length;
						
					//Currently selected item
					int tableItemIndex = aTable.getSelectionIndex();
					if ( R4EUIConstants.INVALID_VALUE == tableItemIndex) {
						//Remove the selected element (and control if there is one) or the last one if noe is selected
						tableItemIndex = numItems - 1;
					}
					if (tableItemIndex >=  firstControlIndex ) {
						controls[tableItemIndex - firstControlIndex].dispose();
					}
					aTable.getItem(tableItemIndex).dispose();
				}
				if (0 == aTable.getItemCount()) removeButton.setEnabled(false);		
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
			}
		});
	}

	/**
	 * Method clearAll.
	 */
	public void clearAll() {
		fMainTable.clearAll();
	}
	
	/**
	 * Method addItem.
	 * @return Item
	 */
	public Item addItem() {
		 return new TableItem (fMainTable, SWT.NONE);
	}
	
	/**
	 * Method getItem.
	 * @param aIndex - int
	 * @return Item
	 */
	public Item getItem(int aIndex) {
		return fMainTable.getItem(aIndex);
	}
	
	/**
	 * Method getItems.
	 * @return Item[]
	 */
	public Item[] getItems() {
		return fMainTable.getItems();
	}
	
	/**
	 * Method getItemCount.
	 * @return int
	 */
	public int getItemCount() {
		return fMainTable.getItemCount();
	}
	
	/**
	 * Method setEnabled.
	 * @param aEnabled - boolean
	 */
	public void setEnabled(boolean aEnabled) {
		fMainComposite.setEnabled(aEnabled);
	}
	
	/**
	 * Method getComposite.
	 * @return Composite
	 */
	public Composite getComposite() {
		return fMainComposite;
	}
}
