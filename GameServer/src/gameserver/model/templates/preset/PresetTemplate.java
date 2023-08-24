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
package gameserver.model.templates.preset;

import gameserver.model.Gender;
import gameserver.model.PlayerClass;
import gameserver.model.templates.item.ItemRace;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author Rolandas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PresetTemplate", propOrder = { "height", "hairType", "faceType", "hairRGB",
												"lipsRGB", "skinRGB", "detail" })
public class PresetTemplate
{
	private float height;
	
	@XmlElement(name = "hair_type")
	private int hairType;
	
	@XmlElement(name = "face_type")
	private int faceType;
	
	@XmlElement(name = "hair_color", required = true)
	private String hairRGB;
	
	@XmlElement(name = "lip_color", required = true)
	private String lipsRGB;
	
	@XmlElement(name = "skin_color", required = true)
	private String skinRGB;
	
	@XmlElement(required = true)
	private String detail;
	
	@XmlAttribute(name = "name", required = true)
	private String name;
	
	@XmlAttribute(name = "class", required = true)
	private PlayerClass class_defined;
	
	@XmlAttribute(name = "race", required = true)
	private ItemRace race;
	
	@XmlAttribute(name = "gender", required = true)
	private Gender gender;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the class_defined
	 */
	public PlayerClass getPcClass()
	{
		return class_defined;
	}

	/**
	 * @return the race
	 */
	public ItemRace getRace()
	{
		return race;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender()
	{
		return gender;
	}

	/**
	 * @return the height
	 */
	public float getHeight()
	{
		return height;
	}

	/**
	 * @return the hairType
	 */
	public int getHairType()
	{
		return hairType;
	}

	/**
	 * @return the faceType
	 */
	public int getFaceType()
	{
		return faceType;
	}

	/**
	 * @return the hairRGB
	 */
	public String getHairRGB()
	{
		return hairRGB;
	}

	/**
	 * @return the lipsRGB
	 */
	public String getLipsRGB()
	{
		return lipsRGB;
	}

	/**
	 * @return the skinRGB
	 */
	public String getSkinRGB()
	{
		return skinRGB;
	}

	/**
	 * @return the detail
	 */
	public String getDetail()
	{
		return detail;
	}

}
