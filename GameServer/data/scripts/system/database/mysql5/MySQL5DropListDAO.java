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
import com.aionemu.commons.database.ParamReadStH;
import gameserver.dao.DropListDAO;
import gameserver.model.drop.DropList;
import gameserver.model.drop.DropTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ATracer
 */
public class MySQL5DropListDAO extends DropListDAO {
    public static final String SELECT_QUERY = "SELECT * FROM `droplist`";

    @Override
    public DropList load() {
        final DropList dropList = new DropList();

        DB.select(SELECT_QUERY, new ParamReadStH() {
            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }

            @Override
            public void handleRead(ResultSet rset) throws SQLException {
                while (rset.next()) {
                    int mobId = rset.getInt("mobId");
                    int itemId = rset.getInt("itemId");
                    int min = rset.getInt("min");
                    int max = rset.getInt("max");
                    float chance = rset.getFloat("chance");

                    if (chance > 0) {
                        DropTemplate dropTemplate = new DropTemplate(mobId, itemId, min, max, chance);
                        dropList.addDropTemplate(mobId, dropTemplate);
                    }
                }
            }
        });

        return dropList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
