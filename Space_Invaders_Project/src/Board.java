import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class Board extends JPanel implements Runnable
{
    boolean ingame = true;
    private Dimension d;
    int BOARD_WIDTH=500;
    int BOARD_HEIGHT=500;
    int x = 0;

    //String message = "Click Screen to Start";
    String message = "";
    private Thread animator;
    Player p;
    Alien[] a = new Alien[10];
    ArrayList<Bullet> bullets  = new ArrayList<Bullet>(); //loop through these each time screen needs to be updated


    public Board()
    {
        addKeyListener(new KAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        p = new Player(BOARD_WIDTH/2, BOARD_HEIGHT-60, 2);

        int ax = 10;
        int ay = 10;

        for(int i=0; i<a.length; i++){
            a[i] = new Alien(ax,ay,10);
            ax += 40;
            if(i==4){
                ax=10;
                ay += 40;
            }
        }


        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }

    }


    public void paint(Graphics graphics)
    {
        super.paint(graphics);

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, d.width, d.height);

        // player
        graphics.setColor(Color.blue);
        graphics.fillRect(p.x, p.y, 20, 20);
        if(p.moveRight==true)
            p.x += p.speed;

        if(p.moveLeft==true)
            p.x -= p.speed;

        moveAliens();
        for(int i=0; i<a.length; i++) {
            graphics.setColor(Color.green);
            graphics.fillRect(a[i].x, a[i].y, 30, 30);
        }

        //JB - added some code to get bullets rendering but Jack will
        //need to alter to get bullets to fire from location of tank

        for(int i=0; i<bullets.size(); i++) {
            bullets.get(i).y -= bullets.get(i).speed;
            graphics.setColor(Color.white);
            graphics.fillRect(bullets.get(i).x, bullets.get(i).y, 5, 10);

        }


        Font small = new Font("Helvetica", Font.ITALIC, 14);
        FontMetrics metr = this.getFontMetrics(small);
        graphics.setColor(Color.black);
        graphics.setFont(small);
        graphics.drawString(message, 10, d.height-60);


    }


    public void moveAliens(){
        for(int i=0; i<a.length; i++){
            if(a[i].moveLeft==true){
                a[i].x -= 2; //a[i].speed;
            }

            if(a[i].moveRight==true){
                a[i].x += 2; //a[i].speed;
            }
        }

        for(int i=0; i<a.length; i++){
            if(a[i].x>BOARD_WIDTH){
                for(int j=0; j<a.length; j++){
                    a[j].moveLeft=true;
                    a[j].moveRight=false;
                    a[j].y += 5;
                }
            }


            if(a[i].x < 0) {
                for (int j = 0; j < a.length; j++) {
                    a[j].moveLeft = false;
                    a[j].moveRight = true;
                    a[j].y += 5;

                }

            }

        }
    }


    private class KAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            p.moveRight = false;
            p.moveLeft = false;

        }

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(key==e.VK_RIGHT){
                p.moveRight=true;
            }

            if(key==e.VK_LEFT){
                p.moveLeft=true;
            }


            //JB - added some code to get bullets created
            if(key==e.VK_SPACE){
                Bullet b = new Bullet(250,500,3);
                bullets.add(b);

                //you want a bullet to emerge from the top of the tank wherever it may be at the time
                //create a Bullet object with a certain position, certain colour, certain width/height
                //Add the bullet to an ArrayList of bullets
            }



        }

    }


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