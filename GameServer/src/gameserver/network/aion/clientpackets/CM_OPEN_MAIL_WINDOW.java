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
package gameserver.network.aion.clientpackets;

import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.AionClientPacket;
import gameserver.network.aion.serverpackets.SM_MAIL_SERVICE;
import gameserver.utils.PacketSendUtility;

/**
 * @author ginho1
 *
 */
public class CM_OPEN_MAIL_WINDOW extends AionClientPacket
{

	/**
	 * @param opcode
	 */
	public CM_OPEN_MAIL_WINDOW(int opcode)
	{
		super(opcode);
	}
	
	@Override
	protected void readImpl()
	{
	    // TODO: Make Mail zephyr like retail
	    // readC() if it is express mail zephyr is 0x01
	    // only black cloud mail list should be shown.
	}

	@Override
	protected void runImpl()
	{
		Player player = this.getConnection().getActivePlayer();
		PacketSendUtility.sendPacket(player, new SM_MAIL_SERVICE(player, player.getMailbox().getLetters()));
	}

}
