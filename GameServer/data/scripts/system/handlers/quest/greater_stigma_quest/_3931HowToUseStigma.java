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

package quest.greater_stigma_quest;

import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.quest.QuestItems;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.ItemService;
import gameserver.services.QuestService;
import gameserver.utils.PacketSendUtility;

import java.util.Collections;

public class _3931HowToUseStigma extends QuestHandler {

    private final static int questId = 3931;
    private final static int[] npc_ids = {798321, 279005, 203711};
    /*
    *
    * 798321 - Koruchinerk
    * 279005 - Kohrunerk
    * 203711 - Miriya
    *
    * 182207104 - Pirates Research Log
    */

    public _3931HowToUseStigma() {
        super(questId);
    }

    @Override
    public void register() {
        qe.setNpcQuestData(203711).addOnQuestStart(questId);    //Miriya
        qe.setQuestItemIds(182206080).add(questId); //Kohrunerks Belt
        qe.setQuestItemIds(182206081).add(questId);    //Stigma Manual
        for (int npc_id : npc_ids)
            qe.setNpcQuestData(npc_id).addOnTalkEvent(questId);
    }


    @Override
    public boolean onDialogEvent(QuestCookie env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 203711)//Miriya
            {
                if (env.getDialogId() == 26)
                    return sendQuestDialog(env, 4762);
                else return defaultQuestStartDialog(env);

            }
            return false;
        }


        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 203711 && player.getInventory().getItemCountByItemId(182206081) == 1)//Miriya
            {
                if (env.getDialogId() == -1)
                    return sendQuestDialog(env, 10002);
                else if (env.getDialogId() == 1009)
                    return sendQuestDialog(env, 5);
                else return defaultQuestEndDialog(env);
            }
            return false;
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 798321)//Koruchinerk
            {
                switch (env.getDialogId()) {
                    case 26:
                        if (var == 0)
                            return sendQuestDialog(env, 1011);
                        if (var == 1)
                            return sendQuestDialog(env, 1352);
                    case 34:
                        if (var == 1) {
                            if (QuestService.collectItemCheck(env, true)) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                ItemService.addItems(player, Collections.singletonList(new QuestItems(182206080, 1)));
                                return sendQuestDialog(env, 10000);
                            } else
                                return sendQuestDialog(env, 10001);
                        }
                    case 10000:
                        if (var == 0)
                            qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                }
                return false;
            } else if (targetId == 279005 && player.getInventory().getItemCountByItemId(182206080) == 1)//Kohrunerk
            {
                switch (env.getDialogId()) {
                    case 26:
                        if (var == 2)
                            return sendQuestDialog(env, 1693);
                    case 10255:
                        if (var == 2)
                            player.getInventory().removeFromBagByItemId(182206080, 1);
                        ItemService.addItems(player, Collections.singletonList(new QuestItems(182206081, 1)));
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        return true;
                }


            }
            return false;
        }
        return false;
    }

}
