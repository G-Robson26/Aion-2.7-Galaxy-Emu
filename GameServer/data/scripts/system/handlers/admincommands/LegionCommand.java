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
import gameserver.model.legion.Legion;
import gameserver.services.LegionService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Simple
 */

public class LegionCommand extends AdminCommand {

    /**
     * The constructor of Legion Command
     */
    public LegionCommand() {
        super("legion");
    }

    @Override
    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_LEGION) {
            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
            return;
        }

        if (params.length < 2) {
            PacketSendUtility.sendMessage(admin,
                    "syntax //legion <disband | setlevel | setpoints | setname <legion name> <value>");
            return;
        }
        LegionService legionService = LegionService.getInstance();
        Legion legion = legionService.getLegion(params[1].toLowerCase());
        if (legion == null) {
            PacketSendUtility.sendMessage(admin, "The " + params[1].toLowerCase() + " legion does not exist.");
            return;
        }

        if (params[0].toLowerCase().equals("disband")) {
            legionService.disbandLegion(legion);
            PacketSendUtility.sendMessage(admin, "The following legion has been disbanded: " + legion.getLegionName());
        } else if (params[0].toLowerCase().equals("setlevel")) {
            int newLevel = Integer.parseInt(params[2]);

            if (newLevel < 1 || newLevel > 5) {
                PacketSendUtility.sendMessage(admin, "Please use a valid legion level. (1 - 5)");
                return;
            } else if (legion.getLegionLevel() == newLevel) {
                PacketSendUtility.sendMessage(admin, "Level of legion already is " + newLevel);
                return;
            }
            legionService.changeLevel(legion, newLevel, true);
            PacketSendUtility.sendMessage(admin, "The " + legion.getLegionName() + " legion has been leveled up to level "
                    + newLevel);
        } else if (params[0].toLowerCase().equals("setpoints")) {
            int newPoints = Integer.parseInt(params[2]);

            if (newPoints <= 0 || newPoints > 2000000000) {
                PacketSendUtility.sendMessage(admin, "Please use valid points amount. (0 - 2.000.000.000)");
                return;
            } else if (legion.getContributionPoints() == newPoints) {
                PacketSendUtility.sendMessage(admin, "Contribution Points of legion already is " + newPoints);
                return;
            }
            legionService.setContributionPoints(legion, newPoints, true);
            PacketSendUtility.sendMessage(admin, "The " + legion.getLegionName()
                    + " legion points have been changed to " + newPoints);
        } else if (params[0].toLowerCase().equals("setname")) {
            String newLegionName = params[2];

            if (!legionService.isValidName(newLegionName)) {
                PacketSendUtility.sendMessage(admin, "Please use a valid legion name!");
                return;
            } else if (legion.getLegionName().toLowerCase() == newLegionName.toLowerCase()) {
                PacketSendUtility.sendMessage(admin, "Name of legion already is " + newLegionName);
                return;
            }
            legionService.setLegionName(legion, newLegionName, true);
            PacketSendUtility.sendMessage(admin, "The " + legion.getLegionName()
                    + " legion's name has been changed to " + newLegionName);
        }
    }
}
