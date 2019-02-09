package flockbase;

//import flockbase.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Bird026 extends Bird
{   
    public Bird026()
    {

    }

    public double getSpeed()
    { 
        return speed;
    }

    public void setSpeed(double s)
    {
        speed = s;
    }
    
    public boolean isLeader()
    {
        return isLeader;
    }

    public String getName()
    {
        if(isLeader)
            return "Leader026";
        return "Bird26";
    }

    public void becomeLeader()
    {
        this.isLeader = true;
    }

    public void retireLead()
    {
        this.isLeader = false;
    }

    public double distance(int dx, int dy)
    {
        return Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2))*1.0;
    }

    public double distanceBetween(Bird b1,Bird b2)
    {
        return distance(b1.getPos().getX()-b2.getPos().getX(), b1.getPos().getY()-b2.getPos().getY());
    }

    public boolean areBirdstooClose(Bird b1,Bird b2,double safeDist)
    {
        return distanceBetween(b1,b2) < safeDist;
    }

    public Position collisionCheck()
    {
        int x = 0;
        int y = 0;
        Flock flock = this.getFlock();

        for(Bird b:flock.getBirds())
        {
            if(b != this)
            {
                Position position_of_other_bird = b.getPos();
                Position position_of_this_bird = this.getPos();
                boolean isTooclose = areBirdstooClose(this,b,minSafeDistance);

                if(isTooclose)
                {
                    double dX = position_of_this_bird.getX() - position_of_other_bird.getX();
                    double dY = position_of_this_bird.getY() - position_of_other_bird.getY();
                    double sqrtSafeDist = Math.sqrt(minSafeDistance);
                    
                    if(dX < 0) 
                    {
                        dX = -sqrtSafeDist - dX;
                    }
                    else 
                    {
                        dX = sqrtSafeDist - dX;
                    }
                    
                    if(dY < 0) 
                    {
                        dY = -sqrtSafeDist - dY;
                    }
                    else 
                    {
                        dY = sqrtSafeDist - dY;
                    }

                    x += (int)dX;
                    y += (int)dY;      
                }
            }
        }

        Position newPos = new Position(0,0);
        newPos.setPos(x,y);
        return newPos;        
    }

    protected void updatePos()
    {
        Position currentPos = this.getPos();
        int x = currentPos.getX();
        int y = currentPos.getY();
        if(!this.isLeader)
        {
            Position LeaderPos = this.getFlock().getLeader().getPos();
            setTarget(LeaderPos.getX(), LeaderPos.getY());
        }
        
        double change_in_x = 0.0;
        double change_in_y = 0.0;
        int xt = this.getTarget().getX();
        int yt = this.getTarget().getY(); 
        double dist = distance(xt-x,yt-y);
        if(dist < 10.0)
        {
            change_in_x = 0.0;
            change_in_y = 0.0;
        }
        else if(xt == x)
        {
            if(yt > y)
                change_in_y = 1.0;
            else
                change_in_y = -1.0;
            change_in_x = 0.0;
        }
        else if(yt == y)
        {
            if(xt > x)
                change_in_x = 1.0;
            else
                change_in_x = -1.0;
            change_in_y = 0.0;
        }
        else
        {
            double mx = (double)(yt - y) / (xt - x);
            double my = (double)(xt - x) / (yt - y);
            if(xt > x)
                change_in_x = 1.0;
            else
                change_in_x = -1.0;

            if(mx >= 10 || mx <= -10)
            {
                change_in_y *= this.getSpeed();
                change_in_x = my * change_in_y;
            }
            else
            {
                change_in_x *= this.getSpeed();
                change_in_y = mx * change_in_x;
            }
        }

        int Total_change_in_x = x + (int)change_in_x + collisionCheck().getX();
        int Total_change_in_y = y + (int)change_in_y + collisionCheck().getY();
        Flock flock = this.getFlock();
        ArrayList<Bird> birds = flock.getBirds();
        for(Bird b : birds)
        {
            int xtemp = b.getPos().getX();
            int ytemp = b.getPos().getY();
            if(xtemp - Total_change_in_x > -10.0 && xtemp - Total_change_in_x < 10.0)
            {
                if(ytemp - Total_change_in_y > -10.0 && ytemp - Total_change_in_y < 10.0)
                {
                    if(this.isLeader())
                        b.setPos(xtemp + 20, ytemp + 20);
                    else
                    {
                        change_in_x = 0; change_in_y = 0;
                    }
                }
            }           
        }

        this.setPos(Total_change_in_x, Total_change_in_y);   
    }


    private double speed = 8.0;
    private boolean isLeader;
    private double minSafeDistance = 20.0;

}
