package com.example.hotel.data.user;

import com.example.hotel.po.CreditRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CreditMapper {

    int createNewRecord(CreditRecord creditRecord);

    List<CreditRecord> getUserRecords(@Param("userid") int userid);


}
