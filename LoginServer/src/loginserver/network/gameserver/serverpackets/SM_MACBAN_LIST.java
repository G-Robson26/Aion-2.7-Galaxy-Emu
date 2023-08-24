package loginserver.network.gameserver.serverpackets;

import java.nio.ByteBuffer;
import java.util.Map;

import loginserver.controller.BannedMacManager;
import loginserver.model.BannedMacEntry;
import loginserver.network.gameserver.GsConnection;
import loginserver.network.gameserver.GsServerPacket;

/**
 * 
 * @author KID
 * @author drsaluml
 *
 */
public class SM_MACBAN_LIST extends GsServerPacket {

	private Map<String, BannedMacEntry> bannedList;
	
	public SM_MACBAN_LIST()
	{ 	
		super(0x09);
		this.bannedList = BannedMacManager.getInstance().getMap();
	}
	
	@Override
	protected void writeImpl(GsConnection con, ByteBuffer buf) {
		writeC(buf,getOpcode());
		writeD(buf,bannedList.size());
		
		for(BannedMacEntry entry : bannedList.values())
		{
			writeS(buf,entry.getMac());
			writeQ(buf,entry.getTime().getTime());
			writeS(buf,entry.getDetails());
		}
	}
}
