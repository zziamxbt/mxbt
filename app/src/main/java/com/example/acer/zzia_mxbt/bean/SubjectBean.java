package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

/**
 * Created by qiyu on 2016/5/30.
 */
public class SubjectBean implements Serializable {
    /**
     * 对应subject表和image表
     */
    private static final long serialVersionUID = 1L;
    private int SIid;// 专题图片id
    private String path;//图片id对应的地址
    private String Scontent;//专题内容

    public SubjectBean(){

    }


    public SubjectBean(int sIid, String path, String scontent) {
        super();
        SIid = sIid;
        this.path = path;
        Scontent = scontent;
    }





    public int getSIid() {
        return SIid;
    }

    public void setSIid(int sIid) {
        SIid = sIid;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public String getScontent() {
        return Scontent;
    }


    public void setScontent(String scontent) {
        Scontent = scontent;
    }


    @Override
    public String toString() {
        return "SubjectBean [SIid=" + SIid + ", path=" + path + ", Scontent="
                + Scontent + "]";
    }



}
