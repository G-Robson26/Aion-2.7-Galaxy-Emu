/*
 *  This file is part of Zetta-Core Engine <http://www.zetta-core.org>.
 *
 *  Zetta-Core is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the License,
 *  or (at your option) any later version.
 *
 *  Zetta-Core is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a  copy  of the GNU General Public License
 *  along with Zetta-Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.daily;

import gameserver.model.EmotionType;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.VisibleObject;
import gameserver.model.gameobjects.player.Player;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.QuestService;


/**
 * @author HellBoy
 *
 */
public class _46516ZantratheRevenge extends QuestHandler
{
	private final static int	questId	= 46516;

	public _46516ZantratheRevenge()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.setNpcQuestData(700776).addOnTalkEvent(questId);
		qe.setNpcQuestData(799882).addOnTalkEvent(questId);
		qe.setNpcQuestData(216642).addOnKillEvent(questId);
	}

	@Override
	public boolean onKillEvent(QuestCookie env)
	{
		return defaultQuestOnKillEvent(env, 216642, 0, true);
	}

	@Override
	public boolean onDialogEvent(QuestCookie env)
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		
		if(defaultQuestStartDaily(env))
			return true;
		
		if(qs == null)
			return false;
		
		if(qs.getStatus() == QuestStatus.START)
		{
			if(env.getTargetId() == 700776)
				return defaultQuestUseNpc(env, 0, 1, EmotionType.NEUTRALMODE2, EmotionType.START_LOOT, true);
		}
		if(defaultQuestRewardDialog(env, 799882, 10002))
			return true;
		else
			return false;
	}
	
	@Override
	public void QuestUseNpcInsideFunction(QuestCookie env)
	{
		Player player = env.getPlayer();
		VisibleObject vO = env.getVisibleObject();
		if(vO instanceof Npc)
		{
			Npc corp = (Npc)vO;
			if(corp.getNpcId() == 700776)
				QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 216642, corp.getX(), corp.getY(), corp.getZ(), (byte) 0, true);
		}
	}
}
