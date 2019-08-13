package cn.itrip.service.orderlinkuser;

import cn.itrip.beans.pojo.ItripOrderLinkUser;
import cn.itrip.beans.vo.order.ItripOrderLinkUserVo;

import java.util.List;
import java.util.Map;

public interface ItripOrderLinkUserService {

    //查询未支付的联系人ID
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;
    //根据Id查询本人订单信息列表
    public ItripOrderLinkUser getItripOrderLinkUserById(Long id)throws Exception;
    //根据Map查询个人订单列表
    public List<ItripOrderLinkUserVo> getItripOrderLinkUserListByMap(Map<String,Object> param)throws Exception;


}
