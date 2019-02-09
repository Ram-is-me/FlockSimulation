package flockbase;
    
//import flockbase.Bird;
//import flockbase.Flock;
//import flockbase.Position;
import java.io.PrintStream;
import java.util.*;    
public class Bird012 extends Bird 
{
        private double birdspeed = 10;
        private boolean leader;
    
        public String getName() {
            if(leader==true)
                return "Leader012";
            else
                return "Bird012";
        }
        public void setSpeed(double s)
        {
            birdspeed = s;
        }

        public void speed_con()
        {
            Bird leader = this.getFlock().getLeader();
            int leaderposx = leader.getPos().getX();
            int leaderposy = leader.getPos().getY();
            if ((this.getPos().getX() < leaderposx + 5 && this.getPos().getX() > leaderposx - 5) || (this.getPos().getY() < leaderposy + 5 && this.getPos().getY() > leaderposy - 5))  
                {
                    this.setSpeed(Bird.getMaxSpeed());
                }
            else
            {    
                this.setSpeed(Bird.getMaxSpeed() + 20);
            }                    
        }
    
        protected void updatePos() {
            speed_con();
            Position current_position = this.getPos();
            int x = current_position.getX();
            int y = current_position.getY();
            double temp1,temp2;
            temp1=0;
            temp2=0;
            if(!leader)
            {
                Position targ = this.getFlock().getLeader().getPos();
                this.setTarget(targ.getX(), targ.getY());
            }
            double distance=(this.getTarget().getX()-x)*(this.getTarget().getX()-x)+(this.getTarget().getY()-y)*(this.getTarget().getY()-y);
            if (x==this.getTarget().getX() && y==this.getTarget().getY())
            {
                this.setPos(x, y);
            }
            else if(x==this.getTarget().getX())
            {
                if(this.getTarget().getY()>y)
                {
                    temp2=birdspeed;
                }
                else
                {
                    temp2=-birdspeed;
                }
            }
            else if(y==this.getTarget().getY())
            {
                if(this.getTarget().getX()>x)
                {
                    temp1=birdspeed;
                }
                else
                {
                    temp1=-birdspeed;
                }
            }
            else
            {
                double m = (float)(this.getTarget().getY() - y) / (float)(this.getTarget().getX() - x);
                temp1 = this.getTarget().getX() > x ? 1.0 : -1.0;
                temp1 = temp1*(double)birdspeed;
                temp2 = m * (temp1);
                if(temp2>5)
                {
                    temp2=5;
                }
                if(temp2<-5)
                {
                    temp2=-5;
                }
                
            }
            for (int i = 0; i < this.getFlock().getBirds().size(); i++)
            {
                int tempx = 0, tempy = 0;
                tempx = this.getFlock().getBirds().get(i).getPos().getX();
                tempy = this.getFlock().getBirds().get(i).getPos().getY();            
                if (x + (int)temp1 >= tempx - 8 && x + (int)temp1 <= tempx + 8)
                {
                    if (y + (int)temp2 >= tempy - 8 && y + (int)temp2 <= tempy + 8)
                    {
                        if (leader)
                        {
                            this.getFlock().getBirds().get(i).setPos(this.getFlock().getBirds().get(i).getPos().getX() + 20, this.getFlock().getBirds().get(i).getPos().getY() + 20);
                        }
                        else
                        {
                            if(temp1<0)
                                temp1=+3;
                            else
                                temp1=-3;
                            if(temp2<0)
                                temp2=+3;
                            else
                                temp2=-3;
                        }
                    }
                }
            }
    
            this.setPos(x + (int)temp1, y + (int)temp2);
        }
    
        public void becomeLeader() {
            this.leader = true;
        }
    
        public void retireLead() {
            this.leader = false;
        }
    }
    
    
