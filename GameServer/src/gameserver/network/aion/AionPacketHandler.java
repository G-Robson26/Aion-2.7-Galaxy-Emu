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
package gameserver.network.aion;

import gameserver.configs.network.NetworkConfig;
import gameserver.network.aion.AionConnection.State;
import gameserver.utils.Util;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author -Nemesiss-
 * @author Luno
 * @author drsaluml
 */
public class AionPacketHandler {
    /**
     * logger for this class
     */
    private static final Logger log = Logger.getLogger(AionPacketHandler.class);

    private Map<State, Map<Integer, AionClientPacket>> packetsPrototypes = new HashMap<State, Map<Integer, AionClientPacket>>();

    /**
     * Reads one packet from given ByteBuffer
     *
     * @param data
     * @param client
     * @return AionClientPacket object from binary data
     */
    public AionClientPacket handle(ByteBuffer data, AionConnection client) {
        State state = client.getState();
        short id = data.getShort(); //Opcode 2 bytes Aion 2.7.

        /* Second opcodec. */
        data.position(data.position() + 3);

        return getPacket(state, id, data, client);
    }

    public void addPacketPrototype(AionClientPacket packetPrototype, State... states) {
        for (State state : states) {
            Map<Integer, AionClientPacket> pm = packetsPrototypes.get(state);
            if (pm == null) {
                pm = new HashMap<Integer, AionClientPacket>();
                packetsPrototypes.put(state, pm);
            }
            pm.put(packetPrototype.getOpcode(), packetPrototype);
        }
    }

    private AionClientPacket getPacket(State state, int id, ByteBuffer buf, AionConnection con) {
        AionClientPacket prototype = null;

        Map<Integer, AionClientPacket> pm = packetsPrototypes.get(state);
        if (pm != null) {
            prototype = pm.get(id);
        }

        if (prototype == null) {
            unknownPacket(state, id, buf);
            return null;
        }

        AionClientPacket res = prototype.clonePacket();
        res.setBuffer(buf);
        res.setConnection(con);

        return res;
    }

    /**
     * Logs unknown packet.
     *
     * @param state
     * @param id
     * @param data
     */
    private void unknownPacket(State state, int id, ByteBuffer data) {
        if (NetworkConfig.DISPLAY_UNKNOWNPACKETS) {
            log.warn(String.format("Unknown packet recived from Aion client: 0x%02X, state=%s %n%s", id, state
                    .toString(), Util.toHex(data)));
		}
	}
}
