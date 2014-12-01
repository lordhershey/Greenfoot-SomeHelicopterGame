import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Radar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Radar extends Monster
{
    static GreenfootImage[] image;
    static GreenfootImage brokenimage;
    static{
        image = new GreenfootImage[2];
        image[0] = new GreenfootImage("Radar1.png");
        image[1] = new GreenfootImage("Radar2.png");
        brokenimage = new GreenfootImage("RadarBroken.png");
    }

    int idx = 0;
    int counter = 0;

    boolean alive = true;

    public void addScore()
    {
        GameManager.addScore(250);
    }
    
    public boolean isAlive()
    {
        if(alive)
        {
            return true;
        }
        return false;
    }

    public void BombHit()
    {
        if(!alive)
        {
            return;
        }

        World w = getWorld();
        
        addScore();

        for(int j = 0 ; j < 3 ; j++)
        {
            Explosion e = new Explosion();
            //e.setRotation(Greenfoot.getRandomNumber(360));
            w.addObject(e,getX() + Greenfoot.getRandomNumber(31) - 15 ,getY()-15 + Greenfoot.getRandomNumber(21) - 10);
        }

        alive = false;

        for(int i = 0 ; i < 8; i++)
        {
            SparksEffect fe = new SparksEffect();
            fe.counter = 0;
            fe.setRotation(Greenfoot.getRandomNumber(360));
            fe.speed = 2 + Greenfoot.getRandomNumber(3);
            getWorld().addObject(fe,getX() + Greenfoot.getRandomNumber(getImage().getWidth()) - getImage().getWidth()/2 ,
                getY() + Greenfoot.getRandomNumber(getImage().getHeight()) - getImage().getHeight()/2 -10);
        }

        return;
    }

    public void act() 
    {
        if(alive)
        {

            List<Player> playerlist = getWorld().getObjects(Player.class);
            for(Player p: playerlist)
            {
                int xdiff = getX() - p.getX();
                int ydiff = getY() - p.getY();

                //also check if player is alive
                if(xdiff <= 34 && xdiff >= -40 && ydiff <= 41 && ydiff >= -20)
                {
                    World w = getWorld();

                    for(int j = 0 ; j < 3 ; j++)
                    {
                        Explosion e = new Explosion();
                        //e.setRotation(Greenfoot.getRandomNumber(360));
                        w.addObject(e,getX() + Greenfoot.getRandomNumber(31) - 15 ,getY()-15 + Greenfoot.getRandomNumber(21) - 10);
                    }
                    alive = false;

                    p.hit();
                    for(int i = 0 ; i < 8; i++)
                    {
                        SparksEffect fe = new SparksEffect();
                        fe.counter = 0;
                        fe.setRotation(Greenfoot.getRandomNumber(360));
                        fe.speed = 2 + Greenfoot.getRandomNumber(3);
                        getWorld().addObject(fe,getX() + Greenfoot.getRandomNumber(getImage().getWidth()) - getImage().getWidth()/2 ,
                            getY() + Greenfoot.getRandomNumber(getImage().getHeight()) - getImage().getHeight()/2 -10);
                    }

                    return;
                }
            }

            if(counter > 1)
            {
                counter--;
            }
            else
            {
                idx = (idx + 1) %2;
                counter = Greenfoot.getRandomNumber(40) + 10;
                setImage(image[idx]);
            }
            return;
        }

        if(Greenfoot.getRandomNumber(20) == 8)
        {
            SparksEffect fe = new SparksEffect();
            fe.counter = 0;
            fe.setRotation(Greenfoot.getRandomNumber(360));
            fe.speed = 2 + Greenfoot.getRandomNumber(3);
            getWorld().addObject(fe,getX()  ,
                getY() + 10);
        }

        setImage(brokenimage);

    }    
}
