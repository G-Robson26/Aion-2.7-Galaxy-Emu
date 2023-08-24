/**
 * This file is part of Aion Galaxy EMU <aiongemu.com>.
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.skillengine.effect;

import gameserver.controllers.movement.ActionObserver;
import gameserver.controllers.movement.ActionObserver.ObserverType;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Homing;
import gameserver.model.templates.spawn.SpawnTemplate;
import gameserver.skillengine.model.Effect;
import gameserver.skillengine.model.Skill;
import gameserver.spawnengine.SpawnEngine;
import gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.commons.utils.Rnd;



/**
 * @author ATracer
 * reworked by kecimis
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummonHomingEffect")
public class SummonHomingEffect extends SummonEffect
{
	@XmlAttribute(name = "npc_count", required = true)
	protected int	npcCount;
	@XmlAttribute(name = "attack_count", required = true)
	protected int	attackCount;
	@XmlAttribute(name = "skill_id", required = true)
	protected int	skillId;

	@Override
	public void applyEffect(final Effect effect)
	{
		final Creature effector = effect.getEffector();
		final SpawnEngine spawnEngine = SpawnEngine.getInstance();
		float x = effector.getX();
		float y = effector.getY();
		final float z = effector.getZ();
		final byte heading = effector.getHeading();
		final int worldId = effector.getWorldId();
		final int instanceId = effector.getInstanceId();

		int randomTime = 0;
		for(int i = 0; i < npcCount; i++)
		{
			final SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, npcId, x, y, z, heading, 0, 0,
				true, true);
			Runnable spawningTask = new Runnable(){
				@Override
				public void run()
				{
					final Homing homing = spawnEngine.spawnHoming(spawn, instanceId, effector, attackCount, skillId,
						effect.getEffector().getLevel());
					// automatically despawn after 1:00 min(prevents leaks)
					Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable(){
						@Override
						public void run()
						{
							homing.getLifeStats().reduceHp(homing.getLifeStats().getCurrentHp() + 1, homing, true);
						}
					}, 60000);
					homing.setDespawnTask(task);

					// add observer
					ActionObserver observer = null;
					if(skillId == 0)
					{
						observer = new ActionObserver(ObserverType.ATTACK){
							@Override
							public void attack(Creature creature)
							{
								homing.setCounter(homing.getCounter() + 1);

								if(homing.getCounter() == attackCount)
								{
									homing.getLifeStats().reduceHp(10000, homing, true);
									return;
								}
							}
						};
					}
					else
					{
						observer = new ActionObserver(ObserverType.SKILLUSE){
							@Override
							public void skilluse(Skill skill)
							{
								homing.setCounter(homing.getCounter() + 1);

								if(homing.getCounter() == attackCount)
								{
									homing.getLifeStats().reduceHp(10000, homing, true);
									return;
								}
							}
						};
					}

					if(observer != null)
					{
						homing.getObserveController().addObserver(observer);
						homing.setObserver(observer);
					}

				}
			};
			//randomization of spawns
			ThreadPoolManager.getInstance().schedule(spawningTask, randomTime);
			randomTime += 500 + Rnd.get(0, 500);
		}
	}

	@Override
	public void calculate(Effect effect)
	{
		effect.addSucessEffect(this);
	}

}