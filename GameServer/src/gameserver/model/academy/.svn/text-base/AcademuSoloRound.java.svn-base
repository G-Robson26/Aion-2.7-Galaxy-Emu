package gameserver.model.academy;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import gameserver.model.templates.academy.AcademySoloSpawnList;

public class AcademuSoloRound
{

    public AcademuSoloRound()
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

    public int size()
    {
        return getAcademySpawn().size();
    }

    protected List academySpawn;
    protected int id;
}
