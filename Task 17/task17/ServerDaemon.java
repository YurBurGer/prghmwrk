package task17;

import java.io.*;
import java.net.*;

public class ServerDaemon extends Thread{
	Socket socket;
	OutputStream socketOut;
	InputStream socketInp;
    DataOutputStream dos;
    DataInputStream dis;
	public ServerDaemon(ServerSocket srv) {
		super();
		this.setDaemon(true);
		try {
			socket=srv.accept();
			System.out.println("Connection established");
			socketOut=socket.getOutputStream();
			socketInp=socket.getInputStream();
			dos=new DataOutputStream(socketOut);
			dis=new DataInputStream(socketInp);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}  
	public void run(){
		try {
			while(true){
				String path="";
				while(dis.available()==0);
				path=dis.readUTF();
				File f=new File(path);
				if(f.exists()){
					System.out.println("found");
					FileInputStream file=new FileInputStream(f);
					int c;
					while((c=file.read())!=-1){
						dos.write(c);
					}
					dos.write(c);
					file.close();					
				}
				else{
					System.out.println("not found");
					dos.write((char)0);
				}
				System.out.println("done");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			
			try {
				socket.close();
				socketOut.close();
				socketInp.close();
				dis.close();
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
