package task11;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static TimeThread tth;
	static JLabel lblTitle = new JLabel("Title");
	static JLabel lblTime = new JLabel("Time");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		tth=new TimeThread();
		tth.start();
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
		while(true){
			long min=tth.getSht()/60,sec=tth.getSht()-min*60;
			lblTime.setText(String.format("%d:%d", min,sec));
			if(tth.isWork()){
				lblTitle.setText("Work time!!!");				
			}
			else{
				lblTitle.setText("Relax=)");
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setResizable(false);
		setTitle("Pomodoro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 196);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(10, 11, 394, 33);
		contentPane.add(lblTitle);
		
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTime.setBounds(10, 55, 394, 33);
		contentPane.add(lblTime);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tth.setStop(true);
				tth.setWork(true);
			}
		});
		btnStop.setBounds(10, 133, 174, 23);
		contentPane.add(btnStop);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tth.setStop(false);				
			}
		});
		btnStart.setBounds(10, 99, 174, 23);
		contentPane.add(btnStart);
	}
}
