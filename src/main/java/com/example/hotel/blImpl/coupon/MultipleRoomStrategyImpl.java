package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class MultipleRoomStrategyImpl implements CouponMatchStrategy {
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try{
            System.out.println(2+"????");
            if(coupon.getCouponType()==2 && orderVO.getRoomNum()>2){
                System.out.println(2);
                return true;
            }


        }catch (Exception e){
            return false;

        }
        return false;
    }
}
