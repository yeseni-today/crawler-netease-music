package com.finderlo;

/**
 * Created by Finderlo on 2016/9/30.
 */
public interface Constant {
    // TODO: 2016/9/30 这是全部歌单的URL
    String URL_PLAY_LISTS = "http://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=0";

    // TODO: 2016/9/30 这是一个歌单的URL
    String URL_PLAY_LIST = "http://music.163.com/playlist?id=454016843";

    String URL_NOTFOUNF = "http://music.163.com/playlist?id=2";

    String URL_SONG = "http://music.163.com/song?id=29999506";


    String testplaylist = "http://music.163.com/playlist?id=466183353";

    String user = "http://music.163.com/user/home?id=306811889";

    public static final String BASE_URL = "http://music.163.com/";
    public static final String BASE_PLAYLIST_URL = "http://music.163.com/playlist?id=";
    public static final int DEFAULT_THREAD_COUNT = 40;
}
