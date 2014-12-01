import java.awt.*;
import java.awt.image.*;
import greenfoot.*;

/**
 * Write a description of class Star here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Star  
{
    int x = 0;
    int y = 0;
    int size = 1;
    int colorIdx;

    static int sx = 0;
    static int sy = 0;
    
    public static void setBounds(int sx,int sy)
    {
        Star.sx = sx;
        Star.sy = sy;
    }
    
    public Star(int x, int y)
    {
        this.x = x;
        this.y = y;
        size = Greenfoot.getRandomNumber(3) + 1;
        colorIdx = Greenfoot.getRandomNumber(6);
    }

    
    public void move(int chx, int chy)
    {
        x = x + chx * size;
        y = y + chy * size;
        boolean flipx = false;
        boolean flipy = false;
        if(x > sx)
        {
            x = x - sx;
            flipx = true;
        }
        if(x < 0)
        {
            x = sx + x;
            flipx = true;
        }
        if(y > sy)
        {
            y = y - sy;
            flipy = true;
        }
        if( y < 0)
        {
            y = sy + y;
            flipy = true;
        }

        if(flipx || flipy)
        {
            size = Greenfoot.getRandomNumber(3) + 1;
            colorIdx = Greenfoot.getRandomNumber(6);

            if(flipx)
            {
                y = Greenfoot.getRandomNumber(sy);
            }
            if(flipy)
            {
                x = Greenfoot.getRandomNumber(sx);
            }
        }

    }
    public void draw(Graphics g)
    {
        switch (colorIdx)
        {
            case 0:
            g.setColor(Color.green);
            break;
            case 1:
            g.setColor(Color.blue);
            break;
            case 2:
            g.setColor(Color.red);
            break;
            case 3:
            g.setColor(Color.cyan);
            break;
            case 4:
            g.setColor(Color.magenta);
            break;
            default:
            g.setColor(Color.orange);
            break;
        }

        g.drawRect(x,y,size,size);
    }
}
