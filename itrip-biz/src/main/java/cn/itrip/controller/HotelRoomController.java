package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.ItripImageVO;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.dao.hotelroom.ItripHotelRoomMapper;
import cn.itrip.service.image.ItripImageService;
import cn.itrip.service.labeldic.ItripLabelDicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/api/hotelroom")


public class HotelRoomController {

    @Resource
    private ItripLabelDicService itripLabelDicService;
    @Resource
    private ItripImageService itripImageService;
    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;
    /**
     *查询room图片 根据类型type=1是房间
     * @param targetId
     * @return
     */
    @RequestMapping(value = "/getimg/{targetId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto<ItripImageVO> getImgByTargetId(@PathVariable Long targetId){
        List<ItripImageVO> itripImageVO=null;
        if(EmptyUtils.isNotEmpty(targetId)){
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("type","1");
            params.put("targetId",targetId);
            try {
                itripImageVO=itripImageService.getItripImageListByMap(params);
                return DtoUtil.returnSuccess("获取酒店图片房型成功", itripImageVO);
            } catch (Exception e) {
                e.printStackTrace();
                return  DtoUtil.returnFail("获取酒店房型图片失败", "100301");
            }
        }else{
            return DtoUtil.returnFail("酒店房型id不能为空", "100302");
        }
    }

    /**
     * 根据入住时间 酒店ID 条件查询房间
     * @param vo
     * @return
     */
    @RequestMapping(value = "/queryhotelroombyhotel", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public  Dto<ItripHotelRoomVO> queryHotelRoomByHotel(@RequestBody SearchHotelRoomVO vo){
        List<ItripHotelRoomVO> itripHotelRoomVOS=null;
        if(EmptyUtils.isEmpty(vo.getHotelId())){
            return DtoUtil.returnFail("酒店ID不能为空", "100303");
        }
        if(EmptyUtils.isEmpty(vo.getStartDate()) ||EmptyUtils.isEmpty(vo.getEndDate())){
            return DtoUtil.returnFail("必须填写酒店入住及退房时间", "100303");
        }
        if(EmptyUtils.isNotEmpty(vo.getStartDate()) && EmptyUtils.isNotEmpty(vo.getEndDate())){
            if(vo.getStartDate().getTime()>vo.getEndDate().getTime()){
                return DtoUtil.returnFail("入住日期不能大于退房日期","100303");
            }
        }
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("hotelId",vo.getHotelId());
        params.put("isBook",vo.getIsBook());
        params.put("isHavingBreakfast", vo.getIsHavingBreakfast());
        params.put("isTimelyResponse", vo.getIsTimelyResponse());
        params.put("roomBedTypeId", vo.getRoomBedTypeId());
        params.put("isCancel", vo.getIsCancel());
        if(EmptyUtils.isEmpty(vo.getPayType()) || vo.getPayType()==3){
            params.put("payType",null);
        }else{
            params.put("payType",vo.getPayType());
        }
        try {
            itripHotelRoomVOS=itripHotelRoomMapper.getItripHotelRoomListByMap(params);

            return DtoUtil.returnSuccess("获取成功", itripHotelRoomVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店房型列表失败", "100304");
        }
    }
    @RequestMapping(value = "/queryhotelroombed", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto<ItripLabelDicVO> queryHotelRoomBed(){
        List<ItripLabelDicVO> itripLabelDicVO=null;
        try {
            itripLabelDicVO=itripLabelDicService.findRoomByParentId();
            return DtoUtil.returnSuccess("获取成功", itripLabelDicVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取床型失败", "100305");
        }
    }
}
