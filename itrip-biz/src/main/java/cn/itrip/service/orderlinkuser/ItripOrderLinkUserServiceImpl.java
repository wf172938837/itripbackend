package cn.itrip.service.orderlinkuser;

import cn.itrip.dao.orderlinkuser.ItripOrderLinkUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItripOrderLinkUserServiceImpl implements ItripOrderLinkUserService {
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;
    @Override
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception {
        return itripOrderLinkUserMapper.getItripOrderLinkUserIdsByOrder();
    }
}
