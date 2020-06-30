package com.example.hotel.bl.voucher;

import com.example.hotel.po.Voucher;
import com.example.hotel.vo.VoucherVO;
import com.example.hotel.vo.AvailableVoucherVO;
import com.example.hotel.vo.ResponseVO;


import java.util.List;

public interface VoucherService {

    List<Voucher> getUserVoucher(Integer userId);

    List<Voucher> getAvailableVoucher(int targetMoney,Integer userId);

    List<Voucher> getAllVoucher();

    void addVoucher(Voucher voucher);

    void addVoucherToAll(Voucher voucher);

    List<Voucher> getAvailableVoucher(AvailableVoucherVO availableVoucherVO);

    ResponseVO useVoucher(int id);
}
