/*
 * This file is part of alpha team <alpha-team.com>.
 *
 * alpha team is pryvate software: you can redistribute it and/or modify
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
package gameserver.ai.events.handler;

import gameserver.ai.AI;
import gameserver.ai.events.Event;
import gameserver.ai.state.AIState;

/**
 * @author ATracer
 */
public class SeeCreatureEventHandler implements EventHandler {
    @Override
    public Event getEvent() {
        return Event.SEE_CREATURE;
    }

    @Override
    public void handleEvent(Event event, AI<?> ai) {
        ai.setAiState(AIState.ACTIVE);
        if (!ai.isScheduled())
            ai.analyzeState();
    }
}
