package cn.itrip.service.tradeends;

import cn.itrip.beans.pojo.ItripTradeEnds;

import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripTradeEndsService {

    public Integer itriptxModifyItripTradeEnds(Map<String, Object> param)throws Exception;
    public List<ItripTradeEnds> getItripTradeEndsByMap(Map<String,Object> parmas) throws Exception;
}
