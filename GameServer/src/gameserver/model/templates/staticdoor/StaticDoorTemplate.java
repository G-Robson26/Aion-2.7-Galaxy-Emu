
package gameserver.model.templates.staticdoor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StaticDoor")
public class StaticDoorTemplate
{
	@XmlAttribute(name = "doorid")
	protected int	doorId;
	@XmlAttribute(name = "keyid")
	protected int	keyId;
	@XmlAttribute(name="mapid")
	protected int mapid;

	/**
	 * @return the npcId
	 */
	public int getDoorId()
	{
		return doorId;
	}

	/**
	 * @return the keyItem
	 */
	public int getKeyId()
	{
		return keyId;
	}
	
	/**
	 * @return the MapId
	 */  
	public int getMapId() 
	{
		return this.mapid;
	}
}
