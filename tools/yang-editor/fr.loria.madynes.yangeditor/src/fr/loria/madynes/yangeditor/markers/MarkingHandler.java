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
package fr.loria.madynes.yangeditor.markers;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.*;
import org.eclipse.ui.texteditor.*;

import fr.loria.madynes.yangeditor.*;

import jyang.parser.*;

/**
 * MarkingHandler
 * 
 * @author Guillaume Gérard
 * 
 */
public class MarkingHandler {

	public static final String MARKER_ID = "fr.loria.madynes.yangeditor.yangmarker";

	private IFile file;
	private IDocument document;

	public MarkingHandler(IFile file, IDocument document) {
		super();
		this.file = file;
		this.document = document;
	}

	public void validateAndMark() {
		for (YangErrorManager.Error e : Parser.getErrors()) {
			addError(e);
		}
	}

	private void addError(YangErrorManager.Error err) {
		Activator.getLogger().config(
				Activator.getResourceString("Logger.Marker.Add") + " (" + err
						+ ")");
		System.out.println(err);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IMarker.SEVERITY, err.getSeverity());
		map.put(IMarker.MESSAGE, err.getMessageId());
		map.put(IMarker.LOCATION, file.getFullPath().toString());
		map.put(IMarker.LINE_NUMBER, err.getLine());
		try {
			int i = document.getLineOffset(err.getLine() - 1) + err.getColumn()
					- 1;
			int j = i;	
			char c = '\0';
			do {
				c = document.getChar(++j);
			} while (c != ';' && c != '\n' && c != '\r');
			map.put(IMarker.CHAR_START, i);
			map.put(IMarker.CHAR_END, j);
		} catch (BadLocationException e) {
		}

		try {
			MarkerUtilities.createMarker(file, map, MARKER_ID);
		} catch (CoreException e) {
			Activator.getLogger().config(
					Activator.getResourceString("Logger.Error")
							+ Activator.getResourceString("Colon") + " "
							+ e.getMessage());
		}
	}

	public void removeExistingMarkers() {
		try {
			file.deleteMarkers(MARKER_ID, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			Activator.getLogger().config(
					Activator.getResourceString("Logger.Error") + "\n"
							+ e.getMessage());
		}
	}
}
