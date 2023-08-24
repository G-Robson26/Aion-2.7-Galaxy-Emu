/**
 * This file is part of Aion Galaxy EMU <aiongemu.com>.
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.ai.events.handler;

import gameserver.ai.AI;
import gameserver.ai.events.Event;
import gameserver.ai.state.AIState;
import gameserver.model.ShoutEventType;
import gameserver.model.gameobjects.Npc;
import gameserver.services.NpcShoutsService;

/**
 * @author ATracer
 *
 */
public class MostHatedChangedEventHandler implements EventHandler
{
	@Override
	public Event getEvent()	{
		return Event.MOST_HATED_CHANGED;
	}

	@Override
	public void handleEvent(Event event, AI<?> ai){
		ai.setAiState(AIState.THINKING);
		if(ai.getOwner() instanceof Npc)
			NpcShoutsService.getInstance().handleEvent((Npc)ai.getOwner(), ai.getOwner().getAggroList().getMostHated(), ShoutEventType.SWICHTARGET);
	}
}