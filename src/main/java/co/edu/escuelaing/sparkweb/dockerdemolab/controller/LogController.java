package co.edu.escuelaing.sparkweb.dockerdemolab.controller;

import static spark.Spark.get;

import co.edu.escuelaing.sparkweb.dockerdemolab.persistence.LogServiceMongoDB;
import co.edu.escuelaing.sparkweb.dockerdemolab.service.LogService;

public class LogController {
	
	public LogController(final LogService logService){
		get("hello", (req,res) -> "Hello Docker part II!");
		get("create", (req,res) -> logService.createString(req, res, new LogServiceMongoDB()));
	}

}
