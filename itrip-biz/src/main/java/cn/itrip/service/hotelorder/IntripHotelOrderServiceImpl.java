package cn.itrip.service.hotelorder;

import cn.itrip.beans.vo.order.ItripListHotelOrderVO;
import cn.itrip.common.Constants;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class IntripHotelOrderServiceImpl implements ItripHotelOrderService{
    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
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



}
