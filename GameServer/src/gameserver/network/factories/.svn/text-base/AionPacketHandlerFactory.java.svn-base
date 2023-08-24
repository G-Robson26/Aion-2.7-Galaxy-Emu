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
package gameserver.network.factories;

import gameserver.network.aion.AionClientPacket;
import gameserver.network.aion.AionConnection.State;
import gameserver.network.aion.AionPacketHandler;
import gameserver.network.aion.clientpackets.*;
import gameserver.configs.main.GSConfig;

/**
 * This factory is responsible for creating {@link AionPacketHandler} object. It also initializes created handler with a
 * set of packet prototypes.<br>
 * Object of this classes uses <tt>Injector</tt> for injecting dependencies into prototype objects.<br>
 * <br>
 *
 * @author Luno
 * @author Ares/Kaipo (1.9-2.0-2.1)
 * @author Magenik (1.9-2.0-2.1)
 * @author poolsharky27 (1.9-2.0-2.1)
 * @author oni
 * @author PZIKO333 (2.1)
 * @author osiris087
 * @author drsaluml
 */
public class AionPacketHandlerFactory {
    private AionPacketHandler handler;

    public static final AionPacketHandlerFactory getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Creates new instance of <tt>AionPacketHandlerFactory</tt><br>
     */
	//Packet Opcodes 2.7
    private AionPacketHandlerFactory() 
	{
        handler = new AionPacketHandler();
        addPacket(new CM_QUESTIONNAIRE(0x0100), State.IN_GAME);// 2.7
        addPacket(new CM_L2AUTH_LOGIN_CHECK(0x0104), State.CONNECTED);// 2.7
        addPacket(new CM_CHARACTER_LIST(0x0105), State.AUTHED);// 2.7
        addPacket(new CM_TELEPORT_SELECT(0x0107), State.IN_GAME);// 2.7
        addPacket(new CM_RESTORE_CHARACTER(0x0108), State.AUTHED);// 2.7
		addPacket(new CM_START_LOOT(0x0109), State.IN_GAME);// 2.7
        addPacket(new CM_CREATE_CHARACTER(0x010A), State.AUTHED);// 2.7
		addPacket(new CM_DELETE_CHARACTER(0x010B), State.AUTHED);// 2.7
		addPacket(new CM_SPLIT_ITEM(0x010C), State.IN_GAME);// 2.7
		// 2.7 Unknown Packet - 0D 01 57 F2 FE
        addPacket(new CM_LOOT_ITEM(0x010E), State.IN_GAME);// 2.7
        addPacket(new CM_MOVE_ITEM(0x010F), State.IN_GAME);// 2.7
		addPacket(new CM_LEGION_UPLOAD_EMBLEM(0x0110), State.IN_GAME);// 2.7
		addPacket(new CM_MAIL_SUMMON_ZEPHYR(0x0111), State.IN_GAME);// 2.7
        addPacket(new CM_PLAYER_SEARCH(0x0112), State.IN_GAME);// 2.7
		addPacket(new CM_LEGION_UPLOAD_INFO(0x0113), State.IN_GAME);// 2.7
        addPacket(new CM_BLOCK_ADD(0x0115), State.IN_GAME);// 2.7
        addPacket(new CM_FRIEND_STATUS(0x0119), State.IN_GAME);// 2.7
		addPacket(new CM_BLOCK_DEL(0x011A), State.IN_GAME);// 2.7
		addPacket(new CM_SHOW_BLOCKLIST(0x011B), State.IN_GAME);// 2.7
        addPacket(new CM_MAC_ADDRESS2(0x011D), State.IN_GAME);// 2.7
		addPacket(new CM_CHANGE_CHANNEL(0x011F), State.IN_GAME);// 2.7
		addPacket(new CM_CHECK_NICKNAME(0x0120), State.AUTHED);// 2.7
		addPacket(new CM_REPLACE_ITEM(0x011C), State.IN_GAME);// 2.7
		addPacket(new CM_MACRO_CREATE(0x0122), State.IN_GAME);// 2.7
		addPacket(new CM_MACRO_DELETE(0x0123), State.IN_GAME);// 2.7
		addPacket(new CM_SHOW_BRAND(0x0124), State.IN_GAME);// 2.7
		addPacket(new CM_BLOCK_SET_REASON(0x0126), State.IN_GAME);// 2.7
        addPacket(new CM_DISTRIBUTION_SETTINGS(0x0128), State.IN_GAME);// 2.7
        addPacket(new CM_MAY_LOGIN_INTO_GAME(0x0129), State.AUTHED);// 2.7
        addPacket(new CM_RECONNECT_AUTH(0x012A), State.AUTHED);// 2.1
        addPacket(new CM_GROUP_LOOT(0x012B), State.IN_GAME);// 2.7
        addPacket(new CM_MAC_ADDRESS(0x012C), State.CONNECTED, State.AUTHED, State.IN_GAME);// 2.1
        addPacket(new CM_ABYSS_RANKING_PLAYERS(0x012F), State.IN_GAME);// 2.7
        addPacket(new CM_IN_GAME_SHOP_INFO(0x0130), State.IN_GAME);// 2.7
        addPacket(new CM_REPORT_PLAYER(0x0132), State.IN_GAME);// 2.7
		//addPacket(new CM_INSTANCE_CD_REQUEST(0x0133), State.IN_GAME);//  2.7 -- NEW!
        addPacket(new CM_NAME_CHANGE(0x0134), State.IN_GAME);// 2.7
        addPacket(new CM_SHOW_MAP(0x0137), State.IN_GAME);// 2.7
        addPacket(new CM_SUMMON_MOVE(0x0138), State.IN_GAME);// 2.7
        addPacket(new CM_SUMMON_EMOTION(0x0139), State.IN_GAME);// 2.7
		//addPacket(new CM_DREDGION_REQUEST(0x013B), State.IN_GAME); //2.7 -- NEW!
        addPacket(new CM_SUMMON_CASTSPELL(0x013C), State.IN_GAME);// 2.7
        addPacket(new CM_FUSION_WEAPONS(0x013D), State.IN_GAME);// 2.7
        addPacket(new CM_SUMMON_ATTACK(0x013E), State.IN_GAME);// 2.7
        addPacket(new CM_PLAY_MOVIE_END(0x0140), State.IN_GAME);// 2.7
        addPacket(new CM_DELETE_QUEST(0x0143), State.IN_GAME);// 2.7
        addPacket(new CM_ITEM_REMODEL(0x0149), State.IN_GAME);// 2.7
		addPacket(new CM_NPC_TRADE(0x014B), State.IN_GAME);// New 2.7
        addPacket(new CM_GODSTONE_SOCKET(0x014E), State.IN_GAME);// 2.7
        addPacket(new CM_INVITE_TO_GROUP(0x0150), State.IN_GAME);// 2.7
        addPacket(new CM_ALLIANCE_GROUP_CHANGE(0x0152), State.IN_GAME);// 1.9 OLD
        addPacket(new CM_PLAYER_STATUS_INFO(0x0153), State.IN_GAME);// 2.7
        addPacket(new CM_VIEW_PLAYER_DETAILS(0x0157), State.IN_GAME);// 2.7
        addPacket(new CM_PING_REQUEST(0x015A), State.IN_GAME);// 2.7
        addPacket(new CM_SHOW_FRIENDLIST(0x015D), State.IN_GAME);// 2.7
        addPacket(new CM_CLIENT_COMMAND_ROLL(0x015E), State.IN_GAME);// 2.7
        addPacket(new CM_GROUP_DISTRIBUTION(0x015F), State.IN_GAME);// 2.7
        addPacket(new CM_DUEL_REQUEST(0x0161), State.IN_GAME);// 2.7
        addPacket(new CM_FRIEND_ADD(0x0162), State.IN_GAME);// 2.7
        addPacket(new CM_FRIEND_DEL(0x0163), State.IN_GAME);// 2.7
        addPacket(new CM_ABYSS_RANKING_LEGIONS(0x0165), State.IN_GAME);// 2.7
        addPacket(new CM_DELETE_ITEM(0x0167), State.IN_GAME);// 2.7
        addPacket(new CM_SUMMON_COMMAND(0x0168), State.IN_GAME);// 2.7
        addPacket(new CM_PRIVATE_STORE(0x016A), State.IN_GAME);// 2.7
        addPacket(new CM_PRIVATE_STORE_NAME(0x016B), State.IN_GAME);// 2.7
        addPacket(new CM_BROKER_REGISTERED(0x016C), State.IN_GAME);// 2.7
        addPacket(new CM_BUY_BROKER_ITEM(0x016D), State.IN_GAME);// 2.7
        addPacket(new CM_BROKER_LIST(0x016E), State.IN_GAME);// 2.7
		addPacket(new CM_BROKER_SEARCH(0x016F), State.IN_GAME);//2.7 -- NEW!
        addPacket(new CM_BROKER_SETTLE_LIST(0x0170), State.IN_GAME);// 2.7
        addPacket(new CM_BROKER_SETTLE_ACCOUNT(0x0171), State.IN_GAME);// 2.7
        addPacket(new CM_REGISTER_BROKER_ITEM(0x0172), State.IN_GAME);// 2.7
        addPacket(new CM_BROKER_CANCEL_REGISTERED(0x0173), State.IN_GAME);// 2.7
		addPacket(new CM_OPEN_MAIL_WINDOW(0x0174), State.IN_GAME);//2.7 
        addPacket(new CM_READ_MAIL(0x0175), State.IN_GAME);// 2.7
        addPacket(new CM_SEND_MAIL(0x0177), State.IN_GAME);// 2.7
        addPacket(new CM_DELETE_MAIL(0x0178), State.IN_GAME);// 2.7
        addPacket(new CM_GET_MAIL_ATTACHMENT(0x017B), State.IN_GAME);// 2.7
        addPacket(new CM_CRAFT(0x017C), State.IN_GAME);// 2.7
        addPacket(new CM_CLIENT_COMMAND_LOC(0x017D), State.IN_GAME);// 2.7
        addPacket(new CM_TITLE_SET(0x017E), State.IN_GAME);//2.7
        addPacket(new CM_TIME_CHECK(0x0081), State.CONNECTED, State.AUTHED, State.IN_GAME);// 2.7
		// Unknown 2.7 - 82 00 57 7D FF (After Channel Change or Spawn, before CM_LEVEL_READY)
        addPacket(new CM_LEGION_EMBLEM(0x0083), State.IN_GAME);// 2.7
        addPacket(new CM_PET_MOVE(0x0084), State.IN_GAME);//2.7
        addPacket(new CM_PET(0x0085), State.IN_GAME);// 2.7
        addPacket(new CM_GATHER(0x0086), State.IN_GAME);// 2.7
        addPacket(new CM_PETITION(0x0089), State.IN_GAME);// 2.7
        addPacket(new CM_OPEN_STATICDOOR(0x008A), State.IN_GAME);// 2.7
        addPacket(new CM_CHAT_MESSAGE_PUBLIC(0x008E), State.IN_GAME);// 2.7
        addPacket(new CM_CHAT_MESSAGE_WHISPER(0x008F), State.IN_GAME);// 2.7
        addPacket(new CM_CASTSPELL(0x0090), State.IN_GAME);// 2.7
        addPacket(new CM_SKILL_DEACTIVATE(0x0091), State.IN_GAME);// 2.7
        addPacket(new CM_TARGET_SELECT(0x0092), State.IN_GAME);// 2.7
        addPacket(new CM_ATTACK(0x0093), State.IN_GAME);// 2.7
        addPacket(new CM_USE_ITEM(0x0094), State.IN_GAME);// 2.7
        addPacket(new CM_EQUIP_ITEM(0x0095), State.IN_GAME);// 2.7
        addPacket(new CM_REMOVE_ALTERED_STATE(0x0096), State.IN_GAME);// 2.7
		addPacket(new CM_PLAYER_LISTENER(0x009B), State.IN_GAME);// 2.7
        addPacket(new CM_LEGION(0x009C), State.IN_GAME);// 2.7
		//addPacket(new CM_EXIT_LOCATION(0x9D), State.IN_GAME);// 2.1
        addPacket(new CM_EMOTION(0x009E), State.IN_GAME);// 2.7
        addPacket(new CM_PING(0x009F), State.AUTHED, State.IN_GAME);// 2.7
        addPacket(new CM_FLIGHT_TELEPORT(0x00A0), State.IN_GAME);// 2.7
        addPacket(new CM_QUESTION_RESPONSE(0x00A1), State.IN_GAME);// 2.7
        addPacket(new CM_LEGION_EMBLEM_SEND(0x00A2), State.IN_GAME);// 2.7
        addPacket(new CM_MOVE(0x00A3), State.IN_GAME);// 2.7
        addPacket(new CM_CLOSE_DIALOG(0x00A4), State.IN_GAME);// 2.7
        addPacket(new CM_DIALOG_SELECT(0x00A5), State.IN_GAME);// 2.7
        addPacket(new CM_BUY_ITEM(0x00A6), State.IN_GAME);// 2.7
        addPacket(new CM_SHOW_DIALOG(0x00A7), State.IN_GAME);// 2.7
        addPacket(new CM_SET_NOTE(0x00A9), State.IN_GAME);// 2.7
        addPacket(new CM_LEGION_TABS(0x00AA), State.IN_GAME);// 2.7
		addPacket(new CM_CHAT_RECRUIT_GROUP(0x00AC), State.IN_GAME);//  2.7 
        addPacket(new CM_LEGION_MODIFY_EMBLEM(0x00AE), State.IN_GAME);// 2.7
        addPacket(new CM_EXCHANGE_ADD_KINAH(0x00B1), State.IN_GAME);// 2.7
        addPacket(new CM_EXCHANGE_REQUEST(0x00B2), State.IN_GAME);// 2.7
        addPacket(new CM_EXCHANGE_ADD_ITEM(0x00B3), State.IN_GAME);// 2.7
        addPacket(new CM_EXCHANGE_CANCEL(0x02B4), State.IN_GAME);// 2.7
        addPacket(new CM_WINDSTREAM(0x02B5), State.IN_GAME);// 2.7
        addPacket(new CM_EXCHANGE_LOCK(0x02B6), State.IN_GAME);// 2.7
        addPacket(new CM_EXCHANGE_OK(0x02B7), State.IN_GAME);//2.7
        addPacket(new CM_MANASTONE(0x02B9), State.IN_GAME);// 2.7
		addPacket(new CM_MOTION(0x02BA), State.IN_GAME);// 2.7 Motion
        addPacket(new CM_FIND_GROUP(0x02BC), State.IN_GAME);// 2.7
        addPacket(new CM_CHARACTER_PASSKEY(0x01C0), State.AUTHED);// 2.7
        addPacket(new CM_BREAK_WEAPONS(0x01C2), State.IN_GAME);// 2.7
        addPacket(new CM_DISCONNECT(0x0118), State.IN_GAME);// 2.7 Testing
        // Unknown 2.6 - 0xC7 Opcode Only, No Data Gets sent by client after SM_MAIL_SERVICE, every time. Server reply is unknown 95
        addPacket(new CM_VERSION_CHECK(0x00F3), State.CONNECTED);// 2.7
        addPacket(new CM_REVIVE(0x00F4), State.IN_GAME);// 2.7
        addPacket(new CM_QUIT(0x00F6), State.AUTHED, State.IN_GAME);// 2.7
        addPacket(new CM_MAY_QUIT(0x00F7), State.AUTHED, State.IN_GAME);// 2.7
        addPacket(new CM_LEVEL_READY(0x00F8), State.IN_GAME);// 2.7
        addPacket(new CM_UI_SETTINGS(0x00F9), State.IN_GAME);// 2.7
		addPacket(new CM_CHARACTER_EDIT(0x00FA), State.AUTHED);// 2.7
        addPacket(new CM_ENTER_WORLD(0x00FB), State.AUTHED);// 2.7
        addPacket(new CM_OBJECT_SEARCH(0x00FE), State.IN_GAME);// 2.7
        addPacket(new CM_CUSTOM_SETTINGS(0x00FF), State.IN_GAME);// 2.7

        addPacket(new CM_GROUP_RESPONSE(0x32), State.IN_GAME);// 2.1
        // addPacket(new CM_REFRESH_NAME(0x37), State.IN_GAME);// 2.1
        // addPacket(new CM_REQUEST_ENTRY(0x3A), State.IN_GAME);// 2.1 (not implemented yet)
        addPacket(new CM_LEGION_MODIFY_EMBLEM(0xA9), State.IN_GAME);// 2.1
        // addPacket(new CM_TWITTER_ADDON(0xAC), State.IN_GAME);// 2.1
        // addPacket(new CM_CHARSELECT_TIMER(0xBF), State.IN_GAME);// 2.1 (not implemented yet)
	}
		
    public AionPacketHandler getPacketHandler() {
        return handler;
    }

    private void addPacket(AionClientPacket prototype, State... states) {
        handler.addPacketPrototype(prototype, states);
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final AionPacketHandlerFactory instance = new AionPacketHandlerFactory();
    }
}
