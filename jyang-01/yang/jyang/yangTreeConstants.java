package jyang;
/*
 * Copyright 2008 Emmanuel Nataf, Olivier Festor
 * 
 * This file is part of jyang.

    jyang is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jyang is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with jyang.  If not, see <http://www.gnu.org/licenses/>.

 */

public interface yangTreeConstants
{
  public int JJTVOID = 0;
  public int JJTMODULE = 1;
  public int JJTSUBMODULE = 2;
  public int JJTYANGVERSION = 3;
  public int JJTIMPORT = 4;
  public int JJTINCLUDE = 5;
  public int JJTNAMESPACE = 6;
  public int JJTPREFIX = 7;
  public int JJTBELONG = 8;
  public int JJTORGANIZATION = 9;
  public int JJTCONTACT = 10;
  public int JJTDESCRIPTION = 11;
  public int JJTREFERENCE = 12;
  public int JJTUNITS = 13;
  public int JJTREVISION = 14;
  public int JJTEXTENSION = 15;
  public int JJTARGUMENT = 16;
  public int JJTYIN = 17;
  public int JJTTYPEDEF = 18;
  public int JJTTYPE = 19;
  public int JJTRANGE = 20;
  public int JJTSTRINGRESTRICTION = 21;
  public int JJTLENGTH = 22;
  public int JJTPATTERN = 23;
  public int JJTDEFAULT = 24;
  public int JJTENUM = 25;
  public int JJTPATH = 26;
  public int JJTUNIONSPECIFICATION = 27;
  public int JJTBITSPECIFICATION = 28;
  public int JJTBIT = 29;
  public int JJTPOSITION = 30;
  public int JJTSTATUS = 31;
  public int JJTCONFIG = 32;
  public int JJTMANDATORY = 33;
  public int JJTPRESENCE = 34;
  public int JJTORDEREDBY = 35;
  public int JJTMUST = 36;
  public int JJTERRORMESSAGE = 37;
  public int JJTERRORAPPT = 38;
  public int JJTMINELEMENT = 39;
  public int JJTMAXELEMENT = 40;
  public int JJTVALUE = 41;
  public int JJTGROUPING = 42;
  public int JJTCONTAINER = 43;
  public int JJTLEAF = 44;
  public int JJTLEAFLIST = 45;
  public int JJTLIST = 46;
  public int JJTKEY = 47;
  public int JJTUNIQUE = 48;
  public int JJTCHOICE = 49;
  public int JJTCASE = 50;
  public int JJTANYXML = 51;
  public int JJTUSES = 52;
  public int JJTREFINELEAF = 53;
  public int JJTREFINELEAFLIST = 54;
  public int JJTREFINELIST = 55;
  public int JJTREFINECHOICE = 56;
  public int JJTREFINECASE = 57;
  public int JJTREFINECONTAINER = 58;
  public int JJTREFINEANYXML = 59;
  public int JJTUNKNOWN = 60;
  public int JJTAUGMENT = 61;
  public int JJTWHEN = 62;
  public int JJTRPC = 63;
  public int JJTINPUT = 64;
  public int JJTOUTPUT = 65;
  public int JJTNOTIFICATION = 66;


  public String[] jjtNodeName = {
    "void",
    "Module",
    "SubModule",
    "YangVersion",
    "Import",
    "Include",
    "NameSpace",
    "Prefix",
    "Belong",
    "Organization",
    "Contact",
    "Description",
    "Reference",
    "Units",
    "Revision",
    "Extension",
    "Argument",
    "Yin",
    "TypeDef",
    "Type",
    "Range",
    "StringRestriction",
    "Length",
    "Pattern",
    "Default",
    "Enum",
    "Path",
    "UnionSpecification",
    "BitSpecification",
    "Bit",
    "Position",
    "Status",
    "Config",
    "Mandatory",
    "Presence",
    "OrderedBy",
    "Must",
    "ErrorMessage",
    "ErrorAppt",
    "MinElement",
    "MaxElement",
    "Value",
    "Grouping",
    "Container",
    "Leaf",
    "LeafList",
    "List",
    "Key",
    "Unique",
    "Choice",
    "Case",
    "AnyXml",
    "Uses",
    "RefineLeaf",
    "RefineLeafList",
    "RefineList",
    "RefineChoice",
    "RefineCase",
    "RefineContainer",
    "RefineAnyXml",
    "Unknown",
    "Augment",
    "When",
    "Rpc",
    "Input",
    "Output",
    "Notification",
  };
}