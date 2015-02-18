package main.common;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewTest {
	@Test(dataProvider="data")
	public void printMethod(String s){
	  System.out.println(s);
	 }
	 
	@DataProvider(name="data")
	public Object[][] dataProviderTest(){
	return new Object[][]{{"Test Data 1"},{"Test Data 2"},{"Test Data 3"}};
	}
}
