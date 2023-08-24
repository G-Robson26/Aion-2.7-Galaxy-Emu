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
 
package quest.academy;

import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.group.PlayerGroup;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.services.instance.AcademyInstanceService;
import gameserver.utils.PacketSendUtility;
import gameserver.world.World;

public class _90002Academy extends QuestHandler {

    private static int questId = 9002;
    private final static int[] npcs = {205331, 205332};

    public _90002Academy() {
        super(questId);
    }

    @Override
    public void register() {
        for (int npc : npcs) {
            qe.setNpcQuestData(npc).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestCookie env) {
        final Player player = env.getPlayer();
        final PlayerGroup group = player.getPlayerGroup();
        int targetId = 0;
        int targetObjId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
            targetObjId = ((Npc) env.getVisibleObject()).getObjectId();
        }
        if (group == null) {
            return false;
        }

        if (targetId == 205331) {
            switch (env.getDialogId()) {
                case -1:
                    AionObject obj = World.getInstance().findAionObject(targetObjId);
                    if (obj instanceof Npc) {
                        Npc npc = (Npc) obj;
                        if (npc != null) {
                            npc.getController().onDespawn(true);
                        }
                    }
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
                    AcademyInstanceService.getInstance().nextSpawn(group);
                    return true;
                default:
                    break;
            }
        }
        return false;
    }
}
