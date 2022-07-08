package com.tedu.show;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

/**
 * @说明 游戏窗体主要实现的功能：显示，关闭，最大最小化
 * @author cyx
 * @功能说明 需要嵌入面板，启动主线程等等
 * @窗体说明 swing awt 窗体大小（记录用户上次使用软件的窗体样式）
 *
 * @分析 1.面板绑定到窗体 2.监听绑定 3.游戏主线程启动 4.显示窗体
 */
public class GameJFrame extends JFrame {
    public static int GameX = 775;//GameX
    public static int GameY = 615;
    private JPanel jPanel = null; //正在显示的面板
    private KeyListener keyListener=null; //键盘监听
    private MouseMotionListener mouseMotionListener = null; //鼠标监听
    private MouseListener mouseListener=null;
    private Thread thread = null; //游戏主线程

    public GameJFrame(){
        init();
    }
    public void init(){
        this.setSize(GameX,GameY);
        this.setTitle("测试游戏-泡泡堂");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并关闭
        this.setLocationRelativeTo(null);//屏幕居中显示
    }
    /*窗体布局：可以存档，读档。button 给大家拓展的*/
    public void addButton(){
//        this.setLayout(manager); //布局格式，可以添加控件
    }

    /**
     * 启动方法
     */
    public void start(){
        if(jPanel != null){
            this.add(jPanel);
        }
        if(keyListener != null){
            this.addKeyListener(keyListener);
        }
        if(thread != null){
            thread.start();//启动主线程
        }
        this.setVisible(true);//显示界面
        //界面的刷新
        if(this.jPanel instanceof Runnable){
            new Thread((Runnable) this.jPanel).start();
        }

    }

    public void setjPanel(JPanel jPanel){
        this.jPanel = jPanel;
    }

    public JPanel getjPanel(){
        return this.jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
