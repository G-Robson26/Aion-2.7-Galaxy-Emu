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
package gameserver.controllers;

import java.util.Iterator;
import java.util.List;
import gameserver.ai.AI;
import gameserver.ai.events.Event;
import gameserver.dataholders.DataManager;
import gameserver.dataholders.PortalData;
import gameserver.model.Race;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.PlayerCommonData;
import gameserver.model.gameobjects.player.Storage;
import gameserver.model.group.PlayerGroup;
import gameserver.model.templates.portal.ExitPoint;
import gameserver.model.templates.portal.PortalTemplate;
import gameserver.model.templates.WorldMapTemplate;
import gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.services.InstanceService;
import gameserver.services.TeleportService;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.world.World;
import gameserver.world.WorldMapInstance;
import gameserver.world.WorldMapType;
import com.aionemu.commons.utils.Rnd;

public class InstancesDoorController extends NpcController
{


  public void onDialogRequest(Player player)
  {
    getOwner().getAi().handleEvent(Event.TALK);
    Npc target = getOwner();
    int i = target.getObjectId().intValue();
    switch (getOwner().getNpcId())
    {
    case 730291:
    case 730293:
      PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(i, 1011));
      return;
    }
  }

  public void onDialogSelect(int paramInt1, Player player, int paramInt2)
  {
    Npc target = getOwner();
	Object localObject;
	PlayerGroup playergroup = player.getPlayerGroup();
    int i = target.getObjectId().intValue();
    switch (getOwner().getNpcId())
    {
    case 730291:
    case 730293:
      switch (paramInt1)
      {
      case 10000:
        int j = 0;
        if (player.getCommonData().getRace() == Race.ASMODIANS)
          j = 185000090;
        else
          j = 185000089;
        if (hasItem(player, j))
        {
		    Item item = player.getInventory().getFirstItemByItemId(j);
            if (item == null)
               return;

               player.getInventory().removeFromBag(item, true);
               PacketSendUtility.sendPacket(player, new SM_DELETE_ITEM(item.getObjectId().intValue()));
                if(player.getPlayerGroup() == null || player.getLevel() < 50)
                {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
                    return;
                }
        int r = WorldMapType.IDDRAMATA_01.getId();
        localObject = InstanceService.getRegisteredInstance(r, player.getObjectId().intValue());
        if (localObject != null)
        {
          transfer(player, (WorldMapInstance)localObject);
          return;
        }
        port(player, r);
        return;
        }
        else
        {
          PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(i, 1012));
        }
        return;
      }
      return;
    }
  }

    private boolean hasItem(Player player, int paramInt) {
    return player.getInventory().getItemCountByItemId(paramInt) > 0L;
    }

  
    private void port(Player player, int paramInt)
    {
	PlayerGroup playergroup = player.getPlayerGroup();
    WorldMapInstance localWorldMapInstance = InstanceService.getRegisteredInstance(WorldMapType.IDDRAMATA_01.getId(), playergroup.getGroupId());
	if(localWorldMapInstance == null){
    localWorldMapInstance = InstanceService.getNextAvailableInstance(paramInt);
    InstanceService.registerGroupWithInstance(localWorldMapInstance, playergroup);
    }  
    transfer(player, localWorldMapInstance);
    }

    private void transfer(Player player, WorldMapInstance worldmapinstance)
    {
    TeleportService.teleportTo(player, WorldMapType.IDDRAMATA_01.getId(), worldmapinstance.getInstanceId(), 369.41263F, 528.68994F, 67.28671F, 0);
    }
  
}