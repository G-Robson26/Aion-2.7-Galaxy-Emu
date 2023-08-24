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
import gameserver.utils.PacketSendUtility;
import gameserver.world.zone.ZoneName;
import gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;


/**
 * @re-Working Leco
 * 
 */

public class _2073CapturedComrades extends QuestHandler {


    private final static int questId = 2073;

    public _2073CapturedComrades() {
        super(questId);
    }

    @Override
    public void register() {
        qe.addQuestLvlUp(questId);
        qe.setNpcQuestData(278002).addOnTalkEvent(questId);
        qe.setNpcQuestData(278019).addOnTalkEvent(questId);
        qe.setNpcQuestData(278088).addOnTalkEvent(questId);
        qe.setNpcQuestData(215045).addOnTalkEvent(questId);
        qe.setQuestEnterZone(ZoneName.Q1073).add(questId);
    }

    @Override
    public boolean onDialogEvent(QuestCookie env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (qs == null)
            return false;

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 278002: {
                    switch (env.getDialogId()) {
                        case 26:
                            if (var == 0)
                                return sendQuestDialog(env, 1011);
                        case 10000:
                            if (var == 0) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                    }
                }
                break;
                case 278019: {
                    switch (env.getDialogId()) {
                        case 26:
                            if (var == 1)
                                return sendQuestDialog(env, 1352);
                        case 10001:
                            if (var == 1) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                    }
                }
                break;
                case 278088: {
                    switch (env.getDialogId()) {
                        case 26:
                            if (var == 2)
                                return sendQuestDialog(env, 1693);
                        case 10002:
                            if (var == 2) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                    }
                }
                break;
                case 253626: {
                    switch (env.getDialogId()) {
                        case 26:
                           if (var == 3)
                                 PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 289));
                            return sendQuestDialog(env, 2034);
                        case 10003:
                           if (var == 3)
                                 return defaultCloseDialog(env, 3, 4);
                              }
                      }
                 }
                
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 278019) {
                if (env.getDialogId() == -1)
                    return sendQuestDialog(env, 10002);
                else if (env.getDialogId() == 1009)
                    return sendQuestDialog(env, 5);
                else return defaultQuestEndDialog(env);
            }
            return false;
        }
	return false;
     }


    @Override
    public boolean onEnterZoneEvent(QuestCookie env, ZoneName zoneName) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (zoneName != ZoneName.Q1073)
            return false;
        if (qs == null || qs.getQuestVarById(0) != 4)
            return false;
        if (qs.getStatus() == QuestStatus.REWARD ||
            qs.getStatus() == QuestStatus.COMPLETE)
         {
           return false;
         }
          PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 290));
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(env);
        return true;
    }




    @Override
    public boolean onLvlUpEvent(QuestCookie env) {
        return defaultQuestOnLvlUpEvent(env, 2701);
    }
}
