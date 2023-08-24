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

package admincommands;

import gameserver.configs.administration.AdminConfig;
import gameserver.model.gameobjects.player.Player;
import gameserver.services.ItemService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.Util;
import gameserver.utils.chathandlers.AdminCommand;
import gameserver.utils.i18n.CustomMessageId;
import gameserver.utils.i18n.LanguageHandler;
import gameserver.world.World;

/**
 * @author Phantom, ATracer
 */

public class Add extends AdminCommand {

    public Add() {
        super("add");
    }

    @Override
    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_ADD) {
            PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.COMMAND_NOT_ENOUGH_RIGHTS));
            return;
        }

        if (params.length == 0 || params.length > 3) {
            PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.COMMAND_ADD_SYNTAX));
            return;
        }

        int itemId = 0;
        long itemCount = 1;
        Player receiver = null;

        try {
            itemId = Integer.parseInt(params[0]);

            if (params.length == 2) {
                itemCount = Long.parseLong(params[1]);
            }
            receiver = admin;
        }
        catch (NumberFormatException e) {
            receiver = World.getInstance().findPlayer(Util.convertName(params[0]));

            if (receiver == null) {
                PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.PLAYER_NOT_ONLINE, Util.convertName(params[0])));
                return;
            }

            try {
                itemId = Integer.parseInt(params[1]);

                if (params.length == 3) {
                    itemCount = Long.parseLong(params[2]);
                }
            }
            catch (NumberFormatException ex) {
                PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.INTEGER_PARAMETER_REQUIRED));
                return;
            }
            catch (Exception ex2) {
                PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.SOMETHING_WRONG_HAPPENED));
                return;
            }
        }

        long count = ItemService.addItem(receiver, itemId, itemCount, admin.getName());

        if (count == 0) {
            PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.COMMAND_ADD_ADMIN_SUCCESS, receiver.getName()));
            PacketSendUtility.sendMessage(receiver, LanguageHandler.translate(CustomMessageId.COMMAND_ADD_PLAYER_SUCCESS, admin.getName(), itemCount));
        } else {
            PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.COMMAND_ADD_FAILURE, itemId, receiver.getName()));
        }
    }
}
