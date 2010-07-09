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

import org.eclipse.jface.resource.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;

import fr.loria.madynes.yangeditor.*;

public class OutlineLabelProvider implements ILabelProvider {

	private HashMap<ImageDescriptor, Image> imageCache;

	public OutlineLabelProvider() {
		super();
		imageCache = new HashMap<ImageDescriptor, Image>();
	}

	public Image getImage(Object e) {
		ImageDescriptor descriptor = null;
		if (e instanceof YANG_Case) {
			descriptor = Activator.getImageDescriptor("case");
		} else if (e instanceof YANG_Choice) {
			descriptor = Activator.getImageDescriptor("choice");
		} else if (e instanceof YANG_Container) {
			descriptor = Activator.getImageDescriptor("container");
		} else if (e instanceof YANG_Leaf) {
			descriptor = Activator.getImageDescriptor("leaf");
		} else if (e instanceof YANG_Key) {
			descriptor = Activator.getImageDescriptor("leafkey");
		} else if (e instanceof YANG_LeafList) {
			descriptor = Activator.getImageDescriptor("leaflist");
		} else if (e instanceof YANG_List) {
			descriptor = Activator.getImageDescriptor("list");
		} else if (e instanceof YANG_Type) {
			descriptor = Activator.getImageDescriptor("type");
		} else if (e instanceof YANG_Grouping) {
			descriptor = Activator.getImageDescriptor("grouping");
		} else if (e instanceof YANG_Uses) {
			descriptor = Activator.getImageDescriptor("uses");
		} else if (e instanceof YANG_Module) {
			descriptor = Activator.getImageDescriptor("module");
		} else if (e instanceof YANG_SubModule) {
			descriptor = Activator.getImageDescriptor("module");
		}else {
			descriptor = Activator.getImageDescriptor("other");
		}		

		if (descriptor != null) {
			// obtain the cached image corresponding to the descriptor
			Image image = (Image) imageCache.get(descriptor);
			if (image == null) {
				image = descriptor.createImage();
				imageCache.put(descriptor, image);
			}

			return descriptor.createImage();
		}
		return null;
	}

	public String getText(Object e) {
		String res = null;
		if (e instanceof SimpleYangNode) {
			res = ((SimpleYangNode) e).getLabel();
			if (e instanceof Deviate) { // Deviate
				Deviate d = (Deviate) e;
				if (d.getUnits() != null) {
					res += " " + d.getUnits().getLabel();
				}
				if (d.getDefault() != null) {
					res += " " + d.getDefault().getLabel();
				}
			} else
			/* Begin of DocumentedNode */
			if (e instanceof YANG_Module) {
				res += " " + ((YANG_Module) e).getModule();
			} else if (e instanceof YANG_SubModule) {
				res += " " + ((YANG_SubModule) e).getSubModule();
			} else if (e instanceof YANG_Body) { // Body
				res += " " + ((YANG_Body) e).getBody();
			} else if (e instanceof YANG_Pattern) { // Pattern
				res += " " + ((YANG_Pattern) e).getPattern();
			} else if (e instanceof YANG_Must) { // Must
				res += " " + ((YANG_Must) e).getMust();
			} else if (e instanceof YANG_Length) { // Length
				res += " " + ((YANG_Length) e).getLength();
			} else if (e instanceof YANG_Range) { // Range
				res += " " + ((YANG_Range) e).getRange();
			} else if (e instanceof YANG_Refine) { // Refine
				res += ((YANG_Refine) e).getRefineNodeId();
			} else if (e instanceof YANG_Revision) { // Revision
				res += " " + ((YANG_Revision) e).getDate();
			} else if (e instanceof YANG_Status) { // Status
				res += " " + ((YANG_Status) e).getStatus();
			} else if (e instanceof YANG_When) { // When
				res += " " + ((YANG_When) e).getWhen();
			} else
			/* End of DocumentedNode */
			if (e instanceof YANG_Include) { // Include
				res += " " + ((YANG_Include) e).getIncludedModule();
			} else if (e instanceof YANG_Import) { // Import
				res += " " + ((YANG_Import) e).getImportedModule();
			} else if (e instanceof YANG_NameSpace) { // NameSpace
				res += " " + ((YANG_NameSpace) e).getNameSpace();
			} else if (e instanceof YANG_Argument) { // Argument
				res += " " + ((YANG_Argument) e).getArgument();
			} else if (e instanceof YANG_Base) { // Base
				res += " " + ((YANG_Base) e).getBase();
			} else if (e instanceof YANG_Belong) { // Belong
				res += " " + ((YANG_Belong) e).getBelong();
			} else if (e instanceof YANG_Bit) { // Bit
				res += " " + ((YANG_Bit) e).getBit();
			} else if (e instanceof YANG_Case) { // Case
				res += " " + ((YANG_Case) e).getCase();
			} else if (e instanceof YANG_Config) { // Config
				res += " " + ((YANG_Config) e).getConfigStr();
			} else if (e instanceof YANG_Contact) { // Contact
				res += " " + ((YANG_Contact) e).getContact();
			} else if (e instanceof YANG_Decimal64Spec) { // Decimal64Spec
				res += " " + ((YANG_Decimal64Spec) e).getFractionDigit();
			} else if (e instanceof YANG_Default) { // Default
				res += " " + ((YANG_Default) e).getDefault();
			} else if (e instanceof YANG_Description) { // Description
				res += " " + ((YANG_Description) e).getDescription();
			} else if (e instanceof YANG_Enum) { // Enum
				res += " " + ((YANG_Enum) e).getEnum();
			} else if (e instanceof YANG_ErrorAppt) { // ErrorAppt
				res += " " + ((YANG_ErrorAppt) e).getErrorAppt();
			} else if (e instanceof YANG_ErrorMessage) { // ErrorMessage
				res += " " + ((YANG_ErrorMessage) e).getErrorMessage();
			} else if (e instanceof YANG_IfFeature) { // IfFeature
				res += " " + ((YANG_IfFeature) e).getIfFeature();
			} else if (e instanceof YANG_Key) { // Key
				res += " " + ((YANG_Key) e).getKey();
			} else if (e instanceof YANG_Mandatory) { // Mandatory
				res += " " + ((YANG_Mandatory) e).getMandatory();
			} else if (e instanceof YANG_MaxElement) { // MaxElement
				res += " " + ((YANG_MaxElement) e).getMaxElement();
			} else if (e instanceof YANG_MinElement) { // MinElement
				res += " " + ((YANG_MinElement) e).getMinElement();
			} else if (e instanceof YANG_OrderedBy) { // OrderedBy
				res += " " + ((YANG_OrderedBy) e).getOrderedBy();
			} else if (e instanceof YANG_Organization) { // Organization
				res += " " + ((YANG_Organization) e).getOrganization();
			} else if (e instanceof YANG_Path) { // Path
				res += " " + ((YANG_Path) e).getPath();
			} else if (e instanceof YANG_Position) { // Position
				res += " " + ((YANG_Position) e).getPosition();
			} else if (e instanceof YANG_Prefix) { // Prefix
				res += " " + ((YANG_Prefix) e).getPrefix();
			} else if (e instanceof YANG_Presence) { // Presence
				res += " " + ((YANG_Presence) e).getPresence();
			} else if (e instanceof YANG_Reference) { // Reference
				res += " " + ((YANG_Reference) e).getReference();
			} else if (e instanceof YANG_Status) { // Status
				res += " " + ((YANG_Status) e).getStatus();
			} else if (e instanceof YANG_Type) { // Type
				res += " " + ((YANG_Type) e).getType();
			} else if (e instanceof YANG_Unique) { // Unique
				res += " " + ((YANG_Unique) e).getUnique();
			} else if (e instanceof YANG_Units) { // Units
				res += " " + ((YANG_Units) e).getUnits();
			} else if (e instanceof YANG_Value) { // Value
				res += " " + ((YANG_Value) e).getValue();
			} else if (e instanceof YANG_YangVersion) { // YangVersion
				res += " " + ((YANG_YangVersion) e).getYangVersion();
			} else if (e instanceof YANG_Yin) { // Yin
				res += " " + ((YANG_Yin) e).getYin();
			}
		}
		return res;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
		for (Image i : imageCache.values()) {
			i.dispose();
		}
		imageCache.clear();
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}