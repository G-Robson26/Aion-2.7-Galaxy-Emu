 package gameserver.taskmanager.tasks;

import org.apache.log4j.Logger;

import gameserver.ShutdownHook;
import gameserver.ShutdownHook.ShutdownMode;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.tasks.TaskFromDBHandler;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;
import gameserver.world.World;
import gameserver.world.Executor;


/**
 * @author flegelDEV and resurrected
 *
 */
public class RestartTask extends TaskFromDBHandler
{
	private static final	Logger			log	= Logger.getLogger(RestartTask.class);
	
	private int				countDown;
	private int				announceInterval;
	private int				warnCountDown;

	@Override
	public String getTaskName()
	{
		return "restart";
	}

	@Override
	public boolean isValid()
	{
		if (params.length == 3)
			return true;
		else
			return false;
	}

	@Override
	public void run()
	{
		log.info("Task[" + id + "] launched : restarting the server !");
		setLastActivation();
		
		countDown			= Integer.parseInt(params[0]);
		announceInterval	= Integer.parseInt(params[1]);
		warnCountDown		= Integer.parseInt(params[2]);
		

		
		for(Player player : World.getInstance().getPlayers())
			PacketSendUtility.sendSysMessage(player, "The server is going to restart in aprox 5 minutes! Please look for a safe place and wait for the restart!");
			
		
		
		/**World.getInstance().doOnAllPlayers(new Executor<Player>() {
            @Override
            public boolean run(Player player) {
                PacketSendUtility.sendSysMessage(player, "Auto-Announce: The server is going to restart in 5 minutes! Please look for a save place and logout. The restart will take approx 1 minute!");
                return true;
            }
        });
		*/
	
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run()
			{
				ShutdownHook.getInstance().doShutdown(countDown, announceInterval, ShutdownMode.RESTART);
			}
		}, warnCountDown * 1000);
	}
}