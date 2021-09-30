package co.edu.escuelaing.sparkweb;

import static spark.Spark.*;

import co.edu.escuelaing.sparkweb.controller.LoadBalancerController;
import co.edu.escuelaing.sparkweb.service.Client;

public class SparkWebServer {
    
    public static void main(String... args){
    	port(getPort());
    	staticFiles.location("/public"); // load static files
		
		new LoadBalancerController(new Client());
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}