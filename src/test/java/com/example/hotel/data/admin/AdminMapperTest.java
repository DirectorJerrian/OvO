package com.example.hotel.data.admin;


import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminMapperTest {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AccountMapper accountMapper;

    @Test
    public void addManagerTest(){
        User user=new User();
        String randomEmail=String.valueOf((int)(Math.random()*90000+10000))+"@qq.com";
        user.setEmail(randomEmail);
        user.setPassword("123456");
        user.setUserType(UserType.HotelManager);
        adminMapper.addManager(user);
        User manager=accountMapper.getAccountByName(randomEmail);
        assertNotNull(manager);
    }
}
