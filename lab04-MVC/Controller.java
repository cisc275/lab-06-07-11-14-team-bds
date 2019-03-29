import java.awt.EventQueue;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;

public class Controller {
    private Model model;
    private View view;
    private int TIMER_DELAY = 100;

    public Controller(){
        view = new View();
        model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight());
        view.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.toggleOrc();
                }
        });
        //resize the orc running area when the window is resized
        view.getFrame().addComponentListener(new ComponentAdapter() {
          public void componentResized(ComponentEvent e) {
              view.updateDimensions();
              model.setFrameDimensions(view.getWidth(), view.getHeight());
          }
        });

        view.getButton().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }
                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_F) {
                        model.fireOrc();
                        view.drawFire();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_K) {
                        model.dieOrc();
                        view.drawDie();
                    }
                }
            });
    }

    public void start(){
        EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Timer time = new Timer(TIMER_DELAY, new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                model.updateLocationAndDirection();
                                view.update(model.getX(), model.getY(), model.getDirect());
                            }
                        });
                    time.start();
                }
            });
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.start();
    }
}
