package duongdd.xpaths;

import duongdd.utils.XMLSign;
import duongdd.utils.XMLUtils;
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

public class MenuDienMayXpaths {
    public List<String> listUrlMenu;
    //get url menu
    public List xpathUrlMenu(String htmlContent){
        String urlMenu = "";
        if(listUrlMenu == null){
            listUrlMenu = new ArrayList<>();
        }
        try{
            Document doc = XMLUtils.parseToDom(htmlContent.trim());
            if(doc != null){
                XPath xPath = XMLUtils.createXpath();
                String exp = "//li//a";
                NodeList nodeListUrl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if(nodeListUrl != null){
                    for(int i = 0; i < nodeListUrl.getLength(); i++){
                        urlMenu = nodeListUrl.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        if(!urlMenu.equals("#") && !urlMenu.contains("index.php?route=product")){
                            listUrlMenu.add(urlMenu);
                        }
                    }
                }
                return listUrlMenu;
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


    public List xpathUrlMenuNT(String htmlContent){
        String urlMenu = "";
        String urlTemp = "";
        List<String> listUrlMenuNT = new ArrayList<>();

        try{
            Document doc = XMLUtils.parseToDom(htmlContent.trim());
            if(doc != null){
                XPath xPath = XMLUtils.createXpath();
                String exp = "//a[@class='root']";
                NodeList nodeListUrl = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if(nodeListUrl != null){
                    for(int i = 0; i < nodeListUrl.getLength(); i++){
                        urlTemp = nodeListUrl.item(i).getAttributes().getNamedItem("href").getNodeValue();
                        if(!urlTemp.contains("/tivi")) {
                            urlMenu = XMLSign.NT_Domain + urlTemp;
                            listUrlMenuNT.add(urlMenu);
                        }
                    }
                }
                return listUrlMenuNT;
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
