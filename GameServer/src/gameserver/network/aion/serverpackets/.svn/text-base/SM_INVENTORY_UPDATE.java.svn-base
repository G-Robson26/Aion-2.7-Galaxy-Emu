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
 */

package gameserver.network.aion.serverpackets;

import gameserver.model.gameobjects.Item;
import gameserver.model.items.ItemId;
import gameserver.model.templates.item.ItemTemplate;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.InventoryPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ATracer
 */
public class SM_INVENTORY_UPDATE extends InventoryPacket {
    private List<Item> items;
    private int size;
	private int mode;

    public SM_INVENTORY_UPDATE(List<Item> items) {
        this.items = items;
        this.size = items.size();
		this.mode = 25;
    }

    public SM_INVENTORY_UPDATE(Item item, boolean isNew) {
        this.items = new ArrayList<Item>();
        this.items.add(item);
        this.size = 1;
		this.mode = isNew ? 25 : 17;
    }

    /**
     * {@inheritDoc} dc
     */

    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        writeH(buf, mode); 
        writeH(buf, size); // number of entries
        for (Item item : items) {
            writeGeneralInfo(buf, item);

            ItemTemplate itemTemplate = item.getItemTemplate();

            if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
                writeKinah(buf, item, true);
            } else if (itemTemplate.isWeapon()) {
                writeWeaponInfo(buf, item, true);
            } else if (itemTemplate.isArmor()) {
                writeArmorInfo(buf, item, true, false, false);
            } else if (itemTemplate.isStigma()) {
                writeStigmaInfo(buf, item);
            } else {
                writeGeneralItemInfo(buf, item, false, false);
                writeC(buf, 0);
            }
        }
    }
}