import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends Actor
{
    int speed = 6;
    public Bullet()
    {

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

        getWorld().removeObject(this);
    }

    public void act()
    {

        for(int i = 0 ; i < 4 ; i++)
        {
            move(speed);

            if(getX() > 800 || getY() < -10)
            {
                getWorld().removeObject(this);
                return;
            }

            //Check for Monster
            boolean hitMonster = false;
            List<Monster> monsterlist = getIntersectingObjects(Monster.class);
            for(Monster m : monsterlist)
            {
                if(!m.isAlive())
                {
                    continue;
                }
                m.BombHit();
                hitMonster = true;
            }

            if(hitMonster)
            {
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
}
