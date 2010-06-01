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

	private static String[] tokens = new String[] { "anyxml", "argument",
			"augment", "base", "belongs-to", "bit", "case", "choice", "config",
			"contact", "container", "default", "description", "enum",
			"error-app-tag", "error-message", "extension", "deviation",
			"deviate", "feature", "fraction-digits", "grouping", "identity",
			"if-feature", "import", "include", "input", "key", "leaf",
			"leaf-list", "length", "list", "mandatory", "max-elements",
			"min-elements", "module", "must", "namespace", "notification",
			"ordered-by", "organization", "output", "path", "pattern",
			"position", "prefix", "presence", "range", "reference", "refine",
			"require-instance", "revision", "revision-date", "rpc", "status",
			"submodule", "type", "typedef", "unique", "units", "uses", "value",
			"when", "yang-version", "yin-element", "add", "current", "delete",
			"deprecated", "false", "max", "min", "not-supported", "obsolete",
			"replace", "system", "true", "unbounded", "user" };

	private static String[] types = new String[] { "binary", "bits", "boolean",
			"decimal64", "empty", "enumeration", "identityref",
			"instance-identifier", "int8", "int16", "int32", "int64",
			"leafref", "string", "uint8", "uint16", "uint32", "uint64", "union" };

	public YANGCodeScanner(ColorManager manager) {
		IToken string = new Token(new TextAttribute(manager
				.getColor(YANGPreferences
						.getColor(YANGPreferences.KEY_COLOR_STRING))));

		IToken token = new Token(new TextAttribute(manager
				.getColor(YANGPreferences
						.getColor(YANGPreferences.KEY_COLOR_TOKEN))));

		IToken type = new Token(new TextAttribute(manager
				.getColor(YANGPreferences
						.getColor(YANGPreferences.KEY_COLOR_TYPE))));

		YANGWordRule wr = new YANGWordRule();
		wr.addWords(tokens, token);
		wr.addWords(types, type);

		List<IRule> rules = new ArrayList<IRule>();
		// Add rule for string.
		rules.add(new SingleLineRule("\"", "\"", string));
		rules.add(new SingleLineRule("'", "'", string));
		// Add rule for tokens and types.
		rules.add(wr);

		rules.add(new WhitespaceRule(new YANGWhitespaceDetector()));

		IRule[] param = new IRule[rules.size()];
		rules.toArray(param);
		setRules(param);
	}
}
