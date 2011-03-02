package org.eclipse.mylyn.reviews.r4e.ui.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class EditableListWidget {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	private Composite fMainComposite = null;
	
	private Table fMainTable = null;
	
	private IEditableListListener fListener = null;
	
	private int fInstanceId = 0;
	
	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------
	
	//TODO:  This should be made more generic to accept various tables.  Right now it is very hard-coded to our stuff...
	public EditableListWidget(FormToolkit aToolkit, Composite aParent, Object aLayoutData, 
			IEditableListListener aListener, int aInstanceId, Class<?> aEditableWidgetClass, String[] aEditableValues) {
		fMainComposite = aToolkit.createComposite(aParent);
		fMainComposite.setLayoutData(aLayoutData);
		fMainComposite.setLayout(new GridLayout(4, false));
		fMainTable = aToolkit.createTable(fMainComposite, SWT.FULL_SELECTION /*| SWT.VIRTUAL*/);
        final GridData tableData = new GridData(GridData.FILL, GridData.FILL, true, true);
        if (aEditableWidgetClass.equals(Date.class)) tableData.horizontalSpan = 1;
        else tableData.horizontalSpan = 2;
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
	
	public void dispose() {
		fMainComposite.dispose();
	}
	
    /**
     * Method createEditableListFromTable.
     * @param aToolkit - FormToolkit
     * @param aParent - Composite
     * @param aTable - Table
     * @param aStringList - List<String>
     */
	public static void createEditableListFromTable(FormToolkit aToolkit, Composite aParent, final Table aTable,
			final IEditableListListener aListener, final int aInstanceId, final Class<?> aEditableWidgetClass, 
			final String[] aEditableValues) {

		final TableColumn tableColumn = new TableColumn(aTable, SWT.LEFT, 0);
		final TableColumn tableColumn2 = new TableColumn(aTable, SWT.RIGHT, 1);
		
		if (aEditableWidgetClass.equals(Date.class)) {
			aTable.setHeaderVisible(true);
			tableColumn.setText("Time spent (minutes)");
			tableColumn2.setText("Time of entry");
		}

        aTable.addControlListener(new ControlAdapter() { 
			@Override
			public void controlResized(ControlEvent e) {
				//TODO:  This is a hack so that the table is displayed somewhat correctly.  However, we need to have a better implementation later
				if (aEditableWidgetClass.equals(Date.class)) {
					tableColumn.setWidth(aTable.getClientArea().width > 600 ? 300 : aTable.getClientArea().width/2);
					tableColumn2.setWidth(aTable.getClientArea().width > 600 ? 300 : aTable.getClientArea().width/2);
				} else {
					tableColumn.setWidth(aTable.getClientArea().width > 600 ? 600 : aTable.getClientArea().width);
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
	      
		Composite buttonsComposite = aToolkit.createComposite(aParent);
		buttonsComposite.setLayout(new GridLayout());
		buttonsComposite.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

        Button addButton = aToolkit.createButton(buttonsComposite, "Add", SWT.NONE);
        final Button removeButton = aToolkit.createButton(buttonsComposite, "Remove", SWT.NONE);

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
				} else if (aEditableWidgetClass.equals(Combo.class)) {
					editableControl = new Combo(aTable, SWT.READ_ONLY);
					((Combo)editableControl).setItems(aEditableValues);
					((Combo)editableControl).addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							newItem.setText(((Combo)editableControl).getText());
						}
					});
				} else if (aEditableWidgetClass.equals(Date.class)) {					
					editableControl = new Text(aTable, SWT.NONE);
					final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String[] data = { ((Text)editableControl).getText(), 
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
				TableEditor editor = new TableEditor (aTable);
				editor.grabHorizontal = true;
				editor.grabVertical = true;
				editor.setEditor(editableControl, newItem, 0);
				removeButton.setEnabled(true);
				
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
        removeButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));	
        removeButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (aTable.getItemCount() > 0) {
					//Remove last element(s) of table
					Control[] controls = aTable.getChildren();
					TableItem item = ((Table)controls[controls.length - 1].getParent()).getItem(controls.length - 1);
					controls[controls.length - 1].dispose();
					item.dispose();
				}
				if (aTable.getItemCount() == 0) removeButton.setEnabled(false);		
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
		 
	public void clearAll() {
		fMainTable.clearAll();
	}
	
	public Item addItem() {
		 return new TableItem (fMainTable, SWT.NONE);
	}
	
	public Item getItem(int aIndex) {
		return fMainTable.getItem(aIndex);
	}
	
	public Item[] getItems() {
		return fMainTable.getItems();
	}
	
	public int getItemCount() {
		return fMainTable.getItemCount();
	}
	
	public void setEnabled(boolean aEnabled) {
		fMainComposite.setEnabled(aEnabled);
	}
	
	public Composite getComposite() {
		return fMainComposite;
	}
}
