package duongdd.crawlers;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.MenuDienMayXpaths;
import duongdd.xpaths.NTProductXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuNTCrawlers {
    public String crawlerMenuNT() throws ParserConfigurationException, SAXException, IOException {
        String htmlContent = XMLCrawler.crawlData(XMLSign.NT_Domain, XMLSign.NT_beginSign, XMLSign.NT_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        return htmlContent;
    }
    public void crawlDataProductNT() throws IOException, SAXException, ParserConfigurationException {
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        NTProductXpaths ntProductXpaths = new NTProductXpaths();
        ProductDTO dto = new ProductDTO();
        ProductNTDienMayCrawlers productNTDienMayCrawlers = new ProductNTDienMayCrawlers();
        // get url menu
        String htmlContent = crawlerMenuNT();
        List<String> listUrlMenu = menuDienMayXpaths.xpathUrlMenuNT(htmlContent);

        List<String> listUrlPage = new ArrayList<>();
        List<String> listDetailProducts = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();
        String urlMenu = "";
        String htmlProduct = "";
        String htmlContentPages = "";
        for(int i = 0; i < listUrlMenu.size(); i++){
            urlMenu = listUrlMenu.get(1);
            htmlProduct = XMLCrawler.crawlData(urlMenu, XMLSign.NT_Category_beginSign, XMLSign.NT_Category_endSign);
            htmlProduct = XMLChecker.encodeContent(htmlProduct);
            htmlProduct = XMLChecker.fixTagName(htmlProduct);

            listUrlPage = ntProductXpaths.xpathUrlPageNT(htmlProduct);
            if(listUrlPage.size() == 0){
                listUrlPage.add(urlMenu);
            }
            for(int j = 0; j < listUrlPage.size(); j++){
                String urlPage = listUrlPage.get(1);
                htmlContentPages = productNTDienMayCrawlers.crawlProductPagesNT(urlPage);
                listDetailProducts = ntProductXpaths.xpathUrlDetailProduct(htmlContentPages);
                for(int k = 0; k < listDetailProducts.size(); k++){
                    String urlDetail = listDetailProducts.get(k);
                  dto = productNTDienMayCrawlers.crawlDetailProductNT(urlDetail);
                  productDTOList.add(dto);
                }
            }
        }

    }
}
