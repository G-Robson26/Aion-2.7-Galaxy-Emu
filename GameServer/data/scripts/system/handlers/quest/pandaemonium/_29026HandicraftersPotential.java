/*
 * This file is part of aion-unique.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.pandaemonium;

import gameserver.model.gameobjects.player.Player;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.QuestService;


public class _29026HandicraftersPotential extends QuestHandler
{
	private final static int	questId	= 29026;
	private final static int	questStartNpc = 204108;
	private final static int	secondNpc = 798060;
	private final static int[]	recipesItemIds = {152207051, 152207052};
	private final static int[]	recipesIds = {155007089, 155007090};

	public _29026HandicraftersPotential()
	{
		super(questId);
	}

	@Override
	public boolean onDialogEvent(QuestCookie env)
	{
		Player player = env.getPlayer();
		
		if(defaultQuestNoneDialog(env, questStartNpc, 4762))
			return true;

		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null)
			return false;

		int var = qs.getQuestVarById(0);
		if(qs.getStatus() == QuestStatus.START)
		{
			switch(env.getTargetId())
			{
				case secondNpc:
					switch(env.getDialogId())
					{
						case 26:
							if(var == 0)
								return sendQuestDialog(env, 1011);
							else if(var == 2)
								return sendQuestDialog(env, 4080);
						case 10009:
							if(player.getInventory().decreaseKinah(167500))
							{
								defaultQuestGiveItem(env, 152029250, 1);
								return defaultCloseDialog(env, var, 1, recipesItemIds[0], 1, 0, 0);
							}
							else
								return sendQuestDialog(env, 4400);
						case 10019:
							if(player.getInventory().decreaseKinah(223000))
							{
								defaultQuestGiveItem(env, 152029250, 1);
								return defaultCloseDialog(env, var, 1, recipesItemIds[1], 1, 0, 0);
							}
							else
								return sendQuestDialog(env, 4400);
					}
					break;
				case questStartNpc:
					switch(env.getDialogId())
					{
						case 26:
							if(var == 1)
								return sendQuestDialog(env, 1352);
						case 34:
							if (var == 1)
							{
								if(QuestService.collectItemCheck(env, true))
								{
									qs.setStatus(QuestStatus.REWARD);
									updateQuestStatus(env);
									return sendQuestDialog(env, 5);
								}
								else
								{
									int checkFailId = 3398;
									if(player.getRecipeList().isRecipePresent(recipesIds[0]) || player.getRecipeList().isRecipePresent(recipesIds[1]))
										checkFailId = 2716;
									else if(player.getInventory().getItemCountByItemId(recipesItemIds[0]) > 0 || player.getInventory().getItemCountByItemId(recipesItemIds[1]) > 0)
										checkFailId = 3057;
									
									if(checkFailId == 3398)
									{
										qs.setQuestVar(2);
										updateQuestStatus(env);
									}
									return sendQuestDialog(env, checkFailId);
								}
							}
							break;
					}
					break;
			}
		}
		return defaultQuestRewardDialog(env, questStartNpc, 0);
	}

	@Override
	public void register()
	{
		qe.setNpcQuestData(questStartNpc).addOnQuestStart(questId);
		qe.setNpcQuestData(questStartNpc).addOnTalkEvent(questId);
		qe.setNpcQuestData(secondNpc).addOnTalkEvent(questId);
	}
}
