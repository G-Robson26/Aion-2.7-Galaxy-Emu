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

package gameserver.network.aion.serverpackets;

import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

import java.nio.ByteBuffer;

/**
 * This packet is used to teleport player
 *
 * @author Luno , orz
 */
public class SM_TELEPORT_LOC extends AionServerPacket {

    private int mapId;
    private float x, y, z;
	private int type;

    public SM_TELEPORT_LOC(int mapId, float x, float y, float z, int type) {
        this.mapId = mapId;
        this.x = x;
        this.y = y;
        this.z = z;
		this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeC(buf, type); //unk
        writeC(buf, 144);  //unk
        writeC(buf, 158);  //unk
        writeD(buf, mapId); //mapid
        writeF(buf, x); //x
        writeF(buf, y); //y
        writeF(buf, z); //z
        writeC(buf, 0);  //headling
    }

}
