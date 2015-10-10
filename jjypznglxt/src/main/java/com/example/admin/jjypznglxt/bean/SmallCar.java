package com.example.admin.jjypznglxt.bean;

/**
 * Created by hzn on 2015/10/8.
 */
public class SmallCar {

    int id;
    String barcode;
    String num;
    String egyname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEgyname() {
        return egyname;
    }

    public void setEgyname(String egyname) {
        this.egyname = egyname;
    }

    @Override
    public String toString() {
        return "SmallCar{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", num='" + num + '\'' +
                ", egyname='" + egyname + '\'' +
                '}';
    }
}
