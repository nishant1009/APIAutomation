package qa.api.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import qa.api.base.TestBase;
import qa.api.client.RestClient;
import qa.api.data.Users;
import qa.api.utilities.GetValuesFromResponseJSON;

public class PostAPITest extends TestBase {
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
		users=new Users("morpheus","leader");
		
	}
	
	@Test(priority=1)
	public void postAPITestStatusCheck() throws Exception, JsonMappingException, IOException{
		
		//HashMap<String, String> headerMap= new HashMap<String, String>();
		//headerMap.put("Content-Type", "application/json");
		//Users users= new Users("morpheus","leader");
		//object to JSON file
		//mapper.writeValue(new File("/Users/dell/workspace/RestAssuredAPIAutomation/src/test/java/qa/api/data/users.json"), users);
			
		// Object to JSON in String
		String userJSONString=mapper.writeValueAsString(users);
		System.out.println(userJSONString);
		closeableHttpResponse=restClient.post(uri, userJSONString);
		
		//1. Status code
		int statusCode=closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
				
	}
	
	
	@Test(priority=2)
	public void postAPIJSONresponseCheck() throws ParseException, IOException{
		
		String responseString=EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson=new JSONObject(responseString);
		System.out.println("Response String is --->"+responseString);
		System.out.println("Response Json is--->"+responseJson);
		
		//3.unmarshalling(JSON to Java Object)
		Users userRespObj=mapper.readValue(responseString, Users.class);
		
		//4. Assert values from Json Response
		Assert.assertTrue(users.getName().equals(userRespObj.getName()));
		Assert.assertTrue(users.getJob().equals(userRespObj.getJob()));
		
		//String name=getValuesFromResponseJSON.getValueByJPath(responseJson, "/name");
		//Assert.assertEquals(name, "morpheus");
		//System.out.println("Name is--->"+name);
		
	}

}
