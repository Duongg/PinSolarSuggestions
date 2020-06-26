package duongdd.xpaths;

import duongdd.dtos.ProductDTO;
import duongdd.utils.XMLSign;
import duongdd.utils.XMLUtils;
import duongdd.utils.XMLValidate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuLightXpaths {
    public List xpathUrlLightMenu(String content) {
        List<String> listUrlLightMenu = new ArrayList<>();
        String urlTemp = "";
        String urlMenu = "";
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//div[@class='container']//ul//li//a[position() < last()]";
                NodeList nodeListUrl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if (nodeListUrl != null) {
                    for (int i = 0; i < nodeListUrl.getLength(); i++) {
                        urlTemp = nodeListUrl.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        urlMenu = XMLSign.Light_Domain + urlTemp;
                        listUrlLightMenu.add(urlMenu);
                    }
                }
                return listUrlLightMenu;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductDTO xpathDetailLightProduct(String htmlcontent) {
        float capacityProduct = 0;
        String nameProduct = "";
        String capacity = "";
        List<ProductDTO> listProduct = new ArrayList<>();
        XMLValidate validate = new XMLValidate();
        float cap1 = 0, cap2 = 0;
        String strNameTemp = "";
        ProductDTO dto = new ProductDTO();
        try {
            Document doc = XMLUtils.parseToDom(htmlcontent.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String expName = "//div[@class='properties']/span[position()=1]/a//text()";
                String expCapacity = "//div[@class='properties']/div[2]/span/a/text()";
                NodeList nameNode = (NodeList) xPath.evaluate(expName, doc, XPathConstants.NODESET);
                NodeList capacityNode = (NodeList) xPath.evaluate(expCapacity, doc, XPathConstants.NODESET);
                for (int i = 0; i < nameNode.getLength(); i++) {
                    strNameTemp = nameNode.item(i).getTextContent();
                    if (!strNameTemp.contains("PHỤ KIỆN")) {
                        nameProduct = validate.parseStringLight(strNameTemp);
                    }
                    try {
                        if (capacityNode.item(i) != null) {
                            capacity = capacityNode.item(i).getTextContent().toLowerCase().trim();
                            int pos = capacity.indexOf("w");
                            if (capacity.charAt(1) == '*') {
                                int pos_star = capacity.indexOf("*");
                                String p1 = capacity.substring(0, pos_star);
                                String p2 = capacity.substring(pos_star + 1, pos);
                                cap1 = Float.parseFloat(p1);
                                cap2 = Float.parseFloat(p2);
                                capacityProduct = cap1 * cap2;
                            } else {
                                String strTemp = capacity.substring(0, pos);
                                capacityProduct = Float.parseFloat(strTemp);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if(capacityProduct != 0.0) {
                        dto.setProductName(nameProduct);
                        dto.setProductCapacity(capacityProduct);

                        System.out.println(dto.getProductName());
                        System.out.println(dto.getProductCapacity());
                    }

                }


            }
            return dto;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
