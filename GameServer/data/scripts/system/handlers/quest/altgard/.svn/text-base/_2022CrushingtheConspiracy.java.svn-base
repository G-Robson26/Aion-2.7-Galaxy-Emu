/**
 * This file is part of Aion Glaxy Emu <aiongemu.com>
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

package quest.altgard;

import gameserver.model.EmotionType;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.serverpackets.SM_EMOTION;
import gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import gameserver.network.aion.serverpackets.SM_TELEPORT_LOC;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.InstanceService;
import gameserver.services.QuestService;
import gameserver.services.TeleportService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.world.WorldMapInstance;
import gameserver.world.WorldMapType;

import java.util.Collection;

/**
 * @author HGabor85
 * @reworked dimoalex
 *
 */
public class _2022CrushingtheConspiracy extends QuestHandler
{
	private final static int	questId	= 2022;
	private final static int[]	npcIds	= { 203557, 700089, 700140, 700141 };

	private boolean spawned = false;
	private int instanceId = 0;
	public _2022CrushingtheConspiracy()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.addOnEnterWorld(questId);
		qe.addQuestLvlUp(questId);
		qe.addOnDie(questId);
		qe.setNpcQuestData(700089).addOnActionItemEvent(questId);//Abyss gate
		qe.setNpcQuestData(700140).addOnActionItemEvent(questId);//Generator
		qe.setNpcQuestData(700141).addOnActionItemEvent(questId);
		qe.setNpcQuestData(210753).addOnKillEvent(questId);
		for(int npcId : npcIds)
			qe.setNpcQuestData(npcId).addOnTalkEvent(questId);
		
	}

	@Override
	public boolean onLvlUpEvent(QuestCookie env)
	{
		int[] quests = {2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021};
		return defaultQuestOnLvlUpEvent(env, quests);
	}
	
	@Override
	public boolean onDialogEvent(final QuestCookie env)
	{
		if(!super.defaultQuestOnDialogInitStart(env))
			return false;

		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int var = qs.getQuestVarById(0);

		if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (env.getTargetId() == 203557) {
				if (env.getDialogId() == -1)
					return sendQuestDialog(env, 1352);
				return defaultQuestEndDialog(env);
			}
		}
		else if (qs.getStatus() != QuestStatus.START)
			return false;
		switch (env.getTargetId())
		{
			case 203557:
				switch (env.getDialogId())
				{
					case 26:
						if (var == 0)
						    defaultQuestMovie(env, 66);
							return sendQuestDialog(env, 1011);
					case 10000:
					    PacketSendUtility.sendPacket(player, new SM_TELEPORT_LOC(220030000, 2452.8877f, 2553.044f, 316.26282f, 1));
					    TeleportService.scheduleTeleportTask(player, 220030000, 2452.8877f, 2553.044f, 316.26282f);
						return defaultCloseDialog(env, 0, 1);
					default:
						return false;
				}
			case 700089:
				if(var == 5){
						final int targetObjectId = env.getVisibleObject().getObjectId();
						PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
						PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));

					    qs.setQuestVarById(0, 6);
						//update status
						qs.setStatus(QuestStatus.REWARD);

						ThreadPoolManager.getInstance().schedule(new Runnable()	{
							@Override
							public void run(){
								PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
								updateQuestStatus(env);
								TeleportService.teleportTo(player, WorldMapType.ALTGARD.getId(), 2452.8877f, 2553.044f, 316.26282f, 0);
							}
						}, 3000);
						return true;
				}
				return false;
			case 700141:
				if(var == 4){
					final int targetObjectId = env.getVisibleObject().getObjectId();
					PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
					PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
					qs.setQuestVarById(0, 4);
					qs.setStatus(QuestStatus.REWARD);

						ThreadPoolManager.getInstance().schedule(new Runnable()	{
							@Override
							public void run(){
                                PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
						    	PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.END_LOOT, 0, targetObjectId), true);
								defaultQuestMovie(env, 154);
								updateQuestStatus(env);
								
							}
						}, 3000);
						return true;
					}
			case 700140:
				if (var == 1 && spawned == false){
					final int targetObjectId = env.getVisibleObject().getObjectId();
					PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
					PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
					spawned = true;
					qs.setQuestVarById(0, 3);
					
					ThreadPoolManager.getInstance().schedule(new Runnable()	{
						@Override
						public void run(){
							PacketSendUtility.sendPacket(player, new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
							PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.END_LOOT, 0, targetObjectId), true);
							QuestService.addNewSpawn(320030000, player.getInstanceId(), 210753, 260.12f, 234.93f, 216.00f, (byte)90, true);
							updateQuestStatus(env);
						}
					}, 3000);
				return true;
				}
			default:
				return false;
		}
	}

	@Override
	public boolean onDieEvent(QuestCookie env){
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START)
			return false;
		int var = qs.getQuestVars().getQuestVars();
		if (var >= 2){
			qs.setQuestVar(1);
			updateQuestStatus(env);
		}

		return false;
	}
		
	@Override
	public boolean onEnterWorldEvent(QuestCookie env){
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
			return false;
		if (qs.getStatus() == QuestStatus.START){
			int var = qs.getQuestVars().getQuestVars();
			if(var >= 2){
				if (player.getWorldId() != 320030000){
					qs.setQuestVar(1);
					updateQuestStatus(env);
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean onKillEvent(QuestCookie env)	{
		if (defaultQuestOnKillEvent(env, 210753, 3, 4))
			return true;
		else
			return false;
	}

	public boolean onActionItemEvent(QuestCookie env){
	  return true;
	}
}