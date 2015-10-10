package com.example.admin.jjypznglxt.bean;

/**
 * Created by hzn on 2015/9/30.
 */
public class MedicineUsage {

    private int _id;
    private String _mecId;
    private int _drawerId;
    private int _position;
    private String _usage;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_mecId() {
        return _mecId;
    }

    public void set_mecId(String _mecId) {
        this._mecId = _mecId;
    }

    public int get_drawerId() {
        return _drawerId;
    }

    public void set_drawerId(int _drawerId) {
        this._drawerId = _drawerId;
    }

    public int get_position() {
        return _position;
    }

    public void set_position(int _position) {
        this._position = _position;
    }

    public String get_usage() {
        return _usage;
    }

    public void set_usage(String _usage) {
        this._usage = _usage;
    }


    @Override
    public String toString() {
        return "MedicineUsage{" +
                "_id=" + _id +
                ", _mecId='" + _mecId + '\'' +
                ", _drawerId=" + _drawerId +
                ", _position=" + _position +
                ", _usage='" + _usage + '\'' +
                '}';
    }
}
