package task6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1414624472843117447L;
	JPanel bottom = new JPanel(new BorderLayout());
	JButton button0 = new JButton("Login");
	JTextArea display = new JTextArea(1, 20);
	JTextField login = new JTextField();
	JPasswordField pass = new JPasswordField();

	public LoginForm() {
		super("Text Editor");
		setBounds(0, 0, 550, 300);
		bottom.setBounds(0, 0, 200, 200);
		// this.setResizable(false);
		display.append("Please, enter your login(2-10 characters, letters and numbers only)."
				+ "Please, enter your password(3-40 characters)."
				+ "\nAnd Press login button.\n");
		button0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pattern logptr = Pattern.compile("[a-zA-Z0-9]{2,10}");
				Matcher logmatch = logptr.matcher(login.getText());
				if (!logmatch.matches()) {
					display.append("Invalid login\n");
					display.append("Please, enter your login(2-10 characters, letters and numbers only, case sensitive).\n"
							+ "And Press login button.\n");
				} else {
					if (!NewUser.isNewUserExists(login.getText())) {
						char[] password = pass.getPassword().clone();
						display.append(String
								.format("No user with name %s exists.\nNew user will be created.\n",
										login.getText()));
						if ((password.length < 3) || (password.length > 40)) {
							display.append("Invalid password format\n");
							System.out
									.println("Please, enter password for new user(3-40 characters).\n");
						} else {
							display.append(NewUser.createNewUser(
									login.getText(), password));
						}
					} else {
						char[] password = pass.getPassword();
						NewUser user = NewUser.loadUser(login.getText());
						if (!user.checkPasswd(password)) {
							display.append(String
									.format("Invalid password for existing user \"%s\"\n",
											login.getText()));
							display.append(String
									.format("Please, enter password for user \"%s\".\n",
											login.getText()));
						} else {
							display.append("Password correct. Access granted.\n");
							display.append(String.format(
									"User's \"%s\" last login date:%s\n",
									user.getLogin(), user.getLastlogindate()));
							user.updateLastlogindate();
						}
					}
				}
			}
		});
		add(display, BorderLayout.CENTER);
		add(button0, BorderLayout.EAST);
		bottom.add(login, BorderLayout.NORTH);
		bottom.add(pass, BorderLayout.SOUTH);
		add(bottom, BorderLayout.SOUTH);
		display.setEditable(false);
		setVisible(true);
	}
}
