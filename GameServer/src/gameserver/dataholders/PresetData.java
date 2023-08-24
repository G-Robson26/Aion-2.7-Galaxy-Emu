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
package gameserver.dataholders;

import gameserver.model.templates.preset.PresetTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * @author Rolandas
 *
 */
@XmlRootElement(name = "custom_presets")
@XmlType(name = "", propOrder = { "presets" })
@XmlAccessorType(XmlAccessType.FIELD)
public class PresetData
{
	@XmlElement(name = "preset", required = true)
	private List<PresetData.Preset> presets;
	
	@XmlTransient
	private Map<String, PresetTemplate> presetByName;
	
	void afterUnmarshal(Unmarshaller u, Object parent)
	{
		presetByName = new HashMap<String, PresetTemplate>();
		for(PresetTemplate it : presets)
		{
			presetByName.put(it.getName(), it);
		}
		
		presets.clear();
		presets = null;
	}
	
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Preset extends PresetTemplate
    {
    }
	
	public int size ()
	{
		return presetByName.size();
	}
	
	public PresetTemplate getPresetTemplate(String name)
	{
		name = name.toUpperCase();
		if (presetByName.containsKey(name))
			return presetByName.get(name);
		return null;
	}
	
	public Collection<PresetTemplate> getPresetTemplates()
	{
		return presetByName.values();
	}
}
