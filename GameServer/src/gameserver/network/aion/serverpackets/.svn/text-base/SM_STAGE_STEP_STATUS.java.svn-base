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

import java.nio.ByteBuffer;

import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

public class SM_STAGE_STEP_STATUS extends AionServerPacket
{
	private int unk1;
	private int unk2;
	private int mess;

	public SM_STAGE_STEP_STATUS(int unk1, int mess, int unk2)
	{
		this.unk1 = unk1;
		this.mess = mess;
		this.unk2 = unk2;
	}

	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		writeC(buf, unk1); // unk 2 or 3 
		writeD(buf, 0); 
		writeH(buf, mess); // id Stage/Round
		writeH(buf, unk2); // unk(1,3,4,6......)
	}
}