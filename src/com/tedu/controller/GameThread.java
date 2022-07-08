package com.tedu.controller;

import com.tedu.element.*;
import com.tedu.game.GameStart;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;
import com.tedu.show.PassDialog;

import javax.swing.*;
import java.util.List;
import java.util.Map;

import static com.tedu.game.GameStart.gj;

/**
 * @author cyx
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，
 * 游戏运行时自动化，游戏判定，游戏地图切换，资源释放与重新读取
 * @继承 使用继承的方式实现多线程
 */
public class GameThread extends Thread {
    private ElementManager em;
    //目标分数，达到则进入下一关
    private int maxPoints = 5;

    private int mapId = 5;

    private int getPoints = 0;

    private int level = 1;

    private boolean tag = true;

    public GameThread() {
        em = ElementManager.getManager();
    }

    @Override
    public void run() { //游戏主线程
        while (tag) {
            //游戏开始前 读进度条，加载游戏资源（场景资源）
            gameLoad();
            //游戏进行时
            gameRun();
            //游戏场景结束 游戏资源回收（场景资源）
            gameOver();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void gameOver() {
        System.out.println("恭喜你过关");
        level++;
        if(level > 2){
            new PassDialog("恭喜通关，游戏结束");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tag = false;
            System.exit(0);
            return;
        }
        new PassDialog("恭喜通关，得分:"+getPoints);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        maxPoints=10;
        mapId=6;
        getPoints=0;
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for(GameElement ge:GameElement.values()){
            List<ElementObj> list = all.get(ge);
            list.clear();
        }
        gj.setjPanel(new GameMainJPanel());
    }

    /**
     * @说明 游戏进行时
     * @任务说明 游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
     * 2.新元素的增加（NPC死亡后出现道具）
     * 3.暂停。。。
     */
    private long gameTime = 0L;

    private void gameRun() {
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
            List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> enemyFiles = em.getElementsByKey(GameElement.ENEMYFILE);
            moveAndUpdate(all, gameTime);
            enemyPk(plays,enemyFiles);
            enemyPk(enemys, files);
            ElementPk(maps, files);
            ElementPk(maps,enemyFiles);
            gameTime++;
            if (getPoints == maxPoints)
                break;
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void enemyPk(List<ElementObj> listA, List<ElementObj> listB) {
        for (int i = 0; i < listA.size(); i++) {
            ElementObj enemy = listA.get(i);
            for (int j = 0; j < listB.size(); j++) {
                ElementObj file = listB.get(j);
                if (enemy.pk(file)) {
                    if(enemy instanceof Play){
                        new PassDialog("你输了！得分:"+getPoints);
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.exit(0);
                    }
                    enemy.setLive(false);
                    file.setLive(false);
                    getPoints++;
                    List<ElementObj> board = em.getElementsByKey(GameElement.BOARD);
                    Board b = (Board) board.get(0);
                    b.setPoints(getPoints);
                    break;
                }
            }
        }
    }

    public void ElementPk(List<ElementObj> listA, List<ElementObj> listB) {
        for (int i = 0; i < listA.size(); i++) {
            ElementObj enemy = listA.get(i);
            for (int j = 0; j < listB.size(); j++) {
                ElementObj file = listB.get(j);
                if (enemy.pk(file)) {
                    if(file instanceof EnemyFile){
                        file.setLive(false);
                        break;
                    }
                    enemy.setLive(false);
                    file.setLive(false);
                    break;
                }
            }
        }
    }

    /**
     * @说明 游戏元素自动化方法
     */
    public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                ElementObj obj = list.get(i);
                if (!obj.isLive()) {
                    obj.die();
                    list.remove(i--);
                }
                obj.model(gameTime);
            }
        }
    }

    /**
     * 游戏的加载在这里完成
     */
    private void gameLoad() {
        System.out.println("gameLoad执行");
        GameLoad.loadImg();
        GameLoad.MapLoad(mapId);
        GameLoad.loadPlay();
        GameLoad.loadEnemy(maxPoints,level);
        GameLoad.loadBoard();
    }

}
