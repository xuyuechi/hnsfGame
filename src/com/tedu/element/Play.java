package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Play extends ElementObj{

    private boolean left=false;
    private boolean up=false;
    private boolean right=false;
    private boolean down=false;

    //图片集合
    private Map<String,ImageIcon> imgMap = GameLoad.imgMap;
    //攻击状态 true攻击，false停止
    private boolean pkType = false;

    //专门用来记录主角当前炮口的方向，默认为up
    private String fx="up";
    public Play(){}
    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    /**
     * @说明 对象自己的事情自己做
     * @param g 画笔 用于进行绘画
     */
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

    @Override
    public void keyClick(boolean bl, int key) {
        if(bl){
            switch(key){
                case 37:this.down=false;this.up=false;this.right=false;this.left=true;this.fx="left";break;
                case 38:this.right=false;this.left=false;this.down=false;this.up=true;this.fx="up";break;
                case 39:this.down=false;this.up=false;this.left=false;this.right=true;this.fx="right";break;
                case 40:this.right=false;this.left=false;this.up=false;this.down=true;this.fx="down";break;
                case 32:this.pkType=true;break;
            }
        }
        else{
            switch(key){
                case 37:this.left=false;break;
                case 38:this.up=false;break;
                case 39:this.right=false;break;
                case 40:this.down=false;break;
                case 32:this.pkType=false;break;
            }
        }
    }

    @Override
    public void move() {
        if(this.left && this.getX()>0 && blockTest(this.getX()-3,this.getY())){
            this.setX(this.getX()-5);
        }
        if(this.up && this.getY()>0 && blockTest(this.getX(),this.getY()-3)){
            this.setY(this.getY()-5);
        }
        if(this.right && this.getX()<760-this.getW() && blockTest(this.getX()+3,this.getY())){
            this.setX(this.getX()+5);
        }
        if(this.down && this.getY()<580-this.getH() && blockTest(this.getX(),this.getY()+3)){
            this.setY(this.getY()+5);
        }
    }

    private boolean blockTest(int x,int y){
        Play test = new Play(x,y,30,30,null);
        ElementManager em = ElementManager.getManager();
        List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        for(int i=0;i<maps.size();i++){
            if(maps.get(i).pk(test))
                return false;
        }
        if(enemies.size()>0){
            for(int j=0;j<enemies.size();j++){
                if(enemies.get(j).pk(test))
                    return false;
            }
        }
        return true;
    }

    @Override
    protected void updateImage() {
        this.setIcon(imgMap.get(fx));
    }

    /**
     * @重写规则
     * 1.重写方法的名称和返回值需与父类的一样
     * 2.重写的方法的传入参数类型序列需与父类的一样
     * 3.重写方法访问修饰符只能比父类的更加宽泛，不能缩小
     * 4.重写方法抛出的异常类型不可以比父类抛出的类型更加宽泛
     * 5.如果父类方法没有抛出异常，重写方法不能抛出异常
     */
    private long fileTime=0;
    @Override
    protected void add(long gameTime){ //添加子弹
        if(!this.pkType){
            return;
        }
        this.pkType=false;
        ElementObj element = new PlayFile().createElement(this.toString());
        ElementManager.getManager().addElement(element, GameElement.PLAYFILE);

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
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
        this.setW(30);
        this.setH(30);
        this.setIcon(icon2);
        return this;
    }

}
