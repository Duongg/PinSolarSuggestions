package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.MenuLightXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ProductLightCrawlers {
    // content category product
    public void crawlLightProduct(String urlProduct){
        MenuLightXpaths menuLightXpaths = new MenuLightXpaths();
        ProductDTO dto = new ProductDTO();
        String contentProduct = "";
        try {
            contentProduct = XMLCrawler.crawlData(urlProduct, XMLSign.Light_Product_beginSign, XMLSign.Light_Product_endSign);
            contentProduct = XMLChecker.encodeContent(contentProduct);
            contentProduct = XMLChecker.fixTagName(contentProduct);
            contentProduct = XMLChecker.preParseLedLight(contentProduct);
            // xpath detail product dto
            dto = menuLightXpaths.xpathDetailLightProduct(contentProduct);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
