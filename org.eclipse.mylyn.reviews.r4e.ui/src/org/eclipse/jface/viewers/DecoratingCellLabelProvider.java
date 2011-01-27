// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
* Copyright (c) 2000, 2006 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     IBM Corporation - initial API and implementation
*******************************************************************************/

package org.eclipse.jface.viewers;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * A decorating label provider is a label provider which combines a nested label
 * provider and an optional decorator. The decorator decorates the label text,
 * image, font and colors provided by the nested label provider.
 *
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class DecoratingCellLabelProvider extends CellLabelProvider implements
		ILabelProvider, IViewerLabelProvider, IColorProvider, IFontProvider,
		ITreePathLabelProvider {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field provider.
	 */
	private final ILabelProvider provider;

	/**
	 * Field decorator.
	 */
	private ILabelDecorator decorator;

	/**
	 * Field listeners.
	 */
	private final ListenerList listeners = new ListenerList();  	// Need to keep our own list of listeners

	/**
	 * Field decorationContext.
	 */
	private IDecorationContext decorationContext;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Creates a decorating label provider which uses the given label decorator
	 * to decorate labels provided by the given label provider.
	 *
	 * @param provider
	 * 		the nested label provider
	 * @param decorator
	 * 		the label decorator, or <code>null</code> if no decorator is to be used initially
	 */
	public DecoratingCellLabelProvider(ILabelProvider provider,
			ILabelDecorator decorator) {
		Assert.isNotNull(provider);
		this.provider = provider;
		this.decorator = decorator;
		decorationContext = createDefaultDecorationContext();
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Create a decoration context for the receiver that has a LocalResourceManager.
	 * @return IDecorationContext
	 */
	private IDecorationContext createDefaultDecorationContext() {
		final DecorationContext newContext = new DecorationContext();
		newContext.putProperty("DecorationContext.RESOURCE_MANAGER_KEY",
				new LocalResourceManager(JFaceResources.getResources()));
		return newContext;
	}

	/**
	 * The <code>DecoratingLabelProvider</code> implementation of this
	 * 	<code>IBaseLabelProvider</code> method adds the listener to both the
	 * 	nested label provider and the label decorator.
	 *
	 * @param listener
	 *            a label provider listener
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
		provider.addListener(listener);
		if (null != decorator) {
			decorator.addListener(listener);
		}
		listeners.add(listener);
	}

	/**
	 * The <code>DecoratingLabelProvider</code> implementation of this
	 * 	<code>IBaseLabelProvider</code> method disposes both the nested label
	 * 	provider and the label decorator.
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		if (null != decorationContext) {
			final Object manager = decorationContext
					.getProperty("DecorationContext.RESOURCE_MANAGER_KEY");

			if (null != manager && manager instanceof ResourceManager) {
				((ResourceManager) manager).dispose();	
			}
		}

		provider.dispose();
		if (null != decorator) {
			decorator.dispose();
		}
	}

	/**
	 * The <code>DecoratingLabelProvider</code> implementation of this
	 * 	<code>ILabelProvider</code> method returns the image provided by the
	 * 	nested label provider's <code>getImage</code> method, decorated with
	 * 	the decoration provided by the label decorator's
	 * 	<code>decorateImage</code> method.
	 * @param element Object
	 * @return Image
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(Object)
	 */
	@Override
	public Image getImage(Object element) {
		final Image image = provider.getImage(element);
		if (null != decorator) {
			if (decorator instanceof LabelDecorator) {
				final LabelDecorator ld2 = (LabelDecorator) decorator;
				final Image decorated = ld2.decorateImage(image, element,
						getDecorationContext());
				if (null != decorated) {
					return decorated;
				}
			} else {
				final Image decorated = decorator.decorateImage(image, element);
				if (null != decorated) {
					return decorated;
				}
			}
		}
		return image;
	}

	/**
	 * Returns the label decorator, or <code>null</code> if none has been set.
	 * @return the label decorator, or <code>null</code> if none has been set.
	 */
	public ILabelDecorator getLabelDecorator() {
		return decorator;
	}

	/**
	 * Returns the nested label provider.
	 *
	 * @return the nested label provider
	 */
	public ILabelProvider getLabelProvider() {
		return provider;
	}

	/**
	 * The <code>DecoratingLabelProvider</code> implementation of this
	 * 	<code>ILabelProvider</code> method returns the text label provided by
	 * 	the nested label provider's <code>getText</code> method, decorated with
	 * 	the decoration provided by the label decorator's
	 * 	<code>decorateText</code> method.
	 * @param element Object
	 * @return String
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(Object)
	 */
	@Override
	public String getText(Object element) {
		final String text = provider.getText(element);
		if (null != decorator) {
			if (decorator instanceof LabelDecorator) {
				final LabelDecorator ld2 = (LabelDecorator) decorator;
				final String decorated = ld2.decorateText(text, element,
						getDecorationContext());
				if (null != decorated) {
					return decorated;
				}
			} else {
				final String decorated = decorator.decorateText(text, element);
				if (null != decorated) {
					return decorated;
				}
			}
		}
		return text;
	}

	/**
	 * The <code>DecoratingLabelProvider</code> implementation of this
	 * 	<code>IBaseLabelProvider</code> method returns <code>true</code> if
	 * 	the corresponding method on the nested label provider returns
	 * 	<code>true</code> or if the corresponding method on the decorator
	 * 	returns <code>true</code>.
	 * @param element Object
	 * @param property String
	 * @return boolean
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(Object, String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		if (provider.isLabelProperty(element, property)) {
			return true;
		}
		if (null != decorator && decorator.isLabelProperty(element, property)) {
			return true;
		}
		return false;
	}

	/**
	 * The <code>DecoratingLabelProvider</code> implementation of this
	 * 	<code>IBaseLabelProvider</code> method removes the listener from both
	 * 	the nested label provider and the label decorator.
	 *
	 * @param listener
	 *		a label provider listener
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		super.removeListener(listener);
		provider.removeListener(listener);
		if (null != decorator) {
			decorator.removeListener(listener);
		}
		listeners.remove(listener);
	}

	/**
	 * Sets the label decorator. Removes all known listeners from the old
	 * 	decorator, and adds all known listeners to the new decorator. The old
	 * 	decorator is not disposed. Fires a label provider changed event
	 * 	indicating that all labels should be updated. Has no effect if the given
	 * 	decorator is identical to the current one.
	 *
	 * @param decorator
	 *		the label decorator, or <code>null</code> if no decorations are to be applied
	 */
	public void setLabelDecorator(ILabelDecorator decorator) {
		final ILabelDecorator oldDecorator = this.decorator;
		if (oldDecorator != decorator) { // $codepro.audit.disable useEquals
			final Object[] listenerList = listeners.getListeners();
			if (null != oldDecorator) {
				for (int i = 0; i < listenerList.length; i++) {
					oldDecorator
							.removeListener((ILabelProviderListener) listenerList[i]);
				}
			}
			this.decorator = decorator;
			if (null != decorator) {
				for (int i = 0; i < listenerList.length; i++) {
					decorator
							.addListener((ILabelProviderListener) listenerList[i]);
				}
			}
			fireLabelProviderChanged(new LabelProviderChangedEvent(this));
		}
	}

	/**
	 * Method updateLabel.
	 * @param settings ViewerLabel
	 * @param element Object
	 * @see org.eclipse.jface.viewers.IViewerLabelProvider#updateLabel(ViewerLabel, Object)
	 */
	@Override
	public void updateLabel(ViewerLabel settings, Object element) {

		final ILabelDecorator currentDecorator = getLabelDecorator();
		final String oldText = settings.getText();
		boolean decorationReady = true;
		if (currentDecorator instanceof IDelayedLabelDecorator) {
			final IDelayedLabelDecorator delayedDecorator = (IDelayedLabelDecorator) currentDecorator;
			if (!delayedDecorator.prepareDecoration(element, oldText)) {
				// The decoration is not ready but has been queued for
				// processing
				decorationReady = false;
			}
		}
		// update icon and label

		if (decorationReady || null == oldText
				|| 0 == settings.getText().length()) {
			settings.setText(getText(element));
		}

		final Image oldImage = settings.getImage();
		if (decorationReady || null == oldImage) {
			settings.setImage(getImage(element));
		}

		if (decorationReady) {
			updateForDecorationReady(settings, element);
		}

	}

	/**
	 * Decoration is ready. Update anything else for the settings.
	 *
	 * @param settings
	 * 		The object collecting the settings.
	 * @param element
	 *		The Object being decorated.
	 * @since 3.1
	 */
	protected void updateForDecorationReady(ViewerLabel settings, Object element) {

		if (decorator instanceof IColorDecorator) {
			final IColorDecorator colorDecorator = (IColorDecorator) decorator;
			Color color = colorDecorator.decorateBackground(element);
			if (null != color) settings.setBackground(color);
			color = colorDecorator.decorateForeground(element);
			if (null != color) settings.setForeground(color);
		}

		if (decorator instanceof IFontDecorator) {
			final Font font = ((IFontDecorator) decorator).decorateFont(element);
			if (null != font) settings.setFont(font);
		}

	}

	/**
	 * Method getBackground.
	 * @param element Object
	 * @return Color
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(Object)
	 */
	@Override
	public Color getBackground(Object element) {
		if (provider instanceof IColorProvider) {
			return ((IColorProvider) provider).getBackground(element);
		}
		return null;
	}

	/**
	 * Method getFont.
	 * @param element Object
	 * @return Font
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(Object)
	 */
	@Override
	public Font getFont(Object element) {
		if (provider instanceof IFontProvider) {
			return ((IFontProvider) provider).getFont(element);
		}
		return null;
	}

	/**
	 * Method getForeground.
	 * @param element Object
	 * @return Color
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(Object)
	 */
	@Override
	public Color getForeground(Object element) {
		if (provider instanceof IColorProvider) {
			return ((IColorProvider) provider).getForeground(element);
		}
		return null;
	}

	/**
	 * Method getToolTipBackgroundColor.
	 * @param object Object
	 * @return Color
	 */
	@Override
	public Color getToolTipBackgroundColor(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipBackgroundColor(object);
		}
		return super.getToolTipBackgroundColor(object);
	}

	/**
	 * Method getToolTipDisplayDelayTime.
	 * @param object Object
	 * @return int
	 */
	@Override
	public int getToolTipDisplayDelayTime(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipDisplayDelayTime(object);
		}
		return super.getToolTipDisplayDelayTime(object);
	}

	/**
	 * Method getToolTipFont.
	 * @param object Object
	 * @return Font
	 */
	@Override
	public Font getToolTipFont(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipFont(object);
		}
		return super.getToolTipFont(object);
	}

	/**
	 * Method getToolTipForegroundColor.
	 * @param object Object
	 * @return Color
	 */
	@Override
	public Color getToolTipForegroundColor(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipForegroundColor(object);
		}
		return super.getToolTipForegroundColor(object);
	}

	/**
	 * Method getToolTipImage.
	 * @param object Object
	 * @return Image
	 */
	@Override
	public Image getToolTipImage(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipImage(object);
		}
		return super.getToolTipImage(object);
	}

	/**
	 * Method getToolTipShift.
	 * @param object Object
	 * @return Point
	 */
	@Override
	public Point getToolTipShift(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipShift(object);
		}
		return super.getToolTipShift(object);
	}

	/**
	 * Method getToolTipStyle.
	 * @param object Object
	 * @return int
	 */
	@Override
	public int getToolTipStyle(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipStyle(object);
		}
		return super.getToolTipStyle(object);
	}

	/**
	 * Method getToolTipText.
	 * @param object Object
	 * @return String
	 */
	@Override
	public String getToolTipText(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipText(object);
		}
		return super.getToolTipText(object);
	}

	/**
	 * Method getToolTipTimeDisplayed.
	 * @param object Object
	 * @return int
	 */
	@Override
	public int getToolTipTimeDisplayed(Object object) {
		if( provider instanceof CellLabelProvider ) {
			return ((CellLabelProvider)provider).getToolTipTimeDisplayed(object);
		}
		return super.getToolTipTimeDisplayed(object);
	}

	/**
	 * Return the decoration context associated with this label provider. It
	 * 	will be passed to the decorator if the decorator is an instance of
	 * 	{@link LabelDecorator}.
	 * @since 3.2
	 * @return the decoration context associated with this label provider */
	public IDecorationContext getDecorationContext() {
		return decorationContext;
	}

	/**
	 * Set the decoration context that will be based to the decorator for this
	 * 	label provider if that decorator implements {@link LabelDecorator}.
	 *
	 * If this decorationContext has a {@link ResourceManager} stored for the
	 * 	{@link DecorationContext#RESOURCE_MANAGER_KEY} property it will be
	 * 	disposed when the label provider is disposed.
	 *
	 * @param decorationContext
	 *		the decoration context.
	 * @since 3.2
	 */
	public void setDecorationContext(IDecorationContext decorationContext) {
		org.eclipse.core.runtime.Assert.isNotNull(decorationContext);
		this.decorationContext = decorationContext;
	}

	/**
	 * Method updateLabel.
	 * @param settings ViewerLabel
	 * @param elementPath TreePath
	 * @see org.eclipse.jface.viewers.ITreePathLabelProvider#updateLabel(ViewerLabel, TreePath)
	 */
	@Override
	public void updateLabel(ViewerLabel settings, TreePath elementPath) { // $codepro.audit.disable overloadedMethods
		final ILabelDecorator currentDecorator = getLabelDecorator();
		final String oldText = settings.getText();
		final Object element = elementPath.getLastSegment();
		boolean decorationReady = true;
		if (currentDecorator instanceof LabelDecorator) {
			final LabelDecorator labelDecorator = (LabelDecorator) currentDecorator;
			if (!labelDecorator.prepareDecoration(element, oldText,
					getDecorationContext())) {
				// The decoration is not ready but has been queued for
				// processing
				decorationReady = false;
			}
		} else if (currentDecorator instanceof IDelayedLabelDecorator) {
			final IDelayedLabelDecorator delayedDecorator = (IDelayedLabelDecorator) currentDecorator;
			if (!delayedDecorator.prepareDecoration(element, oldText)) {
				// The decoration is not ready but has been queued for
				// processing
				decorationReady = false;
			}
		}
		settings.setHasPendingDecorations(!decorationReady);
		// update icon and label

		if (provider instanceof ITreePathLabelProvider) {
			final ITreePathLabelProvider pprov = (ITreePathLabelProvider) provider;
			if (decorationReady || null == oldText
					|| 0 == settings.getText().length()) {
				pprov.updateLabel(settings, elementPath);
				decorateSettings(settings, elementPath);
			}
		} else {
			if (decorationReady || null == oldText
					|| 0 == settings.getText().length()) {
				settings.setText(getText(element));
			}

			final Image oldImage = settings.getImage();
			if (decorationReady || null == oldImage) {
				settings.setImage(getImage(element));
			}

			if (decorationReady) {
				updateForDecorationReady(settings, element);
			}
		}

	}

	/**
	 * Decorate the settings
	 *
	 * @param settings
	 * 		the settings obtained from the label provider
	 * @param elementPath
	 * 		the element path being decorated
	 */
	private void decorateSettings(ViewerLabel settings, TreePath elementPath) { // $codepro.audit.disable unusedMethod
		final Object element = elementPath.getLastSegment();
		if (null != decorator) {
			if (decorator instanceof LabelDecorator) {
				final LabelDecorator labelDecorator = (LabelDecorator) decorator;
				final String text = labelDecorator.decorateText(settings.getText(),
						element, getDecorationContext());
				if (null != text && text.length() > 0) settings.setText(text);
				final Image image = labelDecorator.decorateImage(settings.getImage(),
						element, getDecorationContext());
				if (null != image) settings.setImage(image);

			} else {
				final String text = decorator.decorateText(settings.getText(),
						element);
				if (null != text && text.length() > 0) settings.setText(text);
				final Image image = decorator.decorateImage(settings.getImage(),
						element);
				if (null != image) settings.setImage(image);
			}
			if (decorator instanceof IColorDecorator) {
				final IColorDecorator colorDecorator = (IColorDecorator) decorator;
				final Color background = colorDecorator.decorateBackground(element);
				if (null != background) settings.setBackground(background);
				final Color foreground = colorDecorator.decorateForeground(element);
				if (null != foreground) settings.setForeground(foreground);
			}

			if (decorator instanceof IFontDecorator) {
				final Font font = ((IFontDecorator) decorator).decorateFont(element);
				if (null != font) settings.setFont(font);
			}
		}
	}

	/**
	 * Method update.
	 * @param cell ViewerCell
	 */
	@Override
	public void update(ViewerCell cell) {

		final ViewerLabel label = new ViewerLabel(cell.getText(), cell.getImage());

		// Set up the initial settings from the label provider
		label.setBackground(getBackground(cell.getElement()));
		label.setForeground(getForeground(cell.getElement()));
		label.setFont(getFont(cell.getElement()));

		updateLabel(label, cell.getElement());

		cell.setBackground(label.getBackground());
		cell.setForeground(label.getForeground());
		cell.setFont(label.getFont());

		if (label.hasNewText()) cell.setText(label.getText());

		if (label.hasNewImage()) cell.setImage(label.getImage());
	}
}