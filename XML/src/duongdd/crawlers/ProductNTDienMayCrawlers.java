package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.NTProductXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProductNTDienMayCrawlers {
    public String crawlProductPagesNT(String url) throws ParserConfigurationException, SAXException, IOException {
        String contentDataPages = XMLCrawler.crawlData(url, XMLSign.NT_Product_Pages_beginSign, XMLSign.NT_Product_Pages_endSign);
        contentDataPages = XMLChecker.encodeContent(contentDataPages);
        contentDataPages = XMLChecker.fixTagName(contentDataPages);
        return contentDataPages;
    }
    public ProductDTO crawlDetailProductNT(String urlDetail) throws ParserConfigurationException, SAXException, IOException {
        ProductDTO dto = new ProductDTO();
        NTProductXpaths ntProductXpaths = new NTProductXpaths();
        String contentDetail ="";
        try {
            contentDetail = XMLCrawler.crawlData(urlDetail, XMLSign.NT_Detail_Product_beginSign, XMLSign.NT_Detail_Product_endSign);
            contentDetail = XMLChecker.encodeContent(contentDetail);
            contentDetail = XMLChecker.fixTagName(contentDetail);
            System.out.println(contentDetail);
           // dto = ntProductXpaths.xpathProductNT(contentDetail);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
}
