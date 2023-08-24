/**
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is pryvate software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alpha team is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alpha team.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.dataholders;

import gnu.trove.THashMap;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import gameserver.model.templates.zone.ZoneTemplate;
import gameserver.world.zone.ZoneName;

/**
 * @author Sarynth
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "flight_zones")
public class FlightZoneData implements Iterable<ZoneTemplate>
{
	@XmlElement(name = "flight_zone")
	protected List<ZoneTemplate> flightZoneList;
	
	private THashMap<ZoneName, ZoneTemplate> zoneNameMap = new THashMap<ZoneName, ZoneTemplate>();
	
	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		for(ZoneTemplate zone : flightZoneList)
		{
			zoneNameMap.put(zone.getName(), zone);
		}
	}
	
	@Override
	public Iterator<ZoneTemplate> iterator()
	{
		return flightZoneList.iterator();
	}
	
	public int size()
	{
		return flightZoneList.size();
	}
}
