package task16;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RoomDaemon extends Thread{
	ServerSocket srv;
	Socket p1=null,p2=null;
	boolean isFinised=false;
	public RoomDaemon(int n) {
		this.setDaemon(true);
		String s="1245"+n;
		try {
			srv=new ServerSocket(Integer.parseInt(s));			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	public void run(){
		try {
			DataInputStream p1i,p2i;
			DataOutputStream p1o,p2o;
			p1=srv.accept();
			System.out.println("p1 connected");
			p2=srv.accept();
			System.out.println("p2 connected");
			p1i=new DataInputStream(p1.getInputStream());
			p2i=new DataInputStream(p2.getInputStream());
			p1o=new DataOutputStream(p1.getOutputStream());
			p2o=new DataOutputStream(p2.getOutputStream());
			boolean p1f=false,p2f=false;
			if(Math.random()<0.5){
				p1o.writeInt(1);
				p2o.writeInt(0);
				p1f=true;
			}			
			else{
				p1o.writeInt(0);
				p2o.writeInt(1);
				p2f=true;
			}
			char[][] field=new char[3][3];
			boolean isFinish=false;
			while(!isFinish){
				while((p1i.available()!=0)||(p2i.available()!=0)){
					if(p1i.available()!=0){
						String[] s=p1i.readUTF().split("@");
						int c=Integer.parseInt(s[0]);
						int r=Integer.parseInt(s[1]);
						if(p1f){
							field[c][r]='x';
							
						}
						else{
							field[c][r]='o';
						}
						p2o.writeUTF(c+"@"+r);
					}
					if(p2i.available()!=0){
						String[] s=p2i.readUTF().split("@");
						int c=Integer.parseInt(s[0]);
						int r=Integer.parseInt(s[1]);
						if(p2f){
							field[c][r]='x';
						}
						else{
							field[c][r]='o';
						}
						p1o.writeUTF(c+"@"+r);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
