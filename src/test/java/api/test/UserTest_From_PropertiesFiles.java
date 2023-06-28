package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.javafaker.Faker;

import api.endpoint.UserEndPoints_From_PropertiesFiles;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest_From_PropertiesFiles {

	Faker faker;
	User userPayload;
	public Logger logger;

	
	@BeforeClass
	public void setupData()
	{
		faker = new Faker();
		userPayload = new User();
		
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());;
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		// to apply the logger 
		logger = LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority = 1)
	public void testPostUser()
	{
		logger.info("*User creation started*");
		
		Response response = UserEndPoints_From_PropertiesFiles.createUser(userPayload);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*User created*");
	}
	
	@Test(priority = 2 )
	public void testGetUserByName()
	{
		logger.info("*Fetching users by name*");
		
		Response response = UserEndPoints_From_PropertiesFiles.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.statusCode(), 200);
		
		logger.info("*Users found by name*");
	
	}
	
	@Test(priority = 3)
	public void testUpdateUser()
	{
		logger.info("*Start updating the users*");
		
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());;
		
		Response response = UserEndPoints_From_PropertiesFiles.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		response.then().log().body().statusCode(200); // or
		//Assert.assertEquals(response.getStatusCode(), 200);
	
		//Checking data after update
		Response responseAfterUpdate = UserEndPoints_From_PropertiesFiles.readUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("*Users updated*");
	}
	
	@Test(priority =4)
	public void testDeleteUserByName()
	{
		logger.info("*Start Deleting the users by name*");
		
		Response responseAfterDelte = UserEndPoints_From_PropertiesFiles.deleteUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(responseAfterDelte.getStatusCode(), 200);
		
		logger.info("*Users Deleted*");
	}
}
