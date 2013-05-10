/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.R4EGerritUi;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;


/**
 * @author lmcbout
 *
 */
public class ReviewTableLabelProvider extends LabelProvider implements
ITableLabelProvider {
//public class ReviewTableLabelProvider implements ILabelProvider {

	// Names of images used to represent review-checked
	public static final String CHECKED_IMAGE = "R4E_checked";

	// Names of images used to represent review-not OK
	public static final String NOT_OK_IMAGE = "R4E_not_ok";

	// For the images
	private static ImageRegistry imageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it,
	 * and automatically disposes of them the SWT Display is disposed.
	 */
	static {
		String iconPath = "icons/view16/";
		imageRegistry.put(CHECKED_IMAGE, R4EGerritUi.getImageDescriptor(iconPath
				+ CHECKED_IMAGE + ".gif"));
		imageRegistry.put(NOT_OK_IMAGE, R4EGerritUi.getImageDescriptor(iconPath
				+ NOT_OK_IMAGE + ".gif"));

	}



	public Image getImage(Object element) {
		Image image = null;
		R4EGerritPlugin.Ftracer.traceWarning("getImage column: " + element.toString() );
//		IReviewEntityItem irei = (IReviewEntityItem) element;
//		if (irei.getType().isReviewItem()) {
//			if (columnIndex == ReviewEntityItemType.ITEM_ELE_REVIEWED) {
//				if (ReviewEntityItemType.REVIEW_ITEM.isChecked(irei)) {
					image = imageRegistry.get(CHECKED_IMAGE);
//				}
//			}
//		}
		return image;
	}

//	@Override
//	public String getText(Object element) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	
	
public String getColumnText(Object obj, int index) {
	R4EGerritPlugin.Ftracer.traceWarning("getColumnText column: " + index );
	Image image = getReviewSate ( index %5);
	if (image == null ) {
		return getText(obj);		
	}
	return "";
}

public Image getColumnImage(Object obj, int index) {
	R4EGerritPlugin.Ftracer.traceWarning("getColumnImage column: " + index );
	return  getReviewSate ( index %3);
	//return getImage(obj);
}


/**
 * Retun an image representing the state of the object
 * @param aState
 * @return Image
 */
private Image getReviewSate(int aState) {
	if (aState == 0) {
		return imageRegistry.get(NOT_OK_IMAGE);
	} else if (aState == 1) {
		return imageRegistry.get(CHECKED_IMAGE);
	}
	return null;
}

	
}
