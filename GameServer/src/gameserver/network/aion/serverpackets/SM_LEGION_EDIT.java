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

import gameserver.model.legion.Legion;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

import java.nio.ByteBuffer;

/**
 * @author Simple
 */
public class SM_LEGION_EDIT extends AionServerPacket {
    private int type;
    private Legion legion;
    private int unixTime;
    private String announcement;

    public SM_LEGION_EDIT(int type) {
        this.type = type;
    }

    public SM_LEGION_EDIT(int type, Legion legion) {
        this.type = type;
        this.legion = legion;
    }

    public SM_LEGION_EDIT(int type, int unixTime) {
        this.type = type;
        this.unixTime = unixTime;
    }

    public SM_LEGION_EDIT(int type, int unixTime, String announcement) {
        this.type = type;
        this.announcement = announcement;
        this.unixTime = unixTime;
    }

    @Override
    public void writeImpl(AionConnection con, ByteBuffer buf) {
        writeC(buf, type);
        switch (type) {
            /** Change Legion Level **/
            case 0:
                writeC(buf, legion.getLegionLevel());
                break;
            /** Change Legion Rank **/
            case 1:
                writeD(buf, legion.getLegionRank());
                break;
            /** Change Legion Permissions **/
            case 2:
	            writeC(buf, legion.getDeputyPermission1());
	            writeC(buf, legion.getDeputyPermission2());
				writeC(buf, legion.getCenturionPermission1());
				writeC(buf, legion.getCenturionPermission2());
				writeC(buf, legion.getLegionaryPermission1());
				writeC(buf, legion.getLegionaryPermission2());
                writeC(buf, legion.getVolunteerPermission1());
                writeC(buf, legion.getVolunteerPermission2());
                break;
            /** Change Legion Contributions **/
            case 3:
                writeD(buf, legion.getContributionPoints()); // get Contributions
                break;
			case 4:
                writeQ(buf, legion.getLegionKinah());
                writeH(buf, 0);
                writeC(buf, 0);
                break;
            /** Change Legion Announcement **/
            case 5:
                writeS(buf, announcement);
                writeD(buf, unixTime);
                break;
            /** Disband Legion **/
            case 6:
                writeD(buf, unixTime);
                break;
            /** Recover Legion **/
            case 7:
                break;
            /** Refresh Legion Announcement? **/
            case 8:
                break;
			case 9:
            case 10:	
        }
    }
}
