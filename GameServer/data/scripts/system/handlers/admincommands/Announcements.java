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
import gameserver.model.Announcement;
import gameserver.model.gameobjects.player.Player;
import gameserver.services.AnnouncementService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.chathandlers.AdminCommand;

import java.util.Set;

/**
 * @author Divinity
 */
public class Announcements extends AdminCommand {
    private AnnouncementService announceService;

    public Announcements() {
        super("announcements");
        announceService = AnnouncementService.getInstance();
    }

    @Override
    public void executeCommand(Player admin, String[] params) {
        String syntaxCommand = "Syntax: //announcements list - Obtain all announcements in the database.\n";
        syntaxCommand += "Syntax: //announcements add <faction: ELYOS | ASMODIANS | ALL> <type: NORMAL | ANNOUNCE | ORANGE | YELLOW | SHOUT> <delay in seconds> <message> - Add an announcements in the database.\n";
        syntaxCommand += "Syntax: //announcements delete <id> - Delete an announcements from the database.";

        if (admin.getAccessLevel() < AdminConfig.COMMAND_ANNOUNCEMENTS) {
            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");
            return;
        }

        if ((params.length < 1) || (params == null)) {
            PacketSendUtility.sendMessage(admin, syntaxCommand);
            return;
        }

        if (params[0].equals("list")) {
            Set<Announcement> announces = announceService.getAnnouncements();
            PacketSendUtility.sendMessage(admin, "ID | FACTION | CHAT TYPE | DELAY | MESSAGE");
            PacketSendUtility.sendMessage(admin, "-------------------------------------------------------------");

            for (Announcement announce : announces)
                PacketSendUtility.sendMessage(admin, announce.getId() + " | " + announce.getFaction() + " | " + announce.getType() + " | " + announce.getDelay() + " | " + announce.getAnnounce());
        } else if (params[0].equals("add")) {
            if ((params.length < 5)) {
                PacketSendUtility.sendMessage(admin, syntaxCommand);
                return;
            }

            int delay;

            try {
                delay = Integer.parseInt(params[3]);
            }
            catch (NumberFormatException e) {
                // 15 minutes, default
                delay = 900;
            }

            String message = "";
            if (params.length > 5) {
                // Add with space
                for (int i = 4; i < params.length - 1; i++)
                    message += params[i] + " ";

                // Add the last without the end space
                message += params[params.length - 1];
            }
            else {
                message = params[4];
            }

            // Create the announce
            Announcement announce = new Announcement(message, params[1], params[2], delay);

            // Add the announce in the database
            announceService.addAnnouncement(announce);

            // Reload all announcements
            announceService.reload();

            PacketSendUtility.sendMessage(admin, "The announcement has been created successfuly !");
        } else if (params[0].equals("delete")) {
            if ((params.length < 2)) {
                PacketSendUtility.sendMessage(admin, syntaxCommand);
                return;
            }

            int id;

            try {
                id = Integer.parseInt(params[1]);
            }
            catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The announcement's ID is wrong !");
                PacketSendUtility.sendMessage(admin, syntaxCommand);
                return;
            }

            // Delete the announcement from the database
            announceService.delAnnouncement(id);

            // Reload all announcements
            announceService.reload();

            PacketSendUtility.sendMessage(admin, "The announcement has been deleted successfuly !");
        } else {
            PacketSendUtility.sendMessage(admin, syntaxCommand);
            return;
        }
    }
}
