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
package gameserver.dataholders;

import gameserver.model.templates.goods.TradeInGoodsList;
import gnu.trove.TIntObjectHashMap;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author resurrected , Mcrizza
 *
 */
@XmlRootElement(name = "tradeingoodslists")
@XmlAccessorType(XmlAccessType.FIELD)
public class TradeInGoodsListData
{

	@XmlElement(required = true)
	protected List<TradeInGoodsList> list;

	/** A map containing all tradeingoodslist templates */
	private TIntObjectHashMap<TradeInGoodsList> tradeInGoodsListData;

	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		tradeInGoodsListData = new TIntObjectHashMap<TradeInGoodsList>();
		for(TradeInGoodsList it : list)
		{
			tradeInGoodsListData.put(it.getId(), it);
		}
		list = null;
	}

	public TradeInGoodsList getTradeInGoodsListById(int id)
	{
		return tradeInGoodsListData.get(id);
	}
	
	/**
	 * @return goodListData.size()
	 */
	public int size()
	{
		return tradeInGoodsListData.size();
	}
}
