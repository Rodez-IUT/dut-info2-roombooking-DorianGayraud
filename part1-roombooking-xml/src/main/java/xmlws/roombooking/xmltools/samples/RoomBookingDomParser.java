package xmlws.roombooking.xmltools.samples;

        import org.w3c.dom.Document;
        import xmlws.roombooking.xmltools.RoomBooking;
        import xmlws.roombooking.xmltools.RoomBookingParser;

        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import java.io.InputStream;
        import java.text.SimpleDateFormat;

/**
 *  Class for objects responsible of RoomBooking xml files parsing
 *  DOM version
 */
public class RoomBookingDomParser implements RoomBookingParser {

    /**
     * Parse an xml file provided as an input stream
     * @param inputStream the input stream corresponding to the xml file
     */
    public RoomBooking parse(InputStream inputStream) {
        RoomBooking roomBooking = new RoomBooking();
        /* Format de date */
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputStream);
            roomBooking.setRoomLabel(doc.getElementsByTagName("label").item(0).getTextContent());
            roomBooking.setUsername(doc.getElementsByTagName("username").item(0).getTextContent());
            roomBooking.setStartDate(sdf.parse(doc.getElementsByTagName("startDate").item(0).getTextContent()));
            roomBooking.setEndDate(sdf.parse(doc.getElementsByTagName("endDate").item(0).getTextContent()));
            System.out.println(doc.getElementsByTagName("username").item(0).getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roomBooking;
    }

}
