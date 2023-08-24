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

package gameserver.itemengine.actions;

import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.Storage;
import gameserver.model.gameobjects.player.TitleList;
import gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.network.aion.serverpackets.SM_TITLE_LIST;
import gameserver.utils.PacketSendUtility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * @author ginho1 ,dimoalex
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TitleAction")
public class TitleAction extends AbstractItemAction {

	@XmlAttribute
	protected int titleid;
	@XmlAttribute
	protected int expire;

	/**
	 * Gets the value of the id property.
	 */
	public int getTitleId() {
		return titleid;
	}

	@Override
	public boolean canAct(Player player, Item parentItem, Item targetItem)
	{
		return player.getTitleList().canAddTitle(titleid);
	}

	@Override
	public void act(Player player, Item parentItem, Item targetItem)
	{
		   Item item = player.getInventory().getItemByObjId(parentItem.getObjectId());
            if (item == null)
               return;

               player.getInventory().removeFromBag(item, true);
               PacketSendUtility.sendPacket(player, new SM_DELETE_ITEM(parentItem.getObjectId()));
				if(player.getTitleList().addTitle(titleid, System.currentTimeMillis(), (expire  * 60L)))
				{
					if(expire > 0)
					    PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300774));
					else
					    PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300773));
				}
	}
}