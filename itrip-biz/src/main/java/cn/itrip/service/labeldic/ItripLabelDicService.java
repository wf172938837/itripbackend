package cn.itrip.service.labeldic;

import cn.itrip.beans.vo.ItripLabelDicVO;

import java.util.List;

public interface ItripLabelDicService {
    
    public List<ItripLabelDicVO> findByParentId(Long id) throws Exception;
}
