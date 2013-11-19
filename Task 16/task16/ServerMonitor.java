package task16;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ServerMonitor extends Thread{
	ServerSocket srv;
	Socket socket;
	OutputStream socketOut;
	InputStream socketInp;
    DataOutputStream dos;
    DataInputStream dis;
    LinkedList<InetAddress> q=new LinkedList<>();
	public ServerMonitor(ServerSocket srv) {
		super();
		this.srv=srv;
		this.setDaemon(true);
	}
    public void run(){
    	while(true){
    		try {
				socket=srv.accept();
				socket.getInetAddress();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
