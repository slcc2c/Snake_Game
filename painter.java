import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

public class painter extends java.applet.Applet
        implements Runnable, KeyListener {


    Thread runner;
    Snake s = new Snake();
    int SPEED_OFFSET = s.size / 6;

    int WIDTH = 500;
    int HEIGHT = 500;

    int speed = 0;


    public void start() {
        this.setSize(WIDTH, HEIGHT);
        addKeyListener(this);
        s.new_pellet(getWidth(), getHeight());
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
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            if (s.pos_y + s.size > this.getHeight() || s.pos_y < 0 || s.pos_x + s.size > this.getWidth() || s.pos_x < 0) {
                stop();
                System.exit(0);
                break;

            }
        }
    }

    public void paint(Graphics g) {


        SPEED_OFFSET = s.size / 6;

        s.update_snake(SPEED_OFFSET);

        if (s.test_eat()) {
            s.ate(getWidth(), getHeight());
        }
        g.drawString(String.valueOf(s.score), WIDTH - 25, 25);
        for (Rectangle2D rec : s.rects) {
            g.fillRect((int) rec.getX(), (int) rec.getY(), s.size, s.size);
        }

        g.setColor(Color.RED);
        g.fillRect((int) s.pellet.getX(), (int) s.pellet.getY(), s.pellet_size, s.pellet_size);




    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            if (s.d_y != SPEED_OFFSET) {
                s.setDirection(3);
            }
        } else if (e.getKeyCode() == 40) {
            if (s.d_y != -SPEED_OFFSET) {
                s.setDirection(1);
            }
        } else if (e.getKeyCode() == 39) {
            if (s.d_x != -SPEED_OFFSET) {
                s.setDirection(0);
            }
        } else if (e.getKeyCode() == 37) {
            if (s.d_x != SPEED_OFFSET) {
                s.setDirection(2);
            }
        } else {
            System.out.println(e.getKeyCode());
        }
        System.out.println(e.getKeyCode());
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}