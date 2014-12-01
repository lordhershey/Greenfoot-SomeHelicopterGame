import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InstructActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructor extends Actor
{
    static GreenfootImage image[];
    
    static
    {
        image = new GreenfootImage[3];
        image[0] = new GreenfootImage("helpscreensmall1.png");
        image[1] = new GreenfootImage("helpscreensmall2.png");
        image[2] = new GreenfootImage("helpscreensmall3.png");
       
    }
    
    long startTime = System.currentTimeMillis();
    int counter = 0;
    
    public void act() 
    {
        long endTime = System.currentTimeMillis();
        
        if((endTime - startTime) < 2500)
        {
            return;
        }
        
        counter = (counter + 1) % image.length;
        setImage(image[counter]);
        startTime = System.currentTimeMillis();
    }    
}
