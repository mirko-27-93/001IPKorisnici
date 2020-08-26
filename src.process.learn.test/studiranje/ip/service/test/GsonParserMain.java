package studiranje.ip.service.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Парсирање ЈСОНа ГСОН парсером. 
 * @author mirko
 * @version 1.0
 */
public class GsonParserMain {
	public static void main(String ... args) {
		String json = "{'id':'DELETE_MESSAGE'}"; 
		System.out.println("PARSIRANJE JSON-A : 1"); 
		System.out.println("=====================");
		System.out.println(json);
		System.out.println("=====================");
		JsonParser parser = new JsonParser(); 
		JsonElement element = parser.parse(json); 
		System.out.println(element);
		System.out.println("=====================");
		System.out.println(element.getClass().getName());
		System.out.println("=====================");
		System.out.println(element.getAsJsonObject());
		System.out.println("=====================");
		JsonObject object = element.getAsJsonObject(); 
		System.out.println(object.get("id").getAsString()); 
		System.out.println("=====================");
	}
}
