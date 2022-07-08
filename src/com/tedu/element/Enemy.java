package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Enemy extends ElementObj implements Runnable {
    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    private int level = 1;

    private boolean pkType=false;

    //图片集合
    private Map<String, ImageIcon> imgMap = GameLoad.enemyImgMap;

    //专门用来记录敌人当前炮口的方向，默认为up
    private String fx = "up";

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        Random ran = new Random();
        int x = ran.nextInt(730);
        int y = ran.nextInt(550);
        this.setX(x);
        this.setY(y);
        this.setW(30);
        this.setH(30);
        this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
        return this;
    }

    @Override
    public void run() {
        autoMove();
    }

    public void autoMove() {
        if (level==1) {
            while (this.isLive()) {
                try {
                    this.left=true;
                    for(int i=0;i<3;i++) {
                        this.pkType = true;
                        sleep(100);
                        this.pkType=false;
                        sleep(1000);
                    }
                    this.left=false;
                    this.right=true;
                    for(int i=0;i<3;i++) {
                        this.pkType = true;
                        sleep(100);
                        this.pkType=false;
                        sleep(1000);
                    }
                    this.right=false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(level==2){
            while (this.isLive()) {
                try {
                    this.up=true;
                    for(int i=0;i<3;i++) {
                        this.pkType = true;
                        sleep(100);
                        this.pkType = false;
                        sleep(1000);
                    }
                    this.up=false;
                    this.down=true;
                    for(int i=0;i<3;i++) {
                        this.pkType = true;
                        sleep(100);
                        this.pkType = false;
                        sleep(1000);
                    }
                    this.down=false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void add(long gameTime){ //添加子弹
        if(!this.pkType){
            return;
        }
        this.pkType=false;
        ElementObj element = new EnemyFile().createElement(this.toString());
        ElementManager.getManager().addElement(element, GameElement.ENEMYFILE);
    }

    @Override
    public String toString() {
        int x=this.getX();
        int y=this.getY();
        switch(this.fx){
            case "up":x+=10;break;
            case "left":y+=10;break;
            case "right":x+=20;y+=10;break;
            case "down":x+=10;y+=20;break;
        }
        return "x:"+x+",y:"+y+",f:"+this.fx;
    }

    @Override
    public void move() {
        if (this.left && this.getX() > 0 && blockTest(this.getX() - 5, this.getY())) {
            this.fx="left";
            this.setX(this.getX() - 5);
        }
        if (this.up && this.getY() > 0 && blockTest(this.getX(), this.getY() - 5)) {
            this.fx="up";
            this.setY(this.getY() - 5);
        }
        if (this.right && this.getX() < 760 - this.getW() && blockTest(this.getX() + 5, this.getY())) {
            this.fx="right";
            this.setX(this.getX() + 5);
        }
        if (this.down && this.getY() < 580 - this.getH() && blockTest(this.getX(), this.getY() + 5)) {
            this.fx="down";
            this.setY(this.getY() + 5);
        }
    }

    public boolean blockTest(int x, int y) {
        Play test = new Play(x, y, 30, 30, null);
        ElementManager em = ElementManager.getManager();
        java.util.List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).pk(test))
                return false;
        }
        if (enemies.size() > 0) {
            for (int j = 0; j < enemies.size(); j++) {
                if (enemies.get(j).pk(test) && enemies.get(j)!=this)
                    return false;
            }
        }
        if (plays.size() > 0) {
            for (int k = 0; k < plays.size(); k++) {
                if (plays.get(k).pk(test))
                    return false;
            }
        }
        return true;
    }

    @Override
    protected void updateImage() {
        this.setIcon(imgMap.get(fx));
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
