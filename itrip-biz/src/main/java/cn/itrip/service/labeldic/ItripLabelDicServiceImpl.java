package cn.itrip.service.labeldic;

import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItripLabelDicServiceImpl implements ItripLabelDicService{

    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Override
    public List<ItripLabelDicVO> findByParentId(Long id) throws Exception {

        return itripLabelDicMapper.getItripLabelDicByParentId(id);
    }

    @Override
    public List<ItripLabelDicVO> findRoomByParentId() throws Exception {
        return itripLabelDicMapper.getItripLabelDicByParentId(1L);
    }

}
