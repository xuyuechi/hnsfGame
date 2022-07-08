package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @说明 监听类，用于监听用户的操作 KeyListener
 * @author cyx
 */
public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();
    private Set<Integer> set = new HashSet<Integer>();
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * @说明 按下，左37，上38，右39，下40
     * @param keyEvent
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if(set.contains(key)){
            return;
        }
        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for (ElementObj obj:play){
            obj.keyClick(true,keyEvent.getKeyCode());
            System.out.println("按下"+keyEvent.getKeyCode());
        }
    }

    /**
     * @说明 松开，左37，上38，右39，下40
     * @param keyEvent
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(!set.contains(keyEvent.getKeyCode())){
            return;
        }
        set.remove(keyEvent.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for (ElementObj obj:play){
            obj.keyClick(false,keyEvent.getKeyCode());
        }
    }
}
