package loginserver.network.gameserver.clientpackets;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import loginserver.controller.AccountController;
import loginserver.network.gameserver.GsClientPacket;
import loginserver.network.gameserver.GsConnection;

/**
 * 
 * @author nrg
 * @author drsaluml
 *
 */
public class CM_MAC extends GsClientPacket {
	
	private static final Logger	log	= Logger.getLogger(CM_MAC.class);

	private int accountId;
	private String address;

	public CM_MAC(ByteBuffer buf, GsConnection client)
	{
		super(buf, client, 0x10);
	}
	
	@Override
	protected void readImpl() {
		accountId = readD();
		address = readS();
	}
	
	@Override
	protected void runImpl() {
		if(!AccountController.refreshAccountsLastMac(accountId, address))
			log.error("[WARN] We just weren't able to update account_data.last_mac for accountId "+accountId);
	}
}
