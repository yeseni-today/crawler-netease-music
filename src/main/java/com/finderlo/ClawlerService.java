package com.finderlo;


/**
 * Created by Finderlo on 2016/10/4.
 */
public class ClawlerService {


    public static long _id = 0;


    public long getTopId() {
        long result = 0;
        if (Util.exceptionIds.size() > 1) {
            result = Util.exceptionIds.get(0);
            Util.exceptionIds.remove(0);
        } else {
            _id++;
            result = _id;
        }

        return result;
    }

    public long getId() {
        return _id;
    }

    public String showInfo() {
        return "当前ID数：" + _id;
    }

    public void setId(long id) {
        id = id;
    }
}
