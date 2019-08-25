package cn.itrip.service.tradeends;

import cn.itrip.beans.pojo.ItripTradeEnds;
import cn.itrip.dao.tradeends.ItripTradeEndsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ItripTradeEndsServiceImpl implements ItripTradeEndsService {

    @Resource
    private ItripTradeEndsMapper itripTradeEndsMapper;

    public Integer itriptxModifyItripTradeEnds(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.updateItripTradeEnds(param);
    }
    public List<ItripTradeEnds> getItripTradeEndsByMap(Map<String,Object> parmas) throws Exception {
        return itripTradeEndsMapper.getItripTradeEndsListByMap(parmas);
    }
}
