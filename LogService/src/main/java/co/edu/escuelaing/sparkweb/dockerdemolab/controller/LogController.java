package co.edu.escuelaing.sparkweb.dockerdemolab.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import co.edu.escuelaing.sparkweb.dockerdemolab.service.LogService;

public class LogController {
	
	public LogController(final LogService logService){
		get("hello", (req,res) -> "Hello Docker from logService, Again!");
		post("create", (req,res) -> logService.createString(req, res));
		get("log", (req,res) -> logService.getAllStrings());
		get("recent", (req,res) -> logService.getRecentLog());		
	}

}
