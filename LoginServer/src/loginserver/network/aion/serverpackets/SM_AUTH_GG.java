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
package loginserver.network.aion.serverpackets;

import loginserver.network.aion.AionConnection;
import loginserver.network.aion.AionServerPacket;

import java.nio.ByteBuffer;

/**
 * @author -Nemesiss-
 */
public class SM_AUTH_GG extends AionServerPacket {
    /**
     * Session Id of this connection
     */
    private final int sessionId;

    /**
     * Constructs new instance of <tt>SM_AUTH_GG</tt> packet
     *
     * @param sessionId
     */
    public SM_AUTH_GG(int sessionId) {
        super(0x0b);

        this.sessionId = sessionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeC(buf, getOpcode());
        writeD(buf, sessionId);
        writeD(buf, 0x00);
        writeD(buf, 0x00);
        writeD(buf, 0x00);
        writeD(buf, 0x00);
    }
}
