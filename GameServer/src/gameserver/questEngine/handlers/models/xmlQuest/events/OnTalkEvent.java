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

package gameserver.questEngine.handlers.models.xmlQuest.events;

import gameserver.questEngine.handlers.models.xmlQuest.QuestVar;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author Mr. Poke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OnTalkEvent", propOrder = {"var"})
public class OnTalkEvent extends QuestEvent {

    protected List<QuestVar> var;

    public boolean operate(QuestCookie env) {
        if (conditions == null || conditions.checkConditionOfSet(env)) {
            QuestState qs = env.getPlayer().getQuestStateList().getQuestState(env.getQuestId());
            for (QuestVar questVar : var) {
                if (questVar.operate(env, qs))
                    return true;
            }
        }
        return false;
    }
}
