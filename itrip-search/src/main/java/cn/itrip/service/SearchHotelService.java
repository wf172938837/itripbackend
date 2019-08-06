package cn.itrip.service;

import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.beans.vo.hotel.SearchHotelVO;
import cn.itrip.common.Page;

import java.util.List;

public interface SearchHotelService {

    /***
     * 搜索旅馆
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<ItripHotelVO> searchItripHotelPage(SearchHotelVO vo,Integer pageNo, Integer pageSize);

    /***
     * 根据热门城市查询酒店
     * @param pageSize
     * @return
     */
    public List<ItripHotelVO> searchItripHotelListByHotCity(Integer cityId, Integer pageSize)throws Exception;
}
