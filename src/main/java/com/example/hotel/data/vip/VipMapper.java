package com.example.hotel.data.vip;

import com.example.hotel.po.Vipcard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VipMapper {

    int addVipCard(Vipcard vipcard);

    Vipcard getVIPInfoByUserId(@Param("id") int id);

    int setSavings(@Param("cardId") int cardId,@Param("savings") double savings);



}
