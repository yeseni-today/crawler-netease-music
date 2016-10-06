import model.PlayList;
import model.WebPage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static model.WebPage.Status.crawled;
import static model.WebPage.Status.uncrawl;

/**
 * Created by Finderlo on 2016/10/4.
 */
public class ClawlerService {

    List<WebPage> unCrawlerWebpages = new ArrayList<>();

    DataBase dataBase = new DataBase();

    public synchronized WebPage getTopUnCrawlerWebPage() throws SQLException {

        WebPage webPage = findTopUncrawlWebPage();

        for (WebPage webpage : unCrawlerWebpages) {
            if (webpage == null) continue;
            if (webpage.getStatus() == uncrawl) {
                webpage.setStatus(crawled);
                dataBase.updateStatus(webpage, crawled);
                unCrawlerWebpages.remove(webpage);
                return webpage;
            }
        }

        return null;

    }

    private WebPage findTopUncrawlWebPage() throws SQLException {

        if (unCrawlerWebpages.size() == 0) {
            dataBase.getWebPagesFromDataBaseByStatusAndCount(WebPage.Status.uncrawl, 400).forEach(webPage -> unCrawlerWebpages.add(webPage));
        }

        for (WebPage webpage : unCrawlerWebpages) {
            if (webpage == null) continue;
            if (webpage.getStatus() == uncrawl) {
                return webpage;
            }
        }

        out.println("not find top by status");
        return null;
    }

    public void save(WebPage webPage){
        try {
            if (!dataBase.save(webPage)){
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("保存webpage失败");
            System.err.println(webPage.toString());
        }

    }

    public void save(PlayList playList){
        try {
            if (!dataBase.save(playList)){
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("playList保存失败");
            System.err.println(playList.toString());
        }
    }

    /**
     * 将一系列种子seedUrl保存到数据库
     */
    void init(String catalog) {
        // TODO: 2016/10/1 增加一个分类的所有歌单
        for (int i = 0; i < 43; i++) {
            save(new WebPage("http://music.163.com/discover/playlist/?order=hot&cat=" + catalog + "&limit=35&offset=" + (i * 35),
                    WebPage.PageType.playlists));
        }
    }

    //初始化爬虫队列
    void initAll() {
        init("全部");

        init("华语");
        init("欧美");
        init("日语");
        init("韩语");
        init("粤语");
        init("小语种");

        init("流行");
        init("摇滚");
        init("民谣");
        init("电子");
        init("舞曲");
        init("说唱");
        init("轻音乐");
        init("爵士");
        init("乡村");
        init("R&B/Soul");
        init("古典");
        init("民族");
        init("英伦");
        init("金属");
        init("朋克");
        init("蓝调");
        init("雷鬼");
        init("世界音乐");
        init("拉丁");
        init("另类/独立");
        init("New Age");
        init("古风");
        init("后摇");
        init("Bossa Nova");

        init("清晨");
        init("夜晚");
        init("学习");
        init("工作");
        init("午休");
        init("下午茶");
        init("地铁");
        init("驾车");
        init("运动");
        init("旅行");
        init("散步");
        init("酒吧");

        init("怀旧");
        init("清新");
        init("浪漫");
        init("性感");
        init("伤感");
        init("治愈");
        init("放松");
        init("孤独");
        init("感动");
        init("兴奋");
        init("快乐");
        init("安静");
        init("思念");

        init("影视原声");
        init("ACG");
        init("校园");
        init("游戏");
        init("70后");
        init("80后");
        init("90后");
        init("网络歌曲");
        init("KTV");
        init("经典");
        init("翻唱");
        init("吉他");
        init("钢琴");
        init("器乐");
        init("儿童");
        init("榜单");
        init("00后");

    }

}
