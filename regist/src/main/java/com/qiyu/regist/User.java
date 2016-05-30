package com.qiyu.regist;

import java.io.Serializable;

/**
 * Created by qiyu on 2016/5/23.
 * 对应数据库的用户表user
 */
public class User implements Serializable {
    private int Uid;//主键
    private int Uhead;//头像
    private int Ubk;//背景图片
    private String Uname;//帐号
    private String Unickname;//昵称
    private String Usex;//性别
    private String Ucountry;//国家
    private String Usign;//个性签名
    private String Upassword;//密码

    public User(){
    }

    public User(String uname, String upassword) {
        Uname = uname;
        Upassword = upassword;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public int getUhead() {
        return Uhead;
    }

    public void setUhead(int uhead) {
        Uhead = uhead;
    }

    public int getUbk() {
        return Ubk;
    }

    public void setUbk(int ubk) {
        Ubk = ubk;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUnickname() {
        return Unickname;
    }

    public void setUnickname(String unickname) {
        Unickname = unickname;
    }

    public String getUsex() {
        return Usex;
    }

    public void setUsex(String usex) {
        Usex = usex;
    }

    public String getUcountry() {
        return Ucountry;
    }

    public void setUcountry(String ucountry) {
        Ucountry = ucountry;
    }

    public String getUsign() {
        return Usign;
    }

    public void setUsign(String usign) {
        Usign = usign;
    }

    public String getUpassword() {
        return Upassword;
    }

    public void setUpassword(String upassword) {
        Upassword = upassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "Uid=" + Uid +
                ", Uhead=" + Uhead +
                ", Ubk=" + Ubk +
                ", Uname='" + Uname + '\'' +
                ", Unickname='" + Unickname + '\'' +
                ", Usex='" + Usex + '\'' +
                ", Ucountry='" + Ucountry + '\'' +
                ", Usign='" + Usign + '\'' +
                ", Upassword='" + Upassword + '\'' +
                '}';
    }
}
