package duongdd.utils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class XMLUtils {
    public static BufferedReader getConnectionURL(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        connection.setReadTimeout(60000);
        connection.setConnectTimeout(60000);
        InputStream is = connection.getInputStream();
        if (is == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return reader;
    }

    public static Document parseToDom(String content) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content)));
            return doc;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static XPath createXpath(){
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        return xpath;
    }
}
