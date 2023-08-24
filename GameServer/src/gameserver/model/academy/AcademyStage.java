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
 **/
package gameserver.model.academy;

import java.util.ArrayList;
import java.util.List;

public class AcademyStage
{

    public AcademyStage()
    {
    }

    public List getAcademyRound()
    {
        if(academyRound == null)
            academyRound = new ArrayList();
        return academyRound;
    }

    public int getStage()
    {
        return id;
    }

    public int size()
    {
        return getAcademyRound().size();
    }

    protected List academyRound;
    protected int id;
}