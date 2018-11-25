import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Rectangle;


public class Board extends JPanel implements Runnable {

    boolean ingame = true;
    private Dimension d;
    int BOARD_WIDTH = 500;
    int BOARD_HEIGHT = 500;
    int x = 0;
    String message = "SCORE:";
    private Thread animator;
    Player p;

    public Alien[] a = new Alien[10];
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();


    public Board() {
        addKeyListener(new KAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        p = new Player(BOARD_WIDTH / 2, BOARD_HEIGHT - 60, 2);

        int ax = 10;
        int ay = 10;


        for (int i = 0; i < a.length; i++) {
            a[i] = new Alien(ax, ay, 10);
            ax += 40;
            if (i == 4) {
                ax = 10;
                ay += 40;
            }
        }


        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }

    }

    //Graphics/Paint
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, d.width, d.height);


        //Player
        g2d.setColor(new Color(8, 8, 255));
        g2d.fillRect(p.x, p.y, 20, 20);
        if (p.moveRight == true)
            p.x += p.speed;

        if (p.moveLeft == true)
            p.x -= p.speed;

        //Alien
        moveAliens();
        for (int i = 0; i < a.length; i++) {
            g2d.setColor(new Color(31, 255, 20));
            g2d.fillRect(a[i].x, a[i].y, 30, 30);
        }

        //JB - added some code to get bullets rendering but Jack will
        //need to alter to get bullets to fire from location of tank

        //Bullet
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).y -= bullets.get(i).speed;
            g2d.setColor(new Color(255, 241, 41));
            g2d.fillRect(bullets.get(i).x, bullets.get(i).y, 5, 10);

        }

        //Attempted to get bullets and aliens colliding (kind of works but game crashes when firing too many bullets)

              /*Rectangle r1 = new Rectangle(a[i].x, a[i].y, 30, 30);
                Rectangle r2 = new Rectangle(bullets.get(i).x, bullets.get(i).y, 30, 30);
                Rectangle r3 = new Rectangle(p.x, p.y, 20, 20);


                if(r1.intersects(r2))
                {
                    bullets.remove(bullets.get(i));

                }*/

        //Score Text
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        graphics.setColor(Color.white);
        graphics.setFont(small);
        graphics.drawString(message, 10, d.height - 475);


    }

    // Aliens side to side movement
    public void moveAliens() {
        for (int i = 0; i < a.length; i++) {
            if (a[i].moveLeft == true) {
                a[i].x -= 2; //a[i].speed;
            }

            if (a[i].moveRight == true) {
                a[i].x += 2; //a[i].speed;
            }
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i].x > BOARD_WIDTH) {
                for (int j = 0; j < a.length; j++) {
                    a[j].moveLeft = true;
                    a[j].moveRight = false;
                    a[j].y += 5;
                }
            }


            if (a[i].x < 0) {
                for (int j = 0; j < a.length; j++) {
                    a[j].moveLeft = false;
                    a[j].moveRight = true;
                    a[j].y += 5;

                }

            }

        }
    }


    //Key Bindings
    private class KAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            p.moveRight = false;
            p.moveLeft = false;

        }

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == e.VK_RIGHT) {
                p.moveRight = true;
            }

            if (key == e.VK_LEFT) {
                p.moveLeft = true;
            }


            //JB - added some code to get bullets created

            if (key == e.VK_SPACE) {
                Bullet bullet = new Bullet(p.x, p.y, 3);
                bullets.add(bullet);

            }

        }


    }

    //Thread and time
    public void run() {

        long beforeTime, timeDiff, sleep;

        int animationDelay = 5;
        long time =
                System.currentTimeMillis();
        while (true) {

            repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0,time -
                        System.currentTimeMillis()));
            }catch (InterruptedException e) {
                System.out.println(e);
            }
        }//end while loop



    }

}//end of class