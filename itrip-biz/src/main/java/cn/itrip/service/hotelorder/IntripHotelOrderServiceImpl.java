package cn.itrip.service.hotelorder;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.vo.order.ItripListHotelOrderVO;
import cn.itrip.common.BigDecimalUtil;
import cn.itrip.common.Constants;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import cn.itrip.dao.hotelroom.ItripHotelRoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.math.BigDecimal.ROUND_DOWN;

@Service
public class IntripHotelOrderServiceImpl implements ItripHotelOrderService{

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;


    @Override
    public Page<ItripListHotelOrderVO> queryOrderPageByMap(Map<String, Object> param, Integer pageNO, Integer pageSize) throws Exception {
        Integer total=itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
        pageNO= EmptyUtils.isEmpty(pageNO)? Constants.DEFAULT_PAGE_NO:pageNO;
        pageSize=EmptyUtils.isEmpty(pageSize)?Constants.DEFAULT_PAGE_SIZE:pageSize;
        Page page= new Page(pageNO,pageSize,total);
        param.put("beginPos",page.getBeginPos());
        param.put("pageSize",page.getPageSize());
        List<ItripListHotelOrderVO> dataList=itripHotelOrderMapper.getOrderListByMap(param);
        page.setRows(dataList);
        return page;
    }

    //计算价格
    public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception {
        BigDecimal payAmount = null;
        BigDecimal roomPrice = itripHotelRoomMapper.getItripHotelRoomById(roomId).getRoomPrice();
        payAmount = BigDecimalUtil.OperationASMD(count, roomPrice,
                BigDecimalUtil.BigDecimalOprations.multiply,
                2, ROUND_DOWN);
        return payAmount;
    }
    //修改订单的支付状态和方式
    public boolean itriptxModifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception {
        ItripHotelOrder modufyItripHotelOrder1=itripHotelOrderMapper.getItripHotelOrderById(itripHotelOrder.getId());
        ConcurrentMap<String,Object> map =new ConcurrentHashMap<>();
        map.put("startTime",modufyItripHotelOrder1.getCheckInDate());
        map.put("endTime",modufyItripHotelOrder1.getCheckOutDate());
        map.put("count",modufyItripHotelOrder1.getCount());
        map.put("roomId",modufyItripHotelOrder1.getRoomId());
        itripHotelOrderMapper.updateRoomStore(modufyItripHotelOrder1);
        return itripHotelOrderMapper.updateItripHotelOrder(itripHotelOrder)>0?true:false;
    }

    public ItripHotelOrder getItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderById(id);
    }


}
