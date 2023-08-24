/**
 * This file is part of Aion Galaxy Emu <aiongemu.com>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import gameserver.model.Race;
import gameserver.model.templates.guild.GuildQuests;


/**
 * @author HellBoy
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "guildTemplate", propOrder = {"guildQuests"})

public class GuildTemplate
{
	@XmlElement(name = "guild_quests")
	protected GuildQuests		guildQuests;

    @XmlAttribute
    protected int id;
    @XmlAttribute
    protected int nameId;
    @XmlAttribute
    protected int npcId;
    @XmlAttribute
    protected int requiredLevel;

    /**
     * Gets the value of the guildQuests property.
     * 
     * @return
     *     possible object is
     *     {@link GuildQuests }
     *     
     */
    public GuildQuests getGuildQuests()
    {
        return guildQuests;
    }
    
    /**
     * Gets the value of the id property.  
     */
	public int getGuildId()
    {
        return id;
    }

    /**
     * Gets the value of the nameId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getNameID()
    {
        return nameId;
    }
    
    /**
     * Gets the value of the npcId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getNpcId()
    {
        return npcId;
    }
    
    /**
     * Gets the itemId of the requiredItem property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRequiredLevel()
    {
        return requiredLevel;
    }
    
    public Race getGuildRace()
    {
    	Race race = null;
    	switch(this.id)
    	{
    		case 10:
    		case 11:
    		case 12:
			case 13:
    		case 14:
    			race = Race.ELYOS;
    			break;
    		case 20:
    		case 21:
    		case 22:
    		case 23:
    		case 24:
    			race = Race.ASMODIANS;
    			break;
    	}
        return race;
    }
}
