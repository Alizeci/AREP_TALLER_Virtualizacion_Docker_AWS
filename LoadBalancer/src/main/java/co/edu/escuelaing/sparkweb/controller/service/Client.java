package co.edu.escuelaing.sparkweb.controller.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import spark.Request;

public class Client {

	private static final String LOGSERVICE_URL = "http://localhost";
	private final String[] ports = { "34000", "34001", "34002" };
	private int server = 0;

	/**
	 * Method to add new strings to log
	 * @param req - request with string
	 * @return - successful message
	 * @throws IOException - If string not added to log
	 */
	public String addStrings(Request req) throws IOException {
		try {
			String string = req.queryParams("string");
			StringBuffer response = new StringBuffer();
			getServerNumber();
			
			URL lu_request = new URL(LOGSERVICE_URL + ":" + ports[server] + "/create?string=" + string);
			HttpURLConnection con = (HttpURLConnection) lu_request.openConnection();
			con.setRequestMethod("POST");
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} else {
				return "String not added to log";
			}
			return String.valueOf(response);
		} catch (MalformedURLException ex) {
			throw new MalformedURLException(ex.getMessage());
		} catch (IOException ex) {
			throw new IOException(ex.getMessage());
		}
	}
	
	/**
	 * Method to get log of strings
	 * @return log of strings
	 * @throws IOException - If not found a log
	 */
	public String getRecentLog() throws IOException {
		try {
			getServerNumber();
			StringBuffer response = new StringBuffer();
			URL lu_request = new URL(LOGSERVICE_URL + ":" + ports[server] + "/recent");
			System.out.println(lu_request.getContent());
			HttpURLConnection con = (HttpURLConnection) lu_request.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} else {
				return "Log not found";
			}
			return String.valueOf(response);
		} catch (MalformedURLException ex) {
			throw new MalformedURLException(ex.getMessage());
		} catch (IOException ex) {
			throw new IOException(ex.getMessage());
		}
	}
	
	/**
	 * Calculate instance of server
	 */
	private void getServerNumber() {
		server = (server + 1) % ports.length;
	}

}
