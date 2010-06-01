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

import fr.loria.madynes.yangeditor.editors.rules.*;

/**
 * YANGPartitionScanner
 * 
 * @author Guillaume Gérard
 * 
 */
public class YANGPartitionScanner extends RuleBasedPartitionScanner {

	public final static String YANG_BRACKET = "__yang_bracket";
	public final static String YANG_COMMENT = "__yang_comment";

	public YANGPartitionScanner() {
		IToken yangHook = new Token(YANG_BRACKET);
		IToken yangComment = new Token(YANG_COMMENT);

		ArrayList<IPredicateRule> rules = new ArrayList<IPredicateRule>();
		BracketWordRule scr = new BracketWordRule(yangHook);
		scr.addWord("{");
		scr.addWord("}");
		rules.add(scr);
		rules.add(new MultiLineRule("/*", "*/", yangComment));
		rules.add(new EndOfLineRule("//", yangComment));

		IPredicateRule[] param = new IPredicateRule[rules.size()];
		rules.toArray(param);
		setPredicateRules(param);
	}
}
