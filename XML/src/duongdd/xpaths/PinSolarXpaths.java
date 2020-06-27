package duongdd.xpaths;

import duongdd.dtos.PinSolarDTO;
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

public class PinSolarXpaths {
    public List xpathMenuPinSolar(String content) {
        List<String> listUrl = new ArrayList<>();
        String urlTemp = "";
        String urlMenu = "";
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String expUrl = "//ul[@class='ls menu-hori-1']/li/a[@title=\"Năng lượng mặt trời\" or @title=\"Inverter  \" or @title=\"Bộ hòa lưới\"]";
                NodeList urlNode = (NodeList) xPath.evaluate(expUrl, doc, XPathConstants.NODESET);
                for (int i = 0; i < urlNode.getLength(); i++) {
                    urlTemp = urlNode.item(i).getAttributes().getNamedItem("href").getNodeValue();
                    urlMenu = XMLSign.Pin_Solar_Domain + urlTemp;
                    listUrl.add(urlMenu);
                }
            }
            return listUrl;
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

    public List xpathProductPinSolar(String content) {

        String urlProduct = "";
        String urlTemp = "";
        List<String> listUrlProduct = new ArrayList<>();
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String expUrl = "//div[@class='ProductDetails']//a";
                NodeList listNode = (NodeList) xPath.evaluate(expUrl, doc, XPathConstants.NODESET);
                if (listNode != null) {
                    for (int i = 0; i < listNode.getLength(); i++) {
                        urlTemp = listNode.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        urlProduct = XMLSign.Pin_Solar_Domain + urlTemp;
                        listUrlProduct.add(urlProduct);
                    }
                }
                return listUrlProduct;
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

    public PinSolarDTO xpathDetailPinSolar(String content) {
        PinSolarDTO dto = new PinSolarDTO();
        XMLValidate validate = new XMLValidate();
        String capacity ="";
        float capacityProduct = 0;
        try {
            Document doc = XMLUtils.parseToDom(content);
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String expName = "//div[@class='product-title']/h1";
                String expPrice = "//div[@class='Row Price']/div";
                String expImage = "//div[@class='ProductThumbImage']/a";

                String expCap = "//tr[2]/td[3]/p[contains(text(),'W')]/text()";
                String expCap_02 ="//div[@class='ProductDescriptionContainer']/p[contains(text(),'W')]/span/text()";
                String expCap_03 = "//tr[3]/td[2]/p[contains(text(),'W')]/text()";
                String expCap_04 = "//tr[2]/td[2]/p[contains(text(),'W')]/text()";
                String expCap_05 = "//tr[3]/td[2]/span/text()";
                Node nodeName = (Node) xPath.evaluate(expName, doc, XPathConstants.NODE);
                Node nodePrice = (Node) xPath.evaluate(expPrice, doc, XPathConstants.NODE);
                Node nodeImage = (Node) xPath.evaluate(expImage, doc, XPathConstants.NODE);

                Node nodeCap = (Node) xPath.evaluate(expCap, doc, XPathConstants.NODE);
                Node nodeCap_02 = (Node) xPath.evaluate(expCap_02, doc, XPathConstants.NODE);
                Node nodeCap_03 = (Node) xPath.evaluate(expCap_03, doc, XPathConstants.NODE);
                Node nodeCap_04 = (Node) xPath.evaluate(expCap_04, doc, XPathConstants.NODE);
                Node nodeCap_05 = (Node) xPath.evaluate(expCap_05, doc, XPathConstants.NODE);
                String pinName = nodeName.getTextContent();
                String pinPrice = nodePrice.getTextContent();
                String pinImage = nodeImage.getAttributes().getNamedItem("href").getNodeValue();
                System.out.println(pinName);
                if(capacity.equals("") && nodeCap != null){
                    capacity = nodeCap.getTextContent();
                }else if(capacity.equals("")&& nodeCap_02 != null){
                    capacity = nodeCap_02.getTextContent();
                }else if(capacity.equals("") && nodeCap_03 != null){
                    capacity = nodeCap_03.getTextContent();
                }else if(capacity.equals("") && nodeCap_04 != null) {
                    capacity = nodeCap_04.getTextContent();
                }else if(capacity.equals("") && nodeCap_05 != null) {
                    capacity = nodeCap_05.getTextContent();
                }else if(capacity.equals("")){
                    capacity = validate.cutStringCapacityPinSolar(pinName);
                }

//                System.out.println(pinPrice);
//                System.out.println(pinImage);
                capacityProduct = validate.parseToFloat(capacity);
                System.out.println(capacityProduct);


                return dto;
            }

        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());e.printStackTrace();
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    //xpath url pages
    public List xpathUrlPage(String content){
        List<String> listUrl = new ArrayList<>();
        String urlTemp = "";
        String urlPage = "";
        try{
            Document doc = XMLUtils.parseToDom(content.trim());
            if(doc != null){
                XPath xPath = XMLUtils.createXpath();
                String exp = ".//document/a[position() < last() -1]";
                NodeList nodeList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if(nodeList != null){
                    for(int i = 0; i < nodeList.getLength(); i++){
                        urlTemp = nodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        urlPage = XMLSign.Pin_Solar_Domain + urlTemp;
                        listUrl.add(urlPage);
                    }
                }
                return listUrl;
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
