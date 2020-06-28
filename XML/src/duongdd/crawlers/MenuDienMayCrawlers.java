package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.CategoryDienMayXpaths;
import duongdd.xpaths.MenuDienMayXpaths;
import duongdd.xpaths.ProductDienMayXpaths;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuDienMayCrawlers {
    public String crawlMenuDM() throws ParserConfigurationException, SAXException, IOException {
        String htmlContent = XMLCrawler.crawlData(XMLSign.DM_Domain, XMLSign.DM_beginSign, XMLSign.DM_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        return htmlContent;
    }

    public void crawlDataProduct() throws ParserConfigurationException, SAXException, IOException {
        String contentCategory = "";
        String urlCategory = "";
        String contentDataPages = "";
        String contentDetailProduct = "";
        String htmlContent = "";

        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        CategoryDienMayXpaths xpaths = new CategoryDienMayXpaths();
        ProductDienMayXpaths productDienMayXpaths = new ProductDienMayXpaths();
        ProductDienMayCrawlers productDienMayCrawlers = new ProductDienMayCrawlers();

        List<String> listUrlPages = new ArrayList<>();
        List<String> listUrlDetailProduct = new ArrayList<>();
        List<String> listUrlMenu = new ArrayList<>();

        ProductDTO dto = new ProductDTO();
        List<ProductDTO> listDTO = new ArrayList<>();
        // content menu
        htmlContent = crawlMenuDM();
        //get url menu
        listUrlMenu = menuDienMayXpaths.xpathUrlMenu(htmlContent);


        for (int i = 0; i < listUrlMenu.size(); i++) {
            //list url category
            urlCategory = listUrlMenu.get(i);

            contentCategory = XMLCrawler.crawlData(urlCategory, XMLSign.DM_Category_beginSign, XMLSign.DM_Category_endSign);
            contentCategory = XMLChecker.encodeContent(contentCategory);
            contentCategory = XMLChecker.fixTagName(contentCategory);
            contentCategory = XMLChecker.preParseDM(contentCategory);
            //list url pages
            listUrlPages = xpaths.xpathUrlPage(contentCategory);
            if(listUrlPages.size() == 0){
                listUrlPages.add(urlCategory);

            }

            for (int j = 0; j < listUrlPages.size(); j++) {
                //url 1 page
                String urlPages = listUrlPages.get(j);

                contentDataPages = productDienMayCrawlers.crawlProductPages(urlPages);

                //list url detail product
                listUrlDetailProduct = productDienMayXpaths.xpathUrlDetailProduct(contentDataPages);

                for (int k = 0; k < listUrlDetailProduct.size(); k++) {
                    //url 1 detail product
                    String urlDetailProduct = listUrlDetailProduct.get(k);
                  dto = productDienMayCrawlers.crawlDetailProduct(urlDetailProduct);

                }//end for crawl detail
            }// end for crawl pages
        }// end for category

    }

}
