import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Copter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Copter extends Monster
{
    static GreenfootImage[] images = new GreenfootImage[4];
    boolean alive = true;
    long starttime = System.currentTimeMillis();
    int cnt = 0;
    boolean moveback = false;

    boolean broken = false;

    static{
        images[0] = new GreenfootImage("EnemyCopter-1.png");
        images[1] = new GreenfootImage("EnemyCopter-2.png");
        images[2] = new GreenfootImage("EnemyCopter-3.png");
        images[3] = new GreenfootImage("EnemyCopter-Broken.png");
    }

    public void addScore()
    {
        GameManager.addScore(400);
    }

    public void BombHit()
    {
        World w = getWorld();
        Explosion e = new Explosion();
        //e.setRotation(Greenfoot.getRandomNumber(360));
        w.addObject(e,getX(),getY()-10);
        broken = true;
        alive = false;
        setImage(images[3]);

        addScore();

        for(int i = 0 ; i < 7; i++)
        {
            SparksEffect fe = new SparksEffect();
            fe.counter = 0;
            fe.setRotation(Greenfoot.getRandomNumber(360));
            fe.speed = 2 + Greenfoot.getRandomNumber(3);
            getWorld().addObject(fe,getX() + Greenfoot.getRandomNumber(getImage().getWidth()) - getImage().getWidth()/2 ,
                getY() + Greenfoot.getRandomNumber(getImage().getHeight()) - getImage().getHeight()/2 -10);
        }
    }

    public boolean isAlive()
    {
        return alive;
    }

    
    public void act() 
    {
        long endtime = System.currentTimeMillis();

        if(broken)
        {
            int y = getY();
            y += 7;
            int x = getX() - 5 + Greenfoot.getRandomNumber(15);
            setLocation(getX(),y);
            World w = getWorld();
            cnt = (cnt + 1) % 3;
            if(cnt == 0)
            {
                Explosion e = new Explosion();
                //e.setRotation(Greenfoot.getRandomNumber(360));
                w.addObject(e,getX() - 10 + Greenfoot.getRandomNumber(20),getY()-10);
            }
            if(y > 590)
            {
                broken = false;  
            }
            return;
        }

        if(!alive || getX() < -20)
        {
            //we explode and disappear
            getWorld().removeObject(this);
            return;
        }

        if(/*(endtime - starttime) > 200*/true)
        {
            //starttime = System.currentTimeMillis();
            cnt = (cnt + 1) % 3;
            setImage(images[cnt]);
        }

        if((endtime - starttime) > 1000)
        {
            starttime = System.currentTimeMillis();
            SmallRocket eb = new SmallRocket();
            eb.setRotation(180 + 30);
            getWorld().addObject(eb,getX(),getY());
            eb.move(1);
        }

        if(!moveback && getX() < 450)
        {
            moveback = true;
        }
        else if(moveback && getX() > 550)
        {
            moveback = false;
        }

        if(moveback)
        {
            int x = getX();
            x = x + 3 + Greenfoot.getRandomNumber(4);
            setLocation(x,getY());
        }
    }    
}
