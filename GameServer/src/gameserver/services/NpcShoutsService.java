/**
 * This file is part of Aion Galaxy Emu <aiongemu.com>
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
package gameserver.services;

import gameserver.ai.state.AIState;
import gameserver.dataholders.DataManager;
import gameserver.model.ShoutEventType;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.npcshouts.Shout;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.utils.PacketSendUtility;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.commons.utils.Rnd;


/**
 * This class is handling NPC automatic shouts
 * 
 * @author Sylar, modified Rolandas
 * 
 */
public class NpcShoutsService
{
	//private static final Logger log	= Logger.getLogger(NpcShoutsService.class);
	
	public NpcShoutsService()
	{
	}
	
	public boolean hasShouts(int npcId, ShoutEventType type)
	{
		List<Shout> shouts = DataManager.NPC_SHOUTS_DATA.getShoutsForNpc(npcId);
		if(shouts == null)
			return false;
		for(Shout s : shouts)
		{
			if(ShoutEventType.valueOf(s.getEvent()) == type)
				return true;
		}
		return false;
	}
	
	public void handleEvent(Npc owner, Creature target, ShoutEventType eventType)
	{
		if(owner == null || target == null)
			return;
		
		//log.debug("Handling NPC shout event : " + owner.getNpcId() + " - " + eventType.name());
		
		if(!hasShouts(owner.getNpcId(), eventType))
		{
			if(eventType == ShoutEventType.WAYPOINT && hasShouts(owner.getNpcId(), ShoutEventType.IDLE))
				eventType = ShoutEventType.IDLE;
			else
			{
				//log.debug("No shouts for " + owner.getNpcId() + " - " + eventType.name());
				return;
			}
		}
				
		boolean canTalk = false;
		switch (eventType)
		{
			case IDLE:
				canTalk = owner.getAi().getAiState() == AIState.ACTIVE || owner.getAi().getAiState() == AIState.NONE || owner.getAi().getAiState() == AIState.THINKING;
				break;
			default:
				if(eventType == ShoutEventType.WIN)
					canTalk = true;
				else
					if(!target.getLifeStats().isAlreadyDead())
						canTalk = true;
				break;
		}
		
		if(!canTalk)
			return;
		
		if(eventType != ShoutEventType.LEAVE && eventType != ShoutEventType.DIE && eventType != ShoutEventType.SEEUSER && eventType != ShoutEventType.WIN)
			if(!owner.mayShout())
			{
				//log.debug("Shout " + owner.getNpcId() + " - " + eventType.name() + " :: not allowed to shout");
				return;
			}
		
		List<Shout> shouts = new ArrayList<Shout>();
		for(Shout s : DataManager.NPC_SHOUTS_DATA.getShoutsForNpc(owner.getNpcId()))
		{
			if(ShoutEventType.valueOf(s.getEvent()) == eventType)
			{
				shouts.add(s);
			}
		}
		
		int randomShout = Rnd.get(shouts.size());
		Shout shout = shouts.get(randomShout);
		
		Object param = shout.getParam();
		
		if(target instanceof Player)
		{
			Player player = (Player)target;
			if ("username".equals(param))
				param = player.getName();
			else if ("userclass".equals(param))
				param = (240000 + player.getCommonData().getPlayerClass().getClassId()) * 2 + 1;
			else if ("usernation".equals(param)) 
			{
				// don't know what is that
				return;
			}
			else if ("usergender".equals(param))
				param = (902012 + player.getCommonData().getGender().getGenderId()) * 2 + 1;
			else if ("mainslotitem".equals(param))
			{
				Item weapon = player.getEquipment().getMainHandWeapon();
				if (weapon == null)
					return;
				param = weapon.getItemTemplate().getNameId();
			}
		}
		
		owner.shout();
		//log.debug(owner.getNpcId() + " shouting " + eventType.name() + "-" + shout.getMessageId());
		SM_SYSTEM_MESSAGE message = new SM_SYSTEM_MESSAGE(shout.getMessageId(), true, owner.getObjectId(), param);
		
		PacketSendUtility.broadcastPacket(owner, message, 30);
		
		shouts.clear();
		shouts = null;
		
	}
	
	public synchronized boolean hasAnyShouts(int npcId)
	{
		return DataManager.NPC_SHOUTS_DATA.getShoutsForNpc(npcId) != null;
	}

	public static final NpcShoutsService getInstance()
	{
		return SingletonHolder.instance;
	}
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final NpcShoutsService instance = new NpcShoutsService();
	}
	
}