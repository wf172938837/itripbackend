package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.beans.vo.userinfo.ItripAddUserLinkUserVO;
import cn.itrip.beans.vo.userinfo.ItripModifyUserLinkUserVO;
import cn.itrip.beans.vo.userinfo.ItripSearchUserLinkUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ValidationToken;
import cn.itrip.service.orderlinkuser.ItripOrderLinkUserService;
import cn.itrip.service.userlinkuser.ItripUserLinkUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/api/userinfo")
public class UserInfoController {
    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;
    @Resource
    private ValidationToken validationToken;
    @Resource
    private ItripOrderLinkUserService ItripOrderLinkUserService;
    //新增联系人
    @RequestMapping(value = "/adduserlinkuser",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto addItripUserLinkUser(@RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO, HttpServletRequest request){
        //获取token
        String token =request.getHeader("token");
        //通过token验证获取到当前的用户信息
        ItripUser currentUser= validationToken.getCurrentUser(token);
        if(currentUser!=null &&itripAddUserLinkUserVO!=null){
            ItripUserLinkUser itripUserLinkUser=new ItripUserLinkUser();
            itripUserLinkUser.setLinkUserName(itripAddUserLinkUserVO.getLinkUserName());
            itripUserLinkUser.setLinkIdCardType(itripAddUserLinkUserVO.getLinkIdCardType());
            itripUserLinkUser.setLinkIdCard(itripAddUserLinkUserVO.getLinkIdCard());
            itripUserLinkUser.setLinkPhone(itripAddUserLinkUserVO.getLinkPhone());
            itripUserLinkUser.setId(currentUser.getId());
            itripUserLinkUser.setModifyDate(new Date(System.currentTimeMillis()));
            try {
                itripUserLinkUserService.addItripUserLinkUser(itripUserLinkUser);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("新增常用联系人失败", "100411");
            }
            return DtoUtil.returnDataSuccess("新增联系人成功");
        }else if(currentUser!=null &&itripAddUserLinkUserVO==null){
            return DtoUtil.returnFail("不能提交空，请填写常用联系人信息","100412");
        }else{
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }

    @RequestMapping(value="/modifyuserlinkuser",method=RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto<Object> updateUserLinkUser(@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser curItripUser=validationToken.getCurrentUser(token);

        if(curItripUser!=null && itripModifyUserLinkUserVO!=null){
            ItripUserLinkUser itripUserLinkUser =new ItripUserLinkUser();
            itripUserLinkUser.setId(itripModifyUserLinkUserVO.getId());
            itripUserLinkUser.setLinkUserName(itripModifyUserLinkUserVO.getLinkUserName());
            itripUserLinkUser.setLinkIdCardType(itripModifyUserLinkUserVO.getLinkIdCardType());
            itripUserLinkUser.setLinkIdCard(itripModifyUserLinkUserVO.getLinkIdCard());
            itripUserLinkUser.setUserId(curItripUser.getId());
            itripUserLinkUser.setLinkPhone(itripModifyUserLinkUserVO.getLinkPhone());
            itripUserLinkUser.setModifiedBy(curItripUser.getId());
            itripUserLinkUser.setModifyDate(new Date(System.currentTimeMillis()));

            try {
                itripUserLinkUserService.modifyItripUserLinkUser(itripUserLinkUser);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("修改常用联系人失败", "100421");
            }
            return DtoUtil.returnSuccess("修改常用联系人成功");
        }else if(curItripUser!=null && itripModifyUserLinkUserVO==null){
            return DtoUtil.returnFail("不能提交空，请填写常用联系人信息","100422");
        }else{
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }

    @RequestMapping(value="/deluserlinkuser",method=RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto<Object> delUserLinkUser(Long[] ids,HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser curItripUser = validationToken.getCurrentUser(token);
        List<Long> listLong = new ArrayList<Long>();
        if (curItripUser != null && EmptyUtils.isNotEmpty(ids)) {
            try {
                List<Long> longUserIDs = ItripOrderLinkUserService.getItripOrderLinkUserIdsByOrder();
                Collections.addAll(listLong, ids);
                listLong.retainAll(longUserIDs);
                if (listLong.size() > 0) {
                    return DtoUtil.returnFail("所选的常用联系人中有与某条待支付的订单关联的项，无法删除", "100431");
                } else {
                    itripUserLinkUserService.deleteItripUserLinkUserByIds(ids);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("删除常用联系人失败", "100432");
            }
            return  DtoUtil.returnSuccess("删除常用联系人成功");
        } else if (curItripUser != null && EmptyUtils.isEmpty(ids)) {
            return DtoUtil.returnFail("请选择要删除的常用联系人", "100433");
        } else {
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }

    @RequestMapping(value = "/queryuserlinkuser",method= RequestMethod.POST)
    @ResponseBody
    public Dto<ItripUserLinkUser> queryUserLinkUser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser itripUser=validationToken.getCurrentUser(token);


        return null;
    }




}
