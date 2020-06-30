package com.example.hotel.bl.voucher;


import com.example.hotel.po.Voucher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VoucherServiceTest {
    @Autowired
    VoucherService voucherService;

    @Test
    public void addVoucherTest(){
        Voucher voucher=new Voucher();
        voucher.setStatus("可使用");
        String description=String.valueOf(Math.random()*100000+10000);
        voucher.setDescription(description);
        voucher.setDiscount_money(123);
        voucher.setTarget_money(123);
        voucher.setDiscount(1);
        voucherService.addVoucher(voucher);
        List<Voucher> allVoucher=voucherService.getAllVoucher();
        Voucher goal=new Voucher();
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
        voucherService.addVoucherToAll(voucher);
        List<Voucher> voucherList=voucherService.getUserVoucher(4);
        Voucher goalVoucher=new Voucher();
        for(Voucher i:voucherList){
            if(i.getDescription().equals(description)){
                goalVoucher=i;
                break;
            }
        }
        assertNotNull(goalVoucher.getId());
    }

    @Test
    public void getUserVoucherTest(){
        Voucher voucher=voucherService.getUserVoucher(1).get(0);
        assertEquals("九折优惠券",voucher.getDescription());
        assertEquals(0.9,voucher.getDiscount());
        assertEquals(Integer.valueOf(0),voucher.getTarget_money());
        assertEquals(Integer.valueOf(0),voucher.getDiscount_money());
        assertEquals(Integer.valueOf(2),voucher.getNumber());
        assertEquals("可使用",voucher.getStatus());
    }
}
