import model.PlayList;
import model.WebPage;

import java.io.IOException;

/**
 * Created by Finderlo on 2016/10/6.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        ClawlerThread clawlerThread = new ClawlerThread(new ClawlerService());
        WebPage webPage = new WebPage("http://music.163.com/playlist?id=474170586", WebPage.PageType.playlist);
        PlayList playList = null;
        if (clawlerThread.fetchHtml(webPage)){
           playList =  clawlerThread.parsePlayList(webPage);
        }
        System.out.println(playList.getLabel());
    }
}
