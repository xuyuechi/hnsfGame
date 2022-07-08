package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
    /**
     * 程序的唯一入口
     */

    public static GameJFrame gj;
    public static void main(String[] args) {
        gj = new GameJFrame();

        /*
         * 实例化面板，注入到jFrame中
         */
        GameMainJPanel jPanel = new GameMainJPanel();
        gj.setjPanel(jPanel);

        //实例化监听，并注入
        GameListener listener = new GameListener();
        gj.setKeyListener(listener);

        //实例化主线程，注入
        GameThread th = new GameThread();
        gj.setThread(th);
        gj.start();
    }
}
