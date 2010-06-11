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
package fr.loria.madynes.yangeditor.editors.scanners;

import java.util.*;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;

import fr.loria.madynes.yangeditor.editors.*;
import fr.loria.madynes.yangeditor.editors.rules.*;
import fr.loria.madynes.yangeditor.preferences.*;

/**
 * YANGCodeScanner
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGCodeScanner extends RuleBasedScanner {

	private static String[] tokens = new String[] { "argument", "base",
			"belongs-to", "bit", "config", "default", "enum", "error-app-tag",
			"error-message", "deviation", "deviate", "feature",
			"fraction-digits", "identity", "if-feature", "length", "mandatory",
			"max-elements", "min-elements", "must", "ordered-by", "path",
			"pattern", "position", "presence", "range", "refine",
			"require-instance", "status", "unique", "units", "value", "when",
			"add", "current", "delete", "deprecated", "false", "max", "min",
			"not-supported", "obsolete", "replace", "system", "true",
			"unbounded", "user" };

	private static String[] datanodes = new String[] { "anyxml", "augment",
			"case", "choice", "contact", "container", "description",
			"extension", "grouping", "import", "include", "input", "key",
			"leaf", "leaf-list", "list", "module", "namespace", "notification",
			"organization", "output", "prefix", "reference", "revision",
			"revision-date", "rpc", "submodule", "type", "typedef", "uses",
			"yang-version", "yin-element" };

	private static String[] types = new String[] { "binary", "bits", "boolean",
			"decimal64", "empty", "enumeration", "identityref",
			"instance-identifier", "int8", "int16", "int32", "int64",
			"leafref", "string", "uint8", "uint16", "uint32", "uint64", "union" };

	public YANGCodeScanner(ColorManager manager) {
		IToken string = new Token(new TextAttribute(manager
				.getColor(YANGPreferences.getStringColor())));

		IToken token = new Token(new TextAttribute(manager
				.getColor(YANGPreferences.getTokenColor())));
		
		IToken datanode = new Token(new TextAttribute(manager
				.getColor(YANGPreferences.getDataNodeColor()),null,SWT.BOLD));

		IToken type = new Token(new TextAttribute(manager
				.getColor(YANGPreferences.getTypeColor())));

		YANGWordRule wr = new YANGWordRule();
		wr.addWords(tokens, token);
		wr.addWords(datanodes, datanode);
		wr.addWords(types, type);

		List<IRule> rules = new ArrayList<IRule>();
		// Add rule for string.
		rules.add(new MultiLineRule("\"", "\"", string));
		rules.add(new MultiLineRule("'", "'", string));
		// Add rule for tokens and types.
		rules.add(wr);

		rules.add(new WhitespaceRule(new YANGWhitespaceDetector()));

		IRule[] param = new IRule[rules.size()];
		rules.toArray(param);
		setRules(param);
	}
}
