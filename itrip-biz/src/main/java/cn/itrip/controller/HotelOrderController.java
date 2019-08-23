package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.*;
import cn.itrip.beans.vo.order.*;
import cn.itrip.beans.vo.store.StoreVO;
import cn.itrip.common.*;
import cn.itrip.service.hotelorder.ItripHotelOrderService;
import cn.itrip.service.hotelroom.ItripHotelRoomService;
import cn.itrip.service.hoteltempstore.ItripHotelTempStoreService;
import cn.itrip.service.hotle.ItripHotelService;
import cn.itrip.service.orderlinkuser.ItripOrderLinkUserService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping("/api/hotelorder")
public class HotelOrderController {
    @Resource
    private ItripHotelOrderService itripHotelOrderService;
    @Resource
    private ItripHotelRoomService itripHotelRoomService;
    @Resource
    private ValidationToken validationToken;
    @Resource
    private ItripHotelService itripHotelService;
    @Resource
    private ItripHotelTempStoreService itripHotelTempStoreService;
    @Resource
    private SystemConfig systemConf;
    @Resource
    private ItripOrderLinkUserService itripOrderLinkUserService;
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
        if(EmptyUtils.isNotEmpty(itripUser)){
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

    /**
     * 根据修改订房日期验证是否有房
     * @param validateRoomStoreVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/validateroomstore", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto validateRoomStore(@RequestBody ValidateRoomStoreVO validateRoomStoreVO,HttpServletRequest request){
        String token =request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);

        if(EmptyUtils.isEmpty(itripUser)){
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if(EmptyUtils.isEmpty(validateRoomStoreVO.getHotelId())){
            return DtoUtil.returnFail("hotelId不能为空", "100515");
        }else if(EmptyUtils.isEmpty(validateRoomStoreVO.getRoomId())){
            return DtoUtil.returnFail("roomId不能为空", "100516");
        }else{
            Map<String,Object> parma = new HashMap<>();
            parma.put("startTime",validateRoomStoreVO.getCheckInDate());
            parma.put("endTime",validateRoomStoreVO.getCheckOutDate());
            parma.put("roomId",validateRoomStoreVO.getRoomId());
            parma.put("hotelId",validateRoomStoreVO.getHotelId());
            parma.put("count",validateRoomStoreVO.getCount());
            try {
                boolean flag=itripHotelTempStoreService.validateRoomStore(parma);
                Map<String,Object> data=new HashMap<>();
                data.put("flag",flag);
                return DtoUtil.returnSuccess("操作成功", data);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("系统异常", "100517");
            }
        }
    }
    //生成订单前获取预定信息
    @RequestMapping(value = "/getpreorderinfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto<RoomStoreVO> getPreOrderInfo(HttpServletRequest request,@RequestBody ValidateRoomStoreVO validateRoomStoreVO){
        String token =request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);
        RoomStoreVO roomStoreVO=null;
        ItripHotel itripHotel=null;
        ItripHotelRoom itripHotelRoom=null;
        if(EmptyUtils.isEmpty(itripUser)){
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(validateRoomStoreVO.getHotelId())) {
            return DtoUtil.returnFail("hotelId不能为空", "100510");
        } else if (EmptyUtils.isEmpty(validateRoomStoreVO.getRoomId())) {
            return DtoUtil.returnFail("roomId不能为空", "100511");
        } else {
            roomStoreVO=new RoomStoreVO();
            try {
                itripHotel=itripHotelService.getByHotelId(validateRoomStoreVO.getHotelId());
                itripHotelRoom=itripHotelRoomService.getItripHotelRoomById(validateRoomStoreVO.getRoomId());
                Map<String,Object> param=new HashMap<>();
                param.put("startTime", validateRoomStoreVO.getCheckInDate());
                param.put("endTime", validateRoomStoreVO.getCheckOutDate());
                param.put("roomId", validateRoomStoreVO.getRoomId());
                param.put("hotelId", validateRoomStoreVO.getHotelId());
                roomStoreVO.setCheckInDate(validateRoomStoreVO.getCheckInDate());
                roomStoreVO.setCheckOutDate(validateRoomStoreVO.getCheckOutDate());
                roomStoreVO.setHotelName(itripHotel.getHotelName());
                roomStoreVO.setRoomId(itripHotelRoom.getId());
                roomStoreVO.setPrice(itripHotelRoom.getRoomPrice());
                roomStoreVO.setHotelId(validateRoomStoreVO.getHotelId());
                roomStoreVO.setCount(1);
                List<StoreVO> storeVOList = itripHotelTempStoreService.queryRoomStore(param);
                if (EmptyUtils.isNotEmpty(storeVOList)) {
                    roomStoreVO.setStore(storeVOList.get(0).getStore());
                    return DtoUtil.returnSuccess("获取成功", roomStoreVO);
                } else {
                    return DtoUtil.returnFail("暂时无房", "100512");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("系统异常", "100513");
            }
        }
    }
    //生成订单
    @RequestMapping(value = "/addhotelorder", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto addHotelOrder(HttpServletRequest request,@RequestBody ItripAddHotelOrderVO itripAddHotelOrderVO){
        String token =request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);
        Map<String, Object> validateStoreMap = new HashMap<String, Object>();

        validateStoreMap.put("startTime", itripAddHotelOrderVO.getCheckInDate());
        validateStoreMap.put("endTime", itripAddHotelOrderVO.getCheckOutDate());
        validateStoreMap.put("hotelId", itripAddHotelOrderVO.getHotelId());
        validateStoreMap.put("roomId", itripAddHotelOrderVO.getRoomId());
        validateStoreMap.put("count", itripAddHotelOrderVO.getCount());
        List<ItripUserLinkUser> linkUsers=itripAddHotelOrderVO.getLinkUser();
        if(EmptyUtils.isEmpty(itripUser)){
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        try {
            boolean flag=itripHotelTempStoreService.validateRoomStore(validateStoreMap);
            if(flag && EmptyUtils.isNotEmpty(itripAddHotelOrderVO)){
                //算出预定天数
                Integer days= DateUtil.getBetweenDates(itripAddHotelOrderVO.getCheckInDate(),
                        itripAddHotelOrderVO.getCheckOutDate()).size()-1;

                if(days<=0){
                    return DtoUtil.returnFail("退房日期必须大于入住日期", "100505");
                }
                ItripHotelOrder itripHotelOrder=new ItripHotelOrder();
                //测试一下
                BeanUtils.copyProperties(itripHotelOrder,itripAddHotelOrderVO);
                itripHotelOrder.setUserId(itripUser.getId());
                itripHotelOrder.setCreatedBy(itripAddHotelOrderVO.getId());
                StringBuffer sb =new StringBuffer();
                for(int i=0;i<linkUsers.size();i++){
                    if(i!=linkUsers.size()-1){
                        sb.append(linkUsers.get(i).getLinkUserName()+",");
                    }else{
                        sb.append(linkUsers.get(i).getLinkUserName());
                    }
                }
                itripHotelOrder.setLinkUserName(sb.toString());
                itripHotelOrder.setBookingDays(days);
                if(token.startsWith("token:PC")){
                    itripHotelOrder.setBookType(0);
                }else if(token.startsWith("token:MOBILE")){
                    itripHotelOrder.setBookType(1);
                }else{
                    itripHotelOrder.setBookType(2);
                }
                //订单还未付款的时候设置为 ： 未支付状态
                itripHotelOrder.setOrderStatus(0);

                //生成订单号格式：（商品IDs+毫秒数+1000000的随机数）
                StringBuffer timeCode=new StringBuffer();
                timeCode.append(itripAddHotelOrderVO.getHotelId());
                timeCode.append(itripAddHotelOrderVO.getRoomId());
                timeCode.append(System.currentTimeMillis());
                //两种随机数
               // timeCode.append(Math.random()*1000000);
                timeCode.append(new Random().nextInt(1000000));
                //生成6位的MD5
                String md5str=MD5.getMd5(timeCode.toString(),6);
                StringBuffer orderNO=new StringBuffer();
                orderNO.append(systemConf.getMachineCode());
                orderNO.append(DateUtil.format(new Date(),"yyyyMMddHHmmss"));
                orderNO.append(md5str);
                itripHotelOrder.setOrderNo(orderNO.toString());

                //计算价格
                itripHotelOrder.setPayAmount(itripHotelOrderService.getOrderPayAmount(days*itripAddHotelOrderVO.getCount(),itripAddHotelOrderVO.getRoomId()));

                ConcurrentMap<Object,Object> map =new ConcurrentHashMap<>();
                map.put(itripHotelOrder,linkUsers);
                return DtoUtil.returnSuccess("生成订单成功", map);
            }else if(flag && null==itripAddHotelOrderVO){
                return DtoUtil.returnFail("不能提交空，请填写订单信息", "100506");
            }else{
                return DtoUtil.returnFail("库存不足", "100507");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("生成订单失败", "100505");
        }
    }

    //修改订单的支付方式和状态
    @RequestMapping(value = "/updateorderstatusandpaytype", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto updateOrderStatusAndPayType(@RequestBody ItripModifyHotelOrderVO itripModifyHotelOrderVO,HttpServletRequest request){
        String token =request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);
        if(EmptyUtils.isNotEmpty(itripUser) && EmptyUtils.isNotEmpty(itripModifyHotelOrderVO)){
            ItripHotelOrder itripHotelOrder=new ItripHotelOrder();
            itripHotelOrder.setOrderStatus(2);
            itripHotelOrder.setId(itripModifyHotelOrderVO.getId());
            itripHotelOrder.setOrderStatus(itripModifyHotelOrderVO.getOrderType());
            itripHotelOrder.setPayType(itripModifyHotelOrderVO.getPayType());
            itripHotelOrder.setModifiedBy(itripUser.getId());
            itripHotelOrder.setModifyDate(new Date(System.currentTimeMillis()));
            try {
                System.out.println(itripHotelOrder.getPayType()+"+"+itripHotelOrder.getId());
                    if(itripHotelOrderService.itriptxModifyItripHotelOrder(itripHotelOrder)){

                        return DtoUtil.returnSuccess("修改订单成功");
                    }else{
                        return DtoUtil.returnFail("修改订单异常", "100524");
                    }
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("修改订单失败", "100522");
            }
        }else if(EmptyUtils.isEmpty(itripUser) && EmptyUtils.isEmpty(itripModifyHotelOrderVO)){
            return DtoUtil.returnFail("不能提交空，请填写订单信息", "100523");
        }else{
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }

    }

    //根据订单ID查看个人订单详情
    @RequestMapping(value = "/getpersonalorderinfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto<ItripPersonalHotelOrderVO> getPersonalOrderInfo(@PathVariable String orderId,HttpServletRequest request){
        String token =request.getHeader("token");
        ItripUser itripUser = validationToken.getCurrentUser(token);

        if(EmptyUtils.isNotEmpty(itripUser)){
            if(orderId==null && orderId.equals("")){
                return DtoUtil.returnFail("请传递参数：orderId", "100525");
            }
            try {
                ItripHotelOrder itripHotelOrder=itripHotelOrderService.getItripHotelOrderById(Long.valueOf(orderId));

                if(EmptyUtils.isNotEmpty(itripHotelOrder)){
                    ItripPersonalHotelOrderVO itripPersonalHotelOrderVO=new ItripPersonalHotelOrderVO();
                    itripPersonalHotelOrderVO.setId(itripHotelOrder.getId());
                    itripPersonalHotelOrderVO.setOrderNo(itripHotelOrder.getOrderNo());
                    itripPersonalHotelOrderVO.setBookType(itripHotelOrder.getBookType());
                    itripPersonalHotelOrderVO.setCreationDate(itripHotelOrder.getCreationDate());
                    //查询订单中房间信息
                    ItripHotelRoom itripHotelRoom=itripHotelRoomService.getItripHotelRoomById(itripHotelOrder.getRoomId());
                    if(EmptyUtils.isNotEmpty(itripHotelRoom)){
                        itripPersonalHotelOrderVO.setRoomPayType(itripHotelRoom.getPayType());
                    }

                    Integer orderStatus=itripHotelOrder.getOrderStatus();
                    itripPersonalHotelOrderVO.setOrderStatus(orderStatus);

                    if (orderStatus == 1) {
                        itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConf.getOrderProcessCancel()));
                        itripPersonalHotelOrderVO.setProcessNode("3");
                    } else if (orderStatus == 0) {
                        itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConf.getOrderProcessOK()));
                        itripPersonalHotelOrderVO.setProcessNode("2");//订单支付
                    } else if (orderStatus == 2) {
                        itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConf.getOrderProcessOK()));
                        itripPersonalHotelOrderVO.setProcessNode("3");//支付成功（未出行）
                    } else if (orderStatus == 3) {
                        itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConf.getOrderProcessOK()));
                        itripPersonalHotelOrderVO.setProcessNode("5");//订单点评
                    } else if (orderStatus == 4) {
                        itripPersonalHotelOrderVO.setOrderProcess(JSONArray.parse(systemConf.getOrderProcessOK()));
                        itripPersonalHotelOrderVO.setProcessNode("6");//订单完成
                    } else {
                        itripPersonalHotelOrderVO.setOrderProcess(null);
                        itripPersonalHotelOrderVO.setProcessNode(null);
                    }

                    itripPersonalHotelOrderVO.setPayAmount(itripHotelOrder.getPayAmount());
                    itripPersonalHotelOrderVO.setPayType(itripHotelOrder.getPayType());
                    itripPersonalHotelOrderVO.setNoticePhone(itripHotelOrder.getNoticePhone());

                    return DtoUtil.returnSuccess("获取个人订单信息成功", itripPersonalHotelOrderVO);
                }else{
                    return DtoUtil.returnFail("没有相关订单信息", "100526");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("获取个人订单信息错误", "100527");
            }
        }else{
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
    }
    //根据订单ID查看个人订单详情-房型相关信息

    @RequestMapping(value = "/getpersonalorderroominfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto getPersonalOrderRoomInfo(@PathVariable String orderId,HttpServletRequest request){
        String token =request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);
        if(EmptyUtils.isNotEmpty(itripUser)){
            if(orderId==null && orderId.equals("")){
                return DtoUtil.returnFail("请传递参数：orderId", "100529");
            }
            try {
                ItripPersonalOrderRoomVO itripPersonalOrderRoomVO=itripHotelRoomService.getItripHotelOrderRoomInfoById(Long.valueOf(orderId));

                if(EmptyUtils.isNotEmpty(itripPersonalOrderRoomVO)){
                    return DtoUtil.returnSuccess("获取个人订单房型信息成功", itripPersonalOrderRoomVO);
                }else{
                   return DtoUtil.returnFail("没有相关订单房型信息", "100530");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("获取个人订单房型信息错误", "100531");
            }
        }else{
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
    }

    //根据订单ID获取订单信息
    @RequestMapping(value = "/queryOrderById/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto queryOrderById(@PathVariable String orderId,HttpServletRequest request){
        String token =request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);
        if(EmptyUtils.isNotEmpty(itripUser)){
            if(orderId!=null && !orderId.equals("")){
                try {
                    ItripHotelOrder itripHotelOrder=itripHotelOrderService.getItripHotelOrderById(Long.valueOf(orderId));
                    if(EmptyUtils.isEmpty(itripHotelOrder)){
                        return DtoUtil.returnFail("没有查询到相应订单", "100533");
                    }else{
                        ItripModifyHotelOrderVO itripModifyHotelOrderVO=new ItripModifyHotelOrderVO();
                        BeanUtils.copyProperties(itripModifyHotelOrderVO,itripHotelOrder);
                        ConcurrentMap<String,Object> map=new ConcurrentHashMap();
                        map.put("orderId",itripModifyHotelOrderVO.getId());
                        List<ItripOrderLinkUserVo> list =itripOrderLinkUserService.getItripOrderLinkUserListByMap(map);
                        itripModifyHotelOrderVO.setItripOrderLinkUserList(list);
                        return DtoUtil.returnSuccess("获取订单成功", itripModifyHotelOrderVO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return DtoUtil.returnFail("系统异常", "100534");
                }
            }else{
                return DtoUtil.returnFail("无效信息", "100533");
            }
        }else{
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
    }

    //扫描中间表,执行库存更新操作
    @RequestMapping(value = "/scanTradeEnd", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto scanTradeEnd(){
        return  null;
    }
}
