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
package gameserver.network.loginserver.serverpackets;

import gameserver.network.loginserver.LoginServerConnection;
import gameserver.network.loginserver.LsServerPacket;

import java.nio.ByteBuffer;

/**
 * The universal packet for account/IP bans
 *
 * @author Watson
 */
public class SM_BAN extends LsServerPacket {
    /**
     * Ban type
     * 1 = account
     * 2 = IP
     * 3 = Full ban (account and IP)
     */
    private final byte type;

    /**
     * Account to ban
     */
    private final int accountId;

    /**
     * IP or mask to ban
     */
    private final String ip;

    /**
     * Time in minutes. 0 = infinity;
     * If time < 0 then it's unban command
     */
    private final int time;

    /**
     * Object ID of Admin, who request the ban
     */
    private final int adminObjId;

    public SM_BAN(byte type, int accountId, String ip, int time, int adminObjId) {
        super(0x06);

        this.type = type;
        this.accountId = accountId;
        this.ip = ip;
        this.time = time;
        this.adminObjId = adminObjId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
        writeC(buf, getOpcode());

        writeC(buf, type);
        writeD(buf, accountId);
        writeS(buf, ip);
        writeD(buf, time);
        writeD(buf, adminObjId);
	}
}
