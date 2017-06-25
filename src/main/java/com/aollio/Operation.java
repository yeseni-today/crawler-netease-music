package com.finderlo;

import java.util.Scanner;

/**
 * Created by finderlo on 16-12-31.
 * 这个类用于读取用户的额输入信息，并进行显示相对应的数据
 */
public class Operation {

    public static final String CMD_PAUSE = "pause";
    public static final String CMD_INFORMATION = "info";
    public static final String CMD_RESUME = "resume";
    public static final String CMD_HELP = "help";
    public static final String CMD_EXIT = "exit";

    private Application _application;

    public Operation(Application application) {
        this._application = application;
    }

    public void operation() {
        showHelp();
        showInfo();
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (true) {
            command = scanner.next();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        switch (command.trim()) {
            case CMD_PAUSE:
                _application.setPause(true);
                break;
            case CMD_RESUME:
                _application.setPause(false);
                break;
            case CMD_HELP:
                showHelp();
                break;
            case CMD_INFORMATION:
                showInfo();
                break;
            case CMD_EXIT:
                _application.exit();
                break;
        }
    }

    private void showInfo() {
        String builder = _application.getService().showInfo() + "  " +
                "异常ID数：" + Util.exceptionIds.size() + "  " +
                "404数：" + Util.notFundCount+"  ";
        println(builder);
    }

    private void showHelp() {
        String builder = "暂停: " + "pause" + "  " +
                "继续：" + "resume" + "  " +
                "退出：" + "exit" + "  " +
                "信息：" + "info" + "  " +
                "显示帮助：" + "help" + "   ";
        println(builder);
    }

    private void println(String s) {
        System.out.println(s);
    }
}
