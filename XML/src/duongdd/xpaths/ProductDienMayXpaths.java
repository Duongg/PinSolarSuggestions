package duongdd.xpaths;

import duongdd.dtos.ProductDTO;
import duongdd.entity.ElectricProductEntity;
import duongdd.utils.XMLUtils;
import duongdd.utils.XMLValidate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDienMayXpaths {
    //xpath detail product
    public ProductDTO xpathProduct(String nameCategory,String html) {

        ProductDTO dto = new ProductDTO();
        String strCapa = "";
        String capacity = "";
        String capa = "";
        double capacityProduct = 0;
        try {
            Document doc = XMLUtils.parseToDom(html.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String expName = "//document/h1/text()";
                String expCapacity_c1 = "//td[contains(text(),\"Công suất\")]//following-sibling::td//text()";
                String expCapacity_c2 = "//span[contains(text(),\"Côngsuất\")]//text()";
                String expCapacity_c3 = "//span[contains(text(),\"CôngSuất\")]//text()";
                String expCapacity_c4 = "//div[contains(text(),\"Công suất\")]//text()";
                String expCapacity_c5 = "//li[contains(text(),\"công suất\")]//text()";
                String expCapacity_c6 = "//p[contains(text(),\"Công suất\")]//text()";
                String expCapacity_c7 = "//li[contains(text(),\"W\")]//text()";
                String expCapacity_c8 = "//span[contains(text(),\"Công suất\")]//text()";
                String expCapacity_c9 = "//span[contains(text(),\"Công suất\")]//following-sibling::span";
                String expCapacity_c10 = "//tr[2]//td[2]//span[contains(text(),\"W\")]//text()";

                Node nameNode = (Node) xPath.evaluate(expName, doc, XPathConstants.NODE);
                String nameProduct = nameNode.getTextContent().trim();

                NodeList capacityNode_c1 = (NodeList) xPath.evaluate(expCapacity_c1, doc, XPathConstants.NODESET);
                Node capacityNode_c2 = (Node) xPath.evaluate(expCapacity_c2, doc, XPathConstants.NODE);
                Node capacityNode_c3 = (Node) xPath.evaluate(expCapacity_c3, doc, XPathConstants.NODE);
                Node capacityNode_c4 = (Node) xPath.evaluate(expCapacity_c4, doc, XPathConstants.NODE);
                Node capacityNode_c5 = (Node) xPath.evaluate(expCapacity_c5, doc, XPathConstants.NODE);
                Node capacityNode_c6 = (Node) xPath.evaluate(expCapacity_c6, doc, XPathConstants.NODE);
                Node capacityNode_c7 = (Node) xPath.evaluate(expCapacity_c7, doc, XPathConstants.NODE);
                Node capacityNode_c8 = (Node) xPath.evaluate(expCapacity_c8, doc, XPathConstants.NODE);
                Node capacityNode_c9 = (Node) xPath.evaluate(expCapacity_c9, doc, XPathConstants.NODE);
                Node capacityNode_c10 = (Node) xPath.evaluate(expCapacity_c10, doc, XPathConstants.NODE);

                if (capacityNode_c1 != null) {
                    for (int i = 0; i < capacityNode_c1.getLength(); i++) {
                        strCapa = capacityNode_c1.item(i).getTextContent();
                    }
                    capacity = capacity + strCapa;
                }

                if (capacity.equals("") && capacityNode_c2 != null) {
                    capacity = capacityNode_c2.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c3 != null) {
                    capacity = capacityNode_c3.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c4 != null) {
                    capacity = capacityNode_c4.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c5 != null) {
                    capacity = capacityNode_c5.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c6 != null) {
                    capacity = capacityNode_c6.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c7 != null) {
                    capacity = capacityNode_c7.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c8 != null) {
                    capacity = capacityNode_c8.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c9 != null) {
                    capacity = capacityNode_c9.getTextContent();
                }
                if (capacity.equals("") && capacityNode_c10 != null) {
                    capacity = capacityNode_c10.getTextContent();
                }
                XMLValidate validate = new XMLValidate();
                if (!capacity.equals("")) {
                    capa = validate.convertStringCapacity(capacity);
                    capacityProduct = validate.parseStrCapaToDouble(capa);
                    if (capacityProduct > 20.0) {
                        dto.setProductName(nameProduct);
                        dto.setProductCapacity(capacityProduct);
                        dto.setProductCategory(nameCategory);
                        return dto;
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // xpath get url detail product
    public List xpathUrlDetailProduct(String nameCategory,String content) {
        List<String> listUrlDetail = new ArrayList<>();
        String urlDetail = "";
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//div[@class='caption pt-1 border-top']//h3//a";
                NodeList nodeListLink = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if (nodeListLink != null) {
                    for (int i = 0; i < nodeListLink.getLength(); i++) {
                        urlDetail = nodeListLink.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        if (!urlDetail.contains("index.php?route=product")
                                && !urlDetail.contains("am-thanh")
                                && !urlDetail.contains("bep-")){
                            listUrlDetail.add(urlDetail);
                        }
                    }//end for
                }// end if
                return listUrlDetail;
            }
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //xpath brand
    public List xpathBrandProduct(String content){
        List<String> listBrand = new ArrayList<>();
        String nameBrand = "";
        try{
            Document doc = XMLUtils.parseToDom(content.trim());
            if(doc != null){
                XPath xPath = XMLUtils.createXpath();
                String exp = "//div[@class='radio' and position() >1]//text()";
                NodeList nodeList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if(nodeList != null){
                    for (int i = 0; i < nodeList.getLength(); i++){
                        nameBrand = nodeList.item(i).getTextContent();
                        listBrand.add(nameBrand);
                    }
                }
                return listBrand;
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
}
