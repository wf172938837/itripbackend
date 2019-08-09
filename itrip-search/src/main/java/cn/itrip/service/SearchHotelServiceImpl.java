package cn.itrip.service;

import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.beans.vo.hotel.SearchHotelVO;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.common.PropertiesUtils;
import cn.itrip.dao.BaseQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service("searchHotelServiceImpl")
public class SearchHotelServiceImpl implements SearchHotelService{
    //从properties文件中读取key是baseURL的值。当solr的url
    private static String URL= PropertiesUtils.get("database.properties","baseUrl");
    private static BaseQuery<ItripHotelVO> itripHotelVOBaseQuery=new BaseQuery(URL);

    //酒店查询
    @Override
    public Page<ItripHotelVO> searchItripHotelPage(SearchHotelVO vo, Integer pageNo, Integer pageSize) throws IOException, SolrServerException {
        SolrQuery solrQuery=new SolrQuery("*:*");
        StringBuffer tempQuery = new StringBuffer();
        int tempFlag=0;
        if(EmptyUtils.isNotEmpty(vo)){
           if(EmptyUtils.isNotEmpty(vo.getDestination())){
                tempQuery.append(" destination :"+vo.getDestination());
                tempFlag=1;
           }
           if(EmptyUtils.isNotEmpty(vo.getHotelLevel())){
               solrQuery.addFilterQuery("hotelLevel:"+vo.getHotelLevel()+"");
           }
           if(EmptyUtils.isNotEmpty(vo.getKeywords())){
               if(tempFlag==1){
                   tempQuery.append(" AND keyword :" + vo.getKeywords());
               }else{
                   tempQuery.append("keword : "+vo.getKeywords());
               }
           }
           //判断商圈
           if(EmptyUtils.isNotEmpty(vo.getFeatureIds())){
               StringBuffer buffer =new StringBuffer("(");
               int temp=0;
               String[] featureIdArray=vo.getFeatureIds().split(",");
               for(String feature:featureIdArray ){
                   if(temp==0){
                       buffer.append(" featureIds:" + "*," + feature + ",*");
                   }else{
                       buffer.append(" OR featureIds:" + "*," + feature + ",*");
                   }
                   temp++;
               }
               buffer.append(")");
               solrQuery.addFilterQuery(buffer.toString());
           }
           if(EmptyUtils.isNotEmpty(vo.getTradeAreaIds())){
               StringBuffer buffer = new StringBuffer("(");
               int flag = 0;
               String tradeAreaIdArray[]=vo.getTradeAreaIds().split(",");
               for (String tradeAreaId : tradeAreaIdArray) {
                   if (flag == 0) {
                       buffer.append(" tradingAreaIds:" + "*," + tradeAreaId + ",*");
                   } else {
                       buffer.append(" OR tradingAreaIds:" + "*," + tradeAreaId + ",*");
                   }
                   flag++;
               }
               buffer.append(")");
               solrQuery.addFilterQuery(buffer.toString());
           }
            if (EmptyUtils.isNotEmpty(vo.getMaxPrice())) {
                solrQuery.addFilterQuery("minPrice:" + "[* TO " + vo.getMaxPrice() + "]");
            }
            if (EmptyUtils.isNotEmpty(vo.getMinPrice())) {
                solrQuery.addFilterQuery("minPrice:" + "[" + vo.getMinPrice() + " TO *]");
            }

            if (EmptyUtils.isNotEmpty(vo.getAscSort())) {
                solrQuery.addSort(vo.getAscSort(), SolrQuery.ORDER.asc);
            }

            if (EmptyUtils.isNotEmpty(vo.getDescSort())) {
                solrQuery.addSort(vo.getDescSort(), SolrQuery.ORDER.desc);
            }
        }
        if (EmptyUtils.isNotEmpty(tempQuery.toString())) {
            solrQuery.setQuery(tempQuery.toString());
            System.out.println(tempQuery.toString());
        }
        Page<ItripHotelVO> page = itripHotelVOBaseQuery.queryPage(solrQuery, ItripHotelVO.class, pageNo, pageSize);
        return page;

    }
    //热门城市查询
    @Override
    public List<ItripHotelVO> searchItripHotelListByHotCity(Integer cityId, Integer pageSize) throws Exception {
        SolrQuery solrQuery=new SolrQuery("*:*");
        if(EmptyUtils.isNotEmpty(cityId)){
            solrQuery.addFilterQuery("cityId :"+cityId);
        }else{
            return null;
        }
        List<ItripHotelVO> list= itripHotelVOBaseQuery.queryList(solrQuery,ItripHotelVO.class,pageSize);
        return list;
    }
}
