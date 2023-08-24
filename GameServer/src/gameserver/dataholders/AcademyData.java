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
package gameserver.dataholders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import gnu.trove.TIntObjectHashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import gameserver.model.academy.AcademyStage;

@XmlRootElement(name="academy_spawns")
@XmlAccessorType(XmlAccessType.FIELD)
public class AcademyData
{

  @XmlElement(name="stage")
  private List<AcademyStage> academyStage;
  private TIntObjectHashMap<AcademyStage> academyStageList = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller paramUnmarshaller, Object paramObject)
  {
    this.academyStageList.clear();
    Iterator localIterator = this.academyStage.iterator();
    while (localIterator.hasNext())
    {
      AcademyStage localAcademyStage = (AcademyStage)localIterator.next();
      this.academyStageList.put(localAcademyStage.getStage(), localAcademyStage);
    }
  }

  public int size()
  {
    return this.academyStageList.size();
  }

  public AcademyStage getSpawnsForStage(int paramInt)
  {
    return (AcademyStage)this.academyStageList.get(paramInt);
  }
}