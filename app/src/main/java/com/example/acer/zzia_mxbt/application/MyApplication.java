package com.example.acer.zzia_mxbt.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * Created by acer on 2016/5/18.
 */
public class MyApplication extends Application {
    //获取钱包信息
    private String Url_Wallet="http://10.201.1.183:8080/ZZIA_MXBT/wallet_servlet";
    //获取续写内容
    private static String Url_WriteArticle="http://10.201.1.183:8080/ZZIA_MXBT/write_Content";
    //获取文章内容

<<<<<<< HEAD
    private static String Url_Article = "http://10.201.1.183:8080/ZZIA_MXBT/article_complete";
=======
    private static String Url_Article = "http://10.201.1.170:80/ZZIA_MXBT/article_complete";
>>>>>>> 62bbc80fe493bf3af2f9a942637ed722aaa330bf
    /*private String registUrl="http://10.201.1.170:80/MXBT_Register/checkServlet";
    private String loginUrl="http://10.201.1.170:80/MXBT_Register/loginCheckServlet";*/
    //注册
    private String registUrl = "http://10.201.1.183:8080/ZZIA_MXBT/registCheckServlet";

    //登录
<<<<<<< HEAD
    private String loginUrl = "http://10.201.1.183:8080/ZZIA_MXBT/loginCheckServlet";
=======
    private String loginUrl = "http://10.201.1.115:8080/ZZIA_MXBT/loginCheckServlet";
<<<<<<< HEAD

    //显示搜素版块
    private String searchThemeUrl = "http://10.201.1.170:80/ZZIA_MXBT/searchServlet";

    //显示专题封面
    private String showLableUrl = "http://10.201.1.170:80/ZZIA_MXBT/showLableServlet";

=======
>>>>>>> 62bbc80fe493bf3af2f9a942637ed722aaa330bf
    //显示专题封面

    private String showSubjectUrl = "http://10.201.1.183:80/ZZIA_MXBT/showSubjectServlet";
<<<<<<< HEAD
=======
=======


    private String showSubjectUrl = "http://10.201.1.115:80/ZZIA_MXBT/showSubjectServlet";
>>>>>>> 2543310f901c162d458b167dda87db1f3196a7dc
>>>>>>> 62bbc80fe493bf3af2f9a942637ed722aaa330bf
    //显示专题对应文章
    private String showSubjectArticleUrl = "http://10.201.1.183:80/ZZIA_MXBT/showSubjectArticleServlet";


<<<<<<< HEAD
=======
<<<<<<< HEAD
    //显示标签封面
    private String showSubjectUrl = "http://10.201.1.170:80/ZZIA_MXBT/showSubjectServlet";
    //显示标签对应文章
    private String showLableArticleUrl = "http://10.201.1.170:80/ZZIA_MXBT/showLableArticleServlet";


=======
>>>>>>> ee39f3392c57094014cb703193776a99a327c2c7
>>>>>>> 2543310f901c162d458b167dda87db1f3196a7dc
>>>>>>> 62bbc80fe493bf3af2f9a942637ed722aaa330bf

    //排行榜
    private String author_url = "http://10.201.1.183:8080/ZZIA_MXBT/user_servlet";
    private String novel_url = "http://10.201.1.183:8080/ZZIA_MXBT/novel_servlet";
    //投票
    private String vote_url = "http://10.201.1.183:8080/ZZIA_MXBT/vote_servlet";
    //续写插入
    private String andwrite_url="http://10.201.1.183:8080/ZZIA_MXBT/AndWrite_InsertServlet";
    //评论查询，插入
    private String article_comment_url="http://10.201.1.183:8080/ZZIA_MXBT/ArticeComment_Servlet";
   private String andwrite_comment_url="http://10.201.1.183:8080/ZZIA_MXBT/AndWriteComment_Servlet";

    //我的故事
    private static  String mystory_url="http://10.201.1.183:8080/ZZIA_MXBT/mystory";
    //我的收藏
    private static  String mycollect_url="http://10.201.1.183:8080/ZZIA_MXBT/mycollect";
    //我的推荐
    private static  String myrecommand_url="http://10.201.1.183:8080/ZZIA_MXBT/myrecommand";
    //个人中心
    private static  String center_url="http://10.201.1.183:8080/ZZIA_MXBT/index_servlet";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    public String getUrl_Wallet() {
        return Url_Wallet;
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

    public String getAndwrite_url(){
        return andwrite_url;
    }
    public String getArticle_comment_url(){return article_comment_url;}
    public String getAndwrite_comment_url(){return andwrite_comment_url;}


    public String getShowSubjectArticleUrl() {
        return showSubjectArticleUrl;
    }

    public static String getMystory_url() {
        return mystory_url;
    }

    public static String getMyrecommand_url() {
        return myrecommand_url;
    }

    public static String getMycollect_url() {
        return mycollect_url;
    }

    public static String getCenter_url() {
        return center_url;
    }

    public String getSearchThemeUrl() {
        return searchThemeUrl;
    }

<<<<<<< HEAD

=======
<<<<<<< HEAD
    public String getShowLableUrl() {
        return showLableUrl;
    }

    public String getShowLableArticleUrl() {
        return showLableArticleUrl;
    }
=======
>>>>>>> ee39f3392c57094014cb703193776a99a327c2c7
>>>>>>> 42c3d4d3b7d314f110699972472a68eb9d124c62
>>>>>>> 2543310f901c162d458b167dda87db1f3196a7dc
>>>>>>> 62bbc80fe493bf3af2f9a942637ed722aaa330bf
}




