package cn.itrip.auth.contorller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
public class TokenController {
    @Resource
    private TokenService tokenService;
    @RequestMapping(value = "replace",method = RequestMethod.POST,headers = "token",produces = "application/json")
    @ResponseBody
    public Dto replace(HttpServletRequest request){
        try {
            String newToken =tokenService.replaceToken(request.getHeader("user-agent"),request.getHeader("token"));
            ItripTokenVO itripTokenVO = new ItripTokenVO(newToken,
                            Calendar.getInstance().getTimeInMillis()+TokenService.SESSION_TIMEOUT*1000,
                            Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(itripTokenVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_REPLACEMENT_FAILED);
        }
    }
}
