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
 **/

package gameserver.network.aion.serverpackets;

import gameserver.model.DescriptionId;
import gameserver.model.EmotionType;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;
import gameserver.network.aion.SystemMessageId;

import java.nio.ByteBuffer;

/**
 * System message packet.
 *
 * @author -Nemesiss-
 * @author EvilSpirit
 * @author Luno :D
 * @author Avol!
 * @author Simple :)
 * @author Sarynth
 * @author Jego
 */
public class SM_SYSTEM_MESSAGE extends AionServerPacket {
    /**
     * Coordinates of current location: %WORLDNAME0 Region, X=%1 Y=%2 Z=%3
     *
     * @param worldId id of the world
     * @param x       x coordinate
     * @param y       y coordinate
     * @param z       z coordinate
     * @return Message instance.
     */
    public static SM_SYSTEM_MESSAGE CURRENT_LOCATION(int worldId, float x, float y, float z) {
        return new SM_SYSTEM_MESSAGE(230038, worldId, x, y, z);
    }

    /**
     * Busy in game
     */
    public static final SM_SYSTEM_MESSAGE BUDDYLIST_BUSY = new SM_SYSTEM_MESSAGE(900847);

    /**
     * %0 is not playing the game
     *
     * @param playerName Player name.
     * @return Message instance.
     */
    public static SM_SYSTEM_MESSAGE PLAYER_IS_OFFLINE(String playerName) {
        return new SM_SYSTEM_MESSAGE(1300627, playerName);
    }

    /**
     * You used item
     */
    public static SM_SYSTEM_MESSAGE USE_ITEM(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300423, itemDescId);
    }
	
	/**
	 * You cannot use this item here
	 */
	public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_ITEM_INVALID_LOCATION(DescriptionId itemDescId)
	{
		return new SM_SYSTEM_MESSAGE(1300426, itemDescId);
	}	
	
	

    public static SM_SYSTEM_MESSAGE REQUEST_TRADE(String playerName) {
        return new SM_SYSTEM_MESSAGE(1300353, playerName);
    }

    /**
     * You are dead
     */
    public static SM_SYSTEM_MESSAGE DIE = new SM_SYSTEM_MESSAGE(1340000);

    /**
     *
     */
    public static SM_SYSTEM_MESSAGE REVIVE = new SM_SYSTEM_MESSAGE(1300738);

    public static final SM_SYSTEM_MESSAGE    STR_SKILL_OBSTACLE    = new SM_SYSTEM_MESSAGE(1300030);
    public static final SM_SYSTEM_MESSAGE    STR_ATTACK_OBSTACLE_EXIST    = new SM_SYSTEM_MESSAGE(1300033);

    /**
     *
     */
    public static SM_SYSTEM_MESSAGE EXP(String _exp) {
        return new SM_SYSTEM_MESSAGE(1370002, _exp);
    }

    /**
     * You have gained %num1 EXP from %0.
     */
    public static SM_SYSTEM_MESSAGE EXP(long exp, int objectNameId) {
        return new SM_SYSTEM_MESSAGE(1370000, new DescriptionId(objectNameId * 2 + 1), exp);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(String nameA, String nameB) {
        return new SM_SYSTEM_MESSAGE(1350001, nameA, nameB);
    }
    
    /**
     * Gather-related
     */

    public static SM_SYSTEM_MESSAGE GATHER_SKILL_POINT_UP(String skillName, int newLevel) {
        return new SM_SYSTEM_MESSAGE(1330005, skillName, newLevel);
    }

    public static SM_SYSTEM_MESSAGE GATHER_SUCCESS_GETEXP() {
        return new SM_SYSTEM_MESSAGE(1330058);
    }

    public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_START_1_BASIC(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1330077, nameId);
    }

    public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_SUCCESS_1_BASIC(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1330078, nameId);
    }

    public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_FAIL_1_BASIC(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1330079, nameId);
    }

    public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_CANCEL_1_BASIC() {
        return new SM_SYSTEM_MESSAGE(1330080);
    }

    public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_INVENTORY_IS_FULL() {
        return new SM_SYSTEM_MESSAGE(1330081);
    }

    public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_SUCCESS_GETEXP() {
        return new SM_SYSTEM_MESSAGE(1330082);
    }

    /**
     * Your Requested player to trade
     */
    public static final SM_SYSTEM_MESSAGE REQUEST_TRADE = new SM_SYSTEM_MESSAGE(1300353);

    /**
     * Your Friends List is full
     */
    public static final SM_SYSTEM_MESSAGE BUDDYLIST_LIST_FULL = new SM_SYSTEM_MESSAGE(1300887);

    /**
     * The character is not on your Friends List.
     */
    public static final SM_SYSTEM_MESSAGE BUDDYLIST_NOT_IN_LIST = new SM_SYSTEM_MESSAGE(1300889);

    /**
     * The server is due to shut down in %0 seconds. Please quit the game.
     */
    public static SM_SYSTEM_MESSAGE SERVER_SHUTDOWN(int seconds) {
        return new SM_SYSTEM_MESSAGE(1300642, Integer.toString(seconds));
    }

    /**
     * You cannot block a character who is currently on your Friends List.
     */
    public static SM_SYSTEM_MESSAGE BLOCKLIST_NO_BUDDY = new SM_SYSTEM_MESSAGE(1300891);

    /**
     * Character already in block list
     */
    public static SM_SYSTEM_MESSAGE BLOCKLIST_ALREADY_BLOCKED = new SM_SYSTEM_MESSAGE(1300894);

    /**
     * The character is not blocked.
     */
    public static SM_SYSTEM_MESSAGE BLOCKLIST_NOT_BLOCKED = new SM_SYSTEM_MESSAGE(1300897);

    /**
     * You must level up to raise your skill level.
     */
    public static SM_SYSTEM_MESSAGE STR_CRAFT_INFO_MAXPOINT_UP = new SM_SYSTEM_MESSAGE(1300898);

    /**
     * You do not have enough Kinah.
     */
    public static SM_SYSTEM_MESSAGE STR_NOT_ENOUGH_MONEY = new SM_SYSTEM_MESSAGE(1300388);

    /**
     * You have crafted %0.
     */

    public static SM_SYSTEM_MESSAGE STR_COMBINE_SUCCESS(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1330049, nameId);
    }

    /**
     * You have crafted successfully.
     */
    public static SM_SYSTEM_MESSAGE STR_CRAFT_SUCCESS_GETEXP = new SM_SYSTEM_MESSAGE(1330059);

    /**
     * You have failed to craft %0.
     */
    public static SM_SYSTEM_MESSAGE STR_COMBINE_FAIL(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1330050, nameId);
    }

    /**
     * %0 has blocked you.
     */
    public static SM_SYSTEM_MESSAGE YOU_ARE_BLOCKED_BY(String blocker) {
        return new SM_SYSTEM_MESSAGE(1300628, blocker);
    }

    /**
     * %0 has challenged you to a duel.
     */
    public static SM_SYSTEM_MESSAGE DUEL_ASKED_BY(String player) {
        return new SM_SYSTEM_MESSAGE(1301065, player);
    }

    /**
     * You challenged %0 to a duel.
     */
    public static SM_SYSTEM_MESSAGE DUEL_ASKED_TO(String player) {
        return new SM_SYSTEM_MESSAGE(1300094, player);
    }

    /**
     * %0 rejects your duel request
     */
    public static SM_SYSTEM_MESSAGE DUEL_REJECTED_BY(String player) {
        return new SM_SYSTEM_MESSAGE(1300097, player);
    }

    /**
     * You won the duel against %0.
     */
    public static SM_SYSTEM_MESSAGE DUEL_YOU_WON_AGAINST(String player) {
        return new SM_SYSTEM_MESSAGE(1300098, player);
    }

    /**
     * You lost the duel against %0.
     */
    public static SM_SYSTEM_MESSAGE DUEL_YOU_LOST_AGAINST(String player) {
        return new SM_SYSTEM_MESSAGE(1300099, player);
    }

    public static SM_SYSTEM_MESSAGE DUEL_START = new SM_SYSTEM_MESSAGE(1300770);

    public static SM_SYSTEM_MESSAGE DUEL_END = new SM_SYSTEM_MESSAGE(1300771);

    /**
     * Starting the duel with %0.
     */
    public static SM_SYSTEM_MESSAGE DUEL_STARTING_WITH(String player) {
        return new SM_SYSTEM_MESSAGE(1300777, player);
    }

    /**
     * You declined %0's challenge for a duel.
     */
    public static SM_SYSTEM_MESSAGE DUEL_REJECT_DUEL_OF(String player) {
        return new SM_SYSTEM_MESSAGE(1301064, player);
    }

    /**
     * %0 has withdrawn the challenge for a duel.
     */
    public static SM_SYSTEM_MESSAGE DUEL_CANCEL_DUEL_BY(String player) {
        return new SM_SYSTEM_MESSAGE(1300134, player);
    }

    /**
     * You have withdrawn the challenge to %0 for a duel.
     */
    public static SM_SYSTEM_MESSAGE DUEL_CANCEL_DUEL_WITH(String player) {
        return new SM_SYSTEM_MESSAGE(1300135, player);
    }

    /**
     * You cannot duel with %0.
     */
    public static SM_SYSTEM_MESSAGE DUEL_PARTNER_INVALID(String partner) {
        return new SM_SYSTEM_MESSAGE(1300091, partner);
    }

    /**
     * %0 has been kicked out of the arena.
     */
    public static SM_SYSTEM_MESSAGE STR_PvPZONE_OUT_MESSAGE(String player) {
        return new SM_SYSTEM_MESSAGE(1301031, player);
    }

    /**
     * %0 has defeated %1
     */
    public static SM_SYSTEM_MESSAGE STR_KILLMSG(String name1, String name2) {
        return new SM_SYSTEM_MESSAGE(1300739, name1, name2);
    }

    /**
     * You have successfully soul-bound %0.
     */
    public static SM_SYSTEM_MESSAGE SOUL_BOUND_ITEM_SUCCEED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300485, itemDescId);
    }

    /**
     * You canceled the soul-binding of %0.
     */
    public static SM_SYSTEM_MESSAGE SOUL_BOUND_ITEM_CANCELED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300487, itemDescId);
    }

    /**
     * Soul-binding of items not possible while %0.
     */
    public static SM_SYSTEM_MESSAGE STR_SOUL_BOUND_INVALID_STANCE(int systemMessageId) {
        return new SM_SYSTEM_MESSAGE(1300489, new DescriptionId(systemMessageId * 2 + 1));
    }

    /*
      * You cannot attack because you have no arrow.
      */
    public static SM_SYSTEM_MESSAGE STR_CANT_ATTACK_NO_ARROW = new SM_SYSTEM_MESSAGE(1300397);

    /**
     * Group System Messages
     */
    public static SM_SYSTEM_MESSAGE STR_MSG_DICE_RESULT_ME(int dice) {
        return new SM_SYSTEM_MESSAGE(1390162, dice);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_DICE_RESULT_OTHER(String player, int dice) {
        return new SM_SYSTEM_MESSAGE(1390163, player, dice);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_DICE_GIVEUP_ME() {
        return new SM_SYSTEM_MESSAGE(1390164);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_DICE_GIVEUP_OTHER(String player) {
        return new SM_SYSTEM_MESSAGE(1390165, player);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_GET_ITEM_ME(DescriptionId itemDesc) {
        return new SM_SYSTEM_MESSAGE(1390180, itemDesc);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_GET_ITEM_OTHER(String player, DescriptionId itemDesc) {
        return new SM_SYSTEM_MESSAGE(1390181, player, itemDesc);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_PAY_ACCOUNT_ME(long kinah) {
        return new SM_SYSTEM_MESSAGE(1390185, kinah);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_PAY_ACCOUNT_OTHER(String player, long kinah) {
        return new SM_SYSTEM_MESSAGE(1390186, player, kinah);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_PAY_DISTRIBUTE(long kinah, int count, long Kinah) {
        return new SM_SYSTEM_MESSAGE(1390187, kinah, count, Kinah);
    }

    public static SM_SYSTEM_MESSAGE REQUEST_GROUP_INVITE(String player) {
        return new SM_SYSTEM_MESSAGE(1300173, player);
    }

    public static SM_SYSTEM_MESSAGE PARTY_HE_BECOME_OFFLINE(String player) {
        return new SM_SYSTEM_MESSAGE(1300175, player);
    }

    public static SM_SYSTEM_MESSAGE REJECT_GROUP_INVITE(String player) {
        return new SM_SYSTEM_MESSAGE(1300161, player);
    }

    public static SM_SYSTEM_MESSAGE PLAYER_IN_ANOTHER_GROUP(String player) {
        return new SM_SYSTEM_MESSAGE(1300169, player);
    }

    public static SM_SYSTEM_MESSAGE INVITED_PLAYER_OFFLINE() {
        return new SM_SYSTEM_MESSAGE(1300159);
    }

    public static SM_SYSTEM_MESSAGE MEMBER_LEFT_GROUP(String player) {
        return new SM_SYSTEM_MESSAGE(1300168, player);
    }

    public static SM_SYSTEM_MESSAGE DISBAND_GROUP() {
        return new SM_SYSTEM_MESSAGE(1300167);
    }

    public static SM_SYSTEM_MESSAGE YOU_LEFT_GROUP() {
        return new SM_SYSTEM_MESSAGE(1300043);
    }

    public static SM_SYSTEM_MESSAGE SELECTED_TARGET_DEAD() {
        return new SM_SYSTEM_MESSAGE(1300044);
    }

    public static SM_SYSTEM_MESSAGE DURING_FLYING_PATH_NOT_LEFT_GROUP() {
        return new SM_SYSTEM_MESSAGE(1300047);
    }

    public static SM_SYSTEM_MESSAGE FULL_GROUP() {
        return new SM_SYSTEM_MESSAGE(1300152);
    }

    public static SM_SYSTEM_MESSAGE CHANGE_GROUP_LEADER() {
        return new SM_SYSTEM_MESSAGE(1300155);
    }

    public static SM_SYSTEM_MESSAGE ONLY_GROUP_LEADER_CAN_INVITE() {
        return new SM_SYSTEM_MESSAGE(1300160);
    }

    public static SM_SYSTEM_MESSAGE CANNOT_INVITE_YOURSELF() {
        return new SM_SYSTEM_MESSAGE(1300162);
    }

    public static SM_SYSTEM_MESSAGE CANNOT_INVITE_BECAUSE_YOU_DEAD() {
        return new SM_SYSTEM_MESSAGE(1300163);
    }

    public static SM_SYSTEM_MESSAGE INVITED_ANOTHER_GROUP_MEMBER(String player) {
        return new SM_SYSTEM_MESSAGE(1300169);
    }

    public static SM_SYSTEM_MESSAGE INVITED_YOUR_GROUP_MEMBER(String player) {
        return new SM_SYSTEM_MESSAGE(1300170);
    }

    public static SM_SYSTEM_MESSAGE STR_PARTY_CANT_INVITE_OTHER_RACE() {
        return new SM_SYSTEM_MESSAGE(1300188);
    }

    public static SM_SYSTEM_MESSAGE LEVEL_NOT_ENOUGH_FOR_SEARCH(String level) {
        return new SM_SYSTEM_MESSAGE(1400341, level);
    }

    public static SM_SYSTEM_MESSAGE LEVEL_NOT_ENOUGH_FOR_WHISPER(String level) {
        return new SM_SYSTEM_MESSAGE(1310004, level);
    }

    public static SM_SYSTEM_MESSAGE SOUL_HEALED() {
        return new SM_SYSTEM_MESSAGE(1300674);
    }

    public static SM_SYSTEM_MESSAGE DONT_HAVE_RECOVERED_EXP() {
        return new SM_SYSTEM_MESSAGE(1300682);
    }
    
    public static SM_SYSTEM_MESSAGE STR_EXCHANGE_PARTNER_IS_EXCHANGING_WITH_OTHER() {
        return new SM_SYSTEM_MESSAGE(1300355);
    }

	/**
	 * Mentor Messages
	 */
	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_START()	{
		return new SM_SYSTEM_MESSAGE(1400762);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_START_PARTYMSG(String name) {
		return new SM_SYSTEM_MESSAGE(1400763, name);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_END() {
		return new SM_SYSTEM_MESSAGE(1400764);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_END_PARTYMSG(String name) {
		return new SM_SYSTEM_MESSAGE(1400765, name);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_CANT_ENTER(int worldDescId)	{
		return new SM_SYSTEM_MESSAGE(1400766, new DescriptionId(worldDescId * 2 + 1));
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_END_BANISH(int worldDescId)	{
		return new SM_SYSTEM_MESSAGE(1400767, new DescriptionId(worldDescId * 2 + 1));
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_END_BANISH_PARTYMSG(int worldDescId)	{
		return new SM_SYSTEM_MESSAGE(1400768, new DescriptionId(worldDescId * 2 + 1));
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_CANT_BE_MENTOR(int worldDescId)	{
		return new SM_SYSTEM_MESSAGE(1400769, new DescriptionId(worldDescId * 2 + 1));
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_DONT_USE_ITEM_BY_NOT_MENTOR(DescriptionId itemDescId) {
		return new SM_SYSTEM_MESSAGE(1400773, itemDescId);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_DONT_USE_ITEM_BY_NOT_MENTEE(DescriptionId itemDescId) {
		return new SM_SYSTEM_MESSAGE(1400774, itemDescId);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_DONT_SELECT_LEADER_LOOTING_BY_MENTOR()	{
		return new SM_SYSTEM_MESSAGE(1400837);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_CANT_BE_MENTOR_BY_LAST_MENTEE()	{
		return new SM_SYSTEM_MESSAGE(1400838);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_LEADER_LOOTING_IS_UNAVAILABLE()	{
		return new SM_SYSTEM_MESSAGE(1400839);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_CANT_BE_MENTOR_BY_LEVEL_LIMIT()	{
		return new SM_SYSTEM_MESSAGE(1400840);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_PARTY_END_BY_LEAVE_ALL_MENTEE()	{
		return new SM_SYSTEM_MESSAGE(1400841);
	}

	public static SM_SYSTEM_MESSAGE STR_MSG_MENTOR_PARTY_END_BY_CONVERT_BY_FORCE()	{
		return new SM_SYSTEM_MESSAGE(1400842);
	}
	
    /**
     * Alliance Messages
     */

    /**
     * You have invited %0's group to the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_INVITED_HIS_PARTY(String name) {
        return new SM_SYSTEM_MESSAGE(1300189, name);
    }

    /**
     * %0 has promoted %1. From now on, %1 is the alliance captain.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_CHANGE_LEADER(String name, String name2) {
        return new SM_SYSTEM_MESSAGE(1300986, name, name2);
    }

    /**
     * %0 is now vice Captain of the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_PROMOTE_MANAGER(String name) {
        return new SM_SYSTEM_MESSAGE(1300984, name);
    }

    /**
     * %0 has been demoted to member from vice Captain.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_DEMOTE_MANAGER(String name) {
        return new SM_SYSTEM_MESSAGE(1300985, name);
    }

    /**
     * You cannot invite yourself to the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_CAN_NOT_INVITE_SELF() {
        return new SM_SYSTEM_MESSAGE(1301006);
    }

    /**
     * %0 has declined your invitation to join the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_REJECT_INVITATION(String name) {
        return new SM_SYSTEM_MESSAGE(1300190, name);
    }

    /**
     * Currently, %0 cannot accept your invitation to join the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CANT_INVITE_WHEN_HE_IS_ASKED_QUESTION(String name) {
        return new SM_SYSTEM_MESSAGE(1300191, name);
    }

    /**
     * %0 is already a member of another alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OTHER_ALLIANCE(String name) {
        return new SM_SYSTEM_MESSAGE(1300192, name);
    }

    /**
     * %0 is already a member of your alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OUR_ALLIANCE(String name) {
        return new SM_SYSTEM_MESSAGE(1300193, name);
    }

    /**
     * You cannot invite %0 to the alliance as he or she is not a group leader.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CAN_NOT_INVITE_HIM_HE_IS_NOT_PARTY_LEADER(String name) {
        return new SM_SYSTEM_MESSAGE(1300194, name);
    }

    /**
     * You cannot invite %0 to the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CAN_NOT_INVITE_HIM(String name) {
        return new SM_SYSTEM_MESSAGE(1300195, name);
    }

    /**
     * You cannot invite any more as the alliance is full.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CANT_ADD_NEW_MEMBER() {
        return new SM_SYSTEM_MESSAGE(1300196);
    }

    /**
     * Only the group leader can leave the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_ONLY_PARTY_LEADER_CAN_LEAVE_ALLIANCE() {
        return new SM_SYSTEM_MESSAGE(1300197);
    }

    /**
     * Your group is not part of an alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_YOUR_PARTY_IS_NOT_ALLIANCE_MEMBER() {
        return new SM_SYSTEM_MESSAGE(1300198);
    }

    /**
     * %0's group has left the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HIS_PARTY_LEAVE_ALLIANCE(String name) {
        return new SM_SYSTEM_MESSAGE(1300199, name);
    }

    /**
     * Your group has left the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_MY_PARTY_LEAVE_ALLIANCE() {
        return new SM_SYSTEM_MESSAGE(1300200);
    }

    /**
     * The alliance has been disbanded.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_DISPERSED() {
        return new SM_SYSTEM_MESSAGE(1300201);
    }

    /**
     * %0 has left the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_LEAVED_PARTY(String name) {
        return new SM_SYSTEM_MESSAGE(1300202, name);
    }

    /**
     * %0 has been offline for too long and has been automatically kicked out of the group and the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_LEAVED_PARTY_OFFLINE_TIMEOUT(String name) {
        return new SM_SYSTEM_MESSAGE(1300203, name);
    }

    /**
     * %0 has been kicked out of the group and thus the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_BANISHED(String name) {
        return new SM_SYSTEM_MESSAGE(1300204, name);
    }

    /**
     * %0 has become the new group leader.
     */
    public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_BECOME_PARTY_LEADER(String name) {
        return new SM_SYSTEM_MESSAGE(1300205, name);
    }

    /**
     * You have joined the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_ENTERED_FORCE() {
        return new SM_SYSTEM_MESSAGE(1390263);
    }

    /**
     * You have invited %0 to join the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_INVITED_HIM(String name) {
        return new SM_SYSTEM_MESSAGE(1301017, name);
    }

    /**
     * %0 has joined the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_HE_ENTERED_FORCE(String name) {
        return new SM_SYSTEM_MESSAGE(1400013, name);
    }

    /**
     * %0 has kicked you out of the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_BAN_ME(String name) {
        return new SM_SYSTEM_MESSAGE(1300979, name);
    }

    /**
     * %0 has kicked out %1 of the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_BAN_HIM(String name, String name2) {
        return new SM_SYSTEM_MESSAGE(1300980, name, name2);
    }

    /**
     * The selected alliance member is currently offline.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_OFFLINE_MEMBER() {
        return new SM_SYSTEM_MESSAGE(1301008);
    }

    /**
     * There is no target to invite to the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_NO_USER_TO_INVITE() {
        return new SM_SYSTEM_MESSAGE(1301003);
    }

    /**
     * You cannot issue invitations while you are dead.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_CANT_INVITE_WHEN_DEAD() {
        return new SM_SYSTEM_MESSAGE(1301007);
    }

    /**
     * The leader of %0's group is %1.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_PARTY_HIM(String s0, String s1) {
        return new SM_SYSTEM_MESSAGE(1300969, s0, s1);
    }

    /**
     * %0 is already a member of another alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_ALREADY_OTHER_FORCE(String name) {
        return new SM_SYSTEM_MESSAGE(1300974, name);
    }

    /**
     * There is not enough room in the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_FAILED_NOT_ENOUGH_SLOT() {
        return new SM_SYSTEM_MESSAGE(1300975);
    }

    /**
     * You have invited %0's group to the alliance. %0's group has a total of %1 members.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_PARTY(String s0, String s1) {
        return new SM_SYSTEM_MESSAGE(1300968, s0, s1);
    }

    /**
     * %0 has been disconnected.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_HE_BECOME_OFFLINE(String s0) {
        return new SM_SYSTEM_MESSAGE(1301019, s0);
    }

    /**
     * %0 has been offline for too long and had been automatically kicked out of the alliance.
     */
    public static SM_SYSTEM_MESSAGE STR_FORCE_HE_BECOME_OFFLINE_TIMEOUT(String s0) {
        return new SM_SYSTEM_MESSAGE(1300981, s0);
    }

    /**
     * Legion messages.
     */
    /**
     * NPC TOO FAR messages *
     */
    public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_TOO_FAR_FROM_NPC() {
        // You are too far from the NPC to disband the Legion.
        return new SM_SYSTEM_MESSAGE(1300305);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CREATE_TOO_FAR_FROM_NPC() {
        // You are too far from the NPC to create a Legion.
        return new SM_SYSTEM_MESSAGE(1300229);
    }

    /**
     * Incorrect target / user offline *
     */
    public static SM_SYSTEM_MESSAGE LEGION_INCORRECT_TARGET() {
        return new SM_SYSTEM_MESSAGE(1300627);
    }

    /**
     * Announcement related *
     */
    public static SM_SYSTEM_MESSAGE LEGION_DISPLAY_ANNOUNCEMENT(String announcement, long unixTime, int type) {
        return new SM_SYSTEM_MESSAGE(1400019, announcement, unixTime, type);
    }

    /**
     * Done messages *
     */
    public static SM_SYSTEM_MESSAGE LEGION_WRITE_NOTICE_DONE() {
        // The Legion Announcement has been modified.
        return new SM_SYSTEM_MESSAGE(1300277);
    }

    /**
     * Player online/kicked/left/joined *
     */
    public static SM_SYSTEM_MESSAGE LEGION_MEMBER_ONLINE(String charName) {
        return new SM_SYSTEM_MESSAGE(1400133, charName);
    }

    public static SM_SYSTEM_MESSAGE NEW_MEMBER_JOINED(String charName) {
        // %0 has joined your Legion.
        return new SM_SYSTEM_MESSAGE(1300260, charName);
    }

    public static SM_SYSTEM_MESSAGE LEGION_MEMBER_LEFT(String charName) {
        // %0 has left the Legion.
        return new SM_SYSTEM_MESSAGE(900699, charName);
    }

    public static SM_SYSTEM_MESSAGE LEGION_NEW_MASTER() {
        // %0 was appointed as the new Legion Brigade General.
        return new SM_SYSTEM_MESSAGE(900701);
    }

    /**
     * Requests and their response *
     */
    public static SM_SYSTEM_MESSAGE SEND_INVITE_REQUEST(String charName) {
        // You have sent a Legion invitation to %0.
        return new SM_SYSTEM_MESSAGE(1300258, charName);
    }

    public static SM_SYSTEM_MESSAGE REJECTED_INVITE_REQUEST(String charName) {
        // %0 has declined your Legion invitation.
        return new SM_SYSTEM_MESSAGE(1300259, charName);
    }

    /**
     * Name related messages *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CREATE_INVALID_NAME() {
        // That name is invalid. Please try another..
        return new SM_SYSTEM_MESSAGE(1300228);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CREATE_NAME_EXISTS() {
        // That name is invalid. Please try another.
        return new SM_SYSTEM_MESSAGE(1300233);
    }

    public static SM_SYSTEM_MESSAGE LEGION_WRITE_INTRO_DONE() {
        // Your Character Information has been modified.
        return new SM_SYSTEM_MESSAGE(1300282);
    }

    /**
     * Legion update related *
     */
    public static SM_SYSTEM_MESSAGE LEGION_LEVEL_UP(int legionLevel) {
        // The Legion was leveled up to %0.
        return new SM_SYSTEM_MESSAGE(900700, legionLevel);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_CANT_LEVEL_UP() {
        // The Legion is already at the highest level.
        return new SM_SYSTEM_MESSAGE(1300316);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGED_EMBLEM() {
        return new SM_SYSTEM_MESSAGE(1390137);
    }

    /**
     * Reponse to checks - CREATION *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CREATE_ALREADY_MEMBER() {
        // You cannot create a Legion as you are already a member of another Legion.
        return new SM_SYSTEM_MESSAGE(1300232);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CREATE_NOT_ENOUGH_KINAH() {
        // You do not have enough Kinah to create a Legion.
        return new SM_SYSTEM_MESSAGE(1300231);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CREATE_LAST_DAY_CHECK() {
        // You cannot create a new Legion as the grace period between creating Legions has not expired.
        return new SM_SYSTEM_MESSAGE(1300234);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CREATED(String legionName) {
        // The %0 Legion has been created.
        return new SM_SYSTEM_MESSAGE(1300235, legionName);
    }

    /**
     * Reponse to checks - LEVEL UP *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_POINT() {
        // You do not have enough Contribution Points.
        return new SM_SYSTEM_MESSAGE(1300317);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_MEMBER() {
        // Your Legion does not have enough members.
        return new SM_SYSTEM_MESSAGE(1300318);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_KINAH() {
        // You do not have enough Kinah.
        return new SM_SYSTEM_MESSAGE(1300319);
    }

    /**
     * Reponse to checks - INVITE *
     */
    public static SM_SYSTEM_MESSAGE LEGION_TARGET_BUSY() {
        // The target is busy and cannot be invited at the moment.
        return new SM_SYSTEM_MESSAGE(1300325);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CANT_INVITE_WHILE_DEAD() {
        // You cannot issue a Legion invitation while you are dead.
        return new SM_SYSTEM_MESSAGE(1300250);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CAN_NOT_INVITE_SELF() {
        // You cannot invite yourself to a Legion.
        return new SM_SYSTEM_MESSAGE(1300254);
    }

    public static SM_SYSTEM_MESSAGE LEGION_HE_IS_MY_GUILD_MEMBER(String charName) {
        // %0 is already a member of your Legion.
        return new SM_SYSTEM_MESSAGE(1300255, charName);
    }

    public static SM_SYSTEM_MESSAGE LEGION_HE_IS_OTHER_GUILD_MEMBER(String charName) {
        // %0 is a member of another Legion.
        return new SM_SYSTEM_MESSAGE(1300256, charName);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CAN_NOT_ADD_MEMBER_ANY_MORE() {
        // There is no room in the Legion for more members.
        return new SM_SYSTEM_MESSAGE(1300257);
    }

    public static SM_SYSTEM_MESSAGE LEGION_NO_USER_TO_INVITE() {
        // There is no user to invite to your Legion.
        return new SM_SYSTEM_MESSAGE(1300253);
    }

    /**
     * Reponse to checks - LEAVE *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CANT_LEAVE_BEFORE_CHANGE_MASTER() {
        // You cannot leave your Legion unless you transfer Brigade General authority to someone else.
        return new SM_SYSTEM_MESSAGE(1300238);
    }

    /**
     * Reponse to checks - KICK *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CANT_KICK_YOURSELF() {
        // You cannot kick yourself out from a Legion.
        return new SM_SYSTEM_MESSAGE(1300243);
    }

    public static SM_SYSTEM_MESSAGE LEGION_KICKED_BY(String charName) {
        // You have been kicked out from the %0 Legion.
        return new SM_SYSTEM_MESSAGE(1300246, charName);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CANT_KICK_BRIGADE_GENERAL() {
        // You cannot kick out the Legion Brigade General.
        return new SM_SYSTEM_MESSAGE(1300249);
    }

    /**
     * Reponse to checks - CHANGE RANK *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT() {
        // You cannot change the ranks of Legion members because you are not the Legion Brigade General.
        return new SM_SYSTEM_MESSAGE(1300262);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_ERROR_SELF() {
        // The Legion Brigade General cannot change its own rank.
        return new SM_SYSTEM_MESSAGE(1300263);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_NO_USER() {
        // There is no one to change rank.
        return new SM_SYSTEM_MESSAGE(1300264);
    }

    /**
     * Reponse to checks - APPOINT BRIGADE GENERAL *
     */
    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_ERROR_SELF() {
        // You are already the Legion Brigade General
        return new SM_SYSTEM_MESSAGE(1300271);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_NO_SUCH_USER() {
        // You cannot transfer your Brigade General authority to an offline user.
        return new SM_SYSTEM_MESSAGE(1300270);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_SENT_OFFER_MSG_TO_HIM(String charName) {
        // You nominated %0 as the next Legion Brigade General.
        return new SM_SYSTEM_MESSAGE(1300330, charName);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_SENT_CANT_OFFER_WHEN_HE_IS_QUESTION_ASKED() {
        // You cannot request the selected player to become the Legion Brigade General.
        return new SM_SYSTEM_MESSAGE(1300331);
    }

    public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_HE_DECLINE_YOUR_OFFER(String charName) {
        // %0 has declined to become the Legion Brigade General.
        return new SM_SYSTEM_MESSAGE(1300332, charName);
    }

    /**
     * Reponse to checks - DISBAND *
     */
    public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE() {
        // You have no authority to disband the Legion.
        return new SM_SYSTEM_MESSAGE(1300300);
    }

    public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_REQUESTED(int unixTime) {
        // The Brigade General has requested to disband the Legion. The expected time of disbanding is %DATETIME0.
        return new SM_SYSTEM_MESSAGE(1300303, unixTime);
    }

    public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_ALREADY_REQUESTED() {
        // You have already requested to disband the Legion.
        return new SM_SYSTEM_MESSAGE(1300304);
    }

    public static SM_SYSTEM_MESSAGE LEGION_WAREHOUSE_CANT_USE_WHILE_DISPERSE() {
        // You cannot use the Legion warehouse during the disbandment waiting period.
        return new SM_SYSTEM_MESSAGE(1300333);
    }

    public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_CANT_DISPERSE_GUILD_STORE_ITEM_IN_WAREHOUSE() {
        // You cannot disband your Legion while you have items left in the Legion warehouse.
        return new SM_SYSTEM_MESSAGE(1390212);
    }

    /**
     * Legion Message correct order from bottom
     */
    public static SM_SYSTEM_MESSAGE STR_MSG_NOTIFY_LOGIN_GUILD(String charName) {
        return new SM_SYSTEM_MESSAGE(1400133, charName);
    }

    /**
     * You cannot use an item while running a Private Store.
     */
    public static SM_SYSTEM_MESSAGE STR_MSG_CANNOT_USE_ITEM_WHILE_PRIVATE_STORE = new SM_SYSTEM_MESSAGE(1300048, new DescriptionId(2800123));

    /**
     * You can use this skill only while flying.
     */
    public static SM_SYSTEM_MESSAGE STR_SKILL_RESTRICTION_FLY_ONLY = new SM_SYSTEM_MESSAGE(1300113);

    public static SM_SYSTEM_MESSAGE STR_ITEM_ERROR = new SM_SYSTEM_MESSAGE(1300514);

    public static SM_SYSTEM_MESSAGE STR_DECOMPOSE_ITEM_CANCELED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300450, itemDescId);
    }

    /**
     * You cannot use the item as its cooldown time has not expired yet.
     */
    public static SM_SYSTEM_MESSAGE STR_ITEM_CANT_USE_UNTIL_DELAY_TIME = new SM_SYSTEM_MESSAGE(1300494);

    /**
     * You cannot destroy %0 because it is used in the "%1" quest which cannot be abandoned once started.
     */
    public static SM_SYSTEM_MESSAGE STR_QUEST_GIVEUP_WHEN_DELETE_QUEST_ITEM_IMPOSSIBLE(DescriptionId nameId, DescriptionId questNameId) {
        return new SM_SYSTEM_MESSAGE(1300604, nameId, questNameId);
    }

    /**
     * You cannot fly in this area.
     */
    public static SM_SYSTEM_MESSAGE STR_FLYING_FORBIDDEN_HERE = new SM_SYSTEM_MESSAGE(1300960);

    /**
     * You cannot use teleport services when you flying
     */
    public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_AIRPORT_WHEN_FLYING = new SM_SYSTEM_MESSAGE(1300696);

    /**
     * The NPC you selected does not have the ability to teleport you.
     */
    public static SM_SYSTEM_MESSAGE STR_CANNOT_MOVE_TO_AIRPORT_WRONG_NPC = new SM_SYSTEM_MESSAGE(1300692);

    /**
     * You cannot move to that destination.
     */
    public static SM_SYSTEM_MESSAGE STR_CANNOT_MOVE_TO_AIRPORT_NO_ROUTE = new SM_SYSTEM_MESSAGE(1300691);

    /**
     * Binding Point Messages
     */
    public static SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE() {
        return new SM_SYSTEM_MESSAGE(1300686);
    }

    public static SM_SYSTEM_MESSAGE STR_ALREADY_REGISTER_THIS_RESURRECT_POINT() {
        return new SM_SYSTEM_MESSAGE(1300688);
    }

    public static SM_SYSTEM_MESSAGE STR_DEATH_REGISTER_RESURRECT_POINT() {
        return new SM_SYSTEM_MESSAGE(1300670);
    }

    public static SM_SYSTEM_MESSAGE STR_ATTACK_TOO_FAR_FROM_TARGET() {
        return new SM_SYSTEM_MESSAGE(1300032);
    }

    public static SM_SYSTEM_MESSAGE STR_SKILL_OBSTACLE()
    {
        return new SM_SYSTEM_MESSAGE(1300030);
    }

    public static SM_SYSTEM_MESSAGE NO_POWER_SHARD_EQUIPPED() {
        return new SM_SYSTEM_MESSAGE(1300490);
    }

    public static SM_SYSTEM_MESSAGE ACTIVATE_THE_POWER_SHARD() {
        return new SM_SYSTEM_MESSAGE(1300491);
    }

    public static SM_SYSTEM_MESSAGE DEACTIVATE_THE_POWER_SHARD() {
        return new SM_SYSTEM_MESSAGE(1300492);
    }

    public static SM_SYSTEM_MESSAGE NO_POWER_SHARD_LEFT() {
        return new SM_SYSTEM_MESSAGE(1400075);
    }

    public static SM_SYSTEM_MESSAGE ADDITIONAL_PLACES_IN_WAREHOUSE() {
        return new SM_SYSTEM_MESSAGE(1300433);
    }

    public static SM_SYSTEM_MESSAGE EARNED_ABYSS_POINT(String count) {
        return new SM_SYSTEM_MESSAGE(1320000, count);
    }

    public static SM_SYSTEM_MESSAGE STR_SKILL_CANCELED() {
        return new SM_SYSTEM_MESSAGE(1300023);
    }

    public static SM_SYSTEM_MESSAGE INVALID_TARGET() {
        return new SM_SYSTEM_MESSAGE(1300013);
    }

    public static SM_SYSTEM_MESSAGE SEARCH_NOT_EXIST() {
        return new SM_SYSTEM_MESSAGE(1310019);
    }

    public static SM_SYSTEM_MESSAGE QUEST_ACQUIRE_ERROR_INVENTORY_ITEM(int count) {
        return new SM_SYSTEM_MESSAGE(1300594, count);
    }

    /**
     * Trading (Private Store, etc.)
     */
    public static SM_SYSTEM_MESSAGE NOT_ENOUGH_KINAH(long kinah) {
        return new SM_SYSTEM_MESSAGE(901285, kinah);
    }

    public static final SM_SYSTEM_MESSAGE MSG_FULL_INVENTORY = new SM_SYSTEM_MESSAGE(1300762);

    public static final AionServerPacket CUBEEXPAND_NOT_ENOUGH_KINAH = new SM_SYSTEM_MESSAGE(1300831);

    /**
     * Manastone Messages
     */
    public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_OPTION_SUCCEED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300462, itemDescId);
    }

    public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_OPTION_FAILED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300463, itemDescId);
    }

    /**
     * Enchant Messages
     */
    public static SM_SYSTEM_MESSAGE STR_ENCHANT_ITEM_SUCCEED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300455, itemDescId);
    }

    public static SM_SYSTEM_MESSAGE STR_ENCHANT_ITEM_FAILED(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300456, itemDescId);
    }

    /**
     * cannot equip items if require level higher than character level
     */
    public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_ITEM_TOO_LOW_LEVEL_MUST_BE_THIS_LEVEL(int itemLevel,
                                                                                         DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300372, itemLevel, itemDescId);
    }

    /**
     * Delete character messages
     */
    public static SM_SYSTEM_MESSAGE STR_DELETE_CHARACTER_IN_LEGION() {
        return new SM_SYSTEM_MESSAGE(1300306);
    }

    /**
     * Summon Related
     */
    public static SM_SYSTEM_MESSAGE SUMMON_ATTACKMODE(int nameId) {
        return new SM_SYSTEM_MESSAGE(1200008, new DescriptionId(nameId * 2 + 1));
    }

    public static SM_SYSTEM_MESSAGE SUMMON_GUARDMODE(int nameId) {
        return new SM_SYSTEM_MESSAGE(1200009, new DescriptionId(nameId * 2 + 1));
    }

    public static SM_SYSTEM_MESSAGE SUMMON_RESTMODE(int nameId) {
        return new SM_SYSTEM_MESSAGE(1200010, new DescriptionId(nameId * 2 + 1));
    }

    public static SM_SYSTEM_MESSAGE SUMMON_UNSUMMON(int nameId) {
        return new SM_SYSTEM_MESSAGE(1200011, new DescriptionId(nameId * 2 + 1));
    }

    public static SM_SYSTEM_MESSAGE SUMMON_DISMISSED(int nameId) {
        return new SM_SYSTEM_MESSAGE(1200006, new DescriptionId(nameId * 2 + 1));
    }

    public static SM_SYSTEM_MESSAGE SUMMON_INVALID_TARGET() {
        return new SM_SYSTEM_MESSAGE(1300088);
    }

    public static SM_SYSTEM_MESSAGE SUMMON_ALREADY_HAVE_FOLLOWER() {
        return new SM_SYSTEM_MESSAGE(1300072);
    }

    public static SM_SYSTEM_MESSAGE SUMMON_UNSUMMON_BY_TOO_DISTANCE() {
        return new SM_SYSTEM_MESSAGE(1300073);
    }

    public static SM_SYSTEM_MESSAGE SUMMON_CANT_ORDER_BY_TOO_DISTANCE() {
        return new SM_SYSTEM_MESSAGE(1300074);
    }
	
	public static SM_SYSTEM_MESSAGE RECALL_CANNOT_ACCEPT_EFFECT(String summonedName)
	{
		return new SM_SYSTEM_MESSAGE(1400097, summonedName);
	}

	public static SM_SYSTEM_MESSAGE RECALL_DONOT_ACCEPT_EFFECT(String summonerName)
	{
		return new SM_SYSTEM_MESSAGE(1400098, summonerName);
	}

	public static SM_SYSTEM_MESSAGE RECALL_REJECT_EFFECT(String summonedName)
	{
		return new SM_SYSTEM_MESSAGE(1400099, summonedName);
	}

	public static SM_SYSTEM_MESSAGE RECALL_REJECTED_EFFECT(String summonedName)
	{
		return new SM_SYSTEM_MESSAGE(1400100, summonedName);
	}
	// Looking for info about where to use the following 2 messages
	public static SM_SYSTEM_MESSAGE RECALL_CANCEL_EFFECT(String summonedName)
	{
		return new SM_SYSTEM_MESSAGE(1400101, summonedName);
	}

	public static SM_SYSTEM_MESSAGE RECALL_DUPLICATE_EFFECT(String summonerName)
	{
		return new SM_SYSTEM_MESSAGE(1400102, summonerName);
	}

    /**
     * Loot
     */
    public static SM_SYSTEM_MESSAGE STR_LOOT_NO_RIGHT() {
        // You are not authorized to examine the corpse.
        return new SM_SYSTEM_MESSAGE(901338);
    }

    public static SM_SYSTEM_MESSAGE STR_LOOT_FAIL_ONLOOTING() {
        // Someone is already looting that.
        return new SM_SYSTEM_MESSAGE(1300829);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_ANOTHER_OWNER_ITEM() {
        // You do not have the ownership of this item.
        return new SM_SYSTEM_MESSAGE(1390220);
    }

    public static SM_SYSTEM_MESSAGE CRAFT_RECIPE_LEARN(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1330061, nameId);
    }

    public static SM_SYSTEM_MESSAGE MSG_DONT_GET_PRODUCTION_EXP(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1390221, nameId);
    }

    /**
     * Deny messages
     */
    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_WATCH(String charName) {
        return new SM_SYSTEM_MESSAGE(1390114, charName);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_TRADE(String charName) {
        return new SM_SYSTEM_MESSAGE(1390115, charName);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_PARTY(String charName) {
        return new SM_SYSTEM_MESSAGE(1390116, charName);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_FORCE(String charName) {
        return new SM_SYSTEM_MESSAGE(1390117, charName);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_GUILD(String charName) {
        return new SM_SYSTEM_MESSAGE(1390118, charName);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_FRIEND(String charName) {
        return new SM_SYSTEM_MESSAGE(1390119, charName);
    }

    public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_DUEL(String charName) {
        return new SM_SYSTEM_MESSAGE(1390120, charName);
    }

    /**
     * Item Remodel (Thanks wylovech)
     */
    public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_FAR_FROM_NPC = new SM_SYSTEM_MESSAGE(1300475);
    public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT = new SM_SYSTEM_MESSAGE(1300476);
    public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NO_TARGET_ITEM = new SM_SYSTEM_MESSAGE(1300477);

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_SKIN_CHANGABLE_ITEM(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1300478, nameId);
    }

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_SAME_EQUIP_SLOT(DescriptionId keepNameId, DescriptionId skinNameId) {
        return new SM_SYSTEM_MESSAGE(1300479, keepNameId, skinNameId);
    }

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(DescriptionId keepNameId, DescriptionId skinNameId) {
        return new SM_SYSTEM_MESSAGE(1300480, keepNameId, skinNameId);
    }

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1300481, nameId);
    }

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_CAN_NOT_REMOVE_SKIN_ITEM(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1300482, nameId);
    }

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_SUCCEED(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1300483, nameId);
    }

    public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_INVALID_STANCE(DescriptionId nameId) {
        return new SM_SYSTEM_MESSAGE(1300484, nameId);
    }

    /**
     * Example npc shout
     */
    public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_001() {
        // Leave the crops alone!
        return new SM_SYSTEM_MESSAGE(390270, true);
    }

    public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_002() {
        // I spent so much time and effort to grow these crops!
        return new SM_SYSTEM_MESSAGE(390271, true);
    }

    public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_003() {
        // "Darn, those wretched Kerubs!"
        return new SM_SYSTEM_MESSAGE(390272, true);
    }

    public static final SM_SYSTEM_MESSAGE STR_MOVE_PORTAL_ERROR_INVALID_RACE = new SM_SYSTEM_MESSAGE(901354);
    public static final SM_SYSTEM_MESSAGE STR_MSG_CANT_INSTANCE_ENTER_LEVEL = new SM_SYSTEM_MESSAGE(1400179);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ENTER_ONLY_PARTY_DON = new SM_SYSTEM_MESSAGE(1390256);

    //You may enter %WORLDNAME0 again after %1 minutes.

    public static SM_SYSTEM_MESSAGE STR_MSG_CANNOT_ENTER_INSTANCE_COOL_TIME_MIN(int worldDescId, int time) {
        return new SM_SYSTEM_MESSAGE(1400030, new DescriptionId(worldDescId * 2 + 1), time);
    }
    //You may enter %WORLDNAME0 again after %1 hours

    public static SM_SYSTEM_MESSAGE STR_MSG_CANNOT_ENTER_INSTANCE_COOL_TIME_HOUR(int worldDescId, int time) {
        return new SM_SYSTEM_MESSAGE(1400029, new DescriptionId(worldDescId * 2 + 1), time);
    }


    public static final SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_NO_PROC_GIVE_ITEM = new SM_SYSTEM_MESSAGE(1300505);
    public static final SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_CANNOT_GIVE_PROC_TO_EQUIPPED_ITEM = new SM_SYSTEM_MESSAGE(1300503);

    public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_ENCHANTED_TARGET_ITEM(DescriptionId itemDescId) {
        return new SM_SYSTEM_MESSAGE(1300508, itemDescId);
    }

    public static final SM_SYSTEM_MESSAGE STR_MSG_DICE_INVEN_ERROR = new SM_SYSTEM_MESSAGE(1390182);
    public static final SM_SYSTEM_MESSAGE COMBINE_INVENTORY_IS_FULL = new SM_SYSTEM_MESSAGE(1330037);

    /**
     * Kisks
     * - Sarynth, Master
     */

    public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY = new SM_SYSTEM_MESSAGE(1300799);
    public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_FAR_FROM_NPC = new SM_SYSTEM_MESSAGE(1300800);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_DESTROYED = new SM_SYSTEM_MESSAGE(1300802);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_REMOVED = new SM_SYSTEM_MESSAGE(1300803);
    public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_BINDSTONE_ITEM_NOT_PROPER_AREA = new SM_SYSTEM_MESSAGE(1300804);
    public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_BINDSTONE_ITEM_WHILE_FLYING = new SM_SYSTEM_MESSAGE(1300806);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_DESTROYED = new SM_SYSTEM_MESSAGE(1390158);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_REGISTER = new SM_SYSTEM_MESSAGE(1390159);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_ALREADY_INSTALLED = new SM_SYSTEM_MESSAGE(1390160);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_ALREADY_REGISTERED = new SM_SYSTEM_MESSAGE(1390161);
    public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_ATTACKED = new SM_SYSTEM_MESSAGE(1390166);
    public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_FULL = new SM_SYSTEM_MESSAGE(1400247);

    public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_MAGIC_PASSAGE = new SM_SYSTEM_MESSAGE(1300150);
	
	public static SM_SYSTEM_MESSAGE STR_FORCE_ITEM_WIN(String name,DescriptionId itemDescId)
    {
        return new SM_SYSTEM_MESSAGE(1390003,name,itemDescId);
    }

    /**
     * status-messages
     */
    public static final SM_SYSTEM_MESSAGE STR_CUSTOM_ANIM_TYPE_REST = new SM_SYSTEM_MESSAGE(902875);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_STANDING = new SM_SYSTEM_MESSAGE(1400053);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_PATH_FLYING = new SM_SYSTEM_MESSAGE(1400054);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_FREE_FLYING = new SM_SYSTEM_MESSAGE(1400055);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_RIDING = new SM_SYSTEM_MESSAGE(1400056);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_SITTING = new SM_SYSTEM_MESSAGE(1400057);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_SITTING_ON_CHAIR = new SM_SYSTEM_MESSAGE(1400058);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_DEAD = new SM_SYSTEM_MESSAGE(1400059);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ACT_STATE_DEFAULT = new SM_SYSTEM_MESSAGE(1400064);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ASF_COMBAT = new SM_SYSTEM_MESSAGE(1400079);
    public static final SM_SYSTEM_MESSAGE STR_MSG_ASF_GLIDE = new SM_SYSTEM_MESSAGE(1400082);
    
    /**
     * fusion/break  Weapons
     * - Master,wylovech
     */
    public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_MAIN_REQUIRE_HIGHER_LEVEL = new SM_SYSTEM_MESSAGE(1400288);

    public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_NOT_AVAILABLE(int nameId) {
        return new SM_SYSTEM_MESSAGE(1400289, new DescriptionId(nameId));
    }

    public static final SM_SYSTEM_MESSAGE STR_COMPOUNDED_ITEM_DECOMPOUND_SUCCESS(int nameId) {
        return new SM_SYSTEM_MESSAGE(1400335, new DescriptionId(nameId));
    }

    public static final SM_SYSTEM_MESSAGE STR_COMPOUND_SUCCESS(int nameId, int nameId2) {
        return new SM_SYSTEM_MESSAGE(1400336, new DescriptionId(nameId), new DescriptionId(nameId2));
    }

    public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_NOT_ENOUGH_MONEY(int nameId, int nameId2) {
        return new SM_SYSTEM_MESSAGE(1400337, new DescriptionId(nameId), new DescriptionId(nameId2));
    }

    public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_DIFFERENT_TYPE = new SM_SYSTEM_MESSAGE(1400364);

    public static final SM_SYSTEM_MESSAGE STR_DECOMPOUND_ERROR_NOT_AVAILABLE(int nameId) {
        return new SM_SYSTEM_MESSAGE(1400373, new DescriptionId(nameId));
    }

    public static final SM_SYSTEM_MESSAGE STR_MSG_DECOMPRESS_INVENTORY_IS_FULL = new SM_SYSTEM_MESSAGE(1400363);

	public static  SM_SYSTEM_MESSAGE STR_SKILL_CAN_NOT_USE_ITEM_IN_CURRENT_POSITION	= new SM_SYSTEM_MESSAGE(1300143);

    public static final SM_SYSTEM_MESSAGE STR_MSG_GET_ITEM(int nameId) {
        return new SM_SYSTEM_MESSAGE(1390004, new DescriptionId(nameId));
    }

    public static final SM_SYSTEM_MESSAGE STR_MSG_GET_ITEM_MULTI(int nameId, int count) {
        return new SM_SYSTEM_MESSAGE(1390005, new DescriptionId(nameId), count);
    }

    public static final SM_SYSTEM_MESSAGE STR_UNCOMPRESS_COMPRESSED_ITEM_SUCCEEDED(int nameId) {
        return new SM_SYSTEM_MESSAGE(1400452, new DescriptionId(nameId));
    }

    public static final SM_SYSTEM_MESSAGE STR_ITEM_CANCELED() {
    	return new SM_SYSTEM_MESSAGE(1300427);
    }

    public static final SM_SYSTEM_MESSAGE STR_MSG_DELETE_CASH_ITEM_BY_TIMEOUT(int nameId) {
    	return new SM_SYSTEM_MESSAGE(1400034, new DescriptionId(nameId));
    }
	/**
	 * in combat
	 */
	public static int MSG_ASF_COMBAT = 1400079;
	
    /** 
	 * You cannot feed your pet %0.
	 */
	public static SM_SYSTEM_MESSAGE STR_MSGBOX_TOYPET_FEED_CANT_FEED(int nameId)
	{
		return new SM_SYSTEM_MESSAGE(1400698, new DescriptionId(nameId * 2 + 1));
	}
	
	/** 
	 * %0 spits out %1 and makes a face.
	 */
	public static SM_SYSTEM_MESSAGE STR_MSG_TOYPET_FEED_FOOD_NOT_LOVEFLAVOR(String petName, int nameId)
	{
		return new SM_SYSTEM_MESSAGE(1400618, petName, new DescriptionId(nameId));
	}	
	
	
	/**
	 * TODO:I don't know this message in English. Someone write currect annotation in here plz.
	 * by Mugen
	 */
	
	public static SM_SYSTEM_MESSAGE STR_MSG_GET_ITEM_MULTI(DescriptionId nameId, int count)
	{
		return new SM_SYSTEM_MESSAGE(1390005, nameId, count);
	}
	
	/**
	 * TODO:I don't know this message in English. Someone write currect annotation in here plz.
	 * by Mugen
	 */
	
	public static SM_SYSTEM_MESSAGE STR_MSG_GET_CASH_CUSTOMIZE_MOTION(DescriptionId nameId)
	{
		return new SM_SYSTEM_MESSAGE(1401029, nameId);
	}
    private final int code;
    private final Object[] params;
    private boolean npcShout = false;
    private int npcObjId = 0;

    /**
     * Constructs new <tt>SM_SYSTEM_MESSAGE </tt> packet
     *
     * @param code   operation code, take it from SM_SYSTEM_MESSAGE public static values
     * @param params
     */
    public SM_SYSTEM_MESSAGE(int code, Object... params) {
        this.code = code;
        this.params = params;
    }

    public SM_SYSTEM_MESSAGE(int code, boolean npcShout, int npcObjId, Object... params) {
        this.code = code;
        this.npcShout = npcShout;
        this.npcObjId = npcObjId;
        this.params = params;
    }

    public SM_SYSTEM_MESSAGE(SystemMessageId sm, Object... params) {
        this.code = sm.getId();
        this.params = params;
    }

    /**
     * Return system message id
     */
    public int getCode() {
        return code;
    }

	/**
	*academy
	*/
	public static SM_SYSTEM_MESSAGE STR_MSG_FRIENDLY_MOVE_COMBATAREA_IDARENA(String s)
    {
        return new SM_SYSTEM_MESSAGE(0x156084, new Object[] { s });
    }
	
	public static SM_SYSTEM_MESSAGE STR_MSG_INSTANCE_COMPLETE_STAGE_IDARENA(int i)
    {
        return new SM_SYSTEM_MESSAGE(0x156062, new Object[] { Integer.valueOf(i) });
    }
	
	public static SM_SYSTEM_MESSAGE STR_MSG_INSTANCE_COMPLETE_ROUND_IDARENA(int i)
    {
        return new SM_SYSTEM_MESSAGE(0x156061, new Object[] { Integer.valueOf(i) });
    }
	
	public static SM_SYSTEM_MESSAGE STR_MSG_INSTANCE_START_ROUND_IDARENA(int i)
    {
        return new SM_SYSTEM_MESSAGE(0x156060, new Object[] { Integer.valueOf(i) });
    }
	
	public static SM_SYSTEM_MESSAGE STR_MSG_INSTANCE_JOIN_ROUND_IDARENA(int i, int j)
    {
        return new SM_SYSTEM_MESSAGE(0x156063, new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
    }
	
	public static SM_SYSTEM_MESSAGE STR_MSG_GET_SCORE(int i, int j)
    {
        return new SM_SYSTEM_MESSAGE(0x155dad, new Object[] { new DescriptionId(j * 2 + 1), Integer.valueOf(i) });
    }
	public static SM_SYSTEM_MESSAGE STR_MSG_FRIENDLY_LEAVE_IDARENA(String s)
    {
        return new SM_SYSTEM_MESSAGE(0x156082, new Object[] { s });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con, ByteBuffer buf) {
        if (!npcShout) {
            writeH(buf, 0x13); // unk
            writeD(buf, 0x00);
        } else {
            writeC(buf, 0x01);
            writeC(buf, 0x00);
            writeD(buf, npcObjId); // unk
        }

        writeD(buf, code); // msg id
        writeC(buf, params.length); // count

        for (Object param : params) {
            if (param instanceof DescriptionId) {
                writeH(buf, 0x24);
                writeD(buf, ((DescriptionId) param).getValue());
				writeH(buf, 0x00); // unk
			}
			else
				writeS(buf, String.valueOf(param));
		}
		if(npcShout)
			writeC(buf, 0x01);
		else
			writeC(buf, 0x00);
	}
}
