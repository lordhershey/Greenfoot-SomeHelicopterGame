import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Monster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Monster extends Actor
{
    public abstract void BombHit();
    
    public abstract boolean isAlive();
    
    public abstract void addScore();
}
