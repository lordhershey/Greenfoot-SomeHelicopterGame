import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;

/**
 * Write a description of class CountDown here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CountDown extends Actor
{
    protected GreenfootImage image = null;

    protected int count = 5;

    protected BufferedImage bi = null;
    protected Graphics2D g = null;
    protected int w = 0;
    protected int h = 0;

    protected Font myFont = new Font("TimesRoman", Font.BOLD, 115);

    //public static Color mycolor = new Color(255,50,255,255);

    FontMetrics textMetrics = null;

    public CountDown()
    {

        try
        {
            InputStream is = this.getClass().getResourceAsStream("Outwrite.ttf");  
            //InputStream is = this.getClass().getResourceAsStream("SaucerBB.ttf");  
            //URL fileURL = this.getClass().getResource("Outwrite.ttf");
            //File fontFile = new File(fileURL.toURI());  
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(115f);
            myFont = customFont;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        image = new GreenfootImage(400,400);
        bi = image.getAwtImage();
        g = bi.createGraphics();
        w = bi.getWidth();
        h = bi.getHeight();
        g.setFont(myFont);
        textMetrics = g.getFontMetrics(myFont);
        g.setBackground(new Color(255,255,255,0));
    }

    public void act() 
    {
        setImage(image);
        g.clearRect(0,0,w,h);
        switch(count)
        {
            case 5:
            g.setColor(Color.PINK);
            break;
            case 4:
            g.setColor(Color.MAGENTA);
            break;
            case 3:
            g.setColor(Color.GREEN);
            break;
            case 2:
            g.setColor(Color.YELLOW);
            break;
            default:
            g.setColor(Color.RED);
            break;
        }
        String score = "" + count;
        g.drawString(score,w/2 - textMetrics.stringWidth(score)/2 ,h/2 - textMetrics.getHeight()/2);
        //g.drawString(score,0 ,0);
        Greenfoot.delay(30);
        count--;
        if(count < 1)
        {
            getWorld().removeObject(this);
            return;
        }
        
    }    
}
