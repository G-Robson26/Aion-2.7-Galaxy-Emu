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
package gameserver.model.group;

import com.aionemu.commons.objects.filter.ObjectFilter;
import gameserver.configs.main.GroupConfig;
import gameserver.model.gameobjects.AionObject;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.serverpackets.SM_GROUP;
import gameserver.network.aion.serverpackets.SM_GROUP_INFO;
import gameserver.network.aion.serverpackets.SM_GROUP_MEMBER_INFO;
import gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.utils.MathUtil;
import gameserver.utils.PacketSendUtility;
import javolution.util.FastMap;

import java.util.Collection;

/**
 * @author ATracer, Lyahim, Simple
 * @author Jego
 */
public class PlayerGroup extends AionObject {
    private LootGroupRules lootGroupRules = new LootGroupRules();

    private Player groupLeader;

    private FastMap<Integer, Player> groupMembers = new FastMap<Integer, Player>().shared();
	
	private FastMap<Integer, Player> groupMentors = new FastMap<Integer, Player>();
	private FastMap<Integer, Player> groupMentees = new FastMap<Integer, Player>();
	private boolean	Mentor = false;

	
    private int RoundRobinNr = 0;
    
    private int instancePoints = 0;
	private int	instanceKills = 0;
	private long instanceStartTime	= 0;
	private boolean	instanceDisplaycounter	= true;
	private int arenaStageNumber;
	private int arenaRoundNumber;
	private long academyTime;
	private int arenaPoolNumber;

    /**
     * Instantiates new player group with unique groupId
     *
     * @param groupId
     */
    public PlayerGroup(int groupId, Player groupleader) {
        super(groupId);
        this.groupMembers.put(groupleader.getObjectId(), groupleader);
        this.setGroupLeader(groupleader);
        groupleader.setPlayerGroup(this);
		arenaStageNumber = 0;
		arenaRoundNumber = 0;
        PacketSendUtility.sendPacket(groupLeader, new SM_GROUP_INFO(this));
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return this.getObjectId();
    }

    /**
     * @return the groupLeader
     */
    public Player getGroupLeader() {
        return groupLeader;
    }

    /**
     * Used to set group leader
     *
     * @param groupLeader the groupLeader to set
     */
    public void setGroupLeader(Player groupLeader) {
        this.groupLeader = groupLeader;
    }

    /**
     * Adds player to group
     *
     * @param newComer
     */
    public void addPlayerToGroup(Player newComer) {
        groupMembers.put(newComer.getObjectId(), newComer);
        newComer.setPlayerGroup(this);
        PacketSendUtility.sendPacket(newComer, new SM_GROUP_INFO(this));
        updateGroupUIToEvent(newComer, GroupEvent.ENTER);
    }

    /**
     * This method will return a round robin group member.
     *
     * @param npc The killed Npc
     * @return memberObjId or 0 if the selected player isn't in range.
     */
    public int getRoundRobinMember(Npc npc) {
        if (size() == 0)
            return 0;

        RoundRobinNr = ++RoundRobinNr % size();
        int i = 0;
        for (Player player : getMembers()) {
            if (i == RoundRobinNr) {
                if (MathUtil.isIn3dRange(player, npc, GroupConfig.GROUP_MAX_DISTANCE)) { // the player is in range of the killed NPC.
                    return player.getObjectId();
                } else {
                    return 0;
                }
            }
            i++;
        }
        return 0;
    }

    /**
     * Removes player from group
     *
     * @param player
     */
    public void removePlayerFromGroup(Player player) {
        this.groupMembers.remove(player.getObjectId());
        player.setPlayerGroup(null);
        updateGroupUIToEvent(player, GroupEvent.LEAVE);

        /**
         * Inform all group members player has left the group
         */
        PacketSendUtility.broadcastPacket(player, new SM_LEAVE_GROUP_MEMBER(), true, new ObjectFilter<Player>() {
            @Override
            public boolean acceptObject(Player object) {
                return object.getPlayerGroup() == null ? true : false;
            }
        });
	if (isMentoring(player))
		endMentoring(player);
	else
		checkEndMentoring();	
		
    }

    public void disband() {
        this.groupMembers.clear();
    }

    public void onGroupMemberLogIn(Player player) {
        groupMembers.remove(player.getObjectId());
        groupMembers.put(player.getObjectId(), player);
    }

    /**
     * Checks whether group is full
     *
     * @return true or false
     */
    public boolean isFull() {
        return groupMembers.size() == 6;
    }

    public Collection<Player> getMembers() {
        return groupMembers.values();
    }

	public Collection<Player> getMentors() {
		return groupMentors.values();
	}
	
    public Collection<Integer> getMemberObjIds() {
        return groupMembers.keySet();
    }

    /**
     * @return count of group members
     */
    public int size() {
        return groupMembers.size();
    }

    /**
     * @return the lootGroupRules
     */
    public LootGroupRules getLootGroupRules() {
        return lootGroupRules;
    }

    public void setLootGroupRules(LootGroupRules lgr) {
        this.lootGroupRules = lgr;
        for (Player member : groupMembers.values())
            PacketSendUtility.sendPacket(member, new SM_GROUP_INFO(this));
    }

    /**
     * Update the Client user interface with the newer data
     */
    // TODO: Move to GroupService
	public void updateGroupUIToEvent(Player subjective, GroupEvent groupEvent)	{
		switch(groupEvent)	{
			case CHANGELEADER:
			{
				for(Player member : this.getMembers())
				{
					PacketSendUtility.sendPacket(member, new SM_GROUP_INFO(this));
					if(subjective.equals(member))
						PacketSendUtility.sendPacket(member, SM_SYSTEM_MESSAGE.CHANGE_GROUP_LEADER());
					PacketSendUtility.sendPacket(member, new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
				}
			}
				break;
			case LEAVE:
			{
				boolean changeleader = false;
				if(subjective == this.getGroupLeader())// change group leader
				{
					this.setGroupLeader(this.getMembers().iterator().next());
					changeleader = true;
				}
				for(Player member : this.getMembers())
				{
					if(changeleader)
					{
						PacketSendUtility.sendPacket(member, new SM_GROUP_INFO(this));
						PacketSendUtility.sendPacket(member, SM_SYSTEM_MESSAGE.CHANGE_GROUP_LEADER());
					}
					if(!subjective.equals(member))
						PacketSendUtility.sendPacket(member, new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
					if(this.size() > 1)
						PacketSendUtility.sendPacket(member, SM_SYSTEM_MESSAGE.MEMBER_LEFT_GROUP(subjective.getName()));
				}
				eventToSubjective(subjective, GroupEvent.LEAVE);
			}
				break;
			case ENTER:
			{
				eventToSubjective(subjective, GroupEvent.ENTER);
				for(Player member : this.getMembers())
				{
					if(!subjective.equals(member))
					{
						PacketSendUtility.sendPacket(member, new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
					}
				}
			}
				break;
			default:
			{
				for(Player member : this.getMembers())
				{
					if(!subjective.equals(member))
						PacketSendUtility.sendPacket(member, new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
				}
			}
				break;
		}
	}

    // TODO: Move to GroupService

    private void eventToSubjective(Player subjective, GroupEvent groupEvent) {
        for (Player member : getMembers()) {
            if (!subjective.equals(member))
                PacketSendUtility.sendPacket(subjective, new SM_GROUP_MEMBER_INFO(this, member, groupEvent));
        }
        if (groupEvent == GroupEvent.LEAVE)
            PacketSendUtility.sendPacket(subjective, SM_SYSTEM_MESSAGE.YOU_LEFT_GROUP());
    }


    public void setGroupInstancePoints(int points) {
        instancePoints = points;
    }

    public int getGroupInstancePoints() {
        return instancePoints;
    }

    public void setInstanceStartTimeNow() {
        instanceStartTime = System.currentTimeMillis();
    }

    public long getInstanceStartTime() {
        return instanceStartTime;
    }

	public void setInstanceKills(int kills)	{
		instanceKills = kills;
	}

	public int getInstanceKills() {
		return instanceKills;
	}
	
	
	public boolean isMentoring(Player player)	{
		return groupMentors.containsKey(player.getObjectId());
	}

	/*
	* academy
	*/
	public void setGroupArenaStage(int i)  {
        arenaStageNumber = i;
    }
	
	public int getGroupArenaStage() {
        return arenaStageNumber;
    }
	
	public void setGroupArenaRound(int i) {
        arenaRoundNumber = i;
    }
	
	public void setAcademyTime(long l) {
        academyTime = l;
    }
 
    public long getAcademyTime() {
        return academyTime;
    }
	public void setGroupArenaPool(int i) {
        arenaPoolNumber = i;
    }
	public int getGroupArenaPool() {
        return arenaPoolNumber;
    }
	public int getGroupArenaRound() {
        return arenaRoundNumber;
    }
	
	public void startMentoring(Player player)	{
		if (!isMentoring(player))
			groupMentors.put(player.getObjectId(), player);
		else {
			endMentoring(player);
			return;
		}

		PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_START());
		setMentor(true);
		for (Player groupMember : this.getMembers())	{
			if (groupMember != player){
				if (!isMenteering(groupMember)){
					groupMentees.put(groupMember.getObjectId(), groupMember);
					PacketSendUtility.sendPacket(groupMember, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_START_PARTYMSG(player.getName()));
				}
			}
			PacketSendUtility.sendPacket(groupMember, new SM_GROUP_MEMBER_INFO(this, player, GroupEvent.UPDATE));
		}
	}

	public void endMentoring(Player player)	{
		groupMentors.remove(player.getObjectId());

		PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_END());
		setMentor(false);
		for (Player groupMember : this.getMembers()) {
			if (isMentoring(groupMember)){
				groupMentors.remove(groupMember.getObjectId());
				PacketSendUtility.sendPacket(groupMember, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_END());
			} else if (isMenteering(groupMember)){
				groupMentees.remove(groupMember.getObjectId());
 				PacketSendUtility.sendPacket(groupMember, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_END_PARTYMSG(player.getName()));
			}
			PacketSendUtility.sendPacket(groupMember, new SM_GROUP_MEMBER_INFO(this, player, GroupEvent.UPDATE));
		}
	}

	private void checkEndMentoring() {
		int lowestLevel = 0;
		for (Player member : this.getMembers())	{
			if (lowestLevel == 0)
				lowestLevel = member.getLevel();
			else if (member.getLevel() < lowestLevel)
				lowestLevel = member.getLevel();
		}
		for (Player mentor : this.getMentors())	{
			if (lowestLevel > mentor.getLevel() - 10)		{
				endMentoring(mentor);
				if (this.size() < 2)
					PacketSendUtility.sendPacket(mentor, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_PARTY_END_BY_LEAVE_ALL_MENTEE());
				else
					PacketSendUtility.sendPacket(mentor, SM_SYSTEM_MESSAGE.STR_MSG_CANT_BE_MENTOR_BY_LEVEL_LIMIT());
			}
		}
	}	
	
    @Override
    public String getName() {
        return "Player Group";
    }
	
	public Collection<Player> getMentees()	{
		return groupMentees.values();
	}
	
	public boolean getMentor() {
		return Mentor;
	}
 
	public void setMentor(boolean chek)	{
		Mentor = chek;
	}
	
	public boolean isMenteering(Player player) {
		return groupMentees.containsKey(player.getObjectId());
	}
}