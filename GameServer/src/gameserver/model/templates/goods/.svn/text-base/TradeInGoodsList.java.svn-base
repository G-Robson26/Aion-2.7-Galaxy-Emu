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
package gameserver.model.templates.goods;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * @author resurrected , Mcrizza
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeInGoodsList")
public class TradeInGoodsList {

	@XmlAttribute
	protected int id;
	protected List<TradeInGoodsList.Item> item;
	protected List<Integer> itemIdList;
	protected List<TradeInGoodsList.Item> itemsList;

	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		itemIdList = new ArrayList<Integer>();
		itemsList = new ArrayList<TradeInGoodsList.Item>();
		
		if(item == null)
			return;
		
		for(Item it : item)
		{
			itemIdList.add(it.getId());
			itemsList.add(it);
		}
		item = null;
	}	

	/**
	 * Gets the value of the id property.
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the itemIdList
	 */
	public List<Integer> getItemIdList()
	{
		return itemIdList;
	}

	public List<TradeInGoodsList.Item> getItemsList()
	{
		return itemsList;
	}
	
	/**
	 * <p>Java class for anonymous complex type.
	 * 
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Item {

		@XmlAttribute
		protected int id;

		public int getId() {
			return id;
		}
	}

}
