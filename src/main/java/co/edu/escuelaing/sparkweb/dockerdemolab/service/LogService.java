package co.edu.escuelaing.sparkweb.dockerdemolab.service;

import co.edu.escuelaing.sparkweb.dockerdemolab.persistence.LogServiceMongoDB;
import spark.Request;
import spark.Response;

public class LogService {

	public String createString(Request req, Response res, final LogServiceMongoDB logServiceMongoDB) {
		String string = req.queryParams("string");
		String result = logServiceMongoDB.createString(string);
		return result;
		
	}

}
