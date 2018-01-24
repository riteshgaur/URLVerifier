package com.riteshgaur;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPClient {

	private String failedURLS = "";
	private String succeededURLS = "";
	private String incorrectURLS = "";

	public void validateUrl() throws Exception {
	    Path filePath = Paths.get("src/url-list.txt");
	    List<String> myURLArrayList = Files.readAllLines(filePath);  

	myURLArrayList.forEach((String url) -> {
	        if (verifyUrl(url)) { 
	            try{
	URL myURL = new URL(url);
	HttpURLConnection myConnection = (HttpURLConnection) myURL.openConnection();
	if (myConnection.getResponseCode()==URLStatus.HTTP_OK.getStatusCode()) {
	            succeededURLS = succeededURLS + "\n" + url + "****** Status message is : "
	                + URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
	 } else {
	       failedURLS = failedURLS + "\n" + url + "****** Status message is : "
	           + URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
	 }} catch (Exception e) {
	           System.out.print("For url- " + url+ "" +e.getMessage());
	  }  
	 }else {
	        incorrectURLS += "\n" + url;
	      }
	  });
	}

	private boolean verifyUrl(String url) {
		String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
		Pattern pattern = Pattern.compile(urlRegex);
		Matcher m = pattern.matcher(url);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {

		try {
			HTTPClient myClient = new HTTPClient();
			myClient.validateUrl();
			System.out.println("Valid URLS that have successfully connected :");
			System.out.println(myClient.succeededURLS);
			System.out.println("\n--------------\n\n");
			System.out.println("Broken URLS that did not successfully connect :");
			System.out.println(myClient.failedURLS);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}