package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelSearchVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    void addHotel(HotelVO hotelVO) throws ServiceException;

    /**
     * 删除酒店
     * @param hotelId
     * @throws
     */
    void deleteHotel(int hotelId) throws ServiceException;

    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();
    /**
    * 获取目标酒店信息
    * */
    List<HotelVO> hotelSearch(HotelSearchVO hotelSearchVO);

    /**
     * 列表获取管理员管理目标酒店列表
     * @param userid
     */
    List<HotelVO> getTargetHotel(int userid);

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    ResponseVO updateHotelInfo(int id, String name,String bizRegion, String description);
    /** @param cover */
    ResponseVO updateHotelCover(int id, MultipartFile cover);

    /*
    * 获得所有商圈
    * */

    List<String> getBizRegions();

    int findHotel(String hotelName);


}
