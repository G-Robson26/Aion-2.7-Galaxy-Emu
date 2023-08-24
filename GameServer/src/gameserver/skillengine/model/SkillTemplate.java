/*
 * This file is part of Aion X EMU <aionxemu>.
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
package gameserver.skillengine.model;

import gameserver.skillengine.action.Actions;
import gameserver.skillengine.condition.Conditions;
import gameserver.skillengine.effect.EffectTemplate;
import gameserver.skillengine.effect.Effects;
import gameserver.skillengine.properties.Properties;
import gameserver.skillengine.properties.Property;
import gameserver.skillengine.properties.TargetRangeProperty;
import gameserver.skillengine.properties.TargetRelationProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skillTemplate", propOrder = {
        "initproperties",
        "startconditions",
        "setproperties",
        "useconditions",
        "effects",
        "actions"
})
public class SkillTemplate {
    protected Properties initproperties;
    protected Conditions startconditions;
    protected Properties setproperties;
    protected Conditions useconditions;
    protected Effects effects;
    protected Actions actions;


    @XmlAttribute(name = "skill_id", required = true)
    protected int skillId;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "nameId", required = true)
    protected int nameId;
    @XmlAttribute(name = "stack")
    protected String stack = "NONE";
    @XmlAttribute(name = "lvl")
    protected int lvl;
    @XmlAttribute(name = "skilltype", required = true)
    protected SkillType type;
    @XmlAttribute(name = "skillsubtype", required = true)
    protected SkillSubType subType;
    @XmlAttribute(name = "tslot")
    protected SkillTargetSlot targetSlot;
    @XmlAttribute(name = "tslot_level")
    protected int targetSlotLevel;
    @XmlAttribute(name = "activation", required = true)
    protected ActivationAttribute activationAttribute;
    @XmlAttribute(name = "duration", required = true)
    protected int duration;
    @XmlAttribute(name = "cooldown")
    protected int cooldown;
    @XmlAttribute(name = "penalty_skill_id")
    protected int penaltySkillId;
    @XmlAttribute(name = "pvp_damage")
    protected int pvpDamage;
    @XmlAttribute(name = "pvp_duration")
    protected int pvpDuration;
    @XmlAttribute(name = "chain_skill_prob")
    protected int chainSkillProb;
    @XmlAttribute(name = "cancel_rate")
    protected int cancelRate;
    @XmlAttribute(name = "stance")
    protected boolean stance;
    @XmlAttribute(name = "skillset_exception")
//<delay_id> in client_skills??
    protected int skillSetException;

    /**
     * @return the initProperties
     */
    public Properties getInitproperties() {
        return initproperties;
    }

    /**
     * @return the setProperties
     */
    public Properties getSetproperties() {
        return setproperties;
    }

    /**
     * Gets the value of the startconditions property.
     *
     * @return possible object is
     *         {@link Conditions }
     */
    public Conditions getStartconditions() {
        return startconditions;
    }

    /**
     * Gets the value of the useconditions property.
     *
     * @return possible object is
     *         {@link Conditions }
     */
    public Conditions getUseconditions() {
        return useconditions;
    }

    /**
     * Gets the value of the effects property.
     *
     * @return possible object is
     *         {@link Effects }
     */
    public Effects getEffects() {
        return effects;
    }

    /**
     * Gets the value of the actions property.
     *
     * @return possible object is
     *         {@link Actions }
     */
    public Actions getActions() {
        return actions;
    }

    /**
     * Gets the value of the skillId property.
     */
    public int getSkillId() {
        return skillId;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * @return the nameId
     */
    public int getNameId() {
        return nameId;
    }

    /**
     * @return the stack
     */
    public String getStack() {
        return stack;
    }

    /**
     * @return the lvl
     */
    public int getLvl() {
        return lvl;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     *         {@link SkillType }
     */
    public SkillType getType() {
        return type;
    }

    /**
     * @return the subType
     */
    public SkillSubType getSubType() {
        return subType;
    }

    /**
     * @return the targetSlot
     */
    public SkillTargetSlot getTargetSlot() {
        return targetSlot;
    }

    /**
     * @return the targetSlot Level
     */
    public int getTargetSlotLevel() {
        return targetSlotLevel;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return the activationAttribute
     */
    public ActivationAttribute getActivationAttribute() {
        return activationAttribute;
    }

    public boolean isPassive() {
        return activationAttribute == ActivationAttribute.PASSIVE;
    }

    public boolean isToggle() {
        return activationAttribute == ActivationAttribute.TOGGLE;
    }

    public boolean isProvoked() {
        return activationAttribute == ActivationAttribute.PROVOKED;
    }

    public boolean isActive() {
        return activationAttribute == ActivationAttribute.ACTIVE;
    }


    public TargetRangeProperty getTargetRangeProperty() {
        if (setproperties == null)
            return null;
        for (Property prop : setproperties.getProperties()) {
            if (prop instanceof TargetRangeProperty)
                return (TargetRangeProperty) prop;
        }

        return null;
    }

    public TargetRelationProperty getTargetRelationProperty() {
        if (setproperties == null)
            return null;
        for (Property prop : setproperties.getProperties()) {
            if (prop instanceof TargetRelationProperty)
                return (TargetRelationProperty) prop;
        }

        return null;
    }

    /**
     * @param position
     * @return EffectTemplate
     */
    public EffectTemplate getEffectTemplate(int position) {
        return effects != null && effects.getEffects().size() >= position
                ? effects.getEffects().get(position - 1) : null;

    }

    /**
     * @return
     */
    public int getEffectsDuration() {
        return effects != null ? effects.getEffectsDuration() : 0;
    }

    /**
     * @return the cooldown
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * @return the penaltySkillId
     */
    public int getPenaltySkillId() {
        return penaltySkillId;
    }

    /**
     * @return the pvpDamage
     */
    public int getPvpDamage() {
        return pvpDamage;
    }

    /**
     * @return the pvpDuration
     */
    public int getPvpDuration() {
        return pvpDuration;
    }

    /**
     * @return chainSkillProb
     */
    public int getChainSkillProb() {
        return chainSkillProb;
    }

    /**
     * @return cancelRate
     */
    public int getCancelRate() {
        return cancelRate;
    }

    public boolean isStance() {
        return stance;
    }

    /**
     * @return skillSetException
     */
    public int getSkillSetException() {
        return skillSetException;
    }

    public boolean hasResurrectEffect() {
        return getEffects() != null && getEffects().isResurrect();
    }

    public boolean hasItemHealFpEffect() {
        return getEffects() != null && getEffects().isItemHealFp();
	}
}
