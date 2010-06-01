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

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

import fr.loria.madynes.yangeditor.*;

/**
 * YANGPartitioner
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGPartitioner extends FastPartitioner {

	public YANGPartitioner(IPartitionTokenScanner scanner,
			String[] legalContentTypes) {
		super(scanner, legalContentTypes);
	}

	public void connect(IDocument document, boolean delayInitialise) {
		super.connect(document, delayInitialise);
		printPartitions(document);
	}

	public void printPartitions(IDocument document) {
		StringBuffer buffer = new StringBuffer();
		ITypedRegion[] partitions = computePartitioning(0, document.getLength());
		for (int i = 0; i < partitions.length; i++) {
			try {
				buffer.append(Activator
						.getResourceString("Logger.Partitioner.Type")
						+ partitions[i].getType()
						+ ", "
						+ Activator
								.getResourceString("Logger.Partitioner.Offset")
						+ partitions[i].getOffset()
						+ ", "
						+ Activator
								.getResourceString("Logger.Partitioner.Length")
						+ partitions[i].getLength());
				buffer.append("\n");
				buffer.append(Activator
						.getResourceString("Logger.Partitioner.Text")
						+ "\n");
				buffer.append(document.get(partitions[i].getOffset(),
						partitions[i].getLength()));
				if (i < partitions.length - 1) {
					buffer.append("\n---------------------------\n\n\n");
				}
			} catch (BadLocationException e) {
			}
		}
		Activator
				.getLogger()
				.config(
						Activator.getResourceString("Logger.Partitioner.Begin")
								+ "\n"
								+ buffer.toString()
								+ "\n"
								+ Activator
										.getResourceString("Logger.Partitioner.End"));
	}
}
