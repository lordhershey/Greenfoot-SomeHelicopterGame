import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;

/**
 * Write a description of class ScoreBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScoreBox extends Actor
{
    BufferedImage bi = null;
    Graphics2D g = null;
    int w = 0;
    int h = 0;

    private Font myFont = new Font("TimesRoman", Font.BOLD, 18);

    public static Color mycolor = new Color(255,255,50,150);

    FontMetrics textMetrics = null;
    
    public ScoreBox()
    {
        try
        {
            InputStream is = this.getClass().getResourceAsStream("Outwrite.ttf");  
            //InputStream is = this.getClass().getResourceAsStream("SaucerBB.ttf");  
            //URL fileURL = this.getClass().getResource("Outwrite.ttf");
            //File fontFile = new File(fileURL.toURI());  
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(23f);
            myFont = customFont;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        GreenfootImage gi = getImage();
        bi = gi.getAwtImage();
        g = bi.createGraphics();
        w = bi.getWidth();
        h = bi.getHeight();
        g.setFont(myFont);
        textMetrics = g.getFontMetrics(myFont);
        g.setBackground(new Color(255,255,255,0));
    }

    public void act() 
    {
        g.clearRect(0,0,w,h);
        g.setColor(/*Color.YELLOW*/ mycolor);
        String score = "Score: " + GameManager.getScore();
        
        g.drawString(score,w - textMetrics.stringWidth(score) ,18);
    }    
}
