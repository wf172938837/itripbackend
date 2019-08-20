package cn.itrip.service.hoteltempstore;
import cn.itrip.beans.pojo.ItripHotelTempStore;
import cn.itrip.beans.vo.store.StoreVO;
import cn.itrip.common.Page;

import java.util.List;
import java.util.Map;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripHotelTempStoreService {
    public Page<ItripHotelTempStore> queryItripHotelTempStorePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    /***
     * @param: startTime 开始时间
     * @param :endTime 结束时间
     * @param :roomId 房间ID
     * @param :hotelId 酒店ID
     * @return
     * @throws Exception
     */
    public List<StoreVO> queryRoomStore(Map<String, Object> param)throws Exception;
    /***
     * @param :startTime 开始时间
     * @param :endTime 结束时间
     * @param :roomId 房间ID
     * @param :hotelId 酒店ID
     * @param :count 预订数目
     * @return
     * @throws Exception
     */
    public boolean validateRoomStore(Map<String, Object> param)throws Exception;
    /***
     * @param :startTime 开始时间
     * @param :endTime 结束时间
     * @param :roomId 房间ID
     * @param :count 预订数目
     * @return
     * @throws Exception
     */
    public boolean updateRoomStore(Map<String, Object> param) throws Exception;
}
