package com.bsep.admin.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public class GeneralSubtreeElement {
    @NotEmpty(message = "The obj cannot be empty")
    private String obj;
    @NotNull(message = "The tag cannot be null")
    private int tag;
    @NotEmpty(message = "The minimum cannot be null")
    private BigInteger minimin;
    @NotEmpty(message = "The maximum cannot be null")
    private BigInteger maximum;

    public GeneralSubtreeElement(String obj, int tag, BigInteger minimin, BigInteger maximum) {
        this.obj = obj;
        this.tag = tag;
        this.minimin = minimin;
        this.maximum = maximum;
    }

    public GeneralSubtreeElement() {

    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public BigInteger getMinimin() {
        return minimin;
    }

    public void setMinimin(BigInteger minimin) {
        this.minimin = minimin;
    }

    public BigInteger getMaximum() {
        return maximum;
    }

    public void setMaximum(BigInteger maximum) {
        this.maximum = maximum;
    }
}
