package cn.itrip.service;

import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.beans.vo.hotel.SearchHotelVO;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.common.PropertiesUtils;
import cn.itrip.dao.BaseQuery;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SearchHotelServiceImpl implements SearchHotelService{
    //从properties文件中读取key是baseURL的值。当solr的url
    private static String URL= PropertiesUtils.get("database.properties","baseUrl");
    private static BaseQuery<ItripHotelVO> itripHotelVOBaseQuery=new BaseQuery(URL);

    @Override
    public Page<ItripHotelVO> searchItripHotelPage(SearchHotelVO vo, Integer pageNo, Integer pageSize) {
        SolrQuery solrQuery=new SolrQuery("*:*");
        if(EmptyUtils.isNotEmpty(vo)){

        }




        return null;
    }
    //热门城市查询
    @Override
    public List<ItripHotelVO> searchItripHotelListByHotCity(Integer cityId, Integer pageSize) throws Exception {
        SolrQuery solrQuery=new SolrQuery("*:*");
        if(EmptyUtils.isNotEmpty(cityId)){
            solrQuery.addFacetField("cityId:"+cityId);
        }else{
            return null;
        }
        List<ItripHotelVO> list= itripHotelVOBaseQuery.queryList(solrQuery,ItripHotelVO.class,pageSize);
        return list;
    }
}
