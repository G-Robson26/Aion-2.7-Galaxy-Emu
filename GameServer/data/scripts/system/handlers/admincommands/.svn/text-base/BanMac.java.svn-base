package admincommands;

/**
 * @author drsaluml
 * @author Jenose
 *
 */
import gameserver.configs.administration.AdminConfig;
import gameserver.model.gameobjects.VisibleObject;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.loginserver.BannedMacManager;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.chathandlers.AdminCommand;
import gameserver.utils.i18n.CustomMessageId;
import gameserver.utils.i18n.LanguageHandler;

/**
 * @author Jenose
 */
public class BanMac extends AdminCommand {

	public BanMac() {
		super("banmac");
	}

    @Override
    public void executeCommand(Player admin, String[] params)
    {
		int time = 0; // Default: infinity
		String targetName = "direct_type";
		String macAddress = null;
		int targetId = 0;
        if(admin.getAccessLevel() < AdminConfig.COMMAND_BANMAC)
        {
        	PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.COMMAND_NOT_ENOUGH_RIGHTS));
            return;
        }

		VisibleObject target = admin.getTarget();
		if (target != null && target instanceof Player) {
			if (target.getObjectId() == admin.getObjectId()) {
				PacketSendUtility.sendMessage(admin, "omg, disselect yourself please.");
				return;
			}

			Player targetpl = (Player) target;
			macAddress = targetpl.getClientConnection().getMacAddress();
			targetName = targetpl.getName();
			targetId = targetpl.getObjectId();
			targetpl.getClientConnection().closeNow();
		}
		else
		{
			PacketSendUtility.sendMessage(admin, "Please select target first!!");
			return;
		}

		if (params.length > 0) {
			try {
				time = Integer.parseInt(params[0]);
			}
			catch (NumberFormatException e) {
				PacketSendUtility.sendMessage(admin, "Syntax: //banmac [time in minutes]");
				return;
			}
		}

		BannedMacManager.getInstance().banAddress(macAddress,targetId, System.currentTimeMillis() + time * 60000,
			"author=" + admin.getName() + ", " + admin.getObjectId() + "; target=" + targetName);
	}
}
