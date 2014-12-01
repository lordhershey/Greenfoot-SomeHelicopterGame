import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.*;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{

    protected static GreenfootImage[] image;
    protected static GreenfootImage dead;

    static{
        image = new GreenfootImage[4];
        image [0] = new GreenfootImage("Helicopter1.png");
        image [1] = new GreenfootImage("Helicopter3.png");
        image [2] = new GreenfootImage("Helicopter2.png");
        image [3] = new GreenfootImage("Helicopter3.png");

        dead = new GreenfootImage("BrokenHelicopter.png");
    }

    int counter = 0;
    int firecounter = 0;

    int speed = 5;

    int deadCounter = 0;
    boolean Dying = false;
    boolean Dead = false;
    boolean Crashing = false;

    public boolean alive()
    {
        if(Dying || Dead)
        {
            return false;
        }

        return true;
    }

    public void hit()
    {
        if(Dead || Dying || Crashing)
        {
            return;
        }

        Crashing = true;
    }

    public void act() 
    {

        boolean Down = false;
        boolean Up = false;
        boolean Left = false;
        boolean Right = false;
        boolean Fire = false;
        boolean Shift = false;

        //Check if dead
        if(Dead)
        {
            int x = getX();
            if (x > 135)
            {
                PlatformerWorld w = (PlatformerWorld)getWorld();
                w.shiftScreen(135 - x,0);

            }
            
            setImage(image[0]);
            //setLocation(135,25);
            Dead = false;
            Dying = false;
            Crashing = false;
            getWorld().removeObject(this);
            return;
        }

        //check if dying

        if(Dying)
        {
            deadCounter--;
            if(deadCounter < 1)
            {
                Dead = true;
            }

            for(int i = 0 ; i < 1; i++)
            {
                SparksEffect fe = new SparksEffect();
                fe.counter = 0;
                fe.setRotation(Greenfoot.getRandomNumber(360));
                fe.speed = 2 + Greenfoot.getRandomNumber(3);
                getWorld().addObject(fe,getX() + Greenfoot.getRandomNumber(getImage().getWidth()) - getImage().getWidth()/2 ,
                    getY() + Greenfoot.getRandomNumber(getImage().getHeight()) - getImage().getHeight()/2);
            }

            return;
        }

        if(firecounter > 0)
        {
            firecounter--;
        }

        counter = (counter + 1) % image.length;

        setImage(image[counter]);

        Down = Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down");
        Up  = Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up");
        Right   = Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right");
        Left = Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left");
        Fire = Greenfoot.isKeyDown("space");

        Shift = Greenfoot.isKeyDown("shift") || Greenfoot.isKeyDown("control") || Greenfoot.isKeyDown("backspace") || Greenfoot.isKeyDown("enter");

        if(Crashing)
        {
            Up = false;
            Right = false;
            Left = false;
            Down = true;
            Fire = false;
            World world = getWorld();
            int w = (getImage().getWidth())/2;
            int h = (getImage().getHeight())/2;

            if(Greenfoot.getRandomNumber(3) == 2)
            {
                Explosion e = new Explosion();
                world.addObject(e,getX() + Greenfoot.getRandomNumber(w) - w/2 ,getY()+ Greenfoot.getRandomNumber(h) - h/2);
            }
            else
            {
                SparksEffect fe = new SparksEffect();
                fe.counter = 0;
                fe.setRotation(Greenfoot.getRandomNumber(360));
                fe.speed = 2 + Greenfoot.getRandomNumber(3);
                world.addObject(fe,getX() + Greenfoot.getRandomNumber(w) - w/2 ,
                    getY() + Greenfoot.getRandomNumber(h) - h/2);
            }
        }

        int ox = getX();
        int oy = getY();

        if(Down)
        {
            if(Left)
            {
                setRotation(135);
                if(Shift)
                {
                    setRotation(125);
                }
            }
            else if(Right)
            {
                setRotation(45);
                if(Shift)
                {
                    setRotation(55);
                }

            }
            else
            {
                setRotation(90);
            }
            move(speed);
        }
        else if(Up)
        {
            if(Left)
            {
                setRotation(225);
                if(Shift)
                {
                    setRotation(235);
                }

            }
            else if(Right)
            {
                setRotation(315);
                if(Shift)
                {
                    setRotation(325);
                }

            }
            else
            {
                setRotation(270);
            }
            move(speed);
        }
        else if(Left)
        {
            setRotation(180);
            move(speed);
        }
        else if(Right)
        {
            setRotation(0);
            move(speed);
        }

        //Set the image to upright position
        setRotation(0);

        //Tilt for display only
        if(Left)
        {
            setRotation(-15);
            if(Shift)
            {
                setRotation(-25);
            }

        }
        else if(Right)
        {   
            setRotation(15);
            if(Shift)
            {
                setRotation(25);
            }

        }

        if(Crashing)
        {
            setRotation(45);
        }

        int x = getX();
        int y = getY();

        World w = getWorld();

        if(x < 0)
        {
            setLocation(0,getY());
        }

        if(y < 0)
        {
            setLocation(getX(),0);
        }

        if(x > (w.getWidth()-1))
        {
            setLocation((w.getWidth()-1),getY());
        }

        if(y > (getWorld().getHeight() - 1))
        {
            setLocation(getX(),(w.getHeight() - 1));
        }

        //Check for Block Collission
        List<Platform> plist = getIntersectingObjects(Platform.class);
        if(null != plist)
        {
            for(Platform p : plist)
            {
                //Check to see if it is a high hit or a low hit
                x = getX();
                y = getY();

                int px = p.getX();
                int py = p.getY();

                if((py - y) > 25)
                {
                    //This block is essentially below the helicopter
                    continue;
                }

                if((px - x) < 39 && (px - x) > 0)
                {
                    Dying = true;
                    deadCounter = 60;
                    setImage(dead);
                    break;
                }

                if((x - px) < 35 && (x - px) > 0)
                {
                    Dying = true;
                    deadCounter = 60;
                    setImage(dead);
                    break;
                }
            }
        }

        if(Fire)
        {

            List<Bullet> bulletlist = getObjectsInRange(80,Bullet.class);

            if(bulletlist.size() < 1)
            {
                Bullet bullet = new Bullet();
                bullet.setRotation(getRotation());
                getWorld().addObject(bullet,getX(),getY());
            }
            //Check for Other Bombs

            List<Bomb> bomblist = getWorld().getObjects(Bomb.class);
            /*
            for(Bomb b: bomblist)
            {
            return;
            }
             */

            if(bomblist.size() > 0)
            {
                return;
            }

            int cx = getX();
            int cy = getY();

            int chx = (cx - ox)*3/5;
            int chy = cy - oy;

            Bomb bomb = new Bomb();
            bomb.chx += chx;
            bomb.chy += chy;
            bomb.setRotation(getRotation());
            if(bomb.getRotation() > 270)
            {
                bomb.setRotation(360 -bomb.getRotation() );
            }
            getWorld().addObject(bomb,getX() - 5,getY() + 3);

        }
    }    
}
