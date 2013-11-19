package task17;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket socket = null;
		InputStream socketInput=null;
		OutputStream socketOutput=null;
		DataInputStream dis=null;
		DataOutputStream dos=null;
		try {
			Scanner sc=new Scanner(System.in);
			socket = new Socket("localhost", 12345);
			System.out.println("Connection established");
			socketOutput = socket.getOutputStream();
			socketInput = socket.getInputStream();
			dis = new DataInputStream(socketInput);
			dos = new DataOutputStream(socketOutput);
			String s="";
			boolean quit=false;
			while(!quit){
				while((s=sc.nextLine())=="");
				if(s.equals("q")){
					quit=true;
					System.out.println("exit");
				}
				else{
					dos.writeUTF(s);
					System.out.println("sent");
					while(dis.available()==0);
					System.out.println("recieved");
					int c;
					if((c=dis.read())!=(char)0){
						System.out.println("start");
						File f=new File("r"+s);
						f.createNewFile();
						FileOutputStream file = new FileOutputStream(f);
						file.write((char)c);
						while((c=dis.read())!=255)
							file.write((char)c);
						file.close();
						System.out.println("done");
					}
					else{
						System.out.println("Unable to locate");
					}
				}
			}
			sc.close();
			System.out.println("finished");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			dis.close();
			socketInput.close();
			socket.close();
		}
	}

}
