package com.tedu.manager;

import com.tedu.element.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @说明 加载器
 * @author cyx
 */
public class GameLoad {
    private static ElementManager em = ElementManager.getManager();
    public static Map<String, ImageIcon> imgMap = new HashMap<>();

    public static Map<String,ImageIcon> enemyImgMap = new HashMap<>();
    private static Properties pro = new Properties();


    /**
     * @说明 传入地图id由加载方法依据文件规则自动生成地图文件名称，加载文件
     * @param mapId 文件编号id
     */
    public static void MapLoad(int mapId){
        //得到文件路径
        String mapName="com/tedu/text/"+mapId+".map";
        //使用io流来获取文件对象
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if(maps == null){
            System.out.println("配置文件读取异常");
            return;
        }
        try {
            pro.clear();
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            while(names.hasMoreElements()){
                String key = names.nextElement().toString();
                //System.out.println(pro.getProperty(key));
                String[] arrs = pro.getProperty(key).split(";");
                for(int i=0;i<arrs.length;i++) {
                    ElementObj element = new MapObj().createElement(key + "," + arrs[i]);
                    em.addElement(element,GameElement.MAPS);
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @说明 图片加载器
     */
    public static void loadImg(){
        String textUrl = "com/tedu/text/GameData.pro";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(textUrl);
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String url = pro.getProperty(o.toString());
                imgMap.put(o.toString(),new ImageIcon(url));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pro.clear();
        textUrl = "com/tedu/text/EnemyGameData.pro";
        classLoader = GameLoad.class.getClassLoader();
        texts = classLoader.getResourceAsStream(textUrl);
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String url = pro.getProperty(o.toString());
                enemyImgMap.put(o.toString(),new ImageIcon(url));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 加载玩家
     */
    public static void loadPlay(){
        loadObj();
        String playStr = "500,500,up";
        //使用配置文件来获取类，实例化对象。【拓展】
        Class<?> class1 = objMap.get("play");
        ElementObj obj = null;
        try {
            Object newInstance = class1.newInstance();
            if(newInstance instanceof ElementObj){
                obj=(ElementObj) newInstance;
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ElementObj element = obj.createElement(playStr);
        em.addElement(element,GameElement.PLAY);

    }

    public static void loadEnemy(int num,int level){
        int already = 0;
        while(true){
            Enemy enemy = (Enemy)new Enemy().createElement("");
            if(!enemy.blockTest(enemy.getX(), enemy.getY()))
                continue;
            else{
                em.addElement(enemy,GameElement.ENEMY);
                enemy=null;
                already++;
            }
            if(already==num)
                break;
        }
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        if(level==1){
            for(int j=0;j<5;j++){
                Enemy e = (Enemy)enemies.get(j);
                e.setLevel(1);
                Thread t = new Thread(e);
                t.start();
            }
        }
        if(level==2){
            for(int j=0;j<5;j++){
                Enemy e = (Enemy)enemies.get(j);
                e.setLevel(1);
                Thread t = new Thread(e);
                t.start();
            }
            for(int j=5;j<10;j++){
                Enemy e = (Enemy)enemies.get(j);
                e.setLevel(2);
                Thread t = new Thread(e);
                t.start();
            }
        }
    }

    private static Map<String,Class<?>> objMap = new HashMap<>();
    /**
     * 使用配置文件来获取类。【拓展】
     */
    public static void loadObj(){
        String textUrl="com/tedu/text/obj.pro";
        //使用io流来获取文件对象
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(textUrl);
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set){
                String classUrl = pro.getProperty(o.toString());
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(),forName);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadBoard(){
        Board board = new Board();
        em.addElement(board,GameElement.BOARD);
    }


}
