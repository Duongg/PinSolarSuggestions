package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.ProductDienMayXpaths;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ProductDienMayCrawlers {
    public String crawlProductPages(String url) throws ParserConfigurationException, SAXException, IOException {
        String contentDataPages = XMLCrawler.crawlData(url, XMLSign.DM_Pages_beginSign, XMLSign.DM_Pages_endSign);
        contentDataPages = XMLChecker.encodeContent(contentDataPages);
        contentDataPages = XMLChecker.TagChecker(contentDataPages);
        return contentDataPages;
    }
    public ProductDTO crawlDetailProduct(String urlDetail) throws ParserConfigurationException, SAXException, IOException {
        ProductDTO dto = new ProductDTO();
        String contentDetail ="";
        ProductDienMayXpaths productDienMayXpaths = new ProductDienMayXpaths();
        try {
           contentDetail = XMLCrawler.crawlData(urlDetail, XMLSign.DM_Detail_Product_beginSign, XMLSign.DM_Detail_Product_endSign);
            contentDetail = XMLChecker.encodeContent(contentDetail);
            contentDetail = XMLChecker.TagChecker(contentDetail);
           dto = productDienMayXpaths.xpathProduct(contentDetail);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }


}
