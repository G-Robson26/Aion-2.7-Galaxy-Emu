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


/**
 * @author xavier 
 */
public class Title {

	private int titleId;
	private int race;
	private long title_date = 0;
	private long title_expires_time = 0;

	public Title(int titleId, int race, long title_date, long title_expires_time)
	{
		this.titleId = titleId;
		this.race = race;
		this.title_date = title_date;
		this.title_expires_time = title_expires_time;
	}

	public int getTitleId()
	{
		return titleId;
	}

	public int getRace()
	{
		return race;
	}

	public long getTitleDate()
	{
		return title_date;
	}

	public long getTitleExpiresTime()
	{
		return title_expires_time;
	}

	public void setTitleId(int titleId)
	{
		this.titleId = titleId;
	}

	public void setRace(int race)
	{
		this.race = race;
	}

	public long getTitleTimeLeft()
	{
		if(title_expires_time == 0)
			return 0;

		long timeLeft = (title_date + ((title_expires_time )  * 1000L)) - System.currentTimeMillis();
		if(timeLeft < 0)
			timeLeft = 0;

		return timeLeft /1000L ;
	}

	public void setTitleDate(long title_date)
	{
		this.title_date = title_date;
	}

	public void setTitleExpiresTime(long title_expires_time)
	{
		this.title_expires_time = title_expires_time;
	}
}