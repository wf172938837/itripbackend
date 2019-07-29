package cn.itrip.auth.contorller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.MD5;
import cn.itrip.dto.Dto;
import cn.itrip.pojo.ItripUser;
import cn.itrip.vo.ItripTokenVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Resource(name = "userServiceImpl")
    private UserService us;

    @Resource
    private TokenService tokenService;
    //登陆
    @RequestMapping(value = "/dologin",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dologin(@RequestParam String name, @RequestParam String password, HttpServletRequest request){

        if(!EmptyUtils.isEmpty(name) && !EmptyUtils.isEmpty(password)){
            Map<String,Object> dataMap=new HashMap<String,Object>();
            ItripUser itripUser =new ItripUser();
            dataMap.put("name",name.trim());
            dataMap.put("password", MD5.getMd5(password.trim(),32));
            itripUser=us.login(dataMap);
            //判断itripUser对象不为空
            if(EmptyUtils.isNotEmpty(itripUser)){
                //创建token
                String token = tokenService.generateToken(request.getHeader("User-Agent"),itripUser);
                //保存token
                tokenService.save(token,itripUser);
                ItripTokenVO itripTokenVO=new ItripTokenVO(token, Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,Calendar.getInstance().getTimeInMillis());

                return DtoUtil.returnDataSuccess(itripTokenVO);
            }
        }
        return null;
    }
}
