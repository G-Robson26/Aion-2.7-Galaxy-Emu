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
package gameserver.dataholders;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * An instance of this class is the result of data loading.
 *
 * @author Luno, orz, resurrected , Mcrizza
 */
@XmlRootElement(name = "ae_static_data")
@XmlAccessorType(XmlAccessType.NONE)
public class StaticData {
    @XmlElement(name = "world_maps")
    public WorldMapsData worldMapsData;

    @XmlElement(name = "npc_trade_list")
    public TradeListData tradeListData;

    @XmlElement(name = "npc_teleporter")
    public TeleporterData teleporterData;

    @XmlElement(name = "teleport_location")
    public TeleLocationData teleLocationData;

	@XmlElement(name = "custom_presets")
	public PresetData	customPresets;
	
    @XmlElement(name = "bind_points")
    public BindPointData bindPointData;

    @XmlElement(name = "quests")
    public QuestsData questData;

    @XmlElement(name = "bonuses")
    public BonusData bonusData;

    @XmlElement(name = "quest_scripts")
    public QuestScriptsData questsScriptData;

    @XmlElement(name = "player_experience_table")
    public PlayerExperienceTable playerExperienceTable;

    @XmlElement(name = "player_stats_templates")
    public PlayerStatsData playerStatsData;

    @XmlElement(name = "summon_stats_templates")
    public SummonStatsData summonStatsData;

    @XmlElement(name = "item_templates")
    public ItemData itemData;
	
	@XmlElement(name = "pet_feed")
	public PetFeedData	petFeedData;
	
	@XmlElement(name = "windstreams")
	public WindstreamData windstreamsData;

    @XmlElement(name = "npc_templates")
    public NpcData npcData;

    @XmlElement(name = "player_initial_data")
    public PlayerInitialData playerInitialData;

    @XmlElement(name = "skill_data")
    public SkillData skillData;

    @XmlElement(name = "skill_tree")
    public SkillTreeData skillTreeData;

    @XmlElement(name = "cube_expander")
    public CubeExpandData cubeExpandData;

    @XmlElement(name = "warehouse_expander")
    public WarehouseExpandData warehouseExpandData;

    @XmlElement(name = "player_titles")
    public TitleData titleData;

    @XmlElement(name = "gatherable_templates")
    public GatherableData gatherableData;
	
	@XmlElement(name = "guild_templates")
	public GuildsData	guildsData;

    @XmlElement(name = "npc_walker")
    public WalkerData walkerData;

    @XmlElement(name = "zones")
    public ZoneData zoneData;

    @XmlElement(name = "flight_zones")
    public FlightZoneData flightZoneData;

    @XmlElement(name = "goodslists")
    public GoodsListData goodsListData;
	
	@XmlElement(name = "tradeingoodslists")
    public TradeInGoodsListData tradeInGoodsListData;

    @XmlElement(name = "spawns")
    public SpawnsData spawnsData;

    @XmlElement(name = "tribe_relations")
    public TribeRelationsData tribeRelationsData;

    @XmlElement(name = "recipe_templates")
    public RecipeData recipeData;

    @XmlElement(name = "portal_templates")
    public PortalData portalData;
	
	@XmlElement(name = "chest_templates")
	public ChestData chestData;

	@XmlElement(name = "staticdoor_templates")
	public StaticDoorData staticDoorData;
	
    @XmlElement(name = "item_sets")
    public ItemSetData itemSetData;

    @XmlElement(name = "npc_skill_templates")
    public NpcSkillData npcSkillData;

    @XmlElement(name = "pet_skill_templates")
    public PetSkillData petSkillData;

    @XmlElement(name = "siege_locations")
    public SiegeLocationData siegeLocationData;

    @XmlElement(name = "siege_spawns")
    public SiegeSpawnData siegeSpawnData;

    @XmlElement(name = "shields")
    public ShieldData shieldData;

    @XmlElement(name = "fly_rings")
    public FlyRingData flyRingData;

    @XmlElement(name = "pets")
    public PetData petData;
	
	@XmlElement(name = "roads")
    public RoadData roadData;
	
	@XmlElement(name = "droplist")
	public DroplistData	droplistData;
	
	@XmlElement(name = "npc_shouts")
	public NpcShoutsData npcShoutsData;
	
    @XmlElement(name = "compressed_items")
    public CompressedItemData compressedItemData;
		
	@XmlElement(name = "levelup_surveys")
	public LevelUpSurveyData levelUpSurveys;
	
	@XmlElement(name="academy_shigo_spawns")
	public AcademyShigoData academyShigoData;

	@XmlElement(name="academy_teleports")
	public AcademyTeleportData academyTeleportData;
	
	@XmlElement(name="academy_spawns")
	public AcademyData academyData;
	
	@XmlElement(name="academy_box_spawns")
	public AcademyBoxData academyBoxData;
	
	@XmlElement(name="academy_spawns_solo")
	public AcademySoloData academySoloData;
	
	
    // JAXB callback

    @SuppressWarnings("unused")
    private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        DataManager.log.info("Loaded world maps data: " + worldMapsData.size() + " maps");
        DataManager.log.info("Loaded player exp table: " + playerExperienceTable.getMaxLevel() + " levels");
        DataManager.log.info("Loaded " + playerStatsData.size() + " player stat templates");
        DataManager.log.info("Loaded " + summonStatsData.size() + " summon stat templates");
        DataManager.log.info("Loaded " + itemData.size() + " item templates");
		DataManager.log.info("Loaded " + petFeedData.size() + " pet flavours");
        DataManager.log.info("Loaded " + npcData.size() + " npc templates");
        DataManager.log.info("Loaded " + playerInitialData.size() + " initial player templates");
        DataManager.log.info("Loaded " + tradeListData.size() + " trade lists");
        DataManager.log.info("Loaded " + teleporterData.size() + " npc teleporter templates");
        DataManager.log.info("Loaded " + teleLocationData.size() + " teleport locations");
        DataManager.log.info("Loaded " + customPresets.size() + " preset entries");
		DataManager.log.info("Loaded " + skillData.size() + " skill templates");
        DataManager.log.info("Loaded " + skillTreeData.size() + " skill learn entries");
        DataManager.log.info("Loaded " + cubeExpandData.size() + " cube expand entries");
        DataManager.log.info("Loaded " + warehouseExpandData.size() + " warehouse expand entries");
        DataManager.log.info("Loaded " + bindPointData.size() + " bind point entries");
        DataManager.log.info("Loaded " + questData.size() + " quest data entries");
        DataManager.log.info("Loaded " + bonusData.size() + " quest bonus data entries");
        DataManager.log.info("Loaded " + gatherableData.size() + " gatherable entries");
        DataManager.log.info("Loaded " + guildsData.size() + " guild entries");
		DataManager.log.info("Loaded " + titleData.size() + " title entries");
        DataManager.log.info("Loaded " + walkerData.size() + " walker routes");
        DataManager.log.info("Loaded " + zoneData.size() + " zone entries");
        DataManager.log.info("Loaded " + flightZoneData.size() + " flightzone entries");
        DataManager.log.info("Loaded " + goodsListData.size() + " goodslist entries");
		DataManager.log.info("Loaded " + tradeInGoodsListData.size() + " trade in goodslist entries");
        DataManager.log.info("Loaded " + spawnsData.size() + " spawn entries");
        DataManager.log.info("Loaded " + tribeRelationsData.size() + " tribe relation entries");
        DataManager.log.info("Loaded " + recipeData.size() + " recipe entries");
        DataManager.log.info("Loaded " + portalData.size() + " portal entries");
		DataManager.log.info("Loaded " + chestData.size() + " chest entries");
		DataManager.log.info("Loaded " + staticDoorData.size() + " static door locations");
        DataManager.log.info("Loaded " + itemSetData.size() + " item set entries");
        DataManager.log.info("Loaded " + npcSkillData.size() + " npc skill list entries");
        DataManager.log.info("Loaded " + petSkillData.size() + " pet skill list entries");
        DataManager.log.info("Loaded " + siegeLocationData.size() + " siege location entries");
        DataManager.log.info("Loaded " + siegeSpawnData.size() + " siege spawn entries");
        DataManager.log.info("Loaded " + shieldData.size() + " shield entries");
        DataManager.log.info("Loaded " + flyRingData.size() + " fly ring entries");
        DataManager.log.info("Loaded " + petData.size() + " pet entries");
		DataManager.log.info("Loaded " + roadData.size() + " road entries");
		DataManager.log.info("Loaded " + windstreamsData.size() + " windstream entries");
        DataManager.log.info("Loaded " + compressedItemData.size() + " compressed item entries");
		DataManager.log.info("Loaded " + droplistData.size() + " npc drops");
		DataManager.log.info("Loaded " + npcShoutsData.size() + " npc shouts");
		DataManager.log.info("Loaded " + levelUpSurveys.size() + " level up surveys");
		DataManager.log.info("Loaded " + academyShigoData.size() + " academy shigo spaws");
		DataManager.log.info("Loaded " + academyTeleportData.size() + " academy teleports locations");
		DataManager.log.info("Loaded " + academyData.size() + " academy spaws");
		DataManager.log.info("Loaded " + academyBoxData.size() + " academy box spaws");
		DataManager.log.info("Loaded " + academySoloData.size() + " academy solo spaws");
	}
}
