package com.electron.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

	String hostname = "127.0.0.1";
	int portNumber = 8004;
	Socket clientsocket;
	@Override
	public void run() {
		
		System.out.println("did u call me");
		try {
			clientsocket = new Socket(hostname, portNumber);
			PrintWriter out = new PrintWriter(clientsocket.getOutputStream());
			Scanner sc = new Scanner(clientsocket.getInputStream());
			
			out.write("blall");
			System.out.println("I wrote nothing");
			
			while(sc.hasNext()){
				System.out.println(sc.nextLine());
				System.out.println("I AM STUCK");
			}
			
			out.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
