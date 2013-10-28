package task8;
import test.*;
public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		Test.fill();
		Row r=Result.Select(1);
		System.out.println(r.getName());
	}

}
