package gameserver.model.academy;

import gameserver.model.templates.academy.AcademyShigoBeginSpawnList;
import gameserver.model.templates.academy.AcademyShigoEndSpawnList;

public class AcademyShigoStage
{

    public AcademyShigoStage()
    {
    }

    public AcademyShigoBeginSpawnList getShigoBegin()
    {
        if(shigoBeginSpawn == null)
            shigoBeginSpawn = new AcademyShigoBeginSpawnList();
        return shigoBeginSpawn;
    }

    public AcademyShigoEndSpawnList getShigoEnd()
    {
        if(shigoEndSpawn == null)
            shigoEndSpawn = new AcademyShigoEndSpawnList();
        return shigoEndSpawn;
    }

    public int getStage()
    {
        return id;
    }

    protected AcademyShigoBeginSpawnList shigoBeginSpawn;
    protected AcademyShigoEndSpawnList shigoEndSpawn;
    protected int id;
}
