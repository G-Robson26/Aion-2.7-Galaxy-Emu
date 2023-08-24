/**
 * This file is part of Aion Galaxy Emu <aiongemu.com>
 *
 * alpha team is pryvate software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alpha team is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alpha team.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.dao;


import gameserver.model.NpcShout;

import java.util.List;

import javolution.util.FastMap;

import com.aionemu.commons.database.dao.DAO;


/**
 * @author Sylar, modified Rolandas
 *
 */
public abstract class NpcShoutsDAO implements DAO {

	@Override
	public final String getClassName() {
		 return NpcShoutsDAO.class.getName();
	}

	public abstract FastMap<Integer, List<NpcShout>> getShouts();

}