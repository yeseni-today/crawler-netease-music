package com.finderlo;

import com.finderlo.model.PlayList;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by finderlo on 16-12-30.
 */
public class Handler implements Runnable {

    private List<Document> documents = new ArrayList<>();

    private Application application;

    public Handler(Application application){
        this.application = application;
    }

    @Override
    public void run() {
        System.out.println("hadnlers"+Thread.currentThread().getName()+"开始启动");
        while (true){
            if (documents.size()==0){
//                System.out.println("list=0");
                continue;
            }
            Document document = documents.remove(0);
            if (document==null){
                //无法解析文档时，继续
//                System.out.println("list-item=null");
                continue;
            }
            if (!Util.rightPlayList(document)){
                //404页面时，继续
                Util.notFundCount++;
                continue;
            }
            PlayList playList = Util.parse(document);
            if (playList.getSongCount()==0){
                continue;
            }
            System.out.println("保存歌单");
            application.getPlayListDao().saveOrUpdate(playList);
        }

    }

    public void submit(Document document){
        documents.add(document);
    }
}
