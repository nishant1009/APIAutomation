package qa.api.tests;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import qa.api.base.TestBase;
import qa.api.client.RestClient;
import qa.api.data.Users;
import qa.api.utilities.GetValuesFromResponseJSON;

public class PutAPITest extends TestBase {
	
	TestBase testBase;
	String url;
	String serviceUrl;
	String uri;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	GetValuesFromResponseJSON getValuesFromResponseJSON;
	ObjectMapper mapper;
	Users users;
	
	@BeforeMethod
	public void setUp(){
		testBase= new TestBase();
		url=prop.getProperty("URL");
		serviceUrl=prop.getProperty("serviceURL");
		uri=url+serviceUrl;
		restClient= new RestClient();
		getValuesFromResponseJSON= new GetValuesFromResponseJSON();
		mapper=new ObjectMapper();
		users=new Users("morpheus","teacher");
		
	}
	@Test(priority=1)
	public void putAPIStatusCheck() throws Exception{
		// Object to JSON in String
		String userJSONString=mapper.writeValueAsString(users);
		System.out.println(userJSONString);
		closeableHttpResponse=restClient.put(uri, userJSONString);
				
		//Status code
		int statusCode=closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_200);
		
	}
	@Test(priority=2)
	public void putAPIJSONResponseCheck() throws Exception, IOException{
		String responseString=EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson=new JSONObject(responseString);
		System.out.println("Response String is --->"+responseString);
		System.out.println("Response Json is--->"+responseJson);
		
		//3.unmarshalling(JSON to Java Object)
		Users userRespObj=mapper.readValue(responseString, Users.class);
		
		//4. Assert values from Json Response
		Assert.assertTrue(users.getName().equals(userRespObj.getName()));
		Assert.assertTrue(users.getJob().equals(userRespObj.getJob()));
		
		
	}

}
