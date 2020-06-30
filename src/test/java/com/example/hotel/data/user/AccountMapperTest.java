package com.example.hotel.data.user;

import com.example.hotel.data.vip.VipMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.po.Vipcard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountMapperTest {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    VipMapper vipMapper;


    @Test
    public void createNewAccountTest(){
        User user = new User();
        user.setEmail("826947112@qq.com");
        user.setPassword("123456");
        user.setUserType(UserType.Client);
        user.setPhoneNumber("13271909926");
        user.setUserName("WindRanger");
        user.setCredit(100);
        accountMapper.createNewAccount(user);
        assertEquals(accountMapper.getAccountByName("826947112@qq.com").getId(),user.getId());
    }

    @Test
    public void updateUserPasswordTest(){
        User user = accountMapper.getAccountByName("826947112@qq.com");
        accountMapper.updatePassword(user.getId(),"654321");
        assertEquals(accountMapper.getAccountById(user.getId()).getPassword(),"654321");
    }

    @Test
    public void creditTest(){
        int credit=(int)Math.random()*1000+100;
        accountMapper.updateUserCredit(4,credit);
        User user=accountMapper.getAccountById(4);
        assertEquals(credit,user.getCredit());
    }

    @Test
    public void registerVIPTest(){
        Vipcard vipcard=new Vipcard();
        vipcard.setUserId(4);
        vipcard.setCard_type("personal");
        vipcard.setInfo("2000-01-01");
        vipcard.setLevel(0);
        vipcard.setSavings(0);
        vipcard.setVip_credit(0);
        int row=vipMapper.addVipCard(vipcard);
        assertEquals(1,row);
    }

    @Test
    public void updateVIPSavings(){
        assertEquals(1,accountMapper.updateVIPCard(4,6,0,6));
    }
}