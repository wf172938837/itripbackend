package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

import java.util.Map;

//用户管理接口
public interface UserService {
    //登录 获取账号密码
    public ItripUser login(Map<String,Object> param) throws Exception;

    //邮箱注册
    public void itriptxCreateUser(ItripUser itripUser) throws Exception;

    //通过用户名查找用户
    public ItripUser findByName(String name) throws Exception;
    //查询该用户是否激活
    public boolean activate(String userCode,String code) throws Exception;

    //手机注册
    public void itriptxCreateUserByPhone(ItripUser user) throws Exception;
}
