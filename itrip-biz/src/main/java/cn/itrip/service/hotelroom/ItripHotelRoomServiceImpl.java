package cn.itrip.service.hotelroom;

import cn.itrip.beans.pojo.ItripHotelRoom;
import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.beans.vo.order.ItripPersonalOrderRoomVO;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import cn.itrip.dao.hotelroom.ItripHotelRoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ItripHotelRoomServiceImpl implements ItripHotelRoomService {
    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
    @Override
    public List<ItripHotelRoomVO> getItripHotelRoomListByMap(Map<String, Object> param) throws Exception {
        return itripHotelRoomMapper.getItripHotelRoomListByMap(param);
    }

    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomById(id);
    }

    public ItripPersonalOrderRoomVO getItripHotelOrderRoomInfoById(Long orderId) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderRoomInfoById(orderId);
    }
}
