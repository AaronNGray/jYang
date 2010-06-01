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

import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.*;
import org.eclipse.ui.editors.text.*;

import fr.loria.madynes.yangeditor.*;
import fr.loria.madynes.yangeditor.editors.scanners.*;

/**
 * YANGDocumentProvider
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGDocumentProvider extends FileDocumentProvider {

	public YANGDocumentProvider() {
		super();
	}

	protected IDocument createDocument(Object element) throws CoreException {
		Activator
		.getLogger()
		.config(Activator.getResourceString("Logger.DocumentProvider.CreateDocument"));
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner = new YANGPartitioner(
					new YANGPartitionScanner(), new String[] {
							IDocument.DEFAULT_CONTENT_TYPE,
							YANGPartitionScanner.YANG_BRACKET,
							YANGPartitionScanner.YANG_COMMENT });
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}