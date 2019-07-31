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
    public ItripUser login(Map<String,Object> param) throws Exception {

            List<ItripUser> user=ium.getItripUserListByMap(param);
            if(user !=null && user.size()>0){
                return user.get(0);
            }
        return null;
    }

    /**
     *注册
     * @param: user对象
     * @return: 成功true 失败false
     * @throws Exception
     */
    @Override
    public boolean insertUser(ItripUser itripUser) throws Exception {
       Integer rows=ium.insertItripUser(itripUser);
        if(rows>0){
            return true;
        }
       return false;
    }

    /**
     *通过名字查找对象
     * @param： itripUser对象
     * @return：用户实体对象
     */
    @Override
    public ItripUser findByName(String name) throws Exception {
        Map<String,Object> dataMap=new HashMap<String,Object>();
        dataMap.put("userCode",name);
        List<ItripUser> list=ium.getItripUserListByMap(dataMap);
            if(list.size()>0 && list!=null){
                return list.get(0);
            }
        return null;
    }


}
