package model;

/**
 * Created by Finderlo on 2016/10/3.
 */
public class PlayList {


    private int id;
    private String name;    //歌单的名称

    private String describe;    //歌单的描述
    private String creator;    //歌单的作者
    private String creatorHome;

    private String createDate;     //歌单的创建时间

    private int songCount;  //这个歌单中歌曲的数量
    private int playCount;   // 歌单中播放的次数
    private int favCount;    //歌单中的收藏数
    private int commentCount;    // 歌单的评论数

    private String url;

    private String label;

    public PlayList(int id, String name, String url) {
        this(id, name, "", "", "","", 0, 0, 0, 0,url,"");
    }

    public PlayList(int id, String name,
                    String describe, String creator,String creatorHome, String createDate,
                    int songCount, int playCount, int favCount, int commentCount,String url,String label) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.creator = creator;
        this.creatorHome = creatorHome;
        this.createDate = createDate;
        this.songCount = songCount;
        this.playCount = playCount;
        this.favCount = favCount;
        this.commentCount = commentCount;
        this.url = url;
        this.label = label;
    }

    @Override
    public String toString() {
        return "PlayList : [ id : " + id + ", name : " + name + ", creator : " + creator +
                ", createDate : " + createDate + ", songCount: " + songCount +
                ", playCount : " + playCount + ", favCount : " + favCount +
                ", commentCount : " + commentCount + ", describe : " + describe + "。]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
