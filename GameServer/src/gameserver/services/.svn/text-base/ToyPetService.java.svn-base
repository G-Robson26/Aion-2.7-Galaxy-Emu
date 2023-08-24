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
package gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import gameserver.configs.main.GSConfig;
import gameserver.dao.PlayerPetsDAO;
import gameserver.dataholders.DataManager;
import gameserver.dataholders.PetData;
import gameserver.dataholders.PetFeedData;
import gameserver.itemengine.actions.ItemActions;
import gameserver.itemengine.actions.SkillUseAction;
import gameserver.model.EmotionType;
import gameserver.model.gameobjects.Item;
import gameserver.model.gameobjects.player.PetFeedState;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.ToyPet;
import gameserver.model.templates.item.ItemTemplate;
import gameserver.model.templates.pet.FoodType;
import gameserver.model.templates.pet.PetFunctionTemplate;
import gameserver.model.templates.pet.PetTemplate;
import gameserver.network.aion.serverpackets.SM_EMOTION;
import gameserver.network.aion.serverpackets.SM_PET;
import gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import gameserver.network.aion.serverpackets.SM_WAREHOUSE_INFO;
import gameserver.skillengine.SkillEngine;
import gameserver.skillengine.model.Skill;
import gameserver.utils.InterruptableTask;
import gameserver.utils.PacketSendUtility;
import gameserver.utils.ThreadPoolManager;

import javolution.util.FastMap;
import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledFuture;
import java.util.Calendar;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.FutureTask;

/** 
 * @author Sylar - Kamui - Rolandas
 */
public class ToyPetService
{

	public static final ToyPetService getInstance()
	{
		return SingletonHolder.instance;
	}

    private ToyPetService()
    {
        caringTask = new FastMap();
        moodTask = new FastMap();
    }

	private static final Logger log = Logger.getLogger(DropService.class);
    private FastMap caringTask;
    private FastMap moodTask;
	
	public void createPetForPlayer(Player player, int petId, int decorationId, String name)
	{
		DAOManager.getDAO(PlayerPetsDAO.class).insertPlayerPet(player, petId, decorationId, name);
		List<ToyPet> list = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
		if(list == null)
			return;
		ToyPet pet = null;
		for(ToyPet p : list)
		{
			if(p.getPetId() == petId)
			{
				pet = p;
				pet.setMaster(player);
			}
		}

		if(pet != null)
		{
			PacketSendUtility.sendPacket(player, new SM_PET(1, pet));
		}
	}

	public void surrenderPet(Player player, int petId)
	{
		List<ToyPet> list = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
		if(list == null)
			return;
		ToyPet pet = null;
		for(ToyPet p : list)
		{
			if(p.getPetId() == petId)
			{
				pet = p;
				pet.setMaster(player);
			}
		}
		DAOManager.getDAO(PlayerPetsDAO.class).removePlayerPet(player, pet.getPetId());
		PacketSendUtility.sendPacket(player, new SM_PET(2, pet));
	}

	public void summonPet(Player player, int petId)
	{
		if(player.getToyPet() != null)
			dismissPet(player, petId);
		ToyPet pet = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPet(player.getObjectId(), petId);
		if(pet != null)
		{
			PetTemplate petTemplate = DataManager.PET_DATA.getPetTemplate(petId);
			if (petTemplate == null)
				return;
			player.setToyPet(pet);
			pet.setMaster(player);
			PacketSendUtility.broadcastPacket(player, new SM_PET(3, pet), true);
			if(player.getLastToyPet() != petId && petId != 0)
                ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).setMood(player, player.getLastToyPet(), 0);
            if(!pet.getFullDismissTime())
                ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).savePet(player, pet);
            PacketSendUtility.sendPacket(player, new SM_PET(12, 4, 0, pet));
            addMood(player);
			PetFunctionTemplate template = null;
			if(petTemplate.getFunctionTemplates() == null)
				return;//returning because rest of code only pertains to not null
			for(PetFunctionTemplate tpl : petTemplate.getFunctionTemplates())
			{
				if(tpl.getType().equals("warehouse"))
					template = tpl;
			}
			if (template != null)
			{
				int itemLocation = 0;
				switch (template.getSlots())
				{
					case 6:
						itemLocation = 32;
						break;
					case 12:
						itemLocation = 33;
						break;
					case 18:
						itemLocation = 34;
						break;
					case 24:
						itemLocation = 35;
						break;
				}
				if (itemLocation != 0)
				{
					PacketSendUtility
					.sendPacket(player, new SM_WAREHOUSE_INFO(player.getStorage(itemLocation).getAllItems(),
						itemLocation, 0, true));

					PacketSendUtility.sendPacket(player, new SM_WAREHOUSE_INFO(null, itemLocation, 0, false));
				}
			}

		}
	}

	public void dismissPet(Player player, int petId) {
        if(player.getToyPet() == null)
            return;
        int uid = player.getToyPet().getUid();
        ToyPet pet = player.getToyPet();
        pet.setDismissTime(Calendar.getInstance().getTimeInMillis());
        ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).savePet(player, pet);
        player.setToyPet(null);
        player.setLastToyPet(uid);
        if(moodTask.get(player.getObjectId()) == null) {
            ((ScheduledFuture)moodTask.get(player.getObjectId())).cancel(true);
            moodTask.remove(player.getObjectId());
        }
        if(pet.getAutoLoot())  {
            pet.setAutoLoot(false);
            PacketSendUtility.sendPacket(player, new SM_PET(13, 3, 0, 0, 0));
        }
        PacketSendUtility.broadcastPacket(player, new SM_PET(4, uid), true);
    }
	
	public void renamePet(Player player, int petId, String petName)
	{
		if(player.getToyPet() == null)
			return;
		List<ToyPet> list = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
		if(list == null)
			return;
		ToyPet pet = null;
		for(ToyPet p : list)
		{
			if(p.getPetId() == petId)
			{
				pet = p;
				pet.setMaster(player);
			}

			if(p.getName().equals(petName))
			{
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400694));//[You already have a pet of the same name. Please choose another name]
				return;
			}
		}

		if(pet != null)
		{
			int uid = player.getToyPet().getUid();
			DAOManager.getDAO(PlayerPetsDAO.class).renamePlayerPet(player, petId, petName);
			PacketSendUtility.sendPacket(player, new SM_PET(10, uid, petName));
		}
	}

	/**
	 * @param player
	 * @param foodObjId
	 * @param foodAmount
	 */
	public void feedPet(final Player player, final int foodObjId, final int foodAmount)
	{	
		final ToyPet pet = player.getToyPet();
		Item food = player.getInventory().getItemByObjId(foodObjId);		
		if (food == null || foodAmount == 0 || pet == null || pet.isFeeding())
		{
			if (pet.isFeeding())
				cancelFeeding(player, pet);
			return;
		}

		if (pet.getFeedState() == PetFeedState.FULL)
		{
			PacketSendUtility.sendPacket(player, new SM_PET( 9, 8, foodObjId, foodAmount, pet ) );
			return;
		}

		//Set feeding flag to avoid multiple feedings
		pet.setFeeding(true);
		PetTemplate pt = DataManager.PET_DATA.getPetTemplate(pet.getPetId());
		pet.setFlavour(DataManager.PET_FEED_DATA.getFlavour(pt.getFoodFlavourId(), food.getItemId()));
		final ItemTemplate template = food.getItemTemplate();
		
		PacketSendUtility.sendPacket(player, new SM_PET( 9, 1, foodObjId, foodAmount, pet ) );
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.PET_FEEDING, 0, pet.getUid()), true);

		FutureTask<?> interruptable = new FutureTask<Object>(new Runnable(){
			@Override
			public void run()
			{
				int exp = pet.getExp();
				int love = pet.getLoveCount();
				int count = pet.getFeedCount();
				PetFeedState state = pet.getFeedState();
				int feeded = 1;

				for (int i = foodAmount; i > 0; i--, feeded++)
				{
					try
					{
						Thread.sleep(2000);
						if (feedSuccessful(player, pet, foodObjId, i - 1))
							incrementStats(pet, template, foodObjId);
						else
							feeded--;

						// save changes on state change only, otherwise allow to cancel
						if (pet.getFeedState() != state && pet.getExp() == 0)
						{
							exp = pet.getExp();
							love = pet.getLoveCount();
							count = pet.getFeedCount();
							state = pet.getFeedState();	
							player.getInventory().removeFromBagByObjectId(foodObjId, feeded);
							feeded = 0;
							DAOManager.getDAO(PlayerPetsDAO.class).savePet(player, pet);
						}
						
						if (pet.getFlavour() == null)
						{
							// non eatable item
							PacketSendUtility.sendPacket(pet.getMaster(), 
								SM_SYSTEM_MESSAGE.STR_MSG_TOYPET_FEED_FOOD_NOT_LOVEFLAVOR(pet.getName(), template.getNameId()));
						}
						
						if (!pet.isFeeding())
							break;
					}
					catch(InterruptedException e)
					{
						if (pet.getFeedState() != PetFeedState.FULL)
						{
							// restore old values if canceled
							pet.setExp(exp);
							pet.setLoveCount(love);
							pet.setFeedCount(count);
							pet.setFeedState(state);
						}
						else
						{
							feeded--;
							if (feeded > 0)
								player.getInventory().removeFromBagByObjectId(foodObjId, feeded);
							DAOManager.getDAO(PlayerPetsDAO.class).savePet(player, pet);
						}
						pet.setFeeding(false);
						Thread.currentThread().interrupt();
						return;
					}
				}

				if (feeded > 0)
				{
					player.getInventory().removeFromBagByObjectId(foodObjId, feeded);
					DAOManager.getDAO(PlayerPetsDAO.class).savePet(player, pet);
				}
			}
		}, null);
		
		InterruptableTask feedingTask = new InterruptableTask(interruptable, foodAmount * 2500);
		ThreadPoolManager.getInstance().submitInterruptable(feedingTask);
		pet.setFeedingTask(feedingTask);

	}

	private void incrementStats(ToyPet pet, ItemTemplate template, int foodObjectId)
	{
		pet.setFeedCount(pet.getFeedCount() + 1);
		if (template.getLevel() < 10) 
			return;

		List<FoodType> foodTypes = DataManager.PET_FEED_DATA.getFoodGroups().getFoodTypes(template.getTemplateId());
		FoodType foodType = foodTypes.get(0);
		int maxCount = FoodType.isLoved(foodType) ? pet.getFlavour().getLoveCount()
												  : pet.getFlavour().getCount();
		
		int oldExp = pet.getExp();
		
		if (pet.getFeedState() == PetFeedState.HUNGRY && pet.getFeedCount() > maxCount * 0.5f ||
			pet.getFeedState() == PetFeedState.CONTENT && pet.getFeedCount() > maxCount * 0.8f ||
			pet.getFeedState() == PetFeedState.SEMIFULL && 
			(FoodType.isLoved(foodType) && pet.getFeedCount() == maxCount || pet.getFeedCount() > maxCount * 1.05))
		{
			pet.setFeedState(PetFeedState.valueOf(pet.getFeedState().ordinal() + 1));
			pet.setExp(0);
			pet.setLoveCount(pet.getLoveCount() + 1);
		}
		else
		{
			short step = ToyPet.getFeedStep(template.getLevel(), maxCount);
			pet.addExp(step);
		}

		if (pet.getExp() == 0) 
		{
			if (pet.getFeedState() == PetFeedState.CONTENT && pet.getFeedCount() <= maxCount * 0.487f ||
				pet.getFeedState() == PetFeedState.SEMIFULL && pet.getFeedCount() <= maxCount * 0.78f)
			{
				// limit not reached, set state back
				pet.setFeedState(PetFeedState.valueOf(pet.getFeedState().ordinal() - 1));
				pet.setExp(oldExp);
			} 
			else if (pet.getFeedState() == PetFeedState.FULL)
			{
				int rewardId = pet.getRewardId(template);
				
				pet.setExp(0);
				pet.setLoveCount(0);
				pet.setFeedCount(0);
				pet.setCdStarted(Calendar.getInstance().getTimeInMillis());
				
				Player player = pet.getMaster();
				// TODO: handle inventory is full
				if (ItemService.addItem(player, rewardId, 1) == 0)
					PacketSendUtility.sendPacket(player, new SM_PET( 9, 6, rewardId, 0, pet ) );
				
				PacketSendUtility.sendPacket(player, new SM_PET( 9, 5, 0, 0, pet ) );
				PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.PET_FEEDING2, 0, pet.getUid()), true);
				PacketSendUtility.sendPacket(player, new SM_PET( 9, 7, foodObjectId, 0, pet ) );
				PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.PET_FEEDING2, 0, pet.getUid()), true);
				PacketSendUtility.sendPacket(player, new SM_EMOTION(player, EmotionType.SELECT_TARGET));
				pet.getFeedingTask().cancel();
			}
		}
	}

	private void cancelFeeding(final Player player, ToyPet pet)
	{
		pet.getFeedingTask().cancel();
		PacketSendUtility.sendPacket(player, new SM_PET( 9, 4, 0, 0, pet ) );
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.PET_FEEDING2, 0, pet.getUid()), true);
		PacketSendUtility.sendPacket(player, new SM_EMOTION(player, EmotionType.SELECT_TARGET));
	}

	private boolean feedSuccessful(final Player player, ToyPet pet, int foodObjId, int foodAmount) 
	{
		if (!player.isOnline())
		{
			pet.setFeeding(false);
			pet.getFeedingTask().cancel();
			return false;
		}
		
		if (pet.getFlavour() == null)
		{
			foodAmount = 0;
			PacketSendUtility.sendPacket(player, new SM_PET( 9, 3, foodObjId, foodAmount, pet ) );
		}
		else
			PacketSendUtility.sendPacket(player, new SM_PET( 9, 2, foodObjId, foodAmount, pet ) );
		
		// PacketSendUtility.sendPacket(player, new SM_UPDATE_ITEM(player.getInventory().getItemByObjId(foodObjId)));
		if (foodAmount == 0)
		{
			if (pet.getFlavour() != null)
				PacketSendUtility.sendPacket(player, new SM_PET( 9, 5, 0, 0, pet ) );
			
			PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.PET_FEEDING2, 0, pet.getUid()), true);
			PacketSendUtility.sendPacket(player, new SM_EMOTION(player, EmotionType.SELECT_TARGET));
			pet.setFeeding(false);
		}
		return pet.getFlavour() != null;
	}

	public void caringPet(Player player, int petId, int j)
    {
        ToyPet pet = player.getToyPet();
        if(pet == null || pet.isFeeding() || player == null)
        {
            if(pet.isFeeding())
                cancelFeeding(player, pet);
            return;
        }
        if(petId == 1)
        {
            if(pet.getFullCaringTime() > 0)
                return;
            petId = 2;
            pet.setCdCaring(Calendar.getInstance().getTimeInMillis());
            pet.setMood(pet.getMood() + 1000);
            ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).savePet(player, pet);
            if(caringTask.get(player.getObjectId()) != null)
            {
                ((ScheduledFuture)caringTask.get(player.getObjectId())).cancel(true);
                caringTask.remove(player.getObjectId());
            }
        } else
        if(petId == 3)
        {
            if(pet.getFullGiftTime() > 0 || pet.getMood() < 10000)
                return;
            pet.setMood(pet.getMood() - 10000);
            if(pet.getMood() < 0)
                pet.setMood(0);
            pet.setCdGift(Calendar.getInstance().getTimeInMillis());
            ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).savePet(player, pet);
            giveGift(player);
        } else
        if(petId == 0)
            caringTimer(player, pet);
        PacketSendUtility.sendPacket(player, new SM_PET(12, petId, j, pet));
        if(petId == 3)
        {
            PacketSendUtility.sendPacket(player, new SM_PET(12, 4, 0, pet));
            addMood(player);
        }
    }
	
	private void giveGift(final Player player) {
        if(player == null){
            return;
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                public void run() {
                    ItemService.addItem(player, 0xb356fb2, 1L);
                }
            }
          , 4000L);
            return;
        }
    }
	
	private void caringTimer(final Player player, final ToyPet pet) {
        if(player == null || pet == null){
            return;
        } else {
            ScheduledFuture scheduledfuture = ThreadPoolManager.getInstance().schedule(new Runnable() {

                public void run()
                {
                    pet.setMood(pet.getMood() - 500);
                    if(pet.getMood() < 0)
                        pet.setMood(0);
                    ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).savePet(player, pet);
                    if(caringTask.get(player.getObjectId()) != null)
                    {
                        ((ScheduledFuture)caringTask.get(player.getObjectId())).cancel(true);
                        caringTask.remove(player.getObjectId());
                    }
                }
            }
           , 30000L);
            caringTask.put(player.getObjectId(), scheduledfuture);
            return;
        }
    }
	
	private void addMood(final Player player) {
        ScheduledFuture scheduledfuture = ThreadPoolManager.getInstance().schedule(new Runnable() {

            public void run() {
                if(player == null)
                    return;
                if(player.getToyPet() == null) {
                    if(moodTask.get(player.getObjectId()) == null)   {
                        ((ScheduledFuture)moodTask.get(player.getObjectId())).cancel(true);
                        moodTask.remove(player.getObjectId());
                    }
                    return;
                }
                if(player.getToyPet().getMood() >= 10000) {
                    if(moodTask.get(player.getObjectId()) == null) {
                        ((ScheduledFuture)moodTask.get(player.getObjectId())).cancel(true);
                        moodTask.remove(player.getObjectId());
                    }
                    return;
                } else {
                    player.getToyPet().setMood(player.getToyPet().getMood() + 100);
                    ((PlayerPetsDAO)DAOManager.getDAO(PlayerPetsDAO.class)).setMood(player, player.getToyPet().getPetId(), player.getToyPet().getMood());
                    moodTask.remove(player.getObjectId());
                    addMood(player);
                    return;
                }
            }

        }
        ,60000L);
        moodTask.put(player.getObjectId(), scheduledfuture);
    }
	
	public void onPlayerLogin(Player player)
	{
		List<ToyPet> playerPets = DAOManager.getDAO(PlayerPetsDAO.class).getPlayerPets(player.getObjectId());
		if(playerPets != null && playerPets.size() > 0)
		{
			for (ToyPet pet : playerPets)
				pet.setMaster(player);

			PacketSendUtility.sendPacket(player, new SM_PET(0, playerPets));
		}
	}

	public void autoLoot(Player player, int petId){
        ToyPet pet = player.getToyPet();
        if(pet == null || pet.isFeeding() || player == null) {
            if(pet.isFeeding())
                cancelFeeding(player, pet);
            return;
        }
       PetTemplate pettemplate = DataManager.PET_DATA.getPetTemplate(pet.getPetId());
        if(pettemplate == null)
            return;
        if(!pettemplate.getStatsTemplate().getReaction().equals("brave")){
            return;
        } else {
            pet.setAutoLoot(true);
            PacketSendUtility.sendPacket(player, new SM_PET(13, 3, 0, petId, 0));
            return;
        }
    }
	
	public void startAutoLoot(Player player, int petId) {
        PacketSendUtility.sendPacket(player, new SM_PET(13, 3, 1, petId, 0));
        DropService.getInstance().requestDropListForPet(player, petId);
        DropService.getInstance().requestDropItem(player, petId, 2);
        DropService.getInstance().requestDropListForPet(player, petId);
    }
	
    public void dopingPet(Player player, int i, int j, int k) {
        ToyPet pet = player.getToyPet();
        if(pet == null || pet.isFeeding() || player == null)  {
            if(pet.isFeeding())
                cancelFeeding(player, pet);
            return;
        }
        PetTemplate pettemplate = DataManager.PET_DATA.getPetTemplate(pet.getPetId());
        if(pettemplate == null)
            return;
        PacketSendUtility.sendPacket(player, new SM_PET(13, 2, i, j, k));
        switch(i){
        default:
            break;

        case 3: // '\003'
            if(player.getInventory().getItemCountByItemId(j) > 0L)    {
                ItemTemplate itemtemplate = ItemService.getItemTemplate(j);
                if(itemtemplate == null)
                    return;
                if(itemtemplate.getActions().getSkillUseActions() == null)
                    return;
                Iterator iterator = itemtemplate.getActions().getSkillUseActions().iterator();
                do  {
                    if(!iterator.hasNext())
                        break;
                    SkillUseAction skilluseaction = (SkillUseAction)iterator.next();
                    Skill skill = SkillEngine.getInstance().getSkill(player, skilluseaction.getSkillid(), skilluseaction.getLevel(), player.getTarget());
                    if(skill != null && skill.canUseSkill())
                        skill.useSkill();
                } while(true);
                player.getInventory().removeFromBagByItemId(j, 1L);
            }
            break;
        }
    }
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final ToyPetService instance = new ToyPetService();
	}

	public static boolean isValidName(String petName)
	{
		return GSConfig.PET_NAME_PATTERN.matcher(petName).matches();
	}
}