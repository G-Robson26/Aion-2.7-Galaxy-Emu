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
package gameserver.dataholders;

import gameserver.model.templates.drops.NpcDrop;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Sylar
 */
@XmlRootElement(name = "droplist")
@XmlAccessorType(XmlAccessType.FIELD)
public class DroplistData
{
	@XmlElement(name = "npcdrop")
	protected List<NpcDrop> npcDrops;
	
	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		
	}
	
	public List<NpcDrop> getDrops()
	{
		return npcDrops;
	}
	
	public int size()
	{
		return npcDrops.size();
	}
	
}
