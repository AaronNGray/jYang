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
public class yang/*@bgen(jjtree)*/implements yangTreeConstants, yangConstants {/*@bgen(jjtree)*/
  protected static JJTyangState jjtree = new JJTyangState();

  static final public YANG_Specification Start() throws ParseException {
YANG_Specification n = null;
    if (jj_2_1(2)) {
      n = module();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case submodulekeyword:
        n = submodule();
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Specification module() throws ParseException {
 /*@bgen(jjtree) Module */
 YANG_Module jjtn000 = new YANG_Module(JJTMODULE);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
Node n=null;
YANG_Header h = null;
YANG_Linkage l = null;
YANG_Meta m = null;
YANG_Revision r = null;
YANG_Body b = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(modulekeyword);
      t = jj_consume_token(IDENTIFIER);
                jjtn000.setName(t.image);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
      jj_consume_token(91);
      label_1:
      while (true) {
        if (jj_2_2(2)) {
          ;
        } else {
          break label_1;
        }
        un = unknownstatement();
                                                             jjtn000.addUnknown(un);
      }
      label_2:
      while (true) {
        h = moduleheaderstmts();
                          jjtn000.addHeader(h);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case namespacekeyword:
        case prefixkeyword:
        case yangversionkeyword:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
      }
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case importkeyword:
        case includekeyword:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_3;
        }
        l = linkagestmts();
                        jjtn000.addLinkage(l);
      }
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case contactkeyword:
        case descriptionkeyword:
        case organizationkeyword:
        case referencekeyword:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_4;
        }
        m = metastmts();
                           jjtn000.addMeta(m);
      }
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case revisionkeyword:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_5;
        }
        r = revisionstmt();
                        jjtn000.addRevision(r);
      }
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case extensionkeyword:
        case groupingkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case notificationkeyword:
        case rpckeyword:
        case typedefkeyword:
        case useskeyword:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_6;
        }
        b = bodystmts();
                        jjtn000.addBody(b);
      }
      jj_consume_token(92);
                          jjtree.closeNodeScope(jjtn000, true);
                          jjtc000 = false;
                        {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Specification submodule() throws ParseException {
 /*@bgen(jjtree) SubModule */
YANG_SubModule jjtn000 = new YANG_SubModule(JJTSUBMODULE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);YANG_Header sh = null;
YANG_Linkage l = null;
YANG_Meta s = null;
YANG_Revision r = null;
YANG_Body b = null;
YANG_Unknown un = null;
Token t;
    try {
      jj_consume_token(submodulekeyword);
      t = jj_consume_token(IDENTIFIER);
                jjtn000.setSubModule(t.image);
      jj_consume_token(91);
      label_7:
      while (true) {
        if (jj_2_3(2)) {
          ;
        } else {
          break label_7;
        }
        un = unknownstatement();
                                                  jjtn000.addUnknown(un);
      }
      label_8:
      while (true) {
        sh = submoduleheaderstmts();
                jjtn000.addHeader(sh);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case belongstokeyword:
        case yangversionkeyword:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_8;
        }
      }
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case importkeyword:
        case includekeyword:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_9;
        }
        l = linkagestmts();
                jjtn000.addLinkage(l);
      }
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case contactkeyword:
        case descriptionkeyword:
        case organizationkeyword:
        case referencekeyword:
          ;
          break;
        default:
          jj_la1[8] = jj_gen;
          break label_10;
        }
        s = metastmts();
                jjtn000.addMeta(s);
      }
      label_11:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case revisionkeyword:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_11;
        }
        r = revisionstmt();
                jjtn000.addRevision(r);
      }
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case extensionkeyword:
        case groupingkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case notificationkeyword:
        case rpckeyword:
        case typedefkeyword:
        case useskeyword:
          ;
          break;
        default:
          jj_la1[10] = jj_gen;
          break label_12;
        }
        b = bodystmts();
                jjtn000.addBody(b);
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Header moduleheaderstmts() throws ParseException {
YANG_Header h = null;
YANG_Unknown un = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case yangversionkeyword:
      h = yangversionstmt();
      label_13:
      while (true) {
        if (jj_2_4(2)) {
          ;
        } else {
          break label_13;
        }
        un = unknownstatement();
                                                                     h.addUnknown(un);
      }
      break;
    case namespacekeyword:
      h = namespacestmt();
      label_14:
      while (true) {
        if (jj_2_5(2)) {
          ;
        } else {
          break label_14;
        }
        un = unknownstatement();
                                                                   h.addUnknown(un);
      }
      break;
    case prefixkeyword:
      h = prefixstmt();
      label_15:
      while (true) {
        if (jj_2_6(2)) {
          ;
        } else {
          break label_15;
        }
        un = unknownstatement();
                                                                h.addUnknown(un);
      }
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return (YANG_Header)h;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Header submoduleheaderstmts() throws ParseException {
YANG_Header h = null;
YANG_Unknown un = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case belongstokeyword:
      h = belongstostmt();
      label_16:
      while (true) {
        if (jj_2_7(2)) {
          ;
        } else {
          break label_16;
        }
        un = unknownstatement();
                                                                   h.addUnknown(un);
      }
      break;
    case yangversionkeyword:
      h = yangversionstmt();
      label_17:
      while (true) {
        if (jj_2_8(2)) {
          ;
        } else {
          break label_17;
        }
        un = unknownstatement();
                                                                     h.addUnknown(un);
      }
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return (YANG_Header)h;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Meta metastmts() throws ParseException {
YANG_Meta n = null;
YANG_Unknown un = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case organizationkeyword:
      n = organizationstmt();
      label_18:
      while (true) {
        if (jj_2_9(2)) {
          ;
        } else {
          break label_18;
        }
        un = unknownstatement();
                                                                      n.addUnknown(un);
      }
      break;
    case contactkeyword:
      n = contactstmt();
      label_19:
      while (true) {
        if (jj_2_10(2)) {
          ;
        } else {
          break label_19;
        }
        un = unknownstatement();
                                                                 n.addUnknown(un);
      }
      break;
    case descriptionkeyword:
      n = descriptionstmt();
      label_20:
      while (true) {
        if (jj_2_11(2)) {
          ;
        } else {
          break label_20;
        }
        un = unknownstatement();
                                                                     n.addUnknown(un);
      }
      break;
    case referencekeyword:
      n = referencestmt();
      label_21:
      while (true) {
        if (jj_2_12(2)) {
          ;
        } else {
          break label_21;
        }
        un = unknownstatement();
                                                                   n.addUnknown(un);
      }
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return (YANG_Meta) n;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Linkage linkagestmts() throws ParseException {
Node n = null;
YANG_Unknown un = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case importkeyword:
      n = importstmt();
      label_22:
      while (true) {
        if (jj_2_13(2)) {
          ;
        } else {
          break label_22;
        }
        un = unknownstatement();
                                                                n.addUnknown(un);
      }
      break;
    case includekeyword:
      n = includestmt();
      label_23:
      while (true) {
        if (jj_2_14(2)) {
          ;
        } else {
          break label_23;
        }
        un = unknownstatement();
                                                                 n.addUnknown(un);
      }
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return (YANG_Linkage) n;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Body bodystmts() throws ParseException {
YANG_Body b = null;
YANG_Unknown un = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case extensionkeyword:
      b = extensionstmt();
      break;
    case typedefkeyword:
      b = typedefstmt();
      break;
    case groupingkeyword:
      b = groupingstmt();
      break;
    case anyxmlkeyword:
    case augmentkeyword:
    case choicekeyword:
    case containerkeyword:
    case leafkeyword:
    case leaflistkeyword:
    case listkeyword:
    case useskeyword:
      b = datadefstmt();
      break;
    case rpckeyword:
      b = rpcstmt();
      break;
    case notificationkeyword:
      b = notificationstmt();
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    label_24:
    while (true) {
      if (jj_2_15(2)) {
        ;
      } else {
        break label_24;
      }
      un = unknownstatement();
                                                   b.addUnknown(un);
    }
                {if (true) return b;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_DataDef datadefstmt() throws ParseException {
YANG_DataDef d = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case containerkeyword:
      d = containerstmt();
      break;
    case leafkeyword:
      d = leafstmt();
      break;
    case leaflistkeyword:
      d = leafliststmt();
      break;
    case listkeyword:
      d = liststmt();
      break;
    case choicekeyword:
      d = choicestmt();
      break;
    case anyxmlkeyword:
      d = anyxmlstmt();
      break;
    case useskeyword:
      d = usesstmt();
      break;
    case augmentkeyword:
      d = augmentstmt();
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return d;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_CaseDef casedatadefstmt() throws ParseException {
YANG_CaseDef d = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case containerkeyword:
      d = containerstmt();
      break;
    case leafkeyword:
      d = leafstmt();
      break;
    case leaflistkeyword:
      d = leafliststmt();
      break;
    case listkeyword:
      d = liststmt();
      break;
    case anyxmlkeyword:
      d = anyxmlstmt();
      break;
    case useskeyword:
      d = usesstmt();
      break;
    case augmentkeyword:
      d = augmentstmt();
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return d;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_YangVersion yangversionstmt() throws ParseException {
 /*@bgen(jjtree) YangVersion */
YANG_YangVersion jjtn000 = new YANG_YangVersion(JJTYANGVERSION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
YANG_Unknown un = null;
    try {
      jj_consume_token(yangversionkeyword);
      t = jj_consume_token(DECVALUE);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_25:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[18] = jj_gen;
            break label_25;
          }
          un = unknownstatement();
                                                                                 jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setVersion(t.image);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Import importstmt() throws ParseException {
 /*@bgen(jjtree) Import */
YANG_Import jjtn000 = new YANG_Import(JJTIMPORT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
YANG_Prefix n = null;
String i;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(importkeyword);
      i = identifierstr();
                jjtn000.setIdentifier(i);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
      jj_consume_token(91);
      label_26:
      while (true) {
        if (jj_2_16(2)) {
          ;
        } else {
          break label_26;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      n = prefixstmt();
      label_27:
      while (true) {
        if (jj_2_17(2)) {
          ;
        } else {
          break label_27;
        }
        un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                 jjtn000.setPrefix(n);
                 {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Include includestmt() throws ParseException {
 /*@bgen(jjtree) Include */
YANG_Include jjtn000 = new YANG_Include(JJTINCLUDE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String i;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(includekeyword);
      i = identifierstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_28:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[20] = jj_gen;
            break label_28;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setIdentifier(i);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_NameSpace namespacestmt() throws ParseException {
 /*@bgen(jjtree) NameSpace */
YANG_NameSpace jjtn000 = new YANG_NameSpace(JJTNAMESPACE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String uri;
Token t;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(namespacekeyword);
      uri = uristr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_29:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[22] = jj_gen;
            break label_29;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setNameSpace(uri);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String uristr() throws ParseException {
String s;
    //< a sring which matches the rule
                            //   URI in RFC 3986 >
            s = string();
                {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Prefix prefixstmt() throws ParseException {
 /*@bgen(jjtree) Prefix */
YANG_Prefix jjtn000 = new YANG_Prefix(JJTPREFIX);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String s;
Token t;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(prefixkeyword);
      s = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_30:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[24] = jj_gen;
            break label_30;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setPrefix(s);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Belong belongstostmt() throws ParseException {
 /*@bgen(jjtree) Belong */
YANG_Belong jjtn000 = new YANG_Belong(JJTBELONG);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String b = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(belongstokeyword);
      b = identifierstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_31:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[26] = jj_gen;
            break label_31;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setBelong(b);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Organization organizationstmt() throws ParseException {
 /*@bgen(jjtree) Organization */
YANG_Organization jjtn000 = new YANG_Organization(JJTORGANIZATION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String s;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(organizationkeyword);
      s = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_32:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[28] = jj_gen;
            break label_32;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[29] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setOrganization(s);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Contact contactstmt() throws ParseException {
 /*@bgen(jjtree) Contact */
YANG_Contact jjtn000 = new YANG_Contact(JJTCONTACT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String c;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(contactkeyword);
      c = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_33:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[30] = jj_gen;
            break label_33;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[31] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setContact(c);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Description descriptionstmt() throws ParseException {
 /*@bgen(jjtree) Description */
YANG_Description jjtn000 = new YANG_Description(JJTDESCRIPTION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String d;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(descriptionkeyword);
      d = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_34:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[32] = jj_gen;
            break label_34;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[33] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setDescription(d);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Reference referencestmt() throws ParseException {
 /*@bgen(jjtree) Reference */
YANG_Reference jjtn000 = new YANG_Reference(JJTREFERENCE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String r;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(referencekeyword);
      r = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_35:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[34] = jj_gen;
            break label_35;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[35] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setReference(r);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Units unitsstmt() throws ParseException {
 /*@bgen(jjtree) Units */
YANG_Units jjtn000 = new YANG_Units(JJTUNITS);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String u = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(unitskeyword);
      u = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_36:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[36] = jj_gen;
            break label_36;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[37] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setUnits(u);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);

                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Revision revisionstmt() throws ParseException {
 /*@bgen(jjtree) Revision */
YANG_Revision jjtn000 = new YANG_Revision(JJTREVISION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
YANG_Description d = null;
String da = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(revisionkeyword);
      da = dateexprstr();
                jjtn000.setDate(da);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_37:
        while (true) {
          if (jj_2_18(2)) {
            ;
          } else {
            break label_37;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case descriptionkeyword:
          d = descriptionstmt();
          label_38:
          while (true) {
            if (jj_2_19(2)) {
              ;
            } else {
              break label_38;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
          break;
        default:
          jj_la1[38] = jj_gen;
          ;
        }
        jj_consume_token(92);
                if(d != null)
                        jjtn000.setDescription(d);
        break;
      default:
        jj_la1[39] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Extension extensionstmt() throws ParseException {
 /*@bgen(jjtree) Extension */
YANG_Extension jjtn000 = new YANG_Extension(JJTEXTENSION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String s = null;
YANG_Argument a = null;
YANG_Status st = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(extensionkeyword);
      s = identifierstr();
                jjtn000.setExtension(s);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_39:
        while (true) {
          if (jj_2_20(2)) {
            ;
          } else {
            break label_39;
          }
          un = unknownstatement();
                                                     jjtn000.addUnknown(un);
        }
        label_40:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case argumentkeyword:
          case descriptionkeyword:
          case referencekeyword:
          case statuskeyword:
            ;
            break;
          default:
            jj_la1[40] = jj_gen;
            break label_40;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case argumentkeyword:
            //extensionAnyOrder()
                        a = argumentstmt();
            label_41:
            while (true) {
              if (jj_2_21(2)) {
                ;
              } else {
                break label_41;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setArgument(a);
            break;
          case statuskeyword:
            st = statusstmt();
            label_42:
            while (true) {
              if (jj_2_22(2)) {
                ;
              } else {
                break label_42;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(st);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_43:
            while (true) {
              if (jj_2_23(2)) {
                ;
              } else {
                break label_43;
              }
              un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_44:
            while (true) {
              if (jj_2_24(2)) {
                ;
              } else {
                break label_44;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[41] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[42] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                 {if (true) return  jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Argument argumentstmt() throws ParseException {
 /*@bgen(jjtree) Argument */
YANG_Argument jjtn000 = new YANG_Argument(JJTARGUMENT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);YANG_Yin y = null;
String i = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(argumentkeyword);
      i = identifierstr();
                jjtn000.setArgument(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_45:
        while (true) {
          if (jj_2_25(2)) {
            ;
          } else {
            break label_45;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case yinelementkeyword:
          y = yinelementstmt();
          label_46:
          while (true) {
            if (jj_2_26(2)) {
              ;
            } else {
              break label_46;
            }
            un = unknownstatement();
                                                                            jjtn000.addUnknown(un);
          }
                        jjtn000.setYin(y);
          break;
        default:
          jj_la1[43] = jj_gen;
          ;
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[44] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Yin yinelementstmt() throws ParseException {
 /*@bgen(jjtree) Yin */
YANG_Yin jjtn000 = new YANG_Yin(JJTYIN);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String y = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(yinelementkeyword);
      y = yinelementargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_47:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[45] = jj_gen;
            break label_47;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[46] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setYin(y);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String yinelementargstr() throws ParseException {
String y = null;
    if (jj_2_27(2)) {
      y = yinelementarg();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        y = string();
        break;
      default:
        jj_la1[47] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return y;}
    throw new Error("Missing return statement in function");
  }

  static final public String yinelementarg() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case truekeyword:
      t = jj_consume_token(truekeyword);
      break;
    case falsekeyword:
      t = jj_consume_token(falsekeyword);
      break;
    default:
      jj_la1[48] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_TypeDef typedefstmt() throws ParseException {
 /*@bgen(jjtree) TypeDef */
YANG_TypeDef jjtn000 = new YANG_TypeDef(JJTTYPEDEF);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String i = null;
YANG_Type ty = null;
YANG_Units u = null;
YANG_Default df = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(typedefkeyword);
      i = identifierstr();
                jjtn000.setTypedef(i);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
      jj_consume_token(91);
      label_48:
      while (true) {
        if (jj_2_28(2)) {
          ;
        } else {
          break label_48;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      label_49:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case defaultkeyword:
        case descriptionkeyword:
        case referencekeyword:
        case statuskeyword:
        case typekeyword:
        case unitskeyword:
          ;
          break;
        default:
          jj_la1[49] = jj_gen;
          break label_49;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case typekeyword:
          ty = typestmt();
          label_50:
          while (true) {
            if (jj_2_29(2)) {
              ;
            } else {
              break label_50;
            }
            un = unknownstatement();
                                                                jjtn000.addUnknown(un);
          }
                 jjtn000.setType(ty);
          break;
        case unitskeyword:
          u = unitsstmt();
          label_51:
          while (true) {
            if (jj_2_30(2)) {
              ;
            } else {
              break label_51;
            }
            un = unknownstatement();
                                                                jjtn000.addUnknown(un);
          }
                jjtn000.setUnits(u);
          break;
        case defaultkeyword:
          df = defaultstmt();
          label_52:
          while (true) {
            if (jj_2_31(2)) {
              ;
            } else {
              break label_52;
            }
            un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
          }
                jjtn000.setDefault(df);
          break;
        case statuskeyword:
          s = statusstmt();
          label_53:
          while (true) {
            if (jj_2_32(2)) {
              ;
            } else {
              break label_53;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.setStatus(s);
          break;
        case descriptionkeyword:
          d = descriptionstmt();
          label_54:
          while (true) {
            if (jj_2_33(2)) {
              ;
            } else {
              break label_54;
            }
            un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
          }
                jjtn000.setDescription(d);
          break;
        case referencekeyword:
          r = referencestmt();
          label_55:
          while (true) {
            if (jj_2_34(2)) {
              ;
            } else {
              break label_55;
            }
            un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
          }
          break;
        default:
          jj_la1[50] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Type typestmt() throws ParseException {
 /*@bgen(jjtree) Type */
YANG_Type jjtn000 = new YANG_Type(JJTTYPE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String i = null;
YANG_Enum e = null;
YANG_KeyRefSpecification k = null;
YANG_BitSpecification b = null;
YANG_UnionSpecification u = null;
YANG_NumericalRestriction n = null;
YANG_StringRestriction s = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(typekeyword);
      i = identifierrefstr();
                jjtn000.setType(i);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_56:
        while (true) {
          if (jj_2_35(2)) {
            ;
          } else {
            break label_56;
          }
          un = unknownstatement();
                                                     jjtn000.addUnknown(un);
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case enumkeyword:
          label_57:
          while (true) {
            e = enumspecification();
                        jjtn000.addEnum(e);
            if (jj_2_36(2)) {
              ;
            } else {
              break label_57;
            }
          }
          break;
        case pathkeyword:
          k = keyrefspecification();
                jjtn000.setKeyRef(k);
          break;
        case bitkeyword:
          b = bitsspecification();
                jjtn000.setBitSpec(b);
          break;
        case typekeyword:
          u = unionspecification();
                jjtn000.setUnionSpec(u);
          break;
        case rangekeyword:
          n = numericalrestrictions();
                jjtn000.setNumRest(n);
          break;
        default:
          jj_la1[51] = jj_gen;
          s = stringrestrictions();
                jjtn000.setStringRest(s);
        }
        label_58:
        while (true) {
          if (jj_2_37(2)) {
            ;
          } else {
            break label_58;
          }
          un = unknownstatement();
                                                 jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[52] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                 jjtn000.checkTypeSyntax();
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_NumericalRestriction numericalrestrictions() throws ParseException {
YANG_Range r = null;
YANG_Unknown un = null;
    r = rangestmt();
    label_59:
    while (true) {
      if (jj_2_38(2)) {
        ;
      } else {
        break label_59;
      }
      un = unknownstatement();
                                                               r.addUnknown(un);
    }
                {if (true) return r;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Range rangestmt() throws ParseException {
 /*@bgen(jjtree) Range */
YANG_Range jjtn000 = new YANG_Range(JJTRANGE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String r = null;
YANG_ErrorMessage errmess = null;
YANG_ErrorAppt eat = null;
YANG_Description d = null;
YANG_Reference re = null;
YANG_Unknown un = null;
Token rt;
    try {
      rt = jj_consume_token(rangekeyword);
      r = rangeexprstr();
                jjtn000.setLine(rt.beginLine);
                jjtn000.setCol(rt.beginColumn);
                jjtn000.setRange(r);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_60:
        while (true) {
          if (jj_2_39(2)) {
            ;
          } else {
            break label_60;
          }
          un = unknownstatement();
                                               jjtn000.addUnknown(un);
        }
        label_61:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[53] = jj_gen;
            break label_61;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case errormessagekeyword:
            errmess = errormessagestmt();
            label_62:
            while (true) {
              if (jj_2_40(2)) {
                ;
              } else {
                break label_62;
              }
              un = unknownstatement();
                                                                             jjtn000.addUnknown(un);
            }
                jjtn000.setErrMess(errmess);
            break;
          case errorapptagkeyword:
            eat = errorapptagstmt();
            label_63:
            while (true) {
              if (jj_2_41(2)) {
                ;
              } else {
                break label_63;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setErrAppTag(eat);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_64:
            while (true) {
              if (jj_2_42(2)) {
                ;
              } else {
                break label_64;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            re = referencestmt();
            label_65:
            while (true) {
              if (jj_2_43(2)) {
                ;
              } else {
                break label_65;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(re);
            break;
          default:
            jj_la1[54] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[55] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_StringRestriction stringrestrictions() throws ParseException {
 /*@bgen(jjtree) StringRestriction */
YANG_StringRestriction jjtn000 = new YANG_StringRestriction(JJTSTRINGRESTRICTION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);YANG_Length l = null;
YANG_Pattern p = null;
YANG_Unknown un = null;
    try {
      if (jj_2_51(3)) {
        l = lengthstmt();
        label_66:
        while (true) {
          if (jj_2_44(2)) {
            ;
          } else {
            break label_66;
          }
          un = unknownstatement();
                                                              jjtn000.addUnknown(un);
        }
                jjtn000.setLine(l.getLine());
                jjtn000.setCol(l.getCol());
                jjtn000.setLength(l);
        label_67:
        while (true) {
          if (jj_2_45(3)) {
            ;
          } else {
            break label_67;
          }
          p = patternstmt();
          label_68:
          while (true) {
            if (jj_2_46(2)) {
              ;
            } else {
              break label_68;
            }
            un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
          }
                jjtn000.setLine(p.getLine());
                jjtn000.setCol(p.getCol());
                jjtn000.addPattern(p);
        }
      } else {
        label_69:
        while (true) {
          if (jj_2_47(3)) {
            ;
          } else {
            break label_69;
          }
          p = patternstmt();
          label_70:
          while (true) {
            if (jj_2_48(2)) {
              ;
            } else {
              break label_70;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.setLine(p.getLine());
                jjtn000.setCol(p.getCol());
                jjtn000.addPattern(p);
        }
        if (jj_2_50(3)) {
          l = lengthstmt();
          label_71:
          while (true) {
            if (jj_2_49(2)) {
              ;
            } else {
              break label_71;
            }
            un = unknownstatement();
                                                              jjtn000.addUnknown(un);
          }
                jjtn000.setLine(l.getLine());
                jjtn000.setCol(l.getCol());
                jjtn000.setLength(l);
        } else {
          ;
        }
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
  if (jjtc000) {
    jjtree.clearNodeScope(jjtn000);
    jjtc000 = false;
  } else {
    jjtree.popNode();
  }
  if (jjte000 instanceof RuntimeException) {
    {if (true) throw (RuntimeException)jjte000;}
  }
  if (jjte000 instanceof ParseException) {
    {if (true) throw (ParseException)jjte000;}
  }
  {if (true) throw (Error)jjte000;}
    } finally {
  if (jjtc000) {
    jjtree.closeNodeScope(jjtn000, true);
  }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Length lengthstmt() throws ParseException {
 /*@bgen(jjtree) Length */
YANG_Length jjtn000 = new YANG_Length(JJTLENGTH);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String l = null;
YANG_ErrorMessage em = null;
YANG_ErrorAppt eat = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
Token lk;
    try {
      lk = jj_consume_token(lengthkeyword);
      l = lengthexprstr();
                jjtn000.setLine(lk.beginLine);
                jjtn000.setCol(lk.beginColumn);
                jjtn000.setLength(l);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_72:
        while (true) {
          if (jj_2_52(2)) {
            ;
          } else {
            break label_72;
          }
          un = unknownstatement();
                                                  jjtn000.addUnknown(un);
        }
        label_73:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[56] = jj_gen;
            break label_73;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case errormessagekeyword:
            em = errormessagestmt();
            label_74:
            while (true) {
              if (jj_2_53(2)) {
                ;
              } else {
                break label_74;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setErrMess(em);
            break;
          case errorapptagkeyword:
            eat = errorapptagstmt();
            label_75:
            while (true) {
              if (jj_2_54(2)) {
                ;
              } else {
                break label_75;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setErrAppTag(eat);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_76:
            while (true) {
              if (jj_2_55(2)) {
                ;
              } else {
                break label_76;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_77:
            while (true) {
              if (jj_2_56(2)) {
                ;
              } else {
                break label_77;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[57] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[58] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Pattern patternstmt() throws ParseException {
 /*@bgen(jjtree) Pattern */
YANG_Pattern jjtn000 = new YANG_Pattern(JJTPATTERN);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String p = null;
YANG_ErrorMessage em = null;
YANG_ErrorAppt eat = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
Token pt;
    try {
      pt = jj_consume_token(patternkeyword);
      p = string();
                jjtn000.setLine(pt.beginLine);
                jjtn000.setCol(pt.beginColumn);
                jjtn000.setPattern(p);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_78:
        while (true) {
          if (jj_2_57(2)) {
            ;
          } else {
            break label_78;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        label_79:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[59] = jj_gen;
            break label_79;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case errormessagekeyword:
            em = errormessagestmt();
            label_80:
            while (true) {
              if (jj_2_58(2)) {
                ;
              } else {
                break label_80;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setErrMess(em);
            break;
          case errorapptagkeyword:
            eat = errorapptagstmt();
            label_81:
            while (true) {
              if (jj_2_59(2)) {
                ;
              } else {
                break label_81;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setErrAppTag(eat);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_82:
            while (true) {
              if (jj_2_60(2)) {
                ;
              } else {
                break label_82;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_83:
            while (true) {
              if (jj_2_61(2)) {
                ;
              } else {
                break label_83;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[60] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[61] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Default defaultstmt() throws ParseException {
 /*@bgen(jjtree) Default */
YANG_Default jjtn000 = new YANG_Default(JJTDEFAULT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String d = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(defaultkeyword);
      d = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_84:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[62] = jj_gen;
            break label_84;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[63] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setDefault(d);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Enum enumspecification() throws ParseException {
YANG_Enum e = null;
YANG_Unknown un = null;
    e = enumstmt();
    label_85:
    while (true) {
      if (jj_2_62(2)) {
        ;
      } else {
        break label_85;
      }
      un = unknownstatement();
                                                             e.addUnknown(un);
    }
                {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Enum enumstmt() throws ParseException {
 /*@bgen(jjtree) Enum */
YANG_Enum jjtn000 = new YANG_Enum(JJTENUM);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String e = null;
YANG_Value v = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(enumkeyword);
      e = identifierstr();
                jjtn000.setEnum(e);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_86:
        while (true) {
          if (jj_2_63(2)) {
            ;
          } else {
            break label_86;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_87:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case referencekeyword:
          case statuskeyword:
          case valuekeyword:
            ;
            break;
          default:
            jj_la1[64] = jj_gen;
            break label_87;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case valuekeyword:
            v = valuestmt();
            label_88:
            while (true) {
              if (jj_2_64(2)) {
                ;
              } else {
                break label_88;
              }
              un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
            }
                jjtn000.setValue(v);
            break;
          case statuskeyword:
            s = statusstmt();
            label_89:
            while (true) {
              if (jj_2_65(2)) {
                ;
              } else {
                break label_89;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_90:
            while (true) {
              if (jj_2_66(2)) {
                ;
              } else {
                break label_90;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_91:
            while (true) {
              if (jj_2_67(2)) {
                ;
              } else {
                break label_91;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[65] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[66] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_KeyRefSpecification keyrefspecification() throws ParseException {
YANG_Path p = null;
YANG_Unknown un = null;
    p = pathstmt();
    label_92:
    while (true) {
      if (jj_2_68(2)) {
        ;
      } else {
        break label_92;
      }
      un = unknownstatement();
                                                              p.addUnknown(un);
    }
                {if (true) return (YANG_KeyRefSpecification) p;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Path pathstmt() throws ParseException {
 /*@bgen(jjtree) Path */
YANG_Path jjtn000 = new YANG_Path(JJTPATH);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String p = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(pathkeyword);
      p = pathargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_93:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[67] = jj_gen;
            break label_93;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[68] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setPath(p);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_UnionSpecification unionspecification() throws ParseException {
 /*@bgen(jjtree) UnionSpecification */
YANG_UnionSpecification jjtn000 = new YANG_UnionSpecification(JJTUNIONSPECIFICATION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);YANG_Type t = null;
YANG_Unknown un = null;
    try {
      label_94:
      while (true) {
        t = typestmt();
        label_95:
        while (true) {
          if (jj_2_69(2)) {
            ;
          } else {
            break label_95;
          }
          un = unknownstatement();
                                               jjtn000.addUnknown(un);
        }
                jjtn000.addType(t);
        if (jj_2_70(2)) {
          ;
        } else {
          break label_94;
        }
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_BitSpecification bitsspecification() throws ParseException {
 /*@bgen(jjtree) BitSpecification */
YANG_BitSpecification jjtn000 = new YANG_BitSpecification(JJTBITSPECIFICATION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);YANG_Bit b = null;
YANG_Unknown un = null;
    try {
      label_96:
      while (true) {
        b = bitstmt();
                jjtn000.addBit(b);
        label_97:
        while (true) {
          if (jj_2_71(2)) {
            ;
          } else {
            break label_97;
          }
          un = unknownstatement();
                                              jjtn000.addUnknown(un);
        }
        if (jj_2_72(2)) {
          ;
        } else {
          break label_96;
        }
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Bit bitstmt() throws ParseException {
 /*@bgen(jjtree) Bit */
YANG_Bit jjtn000 = new YANG_Bit(JJTBIT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Position p = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(bitkeyword);
      i = identifierstr();
                jjtn000.setBit(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_98:
        while (true) {
          if (jj_2_73(2)) {
            ;
          } else {
            break label_98;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        label_99:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case positionkeyword:
          case referencekeyword:
          case statuskeyword:
            ;
            break;
          default:
            jj_la1[69] = jj_gen;
            break label_99;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case positionkeyword:
            p = positionstmt();
            label_100:
            while (true) {
              if (jj_2_74(2)) {
                ;
              } else {
                break label_100;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setPosition(p);
            break;
          case statuskeyword:
            s = statusstmt();
            label_101:
            while (true) {
              if (jj_2_75(2)) {
                ;
              } else {
                break label_101;
              }
              un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_102:
            while (true) {
              if (jj_2_76(2)) {
                ;
              } else {
                break label_102;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_103:
            while (true) {
              if (jj_2_77(2)) {
                ;
              } else {
                break label_103;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[70] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[71] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Position positionstmt() throws ParseException {
 /*@bgen(jjtree) Position */
YANG_Position jjtn000 = new YANG_Position(JJTPOSITION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String p = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(positionkeyword);
      p = positionvaluestr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_104:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[72] = jj_gen;
            break label_104;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[73] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setPosition(p);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String positionvaluestr() throws ParseException {
String p = null;
    if (jj_2_78(2)) {
      p = positionvalue();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        p = string();
        break;
      default:
        jj_la1[74] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  static final public String positionvalue() throws ParseException {
String p = null;
    p = nonnegativedecimalvalue();
                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Status statusstmt() throws ParseException {
 /*@bgen(jjtree) Status */
YANG_Status jjtn000 = new YANG_Status(JJTSTATUS);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String s = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(statuskeyword);
      s = statusargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_105:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[75] = jj_gen;
            break label_105;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[76] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setStatus(s);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String statusargstr() throws ParseException {
String s = null;
    if (jj_2_79(2)) {
      s = statusarg();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        s = string();
        break;
      default:
        jj_la1[77] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  static final public String statusarg() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case currentkeyword:
      t = jj_consume_token(currentkeyword);
      break;
    case obsoletekeyword:
      t = jj_consume_token(obsoletekeyword);
      break;
    case deprecatedkeyword:
      t = jj_consume_token(deprecatedkeyword);
      break;
    default:
      jj_la1[78] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Config configstmt() throws ParseException {
 /*@bgen(jjtree) Config */
YANG_Config jjtn000 = new YANG_Config(JJTCONFIG);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String c = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(configkeyword);
      c = configargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_106:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[79] = jj_gen;
            break label_106;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[80] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setConfig(c);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String configargstr() throws ParseException {
String c = null;
    if (jj_2_80(2)) {
      c = configarg();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        c = string();
        break;
      default:
        jj_la1[81] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return c;}
    throw new Error("Missing return statement in function");
  }

  static final public String configarg() throws ParseException {
Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case truekeyword:
      t = jj_consume_token(truekeyword);
      break;
    case falsekeyword:
      t = jj_consume_token(falsekeyword);
      break;
    default:
      jj_la1[82] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Mandatory mandatorystmt() throws ParseException {
 /*@bgen(jjtree) Mandatory */
YANG_Mandatory jjtn000 = new YANG_Mandatory(JJTMANDATORY);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String m = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(mandatorykeyword);
      m = mandatoryargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_107:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[83] = jj_gen;
            break label_107;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[84] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setMandatory(m);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String mandatoryargstr() throws ParseException {
 String m = null;
    //< a string which matches the rule
            //   mandatoryarg >
            m = mandatoryarg();
                {if (true) return m;}
    throw new Error("Missing return statement in function");
  }

  static final public String mandatoryarg() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case truekeyword:
      t = jj_consume_token(truekeyword);
      break;
    case falsekeyword:
      t = jj_consume_token(falsekeyword);
      break;
    default:
      jj_la1[85] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Presence presencestmt() throws ParseException {
 /*@bgen(jjtree) Presence */
YANG_Presence jjtn000 = new YANG_Presence(JJTPRESENCE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String p = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(presencekeyword);
      p = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_108:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[86] = jj_gen;
            break label_108;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[87] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setPresence(p);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_OrderedBy orderedbystmt() throws ParseException {
 /*@bgen(jjtree) OrderedBy */
YANG_OrderedBy jjtn000 = new YANG_OrderedBy(JJTORDEREDBY);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String o = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(orderedbykeyword);
      o = orderedbyargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_109:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[88] = jj_gen;
            break label_109;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[89] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setOrderedBy(o);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String orderedbyargstr() throws ParseException {
String o = null;
    //	< a string which matches the rule
                    //           orderedbyarg >
            o = orderedbyarg();
                {if (true) return o;}
    throw new Error("Missing return statement in function");
  }

  static final public String orderedbyarg() throws ParseException {
Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case userkeyword:
      t = jj_consume_token(userkeyword);
      break;
    case systemkeyword:
      t = jj_consume_token(systemkeyword);
      break;
    default:
      jj_la1[90] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Must muststmt() throws ParseException {
 /*@bgen(jjtree) Must */
YANG_Must jjtn000 = new YANG_Must(JJTMUST);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token must;
String m = null;
YANG_ErrorMessage em = null;
YANG_ErrorAppt ea = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      must = jj_consume_token(mustkeyword);
      m = string();
                        jjtn000.setLine(must.beginLine);
                        jjtn000.setCol(must.beginColumn);
                        jjtn000.setMust(m);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_110:
        while (true) {
          if (jj_2_81(2)) {
            ;
          } else {
            break label_110;
          }
          un = unknownstatement();
                                                       jjtn000.addUnknown(un);
        }
        label_111:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[91] = jj_gen;
            break label_111;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case errormessagekeyword:
            em = errormessagestmt();
            label_112:
            while (true) {
              if (jj_2_82(2)) {
                ;
              } else {
                break label_112;
              }
              un = unknownstatement();
                                                                                jjtn000.addUnknown(un);
            }
                        jjtn000.setErrMess(em);
            break;
          case errorapptagkeyword:
            ea = errorapptagstmt();
            label_113:
            while (true) {
              if (jj_2_83(2)) {
                ;
              } else {
                break label_113;
              }
              un = unknownstatement();
                                                                               jjtn000.addUnknown(un);
            }
                        jjtn000.setErrAppTag(ea);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_114:
            while (true) {
              if (jj_2_84(2)) {
                ;
              } else {
                break label_114;
              }
              un = unknownstatement();
                                                                              jjtn000.addUnknown(un);
            }
                        jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_115:
            while (true) {
              if (jj_2_85(2)) {
                ;
              } else {
                break label_115;
              }
              un = unknownstatement();
                                                                            jjtn000.addUnknown(un);
            }
                        jjtn000.setReference(r);
            break;
          default:
            jj_la1[92] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[93] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_ErrorMessage errormessagestmt() throws ParseException {
 /*@bgen(jjtree) ErrorMessage */
YANG_ErrorMessage jjtn000 = new YANG_ErrorMessage(JJTERRORMESSAGE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String e = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(errormessagekeyword);
      e = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_116:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[94] = jj_gen;
            break label_116;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[95] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setErrorMessage(e);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_ErrorAppt errorapptagstmt() throws ParseException {
 /*@bgen(jjtree) ErrorAppt */
YANG_ErrorAppt jjtn000 = new YANG_ErrorAppt(JJTERRORAPPT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String e = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(errorapptagkeyword);
      e = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_117:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[96] = jj_gen;
            break label_117;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[97] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setErrorAppt(e);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_MinElement minelementsstmt() throws ParseException {
 /*@bgen(jjtree) MinElement */
YANG_MinElement jjtn000 = new YANG_MinElement(JJTMINELEMENT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String m = null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(minelementskeyword);
      m = minvaluestr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_118:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[98] = jj_gen;
            break label_118;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[99] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setMinElement(m);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String minvaluestr() throws ParseException {
String m = null;
    //	< a string which matches the rule
                      //         minvalue >
            m = minvalue();
                {if (true) return m;}
    throw new Error("Missing return statement in function");
  }

  static final public String minvalue() throws ParseException {
String m = null;
    m = nonnegativedecimalvalue();
                {if (true) return m;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_MaxElement maxelementsstmt() throws ParseException {
 /*@bgen(jjtree) MaxElement */
YANG_MaxElement jjtn000 = new YANG_MaxElement(JJTMAXELEMENT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
String m= null;
YANG_Unknown un = null;
    try {
      t = jj_consume_token(maxelementskeyword);
      m = maxvaluestr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_119:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[100] = jj_gen;
            break label_119;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[101] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setMaxElement(m);
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String maxvaluestr() throws ParseException {
String m = null;
    //	< a string which matches the rule
                    //           maxvalue >
            m = maxvalue();
                {if (true) return m;}
    throw new Error("Missing return statement in function");
  }

  static final public String maxvalue() throws ParseException {
Token t;
String p = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case unboundedkeyword:
      t = jj_consume_token(unboundedkeyword);
                {if (true) return t.image;}
      break;
    case DECVALUE:
      p = positivedecimalvalue();
                {if (true) return p;}
      break;
    default:
      jj_la1[102] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Value valuestmt() throws ParseException {
 /*@bgen(jjtree) Value */
YANG_Value jjtn000 = new YANG_Value(JJTVALUE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String d = null;
YANG_Unknown un = null;
    try {
      jj_consume_token(valuekeyword);
      d = decimalvalue();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_120:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[103] = jj_gen;
            break label_120;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[104] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setValue(d);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Body groupingstmt() throws ParseException {
 /*@bgen(jjtree) Grouping */
YANG_Grouping jjtn000 = new YANG_Grouping(JJTGROUPING);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token to;
String i = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_DataDef dd = null;
YANG_Unknown un = null;
    try {
      to = jj_consume_token(groupingkeyword);
      i = identifierstr();
                         jjtn000.setLine(to.beginLine);
                         jjtn000.setCol(to.beginColumn);
                         jjtn000.setGrouping(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_121:
        while (true) {
          if (jj_2_86(2)) {
            ;
          } else {
            break label_121;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_122:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case containerkeyword:
          case descriptionkeyword:
          case groupingkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case referencekeyword:
          case statuskeyword:
          case typedefkeyword:
          case useskeyword:
            ;
            break;
          default:
            jj_la1[105] = jj_gen;
            break label_122;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case statuskeyword:
            s = statusstmt();
            label_123:
            while (true) {
              if (jj_2_87(2)) {
                ;
              } else {
                break label_123;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                        jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_124:
            while (true) {
              if (jj_2_88(2)) {
                ;
              } else {
                break label_124;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                        jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_125:
            while (true) {
              if (jj_2_89(2)) {
                ;
              } else {
                break label_125;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                        jjtn000.setReference(r);
            break;
          case groupingkeyword:
          case typedefkeyword:
            label_126:
            while (true) {
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case typedefkeyword:
                t = typedefstmt();
                        jjtn000.addTypeDef(t);
                break;
              case groupingkeyword:
                g = groupingstmt();
                        if(g instanceof YANG_Grouping)
                                jjtn000.addGrouping((YANG_Grouping)g );
                break;
              default:
                jj_la1[106] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
              }
              label_127:
              while (true) {
                if (jj_2_90(2)) {
                  ;
                } else {
                  break label_127;
                }
                un = unknownstatement();
                                                         jjtn000.addUnknown(un);
              }
              if (jj_2_91(2)) {
                ;
              } else {
                break label_126;
              }
            }
            break;
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case useskeyword:
            dd = datadefstmt();
            label_128:
            while (true) {
              if (jj_2_92(2)) {
                ;
              } else {
                break label_128;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                        jjtn000.addDataDef(dd);
            break;
          default:
            jj_la1[107] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[108] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                          jjtree.closeNodeScope(jjtn000, true);
                          jjtc000 = false;
                        {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Container containerstmt() throws ParseException {
 /*@bgen(jjtree) Container */
YANG_Container jjtn000 = new YANG_Container(JJTCONTAINER);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token to;
String i = null;
YANG_Must m = null;
YANG_Presence p = null;
YANG_Config c = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_DataDef da = null;
YANG_Unknown un = null;
    try {
      to = jj_consume_token(containerkeyword);
      i = identifierstr();
                        jjtn000.setLine(to.beginLine);
                        jjtn000.setCol(to.beginColumn);
                        jjtn000.setContainer(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_129:
        while (true) {
          if (jj_2_93(2)) {
            ;
          } else {
            break label_129;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_130:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case configkeyword:
          case containerkeyword:
          case descriptionkeyword:
          case groupingkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case mustkeyword:
          case presencekeyword:
          case referencekeyword:
          case statuskeyword:
          case typedefkeyword:
          case useskeyword:
            ;
            break;
          default:
            jj_la1[109] = jj_gen;
            break label_130;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case mustkeyword:
            m = muststmt();
            label_131:
            while (true) {
              if (jj_2_94(2)) {
                ;
              } else {
                break label_131;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                        jjtn000.addMust(m);
            break;
          case presencekeyword:
            p = presencestmt();
            label_132:
            while (true) {
              if (jj_2_95(2)) {
                ;
              } else {
                break label_132;
              }
              un = unknownstatement();
                                                                          jjtn000.addUnknown(un);
            }
                        jjtn000.setPresence(p);
            break;
          case configkeyword:
            c = configstmt();
            label_133:
            while (true) {
              if (jj_2_96(2)) {
                ;
              } else {
                break label_133;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                        jjtn000.setConfig(c);
            break;
          case statuskeyword:
            s = statusstmt();
            label_134:
            while (true) {
              if (jj_2_97(2)) {
                ;
              } else {
                break label_134;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                        jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_135:
            while (true) {
              if (jj_2_98(2)) {
                ;
              } else {
                break label_135;
              }
              un = unknownstatement();
                                                                             jjtn000.addUnknown(un);
            }
                        jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_136:
            while (true) {
              if (jj_2_99(2)) {
                ;
              } else {
                break label_136;
              }
              un = unknownstatement();
                                                                           jjtn000.addUnknown(un);
            }
                        jjtn000.setReference(r);
            break;
          case groupingkeyword:
          case typedefkeyword:
            label_137:
            while (true) {
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case typedefkeyword:
                t = typedefstmt();
                                jjtn000.addTypeDef(t);
                break;
              case groupingkeyword:
                g = groupingstmt();
                                jjtn000.addGrouping((YANG_Grouping)g);
                break;
              default:
                jj_la1[110] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
              }
              label_138:
              while (true) {
                if (jj_2_100(2)) {
                  ;
                } else {
                  break label_138;
                }
                un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
              }
              if (jj_2_101(2)) {
                ;
              } else {
                break label_137;
              }
            }
            break;
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case useskeyword:
            da = datadefstmt();
            label_139:
            while (true) {
              if (jj_2_102(2)) {
                ;
              } else {
                break label_139;
              }
              un = unknownstatement();
                                                                          jjtn000.addUnknown(un);
            }
                        jjtn000.addDataDef(da);
            break;
          default:
            jj_la1[111] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[112] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                          jjtree.closeNodeScope(jjtn000, true);
                          jjtc000 = false;
                        {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Leaf leafstmt() throws ParseException {
 /*@bgen(jjtree) Leaf */
YANG_Leaf jjtn000 = new YANG_Leaf(JJTLEAF);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token leaf;
String i = null;
YANG_Type t = null;
YANG_Units u = null;
YANG_Must m = null;
YANG_Default d = null;
YANG_Config c = null;
YANG_Mandatory ma = null;
YANG_Status s = null;
YANG_Description de = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      leaf = jj_consume_token(leafkeyword);
      i = identifierstr();
                        jjtn000.setLine(leaf.beginLine);
                        jjtn000.setCol(leaf.beginColumn);
                        jjtn000.setLeaf(i);
      jj_consume_token(91);
      label_140:
      while (true) {
        if (jj_2_103(2)) {
          ;
        } else {
          break label_140;
        }
        un = unknownstatement();
                                                       jjtn000.addUnknown(un);
      }
      label_141:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case configkeyword:
        case defaultkeyword:
        case descriptionkeyword:
        case mandatorykeyword:
        case mustkeyword:
        case referencekeyword:
        case statuskeyword:
        case typekeyword:
        case unitskeyword:
          ;
          break;
        default:
          jj_la1[113] = jj_gen;
          break label_141;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case typekeyword:
          t = typestmt();
          label_142:
          while (true) {
            if (jj_2_104(2)) {
              ;
            } else {
              break label_142;
            }
            un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
          }
                        jjtn000.setType(t);
          break;
        case unitskeyword:
          u = unitsstmt();
          label_143:
          while (true) {
            if (jj_2_105(2)) {
              ;
            } else {
              break label_143;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
                        jjtn000.setUnits(u);
          break;
        case mustkeyword:
          m = muststmt();
          label_144:
          while (true) {
            if (jj_2_106(2)) {
              ;
            } else {
              break label_144;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
                        jjtn000.addMust(m);
          break;
        case defaultkeyword:
          d = defaultstmt();
          label_145:
          while (true) {
            if (jj_2_107(2)) {
              ;
            } else {
              break label_145;
            }
            un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
          }
                        jjtn000.setDefault(d);
          break;
        case configkeyword:
          c = configstmt();
          label_146:
          while (true) {
            if (jj_2_108(2)) {
              ;
            } else {
              break label_146;
            }
            un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
          }
                        jjtn000.setConfig(c);
          break;
        case mandatorykeyword:
          ma = mandatorystmt();
          label_147:
          while (true) {
            if (jj_2_109(2)) {
              ;
            } else {
              break label_147;
            }
            un = unknownstatement();
                                                                            jjtn000.addUnknown(un);
          }
                        jjtn000.setMandatory(ma);
          break;
        case statuskeyword:
          s = statusstmt();
          label_148:
          while (true) {
            if (jj_2_110(2)) {
              ;
            } else {
              break label_148;
            }
            un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
          }
                        jjtn000.setStatus(s);
          break;
        case descriptionkeyword:
          de = descriptionstmt();
          label_149:
          while (true) {
            if (jj_2_111(2)) {
              ;
            } else {
              break label_149;
            }
            un = unknownstatement();
                                                                              jjtn000.addUnknown(un);
          }
                        jjtn000.setDescription(de);
          break;
        case referencekeyword:
          r = referencestmt();
          label_150:
          while (true) {
            if (jj_2_112(2)) {
              ;
            } else {
              break label_150;
            }
            un = unknownstatement();
                                                                           jjtn000.addUnknown(un);
          }
                        jjtn000.setReference(r);
          break;
        default:
          jj_la1[114] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                          jjtree.closeNodeScope(jjtn000, true);
                          jjtc000 = false;
                        {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_LeafList leafliststmt() throws ParseException {
 /*@bgen(jjtree) LeafList */
YANG_LeafList jjtn000 = new YANG_LeafList(JJTLEAFLIST);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token leaflist;
String i = null;
YANG_Type t = null;
YANG_Units u = null;
YANG_Must mu = null;
YANG_Config c = null;
YANG_MinElement mi = null;
YANG_MaxElement ma = null;
YANG_OrderedBy o = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      leaflist = jj_consume_token(leaflistkeyword);
      i = identifierstr();
                jjtn000.setLine(leaflist.beginLine);
                jjtn000.setCol(leaflist.beginColumn);
                jjtn000.setLeafList(i);
      jj_consume_token(91);
      label_151:
      while (true) {
        if (jj_2_113(2)) {
          ;
        } else {
          break label_151;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      label_152:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case configkeyword:
        case descriptionkeyword:
        case maxelementskeyword:
        case minelementskeyword:
        case mustkeyword:
        case orderedbykeyword:
        case referencekeyword:
        case statuskeyword:
        case typekeyword:
        case unitskeyword:
          ;
          break;
        default:
          jj_la1[115] = jj_gen;
          break label_152;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case typekeyword:
          t = typestmt();
          label_153:
          while (true) {
            if (jj_2_114(2)) {
              ;
            } else {
              break label_153;
            }
            un = unknownstatement();
                                                               jjtn000.addUnknown(un);
          }
                jjtn000.setType(t);
          break;
        case unitskeyword:
          u = unitsstmt();
          label_154:
          while (true) {
            if (jj_2_115(2)) {
              ;
            } else {
              break label_154;
            }
            un = unknownstatement();
                                                                jjtn000.addUnknown(un);
          }
                jjtn000.setUnits(u);
          break;
        case mustkeyword:
          mu = muststmt();
          label_155:
          while (true) {
            if (jj_2_116(2)) {
              ;
            } else {
              break label_155;
            }
            un = unknownstatement();
                                                                jjtn000.addUnknown(un);
          }
                jjtn000.addMust(mu);
          break;
        case configkeyword:
          c = configstmt();
          label_156:
          while (true) {
            if (jj_2_117(2)) {
              ;
            } else {
              break label_156;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.setConfig(c);
          break;
        case minelementskeyword:
          mi = minelementsstmt();
          label_157:
          while (true) {
            if (jj_2_118(2)) {
              ;
            } else {
              break label_157;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
                jjtn000.setMinElement(mi);
          break;
        case maxelementskeyword:
          ma = maxelementsstmt();
          label_158:
          while (true) {
            if (jj_2_119(2)) {
              ;
            } else {
              break label_158;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
                jjtn000.setMaxElement(ma);
          break;
        case orderedbykeyword:
          o = orderedbystmt();
          label_159:
          while (true) {
            if (jj_2_120(2)) {
              ;
            } else {
              break label_159;
            }
            un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
          }
                jjtn000.setOrderedBy(o);
          break;
        case statuskeyword:
          s = statusstmt();
          label_160:
          while (true) {
            if (jj_2_121(2)) {
              ;
            } else {
              break label_160;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.setStatus(s);
          break;
        case descriptionkeyword:
          d = descriptionstmt();
          label_161:
          while (true) {
            if (jj_2_122(2)) {
              ;
            } else {
              break label_161;
            }
            un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
          }
                jjtn000.setDescription(d);
          break;
        case referencekeyword:
          r = referencestmt();
          label_162:
          while (true) {
            if (jj_2_123(2)) {
              ;
            } else {
              break label_162;
            }
            un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
          }
                jjtn000.setReference(r);
          break;
        default:
          jj_la1[116] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_List liststmt() throws ParseException {
 /*@bgen(jjtree) List */
YANG_List jjtn000 = new YANG_List(JJTLIST);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token list;
String i = null;
YANG_Must m = null;
YANG_Key k = null;
YANG_Unique u = null;
YANG_Config c = null;
YANG_MinElement mi = null;
YANG_MaxElement ma = null;
YANG_OrderedBy o = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_DataDef dd = null;
YANG_Unknown un = null;
    try {
      list = jj_consume_token(listkeyword);
      i = identifierstr();
                jjtn000.setLine(list.beginLine);
                jjtn000.setCol(list.beginColumn);
                jjtn000.setList(i);
      jj_consume_token(91);
      label_163:
      while (true) {
        if (jj_2_124(2)) {
          ;
        } else {
          break label_163;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      label_164:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case configkeyword:
        case containerkeyword:
        case descriptionkeyword:
        case groupingkeyword:
        case keykeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case maxelementskeyword:
        case minelementskeyword:
        case mustkeyword:
        case orderedbykeyword:
        case referencekeyword:
        case statuskeyword:
        case typedefkeyword:
        case uniquekeyword:
        case useskeyword:
          ;
          break;
        default:
          jj_la1[117] = jj_gen;
          break label_164;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case mustkeyword:
          m = muststmt();
          label_165:
          while (true) {
            if (jj_2_125(2)) {
              ;
            } else {
              break label_165;
            }
            un = unknownstatement();
                                                               jjtn000.addUnknown(un);
          }
                jjtn000.addMust(m);
          break;
        case keykeyword:
          k = keystmt();
          label_166:
          while (true) {
            if (jj_2_126(2)) {
              ;
            } else {
              break label_166;
            }
            un = unknownstatement();
                                                              jjtn000.addUnknown(un);
          }
                jjtn000.setKey(k);
          break;
        case uniquekeyword:
          u = uniquestmt();
          label_167:
          while (true) {
            if (jj_2_127(2)) {
              ;
            } else {
              break label_167;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.addUnique(u);
          break;
        case configkeyword:
          c = configstmt();
          label_168:
          while (true) {
            if (jj_2_128(2)) {
              ;
            } else {
              break label_168;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.setConfig(c);
          break;
        case minelementskeyword:
          mi = minelementsstmt();
          label_169:
          while (true) {
            if (jj_2_129(2)) {
              ;
            } else {
              break label_169;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
                jjtn000.setMinElement(mi);
          break;
        case maxelementskeyword:
          ma = maxelementsstmt();
          label_170:
          while (true) {
            if (jj_2_130(2)) {
              ;
            } else {
              break label_170;
            }
            un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
          }
                jjtn000.setMaxElement(ma);
          break;
        case orderedbykeyword:
          o = orderedbystmt();
          label_171:
          while (true) {
            if (jj_2_131(2)) {
              ;
            } else {
              break label_171;
            }
            un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
          }
                jjtn000.setOrderedBy(o);
          break;
        case statuskeyword:
          s = statusstmt();
          label_172:
          while (true) {
            if (jj_2_132(2)) {
              ;
            } else {
              break label_172;
            }
            un = unknownstatement();
                                                                jjtn000.addUnknown(un);
          }
                jjtn000.setStatus(s);
          break;
        case descriptionkeyword:
          d = descriptionstmt();
          label_173:
          while (true) {
            if (jj_2_133(2)) {
              ;
            } else {
              break label_173;
            }
            un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
          }
                jjtn000.setDescription(d);
          break;
        case referencekeyword:
          r = referencestmt();
          label_174:
          while (true) {
            if (jj_2_134(2)) {
              ;
            } else {
              break label_174;
            }
            un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
          }
                jjtn000.setReference(r);
          break;
        case typedefkeyword:
          t = typedefstmt();
          label_175:
          while (true) {
            if (jj_2_135(2)) {
              ;
            } else {
              break label_175;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.addTypeDef(t);
          break;
        case groupingkeyword:
          g = groupingstmt();
          label_176:
          while (true) {
            if (jj_2_136(2)) {
              ;
            } else {
              break label_176;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.addGrouping((YANG_Grouping)g);
          break;
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case useskeyword:
          dd = datadefstmt();
          label_177:
          while (true) {
            if (jj_2_137(2)) {
              ;
            } else {
              break label_177;
            }
            un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
          }
                jjtn000.addDataDef(dd);
          break;
        default:
          jj_la1[118] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Key keystmt() throws ParseException {
 /*@bgen(jjtree) Key */
YANG_Key jjtn000 = new YANG_Key(JJTKEY);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token key;
String k = null;
YANG_Unknown un = null;
    try {
      key = jj_consume_token(keykeyword);
      k = keyargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_178:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[119] = jj_gen;
            break label_178;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[120] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(key.beginLine);
                jjtn000.setCol(key.beginColumn);
                jjtn000.setKey(k);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String keyargstr() throws ParseException {
String k = null;
    if (jj_2_138(2)) {
      k = keyarg();
                {if (true) return k;}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        k = string();
                {if (true) return k;}
        break;
      default:
        jj_la1[121] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String keyarg() throws ParseException {
Token t;
String k = new String();
    label_179:
    while (true) {
      t = jj_consume_token(IDENTIFIER);
                k += t.image + " ";
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[122] = jj_gen;
        break label_179;
      }
    }
                {if (true) return k;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Unique uniquestmt() throws ParseException {
 /*@bgen(jjtree) Unique */
YANG_Unique jjtn000 = new YANG_Unique(JJTUNIQUE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token unique;
String u = null;
YANG_Unknown un = null;
    try {
      unique = jj_consume_token(uniquekeyword);
      u = uniqueargstr();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_180:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[123] = jj_gen;
            break label_180;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[124] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(unique.beginLine);
                jjtn000.setCol(unique.beginColumn);
                jjtn000.setUnique(u);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String uniqueargstr() throws ParseException {
String u = null;
    if (jj_2_139(2)) {
      u = uniquearg();
                {if (true) return u;}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        u = string();
                {if (true) return u;}
        break;
      default:
        jj_la1[125] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String uniquearg() throws ParseException {
String d = null,u = new String();
    d = descendantschemanodeid();
                u = d;
                {if (true) return u;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Choice choicestmt() throws ParseException {
 /*@bgen(jjtree) Choice */
YANG_Choice jjtn000 = new YANG_Choice(JJTCHOICE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token choice;
String i = null;
YANG_Default d = null;
YANG_Config cf = null;
YANG_Mandatory m = null;
YANG_Status s = null;
YANG_Description de = null;
YANG_Reference r = null;
YANG_ShortCase sc = null;
YANG_Case c = null;
YANG_Unknown un = null;
    try {
      choice = jj_consume_token(choicekeyword);
      i = identifierstr();
                jjtn000.setLine(choice.beginLine);
                jjtn000.setCol(choice.beginColumn);
                jjtn000.setChoice(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_181:
        while (true) {
          if (jj_2_140(2)) {
            ;
          } else {
            break label_181;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_182:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case casekeyword:
          case configkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case mandatorykeyword:
          case referencekeyword:
          case statuskeyword:
            ;
            break;
          default:
            jj_la1[126] = jj_gen;
            break label_182;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case defaultkeyword:
            d = defaultstmt();
            label_183:
            while (true) {
              if (jj_2_141(2)) {
                ;
              } else {
                break label_183;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setDefault(d);
            break;
          case configkeyword:
            cf = configstmt();
            label_184:
            while (true) {
              if (jj_2_142(2)) {
                ;
              } else {
                break label_184;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(cf);
            break;
          case mandatorykeyword:
            m = mandatorystmt();
            label_185:
            while (true) {
              if (jj_2_143(2)) {
                ;
              } else {
                break label_185;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setMandatory(m);
            break;
          case statuskeyword:
            s = statusstmt();
            label_186:
            while (true) {
              if (jj_2_144(2)) {
                ;
              } else {
                break label_186;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            de = descriptionstmt();
            label_187:
            while (true) {
              if (jj_2_145(2)) {
                ;
              } else {
                break label_187;
              }
              un = unknownstatement();
                                                                          jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(de);
            break;
          case referencekeyword:
            r = referencestmt();
            label_188:
            while (true) {
              if (jj_2_146(2)) {
                ;
              } else {
                break label_188;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case anyxmlkeyword:
          case casekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case anyxmlkeyword:
            case containerkeyword:
            case leafkeyword:
            case leaflistkeyword:
            case listkeyword:
              sc = shortcasestmt();
                        jjtn000.addShortCase(sc);
              break;
            case casekeyword:
              c = casestmt();
                        jjtn000.addCase(c);
              break;
            default:
              jj_la1[127] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
            label_189:
            while (true) {
              if (jj_2_147(2)) {
                ;
              } else {
                break label_189;
              }
              un = unknownstatement();
                                                     jjtn000.addUnknown(un);
            }
            break;
          default:
            jj_la1[128] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[129] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_ShortCase shortcasestmt() throws ParseException {
YANG_ShortCase d = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case containerkeyword:
      d = containerstmt();
      break;
    case leafkeyword:
      d = leafstmt();
      break;
    case leaflistkeyword:
      d = leafliststmt();
      break;
    case listkeyword:
      d = liststmt();
      break;
    case anyxmlkeyword:
      d = anyxmlstmt();
      break;
    default:
      jj_la1[130] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return d;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Case casestmt() throws ParseException {
 /*@bgen(jjtree) Case */
YANG_Case jjtn000 = new YANG_Case(JJTCASE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token tcase;
String i = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_CaseDef c = null;
YANG_Unknown un = null;
    try {
      tcase = jj_consume_token(casekeyword);
      i = identifierstr();
                jjtn000.setLine(tcase.beginLine);
                jjtn000.setCol(tcase.beginColumn);
                jjtn000.setCase(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_190:
        while (true) {
          if (jj_2_148(2)) {
            ;
          } else {
            break label_190;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_191:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case augmentkeyword:
          case containerkeyword:
          case descriptionkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case referencekeyword:
          case statuskeyword:
          case useskeyword:
            ;
            break;
          default:
            jj_la1[131] = jj_gen;
            break label_191;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case statuskeyword:
            s = statusstmt();
            label_192:
            while (true) {
              if (jj_2_149(2)) {
                ;
              } else {
                break label_192;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_193:
            while (true) {
              if (jj_2_150(2)) {
                ;
              } else {
                break label_193;
              }
              un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_194:
            while (true) {
              if (jj_2_151(2)) {
                ;
              } else {
                break label_194;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case anyxmlkeyword:
          case augmentkeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case useskeyword:
            c = casedatadefstmt();
            label_195:
            while (true) {
              if (jj_2_152(2)) {
                ;
              } else {
                break label_195;
              }
              un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
            }
                jjtn000.addCaseDef(c);
            break;
          default:
            jj_la1[132] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[133] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_AnyXml anyxmlstmt() throws ParseException {
 /*@bgen(jjtree) AnyXml */
YANG_AnyXml jjtn000 = new YANG_AnyXml(JJTANYXML);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token any;
String i = null;
YANG_Config c = null;
YANG_Mandatory m = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
    try {
      any = jj_consume_token(anyxmlkeyword);
      i = identifierstr();
                jjtn000.setLine(any.beginLine);
                jjtn000.setCol(any.beginColumn);
                jjtn000.setAnyXml(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_196:
        while (true) {
          if (jj_2_153(2)) {
            ;
          } else {
            break label_196;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_197:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case configkeyword:
          case descriptionkeyword:
          case mandatorykeyword:
          case referencekeyword:
          case statuskeyword:
            ;
            break;
          default:
            jj_la1[134] = jj_gen;
            break label_197;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case configkeyword:
            c = configstmt();
            label_198:
            while (true) {
              if (jj_2_154(2)) {
                ;
              } else {
                break label_198;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case mandatorykeyword:
            m = mandatorystmt();
            label_199:
            while (true) {
              if (jj_2_155(2)) {
                ;
              } else {
                break label_199;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setMandatory(m);
            break;
          case statuskeyword:
            s = statusstmt();
            label_200:
            while (true) {
              if (jj_2_156(2)) {
                ;
              } else {
                break label_200;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_201:
            while (true) {
              if (jj_2_157(2)) {
                ;
              } else {
                break label_201;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_202:
            while (true) {
              if (jj_2_158(2)) {
                ;
              } else {
                break label_202;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[135] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[136] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Uses usesstmt() throws ParseException {
 /*@bgen(jjtree) Uses */
YANG_Uses jjtn000 = new YANG_Uses(JJTUSES);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token uses;
String i = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Refinement re = null;
YANG_Unknown un = null;
    try {
      uses = jj_consume_token(useskeyword);
      i = identifierrefstr();
                jjtn000.setLine(uses.beginLine);
                jjtn000.setCol(uses.beginColumn);
                jjtn000.setUses(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_203:
        while (true) {
          if (jj_2_159(2)) {
            ;
          } else {
            break label_203;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        label_204:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case choicekeyword:
          case containerkeyword:
          case descriptionkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case referencekeyword:
          case statuskeyword:
            ;
            break;
          default:
            jj_la1[137] = jj_gen;
            break label_204;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case statuskeyword:
            s = statusstmt();
            label_205:
            while (true) {
              if (jj_2_160(2)) {
                ;
              } else {
                break label_205;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_206:
            while (true) {
              if (jj_2_161(2)) {
                ;
              } else {
                break label_206;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_207:
            while (true) {
              if (jj_2_162(2)) {
                ;
              } else {
                break label_207;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case anyxmlkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
            re = refinementstmt();
            label_208:
            while (true) {
              if (jj_2_163(2)) {
                ;
              } else {
                break label_208;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.addRefinement(re);
            break;
          default:
            jj_la1[138] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[139] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Refinement refinementstmt() throws ParseException {
YANG_Refinement r = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case containerkeyword:
      r = refinecontainerstmt();
      break;
    case leafkeyword:
      r = refineleafstmt();
      break;
    case leaflistkeyword:
      r = refineleafliststmt();
      break;
    case listkeyword:
      r = refineliststmt();
      break;
    case choicekeyword:
      r = refinechoicestmt();
      break;
    case anyxmlkeyword:
      r = refineanyxmlstmt();
      break;
    default:
      jj_la1[140] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return r;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineLeaf refineleafstmt() throws ParseException {
 /*@bgen(jjtree) RefineLeaf */
YANG_RefineLeaf jjtn000 = new YANG_RefineLeaf(JJTREFINELEAF);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Must m = null;
YANG_Default d = null;
YANG_Config c = null;
YANG_Mandatory ma = null;
YANG_Description de = null;
YANG_Reference r = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(leafkeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineLeaf(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_209:
        while (true) {
          if (jj_2_164(2)) {
            ;
          } else {
            break label_209;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        label_210:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case configkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case mandatorykeyword:
          case mustkeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[141] = jj_gen;
            break label_210;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case mustkeyword:
            m = muststmt();
            label_211:
            while (true) {
              if (jj_2_165(2)) {
                ;
              } else {
                break label_211;
              }
              un = unknownstatement();
                                                               jjtn000.addUnknown(un);
            }
                jjtn000.addMust(m);
            break;
          case defaultkeyword:
            d = defaultstmt();
            label_212:
            while (true) {
              if (jj_2_166(2)) {
                ;
              } else {
                break label_212;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setDefault(d);
            break;
          case configkeyword:
            c = configstmt();
            label_213:
            while (true) {
              if (jj_2_167(2)) {
                ;
              } else {
                break label_213;
              }
              un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case mandatorykeyword:
            ma = mandatorystmt();
            label_214:
            while (true) {
              if (jj_2_168(2)) {
                ;
              } else {
                break label_214;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setMandatory(ma);
            break;
          case descriptionkeyword:
            de = descriptionstmt();
            label_215:
            while (true) {
              if (jj_2_169(2)) {
                ;
              } else {
                break label_215;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(de);
            break;
          case referencekeyword:
            r = referencestmt();
            label_216:
            while (true) {
              if (jj_2_170(2)) {
                ;
              } else {
                break label_216;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[142] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[143] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineLeafList refineleafliststmt() throws ParseException {
 /*@bgen(jjtree) RefineLeafList */
YANG_RefineLeafList jjtn000 = new YANG_RefineLeafList(JJTREFINELEAFLIST);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Must m = null;
YANG_Config c = null;
YANG_MinElement mi = null;
YANG_MaxElement ma = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(leaflistkeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineLeafList(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_217:
        while (true) {
          if (jj_2_171(2)) {
            ;
          } else {
            break label_217;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_218:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case configkeyword:
          case descriptionkeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case mustkeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[144] = jj_gen;
            break label_218;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case mustkeyword:
            m = muststmt();
            label_219:
            while (true) {
              if (jj_2_172(2)) {
                ;
              } else {
                break label_219;
              }
              un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
            }
                jjtn000.addMust(m);
            break;
          case configkeyword:
            c = configstmt();
            label_220:
            while (true) {
              if (jj_2_173(2)) {
                ;
              } else {
                break label_220;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case minelementskeyword:
            mi = minelementsstmt();
            label_221:
            while (true) {
              if (jj_2_174(2)) {
                ;
              } else {
                break label_221;
              }
              un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
            }
                jjtn000.setMinElement(mi);
            break;
          case maxelementskeyword:
            ma = maxelementsstmt();
            label_222:
            while (true) {
              if (jj_2_175(2)) {
                ;
              } else {
                break label_222;
              }
              un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
            }
                jjtn000.setMaxElement(ma);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_223:
            while (true) {
              if (jj_2_176(2)) {
                ;
              } else {
                break label_223;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_224:
            while (true) {
              if (jj_2_177(2)) {
                ;
              } else {
                break label_224;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[145] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[146] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineList refineliststmt() throws ParseException {
 /*@bgen(jjtree) RefineList */
YANG_RefineList jjtn000 = new YANG_RefineList(JJTREFINELIST);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Must m = null;
YANG_Config c = null;
YANG_MinElement mi = null;
YANG_MaxElement ma = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Refinement re = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(listkeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineList(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_225:
        while (true) {
          if (jj_2_178(2)) {
            ;
          } else {
            break label_225;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        label_226:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case choicekeyword:
          case configkeyword:
          case containerkeyword:
          case descriptionkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case mustkeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[147] = jj_gen;
            break label_226;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case mustkeyword:
            m = muststmt();
            label_227:
            while (true) {
              if (jj_2_179(2)) {
                ;
              } else {
                break label_227;
              }
              un = unknownstatement();
                                                                jjtn000.addUnknown(un);
            }
                jjtn000.addMust(m);
            break;
          case configkeyword:
            c = configstmt();
            label_228:
            while (true) {
              if (jj_2_180(2)) {
                ;
              } else {
                break label_228;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case minelementskeyword:
            mi = minelementsstmt();
            label_229:
            while (true) {
              if (jj_2_181(2)) {
                ;
              } else {
                break label_229;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setMinElement(mi);
            break;
          case maxelementskeyword:
            ma = maxelementsstmt();
            label_230:
            while (true) {
              if (jj_2_182(2)) {
                ;
              } else {
                break label_230;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setMaxElement(ma);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_231:
            while (true) {
              if (jj_2_183(2)) {
                ;
              } else {
                break label_231;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_232:
            while (true) {
              if (jj_2_184(2)) {
                ;
              } else {
                break label_232;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case anyxmlkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
            re = refinementstmt();
            label_233:
            while (true) {
              if (jj_2_185(2)) {
                ;
              } else {
                break label_233;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.addRefinement(re);
            break;
          default:
            jj_la1[148] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[149] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineChoice refinechoicestmt() throws ParseException {
 /*@bgen(jjtree) RefineChoice */
YANG_RefineChoice jjtn000 = new YANG_RefineChoice(JJTREFINECHOICE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Default d = null;
YANG_Config c = null;
YANG_Mandatory m = null;
YANG_Description de = null;
YANG_Reference r = null;
YANG_RefineCase re = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(choicekeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineChoice(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_234:
        while (true) {
          if (jj_2_186(2)) {
            ;
          } else {
            break label_234;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_235:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case casekeyword:
          case configkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case mandatorykeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[150] = jj_gen;
            break label_235;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case defaultkeyword:
            d = defaultstmt();
            label_236:
            while (true) {
              if (jj_2_187(2)) {
                ;
              } else {
                break label_236;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setDefault(d);
            break;
          case configkeyword:
            c = configstmt();
            label_237:
            while (true) {
              if (jj_2_188(2)) {
                ;
              } else {
                break label_237;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case mandatorykeyword:
            m = mandatorystmt();
            label_238:
            while (true) {
              if (jj_2_189(2)) {
                ;
              } else {
                break label_238;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setMandatory(m);
            break;
          case descriptionkeyword:
            de = descriptionstmt();
            label_239:
            while (true) {
              if (jj_2_190(2)) {
                ;
              } else {
                break label_239;
              }
              un = unknownstatement();
                                                                         jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(de);
            break;
          case referencekeyword:
            r = referencestmt();
            label_240:
            while (true) {
              if (jj_2_191(2)) {
                ;
              } else {
                break label_240;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case casekeyword:
            re = refinecasestmt();
            label_241:
            while (true) {
              if (jj_2_192(2)) {
                ;
              } else {
                break label_241;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.addRefineCase(re);
            break;
          default:
            jj_la1[151] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[152] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineCase refinecasestmt() throws ParseException {
 /*@bgen(jjtree) RefineCase */
YANG_RefineCase jjtn000 = new YANG_RefineCase(JJTREFINECASE);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Refinement re = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(casekeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineCase(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_242:
        while (true) {
          if (jj_2_193(2)) {
            ;
          } else {
            break label_242;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_243:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case choicekeyword:
          case containerkeyword:
          case descriptionkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[153] = jj_gen;
            break label_243;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
            d = descriptionstmt();
            label_244:
            while (true) {
              if (jj_2_194(2)) {
                ;
              } else {
                break label_244;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_245:
            while (true) {
              if (jj_2_195(2)) {
                ;
              } else {
                break label_245;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case anyxmlkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
            re = refinementstmt();
            label_246:
            while (true) {
              if (jj_2_196(2)) {
                ;
              } else {
                break label_246;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.addRefinement(re);
            break;
          default:
            jj_la1[154] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[155] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineContainer refinecontainerstmt() throws ParseException {
 /*@bgen(jjtree) RefineContainer */
YANG_RefineContainer jjtn000 = new YANG_RefineContainer(JJTREFINECONTAINER);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Must m = null;
YANG_Presence p = null;
YANG_Config c = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Refinement re = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(containerkeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineContainer(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_247:
        while (true) {
          if (jj_2_197(2)) {
            ;
          } else {
            break label_247;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_248:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case choicekeyword:
          case configkeyword:
          case containerkeyword:
          case descriptionkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case mustkeyword:
          case presencekeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[156] = jj_gen;
            break label_248;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case mustkeyword:
            m = muststmt();
            label_249:
            while (true) {
              if (jj_2_198(2)) {
                ;
              } else {
                break label_249;
              }
              un = unknownstatement();
                                                                jjtn000.addUnknown(un);
            }
                jjtn000.addMust(m);
            break;
          case presencekeyword:
            p = presencestmt();
            label_250:
            while (true) {
              if (jj_2_199(2)) {
                ;
              } else {
                break label_250;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setPresence(p);
            break;
          case configkeyword:
            c = configstmt();
            label_251:
            while (true) {
              if (jj_2_200(2)) {
                ;
              } else {
                break label_251;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_252:
            while (true) {
              if (jj_2_201(2)) {
                ;
              } else {
                break label_252;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_253:
            while (true) {
              if (jj_2_202(2)) {
                ;
              } else {
                break label_253;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case anyxmlkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
            re = refinementstmt();
            label_254:
            while (true) {
              if (jj_2_203(2)) {
                ;
              } else {
                break label_254;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.addRefinement(re);
            break;
          default:
            jj_la1[157] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[158] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_RefineAnyXml refineanyxmlstmt() throws ParseException {
 /*@bgen(jjtree) RefineAnyXml */
YANG_RefineAnyXml jjtn000 = new YANG_RefineAnyXml(JJTREFINEANYXML);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String i = null;
YANG_Config c = null;
YANG_Mandatory m = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_Unknown un = null;
Token t;
    try {
      t = jj_consume_token(anyxmlkeyword);
      i = identifierstr();
                jjtn000.setLine(t.beginLine);
                jjtn000.setCol(t.beginColumn);
                jjtn000.setRefineAnyXml(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_255:
        while (true) {
          if (jj_2_204(2)) {
            ;
          } else {
            break label_255;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_256:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case configkeyword:
          case descriptionkeyword:
          case mandatorykeyword:
          case referencekeyword:
            ;
            break;
          default:
            jj_la1[159] = jj_gen;
            break label_256;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case configkeyword:
            c = configstmt();
            label_257:
            while (true) {
              if (jj_2_205(2)) {
                ;
              } else {
                break label_257;
              }
              un = unknownstatement();
                                                                  jjtn000.addUnknown(un);
            }
                jjtn000.setConfig(c);
            break;
          case mandatorykeyword:
            m = mandatorystmt();
            label_258:
            while (true) {
              if (jj_2_206(2)) {
                ;
              } else {
                break label_258;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setMandatory(m);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_259:
            while (true) {
              if (jj_2_207(2)) {
                ;
              } else {
                break label_259;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_260:
            while (true) {
              if (jj_2_208(2)) {
                ;
              } else {
                break label_260;
              }
              un = unknownstatement();
                                                                     jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          default:
            jj_la1[160] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[161] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Unknown unknownstatement() throws ParseException {
 /*@bgen(jjtree) Unknown */
YANG_Unknown jjtn000 = new YANG_Unknown(JJTUNKNOWN);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);String p = null;
String kw = null;
String str = null;
Token i,j,l;
YANG_Unknown unknown = null;
    try {
      p = prefix();
      l = jj_consume_token(94);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        i = jj_consume_token(IDENTIFIER);
                                        jjtn000.setExtension(i.image);
                                        jjtn000.setLine(i.beginLine);
                                        jjtn000.setCol(i.beginColumn);
        break;
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
        kw = anykeyword();
                                        jjtn000.setExtension(kw);
                                        jjtn000.setLine(l.beginLine);
                                        jjtn000.setCol(l.beginColumn);
        break;
      default:
        jj_la1[162] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        str = string();
                                        jjtn000.setArgument(str);
        break;
      default:
        jj_la1[163] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_261:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[164] = jj_gen;
            break label_261;
          }
          unknown = unknownstatement();
                                                        jjtn000.addUnknown(unknown);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[165] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                  jjtree.closeNodeScope(jjtn000, true);
                                  jjtc000 = false;
                                        jjtn000.setPrefix(p);
                                        {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
           if (jjtc000) {
             jjtree.clearNodeScope(jjtn000);
             jjtc000 = false;
           } else {
             jjtree.popNode();
           }
           if (jjte000 instanceof RuntimeException) {
             {if (true) throw (RuntimeException)jjte000;}
           }
           if (jjte000 instanceof ParseException) {
             {if (true) throw (ParseException)jjte000;}
           }
           {if (true) throw (Error)jjte000;}
    } finally {
           if (jjtc000) {
             jjtree.closeNodeScope(jjtn000, true);
           }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Augment augmentstmt() throws ParseException {
 /*@bgen(jjtree) Augment */
YANG_Augment jjtn000 = new YANG_Augment(JJTAUGMENT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token aug;
String a = null;
YANG_When w = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_DataDef da = null;
YANG_Case c = null;
YANG_Input i = null;
YANG_Output o = null;
YANG_Unknown un = null;
    try {
      aug = jj_consume_token(augmentkeyword);
      a = augmentargstr();
                jjtn000.setLine(aug.beginLine);
                jjtn000.setCol(aug.beginColumn);
                jjtn000.setAugment(a);
      jj_consume_token(91);
      label_262:
      while (true) {
        if (jj_2_209(2)) {
          ;
        } else {
          break label_262;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      label_263:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case anyxmlkeyword:
        case augmentkeyword:
        case casekeyword:
        case choicekeyword:
        case containerkeyword:
        case descriptionkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case referencekeyword:
        case statuskeyword:
        case useskeyword:
        case whenkeyword:
          ;
          break;
        default:
          jj_la1[166] = jj_gen;
          break label_263;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case whenkeyword:
          w = whenstmt();
          label_264:
          while (true) {
            if (jj_2_210(2)) {
              ;
            } else {
              break label_264;
            }
            un = unknownstatement();
                                                               jjtn000.addUnknown(un);
          }
                jjtn000.setWhen(w);
          break;
        case statuskeyword:
          s = statusstmt();
          label_265:
          while (true) {
            if (jj_2_211(2)) {
              ;
            } else {
              break label_265;
            }
            un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
          }
                jjtn000.setStatus(s);
          break;
        case descriptionkeyword:
          d = descriptionstmt();
          label_266:
          while (true) {
            if (jj_2_212(2)) {
              ;
            } else {
              break label_266;
            }
            un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
          }
                jjtn000.setDescription(d);
          break;
        case referencekeyword:
          r = referencestmt();
          label_267:
          while (true) {
            if (jj_2_213(2)) {
              ;
            } else {
              break label_267;
            }
            un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
          }
                jjtn000.setReference(r);
          break;
        case anyxmlkeyword:
        case augmentkeyword:
        case casekeyword:
        case choicekeyword:
        case containerkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case useskeyword:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case useskeyword:
            da = datadefstmt();
            label_268:
            while (true) {
              if (jj_2_214(2)) {
                ;
              } else {
                break label_268;
              }
              un = unknownstatement();
                                                                           jjtn000.addUnknown(un);
            }
                        jjtn000.addDataDef(da);
            break;
          case casekeyword:
            c = casestmt();
            label_269:
            while (true) {
              if (jj_2_215(2)) {
                ;
              } else {
                break label_269;
              }
              un = unknownstatement();
                                                                       jjtn000.addUnknown(un);
            }
                        jjtn000.addCase(c);
            break;
          default:
            jj_la1[167] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          break;
        default:
          jj_la1[168] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String augmentargstr() throws ParseException {
String a = null;
    if (jj_2_216(2)) {
      a = augmentarg();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        a = string();
        break;
      default:
        jj_la1[169] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  static final public String augmentarg() throws ParseException {
String a = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 95:
      a = absoluteschemanodeid();
      break;
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      a = descendantschemanodeid();
      break;
    default:
      jj_la1[170] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  static final public YANG_When whenstmt() throws ParseException {
 /*@bgen(jjtree) When */
YANG_When jjtn000 = new YANG_When(JJTWHEN);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token when;
String w = null;
YANG_Unknown un = null;
    try {
      when = jj_consume_token(whenkeyword);
      w = string();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_270:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case argumentkeyword:
          case augmentkeyword:
          case belongstokeyword:
          case bitkeyword:
          case casekeyword:
          case choicekeyword:
          case configkeyword:
          case contactkeyword:
          case containerkeyword:
          case defaultkeyword:
          case descriptionkeyword:
          case enumkeyword:
          case errorapptagkeyword:
          case errormessagekeyword:
          case extensionkeyword:
          case groupingkeyword:
          case importkeyword:
          case includekeyword:
          case inputkeyword:
          case keykeyword:
          case leafkeyword:
          case leaflistkeyword:
          case lengthkeyword:
          case listkeyword:
          case mandatorykeyword:
          case maxelementskeyword:
          case minelementskeyword:
          case modulekeyword:
          case mustkeyword:
          case namespacekeyword:
          case notificationkeyword:
          case orderedbykeyword:
          case organizationkeyword:
          case outputkeyword:
          case pathkeyword:
          case patternkeyword:
          case positionkeyword:
          case prefixkeyword:
          case presencekeyword:
          case rangekeyword:
          case referencekeyword:
          case revisionkeyword:
          case rpckeyword:
          case statuskeyword:
          case submodulekeyword:
          case typekeyword:
          case typedefkeyword:
          case uniquekeyword:
          case unitskeyword:
          case useskeyword:
          case valuekeyword:
          case whenkeyword:
          case yangversionkeyword:
          case yinelementkeyword:
          case currentkeyword:
          case deprecatedkeyword:
          case falsekeyword:
          case minkeyword:
          case maxkeyword:
          case nankeyword:
          case neginfkeyword:
          case obsoletekeyword:
          case posinfkeyword:
          case systemkeyword:
          case thisvariablekeyword:
          case truekeyword:
          case unboundedkeyword:
          case userkeyword:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[171] = jj_gen;
            break label_270;
          }
          un = unknownstatement();
                                             jjtn000.addUnknown(un);
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[172] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                jjtn000.setLine(when.beginLine);
                jjtn000.setCol(when.beginColumn);
                jjtn000.setWhen(w);
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Rpc rpcstmt() throws ParseException {
 /*@bgen(jjtree) Rpc */
YANG_Rpc jjtn000 = new YANG_Rpc(JJTRPC);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token to;
String i = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_Input in = null;
YANG_Output o = null;
YANG_Unknown un = null;
    try {
      to = jj_consume_token(rpckeyword);
      i = identifierstr();
                jjtn000.setLine(to.beginLine);
                jjtn000.setCol(to.beginColumn);
                jjtn000.setRpc(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_271:
        while (true) {
          if (jj_2_217(2)) {
            ;
          } else {
            break label_271;
          }
          un = unknownstatement();
                                                    jjtn000.addUnknown(un);
        }
        label_272:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case descriptionkeyword:
          case groupingkeyword:
          case inputkeyword:
          case outputkeyword:
          case referencekeyword:
          case statuskeyword:
          case typedefkeyword:
            ;
            break;
          default:
            jj_la1[173] = jj_gen;
            break label_272;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case statuskeyword:
            s = statusstmt();
            label_273:
            while (true) {
              if (jj_2_218(2)) {
                ;
              } else {
                break label_273;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_274:
            while (true) {
              if (jj_2_219(2)) {
                ;
              } else {
                break label_274;
              }
              un = unknownstatement();
                                                                        jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_275:
            while (true) {
              if (jj_2_220(2)) {
                ;
              } else {
                break label_275;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case groupingkeyword:
          case typedefkeyword:
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case typedefkeyword:
              t = typedefstmt();
                        jjtn000.addTypeDef(t);
              break;
            case groupingkeyword:
              g = groupingstmt();
                        jjtn000.addGrouping((YANG_Grouping)g);
              break;
            default:
              jj_la1[174] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
            label_276:
            while (true) {
              if (jj_2_221(2)) {
                ;
              } else {
                break label_276;
              }
              un = unknownstatement();
                                                    jjtn000.addUnknown(un);
            }
            break;
          case inputkeyword:
            in = inputstmt();
            label_277:
            while (true) {
              if (jj_2_222(2)) {
                ;
              } else {
                break label_277;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setInput(in);
            break;
          case outputkeyword:
            o = outputstmt();
            label_278:
            while (true) {
              if (jj_2_223(2)) {
                ;
              } else {
                break label_278;
              }
              un = unknownstatement();
                                                                   jjtn000.addUnknown(un);
            }
                jjtn000.setOutput(o);
            break;
          default:
            jj_la1[175] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[176] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Input inputstmt() throws ParseException {
 /*@bgen(jjtree) Input */
YANG_Input jjtn000 = new YANG_Input(JJTINPUT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token to;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_DataDef d = null;
YANG_Unknown un = null;
    try {
      to = jj_consume_token(inputkeyword);
                jjtn000.setLine(to.beginLine);
                jjtn000.setCol(to.beginColumn);
      jj_consume_token(91);
      label_279:
      while (true) {
        if (jj_2_224(2)) {
          ;
        } else {
          break label_279;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      label_280:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case groupingkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case typedefkeyword:
        case useskeyword:
          ;
          break;
        default:
          jj_la1[177] = jj_gen;
          break label_280;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case groupingkeyword:
        case typedefkeyword:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case typedefkeyword:
            t = typedefstmt();
                jjtn000.addTypeDef(t);
            break;
          case groupingkeyword:
            g = groupingstmt();
                jjtn000.addGrouping((YANG_Grouping)g);
            break;
          default:
            jj_la1[178] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          label_281:
          while (true) {
            if (jj_2_225(2)) {
              ;
            } else {
              break label_281;
            }
            un = unknownstatement();
                                                   jjtn000.addUnknown(un);
          }
          break;
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case useskeyword:
          label_282:
          while (true) {
            d = datadefstmt();
            label_283:
            while (true) {
              if (jj_2_226(2)) {
                ;
              } else {
                break label_283;
              }
              un = unknownstatement();
                                                                                jjtn000.addUnknown(un);
            }
                jjtn000.addDataDef(d);
            if (jj_2_227(2)) {
              ;
            } else {
              break label_282;
            }
          }
          break;
        default:
          jj_la1[179] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Output outputstmt() throws ParseException {
 /*@bgen(jjtree) Output */
YANG_Output jjtn000 = new YANG_Output(JJTOUTPUT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token to;
String i = null;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_DataDef d = null;
YANG_Unknown un = null;
    try {
      to = jj_consume_token(outputkeyword);
                jjtn000.setLine(to.beginLine);
                jjtn000.setCol(to.beginColumn);
      jj_consume_token(91);
      label_284:
      while (true) {
        if (jj_2_228(2)) {
          ;
        } else {
          break label_284;
        }
        un = unknownstatement();
                                                   jjtn000.addUnknown(un);
      }
      label_285:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case groupingkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case typedefkeyword:
        case useskeyword:
          ;
          break;
        default:
          jj_la1[180] = jj_gen;
          break label_285;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case groupingkeyword:
        case typedefkeyword:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case typedefkeyword:
            t = typedefstmt();
                jjtn000.addTypeDef(t);
            break;
          case groupingkeyword:
            g = groupingstmt();
                jjtn000.addGrouping((YANG_Grouping)g);
            break;
          default:
            jj_la1[181] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          label_286:
          while (true) {
            if (jj_2_229(2)) {
              ;
            } else {
              break label_286;
            }
            un = unknownstatement();
                                                  jjtn000.addUnknown(un);
          }
          break;
        case anyxmlkeyword:
        case augmentkeyword:
        case choicekeyword:
        case containerkeyword:
        case leafkeyword:
        case leaflistkeyword:
        case listkeyword:
        case useskeyword:
          label_287:
          while (true) {
            d = datadefstmt();
            label_288:
            while (true) {
              if (jj_2_230(2)) {
                ;
              } else {
                break label_288;
              }
              un = unknownstatement();
                                                                                jjtn000.addUnknown(un);
            }
            if (jj_2_231(2)) {
              ;
            } else {
              break label_287;
            }
          }
                jjtn000.addDataDef(d);
          break;
        default:
          jj_la1[182] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(92);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  static final public YANG_Notification notificationstmt() throws ParseException {
 /*@bgen(jjtree) Notification */
YANG_Notification jjtn000 = new YANG_Notification(JJTNOTIFICATION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token to;
String i = null;
YANG_Status s = null;
YANG_Description d = null;
YANG_Reference r = null;
YANG_TypeDef t = null;
YANG_Body g = null;
YANG_DataDef da = null;
YANG_Unknown un = null;
    try {
      to = jj_consume_token(notificationkeyword);
      i = identifierstr();
                jjtn000.setLine(to.beginLine);
                jjtn000.setCol(to.beginColumn);
                jjtn000.setNotification(i);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 93:
        jj_consume_token(93);
        break;
      case 91:
        jj_consume_token(91);
        label_289:
        while (true) {
          if (jj_2_232(2)) {
            ;
          } else {
            break label_289;
          }
          un = unknownstatement();
                                                   jjtn000.addUnknown(un);
        }
        label_290:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case containerkeyword:
          case descriptionkeyword:
          case groupingkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case referencekeyword:
          case statuskeyword:
          case typedefkeyword:
          case useskeyword:
            ;
            break;
          default:
            jj_la1[183] = jj_gen;
            break label_290;
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case statuskeyword:
            s = statusstmt();
            label_291:
            while (true) {
              if (jj_2_233(2)) {
                ;
              } else {
                break label_291;
              }
              un = unknownstatement();
                                                                 jjtn000.addUnknown(un);
            }
                jjtn000.setStatus(s);
            break;
          case descriptionkeyword:
            d = descriptionstmt();
            label_292:
            while (true) {
              if (jj_2_234(2)) {
                ;
              } else {
                break label_292;
              }
              un = unknownstatement();
                                                                      jjtn000.addUnknown(un);
            }
                jjtn000.setDescription(d);
            break;
          case referencekeyword:
            r = referencestmt();
            label_293:
            while (true) {
              if (jj_2_235(2)) {
                ;
              } else {
                break label_293;
              }
              un = unknownstatement();
                                                                    jjtn000.addUnknown(un);
            }
                jjtn000.setReference(r);
            break;
          case groupingkeyword:
          case typedefkeyword:
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case typedefkeyword:
              t = typedefstmt();
                jjtn000.addTypeDef(t);
              break;
            case groupingkeyword:
              g = groupingstmt();
                jjtn000.addGrouping((YANG_Grouping)g);
              break;
            default:
              jj_la1[184] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
            label_294:
            while (true) {
              if (jj_2_236(2)) {
                ;
              } else {
                break label_294;
              }
              un = unknownstatement();
                                                   jjtn000.addUnknown(un);
            }
            break;
          case anyxmlkeyword:
          case augmentkeyword:
          case choicekeyword:
          case containerkeyword:
          case leafkeyword:
          case leaflistkeyword:
          case listkeyword:
          case useskeyword:
            label_295:
            while (true) {
              da = datadefstmt();
              label_296:
              while (true) {
                if (jj_2_237(2)) {
                  ;
                } else {
                  break label_296;
                }
                un = unknownstatement();
                                                                                 jjtn000.addUnknown(un);
              }
              if (jj_2_238(2)) {
                ;
              } else {
                break label_295;
              }
            }
                jjtn000.addDataDef(da);
            break;
          default:
            jj_la1[185] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        jj_consume_token(92);
        break;
      default:
        jj_la1[186] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

// Ranges
  static final public String rangeexprstr() throws ParseException {
String r = null;
    r = string();
                {if (true) return r;}
    throw new Error("Missing return statement in function");
  }

// Lengths
  static final public String lengthexprstr() throws ParseException {
String l = null;
    l = string();
                {if (true) return l;}
    throw new Error("Missing return statement in function");
  }

// Date
  static final public String dateexprstr() throws ParseException {
 String d = null;
    //< a string which matches the rule
            //   dateexpr >
            d = dateexpr();
                {if (true) return d;}
    throw new Error("Missing return statement in function");
  }

  static final public String dateexpr() throws ParseException {
 String d = null;
Token a, b,c;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DATE:
      a = jj_consume_token(DATE);
                {if (true) return a.image;}
      break;
    case DECVALUE:
    case NEGDECVALUE:
    case STRING:
    case FLOAT:
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      d = string();
                {if (true) return d;}
      break;
    default:
      jj_la1[187] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

// Schema Node Identifiers
  static final public String schemanodeid() throws ParseException {
String s = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 95:
      s = absoluteschemanodeid();
                {if (true) return s;}
      break;
    case DDOT:
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
    case 96:
      s = relativeschemanodeid();
                {if (true) return s;}
      break;
    default:
      jj_la1[188] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String absoluteschemanodeid() throws ParseException {
String n = null,a = new String();
    label_297:
    while (true) {
      jj_consume_token(95);
      n = nodeidentifier();
                a += "/" + n;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 95:
        ;
        break;
      default:
        jj_la1[189] = jj_gen;
        break label_297;
      }
    }
                {if (true) return a;}
    throw new Error("Missing return statement in function");
  }

  static final public String relativeschemanodeid() throws ParseException {
String d = null, r = new String();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      d = descendantschemanodeid();
      break;
    case DDOT:
    case 96:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 96:
        jj_consume_token(96);
               d = ".";
        break;
      case DDOT:
        jj_consume_token(DDOT);
                                d = "..";
        break;
      default:
        jj_la1[190] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(97);
                                                 d += "|";
      label_298:
      while (true) {
        if (jj_2_239(2)) {
          ;
        } else {
          break label_298;
        }
        r = relativeschemanodeid();
                d += r;
      }
                {if (true) return d;}
      break;
    default:
      jj_la1[191] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String descendantschemanodeid() throws ParseException {
String d = null,a = null;
    d = nodeidentifier();
    a = absoluteschemanodeid();
                {if (true) return d + a;}
    throw new Error("Missing return statement in function");
  }

  static final public String nodeidentifier() throws ParseException {
Token t = null;
String p = null,n = new String(), m = null;
    if (jj_2_240(2)) {
      p = prefix();
      jj_consume_token(94);
    } else {
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
      break;
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
      m = anykeyword();
      break;
    default:
      jj_la1[192] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                if(p != null)
                        n += p + ":";
                if(m == null)
                   {if (true) return n + t.image;}
                else
                   {if (true) return n + m;}
    throw new Error("Missing return statement in function");
  }

// Instance Identifiers
  static final public void instanceidentifierstr() throws ParseException {
    instanceidentifier();
  }

  static final public void instanceidentifier() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 95:
      absoluteinstid();
      break;
    case DDOT:
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
    case 96:
      relativeinstid();
      break;
    default:
      jj_la1[193] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void absoluteinstid() throws ParseException {
    label_299:
    while (true) {
      jj_consume_token(95);
      nodeidentifier();
      label_300:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 98:
          ;
          break;
        default:
          jj_la1[194] = jj_gen;
          break label_300;
        }
        predicate();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 95:
        ;
        break;
      default:
        jj_la1[195] = jj_gen;
        break label_299;
      }
    }
  }

  static final public void relativeinstid() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      descendantinstid();
      break;
    case DDOT:
    case 96:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 96:
        jj_consume_token(96);
        break;
      case DDOT:
        jj_consume_token(DDOT);
        break;
      default:
        jj_la1[196] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(95);
      label_301:
      while (true) {
        if (jj_2_241(2)) {
          ;
        } else {
          break label_301;
        }
        relativeinstid();
      }
      break;
    default:
      jj_la1[197] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void descendantinstid() throws ParseException {
    nodeidentifier();
    label_302:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 98:
        ;
        break;
      default:
        jj_la1[198] = jj_gen;
        break label_302;
      }
      predicate();
    }
    absoluteinstid();
  }

  static final public void predicate() throws ParseException {
    jj_consume_token(98);
    predicateexpr();
    jj_consume_token(99);
  }

  static final public void predicateexpr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      nodeidentifier();
      break;
    case 96:
      jj_consume_token(96);
      break;
    default:
      jj_la1[199] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(100);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DQUOTE:
      jj_consume_token(DQUOTE);
      string();
      jj_consume_token(DQUOTE);
      break;
    case SQUOTE:
      jj_consume_token(SQUOTE);
      string();
      jj_consume_token(SQUOTE);
      break;
    default:
      jj_la1[200] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

// keyref path
  static final public String pathargstr() throws ParseException {
String p = null;
    if (jj_2_242(2)) {
      p = patharg();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        p = string();
        break;
      default:
        jj_la1[201] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  static final public String patharg() throws ParseException {
String p = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 95:
      p = absolutepatharg();
      break;
    case DDOT:
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      p = relativepatharg();
      break;
    default:
      jj_la1[202] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  static final public String absolutepatharg() throws ParseException {
String p = null, n = null, ap = new String();
    label_303:
    while (true) {
      jj_consume_token(95);
                ap += "/";
      n = nodeidentifier();
                        ap += n;
      label_304:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 98:
          ;
          break;
        default:
          jj_la1[203] = jj_gen;
          break label_304;
        }
        p = pathpredicate();
                        ap += p;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 95:
        ;
        break;
      default:
        jj_la1[204] = jj_gen;
        break label_303;
      }
    }
                {if (true) return ap;}
    throw new Error("Missing return statement in function");
  }

  static final public String relativepatharg() throws ParseException {
String rp = new String(), d = null, r = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
    case IDENTIFIER:
      d = descendantpatharg();
                {if (true) return d;}
      break;
    case DDOT:
      jj_consume_token(DDOT);
      jj_consume_token(95);
                rp += "../";
      label_305:
      while (true) {
        if (jj_2_243(2)) {
          ;
        } else {
          break label_305;
        }
        r = relativepatharg();
                rp += r;
      }
                {if (true) return rp;}
      break;
    default:
      jj_la1[205] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String descendantpatharg() throws ParseException {
String n  = null, p = null,a = null,dp = new String();
    n = nodeidentifier();
                dp += n;
    label_306:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 98:
        ;
        break;
      default:
        jj_la1[206] = jj_gen;
        break label_306;
      }
      p = pathpredicate();
                dp += p;
    }
    a = absolutepatharg();
                dp += a;
                {if (true) return dp;}
    throw new Error("Missing return statement in function");
  }

  static final public String pathpredicate() throws ParseException {
String p = null;
    jj_consume_token(98);
    p = pathequalityexpr();
    jj_consume_token(99);
                {if (true) return "[" + p + "]";}
    throw new Error("Missing return statement in function");
  }

  static final public String pathequalityexpr() throws ParseException {
String n = null, p = null;
    n = nodeidentifier();
    jj_consume_token(100);
    p = pathkeyexpr();
                {if (true) return n + "=" + p;}
    throw new Error("Missing return statement in function");
  }

  static final public String pathkeyexpr() throws ParseException {
String p = null;
    jj_consume_token(thisvariablekeyword);
    jj_consume_token(95);
    p = relpathkeyexpr();
                {if (true) return "this/" + p;}
    throw new Error("Missing return statement in function");
  }

  static final public String relpathkeyexpr() throws ParseException {
String n = null, rp = new String();
    label_307:
    while (true) {
      jj_consume_token(DDOT);
      jj_consume_token(95);
                rp += "../";
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DDOT:
        ;
        break;
      default:
        jj_la1[207] = jj_gen;
        break label_307;
      }
    }
    label_308:
    while (true) {
      if (jj_2_244(2)) {
        ;
      } else {
        break label_308;
      }
      n = nodeidentifier();
      jj_consume_token(95);
                rp += n + "/";
    }
    n = nodeidentifier();
                rp += n;
                {if (true) return rp;}
    throw new Error("Missing return statement in function");
  }

  static final public String prefixstr() throws ParseException {
 String s;
    //s = prefix()
            //|
            s = string();
         {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  static final public String prefix() throws ParseException {
 Token t; String s = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                {if (true) return t.image;}
      break;
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
      s = anykeyword();
                {if (true) return s;}
      break;
    default:
      jj_la1[208] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String identifierstr() throws ParseException {
 Token t;
String s;
    // < a string which matches the rule
                    //   identifier >
    
                    s = string();
                        {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  static final public String anykeyword() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case argumentkeyword:
      t = jj_consume_token(argumentkeyword);
      break;
    case anyxmlkeyword:
      t = jj_consume_token(anyxmlkeyword);
      break;
    case augmentkeyword:
      t = jj_consume_token(augmentkeyword);
      break;
    case belongstokeyword:
      t = jj_consume_token(belongstokeyword);
      break;
    case bitkeyword:
      t = jj_consume_token(bitkeyword);
      break;
    case casekeyword:
      t = jj_consume_token(casekeyword);
      break;
    case choicekeyword:
      t = jj_consume_token(choicekeyword);
      break;
    case configkeyword:
      t = jj_consume_token(configkeyword);
      break;
    case contactkeyword:
      t = jj_consume_token(contactkeyword);
      break;
    case containerkeyword:
      t = jj_consume_token(containerkeyword);
      break;
    case defaultkeyword:
      t = jj_consume_token(defaultkeyword);
      break;
    case descriptionkeyword:
      t = jj_consume_token(descriptionkeyword);
      break;
    case enumkeyword:
      t = jj_consume_token(enumkeyword);
      break;
    case errorapptagkeyword:
      t = jj_consume_token(errorapptagkeyword);
      break;
    case errormessagekeyword:
      t = jj_consume_token(errormessagekeyword);
      break;
    case extensionkeyword:
      t = jj_consume_token(extensionkeyword);
      break;
    case groupingkeyword:
      t = jj_consume_token(groupingkeyword);
      break;
    case importkeyword:
      t = jj_consume_token(importkeyword);
      break;
    case includekeyword:
      t = jj_consume_token(includekeyword);
      break;
    case inputkeyword:
      t = jj_consume_token(inputkeyword);
      break;
    case keykeyword:
      t = jj_consume_token(keykeyword);
      break;
    case leafkeyword:
      t = jj_consume_token(leafkeyword);
      break;
    case leaflistkeyword:
      t = jj_consume_token(leaflistkeyword);
      break;
    case lengthkeyword:
      t = jj_consume_token(lengthkeyword);
      break;
    case listkeyword:
      t = jj_consume_token(listkeyword);
      break;
    case mandatorykeyword:
      t = jj_consume_token(mandatorykeyword);
      break;
    case maxelementskeyword:
      t = jj_consume_token(maxelementskeyword);
      break;
    case minelementskeyword:
      t = jj_consume_token(minelementskeyword);
      break;
    case modulekeyword:
      t = jj_consume_token(modulekeyword);
      break;
    case mustkeyword:
      t = jj_consume_token(mustkeyword);
      break;
    case namespacekeyword:
      t = jj_consume_token(namespacekeyword);
      break;
    case notificationkeyword:
      t = jj_consume_token(notificationkeyword);
      break;
    case orderedbykeyword:
      t = jj_consume_token(orderedbykeyword);
      break;
    case organizationkeyword:
      t = jj_consume_token(organizationkeyword);
      break;
    case outputkeyword:
      t = jj_consume_token(outputkeyword);
      break;
    case pathkeyword:
      t = jj_consume_token(pathkeyword);
      break;
    case patternkeyword:
      t = jj_consume_token(patternkeyword);
      break;
    case positionkeyword:
      t = jj_consume_token(positionkeyword);
      break;
    case prefixkeyword:
      t = jj_consume_token(prefixkeyword);
      break;
    case presencekeyword:
      t = jj_consume_token(presencekeyword);
      break;
    case rangekeyword:
      t = jj_consume_token(rangekeyword);
      break;
    case referencekeyword:
      t = jj_consume_token(referencekeyword);
      break;
    case revisionkeyword:
      t = jj_consume_token(revisionkeyword);
      break;
    case rpckeyword:
      t = jj_consume_token(rpckeyword);
      break;
    case statuskeyword:
      t = jj_consume_token(statuskeyword);
      break;
    case submodulekeyword:
      t = jj_consume_token(submodulekeyword);
      break;
    case typekeyword:
      t = jj_consume_token(typekeyword);
      break;
    case typedefkeyword:
      t = jj_consume_token(typedefkeyword);
      break;
    case uniquekeyword:
      t = jj_consume_token(uniquekeyword);
      break;
    case unitskeyword:
      t = jj_consume_token(unitskeyword);
      break;
    case useskeyword:
      t = jj_consume_token(useskeyword);
      break;
    case valuekeyword:
      t = jj_consume_token(valuekeyword);
      break;
    case whenkeyword:
      t = jj_consume_token(whenkeyword);
      break;
    case yangversionkeyword:
      t = jj_consume_token(yangversionkeyword);
      break;
    case yinelementkeyword:
      t = jj_consume_token(yinelementkeyword);
      break;
    case currentkeyword:
      t = jj_consume_token(currentkeyword);
      break;
    case deprecatedkeyword:
      t = jj_consume_token(deprecatedkeyword);
      break;
    case falsekeyword:
      t = jj_consume_token(falsekeyword);
      break;
    case maxkeyword:
      t = jj_consume_token(maxkeyword);
      break;
    case minkeyword:
      t = jj_consume_token(minkeyword);
      break;
    case nankeyword:
      t = jj_consume_token(nankeyword);
      break;
    case neginfkeyword:
      t = jj_consume_token(neginfkeyword);
      break;
    case obsoletekeyword:
      t = jj_consume_token(obsoletekeyword);
      break;
    case posinfkeyword:
      t = jj_consume_token(posinfkeyword);
      break;
    case systemkeyword:
      t = jj_consume_token(systemkeyword);
      break;
    case thisvariablekeyword:
      t = jj_consume_token(thisvariablekeyword);
      break;
    case truekeyword:
      t = jj_consume_token(truekeyword);
      break;
    case unboundedkeyword:
      t = jj_consume_token(unboundedkeyword);
      break;
    case userkeyword:
      t = jj_consume_token(userkeyword);
      break;
    default:
      jj_la1[209] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
 {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public String identifierrefstr() throws ParseException {
String i = null;
    if (jj_2_245(2)) {
      i = identifierref();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DECVALUE:
      case NEGDECVALUE:
      case STRING:
      case FLOAT:
      case anyxmlkeyword:
      case argumentkeyword:
      case augmentkeyword:
      case belongstokeyword:
      case bitkeyword:
      case casekeyword:
      case choicekeyword:
      case configkeyword:
      case contactkeyword:
      case containerkeyword:
      case defaultkeyword:
      case descriptionkeyword:
      case enumkeyword:
      case errorapptagkeyword:
      case errormessagekeyword:
      case extensionkeyword:
      case groupingkeyword:
      case importkeyword:
      case includekeyword:
      case inputkeyword:
      case keykeyword:
      case leafkeyword:
      case leaflistkeyword:
      case lengthkeyword:
      case listkeyword:
      case mandatorykeyword:
      case maxelementskeyword:
      case minelementskeyword:
      case modulekeyword:
      case mustkeyword:
      case namespacekeyword:
      case notificationkeyword:
      case orderedbykeyword:
      case organizationkeyword:
      case outputkeyword:
      case pathkeyword:
      case patternkeyword:
      case positionkeyword:
      case prefixkeyword:
      case presencekeyword:
      case rangekeyword:
      case referencekeyword:
      case revisionkeyword:
      case rpckeyword:
      case statuskeyword:
      case submodulekeyword:
      case typekeyword:
      case typedefkeyword:
      case uniquekeyword:
      case unitskeyword:
      case useskeyword:
      case valuekeyword:
      case whenkeyword:
      case yangversionkeyword:
      case yinelementkeyword:
      case currentkeyword:
      case deprecatedkeyword:
      case falsekeyword:
      case minkeyword:
      case maxkeyword:
      case nankeyword:
      case neginfkeyword:
      case obsoletekeyword:
      case posinfkeyword:
      case systemkeyword:
      case thisvariablekeyword:
      case truekeyword:
      case unboundedkeyword:
      case userkeyword:
      case IDENTIFIER:
        i = string();
        break;
      default:
        jj_la1[210] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                {if (true) return i;}
    throw new Error("Missing return statement in function");
  }

  static final public String identifierref() throws ParseException {
Token t;
String p = null;
String i = null;
    if (jj_2_246(2)) {
      p = prefix();
      jj_consume_token(94);
    } else {
      ;
    }
    t = jj_consume_token(IDENTIFIER);
                i = new String();
                if(p != null)
                        i += p + ":";
                i += t.image;
                {if (true) return i;}
    throw new Error("Missing return statement in function");
  }

  static final public String string() throws ParseException {
 Token t, u;
String k = "",s = null,a = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING:
      t = jj_consume_token(STRING);
                        s = t.image;
      label_309:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 101:
          ;
          break;
        default:
          jj_la1[211] = jj_gen;
          break label_309;
        }
        jj_consume_token(101);
        u = jj_consume_token(STRING);
                        s = s + " + " + u.image;
      }
                         {if (true) return s;}
      break;
    case anyxmlkeyword:
    case argumentkeyword:
    case augmentkeyword:
    case belongstokeyword:
    case bitkeyword:
    case casekeyword:
    case choicekeyword:
    case configkeyword:
    case contactkeyword:
    case containerkeyword:
    case defaultkeyword:
    case descriptionkeyword:
    case enumkeyword:
    case errorapptagkeyword:
    case errormessagekeyword:
    case extensionkeyword:
    case groupingkeyword:
    case importkeyword:
    case includekeyword:
    case inputkeyword:
    case keykeyword:
    case leafkeyword:
    case leaflistkeyword:
    case lengthkeyword:
    case listkeyword:
    case mandatorykeyword:
    case maxelementskeyword:
    case minelementskeyword:
    case modulekeyword:
    case mustkeyword:
    case namespacekeyword:
    case notificationkeyword:
    case orderedbykeyword:
    case organizationkeyword:
    case outputkeyword:
    case pathkeyword:
    case patternkeyword:
    case positionkeyword:
    case prefixkeyword:
    case presencekeyword:
    case rangekeyword:
    case referencekeyword:
    case revisionkeyword:
    case rpckeyword:
    case statuskeyword:
    case submodulekeyword:
    case typekeyword:
    case typedefkeyword:
    case uniquekeyword:
    case unitskeyword:
    case useskeyword:
    case valuekeyword:
    case whenkeyword:
    case yangversionkeyword:
    case yinelementkeyword:
    case currentkeyword:
    case deprecatedkeyword:
    case falsekeyword:
    case minkeyword:
    case maxkeyword:
    case nankeyword:
    case neginfkeyword:
    case obsoletekeyword:
    case posinfkeyword:
    case systemkeyword:
    case thisvariablekeyword:
    case truekeyword:
    case unboundedkeyword:
    case userkeyword:
      s = anykeyword();
                         {if (true) return s;}
      break;
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                        {if (true) return t.image;}
      break;
    case DECVALUE:
      t = jj_consume_token(DECVALUE);
                        {if (true) return t.image;}
      break;
    case NEGDECVALUE:
      t = jj_consume_token(NEGDECVALUE);
                        {if (true) return t.image;}
      break;
    case FLOAT:
      t = jj_consume_token(FLOAT);
                        {if (true) return t.image;}
      break;
    default:
      jj_la1[212] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String decimalvalue() throws ParseException {
 String n = null;
Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NEGDECVALUE:
      //("-" n = nonnegativedecimalvalue())  
              t = jj_consume_token(NEGDECVALUE);
                {if (true) return  t.image;}
      break;
    case DECVALUE:
      n = nonnegativedecimalvalue();
                {if (true) return n;}
      break;
    default:
      jj_la1[213] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String nonnegativedecimalvalue() throws ParseException {
 String p = null;
    // "0" or positive
            p = positivedecimalvalue();
                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  static final public String positivedecimalvalue() throws ParseException {
 Token t;
    t = jj_consume_token(DECVALUE);
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public String zerodecimalvalue() throws ParseException {
Token t;
    t = jj_consume_token(DECVALUE);
                {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public String floatvalue() throws ParseException {
Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case neginfkeyword:
      t = jj_consume_token(neginfkeyword);
                {if (true) return t.image;}
      break;
    case posinfkeyword:
      t = jj_consume_token(posinfkeyword);
                {if (true) return t.image;}
      break;
    case nankeyword:
      t = jj_consume_token(nankeyword);
                {if (true) return t.image;}
      break;
    case FLOAT:
      t = jj_consume_token(FLOAT);
                {if (true) return t.image;}
      break;
    default:
      jj_la1[214] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static final private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static final private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static final private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static final private boolean jj_2_5(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static final private boolean jj_2_6(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  static final private boolean jj_2_7(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_7(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(6, xla); }
  }

  static final private boolean jj_2_8(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_8(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(7, xla); }
  }

  static final private boolean jj_2_9(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_9(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(8, xla); }
  }

  static final private boolean jj_2_10(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_10(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(9, xla); }
  }

  static final private boolean jj_2_11(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_11(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(10, xla); }
  }

  static final private boolean jj_2_12(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_12(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(11, xla); }
  }

  static final private boolean jj_2_13(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_13(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(12, xla); }
  }

  static final private boolean jj_2_14(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_14(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(13, xla); }
  }

  static final private boolean jj_2_15(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_15(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(14, xla); }
  }

  static final private boolean jj_2_16(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_16(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(15, xla); }
  }

  static final private boolean jj_2_17(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_17(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(16, xla); }
  }

  static final private boolean jj_2_18(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_18(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(17, xla); }
  }

  static final private boolean jj_2_19(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_19(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(18, xla); }
  }

  static final private boolean jj_2_20(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_20(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(19, xla); }
  }

  static final private boolean jj_2_21(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_21(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(20, xla); }
  }

  static final private boolean jj_2_22(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_22(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(21, xla); }
  }

  static final private boolean jj_2_23(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_23(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(22, xla); }
  }

  static final private boolean jj_2_24(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_24(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(23, xla); }
  }

  static final private boolean jj_2_25(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_25(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(24, xla); }
  }

  static final private boolean jj_2_26(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_26(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(25, xla); }
  }

  static final private boolean jj_2_27(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_27(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(26, xla); }
  }

  static final private boolean jj_2_28(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_28(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(27, xla); }
  }

  static final private boolean jj_2_29(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_29(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(28, xla); }
  }

  static final private boolean jj_2_30(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_30(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(29, xla); }
  }

  static final private boolean jj_2_31(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_31(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(30, xla); }
  }

  static final private boolean jj_2_32(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_32(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(31, xla); }
  }

  static final private boolean jj_2_33(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_33(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(32, xla); }
  }

  static final private boolean jj_2_34(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_34(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(33, xla); }
  }

  static final private boolean jj_2_35(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_35(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(34, xla); }
  }

  static final private boolean jj_2_36(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_36(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(35, xla); }
  }

  static final private boolean jj_2_37(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_37(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(36, xla); }
  }

  static final private boolean jj_2_38(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_38(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(37, xla); }
  }

  static final private boolean jj_2_39(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_39(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(38, xla); }
  }

  static final private boolean jj_2_40(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_40(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(39, xla); }
  }

  static final private boolean jj_2_41(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_41(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(40, xla); }
  }

  static final private boolean jj_2_42(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_42(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(41, xla); }
  }

  static final private boolean jj_2_43(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_43(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(42, xla); }
  }

  static final private boolean jj_2_44(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_44(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(43, xla); }
  }

  static final private boolean jj_2_45(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_45(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(44, xla); }
  }

  static final private boolean jj_2_46(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_46(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(45, xla); }
  }

  static final private boolean jj_2_47(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_47(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(46, xla); }
  }

  static final private boolean jj_2_48(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_48(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(47, xla); }
  }

  static final private boolean jj_2_49(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_49(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(48, xla); }
  }

  static final private boolean jj_2_50(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_50(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(49, xla); }
  }

  static final private boolean jj_2_51(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_51(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(50, xla); }
  }

  static final private boolean jj_2_52(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_52(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(51, xla); }
  }

  static final private boolean jj_2_53(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_53(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(52, xla); }
  }

  static final private boolean jj_2_54(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_54(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(53, xla); }
  }

  static final private boolean jj_2_55(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_55(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(54, xla); }
  }

  static final private boolean jj_2_56(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_56(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(55, xla); }
  }

  static final private boolean jj_2_57(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_57(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(56, xla); }
  }

  static final private boolean jj_2_58(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_58(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(57, xla); }
  }

  static final private boolean jj_2_59(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_59(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(58, xla); }
  }

  static final private boolean jj_2_60(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_60(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(59, xla); }
  }

  static final private boolean jj_2_61(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_61(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(60, xla); }
  }

  static final private boolean jj_2_62(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_62(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(61, xla); }
  }

  static final private boolean jj_2_63(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_63(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(62, xla); }
  }

  static final private boolean jj_2_64(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_64(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(63, xla); }
  }

  static final private boolean jj_2_65(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_65(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(64, xla); }
  }

  static final private boolean jj_2_66(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_66(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(65, xla); }
  }

  static final private boolean jj_2_67(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_67(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(66, xla); }
  }

  static final private boolean jj_2_68(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_68(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(67, xla); }
  }

  static final private boolean jj_2_69(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_69(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(68, xla); }
  }

  static final private boolean jj_2_70(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_70(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(69, xla); }
  }

  static final private boolean jj_2_71(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_71(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(70, xla); }
  }

  static final private boolean jj_2_72(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_72(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(71, xla); }
  }

  static final private boolean jj_2_73(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_73(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(72, xla); }
  }

  static final private boolean jj_2_74(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_74(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(73, xla); }
  }

  static final private boolean jj_2_75(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_75(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(74, xla); }
  }

  static final private boolean jj_2_76(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_76(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(75, xla); }
  }

  static final private boolean jj_2_77(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_77(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(76, xla); }
  }

  static final private boolean jj_2_78(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_78(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(77, xla); }
  }

  static final private boolean jj_2_79(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_79(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(78, xla); }
  }

  static final private boolean jj_2_80(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_80(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(79, xla); }
  }

  static final private boolean jj_2_81(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_81(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(80, xla); }
  }

  static final private boolean jj_2_82(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_82(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(81, xla); }
  }

  static final private boolean jj_2_83(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_83(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(82, xla); }
  }

  static final private boolean jj_2_84(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_84(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(83, xla); }
  }

  static final private boolean jj_2_85(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_85(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(84, xla); }
  }

  static final private boolean jj_2_86(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_86(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(85, xla); }
  }

  static final private boolean jj_2_87(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_87(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(86, xla); }
  }

  static final private boolean jj_2_88(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_88(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(87, xla); }
  }

  static final private boolean jj_2_89(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_89(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(88, xla); }
  }

  static final private boolean jj_2_90(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_90(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(89, xla); }
  }

  static final private boolean jj_2_91(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_91(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(90, xla); }
  }

  static final private boolean jj_2_92(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_92(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(91, xla); }
  }

  static final private boolean jj_2_93(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_93(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(92, xla); }
  }

  static final private boolean jj_2_94(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_94(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(93, xla); }
  }

  static final private boolean jj_2_95(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_95(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(94, xla); }
  }

  static final private boolean jj_2_96(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_96(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(95, xla); }
  }

  static final private boolean jj_2_97(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_97(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(96, xla); }
  }

  static final private boolean jj_2_98(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_98(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(97, xla); }
  }

  static final private boolean jj_2_99(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_99(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(98, xla); }
  }

  static final private boolean jj_2_100(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_100(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(99, xla); }
  }

  static final private boolean jj_2_101(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_101(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(100, xla); }
  }

  static final private boolean jj_2_102(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_102(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(101, xla); }
  }

  static final private boolean jj_2_103(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_103(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(102, xla); }
  }

  static final private boolean jj_2_104(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_104(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(103, xla); }
  }

  static final private boolean jj_2_105(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_105(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(104, xla); }
  }

  static final private boolean jj_2_106(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_106(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(105, xla); }
  }

  static final private boolean jj_2_107(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_107(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(106, xla); }
  }

  static final private boolean jj_2_108(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_108(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(107, xla); }
  }

  static final private boolean jj_2_109(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_109(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(108, xla); }
  }

  static final private boolean jj_2_110(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_110(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(109, xla); }
  }

  static final private boolean jj_2_111(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_111(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(110, xla); }
  }

  static final private boolean jj_2_112(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_112(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(111, xla); }
  }

  static final private boolean jj_2_113(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_113(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(112, xla); }
  }

  static final private boolean jj_2_114(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_114(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(113, xla); }
  }

  static final private boolean jj_2_115(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_115(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(114, xla); }
  }

  static final private boolean jj_2_116(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_116(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(115, xla); }
  }

  static final private boolean jj_2_117(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_117(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(116, xla); }
  }

  static final private boolean jj_2_118(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_118(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(117, xla); }
  }

  static final private boolean jj_2_119(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_119(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(118, xla); }
  }

  static final private boolean jj_2_120(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_120(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(119, xla); }
  }

  static final private boolean jj_2_121(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_121(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(120, xla); }
  }

  static final private boolean jj_2_122(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_122(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(121, xla); }
  }

  static final private boolean jj_2_123(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_123(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(122, xla); }
  }

  static final private boolean jj_2_124(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_124(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(123, xla); }
  }

  static final private boolean jj_2_125(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_125(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(124, xla); }
  }

  static final private boolean jj_2_126(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_126(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(125, xla); }
  }

  static final private boolean jj_2_127(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_127(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(126, xla); }
  }

  static final private boolean jj_2_128(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_128(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(127, xla); }
  }

  static final private boolean jj_2_129(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_129(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(128, xla); }
  }

  static final private boolean jj_2_130(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_130(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(129, xla); }
  }

  static final private boolean jj_2_131(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_131(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(130, xla); }
  }

  static final private boolean jj_2_132(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_132(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(131, xla); }
  }

  static final private boolean jj_2_133(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_133(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(132, xla); }
  }

  static final private boolean jj_2_134(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_134(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(133, xla); }
  }

  static final private boolean jj_2_135(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_135(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(134, xla); }
  }

  static final private boolean jj_2_136(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_136(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(135, xla); }
  }

  static final private boolean jj_2_137(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_137(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(136, xla); }
  }

  static final private boolean jj_2_138(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_138(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(137, xla); }
  }

  static final private boolean jj_2_139(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_139(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(138, xla); }
  }

  static final private boolean jj_2_140(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_140(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(139, xla); }
  }

  static final private boolean jj_2_141(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_141(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(140, xla); }
  }

  static final private boolean jj_2_142(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_142(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(141, xla); }
  }

  static final private boolean jj_2_143(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_143(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(142, xla); }
  }

  static final private boolean jj_2_144(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_144(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(143, xla); }
  }

  static final private boolean jj_2_145(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_145(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(144, xla); }
  }

  static final private boolean jj_2_146(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_146(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(145, xla); }
  }

  static final private boolean jj_2_147(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_147(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(146, xla); }
  }

  static final private boolean jj_2_148(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_148(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(147, xla); }
  }

  static final private boolean jj_2_149(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_149(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(148, xla); }
  }

  static final private boolean jj_2_150(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_150(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(149, xla); }
  }

  static final private boolean jj_2_151(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_151(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(150, xla); }
  }

  static final private boolean jj_2_152(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_152(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(151, xla); }
  }

  static final private boolean jj_2_153(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_153(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(152, xla); }
  }

  static final private boolean jj_2_154(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_154(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(153, xla); }
  }

  static final private boolean jj_2_155(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_155(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(154, xla); }
  }

  static final private boolean jj_2_156(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_156(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(155, xla); }
  }

  static final private boolean jj_2_157(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_157(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(156, xla); }
  }

  static final private boolean jj_2_158(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_158(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(157, xla); }
  }

  static final private boolean jj_2_159(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_159(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(158, xla); }
  }

  static final private boolean jj_2_160(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_160(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(159, xla); }
  }

  static final private boolean jj_2_161(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_161(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(160, xla); }
  }

  static final private boolean jj_2_162(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_162(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(161, xla); }
  }

  static final private boolean jj_2_163(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_163(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(162, xla); }
  }

  static final private boolean jj_2_164(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_164(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(163, xla); }
  }

  static final private boolean jj_2_165(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_165(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(164, xla); }
  }

  static final private boolean jj_2_166(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_166(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(165, xla); }
  }

  static final private boolean jj_2_167(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_167(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(166, xla); }
  }

  static final private boolean jj_2_168(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_168(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(167, xla); }
  }

  static final private boolean jj_2_169(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_169(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(168, xla); }
  }

  static final private boolean jj_2_170(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_170(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(169, xla); }
  }

  static final private boolean jj_2_171(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_171(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(170, xla); }
  }

  static final private boolean jj_2_172(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_172(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(171, xla); }
  }

  static final private boolean jj_2_173(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_173(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(172, xla); }
  }

  static final private boolean jj_2_174(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_174(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(173, xla); }
  }

  static final private boolean jj_2_175(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_175(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(174, xla); }
  }

  static final private boolean jj_2_176(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_176(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(175, xla); }
  }

  static final private boolean jj_2_177(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_177(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(176, xla); }
  }

  static final private boolean jj_2_178(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_178(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(177, xla); }
  }

  static final private boolean jj_2_179(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_179(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(178, xla); }
  }

  static final private boolean jj_2_180(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_180(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(179, xla); }
  }

  static final private boolean jj_2_181(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_181(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(180, xla); }
  }

  static final private boolean jj_2_182(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_182(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(181, xla); }
  }

  static final private boolean jj_2_183(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_183(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(182, xla); }
  }

  static final private boolean jj_2_184(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_184(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(183, xla); }
  }

  static final private boolean jj_2_185(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_185(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(184, xla); }
  }

  static final private boolean jj_2_186(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_186(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(185, xla); }
  }

  static final private boolean jj_2_187(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_187(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(186, xla); }
  }

  static final private boolean jj_2_188(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_188(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(187, xla); }
  }

  static final private boolean jj_2_189(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_189(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(188, xla); }
  }

  static final private boolean jj_2_190(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_190(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(189, xla); }
  }

  static final private boolean jj_2_191(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_191(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(190, xla); }
  }

  static final private boolean jj_2_192(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_192(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(191, xla); }
  }

  static final private boolean jj_2_193(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_193(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(192, xla); }
  }

  static final private boolean jj_2_194(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_194(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(193, xla); }
  }

  static final private boolean jj_2_195(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_195(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(194, xla); }
  }

  static final private boolean jj_2_196(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_196(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(195, xla); }
  }

  static final private boolean jj_2_197(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_197(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(196, xla); }
  }

  static final private boolean jj_2_198(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_198(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(197, xla); }
  }

  static final private boolean jj_2_199(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_199(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(198, xla); }
  }

  static final private boolean jj_2_200(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_200(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(199, xla); }
  }

  static final private boolean jj_2_201(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_201(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(200, xla); }
  }

  static final private boolean jj_2_202(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_202(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(201, xla); }
  }

  static final private boolean jj_2_203(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_203(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(202, xla); }
  }

  static final private boolean jj_2_204(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_204(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(203, xla); }
  }

  static final private boolean jj_2_205(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_205(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(204, xla); }
  }

  static final private boolean jj_2_206(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_206(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(205, xla); }
  }

  static final private boolean jj_2_207(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_207(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(206, xla); }
  }

  static final private boolean jj_2_208(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_208(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(207, xla); }
  }

  static final private boolean jj_2_209(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_209(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(208, xla); }
  }

  static final private boolean jj_2_210(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_210(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(209, xla); }
  }

  static final private boolean jj_2_211(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_211(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(210, xla); }
  }

  static final private boolean jj_2_212(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_212(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(211, xla); }
  }

  static final private boolean jj_2_213(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_213(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(212, xla); }
  }

  static final private boolean jj_2_214(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_214(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(213, xla); }
  }

  static final private boolean jj_2_215(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_215(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(214, xla); }
  }

  static final private boolean jj_2_216(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_216(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(215, xla); }
  }

  static final private boolean jj_2_217(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_217(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(216, xla); }
  }

  static final private boolean jj_2_218(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_218(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(217, xla); }
  }

  static final private boolean jj_2_219(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_219(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(218, xla); }
  }

  static final private boolean jj_2_220(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_220(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(219, xla); }
  }

  static final private boolean jj_2_221(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_221(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(220, xla); }
  }

  static final private boolean jj_2_222(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_222(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(221, xla); }
  }

  static final private boolean jj_2_223(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_223(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(222, xla); }
  }

  static final private boolean jj_2_224(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_224(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(223, xla); }
  }

  static final private boolean jj_2_225(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_225(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(224, xla); }
  }

  static final private boolean jj_2_226(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_226(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(225, xla); }
  }

  static final private boolean jj_2_227(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_227(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(226, xla); }
  }

  static final private boolean jj_2_228(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_228(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(227, xla); }
  }

  static final private boolean jj_2_229(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_229(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(228, xla); }
  }

  static final private boolean jj_2_230(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_230(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(229, xla); }
  }

  static final private boolean jj_2_231(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_231(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(230, xla); }
  }

  static final private boolean jj_2_232(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_232(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(231, xla); }
  }

  static final private boolean jj_2_233(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_233(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(232, xla); }
  }

  static final private boolean jj_2_234(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_234(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(233, xla); }
  }

  static final private boolean jj_2_235(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_235(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(234, xla); }
  }

  static final private boolean jj_2_236(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_236(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(235, xla); }
  }

  static final private boolean jj_2_237(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_237(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(236, xla); }
  }

  static final private boolean jj_2_238(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_238(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(237, xla); }
  }

  static final private boolean jj_2_239(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_239(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(238, xla); }
  }

  static final private boolean jj_2_240(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_240(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(239, xla); }
  }

  static final private boolean jj_2_241(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_241(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(240, xla); }
  }

  static final private boolean jj_2_242(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_242(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(241, xla); }
  }

  static final private boolean jj_2_243(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_243(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(242, xla); }
  }

  static final private boolean jj_2_244(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_244(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(243, xla); }
  }

  static final private boolean jj_2_245(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_245(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(244, xla); }
  }

  static final private boolean jj_2_246(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_246(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(245, xla); }
  }

  static final private boolean jj_3R_388() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(20)) {
    jj_scanpos = xsp;
    if (jj_scan_token(19)) {
    jj_scanpos = xsp;
    if (jj_scan_token(21)) {
    jj_scanpos = xsp;
    if (jj_scan_token(22)) {
    jj_scanpos = xsp;
    if (jj_scan_token(23)) {
    jj_scanpos = xsp;
    if (jj_scan_token(24)) {
    jj_scanpos = xsp;
    if (jj_scan_token(25)) {
    jj_scanpos = xsp;
    if (jj_scan_token(26)) {
    jj_scanpos = xsp;
    if (jj_scan_token(27)) {
    jj_scanpos = xsp;
    if (jj_scan_token(28)) {
    jj_scanpos = xsp;
    if (jj_scan_token(29)) {
    jj_scanpos = xsp;
    if (jj_scan_token(30)) {
    jj_scanpos = xsp;
    if (jj_scan_token(31)) {
    jj_scanpos = xsp;
    if (jj_scan_token(32)) {
    jj_scanpos = xsp;
    if (jj_scan_token(33)) {
    jj_scanpos = xsp;
    if (jj_scan_token(34)) {
    jj_scanpos = xsp;
    if (jj_scan_token(35)) {
    jj_scanpos = xsp;
    if (jj_scan_token(36)) {
    jj_scanpos = xsp;
    if (jj_scan_token(37)) {
    jj_scanpos = xsp;
    if (jj_scan_token(38)) {
    jj_scanpos = xsp;
    if (jj_scan_token(39)) {
    jj_scanpos = xsp;
    if (jj_scan_token(40)) {
    jj_scanpos = xsp;
    if (jj_scan_token(41)) {
    jj_scanpos = xsp;
    if (jj_scan_token(42)) {
    jj_scanpos = xsp;
    if (jj_scan_token(43)) {
    jj_scanpos = xsp;
    if (jj_scan_token(44)) {
    jj_scanpos = xsp;
    if (jj_scan_token(45)) {
    jj_scanpos = xsp;
    if (jj_scan_token(46)) {
    jj_scanpos = xsp;
    if (jj_scan_token(47)) {
    jj_scanpos = xsp;
    if (jj_scan_token(48)) {
    jj_scanpos = xsp;
    if (jj_scan_token(49)) {
    jj_scanpos = xsp;
    if (jj_scan_token(50)) {
    jj_scanpos = xsp;
    if (jj_scan_token(51)) {
    jj_scanpos = xsp;
    if (jj_scan_token(52)) {
    jj_scanpos = xsp;
    if (jj_scan_token(53)) {
    jj_scanpos = xsp;
    if (jj_scan_token(54)) {
    jj_scanpos = xsp;
    if (jj_scan_token(55)) {
    jj_scanpos = xsp;
    if (jj_scan_token(56)) {
    jj_scanpos = xsp;
    if (jj_scan_token(57)) {
    jj_scanpos = xsp;
    if (jj_scan_token(58)) {
    jj_scanpos = xsp;
    if (jj_scan_token(59)) {
    jj_scanpos = xsp;
    if (jj_scan_token(60)) {
    jj_scanpos = xsp;
    if (jj_scan_token(61)) {
    jj_scanpos = xsp;
    if (jj_scan_token(62)) {
    jj_scanpos = xsp;
    if (jj_scan_token(63)) {
    jj_scanpos = xsp;
    if (jj_scan_token(64)) {
    jj_scanpos = xsp;
    if (jj_scan_token(65)) {
    jj_scanpos = xsp;
    if (jj_scan_token(66)) {
    jj_scanpos = xsp;
    if (jj_scan_token(67)) {
    jj_scanpos = xsp;
    if (jj_scan_token(68)) {
    jj_scanpos = xsp;
    if (jj_scan_token(69)) {
    jj_scanpos = xsp;
    if (jj_scan_token(70)) {
    jj_scanpos = xsp;
    if (jj_scan_token(71)) {
    jj_scanpos = xsp;
    if (jj_scan_token(72)) {
    jj_scanpos = xsp;
    if (jj_scan_token(73)) {
    jj_scanpos = xsp;
    if (jj_scan_token(74)) {
    jj_scanpos = xsp;
    if (jj_scan_token(75)) {
    jj_scanpos = xsp;
    if (jj_scan_token(76)) {
    jj_scanpos = xsp;
    if (jj_scan_token(78)) {
    jj_scanpos = xsp;
    if (jj_scan_token(77)) {
    jj_scanpos = xsp;
    if (jj_scan_token(79)) {
    jj_scanpos = xsp;
    if (jj_scan_token(80)) {
    jj_scanpos = xsp;
    if (jj_scan_token(81)) {
    jj_scanpos = xsp;
    if (jj_scan_token(82)) {
    jj_scanpos = xsp;
    if (jj_scan_token(83)) {
    jj_scanpos = xsp;
    if (jj_scan_token(84)) {
    jj_scanpos = xsp;
    if (jj_scan_token(85)) {
    jj_scanpos = xsp;
    if (jj_scan_token(86)) {
    jj_scanpos = xsp;
    if (jj_scan_token(87)) return true;
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    }
    return false;
  }

  static final private boolean jj_3R_320() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(85)) {
    jj_scanpos = xsp;
    if (jj_scan_token(76)) return true;
    }
    return false;
  }

  static final private boolean jj_3_19() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_346() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static final private boolean jj_3R_325() {
    Token xsp;
    if (jj_3R_346()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_346()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static final private boolean jj_3R_361() {
    if (jj_3R_388()) return true;
    return false;
  }

  static final private boolean jj_3_208() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_80() {
    if (jj_3R_320()) return true;
    return false;
  }

  static final private boolean jj_3R_311() {
    if (jj_3R_330()) return true;
    if (jj_scan_token(94)) return true;
    return false;
  }

  static final private boolean jj_3R_360() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static final private boolean jj_3_207() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_330() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_360()) {
    jj_scanpos = xsp;
    if (jj_3R_361()) return true;
    }
    return false;
  }

  static final private boolean jj_3_206() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_138() {
    if (jj_3R_325()) return true;
    return false;
  }

  static final private boolean jj_3_18() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_205() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_137() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_244() {
    if (jj_3R_334()) return true;
    if (jj_scan_token(95)) return true;
    return false;
  }

  static final private boolean jj_3_136() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_204() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_319() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(74)) {
    jj_scanpos = xsp;
    if (jj_scan_token(81)) {
    jj_scanpos = xsp;
    if (jj_scan_token(75)) return true;
    }
    }
    return false;
  }

  static final private boolean jj_3_135() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_134() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_133() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_203() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_79() {
    if (jj_3R_319()) return true;
    return false;
  }

  static final private boolean jj_3_202() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_132() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_131() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_201() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_130() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_129() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_200() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_199() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_402() {
    if (jj_scan_token(98)) return true;
    return false;
  }

  static final private boolean jj_3_128() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_127() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_198() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_318() {
    if (jj_3R_343()) return true;
    return false;
  }

  static final private boolean jj_3_126() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_398() {
    if (jj_3R_402()) return true;
    return false;
  }

  static final private boolean jj_3_125() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_391() {
    if (jj_3R_334()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_398()) { jj_scanpos = xsp; break; }
    }
    if (jj_3R_390()) return true;
    return false;
  }

  static final private boolean jj_3_78() {
    if (jj_3R_318()) return true;
    return false;
  }

  static final private boolean jj_3_197() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_124() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_77() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_243() {
    if (jj_3R_333()) return true;
    return false;
  }

  static final private boolean jj_3_76() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_196() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_381() {
    if (jj_scan_token(listkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3R_367() {
    if (jj_scan_token(DDOT)) return true;
    if (jj_scan_token(95)) return true;
    return false;
  }

  static final private boolean jj_3_195() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_75() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_194() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_366() {
    if (jj_3R_391()) return true;
    return false;
  }

  static final private boolean jj_3R_333() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_366()) {
    jj_scanpos = xsp;
    if (jj_3R_367()) return true;
    }
    return false;
  }

  static final private boolean jj_3_74() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_123() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_122() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_121() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_120() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_73() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_119() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_193() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_397() {
    if (jj_scan_token(95)) return true;
    if (jj_3R_334()) return true;
    return false;
  }

  static final private boolean jj_3_118() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_390() {
    Token xsp;
    if (jj_3R_397()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_397()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static final private boolean jj_3_192() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_317() {
    if (jj_scan_token(bitkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3_117() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_365() {
    if (jj_3R_333()) return true;
    return false;
  }

  static final private boolean jj_3_191() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_364() {
    if (jj_3R_390()) return true;
    return false;
  }

  static final private boolean jj_3_190() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_332() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_364()) {
    jj_scanpos = xsp;
    if (jj_3R_365()) return true;
    }
    return false;
  }

  static final private boolean jj_3_116() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_115() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_395() {
    if (jj_3R_400()) return true;
    return false;
  }

  static final private boolean jj_3_189() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_71() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_114() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_72() {
    if (jj_3R_317()) return true;
    return false;
  }

  static final private boolean jj_3_242() {
    if (jj_3R_332()) return true;
    return false;
  }

  static final private boolean jj_3_188() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_187() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_389() {
    if (jj_3R_334()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_395()) { jj_scanpos = xsp; break; }
    }
    if (jj_3R_396()) return true;
    return false;
  }

  static final private boolean jj_3R_400() {
    if (jj_scan_token(98)) return true;
    return false;
  }

  static final private boolean jj_3_113() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_241() {
    if (jj_3R_331()) return true;
    return false;
  }

  static final private boolean jj_3_69() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_363() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(96)) {
    jj_scanpos = xsp;
    if (jj_scan_token(12)) return true;
    }
    if (jj_scan_token(95)) return true;
    return false;
  }

  static final private boolean jj_3_17() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_70() {
    if (jj_3R_316()) return true;
    return false;
  }

  static final private boolean jj_3R_362() {
    if (jj_3R_389()) return true;
    return false;
  }

  static final private boolean jj_3R_331() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_362()) {
    jj_scanpos = xsp;
    if (jj_3R_363()) return true;
    }
    return false;
  }

  static final private boolean jj_3R_368() {
    if (jj_3R_388()) return true;
    return false;
  }

  static final private boolean jj_3_112() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_380() {
    if (jj_scan_token(leaflistkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3R_401() {
    if (jj_scan_token(95)) return true;
    return false;
  }

  static final private boolean jj_3_111() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_186() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_396() {
    Token xsp;
    if (jj_3R_401()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_401()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static final private boolean jj_3_68() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_16() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_110() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_109() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_67() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_185() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_66() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_108() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_184() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_107() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_183() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_65() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_182() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_64() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_106() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_181() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_105() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_104() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_180() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_63() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_240() {
    if (jj_3R_330()) return true;
    if (jj_scan_token(94)) return true;
    return false;
  }

  static final private boolean jj_3R_334() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_240()) jj_scanpos = xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(88)) {
    jj_scanpos = xsp;
    if (jj_3R_368()) return true;
    }
    return false;
  }

  static final private boolean jj_3_179() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_347() {
    if (jj_3R_334()) return true;
    if (jj_3R_377()) return true;
    return false;
  }

  static final private boolean jj_3R_336() {
    if (jj_scan_token(enumkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3R_387() {
    if (jj_scan_token(DDOT)) return true;
    return false;
  }

  static final private boolean jj_3_62() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_103() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_178() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_239() {
    if (jj_3R_329()) return true;
    return false;
  }

  static final private boolean jj_3R_357() {
    if (jj_3R_385()) return true;
    return false;
  }

  static final private boolean jj_3R_386() {
    if (jj_scan_token(96)) return true;
    return false;
  }

  static final private boolean jj_3R_356() {
    if (jj_3R_384()) return true;
    return false;
  }

  static final private boolean jj_3R_313() {
    if (jj_3R_336()) return true;
    return false;
  }

  static final private boolean jj_3R_379() {
    if (jj_scan_token(leafkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3_102() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_359() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_386()) {
    jj_scanpos = xsp;
    if (jj_3R_387()) return true;
    }
    if (jj_scan_token(97)) return true;
    return false;
  }

  static final private boolean jj_3R_355() {
    if (jj_3R_383()) return true;
    return false;
  }

  static final private boolean jj_3R_358() {
    if (jj_3R_347()) return true;
    return false;
  }

  static final private boolean jj_3R_354() {
    if (jj_3R_382()) return true;
    return false;
  }

  static final private boolean jj_3R_329() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_358()) {
    jj_scanpos = xsp;
    if (jj_3R_359()) return true;
    }
    return false;
  }

  static final private boolean jj_3R_353() {
    if (jj_3R_381()) return true;
    return false;
  }

  static final private boolean jj_3_177() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_352() {
    if (jj_3R_380()) return true;
    return false;
  }

  static final private boolean jj_3_176() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_351() {
    if (jj_3R_379()) return true;
    return false;
  }

  static final private boolean jj_3R_350() {
    if (jj_3R_378()) return true;
    return false;
  }

  static final private boolean jj_3R_328() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_350()) {
    jj_scanpos = xsp;
    if (jj_3R_351()) {
    jj_scanpos = xsp;
    if (jj_3R_352()) {
    jj_scanpos = xsp;
    if (jj_3R_353()) {
    jj_scanpos = xsp;
    if (jj_3R_354()) {
    jj_scanpos = xsp;
    if (jj_3R_355()) {
    jj_scanpos = xsp;
    if (jj_3R_356()) {
    jj_scanpos = xsp;
    if (jj_3R_357()) return true;
    }
    }
    }
    }
    }
    }
    }
    return false;
  }

  static final private boolean jj_3_100() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_175() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_61() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_393() {
    if (jj_scan_token(95)) return true;
    if (jj_3R_334()) return true;
    return false;
  }

  static final private boolean jj_3_60() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_174() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_15() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_99() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_324() {
    if (jj_3R_345()) return true;
    return false;
  }

  static final private boolean jj_3R_377() {
    Token xsp;
    if (jj_3R_393()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_393()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static final private boolean jj_3_59() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_98() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_323() {
    if (jj_3R_344()) return true;
    return false;
  }

  static final private boolean jj_3_58() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_173() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_14() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_97() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_101() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_323()) {
    jj_scanpos = xsp;
    if (jj_3R_324()) return true;
    }
    return false;
  }

  static final private boolean jj_3_13() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_172() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_96() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_95() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_11() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_12() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_57() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_94() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_9() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_171() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_10() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_338() {
    if (jj_scan_token(91)) return true;
    return false;
  }

  static final private boolean jj_3R_314() {
    if (jj_scan_token(patternkeyword)) return true;
    if (jj_3R_337()) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(93)) {
    jj_scanpos = xsp;
    if (jj_3R_338()) return true;
    }
    return false;
  }

  static final private boolean jj_3_56() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_8() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_55() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_7() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_54() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_93() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_170() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_169() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_53() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_168() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_339() {
    if (jj_3R_337()) return true;
    return false;
  }

  static final private boolean jj_3_237() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_378() {
    if (jj_scan_token(containerkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3_5() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_4() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_6() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_167() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_92() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_166() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_52() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_165() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_340() {
    if (jj_scan_token(91)) return true;
    return false;
  }

  static final private boolean jj_3_90() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_89() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_322() {
    if (jj_3R_345()) return true;
    return false;
  }

  static final private boolean jj_3R_315() {
    if (jj_scan_token(lengthkeyword)) return true;
    if (jj_3R_339()) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(93)) {
    jj_scanpos = xsp;
    if (jj_3R_340()) return true;
    }
    return false;
  }

  static final private boolean jj_3_88() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_235() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_164() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_236() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_321() {
    if (jj_3R_344()) return true;
    return false;
  }

  static final private boolean jj_3_234() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_238() {
    if (jj_3R_328()) return true;
    return false;
  }

  static final private boolean jj_3_87() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_49() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_91() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_321()) {
    jj_scanpos = xsp;
    if (jj_3R_322()) return true;
    }
    return false;
  }

  static final private boolean jj_3_233() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_48() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_50() {
    if (jj_3R_315()) return true;
    return false;
  }

  static final private boolean jj_3_86() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_46() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_232() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_47() {
    if (jj_3R_314()) return true;
    return false;
  }

  static final private boolean jj_3_230() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_3() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_44() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_163() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_345() {
    if (jj_scan_token(groupingkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3_162() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_45() {
    if (jj_3R_314()) return true;
    return false;
  }

  static final private boolean jj_3_161() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_43() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_51() {
    if (jj_3R_315()) return true;
    return false;
  }

  static final private boolean jj_3_160() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_42() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_40() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_41() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_229() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_231() {
    if (jj_3R_328()) return true;
    return false;
  }

  static final private boolean jj_3_159() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_226() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_228() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_2() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_384() {
    if (jj_scan_token(useskeyword)) return true;
    if (jj_3R_341()) return true;
    return false;
  }

  static final private boolean jj_3_158() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_376() {
    if (jj_scan_token(DECVALUE)) return true;
    return false;
  }

  static final private boolean jj_3_157() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_39() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_310() {
    if (jj_scan_token(modulekeyword)) return true;
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static final private boolean jj_3R_343() {
    if (jj_3R_376()) return true;
    return false;
  }

  static final private boolean jj_3_156() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_38() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_155() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_225() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_227() {
    if (jj_3R_328()) return true;
    return false;
  }

  static final private boolean jj_3_154() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_224() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_1() {
    if (jj_3R_310()) return true;
    return false;
  }

  static final private boolean jj_3R_374() {
    if (jj_scan_token(FLOAT)) return true;
    return false;
  }

  static final private boolean jj_3_223() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_153() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_373() {
    if (jj_scan_token(NEGDECVALUE)) return true;
    return false;
  }

  static final private boolean jj_3_37() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_222() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_372() {
    if (jj_scan_token(DECVALUE)) return true;
    return false;
  }

  static final private boolean jj_3R_371() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static final private boolean jj_3_152() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_383() {
    if (jj_scan_token(anyxmlkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3R_370() {
    if (jj_3R_388()) return true;
    return false;
  }

  static final private boolean jj_3_220() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_151() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_219() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_221() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_150() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_36() {
    if (jj_3R_313()) return true;
    return false;
  }

  static final private boolean jj_3R_392() {
    if (jj_scan_token(101)) return true;
    return false;
  }

  static final private boolean jj_3_218() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_35() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_149() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_369() {
    if (jj_scan_token(STRING)) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_392()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static final private boolean jj_3R_337() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_369()) {
    jj_scanpos = xsp;
    if (jj_3R_370()) {
    jj_scanpos = xsp;
    if (jj_3R_371()) {
    jj_scanpos = xsp;
    if (jj_3R_372()) {
    jj_scanpos = xsp;
    if (jj_3R_373()) {
    jj_scanpos = xsp;
    if (jj_3R_374()) return true;
    }
    }
    }
    }
    }
    return false;
  }

  static final private boolean jj_3_85() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_84() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_246() {
    if (jj_3R_330()) return true;
    if (jj_scan_token(94)) return true;
    return false;
  }

  static final private boolean jj_3_217() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_316() {
    if (jj_scan_token(typekeyword)) return true;
    if (jj_3R_341()) return true;
    return false;
  }

  static final private boolean jj_3_83() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_335() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_246()) jj_scanpos = xsp;
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static final private boolean jj_3_34() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_148() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_33() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_82() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_375() {
    if (jj_3R_337()) return true;
    return false;
  }

  static final private boolean jj_3_32() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_245() {
    if (jj_3R_335()) return true;
    return false;
  }

  static final private boolean jj_3_31() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_341() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_245()) {
    jj_scanpos = xsp;
    if (jj_3R_375()) return true;
    }
    return false;
  }

  static final private boolean jj_3_30() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_29() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_81() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_28() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_146() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_145() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_147() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_349() {
    if (jj_3R_347()) return true;
    return false;
  }

  static final private boolean jj_3R_348() {
    if (jj_3R_377()) return true;
    return false;
  }

  static final private boolean jj_3R_327() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_348()) {
    jj_scanpos = xsp;
    if (jj_3R_349()) return true;
    }
    return false;
  }

  static final private boolean jj_3R_344() {
    if (jj_scan_token(typedefkeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3_144() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_143() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_399() {
    if (jj_3R_337()) return true;
    return false;
  }

  static final private boolean jj_3_142() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_216() {
    if (jj_3R_327()) return true;
    return false;
  }

  static final private boolean jj_3R_394() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_216()) {
    jj_scanpos = xsp;
    if (jj_3R_399()) return true;
    }
    return false;
  }

  static final private boolean jj_3_141() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_215() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_312() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(85)) {
    jj_scanpos = xsp;
    if (jj_scan_token(76)) return true;
    }
    return false;
  }

  static final private boolean jj_3_214() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_27() {
    if (jj_3R_312()) return true;
    return false;
  }

  static final private boolean jj_3_213() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_140() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_212() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_26() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_211() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_382() {
    if (jj_scan_token(choicekeyword)) return true;
    if (jj_3R_342()) return true;
    return false;
  }

  static final private boolean jj_3_210() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_25() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_209() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_24() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_326() {
    if (jj_3R_347()) return true;
    return false;
  }

  static final private boolean jj_3_23() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3R_385() {
    if (jj_scan_token(augmentkeyword)) return true;
    if (jj_3R_394()) return true;
    return false;
  }

  static final private boolean jj_3_22() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_21() {
    if (jj_3R_311()) return true;
    return false;
  }

  static final private boolean jj_3_139() {
    if (jj_3R_326()) return true;
    return false;
  }

  static final private boolean jj_3R_342() {
    if (jj_3R_337()) return true;
    return false;
  }

  static final private boolean jj_3_20() {
    if (jj_3R_311()) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  static public yangTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  static public Token token, jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static public boolean lookingAhead = false;
  static private boolean jj_semLA;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[215];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static private int[] jj_la1_3;
  static {
      jj_la1_0();
      jj_la1_1();
      jj_la1_2();
      jj_la1_3();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x0,0x0,0x0,0x48000000,0x0,0x12280000,0x400000,0x0,0x48000000,0x0,0x12280000,0x0,0x400000,0x48000000,0x0,0x12280000,0x12280000,0x10280000,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0x40000000,0x0,0x40100000,0x40100000,0x0,0x0,0x0,0xfff80000,0x0,0xfffb0180,0x0,0x60000000,0x60000000,0x80800000,0x0,0x40000000,0x40000000,0x0,0x40000000,0x40000000,0x0,0x40000000,0x40000000,0x0,0xfff80000,0x0,0x40000000,0x40000000,0x0,0xfff80000,0x0,0x40000000,0x40000000,0x0,0xfff80000,0x0,0xfffb0180,0xfff80000,0x0,0xfffb0180,0x0,0xfff80000,0x0,0xfffb0180,0x0,0xfff80000,0x0,0x0,0xfff80000,0x0,0xfff80000,0x0,0x0,0x40000000,0x40000000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0xfff80000,0x0,0x80,0xfff80000,0x0,0x52280000,0x0,0x52280000,0x0,0x56280000,0x0,0x56280000,0x0,0x64000000,0x64000000,0x44000000,0x44000000,0x56280000,0x56280000,0xfff80000,0x0,0xfffb0180,0x0,0xfff80000,0x0,0xfffb0180,0x75080000,0x11080000,0x75080000,0x0,0x10080000,0x50280000,0x50280000,0x0,0x44000000,0x44000000,0x0,0x52080000,0x52080000,0x0,0x12080000,0x64000000,0x64000000,0x0,0x44000000,0x44000000,0x0,0x56080000,0x56080000,0x0,0x65000000,0x65000000,0x0,0x52080000,0x52080000,0x0,0x56080000,0x56080000,0x0,0x44000000,0x44000000,0x0,0xfff80000,0xfffb0180,0xfff80000,0x0,0x53280000,0x13280000,0x53280000,0xfffb0180,0xfff80000,0xfff80000,0x0,0x40000000,0x0,0x40000000,0x0,0x12280000,0x0,0x12280000,0x12280000,0x0,0x12280000,0x52280000,0x0,0x52280000,0x0,0xfffb2180,0xfff81000,0x0,0x1000,0xfff81000,0xfff80000,0xfff81000,0x0,0x0,0x1000,0xfff81000,0x0,0xfff80000,0xc00,0xfffb0180,0xfff81000,0x0,0x0,0xfff81000,0x0,0x1000,0xfff80000,0xfff80000,0xfffb0180,0x0,0xfffb0180,0x180,0x20000,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x0,0x2020000,0x30,0x10100000,0x20000000,0x40040b0c,0x0,0x30,0x10100000,0x20000000,0x40040b0c,0x2020000,0x0,0x10100000,0x30,0x40040b0c,0xb00,0xb00,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0x0,0x0,0x90000000,0x90000000,0x0,0x0,0x0,0xffffffff,0x0,0xffffffff,0x0,0x90000000,0x90000000,0x8400000,0x0,0x10000003,0x10000003,0x0,0x10000003,0x10000003,0x0,0x10000003,0x10000003,0x0,0xffffffff,0x0,0x90000000,0x90000000,0x0,0xffffffff,0x0,0x91000000,0x91000000,0x0,0xffffffff,0x0,0xffffffff,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0x0,0xffffffff,0x0,0xffffffff,0x0,0x0,0x10000003,0x10000003,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0x0,0xffffffff,0x0,0x90000b08,0x8,0x90000b08,0x0,0x94010b08,0x8,0x94010b08,0x0,0x90011000,0x90011000,0x90096000,0x90096000,0x90096b88,0x90096b88,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0x90001b00,0xb00,0x90001b00,0x0,0xb00,0x90000b00,0x90000b00,0x0,0x90001000,0x90001000,0x0,0x90000b00,0x90000b00,0x0,0xb00,0x10011000,0x10011000,0x0,0x10016000,0x10016000,0x0,0x10016b00,0x10016b00,0x0,0x10001000,0x10001000,0x0,0x10000b00,0x10000b00,0x0,0x14010b00,0x14010b00,0x0,0x10001000,0x10001000,0x0,0xffffffff,0xffffffff,0xffffffff,0x0,0x90000b00,0xb00,0x90000b00,0xffffffff,0xffffffff,0xffffffff,0x0,0x90200048,0x8,0x90200048,0x0,0xb08,0x8,0xb08,0xb08,0x8,0xb08,0x90000b08,0x8,0x90000b08,0x0,0xffffffff,0xffffffff,0x0,0x0,0xffffffff,0xffffffff,0xffffffff,0x0,0x0,0x0,0xffffffff,0x0,0xffffffff,0x0,0xffffffff,0xffffffff,0x0,0x0,0xffffffff,0x0,0x0,0xffffffff,0xffffffff,0xffffffff,0x0,0xffffffff,0x0,0x0,};
   }
   private static void jj_la1_2() {
      jj_la1_2 = new int[] {0x1,0x100,0x0,0x0,0x0,0x24,0x100,0x0,0x0,0x0,0x24,0x100,0x100,0x0,0x0,0x24,0x20,0x20,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x0,0x28000000,0x0,0x0,0x28000000,0x200,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x201000,0x12,0x12,0x2,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x1ffffff,0x28000000,0x40,0x40,0x28000000,0x1ffffff,0x28000000,0x0,0x0,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x1ffffff,0x28000000,0x1ffffff,0x20c00,0x1ffffff,0x28000000,0x1ffffff,0x201000,0x1ffffff,0x28000000,0x201000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x880000,0x0,0x0,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x1ffffff,0x28000000,0x400000,0x1ffffff,0x28000000,0x24,0x4,0x24,0x28000000,0x24,0x4,0x24,0x28000000,0x12,0x12,0x12,0x12,0x2c,0x2c,0x1ffffff,0x28000000,0x1ffffff,0x1000000,0x1ffffff,0x28000000,0x1ffffff,0x0,0x0,0x0,0x28000000,0x0,0x20,0x20,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x0,0x0,0x28000000,0x1ffffff,0x1ffffff,0x1ffffff,0x28000000,0xa0,0x20,0xa0,0x1ffffff,0x81ffffff,0x1ffffff,0x28000000,0x4,0x4,0x4,0x28000000,0x24,0x4,0x24,0x24,0x4,0x24,0x24,0x4,0x24,0x28000000,0x1ffffff,0x81ffffff,0x80000000,0x0,0x1ffffff,0x1ffffff,0x81ffffff,0x0,0x80000000,0x0,0x1ffffff,0x0,0x1ffffff,0x0,0x1ffffff,0x81ffffff,0x0,0x80000000,0x1ffffff,0x0,0x0,0x1ffffff,0xffffff,0x1ffffff,0x0,0x1ffffff,0x0,0x58000,};
   }
   private static void jj_la1_3() {
      jj_la1_3 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x0,0x1,0x1,0x0,0x1,0x4,0x0,0x1,0x1,0x4,0x1,0x0,0x0,0x0,0x4,0x0,0x0,0x4,0x0,0x0,0x0,0x0,0x20,0x0,0x0,0x0,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[246];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  public yang(java.io.InputStream stream) {
     this(stream, null);
  }
  public yang(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new yangTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 215; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 215; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public yang(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new yangTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 215; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 215; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public yang(yangTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 215; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(yangTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 215; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }

  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  static final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.Vector jj_expentries = new java.util.Vector();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Enumeration e = jj_expentries.elements(); e.hasMoreElements();) {
        int[] oldentry = (int[])(e.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  static public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[102];
    for (int i = 0; i < 102; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 215; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
          if ((jj_la1_3[i] & (1<<j)) != 0) {
            la1tokens[96+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 102; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  static final public void enable_tracing() {
  }

  static final public void disable_tracing() {
  }

  static final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 246; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
            case 5: jj_3_6(); break;
            case 6: jj_3_7(); break;
            case 7: jj_3_8(); break;
            case 8: jj_3_9(); break;
            case 9: jj_3_10(); break;
            case 10: jj_3_11(); break;
            case 11: jj_3_12(); break;
            case 12: jj_3_13(); break;
            case 13: jj_3_14(); break;
            case 14: jj_3_15(); break;
            case 15: jj_3_16(); break;
            case 16: jj_3_17(); break;
            case 17: jj_3_18(); break;
            case 18: jj_3_19(); break;
            case 19: jj_3_20(); break;
            case 20: jj_3_21(); break;
            case 21: jj_3_22(); break;
            case 22: jj_3_23(); break;
            case 23: jj_3_24(); break;
            case 24: jj_3_25(); break;
            case 25: jj_3_26(); break;
            case 26: jj_3_27(); break;
            case 27: jj_3_28(); break;
            case 28: jj_3_29(); break;
            case 29: jj_3_30(); break;
            case 30: jj_3_31(); break;
            case 31: jj_3_32(); break;
            case 32: jj_3_33(); break;
            case 33: jj_3_34(); break;
            case 34: jj_3_35(); break;
            case 35: jj_3_36(); break;
            case 36: jj_3_37(); break;
            case 37: jj_3_38(); break;
            case 38: jj_3_39(); break;
            case 39: jj_3_40(); break;
            case 40: jj_3_41(); break;
            case 41: jj_3_42(); break;
            case 42: jj_3_43(); break;
            case 43: jj_3_44(); break;
            case 44: jj_3_45(); break;
            case 45: jj_3_46(); break;
            case 46: jj_3_47(); break;
            case 47: jj_3_48(); break;
            case 48: jj_3_49(); break;
            case 49: jj_3_50(); break;
            case 50: jj_3_51(); break;
            case 51: jj_3_52(); break;
            case 52: jj_3_53(); break;
            case 53: jj_3_54(); break;
            case 54: jj_3_55(); break;
            case 55: jj_3_56(); break;
            case 56: jj_3_57(); break;
            case 57: jj_3_58(); break;
            case 58: jj_3_59(); break;
            case 59: jj_3_60(); break;
            case 60: jj_3_61(); break;
            case 61: jj_3_62(); break;
            case 62: jj_3_63(); break;
            case 63: jj_3_64(); break;
            case 64: jj_3_65(); break;
            case 65: jj_3_66(); break;
            case 66: jj_3_67(); break;
            case 67: jj_3_68(); break;
            case 68: jj_3_69(); break;
            case 69: jj_3_70(); break;
            case 70: jj_3_71(); break;
            case 71: jj_3_72(); break;
            case 72: jj_3_73(); break;
            case 73: jj_3_74(); break;
            case 74: jj_3_75(); break;
            case 75: jj_3_76(); break;
            case 76: jj_3_77(); break;
            case 77: jj_3_78(); break;
            case 78: jj_3_79(); break;
            case 79: jj_3_80(); break;
            case 80: jj_3_81(); break;
            case 81: jj_3_82(); break;
            case 82: jj_3_83(); break;
            case 83: jj_3_84(); break;
            case 84: jj_3_85(); break;
            case 85: jj_3_86(); break;
            case 86: jj_3_87(); break;
            case 87: jj_3_88(); break;
            case 88: jj_3_89(); break;
            case 89: jj_3_90(); break;
            case 90: jj_3_91(); break;
            case 91: jj_3_92(); break;
            case 92: jj_3_93(); break;
            case 93: jj_3_94(); break;
            case 94: jj_3_95(); break;
            case 95: jj_3_96(); break;
            case 96: jj_3_97(); break;
            case 97: jj_3_98(); break;
            case 98: jj_3_99(); break;
            case 99: jj_3_100(); break;
            case 100: jj_3_101(); break;
            case 101: jj_3_102(); break;
            case 102: jj_3_103(); break;
            case 103: jj_3_104(); break;
            case 104: jj_3_105(); break;
            case 105: jj_3_106(); break;
            case 106: jj_3_107(); break;
            case 107: jj_3_108(); break;
            case 108: jj_3_109(); break;
            case 109: jj_3_110(); break;
            case 110: jj_3_111(); break;
            case 111: jj_3_112(); break;
            case 112: jj_3_113(); break;
            case 113: jj_3_114(); break;
            case 114: jj_3_115(); break;
            case 115: jj_3_116(); break;
            case 116: jj_3_117(); break;
            case 117: jj_3_118(); break;
            case 118: jj_3_119(); break;
            case 119: jj_3_120(); break;
            case 120: jj_3_121(); break;
            case 121: jj_3_122(); break;
            case 122: jj_3_123(); break;
            case 123: jj_3_124(); break;
            case 124: jj_3_125(); break;
            case 125: jj_3_126(); break;
            case 126: jj_3_127(); break;
            case 127: jj_3_128(); break;
            case 128: jj_3_129(); break;
            case 129: jj_3_130(); break;
            case 130: jj_3_131(); break;
            case 131: jj_3_132(); break;
            case 132: jj_3_133(); break;
            case 133: jj_3_134(); break;
            case 134: jj_3_135(); break;
            case 135: jj_3_136(); break;
            case 136: jj_3_137(); break;
            case 137: jj_3_138(); break;
            case 138: jj_3_139(); break;
            case 139: jj_3_140(); break;
            case 140: jj_3_141(); break;
            case 141: jj_3_142(); break;
            case 142: jj_3_143(); break;
            case 143: jj_3_144(); break;
            case 144: jj_3_145(); break;
            case 145: jj_3_146(); break;
            case 146: jj_3_147(); break;
            case 147: jj_3_148(); break;
            case 148: jj_3_149(); break;
            case 149: jj_3_150(); break;
            case 150: jj_3_151(); break;
            case 151: jj_3_152(); break;
            case 152: jj_3_153(); break;
            case 153: jj_3_154(); break;
            case 154: jj_3_155(); break;
            case 155: jj_3_156(); break;
            case 156: jj_3_157(); break;
            case 157: jj_3_158(); break;
            case 158: jj_3_159(); break;
            case 159: jj_3_160(); break;
            case 160: jj_3_161(); break;
            case 161: jj_3_162(); break;
            case 162: jj_3_163(); break;
            case 163: jj_3_164(); break;
            case 164: jj_3_165(); break;
            case 165: jj_3_166(); break;
            case 166: jj_3_167(); break;
            case 167: jj_3_168(); break;
            case 168: jj_3_169(); break;
            case 169: jj_3_170(); break;
            case 170: jj_3_171(); break;
            case 171: jj_3_172(); break;
            case 172: jj_3_173(); break;
            case 173: jj_3_174(); break;
            case 174: jj_3_175(); break;
            case 175: jj_3_176(); break;
            case 176: jj_3_177(); break;
            case 177: jj_3_178(); break;
            case 178: jj_3_179(); break;
            case 179: jj_3_180(); break;
            case 180: jj_3_181(); break;
            case 181: jj_3_182(); break;
            case 182: jj_3_183(); break;
            case 183: jj_3_184(); break;
            case 184: jj_3_185(); break;
            case 185: jj_3_186(); break;
            case 186: jj_3_187(); break;
            case 187: jj_3_188(); break;
            case 188: jj_3_189(); break;
            case 189: jj_3_190(); break;
            case 190: jj_3_191(); break;
            case 191: jj_3_192(); break;
            case 192: jj_3_193(); break;
            case 193: jj_3_194(); break;
            case 194: jj_3_195(); break;
            case 195: jj_3_196(); break;
            case 196: jj_3_197(); break;
            case 197: jj_3_198(); break;
            case 198: jj_3_199(); break;
            case 199: jj_3_200(); break;
            case 200: jj_3_201(); break;
            case 201: jj_3_202(); break;
            case 202: jj_3_203(); break;
            case 203: jj_3_204(); break;
            case 204: jj_3_205(); break;
            case 205: jj_3_206(); break;
            case 206: jj_3_207(); break;
            case 207: jj_3_208(); break;
            case 208: jj_3_209(); break;
            case 209: jj_3_210(); break;
            case 210: jj_3_211(); break;
            case 211: jj_3_212(); break;
            case 212: jj_3_213(); break;
            case 213: jj_3_214(); break;
            case 214: jj_3_215(); break;
            case 215: jj_3_216(); break;
            case 216: jj_3_217(); break;
            case 217: jj_3_218(); break;
            case 218: jj_3_219(); break;
            case 219: jj_3_220(); break;
            case 220: jj_3_221(); break;
            case 221: jj_3_222(); break;
            case 222: jj_3_223(); break;
            case 223: jj_3_224(); break;
            case 224: jj_3_225(); break;
            case 225: jj_3_226(); break;
            case 226: jj_3_227(); break;
            case 227: jj_3_228(); break;
            case 228: jj_3_229(); break;
            case 229: jj_3_230(); break;
            case 230: jj_3_231(); break;
            case 231: jj_3_232(); break;
            case 232: jj_3_233(); break;
            case 233: jj_3_234(); break;
            case 234: jj_3_235(); break;
            case 235: jj_3_236(); break;
            case 236: jj_3_237(); break;
            case 237: jj_3_238(); break;
            case 238: jj_3_239(); break;
            case 239: jj_3_240(); break;
            case 240: jj_3_241(); break;
            case 241: jj_3_242(); break;
            case 242: jj_3_243(); break;
            case 243: jj_3_244(); break;
            case 244: jj_3_245(); break;
            case 245: jj_3_246(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}