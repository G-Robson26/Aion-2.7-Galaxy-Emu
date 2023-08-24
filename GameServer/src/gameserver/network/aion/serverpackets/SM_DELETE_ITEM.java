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

//Author Avol

public class SM_DELETE_ITEM extends AionServerPacket {
    private int itemUniqueId;

    public SM_DELETE_ITEM(int itemUniqueId) {
        this.itemUniqueId = itemUniqueId;
    }


    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeD(buf, itemUniqueId);
        writeC(buf, 21); //unk. can be any 1,2,3 etc.
    }
}