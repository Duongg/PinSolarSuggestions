package duongdd.utils;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;

public class XMLCrawler {
    public static String crawlData(String uri, String begin, String end) throws IOException, ParserConfigurationException, SAXException {
        String htmlString = "";
        BufferedReader br = null;
        boolean isStart = false;
        boolean isFound = true;
        String line = "";
        if (uri != null) {
            br = XMLUtils.getConnectionURL(uri);
            if (br != null) {
                while ((line = br.readLine()) != null) {
                    if (isStart && line.contains(end)) {
                        break;
                    }
                    if (isStart) {
                        htmlString = htmlString + line.trim();
                    }
                    if (isFound && line.contains(begin)) {
                        isStart = true;
                    }
                }//end while
            }//end if br


        }
        return htmlString;
    }
}
