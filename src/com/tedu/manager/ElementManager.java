package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法给予视图和控制获取数据
 * @author cyx
 * @问题一 存储所有元素数据，怎么存放？ list map set 3大集合
 * @问题二 管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 */
public class ElementManager {

    /**
     * String作为key匹配所有的元素  play->List<Object>listPlay  enemy->List<Object>listEnemy
     * 枚举类型，当做map的key用来区分不一样的资源，用于获取资源
     * List中元素的泛型应该是元素基类
     * 所有元素都可以存放在map集合中，显示模块只需要获取到这个map就可以显示有的界面需要显示的元素。
     * （调用元素基类的showElement()）
     *
     */
    private Map<GameElement,List<ElementObj>> gameElements;

    //本方法一定不够用
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    public void clearElements(){
        gameElements.clear();
    }

    //添加元素（多半由加载器调用）
    public void addElement(ElementObj obj,GameElement ge){
        gameElements.get(ge).add(obj);//添加对象到集合，按key值就行存储
    }

    //依据key返回list集合，取出某一类元素
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }

    /**
     * 单例模式：内存中有且只有一个实例
     * 饿汉模式--是启动就自动加载实例
     * 饱汉模式--是需要使用的时候才加载实例
     *
     * 编写方式：
     * 1.需要一个静态的属性（定义一个常量）单例的引用
     * 2.提供一个静态的方法（返回这个实例） return单例的引用
     * 3.一般为防止其他人自己使用（类是可以实例化）ElementManager em=new ElementManager()，所以会私有化构造方法
     *
     */
    private static ElementManager EM;

    //synchornized线程锁保证本方法执行中只有一个线程
    public static synchronized ElementManager getManager(){
        if(EM == null){
            EM = new ElementManager();
        }
        return EM;
    }

    //私有化构造方法
    private ElementManager(){
        init();
    }

    //本方法是为将来可能出现的功能拓展，继承并重写init方法准备的。
    //因为类的构造方法不能被继承
    public void init(){
        //hashMap hash散列
        gameElements=new HashMap<GameElement,List<ElementObj>>();
        //将每种元素集合放入到map中
        for(GameElement ge:GameElement.values()){
            gameElements.put(ge,new ArrayList<>());
        }
        //道具，子弹，爆炸效果，死亡效果。。。


    }
}
