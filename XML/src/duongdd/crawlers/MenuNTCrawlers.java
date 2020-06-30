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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> categoryMap = new HashMap<>();
        // get url menu
        String htmlContent = crawlerMenuNT();
        List<String> listUrlPage = new ArrayList<>();
        List<String> listDetailProducts = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();

        String urlMenu = "";
        String htmlProduct = "";
        String htmlContentPages = "";
        String nameCategory = "";
        // map category
        categoryMap = menuDienMayXpaths.xpathUrlMenuNT(htmlContent);


        for(Map.Entry<String, String> entry : categoryMap.entrySet()){
            nameCategory = entry.getKey();
            urlMenu = entry.getValue();
            // get pages product\
            System.out.println("------------------" + nameCategory + "--------------------------");
            htmlProduct = productNTDienMayCrawlers.crawlPageProductNT(nameCategory,urlMenu);

            listUrlPage = ntProductXpaths.xpathUrlPageNT(htmlProduct);
            if(listUrlPage.size() == 0){
                listUrlPage.add(urlMenu);
            }
            for(int j = 0; j < listUrlPage.size(); j++){
                String urlPage = listUrlPage.get(j);
                htmlContentPages = productNTDienMayCrawlers.crawlProductPagesNT(nameCategory,urlPage);
                listDetailProducts = ntProductXpaths.xpathUrlDetailProduct(nameCategory,htmlContentPages);
                for(int k = 0; k < listDetailProducts.size(); k++){
                    String urlDetail = listDetailProducts.get(k);
                  dto = productNTDienMayCrawlers.crawlDetailProductNT(nameCategory,urlDetail);
                  productDTOList.add(dto);
                }
            }
        }

    }
}
