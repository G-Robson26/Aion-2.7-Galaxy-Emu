package loginserver.network.gameserver.clientpackets;

import java.nio.ByteBuffer;

import loginserver.controller.BannedMacManager;
import loginserver.network.gameserver.GsClientPacket;
import loginserver.network.gameserver.GsConnection;

/**
 * 
 * @author KID
 * @author drsaluml
 *
 */
public class CM_MACBAN_CONTROL  extends GsClientPacket {
	private byte type;
	private int playerId;
	private String address;
	private String details;
	private long time;

	public CM_MACBAN_CONTROL(ByteBuffer buf, GsConnection client)
	{
		super(buf, client, 0x11);
	}
	
	@Override
	protected void readImpl() {
		type = (byte) readC();
		playerId = readD();
		address = readS();
		details = readS();
		time = readQ();
	}
	
	@Override
	protected void runImpl() {
		BannedMacManager bmm = BannedMacManager.getInstance();
		switch(type)
		{
			case 0://unban
				bmm.unban(address, details);
				break;
			case 1://ban
				bmm.ban(address,playerId, time, details);
				break;
		}
	}
}
