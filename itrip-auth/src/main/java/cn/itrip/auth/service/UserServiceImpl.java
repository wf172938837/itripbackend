package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private ItripUserMapper ium;
    @Override
    /**
     * param: 前台传来的用户名 密码
     * return:返回用户实体
     */
    public ItripUser login(Map<String,Object> param) throws Exception {

            List<ItripUser> user=ium.getItripUserListByMap(param);
            if(user !=null && user.size()>0){
                return user.get(0);
            }
        return null;
    }

}
