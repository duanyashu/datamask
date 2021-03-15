package com.github.duanyashu.type;

/**
 * @description: 脱敏参数
 * @author: duanyashu
 * @time: 2021-02-25 09:50
 */
public enum DesensitiveType {

    /**
     * 中文名
     */
    CHINESE_NAME(1,0,null),

    /**
     * 身份证号
     */
    ID_CARD(8,3,null),
    /**
     * 座机号
     */
    FIXED_PHONE(4,1,"-"),
    /**
     * 手机号
     */
    MOBILE_PHONE(3,2,null),
    /**
     * 地址
     */
    ADDRESS(6,0,null),
    /**
     * 电子邮件
     */
    EMAIL(1,20,"@"),
    /**
     * 银行卡
     */
    BANK_CARD(6,4,null),
    /**
     * 公司开户银行联号
     */
    CNAPS_CODE(2,0,null),

    /**
     * 通用手动设置
     */
    BASIC(1,1,null);


    private Integer retainLeft;
    private Integer retainRight;
    private  String separator;

    DesensitiveType(Integer retainLeft, Integer retainRight,String separator) {
        this.retainLeft = retainLeft;
        this.retainRight = retainRight;
        this.separator=separator;
    }


    public void setRetainLeft(Integer retainLeft) {
        this.retainLeft = retainLeft;
    }

    public void setRetainRight(Integer retainRight) {
        this.retainRight = retainRight;
    }

    public Integer getRetainLeft() {
        return retainLeft;
    }

    public Integer getRetainRight() {
        return retainRight;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}