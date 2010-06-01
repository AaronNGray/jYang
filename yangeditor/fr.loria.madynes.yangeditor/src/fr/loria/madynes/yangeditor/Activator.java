/*******************************************************************************
 * Copyright (c) 2010 Guillaume Gérard
 * Projet INRIA Madynes
 * 
 * This file is part of jyang.
 * 
 * jyang is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * jyang is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jyang.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package fr.loria.madynes.yangeditor;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Logger;

import org.eclipse.jface.resource.*;
import org.eclipse.ui.plugin.*;
import org.osgi.framework.*;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Guillaume Gérard
 * 
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.loria.madynes.yangeditor";

	// The shared instance
	private static Activator plugin;

	// Resource bundle
	private ResourceBundle resourceBundle;

	// Logger
	private static Logger logger;

	/**
	 * The constructor
	 */
	public Activator() {
		super();
		try {
			resourceBundle = ResourceBundle.getBundle(PLUGIN_ID
					+ ".langs.YANGResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		logger = Logger.getLogger("fr.loria.madynes.yangeditor");
		try {
			Handler h = new FileHandler("%t/yangeditor.log", false);
			logger.addHandler(h);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	@SuppressWarnings("deprecation")
	public static ImageDescriptor getImageDescriptor(String path) {
		String iconPath = "icons/";
		String ext = ".png";
		try {
			URL installURL = getDefault().getDescriptor().getInstallURL();
			URL url = new URL(installURL, iconPath + path + ext);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found
	 * 
	 * @return the string from the plugin's resource bundle, or 'key' if not
	 *         found
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle
	 * 
	 * @return the plugin's resource bundle
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static Logger getLogger() {
		return logger;
	}
}
