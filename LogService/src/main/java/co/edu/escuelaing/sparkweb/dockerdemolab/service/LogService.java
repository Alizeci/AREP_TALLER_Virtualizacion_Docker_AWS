package co.edu.escuelaing.sparkweb.dockerdemolab.service;

import co.edu.escuelaing.sparkweb.dockerdemolab.persistence.LogServiceMongoDB;
import spark.Request;
import spark.Response;

public class LogService {

	/**
	 * Method that insert strings on database
	 * @param string value
	 * @return message
	 */
	public String createString(Request req, Response res) {
		String ls_string = req.queryParams("string");
		String ls_result = "Operación no realizada, string ingresado es nulo!";
		
		if (ls_string!=null) {
			LogServiceMongoDB l_lmb= new LogServiceMongoDB();
			ls_result = l_lmb.createString(ls_string);
			if (ls_result!=null) {
				return ls_result;
			}
		}
		return ls_result;
	}
	
	/**
	 * Method that get all Strings on database
	 * @return list of all Strings
	 */
	public String getAllStrings(){
		LogServiceMongoDB l_lmb= new LogServiceMongoDB();
		String ls_result = l_lmb.getAllStrings();
		if (ls_result!=null) {
			return ls_result;
		}
		return null;
	}

	/**
	 * Method that extract recent strings from db
	 * @return list of 10 recent strings
	 */
	public String getRecentLog() {
		LogServiceMongoDB l_lmb= new LogServiceMongoDB();
		String ls_result = l_lmb.extract10RecentStrings();
		if (ls_result!=null) {
			return ls_result;
		}
		return null;
	}

}
