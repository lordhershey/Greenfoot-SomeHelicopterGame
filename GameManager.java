import greenfoot.*;

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public enum GameManager  
{
    INSTANCE;
    
    public static int lives = 3;
    public static int level = 0;
    public static int score = 0;
    
    public static int MissileDivisor = 4;
    
    public static void setMissileDivisor(int n)
    {
        MissileDivisor = n;
        if(MissileDivisor < 1)
        {
            MissileDivisor = 1;
        }
    }
    
    public static int getMissileDivisor()
    {
        return MissileDivisor;
    }
    
    public static void resetAll()
    {
        lives = 3;
        level = 0;
        score = 0;
        MissileDivisor = 4;
    }
    
    public static void setLevel(int n)
    {
        level = n;
        if(n < 1)
        {
            resetAll();
            return;
        }
        
        if (n > 3)
        {
            n = 3;
        }
        
        setMissileDivisor(4-n);
    }
    
    public static void addScore(int s)
    {
        score += s;
    }
    
    public static int getScore()
    {
        return score;
    }
    
    public static int getLives()
    {
        return lives;
    }

    public static void saveScore()
    {
        if(UserInfo.isStorageAvailable())
        {
            UserInfo me = UserInfo.getMyInfo();
            if(null == me)
            {
                System.out.println("Not Signed in");
            }
            else
            {
                if(me.getScore() < score)
                {
                    me.setScore(score);
                    me.store();
                }
            }
        }
    }
}
