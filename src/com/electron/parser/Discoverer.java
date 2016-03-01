package com.electron.parser;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Discoverer {
	static String url;
	static String client;
	
	static HashMap<Integer, String> URL = new HashMap<Integer, String>();
	static HashMap<Integer, String> title = new HashMap<Integer, String>();
	static HashMap<Integer, String> price = new HashMap<Integer, String>();
	
	static HashMap<Integer, String> FiltURL = new HashMap<Integer, String>();
	static HashMap<Integer, String> Filttitle = new HashMap<Integer, String>();
	static HashMap<Integer, String> Filtprice = new HashMap<Integer, String>();
	
	static Integer resultNumber = 0;

	public Discoverer() {
		url = "http://windsor.craigslist.ca/search/vga";
		client = "Mozilla";
	}

	public Discoverer(String search) {
		url = ("http://windsor.craigslist.ca/search/sss?query=" + search);
		System.out.println(url);
		this.client = "Mozilla";
	}

	public Discoverer(String url, String client) {
		this.url = url;
		this.client = client;
	}


	public Document connectURL() throws IOException{
		System.out.println(url);
		return Jsoup.connect(url).userAgent(client).get();
	}
	
	
/**
 * 	
 * @param links
 * Elements should be set to an HTML element.
 * Ex. doc.select("a[href]") for links. 
 */
	
	public void setSelect(Elements links){
		for (Element link : links) {
			resultNumber++;
			URL.put(resultNumber, link.attr("abs:href"));
			if(link.text().contains("$")){
				price.put(resultNumber+1, link.text());
			}else{
				title.put(resultNumber, link.text());
			}
			
		}
		int x = 0;
		for (int i = 0; i<resultNumber; i++) {
			if(URL.get(i) != null && title.get(i) != null && price.get(i) != null){
				x++;
				FiltURL.put(x, URL.get(i));
				Filttitle.put(x, title.get(i));
				Filtprice.put(x, price.get(i));
			}
		}
		
	}
	/**
	 * Prints out discovered results.
	 */
	public void getResultsMessage(){
		for (int i = 0; i<resultNumber; i++) {
			if(URL.get(i) != null && title.get(i) != null && price.get(i) != null){
				System.out.println(URL.get(i) + " " + title.get(i) + " " + price.get(i));
			}
		}
	}
	
	public String getJsonURLString(){
		Gson g = new GsonBuilder().create();	
		return g.toJson(FiltURL);
	}
	
	public String getJsonTitleString(){
		Gson g = new GsonBuilder().create();	
		return g.toJson(Filttitle);
	}
	
	public String getJsonPriceString(){
		Gson g = new GsonBuilder().create();	
		return g.toJson(Filtprice);
	}
/**
 * 
 * @param msg
 * @param args
 * Formats message with String.format
 */
	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}
/**
 * 
 * @param s
 * @param width
 * @return
 * 
 * Formatting for the String
 */
	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}
