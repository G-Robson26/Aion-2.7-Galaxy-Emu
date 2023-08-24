/**
 * This file is part of Aion Galaxy Emu <aiongemu.com>.
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
package gameserver.model;

import gameserver.configs.network.FloodConfig;

import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * This class represents flood ip
 * 
 * @author Divinity
 */
public class FloodIP
{
	private String					IP;
	private ArrayList<Timestamp>	dates;
	
	public FloodIP(String IP)
	{
		this.IP = IP;
		dates = new ArrayList<Timestamp>();
		this.addConnection();
	}
	
	public void addConnection()
	{
		dates.add(new Timestamp(System.currentTimeMillis()));
		
		deleteOldConnection();
	}
	
	public void deleteOldConnection()
	{
		ArrayList<Timestamp> datesTmp = dates;
		int i = datesTmp.size()-1;
		
		while (i >= 0)
		{
			if (datesTmp.get(i).getTime() < (System.currentTimeMillis() - (FloodConfig.FLOOD_CONTROLLER_INTERVAL * 60 * 1000)))
				dates.remove(i);
			i--;
		}
	}
	
	public String getIP()
	{
		return IP;
	}
	
	public boolean checkFlood()
	{
		deleteOldConnection();
		return (dates.size() >= FloodConfig.FLOOD_CONTROLLER_MAX_CONNECTION ? true : false);
	}
	
	public int size()
	{
		return dates.size();
	}
}
