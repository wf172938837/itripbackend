package cn.itrip.beans.pojo;

import java.io.Serializable;
import java.util.Date;
//通用字典实体
public class ItripLabelDic implements Serializable {
    private Long id;//主键
    private String name;//通用字典名称(各种设施XXX名字例如 床型，停车等等)
    private String value;
    private String description;//描述
    private Long parentId;//父级标签id(1级标签则为0)
    private String pic;//class？？
    private Date creationDate;//创建时间
    private Long createdBy;//创建人
    private Date modifyDate;//修改时间
    private Long modifiedBy;//修改人


}
