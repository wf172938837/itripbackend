package cn.itrip.service.hotelroom;

import cn.itrip.beans.pojo.ItripHotelRoom;
import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.beans.vo.order.ItripPersonalOrderRoomVO;

import java.util.List;
import java.util.Map;

public interface ItripHotelRoomService {

    public List<ItripHotelRoomVO> getItripHotelRoomListByMap(Map<String,Object> param)throws Exception;

    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception;

    public ItripPersonalOrderRoomVO getItripHotelOrderRoomInfoById(Long orderId) throws Exception;
}
