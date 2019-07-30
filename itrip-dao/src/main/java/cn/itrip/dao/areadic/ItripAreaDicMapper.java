package cn.itrip.dao.areadic;

import cn.itrip.beans.pojo.ItripAreaDic;

import java.util.List;

public interface ItripAreaDicMapper {
    //查询全部
    public List<ItripAreaDic> findById(Integer id);

    //添加
    public Integer inset(ItripAreaDic itripAreaDic);

    //修改
    public Integer updateById(ItripAreaDic itripAreaDic);

    //删除
    public Integer deleteById(ItripAreaDic itripAreaDic);
}
