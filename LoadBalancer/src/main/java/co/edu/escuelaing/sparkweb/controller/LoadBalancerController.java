package co.edu.escuelaing.sparkweb.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import co.edu.escuelaing.sparkweb.controller.service.Client;

public class LoadBalancerController {

	public LoadBalancerController(final Client client) {
		get("hello", (req, res) -> "Hello Docker from loadBalancer, Again!");
		post("addlog", (req, res) -> client.addStrings(req));
		get("recentlog", (req, res) -> client.getRecentLog());
		get("/", (req, res) -> {
			res.redirect("index.html");
			return null;
		});
	}
}