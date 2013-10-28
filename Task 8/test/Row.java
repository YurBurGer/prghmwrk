/**
 * @author Yuriy Gerasimov
 */
package test;

/**
 * @author Yuriy Gerasimov
 *
 */
public class Row {
	private String name;
	private int year;
	private double rating;
	public Row(String name, int year, double rating) {
		super();
		this.name = name;
		this.year = year;
		this.rating = rating;
	}
	public String getName() {
		return name;
	}
	public int getYear() {
		return year;
	}
	public double getRating() {
		return rating;
	}
	
}
