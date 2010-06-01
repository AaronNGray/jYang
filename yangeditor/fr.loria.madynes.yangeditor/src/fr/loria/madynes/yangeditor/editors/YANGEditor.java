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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.*;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.*;
import org.eclipse.ui.texteditor.*;
import org.eclipse.ui.views.contentoutline.*;

import fr.loria.madynes.yangeditor.*;
import fr.loria.madynes.yangeditor.editors.markers.*;
import fr.loria.madynes.yangeditor.editors.menus.*;
import fr.loria.madynes.yangeditor.editors.outline.*;

/**
 * YANGEditor
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGEditor extends TextEditor {
	private ColorManager colorManager;
	private IEditorInput input;
	private YangContentOutlinePage outlinePage;

	public YANGEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new YANGConfiguration(colorManager));
		setDocumentProvider(new YANGDocumentProvider());
	}

	public void dispose() {
		colorManager.dispose();
		if (outlinePage != null) {
			outlinePage.setInput(null);
		}
		super.dispose();
	}

	protected void doSetInput(IEditorInput newInput) throws CoreException {
		Activator.getLogger().config(
				Activator.getResourceString("Logger.Editor.Change"));
		super.doSetInput(newInput);
		this.input = newInput;
		if (outlinePage != null) {
			outlinePage.setInput(input);
		}
		update();
	}

	protected void editorSaved() {
		Activator.getLogger().config(
				Activator.getResourceString("Logger.Editor.Save"));
		super.editorSaved();
		update();
	}

	private void update() {
		Activator.getLogger().config(
				Activator.getResourceString("Logger.Editor.Update"));
		IDocument document = getInputDocument();
		String filename = getInputFile().getLocation().toString();
		Parser.parse(filename);
		if (outlinePage != null) {
			outlinePage.update();
		}
		MarkingHandler mh = new MarkingHandler(getInputFile(), document);
		mh.removeExistingMarkers();
		mh.validateAndMark();
	}

	public IDocument getInputDocument() {
		IDocument document = getDocumentProvider().getDocument(input);
		return document;
	}

	protected IFile getInputFile() {
		IFileEditorInput ife = (IFileEditorInput) input;
		IFile file = ife.getFile();
		return file;
	}

	public IEditorInput getInput() {
		return input;
	}

	protected void createActions() {
		super.createActions();
		ResourceBundle bundle = Activator.getDefault().getResourceBundle();
		setAction("ContentFormatProposal", new TextOperationAction(bundle,
				"ContentFormatProposal.", this, ISourceViewer.FORMAT));
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage = new YangContentOutlinePage(this);
				if (getEditorInput() != null)
					outlinePage.setInput(getEditorInput());
			}
			return outlinePage;
		}
		return super.getAdapter(required);
	}

	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		menu
				.appendToGroup(ITextEditorActionConstants.GROUP_EDIT,
						new YANGFormatAction(getSourceViewer()
								.getTextOperationTarget()));
	}
}
