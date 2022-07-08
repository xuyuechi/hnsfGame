package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * @说明 所有元素的基类
 * @author cyx
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;
    //还有各种必要的状态值，例如：是否生存
    private boolean live=true; //true 代表存在，false代表死亡

    public ElementObj(){ //这个构造其实没有作用，只是为了继承时不报错写的
    }

    /**
     *
     * @return 元素的碰撞矩形对象（实时返回）
     */
    public Rectangle getRectangle(){
        return new Rectangle(x,y,w,h);
    }

    /**
     * @说明 碰撞方法
     * @param obj
     * @return 是否碰撞
     */
    public boolean pk(ElementObj obj){
        return this.getRectangle().intersects(obj.getRectangle());
    }

    /**
     * @说明 带参数的构造方法；可以由子类传输数据给父类
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param w w宽度
     * @param h h高度
     * @param icon 图片
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    /**
     * @说明 抽象方法，显示元素
     * @param g 画笔 用于进行绘画
     */
    public abstract void showElement(Graphics g);

    /**
     * @说明 方式1：使用父类定义接收键盘事件的方法，只有需要实现键盘监听的子类，重写这个方法（约定）
     * 方式2：使用接口的方式；使用接口方式需要在监听类进行类型转换
     * @param bl 点击的类型，true代表按下，false代表松开
     * @param key 代表触发的键盘的code值
     */
    public void keyClick(boolean bl,int key){
    }

    /**
     * @说明 移动方法：需要移动的子类，实现该方法
     */
    protected void move(){}

    //设计模式：模板模式 主角可能有的行为 1.移动 2.换装 3.子弹发射
    /**
     * 主角的行为方法
     */
    public final void model(long gameTime){
        //先换装，再移动，再发射子弹
        updateImage();
        move();
        add(gameTime);
    }

    public ElementObj createElement(String str){
        return null;
    }

    protected void updateImage(){

    }
    protected void add(long gameTime){

    }

    public void die(){

    }

    /**
     * 只要是VO类就要为属性生成get和set方法
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
