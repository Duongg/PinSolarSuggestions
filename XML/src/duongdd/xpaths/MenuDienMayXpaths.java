package duongdd.xpaths;


import duongdd.utils.XMLSign;
import duongdd.utils.XMLUtils;
import org.w3c.dom.Document;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuDienMayXpaths {
    //get url menu
    public Map<String, List<String>> xpathUrlMenu(String htmlContent) {
        String urlMenu = "";
        String nameCategory = "";
        List<String> listUrlDL = new ArrayList<>();
        List<String> listUrlGD = new ArrayList<>();
        List<String> listUrlTB = new ArrayList<>();
        List<String> listUrlTV = new ArrayList<>();
        List<String> listUrlML = new ArrayList<>();
        List<String> listUrlTL = new ArrayList<>();
        List<String> listUrlMG = new ArrayList<>();
        Map<String, List<String>> categoryMap = new HashMap<>();
        try {
            Document doc = XMLUtils.parseToDom(htmlContent.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String expNameCate = "//li/a[contains(@class, 'nav-link')]";
                String exp = "//li//a";
                NodeList nodeListUrl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                NodeList nodeListNameCate = (NodeList) xPath.evaluate(expNameCate, doc, XPathConstants.NODESET);
                if (nodeListNameCate != null) {
                    if (nodeListUrl != null) {
                        for (int k = 0; k < nodeListNameCate.getLength(); k++) {
                            nameCategory = nodeListNameCate.item(k).getTextContent().trim().toUpperCase();
                            for (int i = 0; i < nodeListUrl.getLength(); i++) {
                                urlMenu = nodeListUrl.item(i).getAttributes().getNamedItem("href").getNodeValue();
                                if (!nameCategory.equals("") && !nameCategory.equals("Âm thanh")
                                        && !urlMenu.equals("")
                                        && !urlMenu.equals("#")
                                        && !urlMenu.contains("index.php?route=product")
                                        && !urlMenu.contains("am-thanh")) {
                                    if (nameCategory.equals("ĐIỆN LẠNH")) {
                                        if (urlMenu.contains("dien-lanh") || urlMenu.contains("may-loc-khi")) {
                                            listUrlDL.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlDL);
                                        }
                                    } else if (nameCategory.equals("GIA DỤNG")) {
                                        if (urlMenu.contains("gia-dung")) {
                                            listUrlGD.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlGD);
                                        }
                                    } else if (nameCategory.equals("THIẾT BỊ BẾP")) {
                                        if (urlMenu.contains("thiet-bi-bep")) {
                                            listUrlTB.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlTB);
                                        }
                                    }else if(nameCategory.equals("TIVI")){
                                        if(urlMenu.contains("tivi")){
                                            listUrlTV.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlTV);
                                        }
                                    }else if(nameCategory.equals("MÁY LẠNH")){
                                        if(urlMenu.contains("may-lanh")){
                                            listUrlML.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlML);
                                        }
                                    }else if(nameCategory.equals("TỦ LẠNH")) {
                                        if (urlMenu.contains("tu-lanh")) {
                                            listUrlTL.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlTL);
                                        }
                                    }else if(nameCategory.contains("MÁY GIẶT")) {
                                        if (urlMenu.contains("may-giat")) {
                                            listUrlMG.add(urlMenu);
                                            categoryMap.put(nameCategory, listUrlMG);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return categoryMap;
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

    public Map<String, String> xpathUrlMenuNT(String htmlContent) {
        String urlMenu = "";
        String urlTemp = "";
        String nameCategory = "";
        Map<String, String> categoryMap = new HashMap<>();
        try {
            Document doc = XMLUtils.parseToDom(htmlContent.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//a[@class='root']";
                NodeList nodeListUrl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if (nodeListUrl != null) {
                    for (int i = 0; i < nodeListUrl.getLength(); i++) {
                        nameCategory = nodeListUrl.item(i).getTextContent().trim().toUpperCase();
                        if(nameCategory.equals("ĐIỀU HÒA")){
                            nameCategory = "MÁY LẠNH";
                        }
                        urlTemp = nodeListUrl.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        if (!urlTemp.contains("/tivi") && !urlTemp.contains("tin-khuyen-mai") && !nameCategory.equals("TIN TỨC")) {
                            urlMenu = XMLSign.NT_Domain + urlTemp;
                            categoryMap.put(nameCategory, urlMenu);
                        }
                    }
                }
                return categoryMap;
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
