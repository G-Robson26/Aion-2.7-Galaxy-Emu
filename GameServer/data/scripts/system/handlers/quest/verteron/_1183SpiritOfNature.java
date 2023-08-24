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

package quest.verteron;

import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.quest.QuestItems;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.ItemService;
import gameserver.utils.PacketSendUtility;

import java.util.Collections;

public class _1183SpiritOfNature extends QuestHandler {
    private final static int questId = 1183;

    public _1183SpiritOfNature() {
        super(questId);
    }

    @Override
    public void register() {
        qe.setNpcQuestData(730012).addOnQuestStart(questId);
        qe.setNpcQuestData(730012).addOnTalkEvent(questId);
        qe.setNpcQuestData(730013).addOnTalkEvent(questId);
        qe.setNpcQuestData(730014).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestCookie env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 730012) {
                if (env.getDialogId() == 26) {
                    return sendQuestDialog(env, 1011);
                } else
                    return defaultQuestStartDialog(env);
            }
        }
        if (qs == null)
            return false;

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 730013: {
                    switch (env.getDialogId()) {
                        case -1: {
                            return sendQuestDialog(env, 1352);
                        }
                        case 10000: {
                            if (player.getInventory().getItemCountByItemId(182200550) == 0)
                                if (!ItemService.addItems(player, Collections
                                        .singletonList(new QuestItems(182200550, 1))))
                                    return true;
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(env);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject()
                                    .getObjectId(), 10));
                            return true;
                        }
                    }
                }
                case 730014: {
                    switch (env.getDialogId()) {
                        case 26: {
                            return sendQuestDialog(env, 1693);
                        }
                        case 10001: {
                            if (player.getInventory().getItemCountByItemId(182200565) == 0)
                                if (!ItemService.addItems(player, Collections
                                        .singletonList(new QuestItems(182200565, 1))))
                                    return true;
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(env);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject()
                                    .getObjectId(), 10));
                            return true;
                        }
                        default:
                            return defaultQuestEndDialog(env);
                    }
                }
                case 730012: {
                    switch (env.getDialogId()) {
                        case 26: {
                            return sendQuestDialog(env, 2375);
                        }
                        case 1009: {
                            qs.setQuestVar(3);
                            player.getInventory().removeFromBagByItemId(182200550, 1);
                            player.getInventory().removeFromBagByItemId(182200565, 1);
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(env);
                            return defaultQuestEndDialog(env);
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 730012) {
                if (env.getDialogId() == 1009)
                    return sendQuestDialog(env, 5);
                else
                    return defaultQuestEndDialog(env);
            }
        }
        return false;
    }
}