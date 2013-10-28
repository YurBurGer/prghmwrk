package task6;

/**
 * @author Yuriy Gerasimov
 */

import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class NewUser implements Serializable {
	// constant, decribes path for storaging clss files
	public static final String storagefolder = "Tests for task 6";
	/**
	 * Creates new user with login and password.
	 * 
	 * @param login
	 * @param password
	 */
	public static String createNewUser(String login, char[] password) {
		NewUser user = new NewUser(login, password);
		saveNewUser(user);
		String s = String.format(
				"User \"%s\" created\nUser's \"%s\" last login date:%s\n",
				login, user.getLogin(), user.getLastlogindate());
		return s;
	}
	// staic methods, which do the task
	/**
	 * Checks, if user with login name exists.
	 * 
	 * @param name
	 * @return true, if user exists
	 */
	public static boolean isNewUserExists(String name) {
		File f = new File(storagefolder + "\\" + name);
		return f.exists();
	}
	/**
	 * Loads user from file, if possible.
	 * 
	 * @param name
	 * @return User object if user with login name exists, otherwise null
	 */
	public static NewUser loadUser(String name) {
		try {
			FileInputStream file = new FileInputStream(storagefolder + "\\"
					+ name);
			ObjectInputStream object = new ObjectInputStream(file);
			NewUser usr = (NewUser) object.readObject();
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
	 * Creates file with same name as user login.
	 * 
	 * @param usr
	 */
	public static void saveNewUser(NewUser usr) {
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
	private String login;

	private char[] passwd;

	// counstructors
	public NewUser(String login, char[] passwd) {
		super();
		this.login = login;
		this.passwd = passwd.clone();
		this.lastlogindate = (new Date());
	}

	/**
	 * 
	 * @param pass
	 * @return true, if password correct
	 */
	public boolean checkPasswd(char[] pass) {
		boolean f = (this.passwd.length == pass.length);
		for (int i = 0; (i < pass.length) && (f); i++) {
			f &= (this.passwd[i] == pass[i]);
		}
		return f;
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
	public String updateLastlogindate() {
		this.lastlogindate = (new Date());
		saveNewUser(this);
		String s = String.format("User's \"%s\" last login date updated\n",
				this.login);
		return s;
	}
}