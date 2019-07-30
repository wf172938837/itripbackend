package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
    public ItripUser login(String name,String password) {
        Map<String,Object> dataMap=new HashMap<String,Object>();
        dataMap.put("userCode",name);
        try {
            List<ItripUser> user=ium.getItripUserListByMap(dataMap);
            if(user !=null){
                return user.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
