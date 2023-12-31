/**
 * This file is part of Aion Galxay Emu <aiongemu.com>
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

package gameserver.network.aion.serverpackets;

import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

import java.nio.ByteBuffer;

/**
 * This packet is used to update mp / max mp value.
 *
 * @author Luno
 */
public class SM_STATUPDATE_MP extends AionServerPacket {

    private int currentMp;
    private int maxMp;

    /**
     * @param currentMp
     * @param maxMp
     */
    public SM_STATUPDATE_MP(int currentMp, int maxMp) {
        this.currentMp = currentMp;
        this.maxMp = maxMp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeD(buf, currentMp);
        writeD(buf, maxMp);
    }

}
