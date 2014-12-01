import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Bomb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bomb extends Actor
{
    public int chx = 4;
    public int chy = 0;
    int yspeedmax = 15;

    public Bomb()
    {
        chx = 4;
        chy = 0;
    }

    public void explode()
    {
        int w = getImage().getWidth();
        int h = getImage().getHeight();

        int px = getX();
        int py = getY();

        World world = getWorld();

        for(int i = 0 ; i < 5 ; i++)
        {
            Explosion e = new Explosion();

            world.addObject(e,px + (Greenfoot.getRandomNumber(w) - w/2)*2/3,py + (Greenfoot.getRandomNumber(h) - h/2)*2);
        }

        getWorld().removeObject(this);
    }

    public void act() 
    {
        int x = getX();
        int y = getY();

        if(chy > 0)
        {
            int rot = getRotation();
            if(rot < 45)
            {
                rot += 1;
                setRotation(rot);
            }
        }

        chy ++;
        if(chy > yspeedmax)
        {
            chy = yspeedmax;
        }

        x = x + chx;
        y = y + chy;

        setLocation(x,y);

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
    
    @Override
    public void addedToWorld(World w)
    {
        PlatformerWorld pw = (PlatformerWorld)getWorld();
        if(!pw.canScrollRight())
        {
            chx = chx - 4;
        }
    }
}
