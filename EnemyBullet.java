import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class EnemyBullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyBullet extends Monster
{
    public boolean alive = true;

    public int speed = 5;

    public void addScore()
    {
        GameManager.addScore(50);
    }
    
    public void explode()
    {
        for(int i = 0 ; i < 2; i++)
        {
            SparksEffect fe = new SparksEffect();
            fe.counter = 0;
            fe.setRotation(Greenfoot.getRandomNumber(360));
            fe.speed = 2 + Greenfoot.getRandomNumber(3);
            getWorld().addObject(fe,getX(),
                getY());

        }

        addScore();
        getWorld().removeObject(this);
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void  BombHit()
    {
        alive = false;
    }

    public void act() 
    {
        if(!alive || getX() < -20 || getY() < -20)
        {
            alive = false;
            getWorld().removeObject(this);
            return;
        }

        move(speed);

        List<Player> playerlist = getIntersectingObjects(Player.class);
        for(Player p: playerlist)
        {
            int xdiff = getX() - p.getX();
            int ydiff = getY() - p.getY();

            p.hit();
            explode();
            return;
        }

        //Check for platforms -- Last
        List<Platform> platformlist = getIntersectingObjects(Platform.class);
        for(Platform p: platformlist)
        {
            explode();
            return;
        }

    }    
}
