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
import java.util.ArrayList;
import java.util.List;
import gameserver.model.gameobjects.Npc;
import gameserver.model.templates.spawn.SpawnTemplate;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

public class SM_NPC_ASSEMBLER extends AionServerPacket
{
  private List<Npc> parts = new ArrayList();

  public SM_NPC_ASSEMBLER(List<Npc> paramList)
  {
    this.parts = paramList;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf)
  {
    writeD(buf, parts.size());
    for (int i = 0; i < parts.size(); i++)
    {
      writeD(buf, 3);
      writeD(buf, ((Npc) parts.get(i)).getObjectId().intValue());
      writeD(buf, ((Npc) parts.get(i)).getNpcId());
      writeD(buf, ((Npc) parts.get(i)).getSpawn().getStaticid());
      writeD(buf, 203);
    }
  }
}