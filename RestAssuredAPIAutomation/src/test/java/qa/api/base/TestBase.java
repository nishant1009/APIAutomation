package qa.api.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import qa.api.utilities.ReadPropertyFile;

public class TestBase {
	public Properties prop;
	public int RESPONSE_STATUS_CODE_200=200;
	public int RESPONSE_STATUS_CODE_201=201;
	
	
	public TestBase(){
		try{
		FileInputStream file=new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\Config.properties"));
		prop=new Properties();
		prop.load(file);
		}catch(Exception e)
		{
			System.out.println(e);
		}
				

}
		
	

}
