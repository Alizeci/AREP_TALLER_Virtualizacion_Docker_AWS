package co.edu.escuelaing.sparkweb.dockerdemolab.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class LogServiceMongoDB {

	public static void main(String[] args) {
		String connectionString = System.getProperty("mongodb://localhost:27017");
		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
			List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
			databases.forEach(db -> System.out.println(db.toJson()));
		}
	}

	public String createString(String string) {
		return string;
	}

}
