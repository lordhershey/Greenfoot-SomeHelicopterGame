import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Turret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Turret extends Monster
{

    boolean alive = true;
    int firerotation = 180;

    int counter = 0;
    
    static int BulletLimit = 10;

    static GreenfootImage[] image;
    static GreenfootImage brokenimage;
    static
    {
        image = new GreenfootImage[6];
        image[0] = new GreenfootImage("Turret1.png");
        image[1] = new GreenfootImage("Turret2.png");
        image[2] = new GreenfootImage("Turret3.png");
        image[3] = new GreenfootImage("Turret4.png");
        image[4] = new GreenfootImage("Turret5.png");
        image[5] = new GreenfootImage("Turret6.png");
        brokenimage = new GreenfootImage("TurretBroken.png");
    }

    public void addScore()
    {
        GameManager.addScore(200);
    }
    
    public void BombHit()
    {
        if(!alive)
        {
            return;
        }
        alive = false;
        setImage(brokenimage);
        
        addScore();
        
        World w = getWorld();
        
        for(int j = 0 ; j < 3 ; j++)
        {
            Explosion e = new Explosion();
            //e.setRotation(Greenfoot.getRandomNumber(360));
            w.addObject(e,getX() + Greenfoot.getRandomNumber(31) - 15 ,getY()-15 + Greenfoot.getRandomNumber(21) - 10);
        }

        for(int i = 0 ; i < 8; i++)
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

    boolean fireshot = false;
    
    public void act() 
    {
        if(!alive)
        {
            if(Greenfoot.getRandomNumber(20) == 8)
            {
                SparksEffect fe = new SparksEffect();
                fe.counter = 0;
                fe.setRotation(180 + 45 + Greenfoot.getRandomNumber(90));
                fe.speed = 2 + Greenfoot.getRandomNumber(3);
                getWorld().addObject(fe,getX()  ,
                    getY() + 10);

                if(Greenfoot.getRandomNumber(5) == 1)
                {
                    EnemyBullet eb = new EnemyBullet();
                    eb.setRotation(180 + 45 + Greenfoot.getRandomNumber(90));
                    getWorld().addObject(eb,getX(),getY());
                    eb.move(6);
                }
            }

            return;
        }

        List<Player> playerlist = getIntersectingObjects(Player.class);
        for(Player p: playerlist)
        {
            int xdiff = getX() - p.getX();
            int ydiff = getY() - p.getY();

            p.hit();
            setImage(brokenimage);
            alive = false;
            return;
        }

        
        //playerlist = getWorld().getObjects(Player.class);
        playerlist = getObjectsInRange(670,Player.class);
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

            if(degrees < 188)
            {
                setImage(image[0]);
                firerotation = 180;
            }
            else if (degrees < 188 + 15)
            {
                setImage(image[1]);
                firerotation = 180 + 15;
            }
            else if (degrees < 188 + 30)
            {
                setImage(image[2]);
                firerotation = 180 + 30;
            }
            else if (degrees < 188 + 45)
            {
                setImage(image[3]);
                firerotation = 180 + 45;
            }
            else if (degrees < 188 + 60)
            {
                setImage(image[4]);
                firerotation = 180 + 60;
            }
            else
            {
                setImage(image[5]);
                firerotation = 180 + 75;
            }
            
            if(!fireshot)
            {
                counter = (counter + 1) % 30;
            }
            
            if(fireshot || (counter == 0 && Greenfoot.getRandomNumber(2) == 0))
            {
                
                List<EnemyBullet> eblist = getWorld().getObjects(EnemyBullet.class);
                if(eblist.size() > BulletLimit)
                {
                    fireshot = true;
                    return;
                }
                
                fireshot = false;
                //fire the cannon
                EnemyBullet eb = new EnemyBullet();
                eb.setRotation(firerotation);
                getWorld().addObject(eb,getX(),getY());
                eb.move(6);
            }

            break;
        }

    }    
}
