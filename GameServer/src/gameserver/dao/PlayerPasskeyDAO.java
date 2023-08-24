/**
 * This file is part of Aion X Emu <aionxemu.com>
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

/**
 * @author acu77
 */
public abstract class PlayerPasskeyDAO implements DAO {
    /**
     * @param accountId
     * @param passkey
     */
    public abstract void insertPlayerPasskey(int accountId, String passkey);

    /**
     * @param accountId
     * @param oldPasskey
     * @param newPasskey
     * @return
     */
    public abstract boolean updatePlayerPasskey(int accountId, String oldPasskey, String newPasskey);

    /**
     * @param accountId
     * @param newPasskey
     * @return
     */
    public abstract boolean updateForcePlayerPasskey(int accountId, String newPasskey);

    /**
     * @param accountId
     * @param passkey
     * @return
     */
    public abstract boolean checkPlayerPasskey(int accountId, String passkey);

    /**
     * @param accountId
     * @return
     */
    public abstract boolean existCheckPlayerPasskey(int accountId);

    /*
     * (non-Javadoc)
     * @see com.aionemu.commons.database.dao.DAO#getClassName()
     */

    @Override
    public final String getClassName() {
        return PlayerPasskeyDAO.class.getName();
    }
}
