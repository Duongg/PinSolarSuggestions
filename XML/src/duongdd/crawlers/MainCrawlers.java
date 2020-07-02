package duongdd.crawlers;

import org.xml.sax.SAXException;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class MainCrawlers {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XMLStreamException {
        System.out.println("----------------------Dien may---------------------");
        MenuDienMayCrawlers menuDienMayCrawlers = new MenuDienMayCrawlers();
        menuDienMayCrawlers.crawlDienMayPHT();

//        MenuLedLightCrawlers menuLedLightCrawlers = new MenuLedLightCrawlers();
//        menuLedLightCrawlers.crawlDetailLightProduct();

        MenuNTCrawlers menuNTCrawlers = new MenuNTCrawlers();
        menuNTCrawlers.crawlDienMayNT();

//        System.out.println("----------------------Pin Solar---------------------");
//        MenuPinSolarCrawlers menuPinSolarCrawlers = new MenuPinSolarCrawlers();
//        menuPinSolarCrawlers.crawlProductPinSolar();
    }
}
