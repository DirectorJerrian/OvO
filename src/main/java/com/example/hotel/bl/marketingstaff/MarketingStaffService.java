package com.example.hotel.bl.marketingstaff;

import com.example.hotel.vo.ResponseVO;

/**
 * Author:Hewenbing
 * Date:2020/6/3
 */
public interface MarketingStaffService {
    /**
     * 信用充值
     * @param
     * @return
     */
    ResponseVO creditCharge(String email,String phoneNumber,int money);



}
