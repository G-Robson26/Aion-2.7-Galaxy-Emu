/*
 * This file is part of Aion X EMU <aionxemu.com>.
 *
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.morheim;

import gameserver.model.gameobjects.player.Player;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;



/**
 * @author XRONOS
 *
 */

public class _2486AStrangeCreature extends QuestHandler
{
	private final static int	questId	= 2486;
	private final static int[]	npcs = {204053, 204208, 204092, 798063, 204342};
	
	public _2486AStrangeCreature()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.setNpcQuestData(204053).addOnQuestStart(questId);
		for(int npc: npcs)
			qe.setNpcQuestData(npc).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestCookie env)
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		
		if(defaultQuestNoneDialog(env, 204053))
			return true;

		if(qs == null)
			return false;

		int var = qs.getQuestVarById(0);
		
		if(qs.getStatus() == QuestStatus.START)
		{
			switch(env.getTargetId())
			{
				case 204208:
					switch(env.getDialogId())
					{
						case 26:
							if(var == 0)
								return sendQuestDialog(env, 1352);
						case 10000:
							return defaultCloseDialog(env, 0, 1);
					}
					break;
				case 204092:
					switch(env.getDialogId())
					{
						case 26:
							if(var == 1)
								return sendQuestDialog(env, 1693);
						case 10001:
							return defaultCloseDialog(env, 1, 2);
					}
					break;
				case 798063:
					switch(env.getDialogId())
					{
						case 26:
							if(var == 2)
								return sendQuestDialog(env, 2034);
						case 10002:
							return defaultCloseDialog(env, 2, 3, true, false);
					}
					break;
			}
		}
		return defaultQuestRewardDialog(env, 204342, 2375);
	}
}
