package cn.itrip.auth.service;

import cn.itrip.pojo.ItripUser;

import java.util.Map;

//用户管理接口
public interface UserService {
    //登录 获取账号密码
    public ItripUser login(Map<String, Object> param);
}
