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
package gameserver.model;

/**
 * @author ATracer
 */
public enum TaskId {
	DECAY,
	RESPAWN,
	PRISON,
	PROTECTION_ACTIVE,
	DROWN,
	DESPAWN,
	QUEST_TIMER,
	RESTORE,
	PLAYER_UPDATE,
	INVENTORY_UPDATE,
	GAG,
	ITEM_USE,
	CREATURE_COMBAT,
	SKILL_RESURRECT
}