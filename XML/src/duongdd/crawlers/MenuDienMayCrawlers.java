package duongdd.crawlers;

import duongdd.dao.BrandProductDAO;
import duongdd.dao.CategoryProductDAO;
import duongdd.dao.ElectrictProductDAO;
import duongdd.dtos.ProductDTO;
import duongdd.entity.ElectricProductEntity;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.CategoryDienMayXpaths;
import duongdd.xpaths.MenuDienMayXpaths;
import duongdd.xpaths.ProductDienMayXpaths;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

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
        String nameCategory = "";
        String contentDataPages = "";
        String contentBrand = "";
        String htmlContent = "";
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        CategoryDienMayXpaths xpaths = new CategoryDienMayXpaths();
        ProductDienMayXpaths productDienMayXpaths = new ProductDienMayXpaths();
        ProductDienMayCrawlers productDienMayCrawlers = new ProductDienMayCrawlers();

        CategoryProductDAO categoryProductDAO = new CategoryProductDAO();
        BrandProductDAO brandProductDAO = new BrandProductDAO();
        ElectrictProductDAO electrictProductDAO = new ElectrictProductDAO();

        List<String> listUrlPages = new ArrayList<>();
        List<String> listUrlDetailProduct = new ArrayList<>();
        List<String> listUrlMenu = new ArrayList<>();
        List<String> listBrand = new ArrayList<>();
        List<String> listUrlMenuBrand = new ArrayList<>();
        Map<String, List<String>> categoryMap = new HashMap<>();
        ElectricProductEntity electricProductEntity = new ElectricProductEntity();

        // content menu
        htmlContent = crawlMenuDM();
        //get url menu by category map
        categoryMap = menuDienMayXpaths.xpathUrlMenu(htmlContent);

        //insert category by name
        for (Map.Entry<String, List<String>> entryNameCategory : categoryMap.entrySet()){
            String name = entryNameCategory.getKey();
            categoryProductDAO.insertCategory(name);
        }

        //insert brand by name
        for (Map.Entry<String, List<String>> entrybrand : categoryMap.entrySet()){
            listUrlMenuBrand = entrybrand.getValue();
            // get brand
            for (int b = 0; b < listUrlMenuBrand.size(); b++) {
                String url = listUrlMenuBrand.get(b);
                contentBrand = productDienMayCrawlers.crawBrandDMProduct(url);
                listBrand = productDienMayXpaths.xpathBrandProduct(contentBrand);
                for(int c = 0; c< listBrand.size(); c++){
                    String brand = listBrand.get(c);
                    brandProductDAO.insertBrand(brand);
                }
            }
        }

        //insert product
        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            //list url category
            nameCategory = entry.getKey();
            listUrlMenu = entry.getValue();
            System.out.println("--------------------" + nameCategory + "--------------------------");

            // get product by category
            for (int x = 0; x < listUrlMenu.size(); x++) {
                // urlCategory
                urlCategory = listUrlMenu.get(x);
                //get pages category
                contentCategory = productDienMayCrawlers.crawlCategoryProduct(nameCategory, urlCategory);
                //list url pages
                listUrlPages = xpaths.xpathUrlPage(contentCategory);
                if (listUrlPages.size() == 0) {
                    listUrlPages.add(urlCategory);
                }
                for (int j = 0; j < listUrlPages.size(); j++) {
                    //url 1 page
                    String urlPages = listUrlPages.get(j);
                    contentDataPages = productDienMayCrawlers.crawlProductPages(nameCategory, urlPages);
                    //list url detail product
                    listUrlDetailProduct = productDienMayXpaths.xpathUrlDetailProduct(nameCategory, contentDataPages);
                    for (int k = 0; k < listUrlDetailProduct.size(); k++) {
                        //url 1 detail product
                        String urlDetailProduct = listUrlDetailProduct.get(k);
                        electricProductEntity = productDienMayCrawlers.crawlDetailProduct(nameCategory, urlDetailProduct);
                        electrictProductDAO.insertProduct(electricProductEntity);
                    }//end for crawl detail
                }// end for crawl pages
            }
        }// end for category

    }
    public void insertCategory(){

    }

}
