package api.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import api.endpoint.UserEndPoints;
import api.payload.User;
import api.utilities.DataProvides;
import io.restassured.response.Response;

public class DataDrivenTest {

	public Logger logger;
	
	@Test(priority =1, dataProvider = "Data", dataProviderClass = DataProvides.class)
	public void testPostUser(String uid, String username, String fname, String lname, String email,String pwd, String phnumber )
	{
		logger = LogManager.getLogger(this.getClass());
		logger.info("*Users creation started*");
		
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(uid));
		userPayload.setUsername(username);
		userPayload.setFirstname(fname);
		userPayload.setLastname(lname);
		userPayload.setEmail(email);
		userPayload.setPassword(pwd);
		userPayload.setPhone(phnumber);
		
		Response response = UserEndPoints.createUser(userPayload);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	
		logger.info("*Users Created*");
	}
	
	@Test(priority =2, dataProvider = "UserName", dataProviderClass = DataProvides.class )
	public void testDeleteUserByName(String userName)
	{
		logger.info("*Users start deleting by name*");
		
		Response response = UserEndPoints.deleteUser(userName);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	
		logger.info("*Users Deleted*");
	}
}
