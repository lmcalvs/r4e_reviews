/*******************************************************************************
 * Copyright (c) 2013 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.reviews.r4e.connector.ui.editor;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.internal.tasks.ui.editors.AbstractTaskEditorSection;
import org.eclipse.mylyn.internal.tasks.ui.editors.EditorUtil;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Supports display of review items and files within them. To be merged eventually w/ the Gerrit patch set support.
 * 
 * @author Miles Parker
 * @author Steffen Pingel
 * @author Sascha Scholz
 */
@SuppressWarnings("restriction")
public class ArtifactsSection extends AbstractTaskEditorSection {

	private FormToolkit toolkit;

	private Composite composite;

	public ArtifactsSection() {
		setPartName("Artifacts");
	}

	public Label addTextClient(final FormToolkit toolkit, final Section section, String text) {
		return addTextClient(toolkit, section, text, true);
	}

	public Label addTextClient(final FormToolkit toolkit, final Section section, String text, boolean hideOnExpand) {
		final Label label = new Label(section, SWT.NONE);
		label.setText("  " + text); //$NON-NLS-1$
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

		section.setTextClient(label);

		if (hideOnExpand) {
			label.setVisible(!section.isExpanded());
			section.addExpansionListener(new ExpansionAdapter() {
				@Override
				public void expansionStateChanged(ExpansionEvent e) {
					label.setVisible(!section.isExpanded());
				}
			});
		}

		return label;
	}

	protected Shell getShell() {
		return getTaskEditorPage().getSite().getShell();
	}

	protected ITask getTask() {
		return getTaskEditorPage().getTask();
	}

	@Override
	protected Control createContent(FormToolkit toolkit, Composite parent) {
		this.toolkit = toolkit;

		composite = toolkit.createComposite(parent);
		GridLayoutFactory.fillDefaults().extendedMargins(0, 0, 0, 5).applyTo(composite);
		final Review review = ((R4ETaskEditorPage) getTaskEditorPage()).getReview();
		for (Item item : review.getReviewItems()) {
			createSubSection((R4EItem) item, getSection());
		}

		return composite;
	}

	private void createSubSection(final R4EItem item, Section section) {
		int style = ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT
				| ExpandableComposite.LEFT_TEXT_CLIENT_ALIGNMENT;
		final Section subSection = toolkit.createSection(composite, style);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(subSection);
		String description = item.getDescription();
		description = StringUtils.replace(description, "\n", "");
		description = StringUtils.abbreviate(description, 100);
		if (description == null) {
			description = "Item";
		}
		subSection.setText(description);
		subSection.setTitleBarForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		subSection.setToolTipText(item.getDescription());

		addTextClient(toolkit, subSection, "", false); //$NON-NLS-1$

		if (subSection.isExpanded()) {
			createSubSectionContents(item, subSection);
		}
		subSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				if (subSection.getClient() == null) {
					createSubSectionContents(item, subSection);
				}
			}
		});
	}

	void createSubSectionContents(final R4EItem item, Section subSection) {
		Composite composite = toolkit.createComposite(subSection);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		subSection.setClient(composite);

		String addedBy = item.getAddedById();
		if (addedBy != null) {
			Label authorLabel = new Label(composite, SWT.NONE);
			authorLabel.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
			authorLabel.setText("Added By");

			Text authorText = new Text(composite, SWT.READ_ONLY);
			authorText.setText(addedBy);
		}

		String name = item.getAuthorRep();
		if (name != null) {
			Label commitLabel = new Label(composite, SWT.NONE);
			commitLabel.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
			commitLabel.setText("Author");

			Text commitText = new Text(composite, SWT.READ_ONLY);
			commitText.setText(name);
		}

		final TableViewer viewer = new TableViewer(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.VIRTUAL);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).hint(500, SWT.DEFAULT).applyTo(viewer.getControl());
		viewer.setContentProvider(new IStructuredContentProvider() {
			public void dispose() {
				// ignore
			}

			public Object[] getElements(Object inputElement) {
				return item.getFileContextList().toArray();
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// ignore

			}
		});

//	Not currently supported
//		viewer.addOpenListener(new IOpenListener() {
//			public void open(OpenEvent event) {
//				EditorProxy.openSimpleEditor(R4EUIModelController.getNavigatorView().getSite().getPage(),
//						event.getSelection(), true);
//			}
//		});

		viewer.setLabelProvider(new ReviewItemLabelProvider());

		viewer.setInput(item);
		EditorUtil.addScrollListener(viewer.getTable());

		getTaskEditorPage().reflow();
	}

	@Override
	protected boolean shouldExpandOnCreate() {
		return ((R4ETaskEditorPage) getTaskEditorPage()).getReview() != null;
	}

}
