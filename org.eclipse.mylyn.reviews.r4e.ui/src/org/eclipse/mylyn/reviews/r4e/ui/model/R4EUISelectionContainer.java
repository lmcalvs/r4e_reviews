// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the Selection Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUISelectionContainer extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fSelectionContainerFile.
	 * (value is ""icons/selection_container.gif"")
	 */
	private static final String SELECTION_CONTAINER_ICON_FILE = "icons/selection_container.gif";
    
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fSelections.
	 */
	private final List<R4EUISelection> fSelections;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for SelectionContainerElement.
	 * @param aParent IR4EUIModelElement
	 * @param aName String
	 */
	public R4EUISelectionContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName, null);
		fSelections = new ArrayList<R4EUISelection>();
		setImage(SELECTION_CONTAINER_ICON_FILE);
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fSelections.toArray(new R4EUISelection[fSelections.size()]);
	}
	
	/**
	 * Method getSelectionList.
	 * @return List<SelectionElement>
	 */
	public List<R4EUISelection> getSelectionList() {
		return fSelections;
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fSelections.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUISelection selection = null;
		final int selectionsSize = fSelections.size();
		for (int i = 0; i < selectionsSize; i++) {
			
			selection = fSelections.get(i);
			selection.close();
			//fireRemove(selection);
		}
		fSelections.clear();
		fOpen = false;
		removeListener();
	}
	
	/**
	 * Method open.
	 */
	@Override
	public void open() {
		final EList<R4EDelta> deltas = ((R4EUIFileContext)getParent()).getFileContext().getDeltas();
		if (null != deltas) {
			R4EUITextPosition position = null;
			R4EUISelection newSelection = null;
			final int deltasSize = deltas.size();
			R4EDelta delta = null;
			for (int i = 0; i < deltasSize; i++) {
				delta = deltas.get(i);
				if (delta.isEnabled() || Activator.getDefault().getPreferenceStore().
						getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					position = new R4EUITextPosition(deltas.get(i).getTarget().getLocation());
					newSelection = new R4EUISelection(this, deltas.get(i), position);
					addChildren(newSelection);
				}
			}
			
			try {
				final R4EUIReview review = (R4EUIReview) getParent().getParent().getParent();
				final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

				if (null != user) {
					//Check if the file contexts are part of the reviewed content
					for (R4EUISelection uiSelection : fSelections) {
						if (user.getReviewedContent().contains(uiSelection.getSelection().getId())) {
							uiSelection.setReviewed(true);
						}
					}			
				}
			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);

			} catch (OutOfSyncException e) {
				UIUtils.displaySyncErrorDialog(e);

			}
		}
		fOpen = true;
	}
	
	/**
	 * Method isEnabled.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		if (getParent().isEnabled()) {
			for (R4EUISelection selection : fSelections) {
				if (selection.isEnabled()) return true;
			}
		}
		return false;
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fSelections.add((R4EUISelection) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}

	/**
	 * Method createSelection
	 * @param aUiPosition - the position of the anomaly to create
	 * @return R4EUISelection
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException 
	 */
	public R4EUISelection createSelection(R4EUITextPosition aUiPosition) throws ResourceHandlingException, OutOfSyncException {
		
		//Create and set selection model element
		final R4EDelta selection = R4EUIModelController.FModelExt.createR4EDelta(((R4EUIFileContext)getParent()).getFileContext());
		final R4ETextPosition position = R4EUIModelController.FModelExt.createR4ETextPosition(
				R4EUIModelController.FModelExt.createR4ETargetTextContent(selection));
		aUiPosition.setPositionInModel(position);

		//Create and set UI model element
		final R4EUISelection uiSelection = new R4EUISelection(this, selection, aUiPosition);
		addChildren(uiSelection);
		return uiSelection;
	}
	
	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @param aFileRemove - also remove from file (hard remove)
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		final R4EUISelection removedElement = fSelections.get(fSelections.indexOf(aChildToRemove));
		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getSelection().remove());
		else */
		final R4EDelta modelSelection = removedElement.getSelection();
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelSelection, R4EUIModelController.getReviewer());
		modelSelection.setEnabled(false);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		
		//Remove element from UI if the show disabled element option is off
		if (!(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fSelections.remove(removedElement);
			aChildToRemove.removeListener();
			fireRemove(aChildToRemove);
		} else {
			R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
		}
	}
	
	/**
	 * Method removeAllChildren.
	 * @param aFileRemove boolean
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		//Recursively remove all children
		for (R4EUISelection selection : fSelections) {
			removeChildren(selection, aFileRemove);
		}
	}
	
	//Listeners

	/**
	 * Method addListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
		fListener = aProvider;
		if (null != fSelections) {
			R4EUISelection element = null;
			for (final Iterator<R4EUISelection> iterator = fSelections.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.addListener(aProvider);
			}
		}
	}

	/**
	 * Method removeListener.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener() {
		fListener = null;
		if (null != fSelections) {
			R4EUISelection element = null;
			for (final Iterator<R4EUISelection> iterator = fSelections.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener();
			}
		}
	}
}
