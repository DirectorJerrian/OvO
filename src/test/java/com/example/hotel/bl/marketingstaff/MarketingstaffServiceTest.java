package com.example.hotel.bl.marketingstaff;


import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MarketingstaffServiceTest {
    @Autowired
    MarketingStaffService marketingStaffService;
    @Autowired
    AccountService accountService;


    @Test
    public void depositCreditTest(){
        User user=accountService.getUserInfo(4);
        double beforeCredit=user.getCredit();
        marketingStaffService.creditCharge(user.getEmail(),user.getPhoneNumber(),100);
        user=accountService.getUserInfo(4);
        assertEquals(beforeCredit+1000,user.getCredit());
    }


}
