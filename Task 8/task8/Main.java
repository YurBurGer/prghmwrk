package task8;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import test.*;
public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number");
		int num=sc.nextInt();
		sc.close();
		Test.fill();
		Row r=Result.Select(num);
		String result=String.format("N%d: \"%s\", year:%d, average rating:%.2f", num,r.getName(),r.getYear(),r.getRating());
		try {
			byte[] ptext = result.getBytes("UTF-8");
			String out=new String(ptext);
			System.out.println(out);
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}		
	}

}
