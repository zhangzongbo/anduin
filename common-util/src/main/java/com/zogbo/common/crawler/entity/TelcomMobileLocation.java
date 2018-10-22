package com.zogbo.common.crawler.entity;

/**
 * Description here
 *
 * @author Jing XIAO (xiaojing@wecash.net)
 * @since 2015-10-21 16:42
 */
public class TelcomMobileLocation {
    /**
     Sample data from http://login.189.cn/login
     {
         "SeqID": 130963,
         "PhoneSen": "1398619",
         "ProvinceID": "18",
         "ProvinceName": "湖北",
         "CityNo": "027",
         "CityName": "武汉",
         "AreaCode": "027",
         "NetID": "",
         "CardType": "",
         "Ext1": "",
         "Ext2": "",
         "Ext3": "",
         "Ext4": "",
         "CreateTime": "/Date(1436417498000)/",
         "UpdateTime": "/Date(1436417498000)/",
         "Remark": ""
     }
     */

    private String SeqID;
    private String PhoneSen;
    private String ProvinceID;
    private String ProvinceName;
    private String CityNo;
    private String CityName;
    private String AreaCode;
    private String NetID;
    private String CardType;
    private String Ext1;
    private String Ext2;
    private String Ext3;
    private String Ext4;
    private String CreateTime;
    private String UpdateTime;
    private String Remark;

    public String getSeqID() {
        return SeqID;
    }

    public void setSeqID(String seqID) {
        SeqID = seqID;
    }

    public String getPhoneSen() {
        return PhoneSen;
    }

    public void setPhoneSen(String phoneSen) {
        PhoneSen = phoneSen;
    }

    public String getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(String provinceID) {
        ProvinceID = provinceID;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getCityNo() {
        return CityNo;
    }

    public void setCityNo(String cityNo) {
        CityNo = cityNo;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getNetID() {
        return NetID;
    }

    public void setNetID(String netID) {
        NetID = netID;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getExt1() {
        return Ext1;
    }

    public void setExt1(String ext1) {
        Ext1 = ext1;
    }

    public String getExt2() {
        return Ext2;
    }

    public void setExt2(String ext2) {
        Ext2 = ext2;
    }

    public String getExt3() {
        return Ext3;
    }

    public void setExt3(String ext3) {
        Ext3 = ext3;
    }

    public String getExt4() {
        return Ext4;
    }

    public void setExt4(String ext4) {
        Ext4 = ext4;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
