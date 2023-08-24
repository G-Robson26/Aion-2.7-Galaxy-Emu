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
import gameserver.services.TeleportService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.Util;
import gameserver.utils.chathandlers.AdminCommand;
import gameserver.world.World;

/**
 * Admin moveplayertoplayer command.
 *
 * @author Tanelorn
 */

public class MovePlayerToPlayer extends AdminCommand {

    /**
     * Constructor.
     */

    public MovePlayerToPlayer() {
        super("moveplayertoplayer");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_MOVEPLAYERTOPLAYER) {
            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
            return;
        }

        if (params == null || params.length < 2) {
            PacketSendUtility.sendMessage(admin, "syntax //moveplayertoplayer <player name 1> <player name 2>");
            return;
        }

        Player playerToMove = World.getInstance().findPlayer(Util.convertName(params[0]));

        if (playerToMove == null) {
            PacketSendUtility.sendMessage(admin, "The specified player is not online.");
            return;
        }

        Player playerDestination = World.getInstance().findPlayer(Util.convertName(params[1]));

        if (playerDestination == null) {
            PacketSendUtility.sendMessage(admin, "The destination player is not online.");
            return;
        }

        if (playerToMove == playerDestination) {
            PacketSendUtility.sendMessage(admin, "Cannot move the specified player to their own position.");
            return;
        }

        TeleportService.teleportTo(playerToMove, playerDestination.getWorldId(), playerDestination.getInstanceId(), playerDestination.getX(), playerDestination.getY(), playerDestination.getZ(), playerDestination.getHeading(), 0);
        PacketSendUtility.sendMessage(admin, "Teleported player " + playerToMove.getName() + " to the location of player " + playerDestination.getName() + ".");
        PacketSendUtility.sendMessage(playerToMove, "You have been teleported by an administrator.");
    }
}
