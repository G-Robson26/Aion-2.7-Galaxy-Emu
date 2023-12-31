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
package gameserver.network.aion.clientpackets;

import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.player.DeniedStatus;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.RequestResponseHandler;
import gameserver.network.aion.AionClientPacket;
import gameserver.network.aion.SystemMessageId;
import gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.services.ExchangeService;
import gameserver.utils.PacketSendUtility;
import gameserver.world.World;

/**
 * @author -Avol-
 */
public class CM_EXCHANGE_REQUEST extends AionClientPacket {
    public Integer targetObjectId;

    public CM_EXCHANGE_REQUEST(int opcode) {
        super(opcode);
    }

    @Override
    protected void readImpl() {
        targetObjectId = readD();
    }


    @Override
    protected void runImpl() {
        final Player activePlayer = getConnection().getActivePlayer();
        final Player targetPlayer = World.getInstance().findPlayer(targetObjectId);

        /**
         * check if not trading with yourself.
         */
        if (activePlayer != targetPlayer) {
            /**
             * check if trade partner exists or is he/she a player.
             */
            if (targetPlayer != null) {
                if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.TRADE)) {
                    sendPacket(SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_TRADE(targetPlayer.getName()));
                    return;
                }
                sendPacket(SM_SYSTEM_MESSAGE.REQUEST_TRADE(targetPlayer.getName()));

                RequestResponseHandler responseHandler = new RequestResponseHandler(activePlayer) {
                    @Override
                    public void acceptRequest(Creature requester, Player responder) {
                        ExchangeService.getInstance().registerExchange(activePlayer, targetPlayer);
                    }

                    @Override
                    public void denyRequest(Creature requester, Player responder) {
                        PacketSendUtility.sendPacket(activePlayer, new SM_SYSTEM_MESSAGE(SystemMessageId.EXCHANGE_HE_REJECTED_EXCHANGE, targetPlayer.getName()));
                    }
                };

                boolean requested = targetPlayer.getResponseRequester().putRequest(SM_QUESTION_WINDOW.STR_EXCHANGE_DO_YOU_ACCEPT_EXCHANGE, responseHandler);
                if (requested) {
                    PacketSendUtility.sendPacket(targetPlayer, new SM_QUESTION_WINDOW(SM_QUESTION_WINDOW.STR_EXCHANGE_DO_YOU_ACCEPT_EXCHANGE, 0, activePlayer.getName()));
                }
            }
        } else {
            //TODO: send message, cannot trade with yourself.
        }
    }
}