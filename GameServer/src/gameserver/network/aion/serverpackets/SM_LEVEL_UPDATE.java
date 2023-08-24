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
 * @author ATracer
 */
public class SM_LEVEL_UPDATE extends AionServerPacket {
    private int targetObjectId;
    private int effect;
    private int level;

    public SM_LEVEL_UPDATE(int targetObjectId, int effect, int level) {
        this.targetObjectId = targetObjectId;
        this.effect = effect;
        this.level = level;
    }

    /**
     * {@inheritDoc} dc
     */

    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeD(buf, targetObjectId);
        writeH(buf, effect); //unk
        writeH(buf, level);
        writeH(buf, 0x00); //unk
    }
}
