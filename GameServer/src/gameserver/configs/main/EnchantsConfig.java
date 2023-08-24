/*
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

public class EnchantsConfig {
    /**
     * ManaStone Rates
     */
    @Property(key = "gameserver.manastone.percent1", defaultValue = "98")
    public static int MSPERCENT1;
    @Property(key = "gameserver.manastone.percent2", defaultValue = "85")
    public static int MSPERCENT2;
    @Property(key = "gameserver.manastone.percent3", defaultValue = "75")
    public static int MSPERCENT3;
    @Property(key = "gameserver.manastone.percent4", defaultValue = "65")
    public static int MSPERCENT4;
    @Property(key = "gameserver.manastone.percent5", defaultValue = "55")
    public static int MSPERCENT5;
    @Property(key = "gameserver.manastone.percent6", defaultValue = "45")
    public static int MSPERCENT6;
	 @Property(key = "gameserver.manastone.percent7", defaultValue = "35")
	 public static int MSPERCENT7;

    /**
     * Supplement Additional Success Rates
     */
    @Property(key = "gameserver.supplement.lesser", defaultValue = "10")
    public static int LSSUP;
    @Property(key = "gameserver.supplement.regular", defaultValue = "15")
    public static int RGSUP;
    @Property(key = "gameserver.supplement.greater", defaultValue = "20")
    public static int GRSUP;

	/**
	 * Felicitous Socketing Rates 100%
	 */
	@Property(key = "gameserver.felicitous.socketing.fabled", defaultValue = "100")
	public static int		FELSOCFAB;

	@Property(key = "gameserver.felicitous.socketing.eternal", defaultValue = "100")
	public static int		FELSOCETE;
	
	@Property(key = "gameserver.blessed.socketing.hero", defaultValue = "100")
	public static int		BLESOCHERO;

	@Property(key = "gameserver.blessed.socketing.eternal", defaultValue = "100")
	public static int		BLESOCETE;
}
