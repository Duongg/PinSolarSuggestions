package duongdd.crawlers;

import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.PinSolarXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuPinSolarCrawlers {
    public String crawlPinSolarMenu() throws ParserConfigurationException, SAXException, IOException {
        String htmlContent = XMLCrawler.crawlData(XMLSign.Pin_Solar_Domain, XMLSign.Pin_Solar_Menu_beginSign, XMLSign.Pin_Solar_Menu_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.TagChecker(htmlContent);
        return htmlContent;
    }

    public void crawlProductPinSolar() throws IOException, SAXException, ParserConfigurationException {
        //get html content menu
        String contentMenu = crawlPinSolarMenu();

        List<String> listUrlMenu = new ArrayList<>();
        List<String> listUrlProduct = new ArrayList<>();
        List<String> listUrlPage = new ArrayList<>();
        String urlCategory = "";
        String urlProduct = "";
        String urlPage = "";
        String htmlContentCategory = "";
        String htmlContentPage = "";
        String htmlContentDetailProduct = "";
        PinSolarXpaths xpaths = new PinSolarXpaths();
        PinSolarProductCrawlers pinSolarProductCrawlers = new PinSolarProductCrawlers();
        //list url menu
        listUrlMenu = xpaths.xpathMenuPinSolar(contentMenu);

        for (int i = 0; i < listUrlMenu.size(); i++) {
            //url category
            urlCategory = listUrlMenu.get(i);
            htmlContentCategory = pinSolarProductCrawlers.crawlPinSolar(urlCategory);
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
                    pinSolarProductCrawlers.crawlDetailProduct(urlProduct);
                }
            }

        }
    }


}
