import java.awt.EventQueue;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;
    private int TIMER_DELAY = 30;

    public Controller(){
        view = new View();
        model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight());
        view.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.toggleOrc();
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
