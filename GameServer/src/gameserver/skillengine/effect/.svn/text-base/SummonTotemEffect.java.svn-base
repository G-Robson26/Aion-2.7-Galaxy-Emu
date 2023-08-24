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

import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Totem;
import gameserver.model.gameobjects.stats.StatEnum;
import gameserver.model.templates.spawn.SpawnTemplate;
import gameserver.skillengine.model.Effect;
import gameserver.spawnengine.SpawnEngine;
import gameserver.utils.ThreadPoolManager;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * @author kecimis
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummonTotemEffect")
public class SummonTotemEffect extends SummonEffect
{

	@XmlAttribute(name = "skill_id", required = true)
	protected int	skillId;

	@Override
	public void applyEffect(Effect effect)
	{
		//(no skill)
		if(skillId == 0)
			return;
		
		Creature effector = effect.getEffector();
		SpawnEngine spawnEngine = SpawnEngine.getInstance();
		float x = effector.getX();
		float y = effector.getY();
		float z = effector.getZ();
		byte heading = effector.getHeading();
		int worldId = effector.getWorldId();
		int instanceId = effector.getInstanceId();
		
		SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, npcId, x, y, z, heading, 0, 0, true, true);
		final Totem totem = spawnEngine.spawnTotem(spawn, instanceId, effector, skillId);
		totem.getGameStats().setStat(StatEnum.BOOST_HEAL, (effector.getGameStats().getCurrentStat(StatEnum.BOOST_HEAL)*75)/100);		
		totem.getKnownList().doUpdate();
		ThreadPoolManager.getInstance().schedule(new Runnable(){

			@Override
			public void run()
			{
				totem.getLifeStats().reduceHp(totem.getLifeStats().getCurrentHp() + 1, totem, true);
			}
		}, 1000);
	}

	@Override
	public void calculate(Effect effect)
	{
		effect.addSucessEffect(this);
	}
}