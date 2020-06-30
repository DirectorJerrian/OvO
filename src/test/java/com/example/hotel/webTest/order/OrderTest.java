package com.example.hotel.webTest.order;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderTest {

    private static ChromeDriver browser;
    //记得需要的时候修改
    private static int port_=8000;

    private static String url="http://localhost:";

    private static String name="Hewenbing";

    private static String phoneNumber="12345678901";

    @BeforeClass
    public static void openBrowser(){
        //记得需要的时候修改
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\chromedriver_win32\\chromedriver.exe");
        browser=new ChromeDriver();
        browser.manage().deleteAllCookies();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        url=url+port_;
    }

    @AfterClass
    public static void closeBrowser(){
        browser.quit();
    }

    private void loginAsUser(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("123@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    @Test
    public void addOrderTest() throws InterruptedException {
        browser.get(url);
        loginAsUser();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div[2]/div/div/div/div/div/div[1]/div[1]/img")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div/div[3]/div[3]/div[1]/div[2]/div[2]/div/div/div/div/div/div/table/tbody/tr[1]/td[5]/span/button")).click();
        browser.findElement(By.xpath("//*[@id=\"orderModal_clientName\"]")).sendKeys("何文兵");
        browser.findElement(By.xpath("//*[@id=\"orderModal_phoneNumber\"]")).sendKeys("13271909926");
        browser.findElement(By.xpath("//*[@id=\"orderModal_date\"]/span")).click();
        browser.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[5]/div")).click();
        browser.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[6]/div")).click();
        browser.findElement(By.xpath("//*[@id=\"orderModal_peopleNum\"]")).sendKeys("1");
        browser.findElement(By.xpath("//*[@id=\"orderModal_haveChild\"]/label[2]/span[2]")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/form/div[7]/div[2]")).click();
        browser.findElement(By.cssSelector("ul.ant-select-dropdown-menu :nth-child(1)")).click();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]/div/button[2]")).click();
        Thread.sleep(3000);
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[1]/div/div/ul/li[4]")).click();
        Thread.sleep(3000);
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div[1]/div[1]/div/div/div/div/div[1]/div[2]")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div[1]/div[3]/div[2]/div[2]/div/div/ul/li[4]")).click();
        String text = browser.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div[3]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr[4]/td[2]/span/button/span")).getText();
        Assert.assertEquals("北京唐拉雅秀酒店", text);
    }
}
