package duongdd.crawlers;

import duongdd.dao.CategoryPinSolarDAO;
import duongdd.dao.PinsolarProductDAO;
import duongdd.dtos.PinSolarDTO;
import duongdd.entity.CategoryPinSolarEntity;
import duongdd.entity.PinSolarProductEntity;
import duongdd.jaxb.Jaxb;
import duongdd.utils.XMLChecker;
import duongdd.utils.XMLCrawler;
import duongdd.utils.XMLSign;
import duongdd.xpaths.PinSolarXpaths;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuPinSolarCrawlers {
    private String htmlContent = "";
    private List<CategoryPinSolarEntity> listCategoryDB;

    public void crawlPinSolar() throws ParserConfigurationException, XMLStreamException, SAXException, IOException {
        crawlPinSolarMenu();
        crawlCategoryPinSolar();
        crawlProductPinSolar();
    }

    public String crawlPinSolarMenu() throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        htmlContent = XMLCrawler.crawlData(XMLSign.Pin_Solar_Domain, XMLSign.Pin_Solar_Menu_beginSign, XMLSign.Pin_Solar_Menu_endSign);
        htmlContent = XMLChecker.encodeContent(htmlContent);
        htmlContent = XMLChecker.fixTagName(htmlContent);
        return htmlContent;
    }

    public void crawlCategoryPinSolar() {
        Map<String, String> categoryMap = new HashMap<>();
        PinSolarXpaths xpaths = new PinSolarXpaths();
        CategoryPinSolarDAO dao = new CategoryPinSolarDAO();
        categoryMap = xpaths.xpathMenuPinSolar(htmlContent);
        for (Map.Entry<String, String> entryCategory : categoryMap.entrySet()) {
            String name = entryCategory.getKey();
            dao.insertCategory(name.trim());
        }
        listCategoryDB = dao.getAllCategory();
    }

    public void crawlProductPinSolar() throws IOException, SAXException, ParserConfigurationException, XMLStreamException {
        //get html content menu

        PinsolarProductDAO dao = new PinsolarProductDAO();
        Map<String, String> categoryMapUrlMenu = new HashMap<>();
        List<String> listUrlProduct = new ArrayList<>();
        List<String> listUrlPage = new ArrayList<>();
        String urlCategory = "";
        String urlProduct = "";
        String urlPage = "";
        String htmlContentCategory = "";
        String htmlContentPage = "";
        String nameCategory = "";
        PinSolarXpaths xpaths = new PinSolarXpaths();
        PinSolarProductCrawlers pinSolarProductCrawlers = new PinSolarProductCrawlers();
        PinSolarDTO dto = new PinSolarDTO();
        //list url menu
        categoryMapUrlMenu = xpaths.xpathMenuPinSolar(htmlContent);

        for (Map.Entry<String, String> entryProduct : categoryMapUrlMenu.entrySet()) {
            //url category
            nameCategory = entryProduct.getKey();
            urlCategory = entryProduct.getValue();

            htmlContentCategory = pinSolarProductCrawlers.crawlPageNumberSolar(nameCategory, urlCategory);
            // list url page
            listUrlPage = xpaths.xpathUrlPage(htmlContentCategory);
            for (int k = 0; k < listUrlPage.size(); k++) {
                urlPage = listUrlPage.get(k);
                htmlContentPage = pinSolarProductCrawlers.crawlPinSolar(nameCategory,urlPage);
                // list url product
                listUrlProduct = xpaths.xpathProductPinSolar(nameCategory, htmlContentPage);
                for (int j = 0; j < listUrlProduct.size(); j++) {
                    //url product
                    urlProduct = listUrlProduct.get(j);
                    dto = pinSolarProductCrawlers.crawlDetailProduct(nameCategory, urlProduct);
                    if(dto != null){
                        insertPinProduct(dto);
                    }

                }
            }

        }
    }

    public void insertPinProduct(PinSolarDTO dto) {
        PinsolarProductDAO dao = new PinsolarProductDAO();
        if (dto.getPinName() != null && dto.getPinCapacity() != 0.0 && dto.getPinPrice() != null && dto.getPinImage() != null && dto.getPinCategory() != null) {
            PinSolarProductEntity productEntity = new PinSolarProductEntity();
            try {
                productEntity.setNamePinSolar(dto.getPinName());

                productEntity.setCapacityPinSolar(dto.getPinCapacity());

                productEntity.setPricePinSolar(dto.getPinPrice());

                productEntity.setImagePinSolar(dto.getPinImage());

                for (int i = 0; i < listCategoryDB.size(); i++) {
                    if (dto.getPinCategory() != null) {
                        if (dto.getPinCategory().equals(listCategoryDB.get(i).getNameCategoryPinSolar())) {
                            productEntity.setIdCategoryPinSolar(listCategoryDB.get(i).getIdCategoryPinSolar());
                            break;
                        }
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            boolean validate = Jaxb.doubleCheckPinSolarProduct(XMLSign.FILE_PATH_PIN_SOLAR_PRODUCT, productEntity);
            if(validate){
                dao.insertPinSolar(productEntity);
            }

        }
    }


}
