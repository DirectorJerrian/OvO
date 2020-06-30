package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CouponMapperTest {

    @Autowired
    CouponMapper couponMapper;

    @Test
    void insertCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCouponName("test优惠券");
        coupon.setCouponType(3);
        coupon.setDescription("测试添加优惠策略是否正常");
        Assert.assertEquals(1, couponMapper.insertCoupon(coupon));
    }
}