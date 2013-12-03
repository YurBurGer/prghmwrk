package task16;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RoomDaemon extends Thread{
	ServerSocket srv;
	Socket p1=null,p2=null;
	boolean isFinised=false;
	public RoomDaemon(int n) {
		this.setDaemon(true);
		String s="12345"+n;
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
			p1=srv.accept();
			System.out.println("p1 connected");
			p2=srv.accept();
			System.out.println("p2 connected");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
