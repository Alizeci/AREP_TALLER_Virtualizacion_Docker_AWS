package co.edu.escuelaing.sparkweb.dockerdemolab.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import co.edu.escuelaing.sparkweb.dockerdemolab.model.Document;

import org.json.JSONObject;

public class LogServiceMongoDB {

	MongoClient l_mc = new MongoClient("localhost", 27017); // connect to mongodb
	Datastore datastore = new Morphia().createDatastore(l_mc, "test"); // select test collection

	/**
	 * Method that insert strings on database
	 * 
	 * @param string value
	 * @return message
	 */
	public String createString(String string) {
		Document l_doc = new Document(string, new Date().toString());
		datastore.save(l_doc);
		return "String: " + string + " added!";
	}

	/**
	 * Method that get all Strings on database
	 * 
	 * @return list of jsonObjects
	 */
	public String getAllStrings() {
		String result = "No hay strings almacenados!";
		List<Object> llo_result = getListOfStrings();

		if (llo_result != null && !llo_result.isEmpty()) {
			result = llo_result.toString();
		}

		return result;
	}

	public List<Object> getListOfStrings() {
		List<Object> llo_result = new ArrayList<>();

		DBCollection ldb_collection = datastore.getCollection(Document.class);
		List<DBObject> lldb_object = ldb_collection.find().toArray(); // Obtener data of collection

		for (Object obj : lldb_object) {
			BasicDBObject lb_obj = (BasicDBObject) obj;
			JSONObject l_json = new JSONObject();
			l_json.put("string", lb_obj.getString("string"));
			l_json.put("date", lb_obj.getString("date"));
			llo_result.add(l_json);
		}
		return llo_result;
	}

	/**
	 * Method that extract recent strings from db
	 * @return list of 10 recent strings
	 */
	public String extract10RecentStrings() {
		List<Object> ls_allStrings = getListOfStrings();
		int firstIndex = ls_allStrings.size() - 10;
		String result = null;

		if (firstIndex <= 0) {
			result = ls_allStrings.toString();
		} else {
			List<Object> subString = ls_allStrings.subList(firstIndex, ls_allStrings.size());
			if (!ls_allStrings.isEmpty() && ls_allStrings != null) {
				result = subString.toString();
			}
		}
		return result;

	}
}
