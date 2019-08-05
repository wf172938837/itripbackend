package cn.itrip.auth.contorller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
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
@RequestMapping(value ="api")
public class LoginController {

    @Resource(name = "userServiceImpl")
    private UserService us;

    @Resource(name = "tokenService")
    private TokenService tokenService;
    //登陆
    @RequestMapping(value = "/dologin",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dologin(@RequestParam String name, @RequestParam String password, HttpServletRequest request){
        Map<String,Object> param=new HashMap<>();
        if(!EmptyUtils.isEmpty(name) && !EmptyUtils.isEmpty(password)){
            ItripUser itripUser =new ItripUser();
            name=name.trim();
            password=MD5.getMd5(password.trim(),32);
            param.put("userCode",name);
            param.put("userPassword",password);
            try {
                itripUser=us.login(param);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
            }

            //判断itripUser对象不为空
            if(EmptyUtils.isNotEmpty(itripUser)){
                //创建token
                String token = tokenService.generateToken(request.getHeader("User-Agent"),itripUser);
                //保存token
                tokenService.save(token,itripUser);
                //传入token 过期时间，当前时间
                ItripTokenVO itripTokenVO=new ItripTokenVO(token, Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,Calendar.getInstance().getTimeInMillis());

                return DtoUtil.returnDataSuccess(itripTokenVO);
            }else{
                return DtoUtil.returnFail("用户名或密码错误！！！",ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        }else{
            return DtoUtil.returnFail("参数错误！检查提交的参数名称是否正确。", ErrorCode.AUTH_PARAMETER_ERROR);
        }

    }
    //注销业务
    @RequestMapping(value = "/logout",method = RequestMethod.GET,headers = "token")
    @ResponseBody
    public Dto cancellation(HttpServletRequest request){

        String token=request.getHeader("token");

        if(tokenService.validate(request.getHeader("User-Agent"),token)){
            return DtoUtil.returnFail("Token无效",ErrorCode.AUTH_TOKEN_INVALID);
        }
        tokenService.delete(token);
        return DtoUtil.returnSuccess("注销成功");
    }

}
