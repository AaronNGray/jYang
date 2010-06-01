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
package fr.loria.madynes.yangeditor.editors.format;

import java.util.*;

/**
 * DefaultFormattingStrategy
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGFormattingStrategy extends DefaultFormattingStrategy {

	private boolean lastTagWasOpening;

	public YANGFormattingStrategy() {
		super();
	}

	public String format(String content, boolean isLineStart,
			String indentation, int[] positions) {
		if (isLineStart) {
			indentation = initialIndentation;
		}

		content = formatContent(content);

		if (lastTagWasOpening) {
			indentation = indentation + "\t";
		}

		lastTagWasOpening = false;

		if (content.equals("}")) {
			indentation = indentation.substring(0, indentation.length() - 1);
		}

		content = content
				.replaceAll(lineSeparator, lineSeparator + indentation);

		if (content.equals("{")) {
			lastTagWasOpening = true;
			return " " + content;
		}

		if (!isLineStart) {
			return lineSeparator + indentation + content;
		} else {
			return indentation + content;
		}
	}

	private String formatContent(String content) {
		content = content.replaceAll("(\t| )+", " ").replaceAll(";", ";\n")
				.replaceAll("\n+", "\n");
		String[] ts = content.split("\n");
		ArrayList<String> als = new ArrayList<String>();
		for (String s : ts) {
			als.add(s.trim());
		}
		String res = new String();
		for (int i = 0; i < als.size(); i++) {
			String s = als.get(i);
			if (!s.isEmpty()) {
				if (i > 0) {
					res += lineSeparator + s;
				} else {
					res += s;
				}
			}
		}
		return res;
	}
}
