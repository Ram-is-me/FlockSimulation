package flockbase;
import java.util.*;
//import flockbase.*;

public class Bird509 extends Bird
{
	private int leader = 0;

  Position currentPos =  getPos();

	public Bird509()
	{
		setPos(0,0);
		setTarget(0,0);
	}

	public void becomeLeader()
	{
		leader = 1;
	}

	public String getName() {
        if(leader!=1)
    		return "Bird509";
        else
            return "Leader509";       
	}

	public void retireLead()
	{
		leader = 0;
	}

	// to move the birds without collision to target...
	protected void updatePos()
	{
		if (leader == 0)
		{
      Position leaderPoss = getFlock().getLeader().getPos();

			setTarget(leaderPoss.getX(),leaderPoss.getY());
    }
		//**************************************************************************
		ArrayList<Bird> birdList = new ArrayList<Bird>();

		double dx=0,dy=0;

		double adjX=0,adjY=0,unitValue=0;

    int x = currentPos.getX();

    int y = currentPos.getY();

		birdList = getFlock().getBirds();

    int xTarget = getTarget().getX();

    int yTarget = getTarget().getY();

		unitValue=Math.sqrt(Math.pow(xTarget-x,2)+Math.pow(yTarget-y,2));

		if(unitValue==0)
		 {
			 unitValue = 1;
		 }
		dx = ((xTarget-x)*getMaxSpeed())/unitValue;//moving in unit vector
		dy = ((yTarget-y)*getMaxSpeed())/unitValue;

		for(Bird b:birdList)
		{
			adjX = b.getPos().getX();
			adjY = b.getPos().getY();
			unitValue = Math.pow(Math.pow(adjX-x,2)+Math.pow(adjY-y,2),0.5);
			if(unitValue < 2*getMaxSpeed() && unitValue!=0)
			{
				dx = dx + ((x - adjX)*getMaxSpeed())/unitValue;

				dy = dy + ((y - adjY)*getMaxSpeed())/unitValue;
			}
		}
		setPos(x + (int)dx ,y + (int)dy);
	}

}
