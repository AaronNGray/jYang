package applet;

/**
 * Provides methods to perform useful operations on <code>String</code>s.
 *
 */
public class Util {

	/**
	 * Add &lt;br&gt; tags so that the HTML text will wrap correctly.
	 * @param line : the text to wrap.
	 * @param maxChar : the maximum number of character in a single line.
	 * @param startingGap : the gap at the beginning of the first line.
	 */
	public static String wrapHTMLline(String line, int maxChar, int startingGap) {

		int currentChar = maxChar - startingGap;
		while (currentChar < line.length()) {
			if (line.charAt(currentChar) == ' ') {
				line = line.substring(0, currentChar) + "<br>"
						+ line.substring(currentChar + 1);
				currentChar += maxChar + 5;
			} else {
				currentChar++;
			}
		}

		return line;
	}

	/**
	 * Cut a <code>String</code> so that its length will not exceed a maximum value; if a cut
	 * have been made, add "..." to the end.
	 * @param string : the text to cut.
	 * @param maxLength : the maximum number of characters allowed before a cut will be performed.
	 */
	public static String cutString(String string, int maxLength) {
		if (string.length() > maxLength) {
			return string.substring(0, maxLength) + "...";
		} else {
			return string;
		}
	}

	/**
	 * Clean a <code>String</code> from its quotation marks or unexpected surrounding tags or blank spaces.
	 * @param valueString : the <code>String</code> to clean.
	 */
	public static String cleanValueString(String valueString) {
		if (valueString == null) return null;
		String result;
		if (valueString.startsWith("\"") && valueString.endsWith("\"")) {
			result = valueString.substring(1, valueString.length() - 1);
		} else {
			result = valueString;
		}
		
		while (result.startsWith(" ") || result.startsWith("\n") || result.startsWith("\t")){
			result = result.substring(1);
		}
		while (result.endsWith(" ")  || result.endsWith("\n") || result.endsWith("\t")){
			result = result.substring(0,result.length()-1);
		}
		
		return result;
	}
}
