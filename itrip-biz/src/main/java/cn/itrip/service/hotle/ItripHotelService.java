package cn.itrip.service.hotle;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.vo.hotel.HotelVideoDescVO;
import cn.itrip.beans.vo.hotel.ItripSearchDetailsHotelVO;
import cn.itrip.beans.vo.hotel.ItripSearchFacilitiesHotelVO;
import cn.itrip.beans.vo.hotel.ItripSearchPolicyHotelVO;

import java.util.List;

public interface ItripHotelService {

    /**
     * 根据酒店id查询酒店特色、商圈、酒店名称
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ItripHotel getByHotelId(Long id) throws Exception;

    public HotelVideoDescVO getVideoDescByHotelId(Long id) throws Exception;

    /**
     * 根据酒店的id查询酒店的设施 -add by donghai
     *
     * @param id
     * @return
     * @throws Exception
     */
    public ItripSearchFacilitiesHotelVO getFindByFacilities(Long id) throws Exception;

    /**
     * 根据酒店的id查询酒店的政策 -add by donghai
     *
     * @param id
     * @return
     * @throws Exception
     */

    public ItripSearchPolicyHotelVO getPolicyFindByHotelId(Long id) throws Exception;

    /**
     * 根据酒店的id查询酒店的特色和介绍 -add by donghai
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<ItripSearchDetailsHotelVO> queryHotelDetails(Long id) throws Exception;

}