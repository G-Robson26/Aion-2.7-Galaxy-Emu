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
package gameserver.ai.desires;

import gameserver.ai.AI;

/**
 * This interface represents basic desire functions.<br>
 * Each desire should implement {@link #handleDesire(gameserver.ai.AI)} method with default behaviour.<br>
 * AI can override {@link gameserver.ai.AI#handleDesire(Desire)} to implement custom behaviour of desire.<br>
 *
 * @author SoulKeeper
 * @modified ATracer
 * @see gameserver.ai.AI
 * @see gameserver.ai.AI#handleDesire(Desire)
 * @see gameserver.ai.desires.AbstractDesire
 */
public interface Desire extends Comparable<Desire> {

    /**
     * Invokes default desire action. AI can override invocation of this method to handle desire in it's own way
     *
     * @param ai actor that is doing this desire
     */
    boolean handleDesire(AI<?> ai);

    /**
     * Returns hashcode for this object, must be overrided by child
     *
     * @return hashcode for this object
     */
    int hashCode();

    /**
     * Compares this Desire with another object, must overriden by child
     *
     * @param obj another object to compare with
     * @return result of object comparation
     */
    boolean equals(Object obj);

    /**
     * Returns desire power of this object
     *
     * @return desire power of the object
     */
    int getDesirePower();

    /**
     * Adds desire power to this desire, this call is synchronized.<br>
     * <br>
     * <b>WARNING!!! Changing desire power after adding it to queue will not affect it's position, you have to call
     * {@link gameserver.ai.desires.DesireQueue#addDesire(Desire)} passing this instance as argument</b>
     *
     * @param desirePower amount of desirePower to add
     * @see DesireQueue#addDesire(Desire)
     */
    void increaseDesirePower(int desirePower);

    /**
     * Reduces desire power by give amount.<br>
     * <br>
     * <b>WARNING!!! Changing desire power after adding it to queue will not affect it's position, you have to call
     * {@link gameserver.ai.desires.DesireQueue#addDesire(Desire)} passing this instance as argument</b>
     *
     * @param desirePower amount of desirePower to substract
     * @see DesireQueue#addDesire(Desire)
     */
    void reduceDesirePower(int desirePower);

    /**
     * Used in desire filters
     */
    boolean isReadyToRun();
	
	/**
	 * Will be called by ai when clearing desire queue.
	 */
	void onClear();
}
