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

import java.util.*;

import jyang.parser.*;

import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.texteditor.*;

import fr.loria.madynes.yangeditor.*;

public class OutlineContentProvider implements ITreeContentProvider {

	private Node root = null;
	private IDocumentProvider documentProvider;

	public OutlineContentProvider(IDocumentProvider provider) {
		super();
		this.documentProvider = provider;
	}

	public Object[] getChildren(Object parentElement) {
		Node elt = (Node) parentElement;
		if (elt != null && elt.jjtGetNumChildren() > 0) {
			ArrayList<Node> aln = new ArrayList<Node>();
			for (int i = 0; i < elt.jjtGetNumChildren(); i++) {
				Node n = elt.jjtGetChild(i);
				if (n instanceof YANG_StringRestriction) {
					Node[] nt = (Node[]) getChildren(n);
					for (Node mn : nt) {
						aln.add(mn);
					}
				} else {
					aln.add(n);
				}
			}
			Node[] nt = new Node[aln.size()];
			aln.toArray(nt);
			return nt;
		}
		return new Object[0];
	}

	public Object getParent(Object element) {
		Node n = ((Node) element).jjtGetParent();
		if (n instanceof YANG_StringRestriction) {
			n = (Node) getParent(n);
		}
		return n;
	}

	public boolean hasChildren(Object element) {
		return ((Node) element).jjtGetNumChildren() > 0;
	}

	public Object[] getElements(Object inputElement) {
		if (root != null && root.jjtGetNumChildren() > 0) {
			return getChildren(root);
		}
		return new Object[0];
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput != null) {
			IDocument document = documentProvider.getDocument(newInput);
			if (document != null) {
				Node rootElement = Parser.getYangSpecification();
				if (rootElement != null) {
					root = rootElement;
				}
			}
		}
	}
}