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
package quest.beluslan;

import java.util.Collections;

import gameserver.controllers.PortalController;
import gameserver.dataholders.DataManager;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.VisibleObject;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.WorldMapTemplate;
import gameserver.model.templates.quest.QuestItems;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.questEngine.HandlerResult;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.InstanceService;
import gameserver.services.ItemService;
import gameserver.services.TeleportService;
import gameserver.skillengine.SkillEngine;
import gameserver.skillengine.model.Skill;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.world.WorldMapInstance;
import gameserver.world.zone.ZoneName;

/**
 * @author Thyrador
 */

public class _28602IntotheUnknown extends QuestHandler
{
    private final static int questId = 28602;

    public _28602IntotheUnknown()
    {
        super(questId);
    }

    @Override
    public void register()
    {
        qe.setQuestMovieEndIds(453).add(questId);
        qe.addOnEnterWorld(questId);
        qe.setQuestEnterZone(ZoneName.KALIGA_DUNGEONS_300230000).add(questId);

        qe.setNpcQuestData(730308).addOnActionItemEvent(questId);
        qe.setNpcQuestData(700939).addOnActionItemEvent(questId);
        qe.setNpcQuestData(730340).addOnActionItemEvent(questId);
        qe.setNpcQuestData(730341).addOnActionItemEvent(questId);
        qe.setNpcQuestData(730325).addOnActionItemEvent(questId);
        qe.setNpcQuestData(700924).addOnActionItemEvent(questId);

        qe.setNpcQuestData(205234).addOnQuestStart(questId);
        qe.setNpcQuestData(205234).addOnTalkEvent(questId);
        qe.setNpcQuestData(730308).addOnTalkEvent(questId);
        qe.setNpcQuestData(700939).addOnTalkEvent(questId);
        qe.setNpcQuestData(217006).addOnKillEvent(questId);
        qe.setNpcQuestData(216968).addOnKillEvent(questId);

        qe.setNpcQuestData(730340).addOnTalkEvent(questId);
        qe.setNpcQuestData(730341).addOnTalkEvent(questId);
        qe.setNpcQuestData(730325).addOnTalkEvent(questId);

        qe.setQuestItemIds(164000140).add(questId);
        qe.setQuestItemIds(164000142).add(questId);
        qe.setQuestItemIds(164000143).add(questId);
    }

    @Override
    public boolean onKillEvent(QuestCookie env)
    {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if(qs == null || qs.getStatus() != QuestStatus.START)
            return false;

        int targetId = 0;
        if(env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        switch(targetId)
        {
            case 217006:
                if (qs.getQuestVarById(0) == 3)
                {
                    PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 455));
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(env);    
                }    

            case 216968:
                if (qs.getQuestVarById(0) >= 1)
                {
                    ItemService.addItems(player, Collections.singletonList(new QuestItems(185000109, 0)));
                }
        }
        return false;
    }

    @Override
    public boolean onDialogEvent(QuestCookie env)
    {
        Player player = env.getPlayer();
        int targetId = 0;
        if(env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if(targetId == 205234)
        {
            if(qs == null || qs.getStatus() == QuestStatus.NONE)
            {
                if(env.getDialogId() == 26)
                    return sendQuestDialog(env, 4762);
                else
                    return defaultQuestStartDialog(env);
            }
            else if(qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
            {
                if(env.getDialogId() == 26)
                    return sendQuestDialog(env, 1011);
                else if(env.getDialogId() == 10000)
                {
                    if(player.getPlayerGroup() == null)
                    {
                        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300230000);
                        InstanceService.registerPlayerWithInstance(newInstance, player);
                        TeleportService.teleportTo(player, 300230000, newInstance.getInstanceId(), 247.85089f, 244.04916f, 189.28543f, (byte) 113);
                        Skill skill = SkillEngine.getInstance().getSkill(player,19270,1,player);
                        skill.useSkill();
                        return true;
                    }
                    else
                        return sendQuestDialog(env, 1012);
                }
                else
                    return defaultQuestStartDialog(env);
            }
            else if(qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) > 0)
            {
                if(env.getDialogId() == -1)
                    return sendQuestDialog(env, 1011);
                else if(env.getDialogId() == 10000)
                {
                    if(player.getPlayerGroup() == null)
                    {
                        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300230000);
                        InstanceService.registerPlayerWithInstance(newInstance, player);
                        TeleportService.teleportTo(player, 300230000, newInstance.getInstanceId(), 247.85089f, 244.04916f, 189.28543f, (byte) 113);
                        Skill skill = SkillEngine.getInstance().getSkill(player,19270,1,player);
                        skill.useSkill();
                        return true;
                    }
                    else
                        return sendQuestDialog(env, 1012);
                }
                else
                    return defaultQuestStartDialog(env);
            }
            
            else if(qs != null && qs.getStatus() == QuestStatus.REWARD)
            {
                if(env.getDialogId() == -1)
                    return sendQuestDialog(env, 10002);
                else 
                    return defaultQuestEndDialog(env);
            }
            else if(qs != null && qs.getStatus() == QuestStatus.COMPLETE)
            {
                if(env.getDialogId() == -1)
                    return sendQuestDialog(env, 1011);
                else if(env.getDialogId() == 10000)
                {
                    if(player.getPlayerGroup() == null)
                    {
                        WorldMapTemplate world = DataManager.WORLD_MAPS_DATA.getTemplate(300230000);
                        int mapname = DataManager.WORLD_MAPS_DATA.getTemplate(300230000).getMapId();
                        if (!InstanceService.onRegisterRequest(player, world.getInstanceId(), world.getCooldown())) {
                            int timeinMinutes = InstanceService.getTimeInfo(player).get(world.getInstanceId()) / 60;
                        if (timeinMinutes >= 60)
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_CANNOT_ENTER_INSTANCE_COOL_TIME_HOUR(401255, timeinMinutes / 60));
                        else
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_CANNOT_ENTER_INSTANCE_COOL_TIME_MIN(401255, timeinMinutes));
                    return true;
                    }
                        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300230000);
                        InstanceService.registerPlayerWithInstance(newInstance, player);
                        TeleportService.teleportTo(player, 300230000, newInstance.getInstanceId(), 247.85089f, 244.04916f, 189.28543f, (byte) 113);
                        Skill skill = SkillEngine.getInstance().getSkill(player,19270,1,player);
                        skill.useSkill();
                        return true;
                    }
                    else
                        return sendQuestDialog(env, 1012);
                }
                else
                    return defaultQuestStartDialog(env);
            }
        }
        else if(targetId == 730308)
        {
            if(qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
            {
                if(env.getDialogId() == 26)
                    return sendQuestDialog(env, 1352);
                else if(env.getDialogId() == 10001)
                {
                    if(player.getInventory().getItemCountByItemId(185000109) > 0)
                    {
                        player.getInventory().removeFromBagByItemId(185000109, 1);
                        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                        TeleportService.teleportTo(player, 300230000, 688.6184f, 677.41315f, 200.28648f, (byte) 89);
                        return true;
                    }
                    else
                        return sendQuestDialog(env, 10001);
                }
                else
                    return defaultQuestStartDialog(env);
            }
            else if(qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) > 1)
            {
                if(env.getDialogId() == -1)
                    return sendQuestDialog(env, 1352);
                else if(env.getDialogId() == 10001)
                {
                    if(player.getInventory().getItemCountByItemId(185000109) > 0)
                    {
                        player.getInventory().removeFromBagByItemId(185000109, 1);
                        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                        TeleportService.teleportTo(player, 300230000, 688.6184f, 677.41315f, 200.28648f, (byte) 89);
                        return true;
                    }
                    else
                        return sendQuestDialog(env, 10001);
                }
                else
                    return defaultQuestStartDialog(env);
            }
            else if(qs != null && qs.getStatus() == QuestStatus.COMPLETE)
            {
                if(env.getDialogId() == -1)
                    return sendQuestDialog(env, 1352);
                else if(env.getDialogId() == 10001)
                {
                    TeleportService.teleportTo(player, 300230000, 688.6184f, 677.41315f, 200.28648f, (byte) 89);
                    return true;
                }
                else
                    return defaultQuestStartDialog(env);
            }
        }
        else if(targetId == 700939)
        {
            if(qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
            {
                if(env.getDialogId() == -1)
                    return sendQuestDialog(env, 1693);
                else if(env.getDialogId() == 10002)
                {
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(env);
                    Skill skill = SkillEngine.getInstance().getSkill(player,19288,1,player);
                    skill.useSkill();                    
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                }
                else
                    return defaultQuestStartDialog(env);
            }
        }
        else if (targetId == 730340 || targetId == 730325 || targetId == 730341)
        {
            env.setQuestId(0);
            if(env.getDialogId() == -1)
            {
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1011));
                return true;
            }
            else if(env.getDialogId() == 1012)
            {
                int itemId = targetId == 730340 ? 164000140 : (targetId == 730341 ? 164000143 : 164000142);
                if(player.getInventory().getItemCountByItemId(itemId) > 0)
                {
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 27));
                    return true;
                }
                else
                {
                    defaultQuestGiveItem(env, itemId, 1);
                }
            }
        }
        
        return false;
    }

    @Override
    public boolean onEnterWorldEvent(QuestCookie env)
    {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if(qs != null && qs.getStatus() == QuestStatus.START)
        {
            if(player.getWorldId() == 300230000 && qs.getQuestVarById(0) == 0)
            {
                ThreadPoolManager.getInstance().schedule(new Runnable(){
                    @Override
                    public void run()
                    {
                        PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 453));
                    }
                }, 3000);
            }
        }
        return false;
    }

    @Override
    public boolean onMovieEndEvent(QuestCookie env, int movieId)
    {
        if(movieId != 453)
            return false;
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if(qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 0)
            return false;
        qs.setQuestVar(1);
        updateQuestStatus(env);
        Skill skill = SkillEngine.getInstance().getSkill(player,19270,1,player);
        skill.useSkill();
        return true;
    }

    @Override
    public boolean onEnterZoneEvent(QuestCookie env, ZoneName zoneName)
    {
        if(zoneName != ZoneName.KALIGA_DUNGEONS_300230000)
            return false;
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if(qs == null || qs.getQuestVars().getQuestVars() != 2)
            return false;
        env.setQuestId(questId);
        PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 454));
        return true;
    }
    
    @Override
    public boolean onItemUseEvent(final QuestCookie env, final Item item)
    {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        final int var = qs.getQuestVarById(0);        

        if (id != 164000140 && id != 164000142 && id != 164000143)
            return true;

        if(player.getWorldId() != 300230000)
            return false;

        VisibleObject target = player.getTarget();
        // Use on self
        if (id == 164000143 && target != null && !target.equals(player))
            return false;

        // Do not use on self
        if ((id == 164000140 || id == 164000142) && (target == null || target.equals(player) || !player.isEnemy(target)))
            return false;            

        if (qs != null && qs.getStatus() == QuestStatus.COMPLETE || qs != null && qs.getStatus() == QuestStatus.START)
        {
            if (id == 164000140 && !hasItem(player, 164000140))
            {
                if (ItemService.addItems(player, Collections.singletonList(new QuestItems(164000140, 1))));
            }
            if (id == 164000142 && !hasItem(player, 164000142))
            {
                if (ItemService.addItems(player, Collections.singletonList(new QuestItems(164000142, 1))));
            }
            if (id == 164000143 && !hasItem(player, 164000143))
            {
                if (ItemService.addItems(player, Collections.singletonList(new QuestItems(164000143, 1))));
            }
            useSkill(player, item);
         }
        return false; // don't remove from inventory
    }

    private void useSkill(Player player, Item item)
    {
        if (player.isItemUseDisabled(item.getItemTemplate().getDelayId()))
        {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ITEM_CANT_USE_UNTIL_DELAY_TIME);
            return;
        }

        int useDelay = item.getItemTemplate().getDelayTime();
        player.addItemCoolDown(item.getItemTemplate().getDelayId(), System.currentTimeMillis() + useDelay, useDelay / 1000);

        if (item.getItemId() == 164000140)
        {
            Skill skill = SkillEngine.getInstance().getSkill(player, 9835, 1, player);
            if(skill != null)
            {
            PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemTemplate().getTemplateId()), true);
            skill.useSkill();
            }
            return;
        }
        if (item.getItemId() == 164000142)
        {
            Skill skill = SkillEngine.getInstance().getSkill(player, 9837, 1, player);
            if(skill != null)
            {
            PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemTemplate().getTemplateId()), true);
            skill.useSkill();
            }
            return;
        }
        if (item.getItemId() == 164000143)
        {
            Skill skill = SkillEngine.getInstance().getSkill(player, 9838, 1, player);
            if(skill != null)
            {
            PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemTemplate().getTemplateId()), true);
            skill.useSkill();
            }
            return;
        }
    }

    @Override
    public boolean onActionItemEvent(QuestCookie env)
    {
        int targetId = env.getTargetId();
        return targetId == 730308 || targetId == 700939 || targetId == 730340 || targetId == 730341 || targetId == 730325 || targetId == 730338 || targetId == 730339 || targetId == 730337 || targetId == 730336;
    }

    private boolean hasItem(Player player, int itemId)
    {
        return player.getInventory().getItemCountByItemId(itemId) > 0;
    }
}