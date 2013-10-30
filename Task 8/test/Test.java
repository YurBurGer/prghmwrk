/**
 * @author Yuriy Gerasimov
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yuriy Gerasimov
 *
 */
public class Test {
	public static void fill(){
		String url = "jdbc:sqlserver://YURAPC\\NEW;databaseName=TestDB";
		String username = "Yura";
		String pass = "123";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(url,username,pass);
			Statement statement = null;
			statement = connection.createStatement();
			try {
				statement.executeUpdate("DROP TABLE result");
			} catch (Exception e) {
			}
			statement.executeUpdate("CREATE TABLE result(name NVARCHAR(255) DEFAULT NULL,year NUMERIC(4) DEFAULT NULL,rating DECIMAL(2, 1) DEFAULT NULL)");
	        ResultSet imdb = statement.executeQuery("SELECT * FROM imdb");
	        PreparedStatement sfqrry = connection.prepareStatement("SELECT * FROM kinopoisk where name=? and year=?");
	        PreparedStatement insqrry= connection.prepareStatement("INSERT INTO result(name,year,rating) values(?, ?, ?)");            
            while(imdb.next()){
            	String name=imdb.getNString("name");
            	int year=imdb.getInt("year");
            	double rating=imdb.getDouble("rating");
            	sfqrry.setString(1, name);
            	sfqrry.setInt(2, year);
            	ResultSet sf = sfqrry.executeQuery();
            	while(sf.next()){            		
            		rating+=sf.getDouble("rating");
            	}
            	rating/=2;
            	insqrry.setNString(1, name);
            	insqrry.setInt(2, year);
            	insqrry.setDouble(3, rating);
            	insqrry.executeUpdate();
            }	
            imdb.close();
            ResultSet kp = statement.executeQuery("SELECT * FROM kinopoisk");
            sfqrry = connection.prepareStatement("SELECT * FROM imdb where name=? and year=?");
            while(kp.next()){
            	String name=kp.getNString("name");
            	int year=kp.getInt("year");
            	double rating=kp.getDouble("rating");
            	sfqrry.setString(1, name);
            	sfqrry.setInt(2, year);
            	ResultSet sf = sfqrry.executeQuery();
            	if(!sf.next()){            		
            		rating/=2;
            		insqrry.setNString(1, name);
                	insqrry.setInt(2, year);
                	insqrry.setDouble(3, rating);
                	insqrry.executeUpdate();
            	}            	
            }
            kp.close();
            connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
