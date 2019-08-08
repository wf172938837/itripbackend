package cn.itrip.service.userlinkuser;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.common.Constants;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.dao.userlinkuser.ItripUserLinkUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItripUserLinkUserServiceImpl implements ItripUserLinkUserService{
    @Resource
    private ItripUserLinkUserMapper itripUserLinkUserMapper;

    @Override
    public Integer addItripUserLinkUser(ItripUserLinkUser itripUserLinkUser) throws Exception {
        return itripUserLinkUserMapper.insertItripUserLinkUser(itripUserLinkUser);
    }

    @Override
    public Integer deleteItripUserLinkUserByIds(Long[] ids) throws Exception {

        return itripUserLinkUserMapper.deleteItripUserLinkUserByIds(ids);
    }

    @Override
    @Transactional
    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser) throws Exception {

        return itripUserLinkUserMapper.updateItripUserLinkUser(itripUserLinkUser);
    }

    @Override
    public List<ItripUserLinkUser> findByLinkUser(Map<String,Object> param) throws Exception {
        List<ItripUserLinkUser> dataList=itripUserLinkUserMapper.getItripUserLinkUserListByMap(param);
        return dataList;
    }

    @Override
    public ItripUserLinkUser getItripUserLinkUserById(Long id) throws Exception {

        return itripUserLinkUserMapper.getItripUserLinkUserById(id);
    }

    @Override
    public Page<ItripUserLinkUser> queryItripUserLinkUserPageByMap(Integer pageSize, Integer curPage, Map<String, Object> param) throws Exception {
        Integer total=this.getItripUserLinkUserCountByMap(param);
        Integer rows= EmptyUtils.isEmpty(pageSize)? Constants.DEFAULT_PAGE_SIZE:pageSize;
        Integer currePage=EmptyUtils.isEmpty(curPage)?Constants.DEFAULT_PAGE_NO:curPage;
        Page page =new Page(currePage,rows,total);
        Map<String,Object> mapData=new HashMap<String,Object>();
        mapData.put("beginPage",page.getBeginPos());
        mapData.put("pageSize",page.getPageSize());
        page.setRows(itripUserLinkUserMapper.getItripUserLinkUserListByMap(mapData));
        return page;
    }

    public List<ItripUserLinkUser> findByLinkUserName(Map<String,Object> param) throws Exception {

        return itripUserLinkUserMapper.getItripUserLinkUserListByMap(param);
    }
    @Override
    public Integer getItripUserLinkUserCountByMap(Map<String, Object> param) throws Exception {
        return itripUserLinkUserMapper.getItripUserLinkUserCountByMap(param);
    }
}
