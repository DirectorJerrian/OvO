package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    int deleteHotel(@Param("id") Integer id);

    List<HotelVO> selectAllHotel();

    List<String> getBizRegions();

    List<HotelVO> selectQualifiedHotel(@Param("bizRegion") String bizRegion, @Param("rate") Integer rate);

    HotelVO selectById(@Param("id") Integer id);

    List<HotelVO> selectManager(@Param("userid") int userid);

    int setRate(@Param("id") int id,@Param("rate") double rate);

    int updateHotel(@Param("id") int id,@Param("hotelName") String name, @Param("bizRegion") String bizRegion,@Param("hotelDescription") String description);

    /** @param cover*/
    int updateCoverURL(@Param("id") int id,@Param("cover") String cover);

    int findHotel(@Param("hotelName") String hotelName);
}
