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

package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.dao.DAOManager;
import gameserver.dao.BlockListDAO;
import gameserver.dao.PlayerDAO;
import gameserver.model.gameobjects.player.BlockList;
import gameserver.model.gameobjects.player.BlockedPlayer;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.gameobjects.player.PlayerCommonData;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * MySQL5 DAO for editing the block list
 *
 * @author Ben
 */
public class MySQL5BlockListDAO extends BlockListDAO {
    public static final String LOAD_QUERY = "SELECT blocked_player, reason FROM blocks WHERE player=?";
    public static final String ADD_QUERY = "INSERT INTO blocks (player, blocked_player, reason) VALUES (?, ?, ?)";
    public static final String DEL_QUERY = "DELETE FROM blocks WHERE player=? AND blocked_player=?";
    public static final String SET_REASON_QUERY = "UPDATE blocks SET reason=? WHERE player=? AND blocked_player=?";

    private static Logger log = Logger.getLogger(MySQL5BlockListDAO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addBlockedUser(final int playerObjId, final int objIdToBlock, final String reason) {
        return DB.insertUpdate(ADD_QUERY, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
                stmt.setInt(2, objIdToBlock);
                stmt.setString(3, reason);
                stmt.execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delBlockedUser(final int playerObjId, final int objIdToDelete) {
        return DB.insertUpdate(DEL_QUERY, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
                stmt.setInt(2, objIdToDelete);
                stmt.execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockList load(final Player player) {
        final Map<Integer, BlockedPlayer> list = new HashMap<Integer, BlockedPlayer>();

        DB.select(LOAD_QUERY, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rset) throws SQLException {
                PlayerDAO playerDao = DAOManager.getDAO(PlayerDAO.class);
                while (rset.next()) {
                    int blockedOid = rset.getInt("blocked_player");
                    PlayerCommonData pcd = playerDao.loadPlayerCommonData(blockedOid);
                    if (pcd == null) {
                        log.error("Attempt to load block list for " + player.getName() + " tried to load a player which does not exist: " + blockedOid);
                    } else {
                        list.put(blockedOid, new BlockedPlayer(pcd, rset.getString("reason")));
                    }
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, player.getObjectId());
            }
        });
        return new BlockList(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setReason(final int playerObjId, final int blockedPlayerObjId, final String reason) {
        return DB.insertUpdate(SET_REASON_QUERY, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, reason);
                stmt.setInt(2, playerObjId);
                stmt.setInt(3, blockedPlayerObjId);
                stmt.execute();

            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
