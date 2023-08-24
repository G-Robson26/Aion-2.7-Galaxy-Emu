/**
 * This file is part of Aion Galaxy EMU <aiongemu>.
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

import com.aionemu.commons.utils.Rnd;
import gameserver.controllers.attack.AttackStatus;
import gameserver.controllers.movement.ActionObserver;
import gameserver.controllers.movement.AttackCalcObserver;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.stats.listeners.ItemEquipmentListener;
import gameserver.model.templates.item.ItemTemplate;
import gameserver.model.templates.item.WeaponType;
import gameserver.network.aion.serverpackets.SM_SKILL_ACTIVATION;
import gameserver.skillengine.effect.*;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;

import java.util.*;
import java.util.concurrent.Future;

/**
 * @author ATracer
 */
public class Effect {
    private SkillTemplate skillTemplate;
    private int skillLevel;
    private int duration;
    private int endTime;

    private Creature effected;
    private Creature effector;
    private Future<?> checkTask = null;
    private Future<?> task = null;
    private Future<?>[] periodicTasks = null;
    private Future<?> mpUseTask = null;
    private Future<?> hpUseTask = null;

    /**
     * Used for damage/heal values
     */
    private int reserved1;
    /**
     * Used for shield total hit damage;
     */
    private int reserved2;
    /**
     * Used for shield hit damage
     */
    private int reserved3;

    /**
     * Spell Status
     * <p/>
     * 1 : stumble
     * 2 : knockback
     * 4 : open aerial
     * 8 : close aerial
     * 16 : spin
     * 32 : block
     * 64 : parry
     * 128 : dodge
     * 256 : resist
     */
    private SpellStatus spellStatus = SpellStatus.NONE;

    private AttackStatus attackStatus = AttackStatus.NORMALHIT;
    private int shieldDefense;

    private boolean addedToController;
    private AttackCalcObserver[] attackStatusObserver;

    private AttackCalcObserver[] attackShieldObserver;

    private boolean launchSubEffect = true;
    private Effect subEffect = null;

	private int reflectedDamage = 0;
    private int reflectorSkillId = 0;
	
	/**
	 * coordinates for point skills or dashtype skills
	 */
	private float x;
	private float y;
	private float z;
	
	private DashParam dashType = null;
	
    private boolean isStopped;

    private ItemTemplate itemTemplate;

    /**
     * Hate that will be placed on effected list
     */
    private int tauntHate;
    /**
     * Total hate that will be broadcasted
     */
    private int effectHate;

    private Collection<EffectTemplate> sucessEffects = Collections.synchronizedList(new ArrayList<EffectTemplate>());

    protected int abnormals;

    /**
     * Action observer that should be removed after effect end
     */
    private ActionObserver[] actionObserver;

    /**
     * ABNORMAL EFFECTS
     */

    public void setAbnormal(int mask) {
        abnormals |= mask;
    }

    public int getAbnormals() {
        return abnormals;
    }

    public Effect(Creature effector, Creature effected, SkillTemplate skillTemplate, int skillLevel, int duration) {
        this.effector = effector;
        this.effected = effected;
        this.skillTemplate = skillTemplate;
        this.skillLevel = skillLevel;
        this.duration = duration;
    }

    public Effect(Creature effector, Creature effected, SkillTemplate skillTemplate, int skillLevel, int duration, ItemTemplate itemTemplate) {
        this(effector, effected, skillTemplate, skillLevel, duration);
        this.itemTemplate = itemTemplate;
    }

    /**
     * @return the effectorId
     */
    public int getEffectorId() {
        return effector.getObjectId();
    }

    /**
     * @return the skillId
     */
    public int getSkillId() {
        return skillTemplate.getSkillId();
    }

    /**
     * @return the SkillSetException
     */
    public int getSkillSetException() {
        return skillTemplate.getSkillSetException();
    }

    /**
     * @return the stack
     */
    public String getStack() {
        return skillTemplate.getStack();
    }

    /**
     * @return the skillLevel
     */
    public int getSkillLevel() {
        return skillLevel;
    }

    /**
     * @return the skillStackLvl
     */
    public int getSkillStackLvl() {
        return skillTemplate.getLvl();
    }

    /**
     * @return
     */
    public SkillType getSkillType() {
        return skillTemplate.getType();
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param newDuration
     */
    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }

    /**
     * @return the effected
     */
    public Creature getEffected() {
        return effected;
    }

    /**
     * @return the effector
     */
    public Creature getEffector() {
        return effector;
    }

    /**
     * @return the isPassive
     */
    public boolean isPassive() {
        return skillTemplate.isPassive();
    }

    /**
     * @param task the task to set
     */
    public void setTask(Future<?> task) {
        this.task = task;
    }

    /**
     * @return the periodicTask
     */
    public Future<?> getPeriodicTask(int i) {
        return periodicTasks[i - 1];
    }

    /**
     * @param periodicTask the periodicTask to set
     * @param i
     */
    public void setPeriodicTask(Future<?> periodicTask, int i) {
        if (periodicTasks == null)
            periodicTasks = new Future<?>[4];
        this.periodicTasks[i - 1] = periodicTask;
    }

    /**
     * @return the mpUseTask
     */
    public Future<?> getMpUseTask() {
        return mpUseTask;
    }

    /**
     * @param mpUseTask the mpUseTask to set
     */
    public void setMpUseTask(Future<?> mpUseTask) {
        this.mpUseTask = mpUseTask;
    }

    /**
     * @return the hpUseTask
     */
    public Future<?> getHpUseTask() {
        return hpUseTask;
    }

    /**
     * @param hpUseTask the hpUseTask to set
     */
    public void setHpUseTask(Future<?> hpUseTask) {
        this.hpUseTask = hpUseTask;
    }

    /**
     * @return the reserved1
     */
    public int getReserved1() {
        return reserved1;
    }

    /**
     * @param reserved1 the reserved1 to set
     */
    public void setReserved1(int reserved1) {
        this.reserved1 = reserved1;
    }

    /**
     * @return the reserved2
     */
    public int getReserved2() {
        return reserved2;
    }

    /**
     * @param reserved2 the reserved2 to set
     */
    public void setReserved2(int reserved2) {
        this.reserved2 = reserved2;
    }

    /**
     * @return the reserved3
     */
    public int getReserved3() {
        return reserved3;
    }

    /**
     * @param reserved3 the reserved3 to set
     */
    public void setReserved3(int reserved3) {
        this.reserved3 = reserved3;
    }

    /**
     * @return the attackStatus
     */
    public AttackStatus getAttackStatus() {
        return attackStatus;
    }

    /**
     * @param attackStatus the attackStatus to set
     */
    public void setAttackStatus(AttackStatus attackStatus) {
        this.attackStatus = attackStatus;
    }

    public List<EffectTemplate> getEffectTemplates() {
        return skillTemplate.getEffects().getEffects();
    }

    public boolean isFood() {
        Effects effects = skillTemplate.getEffects();
        return effects != null && effects.isFood();
    }

	public boolean isItemheal() {
		Effects effects = skillTemplate.getEffects();
		return effects != null && effects.isItemHeal();
	}

	public boolean isMpheal() {
		Effects effects = skillTemplate.getEffects();
		return effects != null && effects.isMpHeal();
	}


    public boolean isToggle() {
        return skillTemplate.getActivationAttribute() == ActivationAttribute.TOGGLE;
    }

    public int getTargetSlot() {
        return skillTemplate.getTargetSlot().ordinal();
    }

    public int getTargetSlotLevel() {
        return skillTemplate.getTargetSlotLevel();
    }

    /**
     * @param i
     * @return attackStatusObserver for this effect template
     */
    public AttackCalcObserver getAttackStatusObserver(int i) {
        if (attackStatusObserver != null && i <= attackStatusObserver.length)
            return attackStatusObserver[i - 1];
        else
            return null;
    }

    /**
     * @param attackStatusObserver the attackCalcObserver to set
     */
    public void setAttackStatusObserver(AttackCalcObserver attackStatusObserver, int i) {
        if (this.attackStatusObserver == null)
            this.attackStatusObserver = new AttackCalcObserver[4];
        this.attackStatusObserver[i - 1] = attackStatusObserver;
    }

    /**
     * @param i
     * @return attackShieldObserver for this effect template
     */
    public AttackCalcObserver getAttackShieldObserver(int i) {
        return attackShieldObserver[i - 1];
    }

    /**
     * @param attackShieldObserver the attackShieldObserver to set
     */
    public void setAttackShieldObserver(AttackCalcObserver attackShieldObserver, int i) {
        if (this.attackShieldObserver == null)
            this.attackShieldObserver = new AttackCalcObserver[4];
        this.attackShieldObserver[i - 1] = attackShieldObserver;
    }

    /**
     * @return the launchSubEffect
     */
    public boolean isLaunchSubEffect() {
        return launchSubEffect;
    }

    /**
     * @param launchSubEffect the launchSubEffect to set
     */
    public void setLaunchSubEffect(boolean launchSubEffect) {
        this.launchSubEffect = launchSubEffect;
    }

    /**
     * @return the shieldDefense
     */
    public int getShieldDefense() {
        return shieldDefense;
    }

    /**
     * @param shieldDefense the shieldDefense to set
     */
    public void setShieldDefense(int shieldDefense) {
        this.shieldDefense = shieldDefense;
    }

    /**
     * @return the spellStatus
     */
    public SpellStatus getSpellStatus() {
        return spellStatus;
    }

    /**
     * @param spellStatus the spellStatus to set
     */
    public void setSpellStatus(SpellStatus spellStatus) {
        this.spellStatus = spellStatus;
    }

    /**
     * @return the subEffect
     */
    public Effect getSubEffect() {
        return subEffect;
    }

    /**
     * @param subEffect the subEffect to set
     */
    public void setSubEffect(Effect subEffect) {
        this.subEffect = subEffect;
    }

    /**
     * @param effectId
     * @return true or false
     */
    public boolean containsEffectId(int effectId) {
        synchronized (sucessEffects) {
            for (EffectTemplate template : sucessEffects) {
                if (template.getEffectid() == effectId)
                    return true;
            }
        }

        return false;
    }

    /**
     * Correct lifecycle of Effect
     *  - INITIALIZE
     *  - APPLY
     *  - START
     *  - END
     */


    /**
     * Do initialization with proper calculations
     */
    public void initialize() {
        if (skillTemplate.getEffects() == null)
            return;

        boolean isDmgEffect = false;
        EffectTemplate moveBehindEffect = null;

        for (EffectTemplate template : getEffectTemplates()) {
            template.calculate(this);
            if (template instanceof DamageEffect && !(template instanceof DamageOverTimeEffect)) {
                isDmgEffect = true;
            }
            if (template instanceof MoveBehindEffect) {
                moveBehindEffect = template;
            }
        }

        synchronized (sucessEffects) {
            for (EffectTemplate template : sucessEffects) {
                template.calculateSubEffect(this);
                template.calculateHate(this);
            }
        }

        if (isDmgEffect) {
            if (getAttackStatus() == AttackStatus.RESIST || getAttackStatus() == AttackStatus.DODGE) {
                sucessEffects.clear();
                if (moveBehindEffect != null) {
                    sucessEffects.add(moveBehindEffect);
                }
                return;
            } else {
                if (sucessEffects.size() != getEffectTemplates().size()) {
                    synchronized (sucessEffects) {
                        for (Iterator<EffectTemplate> it = sucessEffects.iterator(); it.hasNext();) {
                            EffectTemplate template = it.next();

                            if (template instanceof DamageEffect && !(template instanceof DamageOverTimeEffect))
                                continue;

                            it.remove();
                        }
                    }
                }
            }
        } else if (sucessEffects.size() != getEffectTemplates().size()) {
            sucessEffects.clear();
            if (getSkillType() == SkillType.MAGICAL)
                setAttackStatus(AttackStatus.RESIST);
            else
                setAttackStatus(AttackStatus.DODGE);
        }
    }

    /**
     * Apply all effect templates
     */
    public void applyEffect() {
        if (skillTemplate.getEffects() == null || sucessEffects.isEmpty())
            return;

        synchronized (sucessEffects) {
            for (EffectTemplate template : sucessEffects) {
                template.applyEffect(this);
                template.startSubEffect(this);
            }
        }

        /**
         * broadcast final hate to all visible objects
         */
        if (effectHate != 0)
            effector.getController().broadcastHate(effectHate);
    }

    /**
     * Start effect which includes:
     * - start effect defined in template
     * - start subeffect if possible
     * - activate toogle skill if needed
     * - schedule end of effect
     */
    public void startEffect(boolean restored) {
        this.isStopped = false;

        if (sucessEffects.isEmpty())
            return;

        synchronized (sucessEffects) {
            for (EffectTemplate template : sucessEffects) {
                template.startEffect(this);
            }
        }

        if (isToggle() && effector instanceof Player) {
            activateToggleSkill();
        }

        if (!restored)
            duration = getEffectsDuration();
        if (duration == 0)
            return;

        endTime = (int) System.currentTimeMillis() + duration;

        task = ThreadPoolManager.getInstance().scheduleEffect((new Runnable() {
            @Override
            public void run() {
                endEffect();
            }
        }), duration);

        if (effected instanceof Player)
            reapplyWeaponStats();
    }

    /**
     * Will activate toggle skill and start checking task
     */
    private void activateToggleSkill() {
        PacketSendUtility.sendPacket((Player) effector, new SM_SKILL_ACTIVATION(getSkillId(), true));
    }

    /**
     * Will deactivate toggle skill and stop checking task
     */
    private void deactivateToggleSkill() {
        PacketSendUtility.sendPacket((Player) effector, new SM_SKILL_ACTIVATION(getSkillId(), false));
    }

    /**
     * End effect and all effect actions
     * This method is synchronized and prevented to be called several times
     * which could cause unexpected behavior
     */
    public synchronized void endEffect() {
        if (isStopped)
            return;

        synchronized (sucessEffects) {
            for (EffectTemplate template : sucessEffects) {
                template.endEffect(this);
            }
        }

        if (isToggle() && effector instanceof Player) {
            deactivateToggleSkill();
        }
        stopTasks();
        effected.getEffectController().clearEffect(this);
        this.isStopped = true;
        this.addedToController = false;

        if (effected instanceof Player)
            reapplyWeaponStats();
    }

    /**
     * Stop all scheduled tasks
     */
    public void stopTasks() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }

        if (checkTask != null) {
            checkTask.cancel(true);
            checkTask = null;
        }

        if (periodicTasks != null) {
            for (Future<?> periodicTask : this.periodicTasks) {
                if (periodicTask != null) {
                    periodicTask.cancel(true);
                    periodicTask = null;
                }
            }
        }

        if (mpUseTask != null) {
            mpUseTask.cancel(true);
            mpUseTask = null;
        }

        if (hpUseTask != null) {
            hpUseTask.cancel(true);
            hpUseTask = null;
        }
    }

    /**
     * Time till the effect end
     *
     * @return
     */
    public int getElapsedTime() {
        int elapsedTime = endTime - (int) System.currentTimeMillis();
        return elapsedTime > 0 ? elapsedTime : 0;
    }

    /**
     * Time effect is active
     *
     * @return
     */
    public int getCurrentTime() {
        return duration - getElapsedTime();
    }

    /**
     * PVP damage ration
     *
     * @return
     */
    public int getPvpDamage() {
        return skillTemplate.getPvpDamage();
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    /**
     * Try to add this effect to effected controller
     */
    public void addToEffectedController() {
        if (!addedToController && effected != null && effected.getEffectController() != null) {
            effected.getEffectController().addEffect(this);
            addedToController = true;
        }
    }

	public int getReflectorSkillId(){
        return reflectorSkillId;
    }

    public void setReflectorSkillId(int reflectorSkillId){
        this.reflectorSkillId = reflectorSkillId;
    }
	
    /**
     * @return the effectHate
     */
    public int getEffectHate() {
        return effectHate;
    }

    /**
     * @param effectHate the effectHate to set
     */
    public void setEffectHate(int effectHate) {
        this.effectHate = effectHate;
    }

    /**
     * @return the tauntHate
     */
    public int getTauntHate() {
        return tauntHate;
    }

    /**
     * @param tauntHate the tauntHate to set
     */
    public void setTauntHate(int tauntHate) {
        this.tauntHate = tauntHate;
    }

    /**
     * @param i
     * @return actionObserver for this effect template
     */
    public ActionObserver getActionObserver(int i) {
	if (actionObserver == null)
		return null;
        return actionObserver[i - 1];
    }

    /**
     * @param observer the observer to set
     */
    public void setActionObserver(ActionObserver observer, int i) {
        if (actionObserver == null)
            actionObserver = new ActionObserver[4];
        actionObserver[i - 1] = observer;
    }

    public void addSucessEffect(EffectTemplate effect) {
        sucessEffects.add(effect);
    }

    /**
     * @return
     */
    public Collection<EffectTemplate> getSuccessEffect() {
        return sucessEffects;
    }

    public void addAllEffectToSucess() {
        for (EffectTemplate template : getEffectTemplates()) {
            sucessEffects.add(template);
        }
    }

    public int getEffectsDuration() {
        int duration = 0;

        synchronized (sucessEffects) {
            for (EffectTemplate template : sucessEffects) {
                int effectDuration = template.getDuration();
                if (template.getRandomTime() > 0)
                    effectDuration -= Rnd.get(template.getRandomTime());
                duration = duration > effectDuration ? duration : effectDuration;
            }
        }
        if (effected instanceof Player && skillTemplate.getPvpDuration() != 0)
            duration = duration * skillTemplate.getPvpDuration() / 100;
        return duration;
    }

    public boolean isStance() {
        return skillTemplate.isStance();
    }

    public boolean isAvatar() {
        for (EffectTemplate et : skillTemplate.getEffects().getEffects()) {
            if (et instanceof TransformEffect && ((TransformEffect) et).getTransformType() == TransformType.AVATAR)
                return true;
        }
        return false;
    }

    public TransformType getTransformType() {
        for (EffectTemplate et : skillTemplate.getEffects().getEffects()) {
            if (et instanceof TransformEffect)
                return ((TransformEffect) et).getTransformType();
        }
        return null;
    }

    public int getLaunchSkillId() {
        for (EffectTemplate et : getEffectTemplates()) {
            if (et instanceof SkillLauncherEffect)
                return ((SkillLauncherEffect) et).getLaunchSkillId();
        }
        return 0;
    }

	public int getReflectorDamage(){
        return reflectedDamage;
    }

    public void setReflectorDamage(int reflectedDamage){
        this.reflectedDamage = reflectedDamage;
    }


    /**
     * Reapply weapon stats, dual-wielding only
     */
    public void reapplyWeaponStats() {
        Player player = (Player) effected;
        WeaponType mainWeaponType = player.getEquipment().getMainHandWeaponType();
        WeaponType subWeaponType = player.getEquipment().getOffHandWeaponType();
        if (mainWeaponType != null && subWeaponType != null) {
            Integer mainSkillId = player.getSkillList().getWeaponMasterySkill(mainWeaponType);
            Integer subSkillId = player.getSkillList().getWeaponMasterySkill(subWeaponType);
            // check to avoid stackoverflow error
            if (getSkillId() != mainSkillId && getSkillId() != subSkillId)
                ItemEquipmentListener.recalculateWeaponMastery(player);
        }
	}
	
    // XP Boost and Artifact Skills will not removed after player die
    public boolean isXPBoostSkill()
    {
	switch (getSkillId())
	{
	case 9881:
	case 9982:
	case 10033:
	case 10249:
	case 10280:
	case 10330:
	case 10335:
	case 10354:
	case 10355:
	case 10366:
	case 10367:
	case 10368:
	    return true;
	}
	return false;
    }
    public boolean isArtifactSkill()
    {
	switch (getSkillId())
	{
	case 12074:
	case 12106:
	case 12103:
	case 12076:
	case 12078:
	case 12072:
	case 12075:
	case 12073:
	case 12077:
	case 12112:
	case 12071:
	case 12070:
	case 12109:
	    return true;
	}
	return false;
    }
	
	public void setDashParam(DashParam dashParam){
		this.dashType = dashParam;
	}
	public DashParam getDashParam()	{
		return this.dashType;
	}
}
