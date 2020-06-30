package com.example.hotel.bl.user;


import com.example.hotel.data.vip.VipMapper;
import com.example.hotel.data.voucher.VoucherMapper;
import com.example.hotel.po.Vipcard;
import com.example.hotel.po.Voucher;
import com.example.hotel.vo.RegisterVIPVO;
import com.example.hotel.vo.VipcardVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    VipMapper vipMapper;
    @Autowired
    VoucherMapper voucherMapper;

    @Test
    public void registerVIPTest(){
        RegisterVIPVO registerVIPVO=new RegisterVIPVO();
        registerVIPVO.setUserId(1);
        registerVIPVO.setType("personal");
        registerVIPVO.setInfo("2000-01-01");
        accountService.registerVIP(registerVIPVO);
        Vipcard target=vipMapper.getVIPInfoByUserId(1);
        assertEquals("personal",target.getCard_type());
        assertEquals("2000-01-01",target.getInfo());
        assertEquals(Integer.valueOf(0),target.getLevel());
        assertEquals(0,target.getSavings());
        assertEquals(0,target.getVip_credit());
        registerVIPVO=new RegisterVIPVO();
        registerVIPVO.setUserId(2);
        registerVIPVO.setType("business");
        registerVIPVO.setInfo("写文档时间有限公司");
        accountService.registerVIP(registerVIPVO);
        target=vipMapper.getVIPInfoByUserId(2);
        assertEquals("business",target.getCard_type());
        assertEquals("写文档时间有限公司",target.getInfo());
        assertEquals(Integer.valueOf(0),target.getLevel());
        assertEquals(0,target.getSavings());
        assertEquals(0,target.getVip_credit());
    }

    @Test
    public void updateVIPSavingsTest(){
        VipcardVO vipcardVO=new VipcardVO();
        vipcardVO.setUserId(1);
        vipcardVO.setSavings(0);
        vipcardVO.setVip_credit(0);
        accountService.updateVIPSavings(1,vipcardVO);
        Vipcard target=vipMapper.getVIPInfoByUserId(1);
        assertEquals(Integer.valueOf(0),target.getLevel());
        assertEquals(0,target.getSavings());
        assertEquals(0,target.getVip_credit());
        assertEquals(0,voucherMapper.getUserVoucher(1).size());

        vipcardVO.setSavings(100);
        vipcardVO.setVip_credit(100);
        accountService.updateVIPSavings(1,vipcardVO);
        target=vipMapper.getVIPInfoByUserId(1);
        assertEquals(Integer.valueOf(1),target.getLevel());
        assertEquals(100,target.getSavings());
        assertEquals(100,target.getVip_credit());
        assertEquals(0,voucherMapper.getUserVoucher(1).size());

        vipcardVO.setSavings(800);
        vipcardVO.setVip_credit(800);
        accountService.updateVIPSavings(1,vipcardVO);
        target=vipMapper.getVIPInfoByUserId(1);
        assertEquals(Integer.valueOf(2),target.getLevel());
        assertEquals(800,target.getSavings());
        assertEquals(800,target.getVip_credit());
        assertEquals(1,voucherMapper.getUserVoucher(1).size());

        vipcardVO.setSavings(2000);
        vipcardVO.setVip_credit(2000);
        accountService.updateVIPSavings(1,vipcardVO);
        target=vipMapper.getVIPInfoByUserId(1);
        assertEquals(Integer.valueOf(3),target.getLevel());
        assertEquals(2000,target.getSavings());
        assertEquals(2000,target.getVip_credit());
        assertEquals(1,voucherMapper.getUserVoucher(1).size());
    }
}
