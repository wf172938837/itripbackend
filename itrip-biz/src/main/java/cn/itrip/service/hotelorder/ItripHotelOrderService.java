package cn.itrip.service.hotelorder;

import cn.itrip.beans.vo.order.ItripListHotelOrderVO;
import cn.itrip.common.Page;

import java.math.BigDecimal;
import java.util.Map;

public interface ItripHotelOrderService {

    public Page<ItripListHotelOrderVO>  queryOrderPageByMap(Map<String,Object> param, Integer pageNO, Integer pageSize) throws Exception;


    public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception;
}
