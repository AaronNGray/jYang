package applet;

public class Util {

	/**
	 * Add &lt;br&gt; tags so that the HTML text will wrap correctly.
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
	 * Cut a string so that its length will not exceed a maximum value; if a cut
	 * have been made, add "..." to the end.
	 */
	public static String cutString(String string, int maxLength) {
		if (string.length() > maxLength) {
			return string.substring(0, maxLength) + "...";
		} else {
			return string;
		}
	}

	/**
	 * Clean a string from its quotation marks or unexpected surrounding tags or blank spaces.
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
