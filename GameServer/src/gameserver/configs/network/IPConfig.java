/**
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

package gameserver.configs.network;

import com.aionemu.commons.network.IPRange;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that is designed to read IPConfig.xml
 *
 * @author Taran
 * @author SoulKeeper
 */
public class IPConfig {

    /**
     * Logger
     */
    private static final Logger log = Logger.getLogger(IPConfig.class);

    /**
     * Location of config file
     */
    private static final String CONFIG_FILE = "./config/ipconfig.xml";

    /**
     * List of all ip ranges
     */
    private static final List<IPRange> ranges = new ArrayList<IPRange>();

    /**
     * Default address
     */
    private static byte[] defaultAddress;

    /**
     * Method that loads IPConfig
     */
    public static void load() {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new File(CONFIG_FILE), new DefaultHandler() {

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {

                    if (qName.equals("ipconfig")) {
                        try {
                            defaultAddress = InetAddress.getByName(attributes.getValue("default")).getAddress();
                        }
                        catch (UnknownHostException e) {
                            throw new RuntimeException("Failed to resolve DSN for address: "
                                    + attributes.getValue("default"), e);
                        }
                    } else if (qName.equals("iprange")) {
                        String min = attributes.getValue("min");
                        String max = attributes.getValue("max");
                        String address = attributes.getValue("address");
                        IPRange ipRange = new IPRange(min, max, address);
                        ranges.add(ipRange);
                    }
                }
            });
        }
        catch (Exception e) {
            log.fatal("Critical error while parsing ipConfig", e);
            throw new Error("Can't load ipConfig", e);
        }
    }

    /**
     * Returns list of ip ranges
     *
     * @return list of ip ranges
     */
    public static List<IPRange> getRanges() {
        return ranges;
    }

    /**
     * Returns default address
     *
     * @return default address
     */
    public static byte[] getDefaultAddress() {
        return defaultAddress;
	}
}
