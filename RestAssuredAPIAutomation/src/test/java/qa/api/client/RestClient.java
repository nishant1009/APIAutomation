package qa.api.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	
	public CloseableHttpResponse get(String url) throws Exception{
		
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpGet);
		return closeableHttpResponse;
		
	}
	
	public CloseableHttpResponse get (String url, HashMap<String, String> headerMap) throws Exception, IOException{
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet= new HttpGet(url);
		
		for(Map.Entry<String, String> entry:headerMap.entrySet()){
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpGet);
		return closeableHttpResponse;
		
		}
	//Post Method:
	public CloseableHttpResponse post(String url, String entityString) throws Exception, IOException{
		CloseableHttpClient httpClient= HttpClients.createDefault();
		
		HttpPost httpPost= new HttpPost(url);
		
		httpPost.setEntity(new StringEntity(entityString));//for Payload
		// for Headers:
		//for(Map.Entry<String, String> entry:headerMap.entrySet()){
			//httpPost.addHeader(entry.getKey(), entry.getValue());
		//}
		httpPost.setHeader("Content-Type", "application/json");
				
		CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpPost);
		return closeableHttpResponse; 
	}
	// Put Method
	public CloseableHttpResponse put(String url, String entityString) throws Exception, IOException{
		CloseableHttpClient httpClient= HttpClients.createDefault();
		
		HttpPut httpPut= new HttpPut(url);
		
		httpPut.setEntity(new StringEntity(entityString));//for Payload
		// for Headers:
		//for(Map.Entry<String, String> entry:headerMap.entrySet()){
			//httpPost.addHeader(entry.getKey(), entry.getValue());
		//}
		httpPut.setHeader("Content-Type", "application/json");
				
		CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpPut);
		return closeableHttpResponse; 
		
	}
}
	
