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
package gameserver.controllers.instances;

import gameserver.ai.AI;
import gameserver.ai.events.Event;
import gameserver.controllers.NpcController;
import gameserver.controllers.PortalController;
import gameserver.controllers.ReviveController;
import gameserver.dataholders.AcademyShigoData;
import gameserver.dataholders.AcademyTeleportData;
import gameserver.dataholders.DataManager;
import gameserver.dataholders.PortalData;
import gameserver.dataholders.WorldMapsData;
import gameserver.model.Race;
import gameserver.model.academy.AcademyShigoStage;
import gameserver.model.academy.AcademyTeleportStage;
import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.PlayerCommonData;
import gameserver.model.gameobjects.player.Storage;
import gameserver.model.gameobjects.stats.PlayerLifeStats;
import gameserver.model.group.PlayerGroup;
import gameserver.model.templates.WorldMapTemplate;
import gameserver.model.templates.academy.AcademyReSpawnTemplate;
import gameserver.model.templates.academy.AcademyShigoBeginSpawnList;
import gameserver.model.templates.academy.AcademyTeleportTemplate;
import gameserver.model.templates.portal.ExitPoint;
import gameserver.model.templates.portal.PortalTemplate;
import gameserver.model.templates.spawn.SpawnTemplate;
import gameserver.network.aion.serverpackets.SM_ARENA_STATUS;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.services.InstanceService;
import gameserver.services.ItemService;
import gameserver.services.TeleportService;
import gameserver.services.instance.AcademyInstanceService;
import gameserver.spawnengine.SpawnEngine;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.world.World;
import gameserver.world.WorldMapInstance;
import gameserver.world.WorldMapType;
import com.aionemu.commons.utils.Rnd;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AcademyController extends NpcController
{

    public AcademyController()
    {
    }

    public void onDialogRequest(Player player)
    {
        getOwner().getAi().handleEvent(Event.TALK);
        Npc npc = getOwner();
        int i = npc.getObjectId().intValue();
        switch(getOwner().getNpcId())
        {
        case 205331: 
        case 205332: 
        case 205333: 
        case 205334: 
        case 205335: 
        case 205336: 
        case 205337: 
        case 205338: 
        case 205339: 
        case 205340: 
        case 205341: 
        case 205342: 
        case 205343: 
        case 205344: 
        case 730430: 
        case 730431: 
        case 799567: 
        case 799568: 
        case 799569: 
        case 799570: 
        case 799571: 
        case 799572: 
        case 799573: 
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(i, 1011));
            return;
        }
    }

    public void onDialogSelect(int i, Player player, int j)
    {
        Npc npc = getOwner();
        int k = npc.getObjectId().intValue();
        PlayerGroup playergroup = player.getPlayerGroup();
        switch(getOwner().getNpcId())
        {
        case 730430: 
        case 730431: 
            switch(i)
            {
            case 10000: 
                if(player.getPlayerGroup() == null || player.getLevel() < 50)
                {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
                    return;
                }
                WorldMapTemplate worldmaptemplate = DataManager.WORLD_MAPS_DATA.getTemplate(WorldMapType.EMPYREAN_CRUCIBLE.getId());
                int l = DataManager.WORLD_MAPS_DATA.getTemplate(WorldMapType.EMPYREAN_CRUCIBLE.getId()).getMapNameId().intValue();
                if(!InstanceService.canEnterInstance(player, worldmaptemplate.getInstanceMapId(), 0))
                {
                    int i1 = ((Integer)InstanceService.getTimeInfo(player).get(Integer.valueOf(worldmaptemplate.getInstanceMapId()))).intValue() / 60;
                    if(i1 >= 60)
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_CANNOT_ENTER_INSTANCE_COOL_TIME_HOUR(l, i1 / 60));
                    else
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_CANNOT_ENTER_INSTANCE_COOL_TIME_MIN(l, i1));
                    return;
                }
                WorldMapInstance worldmapinstance2 = InstanceService.getRegisteredInstance(WorldMapType.EMPYREAN_CRUCIBLE.getId(), playergroup.getGroupId());
                if(worldmapinstance2 == null)
                {
                    worldmapinstance2 = InstanceService.getNextAvailableInstance(WorldMapType.EMPYREAN_CRUCIBLE.getId());
                    InstanceService.registerGroupWithInstance(worldmapinstance2, playergroup);
                    playergroup.setGroupArenaStage(0);
                    playergroup.setGroupInstancePoints(0);
                }
                PortalTemplate portaltemplate = DataManager.PORTAL_DATA.getPortalTemplate(getOwner().getNpcId());
                ExitPoint exitpoint = null;
                Iterator iterator = portaltemplate.getExitPoint().iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    ExitPoint exitpoint1 = (ExitPoint)iterator.next();
                    if(exitpoint1.getRace() == null || exitpoint1.getRace().equals(playergroup.getGroupLeader().getCommonData().getRace()))
                        exitpoint = exitpoint1;
                } while(true);
                int j1 = Rnd.get(1, 2);
                int k1 = Rnd.get(1, 8);
                if(playergroup.getGroupArenaStage() > 0)
                    teleportPlayerToRaund(player, worldmapinstance2);
                else
                    TeleportService.teleportTo(player, exitpoint.getMapId(), worldmapinstance2.getInstanceId(), exitpoint.getX(), exitpoint.getY(), exitpoint.getZ(), 3000);
                PortalController.setInstanceCooldown(player, WorldMapType.EMPYREAN_CRUCIBLE.getId(), worldmapinstance2.getInstanceId());
                ItemService.addItem(player, 0xb1622fc, 1L);
                player.setInstancePlayerScore(0);
                return;
            }
            return;

        case 205331: 
        case 205332: 
        case 205333: 
        case 205334: 
        case 205335: 
        case 205336: 
        case 205337: 
        case 799567: 
        case 799568: 
        case 799569: 
            switch(i)
            {
            case 10000: 
                if(player.getPlayerGroup() == null || player.getLevel() < 50)
                {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
                    return;
                }
                if(playergroup.getGroupArenaStage() == 0)
                {
                    playergroup.setGroupArenaStage(playergroup.getGroupArenaStage());
                    playergroup.setGroupArenaRound(0);
                }
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
                PacketSendUtility.broadcastPacket(npc, new SM_SYSTEM_MESSAGE(getCurrentDialog(playergroup), true, npc.getObjectId().intValue(), new Object[0]), 30);
                AionObject aionobject = World.getInstance().findAionObject(k);
                if(aionobject instanceof Npc)
                {
                    Npc npc1 = (Npc)aionobject;
                    if(npc1 != null)
                        npc1.getController().onDespawn(true);
                }
                AcademyInstanceService.getInstance().nextSpawn(playergroup);
                return;
            }
            return;

        case 205338: 
        case 205339: 
        case 205340: 
        case 205341: 
        case 205342: 
        case 205343: 
        case 205344: 
        case 799570: 
        case 799571: 
        case 799572: 
            switch(i)
            {
            case 10000: 
                if(player.getPlayerGroup() == null || player.getLevel() < 50)
                {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
                    return;
                }
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
                playergroup.setGroupArenaStage(playergroup.getGroupArenaStage());
                playergroup.setGroupArenaRound(0);
                AcademyTeleportStage academyteleportstage = DataManager.ACADEMY_TELEPORT_DATA.getTeleportLocationForStage(playergroup.getGroupArenaStage());
                if(academyteleportstage == null)
                    return;
                WorldMapInstance worldmapinstance = InstanceService.getRegisteredInstance(WorldMapType.EMPYREAN_CRUCIBLE.getId(), playergroup.getGroupId());
                AcademyTeleportTemplate academyteleporttemplate = academyteleportstage.getTeleportTemplate();
                AionObject aionobject1 = World.getInstance().findAionObject(k);
                if(aionobject1 instanceof Npc)
                {
                    Npc npc2 = (Npc)aionobject1;
                    if(npc2 != null)
                        npc2.getController().onDespawn(true);
                }
                if(playergroup.getGroupArenaStage() > 4)
                    teleportPlayers(playergroup, academyteleporttemplate, worldmapinstance);
                else
                    teleportAcademyRevive(playergroup, academyteleporttemplate, worldmapinstance);
                changeStage(playergroup);
                spawnShigo(playergroup, worldmapinstance);
                return;
            }
            return;

        case 799573: 
            switch(i)
            {
            case 10000: 
                if(player.getPlayerGroup() == null || player.getLevel() < 50)
                {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
                    return;
                }
                if(player.getInventory().getItemCountByItemId(0xb1622fc) < 1L)
                {
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(k, 1097));
                    return;
                }
                AcademyTeleportStage academyteleportstage1 = DataManager.ACADEMY_TELEPORT_DATA.getTeleportLocationForStage(playergroup.getGroupArenaStage());
                if(academyteleportstage1 == null)
                {
                    return;
                } else
                {
                    WorldMapInstance worldmapinstance1 = InstanceService.getRegisteredInstance(WorldMapType.EMPYREAN_CRUCIBLE.getId(), playergroup.getGroupId());
                    AcademyTeleportTemplate academyteleporttemplate1 = academyteleportstage1.getTeleportTemplate();
                    teleportPlayer(player, academyteleporttemplate1, worldmapinstance1);
                    return;
                }
            }
            return;
        }
    }

    private void teleportPlayerToRaund(Player player, WorldMapInstance worldmapinstance)
    {
        PlayerGroup playergroup = player.getPlayerGroup();
        AcademyTeleportStage academyteleportstage = DataManager.ACADEMY_TELEPORT_DATA.getTeleportLocationForStage(playergroup.getGroupArenaStage());
        if(academyteleportstage == null)
        {
            return;
        } else
        {
            int i = Rnd.get(1, 3);
            int j = Rnd.get(1, 4);
            player.setInAcademyRevive(true);
            AcademyReSpawnTemplate academyrespawntemplate = academyteleportstage.getRespawnTemplate();
            TeleportService.teleportTo(player, academyrespawntemplate.getMapid(), worldmapinstance.getInstanceId(), academyrespawntemplate.getX(), academyrespawntemplate.getY(), academyrespawntemplate.getZ(), 0);
            PacketSendUtility.sendPacket(player, new SM_ARENA_STATUS(2, code[playergroup.getGroupArenaStage() - 1], typeArray[playergroup.getGroupArenaStage() - 1]));
            return;
        }
    }

    private void changeStage(PlayerGroup playergroup)
    {
        Player player;
        for(Iterator iterator = playergroup.getMembers().iterator(); iterator.hasNext(); PacketSendUtility.sendPacket(player, new SM_ARENA_STATUS(2, code[playergroup.getGroupArenaStage() - 1], typeArray[playergroup.getGroupArenaStage() - 1])))
            player = (Player)iterator.next();

    }

    private void teleportAcademyRevive(PlayerGroup playergroup, AcademyTeleportTemplate academyteleporttemplate, WorldMapInstance worldmapinstance)
    {
        Iterator iterator = playergroup.getMembers().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Player player = (Player)iterator.next();
            if(player.getWorldId() == WorldMapType.EMPYREAN_CRUCIBLE.getId() && player.getInAcademyRevive())
            {
                if(player.getLifeStats().isAlreadyDead())
                    player.getReviveController().skillRevive(false);
                player.setInAcademyRevive(false);
                int i = Rnd.get(1, 2);
                int j = Rnd.get(1, 8);
                TeleportService.teleportTo(player, academyteleporttemplate.getMapid(), worldmapinstance.getInstanceId(), academyteleporttemplate.getX(), academyteleporttemplate.getY(), academyteleporttemplate.getZ(), 0);
            }
        } while(true);
    }

    private void teleportPlayers(PlayerGroup playergroup, AcademyTeleportTemplate academyteleporttemplate, WorldMapInstance worldmapinstance)
    {
        Iterator iterator = playergroup.getMembers().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Player player = (Player)iterator.next();
            if(player.getWorldId() == WorldMapType.EMPYREAN_CRUCIBLE.getId())
            {
                if(player.getLifeStats().isAlreadyDead())
                    player.getReviveController().skillRevive(false);
                if(player.getInAcademyRevive())
                {
                    Iterator iterator1 = playergroup.getMembers().iterator();
                    do
                    {
                        if(!iterator1.hasNext())
                            break;
                        Player player1 = (Player)iterator1.next();
                        if(!player1.equals(player))
                            PacketSendUtility.sendPacket(player1, SM_SYSTEM_MESSAGE.STR_MSG_FRIENDLY_MOVE_COMBATAREA_IDARENA(player.getName()));
                    } while(true);
                    player.setInAcademyRevive(false);
                }
                int i = Rnd.get(1, 2);
                int j = Rnd.get(1, 8);
                TeleportService.teleportTo(player, academyteleporttemplate.getMapid(), worldmapinstance.getInstanceId(), academyteleporttemplate.getX(), academyteleporttemplate.getY(), academyteleporttemplate.getZ(), 0);
            }
        } while(true);
    }

    private void teleportPlayer(Player player, AcademyTeleportTemplate academyteleporttemplate, WorldMapInstance worldmapinstance)
    {
        if(player.getWorldId() == WorldMapType.EMPYREAN_CRUCIBLE.getId() && player.getInAcademyRevive())
        {
            PlayerGroup playergroup = player.getPlayerGroup();
            Iterator iterator = playergroup.getMembers().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                Player player1 = (Player)iterator.next();
                if(!player1.equals(player))
                    PacketSendUtility.sendPacket(player1, SM_SYSTEM_MESSAGE.STR_MSG_FRIENDLY_MOVE_COMBATAREA_IDARENA(player.getName()));
            } while(true);
            int i = Rnd.get(1, 2);
            int j = Rnd.get(1, 8);
            player.setInAcademyRevive(false);
            if(!player.getInAcademy())
                player.setInAcademy(true);
            player.getLifeStats().setCurrentHpPercent(100);
            player.getLifeStats().setCurrentMpPercent(100);
            player.getInventory().removeFromBagByItemId(0xb1622fc, 1L);
            TeleportService.teleportTo(player, academyteleporttemplate.getMapid(), worldmapinstance.getInstanceId(), academyteleporttemplate.getX(), academyteleporttemplate.getY(), academyteleporttemplate.getZ(), 0);
        }
    }

    private void spawnShigo(PlayerGroup playergroup, WorldMapInstance worldmapinstance)
    {
        AcademyShigoStage academyshigostage = DataManager.ACADEMY_SHIGO_DATA.getSpawnsForStage(playergroup.getGroupArenaStage());
        if(academyshigostage == null)
        {
            return;
        } else
        {
            gameserver.model.templates.academy.AcademyShigoBeginSpawnList academyshigobeginspawnlist = academyshigostage.getShigoBegin();
			ThreadPoolManager.getInstance().schedule(((Runnable) (new AcademyController$1(this, academyshigobeginspawnlist, worldmapinstance))), 1000);
            return;
        }
    }

	class AcademyController$1
        implements Runnable {

        final AcademyShigoBeginSpawnList val$beginList;
        final WorldMapInstance val$instance;
        final AcademyController this$0;

        AcademyController$1(AcademyController academycontroller, AcademyShigoBeginSpawnList academyshigobeginspawnlist, WorldMapInstance worldmapinstance) {
                this$0 = academycontroller;
                val$beginList = academyshigobeginspawnlist;
                val$instance = worldmapinstance;
                
        }

        public void run() {
                gameserver.model.templates.spawn.SpawnTemplate spawntemplate = SpawnEngine.getInstance().addNewSpawn(val$beginList.getMapid(), val$instance.getInstanceId(), val$beginList.getNpcid(), val$beginList.getX(), val$beginList.getY(), val$beginList.getZ(), (byte)0, 0, 0, true);
                SpawnEngine.getInstance().spawnObject(spawntemplate, val$instance.getInstanceId());
        }
    }
	
    private int getCurrentDialog(PlayerGroup playergroup)
    {
        int i = playergroup.getGroupArenaStage();
        switch(i)
        {
        case 1: // '\001'
            return 0x10f59a;

        case 2: // '\002'
            return 0x10f59b;

        case 3: // '\003'
            return 0x10f59c;

        case 4: // '\004'
            return 0x10f59d;

        case 5: // '\005'
            return 0x10f59e;

        case 6: // '\006'
            return 0x10f59f;

        case 7: // '\007'
            return 0x10f5a0;

        case 8: // '\b'
            return 0x10f5a1;

        case 9: // '\t'
            return 0x10f5a2;

        case 10: // '\n'
            return 0x10f5a3;
        }
        return 0;
    }

    private static final int code[] = {
        35464, 36464, 37464, 38464, 8392, 43856, 13784, 49248, 19176, 54640
    };
    private static final int typeArray[] = {
        1, 1, 1, 1, 3, 4, 6, 7, 9, 10
    };

}