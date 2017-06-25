import model.PlayList;
import model.WebPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/10/3.
 */
public class ClawlerThread implements Runnable {

    public static final String BASE_URL = "http://music.163.com/";
    public static final String BASE_PLAYLIST_URL = "http://music.163.com/playlist?id=";

    ClawlerService clawlerService = null;

    public ClawlerThread(ClawlerService clawlerService) {
        this.clawlerService = clawlerService;
    }

    public static void main(String[] args) throws IOException {
        ClawlerThread clawlerThread = new ClawlerThread(new ClawlerService());

        WebPage webPage = new WebPage(Constant.URL_PLAY_LISTS, WebPage.PageType.playlists);
        if (clawlerThread.fetchHtml(webPage))
            System.out.println(clawlerThread.parsePlayLists(webPage));
    }

    void parse(WebPage webPage) {
        if (webPage.getType() == WebPage.PageType.playlists) {
            for (WebPage webPage1 : parsePlayLists(webPage)) {
                clawlerService.save(webPage1);
            }
        }
        if (webPage.getType() == WebPage.PageType.playlist) {
            clawlerService.save(parsePlayList(webPage));
        }
    }

    boolean fetchHtml(WebPage webPage) throws IOException {
        Connection.Response response = Jsoup.connect(webPage.getUrl()).timeout(3000).execute();
        webPage.setHtml(response.body());
        return response.statusCode() / 100 == 2;
    }

    List<WebPage> parsePlayLists(WebPage webPage) {

        List<WebPage> webPages = new ArrayList<>();

        if (webPage == null || "".equals(webPage.getHtml()) || webPage.getType() != WebPage.PageType.playlists) {
            return webPages;
        }
        Elements elements = Jsoup.parse(webPage.getHtml()).select(".tit.f-thide.s-fc0");
        for (Element element : elements) {
            String playListName = element.attr("title");
            String url = BASE_URL + element.attr("href");
            int id = Integer.parseInt(element.attr("href").split("=")[1]);
            webPages.add(new WebPage(id, url, playListName, WebPage.PageType.playlist));
        }
        return webPages;
    }

    PlayList parsePlayList(WebPage webPage) {

        if (webPage == null || webPage.getType() != WebPage.PageType.playlist || "".equals(webPage.getHtml())) {
            System.err.println("webPage 类型不对，无法解析");
            return null;
        }

        Document document = Jsoup.parse(webPage.getHtml());

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
        for(Element element:elements){
            label.append(element.html());
            label.append(" ");
        }

//        System.out.println("url" + url);
//        System.err.println("commentCount : " + document.select("#cnt_comment_count").html());

        return new PlayList(id, name, describle, creator, creatorHome, createDate, songcount, playCount, favCount, commentCount, url,label.toString());

    }

    @Override
    public void run() {

        while (true) {

            try {
                WebPage webPage = clawlerService.getTopUnCrawlerWebPage();
                try {
                    if (fetchHtml(webPage)) {
                        parse(webPage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("获取未爬取网页失败");
            }

        }

    }
}
