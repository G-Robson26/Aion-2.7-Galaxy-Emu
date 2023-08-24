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

package gameserver.network.aion.clientpackets;

import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.AionClientPacket;
import gameserver.world.World;

/**
 * @author alexa026, Avol, ATracer
 */
public class CM_ATTACK extends AionClientPacket {
	/**
	 * Packet send when player is auto attacking
	 */
	private int					targetObjectId;
	private int					attackno;
	private int					time;
	private int					type;
	
	// TODO: Question, are they really needed?
	@SuppressWarnings("unused")
	private long                exp;
	@SuppressWarnings("unused")
	private long                maxexp;
	@SuppressWarnings("unused")
	private int					at;

    public CM_ATTACK(int opcode) {
        super(opcode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
 		targetObjectId = readD();
		attackno = readC();
		time = readH();
		type = readC();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if (player != null && !player.getLifeStats().isAlreadyDead()) {
            AionObject object = World.getInstance().findAionObject(targetObjectId);
            if (object instanceof Creature)
                player.getController().attackTarget((Creature) object);
        }
    }
}
