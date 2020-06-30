package com.example.hotel.webTest.hotelManager;

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
public class HotelManagerTest {

    private static ChromeDriver browser;
    //记得需要的时候修改
    private static int port_=8000;

    private static String url="http://localhost:";

    private static String name="Ray";

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
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("333@qq.com");
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    @Test
    public void test() throws InterruptedException {
        browser.get(url);
        loginAsUser();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[1]/ul/li[6]/a")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div[1]/div[3]/div[1]/div[3]/div/div/div/div/div/table/tbody/tr/td[7]/span/button[2]")).click();
        Thread.sleep(1000);
        browser.switchTo().activeElement();
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div[1]/button")).click();
        browser.switchTo().activeElement();
        browser.findElement(By.id("addCouponModal_couponType")).click();
        browser.findElement(By.name("3")).click();
        browser.findElement(By.xpath("//*[@id=\"addCouponModal_couponName\"]")).sendKeys("满减test优惠券");
        browser.findElement(By.xpath("//*[@id=\"addCouponModal_description\"]")).sendKeys("这里是为了test前端代码正确性");
        browser.findElement(By.xpath("//*[@id=\"addCouponModal_target_money\"]")).sendKeys("500");
        browser.findElement(By.xpath("//*[@id=\"addCouponModal_discount_money\"]")).sendKeys("100");
        browser.findElement(By.xpath("//*[@id=\"addCoupon\"]/div[2]/div/div[2]/div[3]/div/button[2]")).click();
        browser.switchTo().activeElement();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div[1]/div[3]/div[1]/div[3]/div/div/div/div/div/table/tbody/tr/td[7]/span/button[2]")).click();
        browser.switchTo().activeElement();
        Assert.assertEquals("这里是为了test前端代码正确性",browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr[5]/td[3]")).getText());
    }
}
