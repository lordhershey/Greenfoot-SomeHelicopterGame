import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.*;
/**
 * Write a description of class Missile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Missile extends Monster
{
    boolean alive = true;
    boolean sittingThere = true;
    boolean flying = false;
    boolean dying = false;

    int speedmax = 5;
    int speed = 0;

    static int MissileNumber = 0;
    int id = 0;

    //easy level
    //static int divisor = 4;

    public Missile()
    {
        id = MissileNumber++;
    }

    public boolean isAlive()
    {
        if(alive && !dying)
        {
            return true;
        }
        return false;
    }
    
    public void addScore()
    {
        GameManager.addScore(100);
        if(flying)
        {
            GameManager.addScore(25);
        }
    }
    
    public void BombHit()
    {
        World w = getWorld();
        Explosion e = new Explosion();
        //e.setRotation(Greenfoot.getRandomNumber(360));
        w.addObject(e,getX(),getY()-20);
        alive = false;

        addScore();
        
        for(int i = 0 ; i < 5; i++)
        {
            SparksEffect fe = new SparksEffect();
            fe.counter = 0;
            fe.setRotation(Greenfoot.getRandomNumber(360));
            fe.speed = 2 + Greenfoot.getRandomNumber(3);
            getWorld().addObject(fe,getX() + Greenfoot.getRandomNumber(getImage().getWidth()) - getImage().getWidth()/2 ,
                getY() + Greenfoot.getRandomNumber(getImage().getHeight()) - getImage().getHeight()/2 -10);
        }
    }

    private boolean testPlayerHit()
    {
        //check hit
        List<Player> playerlist = getWorld().getObjects(Player.class);
        for(Player p: playerlist)
        {
            int xdiff = getX() - p.getX();
            int ydiff = getY() - p.getY();

            //also check if player is alive
            if(xdiff <= 25 && xdiff >= -28 && ydiff <= 31 && ydiff >= -34)
            {
                World w = getWorld();
                Explosion e = new Explosion();
                //e.setRotation(Greenfoot.getRandomNumber(360));
                w.addObject(e,getX(),getY()-20);
                alive = false;

                for(int i = 0 ; i < 5; i++)
                {
                    SparksEffect fe = new SparksEffect();
                    fe.counter = 0;
                    fe.setRotation(Greenfoot.getRandomNumber(360));
                    fe.speed = 2 + Greenfoot.getRandomNumber(3);
                    getWorld().addObject(fe,getX() + Greenfoot.getRandomNumber(getImage().getWidth()) - getImage().getWidth()/2 ,
                        getY() + Greenfoot.getRandomNumber(getImage().getHeight()) - getImage().getHeight()/2 -10);
                }

                p.hit();
                
                return true;
            }
        }
        return false;
    }

    public void act()
    {
        if(!alive || getX() < -200 || getY() < -200)
        {
            getWorld().removeObject(this);
            return;
        }

        if(flying)
        {
            double x = -25;
            double y = 0;

            double theta = (double)getRotation() / 180.0 * Math.PI;

            int ix = (int)(x * Math.cos(theta) - y * Math.sin(theta));
            int iy = (int)(x * Math.sin(theta) + y * Math.cos(theta));

            for(int i = 0 ; i < 3 ; i++)
            {
                FireEffect fe = new FireEffect();
                int rx = Greenfoot.getRandomNumber(5)-2;
                int ry = Greenfoot.getRandomNumber(5)-2;

                int yspace = 5;

                int rot = 90;

                int iix = getX() + ix + rx;
                int iiy = getY() + iy + ry;

                getWorld().addObject(fe,iix,iiy);
                fe.setRotation(rot);
                fe.speed = 4 + Greenfoot.getRandomNumber(4);
            }

            speed ++;

            if(speed > speedmax)
            {
                speed = speedmax;
            }

            move(speed);

        }

        if(testPlayerHit())
        {
            return;
        }       

        if(sittingThere)
        {
            if(id % GameManager.getMissileDivisor() != 0)
            {
                //This missile will not do anything
                return;
            }

            List<Player> playerlist = getWorld().getObjects(Player.class);
            for(Player p: playerlist)
            {
                double deltaX = p.getX() - getX();
                double deltaY = p.getY() - getY();
                int degrees = (int)(Math.atan2(deltaY,deltaX) * 180.0 / Math.PI); 

                //System.out.println("Degrees == " + degrees);

                while(degrees < 0)
                {
                    degrees += 360;
                }

                if(degrees > (180 + 50) && degrees < (270 - 30))
                {
                    flying = true;
                    return;
                }
            }

        }

    }
}
