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

package gameserver.questEngine.handlers.models.xmlQuest.conditions;

import gameserver.model.gameobjects.player.Player;
import gameserver.questEngine.model.QuestCookie;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Mr. Poke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PcInventoryCondition")
public class PcInventoryCondition extends QuestCondition {

    @XmlAttribute(name = "item_id", required = true)
    protected int itemId;
    @XmlAttribute(required = true)
    protected long count;

    /**
     * Gets the value of the itemId property.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Gets the value of the count property.
     */
    public long getCount() {
        return count;
    }

    /* (non-Javadoc)
      * @see com.aionemu.gameserver.questEngine.handlers.template.xmlQuest.condition.QuestCondition#doCheck(com.aionemu.gameserver.questEngine.model.QuestEnv)
      */

    @Override
    public boolean doCheck(QuestCookie env) {
        Player player = env.getPlayer();
        long itemCount = player.getInventory().getItemCountByItemId(itemId);
        switch (getOp()) {
            case EQUAL:
                return itemCount == count;
            case GREATER:
                return itemCount > count;
            case GREATER_EQUAL:
                return itemCount >= count;
            case LESSER:
                return itemCount < count;
            case LESSER_EQUAL:
                return itemCount <= count;
            case NOT_EQUAL:
                return itemCount != count;
            default:
                return false;
        }
    }
}
