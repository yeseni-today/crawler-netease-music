package com.finderlo;


import com.finderlo.model.PlayList;

/**
 * Created by Finderlo on 2016/10/4.
 */
public class ClawlerService {

    PlayListDao playListDao = new PlayListDao();

    public static int id = 10532452;


    public void save(PlayList playList) {
        playListDao.saveOrUpdate(playList);
    }


    public synchronized int getId() {
        System.out.println("当前扫描数：" + id + ",成功数：" + (id - Util.notfoundId.size() - Util.exceptionId.size()) + ",失败数：" + Util.exceptionId.size() + ",404数:" + Util.notfoundId.size());
        id++;
        return id;
    }
}
