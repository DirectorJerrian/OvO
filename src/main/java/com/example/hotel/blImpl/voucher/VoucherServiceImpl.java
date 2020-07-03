package com.example.hotel.blImpl.voucher;

import com.example.hotel.bl.voucher.VoucherService;
import com.example.hotel.data.voucher.VoucherMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.User;
import com.example.hotel.po.Voucher;
import com.example.hotel.vo.AvailableVoucherVO;
import com.example.hotel.vo.ResponseVO;

import com.example.hotel.vo.VoucherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final static String USEVOUCHER_SUCCESS="优惠卷使用成功";
    private final static String USEVOUCHER_FAILURE="优惠卷使用失败";
    @Autowired
    VoucherMapper voucherMapper;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public List<Voucher> getUserVoucher(Integer userId) {

        return voucherMapper.getUserVoucher(userId);
    }

    @Override
    public List<Voucher> getAvailableVoucher(int targetMoney, Integer userId) {
        return null;
    }

    @Override
    public List<Voucher> getAvailableVoucher(AvailableVoucherVO availableVoucherVO) {
        return voucherMapper.getUserAvailableVoucher(availableVoucherVO.getTargetMoney(),availableVoucherVO.getUserId());
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return voucherMapper.getAllVoucher();
    }

    @Override
    public void addVoucher(Voucher voucher) {
        voucherMapper.addVoucher(voucher);
    }

    //给每个人添加优惠券
    @Override
    public void addVoucherToAll(Voucher voucher) {
        List<User> userList=accountMapper.getAllUser();
        voucherMapper.update(voucher.getId());
        List<Integer> idList=new ArrayList<>();
        for(int i=0;i<userList.size();i++){
            idList.add(userList.get(i).getId());
        }
        for(int i=0;i<idList.size();i++){
            voucher.setUserId(idList.get(i));
            voucher.setStatus("可使用");
            voucherMapper.addVoucher(voucher);
        }

    }


    //使用优惠券
    @Override
    public ResponseVO useVoucher(int id){
        try{
            Voucher voucher=voucherMapper.getVoucher(id);
            if(voucher.getNumber()<=0)return ResponseVO.buildFailure(USEVOUCHER_FAILURE);
            voucherMapper.useVoucher(id);
            voucherMapper.refreshVoucher();
            return ResponseVO.buildSuccess(USEVOUCHER_SUCCESS);
        }catch (Exception e){
            return ResponseVO.buildFailure(USEVOUCHER_FAILURE);
        }
    }
}
