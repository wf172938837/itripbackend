package cn.itrip.service.orderlinkuser;

import java.util.List;

public interface ItripOrderLinkUserService {

    //查询未支付的联系人ID
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;
}
