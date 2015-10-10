package com.example.admin.jjypznglxt.pro;

/**
 * Created by admin on 2015/9/25.
 */
public class iformaiton {
    private String drug;
    private String descip;

    public iformaiton() {
    }

    public iformaiton(String drug, String descip) {
        this.drug = drug;
        this.descip = descip;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDescip() {
        return descip;
    }

    public void setDescip(String descip) {
        this.descip = descip;
    }
}
