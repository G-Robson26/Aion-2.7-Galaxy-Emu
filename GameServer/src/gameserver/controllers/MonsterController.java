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
 **/
package gameserver.controllers;

import gameserver.model.alliance.PlayerAlliance;
import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Monster;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.RewardType;
import gameserver.model.group.PlayerGroup;
import gameserver.questEngine.QuestEngine;
import gameserver.questEngine.model.QuestCookie;
import gameserver.services.AllianceService;
import gameserver.services.DropService;
import gameserver.services.GroupService;
import gameserver.services.ToyPetService;
import gameserver.services.instance.AcademySoloInstanceService;
import gameserver.utils.stats.StatFunctions;
import gameserver.world.World;
import gameserver.world.WorldType;

/**
 * @author ATracer, Sarynth
 */
public class MonsterController extends NpcController {
    @Override
    public void doReward() {
        AionObject winner = getOwner().getAggroList().getMostDamage();

        if (winner == null)
            return;

        // TODO: Split the EXP based on overall damage.

        if (winner instanceof PlayerAlliance) {
            AllianceService.getInstance().doReward((PlayerAlliance) winner, getOwner());
        } else if (winner instanceof PlayerGroup) {
            GroupService.getInstance().doReward((PlayerGroup) winner, getOwner());
        } else if (((Player) winner).isInGroup()) {
            GroupService.getInstance().doReward(((Player) winner).getPlayerGroup(), getOwner());
        } else {
            super.doReward();

            Player player = (Player) winner;

            // Exp reward
            long expReward = StatFunctions.calculateSoloExperienceReward(player, getOwner());
            player.getCommonData().addExp(expReward, RewardType.HUNTING);

            // DP reward
            int currentDp = player.getCommonData().getDp();
            int dpReward = StatFunctions.calculateSoloDPReward(player, getOwner());
            player.getCommonData().setDp(dpReward + currentDp);

            // AP reward
            WorldType worldType = World.getInstance().getWorldMap(player.getWorldId()).getWorldType();
            if (worldType == WorldType.ABYSS) {
                int apReward = StatFunctions.calculateSoloAPReward(player, getOwner());
                player.getCommonData().addAp(apReward);
            }

            QuestEngine.getInstance().onKill(new QuestCookie(getOwner(), player, 0, 0));

            // Give Drop
            DropService.getInstance().registerDrop(getOwner(), player, player.getLevel());
			if ((player.getToyPet() != null) && (player.getToyPet().getAutoLoot()))
            ToyPetService.getInstance().startAutoLoot(player, getOwner().getObjectId().intValue());
        }
    }

    @Override
    public void onRespawn() {
        super.onRespawn();
        DropService.getInstance().unregisterDrop(getOwner());
    }

    @Override
    public Monster getOwner() {
        return (Monster) super.getOwner();
    }
	
	private void instanceSpecialRewardCheck(Monster monster, Player player) {
        switch (monster.getWorldId())  {
    case 300320000:
        AcademySoloInstanceService.getInstance().onReward(monster, player);
       }
    }
	
}