/**
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is private software: you can redistribute it and/or modify
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
package gameserver.model.gameobjects.player;

import gameserver.dataholders.DataManager;
import gameserver.model.templates.TitleTemplate;
import gameserver.network.aion.serverpackets.SM_TITLE_LIST;
import gameserver.utils.PacketSendUtility;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author blakawk
 * 
 */
public class TitleList {
	private LinkedHashMap<Integer, Title>	titles;
	private Player owner;

	public TitleList()	{
		this.titles = new LinkedHashMap<Integer, Title>();
		this.owner = null;
	}

	public void setOwner(Player owner)	{
		this.owner = owner;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public boolean addTitle(int titleId) {
		TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);
		if (tt == null)	{
			throw new IllegalArgumentException("Invalid title id " + titleId);
		}
		if (owner != null)	{
			if (owner.getCommonData().getRace().getRaceId() != tt.getRace() && tt.getRace() != 2)	{
				return false;
			}
		}
		if(!titles.containsKey(titleId))	{
			titles.put(titleId, new Title(titleId, tt.getRace(), System.currentTimeMillis(), 0));
		}
		else	{
			return false;
		}

		if (owner != null)	{
			PacketSendUtility.sendPacket(owner, new SM_TITLE_LIST(owner));
		}
		return true;
	}

	public boolean addTitle(int titleId, long title_date, long title_expires_time)	{
		TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);
		if (tt == null)		{
			throw new IllegalArgumentException("Invalid title id " + titleId);
		}
		if (owner != null)	{
			if (owner.getCommonData().getRace().getRaceId() != tt.getRace() && tt.getRace() != 2)	{
				return false;
			}
		}
		if(!titles.containsKey(titleId))	{
			titles.put(titleId, new Title(titleId, tt.getRace(), title_date, title_expires_time));
		}
		else	{
			return false;
		}

		if (owner != null)	{
			PacketSendUtility.sendPacket(owner, new SM_TITLE_LIST(owner));
		}
		return true;
	}

	public boolean canAddTitle(int titleId)	{
		TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);

		if (tt == null)	{
			throw new IllegalArgumentException("Invalid title id " + titleId);
		}
		if (owner != null)	{
			if (owner.getCommonData().getRace().getRaceId() != tt.getRace() && tt.getRace() != 2)	{
				return false;
			}
		}
		if(titles.containsKey(titleId))	{
			return false;
		}

		return true;
	}

	public void delTitle(int titleId) {
		if(titles.containsKey(titleId)) {
			titles.remove(titleId);
		}
	}

	public int size() {
		return titles.size();
	}

	public Collection<Title> getTitles() {

		return titles.values();
	}
}