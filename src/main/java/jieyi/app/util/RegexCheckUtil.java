package jieyi.app.util;

public class RegexCheckUtil {
	
	public static boolean numLetterCheck(String source){
		
		return source.matches("[A-Za-z0-9]+");
	}
	
	public static boolean numCheck(String source){
		
		return source.matches("[0-9]+");
	}
	
	public static boolean letterCheck(String source){
		
		return source.matches("[A-Za-z]+");
	}
}
