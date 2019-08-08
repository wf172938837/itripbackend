package cn.itrip.service.userlinkuser;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.common.Page;

import java.util.List;
import java.util.Map;

//联系人
public interface ItripUserLinkUserService {
    //增
    public Integer addItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    //删
    public Integer deleteItripUserLinkUserByIds(Long[] ids)throws Exception;

    //修改
    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    //查询
    public List<ItripUserLinkUser> findByLinkUser(Map<String,Object> param) throws Exception;

    //通过ID查询
    public ItripUserLinkUser getItripUserLinkUserById(Long id)throws Exception;

    //分页查询常用联系人
    public Page<ItripUserLinkUser> queryItripUserLinkUserPageByMap(Integer pageSize,Integer curPage,Map<String,Object> param) throws Exception;

    //查询联系人总数量
    public Integer getItripUserLinkUserCountByMap(Map<String, Object> param)throws Exception;

    public List<ItripUserLinkUser> findByLinkUserName(Map<String,Object> param) throws Exception;

}
