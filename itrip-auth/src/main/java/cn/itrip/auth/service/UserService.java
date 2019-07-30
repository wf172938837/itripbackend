package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

//用户管理接口
public interface UserService {
    //登录 获取账号密码
    public ItripUser login(String name,String password);
}
