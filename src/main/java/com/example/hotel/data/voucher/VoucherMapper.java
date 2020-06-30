package com.example.hotel.data.voucher;

import com.example.hotel.po.Voucher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VoucherMapper {

    /**
     * notice: add voucher to all users must invoke this function repeatedly,
     * but not implement this feature in the database
     * edited by Hewenbing
     */
    void addVoucher(Voucher voucher);

    List<Voucher> getUserVoucher(@Param("userId") int userId);

    List<Voucher> getUserAvailableVoucher(@Param("targetMoney") int targetMoney,@Param("userId") int userId);

    List<Voucher> getAllVoucher();

    void update(@Param("id") int id);

    int useVoucher(@Param("id") int id);

    int refreshVoucher();

    Voucher getVoucher(@Param("id") int id);

}
