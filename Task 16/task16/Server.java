package task16;

import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		ServerMonitor server=new ServerMonitor();
		server.start();
		System.out.println("To stop server type \"quit\"");
		String s="";
		while(s.compareToIgnoreCase("quit")!=0){
			s=sc.nextLine();
			if(s.compareToIgnoreCase("quit")!=0)
				System.out.println("WAT?");
		}
		System.out.println("Server stopped");
		sc.close();	
	}

}
