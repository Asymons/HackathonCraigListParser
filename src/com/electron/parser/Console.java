package com.electron.parser;

import java.io.IOException;
import java.net.ServerSocket;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Console {

	static ServerSocket configSocket;
	static String searchQuery;
	static String jsonURL, jsonTitle, jsonPrice;

	public Console() {
		configSocket = null;
		jsonURL = null;
		jsonTitle = null;
		jsonPrice = null;
		searchQuery = null;
	}

	public static void main(String[] args) throws IOException {
		Discoverer d = new Discoverer("xbox");
		Document x = d.connectURL();
		Elements links = x.select("a[href]");
		d.setSelect(links);

		Server server = new Server();

		Thread t = new Thread(server);

		t.start();
		
		//Thread client = new Thread(new Client());
		//client.start();
		
	}
}