import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import model.PlayList;
import model.WebPage;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Created by Finderlo on 2016/10/4.
 */
public class DataBase {

    public static final String SQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String URL_OPEN_CONNECTION = "jdbc:mysql://localhost:3306/crawler_netease_music";
    public static final String USER_NAME = "root";
    public static final String USER_PASSWORD = "houyudong2012";

    public static final String TABLE_NAME_PLAYLIST = "playlist";
    public static final String TABLE_NAME_WEBPAGE = "webpage";

    public static final String INDEX_ID = "id";
    public static final String INDEX_NAME = "name";
    public static final String INDEX_DESCRIBE = "describe";
    public static final String INDEX_CREATOR = "creator";
    public static final String INDEX_CREATOR_HOME = "creator_home";
    public static final String INDEX_CREATE_DATE = "create_date";
    public static final String INDEX_SONG_COUNT = "song_count";
    public static final String INDEX_PLAY_COUNT = "play_count";
    public static final String INDEX_FAV_COUNT = "fav_count";
    public static final String INDEX_COMMENT_COUNT = "comment_count";
    public static final String INDEX_TITLE = "title";
    public static final String INDEX_URL = "url";
    public static final String INDEX_TYPE = "type";
    public static final String INDEX_STATUS = "status";
    public static final String INDEX_HTML = "html";

    private Connection connection = null;

    DataBase() {

        try {
            Class.forName(SQL_DRIVER_CLASS_NAME);
            connection = (Connection) DriverManager.getConnection(URL_OPEN_CONNECTION, USER_NAME, USER_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<WebPage> getWebPagesFromDataBaseByStatusAndCount(WebPage.Status status, int count) throws SQLException {
        String sql = "SELECT  * FROM webpage " +
                "WHERE status = '" + status + "' and Id LIMIT 0," + count;

        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<WebPage> webPages = WebPage.parse(resultSet);

        resultSet.close();
        statement.close();

        return webPages;
    }

    public boolean save(WebPage webPage) throws SQLException {
        if (isAlreadyExist(webPage)) {
            System.out.println("Webpage already exist , exit save(Webpage)");
            return true;
        }
        if (webPage.getId() == 0) {
            return saveWithoutId(webPage);
        } else return saveWithId(webPage);
    }

    private boolean saveWithId(WebPage webPage) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME_WEBPAGE
                + "(" + INDEX_ID + "," + INDEX_TITLE + "," + INDEX_TYPE + "," + INDEX_STATUS + "," + INDEX_URL + "," + INDEX_HTML + ") " +
                "values ('" + webPage.getId() + "','" + webPage.getTitle() + "','" + webPage.getType() + "','" + webPage.getStatus() + "','" + webPage.getUrl() + "','" + webPage.getHtml() + "')";
        Statement statement = (Statement) connection.createStatement();
        int isSuccess = statement.executeUpdate(sql);
        statement.close();
        return isSuccess == 1;
    }

    private boolean saveWithoutId(WebPage webPage) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME_WEBPAGE
                + "(" + INDEX_TITLE + "," + INDEX_TYPE + "," + INDEX_STATUS + "," + INDEX_URL + "," + INDEX_HTML + ") " +
                "values ('" + webPage.getTitle() + "','" + webPage.getType() + "','" + webPage.getStatus() + "','" + webPage.getUrl() + "','" + webPage.getHtml() + "')";
        Statement statement = (Statement) connection.createStatement();
        int isSuccess = statement.executeUpdate(sql);
        statement.close();
        return isSuccess == 1;
    }

    public boolean save(PlayList playList) throws SQLException {

        if (isAlreadyExist(playList)) {
            System.out.println("playlist already exist , exit save(playlist)");
            return true;
        }

        String sql = " INSERT INTO  playlist" +
                "(id,playlist_name,playlist_creator,playlist_creator_home,playlist_create_date,song_count,play_count,fav_count,comment_count,url) " +
                "VALUES ('" + playList.getId() + "','" + playList.getName() + "','"+ playList.getCreator() +
                "','" + playList.getCreatorHome() + "','" + playList.getCreateDate() + "','" + playList.getSongCount() + "','"
                + playList.getPlayCount() + "','" + playList.getFavCount() + "'" +
                ",'" + playList.getCommentCount() + "','" + playList.getUrl() + "')";
        System.out.println(sql);

        Statement statement = (Statement) connection.createStatement();
        int isSuccess = statement.executeUpdate(sql);
        statement.close();
        return isSuccess == 1;
    }

    public void updateStatus(WebPage webPage, WebPage.Status status) throws SQLException {
        update(webPage, INDEX_STATUS, status);
    }

    public void updateHtml(WebPage webPage, String html) throws SQLException {
        update(webPage, INDEX_HTML, html);
    }

    /**
     * TODO: 2016/10/3 更新webpage的信息，type为更新的类型，arg为更新的参数
     *
     * @param type Status or Html
     * @param arg  the content of args, Status type is cast to Webpage.Status;Html type is cast to String;
     */
    private void update(WebPage webPage, String type, Object arg) throws SQLException {
        String sqltype = "";
        String args = "";
        if (type == INDEX_STATUS) {
            sqltype = INDEX_STATUS;
            args = String.valueOf(arg);
        } else if (type == INDEX_HTML) {
            sqltype = INDEX_HTML;
            args = (String) arg;
        }
        String sql = "update  webpage set " + sqltype + " = '" + args + "' where id = " + webPage.getId();
        System.out.println(sql);
        Statement statement = (Statement) connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();

    }

    public boolean isAlreadyExist(PlayList playList) throws SQLException {
        return isAlreadyExist(TABLE_NAME_PLAYLIST, playList.getId());
    }

    public boolean isAlreadyExist(WebPage webPage) throws SQLException {
        return isAlreadyExist(TABLE_NAME_WEBPAGE, INDEX_URL, webPage.getUrl());
    }

    /**
     * @param tablename 数据库中表名
     * @return 为0返回false 即不存在返回false 如果表中已经存在返回true
     */
    private boolean isAlreadyExist(String tablename, int id) throws SQLException {
        return isAlreadyExist(tablename, INDEX_ID, String.valueOf(id));
    }

    private boolean isAlreadyExist(String tablename, String index, String args) throws SQLException {
        String sql = "select * from " + tablename + " where " + index + " = '" + args + "' limit 1";
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int count = 0;
        if (resultSet.first())
            count = 1;
        statement.close();
        resultSet.close();
        return !(count == 0);
    }
}
