/**
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is private software: you can redistribute it and/or modify
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

package gameserver.model.templates.flyring;

import gameserver.model.utils3d.Point3D;

import javax.xml.bind.annotation.*;

/**
 * @author xavier
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlyRing")
public class FlyRingTemplate {
    @XmlAttribute(name = "name")
    protected String name;

    @XmlAttribute(name = "map")
    protected int map;

    @XmlAttribute(name = "radius")
    protected float radius;

    @XmlElement(name = "center")
    protected FlyRingPoint center;

    @XmlElement(name = "p1")
    protected FlyRingPoint p1;

    @XmlElement(name = "p2")
    protected FlyRingPoint p2;

    public String getName() {
        return name;
    }

    public int getMap() {
        return map;
    }

    public float getRadius() {
        return radius;
    }

    public FlyRingPoint getCenter() {
        return center;
    }

    public FlyRingPoint getP1() {
        return p1;
    }

    public FlyRingPoint getP2() {
        return p2;
    }

    public FlyRingTemplate() {
    }

    ;

    public FlyRingTemplate(String name, int mapId, Point3D center, Point3D p1, Point3D p2) {
        this.name = name;
        this.map = mapId;
        this.radius = 6;
        this.center = new FlyRingPoint(center);
        this.p1 = new FlyRingPoint(p1);
        this.p2 = new FlyRingPoint(p2);
    }
}
