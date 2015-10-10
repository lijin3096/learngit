package com.example.admin.jjypznglxt.bean;

/**
 * Created by hzn on 2015/9/30.
 */
public class MedicineInfo {

    private String _id;
    private String _name;
    private int _count;
    private int _carId;
    private int _drawerId;
    private String _mbarcode;
    private String _proDate;
    private int minCount;

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_count() {
        return _count;
    }

    public void set_count(int _count) {
        this._count = _count;
    }

    public int get_carId() {
        return _carId;
    }

    public void set_carId(int _carId) {
        this._carId = _carId;
    }

    public int get_drawerId() {
        return _drawerId;
    }

    public void set_drawerId(int _drawerId) {
        this._drawerId = _drawerId;
    }

    public String get_mbarcode() {
        return _mbarcode;
    }

    public void set_mbarcode(String _mbarcode) {
        this._mbarcode = _mbarcode;
    }

    public String get_proDate() {
        return _proDate;
    }

    public void set_proDate(String _proDate) {
        this._proDate = _proDate;
    }

    @Override
    public String toString() {
        return "MedicineInfo{" +
                "_id='" + _id + '\'' +
                ", _name='" + _name + '\'' +
                ", _count='" + _count + '\'' +
                ", _carId=" + _carId +
                ", _drawerId=" + _drawerId +
                ", _mbarcode='" + _mbarcode + '\'' +
                ", _proDate='" + _proDate + '\'' +
                ", minCount='" + minCount + '\'' +
                '}';
    }
}
