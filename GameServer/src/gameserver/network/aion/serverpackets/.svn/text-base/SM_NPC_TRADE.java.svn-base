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
package gameserver.network.aion.serverpackets;

import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Npc;
import gameserver.model.templates.TradeListTemplate;
import gameserver.model.templates.TradeListTemplate.TradeTab;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;
import gameserver.services.TradeService;
import gameserver.world.World;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

/**
 * @author resurrected , Mcrizza
 * 
 */
public class SM_NPC_TRADE extends AionServerPacket
{
    private static Logger log = Logger.getLogger(SM_NPC_TRADE.class);
    
    private int targetObjectId;
    
    public SM_NPC_TRADE(int targetObjectId)
    {
        this.targetObjectId = targetObjectId;
    }
	
	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
	    AionObject object = World.getInstance().findAionObject(targetObjectId);
	    if(object != null && object instanceof Npc){
    	    Npc npc = (Npc) object;
    	    TradeListTemplate tradeList = TradeService.getTradeListData().getTradeListTemplate(npc.getNpcId());
    	    
    	    if (tradeList == null){
    	        log.warn("Missing Trade In list for: " + npc.getName() + " object id: " + npc.getObjectId());
    	        return;
    	    }
    	    
            // New type of npc goods trade list used in crucible exchange shop for platinum medal chest
            writeD(buf, targetObjectId); // Target Object Id
            writeC(buf, 0x01); // 2.7 Unknown, Maybe Number of items?
            writeD(buf, 0x64); // 2.7 Unknown
            writeH(buf, tradeList.getTradeTablist().size()); // 2.7 # of Trade Tabs
            for(TradeTab tab : tradeList.getTradeTablist()){
                writeD(buf, tab.getId()); // 2.7 NPC Trade Goodslist Tab Id
            }
	    }else{
	        // Maybe do some Logging, NPC is not found, NPC is missing trade in goods list etc
	        return;
	    }
	}
}
