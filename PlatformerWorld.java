import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;
import java.awt.image.*;
import java.awt.Graphics;

/**
 * Write a description of class PlatformerWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlatformerWorld extends World
{

    
    public PlatformerWorld()
    {    

        super(800, 560, 1,false);

        makeMap();

        prepare();
        update();

        Star.setBounds(getWidth(),getHeight());

        for(int i = 0 ; i < 300 ; i++)
        {
            Star s = new Star(Greenfoot.getRandomNumber(getWidth()),Greenfoot.getRandomNumber(getHeight()));
            stars.add(s);
        }
        drawBackground();

        setPaintOrder(Missile.class,FireEffect.class);
        //Move this to splash screen
        //GameManager.resetAll();
    }

    public void nextLevel()
    {

    }

    public CavernMap cavern = new CavernMap();
    public PlatformMap map = new CavernMap();/*PlatformMap();*/
    
    //GreenfootImage mapImg = map.getImage();
    public int MAPIMGWIDTH = 0;
    public int MAPIMGHEIGHT = 0;

    //Platform platformTemplate = new Platform(0,0);
    //GreenfootImage pfImg = platformTemplate.getImage();
    public final int PLATFORMHEIGHT = 30;
    public final int PLATFORMWIDTH = 30;
    public int MAPWIDTH =0;
    public int MAPHEIGHT = 0;

    public List<Platform> thePlatforms = new ArrayList<Platform>();

    public List<Spawner> theSpawners = new ArrayList<Spawner>();

    public int leftBound = 0;
    public int bottomBound = 0;
    public int topBound = 0;
    public int rightBound = 0;

    public List<Star> stars = new ArrayList<Star>();

    public int noBulletCounter = 0;

    public int ScrollSpeed = 4;
    @Override
    public void act()
    {

        if(noBulletCounter > 0)
        {
            removeObjects(getObjects(EnemyBullet.class));
            noBulletCounter--;
        }

        if(rightBound >= MAPWIDTH-1)
        {
            //Stop Here
            return;
        }

        List<Player>plist = getObjects(Player.class);

        boolean hasPlayer = false;
        for(Player p : plist)
        {
            hasPlayer = true;
            if(p.alive())
            {
                if(canScrollRight())
                {
                    shiftScreen(ScrollSpeed,0);
                }
                break;
            }
        }

        if(!hasPlayer)
        {
            GameManager.lives--;

            if(GameManager.lives < 1)
            {
                GameManager.saveScore();
                //TODO put You Lose Message or Animation, but for now just go
                //to splash screen
                World w = (World)new SplashScreen();
                Greenfoot.setWorld(w);
                return;
            }

            removeObjects(getObjects(EnemyBullet.class));
            noBulletCounter = 15;
            addObject(new Player(), 135, 25);
            addObject(new CountDown(),400,280);
        }
    }

    void drawBackground()
    {
        GreenfootImage gi = getBackground();
        BufferedImage bi = gi.getAwtImage();
        Graphics g = bi.getGraphics();
        g.setColor(Color.black);
        g.clearRect(0,0,getWidth(),getHeight());
        for(Star s : stars)
        {
            s.draw(g);
        }
    }

    public void addSpawner(Spawner s)
    {
        theSpawners.add(s);
    }

    public void newLevel()
    {
        removeObjects(getObjects(Actor.class));
        thePlatforms = new ArrayList<Platform>();
        theSpawners = new ArrayList<Spawner>();

        /*
        if(!map.moreLevels())
        {
        VictoryScreen bw = new VictoryScreen();
        Greenfoot.setWorld((World)bw);
        return;
        }
         */

        makeMap();
        prepare();
        update();
    }

    public void makeMap()
    {
        GreenfootImage mapImg = null;
        
        //mapImg = map.getImage(); //map.getNextImage();
        mapImg = cavern.getImage();
        
        int MAPIMGWIDTH = mapImg.getWidth();
        int MAPIMGHEIGHT = mapImg.getHeight();

        //Platform platformTemplate = new Platform(0,0);
        //GreenfootImage pfImg = platformTemplate.getImage();
        MAPWIDTH = MAPIMGWIDTH * PLATFORMWIDTH;
        MAPHEIGHT = MAPIMGHEIGHT * PLATFORMHEIGHT;

        leftBound = 0;
        bottomBound = MAPHEIGHT;
        topBound = MAPHEIGHT - getHeight();
        rightBound = getWidth();

        for(int x = 0 ; x < MAPIMGWIDTH ; x++)
        {

            for(int y = 0 ; y < MAPIMGHEIGHT ; y++)
            {
                Color c = mapImg.getColorAt(x,y);
                if((c.equals(Color.black)))
                {
                    //Ground
                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                    thePlatforms.add(new Platform(mapX,mapY));
                    //System.out.println("Add A Block " + mapX + " " + mapY);
                }

                if(c.getRed() == 0x00 && c.getGreen() == 0x00 && c.getBlue() == 0xff)
                {
                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                    theSpawners.add(new Spawner(mapX,mapY,new Copter()));
                }
                
                if(c.getRed() == 255 && c.getGreen() == 138 && c.getBlue() == 0)
                {

                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                    //thePlatforms.add(new LandingPad(mapX,mapY));
                    theSpawners.add(new Spawner(mapX,mapY, new LandingPad()));
                }

                if(c.getRed() == 0 && c.getGreen() == 255 && c.getBlue() == 0 )
                {
                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                    theSpawners.add(new Spawner(mapX,mapY-15,new Fuel()));
                }

                if(c.getRed() == 255 && c.getGreen() == 0 && c.getBlue() == 240)
                {
                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                    theSpawners.add(new Spawner(mapX,mapY - 23,new Radar()));
                }

                if(c.getRed() == 172 && c.getGreen() == 172 && c.getBlue() == 172 )
                {

                    //Bar Spawner
                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                    theSpawners.add(new Spawner(mapX-3,mapY-4,new Turret()));
                }
                /*
                if(c.getRed() == 0 && c.getGreen() == 118 && c.getBlue() == 227)
                {
                //Square Spawner
                int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                theSpawners.add(new Spawner(mapX,mapY,new SquaresGuy()));
                }
                 */

                if(c.getRed() == 255 && c.getGreen() == 0 && c.getBlue() == 0)
                {
                    //Square Spawner
                    int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2 - 12;
                    Missile tmpM = new Missile();
                    tmpM.setRotation(270);
                    theSpawners.add(new Spawner(mapX,mapY,tmpM));
                }

                /*
                if(c.getRed() == 255 && c.getGreen() == 246 && c.getBlue() == 0)
                {
                //Square Spawner
                int mapX = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                int mapY = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;
                theSpawners.add(new Spawner(mapX,mapY,new KeyHole()));
                }
                 */

                if(c.getRed() == 252 && c.getGreen() == 255 && c.getBlue() == 0)
                {
                    /*No Screen Shift so you have to place in picture somewhere to front 
                    we will not scroll the world to the players location*/
                    Player player = new Player();
                    int px = x * PLATFORMWIDTH + PLATFORMWIDTH/2;
                    int py = y * PLATFORMHEIGHT + PLATFORMHEIGHT/2;

                    addObject(player, px, py);
                }
            }
        }
    }

    public void shiftScreen(int changeX,int changeY)
    {
        leftBound += changeX;
        rightBound += changeX;

        topBound -= changeY;
        bottomBound -= changeY;
        int absChx = Math.abs(changeX);
        int absChy = Math.abs(changeY);

        if(absChx < 1)
        {
            absChx = 1;
        }
        if(absChy < 1)
        {
            absChy = 1;
        }

        for(Star s : stars)
        {
            s.move(-(changeX/absChx),(changeY/absChy));
        }

        drawBackground();

        if(leftBound < 0)
        {
            leftBound = 0;
            rightBound = getWidth();
        }

        if(rightBound > MAPWIDTH)
        {
            rightBound = MAPWIDTH;
            leftBound = MAPWIDTH - getWidth();
        }

        if(topBound < 0)
        {
            topBound = 0;
            bottomBound = getHeight();
        }

        if(bottomBound > MAPHEIGHT)
        {
            topBound = MAPHEIGHT - getHeight();
            bottomBound = MAPHEIGHT;
        }

        update();
        //Adjust all flying bullets
        /*
        List<Bullet> bullets = getObjects(Bullet.class);
        for(Bullet b : bullets)
        {
        int x = b.getX() - changeX;
        int y = b.getY() + changeY;
        b.setLocation(x,y);
        }
         */
        /*
        List<LifeBall> blist = getObjects(LifeBall.class);
        for(LifeBall ball : blist)
        {
        int x = ball.getX() - changeX;
        int y = ball.getY() + changeY;
        ball.setLocation(x,y);
        }
         */
        List<Actor> monsterList = getObjects(Actor.class);
        for(Actor monster : monsterList)
        {

            if(monster instanceof Player)
            {
                continue;
            }

            if(monster instanceof Platform)
            {
                continue;
            }

            if(monster instanceof Bullet)
            {
                continue;
            }

            if(monster instanceof ScoreBox)
            {
                continue;
            }

            if(monster instanceof LivesBox)
            {
                continue;
            }

            int x = monster.getX() - changeX;
            int y = monster.getY() + changeY;
            monster.setLocation(x,y);
        }

        /*
        List<Monster> monsterList = getObjects(Monster.class);
        for(Monster monster : monsterList)
        {
        int x = monster.getX() - changeX;
        int y = monster.getY() + changeY;
        monster.setLocation(x,y);
        }

        List<FireEffect> felist = getObjects(FireEffect.class);
        for(FireEffect monster : felist)
        {
        int x = monster.getX() - changeX;
        int y = monster.getY() + changeY;
        monster.setLocation(x,y);
        }

        List<Explosion> explist = getObjects(Explosion.class);
        for(Explosion monster : explist)
        {
        int x = monster.getX() - changeX;
        int y = monster.getY() + changeY;
        monster.setLocation(x,y);
        }
         */
    }

    public void update()
    {
        int thisPlatformX;
        int thisPlatformY;
        int screenX;
        int screenY;

        final int marginX = 200;
        final int marginY = 200;

        for( Platform thisPlatform : thePlatforms)
        {

            thisPlatformX = thisPlatform.mapX;
            thisPlatformY = thisPlatform.mapY;

            //TODO extend a little out of bounds we must keep
            //track of enemies and etc
            if(thisPlatformX >= (leftBound - marginX) &&
            thisPlatformX <= (rightBound + marginX) &&
            thisPlatformY >= (topBound - marginY) &&
            thisPlatformY <= (bottomBound + marginY))
            {
                screenX = thisPlatformX - leftBound;
                screenY = thisPlatformY - topBound;

                if(thisPlatform.getWorld() == null)
                {
                    //System.out.println("Block is in out world " + screenX + " " + screenY);
                    addObject(thisPlatform,screenX,screenY);
                }
                else
                {
                    thisPlatform.setLocation(screenX,screenY);
                }
            }
            else
            {
                if(thisPlatform.getWorld() != null)
                {
                    removeObject(thisPlatform);
                }
            }

        }

        ArrayList<Spawner> theNewSpawners = new ArrayList<Spawner>();
        for( Spawner thisPlatform : theSpawners)
        {

            thisPlatformX = thisPlatform.mapX;
            thisPlatformY = thisPlatform.mapY;

            if(null == thisPlatform.ref)
            {
                continue;
            }

            theNewSpawners.add(thisPlatform);

            //TODO extend a little out of bounds we must keep
            //track of enemies and etc
            if(thisPlatformX >= (leftBound - marginX*2) &&
            thisPlatformX <= (rightBound + marginX*2) &&
            thisPlatformY >= (topBound - marginY*2) &&
            thisPlatformY <= (bottomBound + marginY*2))
            {
                screenX = thisPlatformX - leftBound;
                screenY = thisPlatformY - topBound;

                if(thisPlatform.getWorld() == null)
                {
                    //System.out.println("Block is in out world " + screenX + " " + screenY);
                    addObject(thisPlatform,screenX,screenY);
                }
                else
                {
                    thisPlatform.setLocation(screenX,screenY);
                }
            }
            else
            {
                if(thisPlatform.getWorld() != null)
                {
                    removeObject(thisPlatform);
                }
            }

        }
        theSpawners = theNewSpawners;

    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        //Player player = new Player();
        //addObject(player, 293, 299);
        //LifeBar lifebar = new LifeBar();
        //addObject(lifebar, 23, 60);  
        //ScoreBox scorebox = new ScoreBox();
        //addObject(scorebox, 115, 20);

        ScoreBox scorebox = new ScoreBox();
        addObject(scorebox, 680, 19);

        LivesBox livesbox = new LivesBox();
        addObject(livesbox, 685, 49);
        livesbox.setLocation(679, 40);
        //scorebox.setLocation(679, 13);

        addObject(new CountDown(),400,280);
    }

    public boolean canScrollLeft()
    {
        if(leftBound < 1)
        {
            return(false);
        }
        return true;
    }  

    public boolean canScrollRight()
    {
        if(rightBound < MAPWIDTH)
        {
            return true;
        }
        return false;
    }

    public boolean canScrollUp()
    {
        if(topBound < 1)
        {
            return false;
        }
        return true;
    }

    public boolean canScrollDown()
    {
        if(bottomBound < MAPHEIGHT)
        {
            return true;
        }

        return false;
    }
}
