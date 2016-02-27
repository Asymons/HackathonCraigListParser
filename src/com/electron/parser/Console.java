package com.electron.parser;

import java.io.IOException;
import java.net.ServerSocket;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Console {
	
	static ServerSocket configSocket;
	static String searchQuery; 
	static String jsonURL, jsonTitle, jsonPrice;
	
	public Console(){
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
//		d.getResultsMessage();
//		
		
		
//		while(true){
			StringBuilder builder = new StringBuilder(); 							// Used to convert Strings/Arrays/Maps to JSON Text
			configSocket = new ServerSocket(3003);	
//			Socket client = configSocket.accept();  							//Block entire program until request from client. 
			
//			PrintWriter out = new PrintWriter(client.getOutputStream());		//OUTPUT TO CLIENT
//			Scanner sc = new Scanner(client.getInputStream());					//INPUT TO CLIENT
			
			
//			searchQuery = sc.nextLine();									//Reading from Client Request
			
			//Search Website//
			
			jsonURL = d.getJsonURLString();
			jsonTitle = d.getJsonTitleString();
			jsonPrice = d.getJsonPriceString();
			
			builder.append("{" + "\"url\":"+ jsonURL + ","+"\"title\":"+ jsonTitle + ","+"\"price\":"+ jsonPrice + "}");
//			System.out.println(builder.toString());
			
			Server server = new Server();
			Client client = new Client();
			
			Thread t = new Thread(server);
			Thread tc = new Thread(client);
			
			t.start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tc.start();
			
			
//		}
		
		
		
		
		
		
		
		
//		
//		HashMap<Integer, String> database = new HashMap<>();
//		HashMap<Integer, String> title = new HashMap<>();
//		HashMap<Integer, String> price = new HashMap<>();
//		int result = 0;
//		
//		Document d = null;
//		try {
//			d = Jsoup.connect("http://windsor.craigslist.ca/search/vga").userAgent("Mozilla").get();
//		} catch (IOException e) {
//			System.out.println("Failed.");
//		}
//		Elements links = d.select("a[href]");
//		
//		
//		
//		for(Element link : links){
//			result++;
//			database.put(result, link.attr("abs:href"));
//			if(link.text().contains("$")){
//				price.put(result+1, link.text());
//			}else{
//				title.put(result, link.text());
//			}
//			
//		}
//		
//		for (int i = 0; i<result; i++) {
//			if(database.get(i) != null && title.get(i) != null && price.get(i) != null){
//				System.out.println(database.get(i) + "|===|" + title.get(i)+ "|===|" + price.get(i));
//			}
//		}
		
		
	}

}
