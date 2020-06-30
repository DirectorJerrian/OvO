package com.example.hotel.controller.voucher;


import com.example.hotel.bl.voucher.VoucherService;
import com.example.hotel.po.Voucher;
import com.example.hotel.vo.AvailableVoucherVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.VoucherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@RestController()
@RequestMapping("/api/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/{userId}/userVoucher")
    public ResponseVO userVoucher(@PathVariable Integer userId){
        if(voucherService.getUserVoucher(userId)==null){
                return ResponseVO.buildFailure("无优惠券");
        }
        return ResponseVO.buildSuccess(voucherService.getUserVoucher(userId));
    }

    @GetMapping("/userAvailableVoucher")
    public ResponseVO userAvailableVoucher( @RequestParam Integer userId,
                                            @RequestParam Integer targetMoney
                                            ){
        AvailableVoucherVO availableVoucherVO=new AvailableVoucherVO();
        availableVoucherVO.setTargetMoney(targetMoney);
        availableVoucherVO.setUserId(userId);
        return ResponseVO.buildSuccess(voucherService.getAvailableVoucher(availableVoucherVO));
    }

    @PostMapping("/{id}/useVoucher")
    public ResponseVO useVoucher(@PathVariable Integer id){
        return ResponseVO.buildSuccess(voucherService.useVoucher(id));
    }

    @GetMapping("/getAllVoucher")
    public ResponseVO getAllVoucher() {
        return ResponseVO.buildSuccess(voucherService.getAllVoucher());
    }

    @PostMapping("/addVoucher")
    public ResponseVO addVoucher(@RequestBody Voucher voucher){
        System.out.println(voucher.getDiscount_money()+"contro");
        voucherService.addVoucher(voucher);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/addVoucherToAll")
    public ResponseVO addVoucherToAll(@RequestBody Voucher voucher){
        System.out.println("here");
        voucherService.addVoucherToAll(voucher);
        return ResponseVO.buildSuccess(true);
    }

}
