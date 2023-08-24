/*
 *  This file is part of Zetta-Core Engine <http://www.zetta-core.org>.
 *
 *  Zetta-Core is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the License,
 *  or (at your option) any later version.
 *
 *  Zetta-Core is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a  copy  of the GNU General Public License
 *  along with Zetta-Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package loginserver.network.gameserver.clientpackets;

import java.nio.ByteBuffer;

import com.aionemu.commons.database.dao.DAOManager;

import loginserver.dao.BannedMacDAO;
import loginserver.network.gameserver.GsClientPacket;
import loginserver.network.gameserver.GsConnection;
import loginserver.network.gameserver.serverpackets.SM_MACBAN_LIST;
import loginserver.network.gameserver.serverpackets.SM_MACBAN_REQUEST_MAC;

/**
 * @author Administrator
 *
 */
public class CM_REQUEST_MAC extends GsClientPacket {
	
	private int	playerId;
	private int	type;
	
	public CM_REQUEST_MAC(ByteBuffer buf, GsConnection client)
	{
		super(buf, client, 0x12);
	}
	
	@Override
	protected void readImpl() {
		type = readD();
		playerId = readD();
	}
	
	@Override
	protected void runImpl() {
		switch(type)
		{
			case 1://getMacbyPlayerId
				String macAddress = DAOManager.getDAO(BannedMacDAO.class).getMacAddress(playerId);
				sendPacket(new SM_MACBAN_REQUEST_MAC(macAddress));
				break;
			case 2://reload MacAddress List
				sendPacket(new SM_MACBAN_LIST());
				break;
		}
	}
}
