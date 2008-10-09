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


public interface yangConstants {

  int EOF = 0;
  int DECVALUE = 7;
  int NEGDECVALUE = 8;
  int NONZERO = 9;
  int DQUOTE = 10;
  int SQUOTE = 11;
  int DDOT = 12;
  int DATE = 13;
  int FDIGIT = 14;
  int TDIGIT = 15;
  int STRING = 16;
  int FLOAT = 17;
  int EXPONENT = 18;
  int anyxmlkeyword = 19;
  int argumentkeyword = 20;
  int augmentkeyword = 21;
  int belongstokeyword = 22;
  int bitkeyword = 23;
  int casekeyword = 24;
  int choicekeyword = 25;
  int configkeyword = 26;
  int contactkeyword = 27;
  int containerkeyword = 28;
  int defaultkeyword = 29;
  int descriptionkeyword = 30;
  int enumkeyword = 31;
  int errorapptagkeyword = 32;
  int errormessagekeyword = 33;
  int extensionkeyword = 34;
  int groupingkeyword = 35;
  int importkeyword = 36;
  int includekeyword = 37;
  int inputkeyword = 38;
  int keykeyword = 39;
  int leafkeyword = 40;
  int leaflistkeyword = 41;
  int lengthkeyword = 42;
  int listkeyword = 43;
  int mandatorykeyword = 44;
  int maxelementskeyword = 45;
  int minelementskeyword = 46;
  int modulekeyword = 47;
  int mustkeyword = 48;
  int namespacekeyword = 49;
  int notificationkeyword = 50;
  int orderedbykeyword = 51;
  int organizationkeyword = 52;
  int outputkeyword = 53;
  int pathkeyword = 54;
  int patternkeyword = 55;
  int positionkeyword = 56;
  int prefixkeyword = 57;
  int presencekeyword = 58;
  int rangekeyword = 59;
  int referencekeyword = 60;
  int revisionkeyword = 61;
  int rpckeyword = 62;
  int statuskeyword = 63;
  int submodulekeyword = 64;
  int typekeyword = 65;
  int typedefkeyword = 66;
  int uniquekeyword = 67;
  int unitskeyword = 68;
  int useskeyword = 69;
  int valuekeyword = 70;
  int whenkeyword = 71;
  int yangversionkeyword = 72;
  int yinelementkeyword = 73;
  int currentkeyword = 74;
  int deprecatedkeyword = 75;
  int falsekeyword = 76;
  int minkeyword = 77;
  int maxkeyword = 78;
  int nankeyword = 79;
  int neginfkeyword = 80;
  int obsoletekeyword = 81;
  int posinfkeyword = 82;
  int systemkeyword = 83;
  int thisvariablekeyword = 84;
  int truekeyword = 85;
  int unboundedkeyword = 86;
  int userkeyword = 87;
  int IDENTIFIER = 88;
  int LETTER = 89;
  int DIGIT = 90;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<token of kind 5>",
    "<token of kind 6>",
    "<DECVALUE>",
    "<NEGDECVALUE>",
    "<NONZERO>",
    "\"\\\"\"",
    "\"\\\'\"",
    "\"..\"",
    "<DATE>",
    "<FDIGIT>",
    "<TDIGIT>",
    "<STRING>",
    "<FLOAT>",
    "<EXPONENT>",
    "\"anyxml\"",
    "\"argument\"",
    "\"augment\"",
    "\"belongs-to\"",
    "\"bit\"",
    "\"case\"",
    "\"choice\"",
    "\"config\"",
    "\"contact\"",
    "\"container\"",
    "\"default\"",
    "\"description\"",
    "\"enum\"",
    "\"error-app-tag\"",
    "\"error-message\"",
    "\"extension\"",
    "\"grouping\"",
    "\"import\"",
    "\"include\"",
    "\"input\"",
    "\"key\"",
    "\"leaf\"",
    "\"leaf-list\"",
    "\"length\"",
    "\"list\"",
    "\"mandatory\"",
    "\"max-elements\"",
    "\"min-elements\"",
    "\"module\"",
    "\"must\"",
    "\"namespace\"",
    "\"notification\"",
    "\"ordered-by\"",
    "\"organization\"",
    "\"output\"",
    "\"path\"",
    "\"pattern\"",
    "\"position\"",
    "\"prefix\"",
    "\"presence\"",
    "\"range\"",
    "\"reference\"",
    "\"revision\"",
    "\"rpc\"",
    "\"status\"",
    "\"submodule\"",
    "\"type\"",
    "\"typedef\"",
    "\"unique\"",
    "\"units\"",
    "\"uses\"",
    "\"value\"",
    "\"when\"",
    "\"yang-version\"",
    "\"yin-element\"",
    "\"current\"",
    "\"deprecated\"",
    "\"false\"",
    "\"min\"",
    "\"max\"",
    "\"NaN\"",
    "\"-INF\"",
    "\"obsolete\"",
    "\"INF\"",
    "\"system\"",
    "\"$this\"",
    "\"true\"",
    "\"unbounded\"",
    "\"user\"",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "\"{\"",
    "\"}\"",
    "\";\"",
    "\":\"",
    "\"/\"",
    "\".\"",
    "\"|\"",
    "\"[\"",
    "\"]\"",
    "\"=\"",
    "\"+\"",
  };

}
