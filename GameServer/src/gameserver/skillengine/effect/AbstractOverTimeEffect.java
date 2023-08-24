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
import gameserver.skillengine.model.Effect;
import gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * @author ViAl
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractOverTimeEffect")
public abstract class AbstractOverTimeEffect extends EffectTemplate
{
	@XmlAttribute(required = true)
	protected int checktime;	
	@XmlAttribute
	protected int value;
	@XmlAttribute
	protected int delta;
	@XmlAttribute
	protected boolean percent;

	public int getValue()
	{
		return value;
	}
	
	@Override
	public void applyEffect(Effect effect)
	{
		effect.addToEffectedController();
	}

	@Override
	public void startEffect(Effect effect)
	{
		this.startEffect(effect,null);
	}

	public void startEffect(final Effect effect,EffectId abnormal)
	{
		final Creature effected = effect.getEffected();

		if(abnormal != null)
			effected.getEffectController().setAbnormal(abnormal.getEffectId());
		
		Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable(){

			@Override
			public void run()
			{
				onPeriodicAction(effect);
			}
		}, checktime, checktime);
		effect.setPeriodicTask(task, position);	
	}

}
