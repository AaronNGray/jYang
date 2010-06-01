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
package fr.loria.madynes.yangeditor.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.*;

import fr.loria.madynes.yangeditor.*;

public class YANGPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	// Keys
	public static final String KEY_PARSER_ACTIVE = "parser_active";
	public static final String KEY_COLOR_TEXT = "color_text";
	public static final String KEY_COLOR_STRING = "color_string";
	public static final String KEY_COLOR_TOKEN = "color_token";
	public static final String KEY_COLOR_TYPE = "color_type";
	public static final String KEY_COLOR_COMMENT = "color_comment";
	public static final String KEY_COLOR_BRACKET = "color_bracket";

	// Default values
	private static boolean DEFAULT_PARSER_ACTIVE = true;
	private static RGB DEFAULT_COLOR_TEXT = new RGB(0, 0, 0);
	private static RGB DEFAULT_COLOR_STRING = new RGB(0, 0, 128);
	private static RGB DEFAULT_COLOR_TOKEN = new RGB(128, 0, 128);
	private static RGB DEFAULT_COLOR_TYPE = new RGB(0, 128, 128);
	private static RGB DEFAULT_COLOR_COMMENT = new RGB(0, 128, 0);
	private static RGB DEFAULT_COLOR_BRACKET = new RGB(128, 0, 0);

	// Fields in the option pane
	protected BooleanFieldEditor bParser;
	protected ColorFieldEditor cText;
	protected ColorFieldEditor cString;
	protected ColorFieldEditor cToken;
	protected ColorFieldEditor cType;
	protected ColorFieldEditor cComment;
	protected ColorFieldEditor cBracket;

	public YANGPreferences() {
		super(Activator.getResourceString("Preference.Title"),
				FieldEditorPreferencePage.GRID);
	}

	@Override
	protected void createFieldEditors() {
		bParser = new BooleanFieldEditor(KEY_PARSER_ACTIVE, Activator
				.getResourceString("Preference.Parser"), getFieldEditorParent());
		cText = new ColorFieldEditor(KEY_COLOR_TEXT, Activator
				.getResourceString("Preference.Text"), getFieldEditorParent());
		cString = new ColorFieldEditor(KEY_COLOR_STRING, Activator
				.getResourceString("Preference.String"), getFieldEditorParent());
		cToken = new ColorFieldEditor(KEY_COLOR_TOKEN, Activator
				.getResourceString("Preference.Token"), getFieldEditorParent());
		cType = new ColorFieldEditor(KEY_COLOR_TYPE, Activator
				.getResourceString("Preference.Type"), getFieldEditorParent());
		cComment = new ColorFieldEditor(KEY_COLOR_COMMENT, Activator
				.getResourceString("Preference.Comment"),
				getFieldEditorParent());
		cBracket = new ColorFieldEditor(KEY_COLOR_BRACKET, Activator
				.getResourceString("Preference.Bracket"),
				getFieldEditorParent());

		addField(bParser);
		addField(cText);
		addField(cString);
		addField(cToken);
		addField(cType);
		addField(cComment);
		addField(cBracket);

		// Set the default values
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(KEY_PARSER_ACTIVE, DEFAULT_PARSER_ACTIVE);
		PreferenceConverter.setDefault(store, KEY_COLOR_TEXT,
				DEFAULT_COLOR_TEXT);
		PreferenceConverter.setDefault(store, KEY_COLOR_STRING,
				DEFAULT_COLOR_STRING);
		PreferenceConverter.setDefault(store, KEY_COLOR_TOKEN,
				DEFAULT_COLOR_TOKEN);
		PreferenceConverter.setDefault(store, KEY_COLOR_TYPE,
				DEFAULT_COLOR_TYPE);
		PreferenceConverter.setDefault(store, KEY_COLOR_COMMENT,
				DEFAULT_COLOR_COMMENT);
		PreferenceConverter.setDefault(store, KEY_COLOR_BRACKET,
				DEFAULT_COLOR_BRACKET);
	}

	@Override
	public void init(IWorkbench workbench) {
		// Initialize the preference store we wish to use
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Activator.getResourceString("Preference.Description"));
	}

	public static boolean getParserActive() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_PARSER_ACTIVE)) {
			return store.getBoolean(KEY_PARSER_ACTIVE);
		} else {
			return DEFAULT_PARSER_ACTIVE;
		}
	}

	public static RGB getTextColor() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_COLOR_TEXT)) {
			return PreferenceConverter.getColor(store, KEY_COLOR_TEXT);
		} else {
			return DEFAULT_COLOR_TEXT;
		}
	}

	public static RGB getStringColor() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_COLOR_STRING)) {
			return PreferenceConverter.getColor(store, KEY_COLOR_STRING);
		} else {
			return DEFAULT_COLOR_STRING;
		}
	}

	public static RGB getTokenColor() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_COLOR_TOKEN)) {
			return PreferenceConverter.getColor(store, KEY_COLOR_TOKEN);
		} else {
			return DEFAULT_COLOR_TOKEN;
		}
	}

	public static RGB getTypeColor() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_COLOR_TYPE)) {
			return PreferenceConverter.getColor(store, KEY_COLOR_TYPE);
		} else {
			return DEFAULT_COLOR_TYPE;
		}
	}

	public static RGB getCommentColor() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_COLOR_COMMENT)) {
			return PreferenceConverter.getColor(store, KEY_COLOR_COMMENT);
		} else {
			return DEFAULT_COLOR_COMMENT;
		}
	}

	public static RGB getBracketColor() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.contains(KEY_COLOR_BRACKET)) {
			return PreferenceConverter.getColor(store, KEY_COLOR_BRACKET);
		} else {
			return DEFAULT_COLOR_BRACKET;
		}
	}
}