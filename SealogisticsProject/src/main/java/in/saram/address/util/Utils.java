package in.saram.address.util;

public class Utils {

	public static String StringSTextReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	}
	
	public static boolean StringBlankChk(String str) {
		String match = ".*\\s.*";
		boolean result = false;
		
		result = str.matches(match);
		
		return result;
	}
	
	public static void main (String args[]) {
		System.out.println(Utils.StringBlankChk("안 녕하세 요"));
	}
	
}
