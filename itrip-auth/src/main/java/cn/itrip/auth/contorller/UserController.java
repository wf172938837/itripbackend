package cn.itrip.auth.contorller;

import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.userinfo.ItripUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/api")
public class UserController {
    @Resource
    private UserService userService;

    //邮箱用户注册
    @RequestMapping(value = "/doregister",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto insertUserMail(@RequestBody ItripUserVO itripUserVO)  {
        if (!validEmail(itripUserVO.getUserCode())){
            return DtoUtil.returnFail("邮箱格式不对", ErrorCode.AUTH_ILLEGAL_USERCODE);
        }
        ItripUser itripUser =new ItripUser();
        itripUser.setUserCode(itripUserVO.getUserCode());
        itripUser.setUserPassword(itripUserVO.getUserPassword());
        itripUser.setUserName(itripUserVO.getUserName());
        try {
            if(userService.findByName(itripUser.getUserCode())==null){
                itripUser.setUserPassword(MD5.getMd5(itripUser.getUserPassword(),32));
                userService.itriptxCreateUser(itripUser);
                return DtoUtil.returnSuccess();
            }else{
                return DtoUtil.returnFail("用户名已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }

    }
    //激活用户
    @RequestMapping(value = "activate",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto activate(String itripUserCode,String code){
        try {
            if(userService.activate(itripUserCode,code)){
                return DtoUtil.returnSuccess("激活成功");
            }else{
                //用Success是指请求成功了
                return DtoUtil.returnSuccess("激活失败");
            }        } catch (Exception e) {
            e.printStackTrace();
        }

        return DtoUtil.returnFail("激活出错",ErrorCode.AUTH_UNKNOWN);
    }

    /**			 *
     * 合法E-mail地址：
     * 1. 必须包含一个并且只有一个符号“@”
     * 2. 第一个字符不得是“@”或者“.”
     * 3. 不允许出现“@.”或者.@
     * 4. 结尾不得是字符“@”或者“.”
     * 5. 允许“@”前的字符中出现“＋”
     * 6. 不允许“＋”在最前面，或者“＋@”
     */
    private boolean validEmail(String email){

        String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"  ;
        return Pattern.compile(regex).matcher(email).find();
    }
    /**
     * 验证是否合法的手机号
     * @param phone
     * @return
     */
    private boolean validPhone(String phone) {
        String regex="^1[3578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }
}
