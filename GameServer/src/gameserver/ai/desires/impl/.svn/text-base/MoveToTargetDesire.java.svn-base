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
package gameserver.ai.desires.impl;

import gameserver.ai.AI;
import gameserver.ai.desires.AbstractDesire;
import gameserver.ai.desires.MoveDesire;
import gameserver.ai.state.AIState;
import gameserver.configs.main.CustomConfig;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.state.CreatureState;
import gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import gameserver.skillengine.SkillEngine;
import gameserver.skillengine.model.Skill;
import gameserver.utils.MathUtil;

/**
 * @author Pinguin, ATracer
 */
 
public class MoveToTargetDesire extends AbstractDesire implements MoveDesire
{
	private Npc owner;
	private Creature target;
	private float targetOffset;
	
	/**
	 * @param crt 
	 * @param desirePower
	 */
	public MoveToTargetDesire(Npc owner, Creature target, float targetOffset, int desirePower)
	{
		super(desirePower);
		this.owner = owner;
		this.target = target;
		this.targetOffset = targetOffset;
	}

	@Override
	public boolean handleDesire(AI<?> ai)
	{
		if (owner == null || owner.getLifeStats().isAlreadyDead())
			return false;
		if(target == null || target.getLifeStats().isAlreadyDead())
			return false;

		/**
		 * Disable NPCs desire to move when walk speed equals 0 
		 */
		if(owner.getObjectTemplate().getStatsTemplate().getWalkSpeed() == 0)
			return false;
		
		if((target.isInState(CreatureState.FLYING) && CustomConfig.DISABLE_FLYAGGRO_MOVE) ||
			(target.isInState(CreatureState.GLIDING) && CustomConfig.DISABLE_GLIDEAGGRO_MOVE))
		{
			if(CustomConfig.FLYAGGRO_COUNTERATTACK)
			{
				Skill skill = SkillEngine.getInstance().getSkill(owner, CustomConfig.FLYAGGRO_COUNTERSKILL, 1, target);
				skill.useSkill();
			}
			return false;
		}			
			
		if(MathUtil.isInRange(owner, target, targetOffset))
		{
			owner.getMoveController().stop();
			return true;
		}

		owner.getMoveController().followTarget(targetOffset);
		if(!owner.getMoveController().isScheduled())
			owner.getMoveController().schedule();
		
		/** MoveToHome if *target* is trying to lure it away from home 
		 * (this will prevent Npcs from going home and heal inevitably) 
			and not only if target is too far away **/
		if (owner.getSpawn() != null &&
			MathUtil.getDistance(target, owner.getSpawn().getX(), owner.getSpawn().getY(), owner.getSpawn().getZ()) > 125)
		{
			owner.getLifeStats().increaseHp(TYPE.NATURAL_HP, owner.getLifeStats().getMaxHp());
			ai.setAiState(AIState.MOVINGTOHOME);
			return false;
		}

		if(owner.getMoveController().getDistanceToTarget() > 150)
			return false;
		
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		if(!(o instanceof MoveToTargetDesire))
			return false;

		MoveToTargetDesire that = (MoveToTargetDesire) o;
		return target.equals(that.target);
	}

	/**
	 * @return the target
	 */
	public Creature getTarget()
	{	
		return target;
	}

	@Override
	public int getExecutionInterval()
	{
		return 1;
	}

	@Override
	public void onClear()
	{
		owner.getMoveController().stop();
	}
}