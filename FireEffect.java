import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BulletFireActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FireEffect extends Actor
{
    public static GreenfootImage[] guy;

    static
    {
        guy  = new GreenfootImage[4];
        guy[0] = new GreenfootImage("bullet-01.png");
        guy[1] = new GreenfootImage("bullet-02.png");
        guy[2] = new GreenfootImage("bullet-03.png");
        guy[3] = new GreenfootImage("bullet-04.png");

    }

    public int counter = 0;

    
    public int speed = 0;

    public FireEffect()
    {
        int i;
        

        counter = Greenfoot.getRandomNumber(3);
        setImage(guy[counter]);

        /*start effect with a smaller fireball*/
        counter = 1;
        speed = 0;
    }

    /**
     * Act - do whatever the BulletFireActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        // Add your action code here.
        if(speed != 0)
        {
            move(speed);
        }

        //move(-1 * (Greenfoot.getRandomNumber(5)+2));
        if(counter >= 4)
        {
            getWorld().removeObject(this);
            return;
        }
        setImage(guy[counter]);
        counter++;
    }    

  
}
