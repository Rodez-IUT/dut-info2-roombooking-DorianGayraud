package xmlws.roombooking.xmltools.samples;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import xmlws.roombooking.xmltools.RoomBooking;
import xmlws.roombooking.xmltools.RoomBookingParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class for objects responsible of RoomBooking xml files parsing
 * SAX version
 */
public class RoomBookingSaxParser implements RoomBookingParser {

    /* Format de date */
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Parse an xml file provided as an input stream
     *
     * @param inputStream the input stream corresponding to the xml file
     */
    public RoomBooking parse(InputStream inputStream) {
        RoomBooking roomBooking = new RoomBooking();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingBasicHandler(roomBooking));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomBooking;
    }

    private class RoomBookingBasicHandler extends DefaultHandler {

        /* Permet que chaque instance est son propre contexte */
        private RoomBooking roomBooking;

        /* Contient la balise courante du parsing */
        private String balise;

        public RoomBookingBasicHandler(RoomBooking roomBooking) {
            this.roomBooking = roomBooking;
        }

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {
                balise = localName;
            System.out.println("In element: "+localName);
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            String content = new String(ch, start, length);
            if (content == null || content.startsWith("\n")) {
                // evite un crash pour NullPointerException
            } else if (balise.equals("label")) {
                roomBooking.setRoomLabel(content);
            } else if (balise.equals("username")) {
                roomBooking.setUsername(content);
            } else if (balise.equals("startDate")) {
                /* On convertir la string en date via SimpleDateFormat */
                try {
                    roomBooking.setStartDate(sdf.parse(content));
                } catch (ParseException err) {
                    err.printStackTrace();
                }
            } else if (balise.equals("endDate")) {
                /* On convertir la string en date via SimpleDateFormat */
                try {
                    roomBooking.setEndDate(sdf.parse(content));
                } catch (ParseException err) {
                    err.printStackTrace();
                }
            }
            System.out.println(content);
        }
    }

}
