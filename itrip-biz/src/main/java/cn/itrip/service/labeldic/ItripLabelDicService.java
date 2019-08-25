package cn.itrip.service.labeldic;

import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.beans.vo.ItripLabelDicVO;

import java.util.List;
import java.util.Map;

public interface ItripLabelDicService {
    
    public List<ItripLabelDicVO> findByParentId(Long id) throws Exception;

    public List<ItripLabelDicVO> findRoomByParentId() throws Exception;

    /**
     * 根据parentId查询数据字典
     * @param parentId
     * @return
     * @throws Exception
     * add by hanlu 2017-5-11
     */
    public List<ItripLabelDicVO> getItripLabelDicByParentId(Long parentId)throws Exception;

    public List<ItripLabelDic>	getItripLabelDicListByMap(Map<String,Object> param)throws Exception;
}
