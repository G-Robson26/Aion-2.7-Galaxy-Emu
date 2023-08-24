/*
 * This file is part of aiongemu <aiongemu.com>.
 *
 *  aiongemu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aiongemu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aiongemu.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.model.templates.item;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author resurrected , Mcrizza
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "trade_items")
public class TradeInTemplate
{
    @XmlElement(name = "item", required = false)
    protected List<TradeInItem> tradeItems;
    
    /**
     * @return the tradeItems
     */
    public List<TradeInItem> getTradeItems() {
        return tradeItems;
    }
}
