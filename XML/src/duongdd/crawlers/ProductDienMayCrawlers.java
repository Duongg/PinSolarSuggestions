package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.entity.ElectricProductEntity;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.ProductDienMayXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ProductDienMayCrawlers {
    public String crawlProductPages(String namecate, String url) throws ParserConfigurationException, SAXException, IOException {
        String contentDataPages = XMLCrawler.crawlData(url, XMLSign.DM_Pages_beginSign, XMLSign.DM_Pages_endSign);
        contentDataPages = XMLChecker.encodeContent(contentDataPages);
        contentDataPages = XMLChecker.fixTagName(contentDataPages);
        return contentDataPages;
    }

    public ProductDTO crawlDetailProduct(String namecate, String urlDetail) throws ParserConfigurationException, SAXException, IOException {
        ProductDTO dto = new ProductDTO();
        String contentDetail = "";
        ProductDienMayXpaths productDienMayXpaths = new ProductDienMayXpaths();
        try {
            contentDetail = XMLCrawler.crawlData(urlDetail, XMLSign.DM_Detail_Product_beginSign, XMLSign.DM_Detail_Product_endSign);
            contentDetail = XMLChecker.encodeContent(contentDetail);
            contentDetail = XMLChecker.fixTagName(contentDetail);
            contentDetail = XMLChecker.preParseDM(contentDetail);
            dto = productDienMayXpaths.xpathProduct(namecate,contentDetail);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dto;
    }

    public String crawlCategoryProduct(String nameCate, String url) throws ParserConfigurationException, SAXException, IOException {
        String contentCategory = "";
        contentCategory = XMLCrawler.crawlData(url, XMLSign.DM_Category_beginSign, XMLSign.DM_Category_endSign);
        contentCategory = XMLChecker.encodeContent(contentCategory);
        contentCategory = XMLChecker.fixTagName(contentCategory);
        contentCategory = XMLChecker.preParseDM(contentCategory);
        return contentCategory;
    }
    public String crawBrandDMProduct(String url) throws ParserConfigurationException, SAXException, IOException {
        String contentBrand = "";
        contentBrand = XMLCrawler.crawlData(url, XMLSign.DM_Brand_beginSign, XMLSign.DM_Brand_endSign);
        contentBrand = XMLChecker.encodeContent(contentBrand);
        contentBrand = XMLChecker.fixTagName(contentBrand);
        return contentBrand;
    }
}
