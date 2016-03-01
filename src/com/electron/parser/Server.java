package com.electron.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Server implements Runnable {
	
	String searchQuery; 
	
	String jsonURL,jsonTitle,jsonPrice;

	ServerSocket configSocket;
	
	HashMap<Integer, String> url = new HashMap<>();
	HashMap<Integer, String> title = new HashMap<>();
	HashMap<Integer, String> price = new HashMap<>();
	
	Discoverer d;
	
	public Server(){
		searchQuery = "";
		this.url = null;
		this.title = null;
		this.price = null; 
		
		this.jsonURL = null;
		this.jsonTitle = null;
		this.jsonPrice = null;
		
	}

	@Override
	public void run() {
		System.out.println("server runnig...");
		try {
			StringBuilder builder = new StringBuilder(); 							// Used to convert Strings/Arrays/Maps to JSON Text
			configSocket = new ServerSocket(8004);									//port for socket
			System.out.println("Server initialized...");
			while(true){
				Socket client = configSocket.accept();  							//Block entire program until request from client.
				System.out.println("You've accessed the server!");
				
				PrintWriter out = new PrintWriter(client.getOutputStream());		//OUTPUT TO CLIENT
				Scanner sc = new Scanner(client.getInputStream());					//INPUT TO CLIENT
				
				this.searchQuery = sc.nextLine();
				
				System.out.println(this.searchQuery);
				
				//Search Website//
				d = new Discoverer(searchQuery);
				Document x = d.connectURL();
				Elements links = x.select("a[href]");
				d.setSelect(links);
				jsonURL = d.getJsonURLString();
				jsonTitle = d.getJsonTitleString();
				jsonPrice = d.getJsonPriceString();
				builder.append("{" + "\"url\":"+ jsonURL + ","+"\"title\":"+ jsonTitle + ","+"\"price\":"+ jsonPrice + "}");
				System.out.println(builder.toString());
				out.write(write(builder.toString()));
				builder.delete(0, builder.length());
				System.out.println(builder.toString());
				System.out.println("Server responded <33");
				out.close();
				client.close();
				sc.close();
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String getSearch(){
		return this.searchQuery;
	}

	public String write(String data){
		File file = new File("file.json");
		file.delete();
		try {
			FileWriter writter = new FileWriter(file, false);
			writter.write(data);
			System.out.println("data written");
			writter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
}
