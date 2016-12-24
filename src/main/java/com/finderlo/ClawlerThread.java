package com.finderlo;


import com.finderlo.model.PlayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Finderlo on 2016/10/3.
 */
public class ClawlerThread implements Runnable {

    public static final String BASE_URL = "http://music.163.com/";
    public static final String BASE_PLAYLIST_URL = "http://music.163.com/playlist?id=";

    ClawlerService clawlerService;

    public ClawlerThread(ClawlerService clawlerService) {
        this.clawlerService = clawlerService;
    }


    private void parse(int id) throws IOException {
        String url = BASE_PLAYLIST_URL + id;
        Document document = fetchHtml(url);
        //网络出错，报告
        if (document == null) {
            Util.handleException(id);
            return;
        }
        //404，存入值
        if (!rightPlayList(document)) {
            Util.handleNotFound(id);
            return;
        }

        clawlerService.save(parsePlayList(document));
    }


    Document fetchHtml(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url).timeout(3000).execute();
        if (response.statusCode() / 100 != 2) {
            return null;
        }
        Document document = Jsoup.parse(response.body());
        return document;
    }

    /**
     * true right
     * false wrong
     */
    boolean rightPlayList(Document document) {
        return document.select("p.note.s-fc3").size() == 0;
    }


    PlayList parsePlayList(Document document) {


        int id = Integer.parseInt(document.select("a.u-btni.u-btni-share").attr("data-res-id")); //歌单的ID

        int songcount = Integer.parseInt(document.select("#playlist-track-count").html());//这个歌单中歌曲的数量
        int playCount = Integer.parseInt(document.select("#play-count").html());//歌单中播放的次数
        int favCount = Integer.parseInt(document.select("a.u-btni.u-btni-fav ").attr("data-count"));//歌单中的收藏数

        String commentCountString = document.select("#cnt_comment_count").html();//歌单的评论数
        int commentCount = 0;

        if (commentCountString.equals("评论")) {
            commentCount = 0;
        } else {
            commentCount = Integer.parseInt(commentCountString);
        }

        String creator = document.select("a.u-btni.u-btni-share ").attr("data-res-author");//歌单的作者
        String name = document.select("a.u-btni.u-btni-share ").attr("data-res-name");//歌单的名称
        String describle = document.select("p.intr.f-brk.f-hide").html();//歌单的描述
        String createDate = document.select("span.time.s-fc4").html().split("&")[0];//歌单的创建时间
        String creatorHome = BASE_URL + document.select("a.s-fc7").attr("href");//歌单创建者的主页
        String url = BASE_PLAYLIST_URL + id;//歌单的url

        StringBuilder label = new StringBuilder();
        //查找歌单的标签
        Elements elements = document.select("a.u-tag > i");
        for (Element element : elements) {
            label.append(element.html());
            label.append(" ");
        }

//        System.out.println("url" + url);
//        System.err.println("commentCount : " + document.select("#cnt_comment_count").html());

        return new PlayList(id, name, describle, creator, creatorHome, createDate, songcount, playCount, favCount, commentCount, url, label.toString());

    }

    @Override
    public void run() {
        boolean flag = true;
        int id = 0;
        while (flag) {
            try {

                id = clawlerService.getId();
                if (id>554016845){
                    flag = false;
                    continue;
                }
                parse(id);

            } catch (IOException e) {
                e.printStackTrace();
                Util.handleException(id);
            }
        }
        Util.output();
    }

}
