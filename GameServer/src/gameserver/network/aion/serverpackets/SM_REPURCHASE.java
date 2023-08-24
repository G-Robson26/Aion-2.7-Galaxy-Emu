/**
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is private software: you can redistribute it and/or modify
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

package gameserver.network.aion.serverpackets;

import gameserver.configs.main.GSConfig;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
import gameserver.model.gameobjects.stats.modifiers.StatModifier;
import gameserver.model.items.FusionStone;
import gameserver.model.items.ItemSlot;
import gameserver.model.items.ItemStone;
import gameserver.model.items.ManaStone;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Set;;

/**
 * @author ginho1
 */
public class SM_REPURCHASE extends AionServerPacket {
    private int targetObjectId;
	private LinkedHashMap<Integer, Item> items;
	private Player player;
    
    public SM_REPURCHASE(Npc npc, Player player)
	{
		this.targetObjectId = npc.getObjectId();
		this.player = player;
	}

    @Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		writeD(buf, targetObjectId);
		writeD(buf, 0x01);
		if(GSConfig.ENABLE_REPURCHASE)
		{
			this.items = player.getRepurchase().getRepurchaseItems();
			writeH(buf, items.size());
			for(int itemObjId : items.keySet())
			{
				Item item = items.get(itemObjId);

				if(item != null)
				{
					writeGeneralInfo(buf, item);

					if(item.getItemTemplate().isArmor())
						writeArmorInfo(buf, item);
					else if(item.getItemTemplate().isWeapon())
						writeWeaponInfo(buf, item);
					else
						writeCommonInfo(buf, item);
					long price = player.getPrices().getKinahForSell((item.getItemTemplate().getPrice() * item.getItemCount()), player.getCommonData().getRace());
					writeD(buf, (int) price);
					writeD(buf, 0);
				}
			}
		}
		else
		{
			writeD(buf, 0);
		}
	}
    
    protected void writeGeneralInfo(ByteBuffer buf, Item item)
	{
		writeD(buf, item.getObjectId());
		writeD(buf, item.getItemTemplate().getTemplateId());
		writeH(buf, 0x24);
		writeD(buf, item.getItemTemplate().getNameId());
		writeH(buf, 0);
	}
    
    private void writeCommonInfo(ByteBuffer buf, Item item)
	{
		short byteCount = 26;
		/*byteCount += item.getCrafterName().length() * 2;*/
		writeH(buf, byteCount);
		writeC(buf, 0);
		writeH(buf, item.getItemMask());
		writeQ(buf, item.getItemCount());
		writeS(buf, "" /*item.getCrafterName()*/);
		writeC(buf, 0);
		writeQ(buf, 0 /*item.getTempItemTimeLeft()*/);
		writeD(buf, 0 /*item.getTempTradeTimeLeft()*/);
	}

	private void writeArmorInfo(ByteBuffer buf, Item item)
	{
		int itemSlotId = item.getEquipmentSlot();
		short byteCount = 83;
		/*byteCount += item.getCrafterName().length() * 2;*/
		writeH(buf, byteCount);
		writeC(buf, 0x06);
		writeD(buf, item.isEquipped() ? itemSlotId : 0);
		writeC(buf, 0x02);
		writeD(buf, ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot()).get(0).getSlotIdMask());
		writeD(buf, 0);
		writeD(buf, 0);
		writeC(buf, 0x0B);
		writeC(buf, item.isSoulBound() ? 1 : 0);
		writeC(buf, item.getEnchantLevel());
		writeD(buf, item.getItemSkinTemplate().getTemplateId());
		writeC(buf, item.hasOptionalSocket() ? item.getOptionalSocket() : 0x00);
		writeItemStones(buf, item);
		writeC(buf, 0);
		writeD(buf, item.getItemColor());
		writeD(buf, 0);
		writeD(buf, 0);
		writeC(buf, 0);
		writeH(buf, item.getItemMask());
		writeQ(buf, item.getItemCount());
		writeS(buf, "" /*item.getCrafterName()*/);
		writeC(buf, 0);
		writeQ(buf, 0 /*item.getTempItemTimeLeft()*/);
		writeD(buf, 0 /*item.getTempTradeTimeLeft()*/);
	}

	protected void writeWeaponInfo(ByteBuffer buf, Item item)
	{
		int itemSlotId = item.getEquipmentSlot();
		int placeHolder, sizeLoc, size;
		writeH(buf, 0x05);
		sizeLoc = buf.position();
		if(item.getItemTemplate().getWeaponType().getRequiredSlots() == 2)
		{
			writeC(buf, 0x0E);
			writeD(buf, item.hasFusionedItem() ? item.getFusionedItem() : 0);
			writeFusionStones(buf, item);
			writeC(buf, item.hasOptionalFusionSocket() ? item.getOptionalFusionSocket() : 0x00);
		}
		writeC(buf, 0x06);
		writeD(buf, item.isEquipped() ? itemSlotId : 0x00);
		writeC(buf, 0x01);
		writeD(buf, ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot()).get(0).getSlotIdMask());
		writeD(buf, item.hasFusionedItem() ? 0x00 : 0x02);
		writeC(buf, 0x0B);
		writeC(buf, item.isSoulBound() ? 1 : 0);
		writeC(buf, item.getEnchantLevel());
		writeD(buf, item.getItemSkinTemplate().getTemplateId());
		writeC(buf, item.hasOptionalSocket() ? item.getOptionalSocket() : 0x00);
		writeItemStones(buf, item);
		ItemStone god = item.getGodStone();
		writeD(buf, god == null ? 0 : god.getItemId());
		writeD(buf, 0);
		writeD(buf, 0);
		writeH(buf, 0x00);
		writeH(buf, item.getItemMask());
		writeQ(buf, item.getItemCount());
		writeS(buf, "" /*item.getCrafterName()*/);
		writeC(buf, 0);
		writeQ(buf, 0 /*item.getTempItemTimeLeft()*/);
		writeD(buf, 0 /*item.getTempTradeTimeLeft()*/);
		size = buf.position() - sizeLoc;
		placeHolder = buf.position();
		buf.position(sizeLoc - 2);
		writeH(buf, size);
		buf.position(placeHolder);
	}

	private void writeItemStones(ByteBuffer buf, Item item)
	{
		int count = 0;
		if(item.hasManaStones())
		{
			Set<ManaStone> itemStones = item.getItemStones();
			for(ManaStone itemStone : itemStones)
			{
				if(count == 6)
					break;
				StatModifier modifier = itemStone.getFirstModifier();
				if(modifier != null)
				{
					count++;
					writeC(buf, modifier.getStat().getItemStoneMask());
				}
			}
			writeB(buf, new byte[(6 - count)]);
			count = 0;
			for(ManaStone itemStone : itemStones)
			{
				if(count == 6)
					break;
				StatModifier modifier = itemStone.getFirstModifier();
				if(modifier != null)
				{
					count++;
					writeH(buf, ((SimpleModifier) modifier).getValue());
				}
			}
			writeB(buf, new byte[(6 - count) * 2]);
		}
		else
		{
			writeB(buf, new byte[18]);
		}
	}

	private void writeFusionStones(ByteBuffer buf, Item item)
	{
		int count = 0;
		if(item.hasFusionStones())
		{
			Set<FusionStone> itemStones = item.getFusionStones();
			for(FusionStone itemStone : itemStones)
			{
				if(count == 6)
					break;
				StatModifier modifier = itemStone.getFirstModifier();
				if(modifier != null)
				{
					count++;
					writeC(buf, modifier.getStat().getItemStoneMask());
					writeH(buf, ((SimpleModifier) modifier).getValue());
				}
			}
			writeB(buf, new byte[(6 - count) * 3]);
		}
		else
		{
			writeB(buf, new byte[18]);
		}
	}
}
