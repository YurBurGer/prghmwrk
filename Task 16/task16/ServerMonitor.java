package task16;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
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
    	while(true){
    		try {
				socket=srv.accept();
				if(p1==null)
					p1=socket;
				else
					p2=socket;
				if(p2!=null){
					RoomDaemon rd=new RoomDaemon(n);
					rd.start();
					PrintWriter out1 = new PrintWriter(new OutputStreamWriter(p1.getOutputStream()));
					out1.write(n);
					out1.close();
					out1 = new PrintWriter(new OutputStreamWriter(p2.getOutputStream()));
					out1.write(n);
					out1.close();
					n++;					
					p1=null;
					p2=null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
