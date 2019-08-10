package cn.itrip.service.areadic;

import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.dao.areadic.ItripAreaDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ItripAreaDicServiceImpl implements ItripAreaDicService{
    @Resource
    private ItripAreaDicMapper itripAreaDicMapper;

    @Override
    public List<ItripAreaDic> getItripAreaDicListByMap(Map<String,Object> params) throws Exception {
        return itripAreaDicMapper.getItripAreaDicListByMap(params);
    }
}
