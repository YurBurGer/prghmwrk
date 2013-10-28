package task3;

import java.io.File;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		search("C:\\Users\\Þðà\\Documents\\GitHub\\prghmwrk\\Tests for task 3",
				"txt");
	}

	public static void search(String path, String ext) {
		File f = new File(path);
		if (f.exists()) {
			if (f.isDirectory()) {
				for (String p : f.list())
					search(path + File.separatorChar + p, ext);
			} else if (f.isFile()) {
				if (f.getName().endsWith(ext)) {
					System.out.println(f.length() + " " + f.getAbsolutePath());
				}
			}
		} else {
			System.out.println("Not Found");
		}
	}

}
