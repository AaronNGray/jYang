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
package fr.loria.madynes.yangeditor.editors;

import java.util.*;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * ColorManager
 *
 * @author Guillaume Gérard
 *
 */
public class ColorManager {

	protected Map<RGB, Color> fColorTable;

	public ColorManager() {
		fColorTable = new HashMap<RGB, Color>(10);
	}

	public void dispose() {
		Iterator<Color> it = fColorTable.values().iterator();
		while (it.hasNext()) {
			it.next().dispose();
		}
	}

	public Color getColor(RGB rgb) {
		Color color = fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
}