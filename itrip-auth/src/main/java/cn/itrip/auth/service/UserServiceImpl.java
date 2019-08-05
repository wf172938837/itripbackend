package cn.itrip.auth.service;

import cn.itrip.auth.exception.UserLoginFailedException;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private ItripUserMapper ium;
    @Resource
    private MailService mailService;
    @Resource
    private RedisAPI redisAPI;
    @Resource
    private SmsService smsService;

    private int expire=20;//过期时间（分钟）
    @Override
    /**
     * param: 前台传来的用户名 密码
     * return:返回用户实体
     */
    public ItripUser login(Map<String,Object> param) throws Exception {

            List<ItripUser> user=ium.getItripUserListByMap(param);
            if(user !=null && user.size()>0){
                if(user.get(0).getActivated()!=-1){
                    throw new UserLoginFailedException("用户未激活");
                }
                return user.get(0);
            }
        return null;
    }
    /**
     *邮箱注册
     * @param: user对象
     * @throws Exception
     */
    @Override
    public void itriptxCreateUser(ItripUser itripUser) throws Exception {
        //生成激活码
        String activationCode= MD5.getMd5(new Date().toLocaleString(),32);
        //发送邮件
        mailService.sendActivationMail(itripUser.getUserCode(),activationCode);
        //添加数据库
        ium.insertItripUser(itripUser);
        //激活码存入缓存
        redisAPI.set("activate"+itripUser.getUserCode(),30*60,activationCode);
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
    //激活
    @Override
    public boolean activate(String userCode,String code) throws Exception {
            Map<String,Object> mapDate =new HashMap<String,Object>();
            String value=redisAPI.get("activate:"+userCode);
            if(value.equals(code)){
                mapDate.put("userCode",userCode);
                ItripUser itripUser= ium.getByMap(mapDate);
                if(itripUser !=null){
                    itripUser.setActivated(1);
                    itripUser.setFlatID(itripUser.getId());
                    ium.updateItripUser(itripUser);
                    return true;
                }
            }
            return false;
    }

    @Override
    public void itriptxCreateUserByPhone(ItripUser user) throws Exception {
        //创建激活码
        String code = String.valueOf(MD5.getRandomCode());
        //发送到那个手机 平台ID 验证时间
        smsService.send(user.getUserCode(), "1",new String[]{code,String.valueOf(expire)});
        //设置手机验证key
        String key="activate:"+user.getUserCode();
        //储存到redis缓存
        redisAPI.set(key, expire*60, code);

        ium.insertItripUser(user);
    }


}
