package duongdd.crawlers;

import duongdd.dao.BrandProductDAO;
import duongdd.dao.CategoryProductDAO;
import duongdd.dao.ElectrictProductDAO;
import duongdd.dtos.ProductDTO;
import duongdd.entity.BrandProductEntity;
import duongdd.entity.CategoryProductEntity;
import duongdd.entity.ElectricProductEntity;
import duongdd.jaxb.Jaxb;
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
    private List<BrandProductEntity> listBrandDB;
    private List<CategoryProductEntity> listCategoryDB;
    private String htmlContent = "";
    public void crawlDienMayPHT() throws ParserConfigurationException, SAXException, IOException {
        System.out.println("----------------------Điện máy PHT--------------------------");
        crawlMenuDM();
        crawlCategory();
        crawlBrand();
        crawlDataProduct();
    }

    public String crawlMenuDM() throws ParserConfigurationException, SAXException, IOException {
        htmlContent = XMLCrawler.crawlData(XMLSign.DM_Domain, XMLSign.DM_beginSign, XMLSign.DM_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        return htmlContent;
    }

    public void crawlCategory() throws IOException, SAXException, ParserConfigurationException {
        Map<String, List<String>> categoryMap = new HashMap<>();
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        CategoryProductDAO categoryProductDAO = new CategoryProductDAO();
        categoryMap = menuDienMayXpaths.xpathUrlMenu(htmlContent);
        //insert category by name
        for (Map.Entry<String, List<String>> entryNameCategory : categoryMap.entrySet()) {
            String name = entryNameCategory.getKey();
            categoryProductDAO.insertCategory(name.trim());
        }
        listCategoryDB = categoryProductDAO.getAllCategory();
    }

    public void crawlBrand() throws IOException, SAXException, ParserConfigurationException {
        Map<String, List<String>> categoryMap = new HashMap<>();
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        ProductDienMayCrawlers productDienMayCrawlers = new ProductDienMayCrawlers();
        ProductDienMayXpaths productDienMayXpaths = new ProductDienMayXpaths();
        BrandProductDAO brandProductDAO = new BrandProductDAO();
        //get url menu
        String contentBrand = "";
        List<String> listUrlMenuBrand = new ArrayList<>();
        List<String> listBrand = new ArrayList<>();

        categoryMap = menuDienMayXpaths.xpathUrlMenu(htmlContent);
        for (Map.Entry<String, List<String>> entrybrand : categoryMap.entrySet()) {
            listUrlMenuBrand = entrybrand.getValue();
            // get brand
            for (int b = 0; b < listUrlMenuBrand.size(); b++) {
                String url = listUrlMenuBrand.get(b);
                contentBrand = productDienMayCrawlers.crawBrandDMProduct(url);
                listBrand = productDienMayXpaths.xpathBrandProduct(contentBrand);
                for (int c = 0; c < listBrand.size(); c++) {
                    String brand = listBrand.get(c);
                    brandProductDAO.insertBrand(brand.trim());
                }
            }
        }
        listBrandDB = brandProductDAO.getAllBrand();
    }

    public void crawlDataProduct() throws ParserConfigurationException, SAXException, IOException {

        String contentCategory = "";
        String urlCategory = "";
        String nameCategory = "";
        String contentDataPages = "";
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        CategoryDienMayXpaths xpaths = new CategoryDienMayXpaths();
        ProductDienMayXpaths productDienMayXpaths = new ProductDienMayXpaths();
        ProductDienMayCrawlers productDienMayCrawlers = new ProductDienMayCrawlers();
        List<String> listUrlPages = new ArrayList<>();
        List<String> listUrlDetailProduct = new ArrayList<>();
        List<String> listUrlMenu = new ArrayList<>();
        Map<String, List<String>> categoryMap = new HashMap<>();
        ProductDTO dto = new ProductDTO();
        // content menu
        //get url menu by category map
        categoryMap = menuDienMayXpaths.xpathUrlMenu(htmlContent);

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
                        dto = productDienMayCrawlers.crawlDetailProduct(nameCategory, urlDetailProduct);
                        insertProduct(dto);

                    }//end for crawl detail
                }// end for crawl pages
            }
        }// end for category

    }

    public void insertProduct(ProductDTO dto) throws IOException, SAXException, ParserConfigurationException {
        ElectrictProductDAO dao = new ElectrictProductDAO();
        if (dto != null) {
            ElectricProductEntity productEntity = new ElectricProductEntity();
            try {
                productEntity.setProductName(dto.getProductName());

                productEntity.setProductCapacity(dto.getProductCapacity());

                for (int i = 0; i < listBrandDB.size(); i++) {
                    if (dto.getProductName().toUpperCase().contains(listBrandDB.get(i).getNameBrand())) {
                        productEntity.setIdBrand(listBrandDB.get(i).getIdBrandProduct());
                        break;
                    }
                }
                for (int j = 0; j < listCategoryDB.size(); j++) {
                    if (dto.getProductCategory().equals(listCategoryDB.get(j).getNameCategory())) {
                        productEntity.setIdCategory(listCategoryDB.get(j).getIdCategory());
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            boolean validate = Jaxb.doubleCheckElectricProduct(XMLSign.FILE_PATH_ELECTRIC_PRODUCT, productEntity);
            if(validate){
                dao.insertProduct(productEntity);
            }
        }
    }

}
