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

package gameserver.controllers;

import org.apache.log4j.Logger;
import gameserver.controllers.attack.AttackStatus;
import gameserver.model.Race;
import gameserver.model.gameobjects.VisibleObject;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.shield.Shield;
import gameserver.model.siege.SiegeRace;
import gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import gameserver.services.SiegeService;

/**
 * @author xavier
 * @modified ViAl(fortress shields)
 */
public class ShieldController extends CreatureController<Shield>
{	
	@Override
	public void see(VisibleObject object)
	{
		if (object instanceof Player)
		{
			Player player = (Player) object;
			Shield owner = (Shield)getOwner();
			//areshurat, teminon, primum entrances
			if (owner.getTemplate().getRace() != Race.NONE)
			{
				if (player.getCommonData().getPosition().getX()>2080 && player.getCommonData().getPosition().getX()<2215 && 
					player.getCommonData().getPosition().getY()>1820 && player.getCommonData().getPosition().getY()<2045 &&
					player.getCommonData().getPosition().getZ()>2309)
				{
					// areshurat entrance, nothing to do
					Logger.getLogger(this.getClass()).info("Player "+player.getName()+" passed areshurat entrance.");
				}
				else
			   	{
			    	if (!player.isProtectionActive() && player.getCommonData().getRace() != owner.getTemplate().getRace())
			    		kill(owner,player);

			    }
			}
			if (owner.getTemplate().getRace() == Race.NONE)
			{
				if (SiegeService.getInstance().getSiegeLocation(owner.getTemplate().getFortressId()).isShieldActive())
				{
					SiegeRace sRace = SiegeService.getInstance().getSiegeLocation(owner.getTemplate().getFortressId()).getRace();
					Race race;
					switch (sRace) 
					{
 					case ASMODIANS:
 						race = Race.ASMODIANS;
 						break;
 					case ELYOS:
 						race = Race.ELYOS;
 						break;
 					default:
 						race = Race.DRAKAN;
 						break;
					}
					
					if(player.getCommonData().getRace() != race)
							if(player.getCommonData().getPosition().getZ() >= owner.getTemplate().getZ() -12)
								kill(owner,player);
				}
			}
		}
	}
	
	private void kill(Shield owner, Player player)
	{
		Logger.getLogger(this.getClass()).info("Shield "+owner.getName()+" killing "+player.getName());
		
		if(owner.getTemplate().getFortressId() != 0)
			player.getController();
		else
		{
			player.getController().setCanAutoRevive(false);
			player.getController();
			player.getReviveController().bindRevive();
			player.getController().setCanAutoRevive(true);
		}
	}
}