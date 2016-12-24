package com.finderlo;

public class Application {

    public static void main(String[] args) {
        ClawlerService service = new ClawlerService();
        for (int i = 0; i < 40; i++) {
            new Thread(new ClawlerThread(service)).start();
        }
    }
}