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
package quest.pandaemonium;

import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.utils.PacketSendUtility;

 
/**
 * @author Hellboy
 *
 */
public class _4944LoyaltyandAffableness extends QuestHandler
{
	private final static int	questId	= 4944;
	private final static int[]	npcs = {204053, 204075};
	private final static int[]	mobs = {251002, 251021, 251018, 251039, 251033, 251036};

	public _4944LoyaltyandAffableness()
	{
		super(questId);
	}
	
	@Override
	public void register()
	{
		qe.setNpcQuestData(204053).addOnQuestStart(questId);
		for(int npc: npcs)
			qe.setNpcQuestData(npc).addOnTalkEvent(questId);
		for(int mob: mobs)
			qe.setNpcQuestData(mob).addOnKillEvent(questId);
		qe.setNpcQuestData(214823).addOnKillEvent(questId);
		qe.setNpcQuestData(216850).addOnKillEvent(questId);
		qe.setNpcQuestData(216886).addOnKillEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestCookie env)
	{
		Player player = env.getPlayer();
		
		if(defaultQuestNoneDialog(env, 204053, 4762))
			return true;

		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null)
			return false;

		int var = qs.getQuestVars().getQuestVars();
		if(qs.getStatus() == QuestStatus.START)
		{
			switch(env.getTargetId())
			{
				case 204053:
				{
					switch(env.getDialogId())
					{
						case 26:
							if(var == 0)
								return sendQuestDialog(env, 1011);
							else if(var == 306)
								return sendQuestDialog(env, 1693);
							else if(var == 4)
								return sendQuestDialog(env, 2375);
						case 10002:
							if(var ==  306)
							{
								qs.setQuestVar(3);
								updateQuestStatus(env);
								PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
									return true;
							}
							else
								return false;
						case 10004:
							return defaultCloseDialog(env, 4, 5);
						case 34:
							return defaultQuestItemCheck(env, 0, 6, false, 10000, 10001);
					}
				}	break;
				case 204075:
				{
					switch(env.getDialogId())
					{
						case 26:
							if(var == 5)
								return sendQuestDialog(env, 2716);
						case 2717:
							if(player.getCommonData().getDp() != 4000)
								return sendQuestDialog(env, 2802);
						case 2718:
							if(player.getInventory().getItemCountByItemId(186000087) == 0)
								return sendQuestDialog(env, 2887);
						case 10255:
							if(player.getInventory().getItemCountByItemId(186000087) >= 1)
								return defaultCloseDialog(env, 5, 0, true, false, 0, 0, 186000087, 1);
							else
								return sendQuestDialog(env, 2887);
					}
				} break;
			}
		}
		return defaultQuestRewardDialog(env, 204053, 10002);
	}
	
	@Override
	public boolean onKillEvent(QuestCookie env)
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null)
			return false;
		int var = qs.getQuestVars().getQuestVars();

		if(qs.getStatus() != QuestStatus.START)
			return false;
		for(int id : mobs)
		{
			if(env.getTargetId() == id)
			{
				if(var >= 6 && var < 306)
				{
					qs.setQuestVar(var + 1);
					updateQuestStatus(env);
					return true;
				}
			}
		}
		switch(env.getTargetId())
		{
			case 214823:
				return defaultQuestOnKillEvent(env, 214823, 3, 4);
			case 216850:
				return defaultQuestOnKillEvent(env, 216850, 3, 4);
			case 216886:
				return defaultQuestOnKillEvent(env, 216886, 3, 4);
		}
		return false;
	}
}
