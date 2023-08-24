package gameserver.network.aion.serverpackets;

import gameserver.model.gameobjects.player.Player;
import gameserver.model.group.PlayerGroup;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;


import java.nio.ByteBuffer;


/**
 * 
 * @author Dns, ginho1
 * 
 */
public class SM_INSTANCE_SCORE extends AionServerPacket
{
	private int		mapId;
	private int		instanceTime;
	private int		stopTime;
	private int		totalPoints;
	private int		points;
	private int		kills;
	private int		rank;
	private int		signs;
	private PlayerGroup	elyosGroup;
	private PlayerGroup	asmosGroup;
	private PlayerGroup	registeredGroup;
	private boolean		showRank;

	public SM_INSTANCE_SCORE(int mapId, int instanceTime, int stopTime, int totalPoints, int points, int kills, int rank)
	{
		this.mapId = mapId; // 300040000
		this.instanceTime = instanceTime; // 3h30
		this.stopTime = stopTime; // 2097152 for running time, 
		this.totalPoints = totalPoints; // Total score value
		this.points = points; // Hunted value
		this.kills = kills; // Collection value
		this.rank = rank; // 7 for none, 8 for F, 5 for D, 4 C, 3 B, 2 A, 1 S
	}

	public SM_INSTANCE_SCORE(int mapId, int instanceTime, PlayerGroup elyosGroup, PlayerGroup asmosGroup, boolean showRank)
	{
		this.mapId = mapId;
		this.instanceTime = instanceTime;
		this.elyosGroup = elyosGroup;
		this.asmosGroup = asmosGroup;
		this.showRank = showRank;
	}
	
	public SM_INSTANCE_SCORE(int mapId, int instanceTime, PlayerGroup registeredGroup, int points, int signs, boolean showRank)
	{
		this.mapId = mapId;
		this.instanceTime = instanceTime;
		this.registeredGroup = registeredGroup;
		this.points = points; // Hunted value
		this.signs = signs;
		this.showRank = showRank;
	}
	
	//academy
	public SM_INSTANCE_SCORE(int i, int j, PlayerGroup playergroup, boolean flag)
    {
        mapId = i;
        instanceTime = j;
        registeredGroup = playergroup;
        showRank = flag;
    }

}