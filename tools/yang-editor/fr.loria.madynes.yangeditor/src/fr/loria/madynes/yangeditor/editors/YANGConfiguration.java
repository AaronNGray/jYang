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
import org.eclipse.jface.text.formatter.*;
import org.eclipse.jface.text.presentation.*;
import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.source.*;
import org.eclipse.ui.editors.text.*;

import fr.loria.madynes.yangeditor.*;
import fr.loria.madynes.yangeditor.editors.format.*;
import fr.loria.madynes.yangeditor.editors.scanners.*;
import fr.loria.madynes.yangeditor.preferences.*;

/**
 * YANGConfiguration
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGConfiguration extends TextSourceViewerConfiguration {
	private YANGDoubleClickStrategy doubleClickStrategy;

	private YANGCodeScanner codeScanner;

	private ColorManager colorManager;

	public YANGConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				YANGPartitionScanner.YANG_BRACKET,
				YANGPartitionScanner.YANG_COMMENT };
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new YANGDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected YANGCodeScanner getYANGCodeScanner() {
		if (codeScanner == null) {
			codeScanner = new YANGCodeScanner(colorManager);
			codeScanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(YANGPreferences
							.getColor(YANGPreferences.KEY_COLOR_TEXT)))));
		}
		return codeScanner;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		Activator
				.getLogger()
				.config(
						Activator
								.getResourceString("Logger.Configuration.PresentationReconciler"));

		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				getYANGCodeScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(colorManager.getColor(YANGPreferences
						.getColor(YANGPreferences.KEY_COLOR_BRACKET))));
		reconciler.setDamager(ndr, YANGPartitionScanner.YANG_BRACKET);
		reconciler.setRepairer(ndr, YANGPartitionScanner.YANG_BRACKET);

		ndr = new NonRuleBasedDamagerRepairer(new TextAttribute(colorManager
				.getColor(YANGPreferences
						.getColor(YANGPreferences.KEY_COLOR_COMMENT))));
		reconciler.setDamager(ndr, YANGPartitionScanner.YANG_COMMENT);
		reconciler.setRepairer(ndr, YANGPartitionScanner.YANG_COMMENT);

		return reconciler;
	}

	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		ContentFormatter formatter = new ContentFormatter();
		YANGFormattingStrategy fs = new YANGFormattingStrategy();
		formatter.setFormattingStrategy(fs, IDocument.DEFAULT_CONTENT_TYPE);
		formatter.setFormattingStrategy(fs, YANGPartitionScanner.YANG_BRACKET);
		formatter.setFormattingStrategy(fs, YANGPartitionScanner.YANG_COMMENT);
		return formatter;
	}
}