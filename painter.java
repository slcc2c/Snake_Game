import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class painter extends java.applet.Applet
        implements Runnable, KeyListener {


    Thread runner;
    Snake s = new Snake();
    int s_pos_x = 0;
    int s_pos_y = 0;
    int d_x = 1;
    int d_y = 0;
    int speed = 1;
    public void start() {
        addKeyListener(this);
        s.ate();
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop() {
        if (runner != null) {
            runner.interrupt();
            runner = null;
        }
    }

    public void run() {
        while (true) {
            if (s_pos_y > this.getHeight() || s_pos_y < 0 || s_pos_x > this.getWidth()) {
                stop();
                System.exit(0);
                break;

            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < s.score; i++) {
            g.drawRect(s_pos_x + i * 5, s_pos_y + i * 5, 5, 5);
        }
        s_pos_x += (d_x + (s.score * d_x));
        s_pos_y += (d_y + (s.score * d_y));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            if (d_y != 1) {
                d_x = 0;
                d_y = -1;
            }
        } else if (e.getKeyCode() == 37) {
            if (d_x != 1) {
                d_x = -1;
                d_y = 0;
            }
        } else if (e.getKeyCode() == 39) {
            if (d_x != -1) {
                d_x = 1;
                d_y = 0;
            }
        } else if (e.getKeyCode() == 40) {
            if (d_y != -1) {
                d_x = 0;
                d_y = 1;
            }
        } else {
            System.out.println(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}