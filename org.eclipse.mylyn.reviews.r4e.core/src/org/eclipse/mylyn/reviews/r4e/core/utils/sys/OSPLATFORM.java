/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * 	 Ericsson AB - Initial API and Implementation 
 *   Alvaro Sanchez-Leon - Adapted for Review for Eclipse
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils.sys;

import java.io.File;

import org.eclipse.core.runtime.Platform;

public final class OSPLATFORM {

	public enum OSTYPE {
		WINDOWS {
			@Override
			protected OSTYPE match(String name) {
				OSTYPE type = null;
				if (Platform.getOS().matches(Platform.OS_WIN32)) {
					type = WINDOWS;
				}
				return type;
			}
		}, //
		LINUX {
			@Override
			protected OSTYPE match(String name) {
				OSTYPE type = null;

				if (Platform.getOS().matches(Platform.OS_LINUX)) {
					type = LINUX;
				}
				return type;
			}
		}, // 
		SOLARIS {
			@Override
			protected OSTYPE match(String name) {
				OSTYPE type = null;

				if (Platform.getOS().matches(Platform.OS_SOLARIS)) {
					type = SOLARIS;
				}
				return type;
			}
		},
		MAC {
			@Override
			protected OSTYPE match(String name) {
				OSTYPE type = null;

				if (Platform.getOS().matches(Platform.OS_MACOSX)) {
					type = MAC;
				}
				return type;
			}
		};
		
		protected abstract OSTYPE match(String name);

		/**
		 * Internal Helper
		 * 
		 * @param name
		 * @return
		 */
		protected static OSTYPE resolveOSNameToOSType(String name) {
			OSTYPE type = null;
			for (OSTYPE t : OSTYPE.values()) {
				type = t.match(name);
				if (type != null)
					break;
			}
			return type;
		}

		//
		// Publish methods
		//

		/**
		 * @return boolean
		 */
		public boolean isWindowsOS() {
			return this == WINDOWS;
		}

		/**
		 * @return boolean
		 */
		public boolean isLinuxOS() {
			return this == LINUX;
		}

		/**
		 * @return boolean
		 */
		public boolean isSolarisOS() {
			return this == SOLARIS;
		}
		
		/**
		 * @return boolean
		 */
		public boolean isMacOS() {
			return this == MAC;
		}
	} // enum

	public static final String NAME = System.getProperty("os.name");

	public static final OSTYPE TYPE = OSTYPE.resolveOSNameToOSType(NAME);

	// Separator among files
	public static final String FILE_PATH_SEPARATOR = File.pathSeparator;

	public static final char FILE_PATH_SEPARATOR_CHAR = File.pathSeparatorChar;

	// Separator between file elements (directory and file names)
	public static final String FILE_ELEMENT_SEPARATOR = File.separator;

	public static final char FILE_ELEMENT_SEPARATOR_CHAR = File.separatorChar;

	public static final String FILE_ELEMENT_SEPARATOR_PORTABLE = new String("/");

	public static final boolean IS_FILE_ELEMENT_SEPARATOR_PORTABLE = FILE_ELEMENT_SEPARATOR_PORTABLE
			.equals(FILE_ELEMENT_SEPARATOR);

}
