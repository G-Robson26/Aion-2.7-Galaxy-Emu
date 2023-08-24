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
 * @author kosyachok
 */
public class SM_DELETE_WAREHOUSE_ITEM extends AionServerPacket {
    private int warehouseType;
    private int itemObjId;


    public SM_DELETE_WAREHOUSE_ITEM(int warehouseType, int itemObjId) {
        this.warehouseType = warehouseType;
        this.itemObjId = itemObjId;
    }


    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeC(buf, warehouseType);
        writeD(buf, itemObjId);
        writeC(buf, 20);
    }

}
