package com.tedu.element;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Board extends ElementObj{

    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Board(){
        this.setX(347);
        this.setY(0);
        this.setH(25);
        this.setW(80);
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.getX(),this.getY(),this.getW(),this.getH());
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.PLAIN,20));
        g.drawString("得分："+points,350,20);
    }
}
