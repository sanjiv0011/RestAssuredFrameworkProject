package api.endpoint;

import static io.restassured.RestAssured.*;
import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.response.Response;


// this used to perform Create, Read Update, Delete request for the USER APIs.
public class UserEndPoints_From_PropertiesFiles {

	
	// to load the properties files data 
	static ResourceBundle getURL()
	{
		ResourceBundle routes = ResourceBundle.getBundle("Routes");
		return routes; // it return the ResourceBundle
	}
	

	
	public static Response createUser(User payload)
	{
		// to get fetch the url from the routes.properties files
		String post_url = getURL().getString("post_url");
		
	Response response = given()
			.contentType("application/json")
			.accept("application/json")
			.body(payload)
			
		.when()
			.post(post_url);
		
		return response;
		
	}
	
	public static Response readUser(String userName)
	{
		
		// to get fetch the url from the routes.properties files
		String get_url = getURL().getString("get_url");
				
		Response response = given()
			.pathParam("username", userName)
			
		.when()
			.get(get_url);
		
		return response;
		
	}
	
	public static Response updateUser(String userName, User payload)
	{
		
		// to get fetch the url from the routes.properties files
		String update_url = getURL().getString("update_url");
		
	Response response = given()
			.contentType("application/json")
			.accept("application/json")
			.pathParam("username",userName)
			.body(payload)
			
			
		.when()
			.put(update_url);
		
		return response;
		
	}
	
	public static Response deleteUser(String userName)
	{
		
		// to get fetch the url from the routes.properties files
		String delete_url = getURL().getString("delete_url");
				
		Response response = given()
			.pathParam("username", userName)
			
		.when()
			.delete(delete_url);
		
		return response;
		
	}
}
