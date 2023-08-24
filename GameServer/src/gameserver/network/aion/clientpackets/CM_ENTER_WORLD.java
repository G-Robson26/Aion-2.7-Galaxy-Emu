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

package gameserver.network.aion.clientpackets;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.versionning.Version;
import gameserver.GameServer;
import gameserver.cache.HTMLCache;
import gameserver.configs.main.CustomConfig;
import gameserver.configs.main.GSConfig;
import gameserver.configs.main.RateConfig;
import gameserver.dao.PlayerPasskeyDAO;
import gameserver.dataholders.DataManager;
import gameserver.model.ChatType;
import gameserver.model.EmotionType;
import gameserver.model.account.Account;
import gameserver.model.account.CharacterPasskey.ConnectType;
import gameserver.model.account.PlayerAccountData;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.Storage;
import gameserver.model.gameobjects.state.CreatureVisualState;
import gameserver.model.gameobjects.stats.StatEnum;
import gameserver.model.templates.GuildTemplate;
import gameserver.model.templates.QuestTemplate;
import gameserver.network.aion.AionClientPacket;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.serverpackets.*;
import gameserver.questEngine.QuestEngine;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.*;
import gameserver.skillengine.effect.EffectId;
import gameserver.utils.AEVersions;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.i18n.CustomMessageId;
import gameserver.utils.i18n.LanguageHandler;
import gameserver.utils.rates.Rates;
import gameserver.world.Executor;
import gameserver.world.World;

import java.util.Iterator;
import java.util.List;

/**
 * In this packets aion client is asking if given char [by oid] may login into game [ie start playing].
 *
 * @author -Nemesiss-, Avol, Ares/Kaipo, Vyaslav
 */
public class CM_ENTER_WORLD extends AionClientPacket {
    /**
     * Object Id of player that is entering world
     */
    private int objectId;

    private static final Version gs = new Version(GameServer.class);
    private static String serverMessage;
    private static String serverMessageRegular;
    private static String serverMessagePremium;
	private static String serverMessageVip;


    /**
     * Constructs new instance of <tt>CM_ENTER_WORLD </tt> packet
     *
     * @param opcode
     */
    public CM_ENTER_WORLD(int opcode) {
        super(opcode);
    }

    static {
        String bufferDisplayRev = null;

        if (GSConfig.SERVER_MOTD_DISPLAYREV)
            bufferDisplayRev = LanguageHandler.translate(CustomMessageId.SERVER_REVISION, new Version(GameServer.class).getRevision());

        if (RateConfig.DISPLAY_RATE) {
            String bufferRegular = LanguageHandler.translate(CustomMessageId.WELCOME_REGULAR, GSConfig.SERVER_NAME, RateConfig.XP_RATE, RateConfig.QUEST_XP_RATE, RateConfig.DROP_RATE);
            String bufferPremium = LanguageHandler.translate(CustomMessageId.WELCOME_PREMIUM, GSConfig.SERVER_NAME, RateConfig.PREMIUM_XP_RATE, RateConfig.PREMIUM_QUEST_XP_RATE, RateConfig.PREMIUM_DROP_RATE);
			String bufferVip = LanguageHandler.translate(CustomMessageId.WELCOME_VIP, GSConfig.SERVER_NAME, RateConfig.VIP_XP_RATE, RateConfig.VIP_QUEST_XP_RATE, RateConfig.VIP_DROP_RATE);

            if (bufferDisplayRev != null) {
                bufferRegular += bufferDisplayRev;
                bufferPremium += bufferDisplayRev;
				bufferVip += bufferDisplayRev;
            }

            serverMessageRegular = bufferRegular;
            bufferRegular = null;

            serverMessagePremium = bufferPremium;
            bufferPremium = null;
			
			serverMessageVip = bufferVip;
                    bufferVip = null;
        } else {
            String buffer = LanguageHandler.translate(CustomMessageId.WELCOME_BASIC, GSConfig.SERVER_NAME);

            if (bufferDisplayRev != null)
                buffer += bufferDisplayRev;

            serverMessage = buffer;
            buffer = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        objectId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        AionConnection client = getConnection();

        // passkey check
        if (CustomConfig.PASSKEY_ENABLE && !client.getAccount().getCharacterPasskey().isPass()) {
            client.getAccount().getCharacterPasskey().setConnectType(ConnectType.ENTER);
            client.getAccount().getCharacterPasskey().setObjectId(objectId);
            boolean isExistPasskey = DAOManager.getDAO(PlayerPasskeyDAO.class).existCheckPlayerPasskey(client.getAccount().getId());

            if (!isExistPasskey)
                client.sendPacket(new SM_CHARACTER_SELECT(0));
            else
                client.sendPacket(new SM_CHARACTER_SELECT(1));
        } else
            enterWorld(client, objectId);
    }

    public static void enterWorld(AionConnection client, int objectId) {
        Account account = client.getAccount();
        PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(objectId);

        if (playerAccData == null) {
            // Somebody wanted to login on character that is not at his account
            return;
        }

        Player player = PlayerService.getPlayer(objectId, account);

        if (player != null && client.setActivePlayer(player)) {
            player.setClientConnection(client);
            /*
                * Store player into World.
                */
            Player player2 = World.getInstance().findPlayer(player.getObjectId());
            if (player2 != null)
                World.getInstance().removeObject(player2);
            World.getInstance().storeObject(player);

            PlayerService.playerLoggedIn(player);

            StigmaService.onPlayerLogin(player);
            client.sendPacket(new SM_SKILL_LIST(player));

            if (player.getSkillCoolDowns() != null)
                client.sendPacket(new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));

            if (player.getItemCoolDowns() != null)
                client.sendPacket(new SM_ITEM_COOLDOWN(player.getItemCoolDowns()));

            client.sendPacket(new SM_QUEST_LIST(player));
            client.sendPacket(new SM_STARTED_QUEST_LIST(player)); // 2.1 new packet :)
            client.sendPacket(new SM_RECIPE_LIST(player.getRecipeList().getRecipeList()));

            /*
                * Needed
                */
            client.sendPacket(new SM_ENTER_WORLD_CHECK());

            byte[] uiSettings = player.getPlayerSettings().getUiSettings();
            byte[] shortcuts = player.getPlayerSettings().getShortcuts();

            if (uiSettings != null)
                client.sendPacket(new SM_UI_SETTINGS(uiSettings, 0));

            if (shortcuts != null)
                client.sendPacket(new SM_UI_SETTINGS(shortcuts, 1));

            // Cubesize limit set in inventory.
            int cubeSize = player.getCubeSize();
            player.getInventory().setLimit(27 + cubeSize * 9);

            // items
            Storage inventory = player.getInventory();
            List<Item> equipedItems = player.getEquipment().getEquippedItems();
            if (equipedItems.size() != 0) {
                client.sendPacket(new SM_INVENTORY_INFO(player.getEquipment().getEquippedItems(), cubeSize));
            }

            List<Item> unequipedItems = inventory.getAllItems();
            int itemsSize = unequipedItems.size();

            if (itemsSize != 0) {
                int index = 0;
                while (index + 10 < itemsSize) {
                    client.sendPacket(new SM_INVENTORY_INFO(unequipedItems.subList(index, index + 10), cubeSize));
                    index += 10;
                }
                client.sendPacket(new SM_INVENTORY_INFO(unequipedItems.subList(index, itemsSize), cubeSize));
            }

            client.sendPacket(new SM_INVENTORY_INFO());
			
			client.sendPacket(new SM_MOTION(player, true)); // Load Motion

            client.sendPacket(new SM_STATS_INFO(player));

            client.sendPacket(new SM_CUBE_UPDATE(player, 6, player.getCommonData().getAdvancedStigmaSlotSize()));

            KiskService.onLogin(player);
            TeleportService.sendSetBindPoint(player);

            // Alliance Packet after SetBindPoint
            if (player.isInAlliance())
                AllianceService.getInstance().onLogin(player);

            client.sendPacket(new SM_PLAYER_ID(player));

            client.sendPacket(new SM_MACRO_LIST(player));
            client.sendPacket(new SM_GAME_TIME());
            player.getController().updateNearbyQuests();

            client.sendPacket(new SM_TITLE_LIST(player));
            client.sendPacket(new SM_CHANNEL_INFO(player.getPosition()));
            client.sendPacket(new SM_PLAYER_SPAWN(player));
            client.sendPacket(new SM_EMOTION_LIST(player));
            client.sendPacket(new SM_INFLUENCE_RATIO());
            client.sendPacket(new SM_SIEGE_LOCATION_INFO());
            client.sendPacket(new SM_RIFT_ANNOUNCE(player.getCommonData().getRace()));
            client.sendPacket(new SM_PRICES(player.getPrices()));
            client.sendPacket(new SM_ABYSS_RANK(player.getAbyssRank()));



            if(serverMessage != null)
                        {
                            client.sendPacket(new SM_MESSAGE(0, null, serverMessage,
				ChatType.ANNOUNCEMENTS));
                    } else {
                       if(client.getAccount().getMembership()==1)
                            {
                                client.sendPacket(new SM_MESSAGE(0, null, serverMessagePremium,
                                    ChatType.ANNOUNCEMENTS));
                        }else{
                            if(client.getAccount().getMembership()==2)
                            {
                                client.sendPacket(new SM_MESSAGE(0, null, serverMessageVip,
                                    ChatType.ANNOUNCEMENTS));
							} else {
                                client.sendPacket(new SM_MESSAGE(0, null, serverMessageRegular,
                                    ChatType.ANNOUNCEMENTS));
							}
                        }
					}

            if (player.isInPrison())
                PunishmentService.updatePrisonStatus(player);

            if (player.isLegionMember())
                LegionService.getInstance().onLogin(player);

            if (player.isInGroup())
                GroupService.getInstance().onLogin(player);

            player.setRates(Rates.getRatesFor(client.getAccount().getMembership()));

            ToyPetService.getInstance().onPlayerLogin(player);

            /**
             * Announce on GM connection
             */
            if (CustomConfig.ANNOUNCE_GM_CONNECTION) {
                if (player.isGM()) {
                    String playerName = "";

                    if (CustomConfig.GMTAG_DISPLAY) {
                        if (player.getAccessLevel() == 1) {
                            playerName += CustomConfig.GM_LEVEL1.trim();
                        } else if (player.getAccessLevel() == 2) {
                            playerName += CustomConfig.GM_LEVEL2.trim();
                        } else if (player.getAccessLevel() == 3) {
                            playerName += CustomConfig.GM_LEVEL3.trim();
                        } else if (player.getAccessLevel() == 4) {
                            playerName += CustomConfig.GM_LEVEL4.trim();
                        } else if (player.getAccessLevel() == 5) {
                            playerName += CustomConfig.GM_LEVEL5.trim();
                        }
                    }

                    playerName += player.getName();

                    final String _playerName = playerName;

                    World.getInstance().doOnAllPlayers(new Executor<Player>() {
                        @Override
                        public boolean run(Player p) {
                            PacketSendUtility.sendMessage(p, LanguageHandler.translate(CustomMessageId.ANNOUNCE_GM_CONNECTION, _playerName));
                            return true;
                        }
                    });
                }
            }

            if (player.isGM()) {
                if (CustomConfig.INVIS_GM_CONNECTION) {
                    player.getEffectController().setAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());
                    player.setVisualState(CreatureVisualState.HIDE3);
                    PacketSendUtility.broadcastPacket(player, new SM_PLAYER_STATE(player), true);
                    PacketSendUtility.sendMessage(player, "! YOU LOGGED IN INVISIBLE MODE !");
                }
                if (CustomConfig.INVUL_GM_CONNECTION) {
                    player.setInvul(true);
                    PacketSendUtility.sendMessage(player, "! YOU LOGGED IN INVULNERABLE MODE !");
                }
                if (CustomConfig.SILENCE_GM_CONNECTION) {
                    player.setWhisperable(true);
                    PacketSendUtility.sendMessage(player, "! YOU LOGGED IN SILENT MODE !");
                }
                if (CustomConfig.SPEED_GM_CONNECTION > 0) {
                    int speed = 6000;
                    int flyspeed = 9000;

                    player.getGameStats().setStat(StatEnum.SPEED, (speed + (speed * CustomConfig.SPEED_GM_CONNECTION) / 100));
                    player.getGameStats().setStat(StatEnum.FLY_SPEED, (flyspeed + (flyspeed * CustomConfig.SPEED_GM_CONNECTION) / 100));
                    PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.START_EMOTE2, 0, 0), true);
                    PacketSendUtility.sendMessage(player, "! YOU LOGGED IN SPEED MODE !");
                }
            }

            ClassChangeService.showClassChangeDialog(player);

            /**
             * Notify mail service to load all mails
             */
            MailService.getInstance().onPlayerLogin(player);

            /**
             * Notify player if have broker settled items
             */
            BrokerService.getInstance().onPlayerLogin(player);

            /**
             * Send petition data if player has one
             */
            PetitionService.getInstance().onPlayerLogin(player);

            /**
             * Alert player about currently vulnerable fortresses
             */
            SiegeService.getInstance().onPlayerLogin(player);

            /**
             * Trigger restore services on login.
             */
            player.getLifeStats().updateCurrentStats();

            if (CustomConfig.FIXITEMINFO)
            {
        	List<Item> items = player.getInventory().getAllItems();
        	Iterator<Item> it = items.iterator();

        	if (!items.isEmpty())
        	{
        	    while (it.hasNext())
        	    {
        		Item act = (Item) it.next();
        		PacketSendUtility.sendPacket(player, new SM_UPDATE_ITEM(act));
        	    }
        	}
            }
            if (CustomConfig.ENABLE_HTML_WELCOME)
                HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("welcome.xhtml"));

            if (CustomConfig.ENABLE_SURVEYS)
                HTMLService.onPlayerLogin(player);

			if(player.getGuild().getGuildId() != 0)	{
				int currentQuest = player.getGuild().getCurrentQuest();
				if(currentQuest == 0){
					GuildTemplate guildTemplate = DataManager.GUILDS_DATA.getGuildTemplateByGuildId(player.getGuild().getGuildId());
					GuildService.getInstance().sendDailyQuest(player, guildTemplate);
				}
				else {
					QuestState qs = player.getQuestStateList().getQuestState(currentQuest);
					QuestTemplate template = DataManager.QUEST_DATA.getQuestById(currentQuest);
					if(qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat(template.getMaxRepeatCount()))
						PacketSendUtility.sendPacket(player, new SM_QUEST_ACCEPTED(6, currentQuest));
				}
			}	
				
        } else {
            // TODO this is an client error - inform client.
        }
    }

    @SuppressWarnings("unused")
	private static String[] getWelcomeMessage() {
        return new String[]{
                "Welcome to " + GSConfig.SERVER_NAME + ", powered by Aion Galaxy EMU rev " + AEVersions.getGameRevision(gs),
                "This software is under LGPLv3. See our website for more info: http://www.aiongemu.com"
        };
    }

}