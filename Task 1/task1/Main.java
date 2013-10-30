package task1;

import java.util.regex.*;

public class Main {

	public static boolean isValidNumber(String num) {
		Pattern ptr = Pattern
				.compile("^\\s*\\+\\s*\\d{1,3}\\s*\\(\\d{2,8}\\)\\s*\\d{1,6}-\\d{1,6}-\\d{1,6}$");
		Matcher match = ptr.matcher(num);
		return match.matches();
	}

	/**
	 * @author Yuriy Gerasimov
	 * @param args
	 */
	public static void main(String[] args) {
		String s = ("     +   732  (84333333)   51225-62220-59222");
		System.out.println(isValidNumber(s));
		if (rebuidNum(s) != null)
			System.out.println(rebuidNum(s));		
	}

	public static String rebuidNum(String num) {
		if (isValidNumber(num)) {
			Pattern ptr = Pattern.compile("\\s|\\(|\\)|-");
			Matcher match = ptr.matcher(num);
			return match.replaceAll("");
		}
		return null;
	}

}
