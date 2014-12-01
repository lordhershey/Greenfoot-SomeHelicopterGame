import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * this helicopt just sits there and spimns its blades
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DummyCopter extends Player
{
    int counter = 0;
    
    public void act() 
    {
        counter = (counter + 1) % 4;
        setImage(image[counter]);
    }    
}
