package task16;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMonitor extends Thread{
	ServerSocket srv;
	Socket socket;
	OutputStream socketOut;
	InputStream socketInp;
    DataOutputStream dos;
    DataInputStream dis;
    Socket p1=null,p2=null;
    int n=0;
	public ServerMonitor() {
		super();
		try {
			this.setDaemon(true);
			srv=new ServerSocket(12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public void run(){
    	System.out.println("Server monitor started");
    	while(true){
    		try {				
    			socket=srv.accept();
    			System.out.println("Accpted");
				if(p1==null){
					p1=socket;
					System.out.println("One found");
				}
				else{
					p2=socket;
					System.out.println("Partner found");
				}
				if(p2!=null){
					RoomDaemon rd=new RoomDaemon(n);
					rd.start();
					DataOutputStream out1=new DataOutputStream(p1.getOutputStream());
					out1.writeInt(n);
					out1=new DataOutputStream(p2.getOutputStream());
					out1.writeInt(n);
					out1.close();
					n++;					
					p1=null;
					p2=null;
					System.out.println("Room created");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
