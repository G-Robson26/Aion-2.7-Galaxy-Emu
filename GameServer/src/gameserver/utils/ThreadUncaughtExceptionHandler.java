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
package gameserver.utils;

import org.apache.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author -Nemesiss-
 */
public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler {
    /**
     * Logger for this class.
     */
    private static final Logger log = Logger.getLogger(ThreadUncaughtExceptionHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Critical Error - Thread: " + t.getName() + " terminated abnormaly: " + e, e);
        if (e instanceof OutOfMemoryError) {
            // TODO try get some memory or restart
            log.error("Out of memory! You should get more memory!");
        }
        // TODO! some threads should be "restarted" on error
    }
}
