package com.finderlo;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private ClawlerService _service;
    private HandlerManager _handlerManager;
    private PlayListDao _playListDao;

    private List<ClawlerThread> _clawlerThreads = new ArrayList<>();
    private Operation _operation;

    private boolean pause = false;

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }

    private Application() {
        initialize();
    }

    //初始化成员变量
    private void initialize() {
        _service = new ClawlerService();

        _playListDao = new PlayListDao();
        for (int i = 0; i < Constant.DEFAULT_THREAD_COUNT; i++) {
            ClawlerThread thread = new ClawlerThread(this);
            _clawlerThreads.add(thread);
        }
        _operation = new Operation(this);
        //hadnlerManage初始化 需要放置在线程初始化之后
        _handlerManager = new HandlerManager(this);

        Util.readAndConfig(this);
        System.out.println("初始化完成");
    }

    //启动爬虫程序
    public void start() {

        //先启动handler
        _handlerManager.getHandlers().forEach(runnable -> new Thread(runnable).start());
        System.out.println("handler count:"+_handlerManager.getHandlers().size());
        //启动线程
        _clawlerThreads.forEach(runnable -> new Thread(runnable).start());
        System.out.println("thread count:"+_clawlerThreads.size());
        //启动用户操作
        _operation.operation();
    }


    public ClawlerService getService() {
        return _service;
    }

    public HandlerManager getHandlerManager() {
        return _handlerManager;
    }

    public PlayListDao getPlayListDao() {
        return _playListDao;
    }

    public int getThreadCount() {
        return _clawlerThreads.size();
    }

    public boolean isPause() {
        return pause;
    }

    /**
     * 程序设置暂停，停止正在运行的线程
     */
    public void setPause(boolean pause) {
        this.pause = pause;
        if (pause) {
            //如果是暂停，需要获取对象锁，使线程暂停。
        } else {
            //如果是继续，需要释放对象锁，使线程继续运行
            synchronized (getService()) {
                getService().notifyAll();
            }
        }
    }


    public void exit() {
        //先暂停
        setPause(true);
        //销毁这个线程
        _clawlerThreads.forEach(e -> {
            e.setStop(true);
        });
        //保存进度信息
        Util.saveConfig(this);
        //关闭程序
        System.exit(0);
    }
}