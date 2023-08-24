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

import gameserver.model.gameobjects.Creature;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

import java.nio.ByteBuffer;


/**
 * @author Sweetkr
 * @author Camelot (Abyss Skills activation)
 */
public class SM_TRANSFORM extends AionServerPacket
{
	private Creature creature;
	private int	state;

	public SM_TRANSFORM(Creature creature)
	{
		this.creature = creature;
		this.state = creature.getState();
	}

	/**
	 * {@inheritDoc}
	 */
    protected void writeImpl(AionConnection com, ByteBuffer buf)  {
    writeD(buf, creature.getObjectId().intValue());
    writeD(buf, creature.getTransformedModelId());
    writeH(buf, state);
    writeF(buf, 0.55F);
    writeF(buf, 1.5F);
    switch (creature.getTransformedModelId())
    {
    case 202502:
    case 202503:
    case 202504:
    case 202505:
    case 202506:
    case 202507:
    case 202508:
    case 202509:
    case 202510:
    case 202511:
      writeC(buf, 0);
      writeD(buf, 2);
      writeD(buf, 0);
      break;
    case 210138:
    case 210306:
    case 210390:
    case 210421:
    case 210757:
    case 211875:
      writeC(buf, 1);
      writeD(buf, 0);
      writeD(buf, 1);
      break;
    default:
      writeC(buf, 0);
      writeD(buf, 1);
      writeD(buf, 0);
    }
    writeH(buf, 0);
    writeC(buf, 0);
  }
}