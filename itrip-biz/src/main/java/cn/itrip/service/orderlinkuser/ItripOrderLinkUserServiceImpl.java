package cn.itrip.service.orderlinkuser;

import cn.itrip.beans.pojo.ItripOrderLinkUser;
import cn.itrip.beans.vo.order.ItripOrderLinkUserVo;
import cn.itrip.dao.orderlinkuser.ItripOrderLinkUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ItripOrderLinkUserServiceImpl implements ItripOrderLinkUserService {
    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;

    @Override
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception {
        return itripOrderLinkUserMapper.getItripOrderLinkUserIdsByOrder();
    }

    @Override
    public ItripOrderLinkUser getItripOrderLinkUserById(Long id) throws Exception {
        return null;
    }

    @Override
    public List<ItripOrderLinkUserVo> getItripOrderLinkUserListByMap(Map<String, Object> param) throws Exception {
        return null;
    }
}
