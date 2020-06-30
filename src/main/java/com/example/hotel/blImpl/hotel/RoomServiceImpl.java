package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.RoomVO;
import com.example.hotel.po.HotelRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public void insertRoomInfo(RoomVO roomVO) {
        roomMapper.insertRoom(roomVO);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public void addRoomNum(int roomId){
        roomMapper.addTargetNum(roomId);

    }
    @Override
    public void subRoomNum(int roomId){
        roomMapper.subTargetNum(roomId);

    }

    @Override
    public List<Boolean> hasType(Integer hotelId) {
        List<Boolean> allType= new ArrayList<>();
        System.out.println(hotelId+"MPL");
        List<RoomVO> bigList= roomMapper.getBig(hotelId);
        if(bigList.size()==0) allType.add(true);
        else allType.add(false);
        List<RoomVO> DoubleList= roomMapper.getDouble(hotelId);
        if(bigList.size()==0) allType.add(true);
        else allType.add(false);
        List<RoomVO> FamList= roomMapper.getFam(hotelId);
        if(bigList.size()==0) allType.add(true);
        else allType.add(false);
        return allType;
    }


    @Override
    public void deleteRoomsByHotelId(Integer hotelId){
        roomMapper.deleteRoomsByHotelId(hotelId);
    }
}
