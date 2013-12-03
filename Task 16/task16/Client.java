package task16;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.Canvas;
import net.miginfocom.swing.MigLayout;

public class Client extends JFrame {

	/**
	 * @author Yuriy Gerasimov
	 */
	private static final long serialVersionUID = 7368689367508945414L;
	private JPanel contentPane;
	private JTextField txtIp;
	private boolean isTurn=true;
	private static final String IPPTRN = 
	        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private Socket Server;
	/**
	 * @author Yuriy Gerasimov;
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(341, 37, 91, 23);
		contentPane.add(btnNewButton);	
		
		txtIp = new JTextField();
		txtIp.setText("IP");
		txtIp.setBounds(341, 71, 86, 20);
		contentPane.add(txtIp);
		
		
		final JPanel panel = new JPanel();
		panel.setBounds(31, 71, 196, 144);
		contentPane.add(panel);
		panel.setLayout(new MigLayout("", "0[100px,grow,fill]0[100px,grow,fill]0[100px,grow,fill]0", "0[100px,grow,fill]0[100px,grow,fill]0[100px,grow,fill]0"));
		
		
		final xopanel[][] x=new xopanel[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				x[i][j]=new xopanel();
				panel.add(x[i][j],String.format("cell %d %d", i,j));
			}
		}
		
					
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addr=txtIp.getText();
				Pattern ptr=Pattern.compile(IPPTRN);
				Matcher mptr=ptr.matcher(addr);
				try {
					if(mptr.matches()){
						Socket socket=new Socket(txtIp.getText(),12345);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while(!in.ready());//animation will be good here
						String port="12345"+in.readLine();
						socket.close();						
						Server=new Socket(addr,Integer.parseInt(port));
						
					}
				}
				catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isTurn){
					int wid=panel.getWidth()/3,hei=panel.getHeight()/3;
					int col=((int)Math.round(e.getPoint().getX()))/wid;
					int row=((int)Math.round(e.getPoint().getY()))/hei;
					x[col][row].setType(1);
					x[col][row].repaint();
					isTurn=false;
					
				}
			}
		});
	}
}
class xopanel extends JPanel{
	/**
	 * @author Yuriy Gerasimov
	 */
	private static final long serialVersionUID = 8564123241711571657L;
	int type=-1;
	boolean isPaintable=true;
	public void setType(int type) {
		this.type = type;
	}
	public xopanel() {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));		
	}
	public void paintComponent(Graphics g){
		if(isPaintable){
			super.paintComponent(g);
			switch(type){
			case 0:
				g.setColor(Color.RED);
				g.drawLine(0, 0, this.getWidth(), this.getHeight());
				g.drawLine(this.getWidth(), 0, 0, this.getHeight());
				isPaintable=false;
				break;
			case 1:
				g.setColor(Color.BLUE);
				g.drawOval(2, 2, this.getWidth()-5, this.getHeight()-5);
				isPaintable=false;
				break;
			default:
				break;
			}
			
		}
	}	
}