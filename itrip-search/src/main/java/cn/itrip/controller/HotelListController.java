package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.beans.vo.hotel.SearchHotCityVO;
import cn.itrip.beans.vo.hotel.SearchHotelVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.service.SearchHotelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/api/hotellist")
public class HotelListController {
    @Resource
    private SearchHotelService searchHotelService;

    @RequestMapping(value = "/searchItripHotelPage", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<Page<ItripHotelVO>> searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO) {
        Page page = null;
        if (EmptyUtils.isEmpty(searchHotelVO) || EmptyUtils.isEmpty(searchHotelVO.getDestination())) {
            return DtoUtil.returnFail("目的地不能为空", "20002");
        }
        try {
            page = searchHotelService.searchItripHotelPage(searchHotelVO, searchHotelVO.getPageNo(), searchHotelVO.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "20001");
        }
        return DtoUtil.returnDataSuccess(page);
    }

    @RequestMapping(value = "/searchItripHotelListByHotCity", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto<List<ItripHotelVO>> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO){
        if(EmptyUtils.isEmpty(searchHotCityVO) || EmptyUtils.isEmpty(searchHotCityVO.getCityId())){
            return DtoUtil.returnFail("热门城市id不能为空","20004");
        }
        try {
          List<ItripHotelVO>  list=searchHotelService.searchItripHotelListByHotCity(searchHotCityVO.getCityId(),searchHotCityVO.getCount());
            return DtoUtil.returnDataSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("异常","20004");
        }
    }
}
