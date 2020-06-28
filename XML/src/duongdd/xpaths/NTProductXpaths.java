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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NTProductXpaths {
    public List xpathUrlPageNT(String content) {
        List<String> listUrlPage = new ArrayList<>();
        String urlTemp = "";
        String urlPage = "";
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//div[@class='paging-top']//a[position()>1]";
                NodeList nodeListUrl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeListUrl.getLength(); i++) {
                    urlTemp = nodeListUrl.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    break;
                }

                if (urlTemp.contains("page=")) {
                    int pos = urlTemp.indexOf("2");
                    urlPage = urlTemp.substring(0, pos);
                    int maxPage = getMaxPage(content.trim());
                    for (int i = 1; i <= maxPage; i++) {
                        String urlFull = XMLSign.NT_Domain + urlPage + i;
                        listUrlPage.add(urlFull);
                    }//end for add number to url page
                }

                return listUrlPage;
            }// end if doc
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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
    public int getMaxPage(String content) {
        int maxPage = 1;
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//div[@class='paging-top']//a[last()-1]";
                Node nodePage = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
                if (nodePage != null) {
                    String strMaxPage = nodePage.getTextContent();
                    try {
                        maxPage = Integer.parseInt(strMaxPage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
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
        return maxPage;
    }
    public List xpathUrlDetailProduct(String content){
        List<String> listUrlDetailProduct = new ArrayList<>();
        String urlDetail = "";
        String urlDetailFull = "";
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//a[@class='p-name']";
                NodeList nodeListLink = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if (nodeListLink != null) {
                    for (int i = 0; i < nodeListLink.getLength(); i++) {
                        urlDetail = nodeListLink.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        urlDetailFull = XMLSign.NT_Domain + urlDetail;
                        listUrlDetailProduct.add(urlDetailFull);

                    }//end for
                }// end if
                return listUrlDetailProduct;
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
    public ProductDTO xpathProductNT(String content){
        ProductDTO dto = new ProductDTO();
        XMLValidate validate = new XMLValidate();
        String nameProduct = "";
        String capacity = "";
        String strCapacity = "";
        float capacityProduct = 0;
        try{
            Document doc = XMLUtils.parseToDom(content.trim());
            if(doc != null){
                XPath xPath = XMLUtils.createXpath();
                String expName ="//h1";
                Node nameNode = (Node) xPath.evaluate(expName,doc,XPathConstants.NODE);
                nameProduct = nameNode.getTextContent();

                String expCapacity_01 = "//tr[8]/td/span[normalize-space(text())][contains(text(),'W')]/text()";
                String expCapacity_02 = "//tr[4]/td/span[normalize-space(text())][contains(text(),'W')]/text()";
                String expCapacity_03 = "//tr[7]/td/span[normalize-space(text())][contains(text(),'W')]/text()";
                String expCapacity_04 = "//tr[7]/td[normalize-space(text())][contains(text(),'W')]/text()";
                Node capacityNode_c1 = (Node) xPath.evaluate(expCapacity_01, doc, XPathConstants.NODE);
                Node capacityNode_c2 = (Node) xPath.evaluate(expCapacity_02, doc, XPathConstants.NODE);
                Node capacityNode_c3 = (Node) xPath.evaluate(expCapacity_03, doc, XPathConstants.NODE);
                Node capacityNode_c4 = (Node) xPath.evaluate(expCapacity_04, doc, XPathConstants.NODE);
                if(capacity.equals("") && capacityNode_c1 != null){
                    capacity = capacityNode_c1.getTextContent();
                }
                if(capacity.equals("") && capacityNode_c2 != null){
                    capacity = capacityNode_c2.getTextContent();
                }
                if(capacity.equals("") && capacityNode_c3 != null){
                    capacity = capacityNode_c3.getTextContent();
                }
                if(capacity.equals("") && capacityNode_c4 != null){
                    capacity = capacityNode_c4.getTextContent();
                }
                strCapacity = validate.convertStringDetail(capacity.toLowerCase());

                capacityProduct = validate.convertKwhPerDayToWatt(strCapacity.trim());
                if(capacityProduct != 0.0){
                    dto.setProductName(nameProduct);
                    dto.setProductCapacity(capacityProduct);
                    System.out.println(nameProduct);
                    System.out.println(capacityProduct);
                }

                return dto;
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
