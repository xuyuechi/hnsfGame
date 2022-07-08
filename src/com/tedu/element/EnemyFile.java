package com.tedu.element;

import java.awt.*;

public class EnemyFile extends ElementObj{
    private int attack = 1;
    private int moveNum = 8;
    private String fx;

    /*
    private PlayFile(int x, int y, int w, int h, ImageIcon icon,String fx) {
        super(x, y, w, h, icon);
        this.attack=1;
        this.moveNum=3;
        this.fx = fx;
    }
     */

    public EnemyFile(){}
    @Override
    public ElementObj createElement(String str){
        String[] split = str.split(",");
        for(String str1:split){
            String[] split2 = str1.split(":");
            switch(split2[0]){
                case "x":this.setX(Integer.parseInt(split2[1]));break;
                case "y":this.setY(Integer.parseInt(split2[1]));break;
                case "f":this.fx = split2[1];break;
            }
        }
        this.setW(10);
        this.setH(10);
        return this;
    }
    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(this.getX(),this.getY(),this.getW(),this.getH());
    }

    @Override
    protected void move() {
        if(this.getX()<0||this.getX()>775||this.getY()<0||this.getY()>615){
            this.setLive(false);
            return;
        }
        switch(this.fx){
            case "up":this.setY(this.getY()-this.moveNum);break;
            case "left":this.setX(this.getX()-this.moveNum);break;
            case "right":this.setX(this.getX()+this.moveNum);break;
            case "down":this.setY(this.getY()+this.moveNum);break;
        }
    }
}
