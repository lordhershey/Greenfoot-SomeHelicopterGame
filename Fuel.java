import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Fuel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fuel extends Monster
{
    static GreenfootImage[] image;
    static
    {
        image = new GreenfootImage[2];
        image[0] = new GreenfootImage("FuelContainer.png");
        image[1] = new GreenfootImage("FuelContainerBroken.png");
    }

    boolean alive = true;
    boolean dying = false;
    int diecounter = 0;

    public void addScore()
    {
        GameManager.addScore(150);
    }
    
    public void BombHit()
    {
        if(alive && !dying)
        {
            dying = true;
            diecounter = 10;
            addScore();
        }
    }
    
    public boolean isAlive()
    {
        if(alive && !dying)
        {
            return true;
        }
        return false;
    }
    
    public void act() 
    {
        int w = getImage().getWidth();
        int h = getImage().getHeight();

        if(dying)
        {
            diecounter--; 
            for(int i = 0 ; i < 3 ; i++)
            {
                Explosion e = new Explosion();
                World world = getWorld();
                world.addObject(e,getX()+ Greenfoot.getRandomNumber(w) - w/2,getY()+ Greenfoot.getRandomNumber(h) - h/2);
            }
            if(diecounter < 1)
            {
                alive = false;
                dying = false;
                setImage(image[1]);
                return;
            }
        }

        if(!alive)
        {
            if(Greenfoot.getRandomNumber(15) == 8)
            {
                Explosion e = new Explosion();
                World world = getWorld();
                world.addObject(e,getX()+ Greenfoot.getRandomNumber(w) - w/2,getY()+ Greenfoot.getRandomNumber(h) - h/2);
            }
            //Just sit here and burn
            for(int i = 0 ; i < 3 ; i++)
            {
                FireEffect fe = new FireEffect();

                int iix = getX() + (Greenfoot.getRandomNumber(w) - w/2)/3;
                int iiy = getY() + (Greenfoot.getRandomNumber(h) - h/2)/3;

                getWorld().addObject(fe,iix,iiy);
                fe.setRotation(270);
                fe.speed = 3+i + Greenfoot.getRandomNumber(10);
            }
            return;
        }

        if(dying)
        {
            return;
        }

        List<Player> playerlist = getIntersectingObjects(Player.class);
        for(Player p: playerlist)
        {
            int xdiff = getX() - p.getX();
            int ydiff = getY() - p.getY();

            p.hit();
            dying = true;
            diecounter = 10;
            return;
        }
    }    
}
