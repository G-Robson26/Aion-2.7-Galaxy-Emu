/**
 * This file is part of Aion X Emu <aionxemu.com>
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
package quest.reshanta;

import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.QuestService;
import gameserver.utils.PacketSendUtility;

/**
 * @author Leunam
 * 
 */
public class _2718TradingDown extends QuestHandler {
	private final static int questId = 2718;

	public _2718TradingDown() {
		super(questId);
	}

	@Override
	public void register() {
        	qe.setNpcQuestData(204396).addOnTalkEvent(questId);
		qe.setNpcQuestData(204386).addOnTalkEvent(questId);
		qe.setNpcQuestData(204811).addOnTalkEvent(questId);
		qe.setNpcQuestData(279029).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestCookie env) {
	final Player player = env.getPlayer();
	int targetId = 0;
	final QuestState qs = player.getQuestStateList().getQuestState(questId);
	if (env.getVisibleObject() instanceof Npc)
	targetId = ((Npc) env.getVisibleObject()).getNpcId();

	if (qs == null || qs.getStatus() == QuestStatus.NONE) {
		if (env.getDialogId() == 1002) {	
			QuestService.startQuest(env, QuestStatus.START);
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
			return true;
            } else
                	PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
	} 
	if(qs == null)
	return false;
		
	int var = qs.getQuestVarById(0);
	if(qs.getStatus() == QuestStatus.REWARD)
	{
		if(targetId == 279029)
		{
			if(env.getDialogId() == -1)
				return sendQuestDialog(env, 2375);
			else if(env.getDialogId() == 1009)
				return sendQuestDialog(env, 5);
			else
				return defaultQuestEndDialog(env);
		}
	}
	else if(qs.getStatus() != QuestStatus.START)
	{
		return false;
	}
	if(targetId == 204396)
	{
		switch(env.getDialogId())
		{
			case 26:
				if(var == 0)
					return sendQuestDialog(env, 1352);
			case 10000:
				if(var == 0)
				{
					qs.setQuestVarById(0, var + 1);
					updateQuestStatus(env);								
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
					return true;
				}
			return false;
		}
	}
	else if(targetId ==204386)
	{
		switch(env.getDialogId())
		{
			case 26:
				if(var == 1)
					return sendQuestDialog(env, 1693);
			case 10001:
				if(var == 1)
				{
					qs.setQuestVarById(0, var + 1);
					updateQuestStatus(env);								
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
					return true;
				}
			return false;
		}
	}
	else if(targetId ==204811)
	{
		switch(env.getDialogId())
		{
			case 26:
				if(var == 2)
					return sendQuestDialog(env, 2034);
			case 10002:
				if(var == 2)
				{
					qs.setQuestVarById(0, var + 1);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);								
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
					return true;
				}
			return false;
		}
	}
	return false;
     }
}
