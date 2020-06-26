package duongdd.crawlers;

import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ProductLightCrawlers {
    // content category product
    public String crawlLightProduct(String urlProduct){
        String contentProduct = "";
        try {
            contentProduct = XMLCrawler.crawlData(urlProduct, XMLSign.Light_Product_beginSign, XMLSign.Light_Product_endSign);
            contentProduct = XMLChecker.encodeContent(contentProduct);
            contentProduct = XMLChecker.TagChecker(contentProduct);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentProduct;
    }
}
