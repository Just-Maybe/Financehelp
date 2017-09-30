package com.example.miracle.financehelp.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Miracle on 2017/9/30 0030.
 */
@Entity
public class IncomeType {
    @Property
    private String incomeImage;
    @Property
    private String incometypeName;
    @Generated(hash = 1069230924)
    public IncomeType(String incomeImage, String incometypeName) {
        this.incomeImage = incomeImage;
        this.incometypeName = incometypeName;
    }
    @Generated(hash = 1875397412)
    public IncomeType() {
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
}
