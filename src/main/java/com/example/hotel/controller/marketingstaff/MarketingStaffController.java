package com.example.hotel.controller.marketingstaff;

import com.example.hotel.bl.marketingstaff.MarketingStaffService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.vo.ChargeVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * edited by Hewenbing
 * 2020-06-17
 */
@RestController()
@RequestMapping("/api/marketingStaff")
public class MarketingStaffController {

    @Autowired
    private MarketingStaffService marketingStaffService;

    @PostMapping("/creditCharge")
    public ResponseVO creditCharge(@RequestBody ChargeVO chargeVO){
        return marketingStaffService.creditCharge(chargeVO.getEmail(),chargeVO.getPhoneNumber(),chargeVO.getMoney());
    }
}
