package applet;

public class Util {
	
	public static String wrapHTMLline(String line, int maxChar, int startingGap){
		
		int currentChar = maxChar-startingGap;
		while (currentChar<line.length()){
			if (line.charAt(currentChar)==' '){
				line = line.substring(0, currentChar) + "<br>" + line.substring(currentChar+1);
				currentChar += maxChar+5;
			} else {
				currentChar ++;
			}
		}
		
		return line;
	}
	
	public static String cutString(String string, int maxLength){
		if (string.length()>maxLength){
			return string.substring(0,maxLength)+"...";
		} else {
			return string;
		}
	}
}
