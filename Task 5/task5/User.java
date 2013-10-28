package task5;

/**
 * @author Yuriy Gerasimov
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;

@SuppressWarnings("serial")
public class User implements Serializable {
	// constant, decribes path for storaging clss files
	public static final String storagefolder = "Tests for task 5";
	/**
	 * Creates new user with login and password.
	 * 
	 * @param login
	 * @param password
	 */
	private static void createUser(String login, String password) {
		User user = new User(login, password);
		saveUser(user);
		System.out.format("User \"%s\" created\n", login);
		System.out.format("User's \"%s\" last login date:%s\n",
				user.getLogin(), user.getLastlogindate());
	}
	// staic methods, which do the task
	/**
	 * Checks, if user with login name exists.
	 * 
	 * @param name
	 * @return true, if user exists
	 */
	private static boolean isUserExists(String name) {
		File f = new File(storagefolder + "\\" + name);
		return f.exists();
	}

	/**
	 * Loads user from file, if possible.
	 * 
	 * @param name
	 * @return User object if user with login name exists, otherwise null
	 */
	private static User loadUser(String name) {
		try {
			FileInputStream file = new FileInputStream(storagefolder + "\\"
					+ name);
			ObjectInputStream object = new ObjectInputStream(file);
			User usr = (User) object.readObject();
			object.close();
			return usr;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Starts login mechanism
	 */
	public static void login() {
		System.out
				.println("Please, enter your login(2-10 characters, letters and numbers only, case sensitive).");
		Scanner sc = new Scanner(System.in);
		String login = sc.nextLine();
		Pattern logptr = Pattern.compile("[a-zA-Z0-9]{2,10}");
		Matcher logmatch = logptr.matcher(login);
		while (!logmatch.matches()) {
			System.out.println("Invalid login");
			System.out
					.println("Please, enter your login(2-10 characters, letters and numbers only, case sensitive).");
			sc = new Scanner(System.in);
			login = sc.nextLine();
			logmatch = logptr.matcher(login);
		}
		if (!isUserExists(login)) {
			System.out.format("There is no user with login \"%s\".\n", login);
			System.out
					.println("Please, enter password for new user(3-40 characters).");
			String password = sc.nextLine();
			while ((password.length() < 3) || (password.length() > 40)) {
				System.out.println("Invalid password");
				System.out
						.println("Please, enter password for new user(3-40 characters).");
				password = sc.nextLine();
			}
			createUser(login, password);
		} else {
			System.out.format("Please, enter password for user \"%s\".\n",
					login);
			String password = sc.nextLine();
			User user = loadUser(login);
			while (!user.checkPasswd(password)) {
				System.out.println("Invalid password");
				System.out.format("Please, enter password for user \"%s\".\n",
						login);
				password = sc.nextLine();
			}
			System.out.println("Password correct. Access granted.");
			System.out.format("User's \"%s\" last login date:%s\n",
					user.getLogin(), user.getLastlogindate());
			user.updateLastlogindate();
		}
		sc.close();
	}

	/**
	 * Creates file with same name as user login.
	 * 
	 * @param usr
	 */
	private static void saveUser(User usr) {
		try {
			FileOutputStream file = new FileOutputStream(storagefolder + "\\"
					+ usr.getLogin()); // Файловый поток
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(usr);
			output.flush();
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	Date lastlogindate;

	// fields
	private String login, passwd;

	// counstructors
	public User(String login, String passwd) {
		super();
		this.login = login;
		this.passwd = passwd;
		this.lastlogindate = (new Date());
	}

	/**
	 * 
	 * @param pass
	 * @return true, if password correct
	 */
	public boolean checkPasswd(String pass) {
		return (this.passwd.compareTo(pass) == 0);
	}

	/**
	 * 
	 * @return User's last logn date
	 */
	public String getLastlogindate() {
		return lastlogindate.toString();
	}

	// properties
	/**
	 * 
	 * @return User's login
	 */
	public String getLogin() {
		return login;
	}

	// methods
	/**
	 * Updates user's last login date, when user logs in
	 */
	public void updateLastlogindate() {
		this.lastlogindate = (new Date());
		saveUser(this);
		System.out
				.format("User's \"%s\" last login date updated\n", this.login);
	}
}