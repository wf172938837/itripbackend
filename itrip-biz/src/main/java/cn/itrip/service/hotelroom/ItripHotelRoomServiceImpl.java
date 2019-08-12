package cn.itrip.service.hotelroom;

import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.dao.hotelroom.ItripHotelRoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ItripHotelRoomServiceImpl implements ItripHotelRoomService {
    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;
    @Override
    public List<ItripHotelRoomVO> getItripHotelRoomListByMap(Map<String, Object> param) throws Exception {
        return itripHotelRoomMapper.getItripHotelRoomListByMap(param);
    }
}
