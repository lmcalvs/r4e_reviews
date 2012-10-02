/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class defines a class that is used to hold injected factory classes from
 * the concrete implementation of the Review annotations framework
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationControlCreatorFactory;


/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewAnnotationConfigFactory {
	
	/**
	 * Field fFactory.
	 */
	private static IReviewAnnotationControlCreatorFactory FFactory = null;
	
	/**
	 * Field fUseInformationControlReplacer.
	 */
	private static boolean FUseInformationControlReplacer = false;
	
	/**
	 * Field Fplugin.
	 */
	private static Plugin Fplugin = null;
		
	/**
	 * Method getPlugin.
	 * @return Plugin
	 */
	public static Plugin getPlugin() {
		return Fplugin;
	}

	/**
	 * Method setPlugin.
	 * @param aPlugin - Plugin
	 */
	public static void setPlugin(Plugin aPlugin) {
		Fplugin = aPlugin;
	}
	
	/**
	 * Method registerFactory.
	 * @param aFactory IReviewAnnotationControlCreatorFactory
	 */
	public static void registerFactory(IReviewAnnotationControlCreatorFactory aFactory) {
		FFactory = aFactory;
	}
	
	/**
	 * Method setUseInformationControlReplacer.
	 * @param aValue boolean
	 */
	public static void setUseInformationControlReplacer(boolean aValue) {
		FUseInformationControlReplacer = aValue;
	}
	
	/**
	 * Method getUseInformationControlReplacer.
	
	 * @return boolean */
	public static boolean getUseInformationControlReplacer() {
		return FUseInformationControlReplacer;
	}
	
	/**
	 * Method createInformationControlCreator.
	
	 * @return IInformationControlCreator */
	public static IInformationControlCreator createInformationControlCreator() {
		if (null != FFactory) {
			return FFactory.createInformationControlCreator(); 
		}
		return null;
	}
}
