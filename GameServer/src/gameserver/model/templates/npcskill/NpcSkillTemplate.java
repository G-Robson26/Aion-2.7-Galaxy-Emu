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
package gameserver.model.templates.npcskill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author AionChs Master
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "npcskill")
public class NpcSkillTemplate {
    @XmlAttribute(name = "id")
    protected int id;
    @XmlAttribute(name = "skillid")
    protected int skillid;
    @XmlAttribute(name = "skilllevel")
    protected int skilllevel;
    @XmlAttribute(name = "probability")
    protected int probability;
    @XmlAttribute(name = "abouthp")
    protected boolean abouthp;
    @XmlAttribute(name = "min_hp", required = false)
    protected int    min_hp;
    @XmlAttribute(name = "max_hp", required = false)
    protected int    max_hp;
	
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the skillid
     */
    public int getSkillid() {
        return skillid;
    }

    /**
     * @return the skilllevel
     */
    public int getSkillLevel() {
        return skilllevel;
    }

    /**
     * @return the probability
     */
    public int getProbability() {
        return probability;
    }

    /**
     * @return the abouthp
     */
    public boolean isAboutHp() {
        return abouthp;
    }
	
	/**
     * @return min_hp
     */
	public int getMinHp() {
        return min_hp;
    }
	
	/**
     * @return max_hp
     */
    public int getMaxHp(){
        return max_hp;
    }
}