import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class SmallRocket here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SmallRocket extends EnemyBullet
{
    boolean goleft = true;
    
    public void act() 
    {
        if(!alive || getX() < -20 || getY() < -20)
        {
            alive = false;
            getWorld().removeObject(this);
            return;
        }

        move(speed);

        int rot = getRotation();
        
        for(int i = 0 ; i < 3 ; i++)
            {
                FireEffect fe = new FireEffect();
                int rx = Greenfoot.getRandomNumber(5)-2;
                int ry = Greenfoot.getRandomNumber(5)-2;

                int yspace = 5;

                int iix = getX();
                int iiy = getY();

                getWorld().addObject(fe,iix,iiy);
                fe.setRotation(rot + 170 + Greenfoot.getRandomNumber(21));
                fe.speed = 4 + Greenfoot.getRandomNumber(4);
            }
        
        
        if(goleft)
        {
            if(rot > 135)
            {
                rot--;
                setRotation(rot);
            }
        }
        
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
