/*
 * This file is part of aiongemu <aiongemu.com>.
 *
 *  aiongemu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aiongemu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aiongemu.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameserver.model.trade;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author resurrected , Mcrizza
 *
 */
@XmlType(name = "TradeListType")
@XmlEnum
public enum TradeListType
{
	KINAH,
	ABYSS,
	COUPON,
	EXTRA,
	TRADE
}
