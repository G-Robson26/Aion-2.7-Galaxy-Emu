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
package gameserver.model;

/**
 * This class represents an announcement
 *
 * @author Divinity
 */
public class Announcement {
    private int id;
    private String faction;
    private String announce;
    private String chatType;
    private int delay;

    /**
     * Constructor without the ID of announcement
     *
     * @param announce
     * @param faction
     * @param chatType
     * @param delay
     */
    public Announcement(String announce, String faction, String chatType, int delay) {
        this.announce = announce;

        // Checking the right syntax
        if (!faction.equalsIgnoreCase("ELYOS") && !faction.equalsIgnoreCase("ASMODIANS"))
            faction = "ALL";

        this.faction = faction;
        this.chatType = chatType;
        this.delay = delay;
    }

    /**
     * Constructor with the ID of announcement
     *
     * @param id
     * @param announce
     * @param faction
     * @param chatType
     * @param delay
     */
    public Announcement(int id, String announce, String faction, String chatType, int delay) {
        this.id = id;
        this.announce = announce;

        // Checking the right syntax
        if (!faction.equalsIgnoreCase("ELYOS") && !faction.equalsIgnoreCase("ASMODIANS"))
            faction = "ALL";

        this.faction = faction;
        this.chatType = chatType;
        this.delay = delay;
    }

    /**
     * Return the id of the announcement
     * In case of the id doesn't exist, return -1
     *
     * @return int - Announcement's id
     */
    public int getId() {
        if (id != 0)
            return id;
        else
            return -1;
    }

    /**
     * Return the announcement's text
     *
     * @return String - Announcement's text
     */
    public String getAnnounce() {
        return announce;
    }

    /**
     * Return the announcement's faction in string mode :
     * - ELYOS
     * - ASMODIANS
     * - ALL
     *
     * @return String - Announcement's faction
     */
    public String getFaction() {
        return faction;
    }

    /**
     * Return the announcement's faction in Race enum mode :
     * - Race.ELYOS
     * - Race.ASMODIANS
     *
     * @return Race - Announcement's faction
     */
    public Race getFactionEnum() {
        if (faction.equalsIgnoreCase("ELYOS"))
            return Race.ELYOS;
        else if (faction.equalsIgnoreCase("ASMODIANS"))
            return Race.ASMODIANS;

        return null;
    }

    /**
     * Return the chatType in String mode (for the insert in database)
     *
     * @return String - Announcement's chatType
     */
    public String getType() {
        return chatType;
    }

    /**
     * Return the chatType with the ChatType Enum
     *
     * @return ChatType - Announcement's chatType
     */
    public ChatType getChatType() {
        if (chatType.equalsIgnoreCase("NORMAL"))
            return ChatType.PERIOD_NOTICE;
        else if (chatType.equalsIgnoreCase("YELLOW"))
            return ChatType.ANNOUNCEMENTS;
        else if (chatType.equalsIgnoreCase("SHOUT"))
            return ChatType.SHOUT;
        else if (chatType.equalsIgnoreCase("ORANGE"))
            return ChatType.GROUP_LEADER;
        else
            return ChatType.SYSTEM_NOTICE;
    }

    /**
     * Return the announcement's delay
     *
     * @return int - Announcement's delay
     */
    public int getDelay() {
        return delay;
    }
}
