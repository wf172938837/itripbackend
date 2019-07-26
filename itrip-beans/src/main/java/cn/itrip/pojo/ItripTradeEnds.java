package cn.itrip.pojo;

import java.io.Serializable;
//支付宝交易成功中间表 实体
public class ItripTradeEnds implements Serializable {
    private Long id;		//订单ID
    private String orderNo;	//订单编号(注意非支付宝交易编号tradeNo)
    private Integer flag;	//处理标识 0：未处理；1：处理中

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
