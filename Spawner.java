import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spawner extends Actor
{
    public int mapX;
    public int mapY;
    public Monster ref;
    
    public Spawner(int mapX, int mapY,Monster ref)
    {
        this.mapX = mapX;
        this.mapY = mapY;
        this.ref = ref;
    }
    public void act() 
    {
        if(null == ref)
        {
            return;
        }
        getWorld().addObject(ref,mapX - ((PlatformerWorld)getWorld()).leftBound,mapY - ((PlatformerWorld)getWorld()).topBound);
        this.ref = null;
        getWorld().removeObject(this);
    }    
}
