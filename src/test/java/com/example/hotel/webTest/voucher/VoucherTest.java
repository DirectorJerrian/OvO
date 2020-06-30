package com.example.hotel.webTest.voucher;

import com.example.hotel.data.voucher.VoucherMapper;
import com.example.hotel.po.Voucher;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VoucherTest {
    private static ChromeDriver browser;
    //记得需要的时候修改
    private static int port_=8000;

    private static String url="http://localhost:";

    private static String email="666@qq.com";

    private static String password="123456";

    @Autowired
    VoucherMapper voucherMapper;


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

    private void loginAsMarketingStaff(){
        browser.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(email);
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
        browser.findElement(By.xpath("//*[@id=\"formLogin\"]/div/div[3]/div[1]/div[4]/div/div/span/button")).click();
    }

    @Test
    public void test() throws InterruptedException{
        browser.get(url);
        loginAsMarketingStaff();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[1]/ul/li[6]/a")).click();
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[3]/div/div[2]/button")).click();
        browser.findElement(By.xpath("//*[@id=\"addVoucherModal_description\"]")).sendKeys("this is a voucher");
        browser.findElement(By.xpath("//*[@id=\"addVoucherModal_target_money\"]")).sendKeys("300");
        browser.findElement(By.xpath("//*[@id=\"addVoucherModal_discount_money\"]")).sendKeys("200");
        browser.findElement(By.xpath("//*[@id=\"addVoucherModal_discount\"]")).sendKeys("1");
        browser.findElement(By.xpath("//*[@id=\"addVoucherModal_number\"]")).sendKeys("1");
        browser.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]/div/button[2]")).click();
        String description=browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[3]/div/div[3]/div/div/div/div/div/table/tbody/tr[3]/td[3]")).getText();
        String target_money=browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[3]/div/div[3]/div/div/div/div/div/table/tbody/tr[3]/td[4]")).getText();
        String discount_money=browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[3]/div/div[3]/div/div/div/div/div/table/tbody/tr[3]/td[5]")).getText();
        assertEquals("this is a voucher",description);
        assertEquals("300",target_money);
        assertEquals("200",discount_money);
        browser.findElement(By.xpath("//*[@id=\"layout\"]/div[2]/div/div[3]/div/div[3]/div/div/div/div/div/table/tbody/tr[3]/td[6]/span/button")).click();
        browser.findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div[2]/button[2]")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        List<Voucher> voucherList=voucherMapper.getUserVoucher(4);
        Voucher voucher=voucherList.get(0);
        assertEquals("this is a voucher",voucher.getDescription());
        assertEquals(300,(int)voucher.getTarget_money());
        assertEquals(200,(int)voucher.getDiscount_money());
    }
}
