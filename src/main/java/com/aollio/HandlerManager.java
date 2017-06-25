package com.finderlo;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by finderlo on 16-12-31.
 */
public class HandlerManager  {

    private Application _application;

    private List<Handler> _handlers = new ArrayList<>();

    public HandlerManager(Application application) {
        this._application = application;
        int handleCount = application.getThreadCount() >> 1;
        if (2 * handleCount < application.getThreadCount()) {
            handleCount++;
        }
        for (int i = 0; i < handleCount; i++) {
            Handler handler = new Handler(application);
            _handlers.add(handler);
        }
    }



    Random _random = new Random();

    public void submit(Document document) {
       _handlers.get(_random.nextInt(_handlers.size())).submit(document);
    }

    public List<Handler> getHandlers(){
        return _handlers;
    }
}
