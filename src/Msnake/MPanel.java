package Msnake;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Random;

public class MPanel extends JPanel implements KeyListener, ActionListener
{
    ImageIcon title;
    ImageIcon body;
    ImageIcon up;
    ImageIcon down;
    ImageIcon left;
    ImageIcon right;
    ImageIcon food;

    int len = 3;
    int[] snakex = new int[750];
    int[] snakey = new int[750];
    String Fx = "R";
    boolean isStarted = false;
    boolean isFailed = false;
    Timer timer = new Timer(200, this);
    int foodx, foody;
    Random rand = new Random();
    int score;

    public MPanel()
    {
        loadImages();
        initSnake();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.white);
        title.paintIcon(this, g, 25, 11);

//        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 600);
        g.setColor(Color.white);
        g.drawString("Len " + len, 750, 35);
        g.drawString("Score " + score, 750, 50);

        if (Fx == "R")
            right.paintIcon(this, g, snakex[0], snakey[0]);
        else if (Fx == "L")
            left.paintIcon(this, g, snakex[0], snakey[0]);
        else if (Fx == "D")
            down.paintIcon(this, g, snakex[0], snakey[0]);
        else if (Fx == "U")
            up.paintIcon(this, g, snakex[0], snakey[0]);

        for (int i = 1; i < len; i++)
            body.paintIcon(this, g, snakex[i], snakey[i]);
        if (!isStarted)
        {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press Space to Start", 300, 300);
        }
        if (isFailed)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Failed:Press Space to Restart", 200, 300);
        }

        food.paintIcon(this, g, foodx, foody);
    }

    public void initSnake()
    {
        len = 3;
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
        foodx = 25 + 25 * rand.nextInt(34);
        foody = 75 + 25 * rand.nextInt(24);
        Fx = "R";
        score = 0;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int KeyCode = e.getKeyCode();
        if (KeyCode == KeyEvent.VK_SPACE)
        {
            if (isFailed)
            {
                isFailed = false;
                initSnake();
                isStarted = true;
            } else isStarted = !isStarted;
            repaint();
        } else if (KeyCode == KeyEvent.VK_LEFT)
            Fx = "L";
        else if (KeyCode == KeyEvent.VK_RIGHT)
            Fx = "R";
        else if (KeyCode == KeyEvent.VK_UP)
            Fx = "U";
        else if (KeyCode == KeyEvent.VK_DOWN)
            Fx = "D";
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (isStarted && !isFailed)
        {
            for (int i = len - 1; i > 0; i--)
            {
                snakex[i] = snakex[i - 1];
                snakey[i] = snakey[i - 1];
            }

            if (Fx == "R") snakex[0] = snakex[0] + 25;
            if (Fx == "L") snakex[0] = snakex[0] - 25;
            if (Fx == "U") snakey[0] = snakey[0] - 25;
            if (Fx == "D") snakey[0] = snakey[0] + 25;
            if (snakex[0] > 850) snakex[0] = 25;
            if (snakex[0] < 25) snakex[0] = 850;
            if (snakey[0] < 75) snakey[0] = 650;
            if (snakey[0] > 650) snakey[0] = 75;

            if (foodx == snakex[0] && foody == snakey[0])
            {
                foodx = 25 + 25 * rand.nextInt(34);
                foody = 75 + 25 * rand.nextInt(24);
                len += 1;
                score += 10;
                snakex[len - 1] = snakex[len - 2];
                snakey[len - 1] = snakey[len - 2];
            }

            for (int i = 1; i < len; i++)
            {
                if (snakex[i] == snakex[0] && snakey[i] == snakey[0])
                    isFailed = true;
            }
            repaint();
        }

        timer.start();
    }

    private void loadImages()
    {
        InputStream is;
        try
        {
            is = getClass().getClassLoader().getResourceAsStream("title.jpg");
            title = new ImageIcon(ImageIO.read(is));
            is = getClass().getClassLoader().getResourceAsStream("body.png");
            body = new ImageIcon(ImageIO.read(is));
            is = getClass().getClassLoader().getResourceAsStream("up.png");
            up = new ImageIcon(ImageIO.read(is));
            is = getClass().getClassLoader().getResourceAsStream("down.png");
            down = new ImageIcon(ImageIO.read(is));
            is = getClass().getClassLoader().getResourceAsStream("left.png");
            left = new ImageIcon(ImageIO.read(is));
            is = getClass().getClassLoader().getResourceAsStream("right.png");
            right = new ImageIcon(ImageIO.read(is));
            is = getClass().getClassLoader().getResourceAsStream("food.png");
            food = new ImageIcon(ImageIO.read(is));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
