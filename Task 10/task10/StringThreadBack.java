package task10;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringThreadBack extends Thread {
	private boolean f = false;
	private String pattern;
	private String result;

	public StringThreadBack(String pattern) {
		super();
		this.pattern = pattern;
	}

	private String getMD5(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); // DSA, RSA,
																	// MD5,
																	// SHA-1,
																	// SHA-256
			byte[] messageDigest = md.digest(string.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String getResult() {
		return result;
	}

	public boolean isF() {
		return f;
	}

	public void run() {
		for (char a = 'z'; (a >= 'a') && (!f); a--)
			for (char b = 'z'; (b >= 'a') && (!f); b--)
				for (char c = 'z'; (c >= 'a') && (!f); c--)
					for (char d = 'z'; (d >= 'a') && (!f); d--) {
						String string = String.format("%c%c%c%c", a, b, c, d);
						if (getMD5(string).compareTo(pattern) == 0) {
							f = true;
							result = string;
						}
					}
	}

	public void setF(boolean f) {
		this.f = f;
	}
}
