package Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeys implements KeyListener {

    public static boolean left,right,shoot,weapon;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            left = true;
        }
        if(key == KeyEvent.VK_RIGHT){
            right = true;
        }
        if(key == KeyEvent.VK_UP){
            shoot = true;
        }
        if(key == KeyEvent.VK_DOWN){
            weapon = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            left = false;
        }
        if(key == KeyEvent.VK_RIGHT){
            right = false;
        }
    }
}
