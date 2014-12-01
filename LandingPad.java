import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LandingPad here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LandingPad extends Monster
{
    /**
     * Act - do whatever the LandingPad wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // check to see if player has landed on us.
        Player p = (Player)getOneIntersectingObject(Player.class);
        if(null != p)
        {
            int py = p.getY();
            int px = p.getX();
            
            if((getY() - py) < 6)
            {
                //System.out.println("Landed!");
                World w = getWorld();
                
                py = getY()-8;
                DummyCopter dc = new DummyCopter();
                
                w.removeObject(p);
                w.addObject(dc,px,py);
                
                w.addObject(new PlaceHolderLevelTransition(),400,280);
                //p.setLocation(p.getX(),py);
            }
        }
    }    
    
    public void addScore()
    {
    }
    
    public boolean isAlive()
    {
        return (true);
    }
    
    public void BombHit()
    {
        //indestrucable
    }
}
