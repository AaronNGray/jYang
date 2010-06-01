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

import org.eclipse.jface.text.formatter.*;

public class DefaultFormattingStrategy implements IFormattingStrategy {
	protected static final String lineSeparator = System
			.getProperty("line.separator");

	protected String initialIndentation;

	public DefaultFormattingStrategy() {
		super();
	}

	public void formatterStarts(String initialIndentation) {
		this.initialIndentation = initialIndentation;
	}

	public String format(String content, boolean isLineStart,
			String indentation, int[] positions) {
		return content;
	}

	public void formatterStops() {
	}
}