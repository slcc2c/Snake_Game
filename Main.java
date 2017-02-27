import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

public class Main extends java.applet.Applet
        implements Runnable, KeyListener {
    Graphics bufferGraphics;
    Image offscreen;

    Thread runner;
    Snake s = new Snake();
    int SPEED_OFFSET = 1;

    int WIDTH = 500;
    int HEIGHT = 500;

    int speed = 0;

    @Override
    public void init() {
        this.setSize(WIDTH, HEIGHT);
        addKeyListener(this);
        s.new_pellet(getWidth(), getHeight());
        offscreen = createImage(WIDTH, HEIGHT);
        bufferGraphics = offscreen.getGraphics();
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    @Override
    public void stop() {
        if (runner != null) {
            runner.interrupt();
            runner = null;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(25
            } catch (InterruptedException e) {
            }
            if (s.pos_y + s.size > this.getHeight() || s.pos_y < 0 || s.pos_x + s.size > this.getWidth() || s.pos_x < 0 || s.score > s.max_score) {
                stop();
                System.exit(0);
                break;

            }
            repaint();

        }
    }

    //TODO Rework loop to move 1 px each time
    @Override
    public void paint(Graphics g) {
        bufferGraphics.clearRect(0, 0, WIDTH, HEIGHT);


        s.update_snake(SPEED_OFFSET);

        if (s.test_eat()) {
            s.ate(getWidth(), getHeight());
            if (s.score % 5 == 0) {
                SPEED_OFFSET++;
            }
        }
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.drawString(String.valueOf(s.score), WIDTH - 25, 25);
        for (Rectangle2D rec : s.rects) {
            bufferGraphics.fillRect((int) rec.getX(), (int) rec.getY(), s.size, s.size);
        }

        bufferGraphics.setColor(Color.RED);
        bufferGraphics.fillRect((int) s.pellet.getX(), (int) s.pellet.getY(), s.pellet_size, s.pellet_size);
        g.drawImage(offscreen, 0, 0, this);

    }

    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 38) {
            if (s.d_y == 0) {
                s.setDirection(3);
            }
        } else if (e.getKeyCode() == 40) {
            if (s.d_y == 0) {
                s.setDirection(1);
            }
        } else if (e.getKeyCode() == 39) {
            if (s.d_x == 0) {
                s.setDirection(0);
            }
        } else if (e.getKeyCode() == 37) {
            if (s.d_x == 0) {
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
