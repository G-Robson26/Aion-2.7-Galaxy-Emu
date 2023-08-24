/**
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is pryvate software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alpha team is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alpha team.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author ATracer
 */
public class PeriodicSaveConfig {
    @Property(key = "gameserver.periodicsave.player.general", defaultValue = "900")
    public static int PLAYER_GENERAL;

    @Property(key = "gameserver.periodicsave.player.items", defaultValue = "900")
    public static int PLAYER_ITEMS;

    @Property(key = "gameserver.periodicsave.legion.items", defaultValue = "1200")
    public static int LEGION_ITEMS;

    @Property(key = "gameserver.periodicsave.broker", defaultValue = "1500")
    public static int BROKER;
}
