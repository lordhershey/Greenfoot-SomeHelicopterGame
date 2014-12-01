import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;

/**
 * Write a description of class PlaceHolderLevelTransition here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlaceHolderLevelTransition extends CountDown
{
    
    long startTime = 0;
    
    public PlaceHolderLevelTransition()
    {
        //super();
        myFont = new Font("TimesRoman", Font.BOLD, 21);
        try
        {
            InputStream is = this.getClass().getResourceAsStream("Outwrite.ttf");  
            //InputStream is = this.getClass().getResourceAsStream("SaucerBB.ttf");  
            //URL fileURL = this.getClass().getResource("Outwrite.ttf");
            //File fontFile = new File(fileURL.toURI());  
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(21f);
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
        startTime = System.currentTimeMillis();
    }

    public void act() 
    {
        setImage(image);
        g.clearRect(0,0,w,h);
        String score;
        switch(count)
        {
            case 5:
            g.setColor(Color.PINK);
            score = "Congratulations!";
            break;
            case 4:
            g.setColor(Color.MAGENTA);
            score = "You have completed this level :)";
            break;
            case 3:
            g.setColor(Color.GREEN);
            score = "Next level in 3";
            break;
            case 2:
            g.setColor(Color.YELLOW);
            score = "Next level in 2";
            break;
            default:
            g.setColor(Color.RED);
            score = "Next level in 1";
            break;
        }

        g.drawString(score,w/2 - textMetrics.stringWidth(score)/2 ,h/2 - textMetrics.getHeight()/2);
        //g.drawString(score,0 ,0);
        //Greenfoot.delay(50);
        long endTime = System.currentTimeMillis();
        if((endTime - startTime) > 999)
        {
            count--;
            startTime = endTime;
        }
        if(count < 1)
        {
            GameManager.setLevel(GameManager.level+1);
            getWorld().removeObject(this);
            PlatformerWorld pw = new PlatformerWorld();
            Greenfoot.setWorld(pw);
            return;
        }

    }    
}    

