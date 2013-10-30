package task9;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;

import javax.swing.JPasswordField;

import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5651929452860535502L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField pwdPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setTitle("Login form");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		final JTextArea txtrLog = new JTextArea();
		txtrLog.setLineWrap(true);
		txtrLog.setEditable(false);
		txtrLog.setWrapStyleWord(true);
		txtrLog.setText("Log");
		scrollPane.setViewportView(txtrLog);
		txtrLog.append("Please, enter your login(2-10 characters, letters and numbers only)."
				+ "Please, enter your password(3-40 characters)."
				+ "\nAnd Press login button.\n");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		txtLogin = new JTextField();
		txtLogin.setText("Login");
		GridBagConstraints gbc_txtLogin = new GridBagConstraints();
		gbc_txtLogin.insets = new Insets(0, 0, 5, 0);
		gbc_txtLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLogin.gridx = 0;
		gbc_txtLogin.gridy = 0;
		panel.add(txtLogin, gbc_txtLogin);
		txtLogin.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		GridBagConstraints gbc_pwdPassword = new GridBagConstraints();
		gbc_pwdPassword.insets = new Insets(0, 0, 5, 0);
		gbc_pwdPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdPassword.gridx = 0;
		gbc_pwdPassword.gridy = 1;
		panel.add(pwdPassword, gbc_pwdPassword);
		
		final JComboBox<Gender> comboBox = new JComboBox<>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		panel.add(comboBox, gbc_comboBox);
		for(Gender g:Gender.values()){
			comboBox.addItem(g);
		}
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pattern logptr = Pattern.compile("[a-zA-Z0-9]{2,10}");
				Matcher logmatch = logptr.matcher(txtLogin.getText());
				if (!logmatch.matches()) {
					txtrLog.append("Invalid login\n");
					txtrLog.append("Please, enter your login(2-10 characters, letters and numbers only, case sensitive).\n"
							+ "And Press login button.\n");
				} else {
					if (!NewUser.isNewUserExists(txtLogin.getText())) {
						char[] password = pwdPassword.getPassword().clone();
						txtrLog.append(String
								.format("No user with name %s exists.\nNew user will be created.\n",
										txtLogin.getText()));
						if ((password.length < 3) || (password.length > 40)) {
							txtrLog.append("Invalid password format\n");
							System.out
									.println("Please, enter password for new user(3-40 characters).\n");
						} else {
							txtrLog.append(NewUser.createNewUser(
									txtLogin.getText(), password,(Gender)comboBox.getSelectedItem()));
						}
					} else {
						char[] password = pwdPassword.getPassword();
						NewUser user = NewUser.loadUser(txtLogin.getText());
						if (!user.checkPasswd(password)) {
							txtrLog.append(String
									.format("Invalid password for existing user \"%s\"\n",
											txtLogin.getText()));
							txtrLog.append(String
									.format("Please, enter password for user \"%s\".\n",
											txtLogin.getText()));
						} else {
							txtrLog.append("Password correct. Access granted.\n");
							txtrLog.append(String.format(
									"User's \"%s\"(%s) last login date:%s\n",
									user.getLogin(), user.getGen().toString(),user.getLastlogindate()));
							user.updateLastlogindate();
						}
					}
				}			
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 3;
		panel.add(btnLogin, gbc_btnLogin);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtrLog.setText("");
			}
		});
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 4;
		panel.add(btnClear, gbc_btnClear);
	}

}
