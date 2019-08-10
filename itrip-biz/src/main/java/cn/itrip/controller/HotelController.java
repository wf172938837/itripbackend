package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.vo.ItripAreaDicVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.service.areadic.ItripAreaDicService;
import cn.itrip.service.hotle.ItripHotelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/hotel")
public class HotelController {
    @Resource
    private ItripAreaDicService itripAreaDicService;
    @Resource
    private ItripHotelService itripHotelService;
    /****
     * 查询热门城市的接口
     *
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryhotcity/{type}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto<ItripAreaDicVO> queryHotCity(@PathVariable Integer type){
        Map<String,Object> params=new HashMap<String,Object>();
        List<ItripAreaDic> listDic=null;
        List<ItripAreaDicVO> listVO=null;
        if(EmptyUtils.isNotEmpty(type)){
            try {
                params.put("isHot",1);
                params.put("isChina",type);
                listDic=itripAreaDicService.getItripAreaDicListByMap(params);
                if(EmptyUtils.isNotEmpty(listDic)){
                    listVO=new ArrayList<ItripAreaDicVO>();
                    for(ItripAreaDic temp:listDic){
                        ItripAreaDicVO vo = new ItripAreaDicVO();
                        BeanUtils.copyProperties(temp,vo);
                        listVO.add(vo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DtoUtil.returnFail("系统异常", "10202");
            }
        }else{
            DtoUtil.returnFail("type不能为空", "10201");
        }
        return DtoUtil.returnDataSuccess(listVO);
    }
}
