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
package gameserver.model.templates.drops;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Sylar
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dropitem")
public class DropItem{
	@XmlAttribute(name = "id")
	protected int		itemId;
	
	@XmlAttribute(name = "min")
	protected int		min;
	
	@XmlAttribute(name = "max")
	protected int		max;
	
	@XmlAttribute(name = "chance")
	protected float		chance;
	
	public int getItemId(){
		return itemId;
	}
	
	public int getMin()	{
		return min;
	}
	
	public int getMax()	{
		return max;
	}
	
	public float getChance(){
		return chance;
	}
	
}
