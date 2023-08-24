package admincommands;

import com.aionemu.commons.database.dao.DAOManager;

import gameserver.configs.administration.AdminConfig;
import gameserver.dao.PlayerDAO;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.loginserver.BannedMacManager;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.utils.chathandlers.AdminCommand;
import gameserver.utils.i18n.CustomMessageId;
import gameserver.utils.i18n.LanguageHandler;
import gameserver.utils.Util;

/**
 * @author KID
 * @author drsaluml
 */
public class UnBanMac extends AdminCommand {

	public UnBanMac() {
		super("unbanmac");
	}
	
	@Override
    public void executeCommand(final Player admin, String[] params)
    {
        if(admin.getAccessLevel() < AdminConfig.COMMAND_BANMAC){
        	PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.COMMAND_NOT_ENOUGH_RIGHTS));
            return;
        }
        
		if (params.length <= 0) {
				PacketSendUtility.sendMessage(admin, "Syntax: //unbanmac <player>");
				return;
		}
		String name = Util.convertName(params[0]);
		final int playerId = DAOManager.getDAO(PlayerDAO.class).getPlayerIdByName(name);
		
		if(playerId > 0)
		BannedMacManager.getInstance().getMacAddress(playerId);
		
		
		ThreadPoolManager.getInstance().schedule(new Runnable(){
			@Override
			public void run()
			{
				String macAddress = BannedMacManager.getInstance().loadMacAddress();
				
				if(macAddress != null){
					boolean result = BannedMacManager.getInstance().unbanAddress(macAddress,playerId,
						"uban;mac=" + macAddress + ", playerId : " + playerId + "; admin=" + admin.getName());
					if (result)
						PacketSendUtility.sendMessage(admin, "mac " + macAddress + " has unbanned");
					else
						PacketSendUtility.sendMessage(admin, "mac " + macAddress + " is not banned");
				}else
				{
					PacketSendUtility.sendMessage(admin, "Mac Address was not found!");
				}
			}
		}, 1000);
    }
}



