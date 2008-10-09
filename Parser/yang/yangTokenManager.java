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

public class yangTokenManager implements yangConstants
{
  public static  java.io.PrintStream debugStream = System.out;
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0, long active1)
{
   switch (pos)
   {
      case 0:
         if ((active1 & 0x10000L) != 0L)
            return 5;
         if ((active0 & 0x400L) != 0L)
            return 51;
         if ((active1 & 0x2000000000L) != 0L)
            return 52;
         if ((active0 & 0xfffffffffff80000L) != 0L || (active1 & 0xeeffffL) != 0L)
         {
            jjmatchedKind = 88;
            return 16;
         }
         if ((active0 & 0x800L) != 0L)
            return 53;
         if ((active1 & 0x80000000L) != 0L)
            return 18;
         return -1;
      case 1:
         if ((active0 & 0xfffffffffff80000L) != 0L || (active1 & 0xeeffffL) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 1;
            return 16;
         }
         return -1;
      case 2:
         if ((active0 & 0xbfff9f7fff780000L) != 0L || (active1 & 0xea1fffL) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 88;
               jjmatchedPos = 2;
            }
            return 16;
         }
         if ((active0 & 0x4000608000800000L) != 0L || (active1 & 0x4e000L) != 0L)
            return 16;
         return -1;
      case 3:
         if ((active0 & 0x410b0081000000L) != 0L || (active1 & 0xa000a6L) != 0L)
            return 16;
         if ((active0 & 0xbfbef47f7e780000L) != 0L || (active1 & 0x4a1f59L) != 0L)
         {
            if (jjmatchedPos != 3)
            {
               jjmatchedKind = 88;
               jjmatchedPos = 3;
            }
            return 16;
         }
         return -1;
      case 4:
         if ((active0 & 0x800004000000000L) != 0L || (active1 & 0x1050L) != 0L)
            return 16;
         if ((active0 & 0xb7bef63f7e780000L) != 0L || (active1 & 0x4a0f0dL) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 4;
            return 16;
         }
         return -1;
      case 5:
         if ((active0 & 0x359e722f78700000L) != 0L || (active1 & 0x420f05L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 5;
            return 16;
         }
         if ((active0 & 0x8220841006080000L) != 0L || (active1 & 0x80008L) != 0L)
            return 16;
         return -1;
      case 6:
         if ((active0 & 0x80002028200000L) != 0L || (active1 & 0x404L) != 0L)
            return 16;
         if ((active0 & 0x351e720f50500000L) != 0L || (active1 & 0x420b01L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 6;
            return 16;
         }
         return -1;
      case 7:
         if ((active0 & 0x2500000800100000L) != 0L || (active1 & 0x20000L) != 0L)
            return 16;
         if ((active0 & 0x101e720750400000L) != 0L || (active1 & 0x400b01L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 7;
            return 16;
         }
         return -1;
      case 8:
         if ((active0 & 0x1c600340400000L) != 0L || (active1 & 0xb00L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 8;
            return 16;
         }
         if ((active0 & 0x1002120410000000L) != 0L || (active1 & 0x400001L) != 0L)
            return 16;
         return -1;
      case 9:
         if ((active0 & 0x8000000400000L) != 0L || (active1 & 0x800L) != 0L)
            return 16;
         if ((active0 & 0x14600340000000L) != 0L || (active1 & 0x300L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 9;
            return 16;
         }
         return -1;
      case 10:
         if ((active0 & 0x40000000L) != 0L || (active1 & 0x200L) != 0L)
            return 16;
         if ((active0 & 0x14600300000000L) != 0L || (active1 & 0x100L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 10;
            return 16;
         }
         return -1;
      case 11:
         if ((active0 & 0x14600000000000L) != 0L || (active1 & 0x100L) != 0L)
            return 16;
         if ((active0 & 0x300000000L) != 0L)
         {
            jjmatchedKind = 88;
            jjmatchedPos = 11;
            return 16;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0, long active1)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
}
static private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 34:
         return jjStartNfaWithStates_0(0, 10, 51);
      case 36:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x100000L);
      case 39:
         return jjStartNfaWithStates_0(0, 11, 53);
      case 43:
         return jjStartNfaWithStates_0(0, 101, 52);
      case 45:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x10000L);
      case 46:
         jjmatchedKind = 96;
         return jjMoveStringLiteralDfa1_0(0x1000L, 0x0L);
      case 47:
         return jjStartNfaWithStates_0(0, 95, 18);
      case 58:
         return jjStopAtPos(0, 94);
      case 59:
         return jjStopAtPos(0, 93);
      case 61:
         return jjStopAtPos(0, 100);
      case 73:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x40000L);
      case 78:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x8000L);
      case 91:
         return jjStopAtPos(0, 98);
      case 93:
         return jjStopAtPos(0, 99);
      case 97:
         return jjMoveStringLiteralDfa1_0(0x380000L, 0x0L);
      case 98:
         return jjMoveStringLiteralDfa1_0(0xc00000L, 0x0L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x1f000000L, 0x400L);
      case 100:
         return jjMoveStringLiteralDfa1_0(0x60000000L, 0x800L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x780000000L, 0x0L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x1000L);
      case 103:
         return jjMoveStringLiteralDfa1_0(0x800000000L, 0x0L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x7000000000L, 0x0L);
      case 107:
         return jjMoveStringLiteralDfa1_0(0x8000000000L, 0x0L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0xf0000000000L, 0x0L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x1f00000000000L, 0x6000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x6000000000000L, 0x0L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x38000000000000L, 0x20000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x7c0000000000000L, 0x0L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x7800000000000000L, 0x0L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x8000000000000000L, 0x80001L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x200006L);
      case 117:
         return jjMoveStringLiteralDfa1_0(0x0L, 0xc00038L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x40L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x80L);
      case 121:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x300L);
      case 123:
         return jjStopAtPos(0, 91);
      case 124:
         return jjStopAtPos(0, 97);
      case 125:
         return jjStopAtPos(0, 92);
      default :
         return jjMoveNfa_0(3, 0);
   }
}
static private final int jjMoveStringLiteralDfa1_0(long active0, long active1)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0, active1);
      return 1;
   }
   switch(curChar)
   {
      case 46:
         if ((active0 & 0x1000L) != 0L)
            return jjStopAtPos(1, 12);
         break;
      case 73:
         return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x10000L);
      case 78:
         return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x40000L);
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x8c2300001000000L, active1, 0xd140L);
      case 98:
         return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x20000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x3000078060400000L, active1, 0x800L);
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000L, active1, 0x80L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x480000800000L, active1, 0x2200L);
      case 109:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000000L, active1, 0L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x6080080000L, active1, 0x400018L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x10480001c000000L, active1, 0L);
      case 112:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000000000000000L, active1, 0L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x618000b00100000L, active1, 0x200000L);
      case 115:
         return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x800020L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x8000000000000000L, active1, 0x100000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x21000000200000L, active1, 0x401L);
      case 120:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000L, active1, 0L);
      case 121:
         return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x80006L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0, active1);
}
static private final int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(0, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0, active1);
      return 2;
   }
   switch(curChar)
   {
      case 70:
         if ((active1 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(2, 82, 16);
         break;
      case 78:
         if ((active1 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(2, 79, 16);
         return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x10000L);
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000030000000000L, active1, 0L);
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x400001L);
      case 99:
         if ((active0 & 0x4000000000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 62, 16);
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000000L, active1, 0L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x8800000000000L, active1, 0L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x600000000000000L, active1, 0x8000a0L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000020000000L, active1, 0L);
      case 103:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000300000L, active1, 0L);
      case 104:
         return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x100000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x18L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L, active1, 0x1040L);
      case 109:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000000000L, active1, 0L);
      case 110:
         if ((active1 & 0x2000L) != 0L)
         {
            jjmatchedKind = 77;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x80054001c000000L, active1, 0x300L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x802000000L, active1, 0L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x5000000000L, active1, 0x806L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x300000000L, active1, 0x400L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x101080041000000L, active1, 0xa0000L);
      case 116:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(2, 23, 16);
         return jjMoveStringLiteralDfa3_0(active0, 0xe4000400000000L, active1, 0L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000000L, active1, 0x200000L);
      case 118:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000000000000L, active1, 0L);
      case 120:
         if ((active1 & 0x4000L) != 0L)
         {
            jjmatchedKind = 78;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x200000000000L, active1, 0L);
      case 121:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 39, 16);
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L, active1, 0L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0, active1);
}
static private final int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(1, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0, active1);
      return 3;
   }
   switch(curChar)
   {
      case 45:
         return jjMoveStringLiteralDfa4_0(active0, 0x600000000000L, active1, 0x200L);
      case 70:
         if ((active1 & 0x10000L) != 0L)
            return jjStopAtPos(3, 80);
         break;
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000020000000L, active1, 0L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x40000000L, active1, 0L);
      case 100:
         return jjMoveStringLiteralDfa4_0(active0, 0x100000000000L, active1, 0L);
      case 101:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(3, 24, 16);
         else if ((active1 & 0x2L) != 0L)
         {
            jjmatchedKind = 65;
            jjmatchedPos = 3;
         }
         else if ((active1 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(3, 85, 16);
         return jjMoveStringLiteralDfa4_0(active0, 0x100a000400000000L, active1, 0x4L);
      case 102:
         if ((active0 & 0x10000000000L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x200020004000000L, active1, 0L);
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x800040000000000L, active1, 0x100L);
      case 104:
         if ((active0 & 0x40000000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 54, 16);
         break;
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x2104000002000000L, active1, 0x100000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000000L, active1, 0L);
      case 109:
         if ((active0 & 0x80000000L) != 0L)
            return jjStartNfaWithStates_0(3, 31, 16);
         return jjMoveStringLiteralDfa4_0(active0, 0x200000L, active1, 0x1L);
      case 110:
         if ((active1 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(3, 71, 16);
         break;
      case 111:
         return jjMoveStringLiteralDfa4_0(active0, 0x1300400000L, active1, 0x420000L);
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000000000L, active1, 0L);
      case 113:
         return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0x8L);
      case 114:
         if ((active1 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(3, 87, 16);
         return jjMoveStringLiteralDfa4_0(active0, 0L, active1, 0xc00L);
      case 115:
         if ((active1 & 0x20L) != 0L)
            return jjStartNfaWithStates_0(3, 69, 16);
         return jjMoveStringLiteralDfa4_0(active0, 0x400000000000000L, active1, 0x1000L);
      case 116:
         if ((active0 & 0x80000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 43, 16);
         else if ((active0 & 0x1000000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 48, 16);
         return jjMoveStringLiteralDfa4_0(active0, 0x8080000018000000L, active1, 0x80010L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x804800100000L, active1, 0x40L);
      case 120:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000L, active1, 0L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0, active1);
}
static private final int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(2, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0, active1);
      return 4;
   }
   switch(curChar)
   {
      case 45:
         return jjMoveStringLiteralDfa5_0(active0, 0x20000000000L, active1, 0x100L);
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x100018000000L, active1, 0L);
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L, active1, 0L);
      case 100:
         return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x4L);
      case 101:
         if ((active0 & 0x800000000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 59, 16);
         else if ((active1 & 0x40L) != 0L)
            return jjStartNfaWithStates_0(4, 70, 16);
         else if ((active1 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(4, 76, 16);
         return jjMoveStringLiteralDfa5_0(active0, 0x480600000200000L, active1, 0x80e00L);
      case 102:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000000000000L, active1, 0L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000004000000L, active1, 0L);
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000000000L, active1, 0x20000L);
      case 109:
         return jjMoveStringLiteralDfa5_0(active0, 0x180000L, active1, 0L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000400400000L, active1, 0L);
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x1L);
      case 112:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000000L, active1, 0L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x1008001340000000L, active1, 0L);
      case 115:
         if ((active1 & 0x10L) != 0L)
            return jjStartNfaWithStates_0(4, 68, 16);
         else if ((active1 & 0x100000L) != 0L)
            return jjStopAtPos(4, 84);
         return jjMoveStringLiteralDfa5_0(active0, 0x2002000000000000L, active1, 0L);
      case 116:
         if ((active0 & 0x4000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 38, 16);
         return jjMoveStringLiteralDfa5_0(active0, 0x100040000000000L, active1, 0L);
      case 117:
         return jjMoveStringLiteralDfa5_0(active0, 0x8020002020000000L, active1, 0x400008L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0, active1);
}
static private final int jjMoveStringLiteralDfa5_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(3, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0, active1);
      return 5;
   }
   switch(curChar)
   {
      case 45:
         return jjMoveStringLiteralDfa6_0(active0, 0x300000000L, active1, 0L);
      case 99:
         return jjMoveStringLiteralDfa6_0(active0, 0x8000000L, active1, 0x800L);
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000000L, active1, 0x1L);
      case 101:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(5, 25, 16);
         else if ((active0 & 0x800000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 47, 16);
         else if ((active1 & 0x8L) != 0L)
            return jjStartNfaWithStates_0(5, 67, 16);
         return jjMoveStringLiteralDfa6_0(active0, 0x1008000000100000L, active1, 0x20004L);
      case 103:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(5, 26, 16);
         return jjMoveStringLiteralDfa6_0(active0, 0x400000L, active1, 0L);
      case 104:
         if ((active0 & 0x40000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 42, 16);
         break;
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x2114000850000000L, active1, 0L);
      case 108:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(5, 19, 16);
         return jjMoveStringLiteralDfa6_0(active0, 0x620020000000L, active1, 0x200L);
      case 109:
         if ((active1 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(5, 83, 16);
         break;
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x400000000200000L, active1, 0x400400L);
      case 112:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000000000L, active1, 0L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000000000L, active1, 0L);
      case 115:
         if ((active0 & 0x8000000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 63, 16);
         return jjMoveStringLiteralDfa6_0(active0, 0x400000000L, active1, 0L);
      case 116:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 36, 16);
         else if ((active0 & 0x20000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 53, 16);
         return jjMoveStringLiteralDfa6_0(active0, 0x100000000000L, active1, 0L);
      case 118:
         return jjMoveStringLiteralDfa6_0(active0, 0L, active1, 0x100L);
      case 120:
         if ((active0 & 0x200000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 57, 16);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0, active1);
}
static private final int jjMoveStringLiteralDfa6_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(4, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0, active1);
      return 6;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x2000100000000L, active1, 0x800L);
      case 99:
         return jjMoveStringLiteralDfa7_0(active0, 0x404000000000000L, active1, 0L);
      case 100:
         return jjMoveStringLiteralDfa7_0(active0, 0x8000000000000L, active1, 0x400000L);
      case 101:
         if ((active0 & 0x2000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 37, 16);
         return jjMoveStringLiteralDfa7_0(active0, 0x600000000000L, active1, 0x300L);
      case 102:
         if ((active1 & 0x4L) != 0L)
            return jjStartNfaWithStates_0(6, 66, 16);
         break;
      case 105:
         return jjMoveStringLiteralDfa7_0(active0, 0x20400000000L, active1, 0L);
      case 109:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000000L, active1, 0L);
      case 110:
         if ((active0 & 0x80000000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 55, 16);
         return jjMoveStringLiteralDfa7_0(active0, 0x1000000810100000L, active1, 0L);
      case 111:
         return jjMoveStringLiteralDfa7_0(active0, 0x2100100000000000L, active1, 0L);
      case 112:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000000L, active1, 0L);
      case 115:
         return jjMoveStringLiteralDfa7_0(active0, 0x400000L, active1, 0L);
      case 116:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(6, 21, 16);
         else if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(6, 27, 16);
         else if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(6, 29, 16);
         else if ((active1 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(6, 74, 16);
         return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x20000L);
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x1L);
      case 122:
         return jjMoveStringLiteralDfa7_0(active0, 0x10000000000000L, active1, 0L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0, active1);
}
static private final int jjMoveStringLiteralDfa7_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(5, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0, active1);
      return 7;
   }
   switch(curChar)
   {
      case 45:
         return jjMoveStringLiteralDfa8_0(active0, 0x8000000400000L, active1, 0L);
      case 97:
         return jjMoveStringLiteralDfa8_0(active0, 0x14000000000000L, active1, 0L);
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x1002000000000000L, active1, 0L);
      case 101:
         if ((active0 & 0x400000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 58, 16);
         else if ((active1 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(7, 81, 16);
         return jjMoveStringLiteralDfa8_0(active0, 0x210000000L, active1, 0x400000L);
      case 103:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(7, 35, 16);
         break;
      case 108:
         return jjMoveStringLiteralDfa8_0(active0, 0L, active1, 0x1L);
      case 109:
         return jjMoveStringLiteralDfa8_0(active0, 0x600000000000L, active1, 0x200L);
      case 110:
         if ((active0 & 0x100000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 56, 16);
         else if ((active0 & 0x2000000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 61, 16);
         break;
      case 111:
         return jjMoveStringLiteralDfa8_0(active0, 0x400000000L, active1, 0L);
      case 112:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000000L, active1, 0L);
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000000000L, active1, 0x100L);
      case 115:
         return jjMoveStringLiteralDfa8_0(active0, 0x20000000000L, active1, 0L);
      case 116:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(7, 20, 16);
         return jjMoveStringLiteralDfa8_0(active0, 0x40000000L, active1, 0x800L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0, active1);
}
static private final int jjMoveStringLiteralDfa8_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(6, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0, active1);
      return 8;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa9_0(active0, 0x8000000000000L, active1, 0L);
      case 100:
         if ((active1 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(8, 86, 16);
         break;
      case 101:
         if ((active0 & 0x2000000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 49, 16);
         else if ((active0 & 0x1000000000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 60, 16);
         else if ((active1 & 0x1L) != 0L)
            return jjStartNfaWithStates_0(8, 64, 16);
         return jjMoveStringLiteralDfa9_0(active0, 0x600000000000L, active1, 0xa00L);
      case 105:
         return jjMoveStringLiteralDfa9_0(active0, 0x40000000L, active1, 0L);
      case 110:
         if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(8, 34, 16);
         break;
      case 112:
         return jjMoveStringLiteralDfa9_0(active0, 0x100000000L, active1, 0L);
      case 114:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(8, 28, 16);
         break;
      case 115:
         return jjMoveStringLiteralDfa9_0(active0, 0x200000000L, active1, 0x100L);
      case 116:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 41, 16);
         return jjMoveStringLiteralDfa9_0(active0, 0x14000000400000L, active1, 0L);
      case 121:
         if ((active0 & 0x100000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 44, 16);
         break;
      default :
         break;
   }
   return jjStartNfa_0(7, active0, active1);
}
static private final int jjMoveStringLiteralDfa9_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(7, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0, active1);
      return 9;
   }
   switch(curChar)
   {
      case 45:
         return jjMoveStringLiteralDfa10_0(active0, 0x100000000L, active1, 0L);
      case 100:
         if ((active1 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(9, 75, 16);
         break;
      case 105:
         return jjMoveStringLiteralDfa10_0(active0, 0x14000000000000L, active1, 0x100L);
      case 110:
         return jjMoveStringLiteralDfa10_0(active0, 0x600000000000L, active1, 0x200L);
      case 111:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(9, 22, 16);
         return jjMoveStringLiteralDfa10_0(active0, 0x40000000L, active1, 0L);
      case 115:
         return jjMoveStringLiteralDfa10_0(active0, 0x200000000L, active1, 0L);
      case 121:
         if ((active0 & 0x8000000000000L) != 0L)
            return jjStartNfaWithStates_0(9, 51, 16);
         break;
      default :
         break;
   }
   return jjStartNfa_0(8, active0, active1);
}
static private final int jjMoveStringLiteralDfa10_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(8, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0, active1);
      return 10;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa11_0(active0, 0x200000000L, active1, 0L);
      case 110:
         if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(10, 30, 16);
         break;
      case 111:
         return jjMoveStringLiteralDfa11_0(active0, 0x14000000000000L, active1, 0x100L);
      case 116:
         if ((active1 & 0x200L) != 0L)
            return jjStartNfaWithStates_0(10, 73, 16);
         return jjMoveStringLiteralDfa11_0(active0, 0x600100000000L, active1, 0L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0, active1);
}
static private final int jjMoveStringLiteralDfa11_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(9, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0, active1);
      return 11;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa12_0(active0, 0x100000000L, active1, 0L);
      case 103:
         return jjMoveStringLiteralDfa12_0(active0, 0x200000000L, active1, 0L);
      case 110:
         if ((active0 & 0x4000000000000L) != 0L)
            return jjStartNfaWithStates_0(11, 50, 16);
         else if ((active0 & 0x10000000000000L) != 0L)
            return jjStartNfaWithStates_0(11, 52, 16);
         else if ((active1 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(11, 72, 16);
         break;
      case 115:
         if ((active0 & 0x200000000000L) != 0L)
            return jjStartNfaWithStates_0(11, 45, 16);
         else if ((active0 & 0x400000000000L) != 0L)
            return jjStartNfaWithStates_0(11, 46, 16);
         break;
      default :
         break;
   }
   return jjStartNfa_0(10, active0, active1);
}
static private final int jjMoveStringLiteralDfa12_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(10, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0, 0L);
      return 12;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(12, 33, 16);
         break;
      case 103:
         if ((active0 & 0x100000000L) != 0L)
            return jjStartNfaWithStates_0(12, 32, 16);
         break;
      default :
         break;
   }
   return jjStartNfa_0(11, active0, 0L);
}
static private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
static private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
static private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 51;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 5:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAddStates(0, 2);
                  }
                  else if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(8);
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 8)
                        kind = 8;
                     jjCheckNAdd(4);
                  }
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 6;
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 51:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(10, 11);
                  else if (curChar == 34)
                  {
                     if (kind > 16)
                        kind = 16;
                  }
                  break;
               case 53:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(13, 14);
                  else if (curChar == 39)
                  {
                     if (kind > 16)
                        kind = 16;
                  }
                  break;
               case 52:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                     jjCheckNAddStates(0, 2);
                  }
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 18:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(24, 25);
                  else if (curChar == 47)
                     jjCheckNAddStates(3, 5);
                  break;
               case 3:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 7)
                        kind = 7;
                     jjCheckNAddStates(6, 12);
                  }
                  else if ((0x280000000000L & l) != 0L)
                     jjCheckNAddTwoStates(2, 44);
                  else if (curChar == 47)
                     jjAddStates(13, 14);
                  else if (curChar == 39)
                     jjCheckNAddTwoStates(13, 14);
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(10, 11);
                  if (curChar == 45)
                     jjCheckNAddStates(15, 17);
                  else if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 7)
                     kind = 7;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 2:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 4:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjCheckNAdd(4);
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 8:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 9:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 10:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 11:
                  if (curChar == 34 && kind > 16)
                     kind = 16;
                  break;
               case 12:
                  if (curChar == 39)
                     jjCheckNAddTwoStates(13, 14);
                  break;
               case 13:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(13, 14);
                  break;
               case 14:
                  if (curChar == 39 && kind > 16)
                     kind = 16;
                  break;
               case 16:
                  if ((0x3ff600000000000L & l) == 0L)
                     break;
                  if (kind > 88)
                     kind = 88;
                  jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 17:
                  if (curChar == 47)
                     jjAddStates(13, 14);
                  break;
               case 19:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(3, 5);
                  break;
               case 20:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 21:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 22:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 23:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(24, 25);
                  break;
               case 24:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(24, 25);
                  break;
               case 25:
                  if (curChar == 42)
                     jjAddStates(18, 19);
                  break;
               case 26:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(27, 25);
                  break;
               case 27:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(27, 25);
                  break;
               case 28:
                  if (curChar == 47 && kind > 6)
                     kind = 6;
                  break;
               case 29:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 7)
                     kind = 7;
                  jjCheckNAddStates(6, 12);
                  break;
               case 30:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 7)
                     kind = 7;
                  jjCheckNAdd(30);
                  break;
               case 31:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 32:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 33:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 34:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 35:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 36;
                  break;
               case 36:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 37;
                  break;
               case 37:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 38:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 39;
                  break;
               case 39:
                  if ((0x3ff000000000000L & l) != 0L && kind > 13)
                     kind = 13;
                  break;
               case 40:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 41;
                  break;
               case 41:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 42:
                  if ((0x3ff000000000000L & l) != 0L && kind > 14)
                     kind = 14;
                  break;
               case 43:
                  if ((0x3ff000000000000L & l) != 0L && kind > 15)
                     kind = 15;
                  break;
               case 44:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAddStates(0, 2);
                  break;
               case 45:
                  if (curChar == 46)
                     jjCheckNAdd(46);
                  break;
               case 46:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAddTwoStates(46, 47);
                  break;
               case 48:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(49);
                  break;
               case 49:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 17)
                     kind = 17;
                  jjCheckNAdd(49);
                  break;
               case 50:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAddTwoStates(2, 44);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 51:
               case 10:
                  jjCheckNAddTwoStates(10, 11);
                  break;
               case 53:
               case 13:
                  jjCheckNAddTwoStates(13, 14);
                  break;
               case 3:
               case 16:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 88)
                     kind = 88;
                  jjCheckNAdd(16);
                  break;
               case 0:
                  if (curChar == 120)
                     jjCheckNAdd(1);
                  break;
               case 1:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 7)
                     kind = 7;
                  jjCheckNAdd(1);
                  break;
               case 6:
                  if (curChar == 120)
                     jjCheckNAdd(7);
                  break;
               case 7:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjCheckNAdd(7);
                  break;
               case 19:
                  jjAddStates(3, 5);
                  break;
               case 24:
                  jjCheckNAddTwoStates(24, 25);
                  break;
               case 26:
               case 27:
                  jjCheckNAddTwoStates(27, 25);
                  break;
               case 47:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(20, 21);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 51:
               case 10:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 53:
               case 13:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(13, 14);
                  break;
               case 19:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(3, 5);
                  break;
               case 24:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(24, 25);
                  break;
               case 26:
               case 27:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(27, 25);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 51 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   44, 45, 47, 19, 20, 22, 30, 31, 40, 43, 44, 45, 47, 18, 23, 4, 
   5, 8, 26, 28, 48, 49, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, "\42", "\47", 
"\56\56", null, null, null, null, null, null, "\141\156\171\170\155\154", 
"\141\162\147\165\155\145\156\164", "\141\165\147\155\145\156\164", "\142\145\154\157\156\147\163\55\164\157", 
"\142\151\164", "\143\141\163\145", "\143\150\157\151\143\145", "\143\157\156\146\151\147", 
"\143\157\156\164\141\143\164", "\143\157\156\164\141\151\156\145\162", "\144\145\146\141\165\154\164", 
"\144\145\163\143\162\151\160\164\151\157\156", "\145\156\165\155", "\145\162\162\157\162\55\141\160\160\55\164\141\147", 
"\145\162\162\157\162\55\155\145\163\163\141\147\145", "\145\170\164\145\156\163\151\157\156", "\147\162\157\165\160\151\156\147", 
"\151\155\160\157\162\164", "\151\156\143\154\165\144\145", "\151\156\160\165\164", "\153\145\171", 
"\154\145\141\146", "\154\145\141\146\55\154\151\163\164", "\154\145\156\147\164\150", 
"\154\151\163\164", "\155\141\156\144\141\164\157\162\171", 
"\155\141\170\55\145\154\145\155\145\156\164\163", "\155\151\156\55\145\154\145\155\145\156\164\163", "\155\157\144\165\154\145", 
"\155\165\163\164", "\156\141\155\145\163\160\141\143\145", 
"\156\157\164\151\146\151\143\141\164\151\157\156", "\157\162\144\145\162\145\144\55\142\171", 
"\157\162\147\141\156\151\172\141\164\151\157\156", "\157\165\164\160\165\164", "\160\141\164\150", 
"\160\141\164\164\145\162\156", "\160\157\163\151\164\151\157\156", "\160\162\145\146\151\170", 
"\160\162\145\163\145\156\143\145", "\162\141\156\147\145", "\162\145\146\145\162\145\156\143\145", 
"\162\145\166\151\163\151\157\156", "\162\160\143", "\163\164\141\164\165\163", 
"\163\165\142\155\157\144\165\154\145", "\164\171\160\145", "\164\171\160\145\144\145\146", 
"\165\156\151\161\165\145", "\165\156\151\164\163", "\165\163\145\163", "\166\141\154\165\145", 
"\167\150\145\156", "\171\141\156\147\55\166\145\162\163\151\157\156", 
"\171\151\156\55\145\154\145\155\145\156\164", "\143\165\162\162\145\156\164", "\144\145\160\162\145\143\141\164\145\144", 
"\146\141\154\163\145", "\155\151\156", "\155\141\170", "\116\141\116", "\55\111\116\106", 
"\157\142\163\157\154\145\164\145", "\111\116\106", "\163\171\163\164\145\155", "\44\164\150\151\163", 
"\164\162\165\145", "\165\156\142\157\165\156\144\145\144", "\165\163\145\162", null, null, null, 
"\173", "\175", "\73", "\72", "\57", "\56", "\174", "\133", "\135", "\75", "\53", };
public static final String[] lexStateNames = {
   "DEFAULT", 
};
static final long[] jjtoToken = {
   0xfffffffffffbfd81L, 0x3ff9ffffffL, 
};
static final long[] jjtoSkip = {
   0x7eL, 0x0L, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[51];
static private final int[] jjstateSet = new int[102];
static protected char curChar;
public yangTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}
public yangTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 51; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

public static Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

}
