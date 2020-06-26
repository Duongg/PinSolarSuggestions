package duongdd.crawlers;

import duongdd.dtos.PinSolarDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;

import duongdd.xpaths.PinSolarXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PinSolarProductCrawlers {
    public String crawlPinSolar(String url) throws ParserConfigurationException, SAXException, IOException {
        String content = XMLCrawler.crawlData(url, XMLSign.Pin_Solar_Product_beginSign,XMLSign.Pin_Solar_Product_endSign);
        content = XMLChecker.encodeContent(content);
        content = XMLChecker.TagChecker(content);
        return content;
    }
    public void crawlDetailProduct(String urlDetail) throws ParserConfigurationException, SAXException, IOException {

        PinSolarDTO pinSolarDTO = new PinSolarDTO();
        PinSolarXpaths xpaths = new PinSolarXpaths();
        String contentDetail = "";
        contentDetail = XMLCrawler.crawlData(urlDetail, XMLSign.Pin_Solar_Detail_Product_beginSign, XMLSign.Pin_Solar_Detail_Product_endSign);
        contentDetail = XMLChecker.encodeContent(contentDetail);
        contentDetail = XMLChecker.TagChecker(contentDetail);
        pinSolarDTO = xpaths.xpathDetailPinSolar(contentDetail);
    }
}
