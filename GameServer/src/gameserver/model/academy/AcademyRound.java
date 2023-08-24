package gameserver.model.academy;

import java.util.ArrayList;
import java.util.List;

public class AcademyRound
{

    public AcademyRound()
    {
    }

    public List getAcademySpawn()
    {
        if(academySpawn == null)
            academySpawn = new ArrayList();
        return academySpawn;
    }

    public int getRound()
    {
        return id;
    }

    public int getTime()
    {
        return time;
    }

    public int size()
    {
        return getAcademySpawn().size();
    }

    protected List academySpawn;
    protected int id;
    protected int time;
}
