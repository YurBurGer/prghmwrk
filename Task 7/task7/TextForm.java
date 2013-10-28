package task7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TextForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8691984230401143726L;
	JButton button0 = new JButton("Save");
	JButton button1 = new JButton("Load");
	JTextArea display = new JTextArea(1, 20);
	JTextField path = new JTextField();

	public TextForm() {
		super("Text Editor");
		setBounds(0, 0, 300, 300);
		button0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					File f1 = new File(path.getText());
					f1.delete();
					RandomAccessFile f = new RandomAccessFile(path.getText(),
							"rw");
					f.writeUTF(display.getText());
					f.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					RandomAccessFile f = new RandomAccessFile(path.getText(),
							"rw");
					String s = f.readUTF();
					display.setText(s);
					f.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		add(display, BorderLayout.CENTER);
		add(button0, BorderLayout.EAST);
		add(button1, BorderLayout.WEST);
		add(path, BorderLayout.SOUTH);
		setVisible(true);
	}
}
