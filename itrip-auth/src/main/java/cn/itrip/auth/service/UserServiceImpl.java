package cn.itrip.auth.service;

import cn.itrip.dao.user.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private ItripUserMapper ium;
    @Override
    /**
     * param: 前台传来的用户名 密码
     * return:返回用户实体
     */
    public ItripUser login(Map<String,Object> param) {
        try {
            List<ItripUser> user=ium.getItripUserListByMap(param);
            if(user.size()>0 && user!=null){
                return user.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
