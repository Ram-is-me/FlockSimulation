package flockbase;

import java.util.ArrayList;

public class FlockY extends Flock{
    public FlockY() {
        super();
        flock = new ArrayList<Bird>();
    }

    public void addBird(Bird b)
    {
        flock.add(b);
        b.setFlock(this);
    }

    public void setLeader(Bird leader)
    {
        this.leader = leader;
        this.leader.becomeLeader();
    }

    public ArrayList<Bird> getBirds()
    {
        return flock;
    }

    public Bird getLeader()
    {
        return this.leader;
    }

    public Flock split(int pos)
    {
        Flock newFlock = new FlockY();
        int count=0;
        int n = flock.size();
        while(count<n-pos)
        {
            Bird temp = flock.get(flock.size()-1);
            newFlock.addBird(temp);
            if(count==0)
            {
                newFlock.setLeader(flock.get(flock.size()-1));
                temp.becomeLeader();
            }
            flock.remove(flock.size()-1);
            count++;
        }
        return newFlock;
    }

    public void joinFlock(Flock f)
    {
        int count=0;
        int n = f.getBirds().size();
        ArrayList<Bird> b = f.getBirds();
        while(count<n)
        {
            Bird temp = b.get(b.size()-1);
            temp.retireLead();
            addBird(temp);
            b.remove(b.size()-1);
            count++;
        }
    }

    //Data Members
    private ArrayList<Bird> flock;
    private Bird leader;
}