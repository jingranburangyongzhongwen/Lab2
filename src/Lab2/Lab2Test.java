package Lab2;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Lab2Test {
	Lab2Test test;
	WebDriver driver;
	
	public String webTest(String user,WebDriver driver){
		//输入用户名
		WebElement username = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        username.clear();
		username.sendKeys(user);
        
		//获取密码
		String passWd=user.substring(4);
       
		//输入密码
		WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        password.clear();
        password.sendKeys(passWd);
       
        //提交
        driver.findElement(By.xpath("//*[@id=\"submitButton\"]")).click();
        
        //获取对应网址
        WebElement path = driver.findElement(By.xpath("/html/body/div[1]/div[2]/a/p")); 

		return path.getText();
	}

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://psych.liebes.top/st");
		test=new Lab2Test();
	}

	@Test
	public void test() throws IOException{
		Excel_reader data= new Excel_reader(); 
		ArrayList<ArrayList<String>> arr=data.xlsx_reader("input.xlsx",0,1);  //后面的参数代表需要输出哪些列，参数个数可以任意
		for(int i=0;i<arr.size();i++){
			ArrayList<String> row=arr.get(i);
			
			String expected=row.get(1);
			
			//该同学没有填写网址，不进行判断
			if(row.get(1).equals("---"))
				continue;	
			else {
				String result=" ";
				try {
					result = test.webTest(row.get(0), driver);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(expected.charAt(expected.length()-1)=='/') {
					expected=expected.substring(0, expected.length()-1);
				}
				if(result.charAt(result.length()-1)=='/') {
					result=result.substring(0, result.length()-1);
				}
				assertEquals(expected,result);
				driver.navigate().back();
			}
		}
		driver.close();
		System.out.println("全对");
	}

}
