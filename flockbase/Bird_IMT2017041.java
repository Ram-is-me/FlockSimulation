package flockbase;

//import flockbase.*;
//import flockbase.Bird;
//import flockbase.Flock;
//import flockbase.Position;
import java.util.ArrayList;

public class Bird_IMT2017041 extends flockbase.Bird
{
  // private int speed = 10;
  private Position  _x_and_y_speed = new Position(10,10);  // both x and y direction speeds to be 10
  private boolean amLeader;
  private double range = 40.0; 
  
  public void set_range(double d)
  {
    this.range = d;
  } 
  public double get_range()
  {
    return range;
  }
  public void set_xyspeed(int x,int y)
  {
    this._x_and_y_speed.setPos(x,y);
  }
  public Position get_xyspeed()
  {
    return _x_and_y_speed;
  }

  public Bird_IMT2017041()
  {
    //default constructor
    super();
  }

  public double dis_btw_birds(Bird bird) // gives distance between this bird and the neighbour bird
  {
    double x_me,y_me,x_bird,y_bird;
    double dis;
    x_me = this.getPos().getX();
    y_me = this.getPos().getY();
    x_bird = bird.getPos().getX();
    y_bird = bird.getPos().getY();
    dis =  Math.sqrt(Math.pow((x_bird-x_me),2)+Math.pow((y_bird-y_me),2));
    return dis;
  }

  public boolean no_collision_with_other_birds()
  {
    ArrayList<Bird> birds_in_flock = getFlock().getBirds();
    for(Bird bird : birds_in_flock) 
    {
      if(bird != this) 
      {
        double d = dis_btw_birds(bird);
        if(d<range)
        {
          return false;
        }
        else
        {
          return true;
        }
      }
    }
    return true;
  }

  public boolean collision_with_target() // gives if a bird and collide with target
  {
    boolean chance;
    double dis_target;
    double x_me,y_me,x_target,y_target;
    x_target = this.getTarget().getX();
    y_target = this.getTarget().getY();
    x_me = this.getPos().getX();
    y_me = this.getPos().getY();
    dis_target =  Math.sqrt(Math.pow((x_target-x_me),2)+Math.pow((y_target-y_me),2));
    if(dis_target <= range)
    {
      chance = true;
    }
    else
    {
      chance = false;
    }
    return chance;
  }  

  public Position newpos_to_matchposition() 
  {
    Position newpos = new Position(0,0);
    ArrayList<Bird> birds_in_flock = getFlock().getBirds();

    int x_avg = 0;
    int y_avg = 0;
    
    for(Bird bird : birds_in_flock) 
    {
      if(bird != this) 
      {
        x_avg = x_avg + bird.getPos().getX();
        y_avg = y_avg + bird.getPos().getY();
      }
    }

    x_avg = x_avg/(birds_in_flock.size()-1);
    y_avg = y_avg/(birds_in_flock.size()-1);
    x_avg = (x_avg - this.getPos().getX())/100;
    y_avg = (y_avg - this.getPos().getY())/100;

    newpos.setPos(x_avg,y_avg);
    return newpos;
  }

  public Position newpos_to_matchxyspeed()
  {
    Position newpos = new Position(0,0);
    ArrayList<Bird> birds_in_flock = getFlock().getBirds();

    int xspeed_avg = 0;
    int yspeed_avg = 0;
    
    for(Bird bird : birds_in_flock) 
    {
      if(bird != this) 
      {
        xspeed_avg = xspeed_avg + bird.getMaxSpeed();
        yspeed_avg = yspeed_avg + bird.getMaxSpeed();
      }
    }

    xspeed_avg = xspeed_avg/(birds_in_flock.size()-1);
    yspeed_avg = yspeed_avg/(birds_in_flock.size()-1);
    xspeed_avg = (xspeed_avg - this.get_xyspeed().getX())/8;
    yspeed_avg = (yspeed_avg - this.get_xyspeed().getY())/8;

    newpos.setPos(xspeed_avg,yspeed_avg);
    return newpos;
  }

  public Position newpos_to_avoid_collision()
  {
    Position newpos = new Position(0,0);
    int x_new = 0;
    int y_new = 0;
    int x_dif;
    int y_dif;
    
    for(Bird b: getFlock().getBirds()) 
    {
      if(b != this)
      {
        x_dif = Math.abs(b.getPos().getX() - this.getPos().getX());
        y_dif = Math.abs(b.getPos().getY() - this.getPos().getY());
        if((x_dif < range) && (y_dif < range))
        {
          x_new = x_new - (b.getPos().getX() - this.getPos().getX()); // to move away from the bird b
          y_new = y_new - (b.getPos().getY() - this.getPos().getY()); // to move away from the bird b
        }
      }
    }
    newpos.setPos(x_new,y_new);
    return newpos;
  }

  public String getName()
  {
     if(amLeader) // to differentiate between a leader and normal bird
     {
       return "Leader041";
     }
    
      return "Bird041";
  }
  
  // public Position 

  protected void updatePos()
  {
    Position current_Pos = getPos();
    int x = current_Pos.getX();
    int y = current_Pos.getY();
    int xt = getTarget().getX();
    int yt = getTarget().getY();
    
    if (!amLeader) 
    {
      Position leader_pos = getFlock().getLeader().getPos();
      setTarget(leader_pos.getX(), leader_pos.getY());
    }

    double dy = 0.0;
    double dx = 0.0;

    if ((xt == x) && (yt == y))
    {
      dx = 0.0;
      dy = 0.0; 
    } 
    else 
    {
      if (xt == x) 
      {
        if (yt > y) 
        {
          dy = 1.0;
        } 
        else
        {
          dy = -1.0D;
        }  
        dx = 0.0D; 
      } 
      else 
      {
        if (yt == y)
        { 
          if (xt > x)
          {
            dx = 1.0;
          } 
          else
          {
            dx = -1.0;
          }  
          dy = 0.0;
        } 
        else 
        {
          double m = (yt - y) / (xt - x);
          if (xt > x) 
          {
            dx = 1.0;
          } 
          else
          {
            dx = -1.0;
          } 
          if(m > 10 || m < -10)
          {
            if(m>0)
            {
              m = 10;
            }
            else
            {
              m = -10;
            }
          }
          dx = dx*_x_and_y_speed.getX();
          dy = m * dx;
        }
      }
    }
    
    Position p1,p2,p3;
    p1 = newpos_to_avoid_collision();
    p2 = newpos_to_matchposition();
    p3 = newpos_to_matchxyspeed();
    
    double new_xspeed = _x_and_y_speed.getX() + p3.getX();
    double new_yspeed = _x_and_y_speed.getY() + p3.getY();
    set_xyspeed((int)new_xspeed, (int)new_yspeed);

    double new_x = dx + p1.getX() + p2.getX() + p3.getX();
    double new_y = dy + p1.getY() + p2.getY() + p3.getY();

    if((x+(int)new_x) < 1000 || (y+(int)new_y) < 1000 || (x+(int)new_x) > 0 || (y+(int)new_y) > 0)
    {
      
      setPos(x+(int)new_x,y+(int)new_y);
      // if(no_collision_with_other_birds() == false)
      // {
      //   setPos(x+(int)new_x+50,y+(int)new_y+50);
      // }
      
    }
    else
    {
      setPos(x, y);
    }
  }
  

  public void becomeLeader()
  {
    // System.out.println("lead " + this);
    amLeader = true;
  }
  
  public void retireLead()
  {
    // System.out.println("retire " + this);
    amLeader = false;
    // this.
  }
}
