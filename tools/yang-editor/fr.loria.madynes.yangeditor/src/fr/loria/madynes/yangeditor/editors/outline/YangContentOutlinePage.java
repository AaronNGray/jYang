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
package fr.loria.madynes.yangeditor.editors.outline;

import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.views.contentoutline.*;

import fr.loria.madynes.yangeditor.editors.*;

import jyang.parser.*;

public class YangContentOutlinePage extends ContentOutlinePage {
	private YANGEditor editor;
	private IEditorInput input;
	private OutlineContentProvider outlineContentProvider;
	private OutlineLabelProvider outlineLabelProvider;

	public YangContentOutlinePage(YANGEditor editor) {
		super();
		this.editor = editor;
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();
		outlineContentProvider = new OutlineContentProvider(editor
				.getDocumentProvider());
		viewer.setContentProvider(outlineContentProvider);
		outlineLabelProvider = new OutlineLabelProvider();
		viewer.setLabelProvider(outlineLabelProvider);
		viewer.addSelectionChangedListener(this);

		// control is created after input is set
		if (input != null) {
			viewer.setInput(input);
		}
	}

	public void setInput(Object input) {
		this.input = (IEditorInput) input;
		update();
	}

	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		// find out which item in tree viewer we have selected, and set
		// highlight range accordingly
		ISelection selection = event.getSelection();
		if (selection.isEmpty())
			editor.resetHighlightRange();
		else {
			SimpleYangNode e = (SimpleYangNode) ((IStructuredSelection) selection)
					.getFirstElement();
			try {
				int start = editor.getInputDocument().getLineOffset(
						e.getLine() - 1);
				int length = editor.getInputDocument().getLineOffset(
						e.getLine())
						- 1
						- editor.getInputDocument().getLineOffset(
								e.getLine() - 1);
				editor.setHighlightRange(start, length, true);
			} catch (BadLocationException x) {
			} catch (IllegalArgumentException x) {
				editor.resetHighlightRange();
			}
		}
	}

	public void update() {
		// set the input so that the outlines parse can be called
		// update the tree viewer state
		TreeViewer viewer = getTreeViewer();
		if (viewer != null) {
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(input);
				//viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}
}
