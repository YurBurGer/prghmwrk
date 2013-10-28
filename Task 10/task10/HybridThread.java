package task10;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HybridThread extends Thread {
	private boolean f = false;
	private String pattern;
	private String result;

	public HybridThread(String pattern) {
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
		char i = '0';
		while (i <= 'Z' && !f) {
			char j = '0';
			while (j <= 'Z' && !f) {
				char k = '0';
				while (k <= 'Z' && !f) {
					String string = String.format("%c%c%c", i, j, k);
					if (getMD5(string).compareTo(pattern) == 0) {
						f = true;
						result = string;
					}
					if (k != '9')
						k++;
					else
						k = 'A';
				}
				if (j != '9')
					j++;
				else
					j = 'A';
			}
			if (i != '9')
				i++;
			else
				i = 'A';
		}
	}

	public void setF(boolean f) {
		this.f = f;
	}
}
