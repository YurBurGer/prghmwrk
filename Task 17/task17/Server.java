package task17;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket srv=new ServerSocket(12345);
		Scanner sc=new Scanner(System.in);
		ServerDaemon server=new ServerDaemon(srv);
		server.start();
		while(!sc.nextLine().equals("q"));			
		srv.close();
		sc.close();
		System.out.println("done");
	}

}
