/**
 * @author Yuriy Gerasimov
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yuriy Gerasimov
 *
 */
public class Result {
	public static Row Select(int num) {
		String url = "jdbc:sqlserver://YURAPC\\NEW;databaseName=TestDB";
		String username = "Yura";
		String pass = "123";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(url,username,pass);
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet res = statement.executeQuery("SELECT * FROM result ORDER BY rating DESC");
			if(res.absolute(num))
				return new Row(res.getNString("name"),res.getInt("year"),res.getDouble("rating"));
			else
				return null;			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
