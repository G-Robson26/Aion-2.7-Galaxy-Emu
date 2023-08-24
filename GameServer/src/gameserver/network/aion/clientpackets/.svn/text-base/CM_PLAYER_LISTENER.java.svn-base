package gameserver.network.aion.clientpackets;

import gameserver.configs.main.CustomConfig;
import gameserver.model.gameobjects.player.Player;
import gameserver.network.aion.AionClientPacket;
import gameserver.services.EmotionService;
import gameserver.services.HTMLService;
import gameserver.services.TitleService;

import java.util.Calendar;


/**
 *
 * @author ginho1
 *
 */
public class CM_PLAYER_LISTENER extends AionClientPacket
{
	/*
	 * this CM is send every five minutes by client.
	 */
	public CM_PLAYER_LISTENER(int opcode)
	{
		super(opcode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl()
	{
	}

	/**c
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl()
	{
		Player player = getConnection().getActivePlayer();

		if(player == null)
			return;

		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

		TitleService.checkPlayerTitles(player);

		if(CustomConfig.RETAIL_EMOTIONS)
			EmotionService.removeExpiredEmotions(player);

		if(CustomConfig.ENABLE_SURVEYS)
			HTMLService.onPlayerLogin(player);

		
	}
}
