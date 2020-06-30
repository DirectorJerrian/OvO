package com.example.hotel.data.voucher;

import com.example.hotel.po.Voucher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class VoucherMapperTest {
    @Autowired
    VoucherMapper voucherMapper;

    @Test
    public void addVoucherTest(){
        Voucher voucher=new Voucher();
        voucher.setStatus("可使用");
        String description=String.valueOf(Math.random()*100000+10000);
        voucher.setDescription(description);
        voucher.setDiscount_money(123);
        voucher.setTarget_money(123);
        voucher.setDiscount(1);
        voucher.setUserId(4);
        voucherMapper.addVoucher(voucher);
        List<Voucher> allVoucher=voucherMapper.getUserVoucher(4);
        Voucher goal=new Voucher();        ;
        for(Voucher i:allVoucher){
            if(i.getDescription().equals(description)) {
                goal=i;
                break;
            }
        }
        assertNotNull(goal.getId(),"goal is needed here");
        assertEquals(123,(int)goal.getDiscount_money());
        assertEquals(123,(int)goal.getTarget_money());
        assertEquals(1,(int)goal.getDiscount());
        assertEquals(4,(int)goal.getUserId());

    }

    @Test
    public void getUserVoucherTest(){
        assertEquals(2,voucherMapper.getUserVoucher(4).size());
    }
}
