package duongdd.xpaths;

import duongdd.utils.XMLUtils;
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

public class CategoryDienMayXpaths {

    // get url page number
    public List xpathUrlPage(String content) {
        List<String> listUrlPage = new ArrayList<>();
        String urlTemp = "";
        String urlPage = "";
        try {
            Document doc = XMLUtils.parseToDom(content.trim());
            if (doc != null) {
                XPath xPath = XMLUtils.createXpath();
                String exp = "//ul[@class='pagination']//li[position()>1]//a";
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
                        String urlFull = urlPage + i;
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
                String exp = "//ul[@class='pagination']//li[last()-2]//a";
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


}
