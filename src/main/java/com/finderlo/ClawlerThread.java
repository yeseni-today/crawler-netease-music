package com.finderlo;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static com.finderlo.Constant.BASE_PLAYLIST_URL;

/**
 * Created by Finderlo on 2016/10/3.
 * ClawlerThread 爬虫具体线程，每爬取到一个网页则向处理器（HandlerManager）递交这个网页
 */
public class ClawlerThread implements Runnable {

    private Application application;
    private boolean stop = false;

    public ClawlerThread(Application application) {
        this.application = application;
    }


    Document fetchHtml(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url).timeout(3000).execute();
        if (response.statusCode() / 100 != 2) {
            return null;
        }
        Document document = Jsoup.parse(response.body());
        return document;
    }


    @Override
    public void run() {
        long id = 0;
        try {
            while (!stop) {
                //获取service的同步锁，保证获取的ID不会被其他线程再次获取
                synchronized (application.getService()) {
                    if (application.isPause()) {
                        //如果应用状态为暂停，则暂停线程
                        application.getService().wait();
                    }
                    //如果不为暂停，则获取ID，释放锁
                    id = application.getService().getTopId();

                }
                if (id > 554016845) {
                    setStop(true);
                    continue;
                }
                application.getHandlerManager().submit(fetchHtml(BASE_PLAYLIST_URL + id));

            }
        } catch (IOException e) {
            e.printStackTrace();
            Util.handleException(id);
            run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }
}
