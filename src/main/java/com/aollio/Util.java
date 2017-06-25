package com.finderlo;

import com.finderlo.model.PlayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static com.finderlo.Constant.BASE_PLAYLIST_URL;
import static com.finderlo.Constant.BASE_URL;

/**
 * Created by finderlo on 16-12-23.
 */
public final class Util {

    /**
     * 进度信息保存路径
     */
    public static final String OUT_PATH = "/home/finderlo/clawler-music.properties";

    public static List<Long> exceptionIds = new LinkedList<>();
    public static long notFundCount = 0;

    /**
     * 处理出异常的ID，保留继续重试
     */
    public static void handleException(long id) {
        //todo
        exceptionIds.add(id);
    }

    /**
     * 处理404页面
     */
    public static void handleNotFound(long id) {
        notFundCount++;
    }


    /**
     * 读取并设置上一次爬取到的进度信息
     */
    public static void readAndConfig(Application application) {

        Properties properties = new Properties();
        File file = null;
        try {
            file = new File(OUT_PATH);
            if (!file.exists()) {
                //如果配置文件不存在，则直接返回
                return;
            }
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
            config(application,
                    Long.valueOf(properties.getProperty("id")),
                    properties.getProperty("exception-id"),
                    Long.valueOf(properties.getProperty("404")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置信息
     */
    public static void config(Application application, long id, String exceptionId, long count404) {
        application.getService().setId(id);
        notFundCount = count404;

        for (String s : exceptionId.split(",")) {
            try {
                long exce = Long.valueOf(s);
                exceptionIds.add(exce);
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 保存进度信息
     */
    public static void saveConfig(Application application) {
        File file = null;
        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        try {
            file = new File(OUT_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, false);
            writer = new BufferedWriter(fileWriter);
            //将本次进行到的进度保持起来
            //保存ID数
            String id = "id=" + application.getService().getId() + "\n";
            //保持异常数
            StringBuilder exception = new StringBuilder();
            exception.append("exception-id=");
            exceptionIds.forEach(e -> exception.append(e).append(","));
            exception.append("\n");
            //保存404数
            String not = "404=" + notFundCount + "\n";
            writer.write(id);
            writer.write(exception.toString());
            writer.write(not);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 解析文档到歌单
     */
    public static PlayList parse(Document document) {

        int id = Integer.parseInt(document.select("a.u-btni.u-btni-share").attr("data-res-_id")); //歌单的ID

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


        return new PlayList(id, name, describle, creator, creatorHome, createDate, songcount, playCount, favCount, commentCount, url, label.toString());

    }

    /**
     * true right
     * false wrong
     */
    public static boolean rightPlayList(Document document) {
        return document.select("p.note.s-fc3").size() == 0;
    }
}
