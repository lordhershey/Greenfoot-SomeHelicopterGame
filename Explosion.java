import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Actor
{
    int counter = 0;

    static GreenfootImage[] image;

    static 
    {
        image = new GreenfootImage[8];
        image[0] = new GreenfootImage("Explosion1.png");
        image[1] = new GreenfootImage("Explosion2.png");
        image[2] = new GreenfootImage("Explosion3.png");
        image[3] = new GreenfootImage("Explosion4.png");
        image[4] = new GreenfootImage("Explosion5.png");
        image[5] = new GreenfootImage("Explosion6.png");
        image[6] = new GreenfootImage("Explosion7.png");
        image[7] = new GreenfootImage("Explosion8.png");
    }

    public Explosion()
    {
        counter = 0;
    }
    
    public void act() 
    {
        setRotation(Greenfoot.getRandomNumber(360));
        setImage(image[counter/3]);
        counter++;
        
        if((counter/3) >= image.length)
        {
            getWorld().removeObject(this);
            return;
        }
    }    
}
