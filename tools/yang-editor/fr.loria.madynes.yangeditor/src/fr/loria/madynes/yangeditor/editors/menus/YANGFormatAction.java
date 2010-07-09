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
package fr.loria.madynes.yangeditor.editors.menus;

import org.eclipse.jface.action.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.*;
import org.eclipse.swt.SWT;
import org.eclipse.ui.commands.ICommandService;

import fr.loria.madynes.yangeditor.*;

public class YANGFormatAction extends Action {
	private ITextOperationTarget target;

	public YANGFormatAction(ITextOperationTarget target) {
		this.target = target;
		setText(Activator.getResourceString("Action.Format.Text"));
		setToolTipText(Activator.getResourceString("Action.Format.ToolTipText"));
		setAccelerator(SWT.CTRL | SWT.SHIFT | 'F');
		
	}

	public void run() {
		target.doOperation(ISourceViewer.FORMAT);
	}
}