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
package fr.loria.madynes.yangeditor.editors.menus;

import java.io.*;

import jyang.tools.*;

import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

import fr.loria.madynes.yangeditor.*;

public class YANGToYin implements IWorkbenchWindowActionDelegate {

	public void run(IAction action) {
		String filename = Parser.getFilename().replaceFirst("(yang)$", "yin");
		File f = new File(filename);
		try {
			PrintStream ps = new PrintStream(f);
			String[] paths = new String[] { f.getCanonicalPath() };
			@SuppressWarnings("unused")
			Yang2Yin y2y = new Yang2Yin(Parser.getYangSpecification(), paths,
					ps);
			ps.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}
}
