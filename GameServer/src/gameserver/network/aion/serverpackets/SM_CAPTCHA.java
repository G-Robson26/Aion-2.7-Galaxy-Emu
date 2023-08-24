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

public class SM_CAPTCHA extends AionServerPacket
{
  private int type;
  private int count;
  private int size;
  private byte[] data;
  private boolean isCorrect;
  private int banTime;

  public SM_CAPTCHA(int count, byte[] paramArrayOfByte)
  {
    this.type = 1;
    this.count = count;
    this.size = paramArrayOfByte.length;
    this.data = paramArrayOfByte;
  }

  public SM_CAPTCHA(boolean paramBoolean, int banTime)
  {
    this.type = 3;
    this.isCorrect = paramBoolean;
    this.banTime = banTime;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf)
  {
    writeC(buf, this.type);
    switch (this.type)
    {
    case 1:
      writeC(buf, count);
      writeD(buf, size);
      writeB(buf, data);
      break;
    case 3:
      writeH(buf, isCorrect ? 1 : 0);
      writeD(buf, banTime);
    }
  }
}