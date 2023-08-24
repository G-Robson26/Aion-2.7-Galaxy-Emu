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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;
import gameserver.configs.main.CustomConfig;
import gameserver.geo.GeoEngine;
import gameserver.model.gameobjects.player.Player;
import gameserver.skillengine.model.DashParam;
import gameserver.skillengine.model.Effect;
import gameserver.skillengine.model.DashParam.DashType;
import gameserver.utils.MathUtil;
import gameserver.world.World;


/**
 * @author Sylar
 * @edit kecimis
 * fixed by Pashatr
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RandomMoveLocEffect")
public class RandomMoveLocEffect extends EffectTemplate
{
	
	@XmlAttribute(name = "direction")
	private String direction;
	
	@XmlAttribute(name = "value")
	private int value;
	
	@Override
	public void applyEffect(Effect effect)
	{
		if (CustomConfig.GEODATA_EFFECTS_ENABLED)
		{
			DashParam dash = effect.getDashParam();
			World.getInstance().updatePosition((Player)effect.getEffected(), dash.getX(), dash.getY(), dash.getZ(), (byte)dash.getHeading(), false);
		}
	}
	
	@Override
	public void calculate(Effect effect)
	{
		if (!(effect.getEffected() instanceof Player))
			return;
		
		if (CustomConfig.GEODATA_EFFECTS_ENABLED)
		{
			Player player = (Player)effect.getEffected();
			double radian = Math.toRadians(MathUtil.convertHeadingToDegree(player.getHeading()));
			float x = player.getX();
			float y = player.getY();
			float z = player.getZ();
			int worldId = player.getWorldId();
			
			float x2 = 0;
			float y2 = 0;
			float lastSee = 0;
			float lastNonSee = 0;
						
			if(direction.equals("FRONT"))
			{
				x2 = (float)(x + (value * Math.cos(radian)));
				y2 = (float)(y + (value * Math.sin(radian)));
				
				//if can see the final point then just port
				if (GeoEngine.getInstance().canSee(worldId, x, y, z, x2, y2, z))
				{
					this.setDashParam(effect, x2, y2, GeoEngine.getInstance().getZ(worldId, x2, y2, z));
					
					return;
				}
				else
					lastNonSee = value;
				
				float temp = 0;
				
				while (lastNonSee - lastSee >= 0.5)
				{
					temp = (lastNonSee - lastSee)/2 + lastSee;
					x2 = (float)(x + (temp * Math.cos(radian)));
					y2 = (float)(y + (temp * Math.sin(radian)));
					
					if (GeoEngine.getInstance().canSee(worldId, x, y, z, x2, y2, z))
						lastSee = temp;
					else
						lastNonSee = temp;
				}
	
				//set final coordinates
				x2 = (float)(x + (lastSee * Math.cos(radian)));
				y2 = (float)(y + (lastSee * Math.sin(radian)));
			}
			else if(direction.equals("BACK"))
			{
				x2 = (float)(x + (value * Math.cos(Math.PI+radian)));
				y2 = (float)(y + (value * Math.sin(Math.PI+radian)));
				
				//if can see the final point then just port
				if (GeoEngine.getInstance().canSee(worldId, x, y, z, x2, y2, z))
				{
					this.setDashParam(effect, x2, y2, GeoEngine.getInstance().getZ(worldId, x2, y2, z));
					
					return;
				}
				else
					lastNonSee = value;
				
				float temp = 0;
				
				while (lastNonSee - lastSee >= 0.5)
				{
					temp = (lastNonSee - lastSee)/2 + lastSee;
					x2 = (float)(x + (temp * Math.cos(Math.PI+radian)));
					y2 = (float)(y + (temp * Math.sin(Math.PI+radian)));
					
					if (GeoEngine.getInstance().canSee(worldId, x, y, z, x2, y2, z))
						lastSee = temp;
					else
						lastNonSee = temp;
				}
				
				//set final coordinates
				x2 = (float)(x + (lastSee * Math.cos(Math.PI+radian)));
				y2 = (float)(y + (lastSee * Math.sin(Math.PI+radian)));
			}
			else
			{
				Logger.getLogger(RandomMoveLocEffect.class).error("Cannot move without direction");
				return;
			}

			this.setDashParam(effect, x2, y2, GeoEngine.getInstance().getZ(worldId, x2, y2, z));
		
		}
		return;
	}
	
	private void setDashParam(Effect effect, float x, float y, float z)
	{
		Player player = (Player)effect.getEffected();
		Logger.getLogger(RandomMoveLocEffect.class).debug(player.getObjectId() + " moving " + value +"m direction "+direction.toString());
		Logger.getLogger(RandomMoveLocEffect.class).debug(player.getObjectId() + " x: "+x+" y: "+y+" z: "+z);
		effect.setDashParam(new DashParam(DashType.RANDOMMOVELOC, x, y, z, player.getHeading()));
	}
}
