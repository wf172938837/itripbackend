package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

import java.util.Map;

//用户管理接口
public interface UserService {
    //登录 获取账号密码
    public ItripUser login(Map<String,Object> param) throws Exception;

    //注册
    public boolean insertUser(ItripUser itripUser) throws Exception;

    //通过用户名查找用户
    public ItripUser findByName(String name) throws Exception;
}
