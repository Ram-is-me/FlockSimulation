package flockbase;
import java.lang.*;
import java.util.*;
import flockbase.*;


public class Bird521 extends Bird{
    public Bird521()
    {
        super();
        isLeader = false;
        speed = 10;   //Move per coordinate
        trackingtoggle=0;
    }

    public Bird521(Bird other)
    {
        this.isLeader = false;
        this.speed = 10;
        this.trackingtoggle = 0;
        this.setPos(other.getPos().getX(), other.getPos().getY());
        this.setTarget(other.getTarget().getX(), other.getTarget().getY());
        this.setFlock(other.getFlock());
    }

    public void becomeLeader()
    {
        isLeader = true;
    }

    public void retireLead()
    {
        isLeader = false;
    } 

    public String getName()
    {
        if(isLeader)
            return "Ram-Leader";
        else
            return "Ram";
    }
    public int collisionDetection(int i1, int i2)  //Can perform Collision Detection across 8 directions
    {
        Flock f = this.getFlock();
        ArrayList<Bird> b = f.getBirds();
        int parameter = 18;
        
        for(int i=0;i<b.size();i++)
        {
            Position temp = b.get(i).getPos();
            if(b.get(i).getPos().getX() != this.getPos().getX() || b.get(i).getPos().getY() != this.getPos().getY())
            {
                if(Math.abs(temp.getX() - (this.getPos().getX()+i1*speed))<parameter && Math.abs(temp.getY() - (this.getPos().getY()+i2*speed))<parameter)
                {
                    // System.out.println("Coll1 " + temp.getX() + " " + (this.getPos().getX()+i1*speed));
                    return 1;
                }
            }
            
        }
        return 0;
    }

    public void updatePos()
    {
        if(this.isLeader == true)
        {
            this.setTarget(this.getFlock().getLeader().getTarget().getX(),this.getFlock().getLeader().getTarget().getY());   
            //I believe this might not be necessary, but can act as a safety net for implementation
        }
        else if(trackingtoggle == 2 )
        {
            this.setTarget(this.getFlock().getLeader().getPos().getX(),this.getFlock().getLeader().getPos().getY());
            // trackingtoggle = false;
            trackingtoggle=0;
        }
        else
        {
            this.setTarget(this.getFlock().getLeader().getTarget().getX(),this.getFlock().getLeader().getTarget().getY());
            // trackingtoggle = true;
            // trackingtoggle = 0;
            trackingtoggle++;
        }
        Position temp = this.getPos();
        Position temp2 = this.getTarget();
        int diffx, diffy;
        diffx = temp2.getX();
        diffx -= temp.getX();
        diffy = temp2.getY();
        diffy -= temp.getY();
        if(diffx > 0 && diffy > 0)
        {
            if(collisionDetection(1,1)==0)
                this.setPos(temp.getX()+speed, temp.getY()+speed);
            else if(collisionDetection(0,1) == 0)
                this.setPos(temp.getX(), temp.getY()+speed);
            else if(collisionDetection(1,0) == 0)
                this.setPos(temp.getX()+speed, temp.getY());
            else if(collisionDetection(0,-1) == 0)
                this.setPos(temp.getX(), temp.getY()-speed);
            else if(collisionDetection(-1,0) == 0)
                this.setPos(temp.getX()-speed, temp.getY());
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX()-speed, temp.getY()-speed);

        }
        else if(diffx < 0 && diffy < 0)
        {
            if(collisionDetection(-1,-1)==0)
                this.setPos(temp.getX()-speed, temp.getY()-speed);
            else if(collisionDetection(0,-1)==0)
                this.setPos(temp.getX(), temp.getY()-speed);
            else if(collisionDetection(-1,0)==0)
                this.setPos(temp.getX()-speed, temp.getY());     
            else if(collisionDetection(0,1) == 0)
                this.setPos(temp.getX(), temp.getY()+speed);
            else if(collisionDetection(1,0) == 0)
                this.setPos(temp.getX()+speed, temp.getY());
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());             
            else
                this.setPos(temp.getX()+speed, temp.getY()+speed);
        }
        else if(diffx > 0 && diffy < 0)
        {
            if(collisionDetection(1,-1)==0)
                this.setPos(temp.getX()+speed, temp.getY()-speed);
            else if(collisionDetection(0,-1)==0)
                this.setPos(temp.getX(), temp.getY()-speed);
            else if(collisionDetection(1,0)==0)
                this.setPos(temp.getX()+speed, temp.getY());
            else if(collisionDetection(0,1) == 0)
                this.setPos(temp.getX(), temp.getY()+speed);
            else if(collisionDetection(-1,0) == 0)
                this.setPos(temp.getX()-speed, temp.getY());
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX()-speed, temp.getY()+speed);
        }
        else if(diffx < 0 && diffy > 0)
        {
            if(collisionDetection(-1,1)==0)
                this.setPos(temp.getX()-speed, temp.getY()+speed);
            else if(collisionDetection(0,1)==0)
                this.setPos(temp.getX(), temp.getY()+speed);
            else if(collisionDetection(-1,0)==0)
                this.setPos(temp.getX()-speed, temp.getY());
            else if(collisionDetection(0,-1) == 0)
                this.setPos(temp.getX(), temp.getY()-speed);
            else if(collisionDetection(1,0) == 0)
                this.setPos(temp.getX()+speed, temp.getY());
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX()+speed, temp.getY()-speed);
        }
        else if(diffx < 0 && diffy == 0)
        {
            if(collisionDetection(-1,0)==0)
                this.setPos(temp.getX()-speed, temp.getY());
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX()+speed, temp.getY());
        }
        else if(diffx > 0 && diffy == 0)
        {
            if(collisionDetection(1,0)==0)
                this.setPos(temp.getX()+speed, temp.getY());
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX()-speed, temp.getY());
        }
        else if(diffx == 0 && diffy < 0)
        {
            if(collisionDetection(0,-1)==0)
                this.setPos(temp.getX(), temp.getY()-speed);
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX(), temp.getY()+speed);
        }
        else if(diffx == 0 && diffy > 0)
        {
            if(collisionDetection(0,1)==0)
                this.setPos(temp.getX(), temp.getY()+speed);
            else if(collisionDetection(0,0) == 0)
                this.setPos(temp.getX(), temp.getY());
            else
                this.setPos(temp.getX(), temp.getY()-speed);
        }
        if(this.getPos().getY()==0)
        {
            System.out.println("Lol "+this.getFlock().getLeader().getPos().getX()+" "+this.getFlock().getLeader().getPos().getY());
        }
        // System.out.println("Bird: x: " + this.getPos().getX()+ " y: "+ this.getPos().getY());
    } 

    private Integer speed;   //Move per coordinate
    private boolean isLeader;
    private int trackingtoggle; //To help with the Algorithm
}
