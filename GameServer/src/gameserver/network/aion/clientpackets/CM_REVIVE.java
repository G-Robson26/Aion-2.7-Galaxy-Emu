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
 **/

package gameserver.network.aion.clientpackets;

import gameserver.controllers.ReviveType;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.AionClientPacket;

import org.apache.log4j.Logger;


/**
 * @author ATracer, orz, avol, Simple
 *
 */
public class CM_REVIVE extends AionClientPacket
{
	private int reviveId;
	
	/**
	 * Constructs new instance of <tt>CM_REVIVE </tt> packet
	 * @param opcode
	 */
	public CM_REVIVE(int opcode)
	{
		super(opcode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl()
	{
		reviveId = readC();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl()
	{
		Player activePlayer = getConnection().getActivePlayer();
		
		if (!activePlayer.getLifeStats().isAlreadyDead())
		{
			Logger.getLogger(this.getClass()).info("[AUDIT]Player "+activePlayer.getName()+" sending fake CM_REVIVE: Player is not dead!");
			return;
		}
		
		ReviveType reviveType = ReviveType.getReviveTypeById(reviveId);
		
		switch(reviveType)
		{
			case BIND_REVIVE:
				activePlayer.getReviveController().bindRevive();
				break;
			case REBIRTH_REVIVE:
				activePlayer.getReviveController().rebirthRevive();
				break;
			case ITEM_SELF_REVIVE:
				activePlayer.getReviveController().itemSelfRevive();
				break;
			case SKILL_REVIVE:
				activePlayer.getReviveController().skillRevive(true);
				break;
			case KISK_REVIVE:
				activePlayer.getReviveController().kiskRevive();
				break;
			default:
				break;
		}
		
	}
}