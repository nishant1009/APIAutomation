package qa.api.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import qa.api.base.TestBase;
import qa.api.client.RestClient;
import qa.api.utilities.GetValuesFromResponseJSON;
import qa.api.utilities.ReadPropertyFile;

public class GetAPITest extends TestBase{
	
	TestBase testBase;
	String url;
	String serviceUrl;
	String uri;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	GetValuesFromResponseJSON getValuesFromResponseJSON;
	
	@BeforeMethod
	public void setUp(){
		testBase= new TestBase();
		url=prop.getProperty("URL");
		serviceUrl=prop.getProperty("serviceURL");
		uri=url+serviceUrl;
		restClient= new RestClient();
		getValuesFromResponseJSON= new GetValuesFromResponseJSON();
		
	}
	@Test(priority=1)
	public void apiResponseStatusCheck() throws Exception{
		closeableHttpResponse=restClient.get(uri);
		int statusCode=closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is --->"+statusCode);
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_200);
		
	}
	
	@Test(priority=2)
	public void apiResponseListUsersCheck() throws Exception{
		String responseString=EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson=new JSONObject(responseString);
		String jsonValues=getValuesFromResponseJSON.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per_page is--->"+jsonValues);
		int expectedValues= Integer.parseInt(jsonValues);
		JSONArray jsonArray=responseJson.getJSONArray("data");
		int jsonArraySize=jsonArray.length();
		System.out.println("values of actual per page values is--->"+jsonArraySize);
		Assert.assertEquals(jsonArraySize, expectedValues);
		String firstName= getValuesFromResponseJSON.getValueByJPath(responseJson, "data[0]/first_name");
		Assert.assertEquals(firstName, "George");
		
	}
	
	@Test(priority=3)
	public void apiResponseHeadersCheck(){
		Header[] headerArray=closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders= new HashMap<String, String>();
		for(Header header:headerArray){
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Values are --->"+allHeaders);
		Assert.assertEquals(allHeaders.get("Connection"), "keep-alive");
	}
	@Test(priority=4)
	public void apiResponseCheckWithHeaders() throws Exception{
		
		HashMap<String, String> headerMap= new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		closeableHttpResponse=restClient.get(uri,headerMap);
		int statusCode=closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is --->"+statusCode);
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_200);
		
	}

}
