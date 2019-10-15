package qa.api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertyFile {
	
	public String getPropertyValue(String property)
	{
		FileInputStream file;
		
		try{
			file=new FileInputStream(
					new File(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\Config.properties"));
			Properties prop=new Properties();
			try{
			prop.load(file);
			return prop.getProperty(property);
			}catch(Exception e)
			{
				System.out.println(e);
			}
					
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}

}
