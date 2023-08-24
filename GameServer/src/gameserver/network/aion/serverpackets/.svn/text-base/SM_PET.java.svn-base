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

package gameserver.network.aion.serverpackets;

import gameserver.model.gameobjects.player.ToyPet;
import gameserver.network.aion.AionConnection;
import gameserver.network.aion.AionServerPacket;
import com.aionemu.commons.utils.Rnd;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * @author xitanium
 */
public class SM_PET extends AionServerPacket{

	private int actionId;
	private int feedActionId;
	private int emotionActionId;
    private int lootAction;
	private ToyPet pet;
	private List<ToyPet> pets;
	private int petUniqueId;
	private String petName;
	private int foodObjId;
	private int foodAmount;
    private int emotion;
    private int param;
    private int objId;
    private int cellId;
	
	public SM_PET(int actionId){
		this.actionId = actionId;
	}
	
	public SM_PET(int actionId, int petUniqueId){
		this.actionId = actionId;
		this.petUniqueId = petUniqueId;
	}
	
	public SM_PET(int actionId, ToyPet pet){
		this.actionId = actionId;
		this.pet = pet;
	}
	
	public SM_PET(int actionId, int emotionActionId, int emotion, ToyPet pet) {
        this.actionId = actionId;
        this.emotionActionId = emotionActionId;
        this.emotion = emotion;
        this.pet = pet;
    }
	
	public SM_PET(int actionId, List<ToyPet> pets){
		this.actionId = actionId;
		this.pets = pets;
	}

	public SM_PET(int actionId, int petUniqueId, String petName){
		this.actionId = actionId;
		this.petUniqueId = petUniqueId;
		this.petName = petName;
	}
	
	public SM_PET(int actionId, int feedActionId, int foodObjId, int foodAmount, ToyPet pet){
		this.actionId = actionId;
		this.feedActionId = feedActionId;
		this.pet = pet;
		this.foodObjId = foodObjId;
		this.foodAmount = foodAmount;
	}
	
	public SM_PET(int actionId, int lootAction, int param, int objId, int cellId) {
        this.actionId = actionId;
        this.lootAction = lootAction;
        this.param = param;
        this.objId = objId;
        this.cellId = cellId;
    }
	
	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf){
		writeH(buf, actionId);
		switch(actionId){
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 11: 
                default:
            break;		
			case 0:
				// load list on login
            int i = 0;
            writeC(buf, 0);
            writeH(buf, pets.size());
            for(Iterator iterator = pets.iterator(); iterator.hasNext(); writeC(buf, 0))
            {
                ToyPet pet = (ToyPet)iterator.next();
                i++;
                writeS(buf, pet.getName());
                writeD(buf, pet.getPetId());
                writeD(buf, pet.getUid());
                writeD(buf, pet.getMaster().getObjectId().intValue());
                writeD(buf, 0);
                writeD(buf, 0);
                writeD(buf, (int)(pet.getBirthDay().getTime() / 1000L));
                writeC(buf, 0);
                writeC(buf, 0);
                writeC(buf, pet.getExp());
                writeC(buf, pet.getLoveCount());
                writeC(buf, pet.getFeedCount());
                writeD(buf, pet.getFullRemainingTime());
                writeC(buf, 0);
                writeC(buf, 0);
                writeC(buf, 0);
                writeD(buf, 0);
                writeD(buf, 0);
                writeD(buf, 0);
            }
				break;
			case 1:
				// adopt
				writeS(buf, pet.getName());
				writeD(buf, pet.getPetId());
				writeD(buf, pet.getUid()); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeC(buf, 0); //unk +
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeC(buf, 0); //unk +
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeC(buf, 0); //unk +
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				break;
			case 2:
				// surrender
				writeD(buf, pet.getPetId());
				writeD(buf, pet.getUid()); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				break;
			case 3:
				// spawn
				writeS(buf, pet.getName());
				writeD(buf, pet.getPetId());
				writeD(buf, pet.getUid());
				
				if(pet.getX1() == 0 && pet.getY1() == 0 && pet.getZ1() == 0){
					writeF(buf, pet.getMaster().getX());
					writeF(buf, pet.getMaster().getY());
					writeF(buf, pet.getMaster().getZ());
					
					writeF(buf, pet.getMaster().getX());
					writeF(buf, pet.getMaster().getY());
					writeF(buf, pet.getMaster().getZ());
					
					writeC(buf, pet.getMaster().getHeading());
				}
				else{
					writeF(buf, pet.getX1());
					writeF(buf, pet.getY1());
					writeF(buf, pet.getZ1());
					
					writeF(buf, pet.getX2());
					writeF(buf, pet.getY2());
					writeF(buf, pet.getZ2());
					
					writeC(buf, pet.getH());
				}
				
				writeD(buf, pet.getMaster().getObjectId().intValue()); //unk
				writeC(buf, 1); //unk
				writeD(buf, 0); //unk
				
				writeD(buf, pet.getDecoration());
				writeD(buf, 1); //wings ID if customize_attach = 1
				writeD(buf, 0); //unk
				writeD(buf, 0); //unk
				writeD(buf, 0);
                writeC(buf, 0);
				break;
			case 4:
				// dismiss
				writeD(buf, petUniqueId);
				writeC(buf, 1);
				break;
			case 9:
				// feed
				writeH(buf, 1);
				writeC(buf, 1);
				writeC(buf, feedActionId);
				final int state = 5; // dynamic value, unknown; seen 0x04, 0x05 and 0x0D
				switch(feedActionId){
					case 1:
						// eat
						writeC(buf, state);
						writeC(buf, pet.getExp());
						writeC(buf, pet.getLoveCount());
						writeC(buf, pet.getFeedCount());
						writeD(buf, 0);
						writeD(buf, foodObjId);
						writeD(buf, foodAmount);
						break;
					case 2:
						// eating successful
						writeC(buf, state);
						writeC(buf, pet.getExp());
						writeC(buf, pet.getLoveCount());
						writeC(buf, pet.getFeedCount());
						writeD(buf, 0);
						writeD(buf, foodObjId);
						writeD(buf, foodAmount);
						writeD(buf, 0);
						break;
					case 3:
						// non eatable item
						writeC(buf, state);
						writeC(buf, pet.getExp());
						writeC(buf, pet.getLoveCount());
						writeC(buf, pet.getFeedCount());
						writeD(buf, 0);
						break;
					case 4:
						// cancel feed
						writeC(buf, state);
						writeC(buf, pet.getExp());
						writeC(buf, pet.getLoveCount());
						writeC(buf, pet.getFeedCount());
						writeD(buf, 0);
						break;
					case 5:
						// clean feed task; before reward set exp, love and count to zero
						writeC(buf, state);
						writeC(buf, pet.getExp());
						writeC(buf, pet.getLoveCount());
						writeC(buf, pet.getFeedCount());
						writeD(buf, 0);
						break;
					case 6:
						// give item
						writeC(buf, state);
						writeC(buf, 0);
						writeC(buf, 0);
						writeC(buf, 0);
						writeD(buf, 0);
						writeD(buf, foodObjId); // item id of reward
						writeC(buf, 0);
						break;
					case 7:
						// present notification
						writeC(buf, state);
						writeC(buf, 0);
						writeC(buf, 0);
						writeC(buf, 0);
						writeD(buf, 600); //remaining time (10min)
						writeD(buf, foodObjId);
						writeD(buf, 0);
						break;
					case 8:
						// not hungry
						writeC(buf, state);
						writeC(buf, 0);
						writeC(buf, 0);
						writeC(buf, 0);
						writeD(buf, pet.getFullRemainingTime()); //remaining time
						writeD(buf, foodObjId);
						writeD(buf, foodAmount);
						break;
				}
				break;
			case 10:
				// rename
				writeD(buf, petUniqueId);
				writeS(buf, petName);
				break;
            case 12:
                writeC(buf, emotionActionId);
                switch (emotionActionId) {
                    case 0:
                        writeD(buf, Rnd.get(0, 3));
                    case 2:
                        writeD(buf, Rnd.get(0, 8));
                        writeD(buf, pet.getMood());
                        writeD(buf, emotion);
                        break;
                    case 3:
                       writeD(buf, 0xb356fb2);
                       break;
                    case 4:
                       writeD(buf, pet.getMood());
                       writeD(buf, pet.getFullCaringTime());
                       writeD(buf, pet.getFullGiftTime());
                    break;
                }
                break;
		    case 13:
                writeC(buf, lootAction);
                writeC(buf, param);
                switch (param) {
                    case 0:
                        writeD(buf, objId);
                        writeD(buf, cellId);
                        break;
                    case 1:
                        writeD(buf, objId);
                       break;
                    case 2:
                    case 3:
                        writeD(buf, objId);
					break;
                }
            break;
		}
	}
}
