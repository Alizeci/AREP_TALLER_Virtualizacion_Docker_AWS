package co.edu.escuelaing.sparkweb.dockerdemolab;

import static spark.Spark.*;

import co.edu.escuelaing.sparkweb.dockerdemolab.controller.LogController;
import co.edu.escuelaing.sparkweb.dockerdemolab.service.LogService;

public class SparkWebServer {
    
    public static void main(String... args){
          port(getPort());
          
          new LogController(new LogService());
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4500;
    }
    
}