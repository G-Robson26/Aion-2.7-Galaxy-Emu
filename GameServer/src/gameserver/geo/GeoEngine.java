/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
 
package gameserver.geo;

import aionjHungary.geoEngine.GeoWorldLoader;
import aionjHungary.geoEngine.models.GeoMap;
import aionjHungary.geoEngine.scene.Spatial;
import gameserver.configs.main.GSConfig;
import gameserver.dataholders.DataManager;
import gameserver.model.gameobjects.VisibleObject;
import gameserver.model.templates.WorldMapTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class GeoEngine
{
  private static Logger log = Logger.getLogger(GeoEngine.class);

  private Map<Integer, GeoMap> geoMaps = new HashMap<Integer, GeoMap>();

  public static final GeoEngine getInstance()
  {
    return SingletonHolder.instance;
  }

  GeoEngine() {
    if (GSConfig.GEODATA_ENABLED) 
    {
      Map<String, Spatial> models = null;
      try {
        models = GeoWorldLoader.loadMeshs();
      } catch (IOException ex) {
        log.info("Geodata engine " + ex);
      }
      for (WorldMapTemplate map : DataManager.WORLD_MAPS_DATA) {
        GeoMap geoMap = new GeoMap(Integer.toString(map.getMapId().intValue()), map.getWorldSize());
        try {
          if (GeoWorldLoader.loadWorld(map.getMapId().intValue(), models, geoMap))
            this.geoMaps.put(map.getMapId(), geoMap);
        }
        catch (IOException ex) {
          log.info("Geodata engine " + ex);
        }
      }
      models.clear();
      models = null;

      log.info("Geodata engine: " + this.geoMaps.size() + " geoMaps loaded!");
    } else {
      log.info("Geodata engine disabled.");
    }
  }

  public float getZ(int worldId, float x, float y, float z)
  {
    if ((GSConfig.GEODATA_ENABLED) && (this.geoMaps.containsKey(Integer.valueOf(worldId)))) 
    {
      float newZ = geoMaps.get(worldId).getZ(x, y, z);
      if(newZ > 0)
    	  return newZ;
      else
    	  return z;
    }
    return z;
  }

  public boolean canSee(VisibleObject object, VisibleObject target)
  {
    return canSee(object.getWorldId(), object.getX(), object.getY(), object.getZ(), target.getX(), target.getY(), target.getZ());
  }

  public boolean canSee(int worldId, float x, float y, float z, float targetX, float targetY, float targetZ)
  {
    if ((GSConfig.GEODATA_ENABLED) && (this.geoMaps.containsKey(Integer.valueOf(worldId)))) {
      return ((GeoMap)this.geoMaps.get(Integer.valueOf(worldId))).canSee(x, y, z, targetX, targetY, targetZ);
    }
    return true;
  }

  public GeoMap getGeoMapByWorldId(int worldId)
  {
    return (GeoMap)this.geoMaps.get(Integer.valueOf(worldId));
  }

  private static class SingletonHolder
  {
    protected static final GeoEngine instance = new GeoEngine();
  }
}
