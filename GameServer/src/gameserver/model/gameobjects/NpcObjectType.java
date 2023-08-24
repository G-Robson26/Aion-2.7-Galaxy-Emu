/**
 * This file is part of Aion Galaxy EMU <aiongemu.com>.
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.model.gameobjects;

/**
 * @author ATracer
 */
public enum NpcObjectType {
    NORMAL(1),
    SUMMON(2),
	HOMING(16),
    TRAP(32),
	SKILLAREANPC(64),
	TOTEM(128),
    GROUPGATE(256),
    SERVANT(1024),
    PET(2048);

    private NpcObjectType(int id) {
        this.id = id;
    }

    private int id;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
