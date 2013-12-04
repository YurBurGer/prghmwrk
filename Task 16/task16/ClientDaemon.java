package task16;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientDaemon extends Thread{
	DataInputStream dis;
	xopanel[][] x;
	Boolean isT;
	public ClientDaemon(DataInputStream dis, xopanel[][] x ,Boolean isTurn) {
		super();
		this.setDaemon(true);
		this.dis = dis;
		this.x=x;
		this.isT=isTurn;
	}
	public void run(){
		while(true){
			try {
				while(dis.available()==0);
				String[] s=dis.readUTF().split("@");
				int c=Integer.parseInt(s[0]);
				int r=Integer.parseInt(s[1]);
				x[c][r].type=1-x[c][r].type;
				x[c][r].repaint();
				x[c][r].type=1-x[c][r].type;
				isT=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
