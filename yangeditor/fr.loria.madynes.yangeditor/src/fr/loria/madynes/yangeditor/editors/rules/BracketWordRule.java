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
package fr.loria.madynes.yangeditor.editors.rules;

import org.eclipse.jface.text.rules.*;

public class BracketWordRule extends WordRule implements IPredicateRule {

	static class WordDetector implements IWordDetector {

		public boolean isWordStart(char c) {
			return (c == '{' || c == '}');
		}

		public boolean isWordPart(char c) {
			return (c == '{' || c == '}');
		}
	}

	private IToken token;

	public BracketWordRule(IToken t) {
		super(new WordDetector());
		token = t;
	}

	public void addWord(String word) {
		super.addWord(word, token);
	}

	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

	public IToken getSuccessToken() {
		return token;
	}
}