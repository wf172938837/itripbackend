package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.vo.ItripAreaDicVO;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.service.areadic.ItripAreaDicService;
import cn.itrip.service.hotle.ItripHotelService;
import cn.itrip.service.labeldic.ItripLabelDicService;
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
    @Resource
    private ItripLabelDicService itripLabelDicService;
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

    /***
     * 查询商圈的接口
     *
     * @param cityId 根据城市查询商圈
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querytradearea/{cityId}"+"",produces = "application/json",method = RequestMethod.GET)
    @ResponseBody
    public Dto<ItripAreaDicVO> queryTradeArea(@PathVariable Long cityId){
        List<ItripAreaDicVO> itripAreaDicVO =null;
        List<ItripAreaDic> itripAreaDic=null;

        if(EmptyUtils.isNotEmpty(cityId)){
            Map<String,Object> dataMap=new HashMap<String,Object>();
            dataMap.put("isTradingArea",1);
            dataMap.put("parent",cityId);
            try {
                itripAreaDic= itripAreaDicService.getItripAreaDicListByMap(dataMap);

                if(EmptyUtils.isNotEmpty(itripAreaDic)){
                    for(ItripAreaDic temp:itripAreaDic){
                        ItripAreaDicVO itripAreaDicVO1=new ItripAreaDicVO();
                        BeanUtils.copyProperties(temp,itripAreaDicVO1);
                        itripAreaDicVO.add(itripAreaDicVO1);
                    }
                }
            } catch (Exception e) {
                DtoUtil.returnFail("系统异常", "10204");
                e.printStackTrace();
            }
        }else{
            return DtoUtil.returnFail("cityId不能为空", "10203");
        }
        return DtoUtil.returnDataSuccess(itripAreaDicVO);
    }

    /***
     * 查询酒店特色列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryhotelfeature", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Dto<ItripLabelDicVO> queryHotelFeature(){
        List<ItripLabelDicVO> itripLabelDicVOS=null;
        Long parentId=16L;
        try {
            itripLabelDicVOS=itripLabelDicService.findByParentId(parentId);
            if(EmptyUtils.isNotEmpty(itripLabelDicVOS)){
                return DtoUtil.returnDataSuccess(itripLabelDicVOS);
            }
        } catch (Exception e) {
            DtoUtil.returnFail("系统异常", "10205");
            e.printStackTrace();
        }
        return null;
    }
}
