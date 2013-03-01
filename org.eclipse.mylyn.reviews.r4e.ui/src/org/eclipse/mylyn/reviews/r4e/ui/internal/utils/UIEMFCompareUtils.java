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

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Iterables.filter;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.fromSide;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.hasConflict;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffElement;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.domain.ICompareEditingDomain;
import org.eclipse.emf.compare.domain.impl.EMFCompareEditingDomain;
import org.eclipse.emf.compare.ide.EMFCompareIDE;
import org.eclipse.emf.compare.ide.ui.internal.editor.ComparisonScopeEditorInput;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelPosition;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

import com.google.common.base.Predicate;

/**
 * @author Cedric Notot - Obeo
 * @author Alvaro Sanchez-Leon
 * @author Jacques Bouthillier
 */
@SuppressWarnings({ "restriction", "nls" })
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

		// Model
		Object in = input.getCompareResult();

		if (in instanceof ICompareInput) {

			// Load the two input models
			ResourceSet leftResourceSet = new ResourceSetImpl();

			ResourceSet rightResourceSet = new ResourceSetImpl();
			final ResourceSet ancestorResourceSet = null;

			//Path to the file
			//Set the left resource set
			setResource(((ICompareInput) in).getLeft(), leftResourceSet);

			//Set the Right resource set
			setResource(((ICompareInput) in).getRight(), rightResourceSet);

			// Configure EMF Compare
			IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
			IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
			IMatchEngine matchEngine = new DefaultMatchEngine(matcher, comparisonFactory);
			EMFCompare comparator = EMFCompareIDE.builder().setMatchEngine(matchEngine).build();

			// Compare the two models
			IComparisonScope scope = EMFCompare.createDefaultScope(leftResourceSet, rightResourceSet);
			Comparison comparison = comparator.compare(scope);

			//Handle the differences
			List<Diff> listDiff = comparison.getDifferences();
			R4EUIPlugin.Ftracer.traceDebug("List of diff: " + listDiff.size());

			// Construct the predicate
			Predicate<? super Diff> predicate = and(fromSide(DifferenceSource.LEFT),
					not(hasConflict(ConflictKind.REAL, ConflictKind.PSEUDO)));
			// Filter out the diff that do not satisfy the predicate
			Iterable<Diff> nonConflictualDifferencesFromLeft = filter(comparison.getDifferences(), predicate);

			//Adjust the description from the left side 
			Iterator<Diff> iter = nonConflictualDifferencesFromLeft.iterator();
			while (iter.hasNext()) {
				Diff diffLeft = iter.next();
				R4EUIPlugin.Ftracer.traceDebug("nonConflictualDifferencesFromLeft: " + diffLeft.eContainer());

				Match diffMatch = diffLeft.getMatch();
				EObject eObj = diffMatch.getLeft();
				Resource eObjRes = eObj.eResource();
				String objectId = eObjRes.getURIFragment(eObj);
				R4EUIPlugin.Ftracer.traceDebug("\t eObjLeft .resource: " + eObjRes + "\n\t fragmentId: " + objectId);
				List<EObject> listObj = eObjRes.getContents();
				String description = "";
				for (int i = 0; i < listObj.size(); i++) {
					if (listObj.get(i) instanceof Diagram) {
						Diagram diagram = (Diagram) listObj.get(i);
						description = diagram.getType();
						R4EUIPlugin.Ftracer.traceDebug("DiagramDiff name: " + diagram.getName() + "\n\t type: "
								+ diagram.getType());
					} else {
						R4EUIPlugin.Ftracer.traceDebug("ListObj [ " + i + " ] : " + listObj.get(i));
					}
				}
				//Create the position with the description
				R4EUIModelPosition position = getPosition(objectId, description); //Use the type for now as a description
				positions.add(position);

			}

			//
			//
			//  To test and see on the editor for now
			//   Will remove this following code when we can see the EMF compare model editor,
			//   not as text
			//
			//
			ICompareEditingDomain editingDomain = EMFCompareEditingDomain.create(leftResourceSet, rightResourceSet,
					ancestorResourceSet);

			AdapterFactory adapterFactory = new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			final CompareEditorInput compareInput = new ComparisonScopeEditorInput(input.getCompareConfiguration(),
					editingDomain, adapterFactory, comparator, scope);
//			final CompareEditorInput compareInput = new ComparisonEditorInput(input.getCompareConfiguration(),
//					comparison, editingDomain, adapterFactory);

			final Display display = R4EUIModelController.getNavigatorView().getSite().getShell().getDisplay();
			display.syncExec(new Runnable() {
				public void run() {
					//CompareUI.openCompareDialog(compareInput); // or CompareUI.openCompareEditor(input);
					CompareUI.openCompareEditor(compareInput);
				}
			});

		}

		return positions;
	}

	public static void selectElementInCompareModelEditor(IEditorPart editor, R4EUIModelPosition pos) {
		final String id = pos.getObjectID();
		final List<String> ids = Arrays.asList(id);
//		CompareServices.setSelection(ids, editor);
	}

	/**
	 * Method getPosition. Get position based on a IDiffElement.
	 * 
	 * @param aDiff
	 *            IDiffElement
	 * @return R4EUIModelPosition
	 */
	public static R4EUIModelPosition getPosition(DiffElement aDiff) {
		final R4EUIModelPosition position = new R4EUIModelPosition(aDiff);
		return position;
	}

	/**
	 * Method getPosition. Get position based on a String.
	 * 
	 * @param aObjectId
	 *            String
	 * @param aDescription
	 *            String
	 * @return R4EUIModelPosition
	 */
	public static R4EUIModelPosition getPosition(String aObjectId, String aDescription) {
		final R4EUIModelPosition position = new R4EUIModelPosition(aObjectId, aDescription);
		return position;
	}

	/**
	 * Set the resource set input stream
	 * 
	 * @param ITypedElement
	 *            aTypeElement
	 * @param ResourceSet
	 *            aResourceSet
	 * @throws CoreException
	 */
	private static void setResource(ITypedElement aTypeElement, ResourceSet aResourceSet) throws CoreException {
		if (aTypeElement instanceof R4EFileTypedElement) {
			R4EFileTypedElement fileElement = (R4EFileTypedElement) aTypeElement;
			R4EUIPlugin.Ftracer.traceDebug("setResource : " + fileElement.getFileVersion().getRepositoryPath());

			aResourceSet.setURIConverter((URIConverter) new R4EEMFConverter(fileElement));
			//Verify the IPath here
			String ipathStr = fileElement.getFileVersion().getRepositoryPath();
			load(ipathStr, aResourceSet);
			R4EUIPlugin.Ftracer.traceDebug("R4EFileTypedElement path : " + ipathStr);
		} else if (aTypeElement instanceof R4EFileRevisionTypedElement) {
			R4EFileRevisionTypedElement fileElement = (R4EFileRevisionTypedElement) aTypeElement;
			IPath ipath = fileElement.getFileVersion().getFileRevision().getStorage(null).getFullPath();

			aResourceSet.setURIConverter((URIConverter) new R4EEMFConverter(fileElement));
			load(ipath.toPortableString(), aResourceSet);

			R4EUIPlugin.Ftracer.traceDebug("Element file R4EFileRevisionTypedElement "
					+ fileElement.getFileVersion().getFileRevision().getStorage(null).getFullPath().toOSString());
		}

	}

	/**
	 * Load the resource set
	 * 
	 * @param String
	 *            absolutePath
	 * @param ResourceSet
	 *            aResourceSet
	 */
	private static void load(String absolutePath, ResourceSet aResourceSet) {
		URI uri = URI.createFileURI(absolutePath);

		aResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
//		aResourceSet.getResourceFactoryRegistry()
//				.getExtensionToFactoryMap()
//				.put("ecorediag", new XMIResourceFactoryImpl());

		// Resource will be loaded within the resource set
		aResourceSet.getResource(uri, true);
	}

}
