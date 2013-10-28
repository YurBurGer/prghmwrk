package task10;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

	public static String getMD5(String string) {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String hash = sc.nextLine();
		sc.close();
		NumberThread n = new NumberThread(hash);
		StringThread s = new StringThread(hash);
		StringThreadBack sb = new StringThreadBack(hash);
		HybridThread h = new HybridThread(hash);
		n.start();
		s.start();
		sb.start();
		h.start();
		while (!s.isF()&&!n.isF()&&!h.isF()&&!sb.isF()){
			System.out.print("");
		}
		if (n.isF()) {
			System.out.println(n.getResult());
			s.setF(true);
			sb.setF(true);
			h.setF(true);
		} else if (s.isF()) {
			System.out.println(s.getResult());
			sb.setF(true);
			n.setF(true);
			h.setF(true);
		} else if(sb.isF()){
			System.out.println(sb.getResult());
			n.setF(true);
			s.setF(true);
			h.setF(true);
		} else{
			System.out.println(h.getResult());
			n.setF(true);
			s.setF(true);
			sb.setF(true);
		}
		try {
			n.join();
			s.join();
			sb.join();
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
