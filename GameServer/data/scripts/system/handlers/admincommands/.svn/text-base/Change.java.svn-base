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
import gameserver.model.PlayerClass;
import gameserver.model.Gender;
import gameserver.model.Race;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Nightygun
 *
 */
public class Change extends AdminCommand {

    public Change() {
        super("change");
    }

    @Override
    public void executeCommand(Player admin, String... params) {

        if (admin.getAccessLevel() < AdminConfig.CHANGE_ALL) {
            PacketSendUtility.sendMessage(admin, "You dont have enough rights to use this command!");
            return;
        }
        
        if (params == null || params.length < 1 ) {
            PacketSendUtility.sendMessage(admin, "Syntax: //change <chanter/cleric|sorce/sm|asn/ranger|gladi/templar|girl/man|asmo/ely>");
            return;
        }

        if (params[0].toLowerCase().equals("chanter")) {
            admin.getCommonData().setPlayerClass(PlayerClass.CHANTER);
        } else if (params[0].toLowerCase().equals("cleric")) {
            admin.getCommonData().setPlayerClass(PlayerClass.CLERIC);
        } else if (params[0].toLowerCase().equals("sorce")) {
            admin.getCommonData().setPlayerClass(PlayerClass.SORCERER);
        } else if (params[0].toLowerCase().equals("sm")) {
            admin.getCommonData().setPlayerClass(PlayerClass.SPIRIT_MASTER);
        } else if (params[0].toLowerCase().equals("asn")) {
            admin.getCommonData().setPlayerClass(PlayerClass.ASSASSIN);
        } else if (params[0].toLowerCase().equals("ranger")) {
            admin.getCommonData().setPlayerClass(PlayerClass.RANGER);
        } else if (params[0].toLowerCase().equals("gladi")) {
            admin.getCommonData().setPlayerClass(PlayerClass.GLADIATOR);
        } else if (params[0].toLowerCase().equals("templar")) {
            admin.getCommonData().setPlayerClass(PlayerClass.TEMPLAR);
        } else if (params[0].toLowerCase().equals("man")) {
            admin.getCommonData().setGender(Gender.MALE);
        } else if (params[0].toLowerCase().equals("girl")) {
            admin.getCommonData().setGender(Gender.FEMALE);
        } else if (params[0].toLowerCase().equals("asmo")) {
            admin.getCommonData().setRace(Race.ASMODIANS);
        } else if (params[0].toLowerCase().equals("ely")) {
            admin.getCommonData().setRace(Race.ELYOS);
        } else {
        	PacketSendUtility.sendMessage(admin, "Syntax: //change <chanter/cleric|sorce/sm|asn/ranger|gladi/templar|girl/man|asmo/ely>");
        }
        admin.clearKnownlist();
        PacketSendUtility.sendPacket(admin, new SM_PLAYER_INFO(admin, false));
        admin.updateKnownlist();
    }
}