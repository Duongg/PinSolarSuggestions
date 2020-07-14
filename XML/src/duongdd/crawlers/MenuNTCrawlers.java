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
    public String htmlContent = "";
    public List<BrandProductEntity> listBrandDB;
    public List<CategoryProductEntity> listCategoryDB;

    public void crawlDienMayNT() throws ParserConfigurationException, SAXException, IOException {
        System.out.println("----------------- Điện máy nhất tín -------------");
        crawlerMenuNT();
        crawlCategory();
        getBrand();
        crawlDataProductNT();
    }

    public String crawlerMenuNT() throws ParserConfigurationException, SAXException, IOException {
        htmlContent = XMLCrawler.crawlData(XMLSign.NT_Domain, XMLSign.NT_beginSign, XMLSign.NT_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        return htmlContent;
    }

    public void crawlCategory() {
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        CategoryProductDAO dao = new CategoryProductDAO();
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap = menuDienMayXpaths.xpathUrlMenuNT(htmlContent);
        for (Map.Entry<String, String> entryCategory : categoryMap.entrySet()) {
            String name = entryCategory.getKey().trim();
            dao.insertCategory(name);
        }
        listCategoryDB = dao.getAllCategory();
    }

    public void getBrand() {
        BrandProductDAO brandProductDAO = new BrandProductDAO();
        listBrandDB = brandProductDAO.getAllBrand();
    }

    public void crawlDataProductNT() throws IOException, SAXException, ParserConfigurationException {
        MenuDienMayXpaths menuDienMayXpaths = new MenuDienMayXpaths();
        NTProductXpaths ntProductXpaths = new NTProductXpaths();
        ProductDTO dto = new ProductDTO();
        ProductNTDienMayCrawlers productNTDienMayCrawlers = new ProductNTDienMayCrawlers();
        Map<String, String> categoryMap = new HashMap<>();
        // get url menu
        List<String> listUrlPage = new ArrayList<>();
        List<String> listDetailProducts = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();

        String urlMenu = "";
        String htmlProduct = "";
        String htmlContentPages = "";
        String nameCategory = "";
        // map category
        categoryMap = menuDienMayXpaths.xpathUrlMenuNT(htmlContent);


        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            nameCategory = entry.getKey().trim();
            urlMenu = entry.getValue();
            // get pages product\
            System.out.println("------------------" + nameCategory + "--------------------------");
            htmlProduct = productNTDienMayCrawlers.crawlPageProductNT(nameCategory, urlMenu);

            listUrlPage = ntProductXpaths.xpathUrlPageNT(htmlProduct);
            if (listUrlPage.size() == 0) {
                listUrlPage.add(urlMenu);
            }
            for (int j = 0; j < listUrlPage.size(); j++) {
                String urlPage = listUrlPage.get(j);
                htmlContentPages = productNTDienMayCrawlers.crawlProductPagesNT(nameCategory, urlPage);
                listDetailProducts = ntProductXpaths.xpathUrlDetailProduct(nameCategory, htmlContentPages);
                for (int k = 0; k < listDetailProducts.size(); k++) {
                    String urlDetail = listDetailProducts.get(k);
                    dto = productNTDienMayCrawlers.crawlDetailProductNT(nameCategory, urlDetail);
                    if (dto.getProductName() != null) {
                        insertProduct(dto);
                    }

                }
            }
        }

    }

    public void insertProduct(ProductDTO dto) {
        ElectrictProductDAO electrictProductDAO = new ElectrictProductDAO();

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
                electrictProductDAO.insertProduct(productEntity);
            }

        }
    }
}
