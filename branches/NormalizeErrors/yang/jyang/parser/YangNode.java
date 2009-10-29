package jyang.parser;

import java.util.Vector;

public interface YangNode extends Node {
	public void addUnknown(YANG_Unknown u);
	public Vector<YANG_Unknown> getUnknowns();
    /** Return the line of the node **/
    public int getLine();

    /** Return the column of the node **/
    public int getCol();
    
    public String getFileName();
    
    public void setFileName(String f);


}
