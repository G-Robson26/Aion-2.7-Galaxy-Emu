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
public class _2343AncientGinseng extends QuestHandler
{

	private final static int	questId	= 2343;

	public _2343AncientGinseng()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.setNpcQuestData(700243).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestCookie env)
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if(env.getTargetId() == 0)
			if(defaultQuestStartItem(env))
			{
				qs.setStatus(QuestStatus.REWARD);
				updateQuestStatus(env);
			}
			else
				return false;
		
		if(qs == null)
			return false;
		
		return defaultQuestRewardDialog(env, 700243, 0);
	}
}
