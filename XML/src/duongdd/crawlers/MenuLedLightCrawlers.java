package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.MenuLightXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuLedLightCrawlers {
    public String crawMenuLedLight() throws ParserConfigurationException, SAXException, IOException {
        String htmlContent = XMLCrawler.crawlData(XMLSign.Light_Domain, XMLSign.Light_Menu_beginSign,XMLSign.Light_Menu_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        htmlContent = XMLChecker.preParseLedLight(htmlContent);
        return htmlContent;
    }
    public void crawlDetailLightProduct() throws IOException, SAXException, ParserConfigurationException {
        MenuLightXpaths menuLightXpaths = new MenuLightXpaths();
        ProductLightCrawlers productLightCrawlers = new ProductLightCrawlers();
        String contentDetailProduct = "";
        ProductDTO dto = new ProductDTO();

        List<ProductDTO> listProductLight = new ArrayList<>();
        String contentMenu = crawMenuLedLight();
        //get url menu
        List<String> listUrlLightMenu = new ArrayList<>();
        listUrlLightMenu = menuLightXpaths.xpathUrlLightMenu(contentMenu);
        //get brand name
        List<String> listBrand = new ArrayList<>();
        listBrand = menuLightXpaths.xpathBrand(contentMenu);

        for(int i = 0; i < listUrlLightMenu.size(); i++){
            // url 1 category product
            String urlCategoryProduct = listUrlLightMenu.get(i);
                  contentDetailProduct = productLightCrawlers.crawlLightProduct(urlCategoryProduct);
                  dto = menuLightXpaths.xpathDetailLightProduct(contentDetailProduct);

        }

    }
}
