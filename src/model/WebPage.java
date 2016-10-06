package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/10/3.
 */
public class WebPage {

    public static final String INDEX_ID = "id";
    public static final String INDEX_TITLE = "title";
    public static final String INDEX_URL = "url";
    public static final String INDEX_TYPE = "type";
    public static final String INDEX_STATUS = "status";
    public static final String INDEX_HTML = "html";


    public enum PageType {
        song, playlist, playlists;
    }

    public enum Status {
        crawled, uncrawl;
    }
    private int id;

    private String url;
    private String title;
    private PageType type;
    private Status status;
    private String html;

    public WebPage() {
        super();
    }

    public WebPage(String url, PageType type) {
        super();
        this.url = url;
        this.type = type;
        this.status = Status.uncrawl;
        initId();
    }

    public WebPage(int id,String url, String title,PageType type) {
        super();
        this.id = id;
        this.url = url;
        this.type = type;
        this.title = title;
        this.status = Status.uncrawl;
    }


    public WebPage(String url, PageType type, String title) {
        super();
        this.url = url;
        this.type = type;
        this.title = title;
        this.status = Status.uncrawl;
        initId();
    }

    private void initId() {
        if (type==PageType.playlists)
            return;
        id = Integer.parseInt(getUrl().split("=")[1]);
    }

    public static List<WebPage> parse(ResultSet resultSet) throws SQLException {
        List<WebPage> webPages = new ArrayList<>();
        if (resultSet==null){
            return webPages;
        }
        if (resultSet.first()){
            do {
                WebPage webPage = new WebPage();
                webPage.setId(resultSet.getInt(INDEX_ID));
                webPage.setTitle(resultSet.getString(INDEX_TITLE));
                webPage.setStatus(resultSet.getString(INDEX_STATUS));
                webPage.setType(resultSet.getString(INDEX_TYPE));
                webPage.setTitle(resultSet.getString(INDEX_TITLE));
                webPage.setUrl(resultSet.getString(INDEX_URL));
                webPage.setHtml(resultSet.getString(INDEX_HTML));
                webPages.add(webPage);
            }while (resultSet.next());
        }
        return webPages;
    }

    private void setType(String string) {
        if (string.equals("song")){
            setType(PageType.song);
        }else if (string.equals("playlists")){
            setType(PageType.playlists);
        }else {
            setType(PageType.playlist);
        }
    }

    private void setStatus(String string) {
        if (string.equals("crawled")){
            setStatus(Status.crawled);
        }else {
            setStatus(Status.uncrawl);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WebPage [title= " + title + ", status=" + status + ", title : " + title + ",type : " + type + ", url=" + url + "]";
    }
}
