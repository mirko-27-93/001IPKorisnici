package studiranje.ip.service.test;

import com.google.gson.JsonObject;

/**
 * Испис формираног ЈСОН објекта. 
 * @author mirko
 * @version 1.0
 */
public class JsonMain {
	public static void main(String ... args) {
		JsonObject object = new JsonObject(); 
		object.addProperty("id", "DELETE_MESSAGE");
		System.out.println(object);
	}
}
