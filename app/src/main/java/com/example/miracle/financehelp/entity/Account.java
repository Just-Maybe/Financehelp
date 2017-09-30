package com.example.miracle.financehelp.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Miracle on 2017/9/30 0030.
 */
@Entity
public class Account {
    @Id
    private Long id;
    @Property
    private int category;
    @Property
    private String incomeImage;
    @Property
    private String incometypeName;
    @Property
    private String outcomeImage;
    @Property
    private String outcomeTypeName;
    @Property
    private String paymentType;
    @Property
    private String remark;
    @Property
    private Date time;
    @Property
    private float total;
    @Generated(hash = 1587165496)
    public Account(Long id, int category, String incomeImage, String incometypeName,
            String outcomeImage, String outcomeTypeName, String paymentType,
            String remark, Date time, float total) {
        this.id = id;
        this.category = category;
        this.incomeImage = incomeImage;
        this.incometypeName = incometypeName;
        this.outcomeImage = outcomeImage;
        this.outcomeTypeName = outcomeTypeName;
        this.paymentType = paymentType;
        this.remark = remark;
        this.time = time;
        this.total = total;
    }
    @Generated(hash = 882125521)
    public Account() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getCategory() {
        return this.category;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public String getIncomeImage() {
        return this.incomeImage;
    }
    public void setIncomeImage(String incomeImage) {
        this.incomeImage = incomeImage;
    }
    public String getIncometypeName() {
        return this.incometypeName;
    }
    public void setIncometypeName(String incometypeName) {
        this.incometypeName = incometypeName;
    }
    public String getOutcomeImage() {
        return this.outcomeImage;
    }
    public void setOutcomeImage(String outcomeImage) {
        this.outcomeImage = outcomeImage;
    }
    public String getOutcomeTypeName() {
        return this.outcomeTypeName;
    }
    public void setOutcomeTypeName(String outcomeTypeName) {
        this.outcomeTypeName = outcomeTypeName;
    }
    public String getPaymentType() {
        return this.paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public float getTotal() {
        return this.total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
}
