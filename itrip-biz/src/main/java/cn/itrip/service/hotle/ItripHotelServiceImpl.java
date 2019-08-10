package cn.itrip.service.hotle;

import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.beans.vo.hotel.HotelVideoDescVO;
import cn.itrip.beans.vo.hotel.ItripSearchDetailsHotelVO;
import cn.itrip.beans.vo.hotel.ItripSearchFacilitiesHotelVO;
import cn.itrip.beans.vo.hotel.ItripSearchPolicyHotelVO;
import cn.itrip.dao.hotel.ItripHotelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItripHotelServiceImpl implements ItripHotelService{

    @Resource
    private ItripHotelMapper itripHotelMapper;


    @Override
    public ItripHotel getByHotelId(Long id) throws Exception {
        return itripHotelMapper.getItripHotelById(id);
    }

    @Override
    public HotelVideoDescVO getVideoDescByHotelId(Long id) throws Exception {
       HotelVideoDescVO hotelVideoDescVO=new HotelVideoDescVO();
       //通过酒店ID查询商圈名称
        List<ItripAreaDic> dataDic=itripHotelMapper.getHotelAreaByHotelId(id);

        List<String> dataTrading =new ArrayList<String>();
        //遍历商圈名，加入到集合。
        for(ItripAreaDic temp:dataDic){
            dataTrading.add(temp.getName());
        }
        hotelVideoDescVO.setTradingAreaNameList(dataTrading);

        List<String> dataFeatureList=new ArrayList<String>();
        List<ItripLabelDic> itripLabelDic=new ArrayList<ItripLabelDic>();
        itripLabelDic=itripHotelMapper.getHotelFeatureByHotelId(id);
        for(ItripLabelDic temp1:itripLabelDic){
            dataFeatureList.add(temp1.getName());
        }
        hotelVideoDescVO.setHotelFeatureList(dataFeatureList);
        ItripHotel itripHotel =itripHotelMapper.getItripHotelById(id);
        hotelVideoDescVO.setHotelName(itripHotel.getHotelName());
        return hotelVideoDescVO;
    }

    @Override
    public ItripSearchFacilitiesHotelVO getFindByFacilities(Long id) throws Exception {
        return itripHotelMapper.getItripHotelFacilitiesById(id);
    }

    @Override
    public ItripSearchPolicyHotelVO getPolicyFindByHotelId(Long id) throws Exception {
        return itripHotelMapper.queryHotelPolicy(id);
    }

    @Override
    public List<ItripSearchDetailsHotelVO> queryHotelDetails(Long id) throws Exception {
        List<ItripLabelDic> dataLabel=new ArrayList<ItripLabelDic>();
        List<ItripSearchDetailsHotelVO> dataDetail=new ArrayList<ItripSearchDetailsHotelVO>();
        ItripSearchDetailsHotelVO vo1=new ItripSearchDetailsHotelVO();
        vo1.setName("酒店特色");
        vo1.setDescription(itripHotelMapper.getItripHotelById(id).getFacilities());
        dataDetail.add(vo1);
        dataLabel=itripHotelMapper.getHotelFeatureByHotelId(id);
        if(dataLabel.size()>0 && dataLabel!=null){
            ItripSearchDetailsHotelVO vo2=new ItripSearchDetailsHotelVO();
            for(ItripLabelDic temp:dataLabel){
                vo2.setName(temp.getName());
                vo2.setDescription(temp.getDescription());
            }
            dataDetail.add(vo2);
            return dataDetail;
        }
        return null;
    }
}
