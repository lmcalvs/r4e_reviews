/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.compare.diff.metamodel.ComparisonResourceSetSnapshot;
import org.eclipse.emf.compare.diff.metamodel.ComparisonResourceSnapshot;
import org.eclipse.emf.compare.diff.metamodel.ComparisonSnapshot;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.DiffResourceSet;
import org.eclipse.emf.compare.ui.ModelCompareInput;
import org.eclipse.emf.compare.ui.internal.ModelComparator;
import org.eclipse.emf.compare.ui.services.CompareServices;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelPosition;
import org.eclipse.ui.IEditorPart;

/**
 * @author Cedric Notot - Obeo
 * @author Alvaro Sanchez-Leon
 */
public class UIEMFCompareUtils {
	public static List<IR4EUIPosition> createModelDeltas(R4ECompareEditorInput input) throws CoreException {
		// Model part
		List<IR4EUIPosition> positions = new ArrayList<IR4EUIPosition>();
		try {
			input.run(new NullProgressMonitor());
		} catch (InterruptedException e) {
			IStatus status = new Status(IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, "InterruptedException", e); //$NON-NLS-1$
			throw new CoreException(status);
		} catch (InvocationTargetException e) {
			IStatus status = new Status(IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, "InvocationTargetException", e); //$NON-NLS-1$
			throw new CoreException(status);
		}

		Object diffModel = null;
		// Model
		final ModelComparator comparator;
		Object in = input.getCompareResult();
		if (in instanceof ICompareInput) {
			comparator = ModelComparator.getComparator(input.getCompareConfiguration(), (ICompareInput) in);
		} else {
			comparator = ModelComparator.getComparator(input.getCompareConfiguration());
		}

		ComparisonSnapshot snapshot = null;
		if (in instanceof ModelCompareInput) {
			snapshot = ((ModelCompareInput) in).getComparisonSnapshot();
		} else if (in instanceof ComparisonSnapshot) {
			snapshot = (ComparisonSnapshot) in;
		}

		if (snapshot instanceof ComparisonResourceSnapshot) {
			diffModel = ((ComparisonResourceSnapshot) snapshot).getDiff();
		} else if (snapshot instanceof ComparisonResourceSetSnapshot) {
			diffModel = ((ComparisonResourceSetSnapshot) snapshot).getDiffResourceSet();
		} else if (comparator.getComparisonResult() != null) {
			diffModel = retrieveInputFromSnapshot(comparator.getComparisonResult());
		} else if (in instanceof ModelCompareInput) {
			diffModel = ((ModelCompareInput) in).getDiff();
		} else if (in instanceof ICompareInput) {
			comparator.loadResources((ICompareInput) in);
			diffModel = retrieveInputFromSnapshot(comparator.compareSilentlyInThreadUI(input.getCompareConfiguration()));
		}

		if (diffModel instanceof DiffModel) {
			for (DiffElement adiff : ((DiffModel) diffModel).getDifferences()) {
				R4EUIModelPosition position = getPosition(adiff);
				positions.add(position);
			}
		} else if (diffModel instanceof DiffResourceSet) {
			Iterator<DiffModel> it = ((DiffResourceSet) diffModel).getDiffModels().iterator();
			//Add Deltas from the list of differences
			while (it.hasNext()) {
				DiffModel currentDiffModel = it.next();
				for (DiffElement adiff : currentDiffModel.getDifferences()) {
					R4EUIModelPosition position = getPosition(adiff);
					positions.add(position);
				}
			}
		}

		return positions;
	}

	public static void selectElementInCompareModelEditor(IEditorPart editor, R4EUIModelPosition pos) {
		final String id = pos.getObjectID();
		final List<String> ids = Arrays.asList(id);
		CompareServices.setSelection(ids, editor);
	}

	private static Object retrieveInputFromSnapshot(final ComparisonSnapshot comparisonResult) {
		Object retrievedInput = null;
		if (comparisonResult instanceof ComparisonResourceSnapshot) {
			retrievedInput = ((ComparisonResourceSnapshot) comparisonResult).getDiff();
		} else {
			retrievedInput = ((ComparisonResourceSetSnapshot) comparisonResult).getDiffResourceSet();
		}
		return retrievedInput;
	}

	/**
	 * Method getPosition. Get position based on a IDiffElement.
	 * 
	 * @param aDiff
	 *            IDiffElement
	 * @return R4EUIModelPosition
	 */
	private static R4EUIModelPosition getPosition(DiffElement aDiff) {
		final R4EUIModelPosition position = new R4EUIModelPosition(aDiff);
		return position;
	}
}
