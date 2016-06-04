package com.example.acer.zzia_mxbt.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * Created by acer on 2016/5/18.
 */
public class MyApplication extends Application {

    //获取续写内容
    private static String Url_WriteArticle="http://10.201.1.183:8080/ZZIA_MXBT/write_Content";
    //获取文章内容

    private static String Url_Article = "http://10.201.1.183:8080/ZZIA_MXBT/article_complete";
    /*private String registUrl="http://10.201.1.170:80/MXBT_Register/checkServlet";
    private String loginUrl="http://10.201.1.170:80/MXBT_Register/loginCheckServlet";*/
    //注册
    private String registUrl = "http://10.201.1.183:8080/ZZIA_MXBT/registCheckServlet";

    //登录
    private String loginUrl = "http://10.201.1.183:8080/ZZIA_MXBT/loginCheckServlet";
    //显示专题封面
    private String showSubjectArticleUrl = "http://10.201.1.183:80/ZZIA_MXBT/showSubjectArticleServlet";

    private String showSubjectUrl = "http://10.201.1.183:80/ZZIA_MXBT/showSubjectServlet";

    //排行榜
    private String author_url = "http://10.201.1.183:8080/ZZIA_MXBT/user_servlet";
    private String novel_url = "http://10.201.1.183:8080/ZZIA_MXBT/novel_servlet";
    //投票
    private String vote_url = "http://10.201.1.183:8080/ZZIA_MXBT/vote_servlet";


    //个人中心
    //我的故事
    private static  String mystory_url="http://10.201.1.183:8080/ZZIA_MXBT/mystory";
    //我的收藏
    private static  String mycollect_url="http://10.201.1.183:8080/ZZIA_MXBT/mycollect";


    //我的推荐
    private static  String myrecommand_url="http://10.201.1.183:8080/ZZIA_MXBT/myrecommand";


    public static String getMycollect_url() {
        return mycollect_url;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    public static String getUrl_WriteArticle() {
        return Url_WriteArticle;
    }
    public static String getUrl_Article() {
        return Url_Article;
    }

    public String getRegistUrl() {
        return registUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public String getNovel_url() {
        return novel_url;
    }

    public String getVote_url() {
        return vote_url;
    }

    public String getShowSubjectUrl() {
        return showSubjectUrl;
    }

    public String getShowSubjectArticleUrl() {
        return showSubjectArticleUrl;
    }
    public static String getMystory_url() {
        return mystory_url;
    }

    public static String getMyrecommand_url() {
        return myrecommand_url;
    }

}
