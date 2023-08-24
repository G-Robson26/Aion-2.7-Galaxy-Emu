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
package gameserver.services.instance;

import java.util.Iterator;
import java.util.List;
import gameserver.controllers.MonsterController;
import gameserver.dataholders.AcademySoloData;
import gameserver.dataholders.DataManager;
import gameserver.model.PlayerClass;
import gameserver.model.Race;
import gameserver.model.academy.AcademuSoloRound;
import gameserver.model.academy.AcademySoloStage;
import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Monster;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.VisibleObject;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.PlayerCommonData;
import gameserver.model.gameobjects.player.Storage;
import gameserver.model.templates.NpcTemplate;
import gameserver.model.templates.academy.AcademySoloSpawnList;
import gameserver.model.templates.academy.AcademySoloSpawnTemplate;
import gameserver.model.templates.spawn.SpawnTemplate;
import gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.services.ItemService;
import gameserver.services.TeleportService;
import gameserver.spawnengine.SpawnEngine;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.world.World;
import gameserver.world.WorldMapType;
import com.aionemu.commons.utils.Rnd;

public class AcademySoloInstanceService {

	private int shigosStart[] = { 0x32364, 0x32365, 0x32366, 0x32367, 0x32368, 0x32369	};
	private int shigosEnd[] = {	0x3236a, 0x3236b, 0x3236c, 0x3236d, 0x3236e, 0x3236f	};
	private float shigosX[] = {	345.7795F, 1784.463F, 1307.547F, 1258.743F, 346.523F, 1767.104F	};
	private float shigosY[] = {	1662.67F, 306.9865F, 1732.986F, 237.8629F, 349.2559F, 1288.443F	};
	private float shigosZ[] = {	95.44055F, 469.25F, 316.2322F, 405.3968F, 97.66545F, 390.7308F	};
	private byte shigosHead[] = { 0x40, 0x77, 0x58, 0, 0, 0x50	};
	private float resX[] = { 378.4972F, 1817.571F, 1356.953F, 1298.342F, 379.2112F, 1749.052F };
	private float resY[] = { 1665.008F, 307.2202F, 1752.548F, 237.8471F, 349.1243F, 1251.796F	};
	private float resZ[] = { 97.40607F, 496.3477F, 318.9346F, 406.0368F, 96.75763F, 389.1176F };

	public AcademySoloInstanceService() {
	}

	public static boolean isAcademySoloInstance(int i) {
		return i == WorldMapType.IDARENA_SOLO.getId();
	}

	public void spawnMonster(Player player) {
		AcademySoloStage academysolostage = DataManager.ACADEMY_SOLO_DATA.getSpawnsForStage(player.getAcademySoloStage());
		if (academysolostage == null)
			return;
		for (Iterator iterator = academysolostage.getAcademyRound().iterator(); iterator.hasNext();) {
			AcademuSoloRound academusoloround = (AcademuSoloRound)iterator.next();
			if (academusoloround.getRound() == player.getAcademySoloRound()) {
				Iterator iterator1 = academusoloround.getAcademySpawn().iterator();
				while (iterator1.hasNext())  {
					AcademySoloSpawnList academysolospawnlist = (AcademySoloSpawnList)iterator1.next();
					if (academysolospawnlist == null)
						return;
					if ((academysolospawnlist.getRace() == player.getCommonData().getRace() || academysolospawnlist.getRace() == Race.PC_ALL) && (academysolospawnlist.getPlayerClass() == player.getCommonData().getPlayerClass() || academysolospawnlist.getPlayerClass() == PlayerClass.ALL)) {
						player.setAcademySoloPool(player.getAcademySoloPool() + academysolospawnlist.getPool());
						Iterator iterator2 = academysolospawnlist.getObjects().iterator();
						while (iterator2.hasNext())  {
							AcademySoloSpawnTemplate academysolospawntemplate = (AcademySoloSpawnTemplate)iterator2.next();
							if (academysolospawnlist.getTime() == 0) {
								academysolospawntemplate.setSpawnGroup(academysolospawnlist);
								gameserver.model.templates.spawn.SpawnTemplate spawntemplate = SpawnEngine.getInstance().addNewSpawn(academysolospawntemplate.getWorldId(), player.getInstanceId(), academysolospawntemplate.getNpcId(), academysolospawntemplate.getX(), academysolospawntemplate.getY(), academysolospawntemplate.getZ(), academysolospawntemplate.getHeading(), 0, 0, true);
								gameserver.model.gameobjects.VisibleObject visibleobject = SpawnEngine.getInstance().spawnObject(spawntemplate, player.getInstanceId());
							    Creature creature = (Creature)visibleobject;
								ThreadPoolManager.getInstance().schedule(((Runnable) (new AcademySoloInstanceService$1(this, creature, player))), academysolospawnlist.getTime() * 1000);
							}
						}
					}
				}
			}
		}

	}

	class AcademySoloInstanceService$1
	implements Runnable {

	final Creature val$despawnObjectCreature;
	final Player val$player;
	final AcademySoloInstanceService this$0;

	AcademySoloInstanceService$1(AcademySoloInstanceService academysoloinstanceservice, Creature creature, Player player) {
		this$0 = academysoloinstanceservice;
		val$despawnObjectCreature = creature;
		val$player = player;
		/* return; */
	}

	public void run() {
		int i = val$despawnObjectCreature.getObjectId().intValue();
		AionObject aionobject = World.getInstance().findAionObject(i);
		if (aionobject != null && (aionobject instanceof Creature)) {
			Creature creature = (Creature)aionobject;
			DataManager.SPAWNS_DATA.removeSpawn(creature.getSpawn());
			creature.getController().delete();
			AcademySoloInstanceService.access$000(this$0, val$player);
			}
		/* return; */
		}
	}
	
	class AcademySoloInstanceService$2 implements Runnable {

	final Player val$player;
	final AcademySoloInstanceService this$0;

	AcademySoloInstanceService$2(AcademySoloInstanceService academysoloinstanceservice, Player player) {
		this$0 = academysoloinstanceservice;
		val$player = player;
		/* return; */
	}

	public void run() {
		AcademySoloStage academysolostage = DataManager.ACADEMY_SOLO_DATA.getSpawnsForStage(val$player.getAcademySoloStage());
		if (academysolostage == null)
			return;
		if (val$player.getAcademySoloStage() < academysolostage.size()) {
			AcademySoloInstanceService.access$100(this$0, val$player);
			AcademySoloInstanceService.access$200(this$0, val$player);
			this$0.spawnMonster(val$player);
		  } 
		}
	}
	
	public void nextSpawn(Player player) {
		PacketSendUtility.sendPacket(player, ((gameserver.network.aion.AionServerPacket) (new SM_QUEST_ACCEPTED(4, 0, 6))));
		ThreadPoolManager.getInstance().schedule(((Runnable) (new AcademySoloInstanceService$2(this, player))), 6000);
	}

	private void removeNpc(Monster monster) {
		AionObject aionobject = World.getInstance().findAionObject(monster.getObjectId().intValue());
		if (aionobject instanceof Monster) {
			Monster monster1 = (Monster)aionobject;
			if (monster1 != null)
				monster1.getController().onDespawn(true);
		}
	}

	private void spawnBox(Player player) {
		gameserver.model.templates.spawn.SpawnTemplate spawntemplate = SpawnEngine.getInstance().addNewSpawn(WorldMapType.IDARENA_SOLO.getId(), player.getInstanceId(), 0x35286, 340.9566F, 1653.13F, 95.6664F, (byte)0x15, 0, 0, true);
		SpawnEngine.getInstance().spawnObject(spawntemplate, player.getInstanceId());
	}

	private void spawnEndShigo(Player player) {
		sendEndStageMessage(player);
		if (player.getAcademySoloStage() == 1)
			spawnBox(player);
		int i = player.getAcademySoloStage() - 1;
		gameserver.model.templates.spawn.SpawnTemplate spawntemplate = SpawnEngine.getInstance().addNewSpawn(WorldMapType.IDARENA_SOLO.getId(), player.getInstanceId(), shigosEnd[i], shigosX[i], shigosY[i], shigosZ[i], shigosHead[i], 0, 0, true);
		gameserver.model.gameobjects.VisibleObject visibleobject = SpawnEngine.getInstance().spawnObject(spawntemplate, player.getInstanceId());
		Npc npc = (Npc)visibleobject;
		PacketSendUtility.broadcastPacket(((gameserver.model.gameobjects.VisibleObject) (npc)), ((gameserver.network.aion.AionServerPacket) (new SM_SYSTEM_MESSAGE(shigoDialog(player.getAcademySoloStage()), true, npc.getObjectId().intValue(), new Object[0]))), 0x1e);
	}

	public void spawnStartShigo(Player player) {
		sendEndStageMessage(player);
		int i = player.getAcademySoloStage() - 1;
		gameserver.model.templates.spawn.SpawnTemplate spawntemplate = SpawnEngine.getInstance().addNewSpawn(WorldMapType.IDARENA_SOLO.getId(), player.getInstanceId(), shigosStart[i], shigosX[i], shigosY[i], shigosZ[i], shigosHead[i], 0, 0, true);
		SpawnEngine.getInstance().spawnObject(spawntemplate, player.getInstanceId());
	}

	private int shigoDialog(int i) {
		switch (i) {
		case 1: // '\001'
			return 0x10f5b4;

		case 2: // '\002'
			return 0x10f5b5;

		case 3: // '\003'
			return 0x10f5b6;

		case 4: // '\004'
			return 0x10f5b7;

		case 5: // '\005'
			return 0x10f5b8;

		case 6: // '\006'
			return 0x10f5b9;
		}
		return 0;
	}

	private void sendPointMessage(Player player, int i, Monster monster) {
		PacketSendUtility.sendPacket(player, ((gameserver.network.aion.AionServerPacket) (SM_SYSTEM_MESSAGE.STR_MSG_GET_SCORE(i, monster.getObjectTemplate().getNameId()))));
	}

	private void sendStartRoundAndStageMessage(Player player) {
		PacketSendUtility.sendPacket(player, ((gameserver.network.aion.AionServerPacket) (SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_JOIN_ROUND_IDARENA(player.getAcademySoloStage(), player.getAcademySoloRound()))));
	}

	private void sendStartRoundMessage(Player player) {
		PacketSendUtility.sendPacket(player, ((gameserver.network.aion.AionServerPacket) (SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_START_ROUND_IDARENA(player.getAcademySoloRound()))));
	}

	private void sendEndRoundMessage(Player player) {
		PacketSendUtility.sendPacket(player, ((gameserver.network.aion.AionServerPacket) (SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_COMPLETE_ROUND_IDARENA(player.getAcademySoloRound()))));
	}

	private void sendMessage(Player player) {
	}

	private void sendEndStageMessage(Player player) {
		PacketSendUtility.sendPacket(player, ((gameserver.network.aion.AionServerPacket) (SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_COMPLETE_STAGE_IDARENA(player.getAcademySoloStage()))));
	}

	public void onReward(Monster monster, Player player) {
		player.setInstanceBalaurKills(player.getInstanceBalaurKills() + 1);
		int i = calculatePointsReward(monster, player);
		if (i > 0)
			sendPointMessage(player, i, monster);
		player.setInstancePlayerScore(player.getInstancePlayerScore() + i);
		removeNpc(monster);
		AcademySoloStage academysolostage = DataManager.ACADEMY_SOLO_DATA.getSpawnsForStage(player.getAcademySoloStage());
		if (academysolostage == null)
			return;
		Iterator iterator = academysolostage.getAcademyRound().iterator();
		do {
			if (!iterator.hasNext())
				break;
			AcademuSoloRound academusoloround = (AcademuSoloRound)iterator.next();
			if (academusoloround.getRound() == player.getAcademySoloRound() && player.getInstanceBalaurKills() == player.getAcademySoloPool()) {
				player.setInstanceBalaurKills(0);
				player.setAcademySoloPool(0);
				nextSpawn(player);
				sendEndRoundMessage(player);
			}
		} while (true);
		sendMessage(player);
	}

	private int calculatePointsReward(Monster monster, Player player) {
		int i = 0;
		int j = monster.getObjectTemplate().getTemplateId();
		AcademySoloStage academysolostage = DataManager.ACADEMY_SOLO_DATA.getSpawnsForStage(player.getAcademySoloStage());
		if (academysolostage == null)
			return 0;
		for (Iterator iterator = academysolostage.getAcademyRound().iterator(); iterator.hasNext();) {
			AcademuSoloRound academusoloround = (AcademuSoloRound)iterator.next();
			Iterator iterator1 = academusoloround.getAcademySpawn().iterator();
			while (iterator1.hasNext())  {
				AcademySoloSpawnList academysolospawnlist = (AcademySoloSpawnList)iterator1.next();
				if (j == academysolospawnlist.getNpcid())
					i = academysolospawnlist.getPoint();
			}
		}

		return i;
	}

	public void exitArena(Player player) {
		if (player.getWorldId() == WorldMapType.IDARENA_SOLO.getId())
			if (player.getCommonData().getRace() == Race.ASMODIANS)
				TeleportService.teleportTo(player, WorldMapType.MARCHUTAN_PRIORY.getId(), 571F, 260F, 93.48F, 3000);
			else
				TeleportService.teleportTo(player, WorldMapType.KAISINEL_ACADEMY.getId(), 510F, 230F, 126.9759F, 3000);
		player.setInstancePlayerScore(0);
	}

	public void doReward(Player player) {
		if (player.getInAcademy()) {
			int i = 0xdbba00;
			if (player.getInventory().getItemCountByItemId(0xb1622fc) > 0L)
				player.getInventory().removeFromBagByItemId(0xb1622fc, player.getInventory().getItemCountByItemId(0xb1622fc));
			ItemService.addItem(player, 0xb162302, Math.round(player.getInstancePlayerScore() / 0x3c));
			player.setInAcademy(false);
			exitArena(player);
		}
	}

	public void revivePlayer(Player player) {
		TeleportService.teleportTo(player, WorldMapType.IDARENA_SOLO.getId(), player.getInstanceId(), resX[player.getAcademySoloStage() - 1], resY[player.getAcademySoloStage() - 1], resZ[player.getAcademySoloStage() - 1], (byte)0, 0);
	}

	public static AcademySoloInstanceService getInstance() {
		return SingletonHolder.instance;
	}

	@SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final AcademySoloInstanceService instance = new AcademySoloInstanceService();
    }
	
	static void access$000(AcademySoloInstanceService academysoloinstanceservice, Player player) {
		academysoloinstanceservice.sendStartRoundAndStageMessage(player);
	}

	static void access$100(AcademySoloInstanceService academysoloinstanceservice, Player player) {
		academysoloinstanceservice.sendStartRoundMessage(player);
	}

	static void access$200(AcademySoloInstanceService academysoloinstanceservice, Player player) {
		academysoloinstanceservice.spawnEndShigo(player);
	}
}
