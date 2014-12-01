import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SplashScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SplashScreen extends World
{

    boolean canPlay = false;
    /**
     * Constructor for objects of class SplashScreen.
     * 
     */
    public SplashScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 560, 1,false);
        canPlay = false;
        GameManager.resetAll();

        prepare();
    }

    
    @Override
    public void act()
    {
        boolean JumpKey = Greenfoot.isKeyDown("space");

        if((JumpKey && canPlay) || Greenfoot.mouseClicked(null))
        {
            GameManager.resetAll();
            PlatformerWorld bw = new PlatformerWorld();
            Greenfoot.setWorld((World)bw);
        }

        if(!JumpKey)
        {
            canPlay = true;
        }
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        Instructor instructor = new Instructor();
        addObject(instructor, 645, 445);
        HighScore highscore = new HighScore();
        addObject(highscore, 128, 311);
        highscore.setLocation(123, 308);
    }
}
