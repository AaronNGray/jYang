package yangTree.nodes;

/**
 * Represents a node that can present multiple occurrences in a data tree.
 * 
 */
public interface ListedYangNode {

	/**
	 * Returns the minimum number of occurrence of this list.
	 */
	public int getMinElements();

	/**
	 * Returns the maximum number of occurrence of this list.
	 */
	public int getMaxElements();

	/**
	 * Give the way an element should be sorted
	 * when "ordered-by" statement equals "system".
	 * 
	 * @return -1 if this element should be placed after the other element;<br>
	 *         1  if this element should be placed before the other element;<br> 
	 *         0  if the two elements have the same identifier.
	 */
	public int compareTo(ListedYangNode otherListedNode);
	
	/**
	 * Returns <code>true</code> if the two occurrences are considered as equals in the current Yang tree context.<br>
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equalsOccurrence(ListedYangNode otherOccurrence);

}
