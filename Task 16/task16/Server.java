package task16;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket srv=new ServerSocket(12345);
			Scanner sc=new Scanner(System.in);
			boolean exit=false;
			while(!exit){
				while(!sc.hasNextLine()){
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
