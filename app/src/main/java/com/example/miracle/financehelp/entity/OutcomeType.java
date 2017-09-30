package com.example.miracle.financehelp.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Miracle on 2017/9/30 0030.
 */
@Entity
public class OutcomeType {
    @Property
    private String outcomeImage;
    @Property
    private String outcomeTypeName;
    @Generated(hash = 1040934867)
    public OutcomeType(String outcomeImage, String outcomeTypeName) {
        this.outcomeImage = outcomeImage;
        this.outcomeTypeName = outcomeTypeName;
    }
    @Generated(hash = 1008379536)
    public OutcomeType() {
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
}
