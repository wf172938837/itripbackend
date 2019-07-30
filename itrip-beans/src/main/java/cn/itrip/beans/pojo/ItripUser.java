package cn.itrip.beans.pojo;

import java.io.Serializable;
import java.util.Date;
//用户表
public class ItripUser implements Serializable {
    private Long id;//主键
    private String userCode;//用户登陆名
    private String userPassword;//用户登陆密码
    private Integer userType;//用户类型（标识：0 自注册用户 1 微信登录 2 QQ登录 3 微博登录）
    private Long flatID;//平台ID（根据不同登录用户，进行相应存入：自注册用户主键ID、微信ID、QQID、微博ID）
    private String userName;//用户昵称
    private String weChat;//微信号
    private String QQ;//qq号
    private String weibo;//微博账号
    private String baidu;//百度账号
    private Integer activated;//是否激活,(0 false，1 true,默认是0)
    private Date creationDate;//创建时间
    private Long createdBy;//创建人
    private Date modifyDate;//修改时间
    private Long modifiedBy;//修改人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getFlatID() {
        return flatID;
    }

    public void setFlatID(Long flatID) {
        this.flatID = flatID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getBaidu() {
        return baidu;
    }

    public void setBaidu(String baidu) {
        this.baidu = baidu;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
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
