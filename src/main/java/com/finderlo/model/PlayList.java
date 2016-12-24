package com.finderlo.model;

/**
 * Created by Finderlo on 2016/10/3.
 */


import com.google.gson.Gson;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 CREATE TABLE `playlist` (
 `id` int(11) NOT NULL,
 `name` varchar(255) DEFAULT NULL,
 `describe` text,
 `creator` varchar(255) DEFAULT NULL,
 `creatorHome` varchar(255) DEFAULT NULL,
 `createDate` varchar(255) DEFAULT NULL,
 `songCount` int(11) DEFAULT NULL,
 `playCount` int(11) DEFAULT NULL,
 `favCount` varchar(255) DEFAULT NULL,
 `commentCount` varchar(255) DEFAULT NULL,
 `url` varchar(255) DEFAULT NULL,
 `label` varchar(255) DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Entity
@Table(name = "playList")
public class PlayList {
    @Id
    private int table_id;//歌单的id
    private String play_name;    //歌单的名称

    private String play_describe;    //歌单的描述
    private String play_creator;    //歌单的作者
    private String creatorHome;

    private String createDate;     //歌单的创建时间

    private int songCount;  //这个歌单中歌曲的数量
    private int playCount;   // 歌单中播放的次数
    private int favCount;    //歌单中的收藏数
    private int commentCount;    // 歌单的评论数

    private String url;
    private String play_label;

    public PlayList(){}

    public PlayList(int id, String name, String url) {
        this(id, name, "", "", "","", 0, 0, 0, 0,url,"");
    }

    public PlayList(int id, String name,
                    String describe, String creator,String creatorHome, String createDate,
                    int songCount, int playCount, int favCount, int commentCount,String url,String label) {
        this.table_id = id;
        this.play_name = name;
        this.play_describe = describe;
        this.play_creator = creator;
        this.creatorHome = creatorHome;
        this.createDate = createDate;
        this.songCount = songCount;
        this.playCount = playCount;
        this.favCount = favCount;
        this.commentCount = commentCount;
        this.url = url;
        this.play_label = label;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreatorHome() {
        return creatorHome;
    }

    public void setCreatorHome(String creatorHome) {
        this.creatorHome = creatorHome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getPlay_describe() {
        return play_describe;
    }

    public void setPlay_describe(String play_describe) {
        this.play_describe = play_describe;
    }

    public String getPlay_creator() {
        return play_creator;
    }

    public void setPlay_creator(String play_creator) {
        this.play_creator = play_creator;
    }
}
