package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    public void addCouponTest(){
        HotelTargetMoneyCouponVO couponVO=new HotelTargetMoneyCouponVO();
        couponVO.setHotelId(1);
        couponVO.setName("test优惠券");
        couponVO.setDiscountMoney(100);
        couponVO.setTargetMoney(600);
        couponVO.setType(3);
        couponService.addHotelTargetMoneyCoupon(couponVO);
        List<Coupon> couponVOList=couponService.getHotelAllCoupon(1);
        Coupon coupon=new Coupon();
        for(Coupon item: couponVOList){
            if(item.getCouponName().equals("test优惠券")){
                coupon=item;
                break;
            }
        }
        Assert.assertEquals(coupon.getTargetMoney(),600,0.0);
        Assert.assertEquals(100, coupon.getDiscountMoney(), 0.0);
    }

}