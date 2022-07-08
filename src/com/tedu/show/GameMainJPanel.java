package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @说明 游戏的主要面板
 * @author cyx
 * @功能说明 主要进行元素的显示，同时进行界面的刷新（多线程）
 *
 * @多线程刷新 1.本类实现线程接口 2.本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
    //联动管理器
    private ElementManager em;
    public GameMainJPanel(){
        init();
    }

    public void init(){
        em = ElementManager.getManager(); //得到元素管理器对象
    }

    /**
     * @说明 paint方法绘画时有固定的顺序，先绘画的图片在底层，后绘画的图片会覆盖先绘画的
     * @param g
     * @约定 本方法只执行一次，想实时刷新需要使用多线程
     */
    @Override //用于绘画的 Graphics 画笔 专门用于绘画的
    public void paint(Graphics g) {
        super.paint(g);
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        //问题已解决：无法控制元素的绘画前后顺序，造成错误覆盖（背景覆盖玩家图）
        for(GameElement ge:GameElement.values()){
            List<ElementObj> list = all.get(ge);
            for(int i=0;i<list.size();i++){
                ElementObj obj = list.get(i);
                obj.showElement(g);
            }
        }
    }

    @Override
    public void run() {
        while(true){
            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
