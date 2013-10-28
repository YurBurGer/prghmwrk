package task2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static double convert(String val) {
		Pattern ptr = Pattern.compile("^(\\$|\u00a3)\\d+((,|\\.)\\d{1,2})?$");
		Matcher match = ptr.matcher(val);
		if (match.matches()) {
			char c = val.charAt(0);
			String s1 = val.replaceAll(",", ".");
			String s2 = s1.replaceAll("\\$|\u00a3", "");
			double d = Double.parseDouble(s2);
			double cr;
			switch (c) {
			case '$':
				cr = 32.29;
				break;
			case '\u00a3':
				cr = 43.16;
				break;
			default:
				cr = 1;
			}
			return d * cr;
		}
		// return -1 in order to better debug $0 or \u00a30
		return -1;
	}

	/**
	 * @author Yuriy Gerasimov
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.format("%.2f", convert("$1"));
	}
}
