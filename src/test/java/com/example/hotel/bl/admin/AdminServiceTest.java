package com.example.hotel.bl.admin;

import com.example.hotel.bl.marketingstaff.MarketingStaffService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    AccountService accountService;

    @Test
    public void getAllManagersTest(){
        List<User> allManager = adminService.getAllManagers();
    }


    @Test
    public void addManagerTest(){
        UserForm userForm=new UserForm();
        String randomEmail=String.valueOf((int)(Math.random()*9000+1000))+"@qq.com";
        userForm.setEmail(randomEmail);
        userForm.setPassword("123456");
        adminService.addManager(userForm);
        User user=accountService.getUserInfo(randomEmail);
        assertNotNull(user,"user here is required");
        assertEquals(UserType.HotelManager,user.getUserType());
    }

}