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
package gameserver.ai.desires.impl;

import gameserver.ai.AI;
import gameserver.ai.desires.AbstractDesire;
import gameserver.dataholders.DataManager;
import gameserver.model.ShoutEventType;
import gameserver.model.gameobjects.Creature;
import gameserver.model.gameobjects.Monster;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.stats.NpcLifeStats;
import gameserver.model.templates.npcskill.NpcSkillList;
import gameserver.model.templates.npcskill.NpcSkillTemplate;
import gameserver.model.templates.npcskill.NpcUniqueSkillTemplate;
import gameserver.services.NpcShoutsService;
import gameserver.skillengine.SkillEngine;
import gameserver.skillengine.effect.BackDashEffect;
import gameserver.skillengine.effect.EffectTemplate;
import gameserver.skillengine.effect.FpAttackEffect;
import gameserver.skillengine.effect.FpAttackInstantEffect;
import gameserver.skillengine.effect.PulledEffect;
import gameserver.skillengine.effect.RandomMoveLocEffect;
import gameserver.skillengine.effect.StaggerEffect;
import gameserver.skillengine.model.Skill;
import gameserver.skillengine.model.SkillTemplate;
import gameserver.utils.ThreadPoolManager;
import gameserver.utils.exceptionhandlers.exception_enums;

import java.util.List;

import org.apache.log4j.Logger;

import com.aionemu.commons.utils.Rnd;

/**
 * @author ATracer
 * 
 */
public class SkillUseDesire extends AbstractDesire
{
    
    private static final Logger log = Logger.getLogger(SkillUseDesire.class);

	protected Creature		owner;
	private NpcSkillList	skillList;
	private boolean			InitialSkillCasted	= false;

	/**
	 * @param owner
	 * @param desirePower
	 */
	public SkillUseDesire(Creature owner, int desirePower)
	{
		super(desirePower);
		this.owner = owner;
		this.skillList = ((Npc) owner).getNpcSkillList();
	}

	@Override
	public boolean handleDesire(AI<?> ai)
	{
		if(owner.isCasting())
			return true;

		List<NpcSkillTemplate> skills = skillList.getNpcSkills();
		List<NpcUniqueSkillTemplate> uniqueSkills = skillList.getNpcUniqueSkills();
		NpcSkillTemplate npcSkill = skills.get(Rnd.get(0, skills.size() - 1));

		// Temporary hack to disable GeoData and KnockBack/Stagger skills from NPCs
		// While we're there, check for pull or crash effects
		boolean acceptedSkill = false;
		boolean initialForcedSkill = false;
		int initialForcedSkillID = 0;
		int initialForcedSkillLvl = 0;
		for(int i = 0; i < 10; i++)
		{
			SkillTemplate sT = DataManager.SKILL_DATA.getSkillTemplate(npcSkill.getSkillid());
			if(sT != null && sT.getEffects() != null)
			{
				boolean hasDangerous = false;
				for(EffectTemplate e : sT.getEffects().getEffects())
				{
					if((e instanceof BackDashEffect || e instanceof RandomMoveLocEffect || e instanceof StaggerEffect))
						hasDangerous = true;
					if(e instanceof PulledEffect || e instanceof FpAttackEffect || e instanceof FpAttackEffect
						|| e instanceof FpAttackInstantEffect)
					{
						initialForcedSkill = true;
						initialForcedSkillID = sT.getSkillId();
						initialForcedSkillLvl = sT.getLvl();
					}
				}
				if(!hasDangerous)
				{
					acceptedSkill = true;
					break;
				}
			}
			npcSkill = skills.get(Rnd.get(0, skills.size() - 1));
		}
		if(!acceptedSkill)
			return false;

		/**
		 * Cast initial skill once
		 */
		if(initialForcedSkill == true && InitialSkillCasted == false)
		{
			Skill initialskill = SkillEngine.getInstance().getSkill(owner, initialForcedSkillID, initialForcedSkillLvl,
				owner.getTarget());
			initialskill.useSkill();
			InitialSkillCasted = true;
		}
		
		//Case 1: If NPC has unique skills
        if(uniqueSkills != null && uniqueSkills.size() > 0) {
            for(NpcUniqueSkillTemplate uSkill : uniqueSkills) {
                if(!owner.hasCastedUniqueSkill(uSkill.getSkillid())) {
                    NpcLifeStats nls = (NpcLifeStats)owner.getLifeStats();
                    if(nls.getHpPercentage() <= uSkill.getHPPercent()) {
                        Skill skill = SkillEngine.getInstance().getSkill(owner, uSkill.getSkillid(), uSkill.getSkillLevel(),owner.getTarget());
                        if(skill != null) {
                            owner.setCastedUniqueSkill(uSkill.getSkillid());
                            skill.useSkill();
                            return true;
                        }
                        else  {
                            log.error("npc #" + owner.getName() + " cannot cast unique skill #" + uSkill.getSkillid() + " : no such skill template");
                        }
                    }
                }
            }
        }
        //Case 2: If NPC dont has unique skills, make classic skills system     
        if(skills != null && skills.size() > 0) {
            for(NpcSkillTemplate template : skills) {
                // If skill isAboutHp
                if(template.isAboutHp()) {                    
                    NpcLifeStats nls = (NpcLifeStats)owner.getLifeStats();
                    int minHpPercent = template.getMinHp();
                    int maxHpPercent = template.getMaxHp();
                    
                    if(nls.getHpPercentage() >= minHpPercent && nls.getHpPercentage() <= maxHpPercent) {
                        if(Rnd.get(0, 100) < template.getProbability()) {
                            Skill skill = SkillEngine.getInstance().getSkill(owner, template.getSkillid(), template.getSkillLevel(), owner.getTarget());
                            if(skill != null) {
                                if(owner instanceof Npc && owner.getTarget() instanceof Creature) {
                                    if(skill.getSkillTemplate() != null && skill.getSkillTemplate().getDuration() > 0)
                                        NpcShoutsService.getInstance().handleEvent((Npc) owner, (Creature) owner.getTarget(), ShoutEventType.CAST);
                                    else if(skill.getSkillTemplate() != null)
                                        NpcShoutsService.getInstance().handleEvent((Npc) owner, (Creature) owner.getTarget(), ShoutEventType.SKILL);
                                }
                                skill.useSkill();
                            }
                            return true;                            
                        }
                    }
                }
                else {
                    if(Rnd.get(0, 100) < template.getProbability()) {
                        Skill skill = SkillEngine.getInstance().getSkill(owner, template.getSkillid(), template.getSkillLevel(), owner.getTarget());
                        if(skill != null) {
                            if(owner instanceof Npc && owner.getTarget() instanceof Creature) {
                                if(skill.getSkillTemplate() != null && skill.getSkillTemplate().getDuration() > 0)
                                    NpcShoutsService.getInstance().handleEvent((Npc) owner, (Creature) owner.getTarget(), ShoutEventType.CAST);
                                else if(skill.getSkillTemplate() != null)
                                    NpcShoutsService.getInstance().handleEvent((Npc) owner, (Creature) owner.getTarget(), ShoutEventType.SKILL);
                            }
                            skill.useSkill();                                
                        }
                        // Iron Explosive Mine
                        // TODO : specific controller needed, there is much more than one npc id for mines.
                        if(owner != null && owner instanceof Monster && ((Monster) owner).getNpcId() == exception_enums.NPC_SIEGE_MINE_I) {
                            ThreadPoolManager.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    owner.getController().die();
                                }
                            }, 500);
                        }
                        return true;
                    }
                }
            }
        }
		return true;
	}

	@Override
	public void onClear()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public int getExecutionInterval()
	{
		return 1;
	}
}