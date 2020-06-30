package com.example.hotel.webTest.user;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    private static ChromeDriver browser;
    //记得需要的时候修改
    private static int port_=8000;

    private static String url="http://localhost:";

    private static String name="Jerrian";

    private static String phoneNumber="12345678987";

    @BeforeClass
    public static void openBrowser(){
        //记得需要的时候修改
        System.setProperty("webdriver.chrome.driver","D:\\study\\OvO\\chromedriver.exe");
        browser=new ChromeDriver();
        browser.manage().deleteAllCookies();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        url=url+port_;
    }

    @AfterClass
    public static void closeBrowser(){browser.quit();}

    private void loginAsUser(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("123@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("/html/body/div/div[2]/form/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    @Test
    public void updateVIPSavingsTest() throws InterruptedException{
        browser.get(url);
//        System.out.println(browser.findElement(By.xpath("//*[@id=\"main-message\"]/h1/span")).getText());
        loginAsUser();
        browser.findElement(By.xpath("/html/body/div/div[2]/div[1]/ul/li[4]")).click();
        browser.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div[1]/div/div/div/div/div[1]/div[6]")).click();
        browser.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div[3]/div[6]/button[1]")).sendKeys(Keys.ENTER);
        browser.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div[3]/div[6]/form/div/div[2]/div/span/span/div/input")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[1]/div/input")).sendKeys("2000-01-01");
        browser.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[1]/div/input")).sendKeys(Keys.ENTER);
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/button[3]")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[1]/div/div/div/div/div[1]/div[6]")).click();
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[4]/div/div/span/button")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        Assert.assertEquals("100",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[3]/div[2]/div/span/span")).getText());
        Assert.assertEquals("100",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[2]/div[2]/div/span/span")).getText());
        Assert.assertEquals("1",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[1]/div[2]/div/span/span")).getText());
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[1]/div[2]/div/span/div/label[3]")).click();
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[4]/div/div/span/button")).sendKeys(Keys.ENTER);
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[1]/div[2]/div/span/div/label[5]")).click();
        browser.findElement(By.xpath("//*[@id=\"coordinated_moneyDIY\"]")).sendKeys("200");
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[4]/div/div/span/button")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        Assert.assertEquals("800",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[3]/div[2]/div/span/span")).getText());
        Assert.assertEquals("800",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[2]/div[2]/div/span/span")).getText());
        Assert.assertEquals("2",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[1]/div[2]/div/span/span")).getText());
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[1]/div[2]/div/span/div/label[4]")).click();
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[4]/div/div/span/button")).sendKeys(Keys.ENTER);
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[1]/div[2]/div/span/div/label[2]")).click();
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[2]/div[4]/div/div/span/button")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        Assert.assertEquals("2000",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[3]/div[2]/div/span/span")).getText());
        Assert.assertEquals("2000",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[2]/div[2]/div/span/span")).getText());
        Assert.assertEquals("3",browser.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[3]/div[6]/form[1]/div[1]/div[2]/div/span/span")).getText());
    }
}
