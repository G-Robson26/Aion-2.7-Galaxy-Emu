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
package gameserver.model.drop;


/**
 * @author ATracer
 */
public class DropTemplate {
    private int mobId;
    private int itemId;
    private int min;
    private int max;
    private float chance;

    /**
     * @param mobId
     * @param itemId
     * @param min
     * @param max
     * @param chance
     */
    public DropTemplate(int mobId, int itemId, int min, int max, float chance) {
        this.mobId = mobId;
        this.itemId = itemId;
        this.min = min;
        this.max = max;
        this.chance = chance;
    }

    /**
     * @return the mobId
     */
    public int getMobId() {
        return mobId;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @return the chance
     */
    public float getChance() {
        return chance;
    }
}
