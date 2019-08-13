package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.order.ItripListHotelOrderVO;
import cn.itrip.beans.vo.order.ItripSearchOrderVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.common.ValidationToken;
import cn.itrip.service.hotelorder.ItripHotelOrderService;
import cn.itrip.service.orderlinkuser.ItripOrderLinkUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/hotelorder")
public class HotelOrderController {
    @Resource
    private ItripOrderLinkUserService itripOrderLinkUserService;
    @Resource
    private ItripHotelOrderService itripHotelOrderService;
    /**
     * 根据个人订单列表，分页显示
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping(value = "/getpersonalorderlist", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto getPersonalOrderList(@RequestBody ItripSearchOrderVO vo, HttpServletRequest request) {
        //判断Token
        String token =request.getHeader("token");
        ItripUser itripUser=new ValidationToken().getCurrentUser(token);
        if(itripUser!=null){
            if(EmptyUtils.isEmpty(vo.getOrderType())){
                return DtoUtil.returnFail("请传递参数：orderType", "100501");
            }
            if(EmptyUtils.isEmpty(vo.getOrderStatus())){
                return DtoUtil.returnFail("请传递参数：请传递参数：orderStatus", "100501");
            }
            Map<String,Object> params =new HashMap<String,Object>();
            params.put("userId",itripUser.getId());
            params.put("orderType",vo.getOrderType()==-1?null:vo.getOrderType());
            params.put("orderStatus",vo.getOrderStatus()==-1?null:vo.getOrderStatus());
            params.put("orderNo",vo.getOrderNo());
            params.put("linkUserName",vo.getLinkUserName());
            params.put("startDate",vo.getStartDate());
            params.put("endDate", vo.getEndDate());
            Page<ItripListHotelOrderVO> page =new Page<ItripListHotelOrderVO>();
            try {
                page=itripHotelOrderService.queryOrderPageByMap(params,vo.getPageNo(),vo.getPageSize());
                return DtoUtil.returnSuccess("获取个人订单列表成功", page);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("获取个人订单列表错误", "100503");
            }
        }else{
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
    }

}
