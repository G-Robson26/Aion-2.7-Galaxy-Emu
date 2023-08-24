
package gameserver.dataholders;

import gameserver.model.templates.staticdoor.StaticDoorTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "staticdoor_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class StaticDoorData
{
	@XmlElement(name = "staticdoor")
	private List<StaticDoorTemplate> staticDoors;
	
	/** 
	 * A map containing all door templates 
	 */
	private TIntObjectHashMap<StaticDoorTemplate> staticDoorData = new TIntObjectHashMap<StaticDoorTemplate>();

	/**
	 * @param u
	 * @param parent
	 */
	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		staticDoorData.clear();
		
		for(StaticDoorTemplate staticDoor : staticDoors)
		{
			staticDoorData.put(staticDoor.getDoorId(), staticDoor);
		}
	}
	
	public int size()
	{
		return staticDoorData.size();
	}
	
	/**
	 * @param doorId
	 * @return
	 */
	public StaticDoorTemplate getStaticDoorTemplate(int doorId)
	{
		return staticDoorData.get(doorId);
	}

	/**
	 * @return the static doors
	 */
	public List<StaticDoorTemplate> getStaticDoors()
	{
		return staticDoors;
	}

	public void setStaticDoors(List<StaticDoorTemplate> staticDoors)
	{
		this.staticDoors = staticDoors;
		afterUnmarshal(null, null);
	}
}
