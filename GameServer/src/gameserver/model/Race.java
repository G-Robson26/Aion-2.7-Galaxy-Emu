/**
 * This file is part of Aion X Emu <aionxemu.com>
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

package gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Basic enum with races.<br>
 * I believe that NPCs will have their own rances, so it's quite comfortable to have it in the same place
 *
 * @author SoulKeeper
 */

@XmlEnum
public enum Race {
    /**
     * Playable race
     */
    ELYOS(0),

    /**
     * Playable races
     */
    ASMODIANS(1),

    /**
     * Npc races
     */
    LYCAN(2),
    CONSTRUCT(3),
    CARRIER(4),
    DRAKAN(5),
    LIZARDMAN(6),
    TELEPORTER(7),
    NAGA(8),
    BROWNIE(9),
    KRALL(10),
    SHULACK(11),
    BARRIER(12),
    PC_LIGHT_CASTLE_DOOR(13),
    PC_DARK_CASTLE_DOOR(14),
    DRAGON_CASTLE_DOOR(15),
    GCHIEF_LIGHT(16),
    GCHIEF_DARK(17),
    DRAGON(18),
    OUTSIDER(19),
    RATMAN(20),
    DEMIHUMANOID(21),
    UNDEAD(22),
    BEAST(23),
    MAGICALMONSTER(24),
    ELEMENTAL(25),

    /**
     * Special races
     */
    NONE(26),
    PC_ALL(27);

    /**
     * id of race
     */
    private int raceId;

    /**
     * Constructor.
     *
     * @param raceId id of the message
     */
    private Race(int raceId) {
        this.raceId = raceId;
    }

    /**
     * Get id of this race.
     *
     * @return race id
     */
    public int getRaceId() {
		return raceId;
	}
}
