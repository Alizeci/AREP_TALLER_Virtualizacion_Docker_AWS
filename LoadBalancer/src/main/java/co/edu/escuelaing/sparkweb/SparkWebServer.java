package co.edu.escuelaing.sparkweb;

import static spark.Spark.*;

public class SparkWebServer {
    
    public static void main(String... args){
    	port(getPort());
        System.out.println( "Hello World!" );
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}