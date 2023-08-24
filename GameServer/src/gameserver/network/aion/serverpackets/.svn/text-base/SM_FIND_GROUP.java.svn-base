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
import java.util.Collection;

import gameserver.model.gameobjects.LFGApplyGroup;
import gameserver.model.gameobjects.LFGRecruitGroup;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;
import gameserver.services.LGFService;


/**
 * @author ginho1, oni
 */
public class SM_FIND_GROUP extends AionServerPacket
{
	private int type;
	private Player player;
	
	public SM_FIND_GROUP(int type, Player player)
	{
		this.type = type;
		this.player = player;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		writeC(buf, type);

		switch(type)
		{
			//recruit group
			case 0:
				Collection<LFGRecruitGroup> playerRecruitGroups = LGFService.getInstance().geRecruitGroup(player.getCommonData().getRace());

				writeH(buf, playerRecruitGroups.size());//count1
				writeH(buf, playerRecruitGroups.size());//count2

				writeD(buf, (int)System.currentTimeMillis());

				for(LFGRecruitGroup playerRecruitGroup : playerRecruitGroups)
				{
					Player pl = playerRecruitGroup.getPlayer();
					writeD(buf, pl.getObjectId());//playerID

					if(pl.isInGroup())
						writeD(buf, pl.getPlayerGroup().getGroupId());
					else
						writeD(buf, 0);
					
					writeC(buf, playerRecruitGroup.getGroupType());//0 to group 1 to alliance
					writeS(buf, playerRecruitGroup.getApplyString());
					writeS(buf, pl.getName());//playerName
					writeC(buf, pl.getPlayerGroup().getMembers().size());//groupSize
					writeC(buf, pl.getLevel());//level
					writeC(buf, playerRecruitGroup.getMaxLevel());//max level
					writeD(buf, (int)playerRecruitGroup.getCreationTime());

					//expire in 1 hour
					if((System.currentTimeMillis() - playerRecruitGroup.getCreationTime()) > 3600000)
						LGFService.getInstance().removeApplyGroup(pl.getObjectId());
				}
			break;
			//remove recruit group
			case 1:
					writeD(buf, player.getObjectId());
					if(player.isInGroup())
						writeD(buf, player.getPlayerGroup().getGroupId());
					else
						writeD(buf, 0);
					writeC(buf, 0);
					writeH(buf, 1);
			break;
			//apply for group
			case 4:
				Collection<LFGApplyGroup> playerApplyGroups = LGFService.getInstance().geApplyGroup(player.getCommonData().getRace());

				writeH(buf, playerApplyGroups.size());//count1
				writeH(buf, playerApplyGroups.size());//count2

				writeD(buf, (int)System.currentTimeMillis());

				for(LFGApplyGroup playerApplyGroup : playerApplyGroups)
				{
					Player pl = playerApplyGroup.getPlayer();
					writeD(buf, pl.getObjectId());//playerID
					writeC(buf, playerApplyGroup.getGroupType());//0 to group 1 to alliance
					writeS(buf, playerApplyGroup.getApplyString());
					writeS(buf, pl.getName());//playerName
					writeC(buf, pl.getPlayerClass().getClassId());//class
					writeC(buf, pl.getLevel());//level
					writeD(buf, (int)playerApplyGroup.getCreationTime());

					//expire in 1 hour
					if((System.currentTimeMillis() - playerApplyGroup.getCreationTime()) > 3600000)
						LGFService.getInstance().removeApplyGroup(pl.getObjectId());
				}
			break;
			//remove apply for group
			case 5:
					writeD(buf, player.getObjectId());
					writeH(buf, 722);
					writeC(buf, 0);
			break;
		}
	}
}
