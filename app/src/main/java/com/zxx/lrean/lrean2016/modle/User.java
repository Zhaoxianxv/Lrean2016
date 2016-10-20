package com.zxx.lrean.lrean2016.modle;

/**
 * Created by Personal on 2016/4/27.
 */
public class User {
    private String phone;
    private String password;
    private long id;
    private String UserImg;

    private String Usercode;
    //1200未绑定  1400审核成功  1300审核失败  1100审核中 银行卡审核状态
    private long banktype;
    //1200未绑定  1400审核成功  1300审核失败  1100审核中 身份证审核状态
    private long cardtype;
    //是否有交易密码 0无 1有
    private long Tradepwd;
    //银行卡后4位
    private String banknum;
    //开户行信息
    private String bankname;
    //因还信量
    private String refund;

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBanknum() {
        return banknum;
    }

    public void setBanknum(String banknum) {
        this.banknum = banknum;
    }

    public long getTradepwd() {
        return Tradepwd;
    }

    public void setTradepwd(long tradepwd) {
        Tradepwd = tradepwd;
    }

    public long getBanktype() {
        return banktype;
    }

    public void setBanktype(long banktype) {
        this.banktype = banktype;
    }

    public long getCardtype() {
        return cardtype;
    }

    public void setCardtype(long cardtype) {
        this.cardtype = cardtype;
    }

    public String getUserImg() {
        return UserImg;
    }

    public void setUserImg(String userImg) {
        UserImg = userImg;
    }

    public String getUsercode() {
        return Usercode;
    }

    public void setUsercode(String usercode) {
        Usercode = usercode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
