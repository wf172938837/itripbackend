package cn.itrip.pojo;

import java.io.Serializable;
import java.util.Date;

//区域字典表
public class ItripAreaDic implements Serializable {
    private long id;//主键
    private String name;//区域名称
    private String areaNO;//区域编号
    private long parent;//父级区域ID
    private Integer isActivated;//是否激活0激活，1未激活
    private Integer isTradingArea;//是否是商圈 0：不是 1：是
    private Integer isHot;//是否是热门城市 0：不是 1：是
    private Integer level;//区域级别(0:国家级 1:省级 2:市级 3:县/区)
    private Integer isChina;//1:国内 2：国外
    private String pinyin;//区域名称拼音
    private Date creationDate;//创建时间
    private Long createdBy;//创建人
    private Date modifyDate;//修改时间
    private Long modifiedBy;//修改人

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaNO() {
        return areaNO;
    }

    public void setAreaNO(String areaNO) {
        this.areaNO = areaNO;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }

    public Integer getIsTradingArea() {
        return isTradingArea;
    }

    public void setIsTradingArea(Integer isTradingArea) {
        this.isTradingArea = isTradingArea;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsChina() {
        return isChina;
    }

    public void setIsChina(Integer isChina) {
        this.isChina = isChina;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
