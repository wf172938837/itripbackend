package cn.itrip.service.hotelorder;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.beans.vo.order.ItripListHotelOrderVO;
import cn.itrip.common.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ItripHotelOrderService {
    public Page<ItripListHotelOrderVO>  queryOrderPageByMap(Map<String,Object> param, Integer pageNO, Integer pageSize) throws Exception;

    public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception;

    public boolean itriptxModifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception;

    public ItripHotelOrder getItripHotelOrderById(Long id) throws Exception;

    public boolean flushOrderStatus(Integer type)throws Exception;

    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param)throws Exception;

    public Map<String, String> itriptxAddItripHotelOrder(ItripHotelOrder itripHotelOrder, List<ItripUserLinkUser> linkUserList) throws Exception;
}
