package cn.itrip.dao;

import cn.itrip.common.Constants;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

//连接solr
public class BaseQuery<T> {

    private HttpSolrClient httpSolrClient;

    //该类启动后，创建HttpSolrClient对象，填入URL地址，设置解析格式默认为XML，设置最大连接时间
    public BaseQuery(String URL){
        httpSolrClient =new HttpSolrClient(URL);
        httpSolrClient.setParser(new XMLResponseParser());
        httpSolrClient.setConnectionTimeout(500);
    }
    //去solr中查询内容，返回list
    public List<T> queryList(SolrQuery solrQuery,Class clazz,Integer pageSize) throws IOException, SolrServerException {
        //从第1条开始
       solrQuery.setStart(0);
       //如果传来的每页显示的数量为null  则用默认的10.否则就用传来的
       solrQuery.setRows(EmptyUtils.isEmpty(pageSize)? Constants.DEFAULT_PAGE_SIZE:pageSize);
       //获取查询响应的对象
        QueryResponse queryResponse = httpSolrClient.query(solrQuery);
        //查询到内容的放入集合
        List<T> list =queryResponse.getBeans(clazz);
        return list;
    }

    //查询solr中的分页数据
    public Page<T> queryPage(SolrQuery solrQuery,Class clazz,Integer pageNo,Integer pageSize) throws IOException, SolrServerException {
        //获得当前页
        Integer curPage=EmptyUtils.isEmpty(pageNo)?Constants.DEFAULT_PAGE_NO-1:pageNo-1;
        //获得页容量
        Integer rows=EmptyUtils.isEmpty(pageSize)?Constants.DEFAULT_PAGE_SIZE:pageSize;
        //设置从哪个数据开始（limit第一个）
        solrQuery.setStart(curPage*rows);
        //设置一页有多少数据
        solrQuery.setRows(rows);
        //执行查询
        QueryResponse queryResponse=httpSolrClient.query(solrQuery);
        //查询返回结果
        SolrDocumentList solrDocuments=queryResponse.getResults();
        //solrDocuments.getNumFound()方法是返回总数据
        Page<T> page=new Page<T>(curPage+1,rows,new Long(solrDocuments.getNumFound()).intValue());
        List<T> list =queryResponse.getBeans(clazz);
        page.setRows(list);
        return page;
    }
}
