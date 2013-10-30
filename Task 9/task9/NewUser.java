package task9;

/**
 * @author Yuriy Gerasimov
 */

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@SuppressWarnings("serial")
public class NewUser implements Serializable {
	// constant, decribes path for storaging clss files
	public static final String storagefolder = "Tests for task 9";
	/**
	 * Creates new user with login and password.
	 * 
	 * @param login
	 * @param password
	 */
	public static String createNewUser(String login, char[] password,Gender gen) {
		NewUser user = new NewUser(login, password,gen);
		saveNewUser(user);
		String s = String.format(
				"User \"%s\"(%s) created\nUser's \"%s\"(%s) last login date:%s\n",
				login, gen,user.getLogin(),user.getGen() ,user.getLastlogindate());
		return s;
	}	
	public Gender getGen() {
		return gen;
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

	private String passwd;
	private Gender gen;

	// counstructors
	public NewUser(String login, char[] passwd,Gender gen) {
		super();
		this.login = login;
		this.passwd = getMD5(Arrays.toString(passwd));
		this.lastlogindate = (new Date());
		this.gen=gen;
	}

	/**
	 * 
	 * @param pass
	 * @return true, if password correct
	 */
	public boolean checkPasswd(char[] pass) {
		return (passwd.compareTo(getMD5(Arrays.toString(pass)))==0);
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
		String s = String.format("User's \"%s\"(%s) last login date updated\n",
				this.login,this.gen.toString());
		return s;
	}	
	
	private static String getMD5(String inputString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // DSA, RSA, MD5, SHA-1, SHA-256
            byte[] messageDigest = md.digest(inputString.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}