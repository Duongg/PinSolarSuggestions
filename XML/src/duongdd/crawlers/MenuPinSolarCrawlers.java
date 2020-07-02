package duongdd.crawlers;

import duongdd.dao.PinsolarProductDAO;
import duongdd.dtos.PinSolarDTO;
import duongdd.entity.PinSolarProductEntity;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.PinSolarXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuPinSolarCrawlers {
    public String crawlPinSolarMenu() throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        String htmlContent = XMLCrawler.crawlData(XMLSign.Pin_Solar_Domain, XMLSign.Pin_Solar_Menu_beginSign, XMLSign.Pin_Solar_Menu_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        return htmlContent;
    }

    public void crawlProductPinSolar() throws IOException, SAXException, ParserConfigurationException, XMLStreamException {
        //get html content menu
        String contentMenu = crawlPinSolarMenu();
        PinsolarProductDAO dao = new PinsolarProductDAO();
        List<String> listUrlMenu = new ArrayList<>();
        List<String> listUrlProduct = new ArrayList<>();
        List<String> listUrlPage = new ArrayList<>();
        String urlCategory = "";
        String urlProduct = "";
        String urlPage = "";
        String htmlContentCategory = "";
        String htmlContentPage = "";
        PinSolarXpaths xpaths = new PinSolarXpaths();
        PinSolarProductCrawlers pinSolarProductCrawlers = new PinSolarProductCrawlers();
        PinSolarProductEntity pinSolar = new PinSolarProductEntity();
        //list url menu
        listUrlMenu = xpaths.xpathMenuPinSolar(contentMenu);

        for (int i = 0; i < listUrlMenu.size(); i++) {
            //url category
            urlCategory = listUrlMenu.get(i);
            htmlContentCategory = pinSolarProductCrawlers.crawlPageNumberSolar(urlCategory);
            // list url page
            listUrlPage = xpaths.xpathUrlPage(htmlContentCategory);
            for (int k = 0; k < listUrlPage.size(); k++) {
                urlPage = listUrlPage.get(k);
                htmlContentPage = pinSolarProductCrawlers.crawlPinSolar(urlPage);
                // list url product
                listUrlProduct = xpaths.xpathProductPinSolar(htmlContentPage);
                for (int j = 0; j < listUrlProduct.size(); j++) {
                    //url product
                    urlProduct = listUrlProduct.get(j);
                   pinSolar = pinSolarProductCrawlers.crawlDetailProduct(urlProduct);
                    dao.insertPinSolar(pinSolar);
                }
            }

        }
    }


}
