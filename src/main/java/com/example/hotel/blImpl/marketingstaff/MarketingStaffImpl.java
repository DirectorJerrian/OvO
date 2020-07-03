package com.example.hotel.blImpl.marketingstaff;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.marketingstaff.MarketingStaffService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.voucher.VoucherMapper;
import com.example.hotel.po.User;
import com.example.hotel.po.Voucher;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketingStaffImpl implements MarketingStaffService {
    private final static String CHARGE_ERROR="充值失败";

    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    VoucherMapper voucherMapper;

    //信用充值
    public ResponseVO creditCharge(String email,String phoneNumber,int money){
        User user = accountService.getUserInfo(email);
        if (!user.getPhoneNumber().equals(phoneNumber)){
            return ResponseVO.buildFailure("手机号不符，充值失败");
        }
        try {
            //增加的信用值是充值的10倍
            accountService.createNewCreditRecord(user.getId(), -1, "信用充值", money * 10);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(CHARGE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
