package fr.loria.madynes.yangeditor.editors.markers;

import java.io.*;
import java.util.*;

import jyang.parser.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.*;
import org.eclipse.ui.texteditor.*;

import fr.loria.madynes.yangeditor.*;

/**
 * MarkingErrorHandler
 * 
 * @author Guillaume Gérard
 * 
 */
public class MarkingErrorHandler {

	public static final String ERROR_MARKER_ID = "yangeditor.yangerror";
	private static boolean reinit = false;

	private IFile file;
	private IDocument document;

	public MarkingErrorHandler(IFile file, IDocument document) {
		super();
		this.file = file;
		this.document = document;

	}

	public void validateAndMark() {
		String filename = file.getLocation().toString();
		try {
			FileInputStream yangfile = new FileInputStream(filename);
			// Set the jyang parser with the YANG specification file
			if (!reinit) {
				reinit = true;
				new yang(yangfile);
			} else {
				yang.ReInit(yangfile);
			}
			yang.setFileName(filename);
			YangErrorManager.init();
			YangErrorManager.setCurrentModule(filename);
			// do the lexical and syntactic check
			YANG_Specification spec = yang.Start();
			// do the semantical check
			spec.check();
		} catch (ParseException e) {
			if (e.currentToken != null && e.currentToken.next != null) {
				YangErrorManager.tadd(filename, e.currentToken.next.beginLine,
						e.currentToken.next.beginColumn, "unex_kw",
						e.currentToken.next.image);
			}
		} catch (Exception e) {
			Logger.write(getClass().getName() + " "
					+ Activator.getResourceString("Logger.Error")
					+ Activator.getResourceString("Logger.Colon") + " "
					+ e.getMessage());
		}
		for (YangErrorManager.Error e : YangErrorManager.getErrors()) {
			addError(e);
		}
	}

	private void addError(YangErrorManager.Error err) {
		if (Logger.isActive()) {
			Logger.write(Activator.getResourceString("Logger.AddMarkerError")
					+ " (" + err + ")");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
		map.put(IMarker.MESSAGE, err.getMessageId());
		map.put(IMarker.LOCATION, file.getFullPath().toString());
		map.put(IMarker.LINE_NUMBER, err.getLine());
		try {
			map.put(IMarker.CHAR_START, document
					.getLineOffset(err.getLine() - 1)
					+ err.getLine() - 1);
			int i = document.getLineOffset(err.getLine() - 1) + err.getLine()
					+ 1;
			char c = document.getChar(i);
			while (Character.isLetterOrDigit(c)) {
				c = document.getChar(++i);
			}
			map.put(IMarker.CHAR_END, i);
		} catch (BadLocationException e) {
		}

		try {
			MarkerUtilities.createMarker(file, map, ERROR_MARKER_ID);
		} catch (CoreException e) {
			Logger.write(getClass().getName() + " "
					+ Activator.getResourceString("Logger.Error")
					+ Activator.getResourceString("Logger.Colon") + " "
					+ e.getMessage());
		}
	}

	public void removeExistingMarkers() {
		try {
			file.deleteMarkers(ERROR_MARKER_ID, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			Logger.write(getClass().getName() + " "
					+ Activator.getResourceString("Logger.Error")
					+ Activator.getResourceString("Logger.Colon") + " "
					+ e.getMessage());
		}
	}
}
