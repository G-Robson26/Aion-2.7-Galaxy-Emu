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
package gameserver.network.aion.clientpackets;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import gameserver.dataholders.DataManager;
import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.TradeListTemplate;
import gameserver.model.templates.TradeListTemplate.TradeTab;
import gameserver.model.templates.goods.TradeInGoodsList;
import gameserver.model.templates.item.ItemTemplate;
import gameserver.model.templates.item.TradeInItem;
import gameserver.network.aion.AionClientPacket;
import gameserver.services.ItemService;
import gameserver.services.TradeService;
import gameserver.utils.PacketSendUtility;
import gameserver.world.World;

/**
 * @author resurrected , Mcrizza 
 *
 */
public class CM_NPC_TRADE extends AionClientPacket
{
    private static Logger log = Logger.getLogger(CM_NPC_TRADE.class);
    
	private int targetObjectId;
	private int rewardTemplateId;
	private int rewardQuantity;
	private int tradeQuantity;
	private List<Integer> tradeList;

	public CM_NPC_TRADE(int opcode)
	{
		super(opcode);
	}


	@Override
	protected void readImpl()
	{
	    this.targetObjectId = readD();
	    this.rewardTemplateId = readD();
	    this.rewardQuantity = readD();
	    this.tradeQuantity = readH();
	    this.tradeList = new ArrayList<Integer>();
	    for(int i = 0; i < this.tradeQuantity; i++){
	        this.tradeList.add(readD());
	    }
	}

	@Override
	protected void runImpl()
	{
	    Player player = getConnection().getActivePlayer();
	    if (player == null) return;
	    
       AionObject object = World.getInstance().findAionObject(targetObjectId);
        if(object != null && object instanceof Npc){
            Npc npc = (Npc) object;
            TradeListTemplate tradeListData = TradeService.getTradeListData().getTradeListTemplate(npc.getNpcId());
            TradeInGoodsList matchedTradeInGoodsList = null;
            
            // hack check, make sure reward item is contained on trade list assigned to npc, get trade list containing our item
            boolean matched = false;
            for(TradeTab tab : tradeListData.getTradeTablist()){
                TradeInGoodsList tradeGoodsData = DataManager.TRADEINGOODSLIST_DATA.getTradeInGoodsListById(tab.getId());
                for(int itemId : tradeGoodsData.getItemIdList()){
                    if(itemId == rewardTemplateId) { 
                        matched = true;
                        matchedTradeInGoodsList = tradeGoodsData;
                        break; }
                }
            } if(!matched || matchedTradeInGoodsList == null) return;
            
            ItemTemplate rewardItemTemplate = DataManager.ITEM_DATA.getItemTemplate(rewardTemplateId);
            log.warn("Reward Item Template: " + rewardItemTemplate.getTemplateId());
            
            List<TradeInItem> tradeInItems = rewardItemTemplate.getTradeInTemplate().getTradeItems();
            
            if(tradeInItems == null){
                log.warn("Trade items missing from item template: " + rewardItemTemplate);
                return;
            }
            
    	    // hack check, make sure intended target is selected
    	    if(player.getTarget().getObjectId() == targetObjectId){
    	        
    	       // hack check, make sure player has the required items and corresponding item quantities
    	       for  (TradeInItem tradeItem : tradeInItems){
                   if(player.getInventory().getItemCountByItemId(tradeItem.getitemTemplateId()) < tradeItem.getitemCount()){
                       //TODO: Retail Message & Potential Logging
                       PacketSendUtility.sendMessage(player, "You do not have the required Trade Items!");
                       return;
                   }
    	       }

    	       // Player has the required items, attempt to remove the items.
                for  (TradeInItem tradeItem : tradeInItems){
                    log.warn("Removing item id: " + tradeItem.getitemTemplateId() + " count: " + tradeItem.getitemCount());
                    if(player.getInventory().removeFromBagByItemId(tradeItem.getitemTemplateId(), tradeItem.getitemCount()) == false){
                        //TODO: Retail Message & Potential Logging
                        PacketSendUtility.sendMessage(player, "Could not remove the required Trade Items!");
                        return;
                    }
                }

    	        // TODO: Verify Reward Quantity & Add Check
                ItemService.addItem(player, rewardTemplateId, rewardQuantity);
                // TODO: Retail Message Item Added Successfully
                PacketSendUtility.sendMessage(player, "You received " + rewardItemTemplate.getName() + "!");
    	    }
	    }
	}
}
