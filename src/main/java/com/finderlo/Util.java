package com.finderlo;

import java.io.*;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by finderlo on 16-12-23.
 */
public class Util {

//    public static void main(String[] args) {
//        System.out.println(File.separator);
//    }

    public static final String OUT_PATH_EXCEPTION = "/home/finderlo/music_error.log";
    public static final String OUT_PATH_NOTFOUND = "/home/finderlo/music_404.log";

    public static List<Integer> exceptionId = new LinkedList<>();
    public static List<Integer> notfoundId = new LinkedList<>();

    public static void handleException(int id) {
        //todo
        exceptionId.add(id);
    }

    public static void handleNotFound(int id) {
        notfoundId.add(id);
    }

    public static void output(){
        exceptionId.clear();
        notfoundId.clear();
        output(exceptionId,OUT_PATH_EXCEPTION);
        output(notfoundId,OUT_PATH_NOTFOUND);
    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        List<String> b = new ArrayList<>();
        b.add("e");
        b.add("f");
        b.add("g");
        output(a,OUT_PATH_EXCEPTION);
        output(b,OUT_PATH_EXCEPTION);
    }

    public  static void output(List values, String path) {
        File file = null;
        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file,true);
            writer = new BufferedWriter(fileWriter);
            for (Object value : values) {
                writer.write(value.toString());
                writer.write(",");
            }
            writer.flush();
        } catch (Exception e) {
            System.out.println(path+"日志输出失败");
            e.printStackTrace();
        }finally {
            try {
                writer.close();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println(path+"日志输出失败");
                e.printStackTrace();
            }

        }
    }
}
