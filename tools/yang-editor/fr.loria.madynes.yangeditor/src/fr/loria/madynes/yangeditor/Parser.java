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
import java.util.*;

import jyang.jyang;
import jyang.parser.*;
import jyang.parser.YangErrorManager.Error;

public class Parser {

	private static String filename = null;
	private static jyang jy = null;
	

	public static void parse(String fname) {
		SimpleCharStream.setTabSize(1);
		filename = fname;
		try {
			File f = new File(filename);
			jy = new jyang(new String[] { "-w", "-p", f.getCanonicalPath(),
					filename }, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFilename() {
		return filename;
	}

	public static TreeSet<Error> getErrors() {
		return YangErrorManager.getErrors();
	}

	public static YANG_Specification getYangSpecification() {
		return jy.getYangsSpecs().get(
				filename.substring(
						filename.lastIndexOf(File.separatorChar) + 1, filename
								.length() - 5));
	}

	public static Hashtable<String, YANG_Specification> getYangsSpecs() {
		return jy.getYangsSpecs();
	}
}
