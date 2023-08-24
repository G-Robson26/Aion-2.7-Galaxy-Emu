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
package gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.ToyPet;

import java.util.List;

/**
 * @author xitanium
 */
public abstract class PlayerPetsDAO implements DAO {
    @Override
    public final String getClassName() {
        return PlayerPetsDAO.class.getName();
    }
	
	public abstract void insertPlayerPet(Player player, int petId, int decoration, String name);
	
	public abstract void removePlayerPet(Player player, int petId);
	
	public abstract List<ToyPet> getPlayerPets(int petId);
	
	public abstract ToyPet getPlayerPet(int petId, int decoration);
	
	public abstract void renamePlayerPet(Player player, int petId, String name);
	
	public abstract boolean savePet(Player player, ToyPet pet);

	public abstract void setMood(Player player, int petId, int decoration);
}